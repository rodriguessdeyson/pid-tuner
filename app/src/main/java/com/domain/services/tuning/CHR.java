package com.domain.services.tuning;

import androidx.annotation.NonNull;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.TuningType;

import java.security.InvalidParameterException;

/**
 * Chien-Hrones-Reswick tuning algorithm.
 */
public class CHR
{
	
	/**
	 * Compute the Chien-Hrones-Reswick Servo tuning parameters.
	 * @param controlType Control type (P, PI, PID).
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
			case P:
				return servoPController(kGain, tTime, tDelay);
			case PI:
				return servoPIController(kGain, tTime, tDelay);
			case PID:
				return servoPIDController(kGain, tTime, tDelay);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	/**
	 * Compute the Chien-Hrones-Reswick Servo with overshoot tuning parameters.
	 * @param controlType Control type (P, PI, PID).
	 * @param transferFunction Transfer function.
	 * @return Controller parameters (Kp, Ki and Kd).
	 */
	public static ControllerParameter computeServo20UP(@NonNull ControlType controlType,
													   @NonNull TransferFunction transferFunction)
	{
		double kGain = transferFunction.getGain();
		double tTime = transferFunction.getTimeConstant();
		double tDelay = transferFunction.getTransportDelay();

		switch (controlType)
		{
			case P:
				return servo20UPPController(kGain, tTime, tDelay);
			case PI:
				return servo20UPPIController(kGain, tTime, tDelay);
			case PID:
				return servo20UPPIDController(kGain, tTime, tDelay);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	/**
	 * Compute the Chien-Hrones-Reswick Regulator tuning parameters.
	 * @param controlType Control type (P, PI, PID).
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
			case P:
				return regulatorPController(kGain, tTime, tDelay);
			case PI:
				return regulatorPIController(kGain, tTime, tDelay);
			case PID:
				return regulatorPIDController(kGain, tTime, tDelay);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	/**
	 * Compute the Chien-Hrones-Reswick Regulator with overshoot tuning parameters.
	 * @param controlType Control type (P, PI, PID).
	 * @param transferFunction Transfer function.
	 * @return Controller parameters (Kp, Ki and Kd).
	 */
	public static ControllerParameter computeRegulator20UP(@NonNull ControlType controlType,
														   @NonNull TransferFunction transferFunction)
	{
		double kGain = transferFunction.getGain();
		double tTime = transferFunction.getTimeConstant();
		double tDelay = transferFunction.getTransportDelay();

		switch (controlType)
		{
			case P:
				return regulator20UPPController(kGain, tTime, tDelay);
			case PI:
				return regulator20UPPIController(kGain, tTime, tDelay);
			case PID:
				return regulator20UPPIDController(kGain, tTime, tDelay);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	/**
	 * Compute Chien-Hrones-Reswick Kp tuning parameters.
	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @return Controller parameters.
	 */
	private static ControllerParameter servoPController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.3 * tTime) / (kGain * tDelay);
		double ki = 0;
		double kd = 0;

		return new ControllerParameter(TuningType.CHR, ProcessType.Servo, ControlType.P, kp, ki, kd);
	}

	/**
	 * Compute Chien-Hrones-Reswick Kp and Ki tuning parameters.
	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @return Controller parameters.
	 */
	private static ControllerParameter servoPIController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.35 * tTime) / (kGain * tDelay);
		double ki = 1.16 * tTime;
		double kd = 0;

		return new ControllerParameter(TuningType.CHR, ProcessType.Servo, ControlType.PI, kp, ki, kd);
	}

