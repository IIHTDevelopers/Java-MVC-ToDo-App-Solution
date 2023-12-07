<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Todo Management</title>
	</head>
	<body>
	<div>
		<h2><a href="${pageContext.request.contextPath}/todo/list">Todo List</a></h2>

		<input type="button" value="Add Todo" onclick="window.location.href='addTodoForm'; return false;"/>
        <form:form action="search" method="POST">
			<div>
			    <div>
			      <div><i aria-hidden="true"></i></div>
			    </div>
			    <input type="text" placeholder="Search By Todo Name" name="theSearchName">
			    <input type="submit" value="Search"/>
			</div>
        </form:form>
		<table border="1">
			<tr>
				<th>#</th>
				<th>Task</th>
				<th>Description</th>
				<th>Date</th>
				<th>Time</th>
				<th colspan="2">Action</th>
			</tr>
			<c:set var="index" value="0" />
			<c:forEach var="todo" items="${todos }">
				<c:url var="updateLink" value="/todo/updateTodoForm">
					<c:param name="todoId" value="${todo.id}"></c:param>
				</c:url>

				<c:url var="deleteLink" value="/todo/delete">
					<c:param name="todoId" value="${todo.id}"></c:param>
				</c:url>

				<c:set var="index" value="${index + 1}" />
				<tr>
					<td>${index}</td>
					<td>${todo.task}</td>
					<td>${todo.description}</td>
					<td>${todo.taskDate}</td>
					<td>${todo.taskTime}</td>
					<td>
						<!-- display the update link -->
						<a href="${updateLink}">Update</a>
					</td>
					<td>
						<a href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to delete this todo?'))) return false">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	</body>
</html>