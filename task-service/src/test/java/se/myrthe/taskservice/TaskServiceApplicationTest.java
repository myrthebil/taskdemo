package se.myrthe.taskservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import se.myrthe.commonmodel.model.Task;
import se.myrthe.commonmodel.model.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TaskServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.config.location=classpath:application-integration-test.yml"})
public class TaskServiceApplicationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testApplicationStartup() {
        // tests application startup without any errors
    }

    @Test
    public void givenUserHarry_whenCreateTask_thenStatus200() throws Exception {
        final User user = new User();
        user.setUsername("Harry");
        final Task task = new Task.TaskBuilder()
                .setTaskOwner(user)
                .setName("send an owl")
                .setDescription("remind Hermione to send an owl to Mad Eye Moody")
                .build();

        final String createTaskRequest = mapper.writeValueAsString(task);

        mvc.perform(post("/task").content(createTaskRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        // TODO: write expected string or find solution to make this readable
                        .string(""));
    }

}