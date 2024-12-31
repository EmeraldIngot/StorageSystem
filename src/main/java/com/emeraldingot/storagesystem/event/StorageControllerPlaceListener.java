package com.emeraldingot.storagesystem.event;


import com.emeraldingot.storagesystem.block.StorageControllerBlock;
import com.emeraldingot.storagesystem.impl.ControllerManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.naming.ldap.Control;
import java.sql.SQLException;
import java.util.List;

public class StorageControllerPlaceListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) throws SQLException {

        ItemStack item = event.getItemInHand();

        if (item.getType() != Material.DISPENSER) {
            return;
        }


        if (!item.getItemMeta().getItemName().equals(ChatColor.YELLOW + "Storage Controller")) {
            return;
        }

        Block block = event.getBlock();

        Inventory inventory = ((Dispenser) block.getState()).getInventory();
        StorageControllerBlock.setOffline(inventory);
        ControllerManager.getInstance().addController(block.getLocation());
//        System.out.println(inventory.getClass());

        event.setCancelled(false);

    }



}
