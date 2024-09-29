package com.domain.services.tuning;

import androidx.annotation.NonNull;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.TuningType;

import java.security.InvalidParameterException;

public class CC
{
	public static ControllerParameter compute(@NonNull ControlType controlType,
											  @NonNull TransferFunction transferFunction)
	{
		double kGain = transferFunction.getGain();
		double tTime = transferFunction.getTimeConstant();
		double tDelay = transferFunction.getTransportDelay();

		switch (controlType)
		{
			case P:
                return pController(kGain, tTime, tDelay);
			case PI:
				return piController(kGain, tTime, tDelay);
			case PD:
				return pdController(kGain, tTime, tDelay);
			case PID:
				return pidController(kGain, tTime, tDelay);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	private static ControllerParameter pController(double kGain, double tTime, double tDelay)
	{
		double kp = (1.03 + (0.35 * (tDelay / tTime))) * (tTime / (kGain * tDelay));
		double ki = 0;
		double kd = 0;

        return new ControllerParameter(TuningType.CC, ProcessType.None, ControlType.P, kp, ki, kd);
	}

	private static ControllerParameter piController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.9 + (0.083 * (tDelay / tTime))) * (tTime / (kGain * tDelay));
		double ki = ((0.9 + (0.083 * (tDelay / tTime))) / (1.27 + (0.6 * (tDelay / tTime)))) * tDelay;
		double kd = 0;

		return new ControllerParameter(TuningType.CC, ProcessType.None, ControlType.PI, kp, ki, kd);
	}

	private static ControllerParameter pdController(double kGain, double tTime, double tDelay)
	{
		double kp = ((1.24 / kGain) * ((tTime / tDelay) + 0.129));
		double ki = 0;
		double kd = (0.27 * tDelay) * ((tTime - (0.324 * tDelay)) / (tTime + (0.129 * tDelay)));

		return new ControllerParameter(TuningType.CC, ProcessType.None, ControlType.PD, kp, ki, kd);
	}

	private static ControllerParameter pidController(double kGain, double tTime, double tDelay)
	{
		double kp = (1.35 + (0.25 * (tDelay / tTime))) * (tTime / (kGain * tDelay));
		double ki = ((1.35 + (0.25 * (tDelay / tTime))) / (0.54 + (0.33 * (tDelay / tTime)))) * tDelay;
		double kd = ((0.5 * tDelay) / (1.35 + (0.25 * (tDelay / tTime))));

		return new ControllerParameter(TuningType.CC, ProcessType.None, ControlType.PID, kp, ki, kd);
	}
}
