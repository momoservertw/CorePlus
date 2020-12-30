package tw.momocraft.coreplus.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import tw.momocraft.coreplus.CorePlus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;


public class GsonUtils {

    private Map<String, JsonObject> langProp = new HashMap<>();

    public GsonUtils() {
        setUp();
    }

    private void setUp() {
        Gson gson = new Gson();
        File file = new File(CorePlus.getInstance().getDataFolder().getPath() + "\\Lang");
        String[] fileList = file.list();
        JsonObject json;
        for (String fileName : fileList) {
            if (!fileName.endsWith(".json")) {
                continue;
            }
            try {
                json = gson.fromJson(new FileReader(new File(file.getPath(), fileName)), JsonObject.class);
                langProp.put(fileName, json);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
