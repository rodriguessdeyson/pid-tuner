package com.domain.services.utils;

import java.util.Locale;

public class Parser
{
	public static Double getDouble(String value)
	{
		return Double.parseDouble(value);
	}

	public static Integer getInt(String value)
	{
		return Integer.parseInt(value);
	}

	public static String sanitizeDouble(double d)
	{
		Locale locale = Locale.getDefault();
		if (d == Math.floor(d))
			return String.format(locale, "%.0f", d);
		else
			return Double.toString(d);
	}
}
