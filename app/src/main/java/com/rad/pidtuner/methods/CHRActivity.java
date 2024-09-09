package com.rad.pidtuner.methods;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.rad.pidtuner.R;
import com.rad.pidtuner.ResultActivity;
import com.rad.pidtuner.utils.BottomSheetDialog;
import com.rad.pidtuner.utils.BottomSheetDialogType;
import com.rad.pidtuner.utils.Logger;
import com.rad.pidtuner.utils.Parser;
import com.tunings.methods.CHR;
import com.tunings.models.ProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameter;
import com.tunings.models.Tuning;
import com.tunings.models.TuningConfiguration;
import com.tunings.models.TuningType;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class CHRActivity extends AppCompatActivity
{
	//region Attributes

	/**
	 * Button reference to compute event.
	 */
	private Button ComputeButton;

	/**
	 CheckBox reference to Proportional parameter.
	 */
	private CheckBox CheckBoxP;

	/**
	 CheckBox reference to Proportional-Integral parameter.
	 */
	private CheckBox CheckBoxPI;

	/**
	 CheckBox reference to Proportional-Derivative-integral parameter.
	 */
	private CheckBox CheckBoxPID;

	/**
	 CheckBox reference to process servo.
	 */
	private CheckBox CheckBoxServo;

	/**
	 CheckBox reference to  process regulator.
	 */
	private CheckBox CheckBoxRegulator;

	/**
	 * Switch to identify 20% regulator/servo.
	 */
	private SwitchMaterial SwitchIs20;

	/**
	 EditText reference to process gain parameter.
	 */
	private EditText EditTextProcessGain;

	/**
	 EditText reference to time constant parameter.
	 */
	private EditText EditTextProcessTimeConstant;

	/**
	 EditText reference to transport delay parameter.
	 */
	private EditText EditTextProcessTransportDelay;

	/**
	 * ImageView reference to show about dialog.
	 */
	private ImageView ImageViewMethodInfo;

	//endregion

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_chr);

		// Find Views Reference.
		InitializeViews();

		// Start the listener event handler.
		InitializeEventListener();
	}

	/**
	 Initialize the control views.
	 */
	@SuppressLint("SetTextI18n")
	private void InitializeViews()
	{
		ComputeButton                 = findViewById(R.id.ButtonComputePID);
		CheckBoxP                     = findViewById(R.id.CheckBoxP);
		CheckBoxPI                    = findViewById(R.id.CheckBoxPI);
		CheckBoxPID                   = findViewById(R.id.CheckBoxPID);
		CheckBoxServo                 = findViewById(R.id.CheckBoxServo);
		CheckBoxRegulator             = findViewById(R.id.CheckBoxRegulator);
		SwitchIs20                    = findViewById(R.id.SwitchIs20);
		EditTextProcessGain           = findViewById(R.id.EditTextGain);
		EditTextProcessTimeConstant   = findViewById(R.id.EditTextTimeConstant);
		EditTextProcessTransportDelay = findViewById(R.id.EditTextTransportDelay);
		ImageViewMethodInfo           = findViewById(R.id.ImageViewMethodInfo);
	}

	/**
	 Initialize the buttons events.
	 */
	private void InitializeEventListener()
	{
		// Handle the button click.
		ComputeButton.setOnClickListener(v ->
		{
			// Validates the input, from top-down approach.
			if (!ValidateProcessParameters())
				return;

			ComputeController();
		});

		ImageViewMethodInfo.setOnClickListener(v ->
		{
			String title = getResources().getString(R.string.chr_about_title);
			String description = getResources().getString(R.string.chr_about_description);

			BottomSheetDialog bottomSheet = new BottomSheetDialog(title, description);
			bottomSheet.show(getSupportFragmentManager(),
					"ModalBottomSheet");
		});
	}

	private boolean ValidateProcessParameters()
	{
		// Validates if at least one controller type is checked.
		if (!CheckBoxServo.isChecked() && !CheckBoxRegulator.isChecked())
		{
			Logger.Show(this, R.string.ProcessTypeIsRequired);
			return false;
		}

		// Validates if at least one controller type is checked.
		if (!CheckBoxP.isChecked() && !CheckBoxPI.isChecked() && !CheckBoxPID.isChecked())
		{
			Logger.Show(this, R.string.ControllerTypeIsRequired);
			return false;
		}

		// Validates if the process data are filled.
		if (EditTextProcessGain.getText().toString().isEmpty())
		{
			EditTextProcessGain.setError(getResources().getString(R.string.GainError));
			Logger.Show(this, R.string.GainError);
			return false;
		}

		// Validates if the time constant data are filled.
		if (EditTextProcessTimeConstant.getText().toString().isEmpty())
		{
			EditTextProcessTimeConstant.setError(getResources().getString(R.string.TimeConstantError));
			Logger.Show(this, R.string.TimeConstantError);
			return false;
		}

		// Validates if the dead time data are filled.
		if (EditTextProcessTransportDelay.getText().toString().isEmpty())
		{
			EditTextProcessTransportDelay.setError(getResources().getString(R.string.TransportDelayError));
			Logger.Show(this, R.string.TransportDelayError);
			return false;
		}
		return true;
	}

	private void ComputeController()
	{
		// Get the selected process.
		ArrayList<ProcessType> processTypes = new ArrayList<>();
		if (CheckBoxServo.isChecked())
			processTypes.add(SwitchIs20.isChecked() ? ProcessType.Servo20 : ProcessType.Servo);
		if (CheckBoxRegulator.isChecked())
			processTypes.add(SwitchIs20.isChecked() ? ProcessType.Regulator20 : ProcessType.Regulator);

		// Get the control types.
		ArrayList<ControlType> controlTypes = new ArrayList<>();
		if (CheckBoxP.isChecked()) controlTypes.add(ControlType.P);
		if (CheckBoxPI.isChecked()) controlTypes.add(ControlType.PI);
		if (CheckBoxPID.isChecked()) controlTypes.add(ControlType.PID);

		// Get the transfer function parameters.
		double pGain = Parser.GetDouble(EditTextProcessGain.getText().toString());
		double pTime = Parser.GetDouble(EditTextProcessTimeConstant.getText().toString());
		double pDead = Parser.GetDouble(EditTextProcessTransportDelay.getText().toString());

		// Compute the CHR Controller.
		ArrayList<TuningConfiguration> configuration = new ArrayList<>();
		for (ProcessType processType : processTypes)
		{
			ArrayList<ControllerParameter> controllerParameters = new ArrayList<>();
			for (ControlType controlType :  controlTypes)
			{
				ControllerParameter cp;
				switch (processType)
				{
					case Servo:
						cp = CHR.ComputeServo(controlType, pGain, pTime, pDead);
						controllerParameters.add(cp);
						break;
					case Servo20:
						cp = CHR.ComputeServo20UP(controlType, pGain, pTime, pDead);
						controllerParameters.add(cp);
						break;
					case Regulator:
						cp = CHR.ComputeRegulator(controlType, pGain, pTime, pDead);
						controllerParameters.add(cp);
						break;
					case Regulator20:
						cp = CHR.ComputeRegulator20UP(controlType, pGain, pTime, pDead);
						controllerParameters.add(cp);
						break;
					default:
						throw new InvalidParameterException(processType.toString());
				}
			}
			configuration.add(new TuningConfiguration(processType, controllerParameters, pGain,
				pTime, pDead));
		}

		// Get the tuning information.
		Tuning chrMethod = new Tuning("Chien-Hrones-Reswick", "", TuningType.CHR,
				configuration);

		// Pass through intent to the next activity the results information.
		Intent resultActivity = new Intent(CHRActivity.this, ResultActivity.class);
		resultActivity.putExtra("RESULT", chrMethod);
		startActivity(resultActivity);
	}
}