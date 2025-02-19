package se.myrthe.taskservice.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import se.myrthe.commonmodel.model.Task;
import se.myrthe.commonmodel.model.User;
import se.myrthe.commonmodel.repository.TaskRepository;

public class TaskServiceTest {

  private static final String DUMBLEDORE = "Dumbledore";
  @Mock
  private TaskRepository repository;

  @InjectMocks
  private TaskService taskService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateTask() {
    // Test creating a task and saving it in the repository

    // Arrange
    User mockUser = new User();
    mockUser.setUsername("testUser");

    Task mockTask = new Task();
    mockTask.setTaskOwner(mockUser);
    mockTask.setName("Sample Task");

    when(repository.save(mockTask)).thenReturn(mockTask);

    // Act
    Task createdTask = taskService.createTask(mockTask);

    // Assert
    assertNotNull(createdTask);
    verify(repository, times(1)).save(mockTask);
  }

  @Test
  void testGetTasks() {
    // Test fetching tasks owned or assigned to a user

    // Arrange
    User mockUser = new User();
    mockUser.setUsername(DUMBLEDORE);

    Task ownedTask = new Task();
    ownedTask.setId(1);
    ownedTask.setTaskOwner(mockUser);
    ownedTask.setName("Owned Task");

    Task assignedTask = new Task();
    assignedTask.setId(2);
    assignedTask.setTaskOwner(new User()); // Different owner
    assignedTask.setName("Assigned Task");

    List<Task> ownedTasks = List.of(ownedTask);
    List<Task> assignedTasks = List.of(assignedTask);

    when(repository.findTasksByTaskOwner(mockUser)).thenReturn(ownedTasks);
    when(repository.findTasksByAssignedUsers(mockUser)).thenReturn(assignedTasks);

    // Act
    List<Task> retrievedTasks = taskService.getTasks(mockUser);

    // Assert
    Assertions.assertNotNull(retrievedTasks);
    Assertions.assertEquals(2, retrievedTasks.size());
    Assertions.assertTrue(retrievedTasks.contains(ownedTask));
    Assertions.assertTrue(retrievedTasks.contains(assignedTask));

    verify(repository, times(1)).findTasksByTaskOwner(mockUser);
    verify(repository, times(1)).findTasksByAssignedUsers(mockUser);
  }

  @Test
  void testUpdateTask_Success() {
    // Arrange
    User mockEditor = new User();
    mockEditor.setUsername("Rita");

    Task updatedTask = new Task();
    updatedTask.setId(1);
    updatedTask.setName("Daily Prophet draft");
    updatedTask.setDescription("Dumbledore: daft or dangerous?");
    updatedTask.setLastModifiedBy("Rita");

    // Mock the repository's save method to return the updated task
    when(repository.save(updatedTask)).thenReturn(updatedTask);

    // Act
    Task result = taskService.updateTask(mockEditor, updatedTask);

    // Assert
    assertNotNull(result);
    verify(repository, times(1)).save(updatedTask);  // Ensure save method is called once
  }

  @Test
  void testRemoveTask() {
    // Test removing a task by ID

    // Arrange
    Integer taskId = 1;

    doNothing().when(repository).deleteTaskById(taskId);

    // Act
    taskService.removeTask(taskId);

    // Assert
    verify(repository, times(1)).deleteTaskById(taskId);
  }
}