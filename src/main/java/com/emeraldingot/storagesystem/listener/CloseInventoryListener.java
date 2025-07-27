package com.emeraldingot.storagesystem.listener;


import com.emeraldingot.storagesystem.impl.ControllerManager;
import com.emeraldingot.storagesystem.langauge.Language;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;

public class CloseInventoryListener implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) throws SQLException {

        if (!event.getView().getTitle().startsWith(Language.STORAGE_SYSTEM_TITLE)) {
            return;
        }

        ItemStack infoItem = event.getView().getTopInventory().getItem(49);

        if (infoItem == null) {
            return;
        }

        double x = Double.parseDouble(infoItem.getItemMeta().getLore().get(1).split("X: ")[1].split(" Y:")[0]);
        double y = Double.parseDouble(infoItem.getItemMeta().getLore().get(1).split(" Y: ")[1].split(" Z: ")[0]);
        double z = Double.parseDouble(infoItem.getItemMeta().getLore().get(1).split(" Z: ")[1].split(" W: ")[0]);
        World world = Bukkit.getWorld(infoItem.getItemMeta().getLore().get(1).split(" W: ")[1]);
        Location blockLocation = new Location(world, x, y, z);

        if (!ControllerManager.getInstance().getControllerLocations().contains(blockLocation)) {
            return;
        }


        ControllerManager.getInstance().recalculateUsed(blockLocation);
        ControllerManager.getInstance().closeController(blockLocation);


    }



}
