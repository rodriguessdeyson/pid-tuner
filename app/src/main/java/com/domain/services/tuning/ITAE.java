package com.domain.services.tuning;

import androidx.annotation.NonNull;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.TuningType;

import java.security.InvalidParameterException;

/**
 * Integral Time Absolute Error tuning algorithm.
 */
public class ITAE
{
	/**
	 * Compute the Integral Time Absolute Error Servo tuning parameters.
	 * @param controlType Control type (PI, PID).
	 * @param transferFunction Transfer function.
	 * @return Controller parameters (Kp, Ki and Kd).
	 */
	public static ControllerParameter computeServo(@NonNull ControlType controlType,
												   @NonNull TransferFunction transferFunction)
	{
		double kGain = transferFunction.getGain();
		double tTime = transferFunction.getTimeConstant();
		double tDelay = transferFunction.getTransportDelay();

		switch (controlType)
		{
			case PI:
				return servoPIController(kGain, tTime, tDelay);
			case PID:
				return servoPIDController(kGain, tTime, tDelay);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	/**
	 * Compute the Integral Time Absolute Error Regulator tuning parameters.
	 * @param controlType Control type (PI, PID).
	 * @param transferFunction Transfer function.
	 * @return Controller parameters (Kp, Ki and Kd).
	 */
	public static ControllerParameter computeRegulator(@NonNull ControlType controlType,
													   @NonNull TransferFunction transferFunction)
	{
		double kGain = transferFunction.getGain();
		double tTime = transferFunction.getTimeConstant();
		double tDelay = transferFunction.getTransportDelay();

		switch (controlType)
		{
			case PI:
				return regulatorPIController(kGain, tTime, tDelay);
			case PID:
				return regulatorPIDController(kGain, tTime, tDelay);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	/**
	 * Compute Integral Time Absolute Error Kp and Ki tuning parameters.
	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @return Controller parameters.
	 */
	private static ControllerParameter servoPIController(double kGain, double tTime,
														 double tDelay)
	{
		double kp = (1 / kGain) * (0.586 * Math.pow((tDelay / tTime), (-0.916)));
		double ki = (tTime / (1.03 + ((-0.165) * (tDelay / tTime))));
		double kd = 0;

		return new ControllerParameter(TuningType.ITAE, ProcessType.Servo,
				ControlType.PI, kp, ki, kd);
	}

	/**
	 * Compute Integral Time Absolute Error Kp, Ki and Kd tuning parameters.
	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @return Controller parameters.
	 */
	private static ControllerParameter servoPIDController(double kGain, double tTime,
														  double tDelay)
	{
		double kp = (1 / kGain) * (0.965 * Math.pow((tDelay / tTime), (-0.850)));
		double ki = (tTime / (0.796 + ((-0.147) * (tDelay / tTime))));
		double kd = (tTime * (0.308 * Math.pow((tDelay / tTime), (0.929))));

		return new ControllerParameter(TuningType.ITAE, ProcessType.Servo,
				ControlType.PID, kp, ki, kd);
	}

	/**
	 * Compute Integral Time Absolute Error Kp and Ki tuning parameters.
	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @return Controller parameters.
	 */
	private static ControllerParameter regulatorPIController(double kGain, double tTime,
															 double tDelay)
	{
		double kp = (1 / kGain) * (0.859 * Math.pow((tDelay / tTime), (-0.977)));
		double ki = (tTime/(0.674 * Math.pow ((tDelay/tTime), (-0.68))));
		double kd = 0;

		return new ControllerParameter(TuningType.ITAE, ProcessType.Regulator,
				ControlType.PI, kp, ki, kd);
	}

	/**
	 * Compute Integral Time Absolute Error Kp, Ki and Kd tuning parameters.
	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @return Controller parameters.
	 */
	private static ControllerParameter regulatorPIDController(double kGain, double tTime,
															  double tDelay)
	{
		double kp = (1 / kGain) * (1.357 * Math.pow((tDelay / tTime), (-0.947)));
		double ki = (tTime / (0.842 * Math.pow((tDelay / tTime), (-0.738))));
		double kd = (tTime * (0.381 * Math.pow((tDelay / tTime), (0.995))));

		return new ControllerParameter(TuningType.ITAE, ProcessType.Regulator,
				ControlType.PID, kp, ki, kd);
	}
}
