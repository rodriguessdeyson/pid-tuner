package com.domain.models.tuning.types;

import static xdroid.core.Global.getContext;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.rad.pidtuner.R;

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
	Closed(7),

	/**
	 * Lambda Tuning Process.
	 */
	LambdaModelBasedTuning(8);

	/**
	 * Selected enum.
	 */
	private final int process;

	/**
	 * Enum constructor.
	 * @param process Index of the enum.
	 */
	ProcessType(int process)
	{
		this.process = process;
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
		dest.writeInt(process);
	}

	@NonNull
	@Override
	public String toString() {
		if (process == ProcessType.Regulator20.ordinal())
			return "Regulator +20% UP";
		else if(process == ProcessType.Servo20.ordinal())
			return "Servo +20% UP";
		return super.toString();
	}
}
