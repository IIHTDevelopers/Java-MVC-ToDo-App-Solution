<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.yaksha.training.todo.entity.TaskStatus" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Todo Management</title>
	</head>
	<body>
	<div>
		<h2>Todo List</h2>

		<input type="button" value="Add Todo" onclick="window.location.href='addTodoForm'; return false;"/>
        <form:form action="search" method="POST">
			    <input type="text" placeholder="Search By Todo Name/Desc" name="theSearchName" value="${theSearchName}">
			    Search By Date <input type="date" placeholder="Search By Date" name="theSearchDate" value="${theSearchDate}">
			    <input type="submit" value="Search"/>
        </form:form>
		<table border="1">
			<tr>
				<th>S No.</th>

				<th>Task
       	        &nbsp &nbsp <a href= "/search?page=0&size=5&theSearchName=${theSearchName}&theSearchDate=${theSearchDate}&sort=task,desc"> Desc </a>
                &nbsp &nbsp <a href= "/search?page=0&size=5&theSearchName=${theSearchName}&theSearchDate=${theSearchDate}&sort=task"> Asc </a>
				</th>

				<th>Description
       	        &nbsp &nbsp <a href= "/search?page=0&size=5&theSearchName=${theSearchName}&theSearchDate=${theSearchDate}&sort=description,desc"> Desc </a>
                &nbsp &nbsp <a href= "/search?page=0&size=5&theSearchName=${theSearchName}&theSearchDate=${theSearchDate}&sort=description"> Asc </a>
				</th>

				<th>Date
       	        &nbsp &nbsp <a href= "/search?page=0&size=5&theSearchName=${theSearchName}&theSearchDate=${theSearchDate}&sort=taskDate,desc"> Desc </a>
                &nbsp &nbsp <a href= "/search?page=0&size=5&theSearchName=${theSearchName}&theSearchDate=${theSearchDate}&sort=taskDate"> Asc </a>
				</th>

				<th>Current Status
       	        &nbsp &nbsp <a href= "/search?page=0&size=5&theSearchName=${theSearchName}&theSearchDate=${theSearchDate}&sort=status,desc"> Desc </a>
                &nbsp &nbsp <a href= "/search?page=0&size=5&theSearchName=${theSearchName}&theSearchDate=${theSearchDate}&sort=status"> Asc </a>
				</th>

				<th colspan="2">Action</th>
			</tr>
			<c:set var="index" value="${page * 5 + 1}" />
			<c:forEach var="todo" items="${todos }">
				<c:url var="updateLink" value="/todo/updateTodoForm">
					<c:param name="todoId" value="${todo.id}"></c:param>
				</c:url>

				<c:url var="deleteLink" value="/todo/delete">
					<c:param name="todoId" value="${todo.id}"></c:param>
				</c:url>

				<tr>
					<td>${index}</td>
					<td>${todo.task}</td>
					<td>${todo.description}</td>
					<td>${todo.taskDate}</td>
					<td>${todo.status}</td>

					<td>
						<!-- display the update link -->
						<a href="${updateLink}">Update</a>
					</td>
					<td>
						<a href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to delete this todo?'))) return false">Delete</a>
					</td>
									<c:set var="index" value="${index + 1}" />
					<c:if test="${todo.status eq TaskStatus.PENDING}">
					<td><a href="/updateStatus?todoId=${todo.id}&status=COMPLETED">Mark as Completed</a></td>
					<td><a href="/updateStatus?todoId=${todo.id}&status=DECLINE">Decline Task</a></td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
		        <br><br>
                	<c:choose>
                        <c:when test="${totalPage == 0}">
                            No Record Found
                        </c:when>
                        <c:otherwise>
                            <c:forEach begin="0" end="${totalPage-1}" varStatus="loop">
                                    &nbsp &nbsp<a href="/search?page=${loop.index}&size=5&theSearchName=${theSearchName}&theSearchDate=${theSearchDate}&sort=${sortBy}">${loop.index + 1}</a></li>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>


	</body>
</html>