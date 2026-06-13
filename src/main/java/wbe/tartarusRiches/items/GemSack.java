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

public class GemSack extends ItemStack {

    public GemSack() {
        super(TartarusRiches.config.sackMaterial);

        ItemMeta meta;
        if(hasItemMeta()) {
            meta = getItemMeta();
        } else {
            meta = Bukkit.getItemFactory().getItemMeta(TartarusRiches.config.sackMaterial);
        }

        meta.setDisplayName(TartarusRiches.config.sackName);

        ArrayList<String> lore = new ArrayList<>();
        for(String line : TartarusRiches.config.sackLore) {
            lore.add(line.replace("&", "§"));
        }
        meta.setLore(lore);

        NamespacedKey sackKey = new NamespacedKey(TartarusRiches.getInstance(), "gemsack");
        meta.getPersistentDataContainer().set(sackKey, PersistentDataType.BOOLEAN, true);

        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);

        if(TartarusRiches.config.sackGlow) {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        setItemMeta(meta);
    }
}
