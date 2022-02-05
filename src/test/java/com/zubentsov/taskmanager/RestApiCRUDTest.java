package com.zubentsov.taskmanager;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestApiCRUDTest {
	
	@Autowired
    private MockMvc mockMvc;

    @Test
    public void getTasksTest() throws Exception {
        this.mockMvc.perform(get("/api/tasks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("all")));
            
    }
    
    @Test
    public void getTaskByIdTest() throws Exception {
        this.mockMvc.perform(get("/api/tasks/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("task by id: 1")));
    }
    
    @Test
    public void createTaskTest() throws Exception {
        this.mockMvc.perform(post("/api/tasks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("add new task")));
    }
    
    @Test
    public void updateTaskTest() throws Exception {
        this.mockMvc.perform(put("/api/tasks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("update task")));
    }
    
    @Test
    public void deleteTaskTest() throws Exception {
        this.mockMvc.perform(delete("/api/tasks/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("delete task")));
    }
	

}
