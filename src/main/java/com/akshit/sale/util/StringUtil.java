package com.akshit.sale.util;

public class StringUtil {

	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

	public static String toLowerCaseTrim(String s) {
		return s == null ? null : s.trim().toLowerCase();
	}
	public static boolean negative(int x) {
		if(x<0) {
			return true;
		}
		return false;
	}
	public static boolean negative(double x) {
		if(x<0) {
			return true;
		}
		return false;
	}
}
