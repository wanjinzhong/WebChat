package com.wjz.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SensitiveWord {
	public static String[] word;
	private SensitiveWord(){
	
	}
	/**
	 * 读取敏感词库
	 */
	private static void readWord(){
		String content = "";
		try {
			File file = new File("word.txt");
			System.out.println(file.getAbsolutePath());
			FileInputStream in = new FileInputStream(file);
			byte[] b = new byte[1024];
			
			int length = 0;
			while ((length = in.read(b)) != -1){
				content += new String(b, 0, length);
			}
			word = content.split(";");

			System.out.println("读取敏感词成功");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("文件没找到");
			e.printStackTrace();
		} catch (IOException e){
			System.out.println("IO读写错误");
			e.printStackTrace();
		}
	}
	/**
	 * 获取所有敏感词
	 * @return
	 */
	public String[] getWords(){
		if(word == null)
			readWord();
		return word;
	}
	/**
	 * 获取单个敏感词
	 * @param index 下标
	 * @return 单个敏感词
	 */
	public String getWord(int index){
		if(word == null)
			readWord();
		return word[index];
	}
	/**
	 * 用*替换敏感词
	 * @param content
	 * @return 格式化后的消息
	 */
	public static String formatWord(String content){
		System.out.println("word: " + (word == null));
		if (word == null)
			readWord();
		for(String s : word){
			if(content.indexOf(s) != -1){
				String replace = "";
				for(int i = 0; i < s.length(); i ++){
					replace += "*";
				}
				content = content.replace(s, replace);
			}
		}
		System.out.println(content);
		return content;
	}
	public static void main(String[] args) {
		System.out.println(formatWord("丰进上承载要干枯我干一枯干夺帽子末夺"));
	}
}
