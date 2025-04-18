package com.emeraldingot.storagesystem.event;


import com.emeraldingot.storagesystem.impl.ControllerManager;
import com.emeraldingot.storagesystem.impl.StorageCellData;
import com.emeraldingot.storagesystem.langauge.Language;
import org.bukkit.Material;
import org.bukkit.block.Container;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.UUID;

public class InventoryMoveItemListener implements Listener {
    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) throws SQLException {
        InventoryHolder holder = event.getDestination().getHolder();

        if (holder instanceof BlockInventoryHolder) {
            Block block = ((BlockInventoryHolder) holder).getBlock();
            BlockState state = block.getState();

            if (state instanceof Container) {
                Container container = (Container) state;
                if (container.getCustomName() != null && container.getCustomName().equals(Language.STORAGE_CONTROLLER_ITEM)) {
                    ItemStack storageCell = event.getDestination().getItem(4);

                    if (storageCell == null) {
                        event.setCancelled(true);
                        return;
                    }

                    StorageCellData storageCellData = StorageCellData.fromItemLore(storageCell.getItemMeta().getLore());

                    UUID cellUUID = storageCellData.getUUID();
                    if (StorageCellData.isStorageCell(event.getItem())) {
                        event.setCancelled(true);
                        return;
                    }
                    if (ControllerManager.getInstance().canHold(block.getLocation(), event.getItem())) {
                        InventoryListener.addItem(cellUUID, block.getLocation(), event.getItem());
//                    event.setCancelled(true);
                        event.setItem(new ItemStack(Material.AIR));
                        return;
                    }
                    else {
                        event.setCancelled(true);
                        return;
                    }

                }
            }
        }

        holder = event.getSource().getHolder();

        if (holder instanceof BlockInventoryHolder) {
            Block block = ((BlockInventoryHolder) holder).getBlock();

            BlockState state = block.getState();

            if (state instanceof Container) {
                Container container = (Container) state;
                if (container.getCustomName().equals(Language.STORAGE_CONTROLLER_ITEM)) {
                    event.setCancelled(true);
                    return;
                }
            }
        }


    }
}
