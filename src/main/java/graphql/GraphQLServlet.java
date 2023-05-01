package graphql;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dao.TaskDAO;
import dao.UserDAO;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@WebServlet("/graphql")
public class GraphQLServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private GraphQL graphQL;

	@Override
	public void init() throws ServletException {
		super.init();

		String schemaPath = "/WEB-INF/schema/schema.graphql";
		String realPath = getServletContext().getRealPath(schemaPath);
		File schemaFile = new File(realPath);
		String schemaString;
		try {
			schemaString = Files.readString(schemaFile.toPath());
		} catch (IOException e) {
			throw new ServletException("Failed to read schema file", e);
		}
		TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schemaString);

		try {
			UserDAO userDao = new UserDAO();
			TaskDAO taskDao = new TaskDAO();
			RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
					.type("Query",
							builder -> builder.dataFetcher("task", new TaskDataFetcher(taskDao)).dataFetcher("user",
									new UserDataFetcher(userDao)))
					.type("Mutation", builder -> builder.dataFetcher("createTask", new CreateTaskDataFetcher(taskDao))
							.dataFetcher("updateTask", new UpdateTaskDataFetcher(taskDao)))
					.build();

			SchemaGenerator schemaGenerator = new SchemaGenerator();
			graphQL = GraphQL.newGraphQL(schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring))
					.build();
		} catch (SQLException e) {
			throw new RuntimeException("Error creating DAO objects", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String query = request.getParameter("query");
		String variables = request.getParameter("variables");

		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, Object>>() {
		}.getType();
		Map<String, Object> variablesMap = gson.fromJson(variables, type);

		ExecutionInput executionInput = ExecutionInput.newExecutionInput().query(query).variables(variablesMap).build();
		ExecutionResult executionResult = graphQL.execute(executionInput);

		String jsonResponse = gson.toJson(executionResult.toSpecification());

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonResponse);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/view/graphql.jsp").forward(request, response);
	}

}
