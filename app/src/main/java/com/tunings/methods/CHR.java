package com.tunings.methods;

import com.tunings.models.ProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameter;

import java.security.InvalidParameterException;

public class CHR
{
	public static ControllerParameter ComputeServo(ControlType controlType, double kGain, double tTime, double tDelay)
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

	public static ControllerParameter ComputeServo20UP(ControlType controlType, double kGain, double tTime, double tDelay)
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

	public static ControllerParameter ComputeRegulator(ControlType controlType, double kGain, double tTime, double tDelay)
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

	public static ControllerParameter ComputeRegulator20UP(ControlType controlType, double kGain, double tTime, double tDelay)
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

	private static ControllerParameter ServoPController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.3 * tTime) / (kGain * tDelay);
		double ki = 0;
		double kd = 0;

		return new ControllerParameter(ControlType.P, kp, ki, kd);
	}

	private static ControllerParameter ServoPIController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.35 * tTime) / (kGain * tDelay);
		double ki = 1.16 * tTime;
		double kd = 0;

		return new ControllerParameter(ControlType.PI, kp, ki, kd);
	}

	private static ControllerParameter ServoPIDController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.6 * tTime) / (kGain * tDelay);
		double ki = tTime;
		double kd = (tDelay / 2);

		return new ControllerParameter(ControlType.P, kp, ki, kd);
	}

	private static ControllerParameter Servo20UPPController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.7 * tTime) / (kGain * tDelay);
		double ki = 0;
		double kd = 0;

		return new ControllerParameter(ControlType.P, kp, ki, kd);
	}

	private static ControllerParameter Servo20UPPIController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.6 * tTime) / (kGain * tDelay);
		double ki = tTime;
		double kd = 0;

		return new ControllerParameter(ControlType.PI, kp, ki, kd);
	}

	private static ControllerParameter Servo20UPPIDController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.95 * tTime) / (kGain*tDelay);
		double ki = 1.357 * tTime;
		double kd = 0.473 * tDelay;

		return new ControllerParameter(ControlType.PID, kp, ki, kd);
	}

	private static ControllerParameter RegulatorPController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.3 * tTime) / (kGain * tDelay);
		double ki = 0;
		double kd = 0;

		return new ControllerParameter(ControlType.P, kp, ki, kd);
	}

	private static ControllerParameter RegulatorPIController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.6 * tTime) / (kGain * tDelay);
		double ki = 4 * tDelay;
		double kd = 0;

		return new ControllerParameter(ControlType.PI, kp, ki, kd);
	}

	private static ControllerParameter RegulatorPIDController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.95 * tTime) / (kGain * tDelay);
		double ki = 2.375 * tDelay;
		double kd = 0.421 * tDelay;

		return new ControllerParameter(ControlType.PID, kp, ki, kd);
	}

	private static ControllerParameter Regulator20UPPController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.7 * tTime) / tDelay;
		double ki = 0;
		double kd = 0;

		return new ControllerParameter(ControlType.P, kp, ki, kd);
	}

	private static ControllerParameter Regulator20UPPIController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.7 * tTime) / tDelay;
		double ki = 2.3 * tDelay;
		double kd = 0;

		return new ControllerParameter(ControlType.PI, kp, ki, kd);
	}

	private static ControllerParameter Regulator20UPPIDController(double kGain, double tTime, double tDelay)
	{
		double kp = (1.2 * tTime) / tDelay;
		double ki = 2 * tDelay;
		double kd = 0.42 * tDelay;

		return new ControllerParameter(ControlType.PID, kp, ki, kd);
	}
}

