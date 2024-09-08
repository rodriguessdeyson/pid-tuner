package com.tunings.methods.test;

import com.tunings.methods.TL;
import com.tunings.models.ProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameter;

import org.junit.Test;

import static org.junit.Assert.*;

public class TLTest
{
	private final double KuGain = 5;
	private final double PuGain = 2.56;

	@Test
	public void computeClosedPI()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(ControlType.PI, 1.56, 5.632, 0);

		// Calculated values.
		ControllerParameter sut = TL.Compute(ProcessType.Closed, ControlType.PI, KuGain, PuGain);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computeClosedPID()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(ControlType.PID, 2.27, 5.632, 0.41);

		// Calculated values.
		ControllerParameter sut = TL.Compute(ProcessType.Closed, ControlType.PID, KuGain, PuGain);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}
}