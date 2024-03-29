package controller;

import get.UserGet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cart;
import model.User;



public class UserServlet extends HttpServlet {

  UserGet userGet = new UserGet();
  Cart cart = new Cart();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
   
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String command = request.getParameter("command");
        
        
        String url = "";
        User users = new User();
        HttpSession session = request.getSession();
        switch (command) {
            case "insert":
                users.setUserID(new java.util.Date().getTime());
                users.setUserName(request.getParameter("name"));
                users.setUserEmail(request.getParameter("email"));    
                users.setUserPass(request.getParameter(("pass")));
                users.setUserRole(false);
                users.setUserPhone(request.getParameter("phone"));       
                userGet.insertUser(users);             
                session.setAttribute("user", users);
                url = "/Shopmaytinh/index.jsp";   
                break;
              case "update":
                  long user_id = Long.parseLong(request.getParameter("user_id"));
        String username = request.getParameter("username");
        String useremail = request.getParameter("useremail");
        String password = request.getParameter("pass");
        boolean role = Boolean.parseBoolean(request.getParameter("role"));
        String phone = request.getParameter("phone");
                       userGet.updateUser(new User(user_id, username, useremail, password, role, phone));
                        url = "/Shopmaytinh/myaccount.jsp";
                        break; 
            case "logindeal":
                users = userGet.login(request.getParameter("name"), (request.getParameter("pass")));                
                if (users != null) {
                    session.setAttribute("user", users);
                    url = "/Shopmaytinh/deal.jsp";        
                }
                break;
              
                  
            case "login":
                users = userGet.login(request.getParameter("name"), (request.getParameter("pass")));                
                if (users != null) {
                    session.setAttribute("user", users);
                    url = "/Shopmaytinh/index.jsp";        
                }
                
                
                else{
                    session.setAttribute("error", "Lỗi tên đăng nhập hoặc mật khẩu!");
                    url = "/Shopmaytinh/login.jsp";
                }
                break;
                
        }
        response.sendRedirect(url);
    

    }

}
