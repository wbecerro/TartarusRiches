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
import wbe.tartarusRiches.config.Gem;

public abstract class GemType {

    protected String id;

    public GemType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

        NamespacedKey slotsKey = new NamespacedKey(TartarusRiches.getInstance(), "slots");
        if(!meta.getPersistentDataContainer().has(slotsKey)) {
            return 0;
        }

        String[] slots = meta.getPersistentDataContainer().get(slotsKey, PersistentDataType.STRING).split("\\.");
        int size = slots.length;
        for(int i=0;i<size;i++) {
            Gem gem = TartarusRiches.config.gems.get(slots[i]);
            if(gem == null) {
                continue;
            }

            if(!gem.getType().getId().equalsIgnoreCase(type)) {
                continue;
            }

            NamespacedKey gemKey = new NamespacedKey(TartarusRiches.getInstance(), gem.getId());
            return meta.getPersistentDataContainer().get(gemKey, PersistentDataType.DOUBLE);

        }

        return 0;
    }

    public abstract void applyEffect(Player player, Event event);
}
