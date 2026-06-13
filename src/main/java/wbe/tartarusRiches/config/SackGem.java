package wbe.tartarusRiches.config;

import wbe.tartarusRiches.items.MenuGem;

public class SackGem {

    private Gem gem;

    private double effectiveness;

    public SackGem(Gem gem, double effectiveness) {
        this.gem = gem;
        this.effectiveness = effectiveness;
    }

    public Gem getGem() {
        return gem;
    }

    public void setGem(Gem gem) {
        this.gem = gem;
    }

    public double getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(double effectiveness) {
        this.effectiveness = effectiveness;
    }

    public MenuGem getMenuItem() {
        return new MenuGem(this);
    }
}
