package tw.momocraft.coreplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.api.ConfigInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;
import tw.momocraft.coreplus.utils.conditions.BlocksMap;
import tw.momocraft.coreplus.utils.conditions.LocationMap;
import tw.momocraft.coreplus.utils.customcommands.*;
import tw.momocraft.coreplus.utils.files.ConfigBuilderMap;

import java.io.File;
import java.util.*;

public class ConfigPath implements ConfigInterface {
    public ConfigPath() {
        setUp();
    }

    //  ============================================== //
    //         General Variables                       //
    //  ============================================== //
    private boolean pvp;

    private boolean mySQL;
    private String mySQLPlayerdataPlus;
    private String mySQLHotkeyPlus;
    private String mySQLServerPlus;
    private String mySQLMySQLPlayerDataBridge;
    private String mySQLMySQLPlayerDataBridgeExp;

    private boolean VanillaTrans;
    private String VanillaTransLocal;
    private boolean VanillaTransForce;
    private String menuItemJoin;
    private String menuType;
    private String menuName;
    private String menuSkullTextures;
    private final Map<String, Map<String, List<String>>> groupProp = new HashMap<>();
    private final Map<String, List<String>> cmdProp = new HashMap<>();
    private final Map<String, LogMap> logProp = new HashMap<>();
    private final Map<String, List<String>> conditionProp = new HashMap<>();
    private final Map<String, LocationMap> locProp = new HashMap<>();
    private final Map<String, BlocksMap> blockProp = new HashMap<>();
    private final Map<String, ActionBarMap> actionProp = new HashMap<>();
    private final Map<String, TitleMessageMap> titleProp = new HashMap<>();
    private final Map<String, ParticleMap> particleProp = new HashMap<>();
    private final Map<String, SoundMap> soundProp = new HashMap<>();

    //  ============================================== //
    //         ConfigBuilder Variables                 //
    //  ============================================== //
    private final Map<String, List<ConfigBuilderMap>> configBuilderGroupProp = new HashMap<>();
    private final Map<String, ConfigBuilderMap> configBuilderCustomProp = new HashMap<>();
    private String configBlockWorld;
    private int configBlockRadius;

    //  ============================================== //
    //         Setup all configuration                 //
    //  ============================================== //
    private void setUp() {
        setGeneral();
        setData();
        setGroups();
        setCommands();
        setLogs();
        setCondition();
        setLocation();
        setBlocks();
        setActionBars();
        setTitleMessages();
        setParticles();
        setSounds();

        setConfigBuilder();

        sendSetupMsg();
    }

    private void sendSetupMsg() {
        List<String> list = new ArrayList<>(CorePlus.getInstance().getDescription().getDepend());
        list.addAll(CorePlus.getInstance().getDescription().getSoftDepend());
        UtilsHandler.getLang().sendHookMsg(ConfigHandler.getPluginPrefix(), "plugins", list);

        /*
        list = Arrays.asList((
                "flag" + ","
                        + "flag" + ","
                        + "flag"
        ).split(","));
        CorePlusAPI.getLangManager().sendHookMsg(ConfigHandler.getPluginPrefix(), "Residence flags", list);

         */
    }

    //  ============================================== //
    //         General Setter                          //
    //  ============================================== //
    private void setGeneral() {
        pvp = Boolean.parseBoolean(UtilsHandler.getProperty().getValue(ConfigHandler.getPluginName(), "server.properties", "pvp"));

        VanillaTrans = ConfigHandler.getConfig("config.yml").getBoolean("General.Vanilla-Translation.Enable");
        VanillaTransForce = ConfigHandler.getConfig("config.yml").getBoolean("General.Vanilla-Translation.Force.Enable");
        VanillaTransLocal = ConfigHandler.getConfig("config.yml").getString("General.Vanilla-Translation.Force.Local");
        menuItemJoin = ConfigHandler.getConfig("config.yml").getString("General.Menu.ItemJoin");
        menuType = ConfigHandler.getConfig("config.yml").getString("General.Menu.Item.Type");
        menuName = ConfigHandler.getConfig("config.yml").getString("General.Menu.Item.Name");
        menuSkullTextures = ConfigHandler.getConfig("config.yml").getString("General.Menu.Skull-Textures");
    }

