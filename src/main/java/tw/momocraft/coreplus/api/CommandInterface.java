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
    void addOnlineCmd(String pluginName, String playerName, int waitingTime, String command);

    /**
     * Executing custom commands by group name.
     * Format: "custom: Group, Args"
     *
     * @param pluginName the sent plugin name.
     * @param player     the command sender.
     * @param input      the input command group.
     */
    void sendGroupCmd(String pluginName, Player player, String input);

    /**
     * Executing condition commands.
     * Format: "condition: <Condition>, {true}succeedCmd{false}failedCmd"
     *
     * @param pluginName the sent plugin name.
     * @param player     the command sender.
     * @param input      the input command group.
     */
    void dispatchConditionCmd(String pluginName, Player player, String input);

    /**
     * Executing custom command(s).
     *
     * @param pluginName the sent plugin name.
     * @param player     the target player.
     * @param input      the value of commands.
     */
    void sendCmd(String pluginName, Player player, List<String> input);

    /**
     * Executing custom command(s).
     *
     * @param pluginName the sent plugin name.
     * @param input      the value of commands.
     */
    void sendCmd(String pluginName, List<String> input);

    /**
     * Executing custom command(s).
     *
     * @param pluginName the sent plugin name.
     * @param input      the value of commands.
     */
    void sendCmd(String pluginName, String input);

    /**
     * Executing custom command(s).
     *
     * @param pluginName the sent plugin name.
     * @param player     the target player.
     * @param input      the value of commands.
     */
    void sendCmd(String pluginName, Player player, String input);

    /**
     * Writing message in default log file.
     * Format: "log: Message"
     *
     * @param pluginName the executing plugin name.
     * @param input      the input message.
     */
    void dispatchLog(String pluginName, String input);

    /**
     * Writing message to file from CorePlus config.yml.
     * Format: "log-group: Group, Message"
     *
     * @param pluginName the executing plugin name.
     * @param input      the input group name and message.
     */
    void dispatchLogGroup(String pluginName, String input);

    /**
     * Executing a console command.
     * Format: "console: Command"
     *
     * @param pluginName the executing plugin name.
     * @param input      the input command.
     */
    void dispatchConsoleCmd(String pluginName, String input);

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
    void dispatchBungeeCmd(String pluginName, Player player, String input);

    /**
     * Sending player to another server.
     * Format: "switch: server player"
     *
     * @param pluginName the executing plugin name.
     * @param input      the input command.
     */
    void dispatchSwitch(String pluginName, String input);

    /**
     * Sending a sound to player.
     * Format: "sound: Sound, Volume, Pitch"
     *
     * @param pluginName the executing plugin name.
     * @param player     the target player.
     * @param input      the input command.
     */
    void dispatchSound(String pluginName, Player player, String input);

    /**
     * Sending the sound from CorePlus config.yml to player.
     * Format: "sound-group: Group"
     *
     * @param pluginName the executing plugin name.
     * @param player     the target player.
     * @param input      the group name.
     */
    void dispatchSoundGroup(String pluginName, Player player, String input);

    /**
     * Showing a particle at a location.
     * Format: "particle: Particle, Amount, OffsetX, OffsetY, OffsetZ, Speed"
     *
     * @param pluginName the executing plugin name.
     * @param loc        the target Location.
     * @param input      the input value.
     */
    void dispatchParticle(String pluginName, Location loc, String input);

    /**
     * Showing the particle from CorePlus config.yml at a location.
     * Format: "particle-group: Group"
     *
     * @param pluginName the executing plugin name.
     * @param loc        the target Location.
     * @param input      the group name.
     */
    void dispatchParticleGroup(String pluginName, Location loc, String input);
}
