package tw.momocraft.coreplus.utils.message;

import java.io.File;

public class LogMap {

    private String groupName;
    private File file;
    private boolean time;
    private boolean newFile;
    private boolean zip;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isTime() {
        return time;
    }

    public void setTime(boolean time) {
        this.time = time;
    }

    public boolean isNewFile() {
        return newFile;
    }

    public void setNewFile(boolean newFile) {
        this.newFile = newFile;
    }

    public boolean isZip() {
        return zip;
    }

    public void setZip(boolean zip) {
        this.zip = zip;
    }
}
