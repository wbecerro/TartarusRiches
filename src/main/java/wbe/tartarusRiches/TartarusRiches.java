package wbe.tartarusRiches;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import wbe.tartarusRiches.commands.CommandListener;
import wbe.tartarusRiches.commands.TabListener;
import wbe.tartarusRiches.config.Config;
import wbe.tartarusRiches.config.Messages;
import wbe.tartarusRiches.config.SackGem;
import wbe.tartarusRiches.listeners.EventListeners;
import wbe.tartarusRiches.papi.PapiExtension;
import wbe.tartarusRiches.utils.Scheduler;
import wbe.tartarusRiches.utils.Utilities;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public final class TartarusRiches extends JavaPlugin {

    private FileConfiguration configuration;

    private CommandListener commandListener;

    private TabListener tabListener;

    private EventListeners eventListeners;

    private PapiExtension papiExtension;

    public static Config config;

    public static Messages messages;

    public static Utilities utilities;

    public static HashMap<Player, List<SackGem>> playerSacks = new HashMap<>();

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
        tabListener = new TabListener();
        getCommand("tartarusriches").setTabCompleter(tabListener);
        eventListeners = new EventListeners();
        eventListeners.initializeListeners();
        Scheduler.startSchedulers();
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        reloadConfig();
        for(Player player : playerSacks.keySet()) {
            utilities.savePlayerData(player);
        }

        getLogger().info("Tartarus' Riches disabled correctly.");
    }

    public static TartarusRiches getInstance() {
        return getPlugin(TartarusRiches.class);
    }

    public void reloadConfiguration() {
        if(!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }

        new File(getDataFolder(), "saves").mkdir();
        for(Player player : playerSacks.keySet()) {
            utilities.savePlayerData(player);
        }

        reloadConfig();
        configuration = getConfig();
        config = new Config(configuration);
        messages = new Messages(configuration);
        utilities = new Utilities();
    }
}
