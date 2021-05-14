package tw.momocraft.coreplus.utils.customcommand;

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
import tw.momocraft.coreplus.utils.effect.ParticleMap;
import tw.momocraft.coreplus.utils.effect.ParticleUtils;
import tw.momocraft.coreplus.utils.effect.SoundMap;
import tw.momocraft.coreplus.utils.effect.SoundUtils;
import tw.momocraft.coreplus.utils.message.TitleMsgMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager implements CommandInterface {

    // Table<PlayerName, Pair<sendTime, expiration>, Command>
    private final Table<String, Pair<Long, Integer>, String> onlineCmdTable = HashBasedTable.create();

    public Table<String, Pair<Long, Integer>, String> getOnlineCmdTable() {
        return onlineCmdTable;
    }

    @Override
    public void addOnlineCommand(String pluginName, String playerName, int expiration, String command) {
        Pair<Long, Integer> waitingPair = new Pair<>(System.currentTimeMillis(), expiration * 1000);
        onlineCmdTable.put(playerName, waitingPair, command);
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    onlineCmdTable.remove(playerName, waitingPair);
                    UtilsHandler.getMsg().sendConsoleMsg(ConfigHandler.getPrefix(),
                            "Online command - Player: " + playerName + ", Expiration: " + expiration + ", Command: " + command);
                } catch (Exception ignored) {
                }
            }
        }.runTaskLater(CorePlus.getInstance(), expiration * 20);
    }

    @Override
    public void sendGroupCmd(String pluginName, Player sender, Object target, String input, String... langHolder) {
        sendGroupCmd(pluginName, sender, target, input, true, langHolder);
    }

    // custom: <group>, <arg1>, <arg2>, <arg...>
    @Override
    public void sendGroupCmd(String pluginName, Player sender, Object target, String input, boolean placeholder, String... langHolder) {
        if (input == null)
            return;
        String[] split = input.split(", ");
        String groupName = split[0];
        List<String> commands = ConfigHandler.getConfigPath().getCmdProp().get(groupName);
        List<String> newCommands = new ArrayList<>();
        if (commands == null || commands.isEmpty()) {
            UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPlugin(),
                    "An error occurred while executing command: \"custom: " + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPlugin(),
                    "Can not find the group of \"" + groupName + "\" in CorePlus/commands.yml.");
            return;
        }
        for (String command : commands) {
            for (int i = 1; i < +split.length; i++) {
                // Replace the args with ItemJoin item name.
                // Format: "custom: <group>, ij: <node>"
                if (UtilsHandler.getDepend().ItemJoinEnabled()) {
                    if (target instanceof Player) {
                        if (split[i].startsWith("ij: ")) {
                            try {
                                split[i] = UtilsHandler.getDepend().getItemJoinApi().
                                        getItemStack((Player) target, split[i].substring(4)).getItemMeta().getDisplayName();
                            } catch (Exception ignored) {
                                split[i] = "";
                            }
                        }
                    }
                }
                command = command.replace("%cmd_arg" + i + "%", split[i]);
            }
            newCommands.add(command);
        }
        newCommands = UtilsHandler.getMsg().transLang(sender, newCommands, langHolder);
        if (placeholder)
            newCommands = UtilsHandler.getMsg().transHolder((Player) sender, target, newCommands);
        executeCmd(pluginName, (Player) sender, newCommands);
    }


    @Override
    public void sendCmd(String pluginName, Player sender, Object target, String input, String... langHolder) {
        sendCmd(pluginName, sender, target, input, true, langHolder);
    }

    @Override
    public void sendCmd(String pluginName, Player sender, Object target, String input, boolean placeholder, String... langHolder) {
        if (input == null || input.isEmpty())
            return;
        input = UtilsHandler.getMsg().transLang(sender, input, langHolder);
        if (placeholder)
            input = UtilsHandler.getMsg().transHolder(sender, target, input);
        executeCmd(pluginName, sender, input);
    }

    @Override
    public void sendCmd(String pluginName, Player sender, Object target, List<String> input, String... langHolder) {
        sendCmd(pluginName, sender, target, input, true, langHolder);
    }

    @Override
    public void sendCmd(String pluginName, Player sender, Object target, List<String> input, boolean placeholder, String... langHolder) {
        if (input == null || input.isEmpty())
            return;
        input = UtilsHandler.getMsg().transLang(sender, input, langHolder);
        if (placeholder)
            input = UtilsHandler.getMsg().transHolder(sender, target, input);
        executeCmd(pluginName, sender, input);
    }

    @Override
    public void sendCmd(String pluginName, Player sender, Object target, Object trigger, List<String> input, String... langHolder) {
        sendCmd(pluginName, sender, target, trigger, input, true, langHolder);
    }

    @Override
    public void sendCmd(String pluginName, Player sender, Object target, Object trigger, List<String> input, boolean placeholder, String... langHolder) {
        if (input == null || input.isEmpty())
            return;
        input = UtilsHandler.getMsg().transLang(sender, input, langHolder);
        if (placeholder)
            input = UtilsHandler.getMsg().transHolder(sender, target, trigger, input);
        executeCmd(pluginName, sender, input);
    }

    @Override
    public void sendCmd(String pluginName, Player sender, List<Object> target, List<String> input, String... langHolder) {
        sendCmd(pluginName, sender, target, input, true, langHolder);
    }

    @Override
    public void sendCmd(String pluginName, Player sender, List<Object> target, List<String> input, boolean placeholder, String... langHolder) {
        if (input == null || input.isEmpty())
            return;
        input = UtilsHandler.getMsg().transLang(sender, input, langHolder);
        if (placeholder)
            input = UtilsHandler.getMsg().transHolder(sender, target, input);
        executeCmd(pluginName, sender, input);
    }


    @Override
    public void sendCmd(String pluginName, Player sender, List<Object> target, List<Object> triggers, List<String> input, String... langHolder) {
        sendCmd(pluginName, sender, target, triggers, input, true, langHolder);
    }

    @Override
    public void sendCmd(String pluginName, Player sender, List<Object> target, List<Object> triggers, List<String> input, boolean placeholder, String... langHolder) {
        if (input == null || input.isEmpty())
            return;
        input = UtilsHandler.getMsg().transLang(sender, input, langHolder);
        if (placeholder)
            input = UtilsHandler.getMsg().transHolder(sender, target, triggers, input);
        executeCmd(pluginName, sender, input);
    }

    @Override
    public void executeCmd(String pluginName, List<Player> players, List<String> input) {
        if (input == null || input.isEmpty())
            return;
        for (String cmd : input)
            for (Player player : players)
                UtilsHandler.getCommandManager().executeCmd(pluginName, player, cmd);
    }

    @Override
    public void executeCmd(String pluginName, Player player, List<String> input) {
        if (input == null)
            return;
        if (player == null || player instanceof ConsoleCommandSender) {
            executeCmd(pluginName, input);
            return;
        }
        String cmd;
        for (int i = 0; i < input.size(); i++) {
            cmd = input.get(i);
            if (!cmd.startsWith("delay: ")) {
                executeCmd(pluginName, player, cmd);
                continue;
            }
            // Executing delay command.
            String delay;
            try {
                delay = cmd.split(": ")[1];
                delay = delay.substring(0, delay.lastIndexOf("{n}"));
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of command: \"delay: " + cmd + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
                UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), pluginName, ex);
                continue;
            }
            List<String> newCommandList = new ArrayList<>(input);
            newCommandList.subList(i + 1, newCommandList.size());
            if (cmd.contains("{n}")) {
                cmd = cmd.substring(cmd.indexOf("{n}") + 2);
                newCommandList.add(0, cmd);
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    // To restart the method again after delay.
                    executeCmd(pluginName, player, newCommandList);
                }
            }.runTaskLater(CorePlus.getInstance(), Integer.parseInt(delay));
            return;
        }
    }

    @Override
    public void executeCmd(String pluginName, List<String> input) {
        if (input == null)
            return;
        String cmd;
        for (int i = 0; i < input.size(); i++) {
            cmd = input.get(i);
            if (!cmd.startsWith("delay: ")) {
                executeCmd(pluginName, cmd);
                continue;
            }
            String delay;
            try {
                delay = cmd.split(": ")[1];
                delay = delay.substring(0, delay.lastIndexOf("{n}"));
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of command: \"delay: " + cmd + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
                UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), pluginName, ex);
                continue;
            }
            List<String> newCommandList = new ArrayList<>(input);
            newCommandList.subList(i + 1, newCommandList.size());
            if (cmd.contains("{n}")) {
                cmd = cmd.substring(cmd.indexOf("{n}") + 2);
                newCommandList.add(0, cmd);
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    // To restart the method again after delay.
                    executeCmd(pluginName, newCommandList);
                }
            }.runTaskLater(CorePlus.getInstance(), Integer.parseInt(delay));
            return;
        }
    }

    @Override
    public void executeCmd(String pluginName, List<Player> players, String input) {
        if (input == null)
            return;
        for (Player player : players) {
            UtilsHandler.getCommandManager().executeCmd(pluginName, player, input);
        }
    }

    @Override
    public void executeCmd(String pluginName, Player player, String input) {
        if (input == null)
            return;
        if (player == null || player instanceof ConsoleCommandSender) {
            executeCmd(pluginName, input);
            return;
        }
        if (input.contains("{n}")) {
            executeCmd(pluginName, player, Arrays.asList(input.split("\\{n}")));
            return;
        }
        selectCmdType(pluginName, player, input);
    }

    @Override
    public void executeCmd(String pluginName, String input) {
        if (input == null)
            return;
        if (input.contains("{n}")) {
            executeCmd(pluginName, Arrays.asList(input.split("\\{n}")));
            return;
        }
        selectCmdType(pluginName, input);
    }

    private void selectCmdType(String pluginName, Player player, String input) {
        String[] split;
        try {

            switch (input.split(": ")[0]) {
                // Message
                case "print":
                    input = input.substring(input.indexOf(": ") + 1);
                    UtilsHandler.getMsg().sendConsoleMsg("", input);
                    return;
                case "log":
                    input = input.substring(input.indexOf(": ") + 1);
                    dispatchLog(pluginName, input);
                    return;
                case "log-group":
                    input = input.substring(input.indexOf(": ") + 1);
                    dispatchLogGroup(pluginName, input);
                    return;
                case "broadcast":
                    input = input.substring(input.indexOf(": ") + 1);
                    UtilsHandler.getMsg().sendBroadcastMsg("", input);
                    return;
                case "discord-chat":
                    input = input.substring(input.indexOf(": ") + 1);
                    split = input.split(", ");
                    UtilsHandler.getMsg().sendDiscordMsg("", split[0], split[1], player);
                    return;
                case "discord":
                    input = input.substring(input.indexOf(": ") + 1);
                    split = input.split(", ");
                    UtilsHandler.getMsg().sendDiscordMsg("", split[0], split[1]);
                    return;
                case "bungee":
                    input = input.substring(input.indexOf(": ") + 1);
                    dispatchBungeeCmd(pluginName, player, input);
                    return;
                case "switch":
                    input = input.substring(input.indexOf(": ") + 1);
                    dispatchSwitch(pluginName, input);
                    return;
                case "console":
                    input = input.substring(input.indexOf(": ") + 1);
                    dispatchConsoleCmd(pluginName, input);
                    return;
                case "op":
                    input = input.substring(input.indexOf(": ") + 1);
                    dispatchOpCmd(pluginName, player, input);
                    return;
                case "player":
                    input = input.substring(input.indexOf(": ") + 1);
                    dispatchPlayerCmd(pluginName, player, input);
                    return;
                case "chat-op":
                    input = input.substring(input.indexOf(": ") + 1);
                    sendChatOpMsg("", player, input);
                    return;
                case "chat":
                    input = input.substring(input.indexOf(": ") + 1);
                    UtilsHandler.getMsg().sendChatMsg("", player, input);
                    return;
                case "message":
                    input = input.replace("message: ", "");
                    UtilsHandler.getMsg().sendPlayerMsg("", player, input);
                    return;
                case "actionbar":
                    input = input.replace("actionbar: ", "");
                    UtilsHandler.getMsg().sendActionBarMsg(player, input);
                    return;
                case "actionbar-group":
                    input = input.replace("actionbar-group: ", "");
                    UtilsHandler.getMsg().sendActionBarMsg(player, input);
                    return;
                case "title":
                    input = input.replace("title: ", "");
                    dispatchTitleMsg(ConfigHandler.getPlugin(), player, input);
                    return;
                case "title-group":
                    input = input.replace("title-group: ", "");
                    dispatchTitleMsgGroup(ConfigHandler.getPlugin(), player, input);
                    return;
                case "sound":
                    input = input.replace("sound: ", "");
                    dispatchSound(pluginName, player, input);
                    return;
                case "sound-group":
                    input = input.replace("sound-group: ", "");
                    dispatchSoundGroup(pluginName, player, input);
                    return;
                case "particle":
                    input = input.replace("particle: ", "");
                    dispatchParticle(pluginName, player.getLocation(), input);
                    return;
                case "particle-group":
                    input = input.replace("particle-group: ", "");
                    dispatchParticleGroup(pluginName, player.getLocation(), input);
                    return;
                case "custom":
                    input = input.substring(input.indexOf(": ") + 1);
                    sendGroupCmd(pluginName, player, player, input);
                    return;
                case "condition":
                    input = input.substring(input.indexOf(": ") + 1);
                    dispatchConditionCmd(pluginName, player, input);
                    return;
                default:
                    UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPlugin(),
                            "Unknown command type: \"" + input + "\"");
                    UtilsHandler.getMsg().sendErrorMsg(pluginName,
                            "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            }
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPlugin(),
                    "Unknown command type: \"" + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), pluginName, ex);
        }
    }

    private void selectCmdType(String pluginName, String input) {
        String[] split;
        try {
            switch (input.split(": ")[0]) {
                case "custom":
                    input = input.substring(input.indexOf(": ") + 1);
                    sendGroupCmd(pluginName, null, null, input);
                    return;
                case "condition":
                    input = input.substring(input.indexOf(": ") + 1);
                    dispatchConditionCmd(pluginName, null, input);
                    return;
                case "print":
                    input = input.substring(input.indexOf(": ") + 1);
                    UtilsHandler.getMsg().sendConsoleMsg("", input);
                    return;
                case "log":
                    input = input.substring(input.indexOf(": ") + 1);
                    dispatchLog(pluginName, input);
                    return;
                case "log-group":
                    input = input.substring(input.indexOf(": ") + 1);
                    dispatchLogGroup(pluginName, input);
                    return;
                case "broadcast":
                    input = input.substring(input.indexOf(": ") + 1);
                    UtilsHandler.getMsg().sendBroadcastMsg("", input);
                    return;
                case "discord":
                    input = input.substring(input.indexOf(": ") + 1);
                    split = input.split(", ");
                    UtilsHandler.getMsg().sendDiscordMsg("", split[0], split[1]);
                    return;
                case "bungee":
                    input = input.substring(input.indexOf(": ") + 1);
                    dispatchBungeeCmd(pluginName, null, input);
                    return;
                case "console":
                    input = input.substring(input.indexOf(": ") + 1);
                    dispatchConsoleCmd(pluginName,  input);
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
                    UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPlugin(), "Unknown command type: \"" + input + "\"");
                    UtilsHandler.getMsg().sendErrorMsg(pluginName, "Please check if CorePlus is updated to the latest version.");
                    UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            }
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPlugin(),
                    "Unknown command type: \"" + input + "\"");
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
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
        }
    }

    @Override
    public void dispatchLogGroup(String pluginName, String input) {
        if (input == null)
            return;
        String group = input.split(", ")[0];
        input = input.substring(input.indexOf(",") + 2);
        try {
            UtilsHandler.getFile().getLog().add(pluginName, group, input);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while executing command: \"log-group: " + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
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
            executeCmd(pluginName, player, trueCmd);
        else
            executeCmd(pluginName, player, falseCmd);
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
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
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
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
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
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
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
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
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
            // title: <title>\n<subtitle> -i:FadeIn, -o:FadeOut -s:Stay
            String[] args = input.split("\\s+");
            for (String arg : args) {
                arg = arg.toUpperCase();
                if (arg.startsWith("-I:")) {
                    titleMsgMap.setFadeIn(Integer.parseInt(arg.replace("-I:", "")));
                    input = input.replace("-I:" + arg, "");
                    continue;
                }
                if (arg.startsWith("-O:")) {
                    titleMsgMap.setFadeOut(Integer.parseInt(arg.replace("-O:", "")));
                    input = input.replace("-O:" + arg, "");
                    continue;
                }
                if (arg.startsWith("-S:")) {
                    titleMsgMap.setStay(Integer.parseInt(arg.replace("-S:", "")));
                    input = input.replace("-S:" + arg, "");
                }
            }
            UtilsHandler.getMsg().sendTitleMsg(player, input, titleMsgMap);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of command: \"title: " + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
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
                arg = arg.toUpperCase();
                if (arg.startsWith("-S:")) {
                    soundMap.setType(Sound.valueOf(arg.replace("-S:", "")));
                    continue;
                }
                if (arg.startsWith("-P:")) {
                    soundMap.setPitch(Integer.parseInt(arg.replace("-P:", "")));
                    continue;
                }
                if (arg.startsWith("-V:")) {
                    soundMap.setVolume(Integer.parseInt(arg.replace("-V:", "")));
                    continue;
                }
                if (arg.startsWith("-I:")) {
                    soundMap.setInterval(Integer.parseInt(arg.replace("-I:", "")));
                }
            }
            if (soundMap.getType() == null) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of command: \"sound: " + input + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
                return;
            }
            SoundUtils.sendSound(player, player.getLocation(), soundMap);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of command: \"sound: " + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
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
                arg = arg.toUpperCase();
                if (arg.startsWith("-A:")) {
                    particleMap.setAmount(Integer.parseInt(arg.replace("-A:", "")));
                    value = value.replace("-A:" + arg, "");
                    continue;
                }
                if (arg.startsWith("-T:")) {
                    particleMap.setTimes(Integer.parseInt(arg.replace("-T:", "")));
                    value = value.replace("-T:" + arg, "");
                    continue;
                }
                if (arg.startsWith("-X:")) {
                    particleMap.setOffsetX(Integer.parseInt(arg.replace("-X:", "")));
                    value = value.replace("-X:" + arg, "");
                    continue;
                }
                if (arg.startsWith("-Y:")) {
                    particleMap.setOffsetY(Integer.parseInt(arg.replace("-Y:", "")));
                    value = value.replace("-Y:" + arg, "");
                    continue;
                }
                if (arg.startsWith("-Z:")) {
                    particleMap.setOffsetZ(Integer.parseInt(arg.replace("-Z:", "")));
                    value = value.replace("-Z:" + arg, "");
                    continue;
                }
                if (arg.startsWith("-E:")) {
                    particleMap.setExtra(Integer.parseInt(arg.replace("-E:", "")));
                    value = value.replace("-E:" + arg, "");
                    continue;
                }
                if (arg.startsWith("-M:")) {
                    particleMap.setMaterial(Material.getMaterial(arg.replace("-M:", "").toUpperCase()));
                    value = value.replace("-M:" + arg, "");
                    continue;
                }
                if (arg.startsWith("-C:")) {
                    particleMap.setColorType(arg.replace("-C:", "").toUpperCase());
                    value = value.replace("-C:" + arg, "");
                    continue;
                }
                if (arg.startsWith("-RGB:")) {
                    String[] rgb = arg.replace("-RGB:", "").split(",");
                    particleMap.setColorR(Integer.parseInt(rgb[0]));
                    particleMap.setColorR(Integer.parseInt(rgb[1]));
                    particleMap.setColorR(Integer.parseInt(rgb[2]));
                    value = value.replace("-RGB:" + arg, "");
                }
            }
            particleMap.setType(Particle.valueOf(value));
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
