package tw.momocraft.coreplus.utils.customcommands;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import javafx.util.Pair;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.api.CommandInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomCommands implements CommandInterface {

    // Table<PlayerName, Pair<sendTime, expiration>, Command>
    private final Table<String, Pair<Long, Integer>, String> waitingTable = HashBasedTable.create();

    public Table<String, Pair<Long, Integer>, String> getWaitingTable() {
        return waitingTable;
    }

    @Override
    public void addWaiting(String playerName, int expiration, String command) {
        Pair<Long, Integer> waitingPair = new Pair<>(System.currentTimeMillis(), expiration * 1000);
        waitingTable.put(playerName, waitingPair, command);
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    waitingTable.remove(playerName, waitingPair);
                    UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPrefix(),
                            "Online command - Player: " + playerName + ", Expiration: " + expiration + ", Command: " + command);
                } catch (Exception ignored) {
                }
            }
        }.runTaskLater(CorePlus.getInstance(), expiration * 20);
    }

    @Override
    public void executeCmdList(String pluginName, List<Player> players, List<String> input, boolean placeholder, String... langHolder) {
        if (input == null)
            return;
        if (!input.isEmpty()) {
            for (String cmd : input) {
                if (cmd.startsWith("targets-")) {
                    cmd = cmd.replace("targets-", "");
                    for (Player player : players) {
                        UtilsHandler.getCustomCommands().executeCmd(pluginName, player, cmd, placeholder, langHolder);
                    }
                    continue;
                }
                UtilsHandler.getCustomCommands().executeCmd(pluginName, cmd, placeholder, langHolder);
            }
        }
    }

    @Override
    public void executeCmdList(String pluginName, Player player, List<String> input, boolean placeholder, String... langHolder) {
        if (input == null)
            return;
        if (player == null || player instanceof ConsoleCommandSender) {
            executeCmdList(pluginName, input, placeholder);
            return;
        }
        String cmd;
        for (int i = 0; i < input.size(); i++) {
            cmd = input.get(i);
            if (!cmd.startsWith("delay: ")) {
                executeCmd(pluginName, player, cmd, placeholder, langHolder);
                continue;
            }
            // Executing delay command.
            String delay;
            try {
                delay = cmd.split(": ")[1];
                delay = delay.substring(0, delay.lastIndexOf("{n}"));
            } catch (Exception ex) {
                UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format of command: \"delay: " + cmd + "\"");
                UtilsHandler.getLang().sendErrorMsg(pluginName, "&7More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), pluginName, ex);
                continue;
            }
            List<String> newCommandList = new ArrayList<>(input);
            newCommandList.subList(i + 1, newCommandList.size());
            if (cmd.contains("{n}")) {
                cmd = cmd.substring(cmd.indexOf("{n}") + 1);
                newCommandList.add(0, cmd);
            }
            String finalPrefix = pluginName;
            new BukkitRunnable() {
                @Override
                public void run() {
                    // To restart the method again after delay.
                    executeCmdList(finalPrefix, player, newCommandList, placeholder, langHolder);
                }
            }.runTaskLater(CorePlus.getInstance(), Integer.parseInt(delay));
            return;
        }
    }

    @Override
    public void executeCmdList(String pluginName, List<String> input, boolean placeholder, String... langHolder) {
        if (input == null)
            return;
        String cmd;
        for (int i = 0; i < input.size(); i++) {
            cmd = input.get(i);
            if (!cmd.startsWith("delay: ")) {
                executeCmd(pluginName, cmd, placeholder, langHolder);
                continue;
            }
            String delay;
            try {
                delay = cmd.split(": ")[1];
                delay = delay.substring(0, delay.lastIndexOf("{n}"));
            } catch (Exception ex) {
                UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format of command: \"delay: " + cmd + "\"");
                UtilsHandler.getLang().sendErrorMsg(pluginName, "&7More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), pluginName, ex);
                continue;
            }
            List<String> newCommandList = new ArrayList<>(input);
            newCommandList.subList(i + 1, newCommandList.size());
            if (cmd.contains("{n}")) {
                cmd = cmd.substring(cmd.indexOf("{n}") + 1);
                newCommandList.add(0, cmd);
            }
            String finalPrefix = pluginName;
            new BukkitRunnable() {
                @Override
                public void run() {
                    // To restart the method again after delay.
                    executeCmdList(finalPrefix, newCommandList, placeholder, langHolder);
                }
            }.runTaskLater(CorePlus.getInstance(), Integer.parseInt(delay));
            return;
        }
    }

    @Override
    public void executeCmd(String pluginName, List<Player> players, String input, boolean placeholder, String... langHolder) {
        if (input == null)
            return;
        if (input.startsWith("targets-")) {
            input = input.replace("targets-", "");
            for (Player player : players) {
                UtilsHandler.getCustomCommands().executeCmd(pluginName, player, input, placeholder, langHolder);
            }
            return;
        }
        UtilsHandler.getCustomCommands().executeCmd(pluginName, input, placeholder, langHolder);
    }

    @Override
    public void executeCmd(String pluginName, Player player, String input, boolean placeholder, String... langHolder) {
        if (input == null)
            return;
        if (player == null || player instanceof ConsoleCommandSender) {
            executeCmd(pluginName, input, placeholder);
            return;
        }
        if (input.contains("{n}")) {
            executeCmdList(pluginName, player, Arrays.asList(input.split("\\{n}")), true, langHolder);
            return;
        }
        selectCmdType(pluginName, player, input, placeholder, langHolder);
    }

    @Override
    public void executeCmd(String pluginName, String input, boolean placeholder, String... langHolder) {
        if (input == null)
            return;
        if (input.contains("{n}")) {
            executeCmdList(pluginName, Arrays.asList(input.split("\\{n}")), placeholder, langHolder);
            return;
        }
        selectCmdType(pluginName, input, placeholder, langHolder);
    }

    @Override
    public void dispatchCustomCmd(String pluginName, Player player, String group, boolean placeholder, String... langHolder) {
        if (group == null)
            return;
        String[] placeHolderArr = group.split(", ");
        List<String> commands = ConfigHandler.getConfigPath().getCmdProp().get(placeHolderArr[0]);
        List<String> newCommands = new ArrayList<>();
        if (commands == null) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while executing command: \"custom: " + group + "\"");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Can not find the group of \"" + group + "\" in CorePlus/commands.yml.");
            return;
        }
        if (commands.isEmpty())
            return;
        for (String command : commands) {
            for (int i = 1; i < +placeHolderArr.length; i++) {
                if (UtilsHandler.getDepend().ItemJoinEnabled()) {
                    if (placeHolderArr[i].contains("ij: ")) {
                        try {
                            placeHolderArr[i] = UtilsHandler.getDepend().getItemJoinUtils().
                                    getItemStack(player, placeHolderArr[i].substring(4)).getItemMeta().getDisplayName();
                        } catch (Exception ignored) {
                        }
                    }
                }
                command = command.replace("%cmd_arg" + i + "%", placeHolderArr[i]);
            }
            newCommands.add(command);
        }
        executeCmdList(pluginName, player, newCommands, placeholder, langHolder);
    }

    /**
     * Selecting the type of command for a player.
     *
     * @param pluginName  the sending plugin name
     * @param player      the target player.
     * @param input       the input command.
     * @param placeholder translating placeholders.
     */
    private void selectCmdType(String pluginName, Player player, String input, boolean placeholder, String... langHolder) {
        input = UtilsHandler.getLang().translateLangHolders(player, input, langHolder);
        if (placeholder) {
            input = UtilsHandler.getLang().translateLayout(pluginName, input, player);
        }
        switch (input.split(": ")[0]) {
            case "custom":
                input = input.replace("custom: ", "");
                dispatchCustomCmd(pluginName, player, input, placeholder);
                return;
            case "condition":
                input = input.replace("condition: ", "");
                dispatchConditionCmd(pluginName, player, input, placeholder);
                return;
            case "print":
                input = input.replace("print: ", "");
                UtilsHandler.getLang().sendConsoleMsg(pluginName, input);
                return;
            case "log":
                input = input.replace("log: ", "");
                dispatchLogCmd(pluginName, input);
                return;
            case "log-custom":
                input = input.replace("log-custom: ", "");
                dispatchLogCustomCmd(pluginName, input);
                return;
            case "broadcast":
                input = input.replace("broadcast: ", "");
                UtilsHandler.getLang().sendBroadcastMsg("", input);
                return;
            case "bungee":
                input = input.replace("bungee: ", "");
                dispatchBungeeCordCmd(pluginName, player, input);
                return;
            case "switch":
                input = input.replace("switch: ", "");
                dispatchSwitchCmd(pluginName, input);
                return;
            case "console":
                input = input.replace("console: ", "");
                dispatchConsoleCmd(pluginName, player, input);
                return;
            case "op":
                input = input.replace("op: ", "");
                dispatchOpCmd(pluginName, player, input);
                return;
            case "player":
                input = input.replace("player: ", "");
                dispatchPlayerCmd(pluginName, player, input);
                return;
            case "chat-op":
                input = input.replace("chat-op: ", "");
                sendChatOpMsg("", player, input);
                return;
            case "chat":
                input = input.replace("chat: ", "");
                UtilsHandler.getLang().sendChatMsg("", player, input);
                return;
            case "message":
                input = input.replace("message: ", "");
                UtilsHandler.getLang().sendPlayerMsg("", player, input);
                return;
            case "action-bar":
                input = input.replace("action-bar: ", "");
                UtilsHandler.getLang().sendActionBarMsg(player, input);
                return;
            case "title":
                input = input.replace("title: ", "");
                UtilsHandler.getLang().sendTitleMsg(player, input);
                return;
            case "sound":
                input = input.replace("sound: ", "");
                dispatchSoundCmd(pluginName, player, input);
                return;
            case "sound-custom":
                input = input.replace("sound-custom: ", "");
                dispatchSoundCustomCmd(pluginName, player, input);
                return;
            case "particle":
                input = input.replace("particle: ", "");
                dispatchParticleCmd(pluginName, player.getLocation(), input);
                return;
            case "particle-custom":
                input = input.replace("particle-custom: ", "");
                dispatchParticleCustomCmd(pluginName, player.getLocation(), input);
                return;
            default:
                UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginPrefix(), "Unknown command type: \"" + input + "\"");
                UtilsHandler.getLang().sendErrorMsg(pluginName, "&7More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
        }
    }

    /**
     * Selecting the type of command.
     *
     * @param pluginName  the sending plugin name
     * @param input       the input command.
     * @param placeholder translating placeholders.
     */
    private void selectCmdType(String pluginName, String input, boolean placeholder, String... langHolder) {
        input = UtilsHandler.getLang().translateLangHolders(null, input, langHolder);
        if (placeholder) {
            input = UtilsHandler.getLang().translateLayout(pluginName, input, null);
        }
        switch (input.split(": ")[0]) {
            case "custom":
                input = input.replace("custom: ", "");
                dispatchCustomCmd(pluginName, null, input, placeholder);
                return;
            case "condition":
                input = input.replace("condition: ", "");
                dispatchConditionCmd(pluginName, null, input, placeholder);
                return;
            case "print":
                input = input.replace("print: ", "");
                UtilsHandler.getLang().sendConsoleMsg(pluginName, input);
                return;
            case "log":
                input = input.replace("log: ", "");
                dispatchLogCmd(pluginName, input);
                return;
            case "log-custom":
                input = input.replace("log-custom: ", "");
                dispatchLogCustomCmd(pluginName, input);
                return;
            case "broadcast":
                input = input.replace("broadcast: ", "");
                UtilsHandler.getLang().sendBroadcastMsg("", input);
                return;
            case "bungee":
                input = input.replace("bungee: ", "");
                dispatchBungeeCordCmd(pluginName, null, input);
                return;
            case "console":
                input = input.replace("console: ", "");
                dispatchConsoleCmd(pluginName, null, input);
                return;
            case "op":
            case "player":
            case "chat":
            case "message":
            case "sound":
            case "sound-custom":
            case "particle":
            case "particle-custom":
            case "actionbar":
            case "title":
                UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while executing command: \"" + input + "\"");
                UtilsHandler.getLang().sendErrorMsg(pluginName, "Can not find the execute target.");
                return;
            default:
                UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginPrefix(), "Unknown command type: \"" + input + "\"");
                UtilsHandler.getLang().sendErrorMsg(pluginName, "&7More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
        }
    }

    @Override
    public void dispatchLogCmd(String pluginName, String input) {
        if (input == null)
            return;
        LogMap logMap = ConfigHandler.getConfigPath().getLogProp().get("Default");
        if (logMap == null) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while executing command: \"log: " + input + "\"");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Can not find the group of \"Default\" in CorePlus/logs.yml.");
            return;
        }
        try {
            UtilsHandler.getLang().addLog(pluginName, logMap.getFile(), input, logMap.isTime(), logMap.isNewFile(), logMap.isZip());
        } catch (Exception ex) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while executing command: \"log-: " + input + "\"");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "&7If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getLang().sendDebugTrace(true, pluginName, ex);
        }
    }

    @Override
    public void dispatchLogCustomCmd(String pluginName, String input) {
        if (input == null)
            return;
        String group = input.split(", ")[0];
        LogMap logMap = ConfigHandler.getConfigPath().getLogProp().get(group);
        if (logMap == null) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while executing command: \"log-custom: " + group + ", " + input + "\"");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Can not find the group of \"" + group + "\" in CorePlus/logs.yml.");
            return;
        }
        input = input.substring(input.indexOf(",") + 2);
        try {
            UtilsHandler.getLang().addLog(pluginName, logMap.getFile(), input, logMap.isTime(), logMap.isNewFile(), logMap.isZip());
        } catch (Exception ex) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while executing command: \"log-custom: " + group + ", " + input + "\"");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "&7If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getLang().sendDebugTrace(true, pluginName, ex);
        }
    }

    @Override
    public void dispatchConditionCmd(String pluginName, Player player, String input, boolean placeholder) {
        if (input == null)
            return;
        boolean type;
        String condition = input.split(", ")[0];
        String[] conditionValues;
        if (condition.contains(">=")) {
            conditionValues = condition.split(">=");
            try {
                type = UtilsHandler.getUtil().getCompare(">=",
                        Double.parseDouble(conditionValues[0]), Double.parseDouble(conditionValues[1]));
            } catch (Exception ex) {
                UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format of command: \"condition: " + input + "\"");
                UtilsHandler.getLang().sendErrorMsg(pluginName, "&7More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
                return;
            }
        } else if (condition.contains("<=")) {
            conditionValues = condition.split("<=");
            try {
                type = UtilsHandler.getUtil().getCompare("<=",
                        Double.parseDouble(conditionValues[0]), Double.parseDouble(conditionValues[1]));
            } catch (Exception ex) {
                UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format of command: \"condition: " + input + "\"");
                UtilsHandler.getLang().sendErrorMsg(pluginName, "&7More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
                return;
            }
        } else if (condition.contains(">")) {
            conditionValues = condition.split(">");
            try {
                type = UtilsHandler.getUtil().getCompare(">",
                        Double.parseDouble(conditionValues[0]), Double.parseDouble(conditionValues[1]));
            } catch (Exception ex) {
                UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format of command: \"condition: " + input + "\"");
                UtilsHandler.getLang().sendErrorMsg(pluginName, "&7More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
                return;
            }
        } else if (condition.contains("<")) {
            conditionValues = condition.split("<");
            try {
                type = UtilsHandler.getUtil().getCompare("<",
                        Double.parseDouble(conditionValues[0]), Double.parseDouble(conditionValues[1]));
            } catch (Exception ex) {
                UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format of command: \"condition: " + input + "\"");
                UtilsHandler.getLang().sendErrorMsg(pluginName, "&7More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
                return;
            }
        } else if (condition.contains("=")) {
            conditionValues = condition.split("=");
            try {
                type = UtilsHandler.getUtil().getCompare("=",
                        Double.parseDouble(conditionValues[0]), Double.parseDouble(conditionValues[1]));
            } catch (Exception ex) {
                type = conditionValues[0].equals(conditionValues[1]);
            }
        } else {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format of command: \"condition: " + input + "\"");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "&7More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            return;
        }
        String action = input.substring(input.indexOf(", "));
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
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format of command: \"condition: " + input + "\"");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "&7More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            return;
        }
        if (type) {
            executeCmd(pluginName, player, trueCmd, placeholder);
        } else {
            executeCmd(pluginName, player, falseCmd, placeholder);
        }
    }

    @Override
    public void dispatchConsoleCmd(String pluginName, Player player, String input) {
        if (input == null)
            return;
        try {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), input);
        } catch (Exception ex) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while executing command: \"console: " + input + "\"");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "&7If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getLang().sendDebugTrace(true, pluginName, ex);
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
            UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while executing command: \"chat-op: " + input + "\"");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "&7If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getLang().sendDebugTrace(true, pluginName, ex);
            removeOp(pluginName, player);
        } finally {
            player.setOp(isOp);
        }
    }

    public void dispatchOpCmd(String pluginName, Player player, String input) {
        if (input == null)
            return;
        boolean isOp = player.isOp();
        try {
            player.setOp(true);
            player.chat("/" + input);
        } catch (Exception ex) {
            player.setOp(isOp);
            UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while executing command: \"op: " + input + "\"");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "&7If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getLang().sendDebugTrace(true, pluginName, ex);
            removeOp(pluginName, player);
        } finally {
            player.setOp(isOp);
        }
    }

    private void removeOp(String pluginName, Player player) {
        String playerName = player.getName();
        UtilsHandler.getLang().sendErrorMsg(pluginName, "Trying to remove \"&e" + playerName + "&c\" Op status...");
        Bukkit.getScheduler().scheduleSyncDelayedTask(CorePlus.getInstance(), () -> {
            try {
                player.setOp(false);
                UtilsHandler.getLang().sendErrorMsg(pluginName, "&fSucceed to remove \"" + playerName + "\" Op status!");
            } catch (Exception ex) {
                UtilsHandler.getLang().sendErrorMsg(pluginName, "&cCan not remove \"&e" + playerName + "&c\" Op status.");
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
            UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while executing command: \"player: " + input + "\"");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "&7If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getLang().sendDebugTrace(true, pluginName, ex);
        }
    }

    @Override
    public void dispatchBungeeCordCmd(String pluginName, Player player, String input) {
        if (input == null)
            return;
        BungeeCordUtils.ExecuteCommand(pluginName, player, input);
    }

    @Override
    public void dispatchSwitchCmd(String pluginName, String input) {
        if (input == null)
            return;
        String[] inputSplit = input.split(" ");
        Player player = Bukkit.getPlayer(inputSplit[1]);
        if (player == null)
            return;
        BungeeCordUtils.SwitchServers(pluginName, player, inputSplit[0]);
    }

    @Override
    public void dispatchSoundCmd(String pluginName, Player player, String input) {
        if (input == null)
            return;
        try {
            String[] arr = input.split(", ");
            Location loc = player.getLocation();
            player.playSound(loc, Sound.valueOf(arr[0]), Long.parseLong(arr[1]), Long.parseLong(arr[2]));
        } catch (Exception ex) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format of command: \"sound: " + input + "\"");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "&7More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            UtilsHandler.getLang().sendDebugTrace(true, pluginName, ex);
        }
    }

    @Override
    public void dispatchSoundCustomCmd(String pluginName, Player player, String group) {
        if (group == null)
            return;
        try {
            Location loc = player.getLocation();
            SoundMap soundMap = ConfigHandler.getConfigPath().getSoundProp().get(group);
            if (soundMap == null) {
                UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while executing command: \"sound-custom: " + group + "\"");
                UtilsHandler.getLang().sendErrorMsg(pluginName, "Can not find the group of \"" + group + "\" in CorePlus/sound.yml.");
                return;
            }
            Sound sound = soundMap.getType();
            int times = soundMap.getTimes();
            int interval = soundMap.getInterval();
            long volume = soundMap.getVolume();
            long pitch = soundMap.getPitch();
            new BukkitRunnable() {
                int i = 1;

                @Override
                public void run() {
                    if (i > times) {
                        cancel();
                    } else {
                        ++i;
                        player.playSound(loc, sound, volume, pitch);
                    }
                }
            }.runTaskTimer(CorePlus.getInstance(), 0, interval);
        } catch (Exception ex) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format of command: \"sound-custom: " + group + "\"");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "&7More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), pluginName, ex);
        }
    }

    @Override
    public void dispatchParticleCmd(String pluginName, Location loc, String input) {
        if (input == null)
            return;
        try {
            String[] arr = input.split(", ");
            loc.getWorld().spawnParticle(Particle.valueOf(arr[0]), loc, Integer.parseInt(arr[1]),
                    Double.parseDouble(arr[2]), Double.parseDouble(arr[3]), Double.parseDouble(arr[4]), Double.parseDouble(arr[5]));
        } catch (Exception ex) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format of command: \"particle: " + input + "\"");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "&7More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), pluginName, ex);
        }
    }

    @Override
    public void dispatchParticleCustomCmd(String pluginName, Location loc, String group) {
        if (group == null)
            return;
        try {
            ParticleMap particleMap = ConfigHandler.getConfigPath().getParticleProp().get(group);
            if (particleMap == null) {
                UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while executing command: \"particle-custom: " + group + "\"");
                UtilsHandler.getLang().sendErrorMsg(pluginName, "Can not find the group of \"" + group + "\" in CorePlus/particle.yml.");
                return;
            }
            Particle particle = particleMap.getType();
            int amount = particleMap.getAmount();
            int times = particleMap.getTimes();
            double offsetX = particleMap.getOffsetX();
            double offsetY = particleMap.getOffsetY();
            double offsetZ = particleMap.getOffsetZ();
            double extra = particleMap.getExtra();
            new BukkitRunnable() {
                int i = 1;

                @Override
                public void run() {
                    if (i > times) {
                        cancel();
                    } else {
                        ++i;
                        loc.getWorld().spawnParticle(particle, loc, amount, offsetX, offsetY, offsetZ, extra);
                    }
                }
            }.runTaskTimer(CorePlus.getInstance(), 0, particleMap.getInterval());
        } catch (Exception ex) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format of command: \"particle-custom: " + group + "\"");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "&7More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), pluginName, ex);
        }
    }
}
