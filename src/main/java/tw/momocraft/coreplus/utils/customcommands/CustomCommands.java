package tw.momocraft.coreplus.utils.customcommands;

import org.bukkit.Bukkit;;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.api.CommandInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.utils.BungeeCord;
import tw.momocraft.coreplus.utils.Utils;

import java.util.List;

public class CustomCommands implements CommandInterface {

    @Override
    public void executeMultiCmdsList(String prefix, Player player, List<String> input, boolean placeholder) {
        if (prefix == null)
            prefix = "";
        for (String value : input) {
            if (value.contains(";")) {
                String[] cmds;
                cmds = value.split(";");
                for (String cmd : cmds) {
                    executeCommands(prefix, player, cmd, placeholder);
                }
            } else {
                executeCommands(prefix, player, value, placeholder);
            }
        }
    }

    @Override
    public void executeMultipleCmds(String prefix, Player player, String input, boolean placeholder) {
        if (input.contains(";")) {
            String[] cmds = input.split(";");
            for (String cmd : cmds) {
                executeCommands(prefix, player, cmd, placeholder);
            }
        } else {
            executeCommands(prefix, player, input, placeholder);
        }
    }

    private void executeCommands(String prefix, Player player, String input, boolean placeholder) {
        if (placeholder) {
            input = Utils.translateLayout(input, player);
        }
        if (player == null || player instanceof ConsoleCommandSender) {
            executeCommands(prefix, input, placeholder);
        } else {
            if (input.startsWith("custom:")) {
                input = input.replace("custom: ", "");
                dispatchCustomCommand(prefix, player, input, placeholder);
                return;
            } else if (input.startsWith("print:")) {
                input = input.replace("print: ", "");
                ConfigHandler.getLang().sendConsoleMsg(null, input);
                return;
            } else if (input.startsWith("log:")) {
                input = input.replace("log: ", "");
                ConfigHandler.getLogger().addLog(CorePlus.getInstance().getDataFolder().getPath() + "//Logs", "latest.log", input, true, false, false);
                return;
            } else if (input.startsWith("log-custom:")) {
                String path;
                String name;
                String newFile;
                String zip;
                try {
                    String[] arr = input.split(":");
                    path = arr[1];
                    name = arr[2];
                    newFile = arr[3];
                    zip = arr[4];
                    input = input.replace("log-custom:" + path + ":" + name + ":" + newFile + ":" + zip + ": ", "");
                } catch (Exception ex) {

                    return;
                }
                if (path.startsWith("plugins//")) {
                    path = Bukkit.getServer().getWorldContainer().getPath() + "//" + path;
                } else if (path.startsWith("server//")) {
                    path = path.replace("server//", "");
                    path = Bukkit.getServer().getWorldContainer().getPath() + "//" + path;
                }
                ConfigHandler.getLogger().addLog(path, name, input, true, Boolean.parseBoolean(newFile), Boolean.parseBoolean(zip));
                return;
            } else if (input.startsWith("broadcast:")) {
                input = input.replace("broadcast: ", "");
                ConfigHandler.getLang().sendBroadcastMsg(null, input);
                return;
            } else if (input.startsWith("console:")) {
                input = input.replace("console: ", "");
                dispatchConsoleCommand(prefix, player, input);
                return;
            } else if (input.startsWith("bungee:")) {
                input = input.replace("bungee: ", "");
                dispatchBungeeCordCommand(prefix, player, input);
                return;
            } else if (input.startsWith("op:")) {
                input = input.replace("op: ", "");
                dispatchOpCommand(prefix, player, input);
                return;
            } else if (input.startsWith("player:")) {
                input = input.replace("player: ", "");
                dispatchPlayerCommand(prefix, player, input);
                return;
            } else if (input.startsWith("chat:")) {
                input = input.replace("chat: ", "");
                ConfigHandler.getLang().sendChatMsg(null, player, input);
                return;
            } else if (input.startsWith("message:")) {
                input = input.replace("message: ", "");
                ConfigHandler.getLang().sendPlayerMsg(null, player, input);
                return;
            } else if (input.startsWith("sound:")) {
                input = input.replace("sound: ", "");
                dispatchSoundCommand(prefix, player, input);
                return;
            } else if (input.startsWith("particle:")) {
                input = input.replace("particle: ", "");
                dispatchParticleCommand(prefix, player, input);
                return;
            }
            dispatchConsoleCommand(prefix, null, input);
        }
    }

