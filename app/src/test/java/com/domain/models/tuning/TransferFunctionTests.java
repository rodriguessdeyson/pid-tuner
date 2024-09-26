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

		sut = new TransferFunction(IMCModelBasedType.P, gain, timeConstant, -1, lambdaCoefficient);

		assertEquals("Check Gain", gain, sut.getGain(), 0);
		assertEquals("Check Time Constant", timeConstant, sut.getTimeConstant(), 0);
		assertEquals("Check Lambda Coefficient", lambdaCoefficient, sut.getLambdaCoefficient(), 0);
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
}
