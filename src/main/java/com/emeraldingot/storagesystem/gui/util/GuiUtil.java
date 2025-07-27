package com.emeraldingot.storagesystem.gui.util;

import com.emeraldingot.storagesystem.StorageSystem;
import com.emeraldingot.storagesystem.gui.terminal.pages.AbstractTerminalPage;
import com.emeraldingot.storagesystem.gui.terminal.pages.TerminalItemsPage;
import de.rapha149.signgui.SignGUI;
import de.rapha149.signgui.exception.SignGUIVersionException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.function.Function;

public class GuiUtil {
    public static void openCustomStringSign(Player player, Function<String, AbstractTerminalPage> confirmPageFactory, AbstractTerminalPage returnPage, String message) {
        SignGUI gui = null;
        try {
            gui = SignGUI.builder()
                    // set lines
                    .setLines(null, "^^^^^^", message)


                    // set the sign type
                    .setType(Material.SPRUCE_SIGN)


                    // set the handler/listener (called when the player finishes editing)
                    .setHandler((p, result) -> {
                        // get a speficic line, starting index is 0
                        String line0 = result.getLine(0);

                        if (line0.isEmpty()) {
                            Bukkit.getScheduler().runTask(StorageSystem.getInstance(), () -> {
                                player.openInventory(returnPage.build());
                            });
                        }
                        else {
                            Bukkit.getScheduler().runTask(StorageSystem.getInstance(), () -> {
                                AbstractTerminalPage confirmPage = confirmPageFactory.apply(line0);
                                player.openInventory(confirmPage.build());
                            });
                        }






//                        if (line0.isEmpty())) {
//                            // The user has not entered anything on line 2, so we open the sign again
//
//                        }


                        // Just close the sign by not returning any actions
                        return Collections.emptyList();
                    })

                    // build the SignGUI
                    .build();

            gui.open(player);
        } catch (SignGUIVersionException e) {
            throw new RuntimeException(e);
        }
    }
}
