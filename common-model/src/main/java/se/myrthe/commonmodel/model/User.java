package se.myrthe.commonmodel.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "users")
public class User extends Auditable<String> {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private int id;

    private String username;

    @OneToMany
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private List<Task> ownedTasks;

    @ManyToMany
    @JoinTable(
            name="assigned_task_user",
            joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns={@JoinColumn(name="task_id")}
    )
    private List<Task> assignedTasks;

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
}