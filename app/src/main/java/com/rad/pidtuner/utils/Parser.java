package com.rad.pidtuner.utils;

import java.util.Locale;

public class Parser
{
	public static Double GetDouble(String value)
	{
		return Double.parseDouble(value);
	}

	public static Integer GetInt(String value)
	{
		return Integer.parseInt(value);
	}

	public static String SanitizeDouble(double d)
	{
		Locale locale = Locale.getDefault();
		if (d == Math.floor(d))
			return String.format(locale, "%.0f", d);
		else
			return Double.toString(d);
	}
}
