package wbe.tartarusRiches.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.config.Ore;
import wbe.tartarusRiches.events.PlayerReceiveGemEvent;

import java.util.Random;

public class PlayerReceiveGemListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void manageGemDistribution(PlayerReceiveGemEvent event) {
        if(event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        Random random = new Random();
        double doubleChance = TartarusRiches.utilities.getPlayerDoubleChance(player);
        Ore brokenOre = event.getOre();

        player.playSound(player.getLocation(), TartarusRiches.config.gemDropSound, 1F, 1F);
        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), TartarusRiches.utilities.generateGemstone(brokenOre.getRandomReward()));
        if(random.nextDouble(100) <= doubleChance) {
            player.sendMessage(TartarusRiches.messages.doubleDrop);
            player.playSound(player.getLocation(), TartarusRiches.config.doubleDropSound, 1F, 1F);
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), TartarusRiches.utilities.generateGemstone(brokenOre.getRandomReward()));
        }
    }
}
