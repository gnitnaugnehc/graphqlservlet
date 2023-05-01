<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Task Management</title>
<!-- include CSS dependencies here -->
<style>
/* add your custom styles here */
</style>
</head>
<body>
	<h1>Task Management</h1>

	<!-- create task form -->
	<form id="create-task-form" method="post">
		<h2>Create Task</h2>
		<label for="name">Name:</label> <input type="text" name="name"
			required> <br> <label for="description">Description:</label>
		<textarea name="description"></textarea>
		<br> <label for="status">Status:</label> <input type="text"
			name="status" required> <br>
		<button type="submit">Create</button>
	</form>

	<!-- task list -->
	<h2>Tasks</h2>
	<table>
		<thead>
			<tr>
				<th>Name</th>
				<th>Description</th>
				<th>Status</th>
				<th>Assigned Users</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="task" items="${tasks}">
				<tr>
					<td>${task.name}</td>
					<td>${task.description}</td>
					<td>${task.status}</td>
					<td><c:forEach var="user" items="${task.assignedUsers}">
              ${user.username}
            </c:forEach></td>
					<td>
						<button onclick="editTask(${task.id})">Edit</button>
						<button onclick="deleteTask(${task.id})">Delete</button>
						<button onclick="assignUsersToTask(${task.id})">Assign
							Users</button>
						<button onclick="unassignUsersFromTask(${task.id})">Unassign
							Users</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- assign users to task form -->
	<form id="assign-users-form" method="post">
		<h2>Assign Users to Task</h2>
		<input type="hidden" name="taskId"> <label for="userIds">User
			IDs:</label> <input type="text" name="userIds" required> <br>
		<button type="submit">Assign</button>
	</form>

	<!-- unassign users from task form -->
	<form id="unassign-users-form" method="post">
		<h2>Unassign Users from Task</h2>
		<input type="hidden" name="taskId"> <label for="userIds">User
			IDs:</label> <input type="text" name="userIds" required> <br>
		<button type="submit">Unassign</button>
	</form>

	<!-- assign tasks to user form -->
	<form id="assign-tasks-form" method="post">
		<h2>Assign Tasks to User</h2>
		<input type="hidden" name="userId"> <label for="taskIds">TaskIDs:</label>
		<input type="text" name="taskIds" required> <br>
		<button type="submit">Assign</button>

	</form>
	<!-- unassign tasks from user form -->
	<form id="unassign-tasks-form" method="post">
		<h2>Unassign Tasks from User</h2>
		<input type="hidden" name="userId"> <label for="taskIds">Task
			IDs:</label> <input type="text" name="taskIds" required> <br>
		<button type="submit">Unassign</button>
	</form>
	<!-- edit task form -->
	<form id="edit-task-form" method="post">
		<h2>Edit Task</h2>
		<input type="hidden" name="id"> <label for="name">Name:</label>
		<input type="text" name="name" required> <br> <label
			for="description">Description:</label>
		<textarea name="description"></textarea>
		<br> <label for="status">Status:</label> <input type="text"
			name="status" required> <br>
		<button type="submit">Save</button>
		<button type="button" onclick="cancelEdit()">Cancel</button>
	</form>
</body>
</html>