package com.emeraldingot.storagesystem.event;


import com.emeraldingot.storagesystem.impl.ControllerManager;
import com.emeraldingot.storagesystem.langauge.Language;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;

public class PistonExtendListener implements Listener {
    @EventHandler
    public void onBlockDispense(BlockPistonExtendEvent event) throws SQLException {

        for (Block block : event.getBlocks()) {
            if (ControllerManager.getInstance().getControllerLocations().contains(block.getLocation())) {
                event.setCancelled(true);
                return;
            }
        }



    }



}
