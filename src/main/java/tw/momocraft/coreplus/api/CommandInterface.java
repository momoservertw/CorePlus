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
     * @param pluginName  the executing plugin name.
     * @param players     the target players.
     * @param input       the input command list.
     * @param placeholder translating placeholders or not.
     */
    void executeCmdList(String pluginName, List<Player> players, List<String> input, boolean placeholder, String... langHolder);

    /**
     * Executing command list.
     *
     * @param pluginName  the executing plugin name.
     * @param player      the target player.
     * @param input       the input command list.
     * @param placeholder translating placeholders or not.
     */
    void executeCmdList(String pluginName, Player player, List<String> input, boolean placeholder, String... langHolder);

    /**
     * Executing command list.
     *
     * @param pluginName  the executing plugin name.
     * @param input       the input command list.
     * @param placeholder translating placeholders or not.
     */
    void executeCmdList(String pluginName, List<String> input, boolean placeholder, String... langHolder);

    /**
     * Executing a command for multiple target.
     *
     * @param pluginName  the executing plugin name.
     * @param players     the target s.
     * @param input       the input command.
     * @param placeholder translating placeholders or not.
     */
    void executeCmd(String pluginName, List<Player> players, String input, boolean placeholder, String... langHolder);

    /**
     * Executing a command.
     *
     * @param pluginName  the executing plugin prefix.
     * @param player      the target player.
     * @param input       the input command.
     * @param placeholder translating placeholders or not.
     */
    void executeCmd(String pluginName, Player player, String input, boolean placeholder, String... langHolder);

    /**
     * Executing a command.
     *
     * @param pluginName  the executing plugin name.
     * @param input       the input command.
     * @param placeholder translating placeholders or not.
     */
    void executeCmd(String pluginName, String input, boolean placeholder, String... langHolder);

    /**
     * Executing custom command.
     * Executing commands from CorePlus config.yml.
     * Format: "custom: Group, Args"
     *
     * @param pluginName  the executing plugin name.
     * @param player      the target player.
     * @param group       the input group name.
     * @param placeholder translating placeholders or not.
     */
    void dispatchCustomCmd(String pluginName, Player player, String group, boolean placeholder, String... langHolder);

    /**
     * Executing custom command.
     * Executing commands from CorePlus config.yml.
     * Format: "custom: Group, Args"
     *
     * @param pluginName  the executing plugin name.
     * @param player      the target player.
     * @param input       the input condition and action.
     * @param placeholder translating placeholders or not.
     */
    void dispatchConditionCmd(String pluginName, Player player, String input, boolean placeholder);

    /**
     * Writing message in default log file.
     * Format: "log: Message"
     *
     * @param pluginName the executing plugin name.
     * @param input      the input message.
     */
    void dispatchLogCmd(String pluginName, String input);

    /**
     * Writing message to file from CorePlus config.yml.
     * Format: "log-custom: Group, Message"
     *
     * @param pluginName the executing plugin name.
     * @param input      the input group name and message.
     */
    void dispatchLogCustomCmd(String pluginName, String input);

    /**
     * Executing a console command.
     * Format: "console: Command"
     *
     * @param pluginName the executing plugin name.
     * @param player     the target player.
     * @param input      the input command.
     */
    void dispatchConsoleCmd(String pluginName, Player player, String input);

    /**
     * Executing a operator command.
     * Format: "op: Command"
     *
     * @param pluginName the executing plugin name.
     * @param player     the target player.
     * @param input      the input command.
     */
    void dispatchOpCmd(String pluginName, Player player, String input);

    /**
     * Executing a player command.
     * Format: "player: Command"
     *
     * @param pluginName the executing plugin name.
     * @param player     the target player.
     * @param input      the input command.
     */
    void dispatchPlayerCmd(String pluginName, Player player, String input);

    /**
     * sending a chat message for player as operator to bypass the color limit.
     * Format: "chat-op: Message"
     *
     * @param pluginName the executing plugin name.
     * @param player     the target player.
     * @param input      the input message.
     */
    void sendChatOpMsg(String pluginName, Player player, String input);

    /**
     * Executing a BungeeCord command.
     * Format: "bungee: Command"
     *
     * @param pluginName the executing plugin name.
     * @param player     the target player.
     * @param input      the input command.
     */
    void dispatchBungeeCordCmd(String pluginName, Player player, String input);

    /**
     * Sending player to another server.
     * Format: "switch: server player"
     *
     * @param pluginName the executing plugin name.
     * @param input      the input command.
     */
    void dispatchSwitchCmd(String pluginName, String input);

    /**
     * Sending a sound to player.
     * Format: "sound: Sound, Volume, Pitch"
     *
     * @param pluginName the executing plugin name.
     * @param player     the target player.
     * @param input      the input command.
     */
    void dispatchSoundCmd(String pluginName, Player player, String input);

    /**
     * Sending the sound from CorePlus config.yml to player.
     * Format: "sound-custom: Group"
     *
     * @param pluginName the executing plugin name.
     * @param player     the target player.
     * @param input      the group name.
     */
    void dispatchSoundCustomCmd(String pluginName, Player player, String input);

    /**
     * Showing a particle at a location.
     * Format: "particle: Particle, Amount, OffsetX, OffsetY, OffsetZ, Speed"
     *
     * @param pluginName the executing plugin name.
     * @param loc        the target Location.
     * @param input      the input value.
     */
    void dispatchParticleCmd(String pluginName, Location loc, String input);

    /**
     * Showing the particle from CorePlus config.yml at a location.
     * Format: "particle-custom: Group"
     *
     * @param pluginName the executing plugin name.
     * @param loc        the target Location.
     * @param input      the group name.
     */
    void dispatchParticleCustomCmd(String pluginName, Location loc, String input);
}
