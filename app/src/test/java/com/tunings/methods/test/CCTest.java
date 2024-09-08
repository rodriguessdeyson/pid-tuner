package com.tunings.methods.test;

import com.tunings.methods.CC;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameter;

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
		ControllerParameter expectedParameters = new ControllerParameter(ControlType.P, 11, 0, 0);

		// Calculated values.
		ControllerParameter sut = CC.Compute(ControlType.P, Gain, TimeConstant, TransportDelay);

		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computePD()
	{
		// Desired values.
		ControllerParameter expectedParameters = new ControllerParameter(ControlType.PD, 12.72, 0, 0.25);
		ControllerParameter sut = CC.Compute(ControlType.PD, Gain, TimeConstant, TransportDelay);

		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computePI()
	{
		// Desired values.
		ControllerParameter expectedParameters = new ControllerParameter(ControlType.PI, 9.1, 0.66, 0);
		ControllerParameter sut = CC.Compute(ControlType.PI, Gain, TimeConstant, TransportDelay);

		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.1);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.1);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.1);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computePID()
	{
		// Desired values.
		ControllerParameter expectedParameters = new ControllerParameter(ControlType.PID, 14, 2.32, 0.36);
		ControllerParameter sut = CC.Compute(ControlType.PID, Gain, TimeConstant, TransportDelay);

		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}
}