package com.domain.services.tuning;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.TuningType;

import org.junit.Test;

import static org.junit.Assert.*;

public class CHRTest
{
	private final TransferFunction TF;

	public CHRTest()
	{
		double gain = 0.5;
		double timeConstant = 5.0;
		double transportDelay = 1.0;

		TF = new TransferFunction(gain, timeConstant, transportDelay);
	}

	@Test
	public void computeP()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CHR,
				ProcessType.Servo, ControlType.P, 3, 0, 0);

		// Calculated values.
		ControllerParameter sut = CHR.ComputeServo(ControlType.P, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computePI()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CHR,
				ProcessType.Servo, ControlType.PI, 3.5, 5.8, 0);

		// Calculated values.
		ControllerParameter sut = CHR.ComputeServo(ControlType.PI, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computePID()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CHR,
				ProcessType.Servo, ControlType.PID, 6, 5, 0.5);

		// Calculated values.
		ControllerParameter sut = CHR.ComputeServo(ControlType.PID, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computePRegulator()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CHR,
				ProcessType.Regulator, ControlType.P, 3, 0, 0);

		// Calculated values.
		ControllerParameter sut = CHR.ComputeRegulator(ControlType.P, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computePIRegulator()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CHR,
				ProcessType.Regulator, ControlType.PI, 6, 4, 0);

		// Calculated values.
		ControllerParameter sut = CHR.ComputeRegulator(ControlType.PI, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computePIDRegulator()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CHR,
				ProcessType.Regulator, ControlType.PID, 9.5, 2.375, 0.421);

		// Calculated values.
		ControllerParameter sut = CHR.ComputeRegulator(ControlType.PID, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computeP20()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CHR,
				ProcessType.Servo20, ControlType.P, 7, 0, 0);

		// Calculated values.
		ControllerParameter sut = CHR.ComputeServo20UP(ControlType.P, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computePI20()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CHR,
				ProcessType.Servo20, ControlType.PI, 6, 5, 0);

		// Calculated values.
		ControllerParameter sut = CHR.ComputeServo20UP(ControlType.PI, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void computePID20()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CHR,
				ProcessType.Servo20, ControlType.PID, 9.5, 6.78, 0.473);

		// Calculated values.
		ControllerParameter sut = CHR.ComputeServo20UP(ControlType.PID, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}
}