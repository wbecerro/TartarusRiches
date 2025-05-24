package wbe.tartarusRiches.gemTypes;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class addDamage extends GemType {

    public addDamage() {
        super();
    }

    public void applyEffect(Player player, Event event) {
        if(!(event instanceof EntityDamageByEntityEvent)) {
            return;
        }

        double power = getGemPowerValue(player, "adddamage");
        if(power <= 0) {
            return;
        }

        double damage = ((EntityDamageByEntityEvent) event).getDamage();
        damage = damage * (power/100 + 1.0);

        ((EntityDamageByEntityEvent) event).setDamage(damage);
    }
}
