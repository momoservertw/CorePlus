package tw.momocraft.coreplus.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public interface CommandInterface {

    /**
     * Executing command for a player when he is login.
     *
     * @param playerName  the target player name.
     * @param waitingTime the waiting seconds.
     * @param command     the dispatch command.
     */
    void addWaiting(String playerName, int waitingTime, String command);

    /**
     * Executing command list for targets.
     *
     * @param prefix      the executing plugin prefix.
     * @param players      the target players.
     * @param input       the input command list.
     * @param placeholder translating placeholders or not.
     */
    void executeCmdList(String prefix, List<Player> players, List<String> input, boolean placeholder, String... langHolder);

    /**
     * Executing command list.
     *
     * @param prefix      the executing plugin prefix.
     * @param player      the target player.
     * @param input       the input command list.
     * @param placeholder translating placeholders or not.
     */
    void executeCmdList(String prefix, Player player, List<String> input, boolean placeholder, String... langHolder);

    /**
     * Executing command list.
     *
     * @param prefix      the executing plugin prefix.
     * @param input       the input command list.
     * @param placeholder translating placeholders or not.
     */
    void executeCmdList(String prefix, List<String> input, boolean placeholder, String... langHolder);

    /**
     * Executing a command for multiple target.
     *
     * @param prefix      the executing plugin prefix.
     * @param players      the target s.
     * @param input       the input command.
     * @param placeholder translating placeholders or not.
     */
    void executeCmd(String prefix, List<Player> players, String input, boolean placeholder, String... langHolder);

    /**
     * Executing a command.
     *
     * @param prefix      the executing plugin prefix.
     * @param player      the target player.
     * @param input       the input command.
     * @param placeholder translating placeholders or not.
     */
    void executeCmd(String prefix, Player player, String input, boolean placeholder, String... langHolder);

    /**
     * Executing a command.
     *
     * @param prefix      the executing plugin prefix.
     * @param input       the input command.
     * @param placeholder translating placeholders or not.
     */
    void executeCmd(String prefix, String input, boolean placeholder, String... langHolder);

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
    void dispatchCustomCmd(String prefix, Player player, String group, boolean placeholder, String... langHolder);

    /**
     * Writing message in default log file.
     * Format: "log: Message"
     *
     * @param prefix the executing plugin prefix.
     * @param input  the input message.
     */
    void dispatchLogCmd(String prefix, String input);

    /**
     * Writing message to file from CorePlus config.yml.
     * Format: "log-custom: Group, Message"
     *
     * @param prefix the executing plugin prefix.
     * @param input  the input group name and message.
     */
    void dispatchLogCustomCmd(String prefix, String input);

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
     * sending a chat message for player as operator to bypass the color limit.
     * Format: "chat-op: Message"
     *
     * @param prefix the executing plugin prefix.
     * @param player the target player.
     * @param input  the input message.
     */
    void sendChatOpMsg(String prefix, Player player, String input);

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
