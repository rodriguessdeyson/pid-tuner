package com.tunings.models.test;

import static org.junit.Assert.assertEquals;

import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameter;

import org.junit.Test;

public class ControllerParameterTests
{
    private ControllerParameter sut;

    public ControllerParameterTests()
    {
        sut = new ControllerParameter();
    }

    @Test
    public void when_ConstructorIsRequested_Expect_ObjectCreated()
    {
        ControlType controlType = ControlType.PID;
        double kp = 0.5;
        double ki = 1.75;
        double kd = 0.79;

        sut = new ControllerParameter(controlType, kp, ki, kd);

        assertEquals("Check Control Type", controlType, sut.getControlType());
        assertEquals("Check Kp parameters", kp, sut.getKP(), 0);
        assertEquals("Check Ki parameters", ki, sut.getKI(), 0);
        assertEquals("Check Kd parameters", kd, sut.getKD(), 0);
    }
}
