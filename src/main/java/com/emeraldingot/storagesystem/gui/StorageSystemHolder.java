package com.emeraldingot.storagesystem.gui;

import com.emeraldingot.storagesystem.impl.StorageCellData;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class StorageSystemHolder implements InventoryHolder {
    private StorageCellData storageCellData;

    public StorageSystemHolder (StorageCellData storageCellData) {
        this.storageCellData = storageCellData;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    public StorageCellData getStorageCellData() {
        return storageCellData;
    }
}