	/**
	 * Compute Chien-Hrones-Reswick Kp, Ki and Kd tuning parameters.
	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @return Controller parameters.
	 */
	private static ControllerParameter servoPIDController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.6 * tTime) / (kGain * tDelay);
		double ki = tTime;
		double kd = (tDelay / 2);

		return new ControllerParameter(TuningType.CHR, ProcessType.Servo, ControlType.PID, kp, ki, kd);
	}

	/**
	 * Compute Chien-Hrones-Reswick Kp tuning parameters.
	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @return Controller parameters.
	 */
	private static ControllerParameter servo20UPPController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.7 * tTime) / (kGain * tDelay);
		double ki = 0;
		double kd = 0;

		return new ControllerParameter(TuningType.CHR, ProcessType.Servo20, ControlType.P, kp, ki, kd);
	}

	/**
	 * Compute Chien-Hrones-Reswick Kp and Ki tuning parameters.
 	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @return Controller parameters.
	 */
	private static ControllerParameter servo20UPPIController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.6 * tTime) / (kGain * tDelay);
		double ki = tTime;
		double kd = 0;

		return new ControllerParameter(TuningType.CHR, ProcessType.Servo20, ControlType.PI, kp, ki, kd);
	}

	/**
	 * Compute Chien-Hrones-Reswick Kp, Ki and Kd tuning parameters.
	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @return Controller parameters.
	 */
	private static ControllerParameter servo20UPPIDController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.95 * tTime) / (kGain*tDelay);
		double ki = 1.357 * tTime;
		double kd = 0.473 * tDelay;

		return new ControllerParameter(TuningType.CHR, ProcessType.Servo20, ControlType.PID, kp, ki, kd);
	}

	/**
	 * Compute Chien-Hrones-Reswick Kp tuning parameters.
	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @return Controller parameters.
	 */
	private static ControllerParameter regulatorPController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.3 * tTime) / (kGain * tDelay);
		double ki = 0;
		double kd = 0;

		return new ControllerParameter(TuningType.CHR, ProcessType.Regulator, ControlType.P, kp, ki, kd);
	}

	/**
	 * Compute Chien-Hrones-Reswick Kp and Ki tuning parameters.
	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @return Controller parameters.
	 */
	private static ControllerParameter regulatorPIController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.6 * tTime) / (kGain * tDelay);
		double ki = 4 * tDelay;
		double kd = 0;

		return new ControllerParameter(TuningType.CHR, ProcessType.Regulator, ControlType.PI, kp, ki, kd);
	}

	/**
	 * Compute Chien-Hrones-Reswick Kp, Ki and Kd tuning parameters.
	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @return Controller parameters.
	 */
	private static ControllerParameter regulatorPIDController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.95 * tTime) / (kGain * tDelay);
		double ki = 2.375 * tDelay;
		double kd = 0.421 * tDelay;

		return new ControllerParameter(TuningType.CHR, ProcessType.Regulator, ControlType.PID, kp, ki, kd);
	}

	/**
	 * Compute Chien-Hrones-Reswick Kp tuning parameters.
	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @return Controller parameters.
	 */
	private static ControllerParameter regulator20UPPController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.7 * tTime) / tDelay;
		double ki = 0;
		double kd = 0;

		return new ControllerParameter(TuningType.CHR, ProcessType.Regulator20, ControlType.P, kp, ki, kd);
	}

	/**
	 * Compute Chien-Hrones-Reswick Kp and Ki tuning parameters.
	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @return Controller parameters.
	 */
	private static ControllerParameter regulator20UPPIController(double kGain, double tTime, double tDelay)
	{
		double kp = (0.7 * tTime) / tDelay;
		double ki = 2.3 * tDelay;
		double kd = 0;

		return new ControllerParameter(TuningType.CHR, ProcessType.Regulator20, ControlType.PI, kp, ki, kd);
	}

	/**
	 * Compute Chien-Hrones-Reswick Kp, Ki and Kd tuning parameters.
	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @return Controller parameters.
	 */
	private static ControllerParameter regulator20UPPIDController(double kGain, double tTime, double tDelay)
	{
		double kp = (1.2 * tTime) / tDelay;
		double ki = 2 * tDelay;
		double kd = 0.42 * tDelay;

		return new ControllerParameter(TuningType.CHR, ProcessType.Regulator20, ControlType.PID, kp, ki, kd);
	}
}

