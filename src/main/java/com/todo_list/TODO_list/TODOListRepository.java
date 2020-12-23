package com.todo_list.TODO_list;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.*;

public class TODOListRepository {
  private JDBC jdbc;
  private final String table;

  public TODOListRepository() {
    this("todos");
  }

  public TODOListRepository(String table) {
    try {
      jdbc = new JDBC();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    this.table = table;
  }

  public List<Task> getTasks() {
    List<Task> tasks = new LinkedList<>();

    try (ResultSet rs = jdbc.executeQuery("SELECT * FROM "+table+";")) {
      while (rs.next()) {
        tasks.add(getTask(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return tasks;
  }

  private Task getTask(ResultSet rs) throws SQLException {
    LocalDateTime created = rs.getTimestamp("datecreated").toLocalDateTime();
    LocalDateTime updated = rs.getTimestamp("dateupdated").toLocalDateTime();
    LocalDateTime dueDate = rs.getTimestamp("duedate").toLocalDateTime();

    return new Task(rs.getInt("id"), rs.getString("title"), rs.getString("text"),
            rs.getBoolean("done"), created, updated, dueDate);
  }

  public Task put(Task task) {
    return put(task, table);
  }

  public Task put(Task task, String table) {
    try {
      int id = jdbc.putTask(task, table);
      return get(id);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Smth went wrong :(");
    }
  }

  public Task get(int id) {
    try (ResultSet rs = jdbc.executeQuery("SELECT * FROM "+table+" WHERE id = " + id + ";")){
      if (rs.next()) return getTask(rs);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id was wrong");
    }
    return null;
  }

  public Task update(Task task, int id) {
    String query = "UPDATE "+table+" " +
            "SET title = '" + task.getTitle() + "', text = '" + task.getText() + "', " +
            "done = " + task.isDone() + ", duedate = '" + task.getDueDate() + "', " +
            "dateupdated = '" + new Timestamp(new Date().getTime()) + "' " +
            "WHERE id = " + id +";";

    try {
      if (get(id) != null) {
        jdbc.executeUpdate(query);
        return get(id);
      } else throw  new SQLException();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task was not found");
    }
  }

  public void delete(int id) {
    String query = "DELETE FROM "+table+" WHERE id = " + id + ";";

    try {
      if (get(id) != null) jdbc.executeUpdate(query);
      else throw new SQLException();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task was not found");
    }
  }
}
