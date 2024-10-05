package com.domain.services.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AnimationUtils;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Allows to manipulate all android Views
 */
public class ViewUtils
{
	/**
	 * Applies a fade in animation to a view.
	 * @param context Context.
	 * @param objs View to be faded in.
	 */
	public static void FadeIn(Context context, @NotNull Object... objs)
	{
		for (Object obj : objs)
		{
			((View)obj).startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
			((View)obj).setVisibility(View.VISIBLE);
		}
	}

	/**
	 * Applies a fade out animation to a view.
	 * @param context Context.
	 * @param objs View to be faded out.
	 */
	public static void FadeOut(Context context, @NotNull Object... objs)
	{
		for (Object obj : objs)
		{
			((View)obj).startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out));
			((View)obj).setVisibility(View.GONE);
		}
	}

	/**
	 * Initialize a view by "Gone" it.
	 * @param v View to be gone.
	 */
	public static void Initialize(final View v)
	{
		v.setVisibility(View.GONE);
		v.setTranslationY(v.getHeight());
		v.setAlpha(0f);
	}

	/**
	 * Get the DateTime.
	 * @return Formatted DateTime.
	 */
	public static String FormatDateTime()
	{
		@SuppressLint("SimpleDateFormat")
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return sdf.format(new Date());
	}
}
