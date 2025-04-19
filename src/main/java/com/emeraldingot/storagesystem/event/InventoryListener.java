package com.emeraldingot.storagesystem.event;

import com.emeraldingot.storagesystem.block.StorageControllerBlock;
import com.emeraldingot.storagesystem.impl.ControllerManager;
import com.emeraldingot.storagesystem.impl.DatabaseManager;
import com.emeraldingot.storagesystem.impl.StorageCellData;
import com.emeraldingot.storagesystem.impl.StorageSystemGUI;
import com.emeraldingot.storagesystem.langauge.Language;
import org.bukkit.*;
import org.bukkit.block.Dispenser;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.sql.SQLException;
import java.util.UUID;

public class InventoryListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) throws SQLException {

//        if(!event.getAction().equals(InventoryAction.PLACE_ALL) && !event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
//            return;
//        }

        String title = event.getView().getTitle();


        // don't have to return here since the next if statement will
        if (title.equals(Language.STORAGE_SYSTEM_ITEMS_TITLE)) {
            if (!(event.getClickedInventory() instanceof PlayerInventory)) {
                event.setCancelled(true);
            }
            if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY || event.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD) {
                event.setCancelled(true);
            }
            if (event.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
                event.setCancelled(true);
            }

            if (!(event.getClickedInventory() instanceof PlayerInventory)) {
                if (event.getAction() == InventoryAction.CLONE_STACK) {
                    ItemStack itemStack = event.getCurrentItem().clone();
                    itemStack.setAmount(64);
                    event.getWhoClicked().setItemOnCursor(itemStack);
                }
                if (event.getAction() == InventoryAction.PICKUP_ALL){
                    event.getWhoClicked().setItemOnCursor(event.getCurrentItem());
                }
            }


        }


        if (!title.startsWith(Language.STORAGE_SYSTEM_TITLE)) {
            return;
        }


        ItemStack infoItem = event.getView().getTopInventory().getItem(49);
        StorageCellData storageCellData = StorageCellData.fromGUILore(infoItem.getItemMeta().getLore());

        UUID cellUUID = storageCellData.getUUID();
        Location blockLocation = storageCellData.getLocation();

//        if (!StorageControllerBlock.isStorageController(blockLocation)) {
//            event.setCancelled(true);
//            (event.getWhoClicked()).closeInventory();
//            return;
//        }

        Dispenser dispenser = (Dispenser) blockLocation.getBlock().getState();
        ItemStack cell = dispenser.getInventory().getItem(4);
        if (cell == null || cell.getType() != Material.PLAYER_HEAD) {
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

        if (event.getAction().equals(InventoryAction.PICKUP_ALL) && !(event.getClickedInventory() instanceof PlayerInventory)) {
//            System.out.println("Removed item from storage system");
            removeItem(cellUUID, blockLocation, itemStack);
        }

        if (event.getAction().equals(InventoryAction.PICKUP_HALF) && !(event.getClickedInventory() instanceof PlayerInventory)) {
            ItemStack newItemStack = itemStack.clone();
            newItemStack.setAmount((itemStack.getAmount() + 1) / 2);
//            System.out.println("Removed item from storage system");
            removeItem(cellUUID, blockLocation, newItemStack);
        }

        if (event.getAction().equals(InventoryAction.PICKUP_ONE) && !(event.getClickedInventory() instanceof PlayerInventory)) {
            ItemStack newItemStack = itemStack.clone();
            newItemStack.setAmount(1);
//            System.out.println("Removed item from storage system");
            removeItem(cellUUID, blockLocation, newItemStack);
        }

//        System.out.println(event.getAction());

        if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) && !(event.getClickedInventory() instanceof PlayerInventory)) {
//            System.out.println("Removed item from storage system");
            removeItem(cellUUID, blockLocation, itemStack);

        }

        if (event.getAction().equals(InventoryAction.PLACE_ALL) && !(event.getClickedInventory() instanceof PlayerInventory)) {
            itemStack = event.getCursor();
            if (!ControllerManager.getInstance().canHold(blockLocation, itemStack)) {
                event.setCancelled(true);
                return;
            }
//            System.out.println("Added item to storage system");

            addItem(cellUUID, blockLocation, itemStack);

        }

        if (event.getAction().equals(InventoryAction.PLACE_ONE) && !(event.getClickedInventory() instanceof PlayerInventory)) {
            itemStack = event.getCursor();
            ItemStack newItemStack = itemStack.clone();
            newItemStack.setAmount(1);
            if (!ControllerManager.getInstance().canHold(blockLocation, newItemStack)) {
                event.setCancelled(true);
                return;
            }
//            System.out.println("Added item to storage system");
            addItem(cellUUID, blockLocation, newItemStack);


        }

        if (event.getAction().equals(InventoryAction.HOTBAR_SWAP)) {
            event.setCancelled(true);
            return;
        }

        if (event.getAction().equals(InventoryAction.HOTBAR_MOVE_AND_READD)) {
            event.setCancelled(true);
            return;
        }

        if ((event.getAction().equals(InventoryAction.DROP_ALL_SLOT) || event.getAction().equals(InventoryAction.DROP_ONE_SLOT)) && !(event.getClickedInventory() instanceof PlayerInventory)) {
            event.setCancelled(true);
            return;
        }

        if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) && event.getClickedInventory() instanceof PlayerInventory) {
            if (!ControllerManager.getInstance().canHold(blockLocation, itemStack)) {
                event.setCancelled(true);
                return;
            }
//            System.out.println("Added item to storage system");
            addItem(cellUUID, blockLocation, itemStack);

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

        if (event.getAction().equals(InventoryAction.SWAP_WITH_CURSOR) && !(event.getClickedInventory() instanceof PlayerInventory)) {
            // try taking old item stack
//            System.out.println("Removing item from storage system");
            removeItem(cellUUID, blockLocation, event.getCurrentItem());

            if (!ControllerManager.getInstance().canHold(blockLocation, event.getCursor())) {
                // if you can't put down the new one, add original item back
                addItem(cellUUID, blockLocation, event.getCurrentItem());
                event.setCancelled(true);
                return;
            }

//            System.out.println("Added item to storage system");
            addItem(cellUUID, blockLocation, event.getCursor());

//            addItem(itemStack);
        }

//        if (event.getAction().equals(InventoryAction.DROP_ALL_SLOT)) {
////            System.out.println("Removing item from storage system");
//            removeItem(cellUUID, blockLocation, event.getCurrentItem());
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

        if (title.equals(Language.STORAGE_SYSTEM_ITEMS_TITLE)) {
            event.setCancelled(true);
            return;
        }


        if (title.startsWith(Language.STORAGE_SYSTEM_TITLE)) {
            event.setCancelled(true);
            return;
        }



//        if (event.getInventory() instanceof CraftInventoryCustom) {
//            event.setCancelled(true);
//            return;
//        }

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

        return;

    }

    public static void addItem(UUID uuid, Location location, ItemStack itemStack) throws SQLException {
        DatabaseManager.getInstance().addItemsToCell(uuid, itemStack);
        ControllerManager.getInstance().changeUsed(location, itemStack, false);
    }

    private void removeItem(UUID uuid, Location location, ItemStack itemStack) throws SQLException {

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