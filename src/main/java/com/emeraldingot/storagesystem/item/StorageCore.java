package com.emeraldingot.storagesystem.item;

import com.emeraldingot.storagesystem.util.SkullUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class StorageCore {
    public static ItemStack getStack()  {
        try {
            ItemStack storageCore = SkullUtil.createPlayerHead("http://textures.minecraft.net/texture/3db9e7f6ab3a8c7686cf9d8ac244dd47890ca79494e9ce3951824f03deb87b3d");
            ItemMeta itemMeta = storageCore.getItemMeta();
            itemMeta.setItemName(ChatColor.YELLOW + "Storage Core");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("Infinitely dense cubical structure");
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
