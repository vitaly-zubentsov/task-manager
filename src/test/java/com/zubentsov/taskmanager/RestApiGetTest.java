package com.zubentsov.taskmanager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zubentsov.taskmanager.domain.Task;
import com.zubentsov.taskmanager.service.TaskService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class RestApiGetTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private TaskService taskService;
	
	private Task task1;
	private Task task2;
	private Task task3;
	
	
	//TODO junit executes this code for each test method. At the moment,
	//the correctness of the data is carried out by setting the sequence of tests.
	//Implement @BeforeAll (method must be static), resolving initialization error for TaskService
	
	//set test data
	@PostConstruct
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
    public void getTaskByIdTest() throws Exception {
    
        this.mockMvc.perform(get("/api/tasks/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(task1)));
    }
	
	@Test
	@Order(2)
	public void getTasksTest() throws Exception {

		this.mockMvc.perform(get("/api/tasks"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(mapper.writeValueAsString(taskService.getTasks())));
	}
	
 
	@Test
	@Order(3)
	public void getTasksNotFoundFailTest() throws Exception {

		this.mockMvc.perform(get("/api/tasks/1235"))
	           .andDo(print())
	           .andExpect(status().is4xxClientError());
	}
	
	@Test
	@Order(4)
	public void getTasksWrongIDFailTest() throws Exception {

		this.mockMvc.perform(get("/api/tasks/s1sdf235"))
	           .andDo(print())
	           .andExpect(status().is4xxClientError());
	}
}
