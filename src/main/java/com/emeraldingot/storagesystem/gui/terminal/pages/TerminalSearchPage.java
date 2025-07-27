package com.emeraldingot.storagesystem.gui.terminal.pages;

import com.emeraldingot.storagesystem.gui.GuiButton;
import com.emeraldingot.storagesystem.gui.StorageSystemHolder;
import com.emeraldingot.storagesystem.gui.terminal.StorageSystemGui;
import com.emeraldingot.storagesystem.gui.util.GuiUtil;
import com.emeraldingot.storagesystem.impl.DatabaseManager;
import com.emeraldingot.storagesystem.impl.StorageCellData;
import com.emeraldingot.storagesystem.langauge.Language;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TerminalSearchPage extends AbstractTerminalPage {

    int pageNumber;
    String searchTerm;

    public TerminalSearchPage(Player player, StorageCellData storageCellData, int pageNumber, String searchTerm) {
        super(player, storageCellData);

        this.pageNumber = pageNumber;
        this.searchTerm = searchTerm;
    }

    @Override
    public Inventory build() {

        List<ItemStack> cellItems = DatabaseManager.getInstance().getItemsByCellUUID(storageCellData.getUUID());
        List<ItemStack> filteredList = filterList(cellItems, searchTerm);


        int pageCount = TerminalItemsPage.getPageCount(filteredList);

        // first page is index 0, add 1 to look correct in the UI
        String title = ChatColor.YELLOW + "Search: \"" + searchTerm + "\" (" + (pageNumber + 1) + "/" + pageCount + ")";
        Inventory inventory = Bukkit.createInventory(new StorageSystemHolder(storageCellData, pageNumber), 54, title);

        ItemStack spacer = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta spacerMeta = spacer.getItemMeta();
        spacerMeta.setHideTooltip(true);
        spacer.setItemMeta(spacerMeta);


        for (int slot : TerminalItemsPage.NAVIGATION_BAR) {
            inventory.setItem(slot, spacer);
        }


        ItemStack backArrow = new ItemStack(Material.ARROW);
        ItemMeta backArrowMeta = backArrow.getItemMeta();
        backArrowMeta.setItemName(Language.BACK_BUTTON);
        backArrow.setItemMeta(backArrowMeta);

        GuiButton backButton = new GuiButton(
                backArrow,
                event -> {
                    TerminalItemsPage terminalItemsPage = new TerminalItemsPage(player, storageCellData, pageNumber);
                    player.openInventory(terminalItemsPage.build());
                }
        );
        StorageSystemGui.getButtonItems(player).put(backArrow, backButton);

        inventory.setItem(49, backArrow);


        TerminalItemsPage.populateItems(inventory, filteredList, pageNumber);

        TerminalItemsPage.createNavigationButtons(inventory, player, storageCellData, pageCount, pageNumber, searchTerm);

        return inventory;
    }


    private static List<ItemStack> filterList(List<ItemStack> itemStacks, String searchTerm) {
        String normalizedTerm = searchTerm.toLowerCase();

        List<ItemStack> filteredList = new ArrayList<>();
        // List is already alphabetized, so further sorting is not needed
        for (ItemStack itemStack : itemStacks) {
            String itemName = "";
            if (itemStack.getItemMeta().hasDisplayName()) {
                itemName = itemStack.getItemMeta().getDisplayName();
            }
            else if (itemStack.getItemMeta().hasItemName()) {
                itemName = itemStack.getItemMeta().getItemName();
            }
            else {
                itemName = itemStack.getType().toString().replace("_", " ");
            }

            itemName = itemName.toLowerCase();

            if (itemName.contains(normalizedTerm)) {
                filteredList.add(itemStack);
            }
        }

        return filteredList;
    }


}
