package com.emeraldingot.storagesystem.impl;

import com.emeraldingot.storagesystem.StorageSystem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Container;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import javax.xml.crypto.Data;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseManager {
    private static DatabaseManager instance;
    private final Connection connection;

    public static DatabaseManager getInstance() {
        return instance;
    }

    public DatabaseManager(String path) throws SQLException {
        instance = this;
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);

        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS storage_cells (" +
                    "cell_id TEXT UNIQUE, " +
                    "PRIMARY KEY(cell_id));");
        }
        try (Statement statement = connection.createStatement()) {
//            statement.execute("CREATE TABLE IF NOT EXISTS items (" +
//                    "cell_id TEXT NOT NULL, " +
//                    "item_data TEXT NOT NULL, " +
//                    "PRIMARY KEY(cell_id));");
            String createItemsTable = "CREATE TABLE IF NOT EXISTS items (" +
                    "item_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "cell_id TEXT, " +
                    "material TEXT, " +
                    "item_meta TEXT, " +
                    "count INTEGER, " +
                    "FOREIGN KEY (cell_id) REFERENCES storage_cells(cell_id) ON DELETE CASCADE" +
                    ");";

            statement.execute(createItemsTable);

        }
    }

    public void addStorageCell(UUID uuid) throws SQLException {
        if (!uuidExists(uuid)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO storage_cells (cell_id) VALUES (?)")) {
                preparedStatement.setString(1, uuid.toString());
                preparedStatement.executeUpdate();
            }
        }
    }

    public void addItemsToCell(UUID uuid, ItemStack itemStack) {
        String material = itemStack.getType().toString();  // Get the material type
        String itemMeta = toBase64(itemStack.getItemMeta());  // Get serialized item metadata
        int amountToAdd = itemStack.getAmount();  // Get the amount of items to add

        String selectQuery = "SELECT count FROM items WHERE cell_id = ? AND material = ? AND item_meta = ?";
        try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
            selectStmt.setString(1, uuid.toString());
            selectStmt.setString(2, material);
            selectStmt.setString(3, itemMeta);

            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    // Item exists, update the count
                    int currentCount = rs.getInt("count");
                    int newCount = currentCount + amountToAdd;

                    // Update the count for the existing item
                    String updateQuery = "UPDATE items SET count = ? WHERE cell_id = ? AND material = ? AND item_meta = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                        updateStmt.setInt(1, newCount);  // Set the new count
                        updateStmt.setString(2, uuid.toString());
                        updateStmt.setString(3, material);
                        updateStmt.setString(4, itemMeta);

                        updateStmt.executeUpdate();
//                        System.out.println(amountToAdd + " items added.");
                    }
                } else {
                    // Item does not exist, insert it into the database
                    String insertQuery = "INSERT INTO items (cell_id, material, item_meta, count) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                        insertStmt.setString(1, uuid.toString());
                        insertStmt.setString(2, material);
                        insertStmt.setString(3, itemMeta);
                        insertStmt.setInt(4, amountToAdd);

                        insertStmt.executeUpdate();
//                        System.out.println(amountToAdd + " new items added.");
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void removeItemsFromCell(UUID uuid, ItemStack itemStack) {
        String material = itemStack.getType().toString();  // Get material (e.g., "OAK_LOG")
        String itemMeta = toBase64(itemStack.getItemMeta());  // Get serialized metadata (can be empty or null)
        int amountToRemove = itemStack.getAmount();  // Get the amount to remove

        String selectQuery = "SELECT count FROM items WHERE cell_id = ? AND material = ? AND item_meta = ?";
        try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
            selectStmt.setString(1, uuid.toString());
            selectStmt.setString(2, material);
            selectStmt.setString(3, itemMeta);

            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    int currentCount = rs.getInt("count");  // Get current count in the database

                    // Step 2: Update the count or delete the item
                    if (currentCount > amountToRemove) {
                        // Item exists and we just need to decrease the count
                        String updateQuery = "UPDATE items SET count = count - ? WHERE cell_id = ? AND material = ? AND item_meta = ?";
                        try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                            updateStmt.setInt(1, amountToRemove);  // Decrease by the amount to remove
                            updateStmt.setString(2, uuid.toString());
                            updateStmt.setString(3, material);
                            updateStmt.setString(4, itemMeta);

                            updateStmt.executeUpdate();
//                            System.out.println(amountToRemove + " items removed.");
                        }
                    } else if (currentCount == amountToRemove) {
                        // Remove the item completely if the count reaches 0
                        String deleteQuery = "DELETE FROM items WHERE cell_id = ? AND material = ? AND item_meta = ?";
                        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
                            deleteStmt.setString(1, uuid.toString());
                            deleteStmt.setString(2, material);
                            deleteStmt.setString(3, itemMeta);

                            deleteStmt.executeUpdate();
//                            System.out.println("Item completely removed.");
                        }
                    } else {
                        // Error: Trying to remove more than what exists in the database
//                        System.out.println("Error: Trying to remove more items than are available.");
                    }
                } else {
                    // this should never happen
//                    System.out.println("Item not found in the database.");
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public List<ItemStack> getItemsByCellUUID(UUID cellUUID) {
        List<ItemStack> itemList = new ArrayList<>();

        String query = "SELECT material, item_meta, count FROM items WHERE cell_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Set the cell UUID as parameter
            preparedStatement.setString(1, cellUUID.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Material material = Material.valueOf(resultSet.getString("material"));
                    ItemMeta itemMeta = fromBase64(resultSet.getString("item_meta"));
                    int count = resultSet.getInt("count");

                    ItemStack itemStack = new ItemStack(material, count);
                    itemStack.setItemMeta(itemMeta);

                    itemList.add(itemStack);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        catch (SQLException e) {
            System.out.println("Failed to get data for cell!");
        }

        itemList.sort(Comparator.comparing(ItemStack::getType));
        return itemList;
    }

    public int getCount(UUID cellId, ItemStack itemStack) {
        String query = "SELECT count FROM items WHERE cell_id = ? AND material = ? AND item_meta = ?";
        int count = 0;  // Default to 0 if no items are found

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, cellId.toString());       // Set the cell_id parameter
            preparedStatement.setString(2, itemStack.getType().toString());     // Set the material parameter
            preparedStatement.setString(3, toBase64(itemStack.getItemMeta()));     // Set the item_meta parameter (can be null)

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");  // Get the count from the result set
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle exceptions appropriately (e.g., logging)
        }

        return count;
    }

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
    public static String toBase64(ItemMeta itemMeta) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeObject(itemMeta);

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
    public static ItemMeta fromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            // Read the serialized inventory
            ItemMeta itemMeta = (ItemMeta) dataInput.readObject();

            dataInput.close();
            return itemMeta;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }


    public boolean uuidExists(UUID uuid) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM storage_cells WHERE cell_id = ?")) {
            preparedStatement.setString(1, uuid.toString());
            return preparedStatement.executeQuery().next();
        }
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
