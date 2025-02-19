package se.myrthe.commonmodel.repository;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import se.myrthe.commonmodel.model.Task;
import se.myrthe.commonmodel.model.User;

@Repository
public interface TaskRepository extends ListCrudRepository<Task, Integer> {

  List<Task> findTasksByTaskOwner(final User taskOwner);

  List<Task> findTasksByAssignedUsers(final User assignedUser);

  void deleteTaskById(@NotNull final Integer taskId);
}
