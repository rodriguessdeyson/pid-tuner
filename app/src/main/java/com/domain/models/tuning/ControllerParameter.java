package com.domain.models.tuning;

import android.os.Parcel;
import android.os.Parcelable;

import com.domain.models.tuning.types.*;


/**
 * Allow to manipulate the controller parameters.
 */
public class ControllerParameter implements Parcelable
{
	//region Attributes

	/**
	 * Tuning model.
	 */
	private TuningType TuningType;

	/**
	 * Type of process that the controller is for.
	 */
	private ProcessType ProcessType;

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

	//region Constructor

	/**
	 * Initialize an object of type ControllerParameters
	 */
	public ControllerParameter() {}

	/**
	 * Initialize an object of type ControllerParameters
	 * @param tuningType Type of tuning.
	 * @param processType Type of process.
	 * @param controlType Type of control.
	 * @param kp Proportional gain.
	 * @param ki Integral gain.
	 * @param kd Derivative gain.
	 */
	public ControllerParameter(TuningType tuningType, ProcessType processType,
							   ControlType controlType, double kp, double ki, double kd)
	{
		setTuningType(tuningType);
		setProcessType(processType);
		setControlType(controlType);
		setKP(kp);
		setKD(kd);
		setKI(ki);
	}

	//endregion

	//region Parcelable

	protected ControllerParameter(Parcel in) {
		TuningType = in.readParcelable(com.domain.models.tuning.types.TuningType.class.getClassLoader());
		ProcessType = in.readParcelable(com.domain.models.tuning.types.ProcessType.class.getClassLoader());
		ControlType = in.readParcelable(com.domain.models.tuning.types.ControlType.class.getClassLoader());
		KP = in.readDouble();
		KI = in.readDouble();
		KD = in.readDouble();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(TuningType, flags);
		dest.writeParcelable(ProcessType, flags);
		dest.writeParcelable(ControlType, flags);
		dest.writeDouble(KP);
		dest.writeDouble(KI);
		dest.writeDouble(KD);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<ControllerParameter> CREATOR = new Creator<ControllerParameter>() {
		@Override
		public ControllerParameter createFromParcel(Parcel in) {
			return new ControllerParameter(in);
		}

		@Override
		public ControllerParameter[] newArray(int size) {
			return new ControllerParameter[size];
		}
	};

	//endregion

	//region Methods

	/**
	 * Set tuning type.
	 * @param tuningType Tuning type selected.
	 */
	public void setTuningType(TuningType tuningType)
	{
		TuningType = tuningType;
	}

	/**
	 * Set process type.
	 * @param processType Process type selected.
	 */
	public void setProcessType(ProcessType processType)
	{
		ProcessType = processType;
	}

	/**
	 * Set control type.
	 * @param type Control type selected.
	 */
	public void setControlType(ControlType type)
	{
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

	/**
	 * Get tuning type.
	 * @return Tuning type.
	 */
	public TuningType getTuningType()
	{
		return TuningType;
	}

	/**
	 * Get tuning type.
	 * @return Tuning type.
	 */
	public ProcessType getProcessType()
	{
		return ProcessType;
	}

	/**
	 * Get control type.
	 * @return Control type.
	 */
	public ControlType getControlType()
	{
		return ControlType;
	}

	/**
	 * Get proportional gain.
	 * @return Proportional gain.
	 */
	public double getKP()
	{
		return KP;
	}

	/**
	 * Get integral gain.
	 * @return Integral gain.
	 */
	public double getKI()
	{
		return KI;
	}

	/**
	 * Get derivative gain.
	 * @return Derivative gain.
	 */
	public double getKD()
	{
		return KD;
	}

	/**
	 * Get tuning and process names.
	 * @return Tuning and process names.
	 */
	public String getTuningAndProcess()
	{
		if (this.ProcessType == com.domain.models.tuning.types.ProcessType.Servo ||
			this.ProcessType == com.domain.models.tuning.types.ProcessType.Servo20 ||
			this.ProcessType == com.domain.models.tuning.types.ProcessType.Regulator ||
			this.ProcessType == com.domain.models.tuning.types.ProcessType.Regulator20)
		{
			return String.format("%s - %s", this.ControlType.toString(), this.ProcessType.toString());
		}
		return this.ControlType.toString();
	}

	//endregion
}
