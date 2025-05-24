package wbe.tartarusRiches.gemTypes;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class addDamageMob extends GemType {

    private List<EntityType> entities;

    public addDamageMob(List<EntityType> entities) {
        super();
        this.entities = entities;
    }

    public void applyEffect(Player player, Event event) {
        if(!(event instanceof EntityDamageByEntityEvent)) {
            return;
        }

        double power = getGemPowerValue(player, "adddamagemob");
        if(power <= 0) {
            return;
        }

        double damage = ((EntityDamageByEntityEvent) event).getDamage();
        EntityType eventEntity = ((EntityDamageByEntityEvent) event).getEntity().getType();
        for(EntityType entityType : entities) {
            if(entityType.equals(eventEntity)) {
                damage = damage * (power/100 + 1.0);
                break;
            }
        }

        ((EntityDamageByEntityEvent) event).setDamage(damage);
    }
}
