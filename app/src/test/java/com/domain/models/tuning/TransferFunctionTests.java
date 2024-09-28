package com.domain.models.tuning;
import static org.junit.Assert.assertEquals;

import com.domain.models.tuning.types.IMCModelBasedType;
import org.junit.Test;

public class TransferFunctionTests
{
	private TransferFunction sut;

	public TransferFunctionTests()
	{
		sut = new TransferFunction();
	}

	@Test
	public void when_ConstructorIsRequested_Expect_ObjectCreated()
	{
		double gain = 0.5;
		double timeConstant = 1.75;
		double transportDelay = 0.79;

		sut = new TransferFunction(gain, timeConstant, transportDelay);

		assertEquals("Check Gain", gain, sut.getGain(), 0);
		assertEquals("Check Time Constant", timeConstant, sut.getTimeConstant(), 0);
		assertEquals("Check Transport Delay", transportDelay, sut.getTransportDelay(), 0);
	}

	@Test
	public void when_ConstructorIsRequested_Expect_ObjectCreatedWithLambdaCoefficient()
	{
		double gain = 0.5;
		double timeConstant = 1.75;
		double transportDelay = 0.79;
		double lambdaCoefficient = 4;

		sut = new TransferFunction(gain, timeConstant, transportDelay, lambdaCoefficient);

		assertEquals("Check Gain", gain, sut.getGain(), 0);
		assertEquals("Check Time Constant", timeConstant, sut.getTimeConstant(), 0);
		assertEquals("Check Transport Delay", transportDelay, sut.getTransportDelay(), 0);
		assertEquals("Check Lambda Coefficient", lambdaCoefficient, sut.getLambdaCoefficient(), 0);
	}

	@Test
	public void when_ConstructorIsRequested_Expect_ObjectCreatedWithIMCModelBased()
	{
		double gain = 0.5;
		double timeConstant = 1.75;
		double lambdaCoefficient = 0.79;
		IMCModelBasedType type = IMCModelBasedType.P;

		sut = new TransferFunction(type, gain, timeConstant, -1, lambdaCoefficient);

		assertEquals("Check Gain", gain, sut.getGain(), 0);
		assertEquals("Check Time Constant", timeConstant, sut.getTimeConstant(), 0);
		assertEquals("Check Lambda Coefficient", lambdaCoefficient, sut.getLambdaCoefficient(), 0);
		assertEquals("Check IMC Model Type", type, sut.getIMCModelType());
	}

	@Test
	public void when_ConstructorIsRequested_Expect_ObjectCreatedWithIMCPIDModel1()
	{
		double gain = 0.5;
		double timeConstant = 1.75;
		double dynamicParameter = 5;
		double lambdaCoefficient = 0.79;
		IMCModelBasedType type = IMCModelBasedType.PID1;

		sut = new TransferFunction(type, gain, timeConstant, dynamicParameter, lambdaCoefficient);

		assertEquals("Check Gain", gain, sut.getGain(), 0);
		assertEquals("Check Time Constant", timeConstant, sut.getTimeConstant(), 0);
		assertEquals("Check Second Time Constant", dynamicParameter, sut.getSecondTimeConstant(), 0);
		assertEquals("Check Lambda Coefficient", lambdaCoefficient, sut.getLambdaCoefficient(), 0);
		assertEquals("Check IMC Model Type", type, sut.getIMCModelType());
	}

	@Test
	public void when_ConstructorIsRequested_Expect_ObjectCreatedWithIMCPIDModel2()
	{
		double gain = 1;
		double timeConstant = 2;
		double dynamicParameter = 3;
		double lambdaCoefficient = 4;
		IMCModelBasedType type = IMCModelBasedType.PID2;

		sut = new TransferFunction(type, gain, timeConstant, dynamicParameter, lambdaCoefficient);

		assertEquals("Check Gain", gain, sut.getGain(), 0);
		assertEquals("Check Time Constant", timeConstant, sut.getTimeConstant(), 0);
		assertEquals("Check Damping Ratio", dynamicParameter, sut.getDampingRatio(), 0);
		assertEquals("Check Lambda Coefficient", lambdaCoefficient, sut.getLambdaCoefficient(), 0);
		assertEquals("Check IMC Model Type", type, sut.getIMCModelType());
	}

