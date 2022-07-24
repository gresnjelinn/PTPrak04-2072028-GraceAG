package com.example.pt04prak2072028jdbc.dao;

import com.example.pt04prak2072028jdbc.model.Category;
import com.example.pt04prak2072028jdbc.model.Items;
import com.example.pt04prak2072028jdbc.util.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemsDao implements DaoInterface<Items> {

    @Override
    public ObservableList<Items> getData() {
        ObservableList<Items> iList;
        iList = FXCollections.observableArrayList();

        Connection conn = MyConnection.getConnection();
        String kalimat_sql = "SELECT i.id AS items_id, i.name AS items_name, price, description, category_id, c.id, c.name AS category_name FROM Items i JOIN Category c ON i.category_id = c.id";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(kalimat_sql);
            ResultSet result = ps.executeQuery();
            while(result.next()) {
                Category c = new Category();
                c.setId(result.getInt("category_id"));
                c.setName(result.getString("category_name"));

                Items i = new Items();
                i.setId(result.getInt("items_id"));
                i.setName(result.getString("items_name"));
                i.setPrice(result.getDouble("price"));
                i.setDescription(result.getString("description"));
                i.setCategory(c);
                iList.add(i);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return iList;
    }

    @Override
    public void insertData(Items data) {
        Connection conn = MyConnection.getConnection();
        String kalimat_sql = "INSERT INTO items VALUES(?, ?, ?, ?, ?)";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(kalimat_sql);
            ps.setInt(1, data.getId());
            ps.setString(2, data.getName());
            ps.setDouble(3, data.getPrice());
            ps.setString(4, data.getDescription());
            ps.setInt(5, data.getCategory().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteData(Items data) {
        Connection conn = MyConnection.getConnection();
        String kalimat_sql = "DELETE FROM items WHERE id = ?";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(kalimat_sql);
            ps.setInt(1, data.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateData(Items data) {
        Connection conn = MyConnection.getConnection();
        String kalimat_sql = "UPDATE items SET name = ?, price = ?, description = ?, category_id = ? WHERE id = ?";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(kalimat_sql);
            ps.setString(1, data.getName());
            ps.setDouble(2, data.getPrice());
            ps.setString(3, data.getDescription());
            ps.setInt(4, data.getCategory().getId());
            ps.setInt(5, data.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
