package com.emeraldingot.storagesystem.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.SkullMeta;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.UUID;


public class SkullUtil {
    public static ItemStack createPlayerHead(String url) throws MalformedURLException {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);

        if(itemStack.getItemMeta() instanceof SkullMeta skullMeta){
            var playerProfile = Bukkit.createPlayerProfile(UUID.fromString("b4afb67c-5fa8-4ca9-90d5-3c262d3be769"));
            playerProfile.getTextures().setSkin(URI.create(url).toURL());
            skullMeta.setOwnerProfile(playerProfile);
            itemStack.setItemMeta(skullMeta);
        }
        return itemStack;
    }
}
