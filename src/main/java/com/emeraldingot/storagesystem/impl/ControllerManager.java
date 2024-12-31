package com.emeraldingot.storagesystem.impl;

import com.emeraldingot.storagesystem.StorageSystem;
import com.emeraldingot.storagesystem.block.StorageControllerBlock;
import com.emeraldingot.storagesystem.util.ControllerFileManager;
import net.minecraft.world.entity.ai.control.ControllerLook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.naming.ldap.Control;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ControllerManager {
    private ArrayList<Location> storageControllers = new ArrayList<>();

    public ControllerManager() {
        try {
            this.storageControllers = ControllerFileManager.getInstance().loadControllers();
        }
        catch (Exception e) {
            return;
        }
    }
    private static ControllerManager instance;

    public static ControllerManager getInstance() {
        if (instance == null) {
            instance = new ControllerManager();
        }
        return instance;
    }


    public void addController(Location location) {
        storageControllers.add(location);
        ControllerFileManager.getInstance().writeControllers(ControllerManager.getInstance().getControllerLocations());
    }

    public ArrayList<Location> getControllerLocations() {
        return storageControllers;
    }

    public void removeController(Location location) {
        storageControllers.remove(location);
        ControllerFileManager.getInstance().writeControllers(ControllerManager.getInstance().getControllerLocations());
    }

    public boolean isOnline(Location location) {
        Dispenser dispenser = (Dispenser) location.getBlock().getState();

        if (dispenser.getInventory().getItem(4) == null) {
            return false;
        }
        else {
            return true;
        }
    }

    public UUID getID(Location location) {
        Dispenser dispenser = (Dispenser) location.getBlock().getState();

        if (!isOnline(location)) {
            return null;
        }
        ItemStack cell = dispenser.getInventory().getItem(4);
        String line = cell.getItemMeta().getLore().get(1);
        try {
            UUID uuid = UUID.fromString(line.split(": ")[1]);
            return uuid;
        }
        catch (Exception e) {
            return null;
        }

    }

    public void updateState(Location location) {
        Dispenser dispenser = (Dispenser) location.getBlock().getState();


        if (isOnline(location)) {
            StorageControllerBlock.setOnline(dispenser.getInventory());
            if (getID(location) == null) {
                generateID(location);
            }
        }
        else {
            StorageControllerBlock.setOffline(dispenser.getInventory());
        }
        try {
            recalculateUsed(location);
        }
        catch (Exception e) {
            return;
        }
    }

    private void generateID(Location location) {
        UUID uuid = UUID.randomUUID();
        Dispenser dispenser = (Dispenser) location.getBlock().getState();

        ItemStack cell = dispenser.getInventory().getItem(4);
        ItemMeta itemMeta = cell.getItemMeta();
        List<String> lore = itemMeta.getLore();
        lore.set(1, ChatColor.WHITE + "Cell ID: " + uuid);
        itemMeta.setLore(lore);
        cell.setItemMeta(itemMeta);
        try {
            DatabaseManager.getInstance().addStorageCell(uuid);
        }
        catch (Exception e) {
            return;
        }


    }

    public Location getNearestController(Location playerLocation) {
        Location closestLocation = playerLocation;
        double closestLocationDistance = Integer.MAX_VALUE;

        for (Location location : storageControllers) {
            double distance = location.distance(playerLocation);
            if (distance < closestLocationDistance) {
                closestLocation = location;
                closestLocationDistance = distance;
            }
        }

        if (closestLocationDistance <= 10) {
            return closestLocation;
        }
        else {
            return null;
        }
    }

    // TODO: refactor this into the database and store the locations, their current cell UUID, and capacity max and current

    // TODO: centralize changing of these fields
    public void changeUsed(Location location, ItemStack itemStack, boolean subtract) {
        Dispenser dispenser = (Dispenser) location.getBlock().getState();
        ItemStack cell = dispenser.getInventory().getItem(4);
        ItemMeta itemMeta = cell.getItemMeta();
        List<String> lore = itemMeta.getLore();

        int oldFilledAmount = Integer.parseInt(lore.get(0).split(": ")[1].split("/")[0]);
        String maxAmount = lore.get(0).split(": ")[1].split("/")[1];
        int modifier = subtract ? -1 : 1;
//        System.out.println(getScaledAmount(itemStack) * modifier);
        lore.set(0, ChatColor.WHITE + "Stored: " + (oldFilledAmount + getScaledAmount(itemStack) * modifier) + "/" + maxAmount);
        itemMeta.setLore(lore);
        cell.setItemMeta(itemMeta);
    }

    public boolean canHold(Location location, ItemStack itemStack) {
        Dispenser dispenser = (Dispenser) location.getBlock().getState();
        ItemStack cell = dispenser.getInventory().getItem(4);
        ItemMeta itemMeta = cell.getItemMeta();
        List<String> lore = itemMeta.getLore();


        int oldFilledAmount = Integer.parseInt(lore.get(0).split(": ")[1].split("/")[0]);
        int maxAmount = Integer.parseInt(lore.get(0).split(": ")[1].split("/")[1].split(" bytes")[0]);
        if ((oldFilledAmount + getScaledAmount(itemStack)) > maxAmount) {
            return false;
        }
        else {
            return true;
        }
    }

    public void recalculateUsed(Location location) throws SQLException {
        Dispenser dispenser = (Dispenser) location.getBlock().getState();
        ItemStack cell = dispenser.getInventory().getItem(4);
        ItemMeta itemMeta = cell.getItemMeta();
        List<String> lore = itemMeta.getLore();
        UUID uuid = UUID.fromString(lore.get(1).split("Cell ID: ")[1]);
        List<ItemStack> itemStacks = DatabaseManager.getInstance().getItemsByCellUUID(uuid);
        int total = 0;
        for (ItemStack itemStack : itemStacks) {
            total += getScaledAmount(itemStack);
        }

        String maxAmount = lore.get(0).split(": ")[1].split("/")[1];
        lore.set(0, ChatColor.WHITE + "Stored: " + (total) + "/" + maxAmount);
        itemMeta.setLore(lore);
        cell.setItemMeta(itemMeta);
    }

    public int getScaledAmount(ItemStack itemStack) {
        return (64 / itemStack.getMaxStackSize()) * itemStack.getAmount();
    }








}
