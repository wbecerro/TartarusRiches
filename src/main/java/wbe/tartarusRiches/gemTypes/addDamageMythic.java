package wbe.tartarusRiches.gemTypes;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class addDamageMythic extends GemType {

    public addDamageMythic() {
        super();
    }

    public void applyEffect(Player player, Event event) {
        /*if(!(event instanceof EntityDamageByEntityEvent)) {
            return;
        }

        double power = getGemPowerValue(player, "adddamagemythic");
        if(power <= 0) {
            return;
        }

        Entity entity = ((EntityDamageByEntityEvent) event).getEntity();
        if(!MythicBukkit.inst().getMobManager().isMythicMob(entity)) {
            return;
        }

        double damage = ((EntityDamageByEntityEvent) event).getDamage();
        damage = damage * (power/100 + 1.0);

        ((EntityDamageByEntityEvent) event).setDamage(damage);*/
    }
}
