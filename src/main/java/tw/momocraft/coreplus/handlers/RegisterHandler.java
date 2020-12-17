package tw.momocraft.coreplus.handlers;

import tw.momocraft.coreplus.Commands;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.TabComplete;


public class RegisterHandler {

    public static void registerEvents() {
        CorePlus.getInstance().getCommand("CorePlus").setExecutor(new Commands());
        CorePlus.getInstance().getCommand("CorePlus").setTabCompleter(new TabComplete());
    }
}
