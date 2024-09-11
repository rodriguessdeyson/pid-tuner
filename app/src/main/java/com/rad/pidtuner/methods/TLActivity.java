package com.rad.pidtuner.methods;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.rad.pidtuner.R;
import com.rad.pidtuner.ResultActivity;
import com.rad.pidtuner.utils.BottomSheetDialog;
import com.rad.pidtuner.utils.Logger;
import com.rad.pidtuner.utils.Parser;
import com.tunings.methods.TL;
import com.tunings.models.ProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameter;
import com.tunings.models.Tuning;
import com.tunings.models.TuningConfiguration;
import com.tunings.models.TuningType;

import java.util.ArrayList;

public class TLActivity extends AppCompatActivity
{
	//region Attributes

	/**
	 * Button reference to compute event.
	 */
	private Button ComputeButton;

	/**
	 CheckBox reference to Proportional-integral parameter.
	 */
	private CheckBox CheckBoxPI;

	/**
	 CheckBox reference to Proportional-Derivative-integral parameter.
	 */
	private CheckBox CheckBoxPID;

	/**
	 EditText reference to process gain parameter.
	 */
	private EditText EditTextProcessGain;

	/**
	 EditText reference to time constant parameter.
	 */
	private EditText EditTextProcessTimeConstant;

	/**
	 * Button reference to show about dialog.
	 */
	private Button ButtonMethodInfo;

	//endregion

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tl);

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
		ComputeButton               = findViewById(R.id.ButtonComputePID);
		CheckBoxPI                  = findViewById(R.id.CheckBoxPI);
		CheckBoxPID                 = findViewById(R.id.CheckBoxPID);
		EditTextProcessGain         = findViewById(R.id.EditTextGain);
		EditTextProcessTimeConstant = findViewById(R.id.EditTextTimeConstant);
		ButtonMethodInfo            = findViewById(R.id.ButtonMethodInfo);
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

		ButtonMethodInfo.setOnClickListener(v ->
		{
			String title = getResources().getString(R.string.tl_about_title);
			String description = getResources().getString(R.string.tl_about_description);
			String link = getResources().getString(R.string.tl_about_link);

			BottomSheetDialog bottomSheet = new BottomSheetDialog(title, description, link);
			bottomSheet.show(getSupportFragmentManager(),
					"ModalBottomSheet");
		});
	}

	private boolean ValidateProcessParameters()
	{
		// Validates if at least one controller type is checked.
		if (!CheckBoxPI.isChecked() && !CheckBoxPID.isChecked())
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
		return true;
	}

	private void ComputeController()
	{
		// Get the control types.
		ArrayList<ControlType> controlTypes = new ArrayList<>();
		if (CheckBoxPI.isChecked()) controlTypes.add(ControlType.PI);
		if (CheckBoxPID.isChecked()) controlTypes.add(ControlType.PID);

		// Get the transfer function parameters.
		double pTime = Parser.GetDouble(EditTextProcessGain.getText().toString());
		double pDead = Parser.GetDouble(EditTextProcessTimeConstant.getText().toString());

		// Compute the TL Controller.
		ArrayList<ControllerParameter> controllerParameters = new ArrayList<>();
		for (ControlType controlType : controlTypes)
			controllerParameters.add(TL.Compute(ProcessType.Closed, controlType, pTime, pDead));

		// Set the tuning configuration.
		TuningConfiguration config = new TuningConfiguration(ProcessType.None,
				controllerParameters, pTime, pDead);
		ArrayList<TuningConfiguration> configuration = new ArrayList<>();
		configuration.add(config);

		// Get the tuning information.
		Tuning tlTuning = new Tuning("Tyreus and Luyben", "", TuningType.TL, configuration);

		// Pass through intent to the next activity the results information.
		Intent resultActivity = new Intent(TLActivity.this, ResultActivity.class);
		resultActivity.putExtra("RESULT", tlTuning);
		startActivity(resultActivity);
	}
}