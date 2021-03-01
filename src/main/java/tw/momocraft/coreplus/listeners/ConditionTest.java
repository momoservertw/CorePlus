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

    public void cmdOfflinePlayer(CommandSender sender, String playerName) {
        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender,
                "&6Starting to show the \"OfflinePlayerr\" placeholders...");
        OfflinePlayer offlinePlayer = UtilsHandler.getPlayer().getOfflinePlayer(playerName);
        String input;
        for (String placeholder : getOther()) {
            input = UtilsHandler.getLang().transByOfflinePlayer(ConfigHandler.getPluginName(),
                    null, placeholder, offlinePlayer, "player");
            input = "player - other 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", sender, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);
        }
        for (String placeholder : getTarget()) {
            placeholder = "%player" + placeholder;
            input = UtilsHandler.getLang().transByOfflinePlayer(ConfigHandler.getPluginName(),
                    null, placeholder, offlinePlayer, "player");
            input = "player - target 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", sender, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);
        }
        for (String placeholder : getCustom()) {
            input = UtilsHandler.getLang().transByOfflinePlayer(ConfigHandler.getPluginName(),
                    null, placeholder, offlinePlayer, "player");
            input = "player - custom 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", sender, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);
        }
        for (String placeholder : getJS()) {
            input = UtilsHandler.getLang().transByOfflinePlayer(ConfigHandler.getPluginName(),
                    null, placeholder, offlinePlayer, "player");
            input = "player - condition 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", sender, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);
        }
        for (String placeholder : getCondition()) {
            input = UtilsHandler.getLang().transByOfflinePlayer(ConfigHandler.getPluginName(),
                    null, placeholder, offlinePlayer, "player");
            input = "player - condition 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", sender, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!ConfigHandler.isDebugging() || !player.isOp())
            return;
        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), player,
                "&6Starting to show the \"Blocks\" placeholders...");
        Block target = e.getClickedBlock();
        String local = UtilsHandler.getVanillaUtils().getLocal(player);
        String input;
        for (String placeholder : getOther()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(),
                    local, placeholder, player, "player");
            input = "player - other 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);

            input = UtilsHandler.getLang().transByBlock(ConfigHandler.getPluginName(),
                    local, placeholder, target, "target");
            input = "blocks - other 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);
        }
        for (String placeholder : getTarget()) {
            placeholder = "%player" + placeholder;
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(),
                    local, placeholder, player, "player");
            input = "player - target 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);

            placeholder = "%target" + placeholder.replace("%player", "");
            input = UtilsHandler.getLang().transByBlock(ConfigHandler.getPluginName(),
                    local, placeholder, target, "target");
            input = "block - target 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);
        }
        for (String placeholder : getCustom()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(),
                    local, placeholder, player, "player");
            input = "player - custom 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);

            input = UtilsHandler.getLang().transByBlock(ConfigHandler.getPluginName(),
                    local, placeholder, target, "target");
            input = "blocks - custom 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);
        }
        for (String placeholder : getJS()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(),
                    local, placeholder, player, "player");
            input = "player - condition 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);

            input = UtilsHandler.getLang().transByBlock(ConfigHandler.getPluginName(),
                    local, placeholder, target, "target");
            input = "blocks - condition 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);
        }
        for (String placeholder : getCondition()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(),
                    local, placeholder, player, "player");
            input = "player - condition 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);

            input = UtilsHandler.getLang().transByBlock(ConfigHandler.getPluginName(),
                    local, placeholder, target, "target");
            input = "blocks - condition 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        if (!ConfigHandler.isDebugging() || !player.isOp())
            return;
        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), player,
                "&6Starting to show the \"Entity\" placeholders...");
        Entity target = e.getRightClicked();
        String local = UtilsHandler.getVanillaUtils().getLocal(player);
        String input;
        for (String placeholder : getOther()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(),
                    local, placeholder, player, "player");
            input = "player - other 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);

            input = UtilsHandler.getLang().transByEntity(ConfigHandler.getPluginName(),
                    local, placeholder, target, "target", false);
            input = "entity - other 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);
        }
        for (String placeholder : getTarget()) {
            placeholder = "%player" + placeholder;
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(),
                    local, placeholder, player, "player");
            input = "player - target 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);

            placeholder = "%target" + placeholder.replace("%player", "");
            input = UtilsHandler.getLang().transByEntity(ConfigHandler.getPluginName(),
                    local, placeholder, target, "target", false);
            input = "entity - target 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);
        }
        for (String placeholder : getCustom()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(),
                    local, placeholder, player, "player");
            input = "player - custom 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);

            input = UtilsHandler.getLang().transByEntity(ConfigHandler.getPluginName(),
                    local, placeholder, target, "target", false);
            input = "entity - custom 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);
        }
        for (String placeholder : getJS()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(),
                    local, placeholder, player, "player");
            input = "player - condition 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);

            input = UtilsHandler.getLang().transByEntity(ConfigHandler.getPluginName(),
                    local, placeholder, target, "target", false);
            input = "entity - condition 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);
        }
        for (String placeholder : getCondition()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(),
                    local, placeholder, player, "player");
            input = "player - condition 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);

            input = UtilsHandler.getLang().transByEntity(ConfigHandler.getPluginName(),
                    local, placeholder, target, "target", false);
            input = "entity - condition 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getView().getPlayer();
        if (!ConfigHandler.isDebugging() || !player.isOp())
            return;
        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), player,
                "&6Starting to show the \"Item\" placeholders...");
        ItemStack target = e.getCurrentItem();
        String local = UtilsHandler.getVanillaUtils().getLocal(player);
        String input;
        for (String placeholder : getOther()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(),
                    local, placeholder, player, "player");
            input = "player - other 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);

            input = UtilsHandler.getLang().transByItem(ConfigHandler.getPluginName(),
                    local, placeholder, target, "target");
            input = "item - other 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);
        }
        for (String placeholder : getTarget()) {
            placeholder = "%player" + placeholder;
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(),
                    local, placeholder, player, "player");
            input = "player - target 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);

            placeholder = "%target" + placeholder.replace("%player", "");
            input = UtilsHandler.getLang().transByItem(ConfigHandler.getPluginName(),
                    local, placeholder, target, "target");
            input = "item - target 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);
        }
        for (String placeholder : getCustom()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(),
                    local, placeholder, player, "player");
            input = "player - custom 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);

            input = UtilsHandler.getLang().transByItem(ConfigHandler.getPluginName(),
                    local, placeholder, target, "target");
            input = "item - custom 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);
        }
        for (String placeholder : getJS()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(),
                    local, placeholder, player, "player");
            input = "player - condition 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);

            input = UtilsHandler.getLang().transByItem(ConfigHandler.getPluginName(),
                    local, placeholder, target, "target");
            input = "item - condition 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);
        }
        for (String placeholder : getCondition()) {
            input = UtilsHandler.getLang().transByPlayer(ConfigHandler.getPluginName(),
                    local, placeholder, player, "player");
            input = "player - condition 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);

            input = UtilsHandler.getLang().transByItem(ConfigHandler.getPluginName(),
                    local, placeholder, target, "target");
            input = "item - condition 【 " + placeholder + " 】【 " + input + " 】";
            UtilsHandler.getLang().sendMsg("", player, input);
            UtilsHandler.getLang().sendConsoleMsg("", input);
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
        list.add("%str_replace%Momocraft%craft%{e}%");
        list.add("%str_endsWith%Momocraft%craft%");
        list.add("%str_startsWith%Momocraft%omo%");
        list.add("%str_startsWith%Momocraft%omo%1%");
        list.add("%str_matches%Momocraft%[a-zA-Z]+%");
        list.add("%str_toLowerCase%Momocraft%");
        list.add("%str_toUpperCase%Momocraft%");
        list.add("%str_length%Momocraft%");
        list.add("%str_indexOf%Momocraft%M%");
        list.add("%str_lastIndexOf%Momocraft%t%");
        list.add("%str_equalsIgnoreCase%Momocraft%momocraft%");
        list.add("%str_contains%Momocraft%craf%");
        list.add("%str_charAt%Momocraft%3%");
        list.add("%str_substring%Momocraft%4%");
        list.add("%str_substring%Momocraft%4%%str_length%Momocraft%%"); //
        list.add("%str_split%1,2,3%,%");
        return list;
    }

    private List<String> getJS() {
        List<String> list = new ArrayList<>();
        list.add("<js>(11/(9+1)*10)%10</js>");
        list.add("<js>var input='abcdefg';" + "var output ='' ;" + "for (i = 0; i <= input.length; i++) { " + " output = input.charAt(i) + output" + "}<var>output</js>");
        list.add("<js>var input='abcdefg';" + "var output ='' ;" + "for (i = 0; i <= input.length; i++) { " + " output = input.charAt(i) + output" + "}<var>input,output</js>");
        return list;
    }

    private List<String> getCondition() {
        List<String> list = new ArrayList<>();
        list.add("<if>TEST=TEST</if>");
        list.add("<if>TEST=TEST2<and>TEST=TEST</if>");
        list.add("<if>TEST=TEST2<and>TEST=TEST<or>TEST=TEST</if>");
        return list;
    }
}
