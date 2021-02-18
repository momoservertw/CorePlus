package tw.momocraft.coreplus.utils;

import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import tw.momocraft.coreplus.api.ConfigInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;
import tw.momocraft.coreplus.utils.conditions.BlocksMap;
import tw.momocraft.coreplus.utils.conditions.LocationMap;
import tw.momocraft.coreplus.utils.customcommands.*;

import java.io.File;
import java.util.*;

public class ConfigPath implements ConfigInterface {
    public ConfigPath() {
        setUp();
    }

    //  ============================================== //
    //         General Variables                       //
    //  ============================================== //
    private boolean mySQL;
    private String mySQLUsername;
    private String mySQLPassword;
    private String mySQLHostname;
    private int mySQLPort;
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
    private final Map<String, LocationMap> locProp = new HashMap<>();
    private final Map<String, BlocksMap> blockProp = new HashMap<>();
    private final Map<String, ActionBarMap> actionProp = new HashMap<>();
    private final Map<String, TitleMessageMap> titleProp = new HashMap<>();
    private final Map<String, ParticleMap> particleProp = new HashMap<>();
    private final Map<String, SoundMap> soundProp = new HashMap<>();

    //  ============================================== //
    //         ConfigBuilder Variables                 //
    //  ============================================== //
    private final Map<String, Pair<String, List<String>>> configBuilderProp = new HashMap<>();

    //  ============================================== //
    //         Setup all configuration                 //
    //  ============================================== //
    private void setUp() {
        setGeneral();
        setGroups();
        setCommands();
        setLogs();
        setLocation();
        setBlocks();
        setActionBars();
        setTitleMessages();
        setParticles();
        setSounds();

        sendSetupMessage();

        setConfigBuilder();
    }

