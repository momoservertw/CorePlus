package tw.momocraft.coreplus.utils.file;

import tw.momocraft.coreplus.api.FileInterface;
import tw.momocraft.coreplus.handlers.UtilsHandler;

public class FileManager implements FileInterface {

    @Override
    public YamlUtils getYaml() {
        return UtilsHandler.getYaml();
    }

    @Override
    public JsonUtils getJson() {
        return UtilsHandler.getJson();
    }

    @Override
    public PropertiesUtils getProperty() {
        return UtilsHandler.getProperty();
    }

    @Override
    public LogUtils getLog() {
        return UtilsHandler.getLog();
    }

    @Override
    public DataUtils getData() {
        return UtilsHandler.getData();
    }

    @Override
    public ZipperUtils getZip() {
        return UtilsHandler.getZip();
    }

    @Override
    public MySQLUtils getMySQL() {
        return UtilsHandler.getMySQL();
    }
}
