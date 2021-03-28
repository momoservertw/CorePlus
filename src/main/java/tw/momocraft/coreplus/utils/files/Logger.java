package tw.momocraft.coreplus.utils.files;

import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    public void addLog(String pluginName, File file, String message, boolean time, boolean newFile, boolean zip) {
        createLog(pluginName, file, newFile, zip);
        message = message + "\n";
        if (time) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String date = dateFormat.format(new Date());
            message = "[" + date + "]: " + message;
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.append(message);
            bw.close();
        } catch (IOException ex) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while compressing file.");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getLang().sendDebugTrace(true, ConfigHandler.getPluginName(), ex);
        }
    }

    public void createLog(String pluginName, File file, boolean newFile, boolean zip) {
        // Creating new log.
        if (!file.exists()) {
            // Creating folder.
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                try {
                    if (!parentFile.mkdir()) {
                        UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Log: &fcreate folder &8\"&e" + parentFile.getName() + "&8\"  &c✘");
                    }
                } catch (Exception ex) {
                    UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Log: &fcreate folder &8\"&e" + parentFile.getName() + "&8\"  &c✘");
                    UtilsHandler.getLang().sendDebugTrace(true, ConfigHandler.getPluginName(), ex);
                }
            }
            // Creating file.
            try {
                if (!file.createNewFile()) {
                    UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Log: &fcreate log &8\"&e" + file.getName() + ".log&8\"  &c✘");
                }
            } catch (Exception ex) {
                UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Log: &fcreate log &8\"&e" + file.getName() + ".log&8\"  &c✘");
                UtilsHandler.getLang().sendDebugTrace(true, ConfigHandler.getPluginName(), ex);
            }
        } else {
            // Creating new file on new date.
            if (newFile) {
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
                        if (!file.renameTo(renameFile)) {
                            UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Log: &frename log &8\"&e" + renameFile.getName() + "&8\"  &c✘");
                        }
                    } catch (Exception ex) {
                        UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Log: &frename log &8\"&e" + renameFile.getName() + "&8\"  &c✘");
                        UtilsHandler.getLang().sendDebugTrace(true, ConfigHandler.getPluginName(), ex);
                    }
                    // Compressing the file.
                    if (zip) {
                        try {
                            if (UtilsHandler.getFile().zipFiles(pluginName, file, null, null)) {
                                UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(),
                                        "Log: &fcompress log &8\"&e" + renameFile.getName() + ".zip &8\"  &c✘");
                            }
                        } catch (Exception ex) {
                            UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(),
                                    "Log: &fcompress log &8\"&e" + renameFile.getName() + ".zip &8\"  &c✘");
                            UtilsHandler.getLang().sendDebugTrace(true, ConfigHandler.getPluginName(), ex);
                        }
                    }
                    // Create a new the file.
                    try {
                        if (!file.createNewFile()) {
                            UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Log: &fcreate log &8\"&e" + file.getName() + ".log&8\"  &c✘");
                        }
                    } catch (Exception ex) {
                        UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Log: &fcreate log &8\"&e" + file.getName() + ".log&8\"  &c✘");
                        UtilsHandler.getLang().sendDebugTrace(true, ConfigHandler.getPluginName(), ex);
                    }
                }
            }
        }
    }
}
