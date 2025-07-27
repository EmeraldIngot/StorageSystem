package com.emeraldingot.storagesystem.listener.controller;


import com.emeraldingot.storagesystem.block.StorageControllerBlock;
import com.emeraldingot.storagesystem.impl.ControllerManager;
import com.emeraldingot.storagesystem.langauge.Language;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;

import java.sql.SQLException;

public class StorageControllerBreakListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockBreakEvent event) throws SQLException {

        Block block = event.getBlock();
        if (block.getType() != Material.DISPENSER) {
            return;
        }


//        Dispenser dispenser = (Dispenser) block.getState();

        if (!StorageControllerBlock.isStorageController(block.getLocation())) {
            return;
        }

        event.setDropItems(false);
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            block.getWorld().dropItemNaturally(block.getLocation(), StorageControllerBlock.getStack());
        }

        Inventory inventory = ((Dispenser) block.getState()).getInventory();
        StorageControllerBlock.clearStatusSlots(inventory);
        ControllerManager.getInstance().removeController(block.getLocation());




    }



}
