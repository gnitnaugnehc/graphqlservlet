package model;

import java.sql.Timestamp;

public class User {

	private long id;
	private String username;
	private String password;
	private String email;
	private String uuid;
	private boolean active;
	private Timestamp createdAt;
	private Timestamp lastLogin;

	public User(long id, String username, String password, String email, String uuid, boolean active,
			Timestamp createdAt, Timestamp lastLogin) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.uuid = uuid;
		this.active = active;
		this.createdAt = createdAt;
		this.lastLogin = lastLogin;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(long userId) {
		// TODO Auto-generated constructor stub
	}

	public User(String username, String password, String email, String uuid) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.uuid = uuid;
	}

	public User(long generatedId, String username, String email) {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

}
