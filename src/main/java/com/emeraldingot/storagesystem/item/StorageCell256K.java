package com.emeraldingot.storagesystem.item;

import com.emeraldingot.storagesystem.util.SkullUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class StorageCell256K {
    public static ItemStack getStack()  {
        try {
            ItemStack storageCore = SkullUtil.createPlayerHead("http://textures.minecraft.net/texture/c4bb880535638d45f16f7fc657452d14bcb9f3f0c598d53876355df8919d0898");
            ItemMeta itemMeta = storageCore.getItemMeta();
            itemMeta.setItemName(ChatColor.YELLOW + "256k Storage Cell");
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.WHITE + "Stored: 0/262144 bytes");
            lore.add(ChatColor.WHITE + "Cell ID: unset");
            lore.add(ChatColor.RESET + "" + ChatColor.DARK_GRAY + "StorageSystem");
            itemMeta.setLore(lore);

            itemMeta.getPersistentDataContainer().set(StorageCell1K.CELL_UUID_KEY, PersistentDataType.STRING, StorageCell1K.EMPTY_UUID.toString());

            storageCore.setItemMeta(itemMeta);
            return storageCore;
        }
        catch (Exception e) {
            return new ItemStack(Material.PAPER);
        }

    }
}
