package se.myrthe.commonmodel.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.List;
import java.util.Objects;

@Entity
public class Task extends Auditable<String> {

  @Id
  @GeneratedValue
  @Column(name = "task_id")
  private int id;
  private String name;
  private String description;
  private TaskStatus taskStatus;
  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonBackReference
  private User taskOwner;
  @ManyToMany(mappedBy = "assignedTasks")
  private List<User> assignedUsers;

  public Task() {
  }

  private Task(final TaskBuilder builder) {
    this.name = builder.name;
    this.description = builder.description;
    this.taskStatus = builder.taskStatus;
    this.taskOwner = builder.taskOwner;
  }

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public TaskStatus getTaskStatus() {
    return taskStatus;
  }

  public void setTaskStatus(final TaskStatus taskStatus) {
    this.taskStatus = taskStatus;
  }

  public User getTaskOwner() {
    return taskOwner;
  }

  public void setTaskOwner(final User taskOwner) {
    this.taskOwner = taskOwner;
  }

  public List<User> getAssignedUsers() {
    return assignedUsers;
  }

  public void setAssignedUsers(final List<User> assignedUsers) {
    this.assignedUsers = assignedUsers;
  }

  @Override
  public String toString() {
    return "Task{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", taskStatus=" + taskStatus +
        ", taskOwner=" + taskOwner +
        ", assignedUsers=" + assignedUsers +
        ", createdBy=" + createdBy +
        ", createdAt=" + createdAt +
        ", lastModifiedBy=" + lastModifiedBy +
        ", lastModifiedAt=" + lastModifiedAt +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Task task = (Task) o;
    return getId() == task.getId() && Objects.equals(getName(), task.getName())
        && Objects.equals(getDescription(), task.getDescription())
        && getTaskStatus() == task.getTaskStatus() && Objects.equals(getTaskOwner(),
        task.getTaskOwner()) && Objects.equals(getAssignedUsers(), task.getAssignedUsers());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getDescription(), getTaskStatus(), getTaskOwner(),
        getAssignedUsers());
  }

  public static class TaskBuilder {

    private String name;
    private String description;
    private TaskStatus taskStatus;
    private User taskOwner;

    public TaskBuilder setName(String name) {
      this.name = name;
      return this;
    }

    public TaskBuilder setDescription(String description) {
      this.description = description;
      return this;
    }

    public TaskBuilder setTaskStatus(TaskStatus taskStatus) {
      this.taskStatus = taskStatus;
      return this;
    }

    public TaskBuilder setTaskOwner(User taskOwner) {
      this.taskOwner = taskOwner;
      return this;
    }

    public Task build() {
      return new Task(this);
    }
  }

}