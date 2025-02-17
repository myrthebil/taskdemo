package se.myrthe.commonmodel.repository;

import java.util.List;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import se.myrthe.commonmodel.model.Task;
import se.myrthe.commonmodel.model.User;

@Repository
public interface TaskRepository extends ListCrudRepository<Task, Integer> {

  List<Task> findTasksByTaskOwner(final User user);
}
