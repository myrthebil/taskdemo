package se.myrthe.taskservice.service;

import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.myrthe.commonmodel.model.Task;
import se.myrthe.commonmodel.model.User;
import se.myrthe.commonmodel.repository.TaskRepository;

@Service
public class TaskService {

  @Autowired
  private TaskRepository repository;

  public Task createTask(final Task task) {
    task.setCreatedBy(task.getTaskOwner().getUsername());
    return repository.save(task);
  }

  public Task updateTask(final User editor, final Task task) {
    task.setLastModifiedBy(editor.getUsername());
    return repository.save(task);
  }

  public List<Task> getTasks(final User user) {
    return Stream.concat(
            repository.findTasksByTaskOwner(user).stream(),
            repository.findTasksByAssignedUsers(user).stream()
        )
        .collect(Collectors.toCollection(HashSet::new)) // Ensures uniqueness
        .stream()
        .toList();
  }

  public void removeTask(@NotNull Integer taskId) {
    repository.deleteTaskById(taskId);
  }
}
