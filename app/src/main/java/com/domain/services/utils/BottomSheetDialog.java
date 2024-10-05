package com.domain.services.utils;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rad.pidtuner.R;

public class BottomSheetDialog extends BottomSheetDialogFragment
{
	//region Attributes

	/**
	 * Dialog type. About or Help.
	 */
	private final BottomSheetDialogType DialogType;

	/**
	 * Main Header title of the dialog.
	 */
	private String Title;

	/**
	 * The about/help description.
	 */
	private String Description;

	/**
	 * Link to the external page.
	 */
	private String Link;

	//endregion

	//region Constructor

	/**
	 * Initialize a BottomSheetDialog object for about/help dialog.
	 * @param dialogType Type of dialog.
	 */
	public BottomSheetDialog(BottomSheetDialogType dialogType)
	{
		DialogType = dialogType;
	}


	/**
	 * Initialize a BottomSheetDialog object for about/help dialog.
	 * @param title About title.
	 * @param description About description.
	 * @param link About description.
	 */
	public BottomSheetDialog(String title, String description, String link)
	{
		DialogType = BottomSheetDialogType.About;
		setTitle(title);
		setDescription(description);
		setLink(link);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
							 @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState)
	{
		switch (DialogType)
		{
			case About:
				return aboutView(inflater, container);
			case Help:
				return helpView(inflater, container);
			default:
				return null;
		}
	}

	//endregion

	//region Methods

	public void setTitle(String title)
	{
		this.Title = title;
	}

	public void setDescription(String description)
	{
		this.Description = description;
	}

	public void setLink(String link)
	{
		this.Link = link;
	}


	private View aboutView(LayoutInflater inflater, @Nullable ViewGroup container)
	{
		View v = inflater.inflate(R.layout.layout_bottom_sheet_tuning_about, container, false);

		TextView title = v.findViewById(R.id.TextViewTuningMethodTitle);
		TextView description = v.findViewById(R.id.TextViewTuningMethodDescription);
		TextView link = v.findViewById(R.id.TextViewTuningMethodLink);

		title.setText(Title);
		description.setText(HtmlCompat.fromHtml(Description, HtmlCompat.FROM_HTML_MODE_LEGACY));
		link.setText(HtmlCompat.fromHtml(Link, HtmlCompat.FROM_HTML_MODE_LEGACY));
		link.setMovementMethod(LinkMovementMethod.getInstance());
		return v;
	}

	private View helpView(LayoutInflater inflater, @Nullable ViewGroup container)
	{
		View v = inflater.inflate(R.layout.layout_bottom_sheet_tuning_help, container, false);

		TextView helpTitle = v.findViewById(R.id.TextViewHelpTitle);
		TextView helpIntroduction = v.findViewById(R.id.TextViewHelpIntroduction);
		TextView helpDescription = v.findViewById(R.id.TextViewHelpDescription);
		TextView helpMore = v.findViewById(R.id.TextViewHelpKnowMore);

		helpTitle.setText(getString(R.string.tvHelp));
		helpIntroduction.setText(HtmlCompat.fromHtml(getString(R.string.tvHelpIntroduction), HtmlCompat.FROM_HTML_MODE_LEGACY));
		helpDescription.setText(HtmlCompat.fromHtml(getString(R.string.tvHelpDescription), HtmlCompat.FROM_HTML_MODE_LEGACY));
		helpMore.setText(HtmlCompat.fromHtml(getString(R.string.tvHelpMoreInformation), HtmlCompat.FROM_HTML_MODE_LEGACY));
		helpMore.setMovementMethod(LinkMovementMethod.getInstance());
		return v;
	}

	//endregion
}
