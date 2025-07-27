package com.emeraldingot.storagesystem.listener.controller;


import com.emeraldingot.storagesystem.block.StorageControllerBlock;
import com.emeraldingot.storagesystem.impl.ControllerManager;
import com.emeraldingot.storagesystem.langauge.Language;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;

public class StorageControllerPlaceListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) throws SQLException {

        ItemStack item = event.getItemInHand();

        if (item.getType() != Material.DISPENSER) {
            return;
        }


        if (!item.getItemMeta().getItemName().equals(Language.STORAGE_CONTROLLER_ITEM)) {
            return;
        }

        Block block = event.getBlock();

        Inventory inventory = ((Dispenser) block.getState()).getInventory();
        StorageControllerBlock.setOffline(inventory);
        ControllerManager.getInstance().addController(block.getLocation());
//        System.out.println(inventory.getClass());

        BlockData blockData = event.getBlock().getBlockData();
//        BlockFace blockFace = ((Directional) block.getBlockData()).getFacing();
        ((Directional) blockData).setFacing(BlockFace.UP);
        event.getBlock().setBlockData(blockData);
//        System.out.println(blockFace.getDirection());
        event.setCancelled(false);

    }



}
