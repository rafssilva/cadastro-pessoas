package com.crud.util;

public class Util {

	public static boolean isNotEmpty(String s) {
		return s != null && !s.trim().isEmpty();
	}
	
	public static boolean isEmpty(String s) {
	    return !isNotEmpty(s);
	}
}