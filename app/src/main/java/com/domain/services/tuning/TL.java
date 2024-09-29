package com.domain.services.tuning;

import androidx.annotation.NonNull;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.TuningType;

import java.security.InvalidParameterException;

public class TL
{
	public static ControllerParameter compute(@NonNull ControlType controlType,
											  @NonNull TransferFunction transferFunction)
	{
		double kuGain = transferFunction.getUltimateGain();
		double uPeriod = transferFunction.getUltimatePeriod();

		switch (controlType)
		{
			case PI:
				return piController(kuGain, uPeriod);
			case PID:
				return pidController(kuGain, uPeriod);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	@NonNull
	private static ControllerParameter piController(double kuGain, double uPeriod)
	{
		double kp = kuGain / 3.2;
		double ki = 2.2 * uPeriod;
		double kd = 0;

		return new ControllerParameter(TuningType.TL, ProcessType.Closed, ControlType.PI,
				kp, ki, kd);
	}

	private static ControllerParameter pidController(double kuGain, double uPeriod)
	{
		double kp = kuGain / 2.2;
		double ki = 2.2 * uPeriod;
		double kd = uPeriod / 6.3;

		return new ControllerParameter(TuningType.TL, ProcessType.Closed, ControlType.PID,
				kp, ki, kd);
	}
}
