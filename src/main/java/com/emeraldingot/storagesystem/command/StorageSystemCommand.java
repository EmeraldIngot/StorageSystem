package com.emeraldingot.storagesystem.command;

import com.emeraldingot.storagesystem.impl.ItemsGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StorageSystemCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.isOp()) {
            commandSender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            // returning false will show the command description, which is not necessary
            return true;
        }
        if (strings.length < 1) {
            commandSender.sendMessage(ChatColor.RED + "You must provide at least one argument!");
            return false;
        }

        if (strings[0].equals("items")) {
            ((Player) commandSender).openInventory(ItemsGUI.getInventory());
        }

        return true;
    }

}