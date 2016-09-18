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
	ws = new WebSocket("ws://localhost:8080/WebChat/UsersDo/" + name);
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
			var newhtml = "<li>" + names[i] + "</li>"
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
	var html = "<span class='from'>" + json.from + "</span> 对 "
			+ "<span class='to'>" + json.to + "</span> 说： "
			+ "<span class='messageContent'>" + json.message + "</span><br/>";
	var oldHtml = $(".chatroom").html();
	$(".chatroom").html(oldHtml + html);
}
/**
 * 发送消息
 */
function send() {
	var message = document.getElementById("message").value;
	// 使用WebSocket发送消息
	ws.send(message);
	$(".message").val("");
}
