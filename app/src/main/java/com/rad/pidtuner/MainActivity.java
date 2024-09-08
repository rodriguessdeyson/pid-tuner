package com.rad.pidtuner;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.rad.pidtuner.database.DataAccess;

/**
 * Allows to manipulate the MainActivity views.
 */
public class MainActivity extends AppCompatActivity
{
	//region Attributes

	/**
	 * Button reference to start tuning activity.
	 */
	private Button StartTuningButton;

	//endregion

	//region Constructor

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);

		// Find the Views references.
		StartViewContents();

		// Initialize the database settings.
		ConfigureDatabase();

		// Method to treat all events.
		ControlsEvent();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
	}

	//endregion

	//region Methods

	/**
	 * Initialize all the views controls references.
	 */
	private void StartViewContents()
	{
		StartTuningButton = findViewById(R.id.ButtonStart);
	}

	/**
	 * Configures the database server
	 */
	private void ConfigureDatabase()
	{
		// Creates the database.
		DataAccess tuningDatabase = new DataAccess(this, "Tuner");
		tuningDatabase.CreateDatabase();
	}

	/**
	 * Handle the views control events.
	 */
	private void ControlsEvent()
	{
		StartTuningButton.setOnClickListener(v -> OpenTunings());
	}

	/**
	 * Method to show an ad when tuning button is clicked.
	 */
	private void OpenTunings()
	{
		Intent goTuning = new Intent(MainActivity.this, TuningActivity.class);
		ActivityOptions options = ActivityOptions.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out);
		startActivity(goTuning);
	}

	//endregion
}
