package com.rad.pidtuner.methods.IMC;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.domain.services.tuning.interfaces.imc.IMCModelListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rad.pidtuner.R;

public class BottomSheetDialogIMCModel extends BottomSheetDialogFragment
{
	private boolean ImageSelected = false; // Flag to check if an image was selected

	private IMCModelListener ImageSelectedListener;

	// Use this method to set the listener from the parent
	public void setOnImageSelectedListener(IMCModelListener listener)
	{
		this.ImageSelectedListener = listener;
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
							 @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.layout_bottom_sheet_imc_model_list, container, false);

		TextView tvModelTitle = v.findViewById(R.id.TextViewIMC);
		TextView tvModels = v.findViewById(R.id.TextViewModels);

		tvModelTitle.setText(R.string.imc_about_title);
		tvModels.setText(R.string.tvModelTitle);

		ImageButton[] imageButtonModels = new ImageButton[5];
		imageButtonModels[0] = v.findViewById(R.id.ImageButtonPModel);
		imageButtonModels[1] = v.findViewById(R.id.ImageButtonPDModel);
		imageButtonModels[2] = v.findViewById(R.id.ImageButtonPIModel);
		imageButtonModels[3] = v.findViewById(R.id.ImageButtonPIDModel);
		imageButtonModels[4] = v.findViewById(R.id.ImageButtonPID2Model);

		for (ImageButton model : imageButtonModels)
		{
			model.setOnClickListener(vv ->
			{
				if (ImageSelectedListener != null)
				{
					ImageSelectedListener.onModelSelected((String) model.getTag());
					ImageSelected = true;
				}
				dismiss();
			});
		}

		return v;
	}

	@Override
	public void onDismiss(@NonNull DialogInterface dialog)
	{
		super.onDismiss(dialog);
		if (!ImageSelected && ImageSelectedListener != null)
		{
			ImageSelectedListener.onDialogDismissed();
		}
	}
}
