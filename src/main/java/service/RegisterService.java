package service;

import java.sql.SQLException;

import model.User;
import util.AuthUtil;

public class RegisterService {

	private AuthUtil authUtil;

	public RegisterService() throws SQLException {
		this.authUtil = new AuthUtil();
	}

	public void validateInput(String username, String email, String password, String confirmPassword) {
		if (username == null || username.trim().isEmpty()) {
			throw new IllegalArgumentException("Username is required.");
		}
		if (email == null || email.trim().isEmpty()) {
			throw new IllegalArgumentException("Email is required.");
		}
		if (password == null || password.trim().isEmpty()) {
			throw new IllegalArgumentException("Password is required.");
		}
		if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
			throw new IllegalArgumentException("Confirm password is required.");
		}
		if (!password.equals(confirmPassword)) {
			throw new IllegalArgumentException("Password and confirm password do not match.");
		}
	}

	public boolean checkUsernameExists(String username) throws SQLException {
		return authUtil.checkUsernameExists(username);
	}

	public boolean checkEmailExists(String email) throws SQLException {
		return authUtil.checkEmailExists(email);
	}

	public long createUser(String username, String password, String email) throws SQLException {
		return authUtil.createUser(username, password, email);
	}

	public User registerUser(String username, String email, String password, String confirmPassword)
			throws SQLException, IllegalArgumentException {
		validateInput(username, email, password, confirmPassword);
		boolean usernameExists = checkUsernameExists(username);
		if (usernameExists) {
			throw new IllegalArgumentException("Username already exists.");
		}
		boolean emailExists = checkEmailExists(email);
		if (emailExists) {
			throw new IllegalArgumentException("Email already exists.");
		}
		long generatedId = createUser(username, password, email);
		User user = new User(generatedId, username, email);
		return user;
	}
}
