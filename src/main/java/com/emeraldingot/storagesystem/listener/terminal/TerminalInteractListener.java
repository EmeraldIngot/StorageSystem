package com.emeraldingot.storagesystem.listener.terminal;


import com.emeraldingot.storagesystem.block.StorageControllerBlock;
import com.emeraldingot.storagesystem.gui.terminal.StorageSystemGui;
import com.emeraldingot.storagesystem.impl.ControllerManager;
import com.emeraldingot.storagesystem.impl.StorageCellData;
import com.emeraldingot.storagesystem.item.StorageCell;
import com.emeraldingot.storagesystem.item.StorageTerminal;
import com.emeraldingot.storagesystem.langauge.Language;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

import java.sql.SQLException;
import java.util.SimpleTimeZone;
import java.util.UUID;

public class TerminalInteractListener implements Listener {
    @EventHandler
    public void onPlayerNormalInteract(PlayerInteractEvent event)  {
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

        if (event.getPlayer().isSneaking()) {
            return;
        }

        if (!StorageTerminal.isPaired(event.getItem())) {
            event.getPlayer().sendMessage(Language.NO_PAIRED_CONTROLLER_MESSAGE);
            return;
        }

        Location location = StorageTerminal.getPairedLocation(event.getItem());

        if (event.getPlayer().getLocation().distanceSquared(location) > Math.pow(StorageTerminal.TERMINAL_RANGE, 2)) {
            event.getPlayer().sendMessage(Language.PAIRED_CONTROLLER_RANGE_MESSAGE);
            return;
        }

//        if (location.getWorld() == null) {
//            System.out.println(location);
//            return;
//        }

//        Location location = ControllerManager.getInstance().getNearestController(event.getPlayer().getLocation());

        // The block was removed but the controller still remains
        // Ideally, this shouldn't get called, but if it does it will fix the issue

        if (!StorageControllerBlock.isStorageController(location)) {
            event.getPlayer().sendMessage(Language.REMOVED_CONTROLLER_MESSAGE);
            ControllerManager.getInstance().removeController(location);
            StorageTerminal.unpair(event.getItem());
            return;
        }

        if (!ControllerManager.getInstance().isOnline(location)) {
            event.getPlayer().sendMessage(Language.PAIRED_CONTROLLER_OFFLINE_MESSAGE);
            return;
        }


        ItemStack cell = ControllerManager.getInstance().getCell(location);

        // It takes 5 ticks to update the online state so a player could click again in that time
//        if (cell == null) {
//            event.getPlayer().sendMessage(Language.PAIRED_CONTROLLER_OFFLINE_MESSAGE);
//            return;
//        }

        UUID uuid = StorageCell.getUuid(cell);

        if (uuid != null) {
            // TODO: If player gets disconnected while using controller, it will break

            event.getPlayer().openInventory(StorageSystemGui.getInventory(event.getPlayer(), StorageCellData.fromItemStack(cell, location)));
            ControllerManager.getInstance().openController(location);

//            event.getPlayer().openInventory(StorageSystemGUI.getCompleteStoragePage(uuid, location, 0));



            return;
        }
        else {
            event.getPlayer().sendMessage(Language.NO_PAIRED_CONTROLLER_MESSAGE);
        }


    }



    @EventHandler
    public void onPlayerSneakInteract(PlayerInteractEvent event) {
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

        if (!event.getPlayer().isSneaking()) {
            return;
        }


        if (event.getClickedBlock() == null) {
            return;
        }

        Location location = event.getClickedBlock().getLocation();

        if (StorageControllerBlock.isStorageController(location)) {
            StorageTerminal.setPairedLocation(event.getItem(), location);
            event.getPlayer().sendMessage(Language.PAIRED_WITH_CONTROLLER_MESSAGE);
        }







    }


}
