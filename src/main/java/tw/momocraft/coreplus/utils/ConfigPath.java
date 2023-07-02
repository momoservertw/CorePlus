package tw.momocraft.coreplus.utils;

import me.NoChance.PvPManager.PvPManager;
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
import tw.momocraft.coreplus.utils.condition.BlocksMap;
import tw.momocraft.coreplus.utils.condition.LocationMap;
import tw.momocraft.coreplus.utils.effect.ParticleMap;
import tw.momocraft.coreplus.utils.effect.SoundMap;
import tw.momocraft.coreplus.utils.file.maps.ConfigBuilderMap;
import tw.momocraft.coreplus.utils.file.maps.LogMap;
import tw.momocraft.coreplus.utils.file.maps.MySQLMap;
import tw.momocraft.coreplus.utils.message.ActionBarMap;
import tw.momocraft.coreplus.utils.message.TitleMsgMap;

import java.io.File;
import java.util.*;

public class ConfigPath implements ConfigInterface {

    //  ============================================== //
    //         General Variables                       //
    //  ============================================== //
    private boolean pvp;

    private boolean vanillaTrans;
    private String vanillaTransLocal;
    private boolean vanillaTransForce;
    private String menuItemJoin;
    private String menuType;
    private String menuName;
    private String menuSkullTextures;
    private final Map<String, Map<String, List<String>>> groupProp = new HashMap<>();
    private final Map<String, List<String>> cmdProp = new HashMap<>();
    private final Map<String, List<String>> conditionProp = new HashMap<>();
    private final Map<String, LocationMap> locProp = new HashMap<>();
    private final Map<String, BlocksMap> blockProp = new HashMap<>();
    private final Map<String, ActionBarMap> actionProp = new HashMap<>();
    private final Map<String, TitleMsgMap> titleProp = new HashMap<>();
    private final Map<String, ParticleMap> particleProp = new HashMap<>();
    private final Map<String, SoundMap> soundProp = new HashMap<>();
    private final Map<String, String> colorAliasMap = new HashMap<>();

