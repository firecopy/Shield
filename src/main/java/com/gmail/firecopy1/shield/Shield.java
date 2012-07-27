package com.gmail.firecopy1.shield;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class Shield extends JavaPlugin {

    private final DamageListener dListener = new DamageListener(this);
    public List<String> disabledPlayers;
    public int blockDamaged;
    public int ShieldsCreated;
    public int ShieldsBreakingDamage;
    //
    public static final ChatColor red = ChatColor.RED;
    public static final ChatColor gray = ChatColor.GRAY;
    public static final ChatColor green = ChatColor.GREEN;
    public static final String pre = gray + "[" + green + "Shield" + gray + "] ";

    @Override
    public void onEnable() {
        loadConfig();
        registerEvents();
        addRecipe();
        this.getCommand("shield").setExecutor(this);
    }

    @Override
    public void onDisable() {
        disableConfig();
    }

    private void registerEvents() {
        this.getServer().getPluginManager().registerEvents(dListener, this);
    }

    private void addRecipe() {
        ShapedRecipe Shield = new ShapedRecipe(new ItemStack(Material.PISTON_EXTENSION, ShieldsCreated));
        Shield.shape(new String[]{"WWW", "WBW", "WWW"});
        Shield.setIngredient('W', Material.WOOD);
        Shield.setIngredient('B', Material.STRING);
        this.getServer().addRecipe(Shield);
    }
    
    private void loadConfig() {
        this.getConfig().options().copyDefaults(true);
        disabledPlayers = this.getConfig().getStringList("disabledplayers");
        blockDamaged = this.getConfig().getInt("DamageBlocked", 4);
        ShieldsCreated = this.getConfig().getInt("ShieldsCreated", 64);
        ShieldsBreakingDamage = this.getConfig().getInt("ShieldsBreakingDamage", 1);
        saveConfig();
    }
    
    private void disableConfig() {
        this.getConfig().set("disabledplayers", disabledPlayers);
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        if (!(sender instanceof Player)) {
            sender.sendMessage("Command can only be run as a player. DOH!");
            return true;
        }

        if (args.length == 1 && "notice".equalsIgnoreCase(args[0])) {
            togglePlayers(sender);
        } else {
            sender.sendMessage(pre + red + "Invalid arguments!");
        }

        return true;
    }

    public void togglePlayers(CommandSender sender) {
        if (disabledPlayers.contains(sender.getName())) {
            disabledPlayers.remove(sender.getName());
            sender.sendMessage(pre + ChatColor.RED + "Shield Notice's enabled!");
        } else {
            disabledPlayers.add(sender.getName());
            sender.sendMessage(pre + ChatColor.RED + "Shield Notice's disabled!");
        }
    }
}
