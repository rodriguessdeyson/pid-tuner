package com.tunings.methods.test;

import com.tunings.methods.CHR;
import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

import org.junit.Test;

import static org.junit.Assert.*;

public class CHRTest
{
	private double Gain           = 0.5;
	private double TimeConstant   = 5.0;
	private double TransportDelay = 1.0;

	@Test
	public void computeP()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.P, 3, 0, 0);

		// Calculated values.
		ControllerParameters actualParameters = CHR
			.Compute(ControlType.P, ControlProcessType.Servo, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}

	@Test
	public void computePI()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PI, 3.5, 5.8, 0);

		// Calculated values.
		ControllerParameters actualParameters = CHR
			.Compute(ControlType.PI, ControlProcessType.Servo, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}

	@Test
	public void computePID()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PID, 6, 5, 0.5);

		// Calculated values.
		ControllerParameters actualParameters = CHR
			.Compute(ControlType.PID, ControlProcessType.Servo, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}

	@Test
	public void computePRegulator()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.P, 3, 0, 0);

		// Calculated values.
		ControllerParameters actualParameters = CHR
			.Compute(ControlType.P, ControlProcessType.Regulator, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}

	@Test
	public void computePIRegulator()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PI, 6, 4, 0);

		// Calculated values.
		ControllerParameters actualParameters = CHR
			.Compute(ControlType.PI, ControlProcessType.Regulator, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}

	@Test
	public void computePIDRegulator()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PID, 9.5, 2.375, 0.421);

		// Calculated values.
		ControllerParameters actualParameters = CHR
			.Compute(ControlType.PID, ControlProcessType.Regulator, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}

	@Test
	public void computeP20()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.P, 7, 0, 0);

		// Calculated values.
		ControllerParameters actualParameters = CHR
			.Compute(ControlType.P, ControlProcessType.Servo20, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}

	@Test
	public void computePI20()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PI, 6, 5, 0);

		// Calculated values.
		ControllerParameters actualParameters = CHR
			.Compute(ControlType.PI, ControlProcessType.Servo20, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}

	@Test
	public void computePID20()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PID, 9.5, 6.78, 0.473);

		// Calculated values.
		ControllerParameters actualParameters = CHR
			.Compute(ControlType.PID, ControlProcessType.Servo20, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}
}