package tw.momocraft.coreplus.api;

import tw.momocraft.coreplus.utils.ItemJoinUtils;
import tw.momocraft.coreplus.utils.eco.GemsEcoAPI;
import tw.momocraft.coreplus.utils.eco.PlayerPointsAPI;
import tw.momocraft.coreplus.utils.eco.VaultAPI;
import tw.momocraft.coreplus.utils.permission.LuckPermsAPI;


public interface DependInterface {

    boolean VaultEnabled();

    boolean PlayerPointsEnabled();

    boolean GemsEconomyEnabled();

    boolean PlaceHolderAPIEnabled();

    boolean LangUtilsEnabled();

    boolean DiscordSRVEnabled();

    boolean LuckPermsEnabled();

    boolean MpdbEnabled();

    boolean ResidenceEnabled();

    boolean CMIEnabled();

    boolean MythicMobsEnabled();

    boolean ItemJoinEnabled();

    boolean AuthMeEnabled();


    VaultAPI getVaultApi();

    PlayerPointsAPI getPlayerPointsApi();

    GemsEcoAPI getGemsEcoApi();

    LuckPermsAPI getLuckPermsApi();

    ItemJoinUtils getItemJoinUtils();
}
