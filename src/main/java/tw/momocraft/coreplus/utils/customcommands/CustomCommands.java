package tw.momocraft.coreplus.utils.customcommands;

import org.bukkit.*;;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.api.CommandInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;

import java.util.List;

public class CustomCommands implements CommandInterface {

    @Override
    public void executeCmdList(String prefix, Player player, List<String> input, boolean placeholder) {
        if (prefix == null)
            prefix = "";
        for (String value : input) {
            if (value.contains(";")) {
                String[] cmds;
                cmds = value.split(";");
                for (String cmd : cmds) {
                    if (cmd.startsWith("all-")) {
                        cmd = cmd.replace("all-", "");
                        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                            selectCmdType(prefix, onlinePlayer, cmd, placeholder);
                        }
                        continue;
                    }
                    selectCmdType(prefix, player, cmd, placeholder);
                }
            } else {
                selectCmdType(prefix, player, value, placeholder);
            }
        }
    }

    @Override
    public void executeCmd(String prefix, Player player, String input, boolean placeholder) {
        if (input.contains(";")) {
            String[] cmds = input.split(";");
            for (String cmd : cmds) {
                if (cmd.startsWith("all-")) {
                    cmd = cmd.replace("all-", "");
                    for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                        selectCmdType(prefix, onlinePlayer, cmd, placeholder);
                    }
                    continue;
                }
                selectCmdType(prefix, player, cmd, placeholder);
            }
        } else {
            selectCmdType(prefix, player, input, placeholder);
        }
    }

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
                    ConfigHandler.getLogger().addLog(CorePlus.getInstance().getDataFolder().getPath() + "//Logs",
                            "latest.log", input, true, false, false);
                    return;
                case "log-custom":
                    String path = CorePlus.getInstance().getDataFolder().getPath() + "//Logs";
                    String name = "latest.log";
                    boolean time = true;
                    String type = "default";
                    String[] arr = input.split(":");
                    for (int i = 0; i < arr.length; i++) {
                        switch (i) {
                            case 1:
                                path = arr[1];
                                break;
                            case 2:
                                name = arr[2];
                                break;
                            case 3:
                                try {
                                    time = Boolean.parseBoolean(arr[3]);
                                } catch (Exception ex) {
                                    type = arr[4];
                                }
                                break;
                            case 4:
                                type = arr[4];
                                break;
                        }
                    }
                    input = arr[arr.length - 1];
                    if (path.startsWith("plugins//")) {
                        path = Bukkit.getServer().getWorldContainer().getPath() + "//" + path;
                    } else if (path.startsWith("server//")) {
                        path = path.replace("server//", "");
                        path = Bukkit.getServer().getWorldContainer().getPath() + "//" + path;
                    }
                    boolean newFile;
                    boolean zip;
                    switch (type) {
                        case "zip":
                            newFile = true;
                            zip = true;
                            break;
                        case "new":
                            newFile = true;
                            zip = false;
                            break;
                        case "default":
                        default:
                            newFile = false;
                            zip = false;
                    }
                    ConfigHandler.getLogger().addLog(path, name, input, time, newFile, zip);
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
                    input = input.replace("sound: ", "");
                    dispatchSoundCmd(prefix, player, input);
                    return;
                case "particle":
                    input = input.replace("particle: ", "");
                    dispatchParticleGroupCmd(prefix, player.getLocation(), input);
                    return;
                case "particle-custom":
                    input = input.replace("particle-custom: ", "");
                    dispatchParticleCustomCmd(prefix, player, input);
                    return;
                default:
                    ConfigHandler.getLang().sendErrorMsg(prefix, "&cCan not find the execute command type (" + input + ")");
                    ConfigHandler.getLang().sendErrorMsg(prefix, "&cPlease check whether CorePlus is the latest version.");
            }
        }
    }

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
                ConfigHandler.getLogger().addLog(CorePlus.getInstance().getDataFolder().getPath() + "//Logs",
                        "latest.log", input, true, false, false);
                return;
            case "log-custom":
                String path = CorePlus.getInstance().getDataFolder().getPath() + "//Logs";
                String name = "latest.log";
                boolean time = true;
                String type = "default";

                String[] arr = input.split(":");
                input = arr[arr.length - 1];
                for (int i = 0; i < arr.length; i++) {
                    switch (i) {
                        case 1:
                            path = arr[1];
                            break;
                        case 2:
                            name = arr[2];
                            break;
                        case 3:
                            try {
                                time = Boolean.parseBoolean(arr[3]);
                            } catch (Exception ex) {
                                type = arr[4];
                            }
                            break;
                        case 4:
                            type = arr[4];
                            break;
                    }
                }
                if (path.startsWith("plugins//")) {
                    path = Bukkit.getServer().getWorldContainer().getPath() + "//" + path;
                } else if (path.startsWith("server//")) {
                    path = path.replace("server//", "");
                    path = Bukkit.getServer().getWorldContainer().getPath() + "//" + path;
                }
                boolean newFile;
                boolean zip;
                switch (type) {
                    case "zip":
                        newFile = true;
                        zip = true;
                        break;
                    case "new":
                        newFile = true;
                        zip = false;
                        break;
                    case "default":
                    default:
                        newFile = false;
                        zip = false;
                }
                ConfigHandler.getLogger().addLog(path, name, input, time, newFile, zip);
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
    public void dispatchCustomCmd(String prefix, Player player, String command, boolean placeholder) {
        String[] placeHolderArr = command.split(", ");
        String newCmd = ConfigHandler.getConfigPath().getCustomCmdProp().get(placeHolderArr[0]);
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
     * To execute console command.
     */
    @Override
    public void dispatchConsoleCmd(String prefix, Player player, String command) {
        if (player != null && !(player instanceof ConsoleCommandSender)) {
            try {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
            } catch (Exception e) {
                ConfigHandler.getLang().sendErrorMsg(prefix, "&cThere was an issue executing a console command, if this continues please report it to the developer!");
                ConfigHandler.getLang().sendDebugTrace(prefix, e);
            }
        } else {
            try {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
            } catch (Exception e) {
                ConfigHandler.getLang().sendErrorMsg(prefix, "&cThere was an issue executing a console command, if this continues please report it to the developer!");
                ConfigHandler.getLang().sendDebugTrace(prefix, e);
            }
        }
    }

    /**
     * To execute operator command.
     */
    @Override
    public void dispatchOpCmd(String prefix, Player player, String command) {
        boolean isOp = player.isOp();
        try {
            player.setOp(true);
            player.chat("/" + command);
        } catch (Exception e) {
            ConfigHandler.getLang().sendDebugTrace(prefix, e);
            player.setOp(isOp);
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cAn error has occurred while setting " + player.getName() + " status on the OP list, to ensure server security they have been removed as an OP.");
        } finally {
            player.setOp(isOp);
        }
    }

    /**
     * To execute player command.
     */
    @Override
    public void dispatchPlayerCmd(String prefix, Player player, String command) {
        try {
            player.chat("/" + command);
        } catch (Exception e) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cThere was an issue executing a player command, if this continues please report it to the developer!");
            ConfigHandler.getLang().sendDebugTrace(prefix, e);
        }
    }

    /**
     * To execute BungeeCord command.
     */
    @Override
    public void dispatchBungeeCordCmd(String prefix, Player player, String command) {
        try {
            BungeeCord.ExecuteCommand(player, command);
        } catch (Exception e) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cThere was an issue executing an item's command to BungeeCord, if this continues please report it to the developer!");
            ConfigHandler.getLang().sendDebugTrace(prefix, e);
        }
    }

    /**
     * To send sound to player.
     */
    @Override
    public void dispatchSoundCmd(String prefix, Player player, String command) {
        try {
            Location loc = player.getLocation();
            SoundMap soundMap = ConfigHandler.getConfigPath().getSoundProp().get(command);
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
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cCan not execute particle command (sound: " + command + ")");
            ConfigHandler.getLang().sendDebugTrace(prefix, e);
        }
    }

    /**
     * To send sound to player.
     */
    @Override
    public void dispatchSoundCmd(String prefix, Player player, String sound, long volume, long pitch, int times, int interval) {
        try {
            Location loc = player.getLocation();
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
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cCan not execute sound command (sound: " + sound + ")");
            ConfigHandler.getLang().sendDebugTrace(prefix, e);
        }
    }

    @Override
    public void dispatchParticleCustomCmd(String prefix, Player player, String input) {
        try {
            String[] arr = input.split(", ");
            String particle = arr[1];
            int amount = Integer.parseInt(arr[2]);
            int times = Integer.parseInt(arr[3]);
            int interval = Integer.parseInt(arr[4]);
            double offsetX = Double.parseDouble(arr[5]);
            double offsetY = Double.parseDouble(arr[6]);
            double offsetZ = Double.parseDouble(arr[7]);
            double extra = Double.parseDouble(arr[8]);
            dispatchParticleGroupCmd(prefix, player.getLocation(), particle, amount, times, interval, offsetX, offsetY, offsetZ, extra);
        } catch (Exception ex) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "Can not show particle (" + input + ")");
            ConfigHandler.getLang().sendErrorMsg(prefix, "Format: particle-custom: Particle, Amount, Times, Interval, OffsetX");
        }
    }
    /**
     * To send particle to player.
     */
    @Override
    public void dispatchParticleGroupCmd(String prefix, Location loc, String command) {
        try {
            World world = loc.getWorld();
            if (world == null) {
                ConfigHandler.getLang().sendErrorMsg(prefix, "&cCan not find world to execute particle command (particle: " + command + ")");
                return;
            }
            ParticleMap particleMap = ConfigHandler.getConfigPath().getParticleProp().get(command);
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
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cCan not execute particle command (particle: " + command + ")");
            ConfigHandler.getLang().sendDebugTrace(prefix, e);
        }
    }

    /**
     * To send particle to player.
     */
    @Override
    public void dispatchParticleCmd(String prefix, Location loc, String particle, int amount, int times, int interval,
                                         double offsetX, double offsetY, double offsetZ, double extra) {
        try {
            World world = loc.getWorld();
            if (world == null) {
                ConfigHandler.getLang().sendErrorMsg(prefix, "&cCan not find world to execute particle command (particle: " + particle + ")");
                return;
            }
            Particle particleType = Particle.valueOf(particle);
            new BukkitRunnable() {
                int i = 1;

                @Override
                public void run() {
                    if (i > times) {
                        cancel();
                    } else {
                        ++i;
                        world.spawnParticle(particleType, loc, amount, offsetX, offsetY, offsetZ, extra);
                    }
                }
            }.runTaskTimer(CorePlus.getInstance(), 0, interval);
        } catch (Exception e) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cCan not execute particle command (particle: " + particle + ")");
            ConfigHandler.getLang().sendDebugTrace(prefix, e);
        }
    }
}
