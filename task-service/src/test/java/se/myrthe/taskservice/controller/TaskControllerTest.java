package se.myrthe.taskservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import se.myrthe.commonmodel.model.Task;
import se.myrthe.commonmodel.model.User;
import se.myrthe.commonmodel.repository.TaskRepository;
import se.myrthe.commonmodel.repository.UserRepository;

@DataJpaTest
@TestPropertySource(properties = {
    "spring.config.location=classpath:application-integration-test.yml"})
public class TaskControllerTest {

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void testSaveTask() {
    // Let's create a user first
    final User user = createAndSaveUser();

    final Task savedTask = createAndSaveTask(user);

    // Check the saved entity
    Assertions.assertEquals(1, savedTask.getId());
    Assertions.assertEquals(user, savedTask.getTaskOwner());
    Assertions.assertEquals("Feed slugs", savedTask.getName());
    Assertions.assertEquals("Make sure they don't feed on you", savedTask.getDescription());

    // Check the audit information of the saved task
    Assertions.assertNotNull(savedTask.getCreatedAt());
    Assertions.assertEquals("Rubeus", savedTask.getCreatedBy());
    Assertions.assertNotNull(savedTask.getLastModifiedAt());
    Assertions.assertEquals("Rubeus", savedTask.getLastModifiedBy());
  }

  @Test
  public void testFindById() {
    // Let's create a user first
    final User savedUser = createAndSaveUser();

    final Task savedTask = createAndSaveTask(savedUser);

    // Test finding an entity by ID
    final Task foundTask = taskRepository.findById(savedTask.getId()).orElseThrow();

    // Check if the found task is indeed the saved task, which is the only one present in the repository
    Assertions.assertEquals(savedTask.getId(), foundTask.getId());
  }

  @Test
  public void testFindAll() {
    // Test retrieving all entities
  }

  @Test
  public void testUpdateTask() {
    // Test updating an entity
  }

  @Test
  public void testDeleteTask() {
    // Test deleting an entity
  }

  private User createAndSaveUser() {
    final User user = new User.UserBuilder().setUsername("Rubeus").build();
    userRepository.save(user);
    return user;
  }

  private Task createAndSaveTask(User user) {
    // Test saving an entity for this user
    final Task task = new Task.TaskBuilder()
        .setTaskOwner(user)
        .setName("Feed slugs")
        .setDescription("Make sure they don't feed on you")
        .build();

    // Set the audit info
    task.setCreatedBy("Rubeus");
    task.setLastModifiedBy("Rubeus");

    // Save the task
    return taskRepository.save(task);
  }
}