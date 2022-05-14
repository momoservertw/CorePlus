package tw.momocraft.coreplus.utils.message;

import java.util.HashMap;
import java.util.Map;

public class TranslateMap {

    private Map<String, Object> objectMap = new HashMap<>();
    private String lang;

    public Map<String, Object> getObjectMap() {
        return objectMap;
    }

    public void setObjectMap(Map<String, Object> objectMap) {
        this.objectMap = objectMap;
    }

    public void putObjectMap(String prefix, Object object) {
        objectMap.put(prefix, object);
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
