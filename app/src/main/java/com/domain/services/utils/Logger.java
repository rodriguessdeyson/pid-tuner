package com.domain.services.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rad.pidtuner.R;

/**
 * Allow to create an customized toast message.
 */
public class Logger
{
	/**
	 * Method to show toast messages.
	 * @param context Activity context
	 * @param messageResource Id of the resource string to be shown
	 */
	public static void Show(Context context, int messageResource)
	{
		LayoutInflater inflater = (LayoutInflater)context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		@SuppressLint("InflateParams")
		View layout = inflater.inflate(R.layout.layout_toast, null);

		TextView text = layout.findViewById(R.id.TextViewMessage);
		text.setText(context.getResources().getString(messageResource));
		text.setAllCaps(false);
		text.setGravity(Gravity.CENTER);

		Toast toast = new Toast(context);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}

	/**
	 * Method to show toast messages.
	 * @param context Activity context
	 * @param message Message string to be shown
	 */
	public static void Show(Context context, String message)
	{
		LayoutInflater inflater = (LayoutInflater)context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		@SuppressLint("InflateParams")
		View layout = inflater.inflate(R.layout.layout_toast, null);

		TextView text = layout.findViewById(R.id.TextViewMessage);
		text.setText(message);
		text.setAllCaps(false);
		text.setGravity(Gravity.CENTER);

		Toast toast = new Toast(context);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}
}
