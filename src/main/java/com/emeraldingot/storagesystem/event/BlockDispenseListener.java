package com.emeraldingot.storagesystem.event;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.List;

public class BlockDispenseListener implements Listener {
    @EventHandler
    public void onBlockDispense(BlockDispenseEvent event) throws SQLException {

        Block block = event.getBlock();
        if (((Dispenser) block.getState()).getCustomName().equals(ChatColor.YELLOW + "Storage Controller")) {
            event.setCancelled(true);
            return;
        }



    }



}
