package com.emeraldingot.storagesystem.item;

import com.emeraldingot.storagesystem.StorageSystem;
import com.emeraldingot.storagesystem.impl.StorageCellType;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.UUID;

public abstract class StorageCell {
    public static final NamespacedKey CELL_UUID_KEY = new NamespacedKey(StorageSystem.getInstance(), "cell_uuid");
    public static final NamespacedKey CELL_TYPE_KEY = new NamespacedKey(StorageSystem.getInstance(), "cell_type");
    public static final NamespacedKey BYTES_USED_KEY = new NamespacedKey(StorageSystem.getInstance(), "bytes_used");
    public static final UUID EMPTY_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    public static int getBytesUsed(ItemStack itemStack) {
        int bytesUsed = itemStack.getItemMeta().getPersistentDataContainer().get(BYTES_USED_KEY, PersistentDataType.INTEGER);
        return bytesUsed;
    }


    public ItemStack getStack() {
        return getStack(0, null);
    };

    public ItemStack getStack(int filledBytes, UUID uuid) {
        return null;
    }

    public int getCapacity() {
        return 0;
    }

    // Assumes that itemStack is a StorageCell
    public static UUID getUuid(ItemStack itemStack) {

        return UUID.fromString(itemStack.getItemMeta().getPersistentDataContainer().get(CELL_UUID_KEY, PersistentDataType.STRING));
    }

    public static int getCapacity(ItemStack itemStack) {
        StorageCellType storageCellType = StorageCellType.valueOf(itemStack.getItemMeta().getPersistentDataContainer().get(CELL_TYPE_KEY, PersistentDataType.STRING));
        return storageCellType.getCell().getCapacity();
    }
}
