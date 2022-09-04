package com.rad.pidtuner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.rad.pidtuner.database.DataAccess;
import com.rad.pidtuner.database.SettingModel;
import com.rad.pidtuner.utils.Parser;
import com.rad.pidtuner.utils.ViewUtils;

public class PreferenceActivity extends AppCompatActivity
{
	//region Attributes

	/**
	 * Database access to get settings.
	 */
	private DataAccess UserSettings;

	/**
	 * Switch reference to enable/disable set all parameters.
	 */
	private SwitchMaterial EnableAllParameters;

	/**
	 * EditText reference to get decimal places.
	 */
	private EditText DecimalPlaces;

	/**
	 * EditText reference to get decimal places.
	 */
	private EditText Gain;

	/**
	 * EditText reference to get decimal places.
	 */
	private EditText Time;

	/**
	 * EditText reference to get decimal places.
	 */
	private EditText Transport;

	/**
	 * EditText reference to get decimal places.
	 */
	private EditText Ku;

	/**
	 * EditText reference to get decimal places.
	 */
	private EditText Pu;

	/**
	 * EditText reference to get decimal places.
	 */
	private EditText Lambda;

	/**
	 * Button reference to finish activity.
	 */
	private Button ButtonOK;

	/**
	 * Linear layout reference to parameters.
	 */
	private LinearLayout LinearDefaultParameters;

	//endregion

	//region Constructor

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_preferences);

		FindViews();
		InitializeViews();
		InitializeListener();
	}

	//endregion

	//region Methods

	/**
	 * Gets the views references.
	 */
	private void FindViews()
	{
		EnableAllParameters     = findViewById(R.id.SwitchEnableAll);
		DecimalPlaces           = findViewById(R.id.EditTextDecimalPlaces);
		ButtonOK                = findViewById(R.id.ButtonOK);
		Gain                    = findViewById(R.id.TextInputDefaultGain);
		Time                    = findViewById(R.id.TextInputDefaultTimeConstant);
		Transport               = findViewById(R.id.TextInputDefaultTransportDelay);
		Ku                      = findViewById(R.id.TextInputDefaultUltimateGain);
		Pu                      = findViewById(R.id.TextInputDefaultUltimatePeriod);
		Lambda                  = findViewById(R.id.TextInputDefaultLambda);
		LinearDefaultParameters = findViewById(R.id.LinearDefaultParameters);
	}

	/**
	 * Initialize the views according to the saved configuration.
	 */
	private void InitializeViews()
	{
		// Read the database.
		UserSettings = new DataAccess(this, "Tuner");
		SettingModel settingModel = UserSettings.ReadConfiguration();
		if (settingModel != null)
		{
			// Initialize the views.
			if (settingModel.getSameParameters() == 1)
			{
				EnableAllParameters.setChecked(true);
				ViewUtils.FadeIn(getApplicationContext(), LinearDefaultParameters);
				Gain.setText(String.valueOf(settingModel.getGain()));
				Time.setText(String.valueOf(settingModel.getTime()));
				Transport.setText(String.valueOf(settingModel.getTransport()));
				Ku.setText(String.valueOf(settingModel.getKu()));
				Pu.setText(String.valueOf(settingModel.getPu()));
				Lambda.setText(String.valueOf(settingModel.getLambda()));
			}
			else
				EnableAllParameters.setChecked(false);

			DecimalPlaces.setText(String.valueOf(settingModel.getDecimalPlaces()));
		}
	}

	/**
	 * Initialize the events.
	 */
	private void InitializeListener()
	{
		EnableAllParameters.setOnCheckedChangeListener((buttonView, isChecked) ->
		{
			if (isChecked)
				ViewUtils.FadeIn(getApplicationContext(), LinearDefaultParameters);
			else
				ViewUtils.FadeOut(getApplicationContext(), LinearDefaultParameters);
		});

		ButtonOK.setOnClickListener(v ->
		{
			boolean hasError = false;
			if (EnableAllParameters.isChecked())
			{
				// Validates the input.
				if (DecimalPlaces.getText() == null || Parser.GetInt(DecimalPlaces.getText().toString()) == null ||
						Gain.getText()      == null || Parser.GetDouble(Gain.getText().toString())       == null ||
						Time.getText()      == null || Parser.GetDouble(Time.getText().toString())       == null ||
						Transport.getText() == null || Parser.GetDouble(Transport.getText().toString())  == null ||
						Ku.getText()        == null || Parser.GetDouble(Ku.getText().toString())         == null ||
						Pu.getText()        == null || Parser.GetDouble(Pu.getText().toString())         == null ||
						Lambda.getText()    == null || Parser.GetDouble(Lambda.getText().toString())     == null)
					hasError = true;

				if (Gain.getText() == null || Parser.GetDouble(Gain.getText().toString()) == null)
					Gain.setError(getResources().getString(R.string.GainError));

				if (Time.getText() == null || Parser.GetDouble(Time.getText().toString()) == null)
					Time.setError(getResources().getString(R.string.TimeConstantError));

				if (Transport.getText() == null || Parser.GetDouble(Transport.getText().toString()) == null)
					Transport.setError(getResources().getString(R.string.TransportDelayError));

				if (Ku.getText() == null || Parser.GetDouble(Ku.getText().toString()) == null)
					Ku.setError(getResources().getString(R.string.KuError));

				if (Pu.getText() == null || Parser.GetDouble(Pu.getText().toString()) == null)
					Pu.setError(getResources().getString(R.string.PuError));

				if (Lambda.getText() == null || Parser.GetDouble(Lambda.getText().toString()) == null)
					Lambda.setError(getResources().getString(R.string.LambdaTuningValueRequired));
			}

			if (DecimalPlaces.getText() == null || Parser.GetInt(DecimalPlaces.getText().toString()) == null)
			{
				DecimalPlaces.setError(getResources().getString(R.string.InvalidDecimalPlacesValue));
				hasError = true;
			}

			if (hasError)
				return;

			// Updates the new configuration.
			SettingModel newSettings = new SettingModel();
			if (EnableAllParameters.isChecked())
			{
				newSettings.setSameParameters(1);
				newSettings.setGain(Parser.GetDouble(Gain.getText().toString()));
				newSettings.setTime(Parser.GetDouble(Time.getText().toString()));
				newSettings.setTransport(Parser.GetDouble(Transport.getText().toString()));
				newSettings.setKu(Parser.GetDouble(Ku.getText().toString()));
				newSettings.setPu(Parser.GetDouble(Pu.getText().toString()));
				newSettings.setLambda(Parser.GetDouble(Lambda.getText().toString()));

			}
			else
				newSettings.setSameParameters(0);

			if (DecimalPlaces.getText() == null)
				newSettings.setDecimalPlaces(2);

			//noinspection ConstantConditions
			newSettings.setDecimalPlaces(Parser.GetInt(DecimalPlaces.getText().toString()));
			UserSettings.Update(newSettings);
			finish();
		});
	}
	//endregion
}
