package wbe.tartarusRiches.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.config.Gem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabListener implements TabCompleter {

    private final List<String> subCommands = Arrays.asList("help", "pickaxe", "cluster", "list", "gem",
            "gemChance", "double", "remove", "slots", "stats", "reload");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if(!command.getName().equalsIgnoreCase("TartarusRiches")) {
            return completions;
        }

        // Mostrar subcomandos
        if(args.length == 1) {
            StringUtil.copyPartialMatches(args[0], subCommands, completions);
        }

        // Argumento 1
        if(args.length == 2) {
            switch(args[0].toLowerCase()) {
                case "pickaxe":
                    List<String> materials = new ArrayList<>();
                    Arrays.asList(Material.values()).forEach((material -> {
                        materials.add(material.toString());
                    }));
                    completions.addAll(materials);
                    break;
                case "cluster":
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        completions.add(player.getName());
                    }
                    break;
                case "gem":
                    for(Gem gem : TartarusRiches.config.gemList) {
                        completions.add(gem.getId());
                    }
                    break;
                case "gemChance":
                case "double":
                    completions.add("<Probabilidad>");
                    break;
                case "remove":
                    completions.add("<Hueco>");
                    break;
                case "slots":
                    completions.add("<Huecos>");
                    break;
            }
        }

        // Argumento 2
        if(args.length == 3) {
            switch(args[0].toLowerCase()) {
                case "pickaxe":
                    completions.add("<Probabilidad gema>");
                    break;
                case "gem":
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        completions.add(player.getName());
                    }
                    break;
            }
        }

        // Argumento 3
        if(args.length == 4) {
            switch(args[0].toLowerCase()) {
                case "pickaxe":
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        completions.add(player.getName());
                    }
                    break;
                case "gem":
                    completions.add("<Efectividad>");
                    break;
            }
        }

        return completions;
    }
}
