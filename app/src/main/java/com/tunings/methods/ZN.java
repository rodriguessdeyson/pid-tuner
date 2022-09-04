package com.tunings.methods;

import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

import java.security.InvalidParameterException;

public class ZN
{
	public static ControllerParameters Compute(ControlProcessType loopType, ControlType controlType,
		Double kGain, Double tTime, Double tDelay, Double kuGain, Double uPeriod)
	{
		// Proportional Gain.
		double kp;

		// Integral gain.
		double ki;

		// Derivative gain.
		double kd;
		switch (loopType)
		{
			case Open:
				switch (controlType)
				{
					case P:
						kp = tTime / (kGain * tDelay);
						ki = 0;
						kd = 0;
						break;
					case PI:
						kp = 0.9 * tTime / (kGain * tDelay);
						ki = 3.33 * tDelay;
						kd = 0;
						break;
					case PID:
						kp = 1.2 * tTime / (kGain * tDelay);
						ki = 2 * tDelay;
						kd = 0.5 * tDelay;
						break;
					default:
						throw new InvalidParameterException(controlType.toString());
				}
				break;
			case Closed:
				switch (controlType)
				{
					case P:
						kp = 0.5 * kuGain;
						ki = 0;
						kd = 0;
						break;
					case PI:
						kp = 0.45 * kuGain;
						ki = (uPeriod / 1.2);
						kd = 0;
						break;
					case PID:
						kp = 0.6 * kuGain;
						ki = uPeriod / 2;
						kd = uPeriod / 8;
						break;
					default:
						throw new InvalidParameterException(controlType.toString());
				}
				break;
			default:
				throw new InvalidParameterException(loopType.toString());
		}

		// Create the return object.
		ControllerParameters zn = new ControllerParameters();
		zn.setKP(kp);
		zn.setKI(ki);
		zn.setKD(kd);
		zn.setType(controlType);
		return zn;
	}
}
