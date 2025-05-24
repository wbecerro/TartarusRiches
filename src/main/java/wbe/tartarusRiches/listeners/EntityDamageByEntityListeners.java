package wbe.tartarusRiches.listeners;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.config.Gem;

public class EntityDamageByEntityListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void applyGemstonesEffect(EntityDamageByEntityEvent event) {
        if(event.isCancelled()) {
            return;
        }

        if(!(event.getDamager() instanceof Player)) {
            return;
        }

        if(!(event.getEntity() instanceof LivingEntity)) {
            return;
        }

        Player player = (Player) event.getDamager();

        if(event.getDamage() <= 0.0D) {
            return;
        }

        if(player.getAttackCooldown() < 0.3) {
            return;
        }

        for(Gem gem : TartarusRiches.config.gemList) {
            gem.getType().applyEffect(player, event);
        }
    }
}
