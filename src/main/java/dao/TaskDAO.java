package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Task;
import model.User;
import util.DatabaseUtil;

public class TaskDAO {

	private Connection connection;

	public TaskDAO() throws SQLException {
		connection = DatabaseUtil.getConnection();
	}

	public Task findById(long id) throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM task WHERE id = ?")) {
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return createTaskFromResultSet(rs);
			} else {
				return null;
			}
		}
	}

	public Task create(Task task) throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO task (name, description, status, created_at, updated_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)")) {
			ps.setString(1, task.getName());
			ps.setString(2, task.getDescription());
			ps.setString(3, task.getStatus());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				task.setId(rs.getLong(1));
			}
		}
		return task;
	}

	public Task update(Task task) throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE task SET name = ?, description = ?, status = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?",
				Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, task.getName());
			ps.setString(2, task.getDescription());
			ps.setString(3, task.getStatus());
			ps.setLong(4, task.getId());
			ps.executeUpdate();
		}
		return task;
	}

	public List<Task> findByAssignedUser(User user) throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement(
				"SELECT * FROM tasks t JOIN user_task ut ON t.id = ut.task_id WHERE ut.user_id = ?")) {
			ps.setLong(1, user.getId());
			ResultSet rs = ps.executeQuery();
			List<Task> tasks = new ArrayList<>();
			while (rs.next()) {
				tasks.add(createTaskFromResultSet(rs));
			}
			return tasks;
		}
	}

	public void assignUsersToTask(long taskId, List<User> users) throws SQLException {
		connection.setAutoCommit(false);
		try (PreparedStatement ps = connection
				.prepareStatement("INSERT INTO user_task (user_id, task_id) VALUES (?, ?)")) {
			for (User user : users) {
				ps.setLong(1, user.getId());
				ps.setLong(2, taskId);
				ps.addBatch();
			}
			ps.executeBatch();
			connection.commit();
		}
	}

	public void unassignUsersFromTask(long taskId, List<User> users) throws SQLException {
		connection.setAutoCommit(false);
		try (PreparedStatement ps = connection
				.prepareStatement("DELETE FROM user_task WHERE user_id = ? AND task_id = ?")) {
			for (User user : users) {
				ps.setLong(1, user.getId());
				ps.setLong(2, taskId);
				ps.addBatch();
			}
			ps.executeBatch();
			connection.commit();
		}
	}

	private Task createTaskFromResultSet(ResultSet rs) throws SQLException {
		Task task = new Task();
		task.setId(rs.getLong("id"));
		task.setName(rs.getString("name"));
		task.setDescription(rs.getString("description"));
		task.setStatus(rs.getString("status"));
		task.setCreatedAt(rs.getTimestamp("created_at"));
		task.setUpdatedAt(rs.getTimestamp("updated_at"));
		return task;
	}

}
