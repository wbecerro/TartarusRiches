package wbe.tartarusRiches.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.items.Gemstone;

public class PlayerInteractListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void cancelInteractionOfGem(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(!event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                return;
            }
        }

        ItemStack item = event.getItem();
        NamespacedKey typeKey = new NamespacedKey(TartarusRiches.getInstance(), "gemType");
        if(!TartarusRiches.utilities.checkItem(item, typeKey)) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void openClusterOnInteract(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            return;
        }

        ItemStack item = event.getItem();
        NamespacedKey clusterKey = new NamespacedKey(TartarusRiches.getInstance(), "cluster");
        if(!TartarusRiches.utilities.checkItem(item, clusterKey)) {
            return;
        }

        event.setCancelled(true);
        Player player = event.getPlayer();
        for(Gemstone gemstone : TartarusRiches.utilities.getRandomGemstones()) {
            TartarusRiches.utilities.addItemToInventory(player, gemstone);
        }

        item.setAmount(item.getAmount() - 1);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void openGemSackOnInteract(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack item = event.getItem();
            NamespacedKey sackKey = new NamespacedKey(TartarusRiches.getInstance(), "gemsack");
            if(!TartarusRiches.utilities.checkItem(item, sackKey)) {
                return;
            }

            event.setCancelled(true);
            try {
                MenuListener.openMenu(event.getPlayer(), 1);
            } catch(Exception e) {
                event.getPlayer().sendMessage(e.getMessage());
            }
        }
    }
}
