package com.domain.services.tuning;

import androidx.annotation.NonNull;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.IMCModelBasedType;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.TuningType;

import java.security.InvalidParameterException;

public class IMC
{
	/**
	 * Compute the parameters of a controller based on IMC approach with lambda and dead time.
	 * @param controlType Type of controller.
	 * @param transferFunction Transfer function of process.
	 * @return Return the controller PID parameters.
	 */
	public static ControllerParameter computeLambdaTuning(@NonNull ControlType controlType,
														  @NonNull TransferFunction transferFunction)
	{
		double kGain = transferFunction.getGain();
		double tTime = transferFunction.getTimeConstant();
		double tDelay = transferFunction.getTransportDelay();
		double lambdaValue = transferFunction.getLambdaCoefficient();

		switch (controlType)
		{
			case PI:
				return firstOrderLambdaPIController(kGain, tTime, tDelay, lambdaValue);
			case PID:
				return firstOrderLambdaPIDController(kGain, tTime, tDelay, lambdaValue);
			default:
				throw new InvalidParameterException(controlType.toString());
		}
	}

	/**
	 * Compute the parameters of a PI controller.
	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @param lambdaValue Adjust lambda value. It is null when default is applied.
	 * @return PI controller.
	 */
	private static ControllerParameter firstOrderLambdaPIController(double kGain, double tTime,
																	double tDelay,
																	double lambdaValue)
	{
		double kp = ((2 * tTime + tDelay) / (kGain * 2 * lambdaValue));
		double ki = tTime + (tDelay / 2);
		double kd = 0;

		return new ControllerParameter(TuningType.IMC, ProcessType.LambdaTuning, ControlType.PI,
				kp, ki, kd);
	}

	/**
	 * Compute the parameters of a PID controller.
	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @param lambdaValue Adjust lambda value. It is null when default is applied.
	 * @return PID controller;
	 */
	private static ControllerParameter firstOrderLambdaPIDController(double kGain, double tTime,
																	 double tDelay, double lambdaValue)
	{
		double kp = ((2 * tTime + tDelay) / (kGain * (2 * lambdaValue + tDelay)));
		double ki = tTime + (tDelay / 2);
		double kd = (tTime * tDelay / (2 * tTime + tDelay));

		return new ControllerParameter(TuningType.IMC, ProcessType.LambdaTuning, ControlType.PID,
				kp, ki, kd);
	}

	/**
	 * Compute the parameters of a controller based on IMC approach with lambda value and
	 * without dead time (Model type required).
	 * @param transferFunction Transfer function of process.
	 * @return Return the controller PID parameters.
	 */
	public static ControllerParameter computeLambdaTuning(@NonNull TransferFunction transferFunction)
	{
		double kGain = transferFunction.getGain();
		double tTime = transferFunction.getTimeConstant();
		double tSTime = transferFunction.getSecondTimeConstant();
		double dampingRatio = transferFunction.getDampingRatio();
		double lambdaValue = transferFunction.getLambdaCoefficient();
		IMCModelBasedType imcModelBasedType = transferFunction.getIMCModelType();

		switch (imcModelBasedType)
		{
			case P:
				return lambdaPControllerModel(kGain, lambdaValue);
			case PD:
				return lambdaPDControllerModel(kGain, tTime, lambdaValue);
			case PI:
				return lambdaPIControllerModel(kGain, tTime, lambdaValue);
			case PID1:
				return lambdaPIDControllerFirstModel(kGain, tTime, tSTime, lambdaValue);
			case PID2:
				return lambdaPIDControllerSecondModel(kGain, tTime, dampingRatio, lambdaValue);
			default:
				throw new InvalidParameterException(imcModelBasedType.toString());
		}
	}

	/**
	 * Compute the parameters of a controller
	 * @param kGain Gain.
	 * @param lambdaValue Adjust lambda value. It is null when default is applied.
	 * @return P controller;
	 */
	private static ControllerParameter lambdaPControllerModel(double kGain, double lambdaValue)
	{
		double kp = (1 / (kGain * lambdaValue));
		double ki = 0;
		double kd = 0;

		return new ControllerParameter(TuningType.IMC, ProcessType.LambdaModelBasedTuning,
				ControlType.P, kp, ki, kd);
	}

	/**
	 * Compute the parameters of a controller
	 * @param kGain Gain.
	 * @param tTime First time constant.
	 * @param lambdaValue Adjust lambda value. It is null when default is applied.
	 * @return PD controller;
	 */
	private static ControllerParameter lambdaPDControllerModel(double kGain, double tTime,
															   double lambdaValue)
	{
		double kp = (1 / (kGain * lambdaValue));
		double ki = 0;
		double kd = tTime;

		return new ControllerParameter(TuningType.IMC, ProcessType.LambdaModelBasedTuning,
				ControlType.PD, kp, ki, kd);
	}

	/**
	 * Compute the parameters of a controller
	 * @param kGain Gain.
	 * @param tTime First time constant.
	 * @param lambdaValue Adjust lambda value. It is null when default is applied.
	 * @return  controller;
	 */
	private static ControllerParameter lambdaPIControllerModel(double kGain, double tTime,
															   double lambdaValue)
	{
		double kp = (tTime / (kGain * lambdaValue));
		double ki = tTime;
		double kd = 0;

		return new ControllerParameter(TuningType.IMC, ProcessType.LambdaModelBasedTuning,
				ControlType.PI, kp, ki, kd);
	}

	/**
	 * Compute the parameters of a controller
	 * @param kGain Gain.
	 * @param tTime First time constant.
	 * @param tSTime Second time constant.
	 * @param lambdaValue Adjust lambda value. It is null when default is applied.
	 * @return PID controller;
	 */
	private static ControllerParameter lambdaPIDControllerFirstModel(double kGain, double tTime,
																	 double tSTime,
																	 double lambdaValue)
	{
		double kp = ((tTime + tSTime) / (kGain * lambdaValue));
		double ki = (tTime + tSTime);
		double kd = ((tTime * tSTime) / (tTime + tSTime));

		return new ControllerParameter(TuningType.IMC, ProcessType.LambdaModelBasedTuning,
				ControlType.PID, kp, ki, kd);
	}

	/**
	 * Compute the parameters of a controller
	 * @param kGain Gain.
	 * @param tTime First time constant.
	 * @param epsilon Epsilon coefficient.
	 * @param lambdaValue Adjust lambda value. It is null when default is applied.
	 * @return PID controller;
	 */
	private static ControllerParameter lambdaPIDControllerSecondModel(double kGain, double tTime,
																	  double epsilon,
																	  double lambdaValue)
	{
		double kp = ((2 * epsilon * tTime) / (kGain * lambdaValue));
		double ki = (2 * epsilon * tTime);
		double kd = (tTime / (2 * epsilon));

		return new ControllerParameter(TuningType.IMC, ProcessType.LambdaModelBasedTuning,
				ControlType.PID, kp, ki, kd);
	}

}
