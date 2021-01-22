package tw.momocraft.coreplus.handlers;

import tw.momocraft.coreplus.utils.*;
import tw.momocraft.coreplus.utils.conditions.ConditionUtils;
import tw.momocraft.coreplus.utils.customcommands.CustomCommands;
import tw.momocraft.coreplus.utils.entities.EntityUtils;
import tw.momocraft.coreplus.utils.files.FileUtils;
import tw.momocraft.coreplus.utils.files.Zipper;
import tw.momocraft.coreplus.utils.language.LanguageUtils;
import tw.momocraft.coreplus.utils.language.Logger;
import tw.momocraft.coreplus.utils.language.VanillaUtils;

public class UtilsHandler {

    public static void setUpFirst() {
        languageUtils = new LanguageUtils();
    }

    public static void setUpLast() {
        dependence = new Dependence();
        customCommands = new CustomCommands();
        utils = new Utils();
        fileUtils = new FileUtils();
        conditionUtils = new ConditionUtils();
        playerUtils = new PlayerUtils();
        entityUtils = new EntityUtils();
        zipper = new Zipper();
        logger = new Logger();
        mySQLUtils = new MySQLUtils();
        updater = new Updater();
        vanillaUtils = new VanillaUtils();
    }

    private static Dependence dependence;
    private static CustomCommands customCommands;
    private static LanguageUtils languageUtils;
    private static Updater updater;
    private static Utils utils;
    private static FileUtils fileUtils;
    private static Zipper zipper;
    private static Logger logger;
    private static ConditionUtils conditionUtils;
    private static PlayerUtils playerUtils;
    private static EntityUtils entityUtils;
    private static MySQLUtils mySQLUtils;
    private static VanillaUtils vanillaUtils;

    public static Dependence getDepend() {
        return dependence;
    }

    public static CustomCommands getCustomCommands() {
        return customCommands;
    }

    public static LanguageUtils getLang() {
        return languageUtils;
    }

    public static VanillaUtils getVanillaUtils() {
        return vanillaUtils;
    }

    public static Updater getUpdate() {
        return updater;
    }

    public static Utils getUtil() {
        return utils;
    }

    public static FileUtils getFile() {
        return fileUtils;
    }

    public static Zipper getZip() {
        return zipper;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static ConditionUtils getCondition() {
        return conditionUtils;
    }

    public static PlayerUtils getPlayer() {
        return playerUtils;
    }

    public static EntityUtils getEntity() {
        return entityUtils;
    }

    public static MySQLUtils getMySQL() {
        return mySQLUtils;
    }
}