    private void executeCommands(String input, String prefix, boolean placeholder) {
        if (placeholder) {
            input = Utils.translateLayout(input, null);
        }
        if (input.startsWith("custom:")) {
            input = input.replace("custom: ", "");
            dispatchCustomCommand(prefix, null, input, placeholder);
            return;
        } else if (input.startsWith("print:")) {
            input = input.replace("print: ", "");
            ConfigHandler.getLang().sendConsoleMsg(null, input);
            return;
        } else if (input.startsWith("log:")) {
            input = input.replace("log: ", "");
            ConfigHandler.getLogger().addLog(CorePlus.getInstance().getDataFolder().getPath() + "//Logs", "latest.log", input, true, false, false);
            return;
        } else if (input.startsWith("log-custom:")) {
            String[] arr = input.split(":");
            String path = arr[1];
            String name = arr[2];
            String newFile = arr[3];
            String zip = arr[4];
            input = input.replace("log-custom:" + path + ":" + name + ":" + newFile + ":" + zip + ": ", "");
            if (path.startsWith("plugins//")) {
                path = Bukkit.getServer().getWorldContainer().getPath() + "//" + path;
            } else if (path.startsWith("server//")) {
                path = path.replace("server//", "");
                path = Bukkit.getServer().getWorldContainer().getPath() + "//" + path;
            }
            ConfigHandler.getLogger().addLog(path, name, input, true, Boolean.parseBoolean(newFile), Boolean.parseBoolean(zip));
            return;
        } else if (input.startsWith("broadcast:")) {
            input = input.replace("broadcast: ", "");
            ConfigHandler.getLang().sendBroadcastMsg(null, input);
            return;
        } else if (input.startsWith("console:")) {
            input = input.replace("console: ", "");
            dispatchConsoleCommand(prefix, null, input);
            return;
        } else if (input.startsWith("bungee:")) {
            dispatchBungeeCordCommand(prefix, null, input);
            return;
            // No target.
        } else if (input.startsWith("op:")) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cThere is an error while execute command \"&eop: " + input + "&c\" &8- &cCan not find the execute target.");
            return;
        } else if (input.startsWith("player:")) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cThere is an error while execute command \"&eplayer:" + input + "&c\" &8- &cCan not find the execute target.");
            return;
        } else if (input.startsWith("chat:")) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cThere is an error while execute command \"&echat:" + input + "&c\" &8- &cCan not find the execute target.");
            return;
        } else if (input.startsWith("sound:")) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cThere is an error while execute command \"&esound:" + input + "&c\" &8- &cCan not find the execute target.");
            return;
        } else if (input.startsWith("particle:")) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cThere is an error while execute command \"&eparticle:" + input + "&c\" &8- &cCan not find the execute target.");
            return;
        }
        dispatchConsoleCommand(prefix, null, input);
    }

    /**
     * To execute custom command.
     * <p>
     * custom: group, arg1
     * group: "console: say %cmd_arg1%"
     */
    @Override
    public void dispatchCustomCommand(String prefix, Player player, String command, boolean placeholder) {
        String[] placeHolderArr = command.split(", ");
        String newCmd = ConfigHandler.getConfigPath().getCustomCmdProp().get(placeHolderArr[0]);
        if (newCmd == null) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cCan not find the custom command group: " + placeHolderArr[0]);
            return;
        }
        for (int i = 1; i < +placeHolderArr.length; i++) {
            newCmd = newCmd.replace("%cmd_arg" + i + "%", placeHolderArr[i]);
        }
        executeCommands(prefix, player, newCmd, placeholder);
    }

    /**
     * To execute console command.
     */
    @Override
    public void dispatchConsoleCommand(String prefix, Player player, String command) {
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
    public void dispatchOpCommand(String prefix, Player player, String command) {
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
    public void dispatchPlayerCommand(String prefix, Player player, String command) {
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
    public void dispatchBungeeCordCommand(String prefix, Player player, String command) {
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
    public void dispatchSoundCommand(String prefix, Player player, String command) {
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
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cThere was an issue executing a command to send sound, if this continues please report it to the developer!");
            ConfigHandler.getLang().sendDebugTrace(prefix, e);
        }
    }

    /**
     * To send particle to player.
     */
    @Override
    public void dispatchParticleCommand(String prefix, Player player, String command) {
        try {
            Location loc = player.getLocation();
            ParticleMap particleMap = ConfigHandler.getConfigPath().getParticleProp().get(command);
            Particle particle = Particle.valueOf(particleMap.getType());
            int amount = particleMap.getAmount();
            int times = particleMap.getTimes();
            int interval = particleMap.getInterval();
            new BukkitRunnable() {
                int i = 1;

                @Override
                public void run() {
                    if (i > times) {
                        cancel();
                    } else {
                        ++i;
                        player.spawnParticle(particle, loc, amount, 0, 0, 0, 0);
                    }
                }
            }.runTaskTimer(CorePlus.getInstance(), 0, interval);
        } catch (Exception e) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cThere was an issue executing a command to send particle, if this continues please report it to the developer!");
            ConfigHandler.getLang().sendDebugTrace(prefix, e);
        }
    }
}
