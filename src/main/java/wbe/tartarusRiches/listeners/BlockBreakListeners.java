package wbe.tartarusRiches.listeners;

import com.gmail.nossr50.mcMMO;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.config.Gem;
import wbe.tartarusRiches.config.Ore;
import wbe.tartarusRiches.events.PlayerReceiveGemEvent;
import wbe.tartarusRiches.items.ExtraSlotItem;

import java.util.Random;

public class BlockBreakListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void giveGemsOnBreakingOres(BlockBreakEvent event) {
        if(event.isCancelled()) {
            return;
        }

        if(mcMMO.getUserBlockTracker().isIneligible(event.getBlock().getState())) {
            return;
        }

        Random random = new Random();
        if(random.nextDouble(100) < TartarusRiches.config.extraSlotItemChance) {
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ExtraSlotItem());
        }

        Material material = event.getBlock().getType();
        Ore brokenOre = null;
        for(Ore ore : TartarusRiches.config.ores) {
            if(ore.getId().equals(material)) {
                brokenOre = ore;
            }
        }

        if(brokenOre == null) {
            return;
        }

        Player player = event.getPlayer();
        double gemChance = TartarusRiches.utilities.getPlayerGemChance(player);

        if(random.nextDouble(100) <= gemChance) {
            TartarusRiches.getInstance().getServer().getPluginManager().callEvent(new PlayerReceiveGemEvent(player, brokenOre, event.getBlock()));
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void applyGemstonesEffect(BlockBreakEvent event) {
        if(event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();

        for(Gem gem : TartarusRiches.utilities.getPlayerAppliedGems(player.getInventory())) {
            gem.getType().applyEffect(player, event);
        }
    }
}
