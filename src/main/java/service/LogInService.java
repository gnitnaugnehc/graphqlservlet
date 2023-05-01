package service;

import java.sql.SQLException;

import model.User;
import util.AuthUtil;

public class LogInService {

	private AuthUtil authUtil;

	public LogInService() throws SQLException {
		this.authUtil = new AuthUtil();
	}

	public User authenticate(String usernameOrEmail, String password) throws SQLException {
		if (usernameOrEmail == null || usernameOrEmail.trim().isEmpty()) {
			throw new IllegalArgumentException("Username or email is required.");
		}

		if (password == null || password.trim().isEmpty()) {
			throw new IllegalArgumentException("Password is required.");
		}

		User user = null;
		if (usernameOrEmail.contains("@")) {
			user = authUtil.authenticateUserByEmail(usernameOrEmail, password);
		} else {
			user = authUtil.authenticateUserByUsername(usernameOrEmail, password);
		}

		if (user == null) {
			throw new IllegalArgumentException("Invalid username/email or password.");
		}

		return user;
	}
}
