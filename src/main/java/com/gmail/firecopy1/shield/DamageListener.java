package com.gmail.firecopy1.shield;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {

    private final Shield plugin;

    public DamageListener(Shield instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void whenDamaged(EntityDamageByEntityEvent event) {
        
        Entity entity = event.getEntity();
        
        if (entity instanceof Player) {
            
            Player player = (Player) entity;
            
            if (player.hasPermission("shield.use")) {
                if (player.getItemInHand().getTypeId() == 34) {
                    int Shielded_Damage = event.getDamage() - plugin.blockDamaged;
                    if (Shielded_Damage >= 0) {
                        event.setDamage(Shielded_Damage);
                        if (!plugin.disabledPlayers.contains(player.getName())) {
                            player.sendMessage("Your shield has given you protection!");
                        }
                    } else {
                        event.setDamage(0);
                        if (!plugin.disabledPlayers.contains(player.getName())) {
                            player.sendMessage("Your shield has protected you completly!");
                        }
                    }
                }
            } else {
                player.sendMessage("Your shield is useless, if you don't have permission!");
            }
        }
    }
}
