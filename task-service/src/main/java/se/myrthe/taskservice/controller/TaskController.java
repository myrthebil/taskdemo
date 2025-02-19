package se.myrthe.taskservice.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.myrthe.commonmodel.model.Task;
import se.myrthe.commonmodel.model.User;
import se.myrthe.taskservice.service.TaskService;

@RestController
@RequestMapping(value = "/api/v1", consumes = MediaType.APPLICATION_JSON_VALUE, headers = {
    "Content-Type=application/json", "Accept=application/json"})
public class TaskController {

  private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
  @Autowired
  private TaskService service;

  @PostMapping("/task")
  public Task createTask(@Valid @RequestBody final Task task) {
    logger.info("Creating task for user {}", task.getTaskOwner().getUsername());
    final Task createdTask = service.createTask(task);
    logger.info("Succesfully created a new task with id {}", createdTask.getId());
    return createdTask;
  }

  @PostMapping(value = {"/tasks"})
  public List<Task> getTasks(@Valid @RequestBody final User user) {
    logger.info("Collecting tasks for user {}", user.getUsername());
    return service.getTasks(user);
  }

  @DeleteMapping(value = {"/task/remove"}, params = {"taskId"})
  public void removeTask(@RequestParam(name = "taskId") @NotNull final Integer taskId,
      final HttpServletResponse response) {
    logger.info("Removing task with id {}", taskId);
    service.removeTask(taskId);
  }

}
