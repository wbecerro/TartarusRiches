package wbe.tartarusRiches.listeners;

import org.bukkit.plugin.PluginManager;
import wbe.deathoath.listeners.PlayerLoseLifeListeners;
import wbe.tartarusRiches.TartarusRiches;

public class EventListeners {

    public void initializeListeners() {
        TartarusRiches plugin = TartarusRiches.getInstance();
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerInteractListeners(), plugin);
        pluginManager.registerEvents(new BlockBreakListeners(), plugin);
        pluginManager.registerEvents(new PrepareAnvilListeners(), plugin);
        pluginManager.registerEvents(new InventoryClickListeners(), plugin);
        pluginManager.registerEvents(new PlayerReceiveGemListeners(), plugin);
        pluginManager.registerEvents(new CompleteLabourListeners(), plugin);
        pluginManager.registerEvents(new EntityDamageListeners(), plugin);
        pluginManager.registerEvents(new EntityDamageByEntityListeners(), plugin);
        pluginManager.registerEvents(new McMMOPlayerXpGainListeners(), plugin);
        pluginManager.registerEvents(new PlayerExpChangeListeners(), plugin);
        pluginManager.registerEvents(new PlayerLoseLifeListeners(), plugin);
    }
}