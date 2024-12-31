package com.emeraldingot.storagesystem.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.CraftingInventory;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        System.out.println(player.getOpenInventory());

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
