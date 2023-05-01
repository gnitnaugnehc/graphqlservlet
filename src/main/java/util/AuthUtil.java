package util;

import java.sql.SQLException;
import java.util.UUID;

import dao.UserDAO;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import model.User;

public class AuthUtil {

	private static final int HASH_ITERATIONS = 10;
	private static final int HASH_MEMORY = 65536;
	private static final int HASH_PARALLELISM = 1;

	private UserDAO userDao;
	private Argon2 argon2;

	public AuthUtil() throws SQLException {
		this.userDao = new UserDAO();
		this.argon2 = Argon2Factory.create();
	}

	public User authenticateUserByEmail(String email, String password) throws SQLException {
		User user = userDao.findByEmail(email);

		if (user != null) {
			String hashedPassword = user.getPassword();

			if (argon2.verify(hashedPassword, password.toCharArray())) {
				argon2.wipeArray(password.toCharArray());
				user.setPassword(null);
				return user;
			}
		}

		return null;
	}

	public User authenticateUserByUsername(String username, String password) throws SQLException {
		User user = userDao.findByUsername(username);

		if (user != null) {
			String hashedPassword = user.getPassword();

			if (argon2.verify(hashedPassword, password.toCharArray())) {
				argon2.wipeArray(password.toCharArray());
				user.setPassword(null);
				return user;
			}
		}

		return null;
	}

	public boolean checkUsernameExists(String username) throws SQLException {
		User user = userDao.findByUsername(username);
		return user != null;
	}

	public boolean checkEmailExists(String email) throws SQLException {
		User user = userDao.findByEmail(email);
		return user != null;
	}

	public long createUser(String username, String password, String email) throws SQLException {
		String hashedPassword = hashPassword(password);
		UUID uuid = UUID.randomUUID();
		return userDao.create(new User(username, hashedPassword, email, uuid.toString()));
	}

	public String hashPassword(String password) {
		return argon2.hash(HASH_ITERATIONS, HASH_MEMORY, HASH_PARALLELISM, password.toCharArray());
	}
}
