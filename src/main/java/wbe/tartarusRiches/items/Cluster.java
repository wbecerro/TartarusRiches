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

public class Cluster extends ItemStack {

    public Cluster() {
        super(TartarusRiches.config.clusterMaterial);

        ItemMeta meta;
        if(hasItemMeta()) {
            meta = getItemMeta();
        } else {
            meta = Bukkit.getItemFactory().getItemMeta(TartarusRiches.config.clusterMaterial);
        }

        meta.setDisplayName(TartarusRiches.config.clusterName);

        ArrayList<String> lore = new ArrayList<>();
        for(String line : TartarusRiches.config.clusterLore) {
            lore.add(line.replace("&", "§"));
        }
        meta.setLore(lore);

        NamespacedKey clusterKey = new NamespacedKey(TartarusRiches.getInstance(), "cluster");
        meta.getPersistentDataContainer().set(clusterKey, PersistentDataType.BOOLEAN, true);

        if(TartarusRiches.config.clusterGlow) {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        setItemMeta(meta);
    }
}
