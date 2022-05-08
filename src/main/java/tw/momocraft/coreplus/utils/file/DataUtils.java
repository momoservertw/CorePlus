package tw.momocraft.coreplus.utils.file;

import org.bukkit.Bukkit;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DataUtils {

    public void backup(String pluginName, File file, String backupPath, String backupName, boolean timeFolder, boolean zip) {
        if (timeFolder) {
            LocalDateTime currentDate = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            backupPath += "\\" + currentDate.format(formatter);
        }
        backupName = getFileName(backupPath, backupName);
        createFolder(pluginName, backupPath);
        copy(pluginName, file, backupPath, backupName);
        delete(pluginName, file);
        if (zip) {
            File zipFile = new File(backupPath, backupName + ".zip");
            if (zipFile.exists())
                if (UtilsHandler.getFile().getZip().zipFiles(pluginName, file, null, null))
                    CorePlusAPI.getMsg().sendErrorMsg(pluginName, "Can not compress the file: " + file.getPath());
        }
    }

    public void createFolder(String pluginName, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                Files.createDirectories(Paths.get(file.getPath()));
                if (!file.exists())
                    CorePlusAPI.getMsg().sendErrorMsg(pluginName, "Can not create the file: " + file.getPath());
            } catch (Exception ex) {
                CorePlusAPI.getMsg().sendErrorMsg(pluginName, "Can not create the file: " + file.getPath());
                CorePlusAPI.getMsg().sendDebugTrace(pluginName, ex);
            }
        }
    }

    public void copy(String pluginName, File file, String newFilePath, String newFileName) {
        try {
            createFolder(pluginName, newFilePath);
            Files.copy(file.toPath(), Paths.get(newFilePath, newFileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            CorePlusAPI.getMsg().sendErrorMsg(pluginName, "Can not copy the file: " + file.getPath());
            CorePlusAPI.getMsg().sendDebugTrace(pluginName, ex);
        }
    }

    public void delete(String pluginName, File file) {
        try {
            if (!file.delete())
                CorePlusAPI.getMsg().sendErrorMsg(pluginName, "Can not remove the file: " + file.getPath());
        } catch (Exception ex) {
            CorePlusAPI.getMsg().sendErrorMsg(pluginName, "Can not remove the file: " + file.getPath());
            CorePlusAPI.getMsg().sendDebugTrace(pluginName, ex);
        }
    }

    public String getFileName(String backupPath, String backupName) {
        String[] nameSplit = backupName.split("\\.(?=[^.]+$)");
        int number = 0;
        File file = new File(backupPath, backupName);
        while (file.exists()) {
            number++;
            backupName = nameSplit[0] + "-" + number + "." + nameSplit[1];
            file = new File(backupPath, backupName);
        }
        return backupName;
    }

    public String getFilePath(String pluginName, String filePath) {
        if (filePath == null || filePath.equals(""))
            return Bukkit.getWorldContainer().getPath();
        try {
            switch (filePath.split("\\\\")[0]) {
                case "plugin":
                    return Bukkit.getWorldContainer().getPath() + "\\plugins\\" + pluginName;
                case "server":
                    return Bukkit.getWorldContainer().getPath() + "\\" +
                            filePath.replaceFirst(".*\\\\", "");
                default:
                    return filePath;
            }
        } catch (Exception ex) {
            return filePath;
        }
    }

    public File getWorldDataFolder(String type, String worldName) {
        if (worldName == null || worldName.equals(""))
            return null;
        File worldDir = new File(Bukkit.getWorldContainer().getPath() + "\\" + worldName);
        if (worldDir.exists()) {
            try {
                File[] dataDir;
                if (type.equals("region"))
                    dataDir = worldDir.listFiles(file -> file.isDirectory() && (file.getName().equals(type) || file.getName().equals("DIM1")));
                else
                    dataDir = worldDir.listFiles(file -> file.isDirectory() && (file.getName().equals(type)));
                return dataDir[0];
            } catch (Exception ex) {
                return null;
            }
        }
        return null;
    }

    public void create(String pluginName, File file, boolean newFile, boolean zip) {
        if (!file.exists()) {
            // Creating folder.
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                try {
                    if (!parentFile.mkdir())
                        UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                                "Can not create folder: " + parentFile.getName());
                } catch (Exception ex) {
                    UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                            "Can not create folder: " + parentFile.getName());
                    UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.getPluginName(), ex);
                }
            }
            // Creating file.
            try {
                if (!file.createNewFile())
                    UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                            "Can not create file: " + file.getName());
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                        "Can not create file: " + file.getName());
                UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.getPluginName(), ex);
            }
        } else {
            // Creating new file on new date.
            if (!newFile)
                return;
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(file.lastModified()));
            // Checking the modified time.
            if (currentDate.equals(modifiedDate)) {
                String logPath = file.getParentFile().getPath() + "\\" + modifiedDate;
                // Creating old file.
                String fileExtension = file.getName().substring(file.getName().lastIndexOf("."));
                File renameFile = new File(logPath + fileExtension);
                String logName;
                int number = 1;
                while (renameFile.exists()) {
                    logName = logPath + "-" + number;
                    renameFile = new File(logName + fileExtension);
                    number++;
                }
                // Renaming the old file.
                try {
                    if (!file.renameTo(renameFile))
                        UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                                "&fCan not rename the file: " + renameFile.getName());
                } catch (Exception ex) {
                    UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                            "&fCan not rename the file: " + renameFile.getName());
                    UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.getPluginName(), ex);
                }
                // Compressing the file.
                if (zip) {
                    try {
                        if (UtilsHandler.getFile().getZip().zipFiles(pluginName, file, null, null))
                            UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                                    "&fCan not compress the file: " + renameFile.getName());
                    } catch (Exception ex) {
                        UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                                "&fCan not compress the file: " + renameFile.getName());
                        UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.getPluginName(), ex);
                    }
                }
                // Create a new the file.
                try {
                    if (!file.createNewFile())
                        UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                                "&fCan not create the new file: " + file.getName());
                } catch (Exception ex) {
                    UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                            "&fCan not create the new file: " + file.getName());
                    UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.getPluginName(), ex);
                }
            }
        }
    }
}
