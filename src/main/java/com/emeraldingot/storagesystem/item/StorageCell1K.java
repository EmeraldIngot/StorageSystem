package com.emeraldingot.storagesystem.item;

import com.emeraldingot.storagesystem.util.SkullUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class StorageCell1K {
    public static ItemStack getStack()  {
        try {
            ItemStack storageCore = SkullUtil.createPlayerHead("http://textures.minecraft.net/texture/5c718e958e31e7fa62de6bd616d1f7eacd4a3ffeedfa27307dde372e3200fb17");
            ItemMeta itemMeta = storageCore.getItemMeta();
            itemMeta.setItemName(ChatColor.YELLOW + "1k Storage Cell");
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.WHITE + "Stored: 0/1024 bytes");
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
