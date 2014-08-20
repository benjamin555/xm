<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>My JSP 'read.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<%@include file="/common/bootstrap-header.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/app/miyu/riddle-detail.js"></script>
</head>
<body>
	<input type="hidden" value="<%=basePath%>" id="basePath">
	<div class="container">
		<div class="jumbotron">
			<h1>Riddle</h1>
			
			<s:iterator  value="%{actionMessages}" var="msg">
				<span class="label label-warning"><s:property value="msg"/></span>
			</s:iterator>
			<br/>
			<label >谜题</label> 
			<p>
				
				<span class="label label-info"><s:property value="riddle.question" /></span>
			</p>
			<p>share it</p>
			<form role="form" action="<%=basePath%>miyu/riddle!check.action" method="post">
			<s:hidden name="id" value="%{id}" ></s:hidden>
			<div class="row" style="display: none;" id="answerDiv">
				<!-- /.col-lg-6 -->
				<div class="col-lg-6">
					<div class="input-group">
						<input type="text" class="form-control" name="answer" > <span
							class="input-group-btn">
							<button class="btn btn-default" type="submit">就它了</button> </span>
					</div>
					<!-- /input-group -->
				</div>
				<!-- /.col-lg-6 -->
			</div>
			<!-- /.row -->
			</form>
			</br>
			<button type="button" id="iKnownBtn" class="btn btn-primary btn-lg">哈我知道答案啦</button>
			</br>
			<a href="<%=basePath%>miyu/riddle!input.action"
				class="btn btn-default btn-lg " role="button">再创建一条</a>
		</div>
	</div>
</body>
</html>
