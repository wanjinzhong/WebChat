package com.wjz.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtil {
	/**
	 * 格式化消息内容。屏蔽html标签
	 * 
	 * @param content
	 *            消息未格式化的内容
	 * @return 格式化后的内容
	 */
	public static String fomatTag(String content) {
		content = content.replace("&", "&amp").replace("<", "&lt").replace(">", "&gt");
		return content;
	}

	/**
	 * 解析表情
	 * 
	 * @param content
	 *            消息内容
	 * @return 解析表情后的消息
	 */
	public static String formatFace(String content) {
		//记录“[”和“]”的位置
		int start = 0;
		int end = 0;
		
		for (int i = 0; i < content.length(); i++) {
			//找到下一个“[”的下标
			if (content.charAt(i) == '[')
				start = i;
			//找到下一个“]”的下标
			if (content.charAt(i) == ']') {
				end = i;
				//只有当“[”在“]”之前才有效
				if (start < end) {
					//利用正则表达式判断“[”的“]”之间是否是数字
					Pattern pattern = Pattern.compile("[0-9]*");
					Matcher isNum = pattern.matcher(content.substring(start + 1, end));
					//如果是数字
					if (isNum.matches()) {
						int index = Integer.parseInt(content.substring(start + 1, end));
						//如果这个数字介于0-133之前
						if (index > 0 && index < 133) {
							//将这个表情代码翻译成img标签
							String html = "<img src='expression/" + index + ".gif'/>";
							content = content.replace(content.substring(start, end + 1), html);
						}
					}
				}
			}
		}
		return content;

	}
}
