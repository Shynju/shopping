/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.impl;

import java.sql.*;
import data.dao.UserDao;
import data.driver.MySQLDriver;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author lab
 */
public class UserImpl implements UserDao {
    Connection con = MySQLDriver.getConnection();

    @Override
    public User find(String emailPhone, String password) {
        try {
            String sql;
            if (emailPhone.contains("@")) {
                sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            } else {
                sql = "SELECT * FROM users WHERE phone = ? AND password = ?";
            }

            PreparedStatement sttm = con.prepareStatement(sql);
            sttm.setString(1, emailPhone);
            sttm.setString(2, password);

            ResultSet rs = sttm.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String pass = rs.getString("password");
                String role = rs.getString("role");
                return new User(id, name, email, phone, pass, role);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean register(User user) {
        try {
            String sql = "INSERT INTO users(name, email, phone, password, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement sttm = con.prepareStatement(sql);

            sttm.setString(1, user.getName());
            sttm.setString(2, user.getEmail());
            sttm.setString(3, user.getPhone());
            sttm.setString(4, user.getPassword());
            sttm.setString(5, user.getRole());

            int rows = sttm.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<User> findAll() {
        List<User> listUser = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try {
            PreparedStatement sttm = con.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String role = rs.getString("role");
                String password = rs.getString("password");

                listUser.add(new User(id, name, email, phone, role, password));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listUser;
    }
    @Override
    public boolean updateUser(User user) {
    String sql = "UPDATE users SET name=?, email=?, phone=?, role=?, password=? WHERE id=?";
    try (Connection conn = MySQLDriver.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPhone());
        ps.setString(4, user.getRole());
        ps.setString(5, user.getPassword());
        ps.setInt(6, user.getId());
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}


}

