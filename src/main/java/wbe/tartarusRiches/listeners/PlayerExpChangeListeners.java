package wbe.tartarusRiches.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.config.Gem;

public class PlayerExpChangeListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void applyGemstonesEffect(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();

        for(Gem gem : TartarusRiches.config.gemList) {
            gem.getType().applyEffect(player, event);
        }
    }
}
