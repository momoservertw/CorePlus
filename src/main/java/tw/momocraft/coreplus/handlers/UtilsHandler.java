package tw.momocraft.coreplus.handlers;

import org.bukkit.Bukkit;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.utils.*;
import tw.momocraft.coreplus.utils.conditions.ConditionUtils;
import tw.momocraft.coreplus.utils.entities.EntityUtils;
import tw.momocraft.coreplus.utils.files.FileUtils;
import tw.momocraft.coreplus.utils.files.Zipper;
import tw.momocraft.coreplus.utils.language.LanguageUtils;
import tw.momocraft.coreplus.utils.language.Logger;

public class UtilsHandler {

    public static void setUp() {
        setLanguageUtils(new LanguageUtils());
        setDependence(new Dependence());
        setConditionUtils(new ConditionUtils());
        setPlayerUtils(new PlayerUtils());
        setEntityUtils(new EntityUtils());
        setFileUtils(new FileUtils());
        setZipper(new Zipper());
        setLogger(new Logger());
        setUtils(new Utils());
        setUpdater(new Updater());
    }

    private static Dependence dependence;
    private static LanguageUtils languageUtils;
    private static Updater updater;
    private static Utils utils;
    private static FileUtils fileUtils;
    private static Zipper zipper;
    private static Logger logger;
    private static ConditionUtils conditionUtils;
    private static PlayerUtils playerUtils;
    private static EntityUtils entityUtils;

    public static Dependence getDepend() {
        return dependence;
    }

    private static void setDependence(Dependence depend) {
        dependence = depend;
    }

    public static void setUtils(Utils util) {
        utils = util;
    }

    public static Utils getUtil() {
        return utils;
    }

    public static LanguageUtils getLang() {
        return languageUtils;
    }

    private static void setLanguageUtils(LanguageUtils language) {
        languageUtils = language;
    }

    public static Updater getUpdate() {
        return updater;
    }

    private static void setUpdater(Updater update) {
        updater = update;
    }

    public static ConditionUtils getCondition() {
        return conditionUtils;
    }

    private static void setConditionUtils(ConditionUtils condition) {
        conditionUtils = condition;
    }

    public static EntityUtils getEntity() {
        return entityUtils;
    }

    private static void setEntityUtils(EntityUtils entity) {
        entityUtils = entity;
    }

    public static PlayerUtils getPlayer() {
        return playerUtils;
    }

    private static void setPlayerUtils(PlayerUtils player) {
        playerUtils = player;
    }

    public static FileUtils getFile() {
        return fileUtils;
    }

    private static void setFileUtils(FileUtils file) {
        fileUtils = file;
    }

    public static Zipper getZip() {
        return zipper;
    }

    private static void setZipper(Zipper zip) {
        zipper = zip;
    }

    public static Logger getLogger() {
        return logger;
    }

    private static void setLogger(Logger log) {
        logger = log;
    }
}
