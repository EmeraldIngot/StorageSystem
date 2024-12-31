package com.emeraldingot.storagesystem.item;

import com.emeraldingot.storagesystem.util.NumberUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public abstract class VoucherItem {
    public static ItemStack createVoucher(long value, Player owner) {
        ItemStack voucher = new ItemStack(Material.PAPER);
        ItemMeta voucherMeta = voucher.getItemMeta();

        voucherMeta.setItemName(ChatColor.GOLD + "" + ChatColor.BOLD + "Cashout Voucher");

        ArrayList<String> itemLore = new ArrayList<>();
        itemLore.add(ChatColor.GRAY + "Given to: " + ChatColor.DARK_PURPLE + owner.getName());
        itemLore.add(ChatColor.GRAY + "Valid for: " + ChatColor.GOLD + "$" + NumberUtil.formatNumber(value));
        itemLore.add(""); // Spacer
        itemLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "This voucher can be redeemed at");
        itemLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "any operational cash out machine");
        voucherMeta.setLore(itemLore);

        voucher.setItemMeta(voucherMeta);
        return voucher;
    }
}
