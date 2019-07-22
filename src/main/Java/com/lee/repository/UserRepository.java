package com.lee.repository;

import com.lee.model.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserRepository extends DataAccessObject<User> {

  public UserRepository(Connection connection) {
    super(connection);
  }

  public User readById(String str, String password) {
    String sql =
        "SELECT  username,first_name,last_name,email,age,created_on,password  FROM users WHERE (username = ? OR email = ?) AND password =?";
    User user;
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, str);
      preparedStatement.setString(2, str);
      preparedStatement.setString(3, password);
      ResultSet resultSet = preparedStatement.executeQuery();
      user = IterateUser(resultSet);
    } catch (SQLException e) {
      return null;
    }
    return user;
  }

  @Override
  public User readById(int id) {
    User user;
    String sql =
        "SELECT username,first_name,last_name,email,age,created_on,password FROM users WHERE id = ?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      user = IterateUser(resultSet);
    } catch (SQLException e) {
      return null;
    }
    return user;
  }

  private User IterateUser(ResultSet resultSet) throws SQLException {
    User user = null;
    while (resultSet.next()) {
      user = new User();
      user.setUsername(resultSet.getString(1));
      user.setFirst_name(resultSet.getString(2));
      user.setLast_name(resultSet.getString(3));
      user.setEmail(resultSet.getString(4));
      user.setAge(resultSet.getInt(5));
      user.setCreated_on(resultSet.getDate(6));
      user.setPassword(resultSet.getString(7));
    }
    return user;
  }

  @Override
  public List<User> readAll() {
    return null;
  }

  @Override
  public int delete(int obj) {
    return 0;
  }

  @Override
  public int update(User obj) {
    return 0;
  }

  @Override
  public int create(User obj) {
    String sql =
        "INSERT INTO users(username,first_name,last_name,email,age,created_on,password) VALUES (?,?,?,?,?,?,?)";
    int rowsAffected = 0;

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);

      if (obj != null) {
        preparedStatement.setString(1, obj.getUsername());
        preparedStatement.setString(2, obj.getFirst_name());
        preparedStatement.setString(3, obj.getLast_name());
        preparedStatement.setString(4, obj.getEmail());
        preparedStatement.setInt(5, obj.getAge());
        preparedStatement.setDate(6, new Date(obj.getCreated_on().getTime()));
        preparedStatement.setString(7, obj.getPassword());
        rowsAffected = preparedStatement.executeUpdate();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    System.out.println(rowsAffected);
    return rowsAffected;
  }
}
