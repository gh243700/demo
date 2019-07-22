package com.lee.servlet.filter;

import com.lee.repository.util.DriverConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ConnectionFilter implements Filter {

  private Connection connection;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
//    try {
//      this.connection = new DriverConnectionManager().getConnection();
//    } catch (SQLException e) {
//      this.connection = null;
//      e.printStackTrace();
//    }
//
//    System.out.println(getClass().getSimpleName()+":" );
   HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
////    if (connection == null) {
////      httpServletResponse.sendError(503);
////    }
//    httpServletRequest.setAttribute("connection", connection);
  chain.doFilter(httpServletRequest,httpServletResponse);
  }

  @Override
  public void destroy() {
    try {
      this.connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
