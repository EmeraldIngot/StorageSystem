package com.emeraldingot.storagesystem.event;


import com.emeraldingot.storagesystem.block.StorageControllerBlock;
import com.emeraldingot.storagesystem.impl.ControllerManager;
import com.emeraldingot.storagesystem.impl.StorageSystemGUI;
import com.emeraldingot.storagesystem.langauge.Language;
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

        if (!event.getItem().getItemMeta().getItemName().equals(Language.STORAGE_TERMINAL_ITEM)) {
            return;
        }

//        System.out.println("right click on terminal");

        Location location = ControllerManager.getInstance().getNearestController(event.getPlayer().getLocation());

        if (location != null) {
            if (!StorageControllerBlock.isStorageController(location)) {
                event.getPlayer().sendMessage(Language.NO_CONTROLLER_MESSAGE);
                ControllerManager.getInstance().removeController(location);
                return;
            }
            UUID uuid = ControllerManager.getInstance().getID(location);

            if (uuid != null) {
                ControllerManager.getInstance().openController(location);
                event.getPlayer().openInventory(StorageSystemGUI.getCompleteStoragePage(uuid, location, 0));
                return;
            }
            else {
                event.getPlayer().sendMessage(Language.NO_CONTROLLER_MESSAGE);
            }

        }
        else {
            event.getPlayer().sendMessage(Language.NO_CONTROLLER_MESSAGE);
        }






    }



}
