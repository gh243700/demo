package com.lee.servlet.UserAccountServlet;

import com.lee.business.Business;
import com.lee.business.UserAccount;
import com.lee.model.User;
import com.lee.repository.UserRepository;
import com.lee.repository.util.DriverConnectionManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

public class LoginServlet extends HttpServlet {

  private Connection connection;
  private Business business;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    try {
      this.connection = new DriverConnectionManager().getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    business = new UserAccount(new UserRepository(connection));
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("/JSP/Login.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    System.out.println("in :" + getClass());
    String name = req.getParameter("nameOrEmail");
    String password = req.getParameter("password");

    String remember = req.getParameter("rememberUser");

    HashMap<String, String> value = new HashMap<>();

    UserAccount userAccount = (UserAccount) business;
    Map<String, Object> checkIfUserExists =
        userAccount.checkifvaliduser(name, password);
    if (!(boolean) checkIfUserExists.get("boolean")) {
      printLog("failed");
      value.put("name", name);
      value.put("password", password);
      req.setAttribute("value", value);
      doGet(req, resp);
      return;
    }
    printLog("Yes");
    User user = (User) checkIfUserExists.get("User");
    HttpSession httpSession = req.getSession();
    httpSession.setAttribute("User", user);
    resp.sendRedirect(getServletContext().getInitParameter("uri") + "/main");
  }

  private <T> void printLog(T val) {
    System.out.println(
        "------------------------------------------"
            + val
            + "---------------------------------------------------");
  }
}
