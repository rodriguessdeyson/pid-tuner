package com.domain.services.tuning;

import org.junit.Test;

import static org.junit.Assert.*;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.TuningType;

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
	public void computeRegulatorPI()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IAE,
				ProcessType.Regulator, ControlType.PI, 9.621, 2.636, 0);

		// Calculated values.
		ControllerParameter sut = IAE.ComputeRegulator(ControlType.PI, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computeRegulatorPID()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IAE,
				ProcessType.Regulator, ControlType.PID, 12.637, 1.706, 0.387);

		// Calculated values.
		ControllerParameter sut = IAE.ComputeRegulator(ControlType.PID, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computeServoPI()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IAE,
				ProcessType.Servo, ControlType.PI, 6.06, 5.23, 0);

		// Calculated values.
		ControllerParameter sut = IAE.ComputeServo(ControlType.PI, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computeServoPID()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IAE,
				ProcessType.Servo, ControlType.PID, 8.796, 7.003, 0.400);

		// Calculated values.
		ControllerParameter sut = IAE.ComputeServo(ControlType.PID, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}
}