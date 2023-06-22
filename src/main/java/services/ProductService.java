package services;

import bean.Product;
import db.JDBIConnector;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private static ProductService instance;

    private ProductService() {

    }

    public static ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService();
        }
        return instance;
    }

    public List<Product> getProductList() {
        return JDBIConnector.get().withHandle(handle -> {
            return handle.createQuery("select id, name, description, price, imageSrc, status from product")
                    .mapToBean(Product.class).stream().collect(Collectors.toList());
        });
    }

    public Product getProductByDetailId(int detailId) {
        return JDBIConnector.get().withHandle(handle -> {
            return handle.createQuery("select p.id, p.name, p.description,p.price, p.imageSrc, p.status from product p join detail d on p.id = d.product_id where d.id =?")
                    .bind(0, detailId).mapToBean(Product.class).one();
        });
    }

    public static void main(String[] args) {
        System.out.println(getInstance().getProductByDetailId(1));
    }

    public int nextId() {
        return 1 + JDBIConnector.get().withHandle(handle -> {
            return handle.createQuery("SELECT MAX(`id`) as numberProduct FROM `product`").mapTo(Integer.class).one();
        });
    }
//  7. Lưu sản phẩm vào csdl
        public void addProduct(Product product) {
            JDBIConnector.get().withHandle(handle -> {
                return handle.createUpdate("INSERT INTO product VALUES (:id, :name, :description,  :price, :imageSrc, :status);")
                        .bind("id", nextId())
                        .bind("name", product.getName())
                        .bind("description", product.getDescription())
                        .bind("price", product.getPrice())
                        .bind("imageSrc", product.getImageSrc())
                        .bind("status", product.getStatus())
                        .execute();
            });
        }
    }
