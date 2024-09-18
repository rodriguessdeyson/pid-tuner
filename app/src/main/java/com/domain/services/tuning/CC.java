package com.domain.services.tuning;

import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;

import java.security.InvalidParameterException;

public class CC
{
	public static ControllerParameter Compute(ControlType controlType, double kGain, double tTime, double tDelay)
	{
		switch (controlType)
		{
			case P:
                return PController(kGain, tTime, tDelay);
			case PI:
				return PIController(kGain, tTime, tDelay);
			case PD:
				return PDController(kGain, tTime, tDelay);
			case PID:
				return PIDController(kGain, tTime, tDelay);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	private static ControllerParameter PController(double kGain, double tTime, double tDelay)
	{
		double kp = (1.03 + (0.35 * (tDelay / tTime))) * (tTime / (kGain * tDelay));
		double ki = 0;
		double kd = 0;

        return new ControllerParameter(ControlType.P, kp, ki, kd);
	}

	private static ControllerParameter PIController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.9 + (0.083 * (tDelay / tTime))) * (tTime / (kGain * tDelay));
		double ki = ((0.9 + (0.083 * (tDelay / tTime))) / (1.27 + (0.6 * (tDelay / tTime)))) * tDelay;
		double kd = 0;

		return new ControllerParameter(ControlType.PI, kp, ki, kd);
	}

	private static ControllerParameter PDController(double kGain, double tTime, double tDelay)
	{
		double kp = ((1.24 / kGain) * ((tTime / tDelay) + 0.129));
		double ki = 0;
		double kd = (0.27 * tDelay) * ((tTime - (0.324 * tDelay)) / (tTime + (0.129 * tDelay)));

		return new ControllerParameter(ControlType.PD, kp, ki, kd);
	}

	private static ControllerParameter PIDController(double kGain, double tTime, double tDelay)
	{
		double kp = (1.35 + (0.25 * (tDelay / tTime))) * (tTime / (kGain * tDelay));
		double ki = ((1.35 + (0.25 * (tDelay / tTime))) / (0.54 + (0.33 * (tDelay / tTime)))) * tDelay;
		double kd = ((0.5 * tDelay) / (1.35 + (0.25 * (tDelay / tTime))));

		return new ControllerParameter(ControlType.PID, kp, ki, kd);
	}
}