package com.rad.pidtuner.methods;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.rad.pidtuner.R;
import com.rad.pidtuner.ResultActivity;
import com.rad.pidtuner.database.DataAccess;
import com.rad.pidtuner.database.SettingModel;
import com.rad.pidtuner.utils.Logger;
import com.rad.pidtuner.utils.Parser;
import com.tunings.methods.IAE;
import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;
import com.tunings.models.TuningMethod;
import com.tunings.models.TuningType;

import java.util.ArrayList;

public class IAEActivity extends AppCompatActivity
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
		setContentView(R.layout.layout_iae);

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
		CheckBoxServo                 = findViewById(R.id.CheckBoxServo);
		CheckBoxRegulator             = findViewById(R.id.CheckBoxRegulator);
		EditTextProcessGain           = findViewById(R.id.EditTextGain);
		EditTextProcessTimeConstant   = findViewById(R.id.EditTextTimeConstant);
		EditTextProcessTransportDelay = findViewById(R.id.EditTextTransportDelay);

		DataAccess dbAccess = new DataAccess(this, "Tuner");
		SettingModel userSettings = dbAccess.ReadConfiguration();
		if (userSettings.isSameParameters())
		{
			EditTextProcessGain.setText(userSettings.getGain().toString());
			EditTextProcessTimeConstant.setText(userSettings.getTime().toString());
			EditTextProcessTransportDelay.setText(userSettings.getTransport().toString());
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
			EditTextProcessTransportDelay.setText("");
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
		ArrayList<TuningMethod> tuningMethods = new ArrayList<>();

		// Gets the tuning basics information.
		TuningMethod chrMethod = new TuningMethod();
		chrMethod.setTuningName("Integral of Absolute Error ");
		chrMethod.setTuningType(TuningType.IAE);

		// Gets the selected process.
		ArrayList<ControlProcessType> processTypes = new ArrayList<>();
		if (CheckBoxServo.isChecked())
			processTypes.add(ControlProcessType.Servo);
		if (CheckBoxRegulator.isChecked())
			processTypes.add(ControlProcessType.Regulator);
		chrMethod.setControlProcessTypes(processTypes);

		// Gets the control to compute.
		ArrayList<ControlType> controlTypes = new ArrayList<>();
		if (CheckBoxPI.isChecked())
			controlTypes.add(ControlType.PI);
		if (CheckBoxPID.isChecked())
			controlTypes.add(ControlType.PID);
		chrMethod.setControlTypes(controlTypes);
		tuningMethods.add(chrMethod);

		// Process the tuning.
		Double pGain = Parser.GetDouble(EditTextProcessGain.getText().toString());
		Double pTime = Parser.GetDouble(EditTextProcessTimeConstant.getText().toString());
		Double pDead = Parser.GetDouble(EditTextProcessTransportDelay.getText().toString());
		ArrayList<ControllerParameters> parameters = new ArrayList<>();
		for (TuningMethod tuning : tuningMethods)
		{
			for (ControlType controller: tuning.getControlTypes())
			{
				for (ControlProcessType pType : tuning.getControlProcessTypes())
				{
					ControllerParameters cp = IAE.Compute(
							controller,
							pType,
							pGain,
							pTime,
							pDead);
					parameters.add(cp);
				}
			}
			tuning.setParameters(parameters);
		}

		// Pass through intent to the next activity the results information.
		Intent resultActivity = new Intent(IAEActivity.this, ResultActivity.class);
		resultActivity.putParcelableArrayListExtra("RESULT", tuningMethods);
		resultActivity.putExtra("Gain", pGain);
		resultActivity.putExtra("Time", pTime);
		resultActivity.putExtra("Dead", pDead);
		startActivity(resultActivity);
	}
}