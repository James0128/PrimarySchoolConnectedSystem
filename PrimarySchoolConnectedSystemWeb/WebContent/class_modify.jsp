<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	com.pscsw.servlet.helper.onLoadQueryClassModify(request);
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
			<div style="display: none" class="field_text">
				<label class="label_title">ID</label> <input type="text" name="id"
					id="id" value="${cla.id}" placeholder="ID" />
			</div>
			<div class="inner">
				<label class="label_title">班级编号:</label><input type="text"
					id="classNumber" value="${cla.classNumber}" placeholder="输入班级编号" />
			</div>
			<div class="inner">
				<label class="label_title">班级名称:</label><input type="text"
					id="className" value="${cla.className}" placeholder="输入班级名称" />
			</div>
			<div class="inner">
				<label class="label_title">班级老师:</label><input type="text"
					id="classTeacher" value="${cla.classTeacher}" placeholder="输入班级老师" />
			</div>
			<div class="inner">
				<label class="label_title">班级所在学校</label><input type="text"
					id="classSchool" value="${cla.classSchool}" placeholder="输入班级所在学校" />
			</div>
		</div>
	</form>
</body>
</html>