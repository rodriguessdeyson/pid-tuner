package com.rad.pidtuner.methods.IMC;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.domain.services.katex.Katex;
import com.domain.services.katex.KatexListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rad.pidtuner.R;

import java.security.InvalidParameterException;
import java.util.Locale;

public class BottomSheetDialogIMCModel extends BottomSheetDialogFragment implements KatexListener
{
	/**
	 * Flag to check if a model was selected.
	 */
	private boolean modelSelected = false;

	/**
	 * Listener for the model selection.
	 */
	private IMCModelListener modelListener;

	/**
	 * Set the listener from the parent
	 * @param listener The listener to set.
	 */
	public void setOnModelSelectedListener(IMCModelListener listener)
	{
		this.modelListener = listener;
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

		WebView[] webViewModels = new WebView[5];
		webViewModels[0] = v.findViewById(R.id.WebViewPModelEquation);
		webViewModels[1] = v.findViewById(R.id.WebViewPDModelEquation);
		webViewModels[2] = v.findViewById(R.id.WebViewPIModelEquation);
		webViewModels[3] = v.findViewById(R.id.WebViewPID1ModelEquation);
		webViewModels[4] = v.findViewById(R.id.WebViewPID2ModelEquation);

		setWebView(webViewModels);

		return v;
	}

	/**
	 * Set the web view with models.
	 * @param webViewModels The web view models.
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void setWebView(WebView[] webViewModels)
	{
		String katex_url = "file:///android_asset/katex_function_layout.html";
		Locale locale = Locale.getDefault();
		for (WebView webViewModel : webViewModels)
		{
			Katex katex = new Katex(locale);
			katex.setOnClickListener(this);
			webViewModel.getSettings().setJavaScriptEnabled(true);
			webViewModel.addJavascriptInterface(katex, "Android");
			webViewModel.loadUrl(katex_url);
			webViewModel.setWebViewClient(new WebViewClient() {
				@Override
				public void onPageFinished(WebView view, String url)
				{
					String model = (String)webViewModel.getTag();
					String params = String.format(locale, "updateEquation(Android.setModelEquation('" + "%s" + "'))", model);
					webViewModel.evaluateJavascript(params, null);
					webViewModel.evaluateJavascript("updateTag('" + model + "')", null);
				}
			});
		}
	}

	@Override
	public void onDismiss(@NonNull DialogInterface dialog)
	{
		super.onDismiss(dialog);
		if (!modelSelected && modelListener != null)
		{
			modelListener.onDialogDismissed();
		}
	}

	@Override
	public void onTransferFunctionClick(String model)
	{
		if (modelListener != null)
		{
			modelListener.onModelSelected(model);
			modelSelected = true;
		}
		dismiss();
	}
}
