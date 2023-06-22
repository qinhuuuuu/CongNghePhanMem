<%@ page import="bean.*" %>
<%@ page import="services.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<ul class="nav nav-page">
    <li class=" left">
        <a href="/homepage"><img src="images/logo/logo-rmbg1.png" alt=""></a>
    </li>


    <li class="nav-item center" id="nav-items">

        <a class="nav-link  " id="sanpham"
           href="/CongNghePhanMem/listProduct">Sản
            Phẩm</a>
        <a class="nav-link  " id="go"
           href="/CongNghePhanMem/listProduct">Gỗ</a>
        <a class="nav-link  " id="gom"
           href="/CongNghePhanMem/listProduct">Gốm</a>
        <a class="nav-link "
           href="/CongNghePhanMem/listProduct">Tranh</a>
        <a class="nav-link  "
           href="/CongNghePhanMem/listProduct">Giảm
            giá</a>

    </li>
    <li class=" right">
        <div class="item-right search-item">

            <input type="text" class="search-input" placeholder="Tìm kiếm..."
                   name="txName" value=""/>
            <span class="icon" id="search">
                <i class=" fa fa-search" name="action"></i>
            </span>
        </div>


        <%
//            User user = UserService.getInstance().getUserById(1);
            User user = session.getAttribute("authorization") !=null ?(User)session.getAttribute("authorization") : null;
//            request.getSession().setAttribute("user", user);
            List<Detail> cart = new ArrayList<>();
            if (user != null) {
                 cart = CartService.getInstance().getCartOfUser(user.getId());

        %>
        <a href="/CongNghePhanMem/cart" class="item-right"><i
                class="fa-solid fa-cart-shopping"></i>
            <p>Giỏ hàng </p>
        </a>
        <a href="/userProfile" class="item-right">
            <img src="images/userNull.png" alt="">
            <a href="/userProfile" class="item-right">
                <p><%=user.getName()%>
                </p>
            </a>
        </a>
        <a href="/CongNghePhanMem/logout" class="item-right"><i
                class="fa-solid fa-cart-shopping"></i>
            <p>Đăng xuất </p>
        </a>
        <%} else {%>
<%--        <a href="/http://localhost:8080/cart" class="item-right"><i--%>
<%--                class="fa-solid fa-cart-shopping"></i>--%>
<%--            <p>Giỏ hàng</p>--%>
<%--        </a>--%>
        <a href="/CongNghePhanMem/login" class="item-right">
            <i class="fa-solid fa-user"></i>
            <p>Đăng nhập</p>
        </a>
        <%}%>


    </li>
</ul>