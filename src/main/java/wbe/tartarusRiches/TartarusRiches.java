package wbe.tartarusRiches;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import wbe.tartarusRiches.commands.CommandListener;
import wbe.tartarusRiches.config.Config;
import wbe.tartarusRiches.config.Messages;
import wbe.tartarusRiches.listeners.EventListeners;
import wbe.tartarusRiches.papi.PapiExtension;
import wbe.tartarusRiches.utils.Utilities;

import java.io.File;

public final class TartarusRiches extends JavaPlugin {

    private FileConfiguration configuration;

    private CommandListener commandListener;

    private EventListeners eventListeners;

    private PapiExtension papiExtension;

    public static Config config;

    public static Messages messages;

    public static Utilities utilities;

    @Override
    public void onEnable() {
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            papiExtension = new PapiExtension();
            papiExtension.register();
        }
        saveDefaultConfig();
        getLogger().info("Tartarus' Riches enabled correctly.");
        reloadConfiguration();

        commandListener = new CommandListener();
        getCommand("tartarusriches").setExecutor(commandListener);
        eventListeners = new EventListeners();
        eventListeners.initializeListeners();
    }

    @Override
    public void onDisable() {
        reloadConfig();
        getLogger().info("Tartarus' Riches disabled correctly.");
    }

    public static TartarusRiches getInstance() {
        return getPlugin(TartarusRiches.class);
    }

    public void reloadConfiguration() {
        if(!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }

        reloadConfig();
        configuration = getConfig();
        config = new Config(configuration);
        messages = new Messages(configuration);
        utilities = new Utilities();
    }
}
