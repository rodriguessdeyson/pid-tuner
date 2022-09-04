package com.tunings.methods.test;

import com.tunings.methods.ZN;
import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

import org.junit.Test;

import static org.junit.Assert.*;

public class ZNTest
{
	private double Gain           = 0.5;
	private double TimeConstant   = 5.0;
	private double TransportDelay = 1.0;

	@Test
	public void computeP()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.P, 10, 0, 0);

		// Calculated values.
		ControllerParameters actualParameters = ZN.Compute(ControlProcessType.Open, ControlType.P, Gain, TimeConstant, TransportDelay, 0.0, 0.0);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}

	@Test
	public void computePI()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PI, 9, 3.33, 0);

		// Calculated values.
		ControllerParameters actualParameters = ZN.Compute(ControlProcessType.Open, ControlType.PI, Gain, TimeConstant, TransportDelay, 0.0, 0.0);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}

	@Test
	public void computePID()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PID, 12, 2, 0.5);

		// Calculated values.
		ControllerParameters actualParameters = ZN.Compute(ControlProcessType.Open, ControlType.PID, Gain, TimeConstant, TransportDelay, 0.0, 0.0);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}

	@Test
	public void computeClosedP()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.P, 2.5, 0, 0);

		// Calculated values.
		ControllerParameters actualParameters = ZN
			.Compute(ControlProcessType.Closed, ControlType.P, 0.0, 0.0, 0.0, 5.0, 9.0);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}

	@Test
	public void computeClosedPI()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PI, 2.25, 7.5, 0);

		// Calculated values.
		ControllerParameters actualParameters = ZN
			.Compute(ControlProcessType.Closed, ControlType.PI, 0.0, 0.0, 0.0, 5.0, 9.0);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}

	@Test
	public void computeClosedPID()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PID, 3, 4.5, 1.12);

		// Calculated values.
		ControllerParameters actualParameters = ZN
			.Compute(ControlProcessType.Closed, ControlType.PID, 0.0, 0.0, 0.0, 5.0, 9.0);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.1);
	}
}