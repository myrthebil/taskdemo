package se.myrthe.taskservice.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.myrthe.commonmodel.model.Task;
import se.myrthe.commonmodel.model.TaskStatus;
import se.myrthe.commonmodel.model.User;
import se.myrthe.commonmodel.repository.TaskRepository;
import se.myrthe.commonmodel.repository.UserRepository;
import se.myrthe.taskservice.controller.TaskController;

/**
 * Test class for {@link TaskController} using {@link DataJpaTest} for a more lightweight way of
 * testing.
 */
@DataJpaTest
public class TaskRepositoryTest {


  public static final String RUBEUS = "Rubeus";
  public static final String HARRY = "Harry";

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void testSaveTask() {
    // Let's create some users first
    final User savedTaskOwner = createAndSaveUser("Harry");
    final User savedAssignedUser = createAndSaveUser("Ron");

    final Task savedTask = createAndSaveTask(savedTaskOwner, savedAssignedUser);

    // Check the saved entity
    Assertions.assertEquals(savedTaskOwner, savedTask.getTaskOwner());
    Assertions.assertEquals("Feed slugs", savedTask.getName());
    Assertions.assertEquals("Make sure they don't feed on you", savedTask.getDescription());

    // Check the audit information of the saved task
    Assertions.assertNotNull(savedTask.getCreatedAt());
    Assertions.assertEquals(RUBEUS, savedTask.getCreatedBy());
    Assertions.assertNotNull(savedTask.getLastModifiedAt());
    Assertions.assertEquals(RUBEUS, savedTask.getLastModifiedBy());
  }

  @Test
  public void testFindTasksByTaskOwner() {
    // Let's create a user first
    final User taskOwner = createAndSaveUser(RUBEUS);
    final User assignedUser = createAndSaveUser(HARRY);

    // And save a new task for this user
    final Task savedTask = createAndSaveTask(taskOwner, assignedUser);

    // Test finding all tasks for the user
    final Task foundTask = taskRepository.findTasksByTaskOwner(taskOwner).getFirst();

    // Check if the found task is indeed the saved task
    Assertions.assertEquals(savedTask.getId(), foundTask.getId());
    Assertions.assertEquals(taskOwner, savedTask.getTaskOwner());
  }

  @Test
  public void testFindTasksByTaskAssignee() {
    // Let's create the task owner first
    final User taskOwner = createAndSaveUser(RUBEUS);

    // And the one to be put to work
    final User assignedUser = createAndSaveUser(HARRY);

    // And save a new task for these users
    final Task savedTask = createAndSaveTask(taskOwner, assignedUser);

    // Test finding all tasks for the assigned user
    final List<Task> foundTasks = taskRepository.findTasksByAssignedUsers(assignedUser);

    // Check if the found task is indeed the assigned task
    Assertions.assertEquals(savedTask.getId(), foundTasks.getFirst().getId());
    Assertions.assertEquals(assignedUser, savedTask.getAssignedUsers().getFirst());
  }

  @Test
  public void testUpdateTask() {
    // Let's create the task owner first
    final User taskOwner = createAndSaveUser(RUBEUS);

    // And the one to be put to work
    final User assignedUser = createAndSaveUser(HARRY);

    // And save a new task for these users
    Task savedTask = createAndSaveTask(taskOwner, assignedUser);

    // Find the saved task by ID
    Task foundTask = taskRepository.findById(savedTask.getId()).orElseThrow();

    // Change the name of the task
    foundTask.setName("Wingardium!");

    // Update the task
    taskRepository.save(foundTask);

    // Retrieve the task by id and check the name
    final Task updatedTask = taskRepository.findById(foundTask.getId()).orElseThrow();

    Assertions.assertEquals("Wingardium!", updatedTask.getName());
  }

  @Test
  public void testDeleteTask() {
    // Let's create a user first
    final User savedUser = createAndSaveUser(RUBEUS);

    // And save a new task for this user
    final Task savedTask = createAndSaveTask(savedUser, null);

    // Test deleting this task
    taskRepository.deleteTaskById(savedTask.getId());

    Optional<Task> task = taskRepository.findById(savedTask.getId());

    Assertions.assertTrue(task.isEmpty());
  }

  private User createAndSaveUser(final String username) {
    final User user = new User.UserBuilder().setUsername(username).build();
    userRepository.save(user);
    return user;
  }

  private Task createAndSaveTask(final User taskOwner, final User taskAssignee) {
    // Test saving an entity for this user
    final Task task = createTask(taskOwner, taskAssignee);

    // Set the audit info
    task.setCreatedBy(RUBEUS);
    task.setLastModifiedBy(RUBEUS);

    // Save the task
    return taskRepository.save(task);
  }

  private static Task createTask(final User taskOwner, final User assignedUser) {
    List<User> assignedUsers = new ArrayList<>();
    assignedUsers.add(assignedUser);
    return new Task.TaskBuilder()
        .setTaskOwner(taskOwner)
        .setAssignedUsers(assignedUsers)
        .setTaskStatus(TaskStatus.TODO)
        .setName("Feed slugs")
        .setDescription("Make sure they don't feed on you")
        .build();
  }

}