package com.tunings.methods;

import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

import java.security.InvalidParameterException;

public class IAE
{
	public static ControllerParameters Compute(ControlType controlType, ControlProcessType cpType, double kGain,
		double tTime, double tDelay)
	{
		// Proportional Gain.
		double kp;

		// Integral gain.
		double ki;

		// Derivative gain.
		double kd;
		switch (cpType)
		{
			case Servo:
				switch (controlType)
				{
					case PI:
						kp = (1 / kGain) * (0.758 * Math.pow((tDelay / tTime), (-0.861)));
						ki = (tTime / (1.02 + ((-0.323) * (tDelay / tTime))));
						kd = 0;
						break;
					case PID:
						kp = (1 / kGain) * (1.086 * Math.pow((tDelay / tTime), (-0.869)));
						ki = (tTime / (0.740 + ((-0.130) * (tDelay / tTime))));
						kd = (tTime * (0.348 * Math.pow((tDelay / tTime), (0.914))));
						break;
					default:
						throw new InvalidParameterException(controlType.toString());
				}
				break;
			case Regulator:
				switch (controlType)
				{
					case PI:
						kp = (1 / kGain) * (0.984 * Math.pow((tDelay / tTime), (-0.986)));
						ki = (tTime / (0.608 * Math.pow((tDelay / tTime), (-0.707))));
						kd = 0;
						break;
					case PID:
						kp = (1 / kGain) * (1.435 * Math.pow((tDelay / tTime), (-0.921)));
						ki = (tTime / (0.878 * Math.pow((tDelay / tTime), (-0.749))));
						kd = (tTime * (0.482 * Math.pow((tDelay / tTime), (1.137))));
						break;
					default:
						throw new InvalidParameterException(controlType.toString());
				}
				break;
			default:
				throw new InvalidParameterException(cpType.toString());
		}

		// Create the return object.
		ControllerParameters iae = new ControllerParameters();
		iae.setKP(kp);
		iae.setKI(ki);
		iae.setKD(kd);
		iae.setType(controlType);
		return iae;
	}
}
