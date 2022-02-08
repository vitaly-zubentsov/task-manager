package com.zubentsov.taskmanager.service;

import java.time.LocalDateTime;

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

}
