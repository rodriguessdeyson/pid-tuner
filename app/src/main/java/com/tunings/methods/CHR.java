package com.tunings.methods;

import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

import org.jetbrains.annotations.NotNull;

import java.security.InvalidParameterException;

public class CHR
{
	public static ControllerParameters ComputeServo(ControlType controlType, double kGain, double tTime, double tDelay)
	{
		switch (controlType)
		{
			case P:
				return ServoPController(kGain, tTime, tDelay);
			case PI:
				return ServoPIController(kGain, tTime, tDelay);
			case PID:
				return ServoPIDController(kGain, tTime, tDelay);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	public static ControllerParameters ComputeServo20UP(ControlType controlType, double kGain, double tTime, double tDelay)
	{
		switch (controlType)
		{
			case P:
				return Servo20UPPController(kGain, tTime, tDelay);
			case PI:
				return Servo20UPPIController(kGain, tTime, tDelay);
			case PID:
				return Servo20UPPIDController(kGain, tTime, tDelay);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	public static ControllerParameters ComputeRegulator(ControlType controlType, double kGain, double tTime, double tDelay)
	{
		switch (controlType)
		{
			case P:
				return RegulatorPController(kGain, tTime, tDelay);
			case PI:
				return RegulatorPIController(kGain, tTime, tDelay);
			case PID:
				return RegulatorPIDController(kGain, tTime, tDelay);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	public static ControllerParameters ComputeRegulator20UP(ControlType controlType, double kGain, double tTime, double tDelay)
	{
		switch (controlType)
		{
			case P:
				return Regulator20UPPController(kGain, tTime, tDelay);
			case PI:
				return Regulator20UPPIController(kGain, tTime, tDelay);
			case PID:
				return Regulator20UPPIDController(kGain, tTime, tDelay);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	private static ControllerParameters ServoPController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.3 * tTime) / (kGain * tDelay);
		double ki = 0;
		double kd = 0;

		return new ControllerParameters(ControlType.P, kp, ki, kd);
	}

	private static ControllerParameters ServoPIController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.35 * tTime) / (kGain * tDelay);
		double ki = 1.16 * tTime;
		double kd = 0;

		return new ControllerParameters(ControlType.PI, kp, ki, kd);
	}

	private static ControllerParameters ServoPIDController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.6 * tTime) / (kGain * tDelay);
		double ki = tTime;
		double kd = (tDelay / 2);

		return new ControllerParameters(ControlType.P, kp, ki, kd);
	}

	private static ControllerParameters Servo20UPPController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.7 * tTime) / (kGain * tDelay);
		double ki = 0;
		double kd = 0;

		return new ControllerParameters(ControlType.P, kp, ki, kd);
	}

	private static ControllerParameters Servo20UPPIController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.6 * tTime) / (kGain * tDelay);
		double ki = tTime;
		double kd = 0;

		return new ControllerParameters(ControlType.PI, kp, ki, kd);
	}

	private static ControllerParameters Servo20UPPIDController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.95 * tTime) / (kGain*tDelay);
		double ki = 1.357 * tTime;
		double kd = 0.473 * tDelay;

		return new ControllerParameters(ControlType.PID, kp, ki, kd);
	}

	private static ControllerParameters RegulatorPController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.3 * tTime) / (kGain * tDelay);
		double ki = 0;
		double kd = 0;

		return new ControllerParameters(ControlType.P, kp, ki, kd);
	}

	private static ControllerParameters RegulatorPIController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.6 * tTime) / (kGain * tDelay);
		double ki = 4 * tDelay;
		double kd = 0;

		return new ControllerParameters(ControlType.PI, kp, ki, kd);
	}

	private static ControllerParameters RegulatorPIDController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.95 * tTime) / (kGain * tDelay);
		double ki = 2.375 * tDelay;
		double kd = 0.421 * tDelay;

		return new ControllerParameters(ControlType.PID, kp, ki, kd);
	}

	private static ControllerParameters Regulator20UPPController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.7 * tTime) / tDelay;
		double ki = 0;
		double kd = 0;

		return new ControllerParameters(ControlType.P, kp, ki, kd);
	}

	private static ControllerParameters Regulator20UPPIController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.7 * tTime) / tDelay;
		double ki = 2.3 * tDelay;
		double kd = 0;

		return new ControllerParameters(ControlType.PI, kp, ki, kd);
	}

	private static ControllerParameters Regulator20UPPIDController(double kGain, double tTime, double tDelay)
	{
		double kp = (1.2 * tTime) / tDelay;
		double ki = 2 * tDelay;
		double kd = 0.42 * tDelay;

		return new ControllerParameters(ControlType.PID, kp, ki, kd);
	}
}

