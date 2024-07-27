package com.tunings.methods.test;

import com.tunings.methods.CHR;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

import org.junit.Test;

import static org.junit.Assert.*;

public class CHRTest
{
	private final double Gain           = 0.5;
	private final double TimeConstant   = 5.0;
	private final double TransportDelay = 1.0;

	@Test
	public void computeP()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.P, 3, 0, 0);

		// Calculated values.
		ControllerParameters sut = CHR.ComputeServo(ControlType.P, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Control Process Type", expectedParameters.getControlProcessType(), sut.getControlProcessType());
	}

	@Test
	public void computePI()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PI, 3.5, 5.8, 0);

		// Calculated values.
		ControllerParameters sut = CHR.ComputeServo(ControlType.PI, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Control Process Type", expectedParameters.getControlProcessType(), sut.getControlProcessType());
	}

	@Test
	public void computePID()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PID, 6, 5, 0.5);

		// Calculated values.
		ControllerParameters sut = CHR.ComputeServo(ControlType.PID, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Control Process Type", expectedParameters.getControlProcessType(), sut.getControlProcessType());
	}

	@Test
	public void computePRegulator()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.P, 3, 0, 0);

		// Calculated values.
		ControllerParameters sut = CHR.ComputeRegulator(ControlType.P, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Control Process Type", expectedParameters.getControlProcessType(), sut.getControlProcessType());
	}

	@Test
	public void computePIRegulator()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PI, 6, 4, 0);

		// Calculated values.
		ControllerParameters sut = CHR.ComputeRegulator(ControlType.PI, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Control Process Type", expectedParameters.getControlProcessType(), sut.getControlProcessType());
	}

	@Test
	public void computePIDRegulator()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PID, 9.5, 2.375, 0.421);

		// Calculated values.
		ControllerParameters sut = CHR.ComputeRegulator(ControlType.PID, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Control Process Type", expectedParameters.getControlProcessType(), sut.getControlProcessType());
	}

	@Test
	public void computeP20()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.P, 7, 0, 0);

		// Calculated values.
		ControllerParameters sut = CHR.ComputeServo20UP(ControlType.P, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Control Process Type", expectedParameters.getControlProcessType(), sut.getControlProcessType());
	}

	@Test
	public void computePI20()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PI, 6, 5, 0);

		// Calculated values.
		ControllerParameters sut = CHR.ComputeServo20UP(ControlType.PI, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Control Process Type", expectedParameters.getControlProcessType(), sut.getControlProcessType());
	}

	@Test
	public void computePID20()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PID, 9.5, 6.78, 0.473);

		// Calculated values.
		ControllerParameters sut = CHR.ComputeServo20UP(ControlType.PID, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Control Process Type", expectedParameters.getControlProcessType(), sut.getControlProcessType());
	}
}