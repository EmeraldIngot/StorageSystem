package com.emeraldingot.storagesystem.item;

import com.emeraldingot.storagesystem.util.SkullUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class StorageCell16K {
    public static ItemStack getStack()  {
        try {
            ItemStack storageCore = SkullUtil.createPlayerHead("http://textures.minecraft.net/texture/71aa292c330a1359206d3fec265231becce31650f379eb399d0bab159db5fb9e");
            ItemMeta itemMeta = storageCore.getItemMeta();
            itemMeta.setItemName(ChatColor.YELLOW + "16k Storage Cell");
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.WHITE + "Stored: 0/16384 bytes");
            lore.add(ChatColor.WHITE + "Cell ID: unset");
            lore.add(ChatColor.RESET + "" + ChatColor.DARK_GRAY + "StorageSystem");
            itemMeta.setLore(lore);
            storageCore.setItemMeta(itemMeta);
            return storageCore;
        }
        catch (Exception e) {
            return new ItemStack(Material.PAPER);
        }

    }
}
