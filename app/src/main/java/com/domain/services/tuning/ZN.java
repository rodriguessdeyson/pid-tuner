package com.domain.services.tuning;

import androidx.annotation.NonNull;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.TuningType;

import java.security.InvalidParameterException;

public class ZN
{
	public static ControllerParameter computeOpenLoop(@NonNull ControlType controlType,
													  @NonNull TransferFunction transferFunction)
	{
		double kGain = transferFunction.getGain();
		double tTime = transferFunction.getTimeConstant();
		double tDelay = transferFunction.getTransportDelay();

		switch (controlType)
		{
			case P:
				return openLoopPController(kGain, tTime, tDelay);
			case PI:
				return openLoopPIController(kGain, tTime, tDelay);
			case PID:
				return openLoopPIDController(kGain, tTime, tDelay);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	public static ControllerParameter computeClosedLoop(@NonNull ControlType controlType,
														@NonNull TransferFunction transferFunction)
	{
		double kuGain = transferFunction.getUltimateGain();
		double uPeriod = transferFunction.getUltimatePeriod();

		switch (controlType)
		{
			case P:
				return closedLoopPController(kuGain, uPeriod);
			case PI:
				return closedLoopPIController(kuGain, uPeriod);
			case PID:
				return closedLoopPIDController(kuGain, uPeriod);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	private static ControllerParameter openLoopPController(double kGain, double tTime,
														   double tDelay)
	{
		double kp = tTime / (kGain * tDelay);
		double ki = 0;
		double kd = 0;

		return new ControllerParameter(TuningType.ZN, ProcessType.Open, ControlType.P,
				kp, ki, kd);
	}

	private static ControllerParameter openLoopPIController(double kGain, double tTime,
															double tDelay)
	{
		double kp = 0.9 * tTime / (kGain * tDelay);
		double ki = 3.33 * tDelay;
		double kd = 0;

		return new ControllerParameter(TuningType.ZN, ProcessType.Open, ControlType.PI,
				kp, ki, kd);
	}

	private static ControllerParameter openLoopPIDController(double kGain, double tTime,
															 double tDelay)
	{
		double kp = 1.2 * tTime / (kGain * tDelay);
		double ki = 2 * tDelay;
		double kd = 0.5 * tDelay;

		return new ControllerParameter(TuningType.ZN, ProcessType.Open, ControlType.PID,
				kp, ki, kd);
	}

	private static ControllerParameter closedLoopPController(double kuGain, double uPeriod)
	{
		double kp = 0.5 * kuGain;
		double ki = 0;
		double kd = 0;

		return new ControllerParameter(TuningType.ZN, ProcessType.Closed, ControlType.P,
				kp, ki, kd);
	}

	private static ControllerParameter closedLoopPIController(double kuGain, double uPeriod)
	{
		double kp = 0.45 * kuGain;
		double ki = (uPeriod / 1.2);
		double kd = 0;

		return new ControllerParameter(TuningType.ZN, ProcessType.Closed, ControlType.PI,
				kp, ki, kd);
	}

	private static ControllerParameter closedLoopPIDController(double kuGain, double uPeriod)
	{
		double kp = 0.6 * kuGain;
		double ki = uPeriod / 2;
		double kd = uPeriod / 8;

		return new ControllerParameter(TuningType.ZN, ProcessType.Closed, ControlType.PID,
				kp, ki, kd);
	}
}
