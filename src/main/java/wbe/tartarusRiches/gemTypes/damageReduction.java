package wbe.tartarusRiches.gemTypes;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

public class damageReduction extends GemType {

    public damageReduction(String id) {
        super(id);
    }

    public void applyEffect(Player player, Event event) {
        if(!(event instanceof EntityDamageEvent)) {
            return;
        }

        if(!(((EntityDamageEvent) event).getEntity() instanceof Player)) {
            return;
        }

        double power = getGemPowerValue(player, "damagereduction");
        if(power <= 0) {
            return;
        }

        double damage = ((EntityDamageEvent) event).getDamage();
        damage = damage * (1 - power/100);

        ((EntityDamageEvent) event).setDamage(damage);

    }
}
