package controller;

import bean.Order;
import bean.User;
import services.AddressService;
import services.CartService;
import services.OrderService;
import services.TransportService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "FinishBuy", value = "/finishBuy")
public class FinishBuy extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int deliveryMethodId = Integer.parseInt(request.getParameter("deliveryMethodId"));
        String note = request.getParameter("note");
        int total = Integer.parseInt(request.getParameter("total"));

        Order order = new Order();
        User user = (User) request.getSession().getAttribute("user");
        order.setUser(user);
        order.setAddress(AddressService.getInstance().getAddressById(1));
        order.setTransport(TransportService.getInstance().getTransportById(deliveryMethodId));
        order.setNote(note);
        order.setOrderDetail(CartService.getInstance().getCartOfUser(user.getId()));
        order.setOrderDate(LocalDateTime.now());
        order.setTotal(total);
//    7.    Hệ thống thực thi câu lệnh lưu đơn hàng vào cơ sở dữ liệu.
        OrderService.getInstance().insertOrder(order);
        CartService.getInstance().resetCartOfUser(user.getId());
        request.getRequestDispatcher("finish-buy.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
