package controller;

import bean.User;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(request.getContextPath() + "/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Nhận email và password
        String email = request.getParameter("email");
        String password = request.getParameter("password");
//        Kiểm tra các field có trống không
        if (email.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "Email và mật khẩu không được bỏ trống");
            request.getRequestDispatcher( "login.jsp").forward(request, response);
            return;
        }
//        Tìm user trong database
        User user = userService.findByEmail(email);
        HttpSession session = request.getSession(true);
//      Kiểm tra user lấy từ database và thông tin người dùng nhập vào.
//        (Không kiểm tra trực tiếp tại database vì làm như vậy sẽ bảo mật thông tin về database hơn)
        boolean isPasswordValid = (user != null && user.getPassword() != null) ? user.getPassword().equals(password)  : false;
//      Khi đúng thông tin sẽ set session và chuyển về homepage
        if ((user != null) && isPasswordValid) {
            session.setAttribute("authorization", user);
            if(user.getVariety()==1){
                response.sendRedirect("/ProductManager");
            }else{
                response.sendRedirect("/homepage");
            }
//            request.setAttribute("successLogin", "Bạn đã đăng nhập thành công!");
        } else {
//            Khi sai thì sẽ set error về trang login page cụ thể là login form để hiển thị
            request.setAttribute("error", "Email hoặc Mật khẩu của bạn bị sai");
            request.getRequestDispatcher( "login.jsp").forward(request, response);
        }

    }

}
