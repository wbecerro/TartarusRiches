package wbe.tartarusRiches.gemTypes;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import wbe.tartarusRiches.TartarusRiches;

import java.util.Random;

public class critic extends GemType {

    private double multiplier;

    public critic(double multiplier) {
        super();
        this.multiplier = multiplier;
    }

    public void applyEffect(Player player, Event event) {
        if(!(event instanceof EntityDamageByEntityEvent)) {
            return;
        }

        double power = getGemPowerValue(player, "critic");
        if(power <= 0) {
            return;
        }

        Random random = new Random();
        if(!(random.nextDouble(100) <= power)) {
            return;
        }

        double damage = ((EntityDamageByEntityEvent) event).getDamage();
        damage = damage * multiplier;

        player.playSound(player.getLocation(), TartarusRiches.config.criticSound, 1F, 1F);
        ((EntityDamageByEntityEvent) event).setDamage(damage);
    }
}
