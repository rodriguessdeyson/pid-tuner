package com.domain.services.tuning;

import static org.junit.Assert.assertEquals;

import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.TuningType;

import org.junit.Assert;
import org.junit.Test;

import java.security.InvalidParameterException;

public class RTTest
{

	private final TransferFunction TF;

	public RTTest()
	{
		double kuGain = 5;
		double puGain = 2.56;
		TF = new TransferFunction(kuGain, puGain);
	}

	@Test
	public void when_computeIsCalled_then_returnCorrectPIDControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.RT,
				ProcessType.Open, ControlType.PID, 2.5, 2.048, 0.2048);

		// Calculated values.
		ControllerParameter sut = RT.Compute(ControlType.PID, TF);
		assertEquals("Check Tuning Type", expectedParameters.getTuningType(), sut.getTuningType());
		assertEquals("Check Process Type", expectedParameters.getProcessType(), sut.getProcessType());
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
	}

	@Test(expected = InvalidParameterException.class)
	public void when_computeControlTypeIsInvalid_then_throwException()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.RT,
				ProcessType.Open, ControlType.PI, 2.5, 2.048, 0.2048);

		// Calculated values.
		ControllerParameter sut = RT.Compute(ControlType.PI, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
	}
}