package com.tunings.methods;

import com.tunings.models.ProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameter;

import java.security.InvalidParameterException;

public class RT
{
	public static ControllerParameter Compute(ControlType controlType, ProcessType processType,
                                              double kuGain, double uPeriod)
	{
		if (processType != ProcessType.Closed)
			throw new InvalidParameterException(processType.toString());

		if (controlType != ControlType.PID)
			throw new InvalidParameterException(controlType.toString());

		double kp = 0.5 * kuGain;
		double ki = 0.8 * uPeriod;
		double kd = 0.1 * ki;

        return new ControllerParameter(ControlType.PID, kp, ki, kd);
	}
}
