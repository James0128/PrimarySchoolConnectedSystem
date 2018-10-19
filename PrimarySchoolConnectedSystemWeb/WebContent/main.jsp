<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	com.pscsw.servlet.helper.onLoadQueryClasss(request);// 管理，班级信息
	com.pscsw.servlet.helper.onLoadQuerySubjects(request);// 管理，学校科目
	//com.pscsw.servlet.helper.onLoadQueryReports(request);// 管理，学生成绩
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
<title>小学家校通系统 | 管理</title>
<%@include file="head.html"%>
</head>
<body>
	<div class="body-wrap">
		<div class="content">
			<!--container-->
			<div class="container">
				<!-- row -->
				<div class="row">
					<div class="col-sm-10">
						<!-- Website Menu -->
						<div class="dropdown-wrap boxed-velvet">
							<ul class="dropdown inner clearfix">
								<li class="menu-level-0"><a href="#"><span>管理</span></a></li>
								<c:if test="${not empty sessionScope.account}">
									<li class="menu-level-0"><a href="my.jsp"><span>我的</span></a></li>
									<li class="menu-level-0"><a href="logoutServlet"><span>退出登录</span></a></li>
								</c:if>
								<c:if test="${empty sessionScope.account}">
									<li class="menu-level-0"><a id="signup"
										onclick="signup(this)" href="#"><span>注册</span></a></li>
									<li class="menu-level-0"><a id="login"
										onclick="login(this)" href="#"><span>登录</span></a></li>
								</c:if>
							</ul>
						</div>
						<!--/ Website Menu -->
						<c:if test="${empty sessionScope.account}">
							<!-- row -->
							<div class="row">
								<div class="col-sm-8">
									<!-- Profile -->
									<div class="widget-container widget_profile boxed-velvet">
										<div class="inner">
											<div class="widget_profile_top clearfix">
												<div class="avatar">
													<img src="images/temp/web.png" alt="" />
												</div>
												<h1>欢迎使用，小学家校通系统!</h1>
												<span class="subtitle"><p></p></span>
											</div>
											<ul class="counters clearfix">
												<li class="first"><a id="signup" onclick="signup(this)"
													href="#"><span>注册»</span></a></li>
												<li class="last"><a id="login" onclick="login(this)"
													href="#"><span>登录»</span></a></li>
											</ul>
										</div>
									</div>
									<!--/ Profile -->
								</div>
							</div>
							<!--/ row -->
						</c:if>
						<c:if test="${not empty sessionScope.account}">
							<!-- widget Tags-->
							<!-- row -->
							<div class="row">
								<div class="inner">
									<div class="col-sm-10 col-sm-offset-0">
										<c:if test="${user.getState()==0}">
											<!-- tabs -->
											<div class="tabs_framed styled">
												<div class="inner">
													<ul class="tabs clearfix active_bookmark1">
														<li class="active"><a href="#tab_manage"
															data-toggle="tab">班级管理(缴费设置,成績设置)</a></li>
													</ul>
													<div class="tab-content clearfix">
														<div class="tab-pane clearfix fade in active"
															id="tab_manage">
															<div>
																<div class="ribbon">
																	<a onclick="addClass(this)" href="#"><span>班级添加</span></a>
																</div>
																<div class="table-responsive">
																	<table class="table table-striped">
																		<thead>
																			<tr>
																				<th>id</th>
																				<th>班级编号</th>
																				<th>班级名称</th>
																				<th>班级老师</th>
																				<th>班级学校</th>
																				<th>操作</th>
																			</tr>
																		</thead>
																		<tbody>
																			<c:forEach var="cla"
																				items="${sessionScope.get('clas')}"
																				varStatus="status">
																				<tr>
																					<td>${cla.id}</td>
																					<td>${cla.classNumber}</td>
																					<td>${cla.className}</td>
																					<td>${cla.classTeacher}</td>
																					<td>${cla.classSchool}</td>
																					<td><div class="buttons">
																							<a id="${cla.id}" onclick="modifyClass(this)"
																								href="#" class="btn btn-pagination"><span>修改</span></a>
																							<a id="${cla.id}" onclick="deleteClass(this)"
																								href="#" class="btn btn-pagination"><span>删除</span></a>
																							<a id="${cla.id}" onclick="feeEdit(this)"
																								href="#" class="btn btn-pagination"><span>缴费</span></a>
																							<a id="${cla.id}" onclick="scoreEdit(this)"
																								href="#" class="btn btn-pagination"><span>成绩</span></a>
																						</div></td>
																				</tr>
																			</c:forEach>
																		</tbody>
																	</table>
																</div>
															</div>
															<p>He made his film debut with a mirror part in Black
																to the Future Part II</p>
															<a href="#" class="see-more"><span>Read More</span></a>
														</div>
													</div>
												</div>
											</div>
											<!--/ tabs -->
										</c:if>
									</div>
								</div>
							</div>
							<!--/ row -->
							<!--/ widget Tags-->
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 模态框 -->
	<div class="modal fade" id="loginModal" tabindex="-1" role="dialog"
		aria-labelledby="loginModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 360px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="loginModalLabel"
						style="text-align: center">登錄</h4>
				</div>
				<div class="modal-body" id="loginBody"></div>
				<div class="modal-footer">
					<a class="btn btn-black" data-dismiss="modal"> <span>取消</span>
					</a> <a class="btn btn-pagination" data-dismiss="modal" id="loginSave">
						<span>登錄</span>
					</a>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="signupModal" tabindex="-1" role="dialog"
		aria-labelledby="signupModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 360px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="signupModalLabel"
						style="text-align: center">註冊</h4>
				</div>
				<div class="modal-body" id="signupBody"></div>
				<div class="modal-footer">
					<a class="btn btn-black" data-dismiss="modal"><span>取消</span> </a><a
						class="btn btn-pagination" data-dismiss="modal" id="signupSave">
						<span>註冊</span>
					</a>
				</div>
			</div>
		</div>
	</div>
	<!-- 班級修改模态框 -->
	<div class="modal fade" id="modifyClassModal" tabindex="-1"
		role="dialog" aria-labelledby="modifyClassModalLabel"
		aria-hidden="true">
		<div class="modal-dialog" style="width: 360px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="modifyClassModalLabel"
						style="text-align: center">班級修改</h4>
				</div>
				<div class="modal-body" id="modifyClassBody"></div>
				<div class="modal-footer">
					<a class="btn btn-black" data-dismiss="modal"><span>取消</span> </a><a
						class="btn btn-pagination" data-dismiss="modal"
						id="modifyClassSave"> <span>班級修改</span>
					</a>
				</div>
			</div>
		</div>
	</div>
	<!-- 班級添加模态框 -->
	<div class="modal fade" id="addClassModal" tabindex="-1" role="dialog"
		aria-labelledby="addClassModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 360px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="addClassModalLabel"
						style="text-align: center">班級添加</h4>
				</div>
				<div class="modal-body" id="addClassBody"></div>
				<div class="modal-footer">
					<a class="btn btn-black" data-dismiss="modal"><span>取消</span> </a><a
						class="btn btn-pagination" data-dismiss="modal" id="addClassSave">
						<span>班級添加</span>
					</a>
				</div>
			</div>
		</div>
	</div>
	<!-- 班級缴费模态框 -->
	<div class="modal fade" id="feeEditModal" tabindex="-1" role="dialog"
		aria-labelledby="feeEditModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 360px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="feeEditModalLabel"
						style="text-align: center">班級缴费</h4>
				</div>
				<div class="modal-body" id="feeEditBody"></div>
				<div class="modal-footer">
					<a class="btn btn-black" data-dismiss="modal"><span>取消</span> </a><a
						class="btn btn-pagination" data-dismiss="modal" id="feeEditSave">
						<span>班級缴费</span>
					</a>
				</div>
			</div>
		</div>
	</div>
	<!-- 班級成绩模态框 -->
	<div class="modal fade" id="scoreEditModal" tabindex="-1" role="dialog"
		aria-labelledby="scoreEditModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 360px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="scoreEditModalLabel"
						style="text-align: center">班級成绩</h4>
				</div>
				<div class="modal-body" id="scoreEditBody"></div>
				<div class="modal-footer">
					<a class="btn btn-black" data-dismiss="modal"><span>取消</span> </a><a
						class="btn btn-pagination" data-dismiss="modal" id="scoreEditSave">
						<span>班級成绩</span>
					</a>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function login(obj) {//登錄模板顯示
			$.get("login.jsp", function(data) {
				$("#loginBody").html(data);
				$("#loginModal").modal("show");
			});
		}
		function signup(obj) {//註冊模板顯示
			$.get("signup.jsp", function(data) {
				$("#signupBody").html(data);
				$("#signupModal").modal("show");
			});
		}
		$("#loginSave").click(
				function() {//登錄提交
					var account = $("#account").val();
					var password = $("#password").val();
					if (account == null || account == "" || password == null
							|| password == "") {
						alert("账号或密码不能为空!");
					} else {
						$.post("loginServlet", {
							'account' : account,
							'password' : password
						}, function(data) {
							if (data == "ok") {
								window.location.reload();
							} else {
								alert(data);
							}
						});
					}
				});
		$("#signupSave").click(
				function() {//註冊提交
					var account = $("#account").val();
					var password = $("#password").val();
					var name = $("#name").val();
					var phone = $("#phone").val();
					var address = $("#address").val();
					if (account == null || account == "" || password == null
							|| password == "") {
						alert("账号或密码不能为空!");
					} else {
						$.post("signupServlet", {
							'account' : account,
							'password' : password,
							'name' : name,
							'phone' : phone,
							'address' : address
						}, function(data) {
							alert(data);
						});
					}
				});
		//--班級
		function addClass() {//班級添加模板顯示
			$.get("class_add.jsp", function(data) {
				$("#addClassBody").html(data);
				$("#addClassModal").modal("show");
			});
		}
		$("#addClassSave").click(function() {//班級添加提交
			var classNumber = $("#classNumber").val();
			var className = $("#className").val();
			var classTeacher = $("#classTeacher").val();
			var classSchool = $("#classSchool").val();
			$.post("classAdd", {
				'classNumber' : classNumber,
				'className' : className,
				'classTeacher' : classTeacher,
				'classSchool' : classSchool
			}, function(data) {
				alert(data);
				window.location.reload();
			});
		});
		function modifyClass(obj) {//班級修改模板顯示
			$.get("class_modify.jsp?id=" + obj.id, function(data) {
				$("#modifyClassBody").html(data);
				$("#modifyClassModal").modal("show");
			});
		}
		$("#modifyClassSave").click(function() {//班級修改提交
			var id = $("#id").val();
			var classNumber = $("#classNumber").val();
			var className = $("#className").val();
			var classTeacher = $("#classTeacher").val();
			var classSchool = $("#classSchool").val();
			$.post("classModify", {
				'id' : id,
				'classNumber' : classNumber,
				'className' : className,
				'classTeacher' : classTeacher,
				'classSchool' : classSchool
			}, function(data) {
				alert(data);
				window.location.reload();
			});
		});
		function deleteClass(obj) {//班級删除
			$.get("classDelete?id=" + obj.id, function(data) {
				alert(data);
				window.location.reload();
			});
		}
		//--缴费
		function feeEdit(obj) {//缴费添加模板顯示
			$.get("edit_fee.jsp?id=" + obj.id, function(data) {
				$("#feeEditBody").html(data);
				$("#feeEditModal").modal("show");
			});
		}
		$("#feeEditSave").click(function() {//缴费添加提交
			var id = $("#id").val();
			var editData = $("#editData").val();
			$.post("editFee", {
				'id' : id,
				'editData' : editData
			}, function(data) {
				alert(data);
				window.location.reload();
			});
		});
		//--成绩
		function scoreEdit(obj) {//成绩添加模板顯示
			$.get("edit_score.jsp?id=" + obj.id, function(data) {
				$("#scoreEditBody").html(data);
				$("#scoreEditModal").modal("show");
			});
		}
		$("#scoreEditSave").click(function() {//成绩添加提交
			var id = $("#id").val();
			var editData = $("#editData").val();
			$.post("editScore", {
				'id' : id,
				'editData' : editData
			}, function(data) {
				alert(data);
				window.location.reload();
			});
		});
	</script>
</body>
</html>