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
		String gain = "5.0";
		String timeConstant = "55.0";
		String transportDelay = "555.0";
		String expectedLatex = "G(S) = \\frac{5.0 \\cdot e^{-555.0 \\cdot S}}{55.0 S + 1}";

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

	@Test
	public void getPIDEquation_returnsCorrectLatexStringForP() {
		// Assuming 0 represents P Controller
		int controlType = 0;
		String expectedLatex = "C(S) = K_p";

		String result = katex.getPIDEquation(controlType);

		Assert.assertEquals(expectedLatex, result);
	}

	@Test
	public void getPIDEquation_returnsCorrectLatexStringForPI() {
		// Assuming 1 represents PI Controller
		int controlType = 1;
		String expectedLatex = "C(S) = K_p + \\frac{K_i}{S}";

		String result = katex.getPIDEquation(controlType);

		Assert.assertEquals(expectedLatex, result);
	}

	@Test
	public void getPIDEquation_returnsCorrectLatexStringForPD() {
		// Assuming 2 represents PD Controller
		int controlType = 2;
		String expectedLatex = "C(S) = K_p + K_d \\cdot S";

		String result = katex.getPIDEquation(controlType);

		Assert.assertEquals(expectedLatex, result);
	}

	@Test
	public void getPIDEquation_returnsCorrectLatexStringForPID() {
		// Assuming 3 represents PID Controller
		int controlType = 3;
		String expectedLatex = "C(S) = K_p + \\frac{K_i}{S} + K_d \\cdot S";

		String result = katex.getPIDEquation(controlType);

		Assert.assertEquals(expectedLatex, result);
	}

	@Test
	public void getPIDEquation_handlesInvalidControlType() {
		// Assuming -1 represents an invalid control type
		int controlType = -1;
		String expectedLatex = "";

		String result = katex.getPIDEquation(controlType);

		Assert.assertEquals(expectedLatex, result);
	}
}