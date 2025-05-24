package wbe.tartarusRiches.gemTypes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.tartarusRiches.TartarusRiches;

public abstract class GemType {

    protected double getGemPowerValue(Player player, String type) {
        double power = 0;

        PlayerInventory inventory = player.getInventory();
        ItemStack mainHand = inventory.getItemInMainHand();
        ItemStack offHand = inventory.getItemInOffHand();
        ItemStack[] armor = inventory.getArmorContents();

        if(!mainHand.getType().equals(Material.AIR)) {
            power += getItemGemPower(mainHand, type);
        }

        if(!offHand.getType().equals(Material.AIR)) {
            power += getItemGemPower(offHand, type);
        }

        for(ItemStack item : armor) {
            if(item == null) {
                continue;
            }
            power += getItemGemPower(item, type);
        }

        return power;
    }

    private double getItemGemPower(ItemStack item, String type) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return 0;
        }

        NamespacedKey typeKey = new NamespacedKey(TartarusRiches.getInstance(), type);
        if(meta.getPersistentDataContainer().has(typeKey)) {
            return meta.getPersistentDataContainer().get(typeKey, PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public abstract void applyEffect(Player player, Event event);
}
