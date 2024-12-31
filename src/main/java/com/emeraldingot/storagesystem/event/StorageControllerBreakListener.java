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
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.List;

public class StorageControllerBreakListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockBreakEvent event) throws SQLException {

        Block block = event.getBlock();
        if (block.getType() != Material.DISPENSER) {
            return;
        }

        if (((Dispenser) block.getState()).getCustomName() == null) {
            return;
        }

        if (!((Dispenser) block.getState()).getCustomName().equals(ChatColor.YELLOW + "Storage Controller")) {
            return;
        }


        event.setDropItems(false);
        block.getWorld().dropItemNaturally(block.getLocation(), StorageControllerBlock.getStack());

        Inventory inventory = ((Dispenser) block.getState()).getInventory();
        StorageControllerBlock.clearStatusSlots(inventory);
        ControllerManager.getInstance().removeController(block.getLocation());




    }



}
