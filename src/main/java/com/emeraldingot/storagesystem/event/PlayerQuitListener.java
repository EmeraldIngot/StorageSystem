package com.emeraldingot.storagesystem.event;

import com.emeraldingot.storagesystem.impl.GameManager;
import com.emeraldingot.storagesystem.util.ControllerFileManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (GameManager.getInstance().hasOpenGame(player)) {
//            ControllerFileManager.getInstance().addLostItem(GameManager.getInstance().getItemStack(player));
            GameManager.getInstance().endGame(player);
        }

        if (com.emeraldingot.storagesystem.StorageSystem.playingMusic.contains(player)) {
            com.emeraldingot.storagesystem.StorageSystem.playingMusic.remove(player);
        }
//        if (player.getUniqueId().equals(UUID.fromString("f3c812a5-1919-4225-af3a-1952a6033bb5"))) {
//            System.out.println("updating role to " + SwitchRoleCommand.currentRole);
////            SwitchRoleCommand.updateRole(player);
//        }

//        for (Team team : player.getScoreboard().getTeams()) {
//            if (team.hasEntry(player.getName())) {
//                FileManager.getInstance().setOrAddPlayer(player, team.getDisplayName());
//            }
//        }



//        Double completedPercentage = calculateAdvancementPercentage(player);

//        if (FileManager.getInstance().isNewPlayer(player)) {
//            FileManager.getInstance().setOrAddPlayer(player, "common");
//            setPlayerRarity(player, completedPercentage);
//        }

    }
}
