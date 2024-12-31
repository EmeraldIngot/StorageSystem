package com.emeraldingot.storagesystem.recipe;

import com.emeraldingot.storagesystem.item.StorageCell1K;
import com.emeraldingot.storagesystem.item.StorageCell4K;
import com.emeraldingot.storagesystem.item.StorageCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class StorageCell4KRecipe implements Recipe {
    public void registerRecipe(Plugin plugin) {
        NamespacedKey namespacedKey = new NamespacedKey(plugin, "storage_cell_4k");

        RecipeChoice.ExactChoice storageCellChoice = new RecipeChoice.ExactChoice(StorageCell1K.getStack());

        ShapedRecipe shapedRecipe = new ShapedRecipe(namespacedKey, StorageCell4K.getStack());

        shapedRecipe.shape("#%#", "*&*", "#%#");

        shapedRecipe.setIngredient('#', Material.GOLD_BLOCK);
        shapedRecipe.setIngredient('%', Material.DIAMOND);
        shapedRecipe.setIngredient('*', Material.REDSTONE);
        shapedRecipe.setIngredient('&', Material.PLAYER_HEAD);

        plugin.getServer().addRecipe(shapedRecipe);


    }

    @Override
    public ItemStack getResult() {
        return StorageCell4K.getStack();
    }
}
