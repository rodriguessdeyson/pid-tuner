package com.tunings.methods;

import com.tunings.models.ProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameter;

import java.security.InvalidParameterException;

public class IMC
{
	/**
	 * IMC Transfer Function Model without dead time (Transport Delay).
	 */
	public enum TransferFunctionModel
	{
		P_Controller_Model,
		PD_Controller_Model,
		PI_Controller_Model,
		PID_Controller_Model1,
		PID_Controller_Model2,
	}

	/**
	 * Compute the parameters of a controller based on IMC approach with lambda and dead time.
	 * @param controlType Type of controller.
	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @param lambdaValue Adjust lambda value. It is null when default is applied.
	 * @return Return the controller PID parameters.
	 */
	public static ControllerParameter ComputeLambdaTuning(ControlType controlType,
                                                          double kGain, double tTime, double tDelay,
														  double lambdaValue)
	{
		if (controlType == ControlType.P)
			throw new InvalidParameterException(controlType.toString());

		switch (controlType)
		{
			case PI:
				return LambdaPIController(kGain, tTime, tDelay, lambdaValue);
			case PID:
				return LambdaPIDController(kGain, tTime, tDelay, lambdaValue);
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
	private static ControllerParameter LambdaPIController(double kGain, double tTime, double tDelay,
														  double lambdaValue)
	{
		double kp = ((2 * tTime + tDelay) / (kGain * 2 * lambdaValue));
		double ki = tTime + (tDelay / 2);
		double kd = 0;

		return new ControllerParameter(ControlType.PI, kp, ki, kd);
	}

	/**
	 * Compute the parameters of a PID controller.
	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @param lambdaValue Adjust lambda value. It is null when default is applied.
	 * @return PID controller;
	 */
	private static ControllerParameter LambdaPIDController(double kGain, double tTime,
														   double tDelay, double lambdaValue)
	{
		double kp = ((2 * tTime + tDelay) / (kGain * (2 * lambdaValue + tDelay)));
		double ki = tTime + (tDelay / 2);
		double kd = (tTime * tDelay / (2 * tTime + tDelay));

		return new ControllerParameter(ControlType.PID, kp, ki, kd);
	}

	/**
	 * Compute the parameters of a controller based on IMC approach with lambda value and
	 * without dead time (Model type required).
	 * @param kGain Gain.
	 * @param tTime First time constant.
	 * @param tSTime Second time constant.
	 * @param epsilon Epsilon coefficient.
	 * @param lambdaValue Adjust lambda value. It is null when default is applied.
	 * @return Return the controller PID parameters.
	 */
	public static ControllerParameter ComputeLambdaTuning(TransferFunctionModel tfModel,
														  double kGain, double tTime,
														  double tSTime, double epsilon,
														  double lambdaValue)
	{
		switch (tfModel)
		{
			case P_Controller_Model:
				return LambdaPControllerModel(kGain, lambdaValue);
			case PD_Controller_Model:
				return LambdaPDControllerModel(kGain, tTime, lambdaValue);
			case PI_Controller_Model:
				return LambdaPIControllerModel(kGain, tTime, lambdaValue);
			case PID_Controller_Model1:
				return LambdaPIDControllerFirstModel(kGain, tTime, tSTime, lambdaValue);
			case PID_Controller_Model2:
				return LambdaPIDControllerSecondModel(kGain, tTime, epsilon, lambdaValue);
			default:
				throw new InvalidParameterException(tfModel.toString());
		}
	}

	/**
	 * Compute the parameters of a controller
	 * @param kGain Gain.
	 * @param lambdaValue Adjust lambda value. It is null when default is applied.
	 * @return P controller;
	 */
	private static ControllerParameter LambdaPControllerModel(double kGain, double lambdaValue)
	{
		double kp = (1 / (kGain * lambdaValue));
		double ki = 0;
		double kd = 0;

		return new ControllerParameter(ControlType.P, kp, ki, kd);
	}

	/**
	 * Compute the parameters of a controller
	 * @param kGain Gain.
	 * @param tTime First time constant.
	 * @param lambdaValue Adjust lambda value. It is null when default is applied.
	 * @return PD controller;
	 */
	private static ControllerParameter LambdaPDControllerModel(double kGain, double tTime,
															   double lambdaValue)
	{
		double kp = (1 / (kGain * lambdaValue));
		double ki = 0;
		double kd = tTime;

		return new ControllerParameter(ControlType.PD, kp, ki, kd);
	}

	/**
	 * Compute the parameters of a controller
	 * @param kGain Gain.
	 * @param tTime First time constant.
	 * @param lambdaValue Adjust lambda value. It is null when default is applied.
	 * @return  controller;
	 */
	private static ControllerParameter LambdaPIControllerModel(double kGain, double tTime,
															   double lambdaValue)
	{
		double kp = (tTime / (kGain * lambdaValue));
		double ki = tTime;
		double kd = 0;

		return new ControllerParameter(ControlType.PI, kp, ki, kd);
	}

	/**
	 * Compute the parameters of a controller
	 * @param kGain Gain.
	 * @param tTime First time constant.
	 * @param tSTime Second time constant.
	 * @param lambdaValue Adjust lambda value. It is null when default is applied.
	 * @return PID controller;
	 */
	private static ControllerParameter LambdaPIDControllerFirstModel(double kGain, double tTime,
																	 double tSTime,
																	 double lambdaValue)
	{
		double kp = ((tTime + tSTime) / (kGain * lambdaValue));
		double ki = (tTime + tSTime);
		double kd = ((tTime * tSTime) / (tTime + tSTime));

		return new ControllerParameter(ControlType.PID, kp, ki, kd);
	}

	/**
	 * Compute the parameters of a controller
	 * @param kGain Gain.
	 * @param tTime First time constant.
	 * @param epsilon Epsilon coefficient.
	 * @param lambdaValue Adjust lambda value. It is null when default is applied.
	 * @return PID controller;
	 */
	private static ControllerParameter LambdaPIDControllerSecondModel(double kGain, double tTime,
																	  double epsilon,
																	  double lambdaValue)
	{
		double kp = ((2 * epsilon * tTime) / (kGain * lambdaValue));
		double ki = (2 * epsilon * tTime);
		double kd = (tTime / (2 * epsilon));

		return new ControllerParameter(ControlType.PID, kp, ki, kd);
	}

}
