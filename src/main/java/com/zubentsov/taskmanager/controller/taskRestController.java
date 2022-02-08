package com.zubentsov.taskmanager.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zubentsov.taskmanager.domain.Task;
import com.zubentsov.taskmanager.service.TaskService;

@RestController
@RequestMapping("/api")
public class taskRestController {

	@Autowired
	TaskService taskService;

	@PostMapping("/tasks")
	public void createTask(@RequestBody Task task) {
		taskService.createTask(task);
	}

	@GetMapping("/tasks")
	public Set<Task> getTasks() {
		return taskService.getTasks();
	}

	@GetMapping("/tasks/{taskId}")
	public Task getTaskById(@PathVariable String taskId) {
		return taskService.getTaskById(taskId);
	}

	@PutMapping("/tasks")
	public Task updateTask(@RequestBody Task task) {
		return taskService.updateTask(task);
	}

	@DeleteMapping("/tasks/{taskId}")
	public void deleteTask(@PathVariable String taskId) {
		taskService.deleteTask(taskId);
	}

}
