package wbe.tartarusRiches.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.config.Gem;
import wbe.tartarusRiches.items.Gemstone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Utilities {

    private TartarusRiches plugin;

    public Utilities() {
        plugin = TartarusRiches.getInstance();
    }

    public void addGemChance(ItemStack item, double chance) {
        NamespacedKey baseItemKey = new NamespacedKey(plugin, "gemChance");
        String loreLine = TartarusRiches.config.pickaxeGemChance
                .replace("%gem_chance%", String.valueOf(chance));
        ItemMeta meta = item.getItemMeta();

        if(meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }

        List<String> lore = new ArrayList<>();
        if(meta.hasLore()) {
            lore = meta.getLore();
        }

        lore.add(loreLine);
        meta.setLore(lore);

        meta.getPersistentDataContainer().set(baseItemKey, PersistentDataType.DOUBLE, chance);
        item.setItemMeta(meta);
    }

    public void addDoubleDropChance(ItemStack item, double chance) {
        NamespacedKey baseDoubleKey = new NamespacedKey(plugin, "doubleChance");
        String loreLine = TartarusRiches.config.pickaxeDoubleChance
                .replace("%double_chance%", String.valueOf(chance));
        ItemMeta meta = item.getItemMeta();

        if(meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }

        List<String> lore = new ArrayList<>();
        if(meta.hasLore()) {
            lore = meta.getLore();
        }

        lore.add(loreLine);
        meta.setLore(lore);

        meta.getPersistentDataContainer().set(baseDoubleKey, PersistentDataType.DOUBLE, chance);
        item.setItemMeta(meta);
    }

    private double getItemGemChance(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return 0;
        }

        NamespacedKey baseDoubleKey = new NamespacedKey(plugin, "gemChance");
        if(meta.getPersistentDataContainer().has(baseDoubleKey)) {
            return meta.getPersistentDataContainer().get(baseDoubleKey, PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public double getPlayerGemChance(Player player) {
        double chance = TartarusRiches.config.baseGemChance;

        PlayerInventory inventory = player.getInventory();
        ItemStack mainHand = inventory.getItemInMainHand();
        ItemStack offHand = inventory.getItemInOffHand();
        ItemStack[] armor = inventory.getArmorContents();

        if(!mainHand.getType().equals(Material.AIR)) {
            chance += getItemGemChance(mainHand);
        }

        if(!offHand.getType().equals(Material.AIR)) {
            chance += getItemGemChance(offHand);
        }

        for(ItemStack item : armor) {
            if(item == null) {
                continue;
            }
            chance += getItemGemChance(item);
        }

        return chance;
    }

    private double getItemDoubleChance(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return 0;
        }

        NamespacedKey baseDoubleKey = new NamespacedKey(plugin, "doubleChance");
        if(meta.getPersistentDataContainer().has(baseDoubleKey)) {
            return meta.getPersistentDataContainer().get(baseDoubleKey, PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public double getPlayerDoubleChance(Player player) {
        double chance = TartarusRiches.config.baseDoubleChance;

        PlayerInventory inventory = player.getInventory();
        ItemStack mainHand = inventory.getItemInMainHand();
        ItemStack offHand = inventory.getItemInOffHand();
        ItemStack[] armor = inventory.getArmorContents();

        if(!mainHand.getType().equals(Material.AIR)) {
            chance += getItemDoubleChance(mainHand);
        }

        if(!offHand.getType().equals(Material.AIR)) {
            chance += getItemDoubleChance(offHand);
        }

        for(ItemStack item : armor) {
            if(item == null) {
                continue;
            }
            chance += getItemDoubleChance(item);
        }

        return chance;
    }

    public boolean checkItem(ItemStack item, NamespacedKey key) {
        if(item == null || item.getType().equals(Material.AIR)) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return false;
        }

        if(!meta.getPersistentDataContainer().has(key)) {
            return false;
        }

        return true;
    }

    public List<Gemstone> getRandomGemstones() {
        int size = TartarusRiches.config.gemList.size();
        Random random = new Random();
        List<Gemstone> gems = new ArrayList<>();
        int randomNumber = random.nextInt(TartarusRiches.config.clusterMin, TartarusRiches.config.clusterMax);
        for(int i=0;i<randomNumber;i++) {
            Gem gem = TartarusRiches.config.gemList.get(random.nextInt(size));
            gems.add(generateGemstone(gem));
        }

        return gems;
    }

    public Gemstone generateGemstone(Gem gem) {
        Random random = new Random();
        double effectiveness = Math.round(random.nextDouble(gem.getMin(), gem.getMax()) * 10.0) / 10.0;
        return new Gemstone(gem, effectiveness);
    }

    public void addItemToInventory(Player player, ItemStack item) {
        if(player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItem(player.getLocation(), item);
        } else {
            player.getInventory().addItem(item);
        }
    }

    public boolean applyGem(Gem gem, ItemStack item, double effectiveness, Player player) {
        int slotsTitleLine = findLine(item, TartarusRiches.config.slotsTitle);
        NamespacedKey limitKey = new NamespacedKey(plugin, "slotsLimit");
        NamespacedKey slotsKey = new NamespacedKey(plugin, "slots");
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();

        if(lore == null) {
            lore = new ArrayList<>();
        }

        // Si no se han aplicado gemas nunca ponemos las tags.
        if(!meta.getPersistentDataContainer().has(limitKey)) {
            meta.getPersistentDataContainer().set(limitKey, PersistentDataType.INTEGER, TartarusRiches.config.baseGemSlots);
            meta.getPersistentDataContainer().set(slotsKey, PersistentDataType.STRING, "");
        }

        // Si ya se han aplicado las gemas máximas salimos
        String[] slots = meta.getPersistentDataContainer().get(slotsKey, PersistentDataType.STRING).split("\\.");
        int size = slots.length;
        if(Arrays.stream(slots).toList().contains(gem.getTypeName())) {
            player.sendMessage(TartarusRiches.messages.gemAlreadyPresent);
            return false;
        }

        if(size >= meta.getPersistentDataContainer().get(limitKey, PersistentDataType.INTEGER)) {
            player.sendMessage(TartarusRiches.messages.maxSlots);
            return false;
        }

        if(size == 1) {
            if(slots[0].isEmpty()) {
                size = 0;
            }
        }

        // Aplicamos el lore de las gemas
        StringBuilder keyString = new StringBuilder();
        StringBuilder loreString = new StringBuilder();
        for(int i=0;i<size;i++) {
            keyString.append(slots[i] + ".");
            Gem keyGem = TartarusRiches.config.gems.get(slots[i]);
            NamespacedKey gemKey = new NamespacedKey(plugin, keyGem.getTypeName());
            double keyEffectiveness = meta.getPersistentDataContainer().get(gemKey, PersistentDataType.DOUBLE);
            loreString.append(TartarusRiches.config.slot.replace("%color%", keyGem.getSlotColor())
                    .replace("%power%", String.valueOf(keyEffectiveness))
                    .replace("%icon%", keyGem.getSlotIcon())).append(" ");
        }

        keyString.append(gem.getId());
        loreString.append(TartarusRiches.config.slot.replace("%color%", gem.getSlotColor())
                .replace("%power%", String.valueOf(effectiveness))
                .replace("%icon%", gem.getSlotIcon()));

        meta.getPersistentDataContainer().set(slotsKey, PersistentDataType.STRING, keyString.toString());
        // No hay gemas aplicadas
        if(slotsTitleLine < 0) {
            lore.add("");
            lore.add(TartarusRiches.config.slotsTitle);
            lore.add(loreString.toString());
        } else {
            slotsTitleLine += 1;
            lore.set(slotsTitleLine, loreString.toString());
        }

        meta.setLore(lore);

        // Aplicamos la gema
        NamespacedKey newGemKey = new NamespacedKey(plugin, gem.getTypeName());
        meta.getPersistentDataContainer().set(newGemKey, PersistentDataType.DOUBLE, effectiveness);
        item.setItemMeta(meta);

        player.sendMessage(TartarusRiches.messages.gemApplied);
        return true;
    }

    public boolean removeGem(ItemStack item, int slot, Player player) {
        int slotsTitleLine = findLine(item, TartarusRiches.config.slotsTitle);
        NamespacedKey limitKey = new NamespacedKey(plugin, "slotsLimit");
        NamespacedKey slotsKey = new NamespacedKey(plugin, "slots");
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return false;
        }

        List<String> lore = meta.getLore();
        if(lore == null) {
            return false;
        }

        if(slotsTitleLine < 0) {
            return false;
        }

        if(!meta.getPersistentDataContainer().has(limitKey)) {
            return false;
        }

        String[] slots = meta.getPersistentDataContainer().get(slotsKey, PersistentDataType.STRING).split("\\.");
        int size = slots.length;
        if(slot > size) {
            return false;
        }

        StringBuilder keyString = new StringBuilder();
        StringBuilder loreString = new StringBuilder();
        slotsTitleLine += 1;
        Gem removedGem = null;
        NamespacedKey removedKey = null;
        slot -= 1;
        if(size == 1) {
            lore.remove(slotsTitleLine);
            lore.remove(slotsTitleLine - 1);
            lore.remove(slotsTitleLine - 2);
            removedGem = TartarusRiches.config.gems.get(slots[0]);
            meta.getPersistentDataContainer().set(slotsKey, PersistentDataType.STRING, "");
        } else {
            for(int i=0;i<size;i++) {
                // Nos saltamos la gema a eliminar
                if(i == slot) {
                    removedGem = TartarusRiches.config.gems.get(slots[i]);
                    continue;
                }

                keyString.append(slots[i] + ".");
                Gem keyGem = TartarusRiches.config.gems.get(slots[i]);
                NamespacedKey gemKey = new NamespacedKey(plugin, keyGem.getTypeName());
                double keyEffectiveness = meta.getPersistentDataContainer().get(gemKey, PersistentDataType.DOUBLE);
                loreString.append(TartarusRiches.config.slot.replace("%color%", keyGem.getSlotColor())
                        .replace("%power%", String.valueOf(keyEffectiveness))
                        .replace("%icon%", keyGem.getSlotIcon())).append(" ");
            }

            meta.getPersistentDataContainer().set(slotsKey, PersistentDataType.STRING,
                    keyString.toString().substring(0, keyString.toString().length() - 1));
            lore.set(slotsTitleLine, loreString.toString());
        }

        removedKey = new NamespacedKey(plugin, removedGem.getTypeName());
        double removedEffectiveness = 0;
        removedEffectiveness = meta.getPersistentDataContainer().get(removedKey, PersistentDataType.DOUBLE);
        meta.getPersistentDataContainer().remove(removedKey);

        meta.setLore(lore);
        item.setItemMeta(meta);

        Gemstone removedGemstone = new Gemstone(removedGem, removedEffectiveness);
        addItemToInventory(player, removedGemstone);

        player.sendMessage(TartarusRiches.messages.gemRemoved.replace("%slot%", String.valueOf(slot + 1)));
        return true;
    }

    public void changeMaxSlots(ItemStack item, int slots, Player player) {
        NamespacedKey limitKey = new NamespacedKey(plugin, "slotsLimit");
        NamespacedKey slotsKey = new NamespacedKey(plugin, "slots");
        ItemMeta meta = item.getItemMeta();

        if(meta == null) {
            return;
        }

        // Si no se han aplicado gemas nunca ponemos las tags.
        if(!meta.getPersistentDataContainer().has(limitKey)) {
            meta.getPersistentDataContainer().set(slotsKey, PersistentDataType.STRING, "");
        }

        meta.getPersistentDataContainer().set(limitKey, PersistentDataType.INTEGER, slots);
        item.setItemMeta(meta);
        player.sendMessage(TartarusRiches.messages.slotsChanged.replace("%slots%", String.valueOf(slots)));
    }

    private int findLine(ItemStack item, String line) {
        List<String> lore = item.getItemMeta().getLore();
        if(lore == null || lore.isEmpty()) {
            return -1;
        }

        int size = lore.size();
        for(int i=0;i<size;i++) {
            if(lore.get(i).contains(line)) {
                return i;
            }
        }

        return -1;
    }
}
