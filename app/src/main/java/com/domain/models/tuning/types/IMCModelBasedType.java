package com.domain.models.tuning.types;

import android.os.Parcel;
import android.os.Parcelable;

public enum IMCModelBasedType implements Parcelable
{
	/**
	 * P Model controller.
	 */
	P(0),

	/**
	 * PD Model controller.
	 */
	PD(1),

	/**
	 * PI Model controller.
	 */
	PI(2),

	/**
	 * PID First Model controller
	 */
	PID1(3),

	/**
	 * PID Second Model controller.
	 */
	PID2(4),

	/**
	 * Unknown Model controller.
	 */
	None(-1);

	/**
	 * Selected enum.
	 */
	private final int IMCModelBased;

	/**
	 * Enum constructor.
	 * @param model Index of the enum.
	 */
	IMCModelBasedType(int model)
	{
		this.IMCModelBased = model;
	}

	public static final Creator<IMCModelBasedType> CREATOR = new Creator<IMCModelBasedType>()
	{
		@Override
		public IMCModelBasedType createFromParcel(Parcel in)
		{
			return values()[in.readInt()];
		}

		@Override
		public IMCModelBasedType[] newArray(int size)
		{
			return new IMCModelBasedType[size];
		}
	};

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(IMCModelBased);
	}
}
