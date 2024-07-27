package com.tunings.methods.test;

import com.tunings.methods.ITAE;
import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

import org.junit.Test;

import static org.junit.Assert.*;

public class ITAETest
{
	private final double Gain           = 0.5;
	private final double TimeConstant   = 5.0;
	private final double TransportDelay = 1.0;

	@Test
	public void computeRegulatorPI()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlProcessType.Regulator, ControlType.PI, 8.27, 2.48, 0);

		// Calculated values.
		ControllerParameters sut = ITAE.ComputerRegulator(ControlType.PI, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Control Process Type", expectedParameters.getControlProcessType(), sut.getControlProcessType());
	}

	@Test
	public void computeRegulatorPID()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlProcessType.Regulator, ControlType.PID, 12.46, 1.81, 0.384);

		// Calculated values.
		ControllerParameters sut = ITAE.ComputerRegulator(ControlType.PID, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Control Process Type", expectedParameters.getControlProcessType(), sut.getControlProcessType());
	}

	@Test
	public void computeServoPI()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlProcessType.Servo, ControlType.PI, 5.11, 5.01, 0);

		// Calculated values.
		ControllerParameters sut = ITAE.ComputeServo(ControlType.PI, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Control Process Type", expectedParameters.getControlProcessType(), sut.getControlProcessType());
	}

	@Test
	public void computeServoPID()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlProcessType.Servo, ControlType.PID, 7.58, 6.52, 0.345);

		// Calculated values.
		ControllerParameters sut = ITAE.ComputeServo(ControlType.PID, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
		assertEquals("Check Control Process Type", expectedParameters.getControlProcessType(), sut.getControlProcessType());
	}
}