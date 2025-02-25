package se.myrthe.commonmodel.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a user entity within the system.
 *
 * <p>This entity extends {@link Auditable} to include audit fields such as
 * createdBy and lastModifiedBy, which track changes made by users. The generic
 * parameter {@code String} is used for the auditing identifier.</p>
 *
 * <p>The {@link User} entity is mapped to a database table and is intended
 * to store user-related information.</p>
 *
 * <p>To create a new user, you can use the {@link UserBuilder} for a fluent
 * API:</p>
 * <pre>
 *     User user = User.builder()
 *                     .username("bertie_botts")
 *                     .build();
 * </pre>
 *
 * @author Myrthe Bil
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "users")
public final class User extends Auditable<String> {

  /**
   * Represents the unique identifier for the user. The field is marked with
   * {@code @Id} for primary key mapping and {@code @GeneratedValue} to
   * automatically generate values for the user ID.
   *
   * @see jakarta.persistence.Id
   * @see jakarta.persistence.GeneratedValue
   * @see jakarta.persistence.Column
   */
  @Id
  @GeneratedValue
  @Column(name = "user_id")
  private int id;

  /**
   * The username of the user. This field must not be blank as enforced by the
   * {@code @NotBlank} constraint. A message is provided to indicate that the
   * username is required.
   *
   * @see jakarta.validation.constraints.NotBlank
   */
  @NotBlank(message = "Username is required")
  private String username;


  /**
   * The list of tasks owned by the user. This relationship is defined with
   * {@code @OneToMany}, and the {@code @JoinColumn} annotation specifies the
   * foreign key column in the related {@code Task} entity. The
   * {@code @JsonManagedReference} annotation is used to manage the
   * serialization of this list to prevent infinite recursion during JSON
   * conversion.
   *
   * @see jakarta.persistence.OneToMany
   * @see jakarta.persistence.JoinColumn
   * @see com.fasterxml.jackson.annotation.JsonManagedReference
   * @see Task
   */
  @OneToMany
  @JoinColumn(name = "user_id")
  @JsonManagedReference
  private List<Task> ownedTasks;

  /**
   * The list of tasks assigned to the user. This relationship is defined with
   * {@code @ManyToMany} and is mapped by the {@code assignedUsers} field in the
   * {@code Task} entity.
   *
   * @see jakarta.persistence.ManyToMany
   * @see Task
   */
  @ManyToMany(mappedBy = "assignedUsers")
  private List<Task> assignedTasks;

  /**
   * Default constructor for JPA.
   */
  public User() {
  }

  /**
   * Returns the unique identifier of the user.
   *
   * @return the user ID
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the unique identifier of the user.
   *
   * @param id the new user ID
   */
  public void setId(final int id) {
    this.id = id;
  }

  /**
   * Returns the username of the user.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Updates the username of the user.
   *
   * @param username the new username
   */
  public void setUsername(final String username) {
    this.username = username;
  }

  /**
   * Returns the list of {@link Task}s owned by the user.
   *
   * @return the list of owned {@link Task}s
   */
  public List<Task> getOwnedTasks() {
    return ownedTasks;
  }

  /**
   * Sets the list of {@link Task}s owned by the user.
   *
   * @param ownedTasks the new list of owned {@link Task}s
   */
  public void setOwnedTasks(final List<Task> ownedTasks) {
    this.ownedTasks = ownedTasks;
  }

  /**
   * Returns the list of {@link Task}s assigned to the user.
   *
   * @return the list of assigned {@link Task}s
   */
  public List<Task> getAssignedTasks() {
    return assignedTasks;
  }

  /**
   * Sets the list of {@link Task}s assigned to the user.
   *
   * @param assignedTasks the new list of assigned {@link Task}s
   */
  public void setAssignedTasks(final List<Task> assignedTasks) {
    this.assignedTasks = assignedTasks;
  }

  @Override
  public String toString() {
    return "User{" + "id=" + id + ", username='" + username + '\''
        + ", ownedTasks=" + ownedTasks + ", assignedTasks=" + assignedTasks
        + "}";
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return id == user.id && Objects.equals(username, user.username)
        && Objects.equals(ownedTasks, user.ownedTasks) && Objects.equals(
        assignedTasks, user.assignedTasks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, ownedTasks, assignedTasks);
  }

  /**
   * Private constructor for builder-based instantiation.
   *
   * @param builder the {@link UserBuilder} instance
   */
  private User(final UserBuilder builder) {
    this.id = builder.id;
    this.username = builder.username;
    this.ownedTasks = builder.ownedTasks;
    this.assignedTasks = builder.assignedTasks;
  }

  /**
   * Creates a new {@link UserBuilder} instance for constructing {@link User}
   * objects.
   *
   * @return a new {@link UserBuilder} instance
   */
  public static UserBuilder builder() {
    return new UserBuilder();
  }

  /**
   * Builder class for creating instances of {@link User}.
   */
  public static class UserBuilder {

    /**
     * The ID field of the {@link User}.
     */
    private int id;
    /**
     * The username of the {@link User}.
     */
    private String username;
    /**
     * Initialize to empty list of owned {@link Task}s.
     */
    private List<Task> ownedTasks = new ArrayList<>();
    /**
     * Initialize to empty list of assigned {@link Task}s.
     */
    private List<Task> assignedTasks = new ArrayList<>();

    /**
     * Sets the unique identifier for the user.
     *
     * @param id the user ID
     * @return the current {@link UserBuilder} instance
     */
    public UserBuilder id(final int id) {
      this.id = id;
      return this;
    }

    /**
     * Sets the username for the user.
     *
     * @param username the username
     * @return the current {@link UserBuilder} instance
     */
    public UserBuilder username(final String username) {
      this.username = username;
      return this;
    }

    /**
     * Sets the list of tasks owned by the user.
     *
     * @param ownedTasks the list of owned tasks
     * @return the current {@link UserBuilder} instance
     */
    public UserBuilder ownedTasks(final List<Task> ownedTasks) {
      this.ownedTasks = (ownedTasks != null) ? new ArrayList<>(ownedTasks)
          : new ArrayList<>();
      return this;
    }

    /**
     * Sets the list of tasks assigned to the user.
     *
     * @param assignedTasks the list of assigned tasks
     * @return the current {@link UserBuilder} instance
     */
    public UserBuilder assignedTasks(final List<Task> assignedTasks) {
      this.assignedTasks =
          (assignedTasks != null) ? new ArrayList<>(assignedTasks)
              : new ArrayList<>();
      return this;
    }

    /**
     * Builds and returns a new {@link User} instance using the provided
     * values.
     *
     * @return a new {@link User} object
     */
    public User build() {
      return new User(this);
    }
  }
}
