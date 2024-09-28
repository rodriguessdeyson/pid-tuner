package com.domain.models.tuning;

import static org.junit.Assert.assertEquals;

import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.TuningType;

import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class TuningModelTests
{
    private TuningModel sut;

    public TuningModelTests()
    {
        sut = new TuningModel();
    }

    @Test
    public void when_ConstructorIsRequested_Expect_ObjectCreated()
    {
        String name = "ZN";
        String description = "ZN Description";
        TuningType type = TuningType.ZN;
        ArrayList<ProcessType> processTypes = new ArrayList<>();
        processTypes.add(ProcessType.Servo);
		TransferFunction tf = new TransferFunction(1, 2, 3);
        sut = new TuningModel(name, description, type, tf, processTypes);

        assertEquals("Check Tuning Name", name, sut.getName());
        assertEquals("Check Tuning Description", description, sut.getDescription());
        assertEquals("Check Tuning Type", type, sut.getType());
        assertEquals("Check Tuning Transfer Function", tf, sut.getTransferFunction());
        assertEquals("Check Tuning Process Types", processTypes, sut.getProcessTypes());
    }

    @Test()
    public void when_SetTuningNameMethodIsRequested_Expect_TurningName()
    {
        String tuningName = "ITAE";
        sut.setName(tuningName);

        assertEquals("Check Tuning Name", tuningName, sut.getName());
    }

    @Test(expected = InvalidParameterException.class)
    public void when_TuningNameMethodIsInvalid_Expect_InvalidException()
    {
        String tuningName = "  ";
        sut.setName(tuningName);
    }

    @Test
    public void when_SetTuningDescriptionMethodIsRequested_Expect_TurningDescription()
    {
        String tuningDescription = "ITAE Method Test";
        sut.setDescription(tuningDescription);

        assertEquals("Check Tuning Description", tuningDescription, sut.getDescription());
    }

    @Test(expected = InvalidParameterException.class)
    public void when_TuningDescriptionIsInvalid_Expect_InvalidException()
    {
        String tuningDescription = "";
        sut.setDescription(tuningDescription);
    }

    @Test
    public void when_SetTuningTypeMethodIsRequested_Expect_TurningType()
    {
        TuningType tuningType = TuningType.ZN;
        sut.setType(tuningType);

        assertEquals("Check Tuning Type", tuningType, sut.getType());
    }

    @Test
    public void when_SetTransferFunctionMethodIsRequested_Expect_TransferFunction()
    {
        TransferFunction tf = new TransferFunction(3, 23, 233);
        sut.setTransferFunction(tf);

        assertEquals("Check Tuning Configuration", tf, sut.getTransferFunction());
    }

    @Test(expected = InvalidParameterException.class)
    public void when_ConfigurationIsInvalid_Expect_InvalidException()
    {
        TransferFunction transferFunction = null;
        sut.setTransferFunction(transferFunction);
    }

//    @Test
//    public void when_ParcelableIsRequested_Expect_Parcelable()
//    {
//        // 1. Obtain a Parcel object
//        Parcel parcel = Parcel.obtain();
//
//        // 2. Write the TransferFunction object to the Parcel
//        sut.setName("Test 01");
//        sut.setDescription("Test 01");
//        sut.setType(TuningType.ZN);
//        sut.setTransferFunction(new TransferFunction(1, 2, 3));
//        sut.setProcessTypes(new ArrayList<>());
//        sut.writeToParcel(parcel, 0);
//
//        // 3. Reset the Parcel's data position
//        parcel.setDataPosition(0);
//
//        // 4. Create a new TransferFunction object from the Parcel
//        TuningModel recreatedTuningModel = TuningModel.CREATOR.createFromParcel(parcel);
//
//        // 5. Assert that the original and recreated objects are equal
//        assertEquals(sut.getName(), recreatedTuningModel.getName());
//        assertEquals(sut.getName(), recreatedTuningModel.toString());
//        assertEquals(sut.getDescription(), recreatedTuningModel.getDescription());
//        assertEquals(sut.getType(), recreatedTuningModel.getType());
//        assertEquals(sut.getTransferFunction().getGain(), recreatedTuningModel.getTransferFunction().getGain(), 0.01);
//        assertEquals(sut.getTransferFunction().getTimeConstant(), recreatedTuningModel.getTransferFunction().getTimeConstant(), 0.01);
//        assertEquals(sut.getTransferFunction().getTransportDelay(), recreatedTuningModel.getTransferFunction().getTransportDelay(), 0.01);
//
//        // 6. Recycle the Parcel
//        parcel.recycle();
//    }
}

