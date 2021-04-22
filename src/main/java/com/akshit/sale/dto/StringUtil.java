package com.akshit.sale.dto;

public class StringUtil {

	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

	public static String toLowerCase(String s) {
		return s == null ? null : s.trim().toLowerCase();
	}
	public static boolean negative(int x) {
		if(x<0) {
			return true;
		}
		return false;
	}
}
