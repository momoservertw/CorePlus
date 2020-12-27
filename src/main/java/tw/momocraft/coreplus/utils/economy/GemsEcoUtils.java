<<<<<<< e5811d5844a80d4babc72785c65de69bd5a57b60:src/main/java/tw/momocraft/coreplus/utils/eco/GemsEcoAPI.java
package tw.momocraft.coreplus.utils.eco;

import me.xanium.gemseconomy.api.GemsEconomyAPI;
import me.xanium.gemseconomy.currency.Currency;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class GemsEcoAPI {

    private GemsEconomyAPI ge;

    public GemsEcoAPI() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("GemsEconomy");
        if (plugin != null) {
            ge = new GemsEconomyAPI();
        }
    }

    public double withdraw(UUID uuid, double value, String currency) {
        ge.withdraw(uuid, value, ge.getCurrency(currency));
        return getBalance(uuid, currency);
    }

    public double deposit(UUID uuid, double value, String currency) {
        ge.deposit(uuid, value, ge.getCurrency(currency));
        return getBalance(uuid, currency);
    }

    public double getBalance(UUID uuid, String currency) {
        return ge.getBalance(uuid, ge.getCurrency(currency));
    }

    public Currency getCurrency(String currency) {
        return ge.getCurrency(currency);
    }
}
=======
package tw.momocraft.coreplus.utils.economy;

import me.xanium.gemseconomy.api.GemsEconomyAPI;
import me.xanium.gemseconomy.currency.Currency;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class GemsEcoUtils {

    private GemsEconomyAPI ge;

    public GemsEcoUtils() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("GemsEconomy");
        if (plugin != null) {
            ge = new GemsEconomyAPI();
        }
    }

    public double withdraw(UUID uuid, double value, String currency) {
        ge.withdraw(uuid, value, ge.getCurrency(currency));
        return getBalance(uuid, currency);
    }

    public double deposit(UUID uuid, double value, String currency) {
        ge.deposit(uuid, value, ge.getCurrency(currency));
        return getBalance(uuid, currency);
    }

    public double getBalance(UUID uuid, String currency) {
        return ge.getBalance(uuid, ge.getCurrency(currency));
    }

    public Currency getCurrency(String currency) {
        return ge.getCurrency(currency);
    }
}
>>>>>>> Rewriting...:src/main/java/tw/momocraft/coreplus/utils/economy/GemsEcoUtils.java
