package tw.momocraft.coreplus.utils.customcommands;

public class LogMap {
    private String path;
    private String name;
    private boolean time;
    private boolean newFile;
    private boolean zip;

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(boolean time) {
        this.time = time;
    }

    public void setNewFile(boolean newFile) {
        this.newFile = newFile;
    }

    public void setZip(boolean zip) {
        this.zip = zip;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public boolean isTime() {
        return time;
    }

    public boolean isNewFile() {
        return newFile;
    }

    public boolean isZip() {
        return zip;
    }
}
