package org.weilabs;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Calendar;

public final class IdGenerator {

	private static final int maxId = 999999;
	private static int curId = 0;
	private static final Object lock = new Object();

	public static String getId() { // 32
		return getTimeString() + "-" + getSeqId("#000000") + getRandomString(8);
	}

	public static String getShortId() {// 25
		return getTimeString() + getSeqId("#000000") + getRandomString(2);
	}

	private static String getTimeString() { // 17
		Calendar calendar = Calendar.getInstance();
		return DateFormatUtils.format(calendar.getTime(), "yyyyMMddHHmmssSSS");
	}

	private static String getSeqId(String formatType) {
		synchronized (lock) {
			if (curId >= maxId)
				curId = 0;
			return DateFormatUtils.format(++curId, formatType);
		}
	}

	private static String getRandomString(int digit) {
		StringBuffer str = new StringBuffer(digit);
		for (int i = 0; i < digit; i++) {
			int psd = (int) (Math.random() * (26 * 2 + 10));
			if (psd >= 26 + 10) { // a~z
				char a = (char) (psd + 97 - 10 - 26);
				str.append(String.valueOf(a));
			} else if (psd >= 10) { // A~Z
				char a = (char) (psd + 65 - 10);
				str.append(String.valueOf(a));
			} else { // 0~9
				str.append(String.valueOf(psd));
			}
		}
		return str.toString();
	}

    public static void main(String[] argv) {
        System.out.println(getId());
        System.out.println(getShortId());
        System.out.println(getTimeString());
        System.out.println(getRandomString(20));
    }

}