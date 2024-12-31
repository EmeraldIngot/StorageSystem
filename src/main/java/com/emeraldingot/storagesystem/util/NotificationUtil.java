package com.emeraldingot.storagesystem.util;

import com.emeraldingot.storagesystem.money.MoneyManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class NotificationUtil {

    public static void voucherNotify(Player player, Long value, int count) {
        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "----------------------------------");
        player.sendMessage("You received " + ChatColor.GREEN + "$" + NumberUtil.formatNumber(value * count) + ChatColor.GRAY + " for " +
                ChatColor.DARK_PURPLE + "x" + count + ChatColor.GREEN + " $" + NumberUtil.formatNumber(value) + ChatColor.GRAY +
                " voucher" + NumberUtil.sOrNo(count));
        player.sendMessage("Your new balance: " + ChatColor.GREEN + "$" + NumberUtil.formatNumber(MoneyManager.getInstance().getBalance(player)));
        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "----------------------------------");
    }

    public static void winNotify(Player player, String item, int count) {
        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "----------------------------------");
        player.sendMessage("You won " + ChatColor.DARK_PURPLE + "x" + count + ChatColor.GREEN + " " + item);
        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "----------------------------------");
    }

    public static void winNotifyProgress(Player player, String item, int count) {
        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "----------------------------------");
        player.sendMessage("You won! " + ChatColor.DARK_PURPLE + "x" + count + ChatColor.GREEN + " " + item);
        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "----------------------------------");
    }

    public static void balanceChangeNotify(Player commandSender, Player player, Long oldBalance) {
        commandSender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "----------------------------------");
        commandSender.sendMessage(player.getName() + "'s old balance: " + ChatColor.GREEN + "$" + NumberUtil.formatNumber(oldBalance));
        commandSender.sendMessage(player.getName() + "'s new balance: " + ChatColor.GREEN + "$" + NumberUtil.formatNumber(MoneyManager.getInstance().getBalance(player)));
        commandSender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "----------------------------------");
    }

    public static void balanceGetNoitify(Player commandSender, Player player) {
        commandSender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "----------------------------------");
        commandSender.sendMessage(player.getName() + "'s balance: " + ChatColor.GREEN + "$" + NumberUtil.formatNumber(MoneyManager.getInstance().getBalance(player)));
        commandSender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "----------------------------------");
    }
}
