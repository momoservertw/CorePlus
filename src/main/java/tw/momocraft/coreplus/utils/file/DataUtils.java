package tw.momocraft.coreplus.utils.file;

import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtils {

    private void backup(String pluginName, File file, String backupPath, String backupName, boolean timeFolder, boolean zip) {
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
            if (zipFile.exists()) {
                if (UtilsHandler.getFile().getZip().zipFiles(pluginName, file, null, null))
                    CorePlusAPI.getMsg().sendErrorMsg(pluginName, "Can not compress the file: " + file.getPath());
            }
        }
    }

    private void createFolder(String pluginName, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                Files.createDirectories(Paths.get(file.getPath()));
                if (!file.exists())
                    CorePlusAPI.getMsg().sendErrorMsg(pluginName, "Can not create the file: " + file.getPath());
            } catch (Exception ex) {
                CorePlusAPI.getMsg().sendErrorMsg(pluginName, "Can not create the file: " + file.getPath());
                CorePlusAPI.getMsg().sendDebugTrace(true, pluginName, ex);
            }
        }
    }

    private void copy(String pluginName, File file, String newFilePath, String newFileName) {
        try {
            createFolder(pluginName, newFilePath);
            Files.copy(file.toPath(), Paths.get(newFilePath, newFileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            CorePlusAPI.getMsg().sendErrorMsg(pluginName, "Can not copy the file: " + file.getPath());
            CorePlusAPI.getMsg().sendDebugTrace(true, pluginName, ex);
        }
    }

    private void delete(String pluginName, File file) {
        try {
            if (!file.delete())
                CorePlusAPI.getMsg().sendErrorMsg(pluginName, "Can not remove the file: " + file.getPath());
        } catch (Exception ex) {
            CorePlusAPI.getMsg().sendErrorMsg(pluginName, "Can not remove the file: " + file.getPath());
            CorePlusAPI.getMsg().sendDebugTrace(true, pluginName, ex);
        }
    }

    private String getFileName(String backupPath, String backupName) {
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
}
