package com.domain.services.tuning;

import androidx.annotation.NonNull;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.TuningType;

import java.security.InvalidParameterException;

public class RT
{
	public static ControllerParameter Compute(@NonNull ControlType controlType,
											  @NonNull TransferFunction transferFunction)
	{
		if (controlType != ControlType.PID)
			throw new InvalidParameterException(controlType.toString());

		double kuGain = transferFunction.getUltimateGain();
		double uPeriod = transferFunction.getUltimatePeriod();

		double kp = 0.5 * kuGain;
		double ki = 0.8 * uPeriod;
		double kd = 0.1 * ki;

        return new ControllerParameter(TuningType.RT, ProcessType.Open, ControlType.PID, kp, ki, kd);
	}
}
