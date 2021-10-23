package tw.momocraft.coreplus.utils.file;

import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;
import tw.momocraft.coreplus.utils.message.LogMap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class LogUtils {

    public Map<String, LogMap> getLogProp() {
        return ConfigHandler.getConfigPath().getLogProp();
    }

    public void add(String pluginName, String group, String message) {
        LogMap logMap = getLogProp().get(group);
        if (logMap == null) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not find the log group: " + group);
            return;
        }
        UtilsHandler.getFile().getData().create(pluginName, logMap.getFile(), logMap.isNewFile(), logMap.isZip());
        message = message + "\n";
        if (logMap.isTime()) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String date = dateFormat.format(new Date());
            message = "[" + date + "]: " + message;
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(logMap.getFile(), true));
            bw.append(message);
            bw.close();
        } catch (IOException ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while log message.");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getMsg().sendDebugTrace(true, ConfigHandler.getPluginName(), ex);
        }
    }
}
