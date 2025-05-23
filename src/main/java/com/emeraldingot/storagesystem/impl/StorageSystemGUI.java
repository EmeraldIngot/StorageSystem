package com.emeraldingot.storagesystem.impl;

import com.emeraldingot.storagesystem.StorageSystem;
import com.emeraldingot.storagesystem.langauge.Language;
import com.emeraldingot.storagesystem.util.SkullUtil;
import de.rapha149.signgui.SignGUI;
import de.rapha149.signgui.SignGUIAction;
import de.rapha149.signgui.exception.SignGUIVersionException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class StorageSystemGUI {


    private int calculateStacks(ItemStack itemStack) {
        // 129 items / 64 = 2.something which is truncated to 2
        int stackCount = (itemStack.getAmount() / itemStack.getMaxStackSize());
        // if itemCount divides evenly then we're done, otherwise, there's 1 more stack
        stackCount = (itemStack.getAmount() % itemStack.getMaxStackSize() == 0) ? (stackCount) : (stackCount + 1);
        return stackCount;
    }

    private static int calculateFullStacks(ItemStack itemStack) {
        // 129 items / 64 = 2.something which is truncated to 2
        int fullStackCount = (itemStack.getAmount() / itemStack.getMaxStackSize());
        return fullStackCount;
    }

    private static ArrayList<ItemStack> splitToStacks(List<ItemStack> itemStacks) {
        ArrayList<ItemStack> splitStacks = new ArrayList<>();
        for (ItemStack itemStack : itemStacks) {

            int fullStackCount = calculateFullStacks(itemStack);

            if (fullStackCount > 0) {
                int fullItemCount = itemStack.getAmount();
                for (int i = 0; i < fullStackCount; i++) {
                    itemStack.setAmount(itemStack.getMaxStackSize());
                    splitStacks.add(itemStack);
                }
                int leftoverStackSize = (fullItemCount % itemStack.getMaxStackSize());

                if (leftoverStackSize > 0) {
                    ItemStack leftoverStack = itemStack.clone();
                    leftoverStack.setAmount(leftoverStackSize);
                    splitStacks.add(leftoverStack);
                }

            }
            else {
                splitStacks.add(itemStack);
            }

        }

        return splitStacks;
    }

//    public static Inventory getCompleteLastPage() throws SQLException {
//        ArrayList<ItemStack> itemStackList = splitToStacks(DatabaseManager.getInstance().getItemsByCellUUID(StorageSystem.getInstance().cellUUID));
//        int lastPageNumber = getPageCount(itemStackList);
//        return getCompleteStoragePage(lastPageNumber - 1);
//    }

    public static Inventory getSearchedStoragePage(UUID uuid, Location location, int pageNumber, String searchTerm) throws SQLException {
        // Normalize the search term (lowercase and replace underscores with spaces)
        String normalizedSearchTerm = searchTerm.toLowerCase().replace("_", " ");

        // Fetch the list of item stacks
        ArrayList<ItemStack> itemStackList = splitToStacks(DatabaseManager.getInstance().getItemsByCellUUID(uuid));

        // Filter the list to only include items whose material matches the search term
        itemStackList.removeIf(item -> !item.getType().toString().toLowerCase().replace("_", " ").contains(normalizedSearchTerm));

        // Sort the remaining items alphabetically by material name
        itemStackList.sort((item1, item2) -> {
            String material1 = item1.getType().toString().toLowerCase().replace("_", " ");
            String material2 = item2.getType().toString().toLowerCase().replace("_", " ");
            return material1.compareTo(material2);
        });

        // Now get the storage page based on the filtered and sorted list
        Inventory page = getStoragePage(uuid, location, itemStackList, pageNumber);
        return page;
    }



    public static Inventory getCompleteStoragePage(UUID uuid, Location location, int pageNumber) throws SQLException {
        ArrayList<ItemStack> itemStackList = splitToStacks(DatabaseManager.getInstance().getItemsByCellUUID(uuid));
        Inventory page = getStoragePage(uuid, location, itemStackList, pageNumber);

        return page;


    }

    private static Inventory getStoragePage(UUID uuid, Location location, ArrayList<ItemStack> itemStacks, int pageNumber) {
        int pageCount = getPageCount(itemStacks);
        Inventory inventory = Bukkit.createInventory(null , 9 * 6, Language.STORAGE_SYSTEM_TITLE + " (" + (pageNumber + 1) + "/" + pageCount + ")");

        ItemStack previousPageItem;
        try {
            previousPageItem = SkullUtil.createPlayerHead("http://textures.minecraft.net/texture/528b8cf405eaf606a0210f0303b013179f8f12eaa95824129ebeef9e44b68230");
        }
        catch (Exception e) {
            previousPageItem = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        }

        ItemMeta itemMeta = previousPageItem.getItemMeta();
        itemMeta.setItemName(ChatColor.RED + "Previous Page");
        previousPageItem.setItemMeta(itemMeta);
        inventory.setItem(46, previousPageItem);

        ItemStack firstPageItem;
        try {
            firstPageItem = SkullUtil.createPlayerHead("http://textures.minecraft.net/texture/902ac2617482f61bad194f7bcb8b993431040eb40bd1b663ad41e98f3b324d26");
        }
        catch (Exception e) {
            firstPageItem = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        }

        itemMeta = firstPageItem.getItemMeta();
        itemMeta.setItemName(ChatColor.RED + "First Page");
        firstPageItem.setItemMeta(itemMeta);
        inventory.setItem(45, firstPageItem);

        ItemStack spacer = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        inventory.setItem(47, spacer);
        inventory.setItem(48, spacer);
        inventory.setItem(50, spacer);
        inventory.setItem(51, spacer);

        ItemStack infoItem = new ItemStack(Material.SPRUCE_SIGN);
        itemMeta = infoItem.getItemMeta();
        itemMeta.setItemName(ChatColor.RED + "Search");
        List<String> loreList = new ArrayList<>();
        loreList.add(ChatColor.RESET + "" + ChatColor.DARK_GRAY + uuid.toString());
        loreList.add(ChatColor.RESET + "" + ChatColor.DARK_GRAY + "X: " + location.getX() + " Y: " + location.getY() + " Z: " + location.getZ() + " W: " + location.getWorld().getName());
        loreList.add(ChatColor.RESET + "" + ChatColor.DARK_GRAY + pageNumber);
        itemMeta.setLore(loreList);
        infoItem.setItemMeta(itemMeta);
        inventory.setItem(49, infoItem);

        ItemStack nextPageItem;
        try {
            nextPageItem = SkullUtil.createPlayerHead("http://textures.minecraft.net/texture/5dcda6e3c6dca7e9b8b6ba3febf5cd0917f997b64b2aef18c3f773765e3a579");
        }
        catch (Exception e) {
            nextPageItem = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        }

        itemMeta = nextPageItem.getItemMeta();
        itemMeta.setItemName(ChatColor.RED + "Next Page");
        nextPageItem.setItemMeta(itemMeta);
        inventory.setItem(52, nextPageItem);

        ItemStack lastPageItem;
        try {
            lastPageItem = SkullUtil.createPlayerHead("http://textures.minecraft.net/texture/6befd502ee9cce11fcf8c5713fc02b49787bd9ab8b10ed129043f6ca8f4e207d");
        }
        catch (Exception e) {
            lastPageItem = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        }

        itemMeta = lastPageItem.getItemMeta();
        itemMeta.setItemName(ChatColor.RED + "Last Page");
        lastPageItem.setItemMeta(itemMeta);
        inventory.setItem(53, lastPageItem);



//        ArrayList<Inventory> pages = new ArrayList<>();
        if (itemStacks.isEmpty()) {
            return inventory;
        }

        int currentStack = 45 * pageNumber;
        for (int i = currentStack; i < (currentStack + 45); i++) {
            if (i >= itemStacks.size()) {
                break;
            }
            inventory.addItem(itemStacks.get(i));
        }

        return inventory;
    }

    private static int getPageCount(ArrayList<ItemStack> itemStacks) {
        // if you don't add 1 you get 0 for less than 1 full page
        int fullPages = (itemStacks.size() / 45);
        int pageCount;

        if (itemStacks.size() % 45 == 0) {
            pageCount = fullPages;
        }
        else {
            pageCount = fullPages + 1;
        }

        if (pageCount == 0) {
            return 1;
        }
        else {
            return pageCount;
        }

    }

    public static int getPageCount(UUID cellID) throws SQLException {
        ArrayList<ItemStack> itemStackList = splitToStacks(DatabaseManager.getInstance().getItemsByCellUUID(cellID));
        return getPageCount(itemStackList);

    }



//    public void compareInventoryChanges(Inventory inventory1, Inventory inventory2) {
//        Map<Integer, ItemStack> inventory1Items = getInventoryContentsMap(inventory1);
//        Map<Integer, ItemStack> inventory2Items = getInventoryContentsMap(inventory2);
//
//        // Find additions (items in inventory2 but not in inventory1)
//        for (Map.Entry<Integer, ItemStack> entry : inventory2Items.entrySet()) {
//            Integer slot = entry.getKey();
//            ItemStack itemInInventory2 = entry.getValue();
//
//            if (!inventory1Items.containsKey(slot) || !itemInInventory2.isSimilar(inventory1Items.get(slot))) {
//                System.out.println("Item added or changed at slot " + slot);
//                System.out.println("Item in Inventory 2: " + itemInInventory2);
//            }
//        }
//
//        // Find removals (items in inventory1 but not in inventory2)
//        for (Map.Entry<Integer, ItemStack> entry : inventory1Items.entrySet()) {
//            Integer slot = entry.getKey();
//            ItemStack itemInInventory1 = entry.getValue();
//
//            if (!inventory2Items.containsKey(slot) || !itemInInventory1.isSimilar(inventory2Items.get(slot))) {
//                System.out.println("Item removed or changed at slot " + slot);
//                System.out.println("Item in Inventory 1: " + itemInInventory1);
//            }
//        }
//    }

//    private Map<Integer, ItemStack> getInventoryContentsMap(Inventory inventory) {
//        Map<Integer, ItemStack> contentsMap = new HashMap<>();
//        for (int i = 0; i < inventory.getSize(); i++) {
//            ItemStack item = inventory.getItem(i);
//            if (item != null) {
//                contentsMap.put(i, item);
//            }
//        }
//        return contentsMap;
//    }

    public static void openSearch(Player player, UUID uuid, Location location) {
        SignGUI gui = null;
        try {
            gui = SignGUI.builder()
                    // set lines
                    .setLines(null, null, "^^^^^^", "Search Term")


                    // set the sign type
                    .setType(Material.SPRUCE_SIGN)


                    // set the handler/listener (called when the player finishes editing)
                    .setHandler((p, result) -> {
                        // get a speficic line, starting index is 0
                        String line0 = result.getLine(0);

                        if (line0.isEmpty()) {
                            // The user has not entered anything on line 2, so we open the sign again
                            return List.of(SignGUIAction.displayNewLines(null, null, "^^^^^^", "Search Term"));
                        }


                        try {
                            return Collections.singletonList(SignGUIAction.openInventory(StorageSystem.getInstance(), getSearchedStoragePage(uuid, location, 0, line0)));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        // Just close the sign by not returning any actions
//                        return Collections.emptyList();
                    })

                    // build the SignGUI
                    .build();
        } catch (SignGUIVersionException e) {
            throw new RuntimeException(e);
        }

// open the sign
        gui.open(player);
    }



}
