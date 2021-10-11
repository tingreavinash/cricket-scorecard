package com.avinash.helper;

public class Validations {

	public static boolean isBallValid(String ball) {
		return ball.equals("Wd") || ball.equals("N") || ball.equals("W") || isIntegerValue(ball);
	}
	public static boolean isDeliveryValid(String ball) {
		return !ball.equals("Wd") && !ball.equals("N");
	}

	public static boolean isIntegerValue(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			Integer.parseInt(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
