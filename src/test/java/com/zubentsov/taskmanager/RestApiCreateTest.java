package com.zubentsov.taskmanager;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.zubentsov.taskmanager.service.TaskService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class RestApiCreateTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private TaskService taskService;

	@Test
	@Order(1)
    public void createTaskTest() throws Exception {

        this.mockMvc.perform(post("/api/tasks")
        					.contentType(MediaType.APPLICATION_JSON)
        					.content("{ \"name\": \"first task\", \"description\": \"Description of first task\" } ") 
        					.accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        
        assertThat(taskService.getTasks().size(), equalTo(1));  
        }
	
	//The ID (in previous test id=1 should be used) is duplicated to check that the ID field is generated automatically and ignored
	@Test
	@Order(2)
    public void createTaskDuplicateIdTest() throws Exception {
		
        this.mockMvc.perform(post("/api/tasks")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content("{ \"id\": \"1\", \"name\": \"first task\", \"description\": \"Description of first task\" } ") 
        		.accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        
        assertThat(taskService.getTasks().size(), equalTo(2));  
        }
	
	//set a lastModificationDate field to check that the field is ignored when creating a task
	@Test
	@Order(3)
    public void createTaskSetLastModificationDateTest() throws Exception {
		
        this.mockMvc.perform(post("/api/tasks")
        					.contentType(MediaType.APPLICATION_JSON)
        					.content("{ \"name\": \"first task\", \"description\": \"Description of first task\", \"lastModificationDate\":\""+ LocalDateTime.now().toString()+ "\" } ") 
        					.accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        }

	
	@Test
	@Order(4)
    public void createTaskFailedTest() throws Exception {

        this.mockMvc.perform(post("/api/tasks"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
        }
   

}
