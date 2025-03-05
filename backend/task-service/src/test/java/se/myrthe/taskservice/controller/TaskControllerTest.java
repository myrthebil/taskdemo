package se.myrthe.taskservice.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import se.myrthe.commonmodel.model.Task;
import se.myrthe.commonmodel.model.User;
import se.myrthe.taskservice.service.TaskService;

public class TaskControllerTest {

  private static final String NEVILLE = "Neville";
  @Mock
  private TaskService service;

  @InjectMocks
  private TaskController taskController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateTask_Success() {
    // Arrange
    User mockUser = new User();
    mockUser.setUsername(NEVILLE);

    Task newTask = new Task();
    newTask.setTaskOwner(mockUser);
    newTask.setTitle("Remembrall");
    newTask.setDescription("Don't forget to bring it");

    when(service.createTask(any(Task.class))).thenReturn(newTask);

    // Act
    Task responseTask = taskController.createTask(newTask);

    // Assert
    assertNotNull(responseTask);
    verify(service, times(1)).createTask(any(Task.class));
  }

  @Test
  void testRemoveTask_Success() {
    // Arrange
    Integer taskId = 1;

    // Mock the service to ensure removeTask is invoked
    doNothing().when(service).removeTask(taskId);

    // Act
    taskController.removeTask(taskId);

    // Assert
    verify(service, times(1)).removeTask(taskId);
  }
}