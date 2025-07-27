package com.emeraldingot.storagesystem.listener.controller;

import com.emeraldingot.storagesystem.block.StorageControllerBlock;
import com.emeraldingot.storagesystem.langauge.Language;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class StorageControllerOpenListener implements Listener {

    @EventHandler
    private void onInventoryOpen(InventoryOpenEvent event) {
        String title = event.getView().getTitle();
        if (!title.startsWith(Language.STORAGE_CONTROLLER_ITEM)) {
            return;
        }

        // Extra check just in case
        if (!StorageControllerBlock.isStorageController(event.getInventory().getLocation())) {
            return;
        }


        if (!event.getPlayer().hasPermission("storagesystem.use")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Language.NO_PERMISSION);
            return;
        }

    }
}
