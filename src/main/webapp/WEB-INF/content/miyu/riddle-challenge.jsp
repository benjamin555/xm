<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	int perScore = cn.sp.xm.miyu.action.RiddleAction.getPerScore();
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
<script type="text/javascript" src="<%=basePath%>js/app/miyu/riddle.js"></script>
<link rel="stylesheet" href="<%=basePath%>css/app/miyu/riddle.css">
</head>
<body>
	<input type="hidden" value="<%=basePath%>" id="basePath">
	<input type="hidden" value="0" id="helpCount">
	<input type="hidden" value="<%=perScore %>" id="perScore">
	
	<div class="container">
		<div class="jumbotron">
			<h1>灯谜</h1>
			<div id="msgDiv">
				<s:iterator value="%{actionMessages}" var="msg">
					<div class="msg label-warning" style="">
						<s:property value="msg" />
					</div>
				</s:iterator>
			</div>
			 <label>谜面</label>
			<p class="descr">
				<s:property value="riddle.question" />
			</p>
			<form role="form"
				action="<%=basePath%>miyu/riddle!challengeNext.action" method="post"
				id="rdForm">
				<s:hidden name="id" value="%{riddle.id}"></s:hidden>
				<s:hidden id="realAnswer" value="%{riddle.answer}"></s:hidden>
				<!-- 减分 -->
				<input type="hidden" value="0" name="deduction" id="deduction">
				<input type="hidden" value="false" name="pass" id="pass">
				<div class="row" style="display: none;" id="answerDiv">
					<!-- /.col-lg-6 -->
					<div class="col-lg-6">
						<div class="input-group">
							<input type="text" class="form-control" id="userAnswer"
								placeholder="请输入谜底..."> <span class="input-group-btn">
								<button id="thatsit" class="btn btn-default" type="button">就它了</button>
							</span>
						</div>
						<!-- /input-group -->
					</div>
					<!-- /.col-lg-6 -->
				</div>
				<!-- /.row -->
				<div style="display: none;" id="skipDiv">
					<label class="label label-warning" style="margin-right: 1em;">直接跳过本题（没分给哦）</label>
					<br>
					<button id="skipBtn" class="btn btn-default" type="button">确认跳过</button>
				</div>
			</form>
			
			<div class="btn-group" style="margin-bottom: .5em;">
				<button type="button" id="iKnownBtn" class="btn btn-primary  btn-lg">哈我知道啦</button>
				<a href="<%=basePath%>miyu/riddle!challenge.action"
					class="btn btn-default btn-lg" role="button">重新开始挑战</a>
			</div>
			</br>
			<div class="btn-group tip-group" style="margin-bottom: .5em;">
				<button type="button" id="changer" class="btn btn-primary btn-lg">&nbsp;&nbsp;&nbsp;嫦娥&nbsp;&nbsp;&nbsp;</button>
				<button type="button" id="houyi" class="btn btn-primary btn-lg">&nbsp;&nbsp;&nbsp;后羿&nbsp;&nbsp;&nbsp;</button>
			</div>
			<div class="btn-group " style="margin-bottom: .5em;">
				<button type="button" id="pengmeng" class="btn btn-primary btn-lg">&nbsp;&nbsp;&nbsp;蓬蒙&nbsp;&nbsp;&nbsp;</button>
				<button type="button" id="weixin" class="btn btn-primary btn-lg">&nbsp;&nbsp;&nbsp;月亮&nbsp;&nbsp;&nbsp;</button>
			</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
var msgHtml = '<div class="msg label-warning" ></div>';
var spanHtml = '<span class="msg label-warning" ></span>';
var btnHtml = '<button type="button" class="btn btn-info houyiBtn" style=" margin-bottom:.2em; margin-right:.2em;"></button>';

//遮挡
var msgModalHtml= '<span class="msg label-warning" style="position: relative;left: -10;"></span>';

