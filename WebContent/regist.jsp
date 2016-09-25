<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="styles/style.css">
<link rel="stylesheet" type="text/css" href="styles/login.css"/>
<link rel="SHOTCUT ICON" href="pic/favicon.ico">
<title>Web聊天室</title>
</head>

<body>
	<div class="background" align="center">
		<form action="RegistServlet" method="post">
			<table align="center" id="loginTable">
				<tr>
					<td colspan="2" align="center" id="title">Web聊天室-注册</td>
				</tr>
				<tr>
					<td>用户名：</td>
					<td><input type="text" id="userName"
						name="userName" style="width:200px"></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input type="password" id="pwd"
						name="pwd"  style="width:200px"></td>
				</tr>
				<tr>
					<td>重复密码：</td>
					<td><input type="password" id="repwd"
						name="repwd"   style="width:200px"></td>
				</tr>
				<tr>
					<td><a href="login.jsp" style="text-decoration: none;">已有帐号?</a></td>
					<td align="right"><input type="submit" value="注册" id="login"/><input type="reset" value="重置" id="reset"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>