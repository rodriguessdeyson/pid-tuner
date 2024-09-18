package com.rad.pidtuner.methods.IMC;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.domain.services.tuning.interfaces.imc.ImageSelectedListener;
import com.google.android.material.textfield.TextInputLayout;
import com.domain.services.utils.BottomSheetDialog;
import com.domain.services.tuning.IMC;
import com.domain.models.tuning.types.ProcessType;
import com.domain.models.tuning.types.ControlType;
import com.domain.models.tuning.ControllerParameter;
import com.domain.models.tuning.Tuning;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.rad.pidtuner.R;
import com.rad.pidtuner.ResultActivity;
import com.domain.services.utils.Logger;
import com.domain.services.utils.Parser;
import com.domain.services.utils.ViewUtils;
import com.domain.models.tuning.TuningConfiguration;
import com.domain.models.tuning.types.TuningType;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Objects;

public class IMCActivity extends AppCompatActivity implements ImageSelectedListener
{
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
	private ImageView ImageViewSelectedModel;

	/**
	 * Switch reference to first order model.
	 */
	private SwitchMaterial SwitchUseFirstOrderDynamic;


	/**
	 * Reference of the selected transfer model.
	 */
	private IMC.TransferFunctionModel ModelSelected = null;

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

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_imc);

		// Find Views Reference.
		InitializeViews();

		// Start the listener event handler.
		InitializeEventListener();
	}

	@Override
	public void onImageSelected(String imageTag)
	{
		switch (imageTag)
		{
			case "P":
				ConfigureIMCPModel();
				break;
			case "PD":
				ConfigureIMCPDModel();
				break;
			case "PI":
				ConfigureIMCPIModel();
				break;
			case "PID1":
				ConfigureIMCPID1Model();
				break;
			case "PID2":
				ConfigureIMCPID2Model();
				break;
			default:
				throw new InvalidParameterException("IMC model not valid");
		}

		// Get the background (the border) of the ImageView
		GradientDrawable borderDrawable = (GradientDrawable) ImageViewSelectedModel.getBackground();
		int colorExtra = getResources().getColor(R.color.colorAccent);
		borderDrawable.setStroke(1, colorExtra);
	}

	@Override
	public void onDialogDismissed()
	{
		ConfigureIMCFirstOrderModel();
	}

	/**
	 Initialize the control views.
	 */
	@SuppressLint("SetTextI18n")
	private void InitializeViews()
	{
		ComputeButton                   = findViewById(R.id.ButtonComputePID);
		CheckBoxP                       = findViewById(R.id.CheckBoxP);
		CheckBoxPD                      = findViewById(R.id.CheckBoxPD);
		CheckBoxPI                      = findViewById(R.id.CheckBoxPI);
		CheckBoxPID                     = findViewById(R.id.CheckBoxPID);
		SwitchUseFirstOrderDynamic      = findViewById(R.id.SwitchUseFirstOrderDynamic);
		EditTextProcessGain             = findViewById(R.id.EditTextGain);
		EditTextProcessTimeConstant     = findViewById(R.id.EditTextParam01);
		EditTextProcessTransportDelay   = findViewById(R.id.EditTextParam02);
		EditTextLambdaTuning            = findViewById(R.id.EditTextLambda);
		ImageViewSelectedModel          = findViewById(R.id.ImageViewSelectedModel);
		LayoutGain                      = findViewById(R.id.TextInputLayoutGain);
		LayoutTimeConstant              = findViewById(R.id.TextInputLayoutTimeConstant);
		LayoutTransportDelay            = findViewById(R.id.TextInputLayoutTransportDelay);
		ButtonMethodInfo                = findViewById(R.id.ButtonMethodInfo);
	}

	/**
	 Initialize the buttons events.
	 */
	private void InitializeEventListener()
	{
		// Handle the button click.
		SwitchUseFirstOrderDynamic.setOnCheckedChangeListener((buttonView, isChecked) ->
		{
			if (!isChecked) ShowBottomSheet();
			else ConfigureIMCFirstOrderModel();
		});

		// Handle the image model click.
		ImageViewSelectedModel.setOnClickListener(v ->
		{
			if (!SwitchUseFirstOrderDynamic.isChecked())
			{
				ShowBottomSheet();
			}
		});

		// Handle the button click.
		ComputeButton.setOnClickListener(v ->
		{
			// Validates the input, from top-down approach.
			if (!ValidateProcessParameters())
				return;

			ComputeController();
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

	private void ShowBottomSheet()
	{
		BottomSheetDialogIMCModel bottomSheet = new BottomSheetDialogIMCModel();
		bottomSheet.setOnImageSelectedListener(this);
		bottomSheet.show(getSupportFragmentManager(), "ImageSelectionBottomSheet");
	}

	private boolean ValidateProcessParameters()
	{
		// Validate the lambda value.
		if (EditTextLambdaTuning.getText().toString().isEmpty())
		{
			Logger.Show(this, R.string.LambdaTuningValueRequired);
			EditTextLambdaTuning.setError(getResources().getString(R.string.LambdaTuningValueRequired));
			return false;
		}

		// If the dynamic is first order, compute this validation.
		if (SwitchUseFirstOrderDynamic.isChecked())
		{
			return ValidateFirstOrderDynamic();
		}

		switch (ModelSelected)
		{
			case P_Controller_Model:
				return ValidatePModel();
			case PI_Controller_Model:
			case PD_Controller_Model:
				return ValidatePIAndPIDModel();
			case PID_Controller_Model1:
				return ValidatePIDModel1();
			case PID_Controller_Model2:
				return ValidatePIDModel2();
		}
		return true;
	}

	private boolean ValidateFirstOrderDynamic()
	{
		// Validates if at least one controller type is checked.
		if (!CheckBoxPI.isChecked() && !CheckBoxPID.isChecked())
		{
			Logger.Show(this, R.string.ControllerTypeIsRequired);
			return false;
		}

		// Validates if the process data are filled.
		if (EditTextProcessGain.getText().toString().isEmpty())
		{
			EditTextProcessGain.setError(getResources().getString(R.string.GainError));
			Logger.Show(this, R.string.GainError);
			return false;
		}

		// Validates if the time constant data are filled.
		if (EditTextProcessTimeConstant.getText().toString().isEmpty())
		{
			EditTextProcessTimeConstant.setError(getResources().getString(R.string.TimeConstantError));
			Logger.Show(this, R.string.TimeConstantError);
			return false;
		}

		// Validates if the dead time data are filled.
		if (EditTextProcessTransportDelay.getText().toString().isEmpty())
		{
			EditTextProcessTransportDelay.setError(getResources().getString(R.string.TransportDelayError));
			Logger.Show(this, R.string.TransportDelayError);
			return false;
		}
		return true;
	}

	private boolean ValidatePModel()
	{
		// Validates if the process data are filled.
		if (EditTextProcessGain.getText().toString().isEmpty())
		{
			EditTextProcessGain.setError(getResources().getString(R.string.GainError));
			Logger.Show(this, R.string.GainError);
			return false;
		}
		return true;
	}

	private boolean ValidatePIAndPIDModel()
	{
		// Validates if the process data are filled.
		if (EditTextProcessGain.getText().toString().isEmpty())
		{
			EditTextProcessGain.setError(getResources().getString(R.string.GainError));
			Logger.Show(this, R.string.GainError);
			return false;
		}

		// Validates if the time constant data are filled.
		if (EditTextProcessTimeConstant.getText().toString().isEmpty())
		{
			EditTextProcessTimeConstant.setError(getResources().getString(R.string.TimeConstantError));
			Logger.Show(this, R.string.TimeConstantError);
			return false;
		}
		return true;
	}

	private boolean ValidatePIDModel1()
	{
		// Validates if the process data are filled.
		if (EditTextProcessGain.getText().toString().isEmpty())
		{
			EditTextProcessGain.setError(getResources().getString(R.string.GainError));
			Logger.Show(this, R.string.GainError);
			return false;
		}

		// Validates if the time constant data are filled.
		if (EditTextProcessTimeConstant.getText().toString().isEmpty())
		{
			EditTextProcessTimeConstant.setError(getResources().getString(R.string.TimeConstantError));
			Logger.Show(this, R.string.TimeConstantError);
			return false;
		}

		// Validates if the dead time data are filled.
		if (EditTextProcessTransportDelay.getText().toString().isEmpty())
		{
			EditTextProcessTransportDelay.setError(getResources().getString(R.string.SecondTimeConstantError));
			Logger.Show(this, R.string.SecondTimeConstantError);
			return false;
		}
		return true;
	}

	private boolean ValidatePIDModel2()
	{
		// Validates if the process data are filled.
		if (EditTextProcessGain.getText().toString().isEmpty())
		{
			EditTextProcessGain.setError(getResources().getString(R.string.GainError));
			Logger.Show(this, R.string.GainError);
			return false;
		}

		// Validates if the time constant data are filled.
		if (EditTextProcessTimeConstant.getText().toString().isEmpty())
		{
			EditTextProcessTimeConstant.setError(getResources().getString(R.string.TimeConstantError));
			Logger.Show(this, R.string.TimeConstantError);
			return false;
		}

		// Validates if the dead time data are filled.
		if (EditTextProcessTransportDelay.getText().toString().isEmpty())
		{
			EditTextProcessTransportDelay.setError(getResources().getString(R.string.DampingRatioError));
			Logger.Show(this, R.string.DampingRatioError);
			return false;
		}
		return true;
	}

	private void ComputeController()
	{
		// Get the transfer function parameters.
		double gain             = Parser.GetDouble(EditTextProcessGain.getText().toString());
		double timeConstant     = Parser.GetDouble(EditTextProcessTimeConstant.getText().toString());
		double dynamicParameter = Parser.GetDouble(EditTextProcessTransportDelay.getText().toString());
		double lambda           = Parser.GetDouble(EditTextLambdaTuning.getText().toString());

		Tuning imcMethod = SwitchUseFirstOrderDynamic.isChecked() ?
			FirstOrderModelTuning(gain, timeConstant, dynamicParameter, lambda) :
			ModelBasedTuning(gain, timeConstant, dynamicParameter, lambda);

		// Pass through intent to the next activity the results information.
		Intent resultActivity = new Intent(IMCActivity.this, ResultActivity.class);
		resultActivity.putExtra("RESULT", imcMethod);
		startActivity(resultActivity);
	}

	private void ConfigureIMCPModel()
	{
		ModelSelected = IMC.TransferFunctionModel.P_Controller_Model;
		ImageViewSelectedModel.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.model4, null));
		CheckBoxP.setChecked(true);
		CheckBoxPD.setChecked(false);
		CheckBoxPI.setChecked(false);
		CheckBoxPI.setClickable(false);
		CheckBoxPID.setChecked(false);
		CheckBoxPID.setClickable(false);
		ViewUtils.FadeOut(getApplicationContext(), EditTextProcessTimeConstant, EditTextProcessTransportDelay);
	}

	private void ConfigureIMCPDModel()
	{
		ModelSelected = IMC.TransferFunctionModel.PD_Controller_Model;
		ImageViewSelectedModel.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.model5, null));
		CheckBoxP.setChecked(false);
		CheckBoxPD.setChecked(true);
		CheckBoxPI.setChecked(false);
		CheckBoxPI.setClickable(false);
		CheckBoxPID.setChecked(false);
		CheckBoxPID.setClickable(false);
		ViewUtils.FadeOut(getApplicationContext(), EditTextProcessTimeConstant, EditTextProcessTransportDelay);

		LayoutGain.setHint(getResources().getString(R.string.hintGain));
		LayoutTimeConstant.setHint(getResources().getString(R.string.hintTime));

		ViewUtils.FadeIn(getApplicationContext(),EditTextProcessGain, EditTextProcessTimeConstant);
		ViewUtils.FadeOut(getApplicationContext(), EditTextProcessTransportDelay);
	}

	private void ConfigureIMCPIModel()
	{
		// Notify the model selected.
		ModelSelected = IMC.TransferFunctionModel.PI_Controller_Model;
		ImageViewSelectedModel.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.model1, null));
		CheckBoxP.setChecked(false);
		CheckBoxPD.setChecked(false);
		CheckBoxPI.setChecked(true);
		CheckBoxPI.setClickable(false);
		CheckBoxPID.setChecked(false);
		CheckBoxPID.setClickable(false);

		LayoutGain.setHint(getResources().getString(R.string.hintGain));
		LayoutTimeConstant.setHint(getResources().getString(R.string.hintTime));
		ViewUtils.FadeIn(getApplicationContext(), EditTextProcessGain, EditTextProcessTimeConstant);
		ViewUtils.FadeOut(getApplicationContext(),EditTextProcessTransportDelay);
	}

	private void ConfigureIMCPID1Model()
	{
		// Notify the model selected.
		ModelSelected = IMC.TransferFunctionModel.PID_Controller_Model1;
		ImageViewSelectedModel.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.model2, null));
		CheckBoxP.setChecked(false);
		CheckBoxPD.setChecked(false);
		CheckBoxPI.setChecked(false);
		CheckBoxPI.setClickable(false);
		CheckBoxPID.setChecked(true);
		CheckBoxPID.setClickable(false);

		LayoutGain.setHint(getResources().getString(R.string.hintGain));
		LayoutTimeConstant.setHint(getResources().getString(R.string.hintTime));
		LayoutTransportDelay.setHint(getResources().getString(R.string.hintSecondTimeConstant));
		ViewUtils.FadeIn(getApplicationContext(), EditTextProcessGain, EditTextProcessTimeConstant, EditTextProcessTransportDelay);
	}

	private void ConfigureIMCPID2Model()
	{
		// Notify the model selected.
		ModelSelected = IMC.TransferFunctionModel.PID_Controller_Model2;
		ImageViewSelectedModel.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.model3, null));
		CheckBoxP.setChecked(false);
		CheckBoxPD.setChecked(false);
		CheckBoxPI.setChecked(false);
		CheckBoxPI.setClickable(false);
		CheckBoxPID.setChecked(true);
		CheckBoxPID.setClickable(false);

		LayoutGain.setHint(getResources().getString(R.string.hintGain));
		LayoutTimeConstant.setHint(getResources().getString(R.string.hintTime));
		LayoutTransportDelay.setHint(getResources().getString(R.string.hintDampingRatio));
		ViewUtils.FadeIn(getApplicationContext(), EditTextProcessGain, EditTextProcessTimeConstant, EditTextProcessTransportDelay);
	}

	private void ConfigureIMCFirstOrderModel()
	{
		// Notify the model selected.
		ModelSelected = null;
		SwitchUseFirstOrderDynamic.setChecked(true);
		CheckBoxP.setChecked(false);
		CheckBoxPD.setChecked(false);
		CheckBoxPI.setChecked(false);
		CheckBoxPI.setClickable(true);
		CheckBoxPID.setChecked(false);
		CheckBoxPID.setClickable(true);

		// Get the background (the border) of the ImageView
		GradientDrawable borderDrawable = (GradientDrawable) ImageViewSelectedModel.getBackground();
		int colorExtra = getResources().getColor(R.color.formula);
		borderDrawable.setStroke(1, colorExtra);

		ImageViewSelectedModel.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.first_order_dynamic, null));
		LayoutGain.setHint(getResources().getString(R.string.hintGain));
		LayoutTimeConstant.setHint(getResources().getString(R.string.hintTime));
		LayoutTransportDelay.setHint(getResources().getString(R.string.hintDampingRatio));
		ViewUtils.FadeIn(getApplicationContext(), EditTextProcessGain, EditTextProcessTimeConstant, EditTextProcessTransportDelay);
	}

	/**
	 * Generates the IMC first order model tuning.
	 * @param gain Gain.
	 * @param timeConstant Time constant.
	 * @param transportDelay Transport delay.
	 * @param lambda Lambda.
	 * @return Tuning configuration.
	 */
	private Tuning FirstOrderModelTuning(double gain, double timeConstant, double transportDelay,
										 double lambda)
	{
		// Get the control types.
		ArrayList<ControlType> controlTypes = new ArrayList<>();
		if (SwitchUseFirstOrderDynamic.isChecked())
		{
			if (CheckBoxPI.isChecked()) controlTypes.add(ControlType.PI);
			if (CheckBoxPID.isChecked()) controlTypes.add(ControlType.PID);
		}

		// Compute the IMC Controller.
		ArrayList<ControllerParameter> controllerParameters = new ArrayList<>();
		for (ControlType controlType : controlTypes)
			controllerParameters.add(IMC.ComputeLambdaTuning(controlType, gain, timeConstant,
					transportDelay, lambda));

		// Set the tuning configuration.
		TuningConfiguration config = new TuningConfiguration(ProcessType.LambdaTuning,
				controllerParameters, gain, timeConstant, transportDelay);
		ArrayList<TuningConfiguration> configuration = new ArrayList<>();
		configuration.add(config);

		// Get the tuning information.
		return new Tuning("Internal Model Control", "", TuningType.IMC, configuration);
	}

	/**
	 * Generates the IMC model bases tuning.
	 * @param gain Gain.
	 * @param timeConstant Time constant.
	 * @param dynamicParameter dynamic parameter (Second time constant or Dumping ratio)
	 * @param lambda Lambda.
	 * @return Tuning configuration.
	 */
	private Tuning ModelBasedTuning(double gain, double timeConstant, double dynamicParameter,
									double lambda)
	{
		// Compute the IMC Controller.
		ArrayList<ControllerParameter> controllerParameters = new ArrayList<>();
			controllerParameters.add(IMC.ComputeLambdaTuning(ModelSelected,
					gain,
					timeConstant,
					dynamicParameter,
					dynamicParameter,
					lambda));

		// Set the tuning configuration.
		TuningConfiguration config = new TuningConfiguration(ProcessType.LambdaTuning,
				controllerParameters, gain, timeConstant, lambda, dynamicParameter);
		ArrayList<TuningConfiguration> configuration = new ArrayList<>();
		configuration.add(config);

		// Get the tuning information.
		return new Tuning("Internal Model Control", "", TuningType.IMC, configuration);
	}

}

