package com.todo_list.TODO_list;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class TODOListRepository {
  private final AtomicLong counter = new AtomicLong();
  private Map<Long, Task> tasks;

  public TODOListRepository(Map<Long, Task> tasks) {
    this.tasks = tasks;
  }

  public TODOListRepository() {
    tasks = new HashMap<>();
  }

  public Collection<Task> getTasks() {
    return tasks.values();
  }

  public Task put(Task task) {
    long id = counter.incrementAndGet();
    Task newTask = new Task(id, task.getTitle(), task.getText(), task.getDone(), task.getDueDate());
    tasks.put(id, newTask);
    return newTask;
  }

  public Task update(Task task, long id) {
    Task oldTask = tasks.getOrDefault(id, null);
    if (oldTask == null)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task was not found");

    oldTask.setTitle(task.getTitle());
    oldTask.setText(task.getText());
    oldTask.setDone(task.getDone());
    oldTask.setDueDate(task.getDueDate());

    oldTask.updateLastUpdated();

    return oldTask;
  }

  public void delete(long id) {
    Task oldTask = tasks.getOrDefault(id, null);
    if (oldTask == null)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task was not found");

    tasks.remove(id);
  }
}
