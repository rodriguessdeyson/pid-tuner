package com.tunings.methods;

import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

import java.security.InvalidParameterException;

public class ITAE
{
	public static ControllerParameters Compute(ControlType controlType, ControlProcessType cpType,
		double kGain, double tTime, double tDelay)
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
						kp = (1 / kGain) * (0.586 * Math.pow((tDelay / tTime), (-0.916)));
						ki = (tTime / (1.03 + ((-0.165) * (tDelay / tTime))));
						kd = 0;
						break;
					case PID:
						kp = (1 / kGain) * (0.965 * Math.pow((tDelay / tTime), (-0.850)));
						ki = (tTime / (0.796 + ((-0.147) * (tDelay / tTime))));
						kd = (tTime * (0.308 * Math.pow((tDelay / tTime), (0.929))));
						break;
					default:
						throw new InvalidParameterException(controlType.toString());
				}
				break;
			case Regulator:
				switch (controlType)
				{
					case PI:
						kp = (1 / kGain) * (0.859 * Math.pow((tDelay / tTime), (-0.977)));
						ki = (tTime/(0.674 * Math.pow ((tDelay/tTime), (-0.68))));
						kd = 0;
						break;
					case PID:
						kp = (1 / kGain) * (1.357 * Math.pow((tDelay / tTime), (-0.947)));
						ki = (tTime / (0.842 * Math.pow((tDelay / tTime), (-0.738))));
						kd = (tTime * (0.381 * Math.pow((tDelay / tTime), (0.995))));
						break;
					default:
						throw new InvalidParameterException(controlType.toString());
				}
				break;
			default:
				throw new InvalidParameterException(cpType.toString());
		}

		// Create the return object.
		ControllerParameters itae = new ControllerParameters();
		itae.setKP(kp);
		itae.setKI(ki);
		itae.setKD(kd);
		itae.setType(controlType);
		return itae;
	}
}
