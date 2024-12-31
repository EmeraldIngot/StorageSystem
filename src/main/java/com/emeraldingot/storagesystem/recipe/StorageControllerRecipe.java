package com.emeraldingot.storagesystem.recipe;

import com.emeraldingot.storagesystem.block.StorageControllerBlock;
import com.emeraldingot.storagesystem.item.StorageCore;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import java.net.MalformedURLException;

public class StorageControllerRecipe {
    public void registerRecipe(Plugin plugin) throws MalformedURLException {

        NamespacedKey namespacedKey = new NamespacedKey(plugin, "storage_controller");
        RecipeChoice.ExactChoice storageCoreChoice = new RecipeChoice.ExactChoice(StorageCore.getStack());
        ShapedRecipe shapedRecipe = new ShapedRecipe(namespacedKey, StorageControllerBlock.getStack());
        shapedRecipe.shape("#%#", "*&*", "^$^");

        shapedRecipe.setIngredient('#', Material.GLASS);
        shapedRecipe.setIngredient('%', Material.REDSTONE);
        shapedRecipe.setIngredient('*', Material.DIAMOND_BLOCK);
        shapedRecipe.setIngredient('&', Material.PLAYER_HEAD);
        shapedRecipe.setIngredient('^', Material.IRON_BLOCK);
        shapedRecipe.setIngredient('$', Material.GOLD_INGOT);

        plugin.getServer().addRecipe(shapedRecipe);
    }

}
