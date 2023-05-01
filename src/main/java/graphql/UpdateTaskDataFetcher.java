package graphql;

import java.sql.SQLException;

import dao.TaskDAO;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import model.Task;

public class UpdateTaskDataFetcher implements DataFetcher<Task> {

    private final TaskDAO taskDao;

    public UpdateTaskDataFetcher(TaskDAO taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public Task get(DataFetchingEnvironment environment) throws SQLException {
        long id = Long.parseLong(environment.getArgument("id"));
        String name = environment.getArgument("name");
        String description = environment.getArgument("description");
        String status = environment.getArgument("status");
        
        Task task = new Task();
        task.setId(id);
        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
        
        return taskDao.update(task);
    }
}
