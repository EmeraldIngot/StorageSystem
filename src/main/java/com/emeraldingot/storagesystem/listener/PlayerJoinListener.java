package com.emeraldingot.storagesystem.listener;


import com.emeraldingot.storagesystem.StorageSystem;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {

        event.getPlayer().discoverRecipe(new NamespacedKey(StorageSystem.getInstance(), "storage_cell_1k"));
        event.getPlayer().discoverRecipe(new NamespacedKey(StorageSystem.getInstance(), "storage_cell_4k"));
        event.getPlayer().discoverRecipe(new NamespacedKey(StorageSystem.getInstance(), "storage_cell_16k"));
        event.getPlayer().discoverRecipe(new NamespacedKey(StorageSystem.getInstance(), "storage_cell_64k"));
        event.getPlayer().discoverRecipe(new NamespacedKey(StorageSystem.getInstance(), "storage_cell_256k"));
        event.getPlayer().discoverRecipe(new NamespacedKey(StorageSystem.getInstance(), "storage_controller"));
        event.getPlayer().discoverRecipe(new NamespacedKey(StorageSystem.getInstance(), "storage_core"));
        event.getPlayer().discoverRecipe(new NamespacedKey(StorageSystem.getInstance(), "storage_terminal"));

    }



}
