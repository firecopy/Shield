package com.gmail.firecopy1.shield;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {

    public Shield plugin;

    public DamageListener(Shield instance) {
        this.plugin = instance;
    }
    Player player;

    @EventHandler
    public void whenDamaged(EntityDamageByEntityEvent event) {
        int BlockedDamage = plugin.getConfig().getInt("DamageBlocked", 4);
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            player = (Player) entity;
            if (player.hasPermission("shield.use")) {
                if (player.getItemInHand().getTypeId() == 34) {
                    int Shielded_Damage = event.getDamage() - BlockedDamage;
                    if (Shielded_Damage >= 0) {
                        event.setDamage(Shielded_Damage);
                        if (plugin.disabledPlayers.contains(player.getName())) {
                            return;
                        } else {
                            player.sendMessage("Your shield has given you protection!");
                        }
                    } else {
                        event.setDamage(0);
                        if (plugin.disabledPlayers.contains(player.getName())) {
                            return;
                        } else {
                            player.sendMessage("Your shield has protected you completly!");
                        }

                    }
                }
            } else {
                player.sendMessage("Your shield is useless, if you don't have permission!");
            }
        }


        /*
         * private
         * void
         * blockDamage()
         * {
         * if
         * (player.getItemInHand().getTypeId()
         * ==
         * 34)
         * {
         * int
         * Shielded_Damage
         * =
         * event.getDamage()
         * -
         * 4;
         * if
         * (Shielded_Damage
         * >=
         * 0)
         * {
         * event.setDamage(Shielded_Damage);
         * if
         * (player.hasPermission("shield.message"))
         * {
         * player.sendMessage("Your
         * shield
         * has
         * givem
         * you
         * protection!");
         * }
         * }
         * }
         * }
         *
         * private
         * void
         * nullifyDamage()
         * {
         * event.setDamage(0);
         * if
         * (player.hasPermission("shield.message"))
         * {
         * player.sendMessage("Your
         * shield
         * has
         * protected
         * you
         * completly!");
         * }
         *
         */
    }
}
