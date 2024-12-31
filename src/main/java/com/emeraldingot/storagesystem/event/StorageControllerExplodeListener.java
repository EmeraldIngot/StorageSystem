package com.emeraldingot.storagesystem.event;


import com.emeraldingot.storagesystem.block.StorageControllerBlock;
import com.emeraldingot.storagesystem.impl.ControllerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.Inventory;

import java.sql.SQLException;

public class StorageControllerExplodeListener implements Listener {
    @EventHandler
    public void onBlockPlace(EntityExplodeEvent event) throws SQLException {

        for (Block block : event.blockList()) {
            if (block.getType() != Material.DISPENSER) {
                continue;
            }
            if (((Dispenser) block.getState()).getCustomName() == null) {
                continue;
            }

            if (!((Dispenser) block.getState()).getCustomName().equals(ChatColor.YELLOW + "Storage Controller")) {
                continue;
            }

            Inventory inventory = ((Dispenser) block.getState()).getInventory();
            StorageControllerBlock.clearStatusSlots(inventory);
            ControllerManager.getInstance().removeController(block.getLocation());
        }



//

//        event.setCancelled(true);
//        block.getWorld().dropItemNaturally(block.getLocation(), StorageControllerBlock.getStack());






    }



}
