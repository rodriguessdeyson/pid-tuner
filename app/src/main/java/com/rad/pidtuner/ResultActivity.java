package com.rad.pidtuner;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charts.services.ChartServices;
import com.tunings.models.ControllerParameter;
import com.tunings.models.Tuning;
import com.rad.pidtuner.database.DataAccess;
import com.tunings.models.TuningConfiguration;

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
	 * Tuning method.
	 */
	private Tuning Tuning;

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
		LinearResultContainer = findViewById(R.id.LinearLayoutResults);
	}

	/**
	 * Retrieve the results information.
	 */
	private void RetrieveResults()
	{
		// Deserialize the result.
		Tuning = getIntent().getParcelableExtra("RESULT");
	}

	/**
	 * Builds the layout for the result.
	 */
	private void BuildResult()
	{
		Locale currentLocale = getResources().getConfiguration().locale;

		TextView header = findViewById(R.id.TextViewTuningName);
		header.setText(Tuning.getTuningName());

		for (TuningConfiguration configuration : Tuning.getConfiguration())
		{
			TextView tuningConfiguration = new TextView(getApplicationContext());
			tuningConfiguration.setId(configuration.getProcessType().ordinal());
			tuningConfiguration.setText(String.format("%s", configuration.getProcessType()));
			tuningConfiguration.setTextSize(18);
			tuningConfiguration.setGravity(Gravity.CENTER);
			tuningConfiguration.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
			LinearResultContainer.addView(tuningConfiguration);

			LayoutInflater inflater = getLayoutInflater();
			for (ControllerParameter controllerParameter : configuration.getControllerParameters())
			{
				@SuppressLint("InflateParams")
				View resultView = inflater.inflate(R.layout.layout_result_pid_parameters, null);
				resultView.setId(controllerParameter.getControlType().ordinal());
				resultView.setPadding(0, 10, 0, 10);

				TextView tvResultFor = resultView.findViewById(R.id.TextViewControllerParameters);
				TextView tvKP = resultView.findViewById(R.id.TextViewKPR);
				TextView tvKI = resultView.findViewById(R.id.TextViewKIR);
				TextView tvKD = resultView.findViewById(R.id.TextViewKDR);
				WebView chartWebView = resultView.findViewById(R.id.WebViewPlotly);
				chartWebView.getSettings().setJavaScriptEnabled(true);
				chartWebView.setWebViewClient(new WebViewClient());
				chartWebView.loadUrl("https://assistencia-hml.abraseuatendimento.com.br/carsystem/2/3fd86cbc5f984593a950ffe0853867bea953d7681a1c4e8b8284f65c4cf80ba0/servicos");
				// String pidPlot = ChartServices.plot(configuration.getGain(),
						// configuration.getTimeConstant(), configuration.getTransportDelay(),
						// controllerParameter.getKP(), controllerParameter.getKI(), controllerParameter.getKD());
				// chartWebView.loadDataWithBaseURL(null, pidPlot, "text/html", null, null);

				tvResultFor.setText(String.format("%s", controllerParameter.getControlType().toString()));
				tvKP.setText(String.format(currentLocale, "%.3f", controllerParameter.getKP()));
				tvKI.setText(String.format(currentLocale, "%.3f", controllerParameter.getKI()));
				tvKD.setText(String.format(currentLocale, "%.3f", controllerParameter.getKD()));

				LinearResultContainer.addView(resultView);
			}
		}
	}

	private String buildPlot(TuningConfiguration configuration, ControllerParameter parameter)
	{
		return "<div id=\"plot\"></div>";
	}

	//endregion

}