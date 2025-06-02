package wbe.tartarusRiches.gemTypes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import wbe.laboursOfHercules.events.CompleteLabourEvent;
import wbe.laboursOfHercules.labours.Labour;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class doubleLabour extends GemType {

    public doubleLabour(String id) {
        super(id);
    }

    public void applyEffect(Player player, Event event) {
        if(!(event instanceof CompleteLabourEvent)) {
            return;
        }

        double power = getGemPowerValue(player, "doublelabour");
        if(power <= 0) {
            return;
        }

        Random random = new Random();
        if(!(random.nextDouble(100) <= power)) {
            return;
        }

        Labour labour = ((CompleteLabourEvent) event).getType();
        int rewardsAmount = random.nextInt(labour.getMinRewards(), labour.getMaxRewards()+1);
        List<String> rewards = new ArrayList<>();
        for(int i=0;i<rewardsAmount;i++) {
            String reward = labour.getRandomReward().getCommand().replace("%player%", player.getName());
            rewards.add(reward);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), reward);
        }
    }
}
