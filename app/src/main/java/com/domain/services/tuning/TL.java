package com.domain.services.tuning;

import androidx.annotation.NonNull;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.TuningType;

import java.security.InvalidParameterException;

/**
 * Tyreus-Luyben tuning algorithm.
 */
public class TL
{
	/**
	 * Compute the Tyreus-Luyben tuning parameters.
	 * @param controlType Control type (PI, PID).
	 * @param transferFunction Transfer function.
	 * @return Controller parameters (Kp, Ki and Kd).
	 */
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

	/**
	 * Compute Tyreus-Luyben Kp and Ki tuning parameters.
	 * @param kuGain Ultimate gain.
	 * @param uPeriod Ultimate period.
	 * @return Controller parameters.
	 */
	@NonNull
	private static ControllerParameter piController(double kuGain, double uPeriod)
	{
		double kp = kuGain / 3.2;
		double ki = 2.2 * uPeriod;
		double kd = 0;

		return new ControllerParameter(TuningType.TL, ProcessType.Closed, ControlType.PI,
				kp, ki, kd);
	}

	/**
	 * Compute Tyreus-Luyben Kp, Ki and Kd tuning parameters.
	 * @param kuGain Ultimate gain.
	 * @param uPeriod Ultimate period.
	 * @return Controller parameters.
	 */
	private static ControllerParameter pidController(double kuGain, double uPeriod)
	{
		double kp = kuGain / 2.2;
		double ki = 2.2 * uPeriod;
		double kd = uPeriod / 6.3;

		return new ControllerParameter(TuningType.TL, ProcessType.Closed, ControlType.PID,
				kp, ki, kd);
	}
}
