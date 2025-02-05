package se.myrthe.taskservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.myrthe.commonmodel.model.Task;
import se.myrthe.taskservice.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public Task create(final Task task) {
        return repository.save(task);
    }
}
