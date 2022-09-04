package com.rad.pidtuner.methods;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.rad.pidtuner.R;
import com.rad.pidtuner.ResultActivity;
import com.rad.pidtuner.database.DataAccess;
import com.rad.pidtuner.database.SettingModel;
import com.rad.pidtuner.utils.Logger;
import com.rad.pidtuner.utils.Parser;
import com.tunings.methods.TL;
import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;
import com.tunings.models.TuningMethod;
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
	 Button reference to clean event.
	 */
	private Button CleanButton;

	/**
	 CheckBox reference to Proportional-integral parameter.
	 */
	private CheckBox CheckBoxPI;

	/**
	 CheckBox reference to Proportional-Derivative-integral parameter.
	 */
	private CheckBox CheckBoxPID;

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
		ComputeButton                 = findViewById(R.id.ButtonComputePID);
		CleanButton                   = findViewById(R.id.ButtonCleanParameters);
		CheckBoxPI                    = findViewById(R.id.CheckBoxPI);
		CheckBoxPID                   = findViewById(R.id.CheckBoxPID);
		RadioButtonClosed             = findViewById(R.id.RadioButtonClosedLoop);
		EditTextProcessGain           = findViewById(R.id.EditTextGain);
		EditTextProcessTimeConstant   = findViewById(R.id.EditTextTimeConstant);

		DataAccess dbAccess = new DataAccess(this, "Tuner");
		SettingModel userSettings = dbAccess.ReadConfiguration();
		if (userSettings.isSameParameters())
		{
			EditTextProcessGain.setText(userSettings.getKu().toString());
			EditTextProcessTimeConstant.setText(userSettings.getPu().toString());
		}
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

		// Handle the button click.
		CleanButton.setOnClickListener(v ->
		{
			EditTextProcessGain.setText("");
			EditTextProcessTimeConstant.setText("");
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
		ArrayList<TuningMethod> tuningMethods = new ArrayList<>();

		// Check which is enabled.
		if (RadioButtonClosed.isChecked())
		{
			// Gets the tuning basics information.
			TuningMethod closedTuning = new TuningMethod();
			closedTuning.setTuningName("Tyreus and Luyben");
			closedTuning.setTuningType(TuningType.TL);

			// Gets the all control process type.
			ArrayList<ControlProcessType> processTypes = new ArrayList<>();
			processTypes.add(ControlProcessType.Closed);
			closedTuning.setControlProcessTypes(processTypes);

			ArrayList<ControlType> controlTypes = new ArrayList<>();
			if (CheckBoxPI.isChecked())
				controlTypes.add(ControlType.PI);
			if (CheckBoxPID.isChecked())
				controlTypes.add(ControlType.PID);
			closedTuning.setControlTypes(controlTypes);
			tuningMethods.add(closedTuning);
		}

		// Process the tuning.
		Double pTime = Parser.GetDouble(EditTextProcessGain.getText().toString());
		Double pDead = Parser.GetDouble(EditTextProcessTimeConstant.getText().toString());
		ArrayList<ControllerParameters> parameters = new ArrayList<>();
		for (TuningMethod tuning : tuningMethods)
		{
			for (ControlType controller: tuning.getControlTypes())
			{
				for (ControlProcessType pType : tuning.getControlProcessTypes())
				{
					ControllerParameters cp = TL.Compute(
						pType,
						controller,
							pTime,
							pDead
					);
					// Updates the result.
					parameters.add(cp);
				}
			}
			tuning.setParameters(parameters);
		}

		// Pass through intent to the next activity the results information.
		Intent resultActivity = new Intent(TLActivity.this, ResultActivity.class);
		resultActivity.putParcelableArrayListExtra("RESULT", tuningMethods);
		resultActivity.putExtra("Gain", 0);
		resultActivity.putExtra("Time", pTime);
		resultActivity.putExtra("Dead", pDead);
		startActivity(resultActivity);
	}
}