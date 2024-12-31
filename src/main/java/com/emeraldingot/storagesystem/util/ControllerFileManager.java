package com.emeraldingot.storagesystem.util;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class ControllerFileManager {
    public static FileConfiguration controllersData;

    private File controllersFile;

    private static ControllerFileManager instance;


    public static ControllerFileManager getInstance()
    {
        if (instance == null) {
            instance = new ControllerFileManager();
        }
        return instance;
    }

    public void initData(Plugin plugin) {
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        controllersFile = new File(plugin.getDataFolder(), "controllers.yml");
        if (!controllersFile.exists()) {
            try {
                controllersFile.createNewFile(); //This needs a try catch
                controllersData = YamlConfiguration.loadConfiguration(controllersFile);
                controllersData.set("controllers", new ArrayList<String>());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        controllersData = YamlConfiguration.loadConfiguration(controllersFile);

//        updateData();

    }

//    public void updateData() {
//        MoneyManager.getInstance().resetTable();
//        for (String playerUUID : lossesData.getKeys(false)) {
//            MoneyManager.getInstance().addPlayer(UUID.fromString(playerUUID), Long.parseLong(lossesData.get(playerUUID).toString()));
//        }
//    }



    /**
     * A method to serialize an inventory to Base64 string.
     *
     * <p />
     *
     * Special thanks to Comphenix in the Bukkit forums or also known
     * as aadnk on GitHub.
     *
     * <a href="https://gist.github.com/aadnk/8138186">Original Source</a>
     *
     * @param inventory to serialize
     * @return Base64 string of the provided inventory
     * @throws IllegalStateException
     */
    public static String toBase64(Object object) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeObject(object);

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stack.", e);
        }
    }

    /**
     *
     * A method to get an {@link Inventory} from an encoded, Base64, string.
     *
     * <p />
     *
     * Special thanks to Comphenix in the Bukkit forums or also known
     * as aadnk on GitHub.
     *
     * <a href="https://gist.github.com/aadnk/8138186">Original Source</a>
     *
     * @param data Base64 string of data containing an inventory.
     * @return Inventory created from the Base64 string.
     * @throws IOException
     */
    public static Object fromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            // Read the serialized inventory
            Object object = dataInput.readObject();

            dataInput.close();
            return object;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }




    public void writeControllers(ArrayList<Location> locations) {
        List<String> stringLocations = new ArrayList<>();
        for (Location location : locations) {
            stringLocations.add(toBase64(location));
        }
        controllersData.set("controllers", stringLocations);

        try {
            controllersData.save(controllersFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Location> loadControllers() throws IOException {
        List<String> stringLocations = controllersData.getStringList("controllers");
        ArrayList<Location> locations = new ArrayList<>();
        for (String string : stringLocations) {
            locations.add((Location) fromBase64(string));
        }

        return locations;

    }

    public void writeBalance(UUID playerUUID, long amount) {
        controllersData.set(String.valueOf(playerUUID), amount);
        try {
            controllersData.save(controllersFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public long getBalance(UUID playerUUID) {
        return (long) controllersData.get(String.valueOf(playerUUID));
    }

//    public boolean isNewPlayer(Player player) {
//        return !moneyData.contains(player.getDisplayName());
//    }
}
