package wbe.tartarusRiches.items;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.config.SackGem;

import java.util.ArrayList;

public class MenuGem extends ItemStack {

    public MenuGem(SackGem sackGem) {
        super(sackGem.getGem().getMaterial());

        ItemMeta meta;
        if(hasItemMeta()) {
            meta = getItemMeta();
        } else {
            meta = Bukkit.getItemFactory().getItemMeta(sackGem.getGem().getMaterial());
        }

        meta.setDisplayName(TartarusRiches.config.menuGemName.replace("%gem%",
                TartarusRiches.config.gemName.replace("%gem_name%", sackGem.getGem().getName())
                    .replace("%icon%", sackGem.getGem().getSlotIcon())));

        ArrayList<String> lore = new ArrayList<>();
        for(String loreLine : TartarusRiches.config.menuGemLore) {
            lore.add(loreLine.replace("&", "§")
                    .replace("%description%", sackGem.getGem().getPower()
                            .replace("%effectiveness%", String.format("%.2f", sackGem.getGem().getMax())))
                    .replace("%accumulated%", String.format("%.2f", sackGem.getEffectiveness())));
        }

        meta.setLore(lore);

        NamespacedKey effectivenessKey = new NamespacedKey(TartarusRiches.getInstance(), "menuEffectiveness");
        meta.getPersistentDataContainer().set(effectivenessKey, PersistentDataType.DOUBLE, sackGem.getEffectiveness());

        NamespacedKey typeKey = new NamespacedKey(TartarusRiches.getInstance(), "menuGemType");
        meta.getPersistentDataContainer().set(typeKey, PersistentDataType.STRING, sackGem.getGem().getId());

        meta.addEnchant(Enchantment.INFINITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        setItemMeta(meta);
    }
}
