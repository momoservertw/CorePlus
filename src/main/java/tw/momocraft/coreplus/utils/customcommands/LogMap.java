package tw.momocraft.coreplus.utils.customcommands;

import java.io.File;

public class LogMap {
    private File file;
    private boolean time;
    private boolean newFile;
    private boolean zip;

    public void setFile(File file) {
        this.file = file;
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


    public File getFile() {
        return file;
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
