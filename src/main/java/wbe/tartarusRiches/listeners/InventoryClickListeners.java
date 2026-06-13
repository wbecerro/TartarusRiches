package wbe.tartarusRiches.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.config.Gem;
import wbe.tartarusRiches.config.SackGem;
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

        if(!event.getClick().equals(ClickType.LEFT)) {
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

        boolean correct = TartarusRiches.utilities.applyGem(gem, newItem, effectiveness, player, false);
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

    @EventHandler(priority = EventPriority.NORMAL)
    public void addGemsToSack(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(!event.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)) {
            return;
        }

        if(!event.getClick().equals(ClickType.RIGHT)) {
            return;
        }

        ItemStack gemItem = event.getCursor();
        ItemStack sackItem = event.getCurrentItem();
        ItemMeta meta = gemItem.getItemMeta();
        if(meta == null) {
            return;
        }

        NamespacedKey typeKey = new NamespacedKey(TartarusRiches.getInstance(), "gemType");
        NamespacedKey effectivenessKey = new NamespacedKey(TartarusRiches.getInstance(), "effectiveness");
        NamespacedKey sackKey = new NamespacedKey(TartarusRiches.getInstance(), "gemsack");
        // Cursor es gema
        if(!meta.getPersistentDataContainer().has(typeKey)) {
            return;
        }

        // Current item es saco
        if(!TartarusRiches.utilities.checkItem(sackItem, sackKey)) {
            return;
        }

        Gem gem = TartarusRiches.config.gems.get(meta.getPersistentDataContainer().get(typeKey, PersistentDataType.STRING));
        double effectiveness = meta.getPersistentDataContainer().get(effectivenessKey, PersistentDataType.DOUBLE);
        SackGem sackGem = TartarusRiches.utilities.getSackGem(player, gem.getId());
        if(sackGem == null) {
            sackGem = new SackGem(gem, effectiveness);
            TartarusRiches.playerSacks.get(player).add(sackGem);
        } else {
            sackGem.setEffectiveness(sackGem.getEffectiveness() + effectiveness);
        }

        player.playSound(player, TartarusRiches.config.addGemToSackSound, 1f, 1f);

        gemItem.setAmount(gemItem.getAmount() - 1);
        player.setItemOnCursor(gemItem);
        event.setCancelled(true);
        player.updateInventory();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void applyExtraSlots(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(!event.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)) {
            return;
        }

        ItemStack extraSlotItem = event.getCursor();
        ItemMeta meta = extraSlotItem.getItemMeta();
        if(meta == null) {
            return;
        }

        NamespacedKey extraSlotKey = new NamespacedKey(TartarusRiches.getInstance(), "extraSlotItem");
        if(!meta.getPersistentDataContainer().has(extraSlotKey)) {
            return;
        }

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

        int slots = TartarusRiches.utilities.getItemMaxSlots(newItem) + 1;
        TartarusRiches.utilities.changeMaxSlots(newItem, slots, player);

        extraSlotItem.setAmount(extraSlotItem.getAmount() - 1);
        event.setCurrentItem(newItem);
        player.setItemOnCursor(extraSlotItem);
        event.setCancelled(true);
        player.updateInventory();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void cancelGemSackBundleFunctions(InventoryClickEvent event) {
        if(event.getAction().equals(InventoryAction.valueOf("PICKUP_ALL_INTO_BUNDLE")) ||
            event.getAction().equals(InventoryAction.valueOf("PICKUP_SOME_INTO_BUNDLE")) ||
            event.getAction().equals(InventoryAction.valueOf("PICKUP_FROM_BUNDLE")) ||
            event.getAction().equals(InventoryAction.valueOf("PLACE_ALL_INTO_BUNDLE")) ||
            event.getAction().equals(InventoryAction.valueOf("PLACE_FROM_BUNDLE"))||
            event.getAction().equals(InventoryAction.valueOf("PLACE_SOME_INTO_BUNDLE"))) {
                ItemStack currentItem = event.getCurrentItem();
                ItemStack cursor = event.getCursor();
                NamespacedKey sackKey = new NamespacedKey(TartarusRiches.getInstance(), "gemsack");
                if(!TartarusRiches.utilities.checkItem(currentItem, sackKey)) {
                    if(!TartarusRiches.utilities.checkItem(cursor, sackKey)) {
                        return;
                    }
                }

                event.setCancelled(true);
        } else if((event.getAction().toString().contains("PICKUP") || event.getAction().equals(InventoryAction.NOTHING)) && event.getClick().equals(ClickType.RIGHT)) {
            ItemStack currentItem = event.getCurrentItem();
            NamespacedKey sackKey = new NamespacedKey(TartarusRiches.getInstance(), "gemsack");
            if(!TartarusRiches.utilities.checkItem(currentItem, sackKey)) {
                return;
            }

            event.setCancelled(true);
            try {
                MenuListener.openMenu((Player) event.getWhoClicked(), 1);
            } catch(Exception e) {
                event.getWhoClicked().sendMessage(e.getMessage());
            }
        }
    }
}
