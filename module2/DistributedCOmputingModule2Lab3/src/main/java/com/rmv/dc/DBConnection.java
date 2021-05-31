package com.rmv.dc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
  private static Connection connection = null;

  private static final String url = "jdbc:postgresql://localhost:5432/railway";
  private static final String user = "postgres";
  private static final String password = "postgres";

  public static Connection getConnection() {
    try {
      if (connection == null || connection.isClosed()) {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(url, user, password);
      }
      return connection;
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void closeConnection() {
    try {
      connection.close();
    } catch (SQLException e) {

      e.printStackTrace();
    }
  }
}