    //  ============================================== //
    //         Data.yml Setter                         //
    //  ============================================== //
    private void setData() {
        mySQL = ConfigHandler.getConfig("data.yml").getBoolean("MySQL.Enable");
        if (!mySQL) {
            return;
        }
        mySQLPlayerdataPlus = ConfigHandler.getConfig("data.yml").getString("MySQL.database.PlayerdataPlus");
        mySQLHotkeyPlus = ConfigHandler.getConfig("data.yml").getString("MySQL.database.HotkeyPlus");
        mySQLServerPlus = ConfigHandler.getConfig("data.yml").getString("MySQL.database.ServerPlus");
        mySQLMySQLPlayerDataBridge = ConfigHandler.getConfig("data.yml").getString("MySQL.database.MySQLPlayerDataBridge.Value");
        mySQLMySQLPlayerDataBridgeExp = ConfigHandler.getConfig("data.yml").getString("MySQL.database.MySQLPlayerDataBridge.ExpTable");
    }

    //  ============================================== //
    //         commands.yml Setter                     //
    //  ============================================== //
    private void setCommands() {
        ConfigurationSection config = ConfigHandler.getConfig("commands.yml").getConfigurationSection("Custom-Commands");
        if (config == null) {
            return;
        }
        for (String group : config.getKeys(false)) {
            cmdProp.put(group, ConfigHandler.getConfig("commands.yml").getStringList("Custom-Commands." + group));
        }
    }

    //  ============================================== //
    //         groups.yml Setter                         //
    //  ============================================== //
    private void setGroups() {
        ConfigurationSection customGroupConfig = ConfigHandler.getConfig("groups.yml").getConfigurationSection("");
        if (customGroupConfig == null) {
            return;
        }
        List<String> groupList;
        Map<String, List<String>> groupMap;
        ConfigurationSection groupConfig;
        for (String type : customGroupConfig.getKeys(false)) {
            if (type.equals("Config-Version")) {
                continue;
            }
            groupConfig = ConfigHandler.getConfig("groups.yml").getConfigurationSection(type);
            if (groupConfig == null) {
                return;
            }
            groupMap = new HashMap<>();
            for (String group : groupConfig.getKeys(false)) {
                groupList = ConfigHandler.getConfig("groups.yml").getStringList(type + "." + group);
                if (groupList.isEmpty()) {
                    continue;
                }
                groupMap.put(group, groupList);
            }
            groupProp.put(type, groupMap);
        }
    }

    //  ============================================== //
    //         logs.yml Setter                         //
    //  ============================================== //
    private void setLogs() {
        ConfigurationSection config = ConfigHandler.getConfig("logs.yml").getConfigurationSection("Logs");
        if (config == null) {
            return;
        }
        LogMap logMap;
        String path;
        String name;
        for (String group : config.getKeys(false)) {
            if (ConfigHandler.getConfig("logs.yml").getConfigurationSection("Logs." + group) == null) {
                continue;
            }
            logMap = new LogMap();
            path = ConfigHandler.getConfig("logs.yml").getString("Logs." + group + ".Path");
            name = ConfigHandler.getConfig("logs.yml").getString("Logs." + group + ".Name");
            if (path == null || name == null) {
                continue;
            }
            if (path.startsWith("plugins//")) {
                path = Bukkit.getServer().getWorldContainer().getPath() + "//" + path;
            } else if (path.startsWith("server//")) {
                path = path.replace("server//", "");
                path = Bukkit.getServer().getWorldContainer().getPath() + "//" + path;
            }
            logMap.setFile(new File(path, name));
            logMap.setTime(ConfigHandler.getConfig("logs.yml").getBoolean("Logs." + group + ".Time", true));
            logMap.setNewFile(ConfigHandler.getConfig("logs.yml").getBoolean("Logs." + group + ".New-File", false));
            logMap.setZip(ConfigHandler.getConfig("logs.yml").getBoolean("Logs." + group + ".Zip", false));
            logProp.put(group, logMap);
        }
    }

