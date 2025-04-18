package com.emeraldingot.storagesystem.event;


import com.emeraldingot.storagesystem.block.StorageControllerBlock;
import com.emeraldingot.storagesystem.item.*;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
import java.util.List;

public class PrepareCraftItemsListener implements Listener {
    @EventHandler
    public void onBlockDispense(PrepareItemCraftEvent event) throws SQLException {
        // The exact choice recipe matcher does not work with differing lore so it is set to accept player heads instead
        // this checks to make sure the correct player head is in place

        Recipe recipe = event.getRecipe();

        if (event.getInventory().getResult() == null) {
            return;
        }

        // Recipe: 1k cell
        if (isSameName(event.getInventory().getResult(), StorageCell1K.getStack())) {;
            if (isSameName(event.getInventory().getItem(5), StorageCore.getStack())) {
                return;
            }
            else {
                event.getInventory().setResult(null);
            }
        }

        // Recipe: 4k cell
        if (isSameName(event.getInventory().getResult(), StorageCell4K.getStack())) {
            if (isSameName(event.getInventory().getItem(5), StorageCell1K.getStack())) {
                return;
            }
            else {
                event.getInventory().setResult(null);
            }
        }

        // Recipe: 16k cell
        if (isSameName(event.getInventory().getResult(), StorageCell16K.getStack())) {
            if (isSameName(event.getInventory().getItem(5), StorageCell4K.getStack())) {
                return;
            }
            else {
                event.getInventory().setResult(null);
            }
        }

        // Recipe: 64k cell
        if (isSameName(event.getInventory().getResult(), StorageCell64K.getStack())) {
            if (isSameName(event.getInventory().getItem(5), StorageCell16K.getStack())) {
                return;
            }
            else {
                event.getInventory().setResult(null);
            }
        }

        // Recipe: 256k cell
        if (isSameName(event.getInventory().getResult(), StorageCell256K.getStack())) {
            if (isSameName(event.getInventory().getItem(5), StorageCell64K.getStack())) {
                return;
            }
            else {
                event.getInventory().setResult(null);
            }
        }

        // Recipe: Storage Controller
        if (isSameName(event.getInventory().getResult(), StorageControllerBlock.getStack())) {
            if (isSameName(event.getInventory().getItem(5), StorageCore.getStack())) {
                return;
            }
            else {
                event.getInventory().setResult(null);
            }
        }





//            newCell.setItemMeta(itemMeta);

        }
//

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
