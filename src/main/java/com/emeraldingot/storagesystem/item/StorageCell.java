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
    public static final UUID EMPTY_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    public static int getFilledAmount(ItemStack itemStack) {
        List<String> lore = itemStack.getItemMeta().getLore();

        String[] parts = lore.get(0).split(" ");

        // 0/1024
        String filledAmountString = parts[1].split("/")[0];
        return Integer.parseInt(filledAmountString);

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

    public static UUID getUuid(ItemStack itemStack) {
        return UUID.fromString(itemStack.getItemMeta().getPersistentDataContainer().get(CELL_UUID_KEY, PersistentDataType.STRING));
    }
}
