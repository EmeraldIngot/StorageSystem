package com.emeraldingot.storagesystem.impl;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class GameManager {
    private HashMap<Player, ItemStack> games = new HashMap<Player, ItemStack>();
    private static GameManager instance;
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void startGame(Player player, ItemStack itemStack) {
        games.put(player, itemStack);
    }

    public void endGame(Player player) {
        games.remove(player);
    }

    public void saveGame(Player player, ItemStack itemStack) {

        if (!games.containsKey(player)) {
            return;
        }

        games.put(player, itemStack);
    }

    public boolean hasOpenGame(Player player) {
        return games.containsKey(player);
    }

    public ItemStack getItemStack(Player player) {
        return games.get(player);
    }



}
