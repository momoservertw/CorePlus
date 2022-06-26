package tw.momocraft.coreplus.utils.file.maps;

import java.io.File;

public class LogMap {

    private String groupName;
    private File file;
    private boolean time;
    private boolean newDateFile;
    private boolean newNumberFile;
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

    public boolean isNewDateFile() {
        return newDateFile;
    }

    public void setNewDateFile(boolean newDateFile) {
        this.newDateFile = newDateFile;
    }

    public boolean isNewNumberFile() {
        return newNumberFile;
    }

    public void setNewNumberFile(boolean newNumberFile) {
        this.newNumberFile = newNumberFile;
    }

    public boolean isZip() {
        return zip;
    }

    public void setZip(boolean zip) {
        this.zip = zip;
    }
}
