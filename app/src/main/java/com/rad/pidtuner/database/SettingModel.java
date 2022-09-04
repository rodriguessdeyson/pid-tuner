package com.rad.pidtuner.database;

public class SettingModel
{
	//region Properties

	/**
	 * Set all first order process same parameters.
	 */
	private boolean SameParameters;

	/**
	 * Gain value.
	 */
	private Double Gain;

	/**
	 * Time value
	 */
	private Double Time;

	/**
	 * Transport value.
	 */
	private Double Transport;

	/**
	 * Ku value.
	 */
	private Double Ku;

	/**
	 * Pu value.
	 */
	private Double Pu;

	/**
	 * Lambda value.
	 */
	private Double Lambda;

	/**
	 * Define the max. decimal places.
	 */
	private int DecimalPlaces;

	//endregion

	//region Methods

	public void setSameParameters(int sameParameters)
	{
		SameParameters = sameParameters == 1;
	}

	public int getSameParameters()
	{
		if (SameParameters)
			return 1;
		else
			return 0;
	}

	public boolean isSameParameters()
	{
		return SameParameters;
	}

	public void setDecimalPlaces(int decimalPlaces)
	{
		DecimalPlaces = decimalPlaces;
	}

	public int getDecimalPlaces()
	{
		return DecimalPlaces;
	}

	public Double getGain() {
		return Gain;
	}

	public Double getKu() {
		return Ku;
	}

	public Double getLambda() {
		return Lambda;
	}

	public Double getPu() {
		return Pu;
	}

	public Double getTime() {
		return Time;
	}

	public Double getTransport() {
		return Transport;
	}

	public void setGain(Double gain) {
		Gain = gain;
	}

	public void setKu(Double ku) {
		Ku = ku;
	}

	public void setLambda(Double lambda) {
		Lambda = lambda;
	}

	public void setPu(Double pu) {
		Pu = pu;
	}

	public void setSameParameters(boolean sameParameters) {
		SameParameters = sameParameters;
	}

	public void setTime(Double time) {
		Time = time;
	}

	public void setTransport(Double transport) {
		Transport = transport;
	}

	//endregion
}
