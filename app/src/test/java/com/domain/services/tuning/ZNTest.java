package com.domain.services.tuning;

import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;

import org.junit.Test;

import static org.junit.Assert.*;

public class ZNTest
{
	private final double Gain           = 0.5;
	private final double TimeConstant   = 5.0;
	private final double TransportDelay = 1.0;

	@Test
	public void computeOpenP()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(ControlType.P, 10, 0, 0);

		// Calculated values.
		ControllerParameter sut = ZN.ComputeOpenLoop(ControlType.P, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computeOpenPI()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(ControlType.PI, 9, 3.33, 0);

		// Calculated values.
		ControllerParameter sut = ZN.ComputeOpenLoop(ControlType.PI, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computeOpenPID()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(ControlType.PID, 12, 2, 0.5);

		// Calculated values.
		ControllerParameter sut = ZN.ComputeOpenLoop(ControlType.PID, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computeClosedP()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(ControlType.P, 2.5, 0, 0);

		// Calculated values.
		ControllerParameter sut = ZN.ComputeClosedLoop(ControlType.P, 5.0, 9.0);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computeClosedPI()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(ControlType.PI, 2.25, 7.5, 0);

		// Calculated values.
		ControllerParameter sut = ZN.ComputeClosedLoop(ControlType.PI, 5.0, 9.0);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computeClosedPID()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(ControlType.PID, 3, 4.5, 1.12);

		// Calculated values.
		ControllerParameter sut = ZN.ComputeClosedLoop(ControlType.PID, 5.0, 9.0);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.1);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}
}