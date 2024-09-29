package com.rad.pidtuner;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.domain.models.tuning.TransferFunction;
import com.domain.services.chart.ChartService;
import com.domain.services.katex.Katex;
import com.domain.services.utils.Parser;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.TuningModel;
import com.domain.services.utils.ViewUtils;
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
	 * Tuning method.
	 */
	private TuningModel TuningModel;

	private ArrayList<ControllerParameter> ControllerParameters;

	/**
	 * LinearLayout where the result will take place.
	 */
	private LinearLayout LinearResultContainer;

	//endregion

	//region Constants

	private static final String KATEX_URL = "file:///android_asset/katex_function_layout.html";
	private static final String CHART_URL = "file:///android_asset/chart_layout.html";

	//endregion

	//region Constructors

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_result);

		// Find the Views references.
		startViewContents();

		// Initialize the database settings.
		configureDatabase();

		// Retrieve the values passed.
		retrieveResults();

		// Builds the result layout.
		buildResult();

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
	private void configureDatabase()
	{
		// Creates the database.
		TuningDatabase = new DataAccess(this, "Tuner");
	}

	/**
	 * Starts the control views.
	 */
	private void startViewContents()
	{
		LinearResultContainer = findViewById(R.id.LinearLayoutResults);
	}

	/**
	 * Retrieve the results information.
	 */
	private void retrieveResults()
	{
		// Deserialize the result.
		TuningModel = getIntent().getParcelableExtra("CONFIGURATION");
		ControllerParameters = getIntent().getParcelableArrayListExtra("RESULT");
	}


	@SuppressLint("SetJavaScriptEnabled")
	private void buildResult()
	{
		Locale currentLocale = getResources().getConfiguration().locale;

		// Set the tuning name.
		TextView textViewHeader = findViewById(R.id.TextViewTuningName);
		setTextView(textViewHeader, TuningModel.getName());

		// Set the transfer function model.
		setUpTransferFunction(currentLocale, TuningModel.getTransferFunction());

		// Set the results
		for (ControllerParameter controllerParameter : ControllerParameters)
		{
			LayoutInflater inflater = getLayoutInflater();
			View resultView = inflater.inflate(R.layout.layout_result_pid_parameters, null);

			// Set the control title.
			TextView textViewControlTitle = resultView.findViewById(R.id.TextViewControllerResultType);
			setTextView(textViewControlTitle, controllerParameter.getTuningAndProcess());

			// Set the controller transfer function.
			setUpControllerTransferFunction(currentLocale, resultView, controllerParameter);

			// Set the controller transfer function parameters value.
			TextView textViewPidParameters = resultView.findViewById(R.id.TextViewPIDControllerResultEquationParameters);
			String formatedString = getString(R.string.tvPIDResultParameters,
					controllerParameter.getKP(),
					controllerParameter.getKI(),
					controllerParameter.getKD());
			setTextView(textViewPidParameters, formatedString);

			// Set up the step response.
			switch (controllerParameter.getProcessType())
			{
				case None:
				case Servo:
				case Servo20:
				case Regulator:
				case Regulator20:
				case LambdaTuning:
				case Open:
					setUpStepResponse(currentLocale, resultView, controllerParameter);
					break;
				case Closed:
				case LambdaModelBasedTuning:
					LinearLayout linearLayoutPidStepResponse = resultView.findViewById(R.id.LinearLayoutPIDStepResponse);
					// linearLayoutPidStepResponse.setVisibility(View.GONE);
					ViewUtils.FadeOut(this, linearLayoutPidStepResponse);
					break;
			}
			LinearResultContainer.addView(resultView);
		}

	}

	private void setTextView(TextView textView, String textToShow)
	{
		textView.setText(textToShow);
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void setUpTransferFunction(Locale locale, TransferFunction transferFunction)
	{
		WebView firstOrderFuncWebView = findViewById(R.id.WebViewFirstOrderEquation);
		firstOrderFuncWebView.getSettings().setJavaScriptEnabled(true);
		firstOrderFuncWebView.addJavascriptInterface(new Katex(locale), "Android");
		firstOrderFuncWebView.loadUrl(KATEX_URL);
		firstOrderFuncWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url)
			{
				String gain = Parser.sanitizeDouble(transferFunction.getGain());
				String timeConstant = Parser.sanitizeDouble(transferFunction.getTimeConstant());
				if (transferFunction.getIMCModelType() != null)
				{
					String secondTimeConstant = Parser.sanitizeDouble(transferFunction.getSecondTimeConstant());
					String dampingRatio = Parser.sanitizeDouble(transferFunction.getDampingRatio());

					String params = String.format(locale, "updateEquation(Android.getCustomDynamicEquation(" + "%s,%s,%s,%s,%s" + "))", transferFunction.getIMCModelType().ordinal(), gain, timeConstant, secondTimeConstant, dampingRatio);
					firstOrderFuncWebView.evaluateJavascript(params, null);
				}
				else
				{
					String transportDelay = Parser.sanitizeDouble(transferFunction.getTransportDelay());

					String params = String.format(locale, "updateEquation(Android.getDynamicEquation(" + "%s,%s,%s" + "))", gain, timeConstant, transportDelay);
					firstOrderFuncWebView.evaluateJavascript(params, null);
				}
			}
		});
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void setUpControllerTransferFunction(Locale locale, View resultView,
												 ControllerParameter controllerParameter)
	{
		WebView pidControllerFuncWebView = resultView.findViewById(R.id.WebViewPIDParallelEquation);
		pidControllerFuncWebView.getSettings().setJavaScriptEnabled(true);
		pidControllerFuncWebView.addJavascriptInterface(new Katex(locale), "Android");
		pidControllerFuncWebView.loadUrl(KATEX_URL);
		pidControllerFuncWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url)
			{
				String params = String.format(locale, "updateEquation(Android.getPIDEquation(" + "%s" + "))", controllerParameter.getControlType().ordinal());
				pidControllerFuncWebView.evaluateJavascript(params, null);
			}
		});
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void setUpStepResponse(Locale locale, View resultView,
								   ControllerParameter controllerParameter)
	{
		WebView stepResponseWebView = resultView.findViewById(R.id.WebViewPlotly);
		stepResponseWebView.getSettings().setJavaScriptEnabled(true);
		stepResponseWebView.addJavascriptInterface(new ChartService(locale), "Android");
		stepResponseWebView.loadUrl(CHART_URL);
		stepResponseWebView.setWebViewClient(new WebViewClient()
		{
			@Override
			public void onPageFinished(WebView view, String url)
			{
				double gain = TuningModel.getTransferFunction().getGain();
				double timeConstant = TuningModel.getTransferFunction().getTimeConstant();
				double transportDelay = TuningModel.getTransferFunction().getTransportDelay();
				double kp = controllerParameter.getKP();
				double ki = controllerParameter.getKI();
				double kd = controllerParameter.getKD();
				new android.os.Handler(Looper.getMainLooper()).postDelayed(() ->
				{
					String jsCode = "updateStep(" + gain + ", " + timeConstant + ", " + transportDelay + ", " + kp + ", " + ki+ ", " + kd + ")";
					stepResponseWebView.evaluateJavascript(jsCode, null);
				}, 1000);
			}
		});
	}

	//endregion

}