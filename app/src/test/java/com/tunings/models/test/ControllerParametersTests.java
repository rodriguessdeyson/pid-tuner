package com.tunings.models.test;
import static org.junit.Assert.assertEquals;

import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;

import org.junit.Test;

public class ControllerParametersTests
{
    private ControllerParameters sut;

    public ControllerParametersTests()
    {
        sut = new ControllerParameters();
    }

    @Test
    public void ValidateConstructor()
    {
        ControlType controlType = ControlType.PID;
        double kp = 0.5;
        double ki = 1.75;
        double kd = 0.79;

        sut = new ControllerParameters(controlType, kp, ki, kd);

        assertEquals("Check Kp parameters", kp, sut.getKP(), 0);
        assertEquals("Check Ki parameters", ki, sut.getKI(), 0);
        assertEquals("Check Kd parameters", kd, sut.getKD(), 0);
    }
}
