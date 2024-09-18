package com.domain.models.tuning;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.domain.models.tuning.types.TuningType;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Custom class to manipulate tunings.
 */
public class Tuning implements Parcelable
{
	// region Attributes

	/**
	 * Name of the tuning.
	 */
	private String TuningName;

	/**
	 * Small tuning description.
	 */
	private String TuningDescription;

	/**
	 * Indicator of the tuning type.
	 */
	private com.domain.models.tuning.types.TuningType TuningType;

	/**
	 * Turning method configuration.
	 */
	private ArrayList<TuningConfiguration> Configuration;

	//endregion

	//region Constructor

	public Tuning() {}

	public Tuning(String name, String description, TuningType type,
				  ArrayList<TuningConfiguration> configuration)
	{
		setTuningName(name);
		setTuningDescription(description);
		setTuningType(type);
		setConfiguration(configuration);
	}

	//endregion

	//region Parcel

	protected Tuning(Parcel in)
	{
		TuningName = in.readString();
		TuningDescription = in.readString();
		TuningType = in.readParcelable(com.domain.models.tuning.types.TuningType.class.getClassLoader());
		Configuration = in.createTypedArrayList(TuningConfiguration.CREATOR);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(TuningName);
		dest.writeString(TuningDescription);
		dest.writeParcelable(TuningType, flags);
		dest.writeTypedList(Configuration);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Tuning> CREATOR = new Creator<Tuning>() {
		@Override
		public Tuning createFromParcel(Parcel in) {
			return new Tuning(in);
		}

		@Override
		public Tuning[] newArray(int size) {
			return new Tuning[size];
		}
	};

	//endregion

	//region Methods

	/**
	 * Sets the tuning name.
	 * @param tuningName Tuning name selected.
	 */
	public void setTuningName(String tuningName)
	{
		if (tuningName.trim().isEmpty())
			throw new InvalidParameterException("Name must be not null");

		TuningName = tuningName;
	}

	/**
	 * Sets the tuning description.
	 * @param tuningDescription The tuning description.
	 */
	public void setTuningDescription(String tuningDescription)
	{
//		if (tuningDescription.trim().isEmpty())
//			throw new InvalidParameterException("Description must be not null");
		TuningDescription = tuningDescription;
	}

	/**
	 * Sets the tuning type.
	 * @param tuningType The tuning type selected.
	 */
	public void setTuningType(TuningType tuningType)
	{
		TuningType = tuningType;
	}

	/**
	 * Sets the tuning method configuration.
	 * @param configuration Tuning configuration.
	 */
	public void setConfiguration(ArrayList<TuningConfiguration> configuration)
	{
		if (configuration == null || configuration.isEmpty())
			throw new InvalidParameterException("Configuration must be not null or empty");

		Configuration = configuration;
	}

	/**
	 * Gets the tuning name.
	 * @return Tuning name.
	 */
	public String getTuningName()
	{
		return TuningName;
	}

	/**
	 * Gets the tuning description.
	 * @return Tuning description.
	 */
	public String getTuningDescription()
	{
		return TuningDescription;
	}

	/**
	 * Gets the tuning type.
	 * @return Tuning type.
	 */
	public TuningType getTuningType()
	{
		return TuningType;
	}

	/**
	 * Gets the tuning configuration.
	 * @return Tuning configuration.
	 */
	public ArrayList<TuningConfiguration> getConfiguration()
	{
		return Configuration;
	}

	@NonNull
	@Override
	public String toString()
	{
		return getTuningName();
	}

	//endregion
}
