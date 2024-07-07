package com.tunings.methods.test;

import com.tunings.methods.CC;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

import org.junit.Test;

import static org.junit.Assert.*;

public class CCTest
{
	private final double Gain           = 0.5;
	private final double TimeConstant   = 5.0;
	private final double TransportDelay = 1.0;

	@Test
	public void computeP()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.P, 11, 0, 0);

		// Calculated values.
		ControllerParameters sut = CC.Compute(ControlType.P, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
	}

	@Test
	public void computePD()
	{
		// Desired values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PD, 12.72, 0, 0.25);
		ControllerParameters sut = CC.Compute(ControlType.PD, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
	}

	@Test
	public void computePI()
	{
		// Desired values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PI, 9.1, 0.66, 0);
		ControllerParameters sut = CC.Compute(ControlType.PI, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.1);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.1);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.1);
	}

	@Test
	public void computePID()
	{
		// Desired values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PID, 14, 2.32, 0.36);
		ControllerParameters sut = CC.Compute(ControlType.PID, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
	}
}