package wbe.tartarusRiches.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.config.Gem;

public class EntityDamageListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void applyGemstonesEffect(EntityDamageEvent event) {
        if(event.isCancelled()) {
            return;
        }

        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        for(Gem gem : TartarusRiches.config.gemList) {
            gem.getType().applyEffect(player, event);
        }
    }
}
