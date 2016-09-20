

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
			var newhtml = "<li><a href='javascript:void(0);' onclick='atSomeOne(this)'>" + names[i] + "</a></li>"
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
	//获取消息类型：normal 普通用户消息，system 系统消息
	var msgtype = json.msgtype;
	var oldHtml = $(".content").html();
	//将全部的\n换行修改成<br/>标签换行
	var message = json.message.replace(/\n/g,"<br/>");
	//如果消息类型是普通用户消息
	if (msgtype == "normal") {
		//如果是来自自己发送的消息
		if(json.from == name){
			var html = "<div style='margin-top: 10px;'><span class='from'>我</span> (" +
			json.time + ") :<br/>" + "<span class='getMessage'>" + message +
			"</span></div>";
		}
		//如果来自别人发送的消息，且是单聊
		else if(json.to == name){
			var html = "<div style='margin-top: 10px;'><span class='from'><a href='javascript:void(0);' onclick='atSomeOne(this)'>" + json.from + "</a><span class='toMe'> 对我说</span></span> (" +
			json.time + ") :<br/>" + "<span class='getMessage'>" + message +
			"</span></div>";
		}
		//如果来自别人发送的消息，且不是单聊
		else{
			var html = "<div style='margin-top: 10px;'><span class='from'><a href='javascript:void(0);' onclick='atSomeOne(this)'>" + json.from + "</a></span> (" +
			json.time + ") :<br/>" + "<span class='getMessage'>" + message +
			"</span></div>";
		}
	}else if(msgtype == "system"){
		if (json.from == name){
			var html = "<div style='margin-top: 10px;'><span class='from' style='color:red;'>[系统消息]</span> (" +
			json.time + ") :<br/>" + "<span class='getMessage'>欢迎进入聊天室，请注意文明用语</span></div>";
		}
		else {
			var html = "<div style='margin-top: 10px;'><span class='from' style='color:red;'>[系统消息]</span> (" +
			json.time + ") :<br/>" + "<span class='getMessage'>" + json.message +
			"</span></div>";
		}
		$(".numOfFriend").text("总共" + json.number + "人");
	}
	$(".content").html(oldHtml + html);
}
/**
 * 发送消息
 */
function send() {
	var message = $("#message").val();
	if (message == "")
		return;
	var to = $(".at").val();
	var json = {"to":to,"message": message};
	var jsonMessage = JSON.stringify(json);
	console.log(jsonMessage);
	// 使用WebSocket发送消息
	ws.send(jsonMessage);
	$(".message").val("");
}
/**
 * at某人，将其名字填入at文本框中
 * @param name at的人的名字
 */
function atSomeOne(atName){
	$(".at").val($(atName).text());
}
