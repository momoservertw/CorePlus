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

public class PlaceHolderTest implements Listener {

    private static final List<String> playersList = new ArrayList<>();

    public void placeholder(CommandSender sender, String playerName, String input) {
        Player player;
        if (playerName != null) {
            player = UtilsHandler.getPlayer().getPlayer(playerName);
            if (player == null) {
                OfflinePlayer offlinePlayer = UtilsHandler.getPlayer().getOfflinePlayer(playerName);
                if (input.equals("all")) {
                    testOfflinePlayer(offlinePlayer);
                    return;
                } else if (input.equals("toggle")) {
                    if (!isModeEnabled(playerName))
                        putMode(playerName);
                    else
                        removeMode(playerName);
                    return;
                }
                String output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), null, null, input);
                UtilsHandler.getMsg().sendConsoleMsg(input + ": " + output);
                return;
            }
        } else {
            player = UtilsHandler.getPlayer().getPlayer(sender);
        }
        if (input.equals("all")) {
            testPlayer(player);
            return;
        }
        String output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, null, input);
        UtilsHandler.getMsg().sendConsoleMsg(input + ": " + output);
    }

    public void testPlayer(Player player) {
        String output;
        for (String input : getOther()) {
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, null, input);
            output = "player - other 【 " + input + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
        for (String input : getTarget()) {
            input = "%player" + input;
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, null, input);
            output = "player - target 【 " + input + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
        for (String input : getCustom()) {
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, null, input);
            output = "player - custom 【 " + input + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
        for (String input : getJS()) {
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, null, input);
            output = "player - JS 【 " + input + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
        for (String input : getCondition()) {
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, null, input);
            output = "player - condition 【 " + input + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
    }

    public void testOfflinePlayer(OfflinePlayer offlinePlayer) {
        String output;
        for (String input : getOther()) {
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), offlinePlayer, null, input);
            output = "offlinePlayer - other 【 " + input + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
        for (String input : getTarget()) {
            input = "%offlinePlayer" + input;
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), offlinePlayer, null, input);
            output = "offlinePlayer - target 【 " + input + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
        for (String input : getCustom()) {
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), offlinePlayer, null, input);
            output = "offlinePlayer - custom 【 " + input + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
        for (String input : getJS()) {
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), offlinePlayer, null, input);
            output = "offlinePlayer - JS 【 " + input + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
        for (String input : getCondition()) {
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), offlinePlayer, null, input);
            output = "offlinePlayer - condition 【 " + input + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!ConfigHandler.isDebug() || !player.isOp())
            return;
        if (!isModeEnabled(player.getName()))
            return;
        UtilsHandler.getMsg().sendMsg(ConfigHandler.getPrefix(), player,
                "&6Starting to show the \"Blocks\" placeholders...");
        Block target = e.getClickedBlock();
        String output;
        for (String input : getOther()) {
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, target, input);
            output = "click-block - other 【 " + input + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
        for (String input : getTarget()) {
            input = "%player" + input;
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, target, input);
            output = "click-block - target 【 " + input + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
        for (String input : getCustom()) {
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, target, input);
            output = "click-block - custom 【 " + input + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
        for (String input : getJS()) {
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, target, input);
            output = "click-block - JS 【 " + input + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
        for (String input : getCondition()) {
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, target, input);
            output = "click-block - condition 【 " + input + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        if (!ConfigHandler.isDebug() || !player.isOp())
            return;
        if (!isModeEnabled(player.getName()))
            return;
        UtilsHandler.getMsg().sendMsg(ConfigHandler.getPrefix(), player,
                "&6Starting to show the \"Entity\" placeholders...");
        Entity target = e.getRightClicked();
        String output;
        for (String input : getOther()) {
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, target, input);
            output = "click-entity - other 【 " + output + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
        for (String input : getTarget()) {
            input = "%player" + input;
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, target, input);
            output = "click-entity - target 【 " + output + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
        for (String input : getCustom()) {
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, target, input);
            output = "click-entity - custom 【 " + output + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
        for (String input : getJS()) {
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, target, input);
            output = "click-entity - JS 【 " + output + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
        for (String input : getCondition()) {
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, target, input);
            output = "click-entity - condition 【 " + output + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getView().getPlayer();
        if (!ConfigHandler.isDebug() || !player.isOp())
            return;
        if (!isModeEnabled(player.getName()))
            return;
        UtilsHandler.getMsg().sendMsg(ConfigHandler.getPrefix(), player,
                "&6Starting to show the \"Item\" placeholders...");
        ItemStack target = e.getCurrentItem();
        String output;
        for (String input : getOther()) {
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, target, input);
            output = "click-inventory - other 【 " + output + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
        for (String input : getTarget()) {
            input = "%player" + input;
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, target, input);
            output = "click-inventory - target 【 " + output + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
        for (String input : getCustom()) {
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, target, input);
            output = "click-inventory - custom 【 " + output + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
        for (String input : getJS()) {
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, target, input);
            output = "click-inventory - JS 【 " + output + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
        }
        for (String input : getCondition()) {
            output = UtilsHandler.getMsg().transHolder(ConfigHandler.getPluginName(), player, target, input);
            output = "click-inventory - condition 【 " + output + " 】【 " + output + " 】";
            UtilsHandler.getMsg().sendConsoleMsg(output);
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
        list.add("_nearby%players%type%all%16%");
        list.add("_nearby%players%name%all%16%");
        list.add("_nearby%players%amount%all%16%");
        list.add("_nearby%entities%name%Monsters%16%");
        list.add("_nearby%entities%amount%Monsters%16%");
        list.add("_nearby%blocks%type%UsableBlock%16%");
        list.add("_nearby%entities%name%all%16%");
        list.add("_nearby%blocks%type%all%3%");
        list.add("_nearby%blocks%amount%all%3%");
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

    public static boolean isModeEnabled(String playerName) {
        return playersList.contains(playerName);
    }

    public static void putMode(String playerName) {
        playersList.add(playerName);
    }

    public static void removeMode(String playerName) {
        playersList.remove(playerName);
    }
}
