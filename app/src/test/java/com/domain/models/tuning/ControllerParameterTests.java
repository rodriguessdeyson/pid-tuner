package com.domain.models.tuning;

import static org.junit.Assert.assertEquals;

import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.TuningType;

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
        TuningType tuningType = TuningType.ZN;
        ProcessType processType = ProcessType.Open;
        ControlType controlType = ControlType.PID;
        double kp = 0.5;
        double ki = 1.75;
        double kd = 0.79;

        sut = new ControllerParameter(tuningType, processType, controlType, kp, ki, kd);

        assertEquals("Check Tuning Type", tuningType, sut.getTuningType());
        assertEquals("Check Process Type", processType, sut.getProcessType());
        assertEquals("Check Control Type", controlType, sut.getControlType());
        assertEquals("Check Kp parameters", kp, sut.getKP(), 0);
        assertEquals("Check Ki parameters", ki, sut.getKI(), 0);
        assertEquals("Check Kd parameters", kd, sut.getKD(), 0);
    }

    @Test()
    public void when_SetTuningTypeMethodIsRequested_Expect_TuningType()
    {
        TuningType tuningType = TuningType.CC;
        sut.setTuningType(tuningType);

        assertEquals("Check Tuning Type", tuningType, sut.getTuningType());
    }

    @Test()
    public void when_SetProcessTypeMethodIsRequested_Expect_ProcessType()
    {
        ProcessType processType = ProcessType.Regulator20;
        sut.setProcessType(processType);

        assertEquals("Check Process Type", processType, sut.getProcessType());
    }

    @Test()
    public void when_SetControlTypeMethodIsRequested_Expect_ControlType()
    {
        ControlType controlType = ControlType.PI;
        sut.setControlType(controlType);

        assertEquals("Check Control Type", controlType, sut.getControlType());
    }

    @Test
    public void when_SetKPMethodIsRequested_Expect_KP()
    {
        double kp = 0.5;
        sut.setKP(kp);

        assertEquals("Check Kp parameters", kp, sut.getKP(), 0);
    }

    @Test
    public void when_SetKIMethodIsRequested_Expect_KI()
    {
        double ki = 1.75;
        sut.setKI(ki);

        assertEquals("Check Ki parameters", ki, sut.getKI(), 0);
    }

    @Test
    public void when_SetKDMethodIsRequested_Expect_KD()
    {
        double kd = 0.79;
        sut.setKD(kd);

        assertEquals("Check Kd parameters", kd, sut.getKD(), 0);
    }
}
