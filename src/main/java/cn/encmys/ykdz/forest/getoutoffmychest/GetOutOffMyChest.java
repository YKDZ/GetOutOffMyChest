package cn.encmys.ykdz.forest.getoutoffmychest;

import cn.encmys.ykdz.forest.getoutoffmychest.listener.ChestListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Main class of the plugin
 * @author - YK_DZ
 */
public class GetOutOffMyChest extends JavaPlugin {

    private static GetOutOffMyChest instance;
    private static File fileConfig;
    private static YamlConfiguration config;

    @Override
    public void onLoad() {
        getLogger().info("Loading GetOutOffMyChest...");
    }

    @Override
    public void onEnable() {
        instance = this;
        createConfig();

        Bukkit.getPluginManager().registerEvents(new ChestListener(), instance);

        getLogger().info("Enabling GetOutOffMyChest...");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling GetOutOffMyChest...");
    }

    public static FileConfiguration getMainConfig() {
        return config;
    }

    public static GetOutOffMyChest getInstance() {
        return instance;
    }

    public void createConfig() {

        fileConfig = new File(getDataFolder(),"config.yml");
        config = new YamlConfiguration();

        if (!fileConfig.exists()) {
            fileConfig.getParentFile().mkdirs();
            saveResource("config.yml",false);
        }

        try {
            config.load(fileConfig);
        } catch (IOException | InvalidConfigurationException error) {
            error.printStackTrace();
        }
    }

}