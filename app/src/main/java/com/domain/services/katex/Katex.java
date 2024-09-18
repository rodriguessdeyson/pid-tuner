package com.domain.services.katex;

import android.content.Context;
import android.webkit.JavascriptInterface;

import java.util.Locale;

public class Katex
{
	Context mContext;

	/**
	 * Initialize an object of Katex.
	 * @param c Context of the application.
	 */
	public Katex(Context c)
	{
		mContext = c;
	}

	@JavascriptInterface
	public String getDynamicEquation(String gain, String timeConstant, String transportDelay)
	{
		Locale locale = mContext.getResources().getConfiguration().locale;
		return String.format(locale, "G(S) = \\frac{%s * e^{-%s S}}{%s S + 1}",
				gain, transportDelay, timeConstant);
	}
}
