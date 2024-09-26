package com.domain.services.chart;

import android.webkit.JavascriptInterface;

import java.util.Locale;

public class ChartService
{
	private Locale locale;

	/**
	 * Initialize an object of ChartService.
	 * @param locale locale.
	 */
	public ChartService(Locale locale)
	{
		this.locale = locale;
	}

	@JavascriptInterface
	public String getDynamicEquation(double gain, double timeConstant, double transportDelay,
									 double kp, double ki, double kd)
	{
		return String.format(locale, "G(S) = \\frac{%f \\cdot e^{-%f s}}{%f S + 1}",
				gain, timeConstant, transportDelay);
	}
}
