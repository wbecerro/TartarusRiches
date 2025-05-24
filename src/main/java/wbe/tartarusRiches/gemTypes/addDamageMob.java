package wbe.tartarusRiches.gemTypes;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class addDamageMob extends GemType {

    private List<EntityType> entities;

    public addDamageMob(List<EntityType> entities) {
        super();
        this.entities = entities;
    }

    public void applyEffect(ItemStack item) {

    }
}
