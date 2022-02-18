package com.zubentsov.taskmanager.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.zubentsov.taskmanager.domain.Task;
import com.zubentsov.taskmanager.repos.TaskRepoNativeJavaImp;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TaskServiceImplTest {

	@Autowired
	TaskServiceImpl service;

	@MockBean
	TaskRepoNativeJavaImp repo;

	@Test
	public void updateTaskTest() {

		Task inputTask = new Task("1", "first task", "Description of first task", LocalDateTime.now());

		service.updateTask(inputTask);

		Mockito.verify(repo,Mockito.times(1)).getTaskById(inputTask.getId());

	}
	
	@Test 
	public void getTasksOrderTest() {
		
		Task task1 = new Task("1", "first task", "Description of first task", LocalDateTime.now());
		Task task2 = new Task("2", "second task", "Description of second task", LocalDateTime.now());
		Task task3 = new Task("3", "third task", "Description of third task", LocalDateTime.now());
		
		Set<Task> tasks = new HashSet<>();
		tasks.add(task1);
		tasks.add(task2);
		tasks.add(task3);
				
		Mockito.doReturn(tasks).when(repo).getTasks();
		
		Set<Task> tasksFromService = service.getTasks();
	
		MatcherAssert.assertThat( new ArrayList<>(tasksFromService), Matchers.contains(task3,task2,task1));
		
			
	}

}
