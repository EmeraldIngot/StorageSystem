package com.emeraldingot.storagesystem.item;

import com.emeraldingot.storagesystem.StorageSystem;
import com.emeraldingot.storagesystem.util.SkullUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class StorageTerminal {
    private static final NamespacedKey TERMINAL_KEY = new NamespacedKey(StorageSystem.getInstance(), "storage_terminal");

    public static ItemStack getStack()  {
        try {
            ItemStack storageTerminal = SkullUtil.createPlayerHead("http://textures.minecraft.net/texture/e5c0358fb64eaac6db69a857f8987b5197fcea72609c060a8628da92266fd592");
            ItemMeta itemMeta = storageTerminal.getItemMeta();
            itemMeta.setItemName(ChatColor.YELLOW + "Storage Terminal");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("Wireless item access");
            lore.add(ChatColor.RESET + "" + ChatColor.DARK_GRAY + "StorageSystem");
            itemMeta.setLore(lore);

            itemMeta.getPersistentDataContainer().set(TERMINAL_KEY, PersistentDataType.BYTE, (byte) 0);

            storageTerminal.setItemMeta(itemMeta);
            return storageTerminal;
        }
        catch (Exception e) {
            return new ItemStack(Material.PAPER);
        }

    }

    public static boolean isStorageTerminal(ItemStack itemStack) {
        ItemStack storageTerminal = getStack();
        if (itemStack.getType() != storageTerminal.getType()) {
            return false;
        }

        if (itemStack.getItemMeta() == null) {
            return false;
        }


        return itemStack.getItemMeta().getPersistentDataContainer().has(TERMINAL_KEY);
    }
}
