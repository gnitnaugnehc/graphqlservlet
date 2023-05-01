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

public class UserDAO {

	private Connection connection;

	public UserDAO() throws SQLException {
		connection = DatabaseUtil.getConnection();
	}

	public List<User> findAll() throws SQLException {
		List<User> users = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				User user = createUserFromResultSet(rs);
				users.add(user);
			}
		}
		return users;
	}

	public User findByUsername(String username) throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE username = ?")) {
			ps.setString(1, username);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return createUserFromResultSet(rs);
				} else {
					return null;
				}
			}
		}
	}

	public User findByEmail(String email) throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE email = ?")) {
			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return createUserFromResultSet(rs);
				} else {
					return null;
				}
			}
		}
	}

	public User findById(long id) throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE id = ?")) {
			ps.setLong(1, id);
			ResultSet result = ps.executeQuery();
			if (result.next()) {
				return createUserFromResultSet(result);
			}
			return null;
		}
	}

	public long create(User user) throws SQLException {
		long generatedId = -1;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO users (username, password, email, uuid, active, created_at, last_login) VALUES (?, ?, ?, ?, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)",
				Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getUuid());
			ps.executeUpdate();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
				generatedId = generatedKeys.getLong(1);
			}
		}
		return generatedId;
	}

	public void update(User user) throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE users SET username = ?, password = ?, email = ?, uuid = ?, active = ?, created_at = ?, last_login = ? WHERE id = ?")) {
			setUserParameters(ps, user);
			ps.setLong(8, user.getId());
			ps.executeUpdate();
		}
	}

	public void delete(User user) throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
			ps.setLong(1, user.getId());
			ps.executeUpdate();
		}
	}

	public List<User> findByAssignedTask(Task task) throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement(
				"SELECT u.* FROM users u JOIN user_task ut ON u.id = ut.user_id WHERE ut.task_id = ?")) {
			ps.setLong(1, task.getId());
			ResultSet result = ps.executeQuery();
			List<User> users = new ArrayList<>();
			while (result.next()) {
				users.add(createUserFromResultSet(result));
			}
			return users;
		}
	}

	public void assignTasksToUser(long userId, List<Task> tasks) throws SQLException {
		connection.setAutoCommit(false);
		try (PreparedStatement ps = connection
				.prepareStatement("INSERT INTO user_task (user_id, task_id) VALUES (?, ?)")) {
			for (Task task : tasks) {
				ps.setLong(1, userId);
				ps.setLong(2, task.getId());
				ps.addBatch();
			}
			ps.executeBatch();
			connection.commit();
		}
	}

	public void unassignTasksFromUser(long userId, List<Task> tasks) throws SQLException {
		connection.setAutoCommit(false);
		try (PreparedStatement ps = connection
				.prepareStatement("DELETE FROM user_task WHERE user_id = ? AND task_id = ?")) {
			for (Task task : tasks) {
				ps.setLong(1, userId);
				ps.setLong(2, task.getId());
				ps.addBatch();
			}
			ps.executeBatch();
			connection.commit();
		}
	}

	private User createUserFromResultSet(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getLong("id"));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setEmail(rs.getString("email"));
		user.setUuid(rs.getString("uuid"));
		user.setActive(rs.getBoolean("active"));
		user.setCreatedAt(rs.getTimestamp("created_at"));
		user.setLastLogin(rs.getTimestamp("last_login"));
		return user;
	}

	private void setUserParameters(PreparedStatement ps, User user) throws SQLException {
		ps.setString(1, user.getUsername());
		ps.setString(2, user.getPassword());
		ps.setString(3, user.getEmail());
		ps.setString(4, user.getUuid());
		ps.setBoolean(5, user.isActive());
		ps.setTimestamp(6, user.getCreatedAt());
		ps.setTimestamp(7, user.getLastLogin());
	}
}
