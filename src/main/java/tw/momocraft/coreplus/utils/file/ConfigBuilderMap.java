package tw.momocraft.coreplus.utils.file;

import java.util.Set;

public class ConfigBuilderMap {

    private String groupName;
    private String type;
    private boolean rowLine;
    private String title;
    private String value;
    private String split;
    private Set<String> set;
    private Set<String> ignoreSet;


    public String getGroupName() {
        return groupName;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public boolean isRowLine() {
        return rowLine;
    }

    public String getSplit() {
        return split;
    }

    public String getValue() {
        return value;
    }

    public Set<String> getSet() {
        return set;
    }

    public Set<String> getIgnoreSet() {
        return ignoreSet;
    }


    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRowLine(boolean rowLine) {
        this.rowLine = rowLine;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setSplit(String split) {
        this.split = split;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    public void setIgnoreSet(Set<String> ignoreSet) {
        this.ignoreSet = ignoreSet;
    }
}
