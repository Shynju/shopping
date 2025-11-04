package controller;

import data.dao.Database;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;
import model.Product;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("title", "Home Page");

        // Lấy id_category nếu có
        String id_category = request.getParameter("id_category");
        request.setAttribute("id_category", id_category);

        // Lấy keyword từ form tìm kiếm
        String keyword = request.getParameter("keyword");
        request.setAttribute("keyword", keyword);

        // Lấy danh sách category và product
        List<Category> listCategory = Database.getCategoryDao().findAll();
        List<Product> listProduct = Database.getProductDao().findAll();

        // Nếu có keyword, lọc danh sách product theo tên
        if (keyword != null && !keyword.trim().isEmpty()) {
            List<Product> filteredList = new ArrayList<>();
            for (Product p : listProduct) {
                if (p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredList.add(p);
                }
            }
            listProduct = filteredList; // cập nhật danh sách hiển thị
        }

        // Nếu có id_category, lọc danh sách product theo category
        if (id_category != null && !id_category.isEmpty()) {
            int cid = 0;
            try {
                cid = Integer.parseInt(id_category);
            } catch (Exception e) {
            }
            final int finalCid = cid;
            List<Product> categoryFiltered = new ArrayList<>();
            for (Product p : listProduct) {
                if (p.getId_category() == finalCid) {
                    categoryFiltered.add(p);
                }
            }
            listProduct = categoryFiltered;
        }

        request.setAttribute("listCategory", listCategory);
        request.setAttribute("listProduct", listProduct);

        // Thêm sản phẩm vào giỏ hàng nếu có id_product
        addProductToCart(request);

        // Chuyển tiếp tới JSP
        request.getRequestDispatcher("./views/home.jsp").include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Nếu muốn xử lý POST giống GET (ví dụ form search method="post")
        doGet(request, response);
    }

    // Thêm sản phẩm vào giỏ hàng
    void addProductToCart(HttpServletRequest request) {
        int id_product = 0;
        try {
            id_product = Integer.parseInt(request.getParameter("id_product"));
        } catch (Exception e) {
        }

        List<Product> cart = (List<Product>) request.getSession().getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        if (id_product > 0) {
            Product product = Database.getProductDao().findProduct(id_product);
            boolean isProductInCart = false;
            for (Product pro : cart) {
                if (pro.getId() == id_product) {
                    pro.setQuantity(pro.getQuantity() + 1);
                    isProductInCart = true;
                    break;
                }
            }
            if (!isProductInCart) cart.add(product);
        }

        request.getSession().setAttribute("cart", cart);
    }
}
