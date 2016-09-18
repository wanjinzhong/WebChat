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
		document.getElementById("chatroom").innerHTML += message + "<br/>";
	};

	/**
	 * 关闭连接
	 */
	ws.onclose = function(evt) {
	};
}
/**
 * 发送消息
 */
function send() {
	var message = document.getElementById("message").value;
	// 使用WebSocket发送消息
	ws.send(message);
}
