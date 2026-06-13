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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.config.Gem;
import wbe.tartarusRiches.config.SackGem;
import wbe.tartarusRiches.items.Gemstone;

import java.util.ArrayList;
import java.util.List;

public class MenuListener implements Listener {

    private static void fillBorders(Inventory inventory, int page) {
        ItemStack borde = new ItemStack(TartarusRiches.config.borderMaterial);
        ItemMeta bordeMeta = borde.getItemMeta();
        NamespacedKey currentPage = new NamespacedKey(TartarusRiches.getInstance(), "currentPage");
        bordeMeta.setDisplayName(" ");
        bordeMeta.getPersistentDataContainer().set(currentPage, PersistentDataType.INTEGER, page);
        borde.setItemMeta(bordeMeta);

        for(int i = 0; i < inventory.getSize(); i++) {
            // Primera fila
            if(i<9) {
                inventory.setItem(i, borde);
            }
            // Columna izquierda
            if(i % 9 == 0) {
                inventory.setItem(i, borde);
            }
            // Columna derecha
            if(i % 9 == 8) {
                inventory.setItem(i, borde);
            }
            // Última fila
            if(i >= 45){
                inventory.setItem(i, borde);
            }
        }
    }

    public static void fillGems(Inventory inventory, int page, List<SackGem> gems) {
        int maxGemsToShow = 28*page;
        int gemIndex = 28*(page-1);
        if(28*page > gems.size()) {
            maxGemsToShow = gems.size();
        }

        for(int i = 0; i < inventory.getSize(); i++) {
            // Primera fila
            if(i<9) {
                continue;
            }
            // Columna izquierda
            if(i % 9 == 0) {
                continue;
            }
            // Columna derecha
            if(i % 9 == 8) {
                continue;
            }
            // Última fila
            if(i >= 45){
                continue;
            }

            if(gemIndex < maxGemsToShow) {
                SackGem sackGem = gems.get(gemIndex);
                ItemStack gemItem = sackGem.getMenuItem();
                inventory.setItem(i, gemItem);
                gemIndex++;
            }
        }
    }

