package com.gmail.firecopy1.shield;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class Shield extends JavaPlugin {

    public DamageListener dListener = new DamageListener(this);
    public String pre = ChatColor.GRAY + "[" + ChatColor.GREEN + "Shield" + ChatColor.GRAY + "] ";
    public List<String> disabledPlayers;
    
    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        disabledPlayers = getConfig().getStringList("disabledplayers");
        saveConfig();
        registerEvents();
        addRecipe();
        
        this.getCommand("shield").setExecutor(this);
    }
    
    @Override
    public void onDisable() {
        this.getConfig().set("disabledplayers", this);
        saveConfig();
    }

    private void registerEvents() {
        this.getServer().getPluginManager().registerEvents(dListener, this);
    }

    private void addRecipe() {
        ShapedRecipe Shield = new ShapedRecipe(new ItemStack(Material.PISTON_EXTENSION, 1));
        Shield.shape(new String[]{"WWW", "WBW", "WWW"});
        Shield.setIngredient('W', Material.WOOD);
        Shield.setIngredient('B', Material.STRING);
        this.getServer().addRecipe(Shield);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(pre + ChatColor.RED + "To few arguments!");
            return true;
        }
        if (args.length > 1) {
            sender.sendMessage(pre + ChatColor.RED + "To many arguments!");
            return true;
        }
        if ("notice".equalsIgnoreCase(args[0])) {
            togglePlayers(sender);
            return true;
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
