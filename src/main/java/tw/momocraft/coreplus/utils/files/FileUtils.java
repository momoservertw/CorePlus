package tw.momocraft.coreplus.utils.files;

import tw.momocraft.coreplus.api.FileInterface;

import java.io.File;

public class FileUtils implements FileInterface {

    private static Zipper zipper;

    public FileUtils() {
        setZipper(new Zipper());
    }

    private static void setZipper(Zipper zip) {
        zipper = zip;
    }

    public static Zipper getZipper() {
        return zipper;
    }

    @Override
    public boolean zipFiles(File file, String path, String name) {
        return zipFiles(file, path, name);
    }
}
