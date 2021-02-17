package tw.momocraft.coreplus.utils.customcommands;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import javafx.util.Pair;
import org.bukkit.*;;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.api.CommandInterface;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.*;

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
                            "Online command start - Player: " + playerName + ", Expiration: " + expiration + ", Command: " + command);
                } catch (Exception ignored) {
                }
            }
        }.runTaskLater(CorePlus.getInstance(), expiration * 20);
    }

    @Override
    public void executeCmdList(String prefix, List<Player> players, List<String> input, boolean placeholder, String... langHolder) {
        if (!input.isEmpty()) {
            for (String cmd : input) {
                if (cmd.startsWith("targets-")) {
                    cmd = cmd.replace("targets-", "");
                    for (Player player : players) {
                        CorePlusAPI.getCommandManager().executeCmd(prefix, player, cmd, placeholder, langHolder);
                    }
                    continue;
                }
                CorePlusAPI.getCommandManager().executeCmd(prefix, cmd, placeholder, langHolder);
            }
        }
    }

    @Override
    public void executeCmdList(String prefix, Player player, List<String> input, boolean placeholder, String... langHolder) {
        if (prefix == null)
            prefix = "";
        if (player == null || player instanceof ConsoleCommandSender) {
            executeCmdList(prefix, input, placeholder);
            return;
        }
        String cmd;
        for (int i = 0; i < input.size(); i++) {
            cmd = input.get(i);
            if (!cmd.startsWith("delay: ")) {
                executeCmd(prefix, player, cmd, placeholder, langHolder);
                continue;
            }
            // Executing delay command.
            String delay;
            try {
                delay = cmd.split(": ")[1];
                delay = delay.substring(0, delay.lastIndexOf(";"));
            } catch (Exception e) {
                UtilsHandler.getLang().sendErrorMsg(prefix, "Can not find the execute command (delay: " + cmd + ")");
                UtilsHandler.getLang().sendErrorMsg(prefix, "Correct format: \"delay: Number\"");
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), prefix, e);
                continue;
            }
            List<String> newCommandList = new ArrayList<>(input);
            newCommandList.subList(i + 1, newCommandList.size());
            if (cmd.contains(";;")) {
                cmd = cmd.substring(cmd.indexOf(";;") + 1);
                newCommandList.add(0, cmd);
            }
            String finalPrefix = prefix;
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
    public void executeCmdList(String prefix, List<String> input, boolean placeholder, String... langHolder) {
        if (prefix == null)
            prefix = "";
        String cmd;
        for (int i = 0; i < input.size(); i++) {
            cmd = input.get(i);
            if (!cmd.startsWith("delay: ")) {
                executeCmd(prefix, cmd, placeholder, langHolder);
                continue;
            }
            String delay;
            try {
                delay = cmd.split(": ")[1];
                delay = delay.substring(0, delay.lastIndexOf(";;"));
            } catch (Exception e) {
                UtilsHandler.getLang().sendErrorMsg(prefix, "Can not find the execute command (delay: " + cmd + ")");
                UtilsHandler.getLang().sendErrorMsg(prefix, "Correct format: \"delay: Number\"");
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), prefix, e);
                continue;
            }
            List<String> newCommandList = new ArrayList<>(input);
            newCommandList.subList(i + 1, newCommandList.size());
            if (cmd.contains(";;")) {
                cmd = cmd.substring(cmd.indexOf(";;") + 1);
                newCommandList.add(0, cmd);
            }
            String finalPrefix = prefix;
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
    public void executeCmd(String prefix, List<Player> players, String input, boolean placeholder, String... langHolder) {
        if (prefix == null)
            prefix = "";
        if (input.startsWith("targets-")) {
            input = input.replace("targets-", "");
            for (Player player : players) {
                CorePlusAPI.getCommandManager().executeCmd(prefix, player, input, placeholder, langHolder);
            }
            return;
        }
        CorePlusAPI.getCommandManager().executeCmd(prefix, input, placeholder, langHolder);
    }

    @Override
    public void executeCmd(String prefix, Player player, String input, boolean placeholder, String... langHolder) {
        if (prefix == null)
            prefix = "";
        if (player == null || player instanceof ConsoleCommandSender) {
            executeCmd(prefix, input, placeholder);
            return;
        }
        if (input.contains(";;")) {
            executeCmdList(prefix, player, Arrays.asList(input.split(";;")), true, langHolder);
            return;
        }
        selectCmdType(prefix, player, input, placeholder, langHolder);
    }

    @Override
    public void executeCmd(String prefix, String input, boolean placeholder, String... langHolder) {
        if (prefix == null)
            prefix = "";
        if (input.contains(";;")) {
            executeCmdList(prefix, Arrays.asList(input.split(";;")), placeholder, langHolder);
            return;
        }
        selectCmdType(prefix, input, placeholder, langHolder);
    }

    @Override
    public void dispatchCustomCmd(String pluginName, Player player, String group, boolean placeholder, String... langHolder) {
        String[] placeHolderArr = group.split(", ");
        List<String> commands = ConfigHandler.getConfigPath().getCmdProp().get(placeHolderArr[0]);
        List<String> newCommands = new ArrayList<>();
        if (commands == null || commands.isEmpty()) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Can not find the custom command group: " + placeHolderArr[0]);
            return;
        }
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
            input = UtilsHandler.getLang().translateLayout(input, player);
        }
        switch (input.split(": ")[0]) {
            case "custom":
                input = input.replace("custom: ", "");
                dispatchCustomCmd(pluginName, player, input, placeholder);
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
                UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginPrefix(), "Unknown command type, more information:");
                UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginPrefix(), "https://github.com/momoservertw/CorePlus/wiki/Custom-Commands");
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
            input = UtilsHandler.getLang().translateLayout(input, null);
        }
        switch (input.split(": ")[0]) {
            case "custom":
                input = input.replace("custom: ", "");
                dispatchCustomCmd(pluginName, null, input, placeholder);
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
                UtilsHandler.getLang().sendErrorMsg(pluginName, "Can not find the execute target (" + input + ")");
                return;
            default:
                dispatchConsoleCmd(pluginName, null, input);
        }
    }

    @Override
    public void dispatchLogCmd(String prefix, String input) {
        LogMap logMap = ConfigHandler.getConfigPath().getLogProp().get("Default");
        if (logMap == null) {
            UtilsHandler.getLang().sendErrorMsg(prefix, "Can not execute command (log: " + input + ")");
            UtilsHandler.getLang().sendErrorMsg(prefix, "Can not find the Log group of \"Default\" in CorePlus/config.yml.");
            return;
        }
        try {
            UtilsHandler.getLang().addLog(logMap.getFile(), input, logMap.isTime(), logMap.isNewFile(), logMap.isZip());
        } catch (Exception e) {
            UtilsHandler.getLang().sendErrorMsg(prefix, "An error occurred when executing command (log: " + input + ")");
            UtilsHandler.getLang().sendErrorMsg(prefix, "&7If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), prefix, e);
        }
    }

    @Override
    public void dispatchLogCustomCmd(String prefix, String input) {
        String group = input.split(", ")[0];
        LogMap logMap = ConfigHandler.getConfigPath().getLogProp().get(group);
        if (logMap == null) {
            UtilsHandler.getLang().sendErrorMsg(prefix, "Can not execute command (log-custom: " + input + ")");
            UtilsHandler.getLang().sendErrorMsg(prefix, "Can not find the Log group of \"" + group + "\" in CorePlus/config.yml.");
            return;
        }
        input = input.substring(input.indexOf(",") + 2);
        try {
            UtilsHandler.getLang().addLog(logMap.getFile(), input, logMap.isTime(), logMap.isNewFile(), logMap.isZip());
        } catch (Exception e) {
            UtilsHandler.getLang().sendErrorMsg(prefix, "An error occurred when executing command (log-custom: " + group + ", " + input + ")");
            UtilsHandler.getLang().sendErrorMsg(prefix, "&7If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), prefix, e);
        }
    }

    @Override
    public void dispatchConsoleCmd(String prefix, Player player, String input) {
        try {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), input);
        } catch (Exception e) {
            UtilsHandler.getLang().sendErrorMsg(prefix, "An error occurred when executing command (console: " + input + ")");
            UtilsHandler.getLang().sendErrorMsg(prefix, "&7If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), prefix, e);
        }
    }

    @Override
    public void sendChatOpMsg(String prefix, Player player, String message) {
        if (prefix == null)
            prefix = "";
        message = prefix + message;
        message = ChatColor.translateAlternateColorCodes('&', message);

        boolean isOp = player.isOp();
        try {
            player.setOp(true);
            player.chat(message);
        } catch (Exception e) {
            player.setOp(isOp);
            UtilsHandler.getLang().sendErrorMsg(prefix, "An error occurred when executing command (chat-op: " + message + ")");
            UtilsHandler.getLang().sendErrorMsg(prefix, "&7If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), prefix, e);
            removeOp(prefix, player);
        } finally {
            player.setOp(isOp);
        }
    }

    public void dispatchOpCmd(String pluginName, Player player, String input) {
        boolean isOp = player.isOp();
        try {
            player.setOp(true);
            player.chat("/" + input);
        } catch (Exception e) {
            player.setOp(isOp);
            UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred when executing command (op: " + input + ")");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "&7If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), pluginName, e);
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
            } catch (Exception e) {
                UtilsHandler.getLang().sendErrorMsg(pluginName, "&eCan not remove \"&e" + playerName + "&c\" Op status.");
            }
        }, 20);
    }

    @Override
    public void dispatchPlayerCmd(String pluginName, Player player, String input) {
        try {
            player.chat("/" + input);
        } catch (Exception e) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred when executing command (player: " + input + ")");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "&7If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), pluginName, e);
        }
    }

    @Override
    public void dispatchBungeeCordCmd(String pluginName, Player player, String input) {
        try {
            BungeeCordUtils.ExecuteCommand(player, input);
        } catch (Exception e) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred when executing command (bungee: " + input + ")");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "&7If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), pluginName, e);
        }
    }

    @Override
    public void dispatchSoundCmd(String pluginName, Player player, String input) {
        try {
            String[] arr = input.split(", ");
            Location loc = player.getLocation();
            player.playSound(loc, Sound.valueOf(arr[0]), Long.parseLong(arr[1]), Long.parseLong(arr[2]));
        } catch (Exception e) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Can not execute command (sound: " + input + ")");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Correct format: \"sound: Sound, Volume, Pitch\"");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), pluginName, e);
        }
    }

    @Override
    public void dispatchSoundCustomCmd(String pluginName, Player player, String input) {
        try {
            Location loc = player.getLocation();
            SoundMap soundMap = ConfigHandler.getConfigPath().getSoundProp().get(input);
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
        } catch (Exception e) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Can not execute command (sound-custom: " + input + ")");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Please check the configuration settings.");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), pluginName, e);
        }
    }

    @Override
    public void dispatchParticleCmd(String pluginName, Location loc, String input) {
        try {
            String[] arr = input.split(", ");
            loc.getWorld().spawnParticle(Particle.valueOf(arr[0]), loc, Integer.parseInt(arr[1]),
                    Double.parseDouble(arr[2]), Double.parseDouble(arr[3]), Double.parseDouble(arr[4]), Double.parseDouble(arr[5]));
        } catch (Exception e) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Can not execute command (particle: " + input + ")");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Correct format: particle: Particle, Amount, OffsetX, OffsetY, OffsetZ, Speed");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), pluginName, e);
        }
    }

    @Override
    public void dispatchParticleCustomCmd(String pluginName, Location loc, String input) {
        try {
            ParticleMap particleMap = ConfigHandler.getConfigPath().getParticleProp().get(input);
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
        } catch (Exception e) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Can not execute command (particle-custom: " + input + ")");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Please check the configuration settings.");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), pluginName, e);
        }
    }
}
