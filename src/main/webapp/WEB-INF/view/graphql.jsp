<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GraphQL Servlet Test</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(function() {
  $("#execute").click(function() {
    var query = $("#query").val();
    var variables = $("#variables").val();
    $.post("graphql", { query: query, variables: variables }, function(data) {
      $("#result").text(JSON.stringify(data));
    });
  });
});
</script>
</head>
<body>
  <h1>GraphQL Servlet Test</h1>
  <p>Enter your GraphQL query and variables (if any) below:</p>
  <textarea id="query" rows="5" cols="80">
    
		mutation CreateTask($name: String!, $description: String, $status: String) {
		  createTask(name: $name, description: $description, status: $status) {
		    id
		    name
		    description
		    status
		  }
		}
    
  </textarea>
  <br>
  <textarea id="variables" rows="5" cols="80">
    {
  "name": "New Task",
  "description": "This is a new task",
  "status": "in progress"
	}
  </textarea>
  <br>
  <button id="execute">Create</button>
  <br>
  <pre id="result"></pre>

  <textarea rows="12" cols="160">
    
	mutation UpdateTask($id: ID!, $name: String, $description: String, $status: String) {
	  updateTask(id: $id, name: $name, description: $description, status: $status) {
	    id
	    name
	    description
	    status
	  }
	}
    
  </textarea>
  <br>
  <textarea rows="6" cols="80">
{
  "id": "1",
  "name": "Updated Task Name",
  "description": "This is an updated task",
  "status": "completed"
}

  </textarea>

</body>
</html>
