package tw.momocraft.coreplus.utils;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.utils.blocksutils.BlocksUtils;
import tw.momocraft.coreplus.utils.customcommands.ParticleMap;
import tw.momocraft.coreplus.utils.customcommands.SoundMap;
import tw.momocraft.coreplus.utils.locationutils.LocationUtils;

import java.util.*;

public class ConfigPath {
    public ConfigPath() {
        setUp();
    }

    //  ============================================== //
    //         Message Variables                       //
    //  ============================================== //
    private String msgTitle;
    private String msgHelp;
    private String msgReload;
    private String msgVersion;
    private String msgCheckslime;

    //  ============================================== //
    //         General Variables                       //
    //  ============================================== //
    private Map<String, String> customCmdProp;
    private final Map<String, SoundMap> soundProp = new HashMap<>();
    private final Map<String, ParticleMap> particleProp = new HashMap<>();
    private LocationUtils locationUtils;
    private BlocksUtils blocksUtils;

    private String menuIJ;
    private String menuType;
    private String menuName;
    private String vanillaTrans;

    //  ============================================== //
    //         Setup all configuration                 //
    //  ============================================== //
    private void setUp() {
        setupMsg();
        setGeneral();
    }

    //  ============================================== //
    //         Message Setter                          //
    //  ============================================== //
    private void setupMsg() {
        msgTitle = ConfigHandler.getConfig("config.yml").getString("Message.Commands.title");
        msgHelp = ConfigHandler.getConfig("config.yml").getString("Message.Commands.help");
        msgReload = ConfigHandler.getConfig("config.yml").getString("Message.Commands.reload");
        msgVersion = ConfigHandler.getConfig("config.yml").getString("Message.Commands.version");
    }

    //  ============================================== //
    //         General Setter                          //
    //  ============================================== //
    private void setGeneral() {
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
                particleMap.setType(ConfigHandler.getConfig("config.yml").getString("General.Particles." + group + ".Type"));
                particleMap.setAmount(ConfigHandler.getConfig("config.yml").getInt("General.Particles." + group + ".Amount", 1));
                particleMap.setTimes(ConfigHandler.getConfig("config.yml").getInt("General.Particles." + group + ".Times", 1));
                particleMap.setInterval(ConfigHandler.getConfig("config.yml").getInt("General.Particles." + group + ".Interval", 20));
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
                soundMap.setTimes(ConfigHandler.getConfig("config.yml").getInt("General.Sounds." + group + ".Loop.Times", 1));
                soundMap.setInterval(ConfigHandler.getConfig("config.yml").getInt("General.Sounds." + group + ".Loop.Interval", 20));
                soundProp.put(group, soundMap);
            }
        }
        locationUtils = new LocationUtils();
        blocksUtils = new BlocksUtils();

        menuIJ = ConfigHandler.getConfig("config.yml").getString("General.Menu.ItemJoin");
        menuType = ConfigHandler.getConfig("config.yml").getString("General.Menu.Item.Type");
        menuName = ConfigHandler.getConfig("config.yml").getString("General.Menu.Item.Name");

        vanillaTrans = ConfigHandler.getConfig("config.yml").getString("General.Vanilla-Translate.Local");
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

    public String getMenuIJ() {
        return menuIJ;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getMenuType() {
        return menuType;
    }

    public String getVanillaTrans() {
        return vanillaTrans;
    }

    public LocationUtils getLocationUtils() {
        return locationUtils;
    }

    public BlocksUtils getBlocksUtils() {
        return blocksUtils;
    }

    //  ============================================== //
    //         Message Getter                          //
    //  ============================================== //
    public String getMsgTitle() {
        return msgTitle;
    }

    public String getMsgHelp() {
        return msgHelp;
    }

    public String getMsgReload() {
        return msgReload;
    }

    public String getMsgVersion() {
        return msgVersion;
    }

    //  ============================================== //
    //         Others                                  //
    //  ============================================== //
    public List<String> getTypeList(String file, String path, String listType) {
        List<String> list = new ArrayList<>();
        List<String> customList;
        for (String type : ConfigHandler.getConfig(file).getStringList(path)) {
            try {
                if (listType.equals("Entities")) {
                    list.add(EntityType.valueOf(type).name());
                } else if (listType.equals("Materials")) {
                    list.add(Material.valueOf(type).name());
                }
            } catch (Exception e) {
                customList = ConfigHandler.getConfig("groups.yml").getStringList(listType + "." + type);
                if (customList.isEmpty()) {
                    continue;
                }
                // Add Custom Group.
                for (String customType : customList) {
                    try {
                        if (listType.equals("Entities")) {
                            list.add(EntityType.valueOf(customType).name());
                        } else if (listType.equals("Materials")) {
                            list.add(Material.valueOf(customType).name());
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        return list;
    }
}