    //  ============================================== //
    //         ConfigBuilder Variables                 //
    //  ============================================== //
    private boolean dataMySQL;
    private boolean dataYMAL;
    private boolean dataJson;
    private boolean dataProp;
    private boolean dataLog;
    private final Map<String, MySQLMap> mySQLProp = new HashMap<>();
    private final Map<String, String> YAMLProp = new HashMap<>();
    private final Map<String, String> jsonProp = new HashMap<>();
    private final Map<String, String> propProp = new HashMap<>();
    private final Map<String, LogMap> logProp = new HashMap<>();

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
    public void setupFirst() {
        setGeneral();
        setData();
        setMessages();
        setGroups();
        setCommands();
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

    public void setupLast() {
        if (UtilsHandler.getDepend().PvPManagerEnabled()) {
            pvp = PvPManager.getInstance().getConfig().getBoolean("General.Default PvP");
        } else {
            pvp = Boolean.parseBoolean(UtilsHandler.getFile().getProperty().getValue(
                    ConfigHandler.getPluginName(), "server_properties", "pvp"));
        }
    }

    private void sendSetupMsg() {
        List<String> list = new ArrayList<>(CorePlus.getInstance().getDescription().getDepend());
        list.addAll(CorePlus.getInstance().getDescription().getSoftDepend());
        UtilsHandler.getMsg().sendHookMsg(ConfigHandler.getPluginPrefix(), "plugins", list);
    }

    //  ============================================== //
    //         General Setter                          //
    //  ============================================== //
    private void setGeneral() {
        vanillaTrans = ConfigHandler.getConfig("config.yml").getBoolean("General.Local.Enable");
        vanillaTransForce = ConfigHandler.getConfig("config.yml").getBoolean("General.Local.Force.Enable");
        vanillaTransLocal = ConfigHandler.getConfig("config.yml").getString("General.Local.Force.Language");
        menuItemJoin = ConfigHandler.getConfig("config.yml").getString("General.Menu.ItemJoin");
        menuType = ConfigHandler.getConfig("config.yml").getString("General.Menu.Item.Type");
        menuName = ConfigHandler.getConfig("config.yml").getString("General.Menu.Item.Name");
        menuSkullTextures = ConfigHandler.getConfig("config.yml").getString("General.Menu.Skull-Textures");
    }

    //  ============================================== //
    //         Data.yml Setter                         //
    //  ============================================== //
    private void setData() {
        ConfigurationSection dataConfig;
        ConfigurationSection tableConfig;
        Map<String, String> map;
        dataMySQL = ConfigHandler.getConfig("data.yml").getBoolean("MySQL.Enable");
        if (dataMySQL) {
            String hostname = ConfigHandler.getConfig("data.yml").getString(
                    "MySQL.Settings.Default.Hostname");
            String port = ConfigHandler.getConfig("data.yml").getString(
                    "MySQL.Settings.Default.Port");
            String username = ConfigHandler.getConfig("data.yml").getString(
                    "MySQL.Settings.Default.Username");
            String password = ConfigHandler.getConfig("data.yml").getString(
                    "MySQL.Settings.Default.Password");
            dataConfig = ConfigHandler.getConfig("data.yml").getConfigurationSection("MySQL.Groups");
            if (dataConfig != null) {
                MySQLMap mySQLMap;
                for (String groupName : dataConfig.getKeys(false)) {
                    if (groupName.equals("Enable"))
                        continue;
                    if (!ConfigHandler.getConfig("data.yml").getBoolean(
                            "MySQL.Groups." + groupName + ".Enable", true))
                        continue;
                    mySQLMap = new MySQLMap();
                    mySQLMap.setGroupName(groupName);
                    mySQLMap.setHostName(ConfigHandler.getConfig("data.yml").getString(
                            "MySQL.Groups." + groupName + ".Hostname", hostname));
                    mySQLMap.setPort(ConfigHandler.getConfig("data.yml").getString(
                            "MySQL.Groups." + groupName + ".Port", port));
                    mySQLMap.setUsername(ConfigHandler.getConfig("data.yml").getString(
                            "MySQL.Groups." + groupName + ".Username", username));
                    mySQLMap.setPassword(ConfigHandler.getConfig("data.yml").getString(
                            "MySQL.Groups." + groupName + ".Password", password));
                    mySQLMap.setDatabase(ConfigHandler.getConfig("data.yml").getString(
                            "MySQL.Groups." + groupName + ".Database"));
                    tableConfig = ConfigHandler.getConfig("data.yml").getConfigurationSection(
                            "MySQL.Groups." + groupName + ".Tables");
                    if (tableConfig != null) {
                        map = new HashMap<>();
                        for (String subGroupName : tableConfig.getKeys(false)) {
                            map.put(subGroupName, ConfigHandler.getConfig("data.yml").getString(
                                    "MySQL.Groups." + groupName + ".Tables." + subGroupName));
                        }
                        mySQLMap.setTables(map);
                    }
                    mySQLProp.put(groupName, mySQLMap);
                }
            }
        }
        dataYMAL = ConfigHandler.getConfig("data.yml").getBoolean("Yaml.Enable");
        if (dataYMAL) {
            dataConfig = ConfigHandler.getConfig("data.yml").getConfigurationSection("Yaml.Groups");
            if (dataConfig != null) {
                for (String groupName : dataConfig.getKeys(false)) {
                    if (groupName.equals("Enable"))
                        continue;
                    if (!ConfigHandler.getConfig("data.yml").getBoolean("Yaml.Groups." + groupName + ".Enable", true))
                        continue;
                    YAMLProp.put(groupName, ConfigHandler.getConfig("data.yml").getString("Yaml.Groups." + groupName + ".Path"));
                }
            }
        }
        dataJson = ConfigHandler.getConfig("data.yml").getBoolean("Json.Enable");
        if (dataJson) {
            dataConfig = ConfigHandler.getConfig("data.yml").getConfigurationSection("Json.Groups");
            if (dataConfig != null) {
                for (String groupName : dataConfig.getKeys(false)) {
                    if (groupName.equals("Enable"))
                        continue;
                    if (!ConfigHandler.getConfig("data.yml").getBoolean("Json.Groups." + groupName + ".Enable", true))
                        continue;
                    jsonProp.put(groupName, ConfigHandler.getConfig("data.yml").getString("Json.Groups." + groupName + ".Path"));
                }
            }
        }
        dataProp = ConfigHandler.getConfig("data.yml").getBoolean("Properties.Enable");
        if (dataProp) {
            dataConfig = ConfigHandler.getConfig("data.yml").getConfigurationSection("Properties.Groups");
            if (dataConfig != null) {
                for (String groupName : dataConfig.getKeys(false)) {
                    if (groupName.equals("Enable"))
                        continue;
                    if (!ConfigHandler.getConfig("data.yml").getBoolean("Properties.Groups." + groupName + ".Enable", true))
                        continue;
                    YAMLProp.put(groupName, ConfigHandler.getConfig("data.yml").getString("Properties.Groups." + groupName + ".Path"));
                }
            }
        }
        dataLog = ConfigHandler.getConfig("data.yml").getBoolean("Logs.Enable");
        if (dataLog) {
            boolean time = ConfigHandler.getConfig("data.yml").getBoolean(
                    "Logs.Settings.Default.Time", false);
            boolean newFile = ConfigHandler.getConfig("data.yml").getBoolean(
                    "Logs.Settings.Default.New-File", false);
            boolean zip = ConfigHandler.getConfig("data.yml").getBoolean(
                    "Logs.Settings.Default.Zip", false);
            dataConfig = ConfigHandler.getConfig("data.yml").getConfigurationSection("Logs.Groups");
            if (dataConfig != null) {
                LogMap logMap;
                String path;
                String name;
                for (String groupName : dataConfig.getKeys(false)) {
                    if (ConfigHandler.getConfig("data.yml").getConfigurationSection("Logs.Groups." + groupName) == null)
                        continue;
                    logMap = new LogMap();
                    logMap.setGroupName(groupName);
                    path = ConfigHandler.getConfig("data.yml").getString("Logs.Groups." + groupName + ".Path");
                    name = ConfigHandler.getConfig("data.yml").getString("Logs.Groups." + groupName + ".Name");
                    if (path == null || name == null)
                        continue;
                    if (path.startsWith("plugins//")) {
                        path = Bukkit.getServer().getWorldContainer().getPath() + "//" + path;
                    } else if (path.startsWith("server//")) {
                        path = path.replace("server//", "");
                        path = Bukkit.getServer().getWorldContainer().getPath() + "//" + path;
                    }
                    logMap.setFile(new File(path, name));
                    logMap.setTime(ConfigHandler.getConfig("data.yml").getBoolean("Logs.Groups." + groupName + ".Time", time));
                    logMap.setNewDateFile(ConfigHandler.getConfig("data.yml").getBoolean("Logs.Groups." + groupName + ".New-File", newFile));
                    logMap.setZip(ConfigHandler.getConfig("data.yml").getBoolean("Logs.Groups." + groupName + ".Zip", zip));
                    logProp.put(groupName, logMap);
                }
            }
        }
    }

    //  ============================================== //
    //         commands.yml Setter                     //
    //  ============================================== //
    private void setCommands() {
        ConfigurationSection config = ConfigHandler.getConfig("commands.yml").getConfigurationSection("Custom-Commands");
        if (config == null)
            return;
        for (String group : config.getKeys(false))
            cmdProp.put(group, ConfigHandler.getConfig("commands.yml").getStringList("Custom-Commands." + group));
    }

    //  ============================================== //
    //         groups.yml Setter                         //
    //  ============================================== //
    private void setGroups() {
        ConfigurationSection customGroupConfig = ConfigHandler.getConfig("groups.yml").getConfigurationSection("");
        if (customGroupConfig == null)
            return;
        List<String> groupList;
        Map<String, List<String>> groupMap;
        ConfigurationSection groupConfig;
        for (String type : customGroupConfig.getKeys(false)) {
            if (type.equals("Config-Version"))
                continue;
            groupConfig = ConfigHandler.getConfig("groups.yml").getConfigurationSection(type);
            if (groupConfig == null)
                return;
            groupMap = new HashMap<>();
            for (String group : groupConfig.getKeys(false)) {
                groupList = ConfigHandler.getConfig("groups.yml").getStringList(type + "." + group);
                if (groupList.isEmpty())
                    continue;
                groupMap.put(group, groupList);
            }
            groupProp.put(type, groupMap);
        }
    }

    //  ============================================== //
    //         condition.yml Setter                    //
    //  ============================================== //
    private void setCondition() {
        ConfigurationSection config = ConfigHandler.getConfig("condition.yml").getConfigurationSection("Conditions");
        if (config == null)
            return;
        for (String group : config.getKeys(false))
            conditionProp.put(group, ConfigHandler.getConfig("condition.yml").getStringList("Conditions." + group));
    }

    //  ============================================== //
    //         location.yml Setter                     //
    //  ============================================== //
    private void setLocation() {
        ConfigurationSection locConfig = ConfigHandler.getConfig("location.yml").getConfigurationSection("Location");
        if (locConfig == null)
            return;
        LocationMap locMap;
        ConfigurationSection areaConfig;
        for (String groupName : locConfig.getKeys(false)) {
            locMap = new LocationMap();
            locMap.setGroupName(groupName);
            areaConfig = ConfigHandler.getConfig("location.yml").getConfigurationSection("Location." + groupName + ".Area");
            locMap.setWorlds(ConfigHandler.getConfig("location.yml").getStringList("Location." + groupName + ".Worlds"));
            if (areaConfig != null)
                for (String area : areaConfig.getKeys(false))
                    locMap.addCord(groupName, area, ConfigHandler.getConfig("location.yml").getString("Location." + groupName + ".Area." + area));
            locProp.put(groupName, locMap);
        }
    }

    //  ============================================== //
    //         Blocks.yml Setter                       //
    //  ============================================== //
    private void setBlocks() {
        ConfigurationSection blocksConfig = ConfigHandler.getConfig("blocks.yml").getConfigurationSection("Blocks");
        if (blocksConfig == null)
            return;
        BlocksMap blocksMap;
        for (String groupName : blocksConfig.getKeys(false)) {
            blocksMap = new BlocksMap();
            blocksMap.setGroupName(groupName);
            blocksMap.setBlockTypes(getTypeList(ConfigHandler.getPluginName(),
                    ConfigHandler.getConfig("blocks.yml").getStringList("Blocks." + groupName + ".Types"), "Materials"));
            blocksMap.setIgnoreList(ConfigHandler.getConfig("blocks.yml").getStringList("Blocks." + groupName + ".Ignore"));
            int r = ConfigHandler.getConfig("blocks.yml").getInt("Blocks." + groupName + ".Search.Values.R");
            blocksMap.setR(r);
            blocksMap.setX(ConfigHandler.getConfig("blocks.yml").getInt("Blocks." + groupName + ".Search.Values.X", r));
            blocksMap.setY(ConfigHandler.getConfig("blocks.yml").getInt("Blocks." + groupName + ".Search.Values.Y", r));
            blocksMap.setZ(ConfigHandler.getConfig("blocks.yml").getInt("Blocks." + groupName + ".Search.Values.Z", r));
            blocksMap.setH(ConfigHandler.getConfig("blocks.yml").getInt("Blocks." + groupName + ".Search.Values.H"));
            blocksMap.setMode(ConfigHandler.getConfig("blocks.yml").getString("Blocks." + groupName + ".Search.Mode", "Cuboid"));
            blockProp.put(groupName, blocksMap);
        }
    }

    //  ============================================== //
    //         sounds.yml Setter                       //
    //  ============================================== //
    private void setSounds() {
        ConfigurationSection soundConfig = ConfigHandler.getConfig("sounds.yml").getConfigurationSection("Sounds");
        if (soundConfig == null)
            return;
        SoundMap soundMap;
        for (String groupName : soundConfig.getKeys(false)) {
            soundMap = new SoundMap();
            soundMap.setGroupName(groupName);
            try {
                soundMap.setType(Sound.valueOf(ConfigHandler.getConfig("sounds.yml").getString("Sounds." + groupName + ".Type")));
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(), "Unknown sound type: " + groupName);
                continue;
            }
            soundMap.setVolume(ConfigHandler.getConfig("sounds.yml").getInt("Sounds." + groupName + ".Volume", 1));
            soundMap.setPitch(ConfigHandler.getConfig("sounds.yml").getInt("Sounds." + groupName + ".Pitch", 1));
            soundMap.setTimes(ConfigHandler.getConfig("sounds.yml").getInt("Sounds." + groupName + ".Run.Times", 1));
            soundMap.setInterval(ConfigHandler.getConfig("sounds.yml").getInt("Sounds." + groupName + ".Run.Interval", 0));
            soundMap.setVolume(ConfigHandler.getConfig("sounds.yml").getInt("Sounds." + groupName + ".Volume", 1));
            soundMap.setPitch(ConfigHandler.getConfig("sounds.yml").getInt("Sounds." + groupName + ".Pitch", 1));
            soundProp.put(groupName, soundMap);
        }
    }

