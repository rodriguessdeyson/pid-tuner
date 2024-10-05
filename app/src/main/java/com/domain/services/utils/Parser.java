package com.domain.services.utils;

import java.util.Locale;

public class Parser
{
	/**
	 * Parse a string to a double.
	 * @param value String to be parsed.
	 * @return The parsed double.
	 */
	public static Double getDouble(String value)
	{
		return Double.parseDouble(value);
	}

	/**
	 * Parse a string to a integer.
	 * @param value String to be parsed.
	 * @return The parsed integer.
	 */
	public static Integer getInt(String value)
	{
		return Integer.parseInt(value);
	}

	/**
	 * Sanitize a double.
	 * @param d Double to be sanitized.
	 * @return The sanitized double.
	 */
	public static String sanitizeDouble(double d)
	{
		Locale locale = Locale.getDefault();
		if (d == Math.floor(d))
			return String.format(locale, "%.0f", d);
		else
			return Double.toString(d);
	}
}
