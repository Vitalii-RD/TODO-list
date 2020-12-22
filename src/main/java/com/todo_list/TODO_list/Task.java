package com.todo_list.TODO_list;
import java.util.Date;

public class Task {
  private long id;
  private String title;
  private String text;

  private Date lastUpdated;
  private Date dateCreated;
  private Date dueDate;

  private boolean isDone;

  public Task(long id, String title, String text, boolean isDone, Date dueDate){
    this.id = id;
    this.title = title;
    this.text= text;

    Date now = new Date();
    this.dateCreated = now;
    this.lastUpdated = now;
    this.dueDate = dueDate;
  }

  public void updateLastUpdated() {
    this.lastUpdated = new Date();
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

  public long getId() {
    return id;
  }

  public Date getDateCreated() {
    return dateCreated;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }
  public boolean isDone() {
    return isDone;
  }

  public void setDone(boolean done) {
    isDone = done;
  }

  public Date getLastUpdated() {
    return lastUpdated;
  }
}
