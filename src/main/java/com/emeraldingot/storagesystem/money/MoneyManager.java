package com.emeraldingot.storagesystem.money;

import com.emeraldingot.storagesystem.util.ControllerFileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MoneyManager {
    private final long STARTING_BALANCE = 100;
    private Map<UUID, Long> balanceTable = new HashMap<>(); // UUID instead of player reference because a player may not be online

    private static MoneyManager instance;

    public static MoneyManager getInstance() {
        if (instance == null) {
            instance = new MoneyManager();
        }
        return instance;
    }

    public void addPlayer(UUID playerUUID, long amount) { // addPlayer adds a player from the file to the balanceTable
        balanceTable.put(playerUUID, amount);
    }

    public void registerPlayer(Player player) { // registerPlayer adds a new place to the balanceTable and saves to the file
        UUID playerUUID = player.getUniqueId();
        if (balanceTable.containsKey(playerUUID)) {
            return;
        }
        balanceTable.put(playerUUID, STARTING_BALANCE);
        saveBalance(playerUUID);
    }

    public void addFunds(Player player, long amount) {
        long oldBalance = balanceTable.get(player.getUniqueId());
        setBalance(player, oldBalance + amount);
    }

    public void removeFunds(Player player, long amount) {
        long oldBalance = balanceTable.get(player.getUniqueId());
        setBalance(player, oldBalance - amount);
    }

    public void setBalance(Player player, long balance) {
        UUID playerUUID = player.getUniqueId();
        if (!balanceTable.containsKey(playerUUID)) {
            Bukkit.getLogger().warning("Player " + player.getName() + " does not have an account open!");
            return;
        }

        balanceTable.put(playerUUID, balance);
        saveBalance(playerUUID);

        if (balance < 0) {
            player.sendMessage("You currently have an account balance that is " + ChatColor.BOLD + "" + ChatColor.RED + "NEGATIVE");
            player.sendMessage("If you are unable to pay within 3 days, your debt will be sent to collections");
        }
    }

    public long getBalance(Player player) {
        return balanceTable.get(player.getUniqueId());
    }

    public void resetTable() {
        balanceTable = new HashMap<>();
    }

    private void saveBalance(UUID playerUUID) {
        ControllerFileManager.getInstance().writeBalance(playerUUID, balanceTable.get(playerUUID));
    }

}
