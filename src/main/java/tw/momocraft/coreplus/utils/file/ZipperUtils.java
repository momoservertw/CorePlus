
package tw.momocraft.coreplus.utils.file;

import org.jetbrains.annotations.Nullable;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipperUtils {

    public boolean zipFiles(String pluginName, File file, @Nullable String zipPath, @Nullable String zipName) {
        String outputName;
        if (zipPath == null || zipPath.equals(""))
            zipPath = file.getParentFile().getPath();
        if (zipName == null || zipName.equals(""))
            outputName = zipPath + ".zip";
        else
            outputName = file.getParentFile().getPath() + "\\" + zipName + ".zip";
        String SOURCE_FOLDER = zipPath;
        List<String> fileList = new ArrayList<>();
        generateFileList(new File(SOURCE_FOLDER), fileList, SOURCE_FOLDER);
        zipIt(pluginName, outputName, SOURCE_FOLDER, fileList);
        try (Stream<Path> walk = Files.walk(file.toPath())) {
            walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while compressing file.");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getMsg().sendDebugTrace(true, ConfigHandler.getPluginName(), ex);
            return false;
        }
        return true;
    }

    private void zipIt(String pluginName, String zipFile, String SOURCE_FOLDER, List<String> fileList) {
        byte[] buffer = new byte[1024];
        String source = new File(SOURCE_FOLDER).getName();
        FileOutputStream fos;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);
            FileInputStream in = null;
            for (String file : fileList) {
                ZipEntry ze = new ZipEntry(source + File.separator + file);
                zos.putNextEntry(ze);
                try {
                    in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
                    int len;
                    while ((len = in.read(buffer)) > 0)
                        zos.write(buffer, 0, len);
                } finally {
                    in.close();
                }
            }
            zos.closeEntry();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                zos.close();
            } catch (IOException ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while compressing file.");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "If this error keeps happening, please contact the plugin author.");
                UtilsHandler.getMsg().sendDebugTrace(true, ConfigHandler.getPluginName(), ex);
            }
        }
    }

    private void generateFileList(File node, List<String> fileList, String SOURCE_FOLDER) {
        // add file only
        if (node.isFile())
            fileList.add(generateZipEntry(node.toString(), SOURCE_FOLDER));
        if (node.isDirectory()) {
            String[] subNote = node.list();
            for (String filename : subNote) {
                generateFileList(new File(node, filename), fileList, SOURCE_FOLDER);
            }
        }
    }

    private String generateZipEntry(String file, String SOURCE_FOLDER) {
        return file.substring(SOURCE_FOLDER.length() + 1);
    }
}
