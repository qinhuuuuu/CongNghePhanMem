package services;

import bean.Order;
import db.JDBIConnector;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService {
    private static OrderService instance;

    private OrderService() {

    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    public int nextId() {
        return JDBIConnector.get().withHandle(handle -> {
            List<Integer> result = handle.createQuery("SELECT MAX(`id`) as max FROM `order`").mapTo(Integer.class).stream().collect(Collectors.toList());
            if (result.get(0) != null) {
                return result.get(0) + 1;
            }
            return 1;
        });
    }

    // Phương thức để hệ thống thực thi câu lệnh lưu đơn hàng vào cơ sở dữ liệu.
    public void insertOrder(Order o) {
        JDBIConnector.get().withHandle(handle -> {
            return handle.createUpdate("insert into `order`(id, user_id, address_id, total, note,order_date, transport_id) VALUES(?,?,?,?,?,?,?)")
                    .bind(0, OrderService.getInstance().nextId())
                    .bind(1, o.getUser().getId())
                    .bind(2, o.getAddress().getId())
                    .bind(3, o.getTotal())
                    .bind(4, o.getNote())
                    .bind(5, o.getOrderDate())
                    .bind(6, o.getTransport().getId())
                    .execute();

        });
    }

    public static void main(String[] args) {
        Order order = new Order();
        order.setAddress(AddressService.getInstance().getAddressById(1));
        order.setTransport(TransportService.getInstance().getTransportById(1));
        order.setNote("note");
        order.setOrderDetail(CartService.getInstance().getCartOfUser(1));
        order.setUser(UserService.getInstance().getUserById(1));
        order.setOrderDate(LocalDateTime.now());
        order.setTotal(Integer.parseInt("15"));
       getInstance().insertOrder(order);
    }


}
