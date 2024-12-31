package com.emeraldingot.storagesystem.event;

import com.emeraldingot.storagesystem.util.GamblingUtil;
import com.emeraldingot.storagesystem.util.NotificationUtil;
import org.bukkit.*;
import org.bukkit.block.Container;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public class InventoryPickupItemListener implements Listener {
    @EventHandler
    public void onInventoryMoveItem(InventoryPickupItemEvent event) {


        Container container = (Container) event.getInventory().getHolder();

        if (container.getCustomName() == null || !container.getCustomName().equals("Gambling Station")) {
            return;
        }
        // && !event.getItem().getItemStack().getItemMeta().getItemName().equals("§6§lCashout Voucher")
//        if (container.getCustomName().equals("Gambling Station") ) {
//            event.setCancelled(true);
//            return;
//        }

//        String valueLine = event.getItem().getItemStack().getItemMeta().getLore().get(1);
//        valueLine = valueLine.split("\\$")[1].replace(",", "");

        Item item = event.getItem();

        System.out.println(item.getOwner());
        Player player = Bukkit.getPlayer(item.getOwner());

        int count = event.getItem().getItemStack().getAmount();

        boolean isWinner = GamblingUtil.rollChance();
        if (isWinner) {
            ItemStack itemStack = new ItemStack(item.getItemStack().getType(), count * 2);
            player.getInventory().addItem(itemStack);
        }



        playEffect(player, item.getName(), count);

        event.getItem().remove();
        event.setCancelled(true);

    }

    private void playEffect(Player player, String item, int count) {
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);

        NotificationUtil.winNotify(player, item, count);


        Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.RED).with(FireworkEffect.Type.STAR).withFlicker()
                .withColor(Color.YELLOW).with(FireworkEffect.Type.STAR).withFlicker().build());
        firework.setFireworkMeta(fireworkMeta);
    }
}
