/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.dao;

import java.util.List;
import model.User;

/**
 *
 * @author lab
 */
public interface UserDao {
    public List<User> findAll();
    boolean updateUser(User user);
    public User find(String emailPhone,String password);
    public boolean register(User user);
}
