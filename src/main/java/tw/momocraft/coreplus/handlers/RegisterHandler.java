package tw.momocraft.coreplus.handlers;

import tw.momocraft.coreplus.Commands;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.TabComplete;
import tw.momocraft.coreplus.api.CorePlusAPI;


public class RegisterHandler {

    public static void registerEvents() {
        CorePlus.getInstance().getCommand("CorePlus").setExecutor(new Commands());
        CorePlus.getInstance().getCommand("CorePlus").setTabCompleter(new TabComplete());
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(),
                "Register-Event", "Command Online", "CommandOnline", "continue", new Throwable().getStackTrace()[0]);
    }
}
