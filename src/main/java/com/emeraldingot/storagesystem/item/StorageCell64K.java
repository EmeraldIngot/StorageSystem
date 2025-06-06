package com.emeraldingot.storagesystem.item;

import com.emeraldingot.storagesystem.util.SkullUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class StorageCell64K {
    public static ItemStack getStack()  {
        try {
            ItemStack storageCore = SkullUtil.createPlayerHead("http://textures.minecraft.net/texture/92f704b3b33e90c696a4ac825f2813485c00c9d7ee5a0b4e857b85d9886b7897");
            ItemMeta itemMeta = storageCore.getItemMeta();
            itemMeta.setItemName(ChatColor.YELLOW + "64k Storage Cell");
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.WHITE + "Stored: 0/65536 bytes");
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
