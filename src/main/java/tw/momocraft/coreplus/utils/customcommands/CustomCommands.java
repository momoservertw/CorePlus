package tw.momocraft.coreplus.utils.customcommands;

import org.bukkit.*;;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.api.CommandInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CustomCommands implements CommandInterface {


    /**
     * Executing custom command list.
     *
     * @param prefix      the plugin's prefix.
     * @param player      the target player.
     * @param input       the input commands.
     * @param placeholder translating placeholders.
     */
    @Override
    public void executeCmdList(String prefix, final Player player, List<String> input, final boolean placeholder) {
        if (prefix == null)
            prefix = "";
        for (String value : input) {
            executeCmd(prefix, player, value, placeholder);
        }
    }

    /**
     * Executing custom command.
     *
     * @param prefix      the plugin's prefix.
     * @param player      the target player.
     * @param input       the input command.
     * @param placeholder translating placeholders.
     */
    @Override
    public void executeCmd(String prefix, Player player, String input, boolean placeholder) {
        if (prefix == null)
            prefix = "";
        List<String> cmds = Arrays.asList(input.split(";"));
        Iterator<String> it = cmds.iterator();
        String cmd;
        while (it.hasNext()) {
            cmd = it.next();
            // Executing new commands later.
            if (cmd.startsWith("delay: ")) {
                int delay;
                try {
                    delay = Integer.parseInt(cmd.split(": ")[1]);
                } catch (Exception ex) {
                    ConfigHandler.getLang().sendErrorMsg(prefix, "&cCan not find the execute command type (" + cmd + ")");
                    ConfigHandler.getLang().sendErrorMsg(prefix, "&cPlease check whether CorePlus is the latest version.");
                    continue;
                }
                cmds.remove(cmd);
                String finalPrefix = prefix;
                Bukkit.getScheduler().scheduleSyncDelayedTask(CorePlus.getInstance(), () -> executeCmdList(finalPrefix, player, cmds, placeholder), delay);
                return;
            }
            // Executing a command for online players.
            if (cmd.startsWith("all-")) {
                cmd = cmd.replace("all-", "");
                for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                    selectCmdType(prefix, onlinePlayer, cmd, placeholder);
                }
                continue;
            }
            // Executing a command for a player.
            selectCmdType(prefix, player, cmd, placeholder);
        }
    }

    /**
     * Selecting the type of command for a player.
     *
     * @param prefix      the plugin's prefix.
     * @param player      the target player.
     * @param input       the input command.
     * @param placeholder translating placeholders.
     */
    private void selectCmdType(String prefix, Player player, String input, boolean placeholder) {
        if (placeholder) {
            input = ConfigHandler.getUtils().translateLayout(input, player);
        }
        if (player == null || player instanceof ConsoleCommandSender) {
            selectCmdType(prefix, input, placeholder);
        } else {
            String cmdType = input.split(":")[0];
            switch (cmdType) {
                case "custom":
                    input = input.replace("custom: ", "");
                    dispatchCustomCmd(prefix, player, input, placeholder);
                    return;
                case "print":
                    input = input.replace("print: ", "");
                    ConfigHandler.getLang().sendConsoleMsg(null, input);
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
                    ConfigHandler.getLang().sendBroadcastMsg(null, input);
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
                    ConfigHandler.getLang().sendChatMsg(null, player, input);
                    return;
                case "message":
                    input = input.replace("message: ", "");
                    ConfigHandler.getLang().sendPlayerMsg(null, player, input);
                    return;
                case "sound":
                    input = input.replace("sound-custom: ", "");
                    dispatchSoundCmd(prefix, player, input);
                    return;
                case "sound-custom":
                    input = input.replace("sound: ", "");
                    dispatchSoundCustomCmd(prefix, player, input);
                    return;
                case "particle":
                    input = input.replace("particle-custom: ", "");
                    dispatchParticleCmd(prefix, player.getLocation(), input);
                    return;
                case "particle-custom":
                    input = input.replace("particle: ", "");
                    dispatchParticleCustomCmd(prefix, player.getLocation(), input);
                    return;
                default:
                    ConfigHandler.getLang().sendErrorMsg(prefix, "&cCan not find the execute command type (" + input + ")");
                    ConfigHandler.getLang().sendErrorMsg(prefix, "&cPlease check whether CorePlus is the latest version.");
            }
        }
    }

    /**
     * Selecting the type of command.
     *
     * @param prefix      the plugin's prefix.
     * @param input       the input command.
     * @param placeholder translating placeholders.
     */
    private void selectCmdType(String input, String prefix, boolean placeholder) {
        if (placeholder) {
            input = ConfigHandler.getUtils().translateLayout(input, null);
        }
        String cmdType = input.split(":")[0];
        switch (cmdType) {
            case "custom":
                input = input.replace("custom: ", "");
                dispatchCustomCmd(prefix, null, input, placeholder);
                return;
            case "print":
                input = input.replace("print: ", "");
                ConfigHandler.getLang().sendConsoleMsg(null, input);
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
                ConfigHandler.getLang().sendBroadcastMsg(null, input);
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
                ConfigHandler.getLang().sendErrorMsg(prefix, "&cCan not find the execute target (" + input + ")");
                return;
            default:
                ConfigHandler.getLang().sendErrorMsg(prefix, "&cCan not find the execute command type (" + input + ")");
                ConfigHandler.getLang().sendErrorMsg(prefix, "&cPlease check whether CorePlus is the latest version.");
        }
    }

    /**
     * To execute custom command.
     * <p>
     * custom: group, arg1
     * group: "console: say %cmd_arg1%"
     */
    @Override
    public void dispatchCustomCmd(String prefix, Player player, String input, boolean placeholder) {
        String[] placeHolderArr = input.split(", ");
        String newCmd = ConfigHandler.getConfigPath().getCmdProp().get(placeHolderArr[0]);
        if (newCmd == null) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cCan not find the custom command group: " + placeHolderArr[0]);
            return;
        }
        for (int i = 1; i < +placeHolderArr.length; i++) {
            newCmd = newCmd.replace("%cmd_arg" + i + "%", placeHolderArr[i]);
        }
        selectCmdType(prefix, player, newCmd, placeholder);
    }

    /**
     * Executing log message.
     *
     * @param input the message of log.
     */
    @Override
    public void dispatchLogCmd(String prefix, String input) {
        try {
            ConfigHandler.getLogger().addLog(CorePlus.getInstance().getDataFolder().getPath() + "//Logs",
                    "latest.log", input, true, false, false);
        } catch (Exception e) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "An error occurred when executing command (log: " + input + ")");
            ConfigHandler.getLang().sendErrorMsg(prefix, "&7If this error keeps happening, please contact the plugin author.");
        }
    }


    /**
     * Executing log message from CorePlus config.yml.
     * Format: "log-custom: GROUP"
     *
     * @param prefix the prefix of sending plugin.
     * @param input  the message of log.
     */
    @Override
    public void dispatchLogCustomCmd(String prefix, String input) {
        LogMap logMap = ConfigHandler.getConfigPath().getLogProp().get(input);
        if (logMap == null) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "Can not execute command (log-custom: " + input + ")");
            ConfigHandler.getLang().sendErrorMsg(prefix, "Can not find the group of \"Log\" in CorePlus/config.yml.");
            return;
        }
        String path = logMap.getPath();
        if (path.startsWith("plugins//")) {
            path = Bukkit.getServer().getWorldContainer().getPath() + "//" + path;
        } else if (path.startsWith("server//")) {
            path = path.replace("server//", "");
            path = Bukkit.getServer().getWorldContainer().getPath() + "//" + path;
        }
        try {
            ConfigHandler.getLogger().addLog(path, logMap.getName(), input, logMap.isTime(), logMap.isNewFile(), logMap.isZip());
        } catch (Exception e) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "An error occurred when executing command (log-custom: " + input + ")");
            ConfigHandler.getLang().sendErrorMsg(prefix, "Please check out the format of \"Log\" in CorePlus/config.yml.");
            ConfigHandler.getLang().sendDebugTrace(prefix, e);
        }
    }

    /**
     * Executing console command.
     * Format: "console: COMMAND"
     *
     * @param prefix the prefix of sending plugin.
     * @param player target.
     * @param input  the command.
     */
    @Override
    public void dispatchConsoleCmd(String prefix, Player player, String input) {
        try {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), input);
        } catch (Exception e) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cAn error occurred when executing command (console: " + input + ")");
            ConfigHandler.getLang().sendErrorMsg(prefix, "&7If this error keeps happening, please contact the plugin author.");
            ConfigHandler.getLang().sendDebugTrace(prefix, e);
        }
    }

    /**
     * Executing operator command.
     * Format: "op: COMMAND"
     *
     * @param prefix the prefix of sending plugin.
     * @param player target.
     * @param input  the command.
     */
    @Override
    public void dispatchOpCmd(String prefix, Player player, String input) {
        boolean isOp = player.isOp();
        try {
            player.setOp(true);
            player.chat("/" + input);
        } catch (Exception e) {
            ConfigHandler.getLang().sendDebugTrace(prefix, e);
            player.setOp(isOp);
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cAn error occurred when executing command (op: " + input + ")");
            ConfigHandler.getLang().sendErrorMsg(prefix, "&7If this error keeps happening, please contact the plugin author.");
            removeOp(prefix, player);
        } finally {
            player.setOp(isOp);
        }
    }

    private void removeOp(String prefix, Player player) {
        String playerName = player.getName();
        ConfigHandler.getLang().sendErrorMsg(prefix, "&cTrying to remove \"&e" + playerName + "&c\" Op status...");
        Bukkit.getScheduler().scheduleSyncDelayedTask(CorePlus.getInstance(), () -> {
            try {
                player.setOp(false);
                ConfigHandler.getLang().sendErrorMsg(prefix, "&fSucceed to remove \"" + playerName + "\" Op status!");
            } catch (Exception e) {
                ConfigHandler.getLang().sendErrorMsg(prefix, "&eCan not remove \"&e" + playerName + "&c\" Op status.");
            }
        }, 20);
    }

    /**
     * Executing player command.
     * Format: "player: COMMAND"
     *
     * @param prefix the prefix of sending plugin.
     * @param player target.
     * @param input  the command.
     */
    @Override
    public void dispatchPlayerCmd(String prefix, Player player, String input) {
        try {
            player.chat("/" + input);
        } catch (Exception e) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cAn error occurred when executing command (player: " + input + ")");
            ConfigHandler.getLang().sendErrorMsg(prefix, "&7If this error keeps happening, please contact the plugin author.");
            ConfigHandler.getLang().sendDebugTrace(prefix, e);
        }
    }

    /**
     * Executing BungeeCord command.
     * Format: "player: COMMAND"
     *
     * @param prefix the prefix of sending plugin.
     * @param player target.
     * @param input  the command.
     */
    @Override
    public void dispatchBungeeCordCmd(String prefix, Player player, String input) {
        try {
            BungeeCord.ExecuteCommand(player, input);
        } catch (Exception e) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cAn error occurred when executing command (bungee: " + input + ")");
            ConfigHandler.getLang().sendErrorMsg(prefix, "&7If this error keeps happening, please contact the plugin author.");
            ConfigHandler.getLang().sendDebugTrace(prefix, e);
        }
    }

    /**
     * Sending a sound to player.
     * Format: "Format: particle: Sound, Volume, Pitch"
     *
     * @param prefix the prefix of sending plugin.
     * @param player target.
     * @param input  the input sound value.
     */
    @Override
    public void dispatchSoundCmd(String prefix, Player player, String input) {
        try {
            String[] arr = input.split(", ");
            Location loc = player.getLocation();
            player.playSound(loc, arr[1], Long.parseLong(arr[2]), Long.parseLong(arr[3]));
        } catch (Exception e) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cCan not execute command (sound: " + input + ")");
            ConfigHandler.getLang().sendErrorMsg(prefix, "Format: particle: Sound, Volume, Pitch");
            ConfigHandler.getLang().sendDebugTrace(prefix, e);
        }
    }

    /**
     * Sending the sound from CorePlus config.yml to player.
     * Format: "sound-custom: GROUP"
     *
     * @param prefix the prefix of sending plugin.
     * @param player target.
     * @param input  the sound group from CorePlus config.yml.
     */
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
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cCan not execute command (sound-custom: " + input + ")");
            ConfigHandler.getLang().sendErrorMsg(prefix, "Please check the configuration settings.");
            ConfigHandler.getLang().sendDebugTrace(prefix, e);
        }
    }

    /**
     * Sending a particle to player.
     * Format: "Format: particle: Particle, Amount, OffsetX, OffsetY, OffsetZ, Speed"
     *
     * @param prefix the prefix of sending plugin.
     * @param loc    the location.
     * @param input  the input particle value.
     */
    @Override
    public void dispatchParticleCmd(String prefix, Location loc, String input) {
        try {
            String[] arr = input.split(", ");
            Particle particle = Particle.valueOf(arr[0]);
            int amount = Integer.parseInt(arr[1]);
            double offsetX = Double.parseDouble(arr[2]);
            double offsetY = Double.parseDouble(arr[3]);
            double offsetZ = Double.parseDouble(arr[4]);
            double extra = Double.parseDouble(arr[5]);
            loc.getWorld().spawnParticle(particle, loc, amount, offsetX, offsetY, offsetZ, extra);
        } catch (Exception ex) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cCan not execute command (particle: " + input + ")");
            ConfigHandler.getLang().sendErrorMsg(prefix, "Format: particle: Particle, Amount, OffsetX, OffsetY, OffsetZ, Speed");
        }
    }

    /**
     * Sending the particle from CorePlus config.yml to player.
     * Format: "particle: GROUP"
     *
     * @param prefix the prefix of sending plugin.
     * @param loc    the location.
     * @param input  the particle group from CorePlus config.yml.
     */
    @Override
    public void dispatchParticleCustomCmd(String prefix, Location loc, String input) {
        try {
            World world = loc.getWorld();
            if (world == null) {
                ConfigHandler.getLang().sendErrorMsg(prefix, "&cCan not find world to execute command (particle-custom: " + input + ")");
                ConfigHandler.getLang().sendErrorMsg(prefix, "&7If this error keeps happening, please contact the plugin author.");
                return;
            }
            ParticleMap particleMap = ConfigHandler.getConfigPath().getParticleProp().get(input);
            Particle particle = particleMap.getType();
            int amount = particleMap.getAmount();
            int times = particleMap.getTimes();
            int interval = particleMap.getInterval();
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
            }.runTaskTimer(CorePlus.getInstance(), 0, interval);
        } catch (Exception e) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cCan not execute command (particle-custom: " + input + ")");
            ConfigHandler.getLang().sendErrorMsg(prefix, "Please check the configuration settings.");
            ConfigHandler.getLang().sendDebugTrace(prefix, e);
        }
    }
}
