package se.myrthe.commonmodel.repository;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import se.myrthe.commonmodel.model.Task;
import se.myrthe.commonmodel.model.User;

/**
 * Repository interface for {@link Task} entities.
 *
 * <p>This interface extends {@link ListCrudRepository} to provide basic CRUD
 * (Create, Read, Update, Delete) operations for {@link Task} objects.
 * Additionally, it provides custom query methods to find tasks based on
 * specific criteria and delete tasks by their ID. It is marked with the
 * {@code @Repository} annotation, indicating that it is a Spring Data
 * repository responsible for interacting with the database to manage
 * {@link Task} entities.
 *
 * @see Task
 * @see ListCrudRepository
 */
@Repository
public interface TaskRepository extends ListCrudRepository<Task, Integer> {

  /**
   * Finds a list of tasks assigned to a specific task owner.
   *
   * @param taskOwner The user who owns the tasks.
   * @return A list of {@link Task} objects owned by the specified {@link User}.
   */
  List<Task> findTasksByTaskOwner(User taskOwner);

  /**
   * Finds a list of tasks assigned to a specific {@link User}.
   *
   * @param assignedUser The user to whom the tasks are assigned.
   * @return A list of {@link Task} objects assigned to the specified
   *         {@link User}.
   */
  List<Task> findTasksByAssignedUsers(User assignedUser);

  /**
   * Deletes a task based on its unique identifier (ID).
   *
   * @param taskId The ID of the task to delete.
   * @throws IllegalArgumentException if the taskId is {@code null}.
   */
  void deleteTaskById(@NotNull Integer taskId);
}
