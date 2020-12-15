package tw.momocraft.coreplus.api;

import tw.momocraft.coreplus.utils.Updater;
import tw.momocraft.coreplus.utils.PlayerPointsAPI;
import tw.momocraft.coreplus.utils.VaultAPI;

public interface DependInterface {

    boolean VaultEnabled();

    boolean PlayerPointsEnabled();

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
}
