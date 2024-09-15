package com.charts.services;

import android.content.Context;
import android.webkit.JavascriptInterface;

import java.util.Locale;

public class ChartService
{
	Context mContext;

	// Construtor
	public ChartService(Context c)
	{
		mContext = c;
	}

	@JavascriptInterface
	public String getDynamicEquation(double gain, double timeConstant, double transportDelay,
									 double kp, double ki, double kd)
	{
		Locale locale = mContext.getResources().getConfiguration().locale;
		return String.format(locale, "G(S) = \\frac{%f \\cdot e^{-%f s}}{%f S + 1}",
				gain, timeConstant, transportDelay);
	}
}
