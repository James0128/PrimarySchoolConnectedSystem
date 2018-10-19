<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	com.pscsw.servlet.helper.onLoadQueryEditScoreData(request);
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
	<form method="post">
		<div class="inner">
			<div style="display: none" class="field_text">
				<label for="id" class="label_title">id</label> <input type="text"
					name="id" id="id" value="${param.id}" placeholder="id" />
			</div>
			<div class="form-group">
				<label for="editData">式样//马玉1,XH1001,数学,SX1001,89,2018年10月06日 12时30分</label>
				<textarea class="form-control" rows="9" name="editData"
					id="editData" placeholder="编辑">${editData}</textarea>
			</div>
		</div>
	</form>
</body>
</html>