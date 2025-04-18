package com.emeraldingot.storagesystem.block;

import com.emeraldingot.storagesystem.langauge.Language;
import com.emeraldingot.storagesystem.util.SkullUtil;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class StorageControllerBlock {

    public static ItemStack getStack()  {
        ItemStack storageController = new ItemStack(Material.DISPENSER);
        ItemMeta itemMeta = storageController.getItemMeta();
        itemMeta.setItemName(ChatColor.YELLOW + "Storage Controller");
        itemMeta.setDisplayName(ChatColor.YELLOW + "Storage Controller");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GRAY + "StorageSystem");
        itemMeta.setLore(lore);
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

        inventory.setItem(0, indicator);
        inventory.setItem(1, indicator);
        inventory.setItem(2, indicator);
        inventory.setItem(3, indicator);
        inventory.setItem(5, indicator);
        inventory.setItem(6, indicator);
        inventory.setItem(7, indicator);
        inventory.setItem(8, indicator);
    }

    public static void setOnline(Inventory inventory) {

        ItemStack indicator = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta indicatorMeta = indicator.getItemMeta();
        indicatorMeta.setItemName(ChatColor.GREEN + "ONLINE");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GRAY + "Indicator");
        indicatorMeta.setLore(lore);
        indicator.setItemMeta(indicatorMeta);

        inventory.setItem(0, indicator);
        inventory.setItem(1, indicator);
        inventory.setItem(2, indicator);
        inventory.setItem(3, indicator);
        inventory.setItem(5, indicator);
        inventory.setItem(6, indicator);
        inventory.setItem(7, indicator);
        inventory.setItem(8, indicator);
    }

    public static void clearStatusSlots(Inventory inventory) {

        inventory.setItem(0, null);
        inventory.setItem(1, null);
        inventory.setItem(2, null);
        inventory.setItem(3, null);
        inventory.setItem(5, null);
        inventory.setItem(6, null);
        inventory.setItem(7, null);
        inventory.setItem(8, null);
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
        return dispenser.getCustomName().equals(Language.STORAGE_CONTROLLER_ITEM);
    }

}
