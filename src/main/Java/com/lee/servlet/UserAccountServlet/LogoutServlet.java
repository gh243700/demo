package com.lee.servlet.UserAccountServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    System.out.println("in :" + getClass());
    HttpSession session = req.getSession();
    session.removeAttribute("User");
    session.invalidate();
    resp.sendRedirect(getServletContext().getInitParameter("uri")+"/main");
  }
}