function addBtns(msgs){
	for(var i =0;i<msgs.length;i++){
		var span=$(btnHtml);
		span = span.text(msgs[i]);
		$("#msgDiv").append(span);
	}
	
}
function addMsg(msg){
		var span=$(msgHtml);
		span = span.text(msg);
		$("#msgDiv").append(span);
}

$(function(){
	$("#iKnownBtn").click(function(){
		$("#answerDiv").show("slow");
		$("#userAnswer").focus();
	});
	
	$("#changer").click(function(){
		var realAnswer = $("#realAnswer").val();
		var span=$(msgHtml);
		if(realAnswer.length==1){
			var cha = realAnswer.substring(0,1);
			
			if(isLucky()){
				 span = span.text("免费送你半个字吧："+cha);
			}else{
				 span = span.text("(扣2分)给你半个字吧："+cha);
				 addDeduction(2);
			}
			 span.append(msgModalHtml);
			 
			 disabledAllTipBtn();
		}else if(realAnswer.length>1){
			var cha = realAnswer.substring(0,1);
			var helpCount = $("#helpCount").val();
			if(helpCount==0){
				if(isLucky()){
					 span = span.text("免费送你半个字吧："+cha);
				}else{
					 span = span.text("(扣2分)给你半个字吧："+cha);
					 addDeduction(2);
				}
				span.append(msgModalHtml);
				$("#helpCount").val(helpCount+1)
			}else{
				if(isLucky()){
					 span = span.text("免费再送你半个字吧："+cha);
				}else{
					span = span.text("(扣2分)再给你半个字吧："+cha);
					addDeduction(2);
				}
				disabledAllTipBtn();
			}
			 
		}else{
			 span = span.text("出错了");
		}
		$("#msgDiv").append(span);
		scroll(0,0);
	});
	
	
	$("#thatsit").click(function(){
		var realAnswer = $("#realAnswer").val();
		var userAnswer = $("#userAnswer").val();
		var span = $(msgHtml);
		if($.trim(realAnswer)==$.trim(userAnswer)){
			$("#pass").val(true);
			$("#rdForm").submit();
		}else{
			span=span.text("(扣3分)答案不对，再想想");
			addDeduction(3);
			$("#msgDiv").append(span);
			scroll(0,0);
		}
		
	});
	
	
	$("#pengmeng").click(function(){
		$("#skipDiv").show("slow");
		
	});
	
	$("#skipBtn").click(function(){
		var perScore = $("#perScore").val();
		$("#deduction").val(perScore);
		$("#pass").val(true);
		$("#rdForm").submit();
	});
	
	
	$("#houyi").click(function(){
		
		//获取十个相同字数的答案,将答案插入其中
		var realAnswer = $("#realAnswer").val();
		var len = realAnswer.length;
		
		
		var basePath= $("#basePath").val();
		var url = basePath+"miyu/riddle!getAnsersByLen.action"
		$.getJSON(url, { "len": len },function(json){
			answers=json;
			if(answers.length<3){
				addMsg("我帮不了你。");
				return;
			}
			var pos = $.random(0,answers.length-1);
			answers.insertAt(pos,realAnswer)
			//罗列到指定消息区域
			addMsg("(扣3分)从下面几个答案中挑一个吧:");
			addBtns(answers);
			scroll(0,0);
			disabledAllTipBtn();
			addDeduction(3);
			$(".houyiBtn").click(function(){
				var answer = $(this).text();
				$("#userAnswer").val(answer);
				$("#thatsit").click();
			});	
		} ); 
		
		
	});
	
	
	
});

function addDeduction(d){
	var r = Number($("#deduction").val());
	d = Number(d)
	$("#deduction").val(r+d);
}


function disabledAllTipBtn(){
	$(".tip-group>button").attr("disabled","disabled");
}


Array.prototype.insertAt=function(index,obj){ 
	this.splice(index,0,obj); 
} 

$.random = function(n1,n2){
    return Math.round(Math.random() * (Math.max(n1,n2)-Math.min(n1,n2) + 1),0);
}
function isLucky(){
	var r = $.random(1,3);
	switch (r){
		case 1:
			return false;
		default:
			return true
	}
	return true
}



</script>
