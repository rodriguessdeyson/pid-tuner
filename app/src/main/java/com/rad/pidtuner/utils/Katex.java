package com.rad.pidtuner.utils;

import android.content.Context;
import android.webkit.JavascriptInterface;

import java.util.Locale;

public class Katex
{
	Context mContext;

	// Construtor
	public Katex(Context c)
	{
		mContext = c;
	}

	@JavascriptInterface
	public String getDynamicEquation(String gain, String timeConstant, String transportDelay)
	{
		Locale locale = mContext.getResources().getConfiguration().locale;
		return String.format(locale, "G(S) = \\frac{%s \\cdot e^{-%s s}}{%s S + 1}",
				gain, transportDelay, timeConstant);
	}
}
