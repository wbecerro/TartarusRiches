package wbe.tartarusRiches.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import wbe.tartarusRiches.TartarusRiches;

public class PlayerJoinListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleGemDataOnJoin(PlayerJoinEvent event) {
        TartarusRiches.utilities.loadPlayerData(event.getPlayer());
    }
}
