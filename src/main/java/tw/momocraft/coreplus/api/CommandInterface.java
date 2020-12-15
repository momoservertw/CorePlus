package tw.momocraft.coreplus.api;

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
    void dispatchCustomCommand(String prefix, Player player, String input, boolean placeholder);

    /**
     * To execute console command.
     *
     * @param player  the command sender.
     * @param command the command string.
     */
    void dispatchConsoleCommand(String prefix, Player player, String command);

    /**
     * To execute operator command.
     */
    void dispatchOpCommand(String prefix, Player player, String command);

    /**
     * To execute player command.
     */
    void dispatchPlayerCommand(String prefix, Player player, String command);

    /**
     * To execute BungeeCord command.
     */
    void dispatchBungeeCordCommand(String prefix, Player player, String command);

    /**
     * To send sound to player.
     */
    void dispatchSoundCommand(String prefix, Player player, String command);

    /**
     * To send particle to player.
     */
    void dispatchParticleCommand(String prefix, Player player, String command);
}
