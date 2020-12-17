package tw.momocraft.coreplus.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public interface CommandInterface {

    void executeMultiCmdsList(String prefix, Player player, List<String> input, boolean placeholder);

    void executeMultipleCmds(String prefix, Player player, String input, boolean placeholder);


    /**
     * To execute custom command.
     * <p>
     * custom: group, arg1
     * group: "console: say %cmd_arg1%"
     */
    void dispatchCustomCmd(String prefix, Player player, String input, boolean placeholder);

    /**
     * To execute console command.
     *
     * @param player  the command sender.
     * @param command the command string.
     */
    void dispatchConsoleCmd(String prefix, Player player, String command);

    /**
     * To execute operator command.
     */
    void dispatchOpCmd(String prefix, Player player, String command);

    /**
     * To execute player command.
     */
    void dispatchPlayerCmd(String prefix, Player player, String command);

    /**
     * To execute BungeeCord command.
     */
    void dispatchBungeeCordCmd(String prefix, Player player, String command);

    /**
     * To send sound to player.
     */
    void dispatchSoundCmd(String prefix, Player player, String command);

    /**
     * To send particle to player.
     */
    void dispatchParticleCmd(String prefix, Location loc, String command);
}
