package com.lee.business;

import com.lee.model.User;
import com.lee.repository.DataAccessObject;
import com.lee.repository.UserRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserAccount implements Business {
  private DataAccessObject dataAccessObject;

  public UserAccount(DataAccessObject dataAccessObject) {
    this.dataAccessObject = dataAccessObject;
  }

  public Map<String, Object> checkifvaliduser(
      String emailOrname, String password) {
    UserRepository userRepository;
    User user = null;
    Map<String, Object> hashMap = new HashMap<>();
    if (dataAccessObject instanceof UserRepository) {
      userRepository = (UserRepository) dataAccessObject;
      user = userRepository.readById(emailOrname, password);
    }
    hashMap.put("boolean", user != null);
    hashMap.put("User", user);
    return hashMap;
  }

  public boolean checkIfParamaterHasNull(String... str) {
    for (int i = 0; i < str.length; i++) {
      String param = str[i];
      if (param == null || param.equals("")) {
        return false;
      }
    }
    return true;
  }

  public boolean checkIntegerFormat(String val) {
    try {
      Integer.parseInt(val);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  public boolean checkParamAndRegisterUser(
      String username,
      String email,
      String firstname,
      String lastname,
      String password,
      String age) {
    User user = null;
    if (checkIfParamaterHasNull(username, email, firstname, lastname, password, age)) {
      user = new User();
      user.setUsername(username);
      user.setEmail(email);
      user.setFirst_name(firstname);
      user.setLast_name(lastname);
      user.setPassword(password);
      user.setAge(Integer.parseInt(age));
    }
    UserRepository userRepository = (UserRepository) dataAccessObject;
    return (userRepository.create(user) == 0);
  }

  public boolean checkIfExist(String column, Object value, Connection connection) {

    String sql = "SELECT COUNT(*) FROM  users WHERE " + column + " = ?";
    int count = 0;
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setObject(1, value);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) count = resultSet.getInt(1);
      if (count != 0) {
        return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
}
