package wbe.tartarusRiches.config;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Ore {

    private Material id;

    private HashMap<Gem, Integer> rewards = new HashMap<>();

    private int totalWeight;

    public Ore(Material id, HashMap<Gem, Integer> rewards, int totalWeight) {
        this.id = id;
        this.rewards = rewards;
        this.totalWeight = totalWeight;
    }

    public Material getId() {
        return id;
    }

    public void setId(Material id) {
        this.id = id;
    }

    public HashMap<Gem, Integer> getRewards() {
        return rewards;
    }

    public void setRewards(HashMap<Gem, Integer> rewards) {
        this.rewards = rewards;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Gem getRandomReward() {
        Random random = new Random();
        int randomNumber = random.nextInt(totalWeight);
        int weight = 0;
        Map.Entry<Gem, Integer> last = null;

        for(Map.Entry<Gem, Integer> reward : rewards.entrySet()) {
            weight += reward.getValue();
            if(randomNumber < weight) {
                return reward.getKey();
            }
            last = reward;
        }

        return last.getKey();
    }
}