	@Test
	public void when_SetIMCModelTypeIsRequested_Expect_IMCModelType()
	{
		IMCModelBasedType imcModelBasedType = IMCModelBasedType.P;
		sut.setIMCModelType(imcModelBasedType);

		assertEquals("Check IMC Model Type", imcModelBasedType, sut.getIMCModelType());
	}

	@Test
	public void when_SetGainMethodIsRequested_Expect_Gain()
	{
		double gain = 0.5;
		sut.setGain(gain);

		assertEquals("Check Gain", gain, sut.getGain(), 0);
	}

	@Test
	public void when_SetTimeConstantMethodIsRequested_Expect_TimeConstant()
	{
		double timeConstant = 1.75;
		sut.setTimeConstant(timeConstant);

		assertEquals("Check Time Constant", timeConstant, sut.getTimeConstant(), 0);
	}

	@Test
	public void when_SetTransportDelayMethodIsRequested_Expect_TransportDelay()
	{
		double transportDelay = 0.79;
		sut.setTransportDelay(transportDelay);

		assertEquals("Check Transport Delay", transportDelay, sut.getTransportDelay(), 0);
	}

	@Test
	public void when_SetLambdaCoefficientMethodIsRequested_Expect_LambdaCoefficient()
	{
		double lambdaCoefficient = 4.1;
		sut.setLambdaCoefficient(lambdaCoefficient);

		assertEquals("Check Lambda Coefficient", lambdaCoefficient, sut.getLambdaCoefficient(), 0);
	}

	@Test
	public void when_SetDampingRatioMethodIsRequested_Expect_DampingRatio()
	{
		double dampingRatio = 0.79;
		sut.setDampingRatio(dampingRatio);

		assertEquals("Check Damping Ratio", dampingRatio, sut.getDampingRatio(), 0);
	}

	@Test
	public void when_SetSecondTimeConstantMethodIsRequested_Expect_SecondTimeConstant()
	{
		double secondTimeConstant = 1.75;
		sut.setSecondTimeConstant(secondTimeConstant);

		assertEquals("Check Second Time Constant", secondTimeConstant, sut.getSecondTimeConstant(), 0);
	}

	@Test
	public void when_SetUltimateGainMethodIsRequested_Expect_UltimateGain()
	{
		double ultimateGain = 0.5;
		sut.setUltimateGain(ultimateGain);

		assertEquals("Check Ultimate Gain", ultimateGain, sut.getUltimateGain(), 0);
	}

	@Test
	public void when_SetUltimatePeriodMethodIsRequested_Expect_UltimatePeriod()
	{
		double ultimatePeriod = 1.75;
		sut.setUltimatePeriod(ultimatePeriod);

		assertEquals("Check Ultimate Period", ultimatePeriod, sut.getUltimatePeriod(), 0);
	}
//
//	@Test
//	public void when_ParcelableIsRequested_Expect_Parcelable()
//	{
//		// 1. Obtain a Parcel object
//		Parcel parcel = Parcel.obtain();
//
//		// 2. Write the TransferFunction object to the Parcel
//		sut.setGain(0.5);
//		sut.setTimeConstant(2);
//		sut.writeToParcel(parcel, 0);
//
//		// 3. Reset the Parcel's data position
//		parcel.setDataPosition(0);
//
//		// 4. Create a new TransferFunction object from the Parcel
//		TransferFunction recreatedTransferFunction = TransferFunction.CREATOR.createFromParcel(parcel);
//
//		// 5. Assert that the original and recreated objects are equal
//		assertEquals(sut.getGain(), recreatedTransferFunction.getGain(), 0.01);
//		assertEquals(sut.getTimeConstant(), recreatedTransferFunction.getTimeConstant(), 0.01);
//
//		// 6. Recycle the Parcel
//		parcel.recycle();
//	}
}
