package com.domain.services.tuning;

import androidx.annotation.NonNull;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.TuningType;

import java.security.InvalidParameterException;

/**
 * Rule of Thumb tuning algorithm.
 */
public class RT
{
	/**
	 * Compute the Rule of Thumb tuning parameters.
	 * @param controlType Control type (PID).
	 * @param transferFunction Transfer function.
	 * @return Controller parameters (Kp, Ki and Kd).
	 */
	public static ControllerParameter compute(@NonNull ControlType controlType,
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
