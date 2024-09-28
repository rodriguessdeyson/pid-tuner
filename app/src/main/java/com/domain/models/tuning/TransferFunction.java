package com.domain.models.tuning;

import android.os.Parcel;
import android.os.Parcelable;

import com.domain.models.tuning.types.IMCModelBasedType;

import javax.annotation.processing.Generated;

public class TransferFunction implements Parcelable
{
	//region Attributes

	/**
	 * Flag to indicate if the tuning is IMC model based.
	 */
	private IMCModelBasedType IMCModelType;

	/**
	 * Gain of the transfer function.
	 */
	private double Gain;

	/**
	 * Time constant of the transfer function.
	 */
	private double TimeConstant;

	/**
	 * Transport delay of the transfer function.
	 */
	private double TransportDelay;

	/**
	 * Ultimate gain of the transfer function.
	 */
	private double UltimateGain;

	/**
	 * Ultimate period of the transfer function.
	 */
	private double UltimatePeriod;

	/**
	 * Lambda coefficient of the IMC model based tuning.
	 */
	private double LambdaCoefficient;

	/**
	 * Second time constant of the transfer function.
	 */
	private double SecondTimeConstant;

	/**
	 * Damping ratio of the IMC model based tuning.
	 */
	private double DampingRatio;

	//endregion

	//region Constructor

	public TransferFunction() {}
	/**
	 * Initialize an object of type TransferFunction.
	 * @param gain Gain of the transfer function.
	 * @param timeConstant Time constant of the transfer function.
	 * @param transportDelay Transport delay of the transfer function.
	 */
	public TransferFunction(double gain, double timeConstant, double transportDelay)
	{
		setGain(gain);
		setTimeConstant(timeConstant);
		setTransportDelay(transportDelay);
	}

	/**
	 * Initialize an object of type TransferFunction.
	 * @param ultimateGain Ultimate gain of the transfer function.
	 * @param ultimatePeriod Ultimate period of the transfer function.
	 */
	public TransferFunction(double ultimateGain, double ultimatePeriod)
	{
		setUltimateGain(ultimateGain);
		setUltimatePeriod(ultimatePeriod);
	}

	/**
	 * Initialize an object of type TransferFunction.
	 * @param gain Gain of the transfer function.
	 * @param timeConstant Time constant of the transfer function.
	 * @param transportDelay Transport delay of the transfer function.
	 * @param lambdaCoefficient Lambda coefficient of the IMC tuning.
	 */
	public TransferFunction(double gain, double timeConstant, double transportDelay,
							double lambdaCoefficient)
	{
		this(gain, timeConstant, transportDelay);
		setLambdaCoefficient(lambdaCoefficient);
	}

	/**
	 * Initialize an object of type TransferFunction.
	 * @param gain Gain of the transfer function.
	 * @param timeConstant Time constant of the transfer function.
	 * @param dynamicParam Dynamic param that assume as Second TimeFunction and Dumping ratio.
	 * @param lambdaCoefficient Lambda coefficient of the IMC tuning.
	 */
	public TransferFunction(IMCModelBasedType imcModelBasedType, double gain, double timeConstant,
							double dynamicParam, double lambdaCoefficient)
	{
		if (imcModelBasedType == IMCModelBasedType.PID2)
			setDampingRatio(dynamicParam);

		if (imcModelBasedType == IMCModelBasedType.PID1)
			setSecondTimeConstant(dynamicParam);

		setGain(gain);
		setTimeConstant(timeConstant);
		setLambdaCoefficient(lambdaCoefficient);
		setIMCModelType(imcModelBasedType);
	}

	//endregion

	//region Parcelable

	/* Generate */
	protected TransferFunction(Parcel in) {
		IMCModelType = in.readParcelable(IMCModelBasedType.class.getClassLoader());
		Gain = in.readDouble();
		TimeConstant = in.readDouble();
		TransportDelay = in.readDouble();
		UltimateGain = in.readDouble();
		UltimatePeriod = in.readDouble();
		LambdaCoefficient = in.readDouble();
		SecondTimeConstant = in.readDouble();
		DampingRatio = in.readDouble();
	}

