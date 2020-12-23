package com.todo_list.TODO_list;

import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class TODOListRepositoryTest {
  private static TODOListRepository repository;

  @BeforeAll
  public static void init() throws SQLException, ClassNotFoundException {
    new JDBC().executeUpdate("CREATE TABLE test (" +
            "id serial not null," +
            "title varchar(50) not null," +
            "text varchar(200) not null," +
            "done boolean not null," +
            "datecreated timestamp not null," +
            "dateupdated timestamp not null," +
            "duedate timestamp not null);");

    repository = new TODOListRepository("test");
  }

  @Test
  void WhenEmpty__getTasks__returnsEmpty() {
    Assertions.assertEquals(0, repository.getTasks().size());
  }

  @Test
  void WhenAdded__getTasks__returnsAddedTasks() {
    Task t1 = new Task(1, "title1", "text1", false,
            LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
    Task t2 = new Task(2, "title2", "text2", true,
            LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());

    Task t3 = repository.put(t1);
    Task t4 = repository.put(t2);

    Assertions.assertEquals(t3.getId(), repository.getTasks().get(0).getId());
    Assertions.assertEquals(t4.getId(), repository.getTasks().get(1).getId());
  }

  @Test
  void WhenInvoked__put__addsTask() {
    Task t1 = new Task(1, "title","text1", false, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());

    Task newTask = repository.put(t1);

    Assertions.assertEquals(newTask.getId(), repository.getTasks().get(0).getId());
  }

  @Test
  void update() {
    LocalDateTime dueDate  = LocalDateTime.now();
    Task oldTask = new Task(1, "title", "text", false,
            LocalDateTime.now(), LocalDateTime.now(), dueDate);
    Task newTask = new Task(1, "new title", "new text", true,
            LocalDateTime.now(), LocalDateTime.now(),dueDate);

    Task addedTask = repository.put(oldTask);
    Task updatedTask = repository.update(newTask, addedTask.getId());
    String pattern = "dd/MM/yyyy HH:mm:ss";
    String date1 =  newTask.getDueDate().format(DateTimeFormatter.ofPattern(pattern));
    String date2 =  updatedTask.getDueDate().format(DateTimeFormatter.ofPattern(pattern));

    assertEquals(newTask.getText(), updatedTask.getText());
    assertEquals(newTask.isDone(), updatedTask.isDone());
    assertEquals(date1, date2);
  }

  @Test
  void WhenInvoked__delete__removesTask() {
    Task t1 = new Task(1, "title", "text", false,
            LocalDateTime.now(), LocalDateTime.now(),LocalDateTime.now());
    int sizeBefore = repository.getTasks().size();

    Task addedTask = repository.put(t1);
    repository.delete(addedTask.getId());

    assertEquals(sizeBefore, repository.getTasks().size());
  }

  @AfterEach
  public void cleanUp() throws SQLException, ClassNotFoundException {
    new JDBC().executeUpdate("DELETE FROM test;");
  }

  @AfterAll
  public static void tearDown() throws SQLException, ClassNotFoundException {
    new JDBC().executeUpdate("DROP TABLE test;");
  }
}