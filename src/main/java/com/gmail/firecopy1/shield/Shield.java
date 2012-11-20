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

public class Shield extends JavaPlugin
{

	//Define Globals
	//TODO Move Globals into a different class for good conventions.
    public List<String> disabledPlayers;
    
    public int damageBlocked = 4;
    public int durability = 64;
    
    public String messageSomeDamage = "Your shield has given you protection!";
    public String messageNoDamage = "Your shield has protected you completely!";
    
    public static ChatColor red = ChatColor.RED;
    public static ChatColor yellow = ChatColor.YELLOW;
    public static ChatColor gray = ChatColor.GRAY;
    public static ChatColor green = ChatColor.GREEN;
    public static String prefix = gray + "[" + green + "Shield" + gray + "] ";
    
    //Code run on Enable
    @Override
    public void onEnable()
    {
        loadConfig();
        this.getServer().getPluginManager().registerEvents(new DamageListener(this), this);
        addRecipes();
        this.getCommand("shield").setExecutor(this);
    }

    //Code run on Disable
    @Override
    public void onDisable()
    {
    	disabledPlayers = null;
    	damageBlocked = (Integer) null;
    	durability = (Integer) null;
    	messageSomeDamage = null;
    	messageNoDamage = null;
    	red = null;
    	gray = null;
    	green = null;
    	prefix = null;
        disableConfig();
    }

    private void addRecipes()
    {
        ShapedRecipe Shield = new ShapedRecipe(new ItemStack(Material.PISTON_EXTENSION));
        Shield.shape(new String[]{"WWW", "WBW", "WWW"});
        Shield.setIngredient('W', Material.getMaterial(5));
        Shield.setIngredient('B', Material.STRING);
        this.getServer().addRecipe(Shield);
    }
    
    private void loadConfig()
    {
        this.getConfig().options().copyDefaults(true);
        disabledPlayers = this.getConfig().getStringList("disabledplayers");
        messageSomeDamage = this.getConfig().getString("ShieldMessageSomeDamage");
        messageNoDamage = this.getConfig().getString("ShieldMessageNoDamage");
        durability = this.getConfig().getInt("ShieldsDurability");
    }
    
    private void disableConfig()
    {
        
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("This command can only be run as a player.");
            return true;
        }
        if (args.length == 1)
        {
        	if (args[0].toLowerCase() == "notice")
        	{
        		togglePlayers(sender);
        	}
        	else
        	{
        		sender.sendMessage(prefix + red + "Invalid arguments!");
        	}
        }
        else
        {
            sender.sendMessage(prefix + red + "Invalid arguments!");
        }

        return true;
    }

    public void togglePlayers(CommandSender sender)
    {
        if (disabledPlayers.contains(sender.getName()))
        {
            disabledPlayers.remove(sender.getName());
            sender.sendMessage(prefix + green + "Shield Notice enabled!");
        }
        else
        {
            disabledPlayers.add(sender.getName());
            sender.sendMessage(prefix + red + "Shield Notice disabled!");
        }
        this.getConfig().set("disabledplayers", disabledPlayers);
    }
}
