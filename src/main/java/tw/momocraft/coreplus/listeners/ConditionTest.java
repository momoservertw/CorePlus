package tw.momocraft.coreplus.listeners;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.ArrayList;
import java.util.List;

public class ConditionTest implements Listener {

    public final List<String> toggleMode = new ArrayList<>();

    public void toggleToggleMode(CommandSender commandSender, String playerName) {
        if (toggleMode.contains(playerName)) {
            toggleMode.remove(playerName);
            UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), commandSender, "&fDisabled the placeholder debug mode.");
        } else {
            toggleMode.add(playerName);
            UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), commandSender, "&aEnabled the placeholder debug mode.");
        }
    }


    public void cmdOfflinePlayer(CommandSender sender, String playerName) {
        if (!toggleMode.contains(sender.getName()))
            return;
        OfflinePlayer offlinePlayer = UtilsHandler.getPlayer().getOfflinePlayer(playerName);
        String input;
        for (String placeholder : getOther()) {
            input = UtilsHandler.getLang().transByOfflinePlayer(ConfigHandler.getPluginName(), null,
                    "print: other(player): " + "%player" + placeholder, offlinePlayer, "player");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    input, true);
        }
        for (String placeholder : getTarget()) {
            input = UtilsHandler.getLang().transByOfflinePlayer(ConfigHandler.getPluginName(), null,
                    "print: target(player): " + "%player" + placeholder, offlinePlayer, "player");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    input, true);
        }
        for (String placeholder : getCustom()) {
            input = UtilsHandler.getLang().transByOfflinePlayer(ConfigHandler.getPluginName(), null,
                    "print: custom(player): " + "%player" + placeholder, offlinePlayer, "player");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    input, true);
        }
        for (String placeholder : getCondition()) {
            input = UtilsHandler.getLang().transByOfflinePlayer(ConfigHandler.getPluginName(), null,
                    "print: condition(player): " + "%player" + placeholder, offlinePlayer, "player");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    input, true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        if (!toggleMode.contains(e.getPlayer().getName()))
            return;
        Player player = e.getPlayer();
        Block target = e.getClickedBlock();
        String local = UtilsHandler.getVanillaUtils().getLocal(player);
        String input;
        for (String placeholder : getOther()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(), local,
                    "print: other: " + placeholder, player, "player");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);

            input = UtilsHandler.getLang().transByBlock(ConfigHandler.getPluginName(), local,
                    "print: other: " + placeholder, target, "target");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);
        }
        for (String placeholder : getTarget()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(), local,
                    "print: target(player): " + "%player" + placeholder, player, "player");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);

            input = UtilsHandler.getLang().transByBlock(ConfigHandler.getPluginName(), local,
                    "print: target(block): " + "%target" + placeholder, target, "target");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);
        }
        for (String placeholder : getCustom()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(), local,
                    "print: custom(player): " + placeholder, player, "target");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);

            input = UtilsHandler.getLang().transByBlock(ConfigHandler.getPluginName(), local,
                    "print: custom(block): " + placeholder, target, "target");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);
        }
        for (String placeholder : getCondition()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(), local,
                    "print: condition(player): " + placeholder, player, "player");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);

            input = UtilsHandler.getLang().transByBlock(ConfigHandler.getPluginName(), local,
                    "print: condition(block): " + placeholder, target, "target");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e) {
        if (!toggleMode.contains(e.getPlayer().getName()))
            return;
        Player player = e.getPlayer();
        Entity target = e.getRightClicked();
        String local = UtilsHandler.getVanillaUtils().getLocal(player);
        String input;
        for (String placeholder : getOther()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(), local,
                    "print: other: " + placeholder, player, "player");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);

            input = UtilsHandler.getLang().transByEntity(ConfigHandler.getPluginName(), local,
                    "print: other: " + placeholder, target, "target", false);
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);
        }
        for (String placeholder : getTarget()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(), local,
                    "print: target(player): " + "%player" + placeholder, player, "player");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);

            input = UtilsHandler.getLang().transByEntity(ConfigHandler.getPluginName(), local,
                    "print: target(block): " + "%target" + placeholder, target, "target", false);
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);
        }
        for (String placeholder : getCustom()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(), local,
                    "print: custom(player): " + placeholder, player, "player");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);

            input = UtilsHandler.getLang().transByEntity(ConfigHandler.getPluginName(), local,
                    "print: custom(block): " + placeholder, target, "target", false);
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);
        }
        for (String placeholder : getCondition()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(), local,
                    "print: condition(player): " + placeholder, player, "player");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);

            input = UtilsHandler.getLang().transByEntity(ConfigHandler.getPluginName(), local,
                    "print: condition(block): " + placeholder, target, "target", false);
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getView().getPlayer();
        if (!toggleMode.contains(player.getName()))
            return;
        ItemStack target = e.getCurrentItem();
        String local = UtilsHandler.getVanillaUtils().getLocal(player);
        String input;
        for (String placeholder : getOther()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(), local,
                    "print: other: " + placeholder, player, "player");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);

            input = UtilsHandler.getLang().transByItem(ConfigHandler.getPluginName(), local,
                    "print: other: " + placeholder, target, "target");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);
        }
        for (String placeholder : getTarget()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(), local,
                    "print: target(player): " + "%player" + placeholder, player, "player");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);

            input = UtilsHandler.getLang().transByItem(ConfigHandler.getPluginName(), local,
                    "print: target(block): " + "%target" + placeholder, target, "target");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);
        }
        for (String placeholder : getCustom()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(), local,
                    "print: custom(player): " + placeholder, player, "player");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);

            input = UtilsHandler.getLang().transByItem(ConfigHandler.getPluginName(), local,
                    "print: custom(block): " + placeholder, target, "target");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);
        }
        for (String placeholder : getCondition()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(), local,
                    "print: condition(player): " + placeholder, player, "player");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);

            input = UtilsHandler.getLang().transByItem(ConfigHandler.getPluginName(), local,
                    "print: condition(block): " + placeholder, target, "target");
            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(),
                    player, input, true);
        }
    }

    private List<String> getTarget() {
        List<String> list = new ArrayList<>();
        list.add("%");
        list.add("_type%");
        list.add("_type_local%");
        list.add("_display_name%");
        list.add("_amount%");
        list.add("_max_stack%");
        list.add("_uuid%");
        list.add("_world%");
        list.add("_loc%");
        list.add("_loc_x%");
        list.add("_loc_y%");
        list.add("_loc_z%");
        list.add("_loc_x%5%");
        list.add("_loc_y%5%");
        list.add("_loc_z%-5%");
        list.add("_nearby_players_name%16%"); // f
        list.add("_nearby_players_display_name%16%"); // f
        list.add("_nearby%entities%name%NormalMobs%16%");// f
        list.add("_nearby%materials%type%UsableBlock%16%");// f
        list.add("_world_time%");
        list.add("_biome%");
        list.add("_light%");
        list.add("_liquid%");
        list.add("_solid%"); // not catch
        list.add("_cave%");
        list.add("_residence%");
        list.add("_in_residence%");
        list.add("_is_usable%");
        list.add("_is_container%");
        list.add("_is_itemjoin%");
        list.add("_itemjoin_node%");
        list.add("_is_menu%");
        list.add("_skull_textures%");
        list.add("_inv_type%0%");
        list.add("_inv_name%HAND%");
        list.add("_is_usable%HAND%");
        list.add("_is_container%HAND%");
        list.add("_is_itemjoin%HAND%");
        list.add("_itemjoin_node%HAND%");
        list.add("_skull_textures%HAND%");
        list.add("%localtime_time%");
        return list;
    }

    private List<String> getOther() {
        List<String> list = new ArrayList<>();
        list.add("%localtime_time%");
        list.add("%random_number%100%");
        list.add("%random_list%String_1,String_2%");
        list.add("%random_player%");
        list.add("%random_player_except%huangge0513,Momo_Darkness%");
        return list;
    }

    private List<String> getCustom() {
        List<String> list = new ArrayList<>();
        list.add("%str.replace%Momocraft%craft%{e}%");
        list.add("%str.endsWith%Momocraft%craft%");
        list.add("%str.endsWith%Momocraft%craft%");
        list.add("%str.endsWith%Momocraft%momo%");
        list.add("%str.startsWith%Momocraft%omo%1%");
        list.add("%str.matches%Momocraft%[a-zA-Z]+%");
        list.add("%str.toLowerCase%Momocraft%");
        list.add("%str.toLowerCase%Momocraft%");
        list.add("$string.length: Momocraft$");
        list.add("%str.indexOf%Momocraft%M%");
        list.add("%str.lastIndexOf%Momocraft%t%");
        list.add("%str.equalsIgnoreCase%Momocraft%momocraft%");
        list.add("%str.contains%Momocraft%craf%");
        list.add("%str.charAt%Momocraft%m%");
        list.add("%string.substring%Momocraft%4%");
        list.add("%str.substring%Momocraft%5%%string.length%Momocraft%");
        list.add("%str.split%1+2+3{1}+%");
        list.add("%str.split%1+2+3%+%2%");
        return list;
    }

    private List<String> getCondition() {
        List<String> list = new ArrayList<>();
        list.add("<js>(11/(9+1)*10)%10</js>");
        list.add("<js>var input='abcdefg';" + "var output ='' ;" + "for (i = 0; i <= input.length; i++) { " + " output = input.charAt(i) + output" + "}<var>output</js>");
        list.add("<js>var input='abcdefg';" + "var output ='' ;" + "for (i = 0; i <= input.length; i++) { " + " output = input.charAt(i) + output" + "}<var>input,output</js>");
        return list;
    }
}
