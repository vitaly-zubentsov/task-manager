package com.zubentsov.taskmanager.repos;

import java.util.Set;

import com.zubentsov.taskmanager.domain.Task;

public interface TaskRepo {

	Task createTask(Task task);

	Set<Task> getTasks();

	Task getTaskById(String taskId);

	Task updateTask(Task task);

	void deleteTask(String taskId);

}
