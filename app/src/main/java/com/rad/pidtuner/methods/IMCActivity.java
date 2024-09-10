package com.rad.pidtuner.methods;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.rad.pidtuner.utils.BottomSheetDialog;
import com.tunings.methods.IMC;
import com.tunings.methods.ZN;
import com.tunings.models.ProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameter;
import com.tunings.models.Tuning;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.rad.pidtuner.R;
import com.rad.pidtuner.ResultActivity;
import com.rad.pidtuner.utils.Logger;
import com.rad.pidtuner.utils.Parser;
import com.rad.pidtuner.utils.ViewUtils;
import com.tunings.models.TuningConfiguration;
import com.tunings.models.TuningType;

import java.util.ArrayList;

public class IMCActivity extends AppCompatActivity
{
	//region Attributes

	/**
	 * Button reference to compute event.
	 */
	private Button ComputeButton;

	/**
	 * Button of the models types.
	 */
	private ImageButton[] ImageModelButton;

	/**
	 * Button reference to close.
	 */
	private FloatingActionButton FBClose;

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
	 * View of models reference.
	 */
	private View ViewPIDModels;

	/**
	 * CardView reference to process container.
	 */
	private CardView CardViewProcessConfiguration;

	/**
	 * CardView reference to controller container.
	 */
	private CardView CardViewControllerConfiguration;

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

	/**
	 Initialize the control views.
	 */
	@SuppressLint("SetTextI18n")
	private void InitializeViews()
	{
		ComputeButton                   = findViewById(R.id.ButtonComputePID);
		FBClose                         = findViewById(R.id.FloatingActionButtonClose);
		CheckBoxPI                      = findViewById(R.id.CheckBoxPI);
		CheckBoxPID                     = findViewById(R.id.CheckBoxPID);
		SwitchUseFirstOrderDynamic      = findViewById(R.id.SwitchUseFirstOrderDynamic);
		EditTextProcessGain             = findViewById(R.id.EditTextGain);
		EditTextProcessTimeConstant     = findViewById(R.id.EditTextParam01);
		EditTextProcessTransportDelay   = findViewById(R.id.EditTextParam02);
		EditTextLambdaTuning            = findViewById(R.id.EditTextLambda);
		ViewPIDModels                   = findViewById(R.id.ViewPIDModels);
		ImageViewSelectedModel          = findViewById(R.id.ImageViewSelectedModel);
		CardViewProcessConfiguration    = findViewById(R.id.CardViewProcessConfiguration);
		CardViewControllerConfiguration = findViewById(R.id.CardViewControllerConfiguration);
		LayoutGain                      = findViewById(R.id.TextInputLayoutGain);
		LayoutTimeConstant              = findViewById(R.id.TextInputLayoutTimeConstant);
		LayoutTransportDelay            = findViewById(R.id.TextInputLayoutTransportDelay);
		ButtonMethodInfo                = findViewById(R.id.ButtonMethodInfo);
		ImageModelButton                = new ImageButton[5];
		ImageModelButton[0]             = findViewById(R.id.ImageButtonModel1);
		ImageModelButton[1]             = findViewById(R.id.ImageButtonModel2);
		ImageModelButton[2]             = findViewById(R.id.ImageButtonModel3);
		ImageModelButton[3]             = findViewById(R.id.ImageButtonModel4);
		ImageModelButton[4]             = findViewById(R.id.ImageButtonModel5);
	}

