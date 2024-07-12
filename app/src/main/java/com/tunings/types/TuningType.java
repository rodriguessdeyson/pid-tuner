package com.tunings.types;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Controller tunings types.
 */
public enum TuningType implements Parcelable
{
	/**
	 * Cohen-Coon.
	 */
	CC(0),

	/**
	 * Chien, Hrones and Reswich.
	 */
	CHR(1),

	/**
	 * Integral Absolute Error.
	 */
	IAE(2),

	/**
	 * Internal Model Control.
	 */
	IMC(3),

	/**
	 * Integral Time Absolute Error.
	 */
	ITAE(4),

	/**
	 * Tyreus and Luyben.
	 */
	TL(5),

	/**
	 * Ziegler and Nichols.
	 */
	ZN(6);

	/**
	 * Selected enum.
	 */
	private final int Tuning;

	/**
	 * Enum constructor.
	 * @param tuning Index of the enum.
	 */
	TuningType(int tuning)
	{
		this.Tuning = tuning;
	}

	public static final Creator<TuningType> CREATOR = new Creator<TuningType>() {
		@Override
		public TuningType createFromParcel(Parcel in) {
			return values()[in.readInt()] ;
		}

		@Override
		public TuningType[] newArray(int size)
		{
			return new TuningType[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(Tuning);
	}
}
