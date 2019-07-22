package com.lee.servlet.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class userAuthentication implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
    System.out.println(getClass().getName());
    Boolean url_Add_Topic =
        httpRequest.getRequestURI().contains("Add_Topic") || httpRequest.getParameter("do") != null;
    Boolean url_Topic =
        httpRequest.getRequestURI().contains("topic")
            && httpRequest.getParameter("btn_submit") != null;
    if (url_Add_Topic || url_Topic) {
      HttpSession session = httpRequest.getSession();
      System.out.println("session_id :" + session.getId());
      if (session.getAttribute("User") == null) {
        // httpRequest.setAttribute("userAuth", false);
        httpRequest.getRequestDispatcher("/JSP/Login.jsp").forward(httpRequest, servletResponse);
      }
    }
    filterChain.doFilter(httpRequest, servletResponse);
  }

  @Override
  public void destroy() {}
}
