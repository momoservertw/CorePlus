package tw.momocraft.coreplus.api;


import java.util.UUID;

public interface PriceInterface {
    double getTypeBalance(UUID uuid, String priceType);

    double takeTypeMoney(UUID uuid, String priceType, double amount);
}
