package com.emeraldingot.storagesystem.event;


import com.emeraldingot.storagesystem.item.*;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.A;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CraftItemListener implements Listener {
    @EventHandler
    public void onBlockDispense(CraftItemEvent event) throws SQLException {


        Recipe recipe = event.getRecipe();


        if (
                isSameName(recipe.getResult(), StorageCell4K.getStack()) ||
                isSameName(recipe.getResult(), StorageCell16K.getStack()) ||
                isSameName(recipe.getResult(), StorageCell64K.getStack()) ||
                isSameName(recipe.getResult(), StorageCell256K.getStack())
        ) {
            System.out.println("crafted storage cell - copying disk data");
            ItemStack newCell = event.getCurrentItem();
            ItemStack oldCell = event.getInventory().getItem(5);

            ItemMeta newMeta = newCell.getItemMeta();
            List<String> newLore = newMeta.getLore();
            List<String> oldLore = oldCell.getItemMeta().getLore();
            // Stored: 0/1024 bytes
            String oldFilledAmount = oldLore.get(0).split(": ")[1].split("/")[0];
            String newMaxAmount = newLore.get(0).split(": ")[1].split("/")[1];
            newLore.set(0, ChatColor.WHITE + "Stored: " + oldFilledAmount + "/" + newMaxAmount);
            newLore.set(1, ChatColor.WHITE + "Cell ID: " + oldLore.get(1).split(": ")[1]);
            newMeta.setLore(newLore);
            newCell.setItemMeta(newMeta);


//            newCell.setItemMeta(itemMeta);

        }
//
        event.setCancelled(false);

    }

    private boolean isSameName(ItemStack firstStack, ItemStack secondStack) {
        try {
            String firstName = firstStack.getItemMeta().getItemName();
            String secondName = secondStack.getItemMeta().getItemName();
            return firstName.equals(secondName);
        }
        catch (NullPointerException e) {
            return false;
        }

    }



}
