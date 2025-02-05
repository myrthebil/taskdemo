package se.myrthe.taskservice.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import se.myrthe.commonmodel.model.Task;
import se.myrthe.commonmodel.model.User;

import java.util.List;

@Repository
public interface TaskRepository extends ListCrudRepository<Task, Integer>{
}
