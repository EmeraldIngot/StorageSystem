package com.emeraldingot.storagesystem.event;


import com.emeraldingot.storagesystem.langauge.Language;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;


import java.sql.SQLException;
import java.util.List;

public class StorageCellPlaceListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) throws SQLException {

        ItemStack item = event.getItemInHand();

        if (item.getType() != Material.PLAYER_HEAD) {
            return;
        }

        if (item.getItemMeta().getLore() == null) {
            return;
        }

        List<String> lore = item.getItemMeta().getLore();

        if (lore.contains(Language.STORAGE_SYSTEM_LORE_TAG)) {
            event.setCancelled(true);
            return;
        }
//
        event.setCancelled(false);

    }



}
