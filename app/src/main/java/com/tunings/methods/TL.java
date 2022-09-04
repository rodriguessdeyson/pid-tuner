package com.tunings.methods;

import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

import java.security.InvalidParameterException;

public class TL
{
	public static ControllerParameters Compute(ControlProcessType loopType, ControlType controlType,
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
			switch (controlType)
			{
				case PI:
					kp = kuGain / 3.2;
					ki = 2.2 * uPeriod;
					kd = 0;
					break;
				case PID:
					kp = kuGain / 2.2;
					ki = 2.2 * uPeriod;
					kd = uPeriod / 6.3;
					break;
				default:
					throw new InvalidParameterException(controlType.toString());
			}
		}
		else
			throw new InvalidParameterException(loopType.toString());

		// Create the return object.
		ControllerParameters tl = new ControllerParameters();
		tl.setKP(kp);
		tl.setKI(ki);
		tl.setKD(kd);
		tl.setType(controlType);
		return tl;
	}
}
