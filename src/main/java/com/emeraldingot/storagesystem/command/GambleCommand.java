package com.emeraldingot.storagesystem.command;

import com.emeraldingot.storagesystem.StorageSystem;
import com.emeraldingot.storagesystem.impl.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.UUID;

public class GambleCommand implements CommandExecutor {
    private final DatabaseManager databaseManager;
    private final UUID storageCellUUID = UUID.randomUUID();

    public GambleCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        StorageSystem.getInstance().cellUUID = storageCellUUID;
        Bukkit.getScheduler().runTaskAsynchronously(StorageSystem.getInstance(), () -> {
            try {
                // Add the player to the database
                // We don't need to check if player exists because we already do it in addPlayer method.
                databaseManager.addStorageCell(storageCellUUID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;

//        UUID storageCellUUID = UUID.randomUUID();

        ItemStack itemStack = player.getInventory().getItemInMainHand();

        Bukkit.getScheduler().runTaskAsynchronously(StorageSystem.getInstance(), () -> {
            try {
                // Add the player to the database
                // We don't need to check if player exists because we already do it in addPlayer method.

                databaseManager.addItemsToCell(storageCellUUID, itemStack);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return true;
    }



}
