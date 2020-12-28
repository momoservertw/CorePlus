package tw.momocraft.coreplus.api;

import java.io.File;

public interface FileInterface {

    /**
     * Compressing a file.
     *
     * @param file the file property.
     * @param path the new file's path.
     * @param name the new file's name.
     * @return if the compressing succeed or not.
     */
    boolean zipFiles(File file, String path, String name);
}
