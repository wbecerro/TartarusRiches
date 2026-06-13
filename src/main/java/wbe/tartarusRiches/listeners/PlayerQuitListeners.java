package wbe.tartarusRiches.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import wbe.tartarusRiches.TartarusRiches;

public class PlayerQuitListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleGemDataOnQuit(PlayerQuitEvent event) {
        TartarusRiches.utilities.savePlayerData(event.getPlayer());
    }
}
