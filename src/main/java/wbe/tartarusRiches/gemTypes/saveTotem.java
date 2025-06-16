package wbe.tartarusRiches.gemTypes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;
import wbe.tartarusRiches.TartarusRiches;

import java.util.Random;

public class saveTotem extends GemType {

    public saveTotem(String id) {
        super(id);
    }

    public void applyEffect(Player player, Event event) {
        if(!(event instanceof EntityResurrectEvent)) {
            return;
        }

        if(!(((EntityResurrectEvent) event).getEntity() instanceof Player)) {
            return;
        }

        double power = getGemPowerValue(player, "savetotem");
        if(power <= 0) {
            return;
        }

        Random random = new Random();
        if(!(random.nextDouble(100) <= power)) {
            return;
        }

        Bukkit.getScheduler().runTaskLater(TartarusRiches.getInstance(), () -> {
            player.getInventory().addItem(new ItemStack(Material.TOTEM_OF_UNDYING, 1));
        }, 1L);
    }
}
