package com.tunings.methods;

import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

import java.security.InvalidParameterException;

public class ITAE
{
	public static ControllerParameters ComputeServo(ControlType controlType, double kGain, double tTime, double tDelay)
	{
		switch (controlType)
		{
			case PI:
				return ServoPIController(kGain, tTime, tDelay);
			case PID:
				return ServoPIDController(kGain, tTime, tDelay);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	public static ControllerParameters ComputerRegulator(ControlType controlType, double kGain, double tTime, double tDelay)
	{
		switch (controlType)
		{
			case PI:
				return RegulatorPIController(kGain, tTime, tDelay);
			case PID:
				return RegulatorPIDController(kGain, tTime, tDelay);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}
	private static ControllerParameters	ServoPIController(double kGain, double tTime, double tDelay)
	{
		double kp = (1 / kGain) * (0.586 * Math.pow((tDelay / tTime), (-0.916)));
		double ki = (tTime / (1.03 + ((-0.165) * (tDelay / tTime))));
		double kd = 0;

		return new ControllerParameters(ControlProcessType.Servo, ControlType.PI, kp, ki, kd);
	}

	private static ControllerParameters	ServoPIDController(double kGain, double tTime, double tDelay)
	{
		double kp = (1 / kGain) * (0.965 * Math.pow((tDelay / tTime), (-0.850)));
		double ki = (tTime / (0.796 + ((-0.147) * (tDelay / tTime))));
		double kd = (tTime * (0.308 * Math.pow((tDelay / tTime), (0.929))));

		return new ControllerParameters(ControlProcessType.Servo, ControlType.PID, kp, ki, kd);
	}

	private static ControllerParameters	RegulatorPIController(double kGain, double tTime, double tDelay)
	{
		double kp = (1 / kGain) * (0.859 * Math.pow((tDelay / tTime), (-0.977)));
		double ki = (tTime/(0.674 * Math.pow ((tDelay/tTime), (-0.68))));
		double kd = 0;

		return new ControllerParameters(ControlProcessType.Regulator, ControlType.PI, kp, ki, kd);
	}

	private static ControllerParameters	RegulatorPIDController(double kGain, double tTime, double tDelay)
	{
		double kp = (1 / kGain) * (1.357 * Math.pow((tDelay / tTime), (-0.947)));
		double ki = (tTime / (0.842 * Math.pow((tDelay / tTime), (-0.738))));
		double kd = (tTime * (0.381 * Math.pow((tDelay / tTime), (0.995))));

		return new ControllerParameters(ControlProcessType.Regulator, ControlType.PID, kp, ki, kd);
	}
}
