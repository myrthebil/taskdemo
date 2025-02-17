package se.myrthe.taskservice.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.myrthe.commonmodel.model.Task;
import se.myrthe.commonmodel.model.User;
import se.myrthe.taskservice.exceptions.InvalidRequestBodyException;
import se.myrthe.taskservice.service.TaskService;

@RestController
public class TaskController {

  private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
  @Autowired
  private TaskService service;

  @PostMapping("/task")
  public Task createTask(@RequestBody final Task task) {
    logger.info("Creating task {} for user {}", task, task.getTaskOwner().getUsername());
    final Task createdTask = service.create(task);
    logger.info("Succesfully created a new task with id {}", createdTask.getId());
    return createdTask;
  }

  @PostMapping("/tasks")
  public List<Task> getOwnedTasks(@RequestBody final User user) {
    if (user == null) {
      logger.error("Missing user information, request body is empty");
      throw new InvalidRequestBodyException("Missing value for user in request body");
    }
    if (user.getUsername() == null) {
      logger.error("Missing username in request body");
      throw new InvalidRequestBodyException("Missing value for user in request body");
    }
    logger.info("Collecting tasks for user {}", user.getUsername());
    return new ArrayList<>();
  }
}
