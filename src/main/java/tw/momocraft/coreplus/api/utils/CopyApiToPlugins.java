package tw.momocraft.coreplus.api.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class CopyApiToPlugins {

    public static void main(String[] args) {
        String fileName = "CorePlus-1.0.0.jar";

        String barrierplus = "C:\\Users\\sayuri\\IdeaProjects\\BarrierPlus\\src\\main\\java\\tw\\momocraft\\barrierplus\\extra";
        String entityplus = "C:\\Users\\sayuri\\IdeaProjects\\EntityPlus\\src\\main\\java\\tw\\momocraft\\entityplus\\extra";
        String hotkeyplus = "C:\\Users\\sayuri\\IdeaProjects\\HotKeyPlus\\src\\main\\java\\tw\\momocraft\\hotkeyplus\\extra";
        String lotteryplus = "C:\\Users\\sayuri\\IdeaProjects\\LotteryPlus\\src\\main\\java\\tw\\momocraft\\lotteryplus\\extra";
        String serverplus = "C:\\Users\\sayuri\\IdeaProjects\\ServerPlus\\src\\main\\java\\tw\\momocraft\\serverplus\\extra";
        String slimechunkplus = "C:\\Users\\sayuri\\IdeaProjects\\SlimeChunkPlus\\src\\main\\java\\tw\\momocraft\\slimechunkplus\\extra";
        String server = "C:\\Users\\Sayuri\\Desktop\\Minecraft Server\\plugins";

        File source = new File("C:\\Users\\sayuri\\IdeaProjects\\CorePlus\\target", fileName);
        List<File> targetList = new ArrayList<>();
        targetList.add(new File(barrierplus, fileName));
        targetList.add(new File(entityplus, fileName));
        targetList.add(new File(hotkeyplus, fileName));
        targetList.add(new File(lotteryplus, fileName));
        targetList.add(new File(serverplus, fileName));
        targetList.add(new File(slimechunkplus, fileName));
        //targetList.add(new File(server, fileName));

        copyFiles(source, targetList);
    }

    private static void copyFiles(File source, List<File> targetList) {
        try {
            for (File target : targetList) {
                Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            System.out.print("Succeed.");
        } catch (Exception ex) {
            System.out.print("Failed");
            ex.printStackTrace();
        }
    }
}
