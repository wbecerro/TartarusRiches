package wbe.tartarusRiches.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.config.Gem;
import wbe.tartarusRiches.items.Gemstone;

public class PrepareAnvilListeners implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void showGemUpgrade(PrepareAnvilEvent event) {
        ItemStack first = event.getInventory().getItem(0);
        ItemStack second = event.getInventory().getItem(1);
        NamespacedKey typeKey = new NamespacedKey(TartarusRiches.getInstance(), "gemType");

        if(!TartarusRiches.utilities.checkItem(first, typeKey)) {
            return;
        }

        if(!TartarusRiches.utilities.checkItem(second, typeKey)) {
            return;
        }

        ItemMeta firstMeta = first.getItemMeta();
        ItemMeta secondMeta = second.getItemMeta();
        if(!firstMeta.getPersistentDataContainer().get(typeKey, PersistentDataType.STRING)
                .equalsIgnoreCase(secondMeta.getPersistentDataContainer().get(typeKey, PersistentDataType.STRING))) {
            return;
        }

        Gem gem = TartarusRiches.config.gems.get(firstMeta.getPersistentDataContainer().get(typeKey, PersistentDataType.STRING));
        NamespacedKey effectivenessKey = new NamespacedKey(TartarusRiches.getInstance(), "effectiveness");
        double firstEffectiveness = firstMeta.getPersistentDataContainer().get(effectivenessKey, PersistentDataType.DOUBLE);
        double secondEffectiveness = secondMeta.getPersistentDataContainer().get(effectivenessKey, PersistentDataType.DOUBLE);

        double newEffectiveness = firstEffectiveness + secondEffectiveness;
        if(newEffectiveness > gem.getMax()) {
            newEffectiveness = gem.getMax();
        }

        Gemstone newGemstone = new Gemstone(gem, newEffectiveness);

        event.setResult(newGemstone);
    }
}
