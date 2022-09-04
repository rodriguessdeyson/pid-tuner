package com.tunings.methods;

import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

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
	 * @param cpType Type of process.
	 * @param lambdaValue Adjust lambda value. It is null when default is applied.
	 * @param kGain Gain.
	 * @param tTime Time constant.
	 * @param tDelay Transport delay.
	 * @return Return the controller PID parameters.
	 */
	public static ControllerParameters Compute(ControlType controlType, ControlProcessType cpType,
		Double lambdaValue, Double kGain, Double tTime, Double tDelay)
	{
		// Proportional Gain.
		double kp;

		// Integral gain.
		double ki;

		// Derivative gain.
		double kd;

		if (cpType == ControlProcessType.LambdaTuning)
		{
			switch (controlType)
			{
				case PI:
					kp = ((2 * tTime + tDelay) / (kGain * 2 * lambdaValue));
					ki = tTime + (tDelay / 2);
					kd = 0;
					break;
				case PID:
					kp = ((2 * tTime + tDelay) / (kGain * (2 * lambdaValue + tDelay)));
					ki = tTime + (tDelay / 2);
					kd = (tTime * tDelay / (2 * tTime + tDelay));
					break;
				default:
					throw new InvalidParameterException(controlType.toString());
			}
		}
		else
			throw new InvalidParameterException(cpType.toString());

		// Create return object.
		ControllerParameters imc = new ControllerParameters();
		imc.setKP(kp);
		imc.setKI(ki);
		imc.setKD(kd);
		imc.setType(controlType);
		return imc;
	}

	/**
	 * Compute the parameters of a controller based on IMC approach with lambda value and
	 * without dead time (Model type required).
	 * @param controlType Type of controller.
	 * @param cpType Type of process.
	 * @param lambdaValue Adjust lambda value. It is null when default is applied.
	 * @param kGain Gain.
	 * @param tTime First time constant.
	 * @param tSTime Second time constant.
	 * @return Return the controller PID parameters.
	 */
	public static ControllerParameters Compute(ControlType controlType, ControlProcessType cpType,
		TransferFunctionModel tfModel, Double lambdaValue, Double epsilon, Double kGain,
		Double tTime, Double tSTime)
	{
		// Proportional Gain.
		double kp;

		// Integral gain.
		double ki;

		// Derivative gain.
		double kd;

		if (cpType == ControlProcessType.LambdaTuning)
		{
			switch (tfModel)
			{
				case P_Controller_Model:
					if (controlType == ControlType.P)
					{
						kp = (1 / (kGain * lambdaValue));
						ki = 0;
						kd = 0;
					}
					else
						throw new InvalidParameterException(controlType.toString());
					break;
				case PD_Controller_Model:
					if (controlType == ControlType.PD)
					{
						kp = (1 / (kGain * lambdaValue));
						ki = 0;
						kd = tTime;
					}
					else
						throw new InvalidParameterException(controlType.toString());
					break;
				case PI_Controller_Model:
					if (controlType == ControlType.PI)
					{
						kp = (tTime / (kGain * lambdaValue));
						ki = tTime;
						kd = 0;
					}
					else
						throw new InvalidParameterException(controlType.toString());
					break;
				case PID_Controller_Model1:
					if (controlType == ControlType.PID)
					{
						kp = ((tTime + tSTime) / (kGain * lambdaValue));
						ki = (tTime + tSTime);
						kd = ((tTime * tSTime) / (tTime + tSTime));
					}
					else
						throw new InvalidParameterException(controlType.toString());
					break;
				case PID_Controller_Model2:
					if (controlType == ControlType.PID)
					{
						kp = ((2 * epsilon * tTime) / (kGain * lambdaValue));
						ki = (2 * epsilon * tTime);
						kd = (tTime / (2 * epsilon));
					}
					else
						throw new InvalidParameterException(controlType.toString());
					break;
				default:
					throw new InvalidParameterException(tfModel.toString());
			}
		}
		else
			throw new InvalidParameterException(cpType.toString());

		// Create the return object.
		ControllerParameters imc = new ControllerParameters();
		imc.setKP(kp);
		imc.setKI(ki);
		imc.setKD(kd);
		imc.setType(controlType);
		return imc;
	}
}
