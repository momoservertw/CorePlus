package tw.momocraft.coreplus.utils;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import tw.momocraft.coreplus.api.ConfigInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.utils.customcommands.ParticleMap;
import tw.momocraft.coreplus.utils.customcommands.SoundMap;

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
    private String vanillaTrans;
    private Map<String, String> customCmdProp;
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
        vanillaTrans = ConfigHandler.getConfig("config.yml").getString("General.Vanilla-Translate.Local");
        ConfigurationSection cmdConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("General.Custom-Commands");
        if (cmdConfig != null) {
            customCmdProp = new HashMap<>();
            for (String group : cmdConfig.getKeys(false)) {
                customCmdProp.put(group, ConfigHandler.getConfig("config.yml").getString("General.Custom-Commands." + group));
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
                    ConfigHandler.getLang().sendErrorMsg(ConfigHandler.getPrefix(), "&cUnknown particle type: " + particleType);
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
    public Map<String, String> getCustomCmdProp() {
        return customCmdProp;
    }

    public Map<String, ParticleMap> getParticleProp() {
        return particleProp;
    }

    public Map<String, SoundMap> getSoundProp() {
        return soundProp;
    }

    public String getVanillaTrans() {
        return vanillaTrans;
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
                    case "sound":
                        list.add(Sound.valueOf(type).name());
                        continue;
                    case "particle":
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
                            case "entity":
                                try {
                                    list.add(EntityType.valueOf(customType).name());
                                } catch (Exception ex) {
                                    if (ConfigHandler.getDepends().MythicMobsEnabled()) {
                                        if (MythicMobsAPI.isMythicMobName(type)) {
                                            list.add(type);
                                            continue;
                                        }
                                        ConfigHandler.getLang().sendErrorMsg(prefix, "Can not find the " + listType + ": " + type);
                                    }
                                }
                                break;
                            case "material":
                                list.add(Material.valueOf(customType).name());
                                continue;
                            case "sound":
                                list.add(Sound.valueOf(customType).name());
                                continue;
                            case "particle":
                                list.add(Particle.valueOf(customType).name());
                        }
                    } catch (Exception ignored) {
                        ConfigHandler.getLang().sendErrorMsg(prefix, "Can not find the " + listType + ": " + type);
                    }
                }
            }
        }
        return list;
    }
}
