package se.myrthe.commonmodel.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Entity
public class User extends Auditable<String> {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "user_id", name = "user_id")
    private int id;

    private String username;

    @OneToMany
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private Set<Task> ownedTasks;

    @ManyToMany
    @JoinTable(
            name="assigned_task_user",
            joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns={@JoinColumn(name="task_id")}
    )
    private Set<Task> assignedTasks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Task> getOwnedTasks() {
        return ownedTasks;
    }

    public void setOwnedTasks(Set<Task> ownedTasks) {
        this.ownedTasks = ownedTasks;
    }

    public Set<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public void setAssignedTasks(Set<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }
}