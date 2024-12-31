package com.emeraldingot.storagesystem.command.tabcompleter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CasinoTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> argumentList = new ArrayList<>();

        if (strings.length == 1) { // 1 because it is returning suggestions for the argument currently being added
            argumentList.add("register");
            argumentList.add("balance");
            argumentList.add("reloadbalances");
        }

        if (strings.length == 2 && strings[0].equals("balance")) {
            argumentList.add("get");
            argumentList.add("set");
            argumentList.add("add");
            argumentList.add("remove");
        }

        if (strings.length == 4 && strings[0].equals("balance")) {
            return getPlayerList();
        }

        if (strings.length == 3 && strings[1].equals("get")) {
            return getPlayerList();
        }

        if (strings.length == 2 && strings[0].equals("register")) {
            return getPlayerList();
        }

        return argumentList;
    }

    private List<String> getPlayerList() {

        List<String> playerList = new ArrayList<>();
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);

        for (Player player : players) {
            playerList.add(player.getName());
        }

        return playerList;
    }
}
