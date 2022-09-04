package com.tunings.methods;

import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

import java.security.InvalidParameterException;

public class RT
{
	public static ControllerParameters Compute(ControlType controlType, ControlProcessType loopType,
		double kuGain, double uPeriod)
	{
		// Proportional Gain.
		double kp;

		// Integral gain.
		double ki;

		// Derivative gain.
		double kd;
		if (loopType == ControlProcessType.Closed)
		{
			if (controlType == ControlType.PID)
			{
				kp = 0.5 * kuGain;
				ki = 0.8 * uPeriod;
				kd = 0.1 * ki;
			}
			else
				throw new InvalidParameterException(controlType.toString());
		}
		else
			throw new InvalidParameterException(loopType.toString());

		ControllerParameters rt = new ControllerParameters();
		rt.setKP(kp);
		rt.setKI(ki);
		rt.setKD(kd);
		rt.setType(controlType);
		return rt;
	}
}