    //  ============================================== //
    //         condition.yml Setter                    //
    //  ============================================== //
    private void setCondition() {
        ConfigurationSection config = ConfigHandler.getConfig("condition.yml").getConfigurationSection("Conditions");
        if (config == null) {
            return;
        }
        for (String group : config.getKeys(false)) {
            conditionProp.put(group, ConfigHandler.getConfig("condition.yml").getStringList("Conditions." + group));
        }
    }

    //  ============================================== //
    //         location.yml Setter                     //
    //  ============================================== //
    private void setLocation() {
        ConfigurationSection locConfig = ConfigHandler.getConfig("location.yml").getConfigurationSection("Location");
        if (locConfig == null) {
            return;
        }
        LocationMap locMap;
        ConfigurationSection areaConfig;
        for (String group : locConfig.getKeys(false)) {
            if (ConfigHandler.getConfig("location.yml").getConfigurationSection("Location." + group) == null) {
                continue;
            }
            locMap = new LocationMap();
            areaConfig = ConfigHandler.getConfig("location.yml").getConfigurationSection("Location." + group + ".Area");
            locMap.setWorlds(ConfigHandler.getConfig("location.yml").getStringList("Location." + group + ".Worlds"));
            if (areaConfig != null) {
                for (String area : areaConfig.getKeys(false)) {
                    locMap.addCord(group, area, ConfigHandler.getConfig("location.yml").getString("Location." + group + ".Area." + area));
                }
            }
            locProp.put(group, locMap);
        }
    }

    //  ============================================== //
    //         Blocks.yml Setter                       //
    //  ============================================== //
    private void setBlocks() {
        ConfigurationSection blocksConfig = ConfigHandler.getConfig("blocks.yml").getConfigurationSection("Blocks");
        if (blocksConfig == null) {
            return;
        }
        BlocksMap blocksMap;
        for (String group : blocksConfig.getKeys(false)) {
            if (ConfigHandler.getConfig("blocks.yml").getConfigurationSection("Blocks." + group) == null) {
                return;
            }
            blocksMap = new BlocksMap();
            blocksMap.setGroupName(group);
            blocksMap.setBlockTypes(getTypeList(ConfigHandler.getPluginName(),
                    ConfigHandler.getConfig("blocks.yml").getStringList("Blocks." + group + ".Types"), "Materials"));
            blocksMap.setIgnoreList(ConfigHandler.getConfig("blocks.yml").getStringList("Blocks." + group + ".Ignore"));
            int r = ConfigHandler.getConfig("blocks.yml").getInt("Blocks." + group + ".Search.Values.R");
            blocksMap.setR(r);
            blocksMap.setX(ConfigHandler.getConfig("blocks.yml").getInt("Blocks." + group + ".Search.Values.X", r));
            int y = ConfigHandler.getConfig("blocks.yml").getInt("Blocks." + group + ".Search.Values.Y", r);
            blocksMap.setY(y);
            blocksMap.setZ(ConfigHandler.getConfig("blocks.yml").getInt("Blocks." + group + ".Search.Values.Z", r));
            blocksMap.setH(ConfigHandler.getConfig("blocks.yml").getInt("Blocks." + group + ".Search.Values.H"));
            blocksMap.setMode(ConfigHandler.getConfig("blocks.yml").getString("Blocks." + group + ".Search.Mode", "Cuboid"));
            blockProp.put(group, blocksMap);
        }
    }

