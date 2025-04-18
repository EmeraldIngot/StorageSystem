package com.emeraldingot.storagesystem;


import com.emeraldingot.storagesystem.command.CashoutCommand;
import com.emeraldingot.storagesystem.event.*;
import com.emeraldingot.storagesystem.impl.ControllerManager;
import com.emeraldingot.storagesystem.impl.DatabaseManager;
import com.emeraldingot.storagesystem.recipe.*;
import com.emeraldingot.storagesystem.util.ControllerFileManager;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class StorageSystem extends JavaPlugin {
    private static StorageSystem instance;

    public static StorageSystem getInstance() {
        return instance;
    }

    public static ArrayList<Player> playingMusic = new ArrayList<>();

    private DatabaseManager databaseManager;

    public UUID cellUUID;
    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
        ControllerFileManager.getInstance().initData(this);

        // Events
//        getServer().getPluginManager().registerEvents(new InventoryPickupItemListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new StorageCellPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new StorageControllerPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new StorageControllerBreakListener(), this);
        getServer().getPluginManager().registerEvents(new StorageControllerExplodeListener(), this);
        getServer().getPluginManager().registerEvents(new StorageControllerInventoryListener(), this);
        getServer().getPluginManager().registerEvents(new CraftItemListener(), this);
        getServer().getPluginManager().registerEvents(new PrepareCraftItemsListener(), this);
        getServer().getPluginManager().registerEvents(new BlockDispenseListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new CloseInventoryListener(), this);
        getServer().getPluginManager().registerEvents(new OpenInventoryListener(), this);

        // Commands
        this.getCommand("cashout").setExecutor(new CashoutCommand());
//        this.getCommand("cashout").setTabCompleter(new VoucherTabCompleter());
//        this.getCommand("voucher").setExecutor(new VoucherCommand());
//        this.getCommand("voucher").setTabCompleter(new VoucherTabCompleter());

        // Recipes
        try {
            new StorageCoreRecipe().registerRecipe(this);
            new StorageCell1KRecipe().registerRecipe(this);
            new StorageCell4KRecipe().registerRecipe(this);
            new StorageCell16KRecipe().registerRecipe(this);
            new StorageCell64KRecipe().registerRecipe(this);
            new StorageCell256KRecipe().registerRecipe(this);
            new StorageControllerRecipe().registerRecipe(this);
            new StorageTerminalRecipe().registerRecipe(this);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }


//        MoneyFileManager.getInstance().initData(this);

        try {
            // Ensure the plugin's data folder exists
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            // Initialize the DatabaseManager with the path to the database file
            // we make it 'databaseManager =' because we will use it when registering events.
            // The file name will be 'database.db' but you can change that here.
            databaseManager = new DatabaseManager(getDataFolder().getAbsolutePath() + "/database.db");
        } catch (SQLException e) {
            e.printStackTrace();
            // Disable the plugin if the database connection fails, because we don't want enabled plugin with no functionality.
            Bukkit.getPluginManager().disablePlugin(this);
        }
        instance = this;

//        this.getCommand("gamble").setExecutor(new GambleCommand(databaseManager));
//        this.getCommand("cashout").setExecutor(new CashoutCommand());



//        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
//            public void run() {
//            }
//        }, 20, 1);



    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
        try {
            // Close the database connection when the plugin is disabled
            if (databaseManager != null) {
                databaseManager.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}