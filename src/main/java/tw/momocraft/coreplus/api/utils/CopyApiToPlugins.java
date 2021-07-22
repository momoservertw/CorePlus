package tw.momocraft.coreplus.api.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class CopyApiToPlugins {

    public static void main(String[] args) {
        String fileName = "CorePlus-1.0.0.jar";

        String userPath = System.getProperty("user.home");
        String barrierplus = userPath + "\\IdeaProjects\\BarrierPlus\\src\\main\\java\\tw\\momocraft\\barrierplus\\extra";
        String entityplus = userPath + "\\IdeaProjects\\EntityPlus\\src\\main\\java\\tw\\momocraft\\entityplus\\extra";
        String hotkeyplus = userPath + "\\IdeaProjects\\HotKeyPlus\\src\\main\\java\\tw\\momocraft\\hotkeyplus\\extra";
        String lotteryplus = userPath + "\\IdeaProjects\\LotteryPlus\\src\\main\\java\\tw\\momocraft\\lotteryplus\\extra";
        String serverplus = userPath + "\\IdeaProjects\\ServerPlus\\src\\main\\java\\tw\\momocraft\\serverplus\\extra";
        String slimechunkplus = userPath + "\\IdeaProjects\\SlimeChunkPlus\\src\\main\\java\\tw\\momocraft\\slimechunkplus\\extra";
        String regionplus = userPath + "\\IdeaProjects\\RegionPlus\\src\\main\\java\\tw\\momocraft\\regionplus\\extra";
        String playerdataplus = userPath + "\\IdeaProjects\\PlayerdataPlus\\src\\main\\java\\tw\\momocraft\\playerdataplus\\extra";
        String toolplus = userPath + "\\IdeaProjects\\ToolPlus\\src\\main\\java\\tw\\momocraft\\toolplus\\extra";


        File source = new File(userPath + "\\IdeaProjects\\CorePlus\\target", fileName);
        List<File> targetList = new ArrayList<>();
        targetList.add(new File(entityplus, fileName));

        targetList.add(new File(barrierplus, fileName));
        targetList.add(new File(hotkeyplus, fileName));
        targetList.add(new File(lotteryplus, fileName));
        targetList.add(new File(serverplus, fileName));
        targetList.add(new File(slimechunkplus, fileName));
        targetList.add(new File(regionplus, fileName));
        targetList.add(new File(playerdataplus, fileName));
        targetList.add(new File(toolplus, fileName));

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
