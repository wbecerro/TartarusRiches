package wbe.tartarusRiches.listeners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
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
        pluginManager.registerEvents(new EntityDamageListeners(), plugin);
        pluginManager.registerEvents(new EntityDamageByEntityListeners(), plugin);
        pluginManager.registerEvents(new PlayerExpChangeListeners(), plugin);

        if(Bukkit.getPluginManager().getPlugin("LaboursOfHercules") != null) {
            pluginManager.registerEvents(new CompleteLabourListeners(), plugin);
        }

        if(Bukkit.getPluginManager().getPlugin("DeathOath") != null) {
            pluginManager.registerEvents(new PlayerLoseLifeListeners(), plugin);
        }

        if(Bukkit.getPluginManager().getPlugin("mcMMO") != null) {
            pluginManager.registerEvents(new McMMOPlayerXpGainListeners(), plugin);
        }
    }
}