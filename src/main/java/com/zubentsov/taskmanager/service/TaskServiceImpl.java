package com.zubentsov.taskmanager.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zubentsov.taskmanager.domain.Task;
import com.zubentsov.taskmanager.repos.TaskRepo;

@Service
public class TaskServiceImpl implements TaskService {

	private TaskRepo taskRepo;

	@Autowired
	public TaskServiceImpl(TaskRepo taskRepo) {
		this.taskRepo = taskRepo;
	}

	@Override
	public Task createTask(Task task) {

		task.setLastModificationDate(LocalDateTime.now());

		return taskRepo.createTask(task);
	}

	@Override
	public Set<Task> getTasks() {
		SortedSet<Task> sortedSet = new TreeSet<>(new OrderByModificationDateDESC());
		sortedSet.addAll(taskRepo.getTasks());
		
		return sortedSet;
	}

	@Override
	public Task getTaskById(String taskId) {

		return taskRepo.getTaskById(taskId);
	}

	@Override
	public Task updateTask(Task task) {

		Task taskFromRepo = taskRepo.getTaskById(task.getId());

		task.setLastModificationDate(LocalDateTime.now());

		// fill field, because cannot delete task attributes
		if (task.getName() == null) {
			task.setName(taskFromRepo.getName());
		} else if (task.getName().isEmpty()) {
			task.setName(taskFromRepo.getName());
		}
		if (task.getDescription() == null) {
			task.setDescription(taskFromRepo.getDescription());
		} else if (task.getDescription().isEmpty()) {
			task.setDescription(taskFromRepo.getDescription());
		}

		return taskRepo.updateTask(task);
	}

	@Override
	public void deleteTask(String itaskId) {

		taskRepo.deleteTask(itaskId);
	}
	
	
	class OrderByModificationDateDESC implements Comparator<Task>{
		 
	    @Override
	    public int compare(Task t1, Task t2) {
	        return -(t1.getLastModificationDate().compareTo(t2.getLastModificationDate()));
	    }
	}   

}


