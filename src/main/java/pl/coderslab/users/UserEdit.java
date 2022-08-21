package pl.coderslab.users;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UserEdit", value = "/user/edit")
public class UserEdit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        UserDao userDao = new UserDao();
        User getUser = userDao.read(id);
        request.setAttribute("user", getUser);
        getServletContext().getRequestDispatcher("/users/edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User editUser = new User();
        editUser.setId(Integer.parseInt(request.getParameter("id")));
        editUser.setUsername(request.getParameter("userName"));
        editUser.setEmail(request.getParameter("userEmail"));
        editUser.setPassword(request.getParameter("userPassword"));
        UserDao userDao = new UserDao();
        userDao.update(editUser);
        response.sendRedirect(request.getContextPath()+ "/user/list");
    }
}
