package com.emeraldingot.storagesystem.block;

import com.emeraldingot.storagesystem.StorageSystem;
import com.emeraldingot.storagesystem.langauge.Language;
import com.emeraldingot.storagesystem.util.SkullUtil;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Dispenser;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class StorageControllerBlock {

    private static final int[] SPACER_SLOTS = {0,1,2,3,5,6,7,8};

    public static final NamespacedKey CONTROLLER_KEY = new NamespacedKey(StorageSystem.getInstance(), "storage_controller");

    public static ItemStack getStack()  {
        ItemStack storageController = new ItemStack(Material.DISPENSER);
        ItemMeta itemMeta = storageController.getItemMeta();
        itemMeta.setItemName(ChatColor.YELLOW + "Storage Controller");
        itemMeta.setDisplayName(ChatColor.YELLOW + "Storage Controller");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GRAY + "StorageSystem");
        itemMeta.setLore(lore);

        itemMeta.getPersistentDataContainer().set(CONTROLLER_KEY, PersistentDataType.BYTE, (byte) 0);

        storageController.setItemMeta(itemMeta);


        return storageController;
    }

    public static void setOffline(Inventory inventory) {

        ItemStack indicator = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta indicatorMeta = indicator.getItemMeta();
        indicatorMeta.setItemName(ChatColor.RED + "OFFLINE");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GRAY + "Indicator");
        indicatorMeta.setLore(lore);
        indicator.setItemMeta(indicatorMeta);


        for (int slot : SPACER_SLOTS) {
            inventory.setItem(slot, indicator);
        }


    }

    public static void setOnline(Inventory inventory) {

        ItemStack indicator = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta indicatorMeta = indicator.getItemMeta();
        indicatorMeta.setItemName(ChatColor.GREEN + "ONLINE");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GRAY + "Indicator");
        indicatorMeta.setLore(lore);
        indicator.setItemMeta(indicatorMeta);

        for (int slot : SPACER_SLOTS) {
            inventory.setItem(slot, indicator);
        }

    }

    public static void clearStatusSlots(Inventory inventory) {

        for (int slot : SPACER_SLOTS) {
            inventory.setItem(slot, null);
        }

    }

    public static boolean isStatusPane(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return false;
        }
        if (itemMeta.getLore() == null) {
            return false;
        }
        if (!(itemMeta.getLore().size() == 1)) {
            return false;
        }
        if (itemMeta.getLore().get(0).equals(Language.INDICATOR_LORE)) {
            return true;
        }
        return false;
    }

    public static boolean isStorageController(Location location) {
        if (location.getBlock().getType() != Material.DISPENSER) {
            return false;
        }
        Dispenser dispenser = (Dispenser) location.getBlock().getState();

        return dispenser.getPersistentDataContainer().has(CONTROLLER_KEY);
    }

    public static boolean isStorageController(ItemStack itemStack) {
        if (itemStack.getItemMeta() == null) {
            return false;
        }



        return itemStack.getItemMeta().getPersistentDataContainer().has(CONTROLLER_KEY);
    }

}
