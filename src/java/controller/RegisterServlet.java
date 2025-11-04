package controller;

import data.impl.UserImpl;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("title", "Register");
        // load form đăng ký
        request.getRequestDispatcher("./views/register.jsp").include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String role = request.getParameter("role");

        // kiểm tra mật khẩu
        if (!password.equals(repassword)) {
            request.setAttribute("error", "Mật khẩu không khớp!");
            request.getRequestDispatcher("./views/register.jsp").forward(request, response);
            return;
        }

        User user = new User(0, name, email, phone, password, role);
        UserImpl dao = new UserImpl();

        if (dao.register(user)) {
            // đăng ký thành công → chuyển qua login
            response.sendRedirect("login");
        } else {
            request.setAttribute("error", "Đăng ký thất bại!");
            request.getRequestDispatcher("./views/register.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "RegisterServlet - xử lý đăng ký người dùng";
    }
}
