package com.rad.pidtuner.methods.IMC;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IMCViewModel extends ViewModel
{
	/**
	 * MutableLiveData variable.
	 */
	private final MutableLiveData<String> myVariable = new MutableLiveData<>();

	/**
	 * Getter for LiveData.
	 * @return Get the observable value.
	 */
	public LiveData<String> getMyVariable()
	{
		return myVariable;
	}

	/**
	 * Method to change the value of the variable.
	 * @param value Set the observable value.
	 */
	public void setMyVariable(String value)
	{
		myVariable.setValue(value);
	}
}