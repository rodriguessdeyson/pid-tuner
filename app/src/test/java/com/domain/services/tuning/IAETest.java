package com.domain.services.tuning;

import org.junit.Test;

import static org.junit.Assert.*;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.TuningType;

import java.security.InvalidParameterException;

public class IAETest
{

	private final TransferFunction TF;
	public IAETest()
	{
		double gain = 0.5;
		double timeConstant = 5.0;
		double transportDelay = 1.0;
		TF = new TransferFunction(gain, timeConstant, transportDelay);
	}


	@Test
	public void when_computeRegulatorIsCalled_then_returnCorrectPIControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IAE,
				ProcessType.Regulator, ControlType.PI, 9.621, 2.636, 0);

		// Calculated values.
		ControllerParameter sut = IAE.computeRegulator(ControlType.PI, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void when_computeRegulatorIsCalled_then_returnCorrectPIDControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IAE,
				ProcessType.Regulator, ControlType.PID, 12.637, 1.706, 0.387);

		// Calculated values.
		ControllerParameter sut = IAE.computeRegulator(ControlType.PID, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void when_computeServoIsCalled_then_returnCorrectPIControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IAE,
				ProcessType.Servo, ControlType.PI, 6.06, 5.23, 0);

		// Calculated values.
		ControllerParameter sut = IAE.computeServo(ControlType.PI, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void when_computeServoIsCalled_then_returnCorrectPIDControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IAE,
				ProcessType.Servo, ControlType.PID, 8.796, 7.003, 0.400);

		// Calculated values.
		ControllerParameter sut = IAE.computeServo(ControlType.PID, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test(expected = InvalidParameterException.class)
	public void when_computeServoIsInvalid_then_throwException()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IAE,
				ProcessType.Servo, ControlType.P, 8.796, 7.003, 0.400);

		// Calculated values.
		ControllerParameter sut = IAE.computeServo(ControlType.P, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test(expected = InvalidParameterException.class)
	public void when_computeRegulatorIsInvalid_then_throwException()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IAE,
				ProcessType.Regulator, ControlType.PD, 12.637, 1.706, 0.387);

		// Calculated values.
		ControllerParameter sut = IAE.computeRegulator(ControlType.PD, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}
}