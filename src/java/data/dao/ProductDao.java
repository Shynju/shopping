/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.dao;

import java.util.List;
import model.Product;

/**
 *
 * @author Administrator
 */
public interface ProductDao {
    public List<Product> findAll();
    void insert(Product p);
    public void updateProduct(Product p);
    public void delete(int id);
    public Product find(int id);
    public Product findProduct(int id_product);
    List<Product> searchByName(String keyword);

}
