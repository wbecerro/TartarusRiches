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
}
