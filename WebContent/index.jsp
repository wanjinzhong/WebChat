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
	<div class="header">
		<span class="dispName">我是${userName }</span>
	</div>
	<div class="container">
		<div class="friendList" id="friendList">
			<div class="friendListTitle">好友列表</div>
			<ul id="friendListUl">
			</ul>
		</div>
		<div class="chatroom" id="chatroom"></div>
	</div>
	<div class="footer">
		<div class="input">
			<div class="atDiv">
				@ <input type="text" class="at">
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
</script>
</html>