package se.myrthe.commonmodel.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
public class Task extends Auditable<String> {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "task_id", name = "task_id")
    private int id;

    private String name;

    private String description;

    private TaskStatus taskStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User taskOwner;

    @ManyToMany(mappedBy = "users")
    private List<User> assignedUsers;

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
}