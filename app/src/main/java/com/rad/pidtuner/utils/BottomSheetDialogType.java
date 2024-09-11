package com.rad.pidtuner.utils;

public enum BottomSheetDialogType
{
	/**
	 * About Dialog.
	 */
	About(0),

	/**
	 * Help Dialog.
	 */
	Help(1);

	/**
	 * Selected enum.
	 */
	private final int DialogType;

	/**
	 * Enum constructor.
	 * @param dialogType Index of the enum.
	 */
	BottomSheetDialogType(int dialogType) {
		this.DialogType = dialogType;
	}
}
