package init;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import util.DatabaseUtil;

@WebListener
public class Initializer implements ServletContextListener {

	private static final String CREATE_USERS_TABLE_QUERY = "CREATE TABLE users (" 
			+ "id BIGINT PRIMARY KEY IDENTITY(1,1),"
		    + "username VARCHAR(255) NOT NULL," 
		    + "password VARCHAR(255) NOT NULL," 
		    + "email VARCHAR(255) NOT NULL,"
		    + "uuid VARCHAR(255) NOT NULL UNIQUE,"
		    + "active BIT NOT NULL,"
		    + "created_at DATETIME NOT NULL," 
		    + "last_login DATETIME NOT NULL"
		    + ");";

	private static final String CREATE_TASK_TABLE_QUERY = "CREATE TABLE task (" 
		    + "id BIGINT PRIMARY KEY IDENTITY(1,1),"
		    + "name VARCHAR(255) NOT NULL," 
		    + "description TEXT NOT NULL," 
		    + "status VARCHAR(255) NOT NULL,"
		    + "created_at DATETIME NOT NULL," 
		    + "updated_at DATETIME NOT NULL," 
			+ ");";
	
	private static final String CREATE_USER_TASK_TABLE_QUERY = "CREATE TABLE user_task (" 
	        + "id BIGINT PRIMARY KEY IDENTITY(1,1),"
	        + "user_id BIGINT NOT NULL,"
	        + "task_id BIGINT NOT NULL,"
	        + "created_at DATETIME NOT NULL," 
	        + "updated_at DATETIME NOT NULL," 
	        + "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE"
	        + ");";
	
	private static final String CREATE_PROFILE_PICTURE_TABLE_QUERY = "CREATE TABLE profile_picture (" 
		    + "id BIGINT PRIMARY KEY IDENTITY(1,1),"
		    + "uuid VARCHAR(255) NOT NULL,"
		    + "picture VARBINARY(MAX) NOT NULL,"
		    + "uploaded_at DATETIME NOT NULL," 
		    + "FOREIGN KEY (uuid) REFERENCES users(uuid) ON DELETE CASCADE"
		    + ");";
	
	private void createTableIfNotExists(String tableName, String createQuery) throws SQLException {
	    try (Connection conn = DatabaseUtil.getConnection()) {
	        DatabaseMetaData metaData = conn.getMetaData();
	        ResultSet rs = metaData.getTables(null, null, tableName.toUpperCase(), null);
	        if (!rs.next()) {
	            try (Statement stmt = conn.createStatement()) {
	                stmt.executeUpdate(createQuery);
	            }
	        }
	    }
	}
	
	public void contextInitialized(ServletContextEvent sce) {		
	    ServletContext context = sce.getServletContext();
	    String contextPath = context.getContextPath();
	    context.setAttribute("rootDirectory", contextPath);
	    try {
	        createTableIfNotExists("USERS", CREATE_USERS_TABLE_QUERY);
	        createTableIfNotExists("TASK", CREATE_TASK_TABLE_QUERY);
	        createTableIfNotExists("USER_TASK", CREATE_USER_TASK_TABLE_QUERY);
	        createTableIfNotExists("PROFILE_PICTURE", CREATE_PROFILE_PICTURE_TABLE_QUERY);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Failed to create tables", e);
	    }
	}
}
