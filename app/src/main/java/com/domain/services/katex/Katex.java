package com.domain.services.katex;

import android.webkit.JavascriptInterface;

import java.security.InvalidParameterException;
import java.util.Locale;

public class Katex
{
	private final Locale locale;

	/**
	 * Listener for Katex.
	 */
	private KatexListener katexListener;

	/**
	 * Initialize an object of Katex.
	 * @param locale Locale.
	 */
	public Katex(Locale locale)
	{
		this.locale = locale;
	}

	/**
	 * Set the listener.
	 * @param katexListener Katex listener.
	 */
	public void setOnClickListener(KatexListener katexListener)
	{
		this.katexListener = katexListener;
	}

	/**
	 * Get dynamic transfer function.
	 * @param model IMC model.
	 * @return KATEX dynamic equation.
	 */
	@JavascriptInterface
	public String setModelEquation(String model)
	{
		switch (model)
		{
			case "P":
				return "G(S) = \\frac{K_p}{S}";
			case "PD":
				return "G(S) = \\frac{K_p}{S(\\tau S + 1)}";
			case "PI":
				return "G(S) = \\frac{K_p}{\\tau S + 1}";
			case "PID1":
				return "G(S) = \\frac{K_p}{(\\tau_1 S + 1)(\\tau_2 S + 1)}";
			case "PID2":
				return "G(S) = \\frac{K_p}{\\tau^{2}S^{2} + 2 \\xi \\tau S + 1}";
			default:
				return "G(S) = \\frac{K_p \\cdot e^{-\\theta S}}{\\tau S + 1}";
		}
	}

	/**
	 * Get dynamic transfer function.
	 * @param gain Proportional Gain
	 * @param timeConstant Time Constant
	 * @param transportDelay Transport Delay
	 * @return KATEX dynamic equation.
	 */
	@JavascriptInterface
	public String getDynamicEquation(String gain, String timeConstant, String transportDelay)
	{
		return String.format(locale, "G(S) = \\frac{%s \\cdot e^{-%s \\cdot S}}{%s S + 1}",
				gain, transportDelay, timeConstant);
	}

	/**
	 * Get custom dynamic transfer function.
	 * @param imcModelBased IMC model based.
	 * @param gain Proportional Gain
	 * @param timeConstant Time Constant
	 * @param secondTimeConstant Second Time Constant
	 * @param dampingRatio Damping Ratio
	 * @return KATEX dynamic equation.
	 */
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
						gain, timeConstant, dampingRatio, timeConstant);
			default:
				throw new InvalidParameterException("IMC model not valid");
		}
	}

	/**
	 * Get PID parallel transfer function.
	 * @param controller Controller type.
	 * @return KATEX PID equation.
	 */
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
				return "C(S) = K_p + K_d \\cdot S";
			case 3: // Proportional Integral Derivative.
				return "C(S) = K_p + \\frac{K_i}{S} + K_d \\cdot S";
			default:
				return "";
		}
	}

	@JavascriptInterface
	public void onModelSelected(String model)
	{
		if (katexListener != null)
		{
			katexListener.onTransferFunctionClick(model);
		}
	}
}
