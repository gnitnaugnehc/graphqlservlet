package graphql;

import java.sql.SQLException;

import dao.TaskDAO;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import model.Task;

public class CreateTaskDataFetcher implements DataFetcher<Task> {

	private final TaskDAO taskDao;

	public CreateTaskDataFetcher(TaskDAO taskDao) {
		this.taskDao = taskDao;
	}

	@Override
	public Task get(DataFetchingEnvironment environment) throws SQLException {
		String name = environment.getArgument("name");
		String description = environment.getArgument("description");
		String status = environment.getArgument("status");

		Task task = new Task();
		task.setName(name);
		task.setDescription(description);
		task.setStatus(status);
		
		return taskDao.create(task);
	}
}
