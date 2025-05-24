package wbe.tartarusRiches.listeners;

import org.bukkit.plugin.PluginManager;
import wbe.tartarusRiches.TartarusRiches;

public class EventListeners {

    public void initializeListeners() {
        TartarusRiches plugin = TartarusRiches.getInstance();
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerInteractListeners(), plugin);
        pluginManager.registerEvents(new BlockBreakListeners(), plugin);
    }
}
