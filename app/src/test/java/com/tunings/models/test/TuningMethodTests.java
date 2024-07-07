package com.tunings.models.test;

import static org.junit.Assert.assertEquals;

import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;
import com.tunings.models.TuningMethod;

import org.junit.Test;

import java.util.ArrayList;

public class TuningMethodTests
{
    private TuningMethod sut;

    public TuningMethodTests()
    {
        sut = new TuningMethod();
    }

    @Test
    public void ValidateSetTuningNameMethod()
    {
        String tuningName = "ITAE";
        sut.setTuningName(tuningName);

        assertEquals("Check Tuning Name", tuningName, sut.getTuningName());
    }

    @Test
    public void ValidateSetTuningDescriptionMethod()
    {
        String tuningDescription = "ITAE Method Test";
        sut.setTuningDescription(tuningDescription);

        assertEquals("Check Tuning Description", tuningDescription, sut.getTuningDescription());
    }

    @Test
    public void ValidateSetControlProcessTypesMethod()
    {
        ArrayList<ControlProcessType> controlProcessTypes = new ArrayList<>();
        controlProcessTypes.add(ControlProcessType.Open);
        controlProcessTypes.add(ControlProcessType.Closed);

        sut.setControlProcessTypes(controlProcessTypes);

        assertEquals("Check Tuning Control Process Type", controlProcessTypes, sut.getControlProcessTypes());
    }

    @Test
    public void ValidateSetControlTypesMethod()
    {
        ArrayList<ControlType> controlTypes = new ArrayList<>();
        controlTypes.add(ControlType.PD);
        controlTypes.add(ControlType.PID);

        sut.setControlTypes(controlTypes);

        assertEquals("Check Tuning Control Type", controlTypes, sut.getControlTypes());
    }

    @Test
    public void ValidateSetParametersMethod()
    {
        ControllerParameters param = new ControllerParameters(ControlType.PID, 0.5, 1, 2);
        ArrayList<ControllerParameters> controllerParameters = new ArrayList<>();
        controllerParameters.add(param);

        sut.setParameters(controllerParameters);

        assertEquals("Check Tuning Control Type", controllerParameters, sut.getParameters());
    }
}
