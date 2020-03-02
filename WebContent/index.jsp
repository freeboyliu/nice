<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>学生信息查看</title>
</head>
<body>
	<form action="" method="post">
		<table border="1" cellspacing=0>
			<tr>
				<th>学号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>年龄</th>
				<th>地址</th>
				<th>邮箱</th>
			</tr>
		<c:forEach items="${stulist}" var="stu">
			<tr>
				<td>${stu.sid}</td>
				<td>${stu.sname}</td>
				<td>${stu.sgender}</td>
				<td>${stu.sage}</td>
				<td>${stu.saddress}</td>
				<td>${stu.semail}</td>
			</tr>
		</c:forEach>
			
		</table>
	</form>
</body>
</html>