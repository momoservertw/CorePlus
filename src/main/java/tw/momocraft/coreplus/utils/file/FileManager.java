package tw.momocraft.coreplus.utils.file;

import org.bukkit.configuration.file.YamlConfiguration;
import tw.momocraft.coreplus.api.FileInterface;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.io.File;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class FileManager implements FileInterface {

    @Override
    public boolean zipFiles(String pluginName, File file, String path, String name) {
        return UtilsHandler.getZip().zipFiles(pluginName, file, path, name);
    }

    @Override
    public boolean loadYamlFile(String pluginName, String group, String filePath) {
        return UtilsHandler.getYaml().load(pluginName, group, filePath);
    }

    @Override
    public boolean loadJsonFile(String pluginName, String group, String filePath) {
        return UtilsHandler.getJson().load(pluginName, group, filePath);
    }

    @Override
    public boolean loadPropertiesFile(String pluginName, String group, String filePath) {
        return UtilsHandler.getProperties().load(pluginName, group, filePath);
    }

    @Override
    public String getYamlString(String pluginName, String group, String input) {
        return UtilsHandler.getYaml().getString(pluginName, group, input);
    }

    @Override
    public List<String> getYamlStringList(String pluginName, String group, String input) {
        return UtilsHandler.getYaml().getStringList(pluginName, group, input);
    }

    @Override
    public YamlConfiguration getYamlConfig(String group) {
        return UtilsHandler.getYaml().getConfig(group);
    }

    @Override
    public String getJsonValue(String pluginName, String group, String input) {
        return UtilsHandler.getJson().getValue(pluginName, group, input);
    }

    @Override
    public String getPropertiesValue(String pluginName, String group, String input) {
        return UtilsHandler.getProperties().getValue(pluginName, group, input);
    }

    @Override
    public boolean connectMySQL(String pluginName, String prefix, String databaseType) {
        return UtilsHandler.getMySQL().connect(pluginName, prefix, databaseType);
    }

    @Override
    public boolean isMySQLConnect(String pluginName, String databaseType) {
        return UtilsHandler.getMySQL().isConnect(pluginName, databaseType);
    }

    @Override
    public boolean disabledMySQLConnect(String pluginName, String prefix, String databaseType) {
        return UtilsHandler.getMySQL().disabledConnect(pluginName, prefix, databaseType);
    }

    @Override
    public void createMySQLTables(String pluginName, String databaseType, String table, List<String> columns) {
        UtilsHandler.getMySQL().createTables(pluginName, databaseType, table, columns);
    }

    @Override
    public void addMySQLColumns(String pluginName, String databaseType, String table, List<String> columns) {
        UtilsHandler.getMySQL().addColumns(pluginName, databaseType, table, columns);
    }

    @Override
    public void executeMySQLSQL(String pluginName, String databaseType, String sql) {
        UtilsHandler.getMySQL().executeSQL(pluginName, databaseType, sql);
    }

    @Override
    public Map<String, Map<String, String>> getMySQLValues(String pluginName, String databaseType, String table, String key, List<String> variables) {
        return UtilsHandler.getMySQL().getValues(pluginName, databaseType, table, key, variables);
    }

    @Override
    public Map<String, String> getMySQLValueMap(String pluginName, String databaseType, String table, String keyColumn, String valueColumn) {
        return UtilsHandler.getMySQL().getValueMap(pluginName, databaseType, table, keyColumn, valueColumn);
    }

    @Override
    public Map<Object, Object> getMySQLValueMap(String pluginName, String databaseType, String table, String keyColumn, String valueColumn, String keyType, String valueType) {
        return UtilsHandler.getMySQL().getValueMap(pluginName, databaseType, table, keyColumn, valueColumn, keyType, valueType);
    }

    @Override
    public String getMySQLValue(String pluginName, String databaseType, String table, String column) {
        return UtilsHandler.getMySQL().getValue(pluginName, databaseType, table, column);
    }

    @Override
    public String getMySQLValueWhere(String pluginName, String databaseType, String table, String whereKey, String whereValue, String column) {
        return UtilsHandler.getMySQL().getValueWhere(pluginName, databaseType, table, whereKey, whereValue, column);
    }

    @Override
    public void setMySQLValue(String pluginName, String databaseType, String table, String column, String columnValue) {
        UtilsHandler.getMySQL().setValue(pluginName, databaseType, table, column, columnValue);
    }

    @Override
    public void setMySQLValueWhere(String pluginName, String databaseType, String table, String whereKey, String whereValue, String column, String columnValue) {
        UtilsHandler.getMySQL().setValueWhere(pluginName, databaseType, table, whereKey, whereValue, column, columnValue);
    }

    @Override
    public ResultSet getMySQLResultSet(String pluginName, String databaseType, String sql) {
        return UtilsHandler.getMySQL().getResultSet(pluginName, databaseType, sql);
    }

    @Override
    public ResultSet getMySQLResultSet(String pluginName, String databaseType, String table, String column) {
        return UtilsHandler.getMySQL().getResultSet(pluginName, databaseType, table, column);
    }

}