    //  ============================================== //
    //         sounds.yml Setter                       //
    //  ============================================== //
    private void setSounds() {
        ConfigurationSection soundConfig = ConfigHandler.getConfig("sounds.yml").getConfigurationSection("Sounds");
        if (soundConfig != null) {
            SoundMap soundMap;
            for (String group : soundConfig.getKeys(false)) {
                if (ConfigHandler.getConfig("sounds.yml").getConfigurationSection("Sounds." + group) == null) {
                    continue;
                }
                soundMap = new SoundMap();
                try {
                    soundMap.setType(Sound.valueOf(ConfigHandler.getConfig("sounds.yml").getString("Sounds." + group + ".Type")));
                } catch (Exception ex) {
                    UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Unknown sound type: " + group);
                    continue;
                }
                soundMap.setVolume(ConfigHandler.getConfig("sounds.yml").getInt("Sounds." + group + ".Volume", 1));
                soundMap.setPitch(ConfigHandler.getConfig("sounds.yml").getInt("Sounds." + group + ".Pitch", 1));
                soundMap.setTimes(ConfigHandler.getConfig("sounds.yml").getInt("Sounds." + group + ".Run.Times", 1));
                soundMap.setInterval(ConfigHandler.getConfig("sounds.yml").getInt("Sounds." + group + ".Run.Interval", 0));
                soundMap.setVolume(ConfigHandler.getConfig("sounds.yml").getInt("Sounds." + group + ".Volume", 0));
                soundMap.setPitch(ConfigHandler.getConfig("sounds.yml").getInt("Sounds." + group + ".Pitch", 0));
                soundProp.put(group, soundMap);
            }
        }
    }

