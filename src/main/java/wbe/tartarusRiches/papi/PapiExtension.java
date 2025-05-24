package wbe.tartarusRiches.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import wbe.tartarusRiches.TartarusRiches;

public class PapiExtension extends PlaceholderExpansion {

    @Override
    public String getAuthor() {
        return "wbe";
    }

    @Override
    public String getIdentifier() {
        return "TartarusRiches";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("gem_chance")) {
            TartarusRiches.utilities.getPlayerGemChance(player.getPlayer());
        } else if(params.equalsIgnoreCase("double_chance")) {
            TartarusRiches.utilities.getPlayerDoubleChance(player.getPlayer());
        }

        return null;
    }
}
