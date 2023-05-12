package services;

import bean.Detail;
import db.JDBIConnector;

public class DetailService {
    private static DetailService instance;

    private DetailService() {

    }

    public static DetailService getInstance() {
        if (instance == null) {
            instance = new DetailService();
        }
        return instance;
    }
    public int maxId() {
        return  JDBIConnector.get().withHandle(handle -> {
            return handle.createQuery("SELECT MAX(`id`) as max FROM `order`").mapTo(Integer.class).one();
        });
    }
    public void insertDetail(Detail detail){
        JDBIConnector.get().withHandle(handle -> {
            return handle.createUpdate("insert into detail(product_id, quantity) values(?,?)")
                    .bind(0, detail.getProduct().getId())
                    .bind(1, detail.getQuantity())
                    .execute();
        });
    }
}
