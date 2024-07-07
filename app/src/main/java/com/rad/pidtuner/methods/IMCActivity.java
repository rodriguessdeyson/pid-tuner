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
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.rad.pidtuner.database.DataAccess;
import com.rad.pidtuner.database.SettingModel;
import com.tunings.methods.IMC;
import com.tunings.models.ControlProcessType;
import com.tunings.models.ControlType;
import com.tunings.models.ControllerParameters;
import com.tunings.models.TuningMethod;
import com.tunings.models.TuningType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.rad.pidtuner.R;
import com.rad.pidtuner.ResultActivity;
import com.rad.pidtuner.utils.Logger;
import com.rad.pidtuner.utils.Parser;
import com.rad.pidtuner.utils.ViewUtils;

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
	 * Linear layout reference to controller container.
	 */
	private  LinearLayout LinearLayoutController;

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
		ComputeButton                 = findViewById(R.id.ButtonComputePID);
		FBClose                       = findViewById(R.id.FloatingActionButtonClose);
		CheckBoxPI                    = findViewById(R.id.CheckBoxPI);
		CheckBoxPID                   = findViewById(R.id.CheckBoxPID);
		SwitchUseFirstOrderDynamic    = findViewById(R.id.SwitchUseFirstOrderDynamic);
		EditTextProcessGain           = findViewById(R.id.EditTextGain);
		EditTextProcessTimeConstant   = findViewById(R.id.EditTextParam01);
		EditTextProcessTransportDelay = findViewById(R.id.EditTextParam02);
		EditTextLambdaTuning          = findViewById(R.id.EditTextLambda);
		ViewPIDModels                 = findViewById(R.id.ViewPIDModels);
		ImageViewSelectedModel        = findViewById(R.id.ImageViewSelectedModel);
		LinearLayoutController        = findViewById(R.id.LinearLayoutControllerContainer);
		LayoutGain                    = findViewById(R.id.TextInputLayoutGain);
		LayoutTimeConstant            = findViewById(R.id.TextInputLayoutTimeConstant);
		LayoutTransportDelay          = findViewById(R.id.TextInputLayoutTransportDelay);
		ImageModelButton              = new ImageButton[5];
		ImageModelButton[0]           = findViewById(R.id.ImageButtonModel1);
		ImageModelButton[1]           = findViewById(R.id.ImageButtonModel2);
		ImageModelButton[2]           = findViewById(R.id.ImageButtonModel3);
		ImageModelButton[3]           = findViewById(R.id.ImageButtonModel4);
		ImageModelButton[4]           = findViewById(R.id.ImageButtonModel5);
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
				ViewUtils.FadeIn(getApplicationContext(), LinearLayoutController);
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
			ViewUtils.FadeOut(getApplicationContext(), LinearLayoutController, EditTextProcessTransportDelay,
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
			ViewUtils.FadeOut(getApplicationContext(), LinearLayoutController, ViewPIDModels);
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
			ViewUtils.FadeOut(getApplicationContext(), LinearLayoutController, ViewPIDModels);
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
				LinearLayoutController,
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
				LinearLayoutController,
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
		}
		else
		{
			switch (ModelSelected)
			{
				case PI_Controller_Model:
				case PD_Controller_Model:

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
					break;
				case PID_Controller_Model1:

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
					break;
				case PID_Controller_Model2:

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
					break;
				case P_Controller_Model:

					// Validates if the process data are filled.
					if (EditTextProcessGain.getText().toString().isEmpty())
					{
						EditTextProcessGain.setError(getResources().getString(R.string.GainError));
						Logger.Show(this, R.string.GainError);
						return false;
					}
					break;
			}
		}
		return true;
	}

	private void ComputeController()
	{
		ArrayList<TuningMethod> tuningMethods = new ArrayList<>();
		String usedParameter = getResources().getString(R.string.TransportDelay);

		// Gets the tuning basics information.
		TuningMethod imcMethod = new TuningMethod();
		imcMethod.setTuningName("Internal Model Control");
		imcMethod.setTuningType(TuningType.IMC);

		// Gets the selected process.
		ArrayList<ControlProcessType> processTypes = new ArrayList<>();
		processTypes.add(ControlProcessType.LambdaTuning);
		imcMethod.setControlProcessTypes(processTypes);

		// Gets the control to compute.
		ArrayList<ControlType> controlTypes = new ArrayList<>();
		if (SwitchUseFirstOrderDynamic.isChecked())
		{
			if (CheckBoxPI.isChecked()) controlTypes.add(ControlType.PI);
			if (CheckBoxPID.isChecked()) controlTypes.add(ControlType.PID);
		}
		else
		{
			switch (ModelSelected)
			{
				case PI_Controller_Model:
					controlTypes.add(ControlType.PI);
					break;
				case PID_Controller_Model1:
				case PID_Controller_Model2:
					controlTypes.add(ControlType.PID);
					break;
				case P_Controller_Model:
					controlTypes.add(ControlType.P);
					break;
				case PD_Controller_Model:
					controlTypes.add(ControlType.PD);
					break;
			}
		}
		imcMethod.setControlTypes(controlTypes);
		tuningMethods.add(imcMethod);

		// Process the tuning.
		Double pGain      = Parser.GetDouble(EditTextProcessGain.getText().toString());
		Double pTime      = Parser.GetDouble(EditTextProcessTimeConstant.getText().toString());
		Double pDead      = Parser.GetDouble(EditTextProcessTransportDelay.getText().toString());
		Double pLambda    = Parser.GetDouble(EditTextLambdaTuning.getText().toString());
		ArrayList<ControllerParameters> parameters = new ArrayList<>();

		if (SwitchUseFirstOrderDynamic.isChecked())
		{
			for (TuningMethod tuning : tuningMethods)
			{
				for (ControlType controller: tuning.getControlTypes())
				{
					for (ControlProcessType pType : tuning.getControlProcessTypes())
					{
						ControllerParameters cp = IMC
							.Compute(controller, pType, pLambda, pGain, pTime, pDead);
						parameters.add(cp);
					}
				}
				tuning.setParameters(parameters);
			}
		}
		else
		{
			ControllerParameters cp;
			switch (ModelSelected)
			{
				case P_Controller_Model:
					cp = IMC.Compute(
						ControlType.P,
						ControlProcessType.LambdaTuning,
						ModelSelected,
						pLambda,
						pDead,
						pGain,
						pTime,
						pDead);
					parameters.add(cp);
					tuningMethods.get(0).setParameters(parameters);
					break;
				case PD_Controller_Model:
					cp = IMC.Compute(
						ControlType.PD,
						ControlProcessType.LambdaTuning,
						ModelSelected,
						pLambda,
						pDead,
						pGain,
						pTime,
						pDead);
					parameters.add(cp);
					tuningMethods.get(0).setParameters(parameters);
					break;
				case PI_Controller_Model:
					cp = IMC.Compute(
						ControlType.PI,
						ControlProcessType.LambdaTuning,
						ModelSelected,
						pLambda,
						pDead,
						pGain,
						pTime,
						pDead);
					parameters.add(cp);
					tuningMethods.get(0).setParameters(parameters);
					break;
				case PID_Controller_Model1:
					usedParameter = getResources().getString(R.string.SecondTimeConstant);
					cp = IMC.Compute(
							ControlType.PID,
							ControlProcessType.LambdaTuning,
							ModelSelected,
							pLambda,
							pDead,
							pGain,
							pTime,
							pDead);
					parameters.add(cp);
					tuningMethods.get(0).setParameters(parameters);
					break;
				case PID_Controller_Model2:
						usedParameter = getResources().getString(R.string.DampingRatio);
					cp = IMC.Compute(
						ControlType.PID,
						ControlProcessType.LambdaTuning,
						ModelSelected,
						pLambda,
						pDead,
						pGain,
						pTime,
						pDead);
					parameters.add(cp);
					tuningMethods.get(0).setParameters(parameters);
					break;
			}
		}

		// Pass through intent to the next activity the results information.
		Intent resultActivity = new Intent(IMCActivity.this, ResultActivity.class);
		resultActivity.putParcelableArrayListExtra("RESULT", tuningMethods);
		resultActivity.putExtra("Gain", pGain);
		resultActivity.putExtra("Time", pTime);
		resultActivity.putExtra("Dead", pDead);
		resultActivity.putExtra("TitleName", usedParameter);
		startActivity(resultActivity);
	}
}

