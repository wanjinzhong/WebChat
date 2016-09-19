<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Web聊天室</title>
<link rel="stylesheet" type="text/css" href="styles/style.css">
<link rel="stylesheet" type="text/css" href="styles/index.css" />
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
</head>
<body>
	<!-- 宽：1024px，高：768px -->
	<div class="header">
		<span class="dispName">我是${userName }</span>
	</div>
	<div class="container" id="container">
		<div class="friendList" id="friendList">
			<div class="aboutFriend">
				<div class="friendListTitle">好友列表</div>
				<div class="numOfFriend">总共</div>
			</div>
			<div class="list" id="list">
				<ul class="me">
					<li><a href='javascript:void(0);' onclick='atSomeOne(this)'>所有人</a></li>
					<li>我</li>
				</ul>
				<ul id="friendListUl">
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
			<div class="inputDiv">
				<textarea rows="5" cols="10" class="message" id="message"></textarea>
			</div>
			<input type="button" class="sendBtn" value="发送" onclick="send()">
		</div>
		<div class="emoj"></div>
	</div>
</body>
<script type="text/javascript">
	
	var name = '<%=request.getSession().getAttribute("userName")%>';
	start();
	resize();
	/**
	 * 窗口大小改变事件，页面自适应
	 */
	 window.onresize =resize;
	function resize () {
		var windowHeight = window.innerHeight;
		var windowWidth = window.innerWidth;
		var containerHeight = document.getElementById("container").offsetHeight;
		var containerWidth = document.getElementById("container").offsetWidth;
		console.log(containerHeight);
		if (containerHeight - 50 > 0){
			document.getElementById("list").style.height = containerHeight - 60 + "px";
			document.getElementById("chatroom").style.height = containerHeight - 40 + "px";
			
		}
		if (containerWidth - 253 > 0){
			document.getElementById("chatroom").style.width = containerWidth - 298 + "px";
			document.getElementById("content").style.width = containerWidth - 298 + "px";
			document.getElementById("content").style.height = containerHeight - 30 + "px";
		}
	}
</script>
</html>