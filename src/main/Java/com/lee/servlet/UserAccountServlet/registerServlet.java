package com.lee.servlet.UserAccountServlet;

import com.lee.business.Business;
import com.lee.business.UserAccount;
import com.lee.model.User;
import com.lee.repository.UserRepository;
import com.lee.repository.util.DriverConnectionManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.faces.flow.SwitchCase;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class registerServlet extends HttpServlet {

  private Business business;
  private Connection connection;

  @Override
  public void init(ServletConfig config) throws ServletException {
    try {
      this.connection = new DriverConnectionManager().getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    System.out.println("in :" + getClass());
    req.getRequestDispatcher("/JSP/register.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    System.out.println("in :" + getClass());
    String username = req.getParameter("username");
    String email = req.getParameter("email");
    String firstname = req.getParameter("firstname");
    String lastname = req.getParameter("lastname");
    String password = req.getParameter("password");
    String age = req.getParameter("age");
    // Connection connection = (Connection) req.getAttribute("connection");
    Date date = new Date();
    date.setTime(date.getTime());
    Map<String, String> message = new HashMap<>();

    business = new UserAccount(new UserRepository(connection));
    UserAccount userAccount = (UserAccount) business;

    boolean checkParameterIsnull =
        userAccount.checkIfParamaterHasNull(username, email, firstname, lastname, password, age);

    if (!checkParameterIsnull) {
      String checkInputHasNull = "form should not be empty";
      message.put("checkInputHasNull", checkInputHasNull);
      Map<String ,String > values = returnInputValue(username, email, firstname, lastname, password, age);
      req.setAttribute("data", values);
      req.setAttribute("message",message);
      doGet(req, resp);
      return;
    }

    String checkUsernameMessage = "username already exists";
    String checkEmailMessage = "email already exists";
    String wrongFormatMessage = "Only numbers";

    boolean checkEmail = userAccount.checkIfExist("email", email, connection);
    boolean checkUsername = userAccount.checkIfExist("username", username, connection);
    boolean checkFormat = userAccount.checkIntegerFormat(age);
    printLog("section 1");
    if (!checkEmail || !checkUsername || !checkFormat) {
      printLog("section 1.1");
      if (!checkEmail) {
        printLog("if (!checkEmail)");
        message.put("checkEmailMessage", checkEmailMessage);
      }
      if (!checkUsername) {
        printLog("if (!checkUsername)");
        message.put("checkUsernameMessage", checkUsernameMessage);
      }
      if (!checkFormat) {
        message.put("wrongFormatMessage", wrongFormatMessage);
      }
      HashMap<String, String> values =
          returnInputValue(username, email, firstname, lastname, password, age);

      req.setAttribute("message", message);
      req.setAttribute("data", values);
      doGet(req, resp);
      return;
    }
    printLog("section 2");
    boolean checkParamAndRegisterUser =
        userAccount.checkParamAndRegisterUser(username, email, firstname, lastname, password, age);
    if (!checkParamAndRegisterUser) {
      resp.sendError(500, "Error during register");
      return;
    } else {
      printLog("user created");
    }
    System.out.println("user Register" + message);
    resp.sendRedirect(getServletContext().getInitParameter("uri") + "/home");
  }

  private <T> void printLog(T val) {
    System.out.println(
        "------------------------------------------"
            + val
            + "---------------------------------------------------");
  }

  private HashMap<String, String> returnInputValue(
      String username,
      String email,
      String firstname,
      String lastname,
      String password,
      String age) {
    HashMap<String, String> values = new HashMap<>();
    values.put("username", username);
    values.put("email", email);
    values.put("firstname", firstname);
    values.put("lastname", lastname);
    values.put("password", password);
    values.put("age", age);
    return values;
  }
}
