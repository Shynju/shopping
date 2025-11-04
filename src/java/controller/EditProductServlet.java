package controller;

import data.dao.Database;
import data.dao.ProductDao;
import model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Category;

@WebServlet(name = "EditProductServlet", urlPatterns = {"/editProduct"})
public class EditProductServlet extends HttpServlet {

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setAttribute("title", "Sửa");

    // Lấy id sản phẩm từ query string
    String idStr = request.getParameter("id");
    if (idStr == null || idStr.isEmpty()) {
        response.sendRedirect("admin"); 
        return;
    }
    int id = Integer.parseInt(idStr);

    // Lấy product từ DB
    Product product = Database.getProductDao().find(id);
    if (product == null) {
        response.sendRedirect("admin");
        return;
    }

    // Lấy danh sách category
    List<Category> listCategory = Database.getCategoryDao().findAll();

    // Truyền dữ liệu sang JSP
    request.setAttribute("product", product);
    request.setAttribute("listCategory", listCategory);

    // Forward (chứ không include)
    request.getRequestDispatcher("/views/editProduct.jsp").forward(request, response);
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String image = request.getParameter("image");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        boolean status = request.getParameter("status") != null; // checkbox
        int id_category = Integer.parseInt(request.getParameter("id_category"));

        Product p = new Product(id, name, image, price, quantity, status, id_category);
        Database.getProductDao().updateProduct(p);
        response.sendRedirect("admin");
    }
}
    