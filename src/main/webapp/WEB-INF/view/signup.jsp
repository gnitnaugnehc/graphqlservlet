<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<%@ include file="head.jsp"%>

<nav class="navbar navbar-expand-lg bg-body-tertiary">
	<div class="container-fluid">
		<a class="navbar-brand" href="${rootDirectory}/home">Home</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav ms-auto mb-2 mb-lg-0">
				<li class="nav-item"><a class="nav-link" href="${rootDirectory}/signin">Sign In</a></li>
			</ul>
		</div>
	</div>
</nav>

<body>
	<main class="container">
        <h1 class="mb-4">Create an Account</h1>
        <form method="post" action="${rootDirectory}/signup">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" class="form-control" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" name="email" required>
            </div>
			<div class="form-group">
			    <label for="password">Password:</label>
			    <input type="password" class="form-control" id="password" name="password" required>
			</div>
			<div class="form-group">
			    <label for="confirmPassword">Confirm Password:</label>
			    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
			</div>
			<c:if test="${not empty errorMessage}">
				<p class="text-danger">${errorMessage}</p>
			</c:if>
			<button type="submit" class="btn btn-primary">Submit</button>
		</form>
	</main>
</body>

<%@ include file="footer.jsp"%>

</html>
