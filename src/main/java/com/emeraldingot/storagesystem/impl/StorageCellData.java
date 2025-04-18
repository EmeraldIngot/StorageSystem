package com.emeraldingot.storagesystem.impl;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

public class StorageCellData {
    private UUID uuid;
    private Location location;
    public StorageCellData(UUID uuid, Location location) {
        this.uuid = uuid;
        this.location = location;
    }
    public static boolean isStorageCell(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null && itemMeta.getLore() != null && itemMeta.getLore().size() > 1 && itemMeta.getLore().get(1).contains("Cell ID")) {
            return true;
        }
        else {
            return false;
        }
    }

    public static StorageCellData fromGUILore(List<String> lore) {
        UUID cellUUID = UUID.fromString(lore.get(0).split("§8")[1]);
        double x = Double.parseDouble(lore.get(1).split("X: ")[1].split(" Y:")[0]);
        double y = Double.parseDouble(lore.get(1).split(" Y: ")[1].split(" Z: ")[0]);
        double z = Double.parseDouble(lore.get(1).split(" Z: ")[1].split(" W: ")[0]);
        World world = Bukkit.getWorld(lore.get(1).split(" W: ")[1]);
        Location blockLocation = new Location(world, x, y, z);
        return new StorageCellData(cellUUID, blockLocation);
    }

    public static StorageCellData fromItemLore(List<String> lore) {
        UUID cellUUID = UUID.fromString(lore.get(1).split("§fCell ID: ")[1]);
        Location blockLocation = null;
        return new StorageCellData(cellUUID, blockLocation);
    }

    public UUID getUUID() {
        return uuid;
    }

    public Location getLocation() {
        return location;
    }
}