	/**
	 Initialize the buttons events.
	 */
	private void InitializeEventListener()
	{
		// Handle the button click.
		SwitchUseFirstOrderDynamic.setOnCheckedChangeListener((buttonView, isChecked) ->
		{
			if (isChecked)
			{
				if (ModelSelected == null)
					ViewUtils.FadeOut(getApplicationContext(), ImageViewSelectedModel, ViewPIDModels);
				else
					ViewUtils.FadeOut(getApplicationContext(), ImageViewSelectedModel);
				ViewUtils.FadeIn(getApplicationContext(), CardViewControllerConfiguration, CardViewProcessConfiguration);
				ViewUtils.FadeIn(getApplicationContext(),
						EditTextProcessGain,
						EditTextProcessTimeConstant,
						EditTextProcessTransportDelay);
				LayoutGain.setHint(getResources().getString(R.string.hintGain));
				LayoutTimeConstant.setHint(getResources().getString(R.string.hintTime));
				LayoutTransportDelay.setHint(getResources().getString(R.string.hintDelay));
				ModelSelected = null;
			}
			else
			{
				ViewUtils.FadeOut(getApplicationContext(), ComputeButton);
				ViewUtils.FadeIn(getApplicationContext(), ImageViewSelectedModel, ViewPIDModels);
			}
		});

		// Handle the image model click.
		ImageViewSelectedModel.setOnClickListener(v ->
		{
			ViewUtils.FadeIn(getApplicationContext(), ViewPIDModels);
			ViewUtils.FadeOut(getApplicationContext(), ComputeButton);
		});

		// Handle the ImageButton click for models.
		ImageModelButton[0].setOnClickListener(v ->
		{
			// Notify the model selected.
			ModelSelected = IMC.TransferFunctionModel.PI_Controller_Model;
			ImageViewSelectedModel
				.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.model1, null));
			ViewUtils.FadeIn(getApplicationContext(),
				ImageViewSelectedModel,
				ComputeButton,
				EditTextProcessGain,
				EditTextProcessTimeConstant);
			LayoutGain.setHint(getResources().getString(R.string.hintGain));
			LayoutTimeConstant.setHint(getResources().getString(R.string.hintTime));
			ViewUtils.FadeOut(getApplicationContext(), CardViewControllerConfiguration, CardViewProcessConfiguration, EditTextProcessTransportDelay,
				ViewPIDModels);
		});

		ImageModelButton[1].setOnClickListener(v ->
		{
			// Notify the model selected.
			ModelSelected = IMC.TransferFunctionModel.PID_Controller_Model1;
			ImageViewSelectedModel
				.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.model2, null));
			ViewUtils.FadeIn(getApplicationContext(), ImageViewSelectedModel,
				ComputeButton,
				EditTextProcessGain,
				EditTextProcessTimeConstant,
				EditTextProcessTransportDelay);
			LayoutGain.setHint(getResources().getString(R.string.hintGain));
			LayoutTimeConstant.setHint(getResources().getString(R.string.hintTime));
			LayoutTransportDelay.setHint(getResources().getString(R.string.hintSecondTimeConstant));
			ViewUtils.FadeOut(getApplicationContext(), CardViewControllerConfiguration, CardViewProcessConfiguration, ViewPIDModels);
		});

		ImageModelButton[2].setOnClickListener(v ->
		{
			// Notify the model selected.
			ModelSelected = IMC.TransferFunctionModel.PID_Controller_Model2;
			ImageViewSelectedModel
				.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.model3, null));

			ViewUtils.FadeIn(getApplicationContext(), ImageViewSelectedModel,
				ComputeButton,
				EditTextProcessGain,
				EditTextProcessTimeConstant,
				EditTextProcessTransportDelay);
			LayoutGain.setHint(getResources().getString(R.string.hintGain));
			LayoutTimeConstant.setHint(getResources().getString(R.string.hintTime));
			LayoutTransportDelay.setHint(getResources().getString(R.string.hintDampingRatio));
			ViewUtils.FadeOut(getApplicationContext(), CardViewControllerConfiguration, CardViewProcessConfiguration, ViewPIDModels);
		});

		ImageModelButton[3].setOnClickListener(v ->
		{
			// Notify the model selected.
			ModelSelected = IMC.TransferFunctionModel.P_Controller_Model;
			ImageViewSelectedModel
				.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.model4, null));

			ViewUtils.FadeIn(getApplicationContext(), ImageViewSelectedModel,
				ComputeButton);
			ViewUtils.FadeOut(getApplicationContext(),
					CardViewControllerConfiguration, CardViewProcessConfiguration,
				EditTextProcessTimeConstant,
				EditTextProcessTransportDelay,
				ViewPIDModels);
		});

		ImageModelButton[4].setOnClickListener(v ->
		{
			// Notify the model selected.
			ModelSelected = IMC.TransferFunctionModel.PD_Controller_Model;
			ImageViewSelectedModel
				.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.model5, null));

			ViewUtils.FadeIn(getApplicationContext(), ImageViewSelectedModel,
				ComputeButton,
				EditTextProcessGain,
				EditTextProcessTimeConstant);
			LayoutGain.setHint(getResources().getString(R.string.hintGain));
			LayoutTimeConstant.setHint(getResources().getString(R.string.hintTime));
			ViewUtils.FadeOut(getApplicationContext(),
					CardViewControllerConfiguration, CardViewProcessConfiguration,
				EditTextProcessTransportDelay,
				ViewPIDModels);
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

			BottomSheetDialog bottomSheet = new BottomSheetDialog(title, description);
			bottomSheet.show(getSupportFragmentManager(),
					"ModalBottomSheet");
		});

		// Handle the cancel button.
		FBClose.setOnClickListener(v ->
		{
			if (ModelSelected == null)
			{
				SwitchUseFirstOrderDynamic.setChecked(true);
			}
			ViewUtils.FadeOut(getApplicationContext(), ViewPIDModels);
			ViewUtils.FadeIn(getApplicationContext(), ComputeButton);
		});
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

