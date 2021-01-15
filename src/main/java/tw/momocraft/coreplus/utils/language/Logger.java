package tw.momocraft.coreplus.utils.language;

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

    public void addLog(File file, String message, boolean time, boolean newFile, boolean zip) {
        createLog(file, newFile, zip);
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
        } catch (IOException e) {
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
        }
    }

    public void createLog(File file, boolean newFile, boolean zip) {
        // Creating new log.
        if (!file.exists()) {
            // Creating folder.
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                try {
                    if (!parentFile.mkdir()) {
                        UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPrefix(), "&6Log: &fcreate folder &8\"&e" + parentFile.getName() + "&8\"  &c✘");
                    }
                } catch (Exception e) {
                    UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPrefix(), "&6Log: &fcreate folder &8\"&e" + parentFile.getName() + "&8\"  &c✘");
                    UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
                }
            }
            // Creating file.
            try {
                if (!file.createNewFile()) {
                    UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPrefix(), "&6Log: &fcreate log &8\"&e" + file.getName() + ".log&8\"  &c✘");
                }
            } catch (Exception e) {
                UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPrefix(), "&6Log: &fcreate log &8\"&e" + file.getName() + ".log&8\"  &c✘");
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
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
                            UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPrefix(), "&6Log: &frename log &8\"&e" + renameFile.getName() + "&8\"  &c✘");
                        }
                    } catch (Exception e) {
                        UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPrefix(), "&6Log: &frename log &8\"&e" + renameFile.getName() + "&8\"  &c✘");
                        UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
                    }
                    // Compressing the file.
                    if (zip) {
                        try {
                            if (UtilsHandler.getFile().zipFiles(file, null, null)) {
                                UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPrefix(),
                                        "&6Log: &fcompress log &8\"&e" + renameFile.getName() + ".zip &8\"  &c✘");
                            }
                        } catch (Exception e) {
                            UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPrefix(),
                                    "&6Log: &fcompress log &8\"&e" + renameFile.getName() + ".zip &8\"  &c✘");
                            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
                        }
                    }
                    // Create a new the file.
                    try {
                        if (!file.createNewFile()) {
                            UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPrefix(), "&6Log: &fcreate log &8\"&e" + file.getName() + ".log&8\"  &c✘");
                        }
                    } catch (Exception e) {
                        UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPrefix(), "&6Log: &fcreate log &8\"&e" + file.getName() + ".log&8\"  &c✘");
                        UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
                    }
                }
            }
        }
    }
}
