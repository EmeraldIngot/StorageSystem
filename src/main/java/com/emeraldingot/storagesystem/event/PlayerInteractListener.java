package com.emeraldingot.storagesystem.event;


import com.emeraldingot.storagesystem.impl.ControllerManager;
import com.emeraldingot.storagesystem.impl.StorageSystemGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.sql.SQLException;
import java.util.UUID;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) throws SQLException {
//        System.out.println("test");

        if (event.getHand().equals(EquipmentSlot.OFF_HAND)) {
            return;
        }

        if (event.getItem() == null) {
            return;
        }

        if (!event.getItem().getItemMeta().getItemName().equals(ChatColor.YELLOW + "Storage Terminal")) {
            return;
        }

//        System.out.println("right click on terminal");

        Location location = ControllerManager.getInstance().getNearestController(event.getPlayer().getLocation());

        if (location != null) {

            UUID uuid = ControllerManager.getInstance().getID(location);

            if (uuid != null) {
                event.getPlayer().openInventory(StorageSystemGUI.getCompleteStoragePage(uuid, location, 0));
                return;
            }
            else {
                event.getPlayer().sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "StorageSystem " + ChatColor.RESET + "" + ChatColor.RED + "No online storage controllers found nearby");
            }

        }
        else {
            event.getPlayer().sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "StorageSystem " + ChatColor.RESET + "" + ChatColor.RED + "No online storage controllers found nearby");
        }






    }



}
