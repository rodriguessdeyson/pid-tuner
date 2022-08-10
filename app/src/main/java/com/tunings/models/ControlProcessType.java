package com.tunings.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.rad.pidtuner.R;

import xdroid.enumformat.EnumFormat;
import xdroid.enumformat.EnumString;

public enum ControlProcessType implements Parcelable
{
	/**
	 * Servo process.
	 */
	@EnumString(R.string.rbServo)
	Servo(0),

	/**
	 * Servo process with 20% Overshoot.
	 */
	@EnumString(R.string.rbServo20)
	Servo20(1),

	/**
	 * Regulation process.
	 */
	@EnumString(R.string.rbRegula)
	Regulator(2),

	/**
	 * Regulation process with 20% Overshoot.
	 */
	@EnumString(R.string.rbRegula20)
	Regulator20(3),

	/**
	 * Lambda Tuning Process.
	 */
	@EnumString(R.string.rbLambda)
	LambdaTuning(4),

	/**
	 * Opened loop Feedback
	 */
	@EnumString(R.string.rbOpened)
	Open(5),

	/**
	 * Closed Loop Feedback.
	 */
	@EnumString(R.string.rbClosed)
	Closed(6);

	/**
	 * Selected enum.
	 */
	private int Process;

	/**
	 * Enum constructor.
	 * @param process Index of the enum.
	 */
	ControlProcessType(int process)
	{
		this.Process = process;
	}

	ControlProcessType(Parcel in)
	{
		Process = in.readInt();
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
