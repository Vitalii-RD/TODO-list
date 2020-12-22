package com.todo_list.TODO_list;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TODOListRepositoryTest {
  private TODOListRepository repository;

  @BeforeEach
  public void setUp() {
    repository = new TODOListRepository();
  }

  @Test
  void WhenEmpty__getTasks__returnsEmpty() {
    Assertions.assertEquals(0, repository.getTasks().size());
  }

  void WhenAdded__getTasks__returnsAddedTasks() {
    Task t1 = new Task(1, "title1", "text1", false, new Date());
    Task t2 = new Task(2, "title2", "text2", true, new Date());
    List<Task> tasks = new ArrayList<>();

    tasks.add(t1);
    tasks.add(t2);
    repository.put(t1);
    repository.put(t2);

    Assertions.assertIterableEquals(tasks, repository.getTasks());
  }

  @Test
  void WhenInvoked__put__addsTask() {
    Task t1 = new Task(1, "title","text1", false, new Date());
    List<Task> tasks = new ArrayList<>();

    Task newTask = repository.put(t1);
    tasks.add(newTask);

    Assertions.assertIterableEquals(tasks, repository.getTasks());
  }

  @Test
  void update() {
    Task oldTask = new Task(1, "title1", "text", false, new Date());
    Task newTask = new Task(1, "title1", "new text", true, new Date());

    Task addedTask = repository.put(oldTask);
    repository.update(newTask, addedTask.getId());

    Assertions.assertEquals(newTask.getText(), addedTask.getText());
    Assertions.assertEquals(newTask.isDone(), addedTask.isDone());
    Assertions.assertEquals(newTask.getDueDate(), addedTask.getDueDate());
  }

  @Test
  void WhenInvoked__delete__removesTask() {
    Task t1 = new Task(1, "title", "text", false, new Date());

    repository.put(t1);
    repository.delete(1);

    Assertions.assertEquals(0, repository.getTasks().size());
  }

  public void tearDown() {
    repository = null;
  }
}