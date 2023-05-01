package graphql;

import java.sql.SQLException;

import dao.UserDAO;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import model.User;

public class UserDataFetcher implements DataFetcher<User> {

	private final UserDAO userDao;

	public UserDataFetcher(UserDAO userDao) {
		this.userDao = userDao;
	}

	@Override
	public User get(DataFetchingEnvironment environment) throws SQLException {
		long id = environment.getArgument("id");
		return userDao.findById(id);
	}
}
