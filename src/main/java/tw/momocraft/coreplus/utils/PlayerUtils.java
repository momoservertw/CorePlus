package tw.momocraft.coreplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.api.PlayerInterface;

import java.util.UUID;

public class PlayerUtils implements PlayerInterface {

    @Override
    public Player getPlayerString(String playerName) {
        Player args = null;
        try {
            args = Bukkit.getPlayer(UUID.fromString(playerName));
        } catch (Exception ignored) {
        }
        if (args == null) {
            return Bukkit.getPlayer(playerName);
        }
        return args;
    }
}