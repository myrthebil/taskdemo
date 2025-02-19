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
import java.util.List;
import java.util.Objects;

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

  private User(final UserBuilder builder) {
    this.username = builder.username;
    this.ownedTasks = builder.ownedTasks;
    this.assignedTasks = builder.assignedTasks;
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
    return "User{" +
        "createdBy=" + createdBy +
        ", id=" + id +
        ", username='" + username + '\'' +
        ", ownedTasks=" + ownedTasks +
        ", assignedTasks=" + assignedTasks +
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
    User user = (User) o;
    return getId() == user.getId() && Objects.equals(getUsername(), user.getUsername())
        && Objects.equals(getOwnedTasks(), user.getOwnedTasks())
        && Objects.equals(getAssignedTasks(), user.getAssignedTasks());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getUsername(), getOwnedTasks(), getAssignedTasks());
  }

  public static class UserBuilder {

    private String id;
    private String username;
    private List<Task> ownedTasks;
    private List<Task> assignedTasks;

    public UserBuilder setUsername(final String username) {
      this.username = username;
      return this;
    }

    public UserBuilder setOwnedTasks(final List<Task> ownedTasks) {
      this.ownedTasks = ownedTasks;
      return this;
    }

    public UserBuilder setAssignedTasks(final List<Task> assignedTasks) {
      this.assignedTasks = assignedTasks;
      return this;
    }

    public User build() {
      return new User(this);
    }
  }

}