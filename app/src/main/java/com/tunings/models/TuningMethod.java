package com.tunings.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * Custom class to manipulate tunings.
 */
public class TuningMethod implements Parcelable
{
	//region Properties

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
	private TuningType TuningType;

	/**
	 * Indicator of the process types.
	 */
	private ArrayList<ControlProcessType> ControlProcessTypes;


	/**
	 * Parameters of the specified controller.
	 */
	private ArrayList<ControllerParameters> Parameters;

	//endregion

	//region Methods

	public TuningMethod()
	{
	}

	protected TuningMethod(Parcel in)
	{
		TuningType          = in.readParcelable(com.tunings.models.TuningType.class.getClassLoader());
		ControlTypes        = in.createTypedArrayList(ControlType.CREATOR);
		ControlProcessTypes = in.createTypedArrayList(ControlProcessType.CREATOR);
		TuningName          = in.readString();
		TuningDescription   = in.readString();
		Parameters          = in.createTypedArrayList(ControllerParameters.CREATOR);
	}

	public static final Creator<TuningMethod> CREATOR = new Creator<TuningMethod>()
	{
		@Override
		public TuningMethod createFromParcel(Parcel in) {
			return new TuningMethod(in);
		}

		@Override
		public TuningMethod[] newArray(int size) {
			return new TuningMethod[size];
		}
	};

	/**
	 * Sets the tuning type.
	 * @param tuningType The tuning type selected.
	 */
	public void setTuningType(com.tunings.models.TuningType tuningType)
	{
		TuningType = tuningType;
	}

	/**
	 * Sets the tuning name.
	 * @param tuningName Tuning name selected.
	 */
	public void setTuningName(String tuningName)
	{
		TuningName = tuningName;
	}

	/**
	 * Sets the tuning description.
	 * @param tuningDescription The tuning description.
	 */
	public void setTuningDescription(String tuningDescription)
	{
		TuningDescription = tuningDescription;
	}

	/**
	 *  Sets a set of control types.
	 * @param controlTypes Control type.
	 */
	public void setControlTypes(ArrayList<ControlType> controlTypes)
	{
		ControlTypes = controlTypes;
	}

	/**
	 * Sets a set of control process types.
	 * @param controlProcessTypes An array-list with all desired control process type.
	 */
	public void setControlProcessTypes(ArrayList<com.tunings.models.ControlProcessType> controlProcessTypes)
	{
		ControlProcessTypes = controlProcessTypes;
	}

	/**
	 * Sets a set of control parameters.
	 * @param parameters An Array-List with all controller parameters.
	 */
	public void setParameters(ArrayList<ControllerParameters> parameters)
	{
		Parameters = parameters;
	}

	/**
	 * Gets the tuning type.
	 * @return Tuning type.
	 */
	public com.tunings.models.TuningType getTuningType()
	{
		return TuningType;
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
	 * Gets the tuning controller parameters.
	 * @return Tuning controller parameters.
	 */
	public ArrayList<ControllerParameters> getParameters()
	{
		return Parameters;
	}

	/**
	 * Gets the Tuning controller types.
	 * @return Tuning controller types.
	 */
	public ArrayList<ControlType> getControlTypes()
	{
		return ControlTypes;
	}

	/**
	 * Gets the Tuning type.
	 * @return Tuning type.
	 */
	public ArrayList<com.tunings.models.ControlProcessType> getControlProcessTypes()
	{
		return ControlProcessTypes;
	}

	@NonNull
	@Override
	public String toString()
	{
		return getTuningName();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeParcelable(TuningType, flags);
		dest.writeTypedList(ControlTypes);
		dest.writeTypedList(ControlProcessTypes);
		dest.writeString(TuningName);
		dest.writeString(TuningDescription);
		dest.writeTypedList(Parameters);
	}

	//endregion
}
