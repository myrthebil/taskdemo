package se.myrthe.taskservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import se.myrthe.commonmodel.model.Task;
import se.myrthe.commonmodel.model.TaskStatus;
import se.myrthe.commonmodel.model.User;
import se.myrthe.commonmodel.repository.UserRepository;

/**
 * This test contains a swift integration test using {@link MockMvc} to perform requests and resolve
 * them to retrieve the response body. This performs an application startup and loads all required
 * beans. We use a separate YAML to configure an in memory h2 database. Entities are created and
 * dropped automatically.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TaskServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.config.location=classpath:application-integration-test.yml"})
public class TaskServiceApplicationTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ObjectMapper mapper;

  @Test
  public void testApplicationStartup() {
    // tests application startup without any errors
  }

  @Test
  public void givenUserHarry_whenCreateTask_thenStatus200_andReturnsTask() throws Exception {
    createUsers();

    final String taskRequest = """
        {
          "name": "send an owl",
          "description": "remind Hermione to send an owl to Mad Eye Moody",
          "taskStatus": "TODO",
          "assignedUsers": [{"username": "Ron", "id": "2"}],
          "taskOwner": {
            "username": "Harry",
            "id": 1
          }
        }
        """;

    MvcResult result = mvc.perform(
            post("/api/v1/task").content(taskRequest).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();

    final Task resultTask = mapper.readValue(result.getResponse().getContentAsString(), Task.class);
    Assertions.assertEquals(1, resultTask.getId());
    Assertions.assertEquals("send an owl", resultTask.getName());
    Assertions.assertEquals("remind Hermione to send an owl to Mad Eye Moody",
        resultTask.getDescription());
    Assertions.assertEquals(TaskStatus.TODO, resultTask.getTaskStatus());
    Assertions.assertEquals(1, resultTask.getAssignedUsers().size());
  }

  @Test
  public void givenUserHarry_whenCreateCorruptTask_thenStatus400() throws Exception {
    createUsers();

    final String taskRequest = """
        {
          "invalidProperty": "A shrubbery!"
        }
        """;

    mvc.perform(
            post("/api/v1/task").content(taskRequest).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andReturn();

  }
    private void createUsers() {
    final User userHarry = new User();
    userHarry.setUsername("Harry");

    final User userRon = new User();
    userRon.setUsername("Ron");

    userRepository.saveAll(Arrays.asList(userHarry, userRon));
  }

}