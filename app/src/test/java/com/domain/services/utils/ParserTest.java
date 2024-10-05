package com.domain.services.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ParserTest
{
	@Test
	public void when_SanitizeDoubleMethodIsRequested_then_TheCorrectValueIsReturned()
	{
		// Expected values.
		String expectedValueWithDecimals = "1.25";
		String expectedValueWithManyDecimals = "1.0000000025";
		String expectedValueWithNoDecimals = "2";

		// Calculated values.
		String calculatedValueWithDecimals = Parser.sanitizeDouble(1.25);
		String calculatedValueWithManyDecimals = Parser.sanitizeDouble(1.0000000025);
		String calculatedValueWithNoDecimals = Parser.sanitizeDouble(2);

		assertEquals("Check decimals", expectedValueWithDecimals, calculatedValueWithDecimals);
		assertEquals("Check decimals", expectedValueWithManyDecimals, calculatedValueWithManyDecimals);
		assertEquals("Check decimals", expectedValueWithNoDecimals, calculatedValueWithNoDecimals);
	}

	@Test
	public void when_GetDoubleMethodIsRequested_then_TheCorrectValueIsReturned()
	{
		String value = "1.25";
		Double expectedValue = 1.25;
		Double calculatedValue = Parser.getDouble(value);
		assertEquals("Check decimals", expectedValue, calculatedValue);
	}

	@Test
	public void when_GetIntMethodIsRequested_then_TheCorrectValueIsReturned()
	{
		String value = "2";
		Integer expectedValue = 2;
		Integer calculatedValue = Parser.getInt(value);
		assertEquals("Check decimals", expectedValue, calculatedValue);
	}
}
