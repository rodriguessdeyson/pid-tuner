package com.tunings.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class TuningConfiguration implements Parcelable
{
    //region Attributes

    /**
     * Type of process.
     */
    private ProcessType ProcessType;

    private double Gain;

    private double TimeConstant;

    private double TransportDelay;

    private double LambdaCoefficient;

    private double DampingRatio;

    private double UltimateGain; //TODO CHECK NAME

    private double UltimatePeriod; //TODO CHECK NAME


    /**
     * Parameters of the specified controller.
     */
    private ArrayList<ControllerParameter> ControllerParameters;

    //endregion

    //region Constructor

    public TuningConfiguration() {}

    public TuningConfiguration(ProcessType processType,
                               ArrayList<ControllerParameter> controllerParameters)
    {
        setProcessType(processType);
        setControllerParameters(controllerParameters);
    }

    public TuningConfiguration(ProcessType processType,
                               ArrayList<ControllerParameter> controllerParameters,
                               double gain, double timeConstant, double transportDelay)
    {
        this(processType, controllerParameters);
        setGain(gain);
        setTimeConstant(timeConstant);
        setTransportDelay(transportDelay);
    }

    public TuningConfiguration(ProcessType processType,
                               ArrayList<ControllerParameter> controllerParameters,
                               double gain, double timeConstant, double transportDelay,
                               double lambdaCoefficient)
    {
        this(processType, controllerParameters, gain, timeConstant, transportDelay);
        setLambdaCoefficient(lambdaCoefficient);
    }

    public TuningConfiguration(ProcessType processType,
                               ArrayList<ControllerParameter> controllerParameters,
                               double gain, double timeConstant, double transportDelay,
                               double lambdaCoefficient, double dampingRation)
    {
        this(processType, controllerParameters, gain, timeConstant, transportDelay, lambdaCoefficient);
        setDampingRatio(dampingRation);
    }

    public TuningConfiguration(ProcessType processType,
                               ArrayList<ControllerParameter> controllerParameters,
                               double ultimateGain, double ultimatePeriod)
    {
        this(processType, controllerParameters);
        setUltimateGain(ultimateGain);
        setUltimatePeriod(ultimatePeriod);
    }


    //endregion

    //region Methods

    protected TuningConfiguration(Parcel in)
    {
        ProcessType = in.readParcelable(com.tunings.models.ProcessType.class.getClassLoader());
        Gain = in.readDouble();
        TimeConstant = in.readDouble();
        TransportDelay = in.readDouble();
        LambdaCoefficient = in.readDouble();
        DampingRatio = in.readDouble();
        UltimateGain = in.readDouble();
        UltimatePeriod = in.readDouble();
        ControllerParameters = in.createTypedArrayList(ControllerParameter.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeParcelable(ProcessType, flags);
        dest.writeDouble(Gain);
        dest.writeDouble(TimeConstant);
        dest.writeDouble(TransportDelay);
        dest.writeDouble(LambdaCoefficient);
        dest.writeDouble(DampingRatio);
        dest.writeDouble(UltimateGain);
        dest.writeDouble(UltimatePeriod);
        dest.writeTypedList(ControllerParameters);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TuningConfiguration> CREATOR = new Creator<TuningConfiguration>() {
        @Override
        public TuningConfiguration createFromParcel(Parcel in) {
            return new TuningConfiguration(in);
        }

        @Override
        public TuningConfiguration[] newArray(int size) {
            return new TuningConfiguration[size];
        }
    };

    public void setProcessType(ProcessType processType)
    {
        ProcessType = processType;
    }

    public void setControllerParameters(ArrayList<ControllerParameter> controllerParameters)
    {
        if (controllerParameters == null)
            throw new InvalidParameterException("Controller parameters must be not null");
        ControllerParameters = controllerParameters;
    }

    public void setGain(double gain)
    {
        Gain = gain;
    }

    public void setTimeConstant(double timeConstant)
    {
        TimeConstant = timeConstant;
    }

    public void setTransportDelay(double transportDelay)
    {
        TransportDelay = transportDelay;
    }

    public void setLambdaCoefficient(double lambdaCoefficient)
    {
        LambdaCoefficient = lambdaCoefficient;
    }

    public void setDampingRatio(double dampingRatio)
    {
        DampingRatio = dampingRatio;
    }

    public void setUltimateGain(double ultimateGain)
    {
        UltimateGain = ultimateGain;
    }

    public void setUltimatePeriod(double ultimatePeriod)
    {
        UltimatePeriod = ultimatePeriod;
    }

    public ProcessType getProcessType()
    {
        return ProcessType;
    }

    public double getGain()
    {
        return Gain;
    }

    public double getTimeConstant()
    {
        return TimeConstant;
    }

    public double getTransportDelay()
    {
        return TransportDelay;
    }

    public double getLambdaCoefficient()
    {
        return LambdaCoefficient;
    }

    public double getDampingRatio()
    {
        return DampingRatio;
    }

    public double getUltimateGain()
    {
        return UltimateGain;
    }

    public double getUltimatePeriod()
    {
        return UltimatePeriod;
    }

    public ArrayList<ControllerParameter> getControllerParameters()
    {
        return ControllerParameters;
    }

    //endregion
}
