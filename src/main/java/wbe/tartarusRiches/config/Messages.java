package wbe.tartarusRiches.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Messages {

    public String noPermission;
    public String notEnoughArgs;
    public String reload;
    public String list;
    public String doubleDrop;
    public String pickaxeGiven;
    public String invalidMaterial;
    public String pickaxeArguments;
    public String gemGiven;
    public String gemArguments;
    public String gemChanceAdded;
    public String gemChanceArguments;
    public String doubleAdded;
    public String doubleArguments;
    public String clusterGiven;
    public String gemRemoved;
    public String removeArguments;
    public String gemApplied;
    public String gemAlreadyPresent;
    public String maxSlots;
    public String slotsChanged;
    public String slotsArguments;
    public String notEnoughXP;
    public List<String> stats = new ArrayList<>();
    public List<String> help = new ArrayList<>();

    public Messages(FileConfiguration config) {
        noPermission = config.getString("Messages.noPermission").replace("&", "§");
        notEnoughArgs = config.getString("Messages.notEnoughArgs").replace("&", "§");
        reload = config.getString("Messages.reload").replace("&", "§");
        list = config.getString("Messages.list").replace("&", "§");
        doubleDrop = config.getString("Messages.doubleDrop").replace("&", "§");
        pickaxeGiven = config.getString("Messages.pickaxeGiven").replace("&", "§");
        invalidMaterial = config.getString("Messages.invalidMaterial").replace("&", "§");
        pickaxeArguments = config.getString("Messages.pickaxeArguments").replace("&", "§");
        gemGiven = config.getString("Messages.gemGiven").replace("&", "§");
        gemArguments = config.getString("Messages.gemArguments").replace("&", "§");
        gemChanceAdded = config.getString("Messages.gemChanceAdded").replace("&", "§");
        gemChanceArguments = config.getString("Messages.gemChanceArguments").replace("&", "§");
        doubleAdded = config.getString("Messages.doubleAdded").replace("&", "§");
        doubleArguments = config.getString("Messages.doubleArguments").replace("&", "§");
        clusterGiven = config.getString("Messages.clusterGiven").replace("&", "§");
        gemRemoved = config.getString("Messages.gemRemoved").replace("&", "§");
        removeArguments = config.getString("Messages.removeArguments").replace("&", "§");
        gemApplied = config.getString("Messages.gemApplied").replace("&", "§");
        gemAlreadyPresent = config.getString("Messages.gemAlreadyPresent").replace("&", "§");
        maxSlots = config.getString("Messages.maxSlots").replace("&", "§");
        slotsChanged = config.getString("Messages.slotsChanged").replace("&", "§");
        slotsArguments = config.getString("Messages.slotsArguments").replace("&", "§");
        notEnoughXP = config.getString("Messages.notEnoughXP").replace("&", "§");
        stats = config.getStringList("Messages.stats");
        help = config.getStringList("Messages.help");
    }
}
