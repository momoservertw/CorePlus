package tw.momocraft.coreplus.utils.conditions;

import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationMap {

    private List<String> worlds = new ArrayList<>();
    private final Map<String, String> cord = new HashMap<>();

    public void setWorlds(List<String> worlds) {
        this.worlds = worlds;
    }

    public void addCord(String group, String type, String value) {
        if (isCordFormat(type, value)) {
            cord.put(type, value);
        } else {
            UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Can not set the Location: " + group);
        }
    }

    public List<String> getWorlds() {
        return worlds;
    }

    Map<String, String> getCord() {
        return cord;
    }

    /**
     * @param type  the location value. Like x, y, z, !x...
     * @param value the value of the type.
     * @return check if the coordinate format is correct.
     */
    private static boolean isCordFormat(String type, String value) {
        String[] values = value.split("\\s+");
        int valueLen = values.length;
        int typeLen = type.length();
        if (valueLen == 1) {
            if (typeLen == 1) {
                if (type.matches("[XYZRS]")) {
                    return values[0].matches("-?[0-9]+");
                }
            } else if (typeLen == 2) {
                if (type.matches("[!][XYZRS]")) {
                    return values[0].matches("-?[0-9]+");
                } else if (type.matches("[XYZ]{1,2}")) {
                    return values[0].matches("-?[0-9]+");
                }
            }
        } else if (valueLen == 2) {
            if (typeLen == 1) {
                if (type.matches("[XYZ]")) {
                    if (values[0].length() == 1 && values[0].matches("[><=]") ||
                            values[0].length() == 2 && values[0].matches("[><=]{1,2}")) {
                        return values[1].matches("-?[0-9]+");
                    }
                }
            } else if (typeLen == 2) {
                if (type.matches("[!][XYZ]")) {
                    if (values[0].length() == 1 && values[0].matches("[><=]") || values[0].length() == 2 &&
                            values[0].matches("[><=]{1,2}")) {
                        return values[1].matches("-?[0-9]+");
                    }
                }
            }
        } else if (valueLen == 3) {
            if (typeLen == 1) {
                if (type.matches("[RS]")) {
                    return values[0].matches("-?[0-9]+") && values[1].matches("-?[0-9]+") &&
                            values[2].matches("-?[0-9]+");
                } else if (type.matches("[XYZ]")) {
                    if (values[0].matches("-?[0-9]+") && values[2].matches("-?[0-9]+")) {
                        return values[1].equals("~");
                    }
                }
            } else if (typeLen == 2) {
                if (type.matches("[!][RS]")) {
                    return values[0].matches("-?[0-9]+") && values[1].matches("-?[0-9]+") &&
                            values[2].matches("-?[0-9]+");
                } else if (type.matches("[XYZ]{1,2}")) {
                    if (values[0].matches("-?[0-9]+") && values[2].matches("-?[0-9]+")) {
                        return values[1].equals("~");
                    }
                } else if (type.matches("[!][XYZ]")) {
                    if (values[0].matches("-?[0-9]+") && values[2].matches("-?[0-9]+")) {
                        return values[1].equals("~");
                    }
                }
            }
        } else if (valueLen == 4) {
            if (typeLen == 1) {
                if (type.matches("[RS]")) {
                    return values[0].matches("-?[0-9]+") && values[1].matches("-?[0-9]+") &&
                            values[2].matches("-?[0-9]+") && values[3].matches("-?[0-9]+");
                }
            } else if (typeLen == 2) {
                if (type.matches("[!][RS]")) {
                    return values[0].matches("-?[0-9]+") && values[1].matches("-?[0-9]+") &&
                            values[2].matches("-?[0-9]+") && values[3].matches("-?[0-9]+");
                }
            }
        }
        return false;
    }
}
