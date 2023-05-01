package model;

import java.sql.Timestamp;

public class Task {

	private long id;
	private String name;
	private String description;
	private String status;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	public Task(long id, String name, String description, String status, Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Task() {
		// TODO Auto-generated constructor stub
	}

	public Task(long taskId) {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

}
