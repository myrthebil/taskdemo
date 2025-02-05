package se.myrthe.taskservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.myrthe.commonmodel.model.Task;
import se.myrthe.commonmodel.model.User;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @PostMapping("/tasks")
    public List<Task> getOwnedTasks(@RequestBody final User user) {
        logger.info("Collecting tasks for user {}", user.getUsername());
        return new ArrayList<>();
    }
}
