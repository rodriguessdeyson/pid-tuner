package com.domain.models.tuning;

import static org.junit.Assert.assertEquals;

import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.types.ProcessType;

import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class TuningConfigurationTests
{
    TuningConfiguration sut;

    public TuningConfigurationTests()
    {
        sut = new TuningConfiguration();
    }

    @Test
    public void when_OpenedLoopConstructorIsRequested_Expect_ObjectCreated()
    {
        ArrayList<ControllerParameter> controllerParameter = new ArrayList<>();
        ControllerParameter parameter = new ControllerParameter(ControlType.PI, 0.5, 1, 3);
        controllerParameter.add(parameter);

        ProcessType processType = ProcessType.Open;
        double gain = 0.5;
        double timeConstant = 5;
        double transportDelay = 1;
        double lambdaCoefficient = 0.25;
        double dampingRatio = 5;

        sut = new TuningConfiguration(processType, controllerParameter, gain, timeConstant, transportDelay, lambdaCoefficient, dampingRatio);

        assertEquals("Check Process Type", processType, sut.getProcessType());
        assertEquals("Check Controller Parameters", controllerParameter, sut.getControllerParameters());
        assertEquals("Check Gain parameter", gain, sut.getGain(), 0);
        assertEquals("Check Time Constant parameter", timeConstant, sut.getTimeConstant(), 0);
        assertEquals("Check Transport Delay parameter", transportDelay, sut.getTransportDelay(), 0);
        assertEquals("Check Lambda coefficient parameter", lambdaCoefficient, sut.getLambdaCoefficient(), 0);
        assertEquals("Check Damping Ratio parameter", dampingRatio, sut.getDampingRatio(), 0);
    }

    @Test
    public void when_ClosedLoopConstructorIsRequested_Expect_ObjectCreated()
    {
        ArrayList<ControllerParameter> controllerParameter = new ArrayList<>();
        ControllerParameter parameter = new ControllerParameter(ControlType.PI, 0.5, 1, 3);
        controllerParameter.add(parameter);

        ProcessType processType = ProcessType.Open;
        double ultimateGain = 0.5;
        double ultimatePeriod = 5;

        sut = new TuningConfiguration(processType, controllerParameter, ultimateGain, ultimatePeriod);

        assertEquals("Check Process Type", processType, sut.getProcessType());
        assertEquals("Check Ultimate Gain parameter", ultimateGain, sut.getUltimateGain(), 0);
        assertEquals("Check Ultimate Period parameter", ultimatePeriod, sut.getUltimatePeriod(), 0);
    }

    @Test
    public void when_SetProcessTypeIsRequested_Expect_ProcessTypeSet()
    {
        ProcessType processType = ProcessType.Regulator20;
        sut.setProcessType(processType);
        assertEquals("Check Process Type", processType, sut.getProcessType());
    }

    @Test
    public void when_SetControllerParametersIsRequested_Expect_ControllerParametersSet()
    {
        ArrayList<ControllerParameter> controllerParameter = new ArrayList<>();
        ControllerParameter parameter = new ControllerParameter(ControlType.PI, 0.5, 1, 3);
        controllerParameter.add(parameter);

        sut.setControllerParameters(controllerParameter);
        assertEquals("Check Controller Parameters", controllerParameter, sut.getControllerParameters());
    }

    @Test(expected = InvalidParameterException.class)
    public void when_ControllerParametersIsInvalid_Expect_InvalidException()
    {
        ArrayList<ControllerParameter> controllerParameter = null;
        sut.setControllerParameters(controllerParameter);
    }

    @Test
    public void when_SetGainIsRequested_Expect_GainSet()
    {
        double gain = 25;
        sut.setGain(gain);
        assertEquals("Check Gain parameter", gain, sut.getGain(), 0);
    }

    @Test
    public void when_SetTimeConstantIsRequested_Expect_TimeConstantSet()
    {
        double timeConstant = 2;
        sut.setTimeConstant(timeConstant);
        assertEquals("Check Time Constant parameter", timeConstant, sut.getTimeConstant(), 0);
    }

    @Test
    public void when_SetTransportDelayIsRequested_Expect_TransportDelaySet()
    {
        double transportDelay = 1;
        sut.setTransportDelay(transportDelay);
        assertEquals("Check Transport Delay parameter", transportDelay, sut.getTransportDelay(), 0);
    }

    @Test
    public void when_SetLambdaCoefficientIsRequested_Expect_LambdaCoefficientSet()
    {
        double lambdaCoefficient = 36;
        sut.setLambdaCoefficient(lambdaCoefficient);
        assertEquals("Check Lambda coefficient parameter", lambdaCoefficient, sut.getLambdaCoefficient(), 0);
    }

    @Test
    public void when_SetDampingRatioIsRequested_Expect_DampingRatioSet()
    {
        double dampingRatio = 4;
        sut.setDampingRatio(dampingRatio);
        assertEquals("Check Damping Ratio parameter", dampingRatio, sut.getDampingRatio(), 0);
    }

    @Test
    public void when_SetUltimateGainIsRequested_Expect_UltimateGainSet()
    {
        double ultimateGain = 0.5;
        sut.setUltimateGain(ultimateGain);
        assertEquals("Check Ultimate Gain parameter", ultimateGain, sut.getUltimateGain(), 0);
    }

    @Test
    public void when_SetUltimatePeriodIsRequested_Expect_UltimatePeriodSet()
    {
        double ultimatePeriod = 5;
        sut.setUltimatePeriod(ultimatePeriod);
        assertEquals("Check Ultimate Period parameter", ultimatePeriod, sut.getUltimatePeriod(), 0);
    }
}
