package wbe.tartarusRiches.items;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.tartarusRiches.TartarusRiches;

import java.util.ArrayList;

public class ExtraSlotItem extends ItemStack {

    public ExtraSlotItem() {
        super(TartarusRiches.config.extraSlotMaterial);

        ItemMeta meta;
        if(hasItemMeta()) {
            meta = getItemMeta();
        } else {
            meta = Bukkit.getItemFactory().getItemMeta(TartarusRiches.config.extraSlotMaterial);
        }

        meta.setDisplayName(TartarusRiches.config.extraSlotName);

        ArrayList<String> lore = new ArrayList<>();
        for(String line : TartarusRiches.config.extraSlotLore) {
            lore.add(line.replace("&", "§"));
        }
        meta.setLore(lore);

        NamespacedKey extraSlotKey = new NamespacedKey(TartarusRiches.getInstance(), "extraSlotItem");
        meta.getPersistentDataContainer().set(extraSlotKey, PersistentDataType.BOOLEAN, true);

        if(TartarusRiches.config.extraSlotGlow) {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        setItemMeta(meta);
    }
}
