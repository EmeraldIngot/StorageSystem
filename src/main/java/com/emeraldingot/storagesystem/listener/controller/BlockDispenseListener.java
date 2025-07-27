package com.emeraldingot.storagesystem.listener.controller;


import com.emeraldingot.storagesystem.langauge.Language;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

import java.sql.SQLException;

public class BlockDispenseListener implements Listener {
    @EventHandler
    public void onBlockDispense(BlockDispenseEvent event) throws SQLException {

        Block block = event.getBlock();
        if (((Dispenser) block.getState()).getCustomName().equals(Language.STORAGE_CONTROLLER_ITEM)) {
            event.setCancelled(true);
            return;
        }



    }



}
