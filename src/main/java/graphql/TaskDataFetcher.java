package graphql;

import java.sql.SQLException;

import dao.TaskDAO;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import model.Task;

public class TaskDataFetcher implements DataFetcher<Task> {

	private final TaskDAO taskDao;

	public TaskDataFetcher(TaskDAO taskDao) {
		this.taskDao = taskDao;
	}

	@Override
	public Task get(DataFetchingEnvironment environment) throws SQLException {
		long id = Long.parseLong(environment.getArgument("id"));
		return taskDao.findById(id);
	}
}
