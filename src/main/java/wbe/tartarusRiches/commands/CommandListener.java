package wbe.tartarusRiches.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.config.Gem;
import wbe.tartarusRiches.items.Cluster;
import wbe.tartarusRiches.items.Gemstone;
import wbe.tartarusRiches.items.Pickaxe;

public class CommandListener implements CommandExecutor {

    private TartarusRiches plugin;

    public CommandListener() {
        plugin = TartarusRiches.getInstance();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("TartarusRiches")) {
            Player player = null;
            if(sender instanceof Player) {
                player = (Player) sender;
            }

            if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
                if(!sender.hasPermission("tartarusriches.command.help")) {
                    sender.sendMessage(TartarusRiches.messages.noPermission);
                    return false;
                }

                for(String line : TartarusRiches.messages.help) {
                    sender.sendMessage(line.replace("&", "§"));
                }
            } else if(args[0].equalsIgnoreCase("stats")) {
                if(!sender.hasPermission("tartarusriches.command.stats")) {
                    sender.sendMessage(TartarusRiches.messages.noPermission);
                    return false;
                }

                String gemChance = String.valueOf(TartarusRiches.utilities.getPlayerGemChance(player));
                String doubleChance = String.valueOf(TartarusRiches.utilities.getPlayerDoubleChance(player));
                for(String line : TartarusRiches.messages.stats) {
                    sender.sendMessage(line.replace("&", "§")
                            .replace("%gemChance%", gemChance)
                            .replace("%doubleChance%", doubleChance));
                }
            } else if(args[0].equalsIgnoreCase("pickaxe")) {
                if(!sender.hasPermission("tartarusriches.command.pickaxe")) {
                    sender.sendMessage(TartarusRiches.messages.noPermission);
                    return false;
                }

                if(args.length < 3) {
                    sender.sendMessage(TartarusRiches.messages.notEnoughArgs);
                    sender.sendMessage(TartarusRiches.messages.pickaxeArguments);
                    return false;
                }

                Material material;
                try {
                    material = Material.valueOf(args[1].toUpperCase());
                } catch(IllegalArgumentException ex) {
                    sender.sendMessage(TartarusRiches.messages.invalidMaterial);
                    return false;
                }
                int gemChance = Integer.valueOf(args[2]);

                Pickaxe pickaxe = new Pickaxe(material, gemChance);
                if(args.length > 3) {
                    player = Bukkit.getPlayer(args[3]);
                }

                TartarusRiches.utilities.addItemToInventory(player, pickaxe);
                player.sendMessage(TartarusRiches.messages.pickaxeGiven);
            } else if(args[0].equalsIgnoreCase("cluster")) {
                if(!sender.hasPermission("tartarusriches.command.cluster")) {
                    sender.sendMessage(TartarusRiches.messages.noPermission);
                    return false;
                }

                Cluster cluster = new Cluster();
                if(args.length > 1) {
                    player = Bukkit.getPlayer(args[1]);
                }

                TartarusRiches.utilities.addItemToInventory(player, cluster);
                player.sendMessage(TartarusRiches.messages.clusterGiven);
            } else if(args[0].equalsIgnoreCase("list")) {
                if(!sender.hasPermission("tartarusriches.command.list")) {
                    sender.sendMessage(TartarusRiches.messages.noPermission);
                    return false;
                }

                sender.sendMessage(TartarusRiches.messages.list + TartarusRiches.config.gems.keySet().toString());
            } else if(args[0].equalsIgnoreCase("gem")) {
                if(!sender.hasPermission("tartarusriches.command.gem")) {
                    sender.sendMessage(TartarusRiches.messages.noPermission);
                    return false;
                }

                if(args.length < 2) {
                    sender.sendMessage(TartarusRiches.messages.notEnoughArgs);
                    sender.sendMessage(TartarusRiches.messages.gemArguments);
                    return false;
                }

                Gem gem = TartarusRiches.config.gems.get(args[1]);
                Gemstone gemstone;
                if(args.length > 2) {
                    player = Bukkit.getPlayer(args[2]);
                }

                if(args.length > 3) {
                    gemstone = new Gemstone(gem, Double.valueOf(args[3]));
                } else {
                    gemstone = TartarusRiches.utilities.generateGemstone(gem);
                }

                TartarusRiches.utilities.addItemToInventory(player, gemstone);
                player.sendMessage(TartarusRiches.messages.gemGiven.replace("%gem%", gem.getName()));
            } else if(args[0].equalsIgnoreCase("gemChance")) {
                if(!sender.hasPermission("tartarusriches.command.gemChance")) {
                    sender.sendMessage(TartarusRiches.messages.noPermission);
                    return false;
                }

                if(args.length < 2) {
                    sender.sendMessage(TartarusRiches.messages.notEnoughArgs);
                    sender.sendMessage(TartarusRiches.messages.gemChanceArguments);
                    return false;
                }

                TartarusRiches.utilities.addGemChance(player.getInventory().getItemInMainHand(), Integer.valueOf(args[1]));
                player.sendMessage(TartarusRiches.messages.gemChanceAdded);
            } else if(args[0].equalsIgnoreCase("double")) {
                if(!sender.hasPermission("tartarusriches.command.double")) {
                    sender.sendMessage(TartarusRiches.messages.noPermission);
                    return false;
                }

                if(args.length < 2) {
                    sender.sendMessage(TartarusRiches.messages.notEnoughArgs);
                    sender.sendMessage(TartarusRiches.messages.doubleArguments);
                    return false;
                }

                TartarusRiches.utilities.addDoubleDropChance(player.getInventory().getItemInMainHand(), Integer.valueOf(args[1]));
                player.sendMessage(TartarusRiches.messages.doubleAdded);
            } else if(args[0].equalsIgnoreCase("reload")) {
                if(!sender.hasPermission("tartarusriches.command.reload")) {
                    sender.sendMessage(TartarusRiches.messages.noPermission);
                    return false;
                }

                plugin.reloadConfiguration();
                sender.sendMessage(TartarusRiches.messages.reload);
            }
        }
        return true;
    }
}
