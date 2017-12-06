package com.kington.fshg.common;

import java.util.Random;
import com.jtframework.websupport.identify.Identify;
import com.jtframework.websupport.identify.NumberAndLetterIdentify;

/**
 * 根据长度生成字符，原来的字符会缺少，重写生成方法
 *
 */
public class RamdomLetter extends Identify {
	public static final int NUMBER = 1; // 纯数字
	public static final int LETTER = 2; // 纯字母
	public static final int MIXED_NUMBER_LETTER = 3; // 数字与字母组合
	private String value; // 生成的值
	
	public RamdomLetter(int type, int length) {
		StringBuilder buf = new StringBuilder();
		Random r = new Random();
		while (buf.length() < length) {
			int ch = r.nextInt(123);
			if (ch < 48 || (ch > 57 && ch < 97) || ch > 122) continue;
			if (ch >= 48 && ch <= 57) {
				if (type != LETTER) buf.append((char)ch);
			} else if (ch >= 97 && ch <= 122) {
				if (type != NUMBER) buf.append((char)ch);
			}
		}
		this.value = buf.toString();
	}

	@Override
	public String getValue() {
		return this.value;
	}
	
	public static Identify getMixedNumberAndLetterIdentify(int length) {
		return new RamdomLetter(NumberAndLetterIdentify.NUMBER, 
				length);
	}
	
}
