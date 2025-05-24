package wbe.tartarusRiches.gemTypes;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class mcMMOXP extends GemType {

    private List<String> skills;

    public mcMMOXP(List<String> skills) {
        super();
        this.skills = skills;
    }

    public void applyEffect(ItemStack item) {

    }
}
