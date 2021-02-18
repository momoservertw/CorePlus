package tw.momocraft.coreplus.utils;

import tw.momocraft.coreplus.api.MySQLInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLUtils implements MySQLInterface {
    final String hostname = ConfigHandler.getConfigPath().getMySQLHostname();
    final int port = ConfigHandler.getConfigPath().getMySQLPort();
    final String username = ConfigHandler.getConfigPath().getMySQLUsername();
    final String password = ConfigHandler.getConfigPath().getMySQLPassword();

    static Connection playerdataplus;
    static Connection hotkeyplus;
    static Connection serverplus;

    @Override
    public boolean connect(String prefix, String database) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("jdbc driver unavailable!");
            return false;
        }
        try {
            switch (database) {
                case "playerdataplus":
                    playerdataplus = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/playerdataplus", username, password);
                    break;
                case "hotkeyplus":
                    hotkeyplus = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/hotkeyplus", username, password);
                    break;
                case "serverplus":
                    serverplus = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/serverplus", username, password);
                    break;
                default:
                    UtilsHandler.getLang().sendErrorMsg(database, "Failed to connect the MySQL.");
                    UtilsHandler.getLang().sendErrorMsg(database, "Please check whether CorePlus is the latest version.");
                    return false;
            }
            UtilsHandler.getLang().sendConsoleMsg(prefix, "Succeed to connect the MySQL.");
            return true;
        } catch (SQLException e) {
            UtilsHandler.getLang().sendErrorMsg(database, "Failed to connect the MySQL.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isConnect(String database) {
        try {
            switch (database) {
                case "playerdataplus":
                    if (playerdataplus != null && !playerdataplus.isClosed()) {
                        return true;
                    }
                    break;
                case "hotkeyplus":
                    if (hotkeyplus != null && !hotkeyplus.isClosed()) {
                        return true;
                    }
                    break;
                case "serverplus":
                    if (serverplus != null && !serverplus.isClosed()) {
                        return true;
                    }
                    break;
                default:
                    return false;
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean disabledConnect(String prefix, String database) {
        try {
            switch (database) {
                case "playerdataplus":
                    if (playerdataplus != null && !playerdataplus.isClosed()) {
                        playerdataplus.close();
                    }
                    break;
                case "hotkeyplus":
                    if (hotkeyplus != null && !hotkeyplus.isClosed()) {
                        hotkeyplus.close();
                    }
                    break;
                case "serverplus":
                    if (serverplus != null && !serverplus.isClosed()) {
                        serverplus.close();
                    }
                    break;
                case "ALL":
                    if (playerdataplus != null && !playerdataplus.isClosed()) {
                        playerdataplus.close();
                    }
                    if (hotkeyplus != null && !hotkeyplus.isClosed()) {
                        hotkeyplus.close();
                    }
                    if (serverplus != null && !serverplus.isClosed()) {
                        serverplus.close();
                    }
                    break;
                default:
                    UtilsHandler.getLang().sendErrorMsg(database, "Failed to disable the MySQL connect.");
                    UtilsHandler.getLang().sendErrorMsg(database, "Please check whether CorePlus is the latest version.");
                    return false;
            }
            UtilsHandler.getLang().sendConsoleMsg(prefix, "Succeed to disable the MySQL connect.");
            return true;
        } catch (SQLException e) {
            UtilsHandler.getLang().sendErrorMsg(database, "Failed to disable the MySQL connect.");
            e.printStackTrace();
            return false;
        }
    }


    // CREATE TABLE tableName ("EMP_ID int(11) NOT NULL,"
    //                    + "NAME VARCHAR(255) NOT NULL,"
    //                    + "DOB DATE NOT NULL,"
    //                    + "EMAIL VARCHAR(45) NOT NULL,"
    //                    + "DEPT varchar(45) NOT NULL"
    //                    + )";
    @Override
    public void createTables(String database, String table, List<String> columns) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder("\"CREATE TABLE IF NOT EXISTS " + table + " (\"");
        for (String column : columns) {
            sqlBuilder.append(column).append(",");
        }
        String sql = sqlBuilder.substring(0, sqlBuilder.length() - 1) + ")\";";
        executeSQL(database, sql);
    }

    // ALTER TABLE tableName ADD ("EMP_ID int(11) NOT NULL,"
    //                    + "NAME VARCHAR(255) NOT NULL,"
    //                    + "DOB DATE NOT NULL,"
    //                    + "EMAIL VARCHAR(45) NOT NULL,"
    //                    + "DEPT varchar(45) NOT NULL"
    //                    + )";
    @Override
    public void addColumns(String database, String table, List<String> columns) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder("\"ALTER TABLE " + table + " ADD (\"");
        for (String column : columns) {
            sqlBuilder.append(column).append(",");
        }
        String sql = sqlBuilder.substring(0, sqlBuilder.length() - 1) + ")\";";
        executeSQL(database, sql);
    }

    @Override
    public void executeSQL(String database, String sql) throws SQLException {
        PreparedStatement stat;
        switch (database) {
            case "playerdataplus":
                stat = playerdataplus.prepareStatement(sql);
                break;
            case "hotkeyplus":
                stat = hotkeyplus.prepareStatement(sql);
                break;
            case "serverplus":
                stat = serverplus.prepareStatement(sql);
                break;
            default:
                return;
        }
        stat.executeUpdate();
    }

    @Override
    public Map<String, Map<String, String>> getValues(String database, String table, String key, List<String> variables) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder("\"SELECT ");
        for (String variable : variables) {
            sqlBuilder.append(variable).append(",");
        }
        String sql = sqlBuilder.substring(0, sqlBuilder.length() - 1) + " FROM " + table + "\"";

        PreparedStatement stat;
        switch (database) {
            case "playerdataplus":
                stat = playerdataplus.prepareStatement(sql);
                break;
            case "hotkeyplus":
                stat = hotkeyplus.prepareStatement(sql);
                break;
            case "serverplus":
                stat = serverplus.prepareStatement(sql);
                break;
            default:
                return null;
        }
        ResultSet result = stat.executeQuery();
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
    }

    @Override
    public String getValue(String database, String table, String column) throws SQLException {
        String sql = "\"SELECT " + column + " FROM " + table + "\"";

        PreparedStatement stat;
        switch (database) {
            case "playerdataplus":
                stat = playerdataplus.prepareStatement(sql);
                break;
            case "hotkeyplus":
                stat = hotkeyplus.prepareStatement(sql);
                break;
            case "serverplus":
                stat = serverplus.prepareStatement(sql);
                break;
            default:
                return null;
        }
        ResultSet result = stat.executeQuery();
        String value = null;
        while (result.next()) {
            value = result.getString(column);
        }
        return value;
    }

    @Override
    public ResultSet getResultSet(String database, String sql) throws SQLException {
        PreparedStatement stat;
        switch (database) {
            case "playerdataplus":
                stat = playerdataplus.prepareStatement(sql);
                break;
            case "hotkeyplus":
                stat = hotkeyplus.prepareStatement(sql);
                break;
            case "serverplus":
                stat = serverplus.prepareStatement(sql);
                break;
            default:
                UtilsHandler.getLang().sendErrorMsg(database, "Failed to save the MySQL data.");
                UtilsHandler.getLang().sendErrorMsg(database, "Please check whether CorePlus is the latest version.");
                return null;
        }
        return stat.executeQuery();
    }
}
