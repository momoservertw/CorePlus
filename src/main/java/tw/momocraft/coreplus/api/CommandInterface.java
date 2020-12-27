package tw.momocraft.coreplus.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public interface CommandInterface {

    /**
     * Executing command list.
     *
     * @param prefix      the executing plugin prefix.
     * @param player      the target player.
     * @param input       the input command list.
     * @param placeholder translating placeholders or not.
     */
    void executeCmdList(String prefix, Player player, List<String> input, boolean placeholder);


    /**
     * Executing a command.
     *
     * @param prefix      the executing plugin prefix.
     * @param player      the target player.
     * @param input       the input command.
     * @param placeholder translating placeholders or not.
     */
    void executeCmd(String prefix, Player player, String input, boolean placeholder);

    /**
     * Executing custom command.
     * Executing commands from CorePlus config.yml.
     * Format: "custom: Group, Args"
     *
     * @param prefix      the executing plugin prefix.
     * @param player      the target player.
     * @param group       the input group name.
     * @param placeholder translating placeholders or not.
     */
    void dispatchCustomCmd(String prefix, Player player, String group, boolean placeholder);

    /**
     * Writing message in log file.
     * Format: "log: Message"
     *
     * @param prefix the executing plugin prefix.
     * @param input  the input message.
     */
    void dispatchLogCmd(String prefix, String input);

    /**
     * Writing message to file from CorePlus config.yml.
     * Format: "log-custom: Group"
     *
     * @param prefix the executing plugin prefix.
     * @param group  the input message.
     */
    void dispatchLogCustomCmd(String prefix, String group);


    /**
     * Executing a console command.
     * Format: "console: Command"
     *
     * @param prefix the executing plugin prefix.
     * @param player the target player.
     * @param input  the input command.
     */
    void dispatchConsoleCmd(String prefix, Player player, String input);

    /**
     * Executing a operator command.
     * Format: "op: Command"
     *
     * @param prefix the executing plugin prefix.
     * @param player the target player.
     * @param input  the input command.
     */
    void dispatchOpCmd(String prefix, Player player, String input);

    /**
     * Executing a player command.
     * Format: "player: Command"
     *
     * @param prefix the executing plugin prefix.
     * @param player the target player.
     * @param input  the input command.
     */
    void dispatchPlayerCmd(String prefix, Player player, String input);

    /**
     * Executing a BungeeCord command.
     * Format: "bungee: Command"
     *
     * @param prefix the executing plugin prefix.
     * @param player the target player.
     * @param input  the input command.
     */
    void dispatchBungeeCordCmd(String prefix, Player player, String input);

    /**
     * Sending a sound to player.
     * Format: "sound: Sound, Volume, Pitch"
     *
     * @param prefix the executing plugin prefix.
     * @param player the target player.
     * @param input  the input command.
     */
    void dispatchSoundCmd(String prefix, Player player, String input);

    /**
     * Sending the sound from CorePlus config.yml to player.
     * Format: "sound-custom: Group"
     *
     * @param prefix the executing plugin prefix.
     * @param player the target player.
     * @param input  the group name.
     */
    void dispatchSoundCustomCmd(String prefix, Player player, String input);

    /**
     * Showing a particle at a location.
     * Format: "particle: Particle, Amount, OffsetX, OffsetY, OffsetZ, Speed"
     *
     * @param prefix the executing plugin prefix.
     * @param loc    the target Location.
     * @param input  the input value.
     */
    void dispatchParticleCmd(String prefix, Location loc, String input);

    /**
     * Showing the particle from CorePlus config.yml at a location.
     * Format: "particle-custom: Group"
     *
     * @param prefix the executing plugin prefix.
     * @param loc    the target Location.
     * @param input  the group name.
     */
    void dispatchParticleCustomCmd(String prefix, Location loc, String input);
}
