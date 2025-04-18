package com.emeraldingot.storagesystem.event;


import com.emeraldingot.storagesystem.block.StorageControllerBlock;
import com.emeraldingot.storagesystem.impl.ControllerManager;
import com.emeraldingot.storagesystem.langauge.Language;
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

        for (int i = 0; i < event.blockList().size(); i++) {
            Block block = event.blockList().get(i);
            if (block.getType() != Material.DISPENSER) {
                continue;
            }
            if (((Dispenser) block.getState()).getCustomName() == null) {
                continue;
            }

            if (!((Dispenser) block.getState()).getCustomName().equals(Language.STORAGE_CONTROLLER_ITEM)) {
                continue;
            }

            Inventory inventory = ((Dispenser) block.getState()).getInventory();
            StorageControllerBlock.clearStatusSlots(inventory);
            ControllerManager.getInstance().removeController(block.getLocation());
            if (inventory.getItem(4) != null) {
                block.getWorld().dropItemNaturally(block.getLocation(), inventory.getItem(4));
            }
            block.getWorld().dropItemNaturally(block.getLocation(), StorageControllerBlock.getStack());
            block.setType(Material.AIR);
            event.blockList().remove(i);
            i--;
        }



//

//        event.setCancelled(true);
//        block.getWorld().dropItemNaturally(block.getLocation(), StorageControllerBlock.getStack());






    }



}
