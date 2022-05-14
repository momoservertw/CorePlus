package tw.momocraft.coreplus.api.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class CopyToUpdateFolder {
    public static void main(String[] args) {
        String userPath = System.getProperty("user.home");
        String path = userPath + "\\IdeaProjects\\UploadPlugins\\";
        List<File> targetList = new ArrayList<>();
        String fileName;
        fileName = "CorePlus-1.0.0.jar";
        targetList.add(new File(userPath + "\\IdeaProjects\\CorePlus\\target", fileName));
        fileName = "BarrierPlus-1.2.0.jar";
        targetList.add(new File(userPath + "\\IdeaProjects\\BarrierPlus\\target", fileName));
        fileName = "EntityPlus-1.3.0.jar";
        targetList.add(new File(userPath + "\\IdeaProjects\\EntityPlus\\target", fileName));
        fileName = "HotKeyPlus-1.2.0.jar";
        targetList.add(new File(userPath + "\\IdeaProjects\\HotKeyPlus\\target", fileName));
        fileName = "LotteryPlus-1.1.0.jar";
        targetList.add(new File(userPath + "\\IdeaProjects\\LotteryPlus\\target", fileName));
        fileName = "ServerPlus-1.0.4.jar";
        targetList.add(new File(userPath + "\\IdeaProjects\\ServerPlus\\target", fileName));
        fileName = "SlimeChunkPlus-1.1.0.jar";
        targetList.add(new File(userPath + "\\IdeaProjects\\SlimeChunkPlus\\target", fileName));
        fileName = "RegionPlus-1.1.0.jar";
        targetList.add(new File(userPath + "\\IdeaProjects\\RegionPlus\\target", fileName));
        fileName = "ToolPlus-1.0.0.jar";
        targetList.add(new File(userPath + "\\IdeaProjects\\ToolPlus\\target", fileName));
        fileName = "PlayerdataPlus-1.2.0.jar";
        targetList.add(new File(userPath + "\\IdeaProjects\\PlayerdataPlus\\target", fileName));

        start(path, targetList);
    }

    private static void start(String path, List<File> targetList) {
        try {
            for (File file : targetList) {
                Files.copy(file.toPath(),
                        (new File(path + file.getName())).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            }
            System.out.print("Succeed.");
        } catch (Exception ex) {
            System.out.print("Failed");
            ex.printStackTrace();
        }
    }
}
