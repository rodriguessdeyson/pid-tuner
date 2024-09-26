package com.domain.services.tuning;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.TuningType;

import org.junit.Test;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

public class IMCTest
{
	private final TransferFunction TF;
	public IMCTest()
	{
		double gain           = 0.5;
		double timeConstant   = 5.0;
		double transportDelay = 1.0;
		double lambdaFactor   = 4;
		TF = new TransferFunction(gain, timeConstant, transportDelay, lambdaFactor);
	}

	@Test(expected = InvalidParameterException.class)
	public void when_ComputePIsRequested_Expect_InvalidParameterException()
	{
		// Calculated values.
		ControllerParameter sut = IMC.ComputeLambdaTuning(ControlType.P, TF);
	}

	@Test
	public void computePI()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IMC,
				ProcessType.LambdaTuning, ControlType.PI, 2.75, 5.5, 0);

		// Calculated values.
		ControllerParameter sut = IMC.ComputeLambdaTuning(ControlType.PI, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computePID()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.IMC,
				ProcessType.LambdaTuning, ControlType.PID, 2.44, 5.5, 0.45);

		// Calculated values.
		ControllerParameter sut = IMC.ComputeLambdaTuning(ControlType.PID, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}
}