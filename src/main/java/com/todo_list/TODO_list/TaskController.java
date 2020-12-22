package com.todo_list.TODO_list;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
public class TaskController {
  private TODOListRepository repository = new TODOListRepository();

  @GetMapping("/tasks")
  public Collection<Task> getTasks() {
    return repository.getTasks();
  }

  @PostMapping("/tasks")
  @ResponseStatus(HttpStatus.CREATED)
  public Task putTask(@RequestBody Task task) {
    return repository.put(task);
  }

  @PutMapping("/tasks/{id}")
  public Task updateTask(@RequestBody Task task, @PathVariable("id") long id) {
    return repository.update(task, id);
  }

  @DeleteMapping("/tasks/{id}")
  public void deleteTask(@PathVariable("id") long id) {
    repository.delete(id);
  }
}
