package tw.momocraft.coreplus.utils.file;

import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLUtils {

    static Connection PlayerdataPlus;
    static Connection HotkeyPlus;
    static Connection ServerPlus;
    static Connection MySQLPlayerDataBridge;
    static Connection MyCommand;

    public boolean connect(String pluginName, String prefix, String databaseType) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Failed to connect MySQL database: " + databaseType);
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Message: jdbc driver unavailable!");
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
            return false;
        }
        MySQLMap mySQLMap;
        try {
            switch (databaseType) {
                case "PlayerdataPlus":
                    mySQLMap = ConfigHandler.getConfigPath().getMySQLProp().get("PlayerdataPlus");
                    PlayerdataPlus = DriverManager.getConnection("jdbc:mysql://" +
                                    mySQLMap.getHostName() + ":" + mySQLMap.getPort() + "/" + mySQLMap.getDatabase(),
                            mySQLMap.getUsername(), mySQLMap.getPassword());
                    UtilsHandler.getMsg().sendConsoleMsg(prefix, "Succeed to connect MySQL database: " + databaseType);
                    break;
                case "HotkeyPlus":
                    mySQLMap = ConfigHandler.getConfigPath().getMySQLProp().get("PlayerdataPlus");
                    HotkeyPlus = DriverManager.getConnection("jdbc:mysql://" +
                                    mySQLMap.getHostName() + ":" + mySQLMap.getPort() + "/" + mySQLMap.getDatabase(),
                            mySQLMap.getUsername(), mySQLMap.getPassword());
                    UtilsHandler.getMsg().sendConsoleMsg(prefix, "Succeed to connect MySQL database: " + databaseType);
                    break;
                case "ServerPlus":
                    mySQLMap = ConfigHandler.getConfigPath().getMySQLProp().get("PlayerdataPlus");
                    ServerPlus = DriverManager.getConnection("jdbc:mysql://" +
                                    mySQLMap.getHostName() + ":" + mySQLMap.getPort() + "/" + mySQLMap.getDatabase(),
                            mySQLMap.getUsername(), mySQLMap.getPassword());
                    UtilsHandler.getMsg().sendConsoleMsg(prefix, "Succeed to connect MySQL database: " + databaseType);
                    break;
                case "MySQLPlayerDataBridge":
                    mySQLMap = ConfigHandler.getConfigPath().getMySQLProp().get("PlayerdataPlus");
                    MySQLPlayerDataBridge = DriverManager.getConnection("jdbc:mysql://" +
                                    mySQLMap.getHostName() + ":" + mySQLMap.getPort() + "/" + mySQLMap.getDatabase(),
                            mySQLMap.getUsername(), mySQLMap.getPassword());
                    UtilsHandler.getMsg().sendConsoleMsg(prefix, "Succeed to connect MySQL database: " + databaseType);

                    break;
                case "MyCommand":
                    mySQLMap = ConfigHandler.getConfigPath().getMySQLProp().get("PlayerdataPlus");
                    MyCommand = DriverManager.getConnection("jdbc:mysql://" +
                                    mySQLMap.getHostName() + ":" + mySQLMap.getPort() + "/" + mySQLMap.getDatabase(),
                            mySQLMap.getUsername(), mySQLMap.getPassword());
                    UtilsHandler.getMsg().sendConsoleMsg(prefix, "Succeed to connect MySQL database: " + databaseType);
                    break;
                default:
                    UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not connect MySQL database: " + databaseType);
                    UtilsHandler.getMsg().sendErrorMsg(pluginName, "Please update CorePlus.");
            }
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Failed to connect MySQL database: " + databaseType);
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
            return false;
        }
        return true;
    }

    public boolean isConnect(String pluginName, String databaseType) {
        try {
            switch (databaseType) {
                case "PlayerdataPlus":
                    if (PlayerdataPlus != null && !PlayerdataPlus.isClosed()) {
                        return true;
                    }
                    break;
                case "HotkeyPlus":
                    if (HotkeyPlus != null && !HotkeyPlus.isClosed()) {
                        return true;
                    }
                    break;
                case "ServerPlus":
                    if (ServerPlus != null && !ServerPlus.isClosed()) {
                        return true;
                    }
                    break;
                case "MySQLPlayerDataBridge":
                    if (MySQLPlayerDataBridge != null && !MySQLPlayerDataBridge.isClosed()) {
                        return true;
                    }
                    break;
                case "MyCommand":
                    if (MyCommand != null && !MyCommand.isClosed()) {
                        return true;
                    }
                    break;
                default:
                    return false;
            }
            return true;
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not disconnect MySQL database: " + databaseType);
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
            return false;
        }
    }

    public boolean disabledConnect(String pluginName, String prefix, String databaseType) {
        try {
            switch (databaseType) {
                case "PlayerdataPlus":
                    if (PlayerdataPlus != null && !PlayerdataPlus.isClosed()) {
                        PlayerdataPlus.close();
                        UtilsHandler.getMsg().sendConsoleMsg(prefix, "Succeed to disconnect MySQL database: " + databaseType);
                    }
                    break;
                case "HotkeyPlus":
                    if (HotkeyPlus != null && !HotkeyPlus.isClosed()) {
                        HotkeyPlus.close();
                        UtilsHandler.getMsg().sendConsoleMsg(prefix, "Succeed to disconnect MySQL database: " + databaseType);
                    }
                    break;
                case "ServerPlus":
                    if (ServerPlus != null && !ServerPlus.isClosed()) {
                        ServerPlus.close();
                        UtilsHandler.getMsg().sendConsoleMsg(prefix, "Succeed to disconnect MySQL database: " + databaseType);
                    }
                    break;
                case "MySQLPlayerDataBridge":
                    if (MySQLPlayerDataBridge != null && !MySQLPlayerDataBridge.isClosed()) {
                        MySQLPlayerDataBridge.close();
                        UtilsHandler.getMsg().sendConsoleMsg(prefix, "Succeed to disconnect MySQL database: " + databaseType);
                    }
                    break;
                case "ALL":
                    if (PlayerdataPlus != null && !PlayerdataPlus.isClosed()) {
                        PlayerdataPlus.close();
                        UtilsHandler.getMsg().sendConsoleMsg(prefix, "Succeed to disconnect MySQL database: PlayerdataPlus");
                    }
                    if (HotkeyPlus != null && !HotkeyPlus.isClosed()) {
                        HotkeyPlus.close();
                        UtilsHandler.getMsg().sendConsoleMsg(prefix, "Succeed to disconnect MySQL database: HotkeyPlus");
                    }
                    if (ServerPlus != null && !ServerPlus.isClosed()) {
                        ServerPlus.close();
                        UtilsHandler.getMsg().sendConsoleMsg(prefix, "Succeed to disconnect MySQL database: ServerPlus");
                    }
                    if (MySQLPlayerDataBridge != null && !MySQLPlayerDataBridge.isClosed()) {
                        MySQLPlayerDataBridge.close();
                        UtilsHandler.getMsg().sendConsoleMsg(prefix, "Succeed to disconnect MySQL database: MySQLPlayerDataBridge");
                    }
                    if (MyCommand != null && !MyCommand.isClosed()) {
                        MyCommand.close();
                        UtilsHandler.getMsg().sendConsoleMsg(prefix, "Succeed to disconnect MySQL database: MySQLPlayerDataBridge");
                    }
                    break;
                default:
                    UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not disconnect MySQL database: " + databaseType);
                    UtilsHandler.getMsg().sendErrorMsg(pluginName, "Please update CorePlus.");
                    return false;
            }
            return true;
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not disconnect MySQL database: " + databaseType);
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
            return false;
        }
    }

    private PreparedStatement getStatement(String pluginName, String databaseType, String sql) {
        try {
            switch (databaseType) {
                case "PlayerdataPlus":
                    return PlayerdataPlus.prepareStatement(sql);
                case "HotkeyPlus":
                    return HotkeyPlus.prepareStatement(sql);
                case "ServerPlus":
                    return ServerPlus.prepareStatement(sql);
                case "MyCommand":
                    return MyCommand.prepareStatement(sql);
                default:
                    UtilsHandler.getMsg().sendErrorMsg(pluginName, "Failed to get the value from MySQL database: " + databaseType);
                    UtilsHandler.getMsg().sendErrorMsg(pluginName, "Please check whether CorePlus is the latest version.");
                    return null;
            }
        } catch (SQLException ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Failed to get the value of MySQL database: " + databaseType);
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "sql = " + sql);
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
            return null;
        }
    }

    public void executeSQL(String pluginName, String databaseType, String sql) {
        try {
            getStatement(pluginName, databaseType, sql).executeUpdate();
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Failed to execute SQL statement of MySQL database: " + databaseType);
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "sql = " + sql);
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
        }
    }

    // CREATE TABLE tableName ("EMP_ID int(11) NOT NULL,"
    //                    + "NAME VARCHAR(255) NOT NULL,"
    //                    + "DOB DATE NOT NULL,"
    //                    + "EMAIL VARCHAR(45) NOT NULL,"
    //                    + "DEPT varchar(45) NOT NULL"
    //                    + )";
    public void createTables(String pluginName, String databaseType, String table, List<String> columns) {
        StringBuilder sqlBuilder = new StringBuilder("\"CREATE TABLE IF NOT EXISTS " + table + " (\"");
        for (String column : columns) {
            sqlBuilder.append(column).append(",");
        }
        String sql = sqlBuilder.substring(0, sqlBuilder.length() - 1) + ")\";";
        executeSQL(pluginName, databaseType, sql);
    }

    // ALTER TABLE tableName ADD ("EMP_ID int(11) NOT NULL,"
    //                    + "NAME VARCHAR(255) NOT NULL,"
    //                    + "DOB DATE NOT NULL,"
    //                    + "EMAIL VARCHAR(45) NOT NULL,"
    //                    + "DEPT varchar(45) NOT NULL"
    //                    + )";
    public void addColumns(String pluginName, String databaseType, String table, List<String> columns) {
        try {
            StringBuilder sqlBuilder = new StringBuilder("\"ALTER TABLE " + table + " ADD (\"");
            for (String column : columns) {
                sqlBuilder.append(column).append(",");
            }
            String sql = sqlBuilder.substring(0, sqlBuilder.length() - 1) + ")\";";
            executeSQL(pluginName, databaseType, sql);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Failed to add column of MySQL database: " + databaseType);
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
        }
    }

    public Map<String, Map<String, String>> getValues(String pluginName, String databaseType, String table, String key, List<String> variables) {
        StringBuilder sqlBuilder = new StringBuilder("\"SELECT ");
        for (String variable : variables) {
            sqlBuilder.append(variable).append(",");
        }
        String sql = sqlBuilder.substring(0, sqlBuilder.length() - 1) + " FROM " + table + "\"";
        try {
            ResultSet result = getStatement(pluginName, databaseType, sql).executeQuery();
            Map<String, Map<String, String>> keyMap = new HashMap<>();
            Map<String, String> columnMap = new HashMap<>();
            String keyValue;
            while (result.next()) {
                keyValue = result.getString(key);
                for (String variable : variables) {
                    columnMap.put(variable, result.getString(variable));
                }
                keyMap.put(keyValue, columnMap);
            }
            return keyMap;
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Failed to get the value of MySQL database: " + databaseType);
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "sql = " + sql);
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
            return null;
        }
    }

    public Map<String, String> getValueMap(String pluginName, String databaseType, String table, String keyColumn, String valueColumn) {
        // SELECT
        String sql = "SELECT " + keyColumn + ", " + valueColumn + " FROM " + table + "\"";
        try {
            Map<String, String> map = new HashMap<>();
            ResultSet result = getStatement(pluginName, databaseType, sql).executeQuery();
            while (result.next()) {
                map.put(result.getString(keyColumn), result.getString(valueColumn));
            }
            return map;
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Failed to get the value of MySQL database: " + databaseType);
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "sql = " + sql);
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
            return null;
        }
    }

    public Map<Object, Object> getValueMap(String pluginName, String databaseType, String table, String keyColumn, String valueColumn, String keyType, String valueType) {
        // SELECT column1, column2 FROM table
        String sql = "SELECT " + keyColumn + ", " + valueColumn + " FROM " + table + "\"";
        try {
            Map<Object, Object> map = new HashMap<>();
            ResultSet result = getStatement(pluginName, databaseType, sql).executeQuery();
            while (result.next()) {
                Object key;
                switch (keyType) {
                    case "int":
                        key = result.getInt(keyColumn);
                        break;
                    case "float":
                        key = result.getFloat(keyColumn);
                        break;
                    case "long":
                        key = result.getLong(keyColumn);
                        break;
                    case "double":
                        key = result.getDouble(keyColumn);
                        break;
                    case "boolean":
                        key = result.getBoolean(keyColumn);
                        break;
                    case "byte":
                        key = result.getByte(keyColumn);
                        break;
                    case "string":
                    default:
                        key = result.getString(keyColumn);
                        break;
                }
                Object value;
                switch (valueType) {
                    case "int":
                        value = result.getInt(valueColumn);
                        break;
                    case "float":
                        value = result.getFloat(valueColumn);
                        break;
                    case "long":
                        value = result.getLong(valueColumn);
                        break;
                    case "double":
                        value = result.getDouble(valueColumn);
                        break;
                    case "boolean":
                        value = result.getBoolean(valueColumn);
                        break;
                    case "byte":
                        value = result.getByte(valueColumn);
                        break;
                    case "string":
                    default:
                        value = result.getString(valueColumn);
                        break;
                }
                map.put(key, value);
            }
            return map;
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Failed to get the value of MySQL database: " + databaseType);
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "sql = " + sql);
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
            return null;
        }
    }

    public String getValue(String pluginName, String databaseType, String table, String column) {
        String sql = "\"SELECT " + column + " FROM " + table + "\"";
        try {
            // SELECT targetColumn FROM table
            ResultSet result = getStatement(pluginName, databaseType, sql).executeQuery();
            String value = null;
            while (result.next()) {
                value = result.getString(column);
            }
            return value;
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Failed to get the value of MySQL database: " + databaseType);
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "sql = " + sql);
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
            return null;
        }
    }

    public String getValueWhere(String pluginName, String databaseType, String table, String whereKey, String whereValue, String column) {
        String sql = "\"SELECT " + column + " FROM " + table + "WHERE " +
                whereKey + " = '" + whereValue + "'" +
                "\"";
        try {
            // SELECT targetColumn FROM table WHERE uuid = 'UUID'
            ResultSet result = getStatement(pluginName, databaseType, sql).executeQuery();
            String value = null;
            while (result.next()) {
                value = result.getString(column);
            }
            return value;
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Failed to get the value of MySQL database: " + databaseType);
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "sql = " + sql);
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
            return null;
        }
    }

    public void setValue(String pluginName, String databaseType, String table, String column, String columnValue) {
        /*
        String sql = "\"UPDATE " + table + " SET " +
                column + "='" + columnValue +
                "'\"";
         */
        String sql = "\"INSERT INTO " + table + " SET " +
                column + "='" + columnValue + "'" +
                " ON DUPLICATE KEY UPDATE " +
                column + "='" + columnValue + "'" +
                "\"";
        try {
            getStatement(pluginName, databaseType, sql).executeUpdate();
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Failed to set the value of MySQL database: " + databaseType);
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "sql = " + sql);
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
        }
    }

    public void setValueWhere(String pluginName, String databaseType, String table, String whereKey, String whereValue, String column, String columnValue) {
        String sql = "\"UPDATE " + table + " SET " +
                column + "='" + columnValue + "'" +
                " WHERE " +
                whereKey + " = '" + whereValue + "'" +
                "\"";
        try {
            getStatement(pluginName, databaseType, sql).executeUpdate();
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Failed to set the value of MySQL database: " + databaseType);
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "sql = " + sql);
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
        }
    }

    public ResultSet getResultSet(String pluginName, String databaseType, String sql) {
        try {
            return getStatement(pluginName, databaseType, sql).executeQuery();
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Failed to get the value from MySQL database: " + databaseType);
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "sql = " + sql);
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
            return null;
        }
    }

    public ResultSet getResultSet(String pluginName, String databaseType, String table, String column) {
        String sql = "\"SELECT " + column + " FROM " + table + "\"";
        try {
            return getStatement(pluginName, databaseType, sql).executeQuery();
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Failed to get the value from MySQL database: " + databaseType);
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "sql = " + sql);
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
            return null;
        }
    }
}
