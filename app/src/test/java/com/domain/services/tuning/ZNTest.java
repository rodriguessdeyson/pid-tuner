package com.domain.services.tuning;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.TuningType;

import org.junit.Test;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

public class ZNTest
{
	private final TransferFunction TFOpenedLoop;
	private final TransferFunction TFClosedLoop;

	public ZNTest()
	{
		double gain           = 0.5;
		double timeConstant   = 5.0;
		double transportDelay = 1.0;
		double kuGain         = 5;
		double puGain         = 9.0;
		TFOpenedLoop = new TransferFunction(gain, timeConstant, transportDelay);
		TFClosedLoop = new TransferFunction(kuGain, puGain);
	}


	@Test
	public void when_computeOpenIsCalled_then_returnCorrectPControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.ZN,
				ProcessType.Open, ControlType.P, 10, 0, 0);

		// Calculated values.
		ControllerParameter sut = ZN.computeOpenLoop(ControlType.P, TFOpenedLoop);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void when_computeOpenIsCalled_then_returnCorrectPIControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.ZN,
				ProcessType.Open, ControlType.PI, 9, 3.33, 0);

		// Calculated values.
		ControllerParameter sut = ZN.computeOpenLoop(ControlType.PI, TFOpenedLoop);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void when_computeOpenIsCalled_then_returnCorrectPIDControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.ZN,
				ProcessType.Open, ControlType.PID, 12, 2, 0.5);

		// Calculated values.
		ControllerParameter sut = ZN.computeOpenLoop(ControlType.PID, TFOpenedLoop);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void when_computeClosedIsCalled_then_returnCorrectPControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.ZN,
				ProcessType.Closed, ControlType.P, 2.5, 0, 0);

		// Calculated values.
		ControllerParameter sut = ZN.computeClosedLoop(ControlType.P, TFClosedLoop);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void when_computeClosedIsCalled_then_returnCorrectPIControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.ZN,
				ProcessType.Closed, ControlType.PI, 2.25, 7.5, 0);

		// Calculated values.
		ControllerParameter sut = ZN.computeClosedLoop(ControlType.PI, TFClosedLoop);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void when_computeClosedIsCalled_then_returnCorrectPIDControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.ZN,
				ProcessType.Closed, ControlType.PID, 3, 4.5, 1.12);

		// Calculated values.
		ControllerParameter sut = ZN.computeClosedLoop(ControlType.PID, TFClosedLoop);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.1);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test(expected = InvalidParameterException.class)
	public void when_computeOpenIsInvalid_then_throwException()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.ZN,
				ProcessType.Open, ControlType.PD, 10, 0, 2);

		// Calculated values.
		ControllerParameter sut = ZN.computeOpenLoop(ControlType.PD, TFOpenedLoop);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test(expected = InvalidParameterException.class)
	public void when_computeClosedIsInvalid_then_throwException()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.ZN,
				ProcessType.Closed, ControlType.PD, 10, 0, 2);

		// Calculated values.
		ControllerParameter sut = ZN.computeClosedLoop(ControlType.PD, TFOpenedLoop);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}
}