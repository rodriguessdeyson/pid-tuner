package com.tunings.types;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Types of controllers
 */
public enum ControlType implements Parcelable
{
	/**
	 * Proportional Controller.
	 */
	P(0),

	/**
	 * Proportional Integral Controller.
	 */
	PI(1),

	/**
	 * Proportional Derivative Controller.
	 */
	PD(2),

	/**
	 * Proportional Integral Derivative Controller.
	 */
	PID(3);

	/**
	 * Selected enum.
	 */
	private final int Control;

	/**
	 * Enum constructor.
	 * @param control Index of the enum.
	 */
	ControlType(int control)
	{
		this.Control = control;
	}

	public static final Creator<ControlType> CREATOR = new Creator<ControlType>()
	{
		@Override
		public ControlType createFromParcel(Parcel in) {
			return values()[in.readInt()];
		}

		@Override
		public ControlType[] newArray(int size) {
			return new ControlType[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(Control);
	}
}
