package wbe.tartarusRiches.gemTypes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import wbe.tartarusRiches.TartarusRiches;

import java.util.List;
import java.util.Random;

public class transformation extends GemType {

    private List<Material> allowedTransformations;

    private Random random = new Random();

    public transformation(String id, List<Material> allowedTransformations) {
        super(id);
        this.allowedTransformations = allowedTransformations;
    }

    public void applyEffect(Player player, Event event) {
        if(!(event instanceof BlockBreakEvent)) {
            return;
        }

        double power = getGemPowerValue(player, "transform");
        if(power <= 0) {
            return;
        }

        if(!(random.nextDouble(100) <= power)) {
            return;
        }

        Block block = ((BlockBreakEvent) event).getBlock();
        int tries = 3;
        for(int i=0;i<tries;i++) {
            BlockFace face = TartarusRiches.utilities.getRandomFace();
            Block transformBlock = block.getRelative(face);
            if(transformBlock.getType() == Material.AIR) {
                continue;
            }
            transformBlock.setType(getRandomTransformation());
            player.playSound(player, TartarusRiches.config.transformSound, 1F, 1F);
            return;
        }
    }

    private Material getRandomTransformation() {
        int size = allowedTransformations.size();
        return allowedTransformations.get(random.nextInt(size));
    }
}
