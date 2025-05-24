package wbe.tartarusRiches.config;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;
import wbe.tartarusRiches.gemTypes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Config {

    private FileConfiguration config;

    public double baseGemChance;
    public double baseDoubleChance;
    public int baseGemSlots;

    public Sound doubleDropSound;
    public Sound gemDropSound;
    public Sound removeGemSound;
    public Sound addGemSound;

    public String slotsTitle;
    public String slot;

    public String pickaxeName;
    public List<String> pickaxeLore = new ArrayList<>();
    public String pickaxeGemChance;
    public String pickaxeDoubleChance;

    public String gemName;
    public List<String> gemLore = new ArrayList<>();
    public boolean gemGlow;

    public Material clusterMaterial;
    public String clusterName;
    public List<String> clusterLore = new ArrayList<>();
    public boolean clusterGlow;
    public int clusterMin;
    public int clusterMax;

    public HashMap<String, Gem> gems = new HashMap<>();
    public List<Gem> gemList = new ArrayList<>();
    public List<Ore> ores = new ArrayList<>();
    private int totalweight;

    public Config(FileConfiguration config) {
        this.config = config;

        baseGemChance = config.getDouble("Config.baseGemChance");
        baseDoubleChance = config.getDouble("Config.baseDoubleChance");
        baseGemSlots = config.getInt("Config.baseGemSlots");

        doubleDropSound = Sound.valueOf(config.getString("Sounds.doubleDropSound"));
        gemDropSound = Sound.valueOf(config.getString("Sounds.gemDropSound"));
        removeGemSound = Sound.valueOf(config.getString("Sounds.removeGemSound"));
        addGemSound = Sound.valueOf(config.getString("Sounds.addGemSound"));

        slotsTitle = config.getString("Items.slotsTitle").replace("&", "§");
        slot = config.getString("Items.slot");

        pickaxeName = config.getString("Items.pickaxe.name").replace("&", "§");
        pickaxeLore = config.getStringList("Items.pickaxe.lore");
        pickaxeGemChance = config.getString("Items.pickaxe.gemChance").replace("&", "§");
        pickaxeDoubleChance = config.getString("Items.pickaxe.doubleChance").replace("&", "§");

        gemName = config.getString("Items.gem.name").replace("&", "§");
        gemLore = config.getStringList("Items.gem.lore");
        gemGlow = config.getBoolean("Items.gem.glow");

        clusterMaterial = Material.valueOf(config.getString("Items.gemCluster.material"));
        clusterName = config.getString("Items.gemCluster.name").replace("&", "§");
        clusterLore = config.getStringList("Items.gemCluster.lore");
        clusterGlow = config.getBoolean("Items.gemCluster.glow");
        clusterMin = config.getInt("Items.gemCluster.min");
        clusterMax = config.getInt("Items.gemCluster.max");

        loadGems();
        loadOres();
    }

    private void loadGems() {
        Set<String> configGems = config.getConfigurationSection("Gems").getKeys(false);
        for(String gem : configGems) {
            String id = gem;
            Material material = Material.valueOf(config.getString("Gems." + gem + ".material"));
            String name = config.getString("Gems." + gem + ".name").replace("&", "§");
            String power = config.getString("Gems." + gem + ".power").replace("&", "§");
            double min = config.getDouble("Gems." + gem + ".min");
            double max = config.getDouble("Gems." + gem + ".max");
            String typeName = config.getString("Gems." + gem + ".type");
            String slotIcon = config.getString("Gems." + gem + ".slotIcon");
            String slotColor = config.getString("Gems." + gem + ".slotColor").replace("&", "§");
            GemType type = getType(typeName, min, max);
            Gem newGem = new Gem(id, material, name, power, type, typeName.toLowerCase(), slotIcon, slotColor, min, max);
            gems.put(id, newGem);
            gemList.add(newGem);
        }
    }

    private GemType getType(String gem, double min, double max) {
        gem = gem.toUpperCase();
        double multiplier;
        double damage;
        List<String> skills;
        List<EntityType> entities = new ArrayList<>();

        switch(gem) {
            case "CRITIC":
                multiplier = config.getDouble("Gems." + gem + ".multiplier");
                return new critic(multiplier);
            case "MCMMOXP":
                skills = config.getStringList("Gems." + gem + ".skills");
                return new mcMMOXP(skills);
            case "ADDDAMAGEMOBS":
                config.getStringList("Gems." + gem + ".entites").stream().forEach((entity) -> {
                    entities.add(EntityType.valueOf(entity.toUpperCase()));
                });
                return new addDamageMob(entities);
            case "ADDDAMAGE":
                return new addDamage();
            case "ADDDAMAGEMYTHIC":
                return new addDamageMythic();
            case "DAMAGEREDUCTION":
                return new damageReduction();
            case "DODGE":
                return new dodge();
            case "DOUBLELABOUR":
                return new doubleLabour();
            case "LIFE":
                return new life();
            case "THUNDER":
                damage = config.getDouble("Gems." + gem + ".damage");
                return new thunder(damage);
            case "XP":
                return new xp();
            default:
                return new addDamage();
        }
    }

    private void loadOres() {
        Set<String> configOres = config.getConfigurationSection("Ores").getKeys(false);
        for(String ore : configOres) {
            Material material = Material.valueOf(ore);
            HashMap<Gem, Integer> rewards = loadRewards(ore);
            ores.add(new Ore(material, rewards, totalweight));
        }
    }

    private HashMap<Gem, Integer> loadRewards(String ore) {
        totalweight = 0;
        Set<String> configRewards = config.getConfigurationSection("Ores." + ore + ".rewards").getKeys(false);
        HashMap<Gem, Integer> rewards = new HashMap<>();
        for(String reward : configRewards) {
            Gem gem = gems.get(reward);
            int weight = config.getInt("Ores." + ore + ".rewards." + reward + ".weight");
            totalweight += weight;
            rewards.put(gem, weight);
        }

        return rewards;
    }
}
