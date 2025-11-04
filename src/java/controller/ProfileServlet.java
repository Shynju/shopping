package controller;

import data.dao.UserDao;
import data.impl.UserImpl;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy user hiện tại từ session (giả sử sau khi login đã set vào session)
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("./views/profile.jsp").include(request, response);
    }
}
