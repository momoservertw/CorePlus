package tw.momocraft.coreplus.utils;

import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.coreplus.api.MySQLInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.sql.*;

public class MySQLUtils implements MySQLInterface {
    final String hostname = ConfigHandler.getConfigPath().getMySQLHostname();
    final int port = ConfigHandler.getConfigPath().getMySQLPort();
    final String username = ConfigHandler.getConfigPath().getMySQLUsername();
    final String password = ConfigHandler.getConfigPath().getMySQLPassword();

    static Connection playerdataplus;
    static Connection hotkeyplus;

    @Override
    public boolean connectMySQL(String prefix, String pluginName) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("jdbc driver unavailable!");
            return false;
        }
        try {
            switch (pluginName) {
                case "playerdataplus":
                    playerdataplus = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/playerdataplus", username, password);
                    break;
                case "hotkeyplus":
                    hotkeyplus = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/hotkeyplus", username, password);
                    break;
                default:
                    CorePlusAPI.getLangManager().sendErrorMsg(prefix, "Failed to connect the MySQL.");
                    UtilsHandler.getLang().sendErrorMsg(prefix, "Please check whether CorePlus is the latest version.");
                    return false;
            }
            CorePlusAPI.getLangManager().sendConsoleMsg(prefix, "Succeed to connect the MySQL.");
            return true;
        } catch (SQLException e) {
            CorePlusAPI.getLangManager().sendErrorMsg(prefix, "Failed to connect the MySQL.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean disabledConnect(String prefix, String pluginName) {
        try {
            switch (pluginName) {
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
                case "ALL":
                    if (playerdataplus != null && !playerdataplus.isClosed()) {
                        playerdataplus.close();
                    }
                    if (hotkeyplus != null && !hotkeyplus.isClosed()) {
                        hotkeyplus.close();
                    }
                    break;
                default:
                    CorePlusAPI.getLangManager().sendErrorMsg(prefix, "Failed to disable the MySQL connect.");
                    UtilsHandler.getLang().sendErrorMsg(prefix, "Please check whether CorePlus is the latest version.");
                    return false;
            }
            CorePlusAPI.getLangManager().sendConsoleMsg(prefix, "Succeed to disable the MySQL connect.");
            return true;
        } catch (SQLException e) {
            CorePlusAPI.getLangManager().sendErrorMsg(prefix, "Failed to disable the MySQL connect.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean executeSQL(String prefix, String pluginName, String sql) {
        try {
            PreparedStatement stat;
            switch (pluginName) {
                case "playerdataplus":
                    stat = playerdataplus.prepareStatement(sql);
                    break;
                case "hotkeyplus":
                    stat = hotkeyplus.prepareStatement(sql);
                    break;
                default:
                    CorePlusAPI.getLangManager().sendErrorMsg(prefix, "Failed to save the MySQL data.");
                    UtilsHandler.getLang().sendErrorMsg(prefix, "Please check whether CorePlus is the latest version.");
                    return false;
            }
            stat.executeUpdate();
            return true;
        } catch (SQLException e) {
            CorePlusAPI.getLangManager().sendErrorMsg(prefix, "Failed to disable the MySQL connect.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getValue(String prefix, String pluginName, String valueName, String sql) throws SQLException {
        PreparedStatement stat;
        switch (pluginName) {
            case "playerdataplus":
                stat = playerdataplus.prepareStatement(sql);
                break;
            case "hotkeyplus":
                stat = hotkeyplus.prepareStatement(sql);
                break;
            default:
                CorePlusAPI.getLangManager().sendErrorMsg(prefix, "Failed to save the MySQL data.");
                UtilsHandler.getLang().sendErrorMsg(prefix, "Please check whether CorePlus is the latest version.");
                return null;
        }
        String value = null;
        ResultSet result = stat.executeQuery();
        while (result.next()) {
            value = result.getString(valueName);
        }
        return value;
    }

    @Override
    public ResultSet getResultSet(String prefix, String pluginName, String sql) throws SQLException {
        PreparedStatement stat;
        switch (pluginName) {
            case "playerdataplus":
                stat = playerdataplus.prepareStatement(sql);
                break;
            case "hotkeyplus":
                stat = hotkeyplus.prepareStatement(sql);
                break;
            default:
                CorePlusAPI.getLangManager().sendErrorMsg(prefix, "Failed to save the MySQL data.");
                UtilsHandler.getLang().sendErrorMsg(prefix, "Please check whether CorePlus is the latest version.");
                return null;
        }
        return stat.executeQuery();
    }
}
