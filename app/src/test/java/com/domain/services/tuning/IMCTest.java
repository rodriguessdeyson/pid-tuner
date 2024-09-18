package com.domain.services.tuning;

import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;

import org.junit.Test;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

public class IMCTest
{
	private final double Gain           = 0.5;
	private final double TimeConstant   = 5.0;
	private final double TransportDelay = 1.0;
	private final double LambdaFactor   = 4;

	@Test(expected = InvalidParameterException.class)
	public void when_ComputePIsRequested_Expect_InvalidParameterException()
	{
		// Calculated values.
		ControllerParameter sut = IMC.ComputeLambdaTuning(ControlType.P, LambdaFactor, Gain, TimeConstant, TransportDelay);
	}

	@Test(expected = InvalidParameterException.class)
	public void when_ProcessIsNotLambdaTurning_Expect_InvalidParameterException()
	{
		// Calculated values.
		ControllerParameter sut = IMC.ComputeLambdaTuning(ControlType.P, LambdaFactor, Gain, TimeConstant, TransportDelay);
	}

	@Test
	public void computePI()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(ControlType.PI, 2.75, 5.5, 0);

		// Calculated values.
		ControllerParameter sut = IMC.ComputeLambdaTuning(ControlType.PI, LambdaFactor, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computePID()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(ControlType.PID, 2.44, 5.5, 0.45);

		// Calculated values.
		ControllerParameter sut = IMC.ComputeLambdaTuning(ControlType.PID, LambdaFactor, Gain, TimeConstant, TransportDelay);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}
}