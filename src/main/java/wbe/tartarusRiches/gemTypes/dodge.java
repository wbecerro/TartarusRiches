package wbe.tartarusRiches.gemTypes;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Random;

public class dodge extends GemType {

    public dodge() {
        super();
    }

    public void applyEffect(Player player, Event event) {
        if(!(event instanceof EntityDamageEvent)) {
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

        ((EntityDamageEvent) event).setDamage(0);
    }
}
