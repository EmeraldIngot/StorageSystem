package com.emeraldingot.storagesystem.listener.controller;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import java.sql.SQLException;

public class StorageControllerEntityChangeListener implements Listener {
    @EventHandler
    public void onBlockPlace(EntityChangeBlockEvent event) throws SQLException {
        event.setCancelled(true);


    }



}