    //  ============================================== //
    //         particles.yml Setter                    //
    //  ============================================== //
    private void setParticles() {
        ConfigurationSection particleConfig = ConfigHandler.getConfig("particles.yml").getConfigurationSection("Particles");
        if (particleConfig != null) {
            ParticleMap particleMap;
            for (String group : particleConfig.getKeys(false)) {
                if (ConfigHandler.getConfig("particles.yml").getConfigurationSection("Particles." + group) == null) {
                    continue;
                }
                particleMap = new ParticleMap();
                try {
                    particleMap.setType(Particle.valueOf(ConfigHandler.getConfig("particles.yml").getString("Particles." + group + ".Type")));
                } catch (Exception ex) {
                    UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Unknown Particle type of particle: " + group + "\"");
                    UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "More information: https://github.com/momoservertw/CorePlus/wiki/Particle");
                    continue;
                }
                particleMap.setGroupName(group);
                particleMap.setAmount(ConfigHandler.getConfig("particles.yml").getInt("Particles." + group + ".Amount", 1));
                particleMap.setTimes(ConfigHandler.getConfig("particles.yml").getInt("Particles." + group + ".Run.Times", 1));
                particleMap.setInterval(ConfigHandler.getConfig("particles.yml").getInt("Particles." + group + ".Run.Interval", 20));
                particleMap.setOffsetX(ConfigHandler.getConfig("particles.yml").getDouble("Particles." + group + ".Offset.X", 0));
                particleMap.setOffsetY(ConfigHandler.getConfig("particles.yml").getDouble("Particles." + group + ".Offset.Y", 0));
                particleMap.setOffsetZ(ConfigHandler.getConfig("particles.yml").getDouble("Particles." + group + ".Offset.Z", 0));
                particleMap.setExtra(ConfigHandler.getConfig("particles.yml").getDouble("Particles." + group + ".Speed", 0));
                particleMap.setColorR(ConfigHandler.getConfig("particles.yml").getInt("Particles." + group + ".Color.R", 0));
                particleMap.setColorG(ConfigHandler.getConfig("particles.yml").getInt("Particles." + group + ".Color.G", 0));
                particleMap.setColorB(ConfigHandler.getConfig("particles.yml").getInt("Particles." + group + ".Color.B", 0));
                particleMap.setColorType(ConfigHandler.getConfig("particles.yml").getString("Particles." + group + ".Color.Type"));
                try {
                    particleMap.setMaterial(Material.getMaterial(
                            ConfigHandler.getConfig("particles.yml").getString("Particles." + group + ".Material", "STONE")));
                } catch (Exception ex) {
                    UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Unknown Material type of particle: " + group + "\"");
                    UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "More information: https://github.com/momoservertw/CorePlus/wiki/Particle");
                    continue;
                }
                particleProp.put(group, particleMap);
            }
        }
    }

    //  ============================================== //
    //         action_bars.yml Setter                  //
    //  ============================================== //
    private void setActionBars() {
        ConfigurationSection particleConfig = ConfigHandler.getConfig("action_bars.yml").getConfigurationSection("Action-Bars");
        if (particleConfig != null) {
            ActionBarMap actionBarMap;
            for (String group : particleConfig.getKeys(false)) {
                if (ConfigHandler.getConfig("action_bars.yml").getConfigurationSection("Action-Bars." + group) == null) {
                    continue;
                }
                actionBarMap = new ActionBarMap();
                actionBarMap.setGroupName(group);
                actionBarMap.setTimes(ConfigHandler.getConfig("action_bars.yml").getInt("Action-Bars." + group + ".Times", 1));
                actionBarMap.setInterval(ConfigHandler.getConfig("action_bars.yml").getInt("Action-Bars." + group + ".Interval", 20));
                actionProp.put(group, actionBarMap);
            }
        }
    }

    //  ============================================== //
    //         title_message.yml Setter                //
    //  ============================================== //
    private void setTitleMessages() {
        ConfigurationSection particleConfig = ConfigHandler.getConfig("title_messages.yml").getConfigurationSection("Title-Messages");
        if (particleConfig != null) {
            TitleMessageMap titleMessageMap;
            for (String group : particleConfig.getKeys(false)) {
                if (ConfigHandler.getConfig("title_messages.yml").getConfigurationSection("Title-Messages." + group) == null) {
                    continue;
                }
                titleMessageMap = new TitleMessageMap();
                titleMessageMap.setGroupName(group);
                titleMessageMap.setStay(ConfigHandler.getConfig("title_messages.yml").getInt("Title-Messages" + group + ".Stay", 70));
                titleMessageMap.setFadeIn(ConfigHandler.getConfig("title_messages.yml").getInt("Title-Messages" + group + ".FadeIn", 10));
                titleMessageMap.setFadeOut(ConfigHandler.getConfig("title_messages.yml").getInt("Title-Messages" + group + ".FadeOut", 20));
                titleProp.put(group, titleMessageMap);
            }
        }
    }

    //  ============================================== //
    //         ConfigBuilder Setter                    //
    //  ============================================== //
    private void setConfigBuilder() {
        ConfigurationSection configGroups = ConfigHandler.getConfig("config.yml").getConfigurationSection("Config-Builder.Groups");
        configBlockWorld = ConfigHandler.getConfig("config.yml").getString("Config-Builder.Settings.Material.Temporary-Block-Data.World", "world");
        configBlockRadius = ConfigHandler.getConfig("config.yml").getInt("Config-Builder.Settings.Material.Temporary-Block-Data.Radius", 30);
        if (configGroups != null) {
            List<ConfigBuilderMap> configBuilderMapList;
            ConfigBuilderMap configBuilderMap;
            ConfigurationSection groupConfig;
            for (String type : configGroups.getKeys(false)) {
                groupConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("Config-Builder.Groups." + type);
                if (groupConfig == null)
                    continue;
                configBuilderMapList = new ArrayList<>();
                for (String group : groupConfig.getKeys(false)) {
                    configBuilderMap = new ConfigBuilderMap();
                    configBuilderMap.setGroup(group);
                    if (type.equals("Entities")) {
                        configBuilderMap.setType("entity");
                    } else if (type.equals("Materials")) {
                        configBuilderMap.setType("material");
                    }
                    configBuilderMap.setTitle("  " + group + ":");
                    configBuilderMap.setRowLine(true);
                    configBuilderMap.setValue("    - %value%");
                    configBuilderMap.setSet(new HashSet<>(
                            ConfigHandler.getConfig("config.yml").getStringList("Config-Builder.Groups." + type + "." + group + ".List")));
                    configBuilderMap.setIgnoreSet(new HashSet<>(
                            ConfigHandler.getConfig("config.yml").getStringList("Config-Builder.Groups." + type + "." + group + ".Ignore-List")));
                    configBuilderMapList.add(configBuilderMap);
                }
                configBuilderGroupProp.put(type, configBuilderMapList);
            }
        }
        ConfigurationSection configCustom = ConfigHandler.getConfig("config.yml").getConfigurationSection("Config-Builder.Custom");
        if (configCustom != null) {
            ConfigBuilderMap configBuilderMap;
            ConfigurationSection groupConfig;
            String type;
            for (String group : configCustom.getKeys(false)) {
                groupConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("Config-Builder.Custom." + group);
                if (groupConfig == null)
                    continue;
                type = ConfigHandler.getConfig("config.yml").getString("Config-Builder.Custom." + group + ".Type");
                if (type == null)
                    continue;
                configBuilderMap = new ConfigBuilderMap();
                configBuilderMap.setGroup(group);
                configBuilderMap.setType(type);
                configBuilderMap.setTitle(
                        ConfigHandler.getConfig("config.yml").getString("Config-Builder.Custom." + group + ".Format.Title", group));
                configBuilderMap.setRowLine(
                        ConfigHandler.getConfig("config.yml").getBoolean("Config-Builder.Custom." + group + ".Format.Row-Line", true));
                configBuilderMap.setSplit(
                        ConfigHandler.getConfig("config.yml").getString("Config-Builder.Custom." + group + ".Format.Split", ", "));
                configBuilderMap.setValue(
                        ConfigHandler.getConfig("config.yml").getString("Config-Builder.Custom." + group + ".Format.Value",
                                "  - %value%"));
                configBuilderMap.setSet(new HashSet<>(
                        ConfigHandler.getConfig("config.yml").getStringList("Config-Builder.Custom." + group + ".List")));
                configBuilderMap.setIgnoreSet(new HashSet<>(
                        ConfigHandler.getConfig("config.yml").getStringList("Config-Builder.Custom." + group + ".Ignore-List")));
                configBuilderCustomProp.put(group, configBuilderMap);
            }
        }
    }

    //  ============================================== //
    //         General Getter                          //
    //  ============================================== //
    public boolean isPvp() {
        return pvp;
    }

    @Override
    public boolean isMySQL() {
        return mySQL;
    }

    public String getMySQLPlayerdataPlus() {
        return mySQLPlayerdataPlus;
    }

    public String getMySQLHotkeyPlus() {
        return mySQLHotkeyPlus;
    }

    public String getMySQLServerPlus() {
        return mySQLServerPlus;
    }

    public String getMySQLMySQLPlayerDataBridge() {
        return mySQLMySQLPlayerDataBridge;
    }

    public String getMySQLMySQLPlayerDataBridgeExp() {
        return mySQLMySQLPlayerDataBridgeExp;
    }

    public boolean isVanillaTrans() {
        return VanillaTrans;
    }

    public String getVanillaTransLocal() {
        return VanillaTransLocal;
    }

    public boolean isVanillaTransForce() {
        return VanillaTransForce;
    }

    public String getMenuItemJoin() {
        return menuItemJoin;
    }

    public String getMenuType() {
        return menuType;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getMenuSkullTextures() {
        return menuSkullTextures;
    }

    public Map<String, List<String>> getCmdProp() {
        return cmdProp;
    }

    public Map<String, LogMap> getLogProp() {
        return logProp;
    }

    public Map<String, SoundMap> getSoundProp() {
        return soundProp;
    }

    public Map<String, ParticleMap> getParticleProp() {
        return particleProp;
    }

    @Override
    public Map<String, List<String>> getConditionProp() {
        return conditionProp;
    }

    @Override
    public Map<String, LocationMap> getLocProp() {
        return locProp;
    }

    @Override
    public Map<String, BlocksMap> getBlocksProp() {
        return blockProp;
    }

    @Override
    public Map<String, Map<String, List<String>>> getGroupProp() {
        return groupProp;
    }

    //  ============================================== //
    //         ConfigBuilder Getter                    //
    //  ============================================== //
    public Map<String, List<ConfigBuilderMap>> getConfigBuilderGroupProp() {
        return configBuilderGroupProp;
    }

    public Map<String, ConfigBuilderMap> getConfigBuilderCustomProp() {
        return configBuilderCustomProp;
    }

    public int getConfigBlockRadius() {
        return configBlockRadius;
    }

    public String getConfigBlockWorld() {
        return configBlockWorld;
    }

    //  ============================================== //
    //         Others                                  //
    //  ============================================== //
    @Override
    public List<String> getTypeList(String pluginName, List<String> inputList, String inputType) {
        if (inputList == null || inputList.isEmpty() || inputType == null)
            return null;
        List<String> outputList = new ArrayList<>();
        List<String> customList;
        Map<String, List<String>> customGroup;
        for (String type : inputList) {
            try {
                // Add vanilla type.
                switch (inputType) {
                    case "Entities":
                        outputList.add(EntityType.valueOf(type).name());
                        continue;
                    case "Materials":
                        outputList.add(Material.valueOf(type).name());
                        continue;
                    case "Sound":
                        outputList.add(Sound.valueOf(type).name());
                        continue;
                    case "Particle":
                        outputList.add(Particle.valueOf(type).name());
                        continue;
                }
            } catch (Exception ignored) {
            }
            // Add custom group type.
            customGroup = groupProp.get(inputType);
            // Not find the input type of list.
            if (customGroup == null) {
                UtilsHandler.getLang().sendErrorMsg(pluginName,
                        "Unknown list type: \"" + inputType + ", " + type + "\"");
                UtilsHandler.getLang().sendErrorMsg(pluginName,
                        "Please check if CorePlus is updated to the latest version.");
                return null;
            }
            // Getting the list of that custom group.
            customList = customGroup.get(type);
            if (customList == null || customList.isEmpty())
                continue;
            // Add value to output list.
            for (String customType : customList) {
                try {
                    switch (inputType) {
                        case "Entities":
                            outputList.add(EntityType.valueOf(customType).name());
                            continue;
                        case "Materials":
                            outputList.add(Material.valueOf(customType).name());
                            continue;
                        case "Sound":
                            outputList.add(Sound.valueOf(customType).name());
                            continue;
                        case "Particle":
                            outputList.add(Particle.valueOf(customType).name());
                            continue;
                        case "MythicMobs":
                            if (UtilsHandler.getDepend().MythicMobsEnabled()) {
                                if (UtilsHandler.getEntity().isMythicMobName(customType)) {
                                    outputList.add(customType);
                                    continue;
                                }
                                UtilsHandler.getLang().sendErrorMsg(pluginName, "Can not find the " + inputType + ": " + customType);
                            }
                    }
                } catch (Exception ex) {
                    UtilsHandler.getLang().sendErrorMsg(pluginName, "Can not find the " + inputType + ": " + type);
                }
            }
        }
        return outputList;
    }

    @Override
    public FileConfiguration getConfig(String fileName) {
        return ConfigHandler.getConfig(fileName);
    }

    @Override
    public String getProp(String pluginName, String fileName, String input) {
        return UtilsHandler.getProperty().getValue(pluginName, fileName, input);
    }

    @Override
    public String getJson(String pluginName, String fileName, String input) {
        return UtilsHandler.getProperty().getValue(pluginName, fileName, input);
    }

    @Override
    public FileConfiguration getYaml(String fileName) {
        return UtilsHandler.getYaml().getConfig(fileName);
    }
}
