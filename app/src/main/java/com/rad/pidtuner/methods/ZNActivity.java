package com.rad.pidtuner.methods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

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
import android.widget.RadioButton;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.TuningModel;
import com.domain.services.katex.Katex;
import com.google.android.material.textfield.TextInputLayout;

import com.domain.services.utils.BottomSheetDialog;
import com.domain.services.utils.Logger;
import com.rad.pidtuner.R;
import com.rad.pidtuner.ResultActivity;
import com.domain.services.utils.Parser;
import com.domain.services.tuning.ZN;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.TuningType;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Ziegler-Nichols Activity.
 */
public class ZNActivity extends AppCompatActivity
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
	 CheckBox reference to Proportional-Derivative-integral parameter.
	 */
	private CheckBox CheckBoxPID;

	/**
	 CheckBox reference to Proportional-Integral parameter.
	 */
	private RadioButton RadioButtonOpened;

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

	/**
	 EditText reference to transport delay parameter.
	 */
	private EditText EditTextProcessTransportDelay;

	/**
	 * Layout for hint gain.
	 */
	private TextInputLayout LayoutGain;

	/**
	 * Layout for hint time constant.
	 */
	private TextInputLayout LayoutTimeConstant;

	/**
	 * Layout for hint transport delay.
	 */
	private TextInputLayout LayoutTransportDelay;

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
		setContentView(R.layout.layout_zn);

		// Find Views Reference.
		initializeViews();

		// Start the listener event handler.
		initializeEventListener();

		// Configure transfer function.
		setUpTransferFunction();
	}

	/**
	 * Initialize the control views.
	 */
	private void initializeViews()
	{
		ComputeButton                 = findViewById(R.id.ButtonComputePID);
		CheckBoxP                     = findViewById(R.id.CheckBoxP);
		CheckBoxPI                    = findViewById(R.id.CheckBoxPI);
		CheckBoxPID                   = findViewById(R.id.CheckBoxPID);
		RadioButtonOpened             = findViewById(R.id.RadioButtonOpenedLoop);
		RadioButtonClosed             = findViewById(R.id.RadioButtonClosedLoop);
		EditTextProcessGain           = findViewById(R.id.EditTextGain);
		EditTextProcessTimeConstant   = findViewById(R.id.EditTextTimeConstant);
		EditTextProcessTransportDelay = findViewById(R.id.EditTextTransportDelay);
		LayoutGain                    = findViewById(R.id.TextInputLayoutGain);
		LayoutTimeConstant            = findViewById(R.id.TextInputLayoutTimeConstant);
		LayoutTransportDelay          = findViewById(R.id.TextInputLayoutTransportDelay);
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
			String title = getString(R.string.zn_about_title);
			String description = getString(R.string.zn_about_description);
			String link = getResources().getString(R.string.zn_about_link);

			BottomSheetDialog bottomSheet = new BottomSheetDialog(title, description, link);
			bottomSheet.show(getSupportFragmentManager(),
					"ModalBottomSheet");
		});

		RadioButtonOpened.setOnClickListener(v ->
		{
			LayoutGain.setHint(getResources().getString(R.string.hintGain));
			EditTextProcessGain.setEnabled(true);
			LayoutTimeConstant.setHint(getResources().getString(R.string.hintTime));
			LayoutTransportDelay.setHint(getResources().getString(R.string.hintDelay));
		});

		RadioButtonClosed.setOnClickListener(v ->
		{
			EditTextProcessGain.setText("");
			EditTextProcessGain.setError(null);
			LayoutGain.setHint(getResources().getString(R.string.hintClosed));
			EditTextProcessGain.setEnabled(false);
			LayoutTimeConstant.setHint(getResources().getString(R.string.hintKu));
			LayoutTransportDelay.setHint(getResources().getString(R.string.hintPu));
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
	 * Validate the process parameters.
	 * @return True if the process parameters are valid.
	 */
	private boolean validateProcessParameters()
	{
		// Validates if the process data are filled and is not zero.
		if (EditTextProcessGain.getText().toString().isEmpty() && RadioButtonOpened.isChecked())
		{
			EditTextProcessGain.setError(getResources().getString(R.string.GainError));
			Logger.show(this, R.string.GainError);
			return false;
		}

		// Validates if the time constant data are filled.
		if (EditTextProcessTimeConstant.getText().toString().isEmpty())
		{
			if (RadioButtonClosed.isChecked())
			{
				EditTextProcessTimeConstant.setError(getResources().getString(R.string.KuError));
				Logger.show(this, R.string.KuError);
				return false;
			}

			EditTextProcessTimeConstant.setError(getResources().getString(R.string.TimeConstantError));
			Logger.show(this, R.string.TimeConstantError);
			return false;
		}

		// Validates if the dead time data are filled.
		if (EditTextProcessTransportDelay.getText().toString().isEmpty())
		{
			if (RadioButtonClosed.isChecked())
			{
				EditTextProcessTransportDelay.setError(getResources().getString(R.string.PuError));
				Logger.show(this, R.string.PuError);
				return false;
			}
			EditTextProcessTransportDelay.setError(getResources().getString(R.string.TransportDelayError));
			Logger.show(this, R.string.TransportDelayError);
			return false;
		}

		// Validates if at least one controller type is checked.
		if (!CheckBoxP.isChecked() && !CheckBoxPI.isChecked() && !CheckBoxPID.isChecked())
		{
			Logger.show(this, R.string.ControllerTypeIsRequired);
			return false;
		}

		return true;
	}

	/**
	 * Compute the Controller.
	 */
	private void computeController()
	{
		if (RadioButtonOpened.isChecked()) buildOpenedTuningParameters();
		else buildClosedTuningParameters();
	}

	/**
	 * Compute the Controller using open-loop tuning.
	 */
	private void buildOpenedTuningParameters()
	{
		// Get the transfer function parameters.
		double pGain = Parser.getDouble(EditTextProcessGain.getText().toString());
		double pTime = Parser.getDouble(EditTextProcessTimeConstant.getText().toString());
		double pDead = Parser.getDouble(EditTextProcessTransportDelay.getText().toString());

		// Get the control types.
		ArrayList<ControlType> controlTypes = new ArrayList<>();
		if (CheckBoxP.isChecked()) controlTypes.add(ControlType.P);
		if (CheckBoxPI.isChecked()) controlTypes.add(ControlType.PI);
		if (CheckBoxPID.isChecked()) controlTypes.add(ControlType.PID);

		// Set up the transfer function.
		TransferFunction tf = new TransferFunction(pGain, pTime, pDead);

		// Compute the ZN Controller.
		ArrayList<ControllerParameter> controllerParameters = new ArrayList<>();
		for (ControlType controlType : controlTypes)
			controllerParameters.add(ZN.computeOpenLoop(controlType, tf));

		// Set up the model.
		String name = getResources().getString(R.string.tvZN);
		String description = getResources().getString(R.string.zn_about_description);
		TuningModel znMethod = new TuningModel(name, description,
				TuningType.ZN, tf);

		// Pass through intent to the next activity the results information.
		Intent resultActivity = new Intent(ZNActivity.this, ResultActivity.class);
		View view = findViewById(R.id.WebViewFirstOrderEquation);

		ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
				ZNActivity.this,
				view,
				ViewCompat.getTransitionName(view)
		);

		resultActivity.putExtra("CONFIGURATION", znMethod);
		resultActivity.putParcelableArrayListExtra("RESULT", controllerParameters);
		startActivity(resultActivity, options.toBundle());
	}

	/**
	 * Compute the Controller using closed-loop tuning.
	 */
	private void buildClosedTuningParameters()
	{
		// Get the transfer function parameters.
		double pUltimateGain = Parser.getDouble(EditTextProcessTimeConstant.getText().toString());
		double pUltimatePeriod = Parser.getDouble(EditTextProcessTransportDelay.getText().toString());

		// Get the control types.
		ArrayList<ControlType> controlTypes = new ArrayList<>();
		if (CheckBoxP.isChecked()) controlTypes.add(ControlType.P);
		if (CheckBoxPI.isChecked()) controlTypes.add(ControlType.PI);
		if (CheckBoxPID.isChecked()) controlTypes.add(ControlType.PID);

		// Set up the transfer function.
		TransferFunction tf = new TransferFunction(pUltimateGain, pUltimatePeriod);

		// Compute the ZN Controller.
		ArrayList<ControllerParameter> controllerParameters = new ArrayList<>();
		for (ControlType controlType : controlTypes)
			controllerParameters.add(ZN.computeClosedLoop(controlType, tf));

		// Set up the model.
		String name = getResources().getString(R.string.tvZN);
		String description = getResources().getString(R.string.zn_about_description);
		TuningModel znMethod = new TuningModel(name, description,
				TuningType.ZN, tf);

		// Pass through intent to the next activity the results information.
		Intent resultActivity = new Intent(ZNActivity.this, ResultActivity.class);
		View view = findViewById(R.id.WebViewFirstOrderEquation);

		ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
				ZNActivity.this,
				view,
				ViewCompat.getTransitionName(view)
		);

		resultActivity.putExtra("CONFIGURATION", znMethod);
		resultActivity.putParcelableArrayListExtra("RESULT", controllerParameters);
		startActivity(resultActivity, options.toBundle());
	}

	//endregion
}