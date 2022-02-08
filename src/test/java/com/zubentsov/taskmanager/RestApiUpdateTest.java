package com.zubentsov.taskmanager;


import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.zubentsov.taskmanager.domain.Task;
import com.zubentsov.taskmanager.service.TaskService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class RestApiUpdateTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private TaskService taskService;
	
	private Task task1;
	private Task task2;
	private Task task3;
	private Task task4;
	private Task task5;
	
	//TODO junit executes this code for each test method. At the moment,
	//the correctness of the data is carried out by setting the sequence of tests.
	//Implement @BeforeAll (method must be static), resolving initialization error for TaskService
		
	//set test data
	@BeforeAll
	public void loadData() {
		
		task1 = new Task("1", "first task", "Description of first task",LocalDateTime.now());
		task2 = new Task("2", "second task", "Description of second task", LocalDateTime.now());
		task3 = new Task("3", "third task", "Description of third task", LocalDateTime.now());
		task4 = new Task("4", "four task", "Description of four task", LocalDateTime.now());
		task5 = new Task("5", "five task", "Description of five task", LocalDateTime.now());
		
		taskService.createTask(task1);
		taskService.createTask(task2); 
		taskService.createTask(task3);
		taskService.createTask(task4); 
		taskService.createTask(task5);
		
	}

	@Test
    public void updateTaskTest() throws Exception {
		
		
		
		this.mockMvc.perform(put("/api/tasks")
    				.contentType(MediaType.APPLICATION_JSON)
    				.content("{ \"id\": \"1\",\"name\": \"change first task\", \"description\": \"Change Description of first task\", \"lastModificationDate\":\""+ LocalDateTime.now().toString()+ "\" } ") 
    				.accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("change first task"))
                .andExpect(jsonPath("$.description").value("Change Description of first task"))
                .andExpect(content().string(containsString("lastModificationDate")));
    }
	
	@Test
    public void updateTaskEmptyNameTest() throws Exception {
		
		this.mockMvc.perform(put("/api/tasks")
    				.contentType(MediaType.APPLICATION_JSON)
    				.content("{ \"id\": \"2\",\"name\": \"\", \"description\": \"Change Description of second task\", \"lastModificationDate\":\""+ LocalDateTime.now().toString()+ "\" } ") 
    				.accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("second task"))
                .andExpect(jsonPath("$.description").value("Change Description of second task"))
                .andExpect(content().string(containsString("lastModificationDate")));
    }
	
	@Test
    public void updateTaskNullNameTest() throws Exception {
		
		this.mockMvc.perform(put("/api/tasks")
    				.contentType(MediaType.APPLICATION_JSON)
    				.content("{ \"id\": \"3\", \"description\": \"Change Description of third task\", \"lastModificationDate\":\""+ LocalDateTime.now().toString()+ "\" } ") 
    				.accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("third task"))
                .andExpect(jsonPath("$.description").value("Change Description of third task"))
                .andExpect(content().string(containsString("lastModificationDate")));
    }
	
	@Test
    public void updateTaskEmptyDescriptionTest() throws Exception {
		
		this.mockMvc.perform(put("/api/tasks")
    				.contentType(MediaType.APPLICATION_JSON)
    				.content("{ \"id\": \"4\",\"name\": \"change four task\", \"description\": \"\", \"lastModificationDate\":\""+ LocalDateTime.now().toString()+ "\" } ") 
    				.accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("change four task"))
                .andExpect(jsonPath("$.description").value("Description of four task"))
                .andExpect(content().string(containsString("lastModificationDate")));
    }
	
	@Test
    public void updateTaskNullDescriptionTest() throws Exception {
		
		this.mockMvc.perform(put("/api/tasks")
    				.contentType(MediaType.APPLICATION_JSON)
    				.content("{ \"id\": \"5\",\"name\": \"change five task\", \"lastModificationDate\":\""+ LocalDateTime.now().toString()+ "\" } ") 
    				.accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("change five task"))
                .andExpect(jsonPath("$.description").value("Description of five task"))
                .andExpect(content().string(containsString("lastModificationDate")));
    }
	

	@Test
    public void updateTaskFailTest() throws Exception {
		
		this.mockMvc.perform(put("/api/tasks")
    				.contentType(MediaType.APPLICATION_JSON)
    				.content("{ \"id\": \"5295192\",\"name\": \"change first task\", \"description\": \"Change Description of first task\", \"lastModificationDate\":\""+ LocalDateTime.now().toString()+ "\" } ") 
    				.accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());            
    }


	

}
