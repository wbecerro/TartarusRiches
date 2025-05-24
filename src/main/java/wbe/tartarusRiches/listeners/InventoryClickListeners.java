package wbe.tartarusRiches.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.config.Gem;
import wbe.tartarusRiches.items.Gemstone;

public class InventoryClickListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onGemstoneUpgrade(InventoryClickEvent event) {
        if(!(event.getInventory() instanceof AnvilInventory)) {
            return;
        }

        if(event.getSlotType() != InventoryType.SlotType.RESULT) {
            return;
        }

        ItemStack first = event.getInventory().getItem(0);
        ItemStack second = event.getInventory().getItem(1);
        NamespacedKey typeKey = new NamespacedKey(TartarusRiches.getInstance(), "gemType");

        if(!TartarusRiches.utilities.checkItem(first, typeKey)) {
            return;
        }

        if(!TartarusRiches.utilities.checkItem(second, typeKey)) {
            return;
        }

        ItemMeta firstMeta = first.getItemMeta();
        ItemMeta secondMeta = second.getItemMeta();
        if(!firstMeta.getPersistentDataContainer().get(typeKey, PersistentDataType.STRING)
                .equalsIgnoreCase(secondMeta.getPersistentDataContainer().get(typeKey, PersistentDataType.STRING))) {
            return;
        }

        Gem gem = TartarusRiches.config.gems.get(firstMeta.getPersistentDataContainer().get(typeKey, PersistentDataType.STRING));
        NamespacedKey effectivenessKey = new NamespacedKey(TartarusRiches.getInstance(), "effectiveness");
        double firstEffectiveness = firstMeta.getPersistentDataContainer().get(effectivenessKey, PersistentDataType.DOUBLE);
        double secondEffectiveness = secondMeta.getPersistentDataContainer().get(effectivenessKey, PersistentDataType.DOUBLE);

        double newEffectiveness = firstEffectiveness + secondEffectiveness;
        if(newEffectiveness > gem.getMax()) {
            newEffectiveness = gem.getMax();
        }

        Gemstone newGemstone = new Gemstone(gem, newEffectiveness);
        Player player = (Player) event.getWhoClicked();
        // Si el jugador tiene algo en la mano al coger la gema, ese objeto se suelta
        if(!player.getItemOnCursor().getType().equals(Material.AIR)) {
            player.getWorld().dropItem(player.getLocation(), player.getItemOnCursor());
        }

        event.setCancelled(true);
        event.getInventory().setItem(0, null);
        event.getInventory().setItem(1, null);

        player.setItemOnCursor(newGemstone);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void applyGem(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(!event.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)) {
            return;
        }

        ItemStack gemItem = event.getCursor();
        ItemMeta meta = gemItem.getItemMeta();
        if(meta == null) {
            return;
        }

        NamespacedKey typeKey = new NamespacedKey(TartarusRiches.getInstance(), "gemType");
        NamespacedKey effectivenessKey = new NamespacedKey(TartarusRiches.getInstance(), "effectiveness");
        if(!meta.getPersistentDataContainer().has(typeKey)) {
            return;
        }

        Gem gem = TartarusRiches.config.gems.get(meta.getPersistentDataContainer().get(typeKey, PersistentDataType.STRING));
        double effectiveness = meta.getPersistentDataContainer().get(effectivenessKey, PersistentDataType.DOUBLE);

        ItemStack inventoryItem = event.getCurrentItem();
        ItemMeta inventoryItemMeta = inventoryItem.getItemMeta();
        if(inventoryItemMeta == null) {
            inventoryItemMeta = Bukkit.getItemFactory().getItemMeta(inventoryItem.getType());
        }

        if(inventoryItem.getAmount() != 1) {
            return;
        }

        String name = "";
        if(!inventoryItemMeta.hasDisplayName()) {
            name = inventoryItemMeta.getItemName();
        } else {
            name = inventoryItemMeta.getDisplayName();
        }

        inventoryItemMeta.setDisplayName(name);
        inventoryItem.setItemMeta(inventoryItemMeta);

        ItemStack newItem = new ItemStack(inventoryItem.getType());
        newItem.setItemMeta(inventoryItemMeta);
        newItem.getItemMeta().setLore(inventoryItemMeta.getLore());

        boolean correct = TartarusRiches.utilities.applyGem(gem, newItem, effectiveness, player);
        if(!correct) {
            event.setCancelled(true);
            return;
        }

        gemItem.setAmount(gemItem.getAmount() - 1);
        event.setCurrentItem(newItem);
        player.setItemOnCursor(gemItem);
        event.setCancelled(true);
        player.updateInventory();
    }
}
