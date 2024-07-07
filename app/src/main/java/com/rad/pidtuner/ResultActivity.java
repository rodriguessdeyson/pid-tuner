package com.rad.pidtuner;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tunings.models.TuningType;
import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;
import com.tunings.models.TuningMethod;
import com.rad.pidtuner.database.DataAccess;

import java.util.ArrayList;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity
{
	//region Attributes

	/**
	 * Database reference.
	 */
	private DataAccess TuningDatabase;

	/**
	 * TextView reference to Gain.
	 */
	private TextView Gain;

	/**
	 * TextView reference to TimeConstant.
	 */
	private TextView TimeConstant;

	/**
	 * TextView reference to TransportDelay.
	 */
	private TextView TransportDelay;

	/**
	 * List with the processed tuning.
	 */
	private ArrayList<TuningMethod> TuningMethods;

	/**
	 * LinearLayout where the result will take place.
	 */
	private LinearLayout LinearResultContainer;

	//endregion

	//region Constructors

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_result);

		// Find the Views references.
		StartViewContents();

		// Initialize the database settings.
		ConfigureDatabase();

		// Retrieve the values passed.
		RetrieveResults();

		// Builds the result layout.
		BuildResult();

		getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
			@Override
			public void handleOnBackPressed() {
				TuningDatabase.Update();
				finish();
			}
		});
	}

	//endregion

	//region Methods

	/**
	 * Configures the database server
	 */
	private void ConfigureDatabase()
	{
		// Creates the database.
		TuningDatabase = new DataAccess(this, "Tuner");
	}

	/**
	 * Starts the control views.
	 */
	private void StartViewContents()
	{
		Gain                  = findViewById(R.id.TextViewGain);
		TimeConstant          = findViewById(R.id.TextViewTimeConstant);
		TransportDelay        = findViewById(R.id.TextViewTransportDelay);
		LinearResultContainer = findViewById(R.id.LinearLayoutResults);
	}

	/**
	 * Retrieve the results information.
	 */
	private void RetrieveResults()
	{
		// Deserialize the result.
		TuningMethods = getIntent().getParcelableArrayListExtra("RESULT");

		// Maps the parameters.
		String gain  = String.valueOf(getIntent().getDoubleExtra("Gain", 0));
		String time  = String.valueOf(getIntent().getDoubleExtra("Time", 0));
		String dead  = String.valueOf(getIntent().getDoubleExtra("Dead", 0));

		// Gets the IMC only properties.
		TuningType tuningType = TuningMethods.get(0).getTuningType();
		if (tuningType == TuningType.IMC)
		{
			String title = String.valueOf(getIntent().getStringExtra("TitleName"));
			TransportDelay.setText(String.format("%s: %s", title, dead));
		}
		else if ((tuningType == TuningType.ZN || tuningType == TuningType.TL) &&
			TuningMethods.get(0).getControlProcessTypes().get(0) == ControlProcessType.Closed)
		{
			Gain.setText(getString(R.string.rbClosed));
			TimeConstant.setText(String.format("%s: %s", getString(R.string.hintKu), time));
			TransportDelay.setText(String.format("%s: %s", getString(R.string.hintPu), dead));
			return;
		}
		else
			TransportDelay.setText(String.format("%s: %s", getResources().getString(R.string.TransportDelay), dead));

		Gain.setText(String.format("%s: %s", getResources().getString(R.string.Gain), gain));
		TimeConstant.setText(String.format("%s: %s", getResources().getString(R.string.TimeConstant), time));
	}

	/**
	 * Builds the layout for the result.
	 */
	private void BuildResult()
	{
		Locale currentLocale = getResources().getConfiguration().locale;

		// Initialize new inflater.
		LayoutInflater inflater = getLayoutInflater();
		for (TuningMethod method : TuningMethods)
		{
			// Checks if the process type is null.
			if (method.getControlProcessTypes() == null)
			{
				TextView mainHeader = new TextView(getApplicationContext());
				mainHeader.setText(method.getTuningName());
				mainHeader.setTextSize(18);
				mainHeader.setGravity(Gravity.CENTER);
				mainHeader.setTextColor(ContextCompat.getColor(this, R.color.colorSecondaryText));
				LinearResultContainer.addView(mainHeader);
				for (ControlType cType : method.getControlTypes())
				{
					@SuppressLint("InflateParams")
					View resultView = inflater.inflate(R.layout.layout_pid_parameters, null);
					TextView tvResultFor = resultView.findViewById(R.id.TextViewControllerParameters);
					TextView tvKP = resultView.findViewById(R.id.TextViewKPR);
					TextView tvKI = resultView.findViewById(R.id.TextViewKIR);
					TextView tvKD = resultView.findViewById(R.id.TextViewKDR);

					tvResultFor.setText(cType.toString());
					for (ControllerParameters parameter : method.getParameters())
					{
						if (parameter.getType() == cType)
						{
							tvKP.setText(String.format(currentLocale, "%.3f", parameter.getKP()));
							tvKI.setText(String.format(currentLocale, "%.3f", parameter.getKI()));
							tvKD.setText(String.format(currentLocale, "%.3f", parameter.getKD()));
						}
					}
					LinearResultContainer.addView(resultView);
				}
			}
			else
			{
				for (ControlProcessType pType : method.getControlProcessTypes())
				{
					TextView mainHeader = new TextView(getApplicationContext());
					mainHeader.setText(String.format("%s - %s", method.getTuningName(), pType));
					mainHeader.setTextSize(18);
					mainHeader.setGravity(Gravity.CENTER);
					mainHeader.setTextColor(ContextCompat.getColor(this, R.color.colorSecondaryText));
					LinearResultContainer.addView(mainHeader);
					for (ControlType cType : method.getControlTypes())
					{
						@SuppressLint("InflateParams")
						View resultView = inflater.inflate(R.layout.layout_pid_parameters, null);
						TextView tvResultFor = resultView.findViewById(R.id.TextViewControllerParameters);
						TextView tvKP = resultView.findViewById(R.id.TextViewKPR);
						TextView tvKI = resultView.findViewById(R.id.TextViewKIR);
						TextView tvKD = resultView.findViewById(R.id.TextViewKDR);

						tvResultFor.setText(cType.toString());
						for (ControllerParameters parameter : method.getParameters())
						{
							if (parameter.getType() == cType)
							{
								tvKP.setText(String.format(currentLocale, "%.3f", parameter.getKP()));
								tvKI.setText(String.format(currentLocale, "%.3f", parameter.getKI()));
								tvKD.setText(String.format(currentLocale, "%.3f", parameter.getKD()));
							}
						}
						LinearResultContainer.addView(resultView);
					}
				}
			}
		}
	}

	//endregion

}