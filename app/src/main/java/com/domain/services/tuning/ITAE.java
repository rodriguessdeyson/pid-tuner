package com.domain.services.tuning;

import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;

import java.security.InvalidParameterException;

public class ITAE
{
	public static ControllerParameter ComputeServo(ControlType controlType, double kGain, double tTime, double tDelay)
	{
		switch (controlType)
		{
			case PI:
				return ServoPIController(kGain, tTime, tDelay);
			case PID:
				return ServoPIDController(kGain, tTime, tDelay);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	public static ControllerParameter ComputeRegulator(ControlType controlType, double kGain, double tTime, double tDelay)
	{
		switch (controlType)
		{
			case PI:
				return RegulatorPIController(kGain, tTime, tDelay);
			case PID:
				return RegulatorPIDController(kGain, tTime, tDelay);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}
	private static ControllerParameter ServoPIController(double kGain, double tTime, double tDelay)
	{
		double kp = (1 / kGain) * (0.586 * Math.pow((tDelay / tTime), (-0.916)));
		double ki = (tTime / (1.03 + ((-0.165) * (tDelay / tTime))));
		double kd = 0;

		return new ControllerParameter(ControlType.PI, kp, ki, kd);
	}

	private static ControllerParameter ServoPIDController(double kGain, double tTime, double tDelay)
	{
		double kp = (1 / kGain) * (0.965 * Math.pow((tDelay / tTime), (-0.850)));
		double ki = (tTime / (0.796 + ((-0.147) * (tDelay / tTime))));
		double kd = (tTime * (0.308 * Math.pow((tDelay / tTime), (0.929))));

		return new ControllerParameter(ControlType.PID, kp, ki, kd);
	}

	private static ControllerParameter RegulatorPIController(double kGain, double tTime, double tDelay)
	{
		double kp = (1 / kGain) * (0.859 * Math.pow((tDelay / tTime), (-0.977)));
		double ki = (tTime/(0.674 * Math.pow ((tDelay/tTime), (-0.68))));
		double kd = 0;

		return new ControllerParameter(ControlType.PI, kp, ki, kd);
	}

	private static ControllerParameter RegulatorPIDController(double kGain, double tTime, double tDelay)
	{
		double kp = (1 / kGain) * (1.357 * Math.pow((tDelay / tTime), (-0.947)));
		double ki = (tTime / (0.842 * Math.pow((tDelay / tTime), (-0.738))));
		double kd = (tTime * (0.381 * Math.pow((tDelay / tTime), (0.995))));

		return new ControllerParameter(ControlType.PID, kp, ki, kd);
	}
}