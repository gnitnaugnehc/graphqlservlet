<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<%@ include file="head.jsp"%>
<%@ include file="nav.jsp"%>

<body>

	<div class="container mb-4">
		<h1>Update Profile</h1>
		<form method="post" action="${rootDirectory}/profile"
			enctype="multipart/form-data">
			<div class="form-group">
				<label for="profilePicture">Profile Picture:</label> <input
					type="file" class="form-control-file" id="profilePicture"
					name="profilePicture" onchange="previewImage(event)">
			</div>
			<div class="form-group">
				<img
					src="${rootDirectory}/profile_picture?id=${user.uuid}"
					style="border-radius: 50%; max-width: 100%; height: auto;">
			</div>
			<button type="submit" class="btn btn-primary" style="margin-top: 20px">Update</button>
		</form>
	</div>

	<div class="container">
		<h1>Delete Account</h1>
		<form method="POST" action="${rootDirectory}/deleteuser"
			onsubmit="return confirm('Are you sure you want to delete your account? This action cannot be undone.');">
			<button type="submit" class="btn btn-danger">Delete Account</button>
		</form>
	</div>
	
	<script>
	function previewImage(event) {
		var reader = new FileReader();
		reader.onload = function() {
			var output = document.getElementById('preview');
			output.src = reader.result;
		}
		reader.readAsDataURL(event.target.files[0]);
	}
	</script>
	
</body>
<%@ include file="footer.jsp"%>
</html>
