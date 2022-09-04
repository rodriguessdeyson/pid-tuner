package com.rad.pidtuner.utils;

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
	public static void FadeIn(Context context, @NotNull Object... objs)
	{
		for (Object obj : objs)
		{
			((View)obj).startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
			((View)obj).setVisibility(View.VISIBLE);
		}
	}

	public static void FadeOut(Context context, @NotNull Object... objs)
	{
		for (Object obj : objs)
		{
			((View)obj).startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out));
			((View)obj).setVisibility(View.GONE);
		}
	}

	/**
	 * FadeIn a view.
	 * @param v View to be faded in.
	 */
	public static void PopIn(final View v)
	{
		v.setVisibility(View.VISIBLE);
		v.setAlpha(0f);
		v.setTranslationY(v.getHeight());
		v.animate()
				.setDuration(200)
				.translationY(0)
				.setListener(new AnimatorListenerAdapter()
				{
					@Override
					public void onAnimationEnd(Animator animation)
					{
						super.onAnimationEnd(animation);
					}
				}).alpha(1f).start();
	}

	/**
	 * FadeIn views.
	 * @param objs View to be faded out.
	 */
	public static void PopIn(@NotNull Object... objs)
	{
		for (Object obj : objs)
		{
			((View)obj).setVisibility(View.VISIBLE);
			((View)obj).setAlpha(0f);
			((View)obj).setTranslationY(((View)obj).getHeight());
			((View)obj).animate()
				.setDuration(200)
				.translationY(0)
				.setListener(new AnimatorListenerAdapter()
				{
					@Override
					public void onAnimationEnd(Animator animation)
					{
						super.onAnimationEnd(animation);
					}
				}).alpha(1f).start();
		}
	}

	/**
	 * FadeOut a view.
	 * @param v View to be faded out.
	 */
	public static void PopOut(final View v)
	{
		v.setVisibility(View.VISIBLE);
		v.setAlpha(1f);
		v.setTranslationY(0);
		v.animate()
			.setDuration(200)
			.translationY(v.getHeight())
			.setListener(new AnimatorListenerAdapter()
				{
					@Override
					public void onAnimationEnd(Animator animation)
					{
						v.setVisibility(View.GONE);
						super.onAnimationEnd(animation);
					}
				}).alpha(0f).start();
	}

	/**
	 * FadeOut a view.
	 * @param objs Views to be faded out.
	 */
	public static void PopOut(@NotNull Object... objs)
	{
		for (Object obj : objs)
		{
			((View)obj).setVisibility(View.VISIBLE);
			((View)obj).setAlpha(1f);
			((View)obj).setTranslationY(0);
			((View)obj).animate()
				.setDuration(200)
				.translationY(((View)obj).getHeight())
				.setListener(new AnimatorListenerAdapter()
					{
						@Override
						public void onAnimationEnd(Animator animation)
						{
							((View)obj).setVisibility(View.GONE);
							super.onAnimationEnd(animation);
						}
					}).alpha(0f).start();
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
	 * Rotated an FloatingButton to get effect X.
	 * @param v View to be rotate.
	 * @param rotate Flag to indicate if the FB is already rotated.
	 * @return The rotate state.
	 */
	public static boolean Rotate(final View v, boolean rotate)
	{
		v.animate().setDuration(200).setListener(new AnimatorListenerAdapter()
		{
			@Override
			public void onAnimationEnd(Animator animation)
			{
				super.onAnimationEnd(animation);
			}}).rotation(rotate ? 180f : 0f);
		return rotate;
	}

	/**
	 * Set Visible/Invisible a view.
	 * @param state True to be visible, false to be invisible.
	 * @param objs One or more vies to be visible/invisible.
	 */
	public static void Visible(boolean state, @NotNull Object... objs)
	{
		if (state)
		{
			for (Object obj : objs)
				((View)obj).setVisibility(View.VISIBLE);
		}
		else
		{
			for (Object obj : objs)
				((View)obj).setVisibility(View.GONE);
		}
	}

	/**
	 * Check if a given color is Bright or Dark.
	 * @param color Color to be evaluated.
	 * @return True if dark, false otherwise.
	 */
	public static boolean IsColorDark(int color)
	{
		double darkness = 1 - (
				0.299 * Color.red(color)   +
				0.587 * Color.green(color) +
				0.114 * Color.blue(color)) / 255;
		return !(darkness < 0.5);
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
