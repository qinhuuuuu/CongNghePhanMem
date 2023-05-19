package controller;

import bean.Product;
import bean.User;
import services.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductManager", value = "/ProductManager")
public class ProductManager extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("authorization");
        if (user == null || user.getVariety() != 1) {
            response.sendRedirect("/homepage");
            return;
        }
        String name = "Quản lý sản phẩm";
        List<Product> list = ProductService.getInstance().getProductList();
        int quantity = list.size();
        request.setAttribute("quantity", quantity);
        request.setAttribute("list", list);
        request.setAttribute("name", name);
        request.getRequestDispatcher("product-manager.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
