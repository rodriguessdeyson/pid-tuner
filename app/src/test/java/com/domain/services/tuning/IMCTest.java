package com.domain.services.tuning;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.IMCModelBasedType;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.TuningType;

import org.junit.Test;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

public class IMCTest
{
	private final TransferFunction firstOrderTF;
	private final TransferFunction modelBasedTF;

	public IMCTest()
	{
		double gain               = 0.5;
		double timeConstant       = 5.0;
		double transportDelay     = 1.0;
		double lambdaFactor       = 4;
		modelBasedTF = new TransferFunction();
		firstOrderTF = new TransferFunction(gain, timeConstant, transportDelay, lambdaFactor);
	}

	@Test
	public void when_computeLambdaTuningIsCalled_then_returnCorrectPControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IMC,
				ProcessType.LambdaTuning, ControlType.PI, 2.75, 5.5, 0);

		// Calculated values.
		ControllerParameter sut = IMC.computeLambdaTuning(ControlType.PI, firstOrderTF);
		assertEquals("Check Tuning Type", expectedParameters.getTuningType(), sut.getTuningType());
		assertEquals("Check Process Type", expectedParameters.getProcessType(), sut.getProcessType());
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
	}

	@Test
	public void when_computeLambdaTuningIsCalled_then_returnCorrectPIDControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IMC,
				ProcessType.LambdaTuning, ControlType.PID, 2.44, 5.5, 0.45);

		// Calculated values.
		ControllerParameter sut = IMC.computeLambdaTuning(ControlType.PID, firstOrderTF);
		assertEquals("Check Tuning Type", expectedParameters.getTuningType(), sut.getTuningType());
		assertEquals("Check Process Type", expectedParameters.getProcessType(), sut.getProcessType());
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
	}

	@Test
	public void when_computeLambdaTuningModelBasedIsCalled_then_returnCorrectPControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IMC,
				ProcessType.LambdaModelBasedTuning, ControlType.P, 0.5, 0, 0);

		// Arrange
		double gain = 0.5;
		double lambdaCoefficient = 4;

		modelBasedTF.setGain(gain);
		modelBasedTF.setLambdaCoefficient(lambdaCoefficient);
		modelBasedTF.setIMCModelType(IMCModelBasedType.P);

		// Calculated values.
		ControllerParameter sut = IMC.computeLambdaTuning(modelBasedTF);
		assertEquals("Check Tuning Type", expectedParameters.getTuningType(), sut.getTuningType());
		assertEquals("Check Process Type", expectedParameters.getProcessType(), sut.getProcessType());
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
	}

	@Test
	public void when_computeLambdaTuningModelBasedIsCalled_then_returnCorrectPDControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IMC,
				ProcessType.LambdaModelBasedTuning, ControlType.PD, 0.5, 0, 5);

		// Arrange
		double gain = 0.5;
		double timeConstant = 5.0;
		double lambdaCoefficient = 4;

		modelBasedTF.setGain(gain);
		modelBasedTF.setTimeConstant(timeConstant);
		modelBasedTF.setLambdaCoefficient(lambdaCoefficient);
		modelBasedTF.setIMCModelType(IMCModelBasedType.PD);

		// Calculated values.
		ControllerParameter sut = IMC.computeLambdaTuning(modelBasedTF);
		assertEquals("Check Tuning Type", expectedParameters.getTuningType(), sut.getTuningType());
		assertEquals("Check Process Type", expectedParameters.getProcessType(), sut.getProcessType());
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
	}

	@Test
	public void when_computeLambdaTuningModelBasedIsCalled_then_returnCorrectPIControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IMC,
				ProcessType.LambdaModelBasedTuning, ControlType.PI, 2.5, 5, 0);

		// Arrange
		double gain = 0.5;
		double timeConstant = 5.0;
		double lambdaCoefficient = 4;

		modelBasedTF.setGain(gain);
		modelBasedTF.setTimeConstant(timeConstant);
		modelBasedTF.setLambdaCoefficient(lambdaCoefficient);
		modelBasedTF.setIMCModelType(IMCModelBasedType.PI);

		// Calculated values.
		ControllerParameter sut = IMC.computeLambdaTuning(modelBasedTF);
		assertEquals("Check Tuning Type", expectedParameters.getTuningType(), sut.getTuningType());
		assertEquals("Check Process Type", expectedParameters.getProcessType(), sut.getProcessType());
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
	}

	@Test
	public void when_computeLambdaTuningModelBasedIsCalled_then_returnCorrectPID1ControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IMC,
				ProcessType.LambdaModelBasedTuning, ControlType.PID, 7.5, 15, 3.333);

		// Arrange
		double gain = 0.5;
		double timeConstant = 5.0;
		double lambdaCoefficient = 4;
		double secondTimeConstant = 10;

		modelBasedTF.setGain(gain);
		modelBasedTF.setTimeConstant(timeConstant);
		modelBasedTF.setSecondTimeConstant(secondTimeConstant);
		modelBasedTF.setLambdaCoefficient(lambdaCoefficient);
		modelBasedTF.setIMCModelType(IMCModelBasedType.PID1);

		// Calculated values.
		ControllerParameter sut = IMC.computeLambdaTuning(modelBasedTF);
		assertEquals("Check Tuning Type", expectedParameters.getTuningType(), sut.getTuningType());
		assertEquals("Check Process Type", expectedParameters.getProcessType(), sut.getProcessType());
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
	}

	@Test
	public void when_computeLambdaTuningModelBasedIsCalled_then_returnCorrectPID2ControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IMC,
				ProcessType.LambdaModelBasedTuning, ControlType.PID, 2.5, 5, 5);

		// Arrange
		double gain = 0.5;
		double timeConstant = 5.0;
		double dampingRatio = 0.5;
		double lambdaCoefficient = 4;

		modelBasedTF.setGain(gain);
		modelBasedTF.setTimeConstant(timeConstant);
		modelBasedTF.setDampingRatio(dampingRatio);
		modelBasedTF.setLambdaCoefficient(lambdaCoefficient);
		modelBasedTF.setIMCModelType(IMCModelBasedType.PID2);

		// Calculated values.
		ControllerParameter sut = IMC.computeLambdaTuning(modelBasedTF);
		assertEquals("Check Tuning Type", expectedParameters.getTuningType(), sut.getTuningType());
		assertEquals("Check Process Type", expectedParameters.getProcessType(), sut.getProcessType());
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
	}

	@Test(expected = InvalidParameterException.class)
	public void when_ComputeLambdaTuningIsInvalid_Expect_InvalidParameterException()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IMC,
				ProcessType.LambdaTuning, ControlType.PI, 2.75, 5.5, 0);

		// Calculated values.
		ControllerParameter sut = IMC.computeLambdaTuning(ControlType.P, firstOrderTF);
		assertEquals("Check Tuning Type", expectedParameters.getTuningType(), sut.getTuningType());
		assertEquals("Check Process Type", expectedParameters.getProcessType(), sut.getProcessType());
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
	}

	@Test(expected = InvalidParameterException.class)
	public void when_ComputeLambdaTuningModelBasedIsInvalid_Expect_InvalidParameterException()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IMC,
				ProcessType.LambdaModelBasedTuning, ControlType.P, 0.5, 0, 0);

		// Arrange
		double gain = 0.5;
		double lambdaCoefficient = 4;

		modelBasedTF.setGain(gain);
		modelBasedTF.setLambdaCoefficient(lambdaCoefficient);
		modelBasedTF.setIMCModelType(IMCModelBasedType.None);

		// Calculated values.
		ControllerParameter sut = IMC.computeLambdaTuning(modelBasedTF);
		assertEquals("Check Tuning Type", expectedParameters.getTuningType(), sut.getTuningType());
		assertEquals("Check Process Type", expectedParameters.getProcessType(), sut.getProcessType());
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
	}
}