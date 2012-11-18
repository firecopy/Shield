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
        final int NO_DAMAGE = 0;
        final int SHIELD_ID = 34;
        //
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        
        if (damager instanceof Player) {
            Player PlayerDamager = (Player) damager;
            if (PlayerDamager.hasPermission("shield.use")){
                if (PlayerDamager.getItemInHand().getTypeId() == SHIELD_ID){       
                    event.setDamage(NO_DAMAGE);
                }
            }
        }

        if (entity instanceof Player) {
            
            Player player = (Player) entity;
            
            if (player.hasPermission("shield.use")) {
                if (player.getItemInHand().getTypeId() == SHIELD_ID) {
                    int damageRecieved = event.getDamage() - plugin.blockDamaged;
                    int shieldDurabilityLost = plugin.ShieldsBreakingDamage;
                    int shieldDurability = player.getItemInHand().getAmount() - shieldDurabilityLost;
                    String ShieldMessageSomeDamage = plugin.shieldMessageSomeDamage;
                    String ShieldMessageNoDamage = plugin.shieldMessageNoDamage;
                    
                    if (shieldDurability <= 0){
                        player.setItemInHand(null);
                    }
                    else {
                        player.getItemInHand().setAmount(shieldDurability);
                    }
                    
                    if (damageRecieved >= 0) {
                        event.setDamage(damageRecieved);
                        if (!plugin.disabledPlayers.contains(player.getName())) {
                            player.sendMessage(ShieldMessageSomeDamage);
                        }
                    } else {
                        event.setDamage(NO_DAMAGE);
                        if (!plugin.disabledPlayers.contains(player.getName())) {
                            player.sendMessage(ShieldMessageNoDamage);
                        }
                    }
                }
            } else {
                if (!plugin.disabledPlayers.contains(player.getName())) {
                    player.sendMessage("Your shield is useless, if you don't have permission!");
                }
            }
        }
    }
}
