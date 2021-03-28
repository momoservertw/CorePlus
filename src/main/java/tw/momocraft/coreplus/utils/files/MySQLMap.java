package tw.momocraft.coreplus.utils.files;

import java.util.List;
import java.util.Map;

public class MySQLMap {
    private String groupName;

    private String hostName;
    private String port;
    private String username;
    private String password;
    private String database;
    private Map<String, String> Tables;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Map<String, String> getTables() {
        return Tables;
    }

    public void setTables(Map<String, String> tables) {
        Tables = tables;
    }
}
