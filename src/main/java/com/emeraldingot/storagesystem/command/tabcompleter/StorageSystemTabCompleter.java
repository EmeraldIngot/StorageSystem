package com.emeraldingot.storagesystem.command.tabcompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class StorageSystemTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> argumentList = new ArrayList<>();

        if (strings.length == 1) {
            argumentList.add("items");
        }


        return argumentList;
    }
}
