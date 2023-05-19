package services;

import bean.User;
import db.JDBIConnector;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.HandleCallback;
import org.jdbi.v3.core.Jdbi;

public class UserService {
    private static UserService instance;
    protected Jdbi jdbi;
    public UserService() {
        this.jdbi = JDBIConnector.get();
    }


    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }
//    Tìm user trong databse bằng emal có thì sẽ trả về objet user không thì sẽ trả về null
    public User findByEmail(String email) {
        try {
            return this.jdbi.withHandle(new HandleCallback<User, Exception>() {
                public User withHandle(Handle handle) throws Exception {
                    System.out.println(2);
                    try {
                        return handle.createQuery(
                                        "SELECT * FROM user"  + " WHERE email = ?")
                                .bind(0, email)
                                .mapToBean(User.class).first();
                    } catch (IllegalStateException exception) {
                        return null;
                    }

                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserById(int id) {
        return JDBIConnector.get().withHandle(handle -> {
            return handle.createQuery("select id, name, email, phone, password, status from user where id =?")
                    .bind(0, id)
                    .mapToBean(User.class).one();
        });
    }

    public static void main(String[] args) {
        System.out.println(getInstance().getUserById(1));
    }
}
