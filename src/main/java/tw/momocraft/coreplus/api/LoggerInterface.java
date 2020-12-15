package tw.momocraft.coreplus.api;

public interface LoggerInterface {

    void addLog(String path, String name, String message, boolean time, boolean newFile, boolean zip);
}
