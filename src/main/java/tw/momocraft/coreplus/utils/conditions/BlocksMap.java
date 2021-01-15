package tw.momocraft.coreplus.utils.conditions;

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


    void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    void setBlockTypes(List<String> blockTypes) {
        this.blockTypes = blockTypes;
    }

    void setX(int x) {
        this.x = x;
    }

    void setY(int y) {
        this.y = y;
    }

    void setZ(int z) {
        this.z = z;
    }

    void setR(int r) {
        this.r = r;
    }

    void setH(int h) {
        this.h = h;
    }

    void setMode(String mode) {
        this.mode = mode;
    }

    void setIgnoreList(List<String> ignoreList) {
        this.ignoreList = ignoreList;
    }
}

