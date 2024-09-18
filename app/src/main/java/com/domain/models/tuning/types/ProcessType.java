package com.domain.models.tuning.types;

import android.os.Parcel;
import android.os.Parcelable;

public enum ProcessType implements Parcelable
{
	/**
	 * No process type.
	 */
	None(0),

	/**
	 * Servo process.
	 */
	Servo(1),

	/**
	 * Servo process with 20% Overshoot.
	 */
	Servo20(2),

	/**
	 * Regulation process.
	 */
	Regulator(3),

	/**
	 * Regulation process with 20% Overshoot.
	 */
	Regulator20(4),

	/**
	 * Lambda Tuning Process.
	 */
	LambdaTuning(5),

	/**
	 * Opened loop Feedback
	 */
	Open(6),

	/**
	 * Closed Loop Feedback.
	 */
	Closed(7);

	/**
	 * Selected enum.
	 */
	private final int Process;

	/**
	 * Enum constructor.
	 * @param process Index of the enum.
	 */
	ProcessType(int process)
	{
		this.Process = process;
	}

	public static final Creator<ProcessType> CREATOR = new Creator<ProcessType>()
	{
		@Override
		public ProcessType createFromParcel(Parcel in)
		{
			return values()[in.readInt()];
		}

		@Override
		public ProcessType[] newArray(int size)
		{
			return new ProcessType[size];
		}
	};

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
