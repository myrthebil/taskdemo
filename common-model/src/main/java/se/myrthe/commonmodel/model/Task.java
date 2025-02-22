package se.myrthe.commonmodel.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class Task extends Auditable<String> {

  @Id
  @GeneratedValue
  @Column(name = "task_id")
  private int id;
  @NotBlank(message = "Title is mandatory")
  private String title;
  @NotBlank(message = "Description is mandatory")
  private String description;
  @NotNull(message = "Task status is mandatory")
  private TaskStatus taskStatus;
  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonBackReference
  @NotNull(message = "Task owner is mandatory")
  private User taskOwner;
  @ManyToMany
  @JoinTable(
      name = "assigned_task_user",
      joinColumns = {@JoinColumn(name = "task_id")},
      inverseJoinColumns = {@JoinColumn(name = "user_id")}
  )
  private List<User> assignedUsers;

  public Task() {
  }

  private Task(final TaskBuilder builder) {
    this.title = builder.title;
    this.description = builder.description;
    this.taskStatus = builder.taskStatus;
    this.taskOwner = builder.taskOwner;
    this.assignedUsers = builder.assignedUsers;
  }

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
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
        ", name='" + title + '\'' +
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
    return getId() == task.getId() && Objects.equals(getTitle(), task.getTitle())
        && Objects.equals(getDescription(), task.getDescription())
        && getTaskStatus() == task.getTaskStatus() && Objects.equals(getTaskOwner(),
        task.getTaskOwner()) && Objects.equals(getAssignedUsers(), task.getAssignedUsers());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getTitle(), getDescription(), getTaskStatus(), getTaskOwner(),
        getAssignedUsers());
  }

  public static class TaskBuilder {

    private String title;
    private String description;
    private TaskStatus taskStatus;
    private User taskOwner;
    private List<User> assignedUsers;

    public TaskBuilder setTitle(final String title) {
      this.title = title;
      return this;
    }

    public TaskBuilder setDescription(final String description) {
      this.description = description;
      return this;
    }

    public TaskBuilder setTaskStatus(final TaskStatus taskStatus) {
      this.taskStatus = taskStatus;
      return this;
    }

    public TaskBuilder setTaskOwner(final User taskOwner) {
      this.taskOwner = taskOwner;
      return this;
    }

    public TaskBuilder setAssignedUsers(final List<User> assignedUsers) {
      this.assignedUsers = assignedUsers;
      return this;
    }

    public Task build() {
      return new Task(this);
    }

  }

}