package wbe.tartarusRiches.gemTypes;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import wbe.tartarusRiches.TartarusRiches;

import java.util.Random;

public class dodge extends GemType {

    public dodge(String id) {
        super(id);
    }

    public void applyEffect(Player player, Event event) {
        if(!(event instanceof EntityDamageEvent)) {
            return;
        }

        if(!(((EntityDamageEvent) event).getEntity() instanceof Player)) {
            return;
        }

        double power = getGemPowerValue(player, "dodge");
        if(power <= 0) {
            return;
        }

        Random random = new Random();
        if(!(random.nextDouble(100) <= power)) {
            return;
        }

        player.playSound(player.getLocation(), TartarusRiches.config.dodgeSound, 1F, 1F);
        ((EntityDamageEvent) event).setCancelled(true);
    }
}
