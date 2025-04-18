package com.emeraldingot.storagesystem.event;

import com.emeraldingot.storagesystem.StorageSystem;
import com.emeraldingot.storagesystem.block.StorageControllerBlock;
import com.emeraldingot.storagesystem.impl.ControllerManager;
import com.emeraldingot.storagesystem.impl.DatabaseManager;
import com.emeraldingot.storagesystem.impl.StorageCellData;
import com.emeraldingot.storagesystem.impl.StorageSystemGUI;
import com.emeraldingot.storagesystem.langauge.Language;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftInventoryCustom;
import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftInventoryPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class InventoryListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) throws SQLException {

//        if(!event.getAction().equals(InventoryAction.PLACE_ALL) && !event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
//            return;
//        }

        String title = event.getView().getTitle();
        if (!title.startsWith(Language.STORAGE_SYSTEM_TITLE) && !(event.getInventory() instanceof CraftInventoryCustom || event.getInventory() instanceof CraftInventoryPlayer)) {
            return;
        }
        ItemStack infoItem = event.getView().getTopInventory().getItem(49);
        StorageCellData storageCellData = StorageCellData.fromLore(infoItem.getItemMeta().getLore());

        UUID cellUUID = storageCellData.getUUID();
        Location blockLocation = storageCellData.getLocation();

        if (!StorageControllerBlock.isStorageController(blockLocation)) {
            event.setCancelled(true);
            (event.getWhoClicked()).closeInventory();
            return;
        }
        

        ItemStack itemStack = event.getCurrentItem();

        if (StorageCellData.isStorageCell(itemStack)) {
            event.setCancelled(true);
            return;
        }



        if (itemStack != null && itemStack.getItemMeta() != null && event.getSlot() >= 45) {
            int pageNumber = Integer.parseInt(event.getInventory().getItem(49).getItemMeta().getLore().get(2).split("§8")[1]);
            int pageCount = StorageSystemGUI.getPageCount(cellUUID);

            if (itemStack.getItemMeta().getItemName().equals(ChatColor.RED + "Next Page")) {
                if (pageNumber < (pageCount - 1)) {
                    updateInventory(event, cellUUID, blockLocation,pageNumber + 1, pageCount);

                }
//                event.getWhoClicked().openInventory(CashoutCommand.getCompleteStoragePage(1));
            }



            if (itemStack.getItemMeta().getItemName().equals(ChatColor.RED + "Last Page")) {
                updateInventory(event, cellUUID, blockLocation, pageCount - 1, pageCount);
//                event.getWhoClicked().closeInventory();
//                event.getWhoClicked().openInventory(CashoutCommand.getCompleteLastPage());
            }

            if (itemStack.getItemMeta().getItemName().equals(ChatColor.RED + "Previous Page")) {
                if (pageNumber > 0) {
                    updateInventory(event, cellUUID, blockLocation, pageNumber - 1, pageCount);
                }

//                event.getWhoClicked().closeInventory();
//                event.getWhoClicked().openInventory(CashoutCommand.getCompleteStoragePage(0));
            }

            if (itemStack.getItemMeta().getItemName().equals(ChatColor.RED + "First Page")) {
                updateInventory(event, cellUUID, blockLocation, 0, pageCount);
//                event.getWhoClicked().closeInventory();
//                event.getWhoClicked().openInventory(CashoutCommand.getCompleteStoragePage(0));
            }

            if (itemStack.getItemMeta().getItemName().equals(ChatColor.RED + "Search")) {
                event.getWhoClicked().closeInventory();
                StorageSystemGUI.openSearch((Player) event.getWhoClicked(), cellUUID, blockLocation);
            }


            event.setCancelled(true);
            return;
        }

        if (event.getAction().equals(InventoryAction.COLLECT_TO_CURSOR)) {
            event.setCancelled(true);
            return;
        }

        if (event.getAction().equals(InventoryAction.PICKUP_ALL) && event.getClickedInventory() instanceof CraftInventoryCustom) {
//            System.out.println("Removed item from storage system");
            removeItem(event, cellUUID, blockLocation, itemStack);
        }

        if (event.getAction().equals(InventoryAction.PICKUP_HALF) && event.getClickedInventory() instanceof CraftInventoryCustom) {
            ItemStack newItemStack = itemStack.clone();
            newItemStack.setAmount((itemStack.getAmount() + 1) / 2);
//            System.out.println("Removed item from storage system");
            removeItem(event, cellUUID, blockLocation, newItemStack);
        }

        if (event.getAction().equals(InventoryAction.PICKUP_ONE) && event.getClickedInventory() instanceof CraftInventoryCustom) {
            ItemStack newItemStack = itemStack.clone();
            newItemStack.setAmount(1);
//            System.out.println("Removed item from storage system");
            removeItem(event, cellUUID, blockLocation, newItemStack);
        }

//        System.out.println(event.getAction());

        if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) && event.getClickedInventory() instanceof CraftInventoryCustom) {
//            System.out.println("Removed item from storage system");
            removeItem(event, cellUUID, blockLocation, itemStack);

        }

        if (event.getAction().equals(InventoryAction.PLACE_ALL) && event.getClickedInventory() instanceof CraftInventoryCustom) {
            itemStack = event.getCursor();
            if (!ControllerManager.getInstance().canHold(blockLocation, itemStack)) {
                event.setCancelled(true);
                return;
            }
//            System.out.println("Added item to storage system");

            addItem(event, cellUUID, blockLocation, itemStack);

        }

        if (event.getAction().equals(InventoryAction.PLACE_ONE) && event.getClickedInventory() instanceof CraftInventoryCustom) {
            itemStack = event.getCursor();
            ItemStack newItemStack = itemStack.clone();
            newItemStack.setAmount(1);
            if (!ControllerManager.getInstance().canHold(blockLocation, newItemStack)) {
                event.setCancelled(true);
                return;
            }
//            System.out.println("Added item to storage system");
            addItem(event, cellUUID, blockLocation, newItemStack);


        }

        if (event.getAction().equals(InventoryAction.HOTBAR_SWAP)) {
            event.setCancelled(true);
            return;
        }

        if (event.getAction().equals(InventoryAction.HOTBAR_MOVE_AND_READD)) {
            event.setCancelled(true);
            return;
        }

        if (event.getAction().equals(InventoryAction.DROP_ALL_SLOT) && event.getClickedInventory() instanceof CraftInventoryCustom) {
            event.setCancelled(true);
            return;
        }

        if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) && event.getClickedInventory() instanceof CraftInventoryPlayer) {
            if (!ControllerManager.getInstance().canHold(blockLocation, itemStack)) {
                event.setCancelled(true);
                return;
            }
//            System.out.println("Added item to storage system");
            addItem(event, cellUUID, blockLocation, itemStack);

            // This is in case they shift clicked the item when they are on a full page
            if (event.getInventory().firstEmpty() == -1) {
                if (event.isCancelled()) {
                    return;
                }
                event.getWhoClicked().getInventory().setItem(event.getSlot(), null);

                int pageNumber = Integer.parseInt(event.getInventory().getItem(49).getItemMeta().getLore().get(2).split("§8")[1]);
                int pageCount = StorageSystemGUI.getPageCount(cellUUID);
                updateInventory(event, cellUUID, blockLocation, pageNumber, pageCount);
            }

            // update the page to make it feel responsive


//                event.getWhoClicked().openInventory(CashoutCommand.getCompleteStoragePage(1));
//                event.getWhoClicked().getInventory().removeItem(itemStack);


//
        }

        if (event.getAction().equals(InventoryAction.SWAP_WITH_CURSOR) && event.getClickedInventory() instanceof CraftInventoryCustom) {
            // try taking old item stack
//            System.out.println("Removing item from storage system");
            removeItem(event, cellUUID, blockLocation, event.getCurrentItem());

            if (!ControllerManager.getInstance().canHold(blockLocation, event.getCursor())) {
                // if you can't put down the new one, add original item back
                addItem(event, cellUUID, blockLocation, event.getCurrentItem());
                event.setCancelled(true);
                return;
            }

//            System.out.println("Added item to storage system");
            addItem(event, cellUUID, blockLocation, event.getCursor());

//            addItem(itemStack);
        }

