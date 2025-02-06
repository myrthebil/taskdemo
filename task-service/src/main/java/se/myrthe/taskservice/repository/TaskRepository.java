package se.myrthe.taskservice.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import se.myrthe.commonmodel.model.Task;

@Repository
public interface TaskRepository extends ListCrudRepository<Task, Integer> {
}
