package tw.momocraft.coreplus.utils.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataUtils {


    private void backup() {
        ServerHandler.sendConsoleMessage("&fData successfully cleaned  &a✔");
        ServerHandler.sendConsoleMessage("");
        ServerHandler.sendConsoleMessage("&f---- Statistics ----");
        for (String title : expiredMap.rowKeySet()) {
            ServerHandler.sendConsoleMessage(title + ":" + cleanCustomStatus(title));
            for (String subtitle : expiredMap.rowMap().get(title).keySet()) {
                ServerHandler.sendConsoleMessage("> " + subtitle + " - " + expiredMap.get(title, subtitle).size());
            }
            ServerHandler.sendConsoleMessage("");
        }
        if (ConfigHandler.getConfigPath().isBackupToZip()) {
            if (backupFile.exists()) {
                ServerHandler.sendConsoleMessage("&6Starting to compression the backup folder...");
                if (zipFiles(backupPath)) {
                    ServerHandler.sendConsoleMessage("&fZip successfully created &8\"&e" + backupName + ".zip&8\"  &a✔");
                } else {
                    ServerHandler.sendConsoleMessage("&fZip creation failed &8\"&e" + backupName + ".zip&8\"  &c✘");
                }
            }
        }
        if (ConfigHandler.getConfigPath().isCleanLogEnable()) {
            ServerHandler.sendConsoleMessage("");
            ServerHandler.sendConsoleMessage("&6Starting to create the log...");
            if (saveLogs(backupFile)) {
                ServerHandler.sendConsoleMessage("&fLog successfully created &8\"&elatest.log&8\"  &a✔");
            } else {
                ServerHandler.sendConsoleMessage("&fLog creation failed &8\"&elatest.log&8\"  &c✘");
            }
        }
    }

    private static String getBackupTimeName() {
        String timeFormat = "yyyy-MM-dd";
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
        return currentDate.format(formatter);
    }

    private String getBackupPath() {
        String backupTimeName = getBackupTimeName();
        String backupMode = ConfigHandler.getConfigPath().getBackupMode();
        String backupFolderName = ConfigHandler.getConfigPath().getBackupFolderName();
        if (backupMode == null) {
            backupMode = "plugin";
            ServerHandler.sendConsoleMessage("&cThe option &8\"&eClean.Settings.Backup.Name&8\" &cis missing, using the default value \"plugin\".");
        }
        String backupPath;
        String backupCustomPath;
        switch (backupMode) {
            case "plugin":
                backupPath = PlayerdataPlus.getInstance().getDataFolder().getPath() + "\\" + backupFolderName + "\\" + backupTimeName;
                break;
            case "custom":
                backupCustomPath = ConfigHandler.getConfigPath().getBackupCustomPath();
                if (backupCustomPath != null) {
                    backupPath = backupCustomPath + "\\" + backupFolderName + "\\" + backupTimeName;
                } else {
                    ServerHandler.sendConsoleMessage("&cThe option &8\"&eClean.Settings.Backup.Custom-Path&8\" &cis empty, using the default mode \"plugin\".");
                    backupPath = PlayerdataPlus.getInstance().getDataFolder().getPath() + "\\Backup\\" + backupTimeName;
                }
                break;
            default:
                ServerHandler.sendConsoleMessage("&cThe option &8\"&eClean.Settings.Backup.Mode&8\" &cis empty, using the default mode \"plugin\".");
                backupPath = PlayerdataPlus.getInstance().getDataFolder().getPath() + "\\" + backupFolderName + "\\" + backupTimeName;
                break;
        }

        String backupNewPath = backupPath;
        File zipFile = new File(backupNewPath + ".zip");
        int number = 1;
        while (zipFile.exists()) {
            backupNewPath = backupPath + "-" + number;
            zipFile = new File(backupNewPath + ".zip");
            number++;
        }
        File backupFolder = new File(backupNewPath);
        while (backupFolder.exists()) {
            backupNewPath = backupPath + "-" + number;
            backupFolder = new File(backupNewPath);
            number++;
        }
        return backupNewPath;
    }

    private boolean saveLogs(File backupFile) {
        ConfigHandler.getLogger().createLog("log", "", "");
        DateFormat dateFormat = new SimpleDateFormat("YYYY/MM/dd HH:mm");
        String date = dateFormat.format(new Date());
        long expiredDay = ConfigHandler.getConfigPath().getCleanExpiryDay();
        boolean autoClean = ConfigHandler.getConfigPath().isCleanAutoEnable();
        boolean toZip = ConfigHandler.getConfigPath().isBackupToZip();
        StringBuilder sb = new StringBuilder();
        for (String value : expiredMap.rowKeySet()) {
            sb.append(value);
        }
        String controlList = sb.toString();
        ConfigHandler.getLogger().addLog("log", "---- PlayerdataPlus Clean Log ----", false);
        ConfigHandler.getLogger().addLog("log", "", false);
        ConfigHandler.getLogger().addLog("log", "Time: " + date, false);
        if (backupFile.exists()) {
            if (toZip) {
                ConfigHandler.getLogger().addLog("log", "Backup: " + backupFile.getPath() + ".zip", false);
            } else {
                ConfigHandler.getLogger().addLog("log", "Backup: " + backupFile.getPath(), false);
            }
        } else {
            ConfigHandler.getLogger().addLog("log", "Backup: false", false);
        }
        ConfigHandler.getLogger().addLog("log", "Control-List: " + controlList, false);
        ConfigHandler.getLogger().addLog("log", "Expiry-Days: " + expiredDay, false);
        ConfigHandler.getLogger().addLog("log", "Auto-Clean: " + autoClean, false);
        ConfigHandler.getLogger().addLog("log", "", false);
        ConfigHandler.getLogger().addLog("log", "---- Statistics ----", false);
        for (String title : expiredMap.rowKeySet()) {
            ConfigHandler.getLogger().addLog("log", title + ":" + cleanCustomStatus(title), false);
            for (String subtitle : expiredMap.rowMap().get(title).keySet()) {
                ConfigHandler.getLogger().addLog("log", "> " + subtitle + " - " + expiredMap.get(title, subtitle).size(), false);
            }
            ConfigHandler.getLogger().addLog("log", "", false);
        }
        ConfigHandler.getLogger().addLog("log", "---- Details ----", false);
        for (String title : expiredMap.rowKeySet()) {
            ConfigHandler.getLogger().addLog("log", title + ":", false);
            for (String subtitle : expiredMap.rowMap().get(title).keySet()) {
                for (String value : expiredMap.get(title, subtitle)) {
                    ConfigHandler.getLogger().addLog("log", " - " + value, false);
                }
            }
            ConfigHandler.getLogger().addLog("log", "", false);
        }
        /*
        if (!zipFiles(ConfigHandler.getLogger().getFile().getPath(), backupFile.getName())) {
            ServerHandler.sendConsoleMessage("&Log: &Compression the log &8\"&e" + backupFile.getName() + "&8\"  &c✘");
        }
        */
        return true;
    }

    private List<String> deleteFiles(String title, String subtitle, File dataPath, List<String> expiredList) {
        List<String> cleanedList = new ArrayList<>();
        String titleName;
        if (subtitle != null) {
            titleName = title + "\\" + subtitle;
        } else {
            titleName = title;
        }
        if (!ConfigHandler.getConfigPath().isBackupEnable() || !ConfigHandler.getConfigPath().isBackupEnable(title)) {
            // Backup is disabled - only delete the file.
            for (String fileName : expiredList) {
                File dataFile = new File(dataPath + "\\" + fileName);
                // Backup is disabled - only delete the file.
                try {
                    // Delete the file.
                    if (dataFile.delete()) {
                        cleanedList.add(fileName);
                    } else {
                        ServerHandler.sendConsoleMessage("&6Delete: &f" + titleName + " &8\"&f" + fileName + "&8\"  &c✘");
                    }
                } catch (Exception e) {
                    ServerHandler.sendDebugTrace(e);
                }
            }
            return cleanedList;
        }
        // Backup is enabled - backup and delete the original files.
        String backupTitlePath = backupPath + "\\" + title + "\\";
        String backupSubtitlePath = null;
        if (subtitle != null) {
            backupSubtitlePath = backupTitlePath + subtitle + "\\";
        }
        File titleFolder = new File(backupTitlePath);
        File subtitleFolder = null;
        if (subtitle != null) {
            subtitleFolder = new File(backupSubtitlePath);
        }
        // Create all parent, "Backup", and "time" folder like "C:\\Server\\Playerdata_Backup\\Backup\\2020-12-16".
        if (!backupFile.exists()) {
            try {
                Path pathToFile = Paths.get(backupFile.getPath());
                Files.createDirectories(pathToFile);
                if (!backupFile.exists()) {
                    ServerHandler.sendConsoleMessage("&6Backup: &fcreate folder &8\"" + backupFile.getName() + "&8\"  &c✘");
                    return cleanedList;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Create a title folder like "Playerdata".
        if (!titleFolder.exists()) {
            try {
                if (!titleFolder.mkdir()) {
                    ServerHandler.sendConsoleMessage("&6Backup: &fcreate folder &8\"&e" + titleFolder.getName() + "&8\"  &c✘");
                }
            } catch (Exception e) {
                ServerHandler.sendDebugTrace(e);
            }
        }
        // Create a subtitle folder like "world".
        if (subtitle != null) {
            if (!subtitleFolder.exists()) {
                try {
                    if (!subtitleFolder.mkdir()) {
                        ServerHandler.sendConsoleMessage("&6Backup: &fcreate folder &8\"&e" + subtitleFolder.getName() + "&8\"  &c✘");
                    }
                } catch (Exception e) {
                    ServerHandler.sendDebugTrace(e);
                }
            }
        }
        for (String fileName : expiredList) {
            File dataFile = new File(dataPath + "\\" + fileName);
            // Copy all file to the backup folder.
            try {
                if (subtitle != null) {
                    Files.copy(dataFile.toPath(), (new File(backupSubtitlePath + dataFile.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
                } else {
                    Files.copy(dataFile.toPath(), (new File(backupTitlePath + dataFile.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                // Delete the original file.
                if (dataFile.delete()) {
                    // Add the file name in cleaned list.
                    cleanedList.add(fileName);
                } else {
                    ServerHandler.sendConsoleMessage("&6Backup: &fdelete the files &8\"&e" + fileName + "&8\"  &c✘");
                }
            } catch (Exception e) {
                ServerHandler.sendDebugTrace(e);
            }
        }
        return cleanedList;
    }
}
