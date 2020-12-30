package tw.momocraft.coreplus.utils.conditions;

import java.util.List;

public class BlocksMap {

    private List<String> blockTypes;
    private int s;
    private int r;
    private int y;
    private int v;
    private List<String> ignoreList;

    public List<String> getBlockTypes() {
        return blockTypes;
    }

    int getS() {
        return s;
    }

    int getR() {
        return r;
    }

    int getY() {
        return y;
    }

    int getV() {
        return v;
    }

    List<String> getIgnoreList() {
        return ignoreList;
    }


    void setBlockTypes(List<String> blockTypes) {
        this.blockTypes = blockTypes;
    }

    void setS(int s) {
        this.s = s;
    }

    void setR(int r) {
        this.r = r;
    }

    void setY(int y) {
        this.y = y;
    }

    void setV(int v) {
        this.v = v;
    }

    void setIgnoreList(List<String> ignoreList) {
        this.ignoreList = ignoreList;
    }
}

