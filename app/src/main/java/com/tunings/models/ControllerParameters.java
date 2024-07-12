package com.tunings.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.tunings.types.ControlProcessType;
import com.tunings.types.ControlType;

/**
 * Allow to manipulate the controller parameters.
 */
public class ControllerParameters implements Parcelable
{
	//region Constructor

	/**
	 * Sets the controller gains values.
	 * @param kp Proportional gain.
	 * @param ki Integral gain.
	 * @param kd Derivative gain.
	 * @param controlType Type of control.
	 */
	public ControllerParameters(com.tunings.types.ControlProcessType controlProcessType, com.tunings.types.ControlType controlType,
								double kp, double ki, double kd)
	{
		setControlProcessType(controlProcessType);
		setControlType(controlType);
		setKP(kp);
		setKD(kd);
		setKI(ki);
	}

	/**
	 * Initialize a ControllerParameters
	 */
	public ControllerParameters()
	{
	}

	//endregion

	//region Properties
	/**
	 * Type of process.
	 */
	private ControlProcessType ControlProcessType;

	/**
	 * Type of controller that the parameters are of.
	 */
	private ControlType ControlType;

	/**
	 * Proportional Gain.
	 */
	private double KP;

	/**
	 * Integral Gain.
	 */
	private double KI;

	/**
	 * Derivative Gain.
	 */
	private double KD;

	//endregion

	//region Methods

	protected ControllerParameters(Parcel in)
	{
		ControlProcessType = in.readParcelable(ControlProcessType.class.getClassLoader());
		ControlType        = in.readParcelable(ControlType.class.getClassLoader());
		KP                 = in.readDouble();
		KI                 = in.readDouble();
		KD                 = in.readDouble();
	}

	public static final Creator<ControllerParameters> CREATOR = new Creator<ControllerParameters>()
	{
		@Override
		public ControllerParameters createFromParcel(Parcel in)
		{
			return new ControllerParameters(in);
		}

		@Override
		public ControllerParameters[] newArray(int size)
		{
			return new ControllerParameters[size];
		}
	};

	public void setControlProcessType(ControlProcessType type) {
		ControlProcessType = type;
	}

	public void setControlType(ControlType type) {
		ControlType = type;
	}

	/**
	 * Set proportional gain.
	 * @param kp Proportional gain value.
	 */
	public void setKP(double kp)
	{
		this.KP = kp;
	}

	/**
	 * Set integral gain.
	 * @param ki Integral gain value.
	 */
	public void setKI(double ki)
	{
		this.KI = ki;
	}

	/**
	 * Set derivative gain.
	 * @param kd Derivative gain value.
	 */
	public void setKD(double kd)
	{
		this.KD = kd;
	}

	public ControlProcessType getControlProcessType() {
		return ControlProcessType;
	}

	public ControlType getControlType() {
		return ControlType;
	}

	/**
	 * Get proportional gain.
	 */
	public double getKP()
	{
		return KP;
	}

	/**
	 * Get integral gain.
	 */
	public double getKI()
	{
		return KI;
	}

	/**
	 * Get derivative gain.
	 */
	public double getKD()
	{
		return KD;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeParcelable(ControlProcessType, flags);
		dest.writeParcelable(ControlType, flags);
		dest.writeDouble(KP);
		dest.writeDouble(KI);
		dest.writeDouble(KD);
	}

	//endregion
}
