<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	com.pscsw.servlet.helper.onLoadQueryClasss(request);// 查询，班级信息
	com.pscsw.servlet.helper.onLoadQueryStudentInfo(request);// 查询，學生信息
%>
<!doctype html>
<!--[if lt IE 7 ]><html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]><html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]><html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]><html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta name="author" content="ThemeFuse">
<meta name="keywords" content="">
<meta name="viewport" content="width=device-width,initial-scale=1">
</head>
<body>
	<form action="" method="post">
		<div class="inner">
			<div class="inner">
				<label class="label_title">学号:</label><input type="text"
					id="studentNumber" value="${student.studentNumber}"
					placeholder="输入学号" />
			</div>
			<div class="inner">
				<label class="label_title">父母姓名:</label><input type="text"
					id="studentParent" value="${student.studentParent}"
					placeholder="输入父母姓名" />
			</div>
			<div class="inner">
				<label for="name">班级选择:</label><select id="studentClass"
					name="studentClass" type="text" class="form-control"><c:forEach
						var="cla" items="${sessionScope.get('clas')}" varStatus="status">
						<option>${cla.className}</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</form>
</body>
</html>