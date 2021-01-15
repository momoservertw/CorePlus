package tw.momocraft.coreplus.utils.customcommands;

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
        if (player == null || player instanceof ConsoleCommandSender) {
            executeCmdList(prefix, input, placeholder);
            return;
        }
        if (prefix == null)
            prefix = "";
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
        if (player == null || player instanceof ConsoleCommandSender) {
            executeCmd(prefix, input, placeholder);
            return;
        }
        if (prefix == null)
            prefix = "";
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
    public void dispatchCustomCmd(String prefix, Player player, String group, boolean placeholder, String... langHolder) {
        String[] placeHolderArr = group.split(", ");
        String newCmd = ConfigHandler.getConfigPath().getCmdProp().get(placeHolderArr[0]);
        if (newCmd == null) {
            UtilsHandler.getLang().sendErrorMsg(prefix, "Can not find the custom command group: " + placeHolderArr[0]);
            return;
        }
        for (int i = 1; i < +placeHolderArr.length; i++) {
            newCmd = newCmd.replace("%cmd_arg" + i + "%", placeHolderArr[i]);
        }
        selectCmdType(prefix, player, newCmd, placeholder, langHolder);
    }

    /**
     * Selecting the type of command for a player.
     *
     * @param prefix      the plugin's prefix.
     * @param player      the target player.
     * @param input       the input command.
     * @param placeholder translating placeholders.
     */
    private void selectCmdType(String prefix, Player player, String input, boolean placeholder, String... langHolder) {
        String cmdType = input.split(": ")[0];
        input = UtilsHandler.getLang().translateLangHolders(player, input, langHolder);
        if (placeholder) {
            input = UtilsHandler.getLang().translateLayout(input, player);
        }
        switch (cmdType) {
            case "custom":
                input = input.replace("custom: ", "");
                dispatchCustomCmd(prefix, player, input, placeholder);
                return;
            case "print":
                input = input.replace("print: ", "");
                UtilsHandler.getLang().sendConsoleMsg(prefix, input);
                return;
            case "log":
                input = input.replace("log: ", "");
                dispatchLogCmd(prefix, input);
                return;
            case "log-custom":
                input = input.replace("log-custom: ", "");
                dispatchLogCustomCmd(prefix, input);
                return;
            case "broadcast":
                input = input.replace("broadcast: ", "");
                UtilsHandler.getLang().sendBroadcastMsg("", input);
                return;
            case "bungee":
                input = input.replace("bungee: ", "");
                dispatchBungeeCordCmd(prefix, player, input);
                return;
            case "console":
                input = input.replace("console: ", "");
                dispatchConsoleCmd(prefix, player, input);
                return;
            case "op":
                input = input.replace("op: ", "");
                dispatchOpCmd(prefix, player, input);
                return;
            case "player":
                input = input.replace("player: ", "");
                dispatchPlayerCmd(prefix, player, input);
                return;
            case "chat":
                input = input.replace("chat: ", "");
                UtilsHandler.getLang().sendChatMsg("", player, input);
                return;
            case "message":
                input = input.replace("message: ", "");
                UtilsHandler.getLang().sendPlayerMsg("", player, input);
                return;
                    /*
                case "title":
                    input = input.replace("title: ", "");
                    UtilsHandler.getLang().sendTitleMsg(null, player, input);
                    return;
                case "action-bar":
                    input = input.replace("title: ", "");
                    UtilsHandler.getLang().sendTitleMsg(null, player, input);
                    return;
                     */
            case "sound":
                input = input.replace("sound: ", "");
                dispatchSoundCmd(prefix, player, input);
                return;
            case "sound-custom":
                input = input.replace("sound-custom: ", "");
                dispatchSoundCustomCmd(prefix, player, input);
                return;
            case "particle":
                input = input.replace("particle: ", "");
                dispatchParticleCmd(prefix, player.getLocation(), input);
                return;
            case "particle-custom":
                input = input.replace("particle-custom: ", "");
                dispatchParticleCustomCmd(prefix, player.getLocation(), input);
                return;
            default:
                UtilsHandler.getLang().sendErrorMsg(prefix, "Can not execute command (" + input + ")");
                UtilsHandler.getLang().sendErrorMsg(prefix, "Please check whether CorePlus is the latest version.");
        }
    }

    /**
     * Selecting the type of command.
     *
     * @param prefix      the plugin's prefix.
     * @param input       the input command.
     * @param placeholder translating placeholders.
     */
    private void selectCmdType(String prefix, String input, boolean placeholder, String... langHolder) {
        input = UtilsHandler.getLang().translateLangHolders(null, input, langHolder);
        if (placeholder) {
            input = UtilsHandler.getLang().translateLayout(input, null);
        }
        switch (input.split(": ")[0]) {
            case "custom":
                input = input.replace("custom: ", "");
                dispatchCustomCmd(prefix, null, input, placeholder);
                return;
            case "print":
                input = input.replace("print: ", "");
                UtilsHandler.getLang().sendConsoleMsg(prefix, input);
                return;
            case "log":
                input = input.replace("log: ", "");
                dispatchLogCmd(prefix, input);
                return;
            case "log-custom":
                input = input.replace("log-custom: ", "");
                dispatchLogCustomCmd(prefix, input);
                return;
            case "broadcast":
                input = input.replace("broadcast: ", "");
                UtilsHandler.getLang().sendBroadcastMsg("", input);
                return;
            case "bungee":
                input = input.replace("bungee: ", "");
                dispatchBungeeCordCmd(prefix, null, input);
                return;
            case "console":
                input = input.replace("console: ", "");
                dispatchConsoleCmd(prefix, null, input);
                return;
            case "op":
            case "player":
            case "chat":
            case "message":
            case "sound":
            case "sound-custom":
            case "particle":
            case "particle-custom":
                UtilsHandler.getLang().sendErrorMsg(prefix, "Can not find the execute target (" + input + ")");
                return;
            default:
                UtilsHandler.getLang().sendErrorMsg(prefix, "Can not execute command (" + input + ")");
                UtilsHandler.getLang().sendErrorMsg(prefix, "Please check whether CorePlus is the latest version.");
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
    public void dispatchOpCmd(String prefix, Player player, String input) {
        boolean isOp = player.isOp();
        try {
            player.setOp(true);
            player.chat("/" + input);
        } catch (Exception e) {
            player.setOp(isOp);
            UtilsHandler.getLang().sendErrorMsg(prefix, "An error occurred when executing command (op: " + input + ")");
            UtilsHandler.getLang().sendErrorMsg(prefix, "&7If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), prefix, e);
            removeOp(prefix, player);
        } finally {
            player.setOp(isOp);
        }
    }


    private void removeOp(String prefix, Player player) {
        String playerName = player.getName();
        UtilsHandler.getLang().sendErrorMsg(prefix, "Trying to remove \"&e" + playerName + "&c\" Op status...");
        Bukkit.getScheduler().scheduleSyncDelayedTask(CorePlus.getInstance(), () -> {
            try {
                player.setOp(false);
                UtilsHandler.getLang().sendErrorMsg(prefix, "&fSucceed to remove \"" + playerName + "\" Op status!");
            } catch (Exception e) {
                UtilsHandler.getLang().sendErrorMsg(prefix, "&eCan not remove \"&e" + playerName + "&c\" Op status.");
            }
        }, 20);
    }

    @Override
    public void dispatchPlayerCmd(String prefix, Player player, String input) {
        try {
            player.chat("/" + input);
        } catch (Exception e) {
            UtilsHandler.getLang().sendErrorMsg(prefix, "An error occurred when executing command (player: " + input + ")");
            UtilsHandler.getLang().sendErrorMsg(prefix, "&7If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), prefix, e);
        }
    }

    @Override
    public void dispatchBungeeCordCmd(String prefix, Player player, String input) {
        try {
            BungeeCordUtils.ExecuteCommand(player, input);
        } catch (Exception e) {
            UtilsHandler.getLang().sendErrorMsg(prefix, "An error occurred when executing command (bungee: " + input + ")");
            UtilsHandler.getLang().sendErrorMsg(prefix, "&7If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), prefix, e);
        }
    }

    @Override
    public void dispatchSoundCmd(String prefix, Player player, String input) {
        try {
            String[] arr = input.split(", ");
            Location loc = player.getLocation();
            player.playSound(loc, Sound.valueOf(arr[0]), Long.parseLong(arr[1]), Long.parseLong(arr[2]));
        } catch (Exception e) {
            UtilsHandler.getLang().sendErrorMsg(prefix, "Can not execute command (sound: " + input + ")");
            UtilsHandler.getLang().sendErrorMsg(prefix, "Correct format: \"sound: Sound, Volume, Pitch\"");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), prefix, e);
        }
    }

    @Override
    public void dispatchSoundCustomCmd(String prefix, Player player, String input) {
        try {
            Location loc = player.getLocation();
            SoundMap soundMap = ConfigHandler.getConfigPath().getSoundProp().get(input);
            Sound sound = Sound.valueOf(soundMap.getType());
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
            UtilsHandler.getLang().sendErrorMsg(prefix, "Can not execute command (sound-custom: " + input + ")");
            UtilsHandler.getLang().sendErrorMsg(prefix, "Please check the configuration settings.");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), prefix, e);
        }
    }

    @Override
    public void dispatchParticleCmd(String prefix, Location loc, String input) {
        try {
            String[] arr = input.split(", ");
            loc.getWorld().spawnParticle(Particle.valueOf(arr[0]), loc, Integer.parseInt(arr[1]),
                    Double.parseDouble(arr[2]), Double.parseDouble(arr[3]), Double.parseDouble(arr[4]), Double.parseDouble(arr[5]));
        } catch (Exception e) {
            UtilsHandler.getLang().sendErrorMsg(prefix, "Can not execute command (particle: " + input + ")");
            UtilsHandler.getLang().sendErrorMsg(prefix, "Correct format: particle: Particle, Amount, OffsetX, OffsetY, OffsetZ, Speed");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), prefix, e);
        }
    }

    @Override
    public void dispatchParticleCustomCmd(String prefix, Location loc, String input) {
        try {
            World world = loc.getWorld();
            if (world == null) {
                UtilsHandler.getLang().sendErrorMsg(prefix, "Can not find world to execute command (particle-custom: " + input + ")");
                UtilsHandler.getLang().sendErrorMsg(prefix, "&7If this error keeps happening, please contact the plugin author.");
                return;
            }
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
                        world.spawnParticle(particle, loc, amount, offsetX, offsetY, offsetZ, extra);
                    }
                }
            }.runTaskTimer(CorePlus.getInstance(), 0, particleMap.getInterval());
        } catch (Exception e) {
            UtilsHandler.getLang().sendErrorMsg(prefix, "Can not execute command (particle-custom: " + input + ")");
            UtilsHandler.getLang().sendErrorMsg(prefix, "Please check the configuration settings.");
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), prefix, e);
        }
    }
}
