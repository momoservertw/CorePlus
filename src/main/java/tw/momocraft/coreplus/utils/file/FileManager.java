package tw.momocraft.coreplus.utils.file;

import tw.momocraft.coreplus.api.FileInterface;

public class FileManager implements FileInterface {

    private static LogUtils logUtils;
    private static JsonUtils jsonUtils;
    private static YamlUtils yamlUtils;
    private static PropertiesUtils propertiesUtils;
    private static MySQLUtils mySQLUtils;
    private static ZipperUtils zipperUtils;
    private static DataUtils dataUtils;

    public void setup() {
        mySQLUtils = new MySQLUtils();
        yamlUtils = new YamlUtils();
        jsonUtils = new JsonUtils();
        logUtils = new LogUtils();
        propertiesUtils = new PropertiesUtils();
        mySQLUtils.setup();
        yamlUtils.setup();
        jsonUtils.setup();
        logUtils.setup();
        propertiesUtils.setup();

        zipperUtils = new ZipperUtils();
        dataUtils = new DataUtils();
    }

    @Override
    public YamlUtils getYaml() {
        return yamlUtils;
    }

    @Override
    public JsonUtils getJson() {
        return jsonUtils;
    }

    @Override
    public PropertiesUtils getProperty() {
        return propertiesUtils;
    }

    @Override
    public LogUtils getLog() {
        return logUtils;
    }

    @Override
    public DataUtils getData() {
        return dataUtils;
    }

    @Override
    public ZipperUtils getZip() {
        return zipperUtils;
    }

    @Override
    public MySQLUtils getMySQL() {
        return mySQLUtils;
    }
}
