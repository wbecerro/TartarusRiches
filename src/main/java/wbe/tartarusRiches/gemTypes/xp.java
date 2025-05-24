package wbe.tartarusRiches.gemTypes;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class xp extends GemType {

    public xp() {
        super();
    }

    public void applyEffect(Player player, Event event) {
        if(!(event instanceof PlayerExpChangeEvent)) {
            return;
        }

        double power = getGemPowerValue(player, "xp");
        if(power <= 0) {
            return;
        }

        int xp = ((PlayerExpChangeEvent) event).getAmount();
        xp = (int) (xp * power/100 + 1);
        ((PlayerExpChangeEvent) event).setAmount(xp);
    }
}
