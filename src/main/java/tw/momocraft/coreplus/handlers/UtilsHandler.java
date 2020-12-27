package tw.momocraft.coreplus.handlers;

import org.bukkit.Bukkit;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.utils.*;
import tw.momocraft.coreplus.utils.blocksutils.BlocksUtils;
import tw.momocraft.coreplus.utils.locationutils.LocationUtils;

public class UtilsHandler {

    public static void setUp(boolean reload) {
        setLanguageUtils(new LanguageUtils());
        setDependence(new Dependence());
        setPlayerUtils(new PlayerUtils());
        setUtils(new Utils());
        setLogger(new Logger());
        setZipper(new Zipper());
        setUpdater(new Updater());
        setLocationUtils(new LocationUtils());
        setBlockUtils(new BlocksUtils());
        if (!reload) {
            getUpdate().check(ConfigHandler.getPrefix(), Bukkit.getConsoleSender(), CorePlus.getInstance().getDescription().getName(), CorePlus.getInstance().getDescription().getVersion());
        }
    }

    private static Dependence dependence;
    private static LanguageUtils languageUtils;
    private static PlayerUtils playerUtils;
    private static Utils utils;
    private static Logger logger;
    private static Zipper zipper;
    private static LocationUtils locationUtils;
    private static BlocksUtils blocksUtils;
    private static Updater updater;

    public static LanguageUtils getLang() {
        return languageUtils;
    }

    private static void setLanguageUtils(LanguageUtils language) {
        languageUtils = language;
    }

    public static Dependence getDepend() {
        return dependence;
    }

    private static void setDependence(Dependence depend) {
        dependence = depend;
    }

    public static PlayerUtils getPlayer() {
        return playerUtils;
    }

    private static void setPlayerUtils(PlayerUtils player) {
        playerUtils = player;
    }

    public static void setUtils(Utils util) {
        utils = util;
    }

    public static Utils getUtil() {
        return utils;
    }

    public static Logger getLog() {
        return logger;
    }

    private static void setLogger(Logger log) {
        logger = log;
    }

    public static Zipper getZip() {
        return zipper;
    }

    private static void setZipper(Zipper zip) {
        zipper = zip;
    }

    public static void setLocationUtils(LocationUtils loc) {
        locationUtils = loc;
    }

    public static LocationUtils getLoc() {
        return locationUtils;
    }

    public static void setBlockUtils(BlocksUtils blocks) {
        blocksUtils = blocks;
    }

    public static BlocksUtils getBlock() {
        return blocksUtils;
    }

    public static Updater getUpdate() {
        return updater;
    }

    private static void setUpdater(Updater update) {
        updater = update;
    }
}
