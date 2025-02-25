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

/**
 * Represents a task entity within the system.
 *
 * <p>This entity extends {@link Auditable} to include audit fields such as
 * createdBy and lastModifiedBy, which track changes made by users. The generic parameter
 * {@code String} is used for the auditing identifier.</p>
 *
 * <p>The {@link Task} entity is mapped to a database table and is intended
 * to store task-related information.</p>
 *
 * <p>To create a new task, you can use the {@link TaskBuilder} for a fluent API:</p>
 * <pre>
 *     Task task = Task.builder()
 *                     .title("Find Hedwig")
 *                     .description("We need her to send a letter to Sirius")
 *                     .build();
 * </pre>
 *
 * @author Myrthe Bil
 * @version 1.0
 * @since 1.0
 */
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

  /**
   * Default constructor for JPA.
   */
  public Task() {
  }

  /**
   * Returns the unique identifier of the task.
   *
   * @return the task ID
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the unique identifier of the task.
   *
   * @param id the new task ID
   */
  public void setId(final int id) {
    this.id = id;
  }

  /**
   * Returns the title of the task.
   *
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title of the task.
   *
   * @param title the new title
   */
  public void setTitle(final String title) {
    this.title = title;
  }

  /**
   * Gets the description of the task.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the task.
   *
   * @param description the new description
   */
  public void setDescription(final String description) {
    this.description = description;
  }

  /**
   * Gets the {@link TaskStatus}.
   *
   * @return the {@link TaskStatus}
   */
  public TaskStatus getTaskStatus() {
    return taskStatus;
  }

  /**
   * Sets the {@link TaskStatus}.
   *
   * @param taskStatus the new status
   */
  public void setTaskStatus(final TaskStatus taskStatus) {
    this.taskStatus = taskStatus;
  }

  /**
   * Gets the {@link User} that owns the task.
   *
   * @return the {@link User}
   */
  public User getTaskOwner() {
    return taskOwner;
  }

  /**
   * Sets the {@link User} that owns the task.
   *
   * @param taskOwner the new owner
   */
  public void setTaskOwner(final User taskOwner) {
    this.taskOwner = taskOwner;
  }

  /**
   * Get a list of {@link User}s that are assigned to the task.
   *
   * @return the {@link List} of assigned {@link User}s
   */
  public List<User> getAssignedUsers() {
    return assignedUsers;
  }

  /**
   * Sets the list of {@link User}s assigned to the task.
   *
   * @param assignedUsers the new list of assigned {@link User}s
   */
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

  /**
   * Private constructor for builder-based instantiation.
   *
   * @param builder the {@link TaskBuilder} instance
   */
  private Task(final TaskBuilder builder) {
    this.id = builder.id;
    this.title = builder.title;
    this.description = builder.description;
    this.taskOwner = builder.taskOwner;
    this.assignedUsers = builder.assignedUsers;
  }

  /**
   * Creates a new {@link TaskBuilder} instance for constructing {@link Task} objects.
   *
   * @return a new {@link TaskBuilder} instance
   */
  public static TaskBuilder builder() {
    return new TaskBuilder();
  }

  /**
   * Builder class for creating instances of {@link Task}.
   */
  public static class TaskBuilder {

    private int id;
    private String title;
    private String description;
    private TaskStatus taskStatus;
    private User taskOwner;
    private List<User> assignedUsers;

    /**
     * Sets the title of the {@link Task}.
     *
     * @param title the new title
     * @return the current {@link TaskBuilder} instance
     */
    public TaskBuilder title(final String title) {
      this.title = title;
      return this;
    }

    /**
     * Sets the description of the {@link Task}.
     *
     * @param description the new description
     * @return the current {@link TaskBuilder} instance
     */
    public TaskBuilder description(final String description) {
      this.description = description;
      return this;
    }

    /**
     * Sets the status of the {@link Task}.
     *
     * @param taskStatus the new status
     * @return the current {@link TaskBuilder} instance
     */
    public TaskBuilder taskStatus(final TaskStatus taskStatus) {
      this.taskStatus = taskStatus;
      return this;
    }

    /**
     * Sets the owner of the {@link Task}.
     *
     * @param taskOwner the {@link User} that owns the task
     * @return the current {@link TaskBuilder} instance
     */
    public TaskBuilder taskOwner(final User taskOwner) {
      this.taskOwner = taskOwner;
      return this;
    }

    /**
     * Sets the list of {@link User}s assigned to the task.
     *
     * @param assignedUsers the list of assigned tasks
     * @return the current {@link TaskBuilder} instance
     */
    public TaskBuilder assignedUsers(final List<User> assignedUsers) {
      this.assignedUsers = assignedUsers;
      return this;
    }

    /**
     * Builds and returns a new {@link Task} instance using the provided values.
     *
     * @return a new {@link Task} object
     */
    public Task build() {
      return new Task(this);
    }

  }

}