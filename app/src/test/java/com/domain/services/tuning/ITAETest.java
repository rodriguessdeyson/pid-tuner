package com.domain.services.tuning;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.TuningType;

import org.junit.Test;

import static org.junit.Assert.*;

public class ITAETest
{
	private final TransferFunction TF;

	public ITAETest()
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
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.ITAE,
				ProcessType.Regulator, ControlType.PI, 8.27, 2.48, 0);

		// Calculated values.
		ControllerParameter sut = ITAE.ComputeRegulator(ControlType.PI, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computeRegulatorPID()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.ITAE,
				ProcessType.Regulator, ControlType.PID, 12.46, 1.81, 0.384);

		// Calculated values.
		ControllerParameter sut = ITAE.ComputeRegulator(ControlType.PID, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computeServoPI()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.ITAE,
				ProcessType.Servo, ControlType.PI, 5.11, 5.01, 0);

		// Calculated values.
		ControllerParameter sut = ITAE.ComputeServo(ControlType.PI, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computeServoPID()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.ITAE,
				ProcessType.Servo, ControlType.PID, 7.58, 6.52, 0.345);

		// Calculated values.
		ControllerParameter sut = ITAE.ComputeServo(ControlType.PID, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}
}