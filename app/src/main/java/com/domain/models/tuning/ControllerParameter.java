package com.domain.models.tuning;

import android.os.Parcel;
import android.os.Parcelable;

import com.domain.models.tuning.types.ControlType;

/**
 * Allow to manipulate the controller parameters.
 */
public class ControllerParameter implements Parcelable
{
	//region Attributes

	/**
	 * Type of controller that the parameters are of.
	 */
	private com.domain.models.tuning.types.ControlType ControlType;

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

	//region Constructor

	/**
	 * Sets the controller gains values.
	 * @param kp Proportional gain.
	 * @param ki Integral gain.
	 * @param kd Derivative gain.
	 * @param controlType Type of control.
	 */
	public ControllerParameter(ControlType controlType, double kp, double ki, double kd)
	{
		setControlType(controlType);
		setKP(kp);
		setKD(kd);
		setKI(ki);
	}

	/**
	 * Initialize a ControllerParameters
	 */
	public ControllerParameter()
	{
	}

	//endregion

	//region Methods

	protected ControllerParameter(Parcel in)
	{
		ControlType        = in.readParcelable(ControlType.class.getClassLoader());
		KP                 = in.readDouble();
		KI                 = in.readDouble();
		KD                 = in.readDouble();
	}

	public static final Creator<ControllerParameter> CREATOR = new Creator<ControllerParameter>()
	{
		@Override
		public ControllerParameter createFromParcel(Parcel in)
		{
			return new ControllerParameter(in);
		}

		@Override
		public ControllerParameter[] newArray(int size)
		{
			return new ControllerParameter[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeParcelable(ControlType, flags);
		dest.writeDouble(KP);
		dest.writeDouble(KI);
		dest.writeDouble(KD);
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

	//endregion
}
