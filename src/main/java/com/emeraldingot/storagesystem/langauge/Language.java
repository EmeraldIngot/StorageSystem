package com.emeraldingot.storagesystem.langauge;

import org.bukkit.ChatColor;

public class Language {
    public static final String INDICATOR_LORE = ChatColor.DARK_GRAY + "Indicator";
    public static final String SEARCH_ITEMS = ChatColor.RED + "Search";
    public static final String BACK_BUTTON = ChatColor.RED + "Back";
    public static final String STORAGE_CONTROLLER_ITEM = ChatColor.YELLOW + "Storage Controller";
    public static final String STORAGE_TERMINAL_ITEM = ChatColor.YELLOW + "Storage Terminal";
    public static final String STORAGE_SYSTEM_TITLE = ChatColor.YELLOW + "Storage System";
    public static final String STORAGE_SYSTEM_ITEMS_TITLE = ChatColor.RED + "StorageSystem Items";
    public static final String STORAGE_SYSTEM_LORE_TAG = ChatColor.DARK_GRAY + "StorageSystem";
    public static final String NO_PAIRED_CONTROLLER_MESSAGE = ChatColor.DARK_RED + "" + ChatColor.BOLD + "StorageSystem " + ChatColor.RESET + "" + ChatColor.RED + "You must pair this terminal with a controller first!";
    public static final String FIRST_PAGE = ChatColor.RED + "First Page";
    public static final String PREVIOUS_PAGE = ChatColor.RED + "Previous Page";
    public static final String NEXT_PAGE = ChatColor.RED + "Next Page";
    public static final String LAST_PAGE = ChatColor.RED + "Last Page";
    public static final String REMOVED_CONTROLLER_MESSAGE = ChatColor.DARK_RED + "" + ChatColor.BOLD + "StorageSystem " + ChatColor.RESET + "" + ChatColor.RED + "The paired controller no longer exists!";
    public static final String PAIRED_CONTROLLER_OFFLINE_MESSAGE = ChatColor.DARK_RED + "" + ChatColor.BOLD + "StorageSystem " + ChatColor.RESET + "" + ChatColor.RED + "The paired controller is offline!";
    public static final String PAIRED_WITH_CONTROLLER_MESSAGE = ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "StorageSystem " + ChatColor.RESET + "" + ChatColor.AQUA + "Successfully paired with controller!";
    public static final String PAIRED_CONTROLLER_RANGE_MESSAGE = ChatColor.DARK_RED + "" + ChatColor.BOLD + "StorageSystem " + ChatColor.RESET + "" + ChatColor.RED + "The paired controller is too far away!";
}
