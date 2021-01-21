package tw.momocraft.coreplus.api;


import java.sql.ResultSet;
import java.sql.SQLException;

public interface MySQLInterface {
    /**
     * To connect MySQL database.
     *
     * @param prefix     the prefix of the plugin.
     * @param pluginName the plugin name.
     */
    boolean connectMySQL(String prefix, String pluginName);

    /**
     * To connect MySQL database.
     *
     * @param prefix     the prefix of the plugin.
     * @param pluginName the plugin name.
     */
    boolean disabledConnect(String prefix, String pluginName);

    /**
     * To save the value to MySQL database.
     *
     * @param prefix     the prefix of the plugin.
     * @param pluginName the plugin name.
     * @param sql        the plugin name.
     */
    boolean executeSQL(String prefix, String pluginName, String sql);

    /**
     * To get the value from MySQL.
     *
     * @param prefix     the prefix of the plugin.
     * @param pluginName the plugin name.
     * @return the value from MySQL.
     */
    String getValue(String prefix, String pluginName, String valueName, String sql) throws SQLException;

    /**
     * To get the MySQL ResultSet from MySQL.
     *
     * @param prefix     the prefix of the plugin.
     * @param pluginName the plugin name.
     * @return the MySQL ResultSet from MySQL.
     */
    ResultSet getResultSet(String prefix, String pluginName, String sql) throws SQLException;
}
