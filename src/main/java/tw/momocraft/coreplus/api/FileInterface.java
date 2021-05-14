package tw.momocraft.coreplus.api;

import tw.momocraft.coreplus.utils.file.*;

public interface FileInterface {

    YamlUtils getYaml();

    JsonUtils getJson();

    PropertiesUtils getProperty();

    MySQLUtils getMySQL();

    DataUtils getData();

    LogUtils getLog();

    ZipperUtils getZip();
}
