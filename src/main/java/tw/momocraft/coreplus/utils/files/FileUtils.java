package tw.momocraft.coreplus.utils.files;

import tw.momocraft.coreplus.api.FileInterface;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.io.File;

public class FileUtils implements FileInterface {

    @Override
    public boolean zipFiles(String pluginName, File file, String path, String name) {
        return UtilsHandler.getZip().zipFiles(pluginName, file, path, name);
    }
}
