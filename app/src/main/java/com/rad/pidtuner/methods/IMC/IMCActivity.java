package com.rad.pidtuner.methods.IMC;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.transition.ChangeBounds;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProvider;

import com.domain.models.tuning.TransferFunction;
import com.domain.models.tuning.TuningModel;
import com.domain.models.tuning.types.IMCModelBasedType;
import com.domain.services.katex.Katex;
import com.domain.services.katex.KatexListener;
import com.google.android.material.textfield.TextInputLayout;
import com.domain.services.utils.BottomSheetDialog;
import com.domain.services.tuning.IMC;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.rad.pidtuner.R;
import com.rad.pidtuner.ResultActivity;
import com.domain.services.utils.Logger;
import com.domain.services.utils.Parser;
import com.domain.services.utils.ViewUtils;
import com.domain.models.tuning.types.TuningType;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Internal Model Control Activity.
 */
public class IMCActivity extends AppCompatActivity implements IMCModelListener, KatexListener
{
	//region Constants

	/**
	 * URL for the katex function layout.
	 */
	private static final String KATEX_URL = "file:///android_asset/katex_function_layout.html";

	//endregion

	//region Attributes

	/**
	 * ViewModel Observable Pattern reference.
	 */
	private IMCViewModel imcViewModel;

	/**
	 * Button reference to compute event.
	 */
	private Button ComputeButton;

	/**
	 CheckBox reference to Proportional parameter.
	 */
	private CheckBox CheckBoxP;

	/**
	 CheckBox reference to Proportional-Derivative parameter.
	 */
	private CheckBox CheckBoxPD;

	/**
	 CheckBox reference to Proportional-Integral parameter.
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
	 EditText reference to transport delay parameter.
	 */
	private EditText EditTextProcessTransportDelay;

	/**
	 EditText reference to lambda parameter.
	 */
	private EditText EditTextLambdaTuning;

	/**
	 * Selected Model.
	 */
	private WebView WebViewTransferModel;

	/**
	 * Switch reference to first order model.
	 */
	private SwitchMaterial SwitchUseFirstOrderDynamic;

	/**
	 * Reference of the selected transfer model.
	 */
	private IMCModelBasedType IMCModelType = null;

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

