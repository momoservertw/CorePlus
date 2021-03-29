package tw.momocraft.coreplus.api;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface FileInterface {

    /**
     * Compressing a file.
     *
     * @param file the file property.
     * @param path the new file's path.
     * @param name the new file's name.
     * @return if the compressing succeed or not.
     */
    boolean zipFiles(String pluginName, File file, String path, String name);

    /**
     * Loading a YAML file.
     *
     * @param pluginName the sending plugin name.
     * @param group      the new file's name.
     * @param filePath   the target file path.
     * @return if the load process succeed.
     */
    boolean loadYamlFile(String pluginName, String group, String filePath);

    /**
     * Loading a Json file.
     *
     * @param pluginName the sending plugin name.
     * @param group      the new file's name.
     * @param filePath   the target file path.
     * @return if the load process succeed.
     */
    boolean loadJsonFile(String pluginName, String group, String filePath);

    /**
     * Loading a Json file.
     *
     * @param pluginName the sending plugin name.
     * @param group      the new file's name.
     * @param filePath   the target file path.
     * @return if the load process succeed.
     */
    boolean loadPropertiesFile(String pluginName, String group, String filePath);

    /**
     * Getting all settings from the group file.
     *
     * @param group the file's group name.
     * @return all settings from the group file.
     */
    YamlConfiguration getYamlConfig(String group);

    /**
     * Getting the value from the group file.
     *
     * @param pluginName the sending plugin name.
     * @param group      the file's group name.
     * @param input      the searching option.
     * @return the value from the group file.
     */
    String getYamlString(String pluginName, String group, String input);

    /**
     * Getting the value list from the group file.
     *
     * @param pluginName the sending plugin name.
     * @param group      the file's group name.
     * @param input      the searching option.
     * @return the value list from the group file.
     */
    List<String> getYamlStringList(String pluginName, String group, String input);

    /**
     * Getting the value in a Json file.
     *
     * @param pluginName the sending plugin name.
     * @param group      the file's group name.
     * @param input      the searching option.
     * @return the value in a Json file.
     */
    String getJsonValue(String pluginName, String group, String input);

    /**
     * Getting the value in a Properties file.
     *
     * @param pluginName the sending plugin name.
     * @param group      the file's group name.
     * @param input      the searching option.
     * @return the value in a Properties file.
     */
    String getPropertiesValue(String pluginName, String group, String input);

    /**
     * To connect MySQL database.
     *
     * @param pluginName   the sending plugin name.
     * @param prefix       the prefix of the plugin.
     * @param databaseType the name of database.
     */
    boolean connectMySQL(String pluginName, String prefix, String databaseType);

    /**
     * To connect MySQL database.
     *
     * @param pluginName   the sending plugin name.
     * @param prefix       the prefix of the plugin.
     * @param databaseType the name of database.
     */
    boolean disabledMySQLConnect(String pluginName, String prefix, String databaseType);

    /**
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @return if the plugin data connected.
     */
    boolean isMySQLConnect(String pluginName, String databaseType);

    /**
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @param table        the select table.
     * @param columns      the target columns.
     */
    void createMySQLTables(String pluginName, String databaseType, String table, List<String> columns);

    /**
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @param table        the select table.
     * @param columns      the target columns.
     */
    void addMySQLColumns(String pluginName, String databaseType, String table, List<String> columns);

    /**
     * Executing a sql.
     *
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @param sql          the input sql.
     */
    void executeMySQLSQL(String pluginName, String databaseType, String sql);

    /**
     * The get the values from MySQL database.
     *
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @param table        the select table.
     * @param key          the main key of map.
     * @param variables    the checking variables.
     * @return the values of the variables from MySQL database.
     */
    Map<String, Map<String, String>> getMySQLValues(String pluginName, String databaseType, String table, String key, List<String> variables);


    /**
     * The get a map from MySQL database.
     *
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @param table        the select table.
     * @param keyColumn    the column of key.
     * @param valueColumn  the column of value.
     * @return the values of the variables from MySQL database.
     */
    Map<String, String> getMySQLValueMap(String pluginName, String databaseType, String table, String keyColumn, String valueColumn);

    /**
     * The get a map from MySQL database.
     *
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @param table        the select table.
     * @param keyColumn    the column of key.
     * @param valueColumn  the column of value.
     * @param keyType      the type of key.
     * @param valueType    the type of value.
     * @return the values of the variables from MySQL database.
     */
    Map<Object, Object> getMySQLValueMap(String pluginName, String databaseType, String table, String keyColumn, String valueColumn, String keyType, String valueType);

    /**
     * To get the value from MySQL database.
     *
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @return the value of the variable from MySQLof.
     */
    String getMySQLValue(String pluginName, String databaseType, String valueName, String sql);

    /**
     * To get the value from MySQL database.
     *
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @param table        the table name in database.
     * @param whereKey     the matching key.
     * @param whereValue   the matching value.
     * @param column       the target variable.
     * @return the value of the variable from MySQLof.
     */
    String getMySQLValueWhere(String pluginName, String databaseType, String table, String whereKey, String whereValue, String column);

    /**
     * To set the value in MySQL database.
     *
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @param table        the table name in database.
     * @param column       the target variable.
     * @param columnValue  the target variable's value.
     */
    void setMySQLValue(String pluginName, String databaseType, String table, String column, String columnValue);

    /**
     * To set the value in MySQL database where match the key and value.
     *
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @param table        the table name in database.
     * @param whereKey     the condition key.
     * @param whereValue   the condition value.
     * @param column       the target variable.
     * @param columnValue  the target variable's value.
     */
    void setMySQLValueWhere(String pluginName, String databaseType, String table, String whereKey, String whereValue, String column, String columnValue);

    /**
     * To get the MySQL ResultSet from MySQL database.
     *
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @return the MySQL ResultSet from MySQL.
     */
    ResultSet getMySQLResultSet(String pluginName, String databaseType, String sql);

    /**
     * To get the MySQL ResultSet from MySQL database.
     *
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @return the MySQL ResultSet from MySQL.
     */
    ResultSet getMySQLResultSet(String pluginName, String databaseType, String table, String column);
}
