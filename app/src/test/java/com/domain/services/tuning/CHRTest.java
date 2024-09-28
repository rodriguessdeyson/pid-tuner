package com.domain.services.tuning;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.TuningType;

import org.junit.Test;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

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
	public void when_computeServoIsCalled_then_returnCorrectPControllerValues()
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
	public void when_computeServoIsCalled_then_returnCorrectPIControllerValues()
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
	public void when_computeServoIsCalled_then_returnCorrectPIDControllerValues()
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
	public void when_computeRegulatorIsCalled_then_returnCorrectPControllerValues()
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
	public void when_computeRegulatorIsCalled_then_returnCorrectPIControllerValues()
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
	public void when_computeRegulatorIsCalled_then_returnCorrectPIDControllerValues()
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
	public void when_computeServo20UPIsCalled_then_returnCorrectPControllerValues()
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
	public void when_computeServo20UPIsCalled_then_returnCorrectPIControllerValues()
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
	public void when_computeServo20UPIsCalled_then_returnCorrectPIDControllerValues()
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

	@Test
	public void when_computeRegulator20UPIsCalled_then_returnCorrectPControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CHR,
				ProcessType.Regulator20, ControlType.P, 3.5, 0, 0);

		// Calculated values.
		ControllerParameter sut = CHR.ComputeRegulator20UP(ControlType.P, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void when_computeRegulator20UPIsCalled_then_returnCorrectPIControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CHR,
				ProcessType.Regulator20, ControlType.PI, 3.5, 2.3, 0);

		// Calculated values.
		ControllerParameter sut = CHR.ComputeRegulator20UP(ControlType.PI, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test
	public void when_computeRegulator20UPIsCalled_then_returnCorrectPIDControllerValues()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CHR,
				ProcessType.Regulator20, ControlType.PID, 6, 2, 0.42);

		// Calculated values.
		ControllerParameter sut = CHR.ComputeRegulator20UP(ControlType.PID, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test(expected = InvalidParameterException.class)
	public void when_computeServoIsCalled_then_throwExceptionForInvalidControlType()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CHR,
				ProcessType.Servo, ControlType.PID, 6, 5, 0.5);

		// Calculated values.
		ControllerParameter sut = CHR.ComputeServo(ControlType.None, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test(expected = InvalidParameterException.class)
	public void when_computeServo20UPIsCalled_then_throwExceptionForInvalidControlType()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CHR,
				ProcessType.Servo20, ControlType.PID, 9.5, 6.78, 0.473);

		// Calculated values.
		ControllerParameter sut = CHR.ComputeServo20UP(ControlType.None, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test(expected = InvalidParameterException.class)
	public void when_computeRegulatorIsCalled_then_throwExceptionForInvalidControlType()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CHR,
				ProcessType.Regulator, ControlType.PI, 6, 4, 0);

		// Calculated values.
		ControllerParameter sut = CHR.ComputeRegulator(ControlType.None, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}

	@Test(expected = InvalidParameterException.class)
	public void when_computeRegulator20UPIsCalled_then_throwExceptionForInvalidControlType()
	{
		// Expected values.
		ControllerParameter expectedParameters = new ControllerParameter(TuningType.CHR,
				ProcessType.Regulator20, ControlType.PID, 6, 2, 0.42);

		// Calculated values.
		ControllerParameter sut = CHR.ComputeRegulator20UP(ControlType.None, TF);
		assertEquals("Check Kp parameters", expectedParameters.getKP(), sut.getKP(), 0.01);
		assertEquals("Check Ki parameters", expectedParameters.getKI(), sut.getKI(), 0.01);
		assertEquals("Check Kd parameters", expectedParameters.getKD(), sut.getKD(), 0.01);
		assertEquals("Check Control Type", expectedParameters.getControlType(), sut.getControlType());
	}
}