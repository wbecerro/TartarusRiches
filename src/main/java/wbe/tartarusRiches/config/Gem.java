package wbe.tartarusRiches.config;

import org.bukkit.Material;
import wbe.tartarusRiches.gemTypes.GemType;

public class Gem {

    private String id;

    private Material material;

    private String name;

    private String power;

    private GemType type;

    private String typeName;

    private String slotIcon;

    private String slotColor;

    private double min;

    private double max;

    public Gem(String id, Material material, String name, String power, GemType type, String typeName, String slotIcon,
               String slotColor, double min, double max) {
        this.id = id;
        this.material = material;
        this.name = name;
        this.power = power;
        this.type = type;
        this.typeName = typeName;
        this.slotIcon = slotIcon;
        this.slotColor = slotColor;
        this.min = min;
        this.max = max;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public GemType getType() {
        return type;
    }

    public void setType(GemType type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSlotIcon() {
        return slotIcon;
    }

    public void setSlotIcon(String slotIcon) {
        this.slotIcon = slotIcon;
    }

    public String getSlotColor() {
        return slotColor;
    }

    public void setSlotColor(String slotColor) {
        this.slotColor = slotColor;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
