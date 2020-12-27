package tw.momocraft.coreplus.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public interface CommandInterface {

    void executeCmdList(String prefix, Player player, List<String> input, boolean placeholder);

    void executeCmd(String prefix, Player player, String input, boolean placeholder);


    /**
     * To execute custom command.
     * <p>
     * custom: group, arg1
     * group: "console: say %cmd_arg1%"
     */
    void dispatchCustomCmd(String prefix, Player player, String input, boolean placeholder);


    void dispatchLogCmd(String prefix, String input);

    void dispatchLogCustomCmd(String prefix, String input);

    /**
     * To execute console command.
     *
     * @param player  the command sender.
     * @param input the command string.
     */
    void dispatchConsoleCmd(String prefix, Player player, String input);

    /**
     * To execute operator command.
     */
    void dispatchOpCmd(String prefix, Player player, String input);

    /**
     * To execute player command.
     */
    void dispatchPlayerCmd(String prefix, Player player, String input);

    /**
     * To execute BungeeCord command.
     */
    void dispatchBungeeCordCmd(String prefix, Player player, String input);

    /**
     * To send sound to player.
     */

    void dispatchSoundCustomCmd(String prefix, Player player, String input);

    void dispatchSoundCmd(String prefix, Player player, String input);

    /**
     * To send particle to player.
     */

    void dispatchParticleCustomCmd(String prefix, Location loc, String input);

    void dispatchParticleCmd(String prefix, Location loc, String input);
}
