package com.tunings.methods.test;

import com.tunings.methods.CC;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

import org.junit.Test;

import static org.junit.Assert.*;

public class CCTest
{
	private double Gain           = 0.5;
	private double TimeConstant   = 5.0;
	private double TransportDelay = 1.0;

	@Test
	public void computeP()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.P, 11, 0, 0);

		// Calculated values.
		ControllerParameters actualParameters = CC.Compute(ControlType.P, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}

	@Test
	public void computePD()
	{
		// Desired values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PD, 14, 2.32, 0.36);
		ControllerParameters actualParameters = CC.Compute(ControlType.PD, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}

	@Test
	public void computePI()
	{
		// Desired values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PI, 9.1, 0.66, 0);
		ControllerParameters actualParameters = CC.Compute(ControlType.PI, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.1);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.1);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.1);
	}

	@Test
	public void computePID()
	{
		// Desired values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PID, 14, 2.32, 0.36);
		ControllerParameters actualParameters = CC.Compute(ControlType.PID, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}
}