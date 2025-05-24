package wbe.tartarusRiches.items;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.config.Gem;

import java.util.ArrayList;

public class Gemstone extends ItemStack {

    public Gemstone(Gem gem, double effectiveness) {
        super(gem.getMaterial());

        ItemMeta meta;
        if(hasItemMeta()) {
            meta = getItemMeta();
        } else {
            meta = Bukkit.getItemFactory().getItemMeta(gem.getMaterial());
        }

        meta.setDisplayName(TartarusRiches.config.gemName.replace("%gem_name%", gem.getName()));

        ArrayList<String> lore = new ArrayList<>();
        String power = gem.getPower().replace("%effectiveness%", String.valueOf(effectiveness));
        for(String line : TartarusRiches.config.gemLore) {
            lore.add(line.replace("&", "§").replace("%power%", power));
        }
        meta.setLore(lore);

        NamespacedKey effectivenessKey = new NamespacedKey(TartarusRiches.getInstance(), "effectiveness");
        meta.getPersistentDataContainer().set(effectivenessKey, PersistentDataType.DOUBLE, effectiveness);
        NamespacedKey typeKey = new NamespacedKey(TartarusRiches.getInstance(), "gemType");
        meta.getPersistentDataContainer().set(typeKey, PersistentDataType.STRING, gem.getId());

        if(TartarusRiches.config.gemGlow) {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        setItemMeta(meta);
    }
}
