<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
				<label class="label_title">用户名:</label><input type="text"
					id="account" value="${signup.account}" placeholder="输入用户名" />
			</div>
			<div class="inner">
				<label class="label_title">密码:</label><input type="password"
					id="password" value="${signup.password}" placeholder="输入密码" />
			</div>
			<div class="inner">
				<label class="label_title">姓名:</label><input type="text" id="name"
					value="${signup.name}" placeholder="输入姓名" />
			</div>
			<div class="inner">
				<label class="label_title">电话:</label><input type="text" id="phone"
					value="${signup.phone}" placeholder="输入电话" />
			</div>
			<div class="inner">
				<label class="label_title">地址:</label><input type="text"
					id="address" value="${signup.address}" placeholder="输入地址" />
			
		</div>
	</form>
</body>
</html>