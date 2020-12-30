package tw.momocraft.coreplus.utils;

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
import tw.momocraft.coreplus.utils.customcommands.LogMap;
import tw.momocraft.coreplus.utils.customcommands.ParticleMap;
import tw.momocraft.coreplus.utils.customcommands.SoundMap;

import java.io.File;
import java.util.*;

public class ConfigPath implements ConfigInterface {
    public ConfigPath() {
        setUp();
    }

    //  ============================================== //
    //         General Variables                       //
    //  ============================================== //
    private String menuIJ;
    private String menuType;
    private String menuName;
    private boolean vanillaTrans;
    private String vanillaTransLocal;
    private boolean vanillaTransForce;
    private final Map<String, String> cmdProp = new HashMap<>();
    private final Map<String, LogMap> logProp = new HashMap<>();
    private final Map<String, SoundMap> soundProp = new HashMap<>();
    private final Map<String, ParticleMap> particleProp = new HashMap<>();

    //  ============================================== //
    //         Setup all configuration                 //
    //  ============================================== //
    private void setUp() {
        setGeneral();
    }

    //  ============================================== //
    //         General Setter                          //
    //  ============================================== //
    private void setGeneral() {
        menuIJ = ConfigHandler.getConfig("config.yml").getString("General.Menu.ItemJoin");
        menuType = ConfigHandler.getConfig("config.yml").getString("General.Menu.Item.Type");
        menuName = ConfigHandler.getConfig("config.yml").getString("General.Menu.Item.Name");
        vanillaTrans = ConfigHandler.getConfig("config.yml").getBoolean("General.Vanilla-Translate.Enable");
        vanillaTransLocal = ConfigHandler.getConfig("config.yml").getString("General.Vanilla-Translate.Local");
        vanillaTransForce = ConfigHandler.getConfig("config.yml").getBoolean("General.Vanilla-Translate.Force");
        ConfigurationSection cmdConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("General.Custom-Commands");
        if (cmdConfig != null) {
            for (String group : cmdConfig.getKeys(false)) {
                cmdProp.put(group, ConfigHandler.getConfig("config.yml").getString("General.Custom-Commands." + group));
            }
        }
        ConfigurationSection logConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("General.Logs");
        if (logConfig != null) {
            LogMap logMap;
            String path;
            String name;

            for (String group : logConfig.getKeys(false)) {
                logMap = new LogMap();
                path = ConfigHandler.getConfig("config.yml").getString("General.Logs." + group + ".Path");
                name = ConfigHandler.getConfig("config.yml").getString("General.Logs." + group + ".Name");
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
                logMap.setTime(ConfigHandler.getConfig("config.yml").getBoolean("General.Logs." + group + ".Time", true));
                logMap.setNewFile(ConfigHandler.getConfig("config.yml").getBoolean("General.Logs." + group + ".New-File", false));
                logMap.setZip(ConfigHandler.getConfig("config.yml").getBoolean("General.Logs." + group + ".Zip", false));
                logProp.put(group, logMap);
            }
        }
        ConfigurationSection particleConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("General.Particles");
        if (particleConfig != null) {
            ParticleMap particleMap;
            for (String group : particleConfig.getKeys(false)) {
                particleMap = new ParticleMap();
                String particleType = ConfigHandler.getConfig("config.yml").getString("General.Particles." + group + ".Type", "FLAME");
                try {
                    particleMap.setType(Particle.valueOf(particleType));
                } catch (Exception ex) {
                    UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPrefix(), "&cUnknown particle type: " + particleType);
                    continue;
                }
                particleMap.setAmount(ConfigHandler.getConfig("config.yml").getInt("General.Particles." + group + ".Amount", 1));
                particleMap.setTimes(ConfigHandler.getConfig("config.yml").getInt("General.Particles." + group + ".Run.Times", 1));
                particleMap.setInterval(ConfigHandler.getConfig("config.yml").getInt("General.Particles." + group + ".Run.Interval", 20));
                particleMap.setOffsetX(ConfigHandler.getConfig("config.yml").getInt("General.Particles." + group + ".Offset.X", 0));
                particleMap.setOffsetY(ConfigHandler.getConfig("config.yml").getInt("General.Particles." + group + ".Offset.Y", 0));
                particleMap.setOffsetZ(ConfigHandler.getConfig("config.yml").getInt("General.Particles." + group + ".Offset.Z", 0));
                particleMap.setExtra(ConfigHandler.getConfig("config.yml").getInt("General.Particles." + group + ".Speed", 0));
                particleProp.put(group, particleMap);
            }
        }
        ConfigurationSection soundConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("General.Sounds");
        if (soundConfig != null) {
            SoundMap soundMap;
            for (String group : soundConfig.getKeys(false)) {
                soundMap = new SoundMap();
                soundMap.setType(ConfigHandler.getConfig("config.yml").getString("General.Sounds." + group + ".Type"));
                soundMap.setVolume(ConfigHandler.getConfig("config.yml").getInt("General.Sounds." + group + ".Volume", 1));
                soundMap.setPitch(ConfigHandler.getConfig("config.yml").getInt("General.Sounds." + group + ".Pitch", 1));
                soundMap.setTimes(ConfigHandler.getConfig("config.yml").getInt("General.Sounds." + group + ".Run.Times", 1));
                soundMap.setInterval(ConfigHandler.getConfig("config.yml").getInt("General.Sounds." + group + ".Run.Interval", 20));
                soundMap.setVolume(ConfigHandler.getConfig("config.yml").getInt("General.Sounds." + group + ".Volume", 0));
                soundMap.setPitch(ConfigHandler.getConfig("config.yml").getInt("General.Sounds." + group + ".Pitch", 0));
                soundProp.put(group, soundMap);
            }
        }
    }

    //  ============================================== //
    //         General Getter                          //
    //  ============================================== //
    public Map<String, String> getCmdProp() {
        return cmdProp;
    }

    public Map<String, LogMap> getLogProp() {
        return logProp;
    }

    public Map<String, ParticleMap> getParticleProp() {
        return particleProp;
    }

    public Map<String, SoundMap> getSoundProp() {
        return soundProp;
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

    public String getMenuIJ() {
        return menuIJ;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getMenuType() {
        return menuType;
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
            } catch (Exception e) {
                customList = ConfigHandler.getConfig("groups.yml").getStringList(listType + "." + type);
                if (customList.isEmpty())
                    continue;
                // Add Custom Group.
                for (String customType : customList) {
                    try {
                        switch (listType) {
                            case "Entities":
                                try {
                                    list.add(EntityType.valueOf(customType).name());
                                } catch (Exception ex) {
                                    if (UtilsHandler.getDepend().MythicMobsEnabled()) {
                                        if (UtilsHandler.getEntity().isMythicMobName(type)) {
                                            list.add(type);
                                            continue;
                                        }
                                        UtilsHandler.getLang().sendErrorMsg(prefix, "Can not find the " + listType + ": " + type);
                                    }
                                }
                                break;
                            case "Materials":
                                list.add(Material.valueOf(customType).name());
                                continue;
                            case "Sound":
                                list.add(Sound.valueOf(customType).name());
                                continue;
                            case "Particle":
                                list.add(Particle.valueOf(customType).name());
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
