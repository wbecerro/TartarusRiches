package wbe.tartarusRiches.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import wbe.tartarusRiches.TartarusRiches;

public class Scheduler {

    private static TartarusRiches plugin;

    public static void startSchedulers() {
        plugin = TartarusRiches.getInstance();
        startDataSaveScheduler();
    }

    private static void startDataSaveScheduler() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for(Player player : TartarusRiches.playerSacks.keySet()) {
                    TartarusRiches.utilities.savePlayerData(player);
                }
            }
        }, 10L, 60 * 5 * 20L);
    }
}
