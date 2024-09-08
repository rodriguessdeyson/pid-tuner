package com.tunings.methods;

import androidx.annotation.NonNull;

import com.tunings.models.ProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameter;

import java.security.InvalidParameterException;

public class TL
{
	public static ControllerParameter Compute(ProcessType loopType, ControlType controlType,
                                              double kuGain, double uPeriod)
	{
		if (loopType != ProcessType.Closed)
			throw new InvalidParameterException(loopType.toString());

		if (controlType == ControlType.P)
			throw new InvalidParameterException(controlType.toString());

		switch (controlType)
		{
			case PI:
				return PIController(kuGain, uPeriod);
			case PID:
				return PIDController(kuGain, uPeriod);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	@NonNull
	private static ControllerParameter PIController(double kuGain, double uPeriod)
	{
		double kp = kuGain / 3.2;
		double ki = 2.2 * uPeriod;
		double kd = 0;

		return new ControllerParameter(ControlType.PI, kp, ki, kd);
	}

	private static ControllerParameter PIDController(double kuGain, double uPeriod)
	{
		double kp = kuGain / 2.2;
		double ki = 2.2 * uPeriod;
		double kd = uPeriod / 6.3;

		return new ControllerParameter(ControlType.PID, kp, ki, kd);
	}
}