    private void sendSetupMessage() {
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPluginPrefix(), "Setup Groups: ");
        for (String group : groupProp.keySet()) {
            UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPluginPrefix(), "  " + group + ": " + groupProp.get(group).keySet());
        }
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPluginPrefix(), "Setup Commands: " + cmdProp.keySet());
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPluginPrefix(), "Setup Logs: " + logProp.keySet());
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPluginPrefix(), "Setup Location: " + locProp.keySet());
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPluginPrefix(), "Setup Blocks: " + blockProp.keySet());
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPluginPrefix(), "Setup Particles: " + particleProp.keySet());
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPluginPrefix(), "Setup Sounds: " + soundProp.keySet());
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPluginPrefix(), "Setup Action Bars: " + actionProp.keySet());
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPluginPrefix(), "Setup Title Messages: " + titleProp.keySet());
    }

    //  ============================================== //
    //         General Setter                          //
    //  ============================================== //
    private void setGeneral() {
        mySQL = ConfigHandler.getConfig("config.yml").getBoolean("General.MySQL.Enable");
        mySQLUsername = ConfigHandler.getConfig("config.yml").getString("General.MySQL.username");
        mySQLPassword = ConfigHandler.getConfig("config.yml").getString("General.MySQL.password");
        mySQLHostname = ConfigHandler.getConfig("config.yml").getString("General.MySQL.hostname");
        mySQLPort = ConfigHandler.getConfig("config.yml").getInt("General.MySQL.port");
        VanillaTrans = ConfigHandler.getConfig("config.yml").getBoolean("General.Vanilla-Translation.Enable");
        VanillaTransForce = ConfigHandler.getConfig("config.yml").getBoolean("General.Vanilla-Translation.Force.Enable");
        VanillaTransLocal = ConfigHandler.getConfig("config.yml").getString("General.Vanilla-Translation.Force.Local");
        menuItemJoin = ConfigHandler.getConfig("config.yml").getString("General.Menu.ItemJoin");
        menuType = ConfigHandler.getConfig("config.yml").getString("General.Menu.Item.Type");
        menuName = ConfigHandler.getConfig("config.yml").getString("General.Menu.Item.Name");
        menuSkullTextures = ConfigHandler.getConfig("config.yml").getString("General.Menu.Skull-Textures");
    }

    private void setCommands() {
        ConfigurationSection cmdConfig = ConfigHandler.getConfig("commands.yml").getConfigurationSection("Custom-Commands");
        if (cmdConfig == null) {
            return;
        }
        for (String group : cmdConfig.getKeys(false)) {
            cmdProp.put(group, ConfigHandler.getConfig("commands.yml").getStringList("Custom-Commands." + group));
        }
    }

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

    private void setLogs() {
        ConfigurationSection logConfig = ConfigHandler.getConfig("logs.yml").getConfigurationSection("Logs");
        if (logConfig == null) {
            return;
        }
        LogMap logMap;
        String path;
        String name;
        for (String group : logConfig.getKeys(false)) {
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
                    UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "&cUnknown sound type: " + group);
                    continue;
                }
                soundMap.setGroupName(group);
                soundMap.setVolume(ConfigHandler.getConfig("sounds.yml").getInt("Sounds." + group + ".Volume", 1));
                soundMap.setPitch(ConfigHandler.getConfig("sounds.yml").getInt("Sounds." + group + ".Pitch", 1));
                soundMap.setTimes(ConfigHandler.getConfig("sounds.yml").getInt("Sounds." + group + ".Run.Times", 1));
                soundMap.setInterval(ConfigHandler.getConfig("sounds.yml").getInt("Sounds." + group + ".Run.Interval", 20));
                soundMap.setVolume(ConfigHandler.getConfig("sounds.yml").getInt("Sounds." + group + ".Volume", 0));
                soundMap.setPitch(ConfigHandler.getConfig("sounds.yml").getInt("Sounds." + group + ".Pitch", 0));
                soundProp.put(group, soundMap);
            }
        }
    }

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
                    UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "&cUnknown particle type: " + group);
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
                particleProp.put(group, particleMap);
            }
        }
    }

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
        ConfigurationSection config = ConfigHandler.getConfig("config.yml").getConfigurationSection("Config-Builder");
        if (config != null) {
            for (String group : config.getKeys(false)) {
                configBuilderProp.put(group, new Pair<>(
                        ConfigHandler.getConfig("config.yml").getString("Config-Builder." + group + ".Format"),
                        ConfigHandler.getConfig("config.yml").getStringList("Config-Builder." + group + ".Ignore")));
            }
        }
    }

    //  ============================================== //
    //         General Getter                          //
    //  ============================================== //
    @Override
    public boolean isMySQL() {
        return mySQL;
    }

    public String getMySQLUsername() {
        return mySQLUsername;
    }

    public String getMySQLPassword() {
        return mySQLPassword;
    }

    public String getMySQLHostname() {
        return mySQLHostname;
    }

    public int getMySQLPort() {
        return mySQLPort;
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
    public Map<String, LocationMap> getLocProp() {
        return locProp;
    }

    @Override
    public Map<String, BlocksMap> getBlocksProp() {
        return blockProp;
    }

    //  ============================================== //
    //         ConfigBuilder Getter                    //
    //  ============================================== //
    public Map<String, Pair<String, List<String>>> getConfigBuilderProp() {
        return configBuilderProp;
    }

    //  ============================================== //
    //         Others                                  //
    //  ============================================== //
    @Override
    public List<String> getTypeList(String prefix, List<String> types, String listType) {
        List<String> list = new ArrayList<>();
        List<String> customList;
        for (String type : types) {
            try {
                switch (listType) {
                    case "Entities":
                        list.add(EntityType.valueOf(type).name());
                        break;
                    case "Materials":
                        list.add(Material.valueOf(type).name());
                        break;
                    case "Sound":
                        list.add(Sound.valueOf(type).name());
                        continue;
                    case "Particle":
                        list.add(Particle.valueOf(type).name());
                }
            } catch (Exception ex) {
                customList = groupProp.get(listType).get(type);
                if (customList == null || customList.isEmpty()) {
                    continue;
                }
                // Add Custom Group.
                for (String customType : customList) {
                    try {
                        switch (listType) {
                            case "Entities":
                                list.add(EntityType.valueOf(customType).name());
                                continue;
                            case "Materials":
                                list.add(Material.valueOf(customType).name());
                                continue;
                            case "Sound":
                                list.add(Sound.valueOf(customType).name());
                                continue;
                            case "Particle":
                                list.add(Particle.valueOf(customType).name());
                                continue;
                            case "MythicMobs":
                                if (UtilsHandler.getDepend().MythicMobsEnabled()) {
                                    if (UtilsHandler.getEntity().isMythicMobName(type)) {
                                        list.add(type);
                                        continue;
                                    }
                                    UtilsHandler.getLang().sendErrorMsg(prefix, "Can not find the " + listType + ": " + type);
                                }
                                break;
                        }
                    } catch (Exception ignored) {
                        UtilsHandler.getLang().sendErrorMsg(prefix, "Can not find the " + listType + ": " + type);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public FileConfiguration getConfig(String fileName) {
        return ConfigHandler.getConfig(fileName);
    }
}
