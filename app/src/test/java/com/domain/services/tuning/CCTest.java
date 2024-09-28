package com.domain.services.tuning;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.TuningType;

import org.junit.Test;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;
import java.util.Enumeration;

public class CCTest
{
	private final TransferFunction TF;

	public CCTest()
	{
		double gain = 0.5;
		double timeConstant = 5.0;
		double transportDelay = 1.0;
		TF = new TransferFunction(gain, timeConstant, transportDelay);
	}

	@Test
	public void when_computeIsCalled_then_returnCorrectPControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CC,
				ProcessType.None, ControlType.P, 11, 0, 0);

		// Calculated values.
		ControllerParameter sut = CC.Compute(ControlType.P, TF);

		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void when_computeIsCalled_then_returnCorrectPDControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CC,
				ProcessType.None, ControlType.PD, 12.72, 0, 0.25);

		// Calculated values.
		ControllerParameter sut = CC.Compute(ControlType.PD, TF);

		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void when_computeIsCalled_then_returnCorrectPIControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CC,
				ProcessType.None, ControlType.PI, 9.1, 0.66, 0);

		// Calculated values.
		ControllerParameter sut = CC.Compute(ControlType.PI, TF);

		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.1);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.1);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.1);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void when_computeIsCalled_then_returnCorrectPIDControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CC,
				ProcessType.None, ControlType.PID, 14, 2.32, 0.36);

		// Calculated values.
		ControllerParameter sut = CC.Compute(ControlType.PID, TF);

		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test(expected = InvalidParameterException.class)
	public void when_computeIsInvalid_then_throwException()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CC,
				ProcessType.None, ControlType.P, 11, 0, 0);

		// Calculated values.
		ControllerParameter sut = CC.Compute(ControlType.None, TF);

		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}
}