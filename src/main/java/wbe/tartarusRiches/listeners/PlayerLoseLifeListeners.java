package wbe.tartarusRiches.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import wbe.deathoath.events.PlayerLoseLifeEvent;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.config.Gem;

public class PlayerLoseLifeListeners implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void applyGemstonesEffect(PlayerLoseLifeEvent event) {
        Player player = event.getPlayer();

        for(Gem gem : TartarusRiches.utilities.getPlayerAppliedGems(player.getInventory())) {
            gem.getType().applyEffect(player, event);
        }
    }
}
