package com.domain.services.katex;

import android.webkit.JavascriptInterface;

import com.domain.models.tuning.types.IMCModelBasedType;

import java.security.InvalidParameterException;
import java.util.Locale;

public class Katex
{
	private Locale locale;

	/**
	 * Initialize an object of Katex.
	 * @param locale Locale.
	 */
	public Katex(Locale locale)
	{
		this.locale = locale;
	}

	@JavascriptInterface
	public String getDynamicEquation(String gain, String timeConstant, String transportDelay)
	{
		return String.format(locale, "G(S) = \\frac{%s \\cdot e^{-%s \\cdot S}}{%s S + 1}",
				gain, transportDelay, timeConstant);
	}

	@JavascriptInterface
	public String getCustomDynamicEquation(String imcModelBased, String gain, String timeConstant,
										   String secondTimeConstant, String dampingRatio)
	{
		switch (imcModelBased)
		{
			case "0":
				return String.format(locale, "G(S) = \\frac{%s}{S}", gain);
			case "1":
				return String.format(locale, "G(S) = \\frac{%s}{S(%s S + 1)}",
						gain, timeConstant);
			case "2":
				return String.format(locale, "G(S) = \\frac{%s}{%s S + 1}",
						gain, timeConstant);
			case "3":
				return String.format(locale, "G(S) = \\frac{%s}{(%s S + 1)(%s S + 1)}",
						gain, timeConstant, secondTimeConstant);
			case "4":
				return String.format(locale, "G(S) = \\frac{%s}{%s^{2}S^{2} + 2 \\cdot %s \\cdot %s S + 1}",
						gain, timeConstant, timeConstant, dampingRatio);
			default:
				throw new InvalidParameterException("IMC model not valid");
		}
	}

	@JavascriptInterface
	public String getPIDEquation(int controller)
	{
		switch (controller)
		{
			case 0: // Proportional.
				return "C(S) = K_p";
			case 1: // Proportional Integral.
				return "C(S) = K_p + \\frac{K_i}{S}";
			case 2: // Proportional Derivative.
				return "S(S) = K_p + K_d \\cdot S";
			case 3: // Proportional Integral Derivative.
				return "C(S) = K_p + \\frac{K_i}{S} + K_d \\cdot S";
			default:
				return "";

		}
	}
}
