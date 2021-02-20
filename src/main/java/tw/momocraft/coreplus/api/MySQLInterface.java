package tw.momocraft.coreplus.api;


import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface MySQLInterface {
    /**
     * To connect MySQL database.
     *
     * @param pluginName   the sending plugin name.
     * @param prefix       the prefix of the plugin.
     * @param databaseType the name of database.
     */
    boolean connect(String pluginName, String prefix, String databaseType);

    /**
     * To connect MySQL database.
     *
     * @param pluginName   the sending plugin name.
     * @param prefix       the prefix of the plugin.
     * @param databaseType the name of database.
     */
    boolean disabledConnect(String pluginName, String prefix, String databaseType);

    /**
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @return if the plugin data connected.
     */
    boolean isConnect(String pluginName, String databaseType);

    /**
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @param table        the select table.
     * @param columns      the target columns.
     */
    void createTables(String pluginName, String databaseType, String table, List<String> columns);

    /**
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @param table        the select table.
     * @param columns      the target columns.
     */
    void addColumns(String pluginName, String databaseType, String table, List<String> columns);

    /**
     * Executing a sql.
     *
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @param sql          the input sql.
     */
    void executeSQL(String pluginName, String databaseType, String sql);

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
    Map<String, Map<String, String>> getValues(String pluginName, String databaseType, String table, String key, List<String> variables);


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
    Map<String, String> getValueMap(String pluginName, String databaseType, String table, String keyColumn, String valueColumn);

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
    Map<Object, Object> getValueMap(String pluginName, String databaseType, String table, String keyColumn, String valueColumn, String keyType, String valueType);

    /**
     * To get the value from MySQL database.
     *
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @return the value of the variable from MySQLof.
     */
    String getValue(String pluginName, String databaseType, String valueName, String sql);

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
    String getValueWhere(String pluginName, String databaseType, String table, String whereKey, String whereValue, String column);

    /**
     * To set the value in MySQL database.
     *
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @param table        the table name in database.
     * @param column       the target variable.
     * @param columnValue  the target variable's value.
     */
    void setValue(String pluginName, String databaseType, String table, String column, String columnValue);

    /**
     * To get the MySQL ResultSet from MySQL database.
     *
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @return the MySQL ResultSet from MySQL.
     */
    ResultSet getResultSet(String pluginName, String databaseType, String sql);

    /**
     * To get the MySQL ResultSet from MySQL database.
     *
     * @param pluginName   the sending plugin name.
     * @param databaseType the name of database.
     * @return the MySQL ResultSet from MySQL.
     */
    ResultSet getResultSet(String pluginName, String databaseType, String table, String column);
}
