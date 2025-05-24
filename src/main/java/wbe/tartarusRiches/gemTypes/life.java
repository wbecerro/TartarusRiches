package wbe.tartarusRiches.gemTypes;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import wbe.deathoath.events.PlayerLoseLifeEvent;

import java.util.Random;

public class life extends GemType {

    public life() {
        super();
    }

    public void applyEffect(Player player, Event event) {
        if(!(event instanceof PlayerLoseLifeEvent)) {
            return;
        }

        double power = getGemPowerValue(player, "life");
        if(power <= 0) {
            return;
        }

        Random random = new Random();
        if(!(random.nextDouble(100) <= power)) {
            return;
        }

        ((PlayerLoseLifeEvent) event).setCancelled(true);
    }
}
