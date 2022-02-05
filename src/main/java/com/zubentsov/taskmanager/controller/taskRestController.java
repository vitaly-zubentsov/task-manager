package com.zubentsov.taskmanager.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class taskRestController {

	@GetMapping("/tasks")
	public String getTasks() {
		return "all tasks";
	}

	@GetMapping("tasks/{taskId}")
	public String getTaskById(@PathVariable String taskId) {
		return "task by id: " + taskId;
	}

	@PostMapping("/tasks")
	public String createTask() {
		return "add new task";
	}

	@PutMapping("/tasks")
	public String updateTask() {
		return "update task";
	}

	@DeleteMapping("/tasks/{taskId}")
	public String deleteTask() {
		return "delete task";
	}

}
