
/**
 * 向好友列表中插入用户
 * 
 * @param name
 *            用户名
 */
function friendListInit(name) {

	var li = document.createElement("li");
	li.innerHTML = name;
	document.getElementById("friendListUl").appendChild(li);
}
function start() {

	friendListInit(name);
	/**
	 * 建立连接
	 */
	ws = new WebSocket("ws://www.wanjinzhong.cn/UsersDo/" + name);
	/**
	 * 已经建立好连接
	 * 
	 * @param evt
	 */
	ws.onopen = function(evt) {

	};

	/**
	 * 收到消息
	 * 
	 * @param evt
	 */
	ws.onmessage = function(evt) {
		var message = evt.data;
		var json = eval("(" + evt.data + ")");
		if (json.type == "name") {
			nameHandle(json.content);
		} else if (json.type = "content") {
			contentHandle(json.content);
		}
	};

	/**
	 * 关闭连接
	 */
	ws.onclose = function(evt) {

	};
}
/**
 * 处理类型为刷新用户名列表的数据
 * 
 * @param jsonString
 *            包含所有用户名的字符串
 */
function nameHandle(jsonString) {
	var names = jsonString.split("@");
	$("#friendListUl").html("");
	for ( var i in names) {
		if (names[i] != name) {
			var html = $("#friendListUl").html();
			var newhtml = "<li><a href='javascript:void(0);' onclick='atSomeOne(this)' class='user'>"
					+ names[i] + "</a></li>"
			$("#friendListUl").html(html + newhtml);
		}
	}
}
/**
 * 处理类型为内容的数据
 * 
 * @param json
 *            包含来自用户，向用户，消息内容
 */
function contentHandle(json) {
	// 获取消息类型：normal 普通用户消息，system 系统消息
	var msgtype = json.msgtype;
	var oldHtml = $(".content").html();
	// 将全部的\n换行修改成<br/>标签换行
	var message = json.message.replace(/\n/g, "<br/>");
	// 如果消息类型是普通用户消息
	if (msgtype == "normal" || msgtype == "record") {
		// 如果是来自自己发送的消息
		if (json.from == name) {
			var html = "<div class='messageDiv'><div class='fromMe'><span class='from'>我<span class='toMe'> 对"
					+ json.to
					+ "说</span></span> ("
					+ json.time
					+ ") <div>"
					+ "<div class='msgfromMe'><span class='messageContent'>"
					+ message + "<span></div></div>";
		}
		// 如果来自别人发送的消息，且是单聊
		else if (json.to == name) {
			var html = "<div class='messageDiv'<span class='from'><a href='javascript:void(0);' onclick='atSomeOne(this)'>"
					+ json.from
					+ "</a><span class='toMe'> 对我说</span></span> ("
					+ json.time
					+ ") <br/>"
					+ "<div class='getMessage'><span class='messageContent'>"
					+ message + "</span></div></div>";
			if (msgtype != "record") {
				document.getElementById("msgAudio").src = "audio/saytome.mp3";
				document.getElementById("msgAudio").play();
			}
		}
		// 如果来自别人发送的消息，且不是单聊
		else {
			var html = "<div class='messageDiv'><span class='from'><a href='javascript:void(0);' onclick='atSomeOne(this)'>"
					+ json.from
					+ "</a><span class='toMe'> 对所有人说</span></span> ("
					+ json.time
					+ ") <br/>"
					+ "<div class='getMessage'><span class='messageContent'>"
					+ message + "</span></span></div>";
			if (msgtype != "record") {
				document.getElementById("msgAudio").src = "audio/messagein.mp3";
				document.getElementById("msgAudio").play();
			}
		}
	} else if (msgtype == "system") {
		if (json.from == name) {
			var html = "<div class='messageDiv'><span class='from' style='color:red;'>[系统消息]</span> ("
					+ json.time
					+ ") <br/>"
					+ "<div class='getMessage'>欢迎进入聊天室，请注意文明用语</div></div>";
		} else {
			var html = "<div class='messageDiv'><span class='from' style='color:red;'>[系统消息]</span> ("
					+ json.time
					+ ") <br/>"
					+ "<div class='getMessage'>"
					+ json.message + "</div></div>";
			document.getElementById("msgAudio").src = "audio/online.mp3";
			document.getElementById("msgAudio").play();
		}
		$(".numOfFriend").text("总共" + json.number + "人");
	}
	$(".content").html(oldHtml + html);
	$(".content").scrollTop($(".content")[0].scrollHeight);
}
/**
 * 发送消息
 */
function send() {
	inputClick()
	var message = $("#message").html();
	if (message == "")
		return;
	var to = $(".at").val();
	var noHtml = $(".noHtml").get(0).checked;
	var json = {
		"to" : to,
		"noHtml" : noHtml,
		"message" : message
	};
	var jsonMessage = JSON.stringify(json);
	console.log(jsonMessage);
	// 使用WebSocket发送消息
	ws.send(jsonMessage);
	$("#message").html("");
}
/**
 * at某人，将其名字填入at文本框中
 * 
 * @param name
 *            at的人的名字
 */
function atSomeOne(atName) {
	$(".at").val($(atName).text());
}
/**
 * 显示或隐藏表情Div
 */
function emojiDiv() {
	if ($(".emoji").css("display") == "none")
		$(".emoji").css("display", "block");
	else
		$(".emoji").css("display", "none");
}

/**
 * 显示或隐藏背景Div
 */
function backimgDiv() {
	if ($(".backgourd").css("display") == "none")
		$(".backgourd").css("display", "block");
	else
		$(".backgourd").css("display", "none");
}

/**
 * 选择表情
 * 
 * @param index
 *            表情代号
 */
function chooseExp(index) {
	var pic = "<img src='expression/" + index + ".gif'/>"
	$("#message").html($("#message").html() + pic);
	emojiDiv();
}

/**
 * 输入框点击时表情列表消失
 */
function inputClick() {
	$(".emoj").css("display", "none");
	$(".backgourd").css("display", "none");
}

/**
 * 清空屏幕
 */
function clearScreen() {
	$(".content").text("");
}

/**
 * 设置背景图片
 * @param index
 */
function setBackImg(index) {
	$(".chatroom").css("background-image", "url('backimg/" + index + ".jpg')");
	$(".friendList")
			.css("background-image", "url('backimg/" + index + ".jpg')");
	$(".backgourd").css("display", "none");
}