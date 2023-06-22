package controller;

import bean.*;
import bean.Product;
import services.ProductService;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddProduct", value = "/addProduct")
public class AddProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        int sizeProduct = ProductService.getInstance().getProductList().size();
        int price = Integer.parseInt(request.getParameter("price"));
        int inventory = Integer.parseInt(request.getParameter("inventory"));
        String imgSrc = "";
        String decription = request.getParameter("decription");
// 6. thêm sản phẩm vào cơ sở dữ liệu
        Product product = new Product(sizeProduct + 1, name, decription, price, imgSrc, 0);
        ProductService.getInstance().addProduct(product);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
