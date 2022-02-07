package com.zubentsov.taskmanager.domain;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class Task {

	private String id;

	private String name;

	private String description;

	private LocalDateTime lastModificationDate;

	public Task() {

	}

	public Task(String id, String name, String description, LocalDateTime lastModificationDate) {

		this.id = id;
		this.name = name;
		this.description = description;
		this.lastModificationDate = lastModificationDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(LocalDateTime lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}
	
	

	
	
}
