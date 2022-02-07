package com.zubentsov.taskmanager.repos;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Repository;

import com.zubentsov.taskmanager.domain.Task;
import com.zubentsov.taskmanager.exceptions.NotFoundException;

@Repository
public class TaskRepoNativeJavaImp implements TaskRepo {

	private static int countOfTasks;
	private static Set<Task> tasks;

	static {
		countOfTasks = 1;
		tasks = new CopyOnWriteArraySet<>();
	}

	//TODO not thread safe
	@Override
	public Task createTask(Task task) {
		
		Task createdTask = new Task( 
				Integer.toString(countOfTasks), //set incremental identifier to enforce uniqueness
				task.getName(), 
				task.getDescription(), 
				task.getLastModificationDate());
		countOfTasks++;
		tasks.add(createdTask);
		return createdTask;
	}

	@Override
	public Set<Task> getTasks() {
		
		return tasks;
	}

	@Override
	public Task getTaskById(String taskId) {
		
		return findById(taskId);
	}

	
	@Override
	public Task updateTask(Task task) {
		Task oldTask = findById(task.getId());
		
		oldTask.setName(task.getName());
		oldTask.setDescription(task.getDescription());
		oldTask.setLastModificationDate(task.getLastModificationDate());
		
		return oldTask;
	}

	//TODO not thread safe
	@Override
	public void deleteTask(String taskId) {
		findById(taskId);
		tasks.remove(findById(taskId));		
	}
	
	private Task findById(String taskId) {
		return tasks.stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .orElseThrow(NotFoundException::new);
	}

}
