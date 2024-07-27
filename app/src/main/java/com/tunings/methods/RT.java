package com.tunings.methods;

import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

import java.security.InvalidParameterException;

public class RT
{
	public static ControllerParameters Compute(ControlType controlType, ControlProcessType controlProcessType,
		double kuGain, double uPeriod)
	{
		if (controlProcessType != ControlProcessType.Closed)
			throw new InvalidParameterException(controlProcessType.toString());

		if (controlType != ControlType.PID)
			throw new InvalidParameterException(controlType.toString());

		double kp = 0.5 * kuGain;
		double ki = 0.8 * uPeriod;
		double kd = 0.1 * ki;

        return new ControllerParameters(ControlProcessType.Closed, ControlType.PID, kp, ki, kd);
	}
}
