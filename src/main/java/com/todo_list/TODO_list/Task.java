package com.todo_list.TODO_list;
import java.time.LocalDateTime;

public class Task {
  private int id;
  private String title;
  private String text;

  private LocalDateTime lastUpdated;
  private LocalDateTime dateCreated;
  private LocalDateTime dueDate;

  private boolean done;

  public Task(int id, String title, String text, boolean done,
              LocalDateTime created, LocalDateTime updated, LocalDateTime dueDate){
    this.id = id;
    this.title = title;
    this.text= text;
    this.done = done;

    this.dateCreated = created;
    this.lastUpdated = updated;
    this.dueDate = dueDate;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int getId() {
    return id;
  }

  public LocalDateTime getDateCreated() {
    return dateCreated;
  }

  public LocalDateTime getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDateTime dueDate) {
    this.dueDate = dueDate;
  }

  public boolean isDone() {
    return done;
  }

  public void setDone(boolean done) {
    this.done = done;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }
}
