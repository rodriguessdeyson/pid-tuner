package com.rad.pidtuner.methods;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.TuningModel;
import com.domain.services.katex.Katex;
import com.rad.pidtuner.R;
import com.rad.pidtuner.ResultActivity;
import com.domain.services.utils.BottomSheetDialog;
import com.domain.services.utils.Logger;
import com.domain.services.utils.Parser;
import com.domain.services.tuning.CC;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.TuningType;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Cohen-Coon Activity.
 */
public class CCActivity extends AppCompatActivity
{
	//region Constants

	private static final String KATEX_URL = "file:///android_asset/katex_function_layout.html";

	//endregion

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

	/**
	 * Button reference to show about dialog.
	 */
	private Button ButtonMethodInfo;

	//endregion

	//region Methods

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_cc);

		// Find Views Reference.
		InitializeViews();

		// Start the listener event handler.
		initializeEventListener();

		// Configure transfer function.
		setUpTransferFunction();
	}

	/**
	 * Initialize the control views.
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
		ButtonMethodInfo              = findViewById(R.id.ButtonMethodInfo);
	}

	/**
	 * Initialize the buttons events.
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
			String title = getResources().getString(R.string.cc_about_title);
			String description = getResources().getString(R.string.cc_about_description);
			String link = getResources().getString(R.string.cc_about_link);

			BottomSheetDialog bottomSheet = new BottomSheetDialog(title, description, link);
			bottomSheet.show(getSupportFragmentManager(),
					"ModalBottomSheet");
		});
	}

	/**
	 * Set up the transfer function.
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void setUpTransferFunction()
	{
		Locale locale = Locale.getDefault();
		WebView firstOrderFuncWebView = findViewById(R.id.WebViewFirstOrderEquation);
		firstOrderFuncWebView.getSettings().setJavaScriptEnabled(true);
		firstOrderFuncWebView.addJavascriptInterface(new Katex(locale), "Android");
		firstOrderFuncWebView.loadUrl(KATEX_URL);
		firstOrderFuncWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url)
			{
				// Resume transition after the web page is fully loaded
				startPostponedEnterTransition();

				// Set shared element transition (can add other types as needed)
				getWindow().setSharedElementEnterTransition(new ChangeBounds());
				getWindow().setSharedElementExitTransition(new ChangeBounds());
			}
		});
	}

	/**
	 * Validates the parameters.
	 * @return True if the parameters are ok.
	 */
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
		if (!CheckBoxP.isChecked()  &&
			!CheckBoxPI.isChecked() &&
			!CheckBoxPD.isChecked() &&
			!CheckBoxPID.isChecked())
		{
			Logger.show(this, R.string.ControllerTypeIsRequired);
			return false;
		}

		return true;
	}

	/**
	 * Computes the controller.
	 */
	private void computeController()
	{
		// Get the control types.
		ArrayList<ControlType> controlTypes = new ArrayList<>();
		if (CheckBoxP.isChecked()) controlTypes.add(ControlType.P);
		if (CheckBoxPD.isChecked()) controlTypes.add(ControlType.PD);
		if (CheckBoxPI.isChecked()) controlTypes.add(ControlType.PI);
		if (CheckBoxPID.isChecked()) controlTypes.add(ControlType.PID);

		// Get the transfer function parameters.
		double pGain = Parser.getDouble(EditTextProcessGain.getText().toString());
		double pTime = Parser.getDouble(EditTextProcessTimeConstant.getText().toString());
		double pDead = Parser.getDouble(EditTextProcessTransportDelay.getText().toString());

		// Set up the transfer function.
		TransferFunction tf = new TransferFunction(pGain, pTime, pDead);

		// Compute the CC Controller.
		ArrayList<ControllerParameter> controllerParameters = new ArrayList<>();
		for (ControlType controlType: controlTypes)
			controllerParameters.add(CC.compute(controlType, tf));

		// Set up the model.
		String name = getResources().getString(R.string.tvCohenCoon);
		String description = getResources().getString(R.string.cc_about_description);
		TuningModel ccMethod = new TuningModel(name, description, TuningType.CC, tf);

		// Pass through intent to the next activity the results information.
		Intent resultActivity = new Intent(CCActivity.this, ResultActivity.class);
		View view = findViewById(R.id.WebViewFirstOrderEquation);

		ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
				CCActivity.this,
				view,
				ViewCompat.getTransitionName(view)
		);

		resultActivity.putExtra("CONFIGURATION", ccMethod);
		resultActivity.putParcelableArrayListExtra("RESULT", controllerParameters);
		startActivity(resultActivity, options.toBundle());
	}

	//endregion
}