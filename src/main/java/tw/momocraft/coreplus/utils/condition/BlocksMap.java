package tw.momocraft.coreplus.utils.condition;

import java.util.List;

public class BlocksMap {

    private String groupName;
    private List<String> blockTypes;
    private int x;
    private int y;
    private int z;
    private int r;
    private int h;
    private String mode;
    private List<String> ignoreList;

    String getGroupName() {
        return groupName;
    }

    List<String> getBlockTypes() {
        return blockTypes;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getZ() {
        return z;
    }

    int getR() {
        return r;
    }

    int getH() {
        return h;
    }

    String getMode() {
        return mode;
    }

    List<String> getIgnoreList() {
        return ignoreList;
    }


    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setBlockTypes(List<String> blockTypes) {
        this.blockTypes = blockTypes;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setIgnoreList(List<String> ignoreList) {
        this.ignoreList = ignoreList;
    }
}

