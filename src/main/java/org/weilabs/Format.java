package org.weilabs;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Format {

	public static String HideCardNo(String cardNo) {
		try {
			if (cardNo == null || cardNo.length() < 16) {
				return cardNo;
			}
			return cardNo.substring(0, 6) + "******" + cardNo.substring(12);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将以元为单位的金额字符串转为以分为单位的金额字符串
	 * 
	 * @param yuan
	 *            元必须为数字
	 * @return
	 */
	public static String formatYuanToFen(String yuan) {
		if (StringUtil.isEmpty(yuan)) {
			return "";
		}
		boolean isNegative = false;
		if (yuan.startsWith("-")) {
			isNegative = true;
			yuan = yuan.replaceAll("-", "");
		}
		if (!StringUtil.isNumber(yuan.replace("-", "").replace(".", ""))) {
			return "";
		}
		int fen = (int) (Double.parseDouble(yuan) * 100);
		return (isNegative ? "-" : "") + fen;
	}

	/**
	 * 将以分为单位的金额字符串转为以元为单位的金额字符串, 分必须为整数
	 * 
	 * @param fen
	 *            分必须为整数
	 * @return
	 */
	public static String formatFenToYuan(String fen) {
		if (StringUtil.isEmpty(fen)) {
			return "";
		}
		if (!StringUtil.isNumber(fen.replace("-", "").replace(".", ""))) {
			return "";
		}
		BigDecimal bd = new BigDecimal(fen).divide(new BigDecimal(100.0));
		return format(bd).replaceAll(",", "");
	}

	public static String format(Date date, String type) {
		DateFormat format = new SimpleDateFormat(type);
		return format.format(date);
	}

	public static String formatDate(String date, String splitChar) {
		if (StringUtil.isEmpty(date)) {
			return "";
		}
		if (!StringUtil.isNumber(date)) {
			return "";
		}
		if (date.length() != 8) {
			return "";
		}
		return date.substring(0, 4) + splitChar + date.substring(4, 6)
				+ splitChar + date.substring(6, 8);
	}

	public static String format(Date date) {
		return format(date, "yyyy-MM-dd");
	}

	public static String formatDataTime(Date date) {

		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String format(double value, String type) {
		NumberFormat format = new DecimalFormat(type);
		return format.format(value);
	}

	public static String format(BigDecimal value, String type) {
		DecimalFormat format = new DecimalFormat(type);
		return format.format(value);
	}

	public static String format(BigDecimal value) {
		return format(value, "#,##0.00");
	}

	public static String format(double value) {
		return format(value, "###0.00");
	}

	public static String format(long value, String type) {
		NumberFormat format = new DecimalFormat(type);
		return format.format(value);
	}

	public static String format(long value) {
		return format(value, "#,###");
	}

	public static Date parse(String str, String dateType) throws Exception {
		DateFormat format = new SimpleDateFormat(dateType);
		try {
			return format.parse(str);
		} catch (ParseException e) {
			throw new Exception(e);
		}
	}

	public static Date parse(String str) throws Exception {
		return parse(str, "yyyy-MM-dd HH:mm:ss");
	}

	public static double parseDouble(String str) throws Exception {
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			throw new Exception(e);
		}
	}

	public static long parseLong(String str) throws Exception {
		return parseLong(str, 10);
	}

	public static long parseLong(String str, int radix) throws Exception {
		try {
			return Long.parseLong(str, radix);
		} catch (NumberFormatException e) {
			throw new Exception(e);
		}
	}

	public static int parseInt(String str) throws Exception {
		return parseInt(str, 10);
	}

	public static int parseInt(String str, int radix) throws Exception {
		try {
			return Integer.parseInt(str, radix);
		} catch (NumberFormatException e) {
			throw new Exception(e);
		}
	}

	public static BigDecimal parseBigDecimal(String str) throws Exception {
		try {
			return str == null || "".equals(str) ? null : new BigDecimal(str);
		} catch (NumberFormatException e) {
			throw new Exception(e);
		}
	}

	/**
	 * 格式化数字为规定长度字符串
	 * 
	 * @author kevin 2012.2.10
	 * @param src
	 * @param letterNum
	 * @return
	 */
	public static String formatCash(String src, int letterNum) {
		src = src.trim();
		String result = "";
		if (src.length() > letterNum) {
			return src.substring((src.length() - letterNum));
		} else {
			while (result.length() < (letterNum - src.length())) {
				result += "0";
			}
			result += src;
			return result;
		}
	}

	/**
	 * 将金额左补零至18位
	 * 
	 * @param value
	 * @param length
	 *            所要的字符长度
	 * @return
	 */
	public static String ringhtPend(int value, int length) {
		String strZero = "000000000000000000";
		String newValue = strZero + value;
		int strLength = newValue.length();
		return newValue.substring(strLength - length, strLength);
	}

	/**
	 * 左补0到特定长度
	 * 
	 * @author
	 * @param value
	 * @param length
	 *            所要的字符长度
	 * @return
	 */
	public static String ringhtPend(String value, int length) {
		String strZero = "000000000000000000";
		String newValue = strZero + value;
		int strLength = newValue.length();
		return newValue.substring(strLength - length, strLength);
	}

	/**
	 * 格式化金额，不加逗号分割符。
	 * 
	 * @author
	 * @param fen
	 * @return
	 */
	public static String FenToYuanWithoutComma(String fen) {

		BigDecimal bd = new BigDecimal(fen).divide(new BigDecimal(100.0));
		return format(bd, "0.00");
	}

	/**
	 * 取字符串金额的负数
	 * 
	 * @param amt
	 * @return
	 */
	public static String ToNegativeNumber(String amt) {
		if (amt == null) {
			return null;
		}
		int iAmt = Integer.parseInt(amt);
		iAmt = -1 * iAmt;
		return String.valueOf(iAmt);
	}

	/**
	 * 保留num位小数，四舍五入
	 * @param amt
	 * @param num
	 * @return
	 */
	public static String format(double amt, int num) {
		BigDecimal b = new BigDecimal(amt);
		double f1 = b.setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
		return String.valueOf(f1);
	}


    public static void main(String[] argv) {
        System.out.println(formatYuanToFen("1010.249500998004"));
        System.out.println(formatFenToYuan("-100099"));
        System.out.println(formatDate("20120313", "-"));
        System.out.println(formatCash("1", 15));
        System.out.println(format(1010.249300998004, 3));
    }
}
