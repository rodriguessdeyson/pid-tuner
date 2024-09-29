package com.domain.services.katex;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;
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
	public void when_getDynamicEquationIsCalled_then_returnsCorrectLatexString()
	{
		String gain = "5.0";
		String timeConstant = "55.0";
		String transportDelay = "555.0";
		String expectedLatex = "G(S) = \\frac{5.0 \\cdot e^{-555.0 \\cdot S}}{55.0 S + 1}";

		String result = katex.getDynamicEquation(gain, timeConstant, transportDelay);

		Assert.assertEquals(expectedLatex, result);
	}

	@Test
	public void when_getDynamicEquationIsCalled_then_handlesZeroValues()
	{
		String gain = "0";
		String timeConstant = "0";
		String transportDelay = "0";
		String expectedLatex = "G(S) = \\frac{0 \\cdot e^{-0 \\cdot S}}{0 S + 1}";

		String result = katex.getDynamicEquation(gain, timeConstant, transportDelay);

		Assert.assertEquals(expectedLatex, result);
	}

	@Test
	public void when_getPIDEquationIsCalled_then_returnsCorrectLatexStringForP()
	{
		// Assuming 0 represents P Controller
		int controlType = 0;
		String expectedLatex = "C(S) = K_p";

		String result = katex.getPIDEquation(controlType);

		Assert.assertEquals(expectedLatex, result);
	}

	@Test
	public void when_getPIDEquationIsCalled_then_returnsCorrectLatexStringForPI()
	{
		// Assuming 1 represents PI Controller
		int controlType = 1;
		String expectedLatex = "C(S) = K_p + \\frac{K_i}{S}";

		String result = katex.getPIDEquation(controlType);

		Assert.assertEquals(expectedLatex, result);
	}

	@Test
	public void when_getPIDEquationIsCalled_then_returnsCorrectLatexStringForPD()
	{
		// Assuming 2 represents PD Controller
		int controlType = 2;
		String expectedLatex = "C(S) = K_p + K_d \\cdot S";

		String result = katex.getPIDEquation(controlType);

		Assert.assertEquals(expectedLatex, result);
	}

	@Test
	public void when_getPIDEquationIsCalled_then_returnsCorrectLatexStringForPID()
	{
		// Assuming 3 represents PID Controller
		int controlType = 3;
		String expectedLatex = "C(S) = K_p + \\frac{K_i}{S} + K_d \\cdot S";

		String result = katex.getPIDEquation(controlType);

		Assert.assertEquals(expectedLatex, result);
	}

	@Test
	public void when_getCustomDynamicEquationIsCalled_then_returnsCorrectLatexStringForP()
	{
		String imcModelBased = "0";
		String gain = "0.5";
		String timeConstant = "5";
		String secondTimeConstant = "10";
		String dampingRatio = "0.1";

		String expectedLatex = "G(S) = \\frac{0.5}{S}";

		String result = katex.getCustomDynamicEquation(imcModelBased, gain, timeConstant,
				secondTimeConstant, dampingRatio);

		Assert.assertEquals(expectedLatex, result);
	}

	@Test
	public void when_getCustomDynamicEquationIsCalled_then_returnsCorrectLatexStringForPD()
	{
		String imcModelBased = "1";
		String gain = "0.5";
		String timeConstant = "5";
		String secondTimeConstant = "10";
		String dampingRatio = "0.1";

		String expectedLatex = "G(S) = \\frac{0.5}{S(5 S + 1)}";

		String result = katex.getCustomDynamicEquation(imcModelBased, gain, timeConstant,
				secondTimeConstant, dampingRatio);

		Assert.assertEquals(expectedLatex, result);
	}

	@Test
	public void when_getCustomDynamicEquationIsCalled_then_returnsCorrectLatexStringForPI()
	{
		String imcModelBased = "2";
		String gain = "0.5";
		String timeConstant = "5";
		String secondTimeConstant = "10";
		String dampingRatio = "0.1";

		String expectedLatex = "G(S) = \\frac{0.5}{5 S + 1}";

		String result = katex.getCustomDynamicEquation(imcModelBased, gain, timeConstant,
				secondTimeConstant, dampingRatio);

		Assert.assertEquals(expectedLatex, result);
	}

	@Test
	public void when_getCustomDynamicEquationIsCalled_then_returnsCorrectLatexStringForPID1()
	{
		String imcModelBased = "3";
		String gain = "0.5";
		String timeConstant = "5";
		String secondTimeConstant = "10";
		String dampingRatio = "0.1";

		String expectedLatex = "G(S) = \\frac{0.5}{(5 S + 1)(10 S + 1)}";

		String result = katex.getCustomDynamicEquation(imcModelBased, gain, timeConstant,
				secondTimeConstant, dampingRatio);

		Assert.assertEquals(expectedLatex, result);
	}

	@Test
	public void when_getCustomDynamicEquationIsCalled_then_returnsCorrectLatexStringForPID2()
	{
		String imcModelBased = "4";
		String gain = "0.5";
		String timeConstant = "5";
		String secondTimeConstant = "10";
		String dampingRatio = "0.1";

		String expectedLatex = "G(S) = \\frac{0.5}{5^{2}S^{2} + 2 \\cdot 0.1 \\cdot 5 S + 1}";

		String result = katex.getCustomDynamicEquation(imcModelBased, gain, timeConstant,
				secondTimeConstant, dampingRatio);

		Assert.assertEquals(expectedLatex, result);
	}

	@Test(expected = InvalidParameterException.class)
	public void when_getCustomDynamicEquationIsInvalid_Expect_InvalidException()
	{
		String imcModelBased = "7";
		String gain = "0.5";
		String timeConstant = "5";
		String secondTimeConstant = "10";
		String dampingRatio = "0.1";

		String expectedLatex = "G(S) = \\frac{0.5}{5^{2}S^{2} + 2 \\cdot 0.1 \\cdot 5 S + 1}";

		String result = katex.getCustomDynamicEquation(imcModelBased, gain, timeConstant,
				secondTimeConstant, dampingRatio);

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