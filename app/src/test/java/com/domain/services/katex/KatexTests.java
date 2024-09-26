package com.domain.services.katex;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

public class KatexTests
{
	private Katex katex;

	@Before
	public void setup()
	{
		Locale mockLocale = Locale.US;
		katex = new Katex(mockLocale);
	}

	@Test
	public void getDynamicEquation_returnsCorrectLatexString()
	{
		String gain = "2.0";
		String timeConstant = "5.0";
		String transportDelay = "1.0";
		String expectedLatex = "G(S) = \\frac{2.0 \\cdot e^{-1.0 \\cdot S}}{5.0 S + 1}";

		String result = katex.getDynamicEquation(gain, timeConstant, transportDelay);

		Assert.assertEquals(expectedLatex, result);
	}

	@Test
	public void getDynamicEquation_handlesZeroValues()
	{
		String gain = "0";
		String timeConstant = "0";
		String transportDelay = "0";
		String expectedLatex = "G(S) = \\frac{0 \\cdot e^{-0 \\cdot S}}{0 S + 1}";

		String result = katex.getDynamicEquation(gain, timeConstant, transportDelay);

		Assert.assertEquals(expectedLatex, result);
	}
}