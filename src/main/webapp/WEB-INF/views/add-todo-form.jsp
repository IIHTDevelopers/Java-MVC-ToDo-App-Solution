<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Todo Management</title>
	</head>
	<body>
	<div>
		<h2>Todos</h2>
		<h3>Add Todo</h3>
		<div>
			<div>
				<hr>
				<form:form action="saveTodo" modelAttribute="todo" method="POST">
					<div>
						<label for="task">Task: </label>
						<form:input path="task" name="task" />
						<form:errors path="task"/>
					</div>
                    <div>
						<label for="description">Task Description: </label>
						<form:textarea path="description" name="description" />
						<form:errors path="description"/>
					</div>
                    <div>
						<label for="taskDate">Task Date: </label>
						<form:input type="date" path="taskDate" name="taskDate" />
						<form:errors path="taskDate"/>
					</div>
					<input type="submit" value="Save"/>
				</form:form>
			</div>
		</div>
		<a href="${pageContext.request.contextPath}/todo/list">Back to List</a>
	</div>
	</body>
</html>