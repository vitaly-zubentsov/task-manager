package com.zubentsov.taskmanager.repos;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import com.zubentsov.taskmanager.domain.Task;
import com.zubentsov.taskmanager.exceptions.NotFoundException;

@Repository
public class TaskRepoNativeJavaImp implements TaskRepo {
	
	private static AtomicInteger countOfTasks;
	
	private static Set<Task> tasks;

	static {
		countOfTasks = new AtomicInteger(1);
		tasks = new CopyOnWriteArraySet<>();
	}

	@Override
	public Task createTask(Task task) {
		
		Task createdTask = new Task( 
				Integer.toString(countOfTasks.getAndIncrement()), //set incremental identifier to enforce uniqueness
				task.getName(), 
				task.getDescription(), 
				task.getLastModificationDate()); 
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
		//get block on task in case another thread try update
		synchronized(oldTask) {
		oldTask.setName(task.getName());
		oldTask.setDescription(task.getDescription());
		oldTask.setLastModificationDate(task.getLastModificationDate());
		}
		return oldTask;
	}


	@Override
	public void deleteTask(String taskId) {
		Task taskInRepo = findById(taskId);
		//get clock in case task is changing now
		synchronized(taskInRepo) {
		tasks.remove(findById(taskId));		
		}
	}
	
	private Task findById(String taskId) {
		return tasks.stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .orElseThrow(NotFoundException::new);
	}

}
