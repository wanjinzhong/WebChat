<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Web聊天室</title>
<link rel="stylesheet" type="text/css" href="styles/style.css">
<link rel="stylesheet" type="text/css" href="styles/index.css" />
<link rel="SHOTCUT ICON" href="pic/favicon.ico">
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
</head>
<%
	Object user = request.getSession().getAttribute("userName");
	if (user == null){
		response.sendRedirect("login.jsp");
		return;
	}
%>
<body>
	<div class="header">
	<img src="pic/dabai.png" id="logo"><span class="title">WEB聊天室</span>
		<span class="dispName">我是${userName }</span>
	</div>
	<div class="container" id="container">
		<div class="friendList" id="friendList">
			<div class="aboutFriend">
				<div class="friendListTitle">好友列表</div>
				<div class="numOfFriend">总共</div>
			</div>
			<div class="list" id="list">
				<ul class="userList" id="me">
					<li><a href='javascript:void(0);' onclick='atSomeOne(this)' class='user'>所有人</a></li>
					<li>我</li>
				</ul>
				<ul class="userList" id="friendListUl">
				</ul>
			</div>
		</div>
		<div class="chatroom" id="chatroom">
			<div class="content" id="content"></div>
		</div>
	</div>
	<div class="footer">
		<div class="input">
			<div class="atDiv">
				@ <input type="text" class="at" disabled="disabled" value="所有人">
			</div>
			<div class="operate">
				<input type="checkbox" name="noHtml" class="noHtml" />屏蔽HTML代码 
				<a href="javascript:void(0);" onclick="emojiDiv()">表情</a>
				<a href="javascript:void(0);" onclick="backimgDiv()">背景</a>
				<a href="javascript:void(0);" onclick="clearScreen()">清屏</a>
			</div>
			<div class="inputDiv">
				<div class="message" id="message" onclick="inputClick()" contenteditable="true"></div>
			</div>
			<input type="button" class="sendBtn" value="发送" onclick="send()">
		</div>

	</div>
	<div class="emoji" style="display: none;">
		<c:forEach begin="1" end="132" varStatus="index">
			<img alt="${index.count }" src="expression/${index.count }.gif"
				onclick="chooseExp('${index.count }')"  class="emojiPic"/>
			<c:if test="${index.count % 20 == 0 }">
				<br />
			</c:if>
		</c:forEach>
	</div>
	<div class="backgourd" style="display: none;">
		<c:forEach begin="1" end="9" varStatus="index">
			<img alt="${index.count }" src="backimg/${index.count }_index.jpg"
				onclick="setBackImg(${index.count })" class="back">
			<c:if test="${index.count % 3 == 0 }">
				<br />
			</c:if>
		</c:forEach>
	</div>
	<audio  id="msgAudio"></audio>
</body>
<script type="text/javascript">
	
	var name = '<%=request.getSession().getAttribute("userName")%>';
	var emojiDisplayed = false;
	start();
	resize();
	setBackImg(1);
	/**
	 * 窗口大小改变事件，页面自适应
	 */
	window.onresize = resize;
	function resize() {
		var windowHeight = window.innerHeight;
		var windowWidth = window.innerWidth;
		var containerHeight = document.getElementById("container").offsetHeight;
		var containerWidth = document.getElementById("container").offsetWidth;
		console.log(containerHeight);
		if (containerHeight - 50 > 0) {
			document.getElementById("list").style.height = containerHeight - 70
					+ "px";
			document.getElementById("chatroom").style.height = containerHeight
					- 40 + "px";

		}
		if (containerWidth - 203 > 0) {
			document.getElementById("chatroom").style.width = containerWidth
					- 248 + "px";
			document.getElementById("content").style.width = containerWidth
					- 248 + "px";
			document.getElementById("content").style.height = containerHeight
					- 30 + "px";
		}
	}
</script>
</html>