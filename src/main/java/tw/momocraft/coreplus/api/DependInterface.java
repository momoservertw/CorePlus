package tw.momocraft.coreplus.api;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import tw.momocraft.coreplus.utils.eco.GemsEcoAPI;
import tw.momocraft.coreplus.utils.eco.PlayerPointsAPI;
import tw.momocraft.coreplus.utils.eco.VaultAPI;

import java.util.UUID;


public interface DependInterface {

    boolean VaultEnabled();

    boolean PlayerPointsEnabled();

    boolean GemsEconomyEnabled();

    boolean PlaceHolderAPIEnabled();

    boolean LangUtilsEnabled();

    boolean ResidenceEnabled();

    boolean CMIEnabled();

    boolean MyPetEnabled();

    boolean ItemJoinEnabled();

    boolean MorphToolEnabled();

    boolean DiscordSRVEnabled();

    boolean MpdbEnabled();

    boolean AuthMeEnabled();


    VaultAPI getVaultApi();

    PlayerPointsAPI getPlayerPointsApi();

    GemsEcoAPI getGemsEcoApi();
}
