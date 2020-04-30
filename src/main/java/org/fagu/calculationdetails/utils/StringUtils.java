package org.fagu.calculationdetails.utils;

public class StringUtils {

	public static final String EMPTY = "";

	public static final int INDEX_NOT_FOUND = - 1;

	public static final String SPACE = " ";

	private static final int PAD_LIMIT = 8192;

	private StringUtils() {}

	public static boolean isEmpty(final CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	public static String substringBefore(final String str, final String separator) {
		if(isEmpty(str) || separator == null) {
			return str;
		}
		if(separator.isEmpty()) {
			return EMPTY;
		}
		final int pos = str.indexOf(separator);
		if(pos == INDEX_NOT_FOUND) {
			return str;
		}
		return str.substring(0, pos);
	}

	public static String substringAfter(final String str, final String separator) {
		if(isEmpty(str)) {
			return str;
		}
		if(separator == null) {
			return EMPTY;
		}
		final int pos = str.indexOf(separator);
		if(pos == INDEX_NOT_FOUND) {
			return EMPTY;
		}
		return str.substring(pos + separator.length());
	}

	public static String leftPad(final String str, final int size) {
		return leftPad(str, size, ' ');
	}

	public static String leftPad(final String str, final int size, final char padChar) {
		if(str == null) {
			return null;
		}
		final int pads = size - str.length();
		if(pads <= 0) {
			return str; // returns original String when possible
		}
		if(pads > PAD_LIMIT) {
			return leftPad(str, size, String.valueOf(padChar));
		}
		return repeat(padChar, pads).concat(str);
	}

	public static String leftPad(final String str, final int size, String padStr) {
		if(str == null) {
			return null;
		}
		if(isEmpty(padStr)) {
			padStr = SPACE;
		}
		final int padLen = padStr.length();
		final int strLen = str.length();
		final int pads = size - strLen;
		if(pads <= 0) {
			return str; // returns original String when possible
		}
		if(padLen == 1 && pads <= PAD_LIMIT) {
			return leftPad(str, size, padStr.charAt(0));
		}

		if(pads == padLen) {
			return padStr.concat(str);
		} else if(pads < padLen) {
			return padStr.substring(0, pads).concat(str);
		} else {
			final char[] padding = new char[pads];
			final char[] padChars = padStr.toCharArray();
			for(int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return new String(padding).concat(str);
		}
	}

	public static String repeat(final char ch, final int repeat) {
		if(repeat <= 0) {
			return EMPTY;
		}
		final char[] buf = new char[repeat];
		for(int i = repeat - 1; i >= 0; i--) {
			buf[i] = ch;
		}
		return new String(buf);
	}
}