//        if (event.getAction().equals(InventoryAction.DROP_ALL_SLOT)) {
////            System.out.println("Removing item from storage system");
//            removeItem(event, cellUUID, blockLocation, event.getCurrentItem());
//        }




//        ItemStack itemStack = event.getCurrentItem();
//        System.out.println(event.getInventory());
//        System.out.println(event.getClickedInventory());



        event.setCancelled(false);

    }


    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) throws SQLException {

//        if(!event.getAction().equals(InventoryAction.PLACE_ALL) && !event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
//            return;
//        }

        String title = event.getView().getTitle();
        if (!title.startsWith(Language.STORAGE_SYSTEM_TITLE)) {
            return;
        }

        if (event.getInventory() instanceof CraftInventoryCustom) {
            event.setCancelled(true);
            return;
        }

//        System.out.println(event.getInventory());
//
//        for (ItemStack itemStack : event.getNewItems().values()) {
//            System.out.println(itemStack.getAmount());
//            System.out.println("Added item to storage system");
//            addItem(itemStack);
//        }





//        ItemStack itemStack = event.getCurrentItem();
//        System.out.println(event.getInventory());
//        System.out.println(event.getClickedInventory());



        event.setCancelled(false);

    }

    private void addItem(InventoryClickEvent event, UUID uuid, Location location, ItemStack itemStack) throws SQLException {
        DatabaseManager.getInstance().addItemsToCell(uuid, itemStack);
        ControllerManager.getInstance().changeUsed(location, itemStack, false);
    }

    private void removeItem(InventoryClickEvent event, UUID uuid, Location location, ItemStack itemStack) throws SQLException {

        DatabaseManager.getInstance().removeItemsFromCell(uuid, itemStack);
        ControllerManager.getInstance().changeUsed(location, itemStack, true);
    }

    private void updateInventory(InventoryClickEvent event, UUID uuid, Location location, int pageNumber, int pageCount) throws SQLException {
        event.getWhoClicked().getOpenInventory().getTopInventory().setContents(StorageSystemGUI.getCompleteStoragePage(uuid, location, pageNumber).getContents());
        updateTitle(event, pageNumber, pageCount);
    }

    private void updateTitle(InventoryClickEvent event, int pageNumber, int pageCount) {
        event.getView().setTitle(Language.STORAGE_SYSTEM_TITLE + " (" + (pageNumber + 1) + "/" + pageCount + ")");
    }
}
