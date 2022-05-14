package tw.momocraft.coreplus.utils.customcommand;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.api.CommandInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;
import tw.momocraft.coreplus.utils.effect.ParticleMap;
import tw.momocraft.coreplus.utils.effect.ParticleUtils;
import tw.momocraft.coreplus.utils.effect.SoundMap;
import tw.momocraft.coreplus.utils.effect.SoundUtils;
import tw.momocraft.coreplus.utils.message.TitleMsgMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager implements CommandInterface {

    // Table<PlayerName, expireTime, Command>
    private final Table<String, Long, String> onlineCmdTable = HashBasedTable.create();

    public Table<String, Long, String> getOnlineCmdTable() {
        return onlineCmdTable;
    }

    @Override
    public void addOnlineCmd(String pluginName, String playerName, int expiration, String command) {
        long waitingPair = System.currentTimeMillis() + expiration * 1000L;
        onlineCmdTable.put(playerName, waitingPair, command);
        new BukkitRunnable() {
            @Override
            public void run() {
                onlineCmdTable.remove(playerName, waitingPair);
                UtilsHandler.getMsg().sendConsoleMsg(ConfigHandler.getPrefix(),
                        "Online command - Player: " + playerName + ", Expiration: " + expiration + ", Command: " + command);
            }
        }.runTaskLater(CorePlus.getInstance(), expiration * 20L);
    }

    // custom: <group>, <arg1>, <arg2>, <arg...>
    @Override
    public void sendGroupCmd(String pluginName, Player player, String input) {
        if (input == null)
            return;
        String[] split = input.split(", ");
        String groupName = split[0];
        List<String> cmdList = ConfigHandler.getConfigPath().getCmdProp().get(groupName);
        if (cmdList == null || cmdList.isEmpty()) {
            UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                    "An error occurred while executing command: \"custom: " + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                    "Can not find the group of \"" + groupName + "\" in CorePlus/commands.yml.");
            return;
        }
        List<String> newCmdList = new ArrayList<>();
        for (String command : cmdList) {
            for (int i = 1; i < +split.length; i++) {
                // Format: "custom: <group>, ij: <node>"
                if (UtilsHandler.getDepend().ItemJoinEnabled())
                    if (split[i].startsWith("ij: "))
                        try {
                            split[i] = UtilsHandler.getDepend().getItemJoinAPI().
                                    getItemStack(player, split[i].substring(4)).getItemMeta().getDisplayName();
                        } catch (Exception ignored) {
                            split[i] = "";
                        }
                command = command.replace("%cmd_arg" + i + "%", split[i]);
            }
            newCmdList.add(command);
        }
        newCmdList = UtilsHandler.getMsg().transLang(player, newCmdList);
        for (String cmd : newCmdList)
            dispatchCustomCmd(pluginName, player, cmd);
    }

    @Override
    public void sendCmd(String pluginName, Player player, List<String> input) {
        if (input == null || input.isEmpty())
            return;
        List<String> newList = new ArrayList<>();
        for (String value : input) {
            if (value.contains("{n}")) {
                String[] split = value.split("\\{n}");
                newList.addAll(Arrays.asList(split));
                continue;
            }
            newList.add(value);
        }
        for (String value : newList)
            sendCmd(pluginName, player, value);
    }

    @Override
    public void sendCmd(String pluginName, List<String> input) {
        if (input == null || input.isEmpty())
            return;
        List<String> newList = new ArrayList<>();
        for (String value : input) {
            if (value.contains("{n}")) {
                String[] split = value.split("\\{n}");
                newList.addAll(Arrays.asList(split));
                continue;
            }
            newList.add(value);
        }
        for (String value : newList)
            sendCmd(pluginName, null, value);
    }

    @Override
    public void sendCmd(String pluginName, String input) {
        sendCmd(pluginName, null, input);
    }

    @Override
    public void sendCmd(String pluginName, Player player, String input) {
        if (input == null || input.isEmpty())
            return;
        if (input.contains("{n}")) {
            String[] split = input.split("\\{n}");
            sendCmd(pluginName, player, new ArrayList<>(Arrays.asList(split)));
            return;
        }
        if (input.startsWith("delay: ")) {
            String delay;
            try {
                delay = input.split(": ")[1];
                delay = delay.substring(0, delay.lastIndexOf("{n}"));
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of command \"delay: " + input + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
                UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), pluginName, ex);
                return;
            }
            input = input.replace("delay: " + delay, "");
            String finalInput = input;
            new BukkitRunnable() {
                @Override
                public void run() {
                    dispatchCustomCmd(pluginName, player, finalInput);
                }
            }.runTaskLater(CorePlus.getInstance(), Integer.parseInt(delay));
            return;
        }
        dispatchCustomCmd(pluginName, player, input);
    }

    private void dispatchCustomCmd(String pluginName, Player player, String input) {
        if (player == null) {
            dispatchCustomCmd(pluginName, input);
            return;
        }
        String[] split;
        String subInput = input.substring(input.indexOf(": ") + 2);
        try {
            switch (input.split(": ")[0]) {
                // Message
                case "print":
                    UtilsHandler.getMsg().sendConsoleMsg(subInput);
                    return;
                case "log":
                    dispatchLog(pluginName, subInput);
                    return;
                case "log-group":
                    dispatchLogGroup(pluginName, subInput);
                    return;
                case "broadcast":
                    UtilsHandler.getMsg().sendBroadcastMsg(subInput);
                    return;
                case "discord-chat":
                    if (!UtilsHandler.getDepend().DiscordSRVEnabled()) {
                        UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                                "Can not send discord message: " + input);
                        UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                                "Reason: DiscordSRV plugin not enabled.");
                        return;
                    }
                    split = subInput.split(", ");
                    UtilsHandler.getMsg().sendDiscordMsg("", split[0], split[1], player);
                    return;
                case "discord":
                    if (!UtilsHandler.getDepend().DiscordSRVEnabled()) {
                        UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                                "Can not send discord message: " + input);
                        UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                                "Reason: DiscordSRV plugin not enabled.");
                        return;
                    }
                    split = subInput.split(", ");
                    UtilsHandler.getMsg().sendDiscordMsg(split[0], split[1]);
                    return;
                case "bungee":
                    dispatchBungeeCmd(pluginName, player, subInput);
                    return;
                case "switch":
                    dispatchSwitch(pluginName, subInput);
                    return;
                case "console":
                    dispatchConsoleCmd(pluginName, subInput);
                    return;
                case "op":
                    dispatchOpCmd(pluginName, player, subInput);
                    return;
                case "player":
                    dispatchPlayerCmd(pluginName, player, subInput);
                    return;
                case "chat-op":
                    sendChatOpMsg("", player, subInput);
                    return;
                case "chat":
                    UtilsHandler.getMsg().sendChatMsg(player, subInput);
                    return;
                case "message":
                    UtilsHandler.getMsg().sendPlayerMsg(player, subInput);
                    return;
                case "actionbar":
                case "actionbar-group":
                    UtilsHandler.getMsg().sendActionBarMsg(player, subInput);
                    return;
                case "title":
                    dispatchTitleMsg(ConfigHandler.getPluginName(), player, subInput);
                    return;
                case "title-group":
                    dispatchTitleMsgGroup(ConfigHandler.getPluginName(), player, subInput);
                    return;
                case "sound":
                    dispatchSound(pluginName, player, subInput);
                    return;
                case "sound-group":
                    dispatchSoundGroup(pluginName, player, subInput);
                    return;
                case "particle":
                    dispatchParticle(pluginName, player.getLocation(), subInput);
                    return;
                case "particle-group":
                    dispatchParticleGroup(pluginName, player.getLocation(), subInput);
                    return;
                case "custom":
                    sendGroupCmd(pluginName, player, subInput);
                    return;
                case "condition":
                    dispatchConditionCmd(pluginName, player, subInput);
                    return;
                default:
                    UtilsHandler.getMsg().sendErrorMsg(pluginName,
                            "Unknown command type: \"" + input + "\"");
                    UtilsHandler.getMsg().sendErrorMsg(pluginName,
                            "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            }
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "There is an error when executing command: \"" + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), pluginName, ex);
        }
    }

    private void dispatchCustomCmd(String pluginName, String input) {
        String[] split;
        String subInput = input.substring(input.indexOf(": ") + 2);
        try {
            switch (input.split(": ")[0]) {
                case "custom":
                    sendGroupCmd(pluginName, null, subInput);
                    return;
                case "condition":
                    dispatchConditionCmd(pluginName, null, subInput);
                    return;
                case "print":
                    UtilsHandler.getMsg().sendConsoleMsg(subInput);
                    return;
                case "log":
                    dispatchLog(pluginName, subInput);
                    return;
                case "log-group":
                    dispatchLogGroup(pluginName, subInput);
                    return;
                case "broadcast":
                    UtilsHandler.getMsg().sendBroadcastMsg(subInput);
                    return;
                case "discord":
                    if (!UtilsHandler.getDepend().DiscordSRVEnabled()) {
                        UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                                "Can not send discord message: " + input);
                        UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                                "Reason: DiscordSRV plugin not enabled.");
                        return;
                    }
                    split = subInput.split(", ");
                    UtilsHandler.getMsg().sendDiscordMsg(split[0], split[1]);
                    return;
                case "bungee":
                    dispatchBungeeCmd(pluginName, null, subInput);
                    return;
                case "console":
                    dispatchConsoleCmd(pluginName, subInput);
                    return;
                case "op":
                case "player":
                case "chat":
                case "discord-chat":
                case "message":
                case "sound":
                case "sound-group":
                case "particle":
                case "particle-group":
                case "actionbar":
                case "actionbar-group":
                case "title":
                case "title-group":
                    UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while executing command: \"" + input + "\"");
                    UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not find the execute target.");
                    return;
                default:
                    UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(), "Unknown command type: \"" + input + "\"");
                    UtilsHandler.getMsg().sendErrorMsg(pluginName, "Please check if CorePlus is updated to the latest version.");
                    UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            }
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "There is an error when executing command: \"" + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), pluginName, ex);
        }
    }

    @Override
    public void dispatchLog(String pluginName, String input) {
        if (input == null)
            return;
        try {
            UtilsHandler.getFile().getLog().add(pluginName, "Default", input);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while executing command: \"log-: " + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
        }
    }

    @Override
    public void dispatchLogGroup(String pluginName, String input) {
        if (input == null)
            return;
        try {
            String group = input.split(", ")[0];
            input = input.substring(input.indexOf(",") + 2);
            UtilsHandler.getFile().getLog().add(pluginName, group, input);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while executing command: \"log-group: " + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
        }
    }

    @Override
    public void dispatchConditionCmd(String pluginName, Player player, String input) {
        if (input == null)
            return;
        boolean succeed;
        String condition = input.substring(0, input.lastIndexOf(", ") - 1);
        String action = input.substring(input.lastIndexOf(", ") + 1);
        String[] conditionValues;
        if (condition.contains(">=")) {
            conditionValues = condition.split(">=");
            try {
                succeed = UtilsHandler.getUtil().checkCompare(">=",
                        Double.parseDouble(conditionValues[0]), Double.parseDouble(conditionValues[1]));
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of command: \"condition: " + input + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
                return;
            }
        } else if (condition.contains("<=")) {
            conditionValues = condition.split("<=");
            try {
                succeed = UtilsHandler.getUtil().checkCompare("<=",
                        Double.parseDouble(conditionValues[0]), Double.parseDouble(conditionValues[1]));
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of command: \"condition: " + input + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
                return;
            }
        } else if (condition.contains(">")) {
            conditionValues = condition.split(">");
            try {
                succeed = UtilsHandler.getUtil().checkCompare(">",
                        Double.parseDouble(conditionValues[0]), Double.parseDouble(conditionValues[1]));
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of command: \"condition: " + input + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
                return;
            }
        } else if (condition.contains("<")) {
            conditionValues = condition.split("<");
            try {
                succeed = UtilsHandler.getUtil().checkCompare("<",
                        Double.parseDouble(conditionValues[0]), Double.parseDouble(conditionValues[1]));
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of command: \"condition: " + input + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
                return;
            }
        } else if (condition.contains("=")) {
            conditionValues = condition.split("=");
            try {
                succeed = UtilsHandler.getUtil().checkCompare("=",
                        Double.parseDouble(conditionValues[0]), Double.parseDouble(conditionValues[1]));
            } catch (Exception ex) {
                succeed = conditionValues[0].equals(conditionValues[1]);
            }
        } else {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of command: \"condition: " + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            return;
        }
        String trueCmd = null;
        String falseCmd = null;
        if (action.startsWith("{true}")) {
            if (action.contains("{false}")) {
                trueCmd = action.split("\\{false}")[0];
                trueCmd = trueCmd.replace("{true}", "");
                falseCmd = action.split("\\{false}")[1];
            } else {
                trueCmd = action.replace("{true}", "");
            }
        } else if (action.startsWith("{false}")) {
            if (action.contains("{true}")) {
                falseCmd = action.split("\\{true}")[0];
                falseCmd = falseCmd.replace("{false}", "");
                trueCmd = action.split("\\{true}")[1];
            } else {
                falseCmd = action.replace("{false}", "");
            }
        } else {
            if (action.contains("{false}")) {
                trueCmd = action.split("\\{false}")[0];
                trueCmd = trueCmd.replace("{true}", "");
                falseCmd = action.split("\\{false}")[1];
            } else {
                trueCmd = action.replace("{true}", "");
            }
        }
        if (succeed)
            dispatchCustomCmd(pluginName, player, trueCmd);
        else
            dispatchCustomCmd(pluginName, player, falseCmd);
    }

    @Override
    public void dispatchConsoleCmd(String pluginName, String input) {
        if (input == null)
            return;
        try {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), input);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while executing command: \"console: " + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
        }
    }

    @Override
    public void sendChatOpMsg(String pluginName, Player player, String input) {
        if (input == null)
            return;
        input = pluginName + input;
        input = ChatColor.translateAlternateColorCodes('&', input);
        boolean isOp = player.isOp();
        try {
            player.setOp(true);
            player.chat(input);
        } catch (Exception ex) {
            player.setOp(isOp);
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while executing command: \"chat-op: " + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            removeOp(pluginName, player);
        } finally {
            player.setOp(isOp);
        }
    }

    @Override
    public void dispatchOpCmd(String pluginName, Player player, String input) {
        if (input == null)
            return;
        boolean isOp = player.isOp();
        try {
            player.setOp(true);
            player.chat("/" + input);
        } catch (Exception ex) {
            player.setOp(isOp);
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while executing command: \"op: " + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            removeOp(pluginName, player);
        } finally {
            player.setOp(isOp);
        }
    }

    private void removeOp(String pluginName, Player player) {
        String playerName = player.getName();
        UtilsHandler.getMsg().sendErrorMsg(pluginName, "Trying to remove \"&e" + playerName + "&c\" Op status...");
        Bukkit.getScheduler().scheduleSyncDelayedTask(CorePlus.getInstance(), () -> {
            try {
                player.setOp(false);
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Succeed to remove \"" + playerName + "\" Op status!");
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not remove \"&e" + playerName + "&c\" Op status.");
            }
        }, 20);
    }

    @Override
    public void dispatchPlayerCmd(String pluginName, Player player, String input) {
        if (input == null)
            return;
        try {
            player.chat("/" + input);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while executing command: \"player: " + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
        }
    }

    @Override
    public void dispatchBungeeCmd(String pluginName, Player player, String input) {
        if (input == null)
            return;
        BungeeCordUtils.ExecuteCommand(pluginName, player, input);
    }

    @Override
    public void dispatchSwitch(String pluginName, String input) {
        if (input == null)
            return;
        String[] inputSplit = input.split("\\s+");
        Player player = Bukkit.getPlayer(inputSplit[1]);
        if (player == null)
            return;
        BungeeCordUtils.SwitchServers(pluginName, player, inputSplit[0]);
    }

    public void dispatchTitleMsg(String pluginName, Player player, String input) {
        if (input == null)
            return;
        try {
            TitleMsgMap titleMsgMap = new TitleMsgMap();
            // title: <title>\n<subtitle> -i:FadeIn -o:FadeOut -s:Stay
            String[] args = input.split("\\s+");
            for (String arg : args) {
                arg = arg.toUpperCase();
                if (arg.startsWith("-i:")) {
                    titleMsgMap.setFadeIn(Integer.parseInt(arg.replace("-i:", "")));
                    input = input.replace("-i:" + arg, "");
                } else if (arg.startsWith("-o:")) {
                    titleMsgMap.setFadeOut(Integer.parseInt(arg.replace("-o:", "")));
                    input = input.replace("-o:" + arg, "");
                } else if (arg.startsWith("-s:")) {
                    titleMsgMap.setStay(Integer.parseInt(arg.replace("-s:", "")));
                    input = input.replace("-s:" + arg, "");
                }
            }
            String[] inputSplit = input.split("\\n");
            UtilsHandler.getMsg().sendTitleMsg(player, inputSplit[0], inputSplit[1],
                    titleMsgMap.getFadeIn(), titleMsgMap.getStay(), titleMsgMap.getFadeOut());
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of command: \"title: " + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
        }
    }

    public void dispatchTitleMsgGroup(String pluginName, Player player, String input) {
        if (input == null)
            return;
        String groupName = input.substring(0, input.indexOf(" ") + 1);
        String[] split = input.substring(input.indexOf(" ")).split(", ");
        try {
            TitleMsgMap titleMsgMap = ConfigHandler.getConfigPath().getTitleProp().get(groupName);
            if (titleMsgMap == null) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while executing command: \"title-group: " + input + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not find the group of \"" + groupName + "\" in CorePlus/title_messages.yml.");
                return;
            }
            // String inputTitle, String inputSubtitle, int fadeIn, int stay, int fadeOut
            UtilsHandler.getMsg().sendTitleMsg(player, split[0], split[1],
                    titleMsgMap.getFadeIn(), titleMsgMap.getStay(), titleMsgMap.getFadeOut());
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of command: \"title-group: " + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), pluginName, ex);
        }
    }

    @Override
    public void dispatchSound(String pluginName, Player player, String input) {
        if (input == null)
            return;
        try {
            SoundMap soundMap = new SoundMap();
            // sound: -s:Sound, -p:Pitch -v:Volume -t:Times -i:Interval
            String[] args = input.split("\\s+");
            for (String arg : args) {
                if (arg.startsWith("-s:")) {
                    soundMap.setType(Sound.valueOf(arg.replace("-s:", "").toUpperCase()));
                } else if (arg.startsWith("-p:")) {
                    soundMap.setPitch(Integer.parseInt(arg.replace("-p:", "")));
                } else if (arg.startsWith("-v:")) {
                    soundMap.setVolume(Integer.parseInt(arg.replace("-v:", "")));
                } else if (arg.startsWith("-i:")) {
                    soundMap.setInterval(Integer.parseInt(arg.replace("-i:", "")));
                } else if (arg.startsWith("-t:")) {
                    soundMap.setTimes(Integer.parseInt(arg.replace("-t:", "")));
                } else {
                    UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of command: \"sound: " + input + "\"");
                    UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
                }
            }
            SoundUtils.sendSound(player, player.getLocation(), soundMap);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of command: \"sound: " + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
        }
    }

    @Override
    public void dispatchSoundGroup(String pluginName, Player player, String input) {
        if (input == null)
            return;
        try {
            Location loc = player.getLocation();
            SoundMap soundMap = ConfigHandler.getConfigPath().getSoundProp().get(input);
            if (soundMap == null) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while executing command: \"sound-group: " + input + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not find the group of \"" + input + "\" in CorePlus/sound.yml.");
                return;
            }
            SoundUtils.sendSound(player, loc, soundMap);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of command: \"sound-group: " + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), pluginName, ex);
        }
    }

    @Override
    public void dispatchParticle(String pluginName, Location loc, String input) {
        if (input == null)
            return;
        try {
            ParticleMap particleMap = new ParticleMap();
            // particle: <particle> -a:Amount -t:Times -i:Interval -x:OffsetX -y:OffsetY -z:OffsetZ -e:Extra -m:Material -c:Color -rgb:0,0,0
            String value = input;
            String[] args = value.split("\\s+");
            for (String arg : args) {
                if (arg.startsWith("-p:")) {
                    particleMap.setType(Particle.valueOf(arg.replace("-p:", "").toUpperCase()));
                } else if (arg.startsWith("-a:")) {
                    particleMap.setAmount(Integer.parseInt(arg.replace("-a:", "")));
                } else if (arg.startsWith("-t:")) {
                    particleMap.setTimes(Integer.parseInt(arg.replace("-t:", "")));
                } else if (arg.startsWith("-i:")) {
                    particleMap.setInterval(Integer.parseInt(arg.replace("-i:", "")));
                } else if (arg.startsWith("-x:")) {
                    particleMap.setOffsetX(Integer.parseInt(arg.replace("-x:", "")));
                } else if (arg.startsWith("-y:")) {
                    particleMap.setOffsetY(Integer.parseInt(arg.replace("-y:", "")));
                } else if (arg.startsWith("-z:")) {
                    particleMap.setOffsetZ(Integer.parseInt(arg.replace("-z:", "")));
                } else if (arg.startsWith("-e:")) {
                    particleMap.setExtra(Integer.parseInt(arg.replace("-e:", "")));
                } else if (arg.startsWith("-m:")) {
                    particleMap.setMaterial(Material.getMaterial(arg.replace("-m:", "").toUpperCase()));
                } else if (arg.startsWith("-c:")) {
                    particleMap.setColorType(arg.replace("-c:", "").toUpperCase());
                } else if (arg.startsWith("-rgb:")) {
                    String[] rgb = arg.replace("-rgb:", "").split(",");
                    particleMap.setColorR(Integer.parseInt(rgb[0]));
                    particleMap.setColorR(Integer.parseInt(rgb[1]));
                    particleMap.setColorR(Integer.parseInt(rgb[2]));
                    value = value.replace("-rgb:" + arg, "");
                } else {
                    UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of command: \"particle: " + input + "\"");
                    UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
                    return;
                }
            }
            ParticleUtils.spawnParticle(loc, particleMap);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of command: \"particle: " + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), pluginName, ex);
        }
    }

    @Override
    public void dispatchParticleGroup(String pluginName, Location loc, String group) {
        try {
            if (group == null)
                return;
            ParticleMap particleMap = ConfigHandler.getConfigPath().getParticleProp().get(group);
            if (particleMap == null) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while executing command: \"particle-group: " + group + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not find the group of \"" + group + "\" in CorePlus/particle.yml.");
                return;
            }
            ParticleUtils.spawnParticle(loc, particleMap);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of command: \"particle-group: " + group + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), pluginName, ex);
        }
    }
}
