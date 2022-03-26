<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="style/bootstrap.min.css" />
</head>
<body>
	<header>

		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: Green">

			<div>
				<a href="http://www.Xadmin.net" class="navbar-brand">User
					Management System</a>

			</div>
			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link">Users List</a></li>
			</ul>
		</nav>
	</header>
	<br>

	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${user!=null}">
					<form action="update" method="post">
				</c:if>



				<c:if test="${user == null}">
					<form action="insert" method="post">
				</c:if>
				<caption>
					<h2>
						<c:if test="${user!=null}">
                                        Edit User
                                   </c:if>
						<c:if test="${user==null}">
                        Add new User
                     </c:if>
					</h2>
				</caption>


				<c:if test="${user!=null}">
					<input type="hidden" name="id" value="<c:out value ='${user.id}'/>" />
				</c:if>

				<fieldset class="form-group">
					<label>User Name</label> <input type="text" name="name"
						value="<c:out value ='${user.name}'/>" class="form-control"
						required="required">
				</fieldset>


				<fieldset class="form-group">
					<label>User Email</label> <input type="text" name="email"
						value="<c:out value ='${user.email}'/>" class="form-control"
						required="required">
				</fieldset>



				<fieldset class="form-group">
					<label>User Country</label> <input type="text" name="country"
						value="<c:out value ='${user.country}'/>" class="form-control"
						required="required">
				</fieldset>
				<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>