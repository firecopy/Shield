package com.gmail.firecopy1.shield;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class Shield extends JavaPlugin {

    private final Damage_Listener dListener = new Damage_Listener();
    
    @Override
    public void onEnable() {
        registerEvents();
        addRecipe();
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
}
