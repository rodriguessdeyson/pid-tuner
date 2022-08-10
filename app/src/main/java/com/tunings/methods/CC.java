package com.tunings.methods;

import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

import java.security.InvalidParameterException;

public class CC
{
	public static ControllerParameters Compute(ControlType controlType, double kGain, double tTime, double tDelay)
	{
		// Proportional Gain.
		double kp;

		// Integral gain.
		double ki;

		// Derivative gain.
		double kd;
		switch (controlType)
		{
			case P:
				kp = (1.03 + (0.35 * (tDelay / tTime))) * (tTime / (kGain * tDelay));
				ki = 0;
				kd = 0;
				break;
			case PI:
				kp = (0.9 + (0.083 * (tDelay / tTime))) * (tTime / (kGain * tDelay));
				ki = ((0.9 + (0.083 * (tDelay / tTime))) / (1.27 + (0.6 * (tDelay / tTime)))) * tDelay;
				kd = 0;
				break;
			case PD:
				kp = ((1.24 / kGain) * ((tTime / tDelay) + 0.129));
				ki = 0;
				kd = (0.27 * tDelay) * ((tTime - (0.324 * tDelay)) / (tTime + (0.129 * tDelay)));
				break;
			case PID:
				kp = (1.35 + (0.25 * (tDelay / tTime))) * (tTime / (kGain * tDelay));
				ki = ((1.35 + (0.25 * (tDelay / tTime))) / (0.54 + (0.33 * (tDelay / tTime)))) * tDelay;
				kd = ((0.5 * tDelay) / (1.35 + (0.25 * (tDelay / tTime))));
				break;
			default:
				throw new InvalidParameterException(controlType.toString());
		}
		ControllerParameters cc = new ControllerParameters();
		cc.setKP(kp);
		cc.setKI(ki);
		cc.setKD(kd);
		cc.setType(controlType);
		return cc;
	}
}
