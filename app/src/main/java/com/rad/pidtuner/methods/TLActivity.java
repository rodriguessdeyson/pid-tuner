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

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.TuningModel;
import com.domain.services.katex.Katex;
import com.rad.pidtuner.R;
import com.rad.pidtuner.ResultActivity;
import com.domain.services.utils.BottomSheetDialog;
import com.domain.services.utils.Logger;
import com.domain.services.utils.Parser;
import com.domain.services.tuning.TL;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.types.TuningType;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

/**
 * Tyreus-Luyben Activity.
 */
public class TLActivity extends AppCompatActivity
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
	 CheckBox reference to Proportional-integral parameter.
	 */
	private CheckBox CheckBoxPI;

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
	 * Button reference to show about dialog.
	 */
	private Button ButtonMethodInfo;

	//endregion

	//region Methods

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tl);

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
	@SuppressLint("SetTextI18n")
	private void initializeViews()
	{
		ComputeButton               = findViewById(R.id.ButtonComputePID);
		CheckBoxPI                  = findViewById(R.id.CheckBoxPI);
		CheckBoxPID                 = findViewById(R.id.CheckBoxPID);
		EditTextProcessGain         = findViewById(R.id.EditTextGain);
		EditTextProcessTimeConstant = findViewById(R.id.EditTextTimeConstant);
		ButtonMethodInfo            = findViewById(R.id.ButtonMethodInfo);
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
			String title = getResources().getString(R.string.tl_about_title);
			String description = getResources().getString(R.string.tl_about_description);
			String link = getResources().getString(R.string.tl_about_link);

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
	 * Validate the process parameters.
	 * @return True if the process parameters are valid.
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

		// Validates if at least one controller type is checked.
		if (!CheckBoxPI.isChecked() && !CheckBoxPID.isChecked())
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
		// Get the control types.
		ArrayList<ControlType> controlTypes = new ArrayList<>();
		if (CheckBoxPI.isChecked()) controlTypes.add(ControlType.PI);
		if (CheckBoxPID.isChecked()) controlTypes.add(ControlType.PID);

		// Get the transfer function parameters.
		double pUltimateGain = Parser.getDouble(EditTextProcessGain.getText().toString());
		double pUltimatePeriod = Parser.getDouble(EditTextProcessTimeConstant.getText().toString());

		// Set up the transfer function.
		TransferFunction tf = new TransferFunction(pUltimateGain, pUltimatePeriod);

		// Compute the TL Controller.
		ArrayList<ControllerParameter> controllerParameters = new ArrayList<>();
		for (ControlType controlType : controlTypes)
			controllerParameters.add(TL.compute(controlType, tf));

		// Set up the model.
		String name = getResources().getString(R.string.tvTL);
		String description = getResources().getString(R.string.tl_about_description);
		TuningModel tlMethod = new TuningModel(name, description,
				TuningType.ITAE, tf);

		// Pass through intent to the next activity the results information.
		Intent resultActivity = new Intent(TLActivity.this, ResultActivity.class);
		View view = findViewById(R.id.WebViewFirstOrderEquation);

		ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
				TLActivity.this,
				view,
				ViewCompat.getTransitionName(view)
		);

		resultActivity.putExtra("CONFIGURATION", tlMethod);
		resultActivity.putParcelableArrayListExtra("RESULT", controllerParameters);
		startActivity(resultActivity, options.toBundle());
	}

	//endregion
}