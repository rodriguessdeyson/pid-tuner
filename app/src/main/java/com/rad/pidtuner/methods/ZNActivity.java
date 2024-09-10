package com.rad.pidtuner.methods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.google.android.material.textfield.TextInputLayout;

import com.rad.pidtuner.utils.BottomSheetDialog;
import com.rad.pidtuner.utils.Logger;
import com.rad.pidtuner.R;
import com.rad.pidtuner.ResultActivity;
import com.rad.pidtuner.utils.Parser;
import com.tunings.methods.ZN;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameter;
import com.tunings.models.ProcessType;
import com.tunings.models.Tuning;
import com.tunings.models.TuningConfiguration;
import com.tunings.models.TuningType;

import java.util.ArrayList;

public class ZNActivity extends AppCompatActivity
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
	 CheckBox reference to Proportional-Integral parameter.
	 */
	private RadioButton RadioButtonOpened;

	/**
	 CheckBox reference to Proportional-Derivative-integral parameter.
	 */
	private RadioButton RadioButtonClosed;

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
	 * Layout for hint gain.
	 */
	private TextInputLayout LayoutGain;

	/**
	 * Layout for hint time constant.
	 */
	private TextInputLayout LayoutTimeConstant;

	/**
	 * Layout for hint transport delay.
	 */
	private TextInputLayout LayoutTransportDelay;

	/**
	 * Button reference to show about dialog.
	 */
	private Button ButtonMethodInfo;

	//endregion

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_zn);

		// Find Views Reference.
		InitializeViews();

		// Start the listener event handler.
		InitializeEventListener();
	}

	/**
	 Initialize the control views.
	 */
	private void InitializeViews()
	{
		ComputeButton                 = findViewById(R.id.ButtonComputePID);
		CheckBoxP                     = findViewById(R.id.CheckBoxP);
		CheckBoxPI                    = findViewById(R.id.CheckBoxPI);
		CheckBoxPID                   = findViewById(R.id.CheckBoxPID);
		RadioButtonOpened             = findViewById(R.id.RadioButtonOpenedLoop);
		RadioButtonClosed             = findViewById(R.id.RadioButtonClosedLoop);
		EditTextProcessGain           = findViewById(R.id.EditTextGain);
		EditTextProcessTimeConstant   = findViewById(R.id.EditTextTimeConstant);
		EditTextProcessTransportDelay = findViewById(R.id.EditTextTransportDelay);
		LayoutGain                    = findViewById(R.id.TextInputLayoutGain);
		LayoutTimeConstant            = findViewById(R.id.TextInputLayoutTimeConstant);
		LayoutTransportDelay          = findViewById(R.id.TextInputLayoutTransportDelay);
		ButtonMethodInfo              = findViewById(R.id.ButtonMethodInfo);
	}

	/**
	 Initialize the buttons events.
	 */
	@SuppressLint("SetTextI18n")
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

		ButtonMethodInfo.setOnClickListener(v ->
		{
			String title = getString(R.string.zn_about_title);
			String description = getString(R.string.zn_about_description);

			BottomSheetDialog bottomSheet = new BottomSheetDialog(title, description);
			bottomSheet.show(getSupportFragmentManager(),
					"ModalBottomSheet");
		});

		RadioButtonOpened.setOnClickListener(v ->
		{
			LayoutGain.setHint(getResources().getString(R.string.hintGain));
			EditTextProcessGain.setEnabled(true);
			LayoutTimeConstant.setHint(getResources().getString(R.string.hintTime));
			LayoutTransportDelay.setHint(getResources().getString(R.string.hintDelay));
		});

		RadioButtonClosed.setOnClickListener(v ->
		{
			EditTextProcessGain.setText("");
			EditTextProcessGain.setError(null);
			LayoutGain.setHint(getResources().getString(R.string.hintClosed));
			EditTextProcessGain.setEnabled(false);
			LayoutTimeConstant.setHint(getResources().getString(R.string.hintKu));
			LayoutTransportDelay.setHint(getResources().getString(R.string.hintPu));
		});
	}

	private boolean ValidateProcessParameters()
	{
		// Validates if at least one controller type is checked.
		if (!CheckBoxP.isChecked() && !CheckBoxPI.isChecked() && !CheckBoxPID.isChecked())
		{
			Logger.Show(this, R.string.ControllerTypeIsRequired);
			return false;
		}

		// Validates if the process data are filled and is not zero.
		if (EditTextProcessGain.getText().toString().isEmpty() && RadioButtonOpened.isChecked())
		{
			EditTextProcessGain.setError(getResources().getString(R.string.GainError));
			Logger.Show(this, R.string.GainError);
			return false;
		}

		// Validates if the time constant data are filled.
		if (EditTextProcessTimeConstant.getText().toString().isEmpty())
		{
			if (RadioButtonClosed.isChecked())
			{
				EditTextProcessTimeConstant.setError(getResources().getString(R.string.KuError));
				Logger.Show(this, R.string.KuError);
				return false;
			}

			EditTextProcessTimeConstant.setError(getResources().getString(R.string.TimeConstantError));
			Logger.Show(this, R.string.TimeConstantError);
			return false;
		}

		// Validates if the dead time data are filled.
		if (EditTextProcessTransportDelay.getText().toString().isEmpty())
		{
			if (RadioButtonClosed.isChecked())
			{
				EditTextProcessTransportDelay.setError(getResources().getString(R.string.PuError));
				Logger.Show(this, R.string.PuError);
				return false;
			}
			EditTextProcessTransportDelay.setError(getResources().getString(R.string.TransportDelayError));
			Logger.Show(this, R.string.TransportDelayError);
			return false;
		}

		return true;
	}

	private void ComputeController()
	{
		// Get the transfer function parameters.
		double pGain = Parser.GetDouble(EditTextProcessGain.getText().toString());
		double pTime = Parser.GetDouble(EditTextProcessTimeConstant.getText().toString());
		double pDead = Parser.GetDouble(EditTextProcessTransportDelay.getText().toString());

		Tuning tuning = RadioButtonOpened.isChecked()
				? BuildOpenedTuningParameters(pGain, pTime, pDead)
				: BuildClosedTuningParameters(pTime, pDead);

		// Pass through intent to the next activity the results information.
		Intent resultActivity = new Intent(ZNActivity.this, ResultActivity.class);
		resultActivity.putExtra("RESULT", tuning);
		resultActivity.putExtra("Gain", pGain);
		resultActivity.putExtra("Time", pTime);
		resultActivity.putExtra("Dead", pDead);
		startActivity(resultActivity);
	}

	private Tuning BuildOpenedTuningParameters(double pGain, double pTime, double pDead)
	{
		// Get the control types.
		ArrayList<ControlType> controlTypes = new ArrayList<>();
		if (CheckBoxP.isChecked()) controlTypes.add(ControlType.P);
		if (CheckBoxPI.isChecked()) controlTypes.add(ControlType.PI);
		if (CheckBoxPID.isChecked()) controlTypes.add(ControlType.PID);

		// Compute the ZN Controller.
		ArrayList<ControllerParameter> controllerParameters = new ArrayList<>();
		for (ControlType controlType : controlTypes)
			controllerParameters.add(ZN.ComputeOpenLoop(controlType, pGain, pTime, pDead));

		// Set the tuning configuration.
		TuningConfiguration config = new TuningConfiguration(ProcessType.Open,
			controllerParameters, pGain, pTime, pDead);
		ArrayList<TuningConfiguration> configuration = new ArrayList<>();
		configuration.add(config);

		// Get the tuning information.
		return new Tuning("Ziegler and Nichols", "", TuningType.ZN, configuration);
	}

	private Tuning BuildClosedTuningParameters(double pTime, double pDead)
	{
		// Get the control types.
		ArrayList<ControlType> controlTypes = new ArrayList<>();
		if (CheckBoxP.isChecked()) controlTypes.add(ControlType.P);
		if (CheckBoxPI.isChecked()) controlTypes.add(ControlType.PI);
		if (CheckBoxPID.isChecked()) controlTypes.add(ControlType.PID);

		// Compute the ZN Controller.
		ArrayList<ControllerParameter> controllerParameters = new ArrayList<>();
		for (ControlType controlType : controlTypes)
			controllerParameters.add(ZN.ComputeClosedLoop(controlType, pTime, pDead));

		// Set the tuning configuration.
		TuningConfiguration config = new TuningConfiguration(ProcessType.Open,
			controllerParameters, pTime, pDead);
		ArrayList<TuningConfiguration> configuration = new ArrayList<>();
		configuration.add(config);

		// Get the tuning information.
		return new Tuning("Ziegler and Nichols", "", TuningType.ZN, configuration);
	}
}