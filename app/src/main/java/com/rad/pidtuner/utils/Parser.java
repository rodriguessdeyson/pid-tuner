package com.rad.pidtuner.utils;

public class Parser
{
	public static Double GetDouble(String value)
	{
		try
		{
			return Double.parseDouble(value);
		}
		catch (Exception ex)
		{
			return null;
		}
	}

	public static Integer GetInt(String value)
	{
		try
		{
			return Integer.parseInt(value);
		}
		catch (Exception ex)
		{
			return null;
		}
	}
}
