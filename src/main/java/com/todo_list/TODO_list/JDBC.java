package com.todo_list.TODO_list;

import java.sql.*;
import java.util.Date;

public class JDBC {
  private Connection connection;

  public JDBC() throws ClassNotFoundException, SQLException {
    Class.forName("org.postgresql.Driver");
    connection = getConnection();
  }

  private Connection getConnection() throws SQLException {
    String url = "jdbc:postgresql://localhost:5432/java_todos";
    String user = "postgres";
    String passwd = "dydvitrom";
    return DriverManager.getConnection(url, user, passwd);
  }

  public ResultSet executeQuery(String query) throws SQLException {
    Statement statement = connection.createStatement();
    return statement.executeQuery(query);
  }

  public int putTask(Task task, String table) throws SQLException {
    String query = "INSERT INTO "+ table + " (title, text, done, datecreated, dateupdated, duedate) VALUES(?,?,?,?,?,?);";
    PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    Timestamp date = new Timestamp(new Date().getTime());
    int id = 0;
    ResultSet rs;

    pstmt.setString(1, task.getTitle());
    pstmt.setString(2, task.getText());
    pstmt.setBoolean(3, task.isDone());
    pstmt.setTimestamp(4, date);
    pstmt.setTimestamp(5, date);
    pstmt.setTimestamp(6, Timestamp.valueOf(task.getDueDate()));

    int rowAffected = pstmt.executeUpdate();
    if(rowAffected == 1) {
        rs = pstmt.getGeneratedKeys();
        if (rs.next()) id = rs.getInt(1);
    }

    return id;
  }

  public void executeUpdate(String query) throws SQLException {
    connection.createStatement().executeUpdate(query);
  }

  public void close() throws SQLException {
    connection.close();
  }
}