	//region Callbacks

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_imc);

		// Find Views Reference.
		initializeViews();

		// Start the listener event handler.
		initializeEventListener();

		// Configure transfer function.
		setTransferFunction();
	}

	@Override
	public void onModelSelected(String modelTag)
	{
		runOnUiThread(() -> imcViewModel.setMyVariable(modelTag));

		switch (modelTag)
		{
			case "P":
				IMCModelType = IMCModelBasedType.P;
				runOnUiThread(this::configureIMCPModel);
				break;
			case "PD":
				IMCModelType = IMCModelBasedType.PD;
				runOnUiThread(this::configureIMCPDModel);
				break;
			case "PI":
				IMCModelType = IMCModelBasedType.PI;
				runOnUiThread(this::configureIMCPIModel);
				break;
			case "PID1":
				IMCModelType = IMCModelBasedType.PID1;
				runOnUiThread(this::configureIMCPID1Model);
				break;
			case "PID2":
				IMCModelType = IMCModelBasedType.PID2;
				runOnUiThread(this::configureIMCPID2Model);
				break;
			default:
				throw new InvalidParameterException("IMC model not valid");
		}
	}

	@Override
	public void onDialogDismissed()
	{
		configureIMCFirstOrderModel();
	}

	@Override
	public void onTransferFunctionClick(String model)
	{
		if (!SwitchUseFirstOrderDynamic.isChecked())
		{
			showBottomSheet();
		}
	}

	//endregion

	//region Methods

	/**
	 * Initialize the control views.
	 */
	private void initializeViews()
	{
		ComputeButton                 = findViewById(R.id.ButtonComputePID);
		CheckBoxP                     = findViewById(R.id.CheckBoxP);
		CheckBoxPD                    = findViewById(R.id.CheckBoxPD);
		CheckBoxPI                    = findViewById(R.id.CheckBoxPI);
		CheckBoxPID                   = findViewById(R.id.CheckBoxPID);
		SwitchUseFirstOrderDynamic    = findViewById(R.id.SwitchUseFirstOrderDynamic);
		EditTextProcessGain           = findViewById(R.id.EditTextGain);
		EditTextProcessTimeConstant   = findViewById(R.id.EditTextParam01);
		EditTextProcessTransportDelay = findViewById(R.id.EditTextParam02);
		EditTextLambdaTuning          = findViewById(R.id.EditTextLambda);
		WebViewTransferModel          = findViewById(R.id.WebViewTransferModel);
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
		SwitchUseFirstOrderDynamic.setOnCheckedChangeListener((buttonView, isChecked) ->
		{
			if (!isChecked) showBottomSheet();
			else configureIMCFirstOrderModel();

			EditTextProcessGain.setText("");
			EditTextProcessTimeConstant.setText("");
			EditTextProcessTransportDelay.setText("");
			EditTextLambdaTuning.setText("");
		});

		// Initialize ViewModel.
		imcViewModel = new ViewModelProvider(this).get(IMCViewModel.class);

		// Observe LiveData for changes
		// Trigger a method when the variable changes
		imcViewModel.getMyVariable().observe(this, this::updateWebViewTransferFunction);

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
			String title = getResources().getString(R.string.imc_about_title);
			String description = getResources().getString(R.string.imc_about_description);
			String link = getResources().getString(R.string.imc_about_link);

			BottomSheetDialog bottomSheet = new BottomSheetDialog(title, description, link);
			bottomSheet.show(getSupportFragmentManager(),
					"ModalBottomSheet");
		});
	}

	/**
	 * Set up the transfer function.
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void setTransferFunction()
	{
		Locale locale = Locale.getDefault();
		Katex katex = new Katex(locale);
		katex.setOnClickListener(this);
		WebViewTransferModel.getSettings().setJavaScriptEnabled(true);
		WebViewTransferModel.addJavascriptInterface(katex, "Android");
		WebViewTransferModel.loadUrl(KATEX_URL);
		WebViewTransferModel.setWebViewClient(new WebViewClient() {
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
	 * Update transfer function model.
	 * @param model Model to update.
	 */
	private void updateWebViewTransferFunction(String model)
	{
		Locale locale = Locale.getDefault();
		String params = String.format(locale, "updateEquation(Android.setModelEquation('" + "%s" + "'))", model);
		WebViewTransferModel.evaluateJavascript(params, null);
	}

	/**
	 * Show the bottom sheet dialog.
	 */
	private void showBottomSheet()
	{
		BottomSheetDialogIMCModel bottomSheet = new BottomSheetDialogIMCModel();
		bottomSheet.setOnModelSelectedListener(this);
		bottomSheet.show(getSupportFragmentManager(), "ModelSelectionBottomSheet");
	}

	/**
	 * Validates the process parameters.
	 * @return True if valid, false otherwise.
	 */
	private boolean validateProcessParameters()
	{
		// If the dynamic is first order, compute this validation.
		if (SwitchUseFirstOrderDynamic.isChecked())
			return validateFirstOrderDynamic();

		switch (IMCModelType)
		{
			case P:
				return validatePModel();
			case PI:
			case PD:
				return validatePIAndPIDModel();
			case PID1:
				return validatePIDModel1();
			case PID2:
				return validatePIDModel2();
		}

		// Validate the lambda value.
		if (EditTextLambdaTuning.getText().toString().isEmpty())
		{
			Logger.show(this, R.string.LambdaTuningValueRequired);
			EditTextLambdaTuning.setError(getResources().getString(R.string.LambdaTuningValueRequired));
			return false;
		}

		return true;
	}

	/**
	 * Validates the first order dynamic parameters.
	 * @return True if valid, false otherwise.
	 */
	private boolean validateFirstOrderDynamic()
	{
		// Validates if at least one controller type is checked.
		if (!CheckBoxPI.isChecked() && !CheckBoxPID.isChecked())
		{
			Logger.show(this, R.string.ControllerTypeIsRequired);
			return false;
		}

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
		return true;
	}

	/**
	 * Validates the P model parameters.
	 * @return True if valid, false otherwise.
	 */
	private boolean validatePModel()
	{
		// Validates if the process data are filled.
		if (EditTextProcessGain.getText().toString().isEmpty())
		{
			EditTextProcessGain.setError(getResources().getString(R.string.GainError));
			Logger.show(this, R.string.GainError);
			return false;
		}
		return true;
	}

	/**
	 * Validates the PI and PID model parameters.
	 * @return True if valid, false otherwise.
	 */
	private boolean validatePIAndPIDModel()
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
		return true;
	}

	/**
	 * Validates the PID model 1 parameters.
	 * @return True if valid, false otherwise.
	 */
	private boolean validatePIDModel1()
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
			EditTextProcessTransportDelay.setError(getResources().getString(R.string.SecondTimeConstantError));
			Logger.show(this, R.string.SecondTimeConstantError);
			return false;
		}
		return true;
	}

	/**
	 * Validates the PID model 2 parameters.
	 * @return True if valid, false otherwise.
	 */
	private boolean validatePIDModel2()
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
			EditTextProcessTransportDelay.setError(getResources().getString(R.string.DampingRatioError));
			Logger.show(this, R.string.DampingRatioError);
			return false;
		}
		return true;
	}

	/**
	 * Configures the UI for IMC P model.
	 */
	private void configureIMCPModel()
	{
		// Set up checkboxes.
		pCheckbox(true, true);
		pdCheckbox(false, false);
		piCheckbox(false, false, false);
		pidCheckbox(false, false, false);

		ViewUtils.FadeOut(getApplicationContext(),
			LayoutTimeConstant,
			LayoutTransportDelay);
	}

	/**
	 * Configures the UI for IMC PD model.
	 */
	private void configureIMCPDModel()
	{
		// Set up checkboxes.
		pCheckbox(false, false);
		pdCheckbox(true, true);
		piCheckbox(false, false, false);
		pidCheckbox(false, false, false);

		LayoutGain.setHint(getResources().getString(R.string.hintGain));
		LayoutTimeConstant.setHint(getResources().getString(R.string.hintTime));

		ViewUtils.FadeIn(getApplicationContext(),
				LayoutGain,
				LayoutTimeConstant);

		ViewUtils.FadeOut(getApplicationContext(),
				LayoutTransportDelay);
	}

	/**
	 * Configures the UI for IMC PI model.
	 */
	private void configureIMCPIModel()
	{
		// Set up checkboxes.
		pCheckbox(false, false);
		pdCheckbox(false, false);
		piCheckbox(true, false, true);
		pidCheckbox(false, false, false);

		LayoutGain.setHint(getResources().getString(R.string.hintGain));
		LayoutTimeConstant.setHint(getResources().getString(R.string.hintTime));

		ViewUtils.FadeIn(getApplicationContext(),
				LayoutGain,
				LayoutTimeConstant);

		ViewUtils.FadeOut(getApplicationContext(),
				LayoutTransportDelay);
	}

	/**
	 * Configures the UI for IMC PID model.
	 */
	private void configureIMCPID1Model()
	{
		// Set up checkboxes.
		pCheckbox(false, false);
		pdCheckbox(false, false);
		piCheckbox(false, false, false);
		pidCheckbox(true, false, true);

		LayoutGain.setHint(getResources().getString(R.string.hintGain));
		LayoutTimeConstant.setHint(getResources().getString(R.string.hintTime));
		LayoutTransportDelay.setHint(getResources().getString(R.string.hintSecondTimeConstant));

		ViewUtils.FadeIn(getApplicationContext(),
				LayoutGain,
				LayoutTimeConstant,
				LayoutTransportDelay);
	}

	/**
	 * Configures the UI for IMC PID model.
	 */
	private void configureIMCPID2Model()
	{
		// Set up checkboxes.
		pCheckbox(false, false);
		pdCheckbox(false, false);
		piCheckbox(false, false, false);
		pidCheckbox(true, false, true);

		LayoutGain.setHint(getResources().getString(R.string.hintGain));
		LayoutTimeConstant.setHint(getResources().getString(R.string.hintTime));
		LayoutTransportDelay.setHint(getResources().getString(R.string.hintDampingRatio));

		ViewUtils.FadeIn(getApplicationContext(),
				LayoutGain,
				LayoutTimeConstant,
				LayoutTransportDelay);
	}

	/**
	 * Configures the UI for IMC first order model.
	 */
	private void configureIMCFirstOrderModel()
	{
		// Notify the model selected.
		imcViewModel.setMyVariable("");
		SwitchUseFirstOrderDynamic.setChecked(true);

		// Set up checkboxes.
		pCheckbox(false, false);
		pdCheckbox(false, false);
		piCheckbox(true, true, false);
		pidCheckbox(true, true, false);

		LayoutGain.setHint(getResources().getString(R.string.hintGain));
		LayoutTimeConstant.setHint(getResources().getString(R.string.hintTime));
		LayoutTransportDelay.setHint(getResources().getString(R.string.hintDelay));

		ViewUtils.FadeIn(getApplicationContext(),
				LayoutGain,
				LayoutTimeConstant,
				LayoutTransportDelay);
	}

	/**
	 * Set up the p checkbox states.
	 * @param enabled Enabled state.
	 * @param checked Checked state.
	 */
	private void pCheckbox(boolean enabled, boolean checked)
	{
		CheckBoxP.setEnabled(enabled);
		CheckBoxP.setChecked(checked);
		CheckBoxP.setClickable(false);
	}

	/**
	 * Set up the pd checkbox states.
	 * @param enabled Enabled state.
	 * @param checked Checked state.
	 */
	private void pdCheckbox(boolean enabled, boolean checked)
	{
		CheckBoxPD.setEnabled(enabled);
		CheckBoxPD.setChecked(checked);
		CheckBoxPD.setClickable(false);
	}

	/**
	 * Set up the pi checkbox states.
	 * @param enabled Enabled state.
	 * @param clickable Clickable state.
	 * @param checked Checked state.
	 */
	private void piCheckbox(boolean enabled, boolean clickable, boolean checked)
	{
		CheckBoxPI.setEnabled(enabled);
		CheckBoxPI.setChecked(checked);
		CheckBoxPI.setClickable(clickable);
	}

	/**
	 * Set up the pid checkbox states.
	 * @param enabled Enabled state.
	 * @param clickable Clickable state.
	 * @param checked Checked state.
	 */
	private void pidCheckbox(boolean enabled, boolean clickable, boolean checked)
	{
		CheckBoxPID.setEnabled(enabled);
		CheckBoxPID.setChecked(checked);
		CheckBoxPID.setClickable(clickable);
	}

	/**
	 * Compute the controller.
	 */
	private void computeController()
	{
		Editable processGain = EditTextProcessGain.getText();
		Editable processTimeConstant = EditTextProcessTimeConstant.getText();
		Editable processTransportDelay = EditTextProcessTransportDelay.getText();
		Editable lambdaTuning = EditTextLambdaTuning.getText();

		// Get the transfer function parameters.
		double gain = Parser
			.getDouble(processGain.length() > 0 ? processGain.toString() : "0");
		double timeConstant = Parser
			.getDouble(processTimeConstant.length() > 0 ? processTimeConstant.toString() : "0");
		double dynamicParameter = Parser
			.getDouble(processTransportDelay.length() > 0 ? processTransportDelay.toString() : "0");
		double lambda = Parser
			.getDouble(lambdaTuning.length() > 0 ? lambdaTuning.toString() : "0");

		if (SwitchUseFirstOrderDynamic.isChecked())
			firstOrderModelTuning(gain, timeConstant, dynamicParameter, lambda);
		else
			modelBasedTuning(gain, timeConstant, dynamicParameter, lambda);
	}

	/**
	 * Generates the IMC first order model tuning.
	 * @param gain Gain.
	 * @param timeConstant Time constant.
	 * @param transportDelay Transport delay.
	 * @param lambda Lambda.
	 */
	private void firstOrderModelTuning(double gain, double timeConstant, double transportDelay,
									   double lambda)
	{
		// Get the control types.
		ArrayList<ControlType> controlTypes = new ArrayList<>();
		if (SwitchUseFirstOrderDynamic.isChecked())
		{
			if (CheckBoxPI.isChecked()) controlTypes.add(ControlType.PI);
			if (CheckBoxPID.isChecked()) controlTypes.add(ControlType.PID);
		}

		// Set up the transfer function.
		TransferFunction tf = new TransferFunction(gain, timeConstant, transportDelay, lambda);

		// Compute the IMC Controller.
		ArrayList<ControllerParameter> controllerParameters = new ArrayList<>();
		for (ControlType controlType : controlTypes)
			controllerParameters.add(IMC.computeLambdaTuning(controlType, tf));

		// Set up the model.
		String description = getString(R.string.tvIMCDesc);
		TuningModel imcMethod = new TuningModel("Internal Model Control", description,
				TuningType.IMC, tf);

		// Pass through intent to the next activity the results information.
		Intent resultActivity = new Intent(IMCActivity.this, ResultActivity.class);
		View view = findViewById(R.id.WebViewTransferModel);

		ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
				IMCActivity.this,
				view,
				ViewCompat.getTransitionName(view)
		);

		resultActivity.putExtra("CONFIGURATION", imcMethod);
		resultActivity.putParcelableArrayListExtra("RESULT", controllerParameters);
		startActivity(resultActivity, options.toBundle());
	}

	/**
	 * Generates the IMC model bases tuning.
	 * @param gain Gain.
	 * @param timeConstant Time constant.
	 * @param dynamicParameter dynamic parameter (Second time constant or Dumping ratio)
	 * @param lambda Lambda.
	 */
	private void modelBasedTuning(double gain, double timeConstant, double dynamicParameter,
								  double lambda)
	{
		// Set up the transfer function.
		TransferFunction tf = new TransferFunction(IMCModelType, gain, timeConstant,
				dynamicParameter, lambda);

		// Compute the IMC Controller.
		ArrayList<ControllerParameter> controllerParameters = new ArrayList<>();
			controllerParameters.add(IMC.computeLambdaTuning(tf));

		// Set up the model.
		String description = getString(R.string.tvIMCDesc);
		TuningModel imcMethod = new TuningModel("Internal Model Control",
				description, TuningType.IMC, tf);

		// Pass through intent to the next activity the results information.
		Intent resultActivity = new Intent(IMCActivity.this, ResultActivity.class);
		View view = findViewById(R.id.WebViewTransferModel);

		ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
				IMCActivity.this,
				view,
				ViewCompat.getTransitionName(view)
		);

		resultActivity.putExtra("CONFIGURATION", imcMethod);
		resultActivity.putParcelableArrayListExtra("RESULT", controllerParameters);
		startActivity(resultActivity, options.toBundle());
	}

	//endregion
}

