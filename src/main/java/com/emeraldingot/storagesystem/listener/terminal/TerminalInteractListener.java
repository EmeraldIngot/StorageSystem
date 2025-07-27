package com.emeraldingot.storagesystem.listener.terminal;


import com.emeraldingot.storagesystem.block.StorageControllerBlock;
import com.emeraldingot.storagesystem.impl.ControllerManager;
import com.emeraldingot.storagesystem.impl.StorageSystemGUI;
import com.emeraldingot.storagesystem.item.StorageTerminal;
import com.emeraldingot.storagesystem.langauge.Language;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.sql.SQLException;
import java.util.UUID;

public class TerminalInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) throws SQLException {
//        System.out.println("test");

        if (event.getHand().equals(EquipmentSlot.OFF_HAND)) {
            return;
        }

        if (event.getItem() == null) {
            return;
        }


        if (!StorageTerminal.isStorageTerminal(event.getItem())) {
            return;
        }


        Location location = ControllerManager.getInstance().getNearestController(event.getPlayer().getLocation());

        if (location == null) {
            event.getPlayer().sendMessage(Language.NO_CONTROLLER_MESSAGE);
            return;
        }

        // The block was removed but the controller still remains
        // Ideally, this shouldn't get called, but if it does it will fix the issue
        if (!StorageControllerBlock.isStorageController(location)) {
            event.getPlayer().sendMessage(Language.NO_CONTROLLER_MESSAGE);
            ControllerManager.getInstance().removeController(location);
            return;
        }


        UUID uuid = ControllerManager.getInstance().getID(location);

        if (uuid != null) {
            // TODO: If player gets disconnected while using controller, it will break
            ControllerManager.getInstance().openController(location);
            event.getPlayer().openInventory(StorageSystemGUI.getCompleteStoragePage(uuid, location, 0));
            return;
        }
        else {
            event.getPlayer().sendMessage(Language.NO_CONTROLLER_MESSAGE);
        }





    }


}
