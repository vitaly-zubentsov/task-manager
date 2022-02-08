package com.zubentsov.taskmanager;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.zubentsov.taskmanager.domain.Task;
import com.zubentsov.taskmanager.service.TaskService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class RestApiDeleteTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private TaskService taskService;
	
	private Task task1;
	private Task task2;
	private Task task3;
	
	
	@BeforeAll
	public void loadData() {
		
		task1 = new Task("1", "first task", "Description of first task",LocalDateTime.now());
		task2 = new Task("2", "second task", "Description of second task", LocalDateTime.now());
		task3 = new Task("3", "third task", "Description of third task", LocalDateTime.now());
		
		taskService.createTask(task1);
		taskService.createTask(task2); 
		taskService.createTask(task3); 
		
		//update task time, because it change while create
		task1.setLastModificationDate(taskService.getTaskById("1").getLastModificationDate());
		task2.setLastModificationDate(taskService.getTaskById("2").getLastModificationDate());
		task3.setLastModificationDate(taskService.getTaskById("3").getLastModificationDate());		
	}

	@Test
	@Order(1)
    public void createTaskTest() throws Exception {

        this.mockMvc.perform(delete("/api/tasks/2"))
                .andDo(print())
                .andExpect(status().isOk());
        
        assertThat(taskService.getTasks().size(), equalTo(2));  
        }

}
