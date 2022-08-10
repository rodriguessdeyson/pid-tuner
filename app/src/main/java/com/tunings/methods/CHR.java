package com.tunings.methods;

import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

import org.jetbrains.annotations.NotNull;

import java.security.InvalidParameterException;

public class CHR
{
	@org.jetbrains.annotations.NotNull
	public static ControllerParameters Compute(ControlType controlType, @NotNull ControlProcessType cpType,
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
					case P:
						kp = (0.3 * tTime) / (kGain * tDelay);
						ki = 0;
						kd = 0;
						break;
					case PI:
						kp = (0.35 * tTime) / (kGain * tDelay);
						ki = 1.16 * tTime;
						kd = 0;
						break;
					case PID:
						kp = (0.6 * tTime) / (kGain * tDelay);
						ki = tTime;
						kd = (tDelay / 2);
						break;
					default:
						throw new InvalidParameterException(controlType.toString());
				}
				break;
			case Servo20:
				switch (controlType)
				{
					case P:
						kp = (0.7 * tTime) / (kGain * tDelay);
						ki = 0;
						kd = 0;
						break;
					case PI:
						kp = (0.6 * tTime) / (kGain * tDelay);
						ki = tTime;
						kd = 0;
						break;
					case PID:
						kp = (0.95 * tTime) / (kGain*tDelay);
						ki = 1.357 * tTime;
						kd = 0.473 * tDelay;
						break;
					default:
						throw new InvalidParameterException(controlType.toString());
				}
				break;
			case Regulator:
				switch (controlType)
				{
					case P:
						kp = (0.3 * tTime) / (kGain * tDelay);
						ki = 0;
						kd = 0;
						break;
					case PI:
						kp = (0.6 * tTime) / (kGain * tDelay);
						ki = 4 * tDelay;
						kd = 0;
						break;
					case PID:
						kp = (0.95 * tTime) / (kGain * tDelay);
						ki = 2.375 * tDelay;
						kd = 0.421 * tDelay;
						break;
					default:
						throw new InvalidParameterException(controlType.toString());
				}
				break;
			case Regulator20:
				switch (controlType)
				{
					case P:
						kp = (0.7 * tTime) / tDelay;
						ki = 0;
						kd = 0;
						break;
					case PI:
						kp = (0.7 * tTime) / tDelay;
						ki = 2.3 * tDelay;
						kd = 0;
						break;
					case PID:
						kp = (1.2 * tTime) / tDelay;
						ki = 2 * tDelay;
						kd = 0.42 * tDelay;
						break;
					default:
						throw new InvalidParameterException(controlType.toString());
				}
				break;
			default:
				throw new InvalidParameterException(cpType.toString());
		}

		// Create the return object.
		ControllerParameters chr = new ControllerParameters();
		chr.setKP(kp);
		chr.setKI(ki);
		chr.setKD(kd);
		chr.setType(controlType);
		return chr;
	}
}
