package com.domain.services.tuning.interfaces.imc;

public interface IMCModelListener
{
	/**
	 * Trigger when a model is selected.
	 * @param imageTag The image tag of the selected model.
	 */
	void onModelSelected(String imageTag);

	/**
	 * Trigger when the dialog is dismissed.
	 */
	void onDialogDismissed();
}
