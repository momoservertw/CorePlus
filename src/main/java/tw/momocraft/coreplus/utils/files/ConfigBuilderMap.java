package tw.momocraft.coreplus.utils.files;

import java.util.List;

public class ConfigBuilderMap {
    /*
      Type: material
      Row-line: true
      Title: "%title%"
      Format: "  - %value%"
      List:
        - Chest
      Ignore-List: []
     */
    private String type;
    private boolean rowLine;
    private String title;
    private String format;
    private String group;
    private List<String> list;
    private List<String> ignoreList;

    public void setRowLine(boolean rowLine) {
        this.rowLine = rowLine;
    }

    public String getTitle() {
        return title;
    }

    public String getFormat() {
        return format;
    }

    public String getType() {
        return type;
    }

    public String getGroup() {
        return group;
    }

    public List<String> getList() {
        return list;
    }

    public List<String> getIgnoreList() {
        return ignoreList;
    }


    public boolean isRowLine() {
        return rowLine;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setIgnoreList(List<String> ignoreList) {
        this.ignoreList = ignoreList;
    }
}
