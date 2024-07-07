package com.rad.pidtuner.methods;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.rad.pidtuner.R;
import com.rad.pidtuner.ResultActivity;
import com.rad.pidtuner.utils.Logger;
import com.rad.pidtuner.utils.Parser;
import com.tunings.methods.CC;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;
import com.tunings.models.TuningMethod;
import com.tunings.models.TuningType;

import java.util.ArrayList;

public class CCActivity extends AppCompatActivity
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
	 CheckBox reference to Proportional-Derivative parameter.
	 */
	private CheckBox CheckBoxPD;

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
	 EditText reference to transport delay parameter.
	 */
	private EditText EditTextProcessTransportDelay;


	//endregion

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_cc);

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
		CheckBoxPD                    = findViewById(R.id.CheckBoxPD);
		CheckBoxPID                   = findViewById(R.id.CheckBoxPID);
		EditTextProcessGain           = findViewById(R.id.EditTextGain);
		EditTextProcessTimeConstant   = findViewById(R.id.EditTextTimeConstant);
		EditTextProcessTransportDelay = findViewById(R.id.EditTextTransportDelay);
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
	}

	/**
	 * Validates the parameters.
	 * @return True if the parameters are ok.
	 */
	private boolean ValidateProcessParameters()
	{
		// Validates if at least one controller type is checked.
		if (!CheckBoxP.isChecked()  &&
			!CheckBoxPI.isChecked() &&
			!CheckBoxPD.isChecked() &&
			!CheckBoxPID.isChecked())
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

	/**
	 * Computes the controller.
	 */
	private void ComputeController()
	{
		ArrayList<TuningMethod> tuningMethods = new ArrayList<>();

		// Gets the tuning basics information.
		TuningMethod openTuning = new TuningMethod();
		openTuning.setTuningName("Cohen-Coon");
		openTuning.setTuningType(TuningType.CC);

		// Gets the process types.
		ArrayList<ControlType> controlTypes = new ArrayList<>();
		if (CheckBoxP.isChecked())
			controlTypes.add(ControlType.P);
		if (CheckBoxPD.isChecked())
			controlTypes.add(ControlType.PD);
		if (CheckBoxPI.isChecked())
			controlTypes.add(ControlType.PI);
		if (CheckBoxPID.isChecked())
			controlTypes.add(ControlType.PID);
		openTuning.setControlTypes(controlTypes);
		tuningMethods.add(openTuning);

		// Process the tuning.
		Double pGain = Parser.GetDouble(EditTextProcessGain.getText().toString());
		Double pTime = Parser.GetDouble(EditTextProcessTimeConstant.getText().toString());
		Double pDead = Parser.GetDouble(EditTextProcessTransportDelay.getText().toString());
		ArrayList<ControllerParameters> parameters = new ArrayList<>();
		for (TuningMethod tuning : tuningMethods)
		{
			for (ControlType controller: tuning.getControlTypes())
			{
				ControllerParameters cp = CC.Compute(controller, pGain, pTime, pDead);

				// Updates the result.
				parameters.add(cp);
			}
			tuning.setParameters(parameters);
		}

		// Pass through intent to the next activity the results information.
		Intent resultActivity = new Intent(CCActivity.this, ResultActivity.class);
		resultActivity.putParcelableArrayListExtra("RESULT", tuningMethods);
		resultActivity.putExtra("Gain", pGain);
		resultActivity.putExtra("Time", pTime);
		resultActivity.putExtra("Dead", pDead);
		startActivity(resultActivity);
	}
}
