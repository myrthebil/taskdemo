package se.myrthe.taskservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.myrthe.commonmodel.model.Task;

import java.util.HashSet;
import java.util.Set;

@RestController
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @GetMapping
    public Set<Task> getOwnedTasks(final String username) {
        logger.info("Collecting tasks for user {}", username);
        return new HashSet<>();
    }
}
