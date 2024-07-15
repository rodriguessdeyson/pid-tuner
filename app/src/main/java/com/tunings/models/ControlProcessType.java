package com.tunings.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import xdroid.enumformat.EnumFormat;

public enum ControlProcessType implements Parcelable
{
	/**
	 * Servo process.
	 */
	Servo(0),

	/**
	 * Servo process with 20% Overshoot.
	 */
	Servo20(1),

	/**
	 * Regulation process.
	 */
	Regulator(2),

	/**
	 * Regulation process with 20% Overshoot.
	 */
	Regulator20(3),

	/**
	 * Lambda Tuning Process.
	 */
	LambdaTuning(4),

	/**
	 * Opened loop Feedback
	 */
	Open(5),

	/**
	 * Closed Loop Feedback.
	 */
	Closed(6);

	/**
	 * Selected enum.
	 */
	private final int Process;

	/**
	 * Enum constructor.
	 * @param process Index of the enum.
	 */
	ControlProcessType(int process)
	{
		this.Process = process;
	}

	public static final Creator<ControlProcessType> CREATOR = new Creator<ControlProcessType>()
	{
		@Override
		public ControlProcessType createFromParcel(Parcel in)
		{
			return values()[in.readInt()];
		}

		@Override
		public ControlProcessType[] newArray(int size)
		{
			return new ControlProcessType[size];
		}
	};

	@NonNull
	@Override
	public String toString()
	{
		EnumFormat enumFormat = EnumFormat.getInstance();
		return enumFormat.format(this);
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(Process);
	}
}
