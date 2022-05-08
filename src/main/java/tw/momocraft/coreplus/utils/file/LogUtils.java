package tw.momocraft.coreplus.utils.file;

import org.bukkit.Bukkit;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;
import tw.momocraft.coreplus.utils.message.LogMap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogUtils {

    private final Map<String, LogMap> fileMap = new HashMap<>();
    private final List<String> dataList = new ArrayList<>();

    public boolean isEnable() {
        return ConfigHandler.getConfigPath().isDataLog();
    }

    public void setup() {
        setupConfig();


        UtilsHandler.getMsg().sendFileLoadedMsg("Logs", dataList);
    }

    public void setupConfig() {
        String logName;
        File file;
        LogMap logMap;

        // CorePlus latest.log
        logName = "coreplus";
        file = new File(Bukkit.getWorldContainer().getPath() + "/plugins/CorePlus/Logs/Message",
                "latest.log");
        logMap = new LogMap();
        logMap.setGroupName(logName);
        logMap.setFile(file);
        logMap.setTime(true);
        logMap.setNewFile(true);
        logMap.setZip(true);
        load(logName, logMap);

        // CorePlus groups.yml
        logName = "coreplus_groups";
        file = new File(Bukkit.getWorldContainer().getPath() + "/plugins/CorePlus/Logs",
                "groups.yml");
        logMap = new LogMap();
        logMap.setGroupName(logName);
        logMap.setFile(file);
        logMap.setTime(false);
        logMap.setNewFile(false);
        logMap.setZip(false);
        load(logName, logMap);

        // CorePlus custom.yml
        logName = "coreplus_custom";
        file = new File(Bukkit.getWorldContainer().getPath() + "/plugins/CorePlus/Logs",
                "custom.yml");
        logMap = new LogMap();
        logMap.setGroupName(logName);
        logMap.setFile(file);
        logMap.setTime(true);
        logMap.setNewFile(false);
        logMap.setZip(true);
        load(logName, logMap);

        // EntityPlus spawner.log
        logName = "entityplus_spawner";
        file = new File(Bukkit.getWorldContainer().getPath() + "/plugins/EntityPlus/Logs",
                "spawner.log");
        logMap = new LogMap();
        logMap.setGroupName(logName);
        logMap.setFile(file);
        logMap.setTime(true);
        logMap.setNewFile(false);
        logMap.setZip(true);
        load(logName, logMap);

        // LotteryPlus latest.log
        logName = "lotteryplus";
        file = new File(Bukkit.getWorldContainer().getPath() + "/plugins/LotteryPlus/Logs",
                "latest.log");
        logMap = new LogMap();
        logMap.setGroupName(logName);
        logMap.setFile(file);
        logMap.setTime(true);
        logMap.setNewFile(false);
        logMap.setZip(true);
        load(logName, logMap);

        // PlayerdataPlus latest.log
        logName = "playerdataplus";
        file = new File(Bukkit.getWorldContainer().getPath() + "/plugins/PlayerdataPlus/Logs",
                "latest.log");
        logMap = new LogMap();
        logMap.setGroupName(logName);
        logMap.setFile(file);
        logMap.setFile(file);
        logMap.setTime(true);
        logMap.setNewFile(false);
        logMap.setZip(true);
        load(logName, logMap);

        // Custom
        for (Map.Entry<String, LogMap> entry : ConfigHandler.getConfigPath().getLogProp().entrySet()) {
            load(entry.getKey(), entry.getValue());
        }
    }

    public void load(String name, LogMap logMap) {
        fileMap.put(name, logMap);
        dataList.add(name);
    }

    public void add(String pluginName, String groupName, String input) {
        LogMap logMap = fileMap.get(groupName);
        if (logMap == null) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not find the log group: " + groupName);
            return;
        }
        UtilsHandler.getFile().getData().create(pluginName, logMap.getFile(), logMap.isNewFile(), logMap.isZip());
        input = input + "\n";
        if (logMap.isTime()) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String date = dateFormat.format(new Date());
            input = "[" + date + "]: " + input;
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(logMap.getFile(), true));
            bw.append(input);
            bw.close();
        } catch (IOException ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Cannot set the value for Log file: " + groupName);
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Value = " + input);
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.getPluginName(), ex);
        }
    }
}