    public static void openMenu(Player player, int page) throws Exception {
        List<SackGem> gems = TartarusRiches.playerSacks.get(player);
        if(gems.size() == 0) {
            throw new Exception(TartarusRiches.messages.noGemsFound);
        }

        int necesaryPages = (int) Math.ceil((double) gems.size() / 28);
        if(page > necesaryPages) {
            throw new Exception(TartarusRiches.messages.pageNotFound);
        }
        NamespacedKey goToPage = new NamespacedKey(TartarusRiches.getInstance(), "goToPage");

        Inventory inventory = Bukkit.createInventory(null, 54, TartarusRiches.config.menuTitle);
        fillBorders(inventory, page);
        fillGems(inventory, page, gems);

        if(necesaryPages > page) {
            ItemStack nextPage = new ItemStack(Material.ARROW);
            ItemMeta nextPageMeta = nextPage.getItemMeta();
            nextPageMeta.setDisplayName(
                TartarusRiches.messages.menuNextPage.replace("%next_page%", String.valueOf(page+1))
            );
            nextPageMeta.getPersistentDataContainer().set(goToPage, PersistentDataType.INTEGER, page+1);
            nextPage.setItemMeta(nextPageMeta);
            inventory.setItem(53, nextPage);
        }

        if(page > 1) {
            ItemStack backPage = new ItemStack(Material.ARROW);
            ItemMeta backPageMeta = backPage.getItemMeta();
            backPageMeta.setDisplayName(
                TartarusRiches.messages.menuPreviousPage.replace("%previous_page%", String.valueOf(page-1))
            );
            backPageMeta.getPersistentDataContainer().set(goToPage, PersistentDataType.INTEGER, page-1);

            backPage.setItemMeta(backPageMeta);
            inventory.setItem(45, backPage);
        }

        player.openInventory(inventory);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack bordeItem = event.getInventory().getItem(0);
        Inventory inventory = event.getInventory();
        if(bordeItem == null) {
            return;
        }
        NamespacedKey currentPageKey = new NamespacedKey(TartarusRiches.getInstance(), "currentPage");

        if(!bordeItem.getItemMeta().getPersistentDataContainer().has(currentPageKey)) {
            return;
        }

        int currentPage = bordeItem.getItemMeta().getPersistentDataContainer().get(
            currentPageKey, PersistentDataType.INTEGER
        );

        if(!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) {
            return;
        }
        NamespacedKey goToPage = new NamespacedKey(TartarusRiches.getInstance(), "goToPage");
        NamespacedKey typeKey = new NamespacedKey(TartarusRiches.getInstance(), "menuGemType");

        // Clic en flecha de cambio de página
        ItemMeta meta = item.getItemMeta();
        if(meta.getPersistentDataContainer().has(goToPage)) {
            int page = meta.getPersistentDataContainer().get(goToPage, PersistentDataType.INTEGER);
            try{
                openMenu(player, page);
            } catch(Exception e){
                player.sendMessage(TartarusRiches.messages.pageNotFound);
            }
        }

        // Clic en gema del menú
        if(meta.getPersistentDataContainer().has(typeKey)) {
            String gemId = meta.getPersistentDataContainer().get(typeKey, PersistentDataType.STRING);
            Gem gem = TartarusRiches.config.gems.get(gemId);
            SackGem clickedGem = TartarusRiches.utilities.getSackGem(player, gemId);
            if(clickedGem == null) {
                event.setCancelled(true);
                return;
            }

            NamespacedKey effectivenessKey = new NamespacedKey(TartarusRiches.getInstance(), "menuEffectiveness");
            double accumulatedEffectiveness = meta.getPersistentDataContainer().get(effectivenessKey, PersistentDataType.DOUBLE);
            double effectiveness = Math.min(gem.getMax(), accumulatedEffectiveness);

            if(accumulatedEffectiveness - effectiveness <= 0) {
                TartarusRiches.playerSacks.get(player).remove(clickedGem);
            } else {
                clickedGem.setEffectiveness(clickedGem.getEffectiveness() - effectiveness);
            }

            try {
                event.setCancelled(true);
                TartarusRiches.utilities.addItemToInventory(player, new Gemstone(gem, effectiveness));
                openMenu(player, currentPage);
            } catch(Exception e){
                player.sendMessage(TartarusRiches.messages.pageNotFound);
                player.closeInventory();
            }
        }

        // Clic en gema del inventario
        NamespacedKey gemTypeKey = new NamespacedKey(TartarusRiches.getInstance(), "gemType");
        NamespacedKey gemEffectivenessKey = new NamespacedKey(TartarusRiches.getInstance(), "effectiveness");
        if(meta.getPersistentDataContainer().has(gemTypeKey)) {
            String gemId = meta.getPersistentDataContainer().get(gemTypeKey, PersistentDataType.STRING);
            Gem gem = TartarusRiches.config.gems.get(gemId);
            if(gem == null) {
                event.setCancelled(true);
                return;
            }

            double effectiveness = meta.getPersistentDataContainer().get(gemEffectivenessKey, PersistentDataType.DOUBLE);
            SackGem sackGem = TartarusRiches.utilities.getSackGem(player, gem.getId());
            if(sackGem == null) {
                sackGem = new SackGem(gem, effectiveness);
                TartarusRiches.playerSacks.get(player).add(sackGem);
            } else {
                sackGem.setEffectiveness(sackGem.getEffectiveness() + effectiveness);
            }

            player.playSound(player, TartarusRiches.config.addGemToSackSound, 1f, 1f);

            try {
                event.setCancelled(true);
                item.setAmount(item.getAmount() - 1);
                event.setCurrentItem(item);
                openMenu(player, currentPage);
            } catch(Exception e){
                player.sendMessage(TartarusRiches.messages.pageNotFound);
                player.closeInventory();
            }
        }

        event.setCancelled(true);
    }
}
