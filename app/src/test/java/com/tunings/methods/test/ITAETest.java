package com.tunings.methods.test;

import com.tunings.methods.ITAE;
import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

import org.junit.Test;

import static org.junit.Assert.*;

public class ITAETest
{
	private double Gain           = 0.5;
	private double TimeConstant   = 5.0;
	private double TransportDelay = 1.0;

	@Test
	public void computeRegulatorPI()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PI, 8.27, 2.48, 0);

		// Calculated values.
		ControllerParameters actualParameters = ITAE
			.Compute(ControlType.PI, ControlProcessType.Regulator, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}

	@Test
	public void computeRegulatorPID()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PID, 12.46, 1.81, 0.384);

		// Calculated values.
		ControllerParameters actualParameters = ITAE
			.Compute(ControlType.PID, ControlProcessType.Regulator, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}

	@Test
	public void computeServoPI()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PI, 5.11, 5.01, 0);

		// Calculated values.
		ControllerParameters actualParameters = ITAE
			.Compute(ControlType.PI, ControlProcessType.Servo, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}

	@Test
	public void computeServoPID()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PID, 7.58, 6.52, 0.345);

		// Calculated values.
		ControllerParameters actualParameters = ITAE
			.Compute(ControlType.PID, ControlProcessType.Servo, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}
}