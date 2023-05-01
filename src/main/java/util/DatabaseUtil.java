package util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseUtil {

	private static final String DATASOURCE_NAME = "java:comp/env/jdbc/myDB";

	public static Connection getConnection() throws SQLException {
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup(DATASOURCE_NAME);
			return dataSource.getConnection();
		} catch (NamingException e) {
			throw new SQLException("Failed to lookup DataSource: " + DATASOURCE_NAME, e);
		}
	}
}
