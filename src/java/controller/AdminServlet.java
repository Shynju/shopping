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
import jakarta.servlet.http.HttpSession;
import model.Category;
import model.Product;
import model.User;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);

        request.setAttribute("title", "Admin");
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
     
        // Chuyển tiếp tới JSP
        request.getRequestDispatcher("./views/admin.jsp").include(request, response);
    }

   
}
