<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="styles/style.css">
<link rel="stylesheet" type="text/css" href="styles/login.css"/>
<title>Web聊天室</title>
</head>
<body>
	<div class="background" align="center">
		<form action="" method="post">
			<table align="center" id="loginTable">
				<tr>
					<td colspan="3" align="center">Web聊天室</td>
				</tr>
				<tr>
					<td>用户名：</td>
					<td colspan="2"><input type="text" id="userName"
						name="userNameIn"></td>
				</tr>
				<tr>
					<td>密&nbsp;&nbsp;码：</td>
					<td colspan="2"><input type="password" id="passowrd"
						name="passwordIn"></td>
				</tr>
				<tr>
					<td>验证码：</td>
					<td><input type="text" id="checkCode" name="checkCodeIn" /></td>
					<td><img alt="验证码" src=""></td>
				</tr>
				<tr>
					<td></td>
					<td align="right"><input type="submit" value="登陆"/><input type="reset" value="重置"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>