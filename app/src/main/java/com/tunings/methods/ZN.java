package com.tunings.methods;

import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

import java.security.InvalidParameterException;

public class ZN
{
	public static ControllerParameters ComputeOpenLoop(ControlType controlType, double kGain, double tTime, double tDelay)
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

	public static ControllerParameters ComputeClosedLoop(ControlType controlType, double kuGain, double uPeriod)
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

	private static ControllerParameters OpenLoopPController(double kGain, double tTime, double tDelay)
	{
		double kp = tTime / (kGain * tDelay);
		double ki = 0;
		double kd = 0;

		return new ControllerParameters(ControlType.P, kp, ki, kd);
	}

	private static ControllerParameters OpenLoopPIController(double kGain, double tTime, double tDelay)
	{
		double kp = 0.9 * tTime / (kGain * tDelay);
		double ki = 3.33 * tDelay;
		double kd = 0;

		return new ControllerParameters(ControlType.PI, kp, ki, kd);
	}

	private static ControllerParameters OpenLoopPIDController(double kGain, double tTime, double tDelay)
	{
		double kp = 1.2 * tTime / (kGain * tDelay);
		double ki = 2 * tDelay;
		double kd = 0.5 * tDelay;

		return new ControllerParameters(ControlType.PID, kp, ki, kd);
	}

	private static ControllerParameters ClosedLoopPController(double kuGain, double uPeriod)
	{
		double kp = 0.5 * kuGain;
		double ki = 0;
		double kd = 0;

		return new ControllerParameters(ControlType.P, kp, ki, kd);
	}

	private static ControllerParameters ClosedLoopPIController(double kuGain, double uPeriod)
	{
		double kp = 0.45 * kuGain;
		double ki = (uPeriod / 1.2);
		double kd = 0;

		return new ControllerParameters(ControlType.PI, kp, ki, kd);
	}

	private static ControllerParameters ClosedLoopPIDController(double kuGain, double uPeriod)
	{
		double kp = 0.6 * kuGain;
		double ki = uPeriod / 2;
		double kd = uPeriod / 8;

		return new ControllerParameters(ControlType.PID, kp, ki, kd);
	}
}
