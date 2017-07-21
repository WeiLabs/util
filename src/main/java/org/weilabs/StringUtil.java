package org.weilabs;

import java.math.BigDecimal;

public class StringUtil {

	private StringUtil() {}

	/**
	 * 检查指定的字符串是否为空。
	 * <ul>
	 * <li>SysUtils.isEmpty(null) = true</li>
	 * <li>SysUtils.isEmpty("") = true</li>
	 * <li>SysUtils.isEmpty("   ") = true</li>
	 * <li>SysUtils.isEmpty("abc") = false</li>
	 * </ul>
	 * 
	 * @param value 待检查的字符串
	 * @return true/false
	 */
	public static boolean isEmpty(String value) {
		int strLen;
		if (value == null || (strLen = value.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(value.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 检查对象是否为数字型字符串,包含负数开头的。只算整数
	 */
	public static boolean isNumeric(Object obj) {
		if (obj == null) {
			return false;
		}
		char[] chars = obj.toString().toCharArray();
		int length = chars.length;
		if(length < 1)
			return false;
		
		int i = 0;
		if(length > 1 && chars[0] == '-')
			i = 1;
		
		for (; i < length; i++) {
			if (!Character.isDigit(chars[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查指定的字符串列表是否不为空。
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}

	/**
	 * 把通用字符编码的字符串转化为汉字编码。
	 */
	public static String unicodeToChinese(String unicode) {
		StringBuilder out = new StringBuilder();
		if (!isEmpty(unicode)) {
			for (int i = 0; i < unicode.length(); i++) {
				out.append(unicode.charAt(i));
			}
		}
		return out.toString();
	}
	
	/**
	 * 过滤不可见字符
	 */
	public static String stripNonValidXMLCharacters(String input) {
		if (input == null || ("".equals(input)))
			return "";
		StringBuilder out = new StringBuilder();
		char current;
		for (int i = 0; i < input.length(); i++) {
			current = input.charAt(i);
			if ((current == 0x9) || (current == 0xA) || (current == 0xD)
					|| ((current >= 0x20) && (current <= 0xD7FF))
					|| ((current >= 0xE000) && (current <= 0xFFFD))
					|| ((current >= 0x10000) && (current <= 0x10FFFF)))
				out.append(current);
		}
		return out.toString();
	}
	
    public static boolean isBlank(Object s) {
	return (s == null) || (nullValue(s).trim().length() == 0);
    }
    
    public static String nullValue(Object s) {
	return s == null ? "" :s.toString().toUpperCase().equals("NULL")?"": s.toString();
    }
    
    public static boolean isNotBlank(String str){
	    int strLen=0;
	    if (str != null){
	    	if ((strLen = str.length()) != 0) return true;
	    }
	    for (int i = 0; i < strLen; ++i){
	    	if (Character.isWhitespace(str.charAt(i)) == false) return true;
	    }
	    return false;
	}
	
	public static boolean isNumber(String str) {
		if (isEmpty(str))
			return false;
		String s = str.trim();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (ch < '0' || ch > '9')
				return false;
		}
		return true;
	}

	public static String getNegativeValue(BigDecimal resMoney){
		String resAmount="";
		if(resMoney.compareTo(new BigDecimal(0))==-1){
			resAmount=""+resMoney.abs();
		} else if(resMoney.compareTo(new BigDecimal(0))==0){
			resAmount=""+resMoney;
		}else{
			resAmount="-"+resMoney;
		}
		return resAmount;
	}

}
