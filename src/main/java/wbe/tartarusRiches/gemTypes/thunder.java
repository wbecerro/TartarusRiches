package wbe.tartarusRiches.gemTypes;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

public class thunder extends GemType {

    private double damage;

    public thunder(String id, double damage) {
        super(id);
        this.damage = damage;
    }

    public void applyEffect(Player player, Event event) {
        if(!(event instanceof EntityDamageByEntityEvent)) {
            return;
        }

        if(!(((EntityDamageByEntityEvent) event).getDamager() instanceof Player)) {
            return;
        }

        double power = getGemPowerValue(player, "thunder");
        if(power <= 0) {
            return;
        }

        Random random = new Random();
        if(!(random.nextDouble(100) <= power)) {
            return;
        }

        double damage = ((EntityDamageByEntityEvent) event).getDamage();
        damage = damage + this.damage;
        ((EntityDamageByEntityEvent) event).setDamage(damage);
        Entity entity = ((EntityDamageByEntityEvent) event).getEntity();
        entity.getWorld().strikeLightningEffect(entity.getLocation());
    }
}
