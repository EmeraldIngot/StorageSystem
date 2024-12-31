package com.emeraldingot.storagesystem.util;

import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

public class GamblingUtil {
    private static BoundingBox boundingBox = new BoundingBox(30, 100, -103, 20, 96, -120);
    public static boolean rollChance() {
        double randomNumber = (int) (Math.random() * 100) + 1;

        if (randomNumber > 40) {
            return false;
        }
        else {
            return true;
        }

    }

    public static boolean isInCasino(Player player) {
        if (boundingBox.contains(player.getLocation().toVector())) {
            return true;
        }
        else {
            return false;
        }
    }
}
