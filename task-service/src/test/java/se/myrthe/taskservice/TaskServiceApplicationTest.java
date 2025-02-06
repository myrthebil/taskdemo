package se.myrthe.taskservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import se.myrthe.taskservice.repository.TaskRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TaskServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        properties = {"spring.config.location=classpath:application-integration-test.yml"})
public class TaskServiceApplicationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TaskRepository repository;

    @Test
    public void testApplicationStartup() {
        // tests application startup without any errors
    }

    @Test
    public void givenUserBob_whenCreateTask_thenStatus200() throws Exception {
        mvc.perform(post("/task")
                        .content("{ \"taskOwner\": { \"username\": \"bob\" } }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}