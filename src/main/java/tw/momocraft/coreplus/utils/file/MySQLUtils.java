package tw.momocraft.coreplus.utils.file;

import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;
import tw.momocraft.coreplus.utils.file.maps.MySQLMap;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLUtils {

    public Map<String, MySQLMap> getMySQLProp() {
        return ConfigHandler.getConfigPath().getMySQLProp();
    }

    Map<String, Connection> dataMap = new HashMap<>();
    private final List<String> dataList = new ArrayList<>();

    public Map<String, Connection> getDataMap() {
        return dataMap;
    }

    public boolean isEnable() {
        return ConfigHandler.getConfigPath().isDataProp();
    }

    public void setup() {
        setupConfig();

        UtilsHandler.getMsg().sendFileLoadedMsg("MySQL", dataList);
    }

    public void setupConfig() {
        Map<String, MySQLMap> map = ConfigHandler.getConfigPath().getMySQLProp();
        for (Map.Entry<String, MySQLMap> entry : map.entrySet())
            connect(entry.getKey(), entry.getValue());
    }

    public boolean connect(String pluginName, MySQLMap mySQLMap) {
        String groupName = mySQLMap.getGroupName();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Failed to connect MySQL: " + groupName);
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "The jdbc driver is unavailable!");
            UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            return false;
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://" +
                            mySQLMap.getHostName() + ":" + mySQLMap.getPort() + "/" + mySQLMap.getDatabase(),
                    mySQLMap.getUsername(), mySQLMap.getPassword());
            dataMap.put(groupName, connection);
            dataList.add(groupName);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Failed to connect MySQL: " + groupName);
            UtilsHandler.getMsg().sendDebugTrace(groupName, ex);
            return false;
        }
        UtilsHandler.getMsg().sendConsoleMsg(pluginName, "Succeed to connect MySQL: " + groupName);
        return true;
    }

    public boolean isConnect(String pluginName, String database) {
        try {
            Connection connection = dataMap.get(database);
            return connection != null && connection.isClosed();
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Can not check the connect of MySQL: " + database);
            UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            return false;
        }
    }

    public boolean disabledConnect(String pluginName, String database) {
        try {
            Connection connection = dataMap.get(database);
            if (connection != null && connection.isClosed()) {
                connection.close();
                return true;
            }
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not disconnect MySQL: " + database);
            UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            return false;
        }
        return true;
    }

    private PreparedStatement getStatement(String pluginName, String database, String sql) {
        try {
            Connection connection = dataMap.get(database);
            if (connection != null && connection.isClosed()) {
                return connection.prepareStatement(sql);
            }
        } catch (SQLException ex) {
            sendGetErrorMsg(pluginName, database, sql, ex);
            return null;
        }
        return null;
    }

    public void executeSQL(String pluginName, String database, String sql) {
        try {
            getStatement(pluginName, database, sql).executeUpdate();
        } catch (Exception ex) {
            sendGetErrorMsg(pluginName, database, sql, ex);
        }
    }


    /**
     * Create a table with columns.
     * <p>
     * CREATE TABLE tableName ("EMP_ID int(11) NOT NULL,"
     * + "NAME VARCHAR(255) NOT NULL,"
     * + "DOB DATE NOT NULL,"
     * + "EMAIL VARCHAR(45) NOT NULL,"
     * + "DEPT varchar(45) NOT NULL"
     *
     * @param pluginName the sent plugin name.
     * @param database   the database name.
     * @param table      the name of the database.
     * @param columns    the target column.
     */
    public void createTables(String pluginName, String database, String table, List<String> columns) {
        StringBuilder sqlBuilder = new StringBuilder("\"CREATE TABLE IF NOT EXISTS " + table + " (");
        for (String column : columns) {
            sqlBuilder.append("'");
            sqlBuilder.append(column);
            sqlBuilder.append("'");
            sqlBuilder.append(",");
        }
        String sql = sqlBuilder.substring(0, sqlBuilder.length() - 1) + ")\";";
        executeSQL(pluginName, database, sql);
    }

    /**
     * Create a table with columns.
     * <p>
     * ALTER TABLE tableName ADD ("EMP_ID int(11) NOT NULL,"
     * + "NAME VARCHAR(255) NOT NULL,"
     * + "DOB DATE NOT NULL,"
     * + "EMAIL VARCHAR(45) NOT NULL,"
     * + "DEPT varchar(45) NOT NULL"
     * + )";
     *
     * @param pluginName the sent plugin name.
     * @param database   the database name.
     * @param table      the name of the database.
     * @param columns    the target column.
     */
    public void setColumns(String pluginName, String database, String table, List<String> columns) {
        StringBuilder sqlBuilder = new StringBuilder("\"ALTER TABLE " + table + " ADD (");
        for (String column : columns)
            sqlBuilder.append(column).append(",");
        String sql = sqlBuilder.substring(0, sqlBuilder.length() - 1) + ")\";";
        try {
            executeSQL(pluginName, database, sql);
        } catch (Exception ex) {
            sendGetErrorMsg(pluginName, database, sql, ex);
        }
    }

    /**
     * Add a column to a table.
     *
     * @param pluginName the sent plugin name.
     * @param database   the database name.
     * @param table      the name of the database.
     * @param column     the target column.
     */
    public void addColumn(String pluginName, String database, String table, String column) {
        String sql = "\"ALTER TABLE " + table + " ADD " + column + "\"";
        try {
            executeSQL(pluginName, database, sql);
        } catch (Exception ex) {
            sendGetErrorMsg(pluginName, database, sql, ex);
        }
    }

    /**
     * Remove a column from a table.
     *
     * @param pluginName the sent plugin name.
     * @param database   the database name.
     * @param table      the name of the database.
     * @param column     the target column.
     */
    public void removeColumn(String pluginName, String database, String table, String column) {
        String sql = "\"ALTER TABLE " + table + " DROP " + column + "\"";
        try {
            executeSQL(pluginName, database, sql);
        } catch (Exception ex) {
            sendGetErrorMsg(pluginName, database, sql, ex);
        }
    }

    /**
     * Get all select rows with custom variables.
     *
     * @param pluginName the sent plugin name.
     * @param database   the database name.
     * @param table      the name of the database.
     * @param key        the key of the map.
     * @param variables  the variables to show.
     * @return all select rows with custom variables.
     */
    public Map<String, Map<String, String>> getValues(String pluginName, String database, String table, String key, List<String> variables) {
        StringBuilder sqlBuilder = new StringBuilder("\"SELECT ");
        for (String variable : variables)
            sqlBuilder.append(variable).append(",");
        String sql = sqlBuilder.substring(0, sqlBuilder.length() - 1) + " FROM " + table + "\"";
        try {
            ResultSet result = getStatement(pluginName, database, sql).executeQuery();
            Map<String, Map<String, String>> keyMap = new HashMap<>();
            Map<String, String> columnMap = new HashMap<>();
            String keyValue;
            while (result.next()) {
                keyValue = result.getString(key);
                for (String variable : variables)
                    columnMap.put(variable, result.getString(variable));
                keyMap.put(keyValue, columnMap);
            }
            return keyMap;
        } catch (Exception ex) {
            sendGetErrorMsg(pluginName, database, sql, ex);
            return null;
        }
    }

    /**
     * Get the select rows with custom key and value.
     * E.g. playername, value
     *
     * @param pluginName the sent plugin name.
     * @param database   the database name.
     * @param table      the name of the database.
     * @param keyColumn        the key of the map.
     * @param valueColumn  the variables to show.
     * @return the select rows with custom key and value.
     */
    public Map<String, String> getValueMap(String pluginName, String database, String table, String keyColumn, String valueColumn) {
        // SELECT
        String sql = "SELECT " + keyColumn + ", " + valueColumn + " FROM " + table + "\"";
        try {
            Map<String, String> map = new HashMap<>();
            ResultSet result = getStatement(pluginName, database, sql).executeQuery();
            while (result.next())
                map.put(result.getString(keyColumn), result.getString(valueColumn));
            return map;
        } catch (Exception ex) {
            sendGetErrorMsg(pluginName, database, sql, ex);
            return null;
        }
    }

    /**
     * Get all select rows with custom variables.
     *
     * @param pluginName the sent plugin name.
     * @param database   the database name.
     * @param table      the name of the database.
     * @param keyColumn        the key of the map.
     * @param valueColumn  the variables to show.
     * @return all select rows with custom variables.
     */
    public Map<Object, Object> getValueMap(String pluginName, String database, String table, String keyColumn, String valueColumn, String keyType, String valueType) {
        // SELECT column1, column2 FROM table
        String sql = "SELECT " + keyColumn + ", " + valueColumn + " FROM " + table + "\"";
        try {
            Map<Object, Object> map = new HashMap<>();
            ResultSet result = getStatement(pluginName, database, sql).executeQuery();
            while (result.next()) {
                Object key = switch (keyType) {
                    case "int" -> result.getInt(keyColumn);
                    case "float" -> result.getFloat(keyColumn);
                    case "long" -> result.getLong(keyColumn);
                    case "double" -> result.getDouble(keyColumn);
                    case "boolean" -> result.getBoolean(keyColumn);
                    case "byte" -> result.getByte(keyColumn);
                    default -> result.getString(keyColumn);
                };
                Object value = switch (valueType) {
                    case "int" -> result.getInt(valueColumn);
                    case "float" -> result.getFloat(valueColumn);
                    case "long" -> result.getLong(valueColumn);
                    case "double" -> result.getDouble(valueColumn);
                    case "boolean" -> result.getBoolean(valueColumn);
                    case "byte" -> result.getByte(valueColumn);
                    default -> result.getString(valueColumn);
                };
                map.put(key, value);
            }
            return map;
        } catch (Exception ex) {
            sendGetErrorMsg(pluginName, database, sql, ex);
            return null;
        }
    }

    public void setValue(String pluginName, String database, String table, String column, String columnValue) {
        // INSERT INTO table SET column = 'columnValue' ON DUPLICATE KEY UPDATE column = 'columnValue'
        String sql = "\"INSERT INTO " + table +
                " SET " + column + "='" + columnValue + "'" +
                " ON DUPLICATE KEY UPDATE " + column + "='" + columnValue + "'" +
                "\"";
        try {
            getStatement(pluginName, database, sql).executeUpdate();
        } catch (Exception ex) {
            sendGetErrorMsg(pluginName, database, sql, ex);
        }
    }

    public void setValueWhere(String pluginName, String database, String table, String whereKey, String whereValue, String column, String columnValue) {
        // UPDATE table SET column = 'columnValue' WHERE whereKey = 'whereValue'
        String sql = "\"UPDATE " + table +
                " SET " + column + "='" + columnValue + "'" +
                " WHERE " + whereKey + " = '" + whereValue + "'" +
                "\"";
        try {
            getStatement(pluginName, database, sql).executeUpdate();
        } catch (Exception ex) {
            sendGetErrorMsg(pluginName, database, sql, ex);
        }
    }

    public void removeValue(String pluginName, String database, String table, String whereKey, String whereValue) {
        // DELETE FROM table WHERE whereKey = 'whereValue'
        String sql = "\"DELETE FROM " + table +
                " WHERE " + whereKey + " = '" + whereValue + "'" +
                "\"";
        try {
            getStatement(pluginName, database, sql).executeUpdate();
        } catch (Exception ex) {
            sendGetErrorMsg(pluginName, database, sql, ex);
        }
    }

    public String getValue(String pluginName, String database, String table, String column) {
        // SELECT targetColumn FROM table
        String sql = "\"SELECT " + column + " FROM " + table + "\"";
        try {
            ResultSet result = getStatement(pluginName, database, sql).executeQuery();
            String value = null;
            while (result.next()) {
                value = result.getString(column);
            }
            return value;
        } catch (Exception ex) {
            sendGetErrorMsg(pluginName, database, sql, ex);
            return null;
        }
    }

    public String getValueWhere(String pluginName, String database, String table, String whereKey, String whereValue, String column) {
        // SELECT targetColumn FROM table WHERE uuid = 'UUID'
        String sql = "\"SELECT " + column + " FROM " + table +
                "WHERE " + whereKey + " = '" + whereValue + "'" +
                "\"";
        try {
            ResultSet result = getStatement(pluginName, database, sql).executeQuery();
            String value = null;
            while (result.next()) {
                value = result.getString(column);
            }
            return value;
        } catch (Exception ex) {
            sendGetErrorMsg(pluginName, database, sql, ex);
            return null;
        }
    }

    public List<String> getValueList(String pluginName, String database, String table, String column) {
        // SELECT targetColumn FROM table
        String sql = "\"SELECT " + column + " FROM " + table + "\"";
        try {
            ResultSet result = getStatement(pluginName, database, sql).executeQuery();
            List<String> value = new ArrayList<>();
            while (result.next()) {
                value.add(result.getString(column));
            }
            return value;
        } catch (Exception ex) {
            sendGetErrorMsg(pluginName, database, sql, ex);
            return null;
        }
    }

    public List<String> getValueListWhere(String pluginName, String database, String table, String whereKey, String whereValue, String column) {
        String sql = "\"SELECT " + column + " FROM " + table +
                "WHERE " + whereKey + " = '" + whereValue + "'" +
                "\"";
        try {
            // SELECT targetColumn FROM table WHERE uuid = 'UUID'
            ResultSet result = getStatement(pluginName, database, sql).executeQuery();
            List<String> value = new ArrayList<>();
            while (result.next()) {
                value.add(result.getString(column));
            }
            return value;
        } catch (Exception ex) {
            sendGetErrorMsg(pluginName, database, sql, ex);
            return null;
        }
    }

    public ResultSet getResultSet(String pluginName, String database, String sql) {
        try {
            return getStatement(pluginName, database, sql).executeQuery();
        } catch (Exception ex) {
            sendGetErrorMsg(pluginName, database, sql, ex);
            return null;
        }
    }

    public ResultSet getResultSet(String pluginName, String database, String table, String column) {
        String sql = "\"SELECT " + column + " FROM " + table + "\"";
        try {
            return getStatement(pluginName, database, sql).executeQuery();
        } catch (Exception ex) {
            sendGetErrorMsg(pluginName, database, sql, ex);
            return null;
        }
    }

    public ResultSet getResultSetWhere(String pluginName, String database, String table, String column, String whereKey, String whereValue) {
        String sql = "\"SELECT " + column + " FROM " + table +
                "WHERE " + whereKey + " = '" + whereValue + "'" +
                "\"";
        try {
            return getStatement(pluginName, database, sql).executeQuery();
        } catch (Exception ex) {
            sendGetErrorMsg(pluginName, database, sql, ex);
            return null;
        }
    }

    private void sendGetErrorMsg(String pluginName, String database, String sql, Exception ex) {
        UtilsHandler.getMsg().sendErrorMsg(pluginName, "Failed to execute sql for MySQL: " + database);
        UtilsHandler.getMsg().sendErrorMsg(pluginName, "SQL = " + sql);
        UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
    }
}
