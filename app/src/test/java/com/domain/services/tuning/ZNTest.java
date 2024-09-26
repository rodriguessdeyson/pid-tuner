package com.domain.services.tuning;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.TuningType;

import org.junit.Test;

import static org.junit.Assert.*;

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
	public void computeOpenP()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.ZN,
				ProcessType.Open, ControlType.P, 10, 0, 0);

		// Calculated values.
		ControllerParameter sut = ZN.ComputeOpenLoop(ControlType.P, TFOpenedLoop);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computeOpenPI()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.ZN,
				ProcessType.Open, ControlType.PI, 9, 3.33, 0);

		// Calculated values.
		ControllerParameter sut = ZN.ComputeOpenLoop(ControlType.PI, TFOpenedLoop);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computeOpenPID()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.ZN,
				ProcessType.Open, ControlType.PID, 12, 2, 0.5);

		// Calculated values.
		ControllerParameter sut = ZN.ComputeOpenLoop(ControlType.PID, TFOpenedLoop);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computeClosedP()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.ZN,
				ProcessType.Closed, ControlType.P, 2.5, 0, 0);

		// Calculated values.
		ControllerParameter sut = ZN.ComputeClosedLoop(ControlType.P, TFClosedLoop);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computeClosedPI()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.ZN,
				ProcessType.Closed, ControlType.PI, 2.25, 7.5, 0);

		// Calculated values.
		ControllerParameter sut = ZN.ComputeClosedLoop(ControlType.PI, TFClosedLoop);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computeClosedPID()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.ZN,
				ProcessType.Closed, ControlType.PID, 3, 4.5, 1.12);

		// Calculated values.
		ControllerParameter sut = ZN.ComputeClosedLoop(ControlType.PID, TFClosedLoop);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.1);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}
}