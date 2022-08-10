package com.tunings.methods.test;

import com.tunings.methods.IMC;
import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

import org.junit.Test;

import static org.junit.Assert.*;

public class IMCTest
{
	private double Gain           = 0.5;
	private double TimeConstant   = 5.0;
	private double TransportDelay = 1.0;
	private double LambdaFactor   = 4;

	@Test
	public void computePI()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PI, 2.75, 5.5, 0);

		// Calculated values.
		ControllerParameters actualParameters = IMC
			.Compute(ControlType.PI, ControlProcessType.LambdaTuning, LambdaFactor, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}

	@Test
	public void computePID()
	{
		// Expected values.
		ControllerParameters expectedParameters = new ControllerParameters(ControlType.PID, 2.44, 5.5, 0.45);

		// Calculated values.
		ControllerParameters actualParameters = IMC
			.Compute(ControlType.PID, ControlProcessType.LambdaTuning, LambdaFactor, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), actualParameters.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), actualParameters.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), actualParameters.getKD(), 0.01);
	}



	@Test
	public void testCompute() {
	}
}