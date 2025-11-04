/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.impl;
import data.driver.MySQLDriver;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import data.dao.ProductDao;
import model.Product;

/**
 *
 * @author Administrator
 */
public class ProductImpl implements ProductDao {
    Connection con = MySQLDriver.getConnection();
    @Override
public List<Product> findAll() {
    List<Product> listProduct = new ArrayList<>();
    String sql = "SELECT * FROM products";
    try {
        PreparedStatement sttm = con.prepareStatement(sql);
        ResultSet rs = sttm.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String image = rs.getString("image");
            double price = rs.getDouble("price");
            int quantity = rs.getInt("quantity");
            boolean status = rs.getBoolean("status");
            int id_category = rs.getInt("id_category");

            listProduct.add(new Product(id, name, image, price, quantity, status, id_category));
        }
    } catch (SQLException ex) {
        Logger.getLogger(ProductImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return listProduct;
}

    @Override
public void insert(Product p) {
    String sql = "INSERT INTO products(name, image, price, quantity, status, id_category) VALUES (?,?,?,?,?,?)";
    try (Connection con = MySQLDriver.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, p.getName());
        ps.setString(2, p.getImage());
        ps.setDouble(3, p.getPrice());
        ps.setInt(4, p.getQuantity());
        ps.setBoolean(5, p.isStatus());
        ps.setInt(6, p.getId_category());
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    @Override
public void updateProduct(Product p) {
    String sql = "UPDATE products SET name=?, image=?, price=?, quantity=?, status=?, id_category=? WHERE id=?";
    try (Connection con = MySQLDriver.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, p.getName());
        ps.setString(2, p.getImage());
        ps.setDouble(3, p.getPrice());
        ps.setInt(4, p.getQuantity());
        ps.setBoolean(5, p.isStatus());
        ps.setInt(6, p.getId_category());
        ps.setInt(7, p.getId());
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    @Override
    public void delete(int id) {
    String sql = "DELETE FROM products WHERE id = ?";
    try (Connection con = MySQLDriver.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



    @Override
    public Product find(int id){
        String sql = "SELECT * FROM products WHERE id = ?";
    try (Connection conn = MySQLDriver.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Product p = new Product();
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setImage(rs.getString("image"));
            p.setPrice(rs.getDouble("price"));
            p.setQuantity(rs.getInt("quantity"));
            p.setStatus(rs.getBoolean("status"));
            p.setId_category(rs.getInt("id_category"));
            return p;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
    }
    @Override
    public Product findProduct(int id_product){
        try {
            String sql="select * from products where id="+id_product;
            PreparedStatement sttm= con.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            if(rs.next()) return new Product(rs);
        } catch (SQLException ex) {
            Logger.getLogger(ProductImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    @Override
    public List<Product> searchByName(String keyword) { 
    List<Product> list = new ArrayList<>();
    String sql = "SELECT * FROM products WHERE name LIKE ?";
    try (Connection con = MySQLDriver.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("image"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getBoolean("status"),
                    rs.getInt("id_category")
            );
            list.add(p);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


}
