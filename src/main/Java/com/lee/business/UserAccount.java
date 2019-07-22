package com.lee.business.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccount {

  public boolean checkifvaliduser(String emailOrname, String password, Connection connection) {
    boolean check = false;
    String sql = "SELECT *FROM users WHERE (username = ? OR email = ?) AND password =?";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, emailOrname);
      preparedStatement.setString(2, emailOrname);
      preparedStatement.setString(3, password);
      ResultSet resultSet = preparedStatement.executeQuery();
      String username = null;
      while (resultSet.next()) {
        username = resultSet.getString("username");
        if (username != null) {
          check = true;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return false
    }
    return check;
  }

  public Integer getIdByusernameOrEmail(String info,Connection connection) {
    String sql = "SELECT id FROM users WHERE username = ? OR email = ?";
    Integer id = null;
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, info);
      preparedStatement.setString(2, info);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        id = resultSet.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return id;
  }

  public <T> boolean CheckifExists(T val, String comumn, Connection connection) {
    String sql = "SELECT *FROM users WHERE " + comumn + " =?";
    boolean check = false;
    try {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setObject(1, val);
      ResultSet resultSet = statement.executeQuery();

      int id = -1;
      while (resultSet.next()) {
        id = resultSet.getInt("id");
      }
      if (id == -1) {
        check = true;
      }
      System.out.println(check);
    } catch (SQLException e) {
      return false;
    }
    return check;
  }
}
