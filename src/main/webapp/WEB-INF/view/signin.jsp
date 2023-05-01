<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<%@ include file="head.jsp"%>

<nav class="navbar navbar-expand-lg bg-body-tertiary">
	<div class="container-fluid">
		<a class="navbar-brand" href="${rootDirectory}/home">Home</a>
	</div>
</nav>

<body>
	<div id="root">
	<main class="container">
        <h1 class="mb-4">Sign In</h1>
        <form method="post" action="${rootDirectory}/signin">
            <div class="form-group">
                <label for="usernameOrEmail">Username or Email:</label>
                <input type="text" class="form-control" id="usernameOrEmail" name="usernameOrEmail" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
				<c:if test="${not empty errorMessage}">
					<p class="text-danger">${errorMessage}</p>
				</c:if>
			<button type="submit" class="btn btn-primary">Sign In</button>
			<a href="${rootDirectory}/signup" class="btn-primary-inverted">Create an Account</a>
		</form>
	</main>
	</div>
</body>

<%@ include file="footer.jsp"%>

</html>
