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
        final int NoDamage = 0;
        final int ShieldId = 34;
        //
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        
        if (damager instanceof Player) {
            Player PlayerDamager = (Player) damager;
            if (PlayerDamager.hasPermission("shield.use")){
                if (PlayerDamager.getItemInHand().getTypeId() == ShieldId){       
                    event.setDamage(NoDamage);
                }
            }
        }

        if (entity instanceof Player) {
            
            Player player = (Player) entity;
            
            if (player.hasPermission("shield.use")) {
                if (player.getItemInHand().getTypeId() == ShieldId) {
                    int Shielded_Damage = event.getDamage() - plugin.blockDamaged;
                    int ShieldHurt = plugin.ShieldsBreakingDamage;
                    int ShieldDurability = player.getItemInHand().getAmount() - ShieldHurt;
                    String ShieldMessageSomeDamage = plugin.shieldMessageSomeDamage;
                    String ShieldMessageNoDamage = plugin.shieldMessageNoDamage;
                    
                    if (ShieldDurability <= 0){
                        player.setItemInHand(null);
                    }
                    else {
                        player.getItemInHand().setAmount(ShieldDurability);
                    }
                    
                    if (Shielded_Damage >= NoDamage) {
                        event.setDamage(Shielded_Damage);
                        if (!plugin.disabledPlayers.contains(player.getName())) {
                            player.sendMessage(ShieldMessageSomeDamage);
                        }
                    } else {
                        event.setDamage(NoDamage);
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
