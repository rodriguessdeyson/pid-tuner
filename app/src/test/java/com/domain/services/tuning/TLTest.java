package com.domain.services.tuning;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.TuningType;

import org.junit.Test;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

public class TLTest
{
	private final TransferFunction TF;

	public TLTest()
	{
		double kuGain = 5;
		double puGain = 2.56;
		TF = new TransferFunction(kuGain, puGain);
	}

	@Test
	public void when_computeIsCalled_then_returnCorrectPIControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.TL,
				ProcessType.Closed, ControlType.PI, 1.56, 5.632, 0);

		// Calculated values.
		ControllerParameter sut = TL.compute(ControlType.PI, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void when_computeIsCalled_then_returnCorrectPIDControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.TL,
				ProcessType.Closed, ControlType.PID, 2.27, 5.632, 0.41);

		// Calculated values.
		ControllerParameter sut = TL.compute(ControlType.PID, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test(expected = InvalidParameterException.class)
	public void when_computeControlTypeIsInvalid_then_throwException()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.TL,
				ProcessType.Closed, ControlType.P, 2.27, 5.632, 0.41);

		// Calculated values.
		ControllerParameter sut = TL.compute(ControlType.P, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}
}