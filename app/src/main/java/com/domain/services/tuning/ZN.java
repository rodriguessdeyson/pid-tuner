package com.domain.services.tuning;

import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;

import java.security.InvalidParameterException;

public class ZN
{
	public static ControllerParameter ComputeOpenLoop(ControlType controlType, double kGain, double tTime, double tDelay)
	{
		switch (controlType)
		{
			case P:
				return OpenLoopPController(kGain, tTime, tDelay);
			case PI:
				return OpenLoopPIController(kGain, tTime, tDelay);
			case PID:
				return OpenLoopPIDController(kGain, tTime, tDelay);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	public static ControllerParameter ComputeClosedLoop(ControlType controlType, double kuGain, double uPeriod)
	{
		switch (controlType)
		{
			case P:
				return ClosedLoopPController(kuGain, uPeriod);
			case PI:
				return ClosedLoopPIController(kuGain, uPeriod);
			case PID:
				return ClosedLoopPIDController(kuGain, uPeriod);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	private static ControllerParameter OpenLoopPController(double kGain, double tTime, double tDelay)
	{
		double kp = tTime / (kGain * tDelay);
		double ki = 0;
		double kd = 0;

		return new ControllerParameter(ControlType.P, kp, ki, kd);
	}

	private static ControllerParameter OpenLoopPIController(double kGain, double tTime, double tDelay)
	{
		double kp = 0.9 * tTime / (kGain * tDelay);
		double ki = 3.33 * tDelay;
		double kd = 0;

		return new ControllerParameter(ControlType.PI, kp, ki, kd);
	}

	private static ControllerParameter OpenLoopPIDController(double kGain, double tTime, double tDelay)
	{
		double kp = 1.2 * tTime / (kGain * tDelay);
		double ki = 2 * tDelay;
		double kd = 0.5 * tDelay;

		return new ControllerParameter(ControlType.PID, kp, ki, kd);
	}

	private static ControllerParameter ClosedLoopPController(double kuGain, double uPeriod)
	{
		double kp = 0.5 * kuGain;
		double ki = 0;
		double kd = 0;

		return new ControllerParameter(ControlType.P, kp, ki, kd);
	}

	private static ControllerParameter ClosedLoopPIController(double kuGain, double uPeriod)
	{
		double kp = 0.45 * kuGain;
		double ki = (uPeriod / 1.2);
		double kd = 0;

		return new ControllerParameter(ControlType.PI, kp, ki, kd);
	}

	private static ControllerParameter ClosedLoopPIDController(double kuGain, double uPeriod)
	{
		double kp = 0.6 * kuGain;
		double ki = uPeriod / 2;
		double kd = uPeriod / 8;

		return new ControllerParameter(ControlType.PID, kp, ki, kd);
	}
}
