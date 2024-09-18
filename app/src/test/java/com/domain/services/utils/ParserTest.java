package com.domain.services.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ParserTest
{
	@Test
	public void SanitizeDouble()
	{
		// Expected values.
		String expectedValueWithDecimals = "1.25";
		String expectedValueWithManyDecimals = "1.0000000025";
		String expectedValueWithNoDecimals = "2";

		// Calculated values.
		String calculatedValueWithDecimals = Parser.SanitizeDouble(1.25);
		String calculatedValueWithManyDecimals = Parser.SanitizeDouble(1.0000000025);
		String calculatedValueWithNoDecimals = Parser.SanitizeDouble(2);

		assertEquals("Check decimals", expectedValueWithDecimals, calculatedValueWithDecimals);
		assertEquals("Check decimals", expectedValueWithManyDecimals, calculatedValueWithManyDecimals);
		assertEquals("Check decimals", expectedValueWithNoDecimals, calculatedValueWithNoDecimals);
	}
}
