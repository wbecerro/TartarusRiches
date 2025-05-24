package wbe.tartarusRiches.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.tartarusRiches.TartarusRiches;

import java.util.ArrayList;

public class Pickaxe extends ItemStack {

    public Pickaxe(Material material, double gemChance) {
        super(material);

        ItemMeta meta;
        if(hasItemMeta()) {
            meta = getItemMeta();
        } else {
            meta = Bukkit.getItemFactory().getItemMeta(material);
        }

        meta.setDisplayName(TartarusRiches.config.pickaxeName);

        ArrayList<String> lore = new ArrayList<>();
        for(String line : TartarusRiches.config.pickaxeLore) {
            lore.add(line.replace("&", "§"));
        }

        lore.add(TartarusRiches.config.pickaxeGemChance.replace("%gem_chance%", String.valueOf(gemChance)));
        meta.setLore(lore);

        NamespacedKey gemKey = new NamespacedKey(TartarusRiches.getInstance(), "gemChance");
        meta.getPersistentDataContainer().set(gemKey, PersistentDataType.DOUBLE, gemChance);

        setItemMeta(meta);
    }
}
