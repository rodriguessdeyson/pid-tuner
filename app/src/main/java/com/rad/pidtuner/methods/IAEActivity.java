package com.rad.pidtuner.methods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.TuningModel;
import com.rad.pidtuner.R;
import com.rad.pidtuner.ResultActivity;
import com.domain.services.utils.BottomSheetDialog;
import com.domain.services.utils.Logger;
import com.domain.services.utils.Parser;
import com.domain.services.tuning.IAE;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.TuningType;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Objects;

public class IAEActivity extends AppCompatActivity
{
	//region Attributes

	/**
	 * Button reference to compute event.
	 */
	private Button ComputeButton;

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

	/**
	 * Button reference to show about dialog.
	 */
	private Button ButtonMethodInfo;

	//endregion

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_iae);

		// Find Views Reference.
		initializeViews();

		// Start the listener event handler.
		initializeEventListener();
	}

	/**
	 Initialize the control views.
	 */
	@SuppressLint("SetTextI18n")
	private void initializeViews()
	{
		ComputeButton                 = findViewById(R.id.ButtonComputePID);
		CheckBoxPI                    = findViewById(R.id.CheckBoxPI);
		CheckBoxPID                   = findViewById(R.id.CheckBoxPID);
		CheckBoxServo                 = findViewById(R.id.CheckBoxServo);
		CheckBoxRegulator             = findViewById(R.id.CheckBoxRegulator);
		EditTextProcessGain           = findViewById(R.id.EditTextGain);
		EditTextProcessTimeConstant   = findViewById(R.id.EditTextTimeConstant);
		EditTextProcessTransportDelay = findViewById(R.id.EditTextTransportDelay);
		ButtonMethodInfo              = findViewById(R.id.ButtonMethodInfo);
	}

	/**
	 Initialize the buttons events.
	 */
	private void initializeEventListener()
	{
		// Handle the button click.
		ComputeButton.setOnClickListener(v ->
		{
			// Validates the input, from top-down approach.
			if (!validateProcessParameters())
				return;

			computeController();
		});

		ButtonMethodInfo.setOnClickListener(v ->
		{
			String title = getResources().getString(R.string.iae_about_title);
			String description = getResources().getString(R.string.iae_about_description);
			String link = getResources().getString(R.string.iae_about_link);

			BottomSheetDialog bottomSheet = new BottomSheetDialog(title, description, link);
			bottomSheet.show(getSupportFragmentManager(),
					"ModalBottomSheet");
		});
	}

	private boolean validateProcessParameters()
	{
		// Validates if the process data are filled.
		if (EditTextProcessGain.getText().toString().isEmpty())
		{
			EditTextProcessGain.setError(getResources().getString(R.string.GainError));
			Logger.show(this, R.string.GainError);
			return false;
		}

		// Validates if the time constant data are filled.
		if (EditTextProcessTimeConstant.getText().toString().isEmpty())
		{
			EditTextProcessTimeConstant.setError(getResources().getString(R.string.TimeConstantError));
			Logger.show(this, R.string.TimeConstantError);
			return false;
		}

		// Validates if the dead time data are filled.
		if (EditTextProcessTransportDelay.getText().toString().isEmpty())
		{
			EditTextProcessTransportDelay.setError(getResources().getString(R.string.TransportDelayError));
			Logger.show(this, R.string.TransportDelayError);
			return false;
		}

		// Validates if at least one controller type is checked.
		if (!CheckBoxServo.isChecked() && !CheckBoxRegulator.isChecked())
		{
			Logger.show(this, R.string.ProcessTypeIsRequired);
			return false;
		}

		// Validates if at least one controller type is checked.
		if (!CheckBoxPI.isChecked() && !CheckBoxPID.isChecked())
		{
			Logger.show(this, R.string.ControllerTypeIsRequired);
			return false;
		}

		return true;
	}

	private void computeController()
	{
		// Get the selected process.
		ArrayList<ProcessType> processTypes = new ArrayList<>();
		if (CheckBoxServo.isChecked()) processTypes.add(ProcessType.Servo);
		if (CheckBoxRegulator.isChecked())  processTypes.add(ProcessType.Regulator);

		// Get the control types.
		ArrayList<ControlType> controlTypes = new ArrayList<>();
		if (CheckBoxPI.isChecked()) controlTypes.add(ControlType.PI);
		if (CheckBoxPID.isChecked()) controlTypes.add(ControlType.PID);

		// Get the transfer function parameters.
		double pGain = Parser.getDouble(EditTextProcessGain.getText().toString());
		double pTime = Parser.getDouble(EditTextProcessTimeConstant.getText().toString());
		double pDead = Parser.getDouble(EditTextProcessTransportDelay.getText().toString());

		// Set up the transfer function.
		TransferFunction tf = new TransferFunction(pGain, pTime, pDead);

		// Compute the IAE Controller.
		ArrayList<ControllerParameter> controllerParameters = new ArrayList<>();
		for (ProcessType processType : processTypes)
		{
			for (ControlType controlType :  controlTypes)
			{
				ControllerParameter cp;
				switch (processType)
				{
					case Servo:
						cp = IAE.computeServo(controlType, tf);
						controllerParameters.add(cp);
						break;
					case Regulator:
						cp = IAE.computeRegulator(controlType, tf);
						controllerParameters.add(cp);
						break;
					default:
						throw new InvalidParameterException(processType.toString());
				}
			}
		}

		// Set up the model.
		String name = getResources().getString(R.string.tvIAE);
		String description = getResources().getString(R.string.iae_about_description);
		TuningModel iaeMethod = new TuningModel(name, description,
				TuningType.IAE, tf, processTypes);

		// Pass through intent to the next activity the results information.
		Intent resultActivity = new Intent(IAEActivity.this, ResultActivity.class);
		View view = findViewById(R.id.ImageViewFirstOrderProcess);

		ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
				IAEActivity.this,
				view,
				ViewCompat.getTransitionName(view)
		);

		resultActivity.putExtra("CONFIGURATION", iaeMethod);
		resultActivity.putParcelableArrayListExtra("RESULT", controllerParameters);
		startActivity(resultActivity, options.toBundle());
	}
}