    //  ============================================== //
    //         particles.yml Setter                    //
    //  ============================================== //
    private void setParticles() {
        ConfigurationSection particleConfig = ConfigHandler.getConfig("particles.yml").getConfigurationSection("Particles");
        if (particleConfig == null)
            return;
        ParticleMap particleMap;
        for (String groupName : particleConfig.getKeys(false)) {
            particleMap = new ParticleMap();
            particleMap.setGroupName(groupName);
            try {
                particleMap.setType(Particle.valueOf(ConfigHandler.getConfig("particles.yml").getString("Particles." + groupName + ".Type")));
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(), "Unknown Particle type of particle: " + groupName + "\"");
                UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(), "More information: https://github.com/momoservertw/CorePlus/wiki/Particle");
                continue;
            }
            particleMap.setAmount(ConfigHandler.getConfig("particles.yml").getInt("Particles." + groupName + ".Amount", 1));
            particleMap.setTimes(ConfigHandler.getConfig("particles.yml").getInt("Particles." + groupName + ".Run.Times", 1));
            particleMap.setInterval(ConfigHandler.getConfig("particles.yml").getInt("Particles." + groupName + ".Run.Interval", 20));
            particleMap.setOffsetX(ConfigHandler.getConfig("particles.yml").getDouble("Particles." + groupName + ".Offset.X", 0));
            particleMap.setOffsetY(ConfigHandler.getConfig("particles.yml").getDouble("Particles." + groupName + ".Offset.Y", 0));
            particleMap.setOffsetZ(ConfigHandler.getConfig("particles.yml").getDouble("Particles." + groupName + ".Offset.Z", 0));
            particleMap.setExtra(ConfigHandler.getConfig("particles.yml").getDouble("Particles." + groupName + ".Speed", 0));
            particleMap.setColorR(ConfigHandler.getConfig("particles.yml").getInt("Particles." + groupName + ".Color.R", 0));
            particleMap.setColorG(ConfigHandler.getConfig("particles.yml").getInt("Particles." + groupName + ".Color.G", 0));
            particleMap.setColorB(ConfigHandler.getConfig("particles.yml").getInt("Particles." + groupName + ".Color.B", 0));
            particleMap.setColorType(ConfigHandler.getConfig("particles.yml").getString("Particles." + groupName + ".Color.Type"));
            try {
                particleMap.setMaterial(Material.getMaterial(
                        ConfigHandler.getConfig("particles.yml").getString("Particles." + groupName + ".Material", "STONE")));
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(), "Unknown Material type of particle: " + groupName + "\"");
                UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(), "More information: https://github.com/momoservertw/CorePlus/wiki/Particle");
                continue;
            }
            particleProp.put(groupName, particleMap);
        }
    }

    //  ============================================== //
    //         action_bars.yml Setter                  //
    //  ============================================== //
    private void setActionBars() {
        ConfigurationSection particleConfig = ConfigHandler.getConfig("action_bars.yml").getConfigurationSection("Action-Bars");
        if (particleConfig == null)
            return;
        ActionBarMap actionBarMap;
        for (String groupName : particleConfig.getKeys(false)) {
            actionBarMap = new ActionBarMap();
            actionBarMap.setGroupName(groupName);
            actionBarMap.setTimes(ConfigHandler.getConfig("action_bars.yml").getInt("Action-Bars." + groupName + ".Times", 1));
            actionBarMap.setInterval(ConfigHandler.getConfig("action_bars.yml").getInt("Action-Bars." + groupName + ".Interval", 20));
            actionProp.put(groupName, actionBarMap);
        }
    }

    //  ============================================== //
    //         message.yml Setter                //
    //  ============================================== //
    private void setMessages() {
        ConfigurationSection message = ConfigHandler.getConfig("message.yml").getConfigurationSection("Message");
        if (message != null)
            return;
        colorAliasMap.put("a", ConfigHandler.getConfig("message.yml").getString("Messages.Translation", "green"));
        colorAliasMap.put("b", ConfigHandler.getConfig("message.yml").getString("Messages.Translation", "aqua"));
        colorAliasMap.put("c", ConfigHandler.getConfig("message.yml").getString("Messages.Translation", "red"));
        colorAliasMap.put("d", ConfigHandler.getConfig("message.yml").getString("Messages.Translation", "light_purple"));
        colorAliasMap.put("e", ConfigHandler.getConfig("message.yml").getString("Messages.Translation", "yellow"));
        colorAliasMap.put("f", ConfigHandler.getConfig("message.yml").getString("Messages.Translation", "white"));
        colorAliasMap.put("0", ConfigHandler.getConfig("message.yml").getString("Messages.Translation", "black"));
        colorAliasMap.put("1", ConfigHandler.getConfig("message.yml").getString("Messages.Translation", "dark_blue"));
        colorAliasMap.put("2", ConfigHandler.getConfig("message.yml").getString("Messages.Translation", "dark_green"));
        colorAliasMap.put("3", ConfigHandler.getConfig("message.yml").getString("Messages.Translation", "dark_aqua"));
        colorAliasMap.put("4", ConfigHandler.getConfig("message.yml").getString("Messages.Translation", "dark_red"));
        colorAliasMap.put("5", ConfigHandler.getConfig("message.yml").getString("Messages.Translation", "dark_purple"));
        colorAliasMap.put("6", ConfigHandler.getConfig("message.yml").getString("Messages.Translation", "gold"));
        colorAliasMap.put("7", ConfigHandler.getConfig("message.yml").getString("Messages.Translation", "gray"));
        colorAliasMap.put("8", ConfigHandler.getConfig("message.yml").getString("Messages.Translation", "dark_gray"));
        colorAliasMap.put("9", ConfigHandler.getConfig("message.yml").getString("Messages.Translation", "blue"));
    }

    //  ============================================== //
    //         title_message.yml Setter                //
    //  ============================================== //
    private void setTitleMessages() {
        ConfigurationSection titleMsgConfig = ConfigHandler.getConfig("title_messages.yml").getConfigurationSection("Title-Messages");
        if (titleMsgConfig != null)
            return;
        TitleMsgMap titleMessageMap;
        for (String groupName : titleMsgConfig.getKeys(false)) {
            titleMessageMap = new TitleMsgMap();
            titleMessageMap.setGroupName(groupName);
            titleMessageMap.setStay(ConfigHandler.getConfig("title_messages.yml").getInt("Title-Messages." + groupName + ".Stay", 70));
            titleMessageMap.setFadeIn(ConfigHandler.getConfig("title_messages.yml").getInt("Title-Messages." + groupName + ".FadeIn", 10));
            titleMessageMap.setFadeOut(ConfigHandler.getConfig("title_messages.yml").getInt("Title-Messages." + groupName + ".FadeOut", 20));
            titleProp.put(groupName, titleMessageMap);
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
                for (String groupName : groupConfig.getKeys(false)) {
                    configBuilderMap = new ConfigBuilderMap();
                    configBuilderMap.setGroupName(groupName);
                    if (type.equals("Entities"))
                        configBuilderMap.setType("entity");
                    else if (type.equals("Materials"))
                        configBuilderMap.setType("material");
                    configBuilderMap.setTitle("  " + groupName + ":");
                    configBuilderMap.setRowLine(true);
                    configBuilderMap.setValue("    - %value%");
                    configBuilderMap.setSet(new HashSet<>(
                            ConfigHandler.getConfig("config.yml").getStringList("Config-Builder.Groups." + type + "." + groupName + ".List")));
                    configBuilderMap.setIgnoreSet(new HashSet<>(
                            ConfigHandler.getConfig("config.yml").getStringList("Config-Builder.Groups." + type + "." + groupName + ".Ignore-List")));
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
            for (String groupName : configCustom.getKeys(false)) {
                groupConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("Config-Builder.Custom." + groupName);
                if (groupConfig == null)
                    continue;
                type = ConfigHandler.getConfig("config.yml").getString("Config-Builder.Custom." + groupName + ".Type");
                if (type == null)
                    continue;
                configBuilderMap = new ConfigBuilderMap();
                configBuilderMap.setGroupName(groupName);
                configBuilderMap.setType(type);
                configBuilderMap.setTitle(
                        ConfigHandler.getConfig("config.yml").getString("Config-Builder.Custom." + groupName + ".Format.Title", groupName));
                configBuilderMap.setRowLine(
                        ConfigHandler.getConfig("config.yml").getBoolean("Config-Builder.Custom." + groupName + ".Format.Row-Line", true));
                configBuilderMap.setSplit(
                        ConfigHandler.getConfig("config.yml").getString("Config-Builder.Custom." + groupName + ".Format.Split", ", "));
                configBuilderMap.setValue(
                        ConfigHandler.getConfig("config.yml").getString("Config-Builder.Custom." + groupName + ".Format.Value",
                                "  - %value%"));
                configBuilderMap.setSet(new HashSet<>(
                        ConfigHandler.getConfig("config.yml").getStringList("Config-Builder.Custom." + groupName + ".List")));
                configBuilderMap.setIgnoreSet(new HashSet<>(
                        ConfigHandler.getConfig("config.yml").getStringList("Config-Builder.Custom." + groupName + ".Ignore-List")));
                configBuilderCustomProp.put(groupName, configBuilderMap);
            }
        }
    }

    //  ============================================== //
    //         General Getter                          //
    //  ============================================== //
    public boolean isPvp() {
        return pvp;
    }

    public boolean isVanillaTrans() {
        return vanillaTrans;
    }

    public String getVanillaTransLocal() {
        return vanillaTransLocal;
    }

    public boolean isVanillaTransForce() {
        return vanillaTransForce;
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

    public Map<String, BlocksMap> getBlockProp() {
        return blockProp;
    }

    public Map<String, ActionBarMap> getActionProp() {
        return actionProp;
    }

    public Map<String, TitleMsgMap> getTitleProp() {
        return titleProp;
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
    //         Data Getter                             //
    //  ============================================== //
    @Override
    public boolean isDataMySQL() {
        return dataMySQL;
    }

    @Override
    public boolean isDataYMAL() {
        return dataYMAL;
    }

    @Override
    public boolean isDataJson() {
        return dataJson;
    }

    @Override
    public boolean isDataProp() {
        return dataProp;
    }

    @Override
    public boolean isDataLog() {
        return dataLog;
    }

    @Override
    public Map<String, MySQLMap> getMySQLProp() {
        return mySQLProp;
    }

    @Override
    public Map<String, String> getYAMLProp() {
        return YAMLProp;
    }

    @Override
    public Map<String, String> getJsonProp() {
        return jsonProp;
    }

    @Override
    public Map<String, String> getPropProp() {
        return propProp;
    }

    @Override
    public Map<String, LogMap> getLogProp() {
        return logProp;
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


    public Map<String, String> getColorAliasMap() {
        return colorAliasMap;
    }

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
                UtilsHandler.getMsg().sendErrorMsg(pluginName,
                        "Unknown list type: \"" + inputType + ", " + type + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName,
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
                                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not find the " + inputType + ": " + customType);
                            }
                    }
                } catch (Exception ex) {
                    UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not find the " + inputType + ": " + type);
                }
            }
        }
        return outputList;
    }

    @Override
    public FileConfiguration getConfig(String fileName) {
        return ConfigHandler.getConfig(fileName);
    }
}