	@Override
	@Generated("coverage-excluded")
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(IMCModelType, flags);
		dest.writeDouble(Gain);
		dest.writeDouble(TimeConstant);
		dest.writeDouble(TransportDelay);
		dest.writeDouble(UltimateGain);
		dest.writeDouble(UltimatePeriod);
		dest.writeDouble(LambdaCoefficient);
		dest.writeDouble(SecondTimeConstant);
		dest.writeDouble(DampingRatio);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<TransferFunction> CREATOR = new Creator<TransferFunction>() {
		@Override
		public TransferFunction createFromParcel(Parcel in) {
			return new TransferFunction(in);
		}

		@Override
		public TransferFunction[] newArray(int size) {
			return new TransferFunction[size];
		}
	};

	//endregion

	//region Methods

	/**
	 * Set gain attribute.
	 * @param gain Gain value.
	 */
	public void setGain(double gain) {
		Gain = gain;
	}

	/**
	 * Get gain attribute.
	 * @return Gain value.
	 */
	public double getGain()
	{
		return Gain;
	}

	/**
	 * Set time constant attribute.
	 * @param timeConstant Time constant value.
	 */
	public void setTimeConstant(double timeConstant) {
		TimeConstant = timeConstant;
	}

	/**
	 * Get time constant attribute.
	 * @return Time constant value.
	 */
	public double getTimeConstant()
	{
		return TimeConstant;
	}

	/**
	 * Set second time constant attribute.
	 * @param secondTimeConstant Second time constant value.
	 */
	public void setSecondTimeConstant(double secondTimeConstant) {
		SecondTimeConstant = secondTimeConstant;
	}

	/**
	 * Get second time constant attribute.
	 * @return Second time constant value.
	 */
	public double getSecondTimeConstant()
	{
		return SecondTimeConstant;
	}

	/**
	 * Set transport delay attribute.
	 * @param transportDelay Transport delay value.
	 */
	public void setTransportDelay(double transportDelay) {
		TransportDelay = transportDelay;
	}

	/**
	 * Get transport delay attribute.
	 * @return Transport delay value.
	 */
	public double getTransportDelay()
	{
		return TransportDelay;
	}

	/**
	 * Set damping ratio attribute.
	 * @param dampingRatio Damping ratio value.
	 */
	public void setDampingRatio(double dampingRatio) {
		DampingRatio = dampingRatio;
	}

	/**
	 * Get damping ratio attribute.
	 * @return Damping ratio value.
	 */
	public double getDampingRatio()
	{
		return DampingRatio;
	}

	/**
	 * Set lambda coefficient attribute.
	 * @param lambdaCoefficient Lambda coefficient value.
	 */
	public void setLambdaCoefficient(double lambdaCoefficient) {
		LambdaCoefficient = lambdaCoefficient;
	}

	/**
	 * Get lambda coefficient attribute.
	 * @return Lambda coefficient value.
	 */
	public double getLambdaCoefficient()
	{
		return LambdaCoefficient;
	}

	/**
	 * Set IMC model based type attribute.
	 * @param IMCModelType IMC model based type value.
	 */
	public void setIMCModelType(IMCModelBasedType IMCModelType) {
		this.IMCModelType = IMCModelType;
	}

	/**
	 * Get IMC model based type attribute.
	 * @return IMC model based type value.
	 */
	public IMCModelBasedType getIMCModelType()
	{
		return IMCModelType;
	}

	/**
	 * Set ultimate gain attribute.
	 * @param ultimateGain Ultimate gain value.
	 */
	public void setUltimateGain(double ultimateGain) {
		UltimateGain = ultimateGain;
	}

	/**
	 * Get ultimate gain attribute.
	 * @return Ultimate gain value.
	 */
	public double getUltimateGain()
	{
		return UltimateGain;
	}

	/**
	 * Set ultimate period attribute.
	 * @param ultimatePeriod Ultimate period value.
	 */
	public void setUltimatePeriod(double ultimatePeriod) {
		UltimatePeriod = ultimatePeriod;
	}

	/**
	 * Get ultimate period attribute.
	 * @return Ultimate period value.
	 */
	public double getUltimatePeriod()
	{
		return UltimatePeriod;
	}

	//endregion
}
