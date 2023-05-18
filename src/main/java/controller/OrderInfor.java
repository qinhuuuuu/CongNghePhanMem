package controller;

import bean.Address;
import bean.Transport;
import bean.User;
import services.AddressService;
import services.TransportService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Shipping", value = "/orderInfor")
public class OrderInfor extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("authorization");

        List<Address> addressList = AddressService.getInstance().getAddressOfUser(user.getId());
        request.setAttribute("addressList", addressList);

        List<Transport> transportList = TransportService.getInstance().getListTransport();
        request.setAttribute("transportList", transportList);

        request.getRequestDispatcher("order-infor.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
