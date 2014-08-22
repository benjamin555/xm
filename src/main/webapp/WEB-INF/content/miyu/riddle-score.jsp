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
<title>tttttt</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<%@include file="/common/bootstrap-header.jsp"%>
<script type="text/javascript"
	src="<%=basePath%>js/app/miyu/riddle.js"></script>
<link rel="stylesheet" href="<%=basePath%>css/app/miyu/riddle.css">
</head>
<body>
	<input type="hidden" value="<%=basePath%>" id="basePath">
	<input type="hidden" value="0" id="helpCount">
	<div class="container">
		<div class="jumbotron">
			<h1>灯谜</h1>
			<div id="msgDiv">
			<s:iterator value="%{actionMessages}" var="msg">
				
				<div class="msg label-warning" style=""><s:property value="msg" />
				</div>
			</s:iterator>
			</div>
			<div>
				<p class="descr">
					您的分数是：<s:property value="score" />
					</br>
					<s:property value="comment" />
				</p>
			</div>
			
			<div class="btn-group">
				<a href="#" class="btn btn-default "
					role="button">分享</a>
				<a href="<%=basePath%>miyu/riddle!challenge.action" class="btn btn-default "
					role="button">重新开始挑战</a>
			</div>
			</br>
		</div>
	</div>
</body>
</html>
<script type="text/javascript" >
</script>
