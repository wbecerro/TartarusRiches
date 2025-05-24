package wbe.tartarusRiches.gemTypes;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.tartarusRiches.TartarusRiches;

public abstract class GemType {

    protected double getGemPowerValue(ItemStack item, String type) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return 0;
        }

        NamespacedKey key = new NamespacedKey(TartarusRiches.getInstance(), type);
        if(meta.getPersistentDataContainer().has(key)) {
            return meta.getPersistentDataContainer().get(key, PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public abstract void applyEffect(ItemStack item);
}
