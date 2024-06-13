<%@ page import="com.example.demosql.entity.Student" %><%--
  Created by IntelliJ IDEA.
  User: manhl
  Date: 6/13/2024
  Time: 2:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<jsp:include page="include/navbar.jsp"></jsp:include>

<br/>
<h1>Edit Student</h1>
<%
    Student student = (Student) request.getAttribute("student");
    if (student != null) {
%>
<form action="students" method="POST">
    <div class="mb-3">
        <label for="exampleInputPasswordId" class="form-label">ID</label>
        <input type="number" name="id" class="form-control" id="exampleInputPasswordId" value="<%= student.getId() %>">
    </div>
    <div class="mb-3">
        <label for="exampleInputEmail1" class="form-label">Name</label>
        <input type="text" name="name" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" value="<%= student.getName() %>">
        <div id="emailHelp" class="form-text">We'll never share your name with anyone else.</div>
    </div>
    <div class="mb-3">
        <label for="exampleInputPassword1" class="form-label">Age</label>
        <input type="number" name="age" class="form-control" id="exampleInputPassword1" value="<%= student.getAge() %>">
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
<%
    }
%>

</body>
</html>
