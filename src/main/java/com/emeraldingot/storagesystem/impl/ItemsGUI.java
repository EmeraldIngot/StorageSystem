package com.emeraldingot.storagesystem.impl;

import com.emeraldingot.storagesystem.block.StorageControllerBlock;
import com.emeraldingot.storagesystem.item.*;
import com.emeraldingot.storagesystem.langauge.Language;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

public class ItemsGUI {
    public static Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(null , 9 * 2, Language.STORAGE_SYSTEM_ITEMS_TITLE);
        inventory.addItem(StorageCore.getStack());
        inventory.addItem(StorageCell1K.getStack());
        inventory.addItem(StorageCell4K.getStack());
        inventory.addItem(StorageCell16K.getStack());
        inventory.addItem(StorageCell64K.getStack());
        inventory.addItem(StorageCell256K.getStack());
        inventory.addItem(StorageControllerBlock.getStack());
        inventory.addItem(StorageTerminal.getStack());
        return inventory;
    }
}
