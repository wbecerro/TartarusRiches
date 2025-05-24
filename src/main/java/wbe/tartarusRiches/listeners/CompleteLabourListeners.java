package wbe.tartarusRiches.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import wbe.laboursOfHercules.events.CompleteLabourEvent;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.config.Gem;

public class CompleteLabourListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void applyGemstonesEffect(CompleteLabourEvent event) {
        if(event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();

        for(Gem gem : TartarusRiches.config.gemList) {
            gem.getType().applyEffect(player, event);
        }
    }
}
