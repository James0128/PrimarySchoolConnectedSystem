<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String account = (String) request.getSession().getAttribute("account");
	if (account == null) {
		response.sendRedirect("main.jsp");
	} else {
		com.pscsw.servlet.helper.onLoadQueryStudentInfo(request);//查询，學生信息
		com.pscsw.servlet.helper.onLoadQueryStudentReports(request);// 查询，成績報告
		com.pscsw.servlet.helper.onLoadQueryStudentFees(request);//查询，繳費
	}
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
<title>小学家校通系统 | 我的</title>
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
								<li class="menu-level-0"><a href="main.jsp"><span>管理</span></a></li>
								<c:if test="${not empty sessionScope.account}">
									<li class="menu-level-0"><a href="my.jsp"><span>我的</span></a></li>
									<li class="menu-level-0"><a href="logoutServlet"><span>退出登录</span></a></li>
								</c:if>
							</ul>
						</div>
						<!--/ Website Menu -->
						<div class="row">
							<!--顯示學生成績-->
							<div class="col-sm-10 col-sm-offset-0">
								<c:if test="${sessionScope.user.state==0}">
									<!--管理員-->
									<!-- tabs -->
									<div class="tabs_framed styled">
										<div class="inner">
											<ul class="tabs clearfix active_bookmark1">
												<li class="active"><a href="#tab_me" data-toggle="tab">我的信息</a></li>
											</ul>
											<div class="tab-content clearfix">
												<div class="tab-pane clearfix fade in active" id="tab_me">
													<div class="col-sm-8 col-sm-offset-0">
														<h6 class="foo">我的信息</h6>
														<!-- Profile -->
														<div class="widget-container widget_profile boxed-velvet">
															<div class="inner">
																<div class="widget_profile_top clearfix">
																	<div class="avatar">
																		<img src="images/temp/avatar.png" alt="" />
																	</div>
																	<h5>${user.account}</h5>
																	<span class="subtitle">${user.name}</span>
																</div>
																<ul class="counters inner clearfix">
																	<li class="first"><a id="pass"
																		onclick="pass(this)" href="#"><span>修改密码</span></a></li>
																	<li class="last"><a href="#"><span></span></a></li>
																</ul>
															</div>
														</div>
														<!--/ Profile -->
													</div>
												</div>
											</div>
										</div>
									</div>
									<!--/ tabs -->
								</c:if>
								<c:if test="${sessionScope.user.state!=0}">
									<!-- tabs -->
									<div class="tabs_framed styled">
										<div class="inner">
											<ul class="tabs clearfix active_bookmark1">
												<li class="active"><a href="#tab_reports"
													data-toggle="tab">成績報告</a></li>
												<li><a href="#tab_fees" data-toggle="tab">班級繳費</a></li>
												<li><a href="#tab_me" data-toggle="tab">我的信息</a></li>
											</ul>
											<div class="tab-content clearfix">
												<div class="tab-pane clearfix fade" id="tab_me">
													<div class="col-sm-8 col-sm-offset-0">
														<h6 class="foo">我的信息</h6>
														<!-- Profile -->
														<div class="widget-container widget_profile boxed-velvet">
															<div class="inner">
																<div class="widget_profile_top clearfix">
																	<div class="avatar">
																		<img src="images/temp/avatar.png" alt="" />
																	</div>
																	<h5>${user.account}</h5>
																	<span class="subtitle">${user.name}</span>
																</div>
																<ul class="counters inner clearfix">
																	<li class="first"><a id="pass"
																		onclick="pass(this)" href="#"><span>修改密码</span></a></li>
																	<li class="last"><a id="info" onclick="info(this)"
																		href="#"><span>更新信息</span></a></li>
																</ul>
															</div>
														</div>
														<!--/ Profile -->
													</div>
												</div>
												<div class="tab-pane clearfix fade" id="tab_fees">
													<div>
														<div class="table-responsive">
															<table class="table table-striped">
																<thead>
																	<tr>
																		<th>id</th>
																		<th>学生姓名</th>
																		<th>学生学号</th>
																		<th>学生班级</th>
																		<th>交費代碼</th>
																		<th>应交費用</th>
																		<th>实缴費用</th>
																		<th>費用说明</th>
																		<th>操作</th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach var="fee"
																		items="${sessionScope.get('fees')}" varStatus="status">
																		<tr>
																			<td>${fee.id}</td>
																			<td>${student.studentName}</td>
																			<td>${student.studentNumber}</td>
																			<td>${student.studentClass}</td>
																			<td>${fee.feeNumber}</td>
																			<td>${fee.classFee}</td>
																			<td>${fee.classFeeRecord}</td>
																			<td>${fee.classFeeDetail}</td>
																			<td><c:if
																					test="${fee.classFee>fee.classFeeRecord}">
																					<div class="buttons">
																						<a id="${fee.id}" onclick="order(this)" href="#"
																							class="btn btn-pagination"><span>现在缴费</span></a>
																					</div>
																					<!--/ Buttons -->
																				</c:if> <c:if test="${fee.classFee<=fee.classFeeRecord}">
																					<div class="buttons">
																						<a href="#" class="btn btn-black"><span>已缴费</span></a>
																					</div>
																					<!--/ Buttons -->
																				</c:if></td>

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
												<div class="tab-pane clearfix fade in active"
													id="tab_reports">
													<!-- search widget -->
													<div
														class="widget-container widget_search styled boxed">
														<div class="inner">
															<form method="get" id="searchform2" action="my.jsp">
																<div class="clearfix">
																	<span class="btn btn-small"><input type="submit"
																		id="searchsubmit2" value="Search" /></span>
																	<div class="input_wrap">
																		<input class="inputField" name="searchKey"
																			id="searchKey" placeholder="Search..."
																			value="${param.searchKey}" type="text" />
																	</div>
																</div>
															</form>
														</div>
													</div>
													<!--/ search widget -->
													<div>
														<div class="table-responsive">
															<table class="table table-striped">
																<thead>
																	<tr>
																		<th>id</th>
																		<th>学生姓名</th>
																		<th>学生学号</th>
																		<th>学生班级</th>
																		<th>科目名称</th>
																		<th>科目编号</th>
																		<th>考试成绩</th>
																		<th>考试时间</th>
																		<th>成绩排名</th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach var="report"
																		items="${sessionScope.get('reports')}"
																		varStatus="status">
																		<tr>
																			<td>${report.id}</td>
																			<td>${report.studentName}</td>
																			<td>${report.studentNumber}</td>
																			<td>${report.studentClass}</td>
																			<td>${report.subjectName}</td>
																			<td>${report.subjectNumber}</td>
																			<td>${report.examinationScore}</td>
																			<td>${report.examinationTime}</td>
																			<td>${report.examinationRank}</td>
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
						<!--/ row -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 密码修改模态框 -->
	<div class="modal fade" id="passModal" tabindex="-1" role="dialog"
		aria-labelledby="passModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 360px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="passModalLabel"
						style="text-align: center">密码修改</h4>
				</div>
				<div class="modal-body" id="passBody"></div>
				<div class="modal-footer">
					<a class="btn btn-black" data-dismiss="modal"><span>取消</span> </a><a
						class="btn btn-pagination" data-dismiss="modal" id="passSave">
						<span>密码修改</span>
					</a>
				</div>
			</div>
		</div>
	</div>
	<!-- 更新信息模态框 -->
	<div class="modal fade" id="infoModal" tabindex="-1" role="dialog"
		aria-labelledby="infoModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 360px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="infoModalLabel"
						style="text-align: center">更新信息</h4>
				</div>
				<div class="modal-body" id="infoBody"></div>
				<div class="modal-footer">
					<a class="btn btn-black" data-dismiss="modal"><span>取消</span> </a><a
						class="btn btn-pagination" data-dismiss="modal" id="infoSave">
						<span>更新信息</span>
					</a>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function pass(obj) {//密碼修改 
			$.get("pass.jsp", function(data) {
				$("#passBody").html(data);
				$("#passModal").modal("show");
			});
		}
		$("#passSave").click(function() {//密碼修改
			var password_old = $("#password_old").val();
			var password_new = $("#password_new").val();
			$.post("passServlet", {
				'password_old' : password_old,
				'password_new' : password_new
			}, function(data) {
				alert(data);
				window.location.reload();
			});
		});
		function info(obj) {//更新信息 
			$.get("info.jsp", function(data) {
				$("#infoBody").html(data);
				$("#infoModal").modal("show");
			});
		}
		$("#infoSave").click(function() {//更新信息
			var studentNumber = $("#studentNumber").val();
			var studentParent = $("#studentParent").val();
			var studentClass = $("#studentClass").val();
			$.post("infoServlet", {
				'studentNumber' : studentNumber,
				'studentParent' : studentParent,
				'studentClass' : studentClass
			}, function(data) {
				alert(data);
				window.location.reload();
			});
		});
		function order(obj) {//缴费 
			$.get("orderServlet?id=" + obj.id, function(data) {
				alert(data);
				window.location.reload();
			});
		}
	</script>
</body>
</html>