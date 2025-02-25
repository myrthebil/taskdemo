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
 * createdBy and lastModifiedBy, which track changes made by users. The generic parameter
 * {@code String} is used for the auditing identifier.</p>
 *
 * <p>The {@code User} entity is mapped to a database table and is intended
 * to store user-related information.</p>
 *
 * <p>To create a new user, you can use the {@link UserBuilder} for a fluent API:</p>
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
public class User extends Auditable<String> {

  @Id
  @GeneratedValue
  @Column(name = "user_id")
  private int id;
  @NotBlank(message = "Username is required")
  private String username;
  @OneToMany
  @JoinColumn(name = "user_id")
  @JsonManagedReference
  private List<Task> ownedTasks;
  @ManyToMany(mappedBy = "assignedUsers")
  private List<Task> assignedTasks;

  public User() {
  }

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public List<Task> getOwnedTasks() {
    return ownedTasks;
  }

  public void setOwnedTasks(final List<Task> ownedTasks) {
    this.ownedTasks = ownedTasks;
  }

  public List<Task> getAssignedTasks() {
    return assignedTasks;
  }

  public void setAssignedTasks(final List<Task> assignedTasks) {
    this.assignedTasks = assignedTasks;
  }

  @Override
  public String toString() {
    return "User{" + "createdBy=" + createdBy + ", id=" + id + ", username='" + username + '\''
        + ", ownedTasks=" + ownedTasks + ", assignedTasks=" + assignedTasks + ", createdAt="
        + createdAt + ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedAt=" + lastModifiedAt
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return getId() == user.getId() && Objects.equals(getUsername(), user.getUsername())
        && Objects.equals(getOwnedTasks(), user.getOwnedTasks()) && Objects.equals(
        getAssignedTasks(), user.getAssignedTasks());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getUsername(), getOwnedTasks(), getAssignedTasks());
  }

  private User(final UserBuilder builder) {
    this.id = builder.id;
    this.username = builder.username;
    this.ownedTasks = builder.ownedTasks;
    this.assignedTasks = builder.assignedTasks;
  }

  /**
   * Creates a new {@link UserBuilder} instance for constructing {@link User} objects.
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

    private int id;
    private String username;
    private List<Task> ownedTasks = new ArrayList<>();
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
      this.ownedTasks = (ownedTasks != null) ? new ArrayList<>(ownedTasks) : new ArrayList<>();
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
          (assignedTasks != null) ? new ArrayList<>(assignedTasks) : new ArrayList<>();
      return this;
    }

    /**
     * Builds and returns a new {@link User} instance using the provided values.
     *
     * @return a new {@link User} object
     */
    public User build() {
      return new User(this);
    }
  }

}