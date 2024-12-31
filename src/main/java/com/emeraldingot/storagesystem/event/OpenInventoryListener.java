package com.emeraldingot.storagesystem.event;


import com.emeraldingot.storagesystem.impl.ControllerManager;
import com.emeraldingot.storagesystem.util.ControllerFileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;

public class OpenInventoryListener implements Listener {
    @EventHandler
    public void onBlockDispense(InventoryOpenEvent event) throws SQLException {

        if (!event.getView().getTitle().startsWith(ChatColor.RED + "Storage System")) {
            return;
        }

        ItemStack infoItem = event.getView().getTopInventory().getItem(49);

        double x = Double.parseDouble(infoItem.getItemMeta().getLore().get(1).split("X: ")[1].split(" Y:")[0]);
        double y = Double.parseDouble(infoItem.getItemMeta().getLore().get(1).split(" Y: ")[1].split(" Z: ")[0]);
        double z = Double.parseDouble(infoItem.getItemMeta().getLore().get(1).split(" Z: ")[1].split(" W: ")[0]);
        World world = Bukkit.getWorld(infoItem.getItemMeta().getLore().get(1).split(" W: ")[1]);
        Location blockLocation = new Location(world, x, y, z);

        if (!ControllerManager.getInstance().getControllerLocations().contains(blockLocation)) {
            event.setCancelled(true);
            return;
        }



    }



}
