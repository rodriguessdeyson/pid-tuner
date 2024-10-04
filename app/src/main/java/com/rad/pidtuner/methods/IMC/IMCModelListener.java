package com.rad.pidtuner.methods.IMC;

public interface IMCModelListener
{
	/**
	 * Trigger when a model is selected.
	 * @param controllerTag The tag of the selected model.
	 */
	void onModelSelected(String controllerTag);

	/**
	 * Trigger when the dialog is dismissed.
	 */
	void onDialogDismissed();
}
