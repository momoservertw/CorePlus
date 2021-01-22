package tw.momocraft.coreplus.api;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MySQLInterface {
    /**
     * To connect MySQL database.
     *
     * @param prefix   the prefix of the plugin.
     * @param database the name of database.
     */
    boolean connect(String prefix, String database);

    /**
     * To connect MySQL database.
     *
     * @param prefix   the prefix of the plugin.
     * @param database the name of database.
     */
    boolean disabledConnect(String prefix, String database);

    /**
     * @param database the name of database.
     * @return if the plugin data connected.
     */
    boolean isConnect(String database);

    /**
     * @param database the name of database.
     * @param table    the select table.
     * @param columns  the target columns.
     * @throws SQLException
     */
    void createTables(String database, String table, List<String> columns) throws SQLException;

    /**
     * @param database the name of database.
     * @param table    the select table.
     * @param columns  the target columns.
     * @throws SQLException
     */
    void addColumns(String database, String table, List<String> columns) throws SQLException;

    /**
     * Executing a sql.
     *
     * @param database the name of database.
     * @param sql      the input sql.
     * @throws SQLException
     */
    void executeSQL(String database, String sql) throws SQLException;

    /**
     * The get the values from MySQL database.
     *
     * @param database  the name of database.
     * @param table     the select table.
     * @param key       the main key of map.
     * @param variables the checking variables.
     * @return the values of the variables from MySQL database.
     * @throws SQLException
     */
    Map<String, Map<String, String>> getValues(String database, String table, String key, List<String> variables) throws SQLException;

    /**
     * To get the value from MySQL database.
     *
     * @param database the name of database.
     * @return the value of the variable from MySQLof.
     * @throws SQLException
     */
    String getValue(String database, String valueName, String sql) throws SQLException;

    /**
     * To get the MySQL ResultSet from MySQL database.
     *
     * @param database the name of database.
     * @return the MySQL ResultSet from MySQL.
     * @throws SQLException
     */
    ResultSet getResultSet(String database, String sql) throws SQLException;
}
