package wbe.tartarusRiches.listeners;

import com.gmail.nossr50.mcMMO;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.config.Ore;
import wbe.tartarusRiches.events.PlayerReceiveGemEvent;

import java.util.Collection;
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

        Random random = new Random();
        Player player = event.getPlayer();
        double gemChance = TartarusRiches.utilities.getPlayerGemChance(player);

        if(random.nextDouble(100) <= gemChance) {
            TartarusRiches.getInstance().getServer().getPluginManager().callEvent(new PlayerReceiveGemEvent(player, brokenOre, event.getBlock()));
        }
    }
}
