package com.domain.services.tuning;

import androidx.annotation.NonNull;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.TuningType;

import java.security.InvalidParameterException;

public class IAE
{
	public static ControllerParameter ComputeServo(@NonNull ControlType controlType,
												   @NonNull TransferFunction transferFunction)
	{
		double kGain = transferFunction.getGain();
		double tTime = transferFunction.getTimeConstant();
		double tDelay = transferFunction.getTransportDelay();

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

	public static ControllerParameter ComputeRegulator(@NonNull ControlType controlType,
													   @NonNull TransferFunction transferFunction)
	{
		double kGain = transferFunction.getGain();
		double tTime = transferFunction.getTimeConstant();
		double tDelay = transferFunction.getTransportDelay();

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

	private static ControllerParameter ServoPIController(double kGain, double tTime, double tDelay)
	{
		double kp = (1 / kGain) * (0.758 * Math.pow((tDelay / tTime), (-0.861)));
		double ki = (tTime / (1.02 + ((-0.323) * (tDelay / tTime))));
		double kd = 0;

		return new ControllerParameter(TuningType.IAE, ProcessType.Servo, ControlType.PI,
				kp, ki, kd);
	}

	private static ControllerParameter ServoPIDController(double kGain, double tTime, double tDelay)
	{
		double kp = (1 / kGain) * (1.086 * Math.pow((tDelay / tTime), (-0.869)));
		double ki = (tTime / (0.740 + ((-0.130) * (tDelay / tTime))));
		double kd = (tTime * (0.348 * Math.pow((tDelay / tTime), (0.914))));

		return new ControllerParameter(TuningType.IAE, ProcessType.Servo, ControlType.PID,
				kp, ki, kd);
	}

	private static ControllerParameter RegulatorPIController(double kGain, double tTime,
															 double tDelay)
	{
		double kp = (1 / kGain) * (0.984 * Math.pow((tDelay / tTime), (-0.986)));
		double ki = (tTime / (0.608 * Math.pow((tDelay / tTime), (-0.707))));
		double kd = 0;

		return new ControllerParameter(TuningType.IAE, ProcessType.Regulator, ControlType.PI,
				kp, ki, kd);
	}

	private static ControllerParameter RegulatorPIDController(double kGain, double tTime,
															  double tDelay)
	{
		double kp = (1 / kGain) * (1.435 * Math.pow((tDelay / tTime), (-0.921)));
		double ki = (tTime / (0.878 * Math.pow((tDelay / tTime), (-0.749))));
		double kd = (tTime * (0.482 * Math.pow((tDelay / tTime), (1.137))));

		return new ControllerParameter(TuningType.IAE, ProcessType.Regulator, ControlType.PID,
				kp, ki, kd);
	}
}
