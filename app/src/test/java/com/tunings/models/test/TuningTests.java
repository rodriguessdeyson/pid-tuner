package com.tunings.models.test;

import static org.junit.Assert.assertEquals;

import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameter;
import com.tunings.models.ProcessType;
import com.tunings.models.Tuning;
import com.tunings.models.TuningConfiguration;
import com.tunings.models.TuningType;

import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class TuningTests
{
    private Tuning sut;

    public TuningTests()
    {
        sut = new Tuning();
    }

    @Test
    public void when_ConstructorIsRequested_Expect_ObjectCreated()
    {
        ArrayList<ControllerParameter> servoControllerParameters = new ArrayList<>();
        ControllerParameter piSParameter = new ControllerParameter(ControlType.PI, 1, 2, 0);
		ControllerParameter pidSParameter = new ControllerParameter(ControlType.PID, 3, 4, 5);
		servoControllerParameters.add(piSParameter);
		servoControllerParameters.add(pidSParameter);

		ArrayList<ControllerParameter> regulatorControllerParameters = new ArrayList<>();
		ControllerParameter piRParameter = new ControllerParameter(ControlType.PI, 0.1, 0.2, 0);
		ControllerParameter pidRParameter = new ControllerParameter(ControlType.PID, 0.3, 0.4, 0.5);
		regulatorControllerParameters.add(piRParameter);
		regulatorControllerParameters.add(pidRParameter);

        ArrayList<TuningConfiguration> configuration = new ArrayList<>();

		TuningConfiguration servo = new TuningConfiguration(ProcessType.Servo,
				servoControllerParameters, 0.5, 5, 1);
		configuration.add(servo);

        TuningConfiguration regulator = new TuningConfiguration(ProcessType.Regulator,
			regulatorControllerParameters, 0.5, 5, 1);
		configuration.add(regulator);

        String name = "ZN";
        String description = "ZN Description";
        TuningType type = TuningType.ZN;
        sut = new Tuning(name, description, type, configuration);

        assertEquals("Check Tuning Name", name, sut.getTuningName());
        assertEquals("Check Tuning Description", description, sut.getTuningDescription());
        assertEquals("Check Tuning Type", type, sut.getTuningType());
        assertEquals("Check Tuning Configuration", configuration, sut.getConfiguration());
    }

    @Test()
    public void when_SetTuningNameMethodIsRequested_Expect_TurningNameSet()
    {
        String tuningName = "ITAE";
        sut.setTuningName(tuningName);

        assertEquals("Check Tuning Name", tuningName, sut.getTuningName());
    }

    @Test(expected = InvalidParameterException.class)
    public void when_TuningNameMethodIsInvalid_Expect_InvalidException()
    {
        String tuningName = "";
        sut.setTuningName(tuningName);
    }

    @Test
    public void when_SetTuningDescriptionMethodIsRequested_Expect_TurningDescriptionSet()
    {
        String tuningDescription = "ITAE Method Test";
        sut.setTuningDescription(tuningDescription);

        assertEquals("Check Tuning Description", tuningDescription, sut.getTuningDescription());
    }

    @Test(expected = InvalidParameterException.class)
    public void when_TuningDescriptionIsInvalid_Expect_InvalidException()
    {
        String tuningDescription = "";
        sut.setTuningDescription(tuningDescription);
    }

    @Test
    public void when_SetTuningTypeMethodIsRequested_Expect_TurningTypeSet()
    {
        TuningType tuningType = TuningType.ZN;
        sut.setTuningType(tuningType);

        assertEquals("Check Tuning Type", tuningType, sut.getTuningType());
    }

    @Test
    public void when_SetConfigurationMethodIsRequested_Expect_ConfigurationSet()
    {
		ArrayList<ControllerParameter> servoControllerParameters = new ArrayList<>();
		ControllerParameter piSParameter = new ControllerParameter(ControlType.P, 1, 0, 0);
		ControllerParameter pidSParameter = new ControllerParameter(ControlType.PID, 3, 4, 5);
		servoControllerParameters.add(piSParameter);
		servoControllerParameters.add(pidSParameter);

		ArrayList<TuningConfiguration> configuration = new ArrayList<>();
		TuningConfiguration servo = new TuningConfiguration(ProcessType.Servo,
				servoControllerParameters, 0.5, 5, 1);
		configuration.add(servo);

        sut.setConfiguration(configuration);

        assertEquals("Check Tuning Configuration", configuration, sut.getConfiguration());
    }

    @Test(expected = InvalidParameterException.class)
    public void when_ConfigurationIsInvalid_Expect_InvalidException()
    {
        ArrayList<TuningConfiguration> configuration = null;
        sut.setConfiguration(configuration);
    }
}
