package com.rad.pidtuner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.domain.models.tuning.TuningModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rad.pidtuner.database.DataAccess;
import com.rad.pidtuner.methods.CCActivity;
import com.rad.pidtuner.methods.CHRActivity;
import com.rad.pidtuner.methods.IAEActivity;
import com.rad.pidtuner.methods.IMC.IMCActivity;
import com.rad.pidtuner.methods.ITAEActivity;
import com.rad.pidtuner.methods.TLActivity;
import com.rad.pidtuner.methods.ZNActivity;
import com.domain.services.utils.BottomSheetDialog;
import com.domain.services.utils.BottomSheetDialogType;
import com.domain.models.tuning.types.TuningType;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Allows to create an object of type TuningActivity to shown tuning methods.
 */
public class TuningActivity extends AppCompatActivity
{
	//region Attributes

	/**
	 * Database reference.
	 */
	private DataAccess TuningDatabase;

	/**
	 * List with all available tuning methods.
	 */
	private final ArrayList<TuningModel> tuningModels = new ArrayList<>();

	/**
	 * Object adapter for list view to show tuning methods.
	 */
	private ArrayAdapter<TuningModel> TuningAdapter;

	/**
	 * FloatingActionButton to handle the Help event.
	 */
	private FloatingActionButton FBHelp;

	/**
	 * Lock for avoid returning when help is opened.
	 */
	private boolean IsReturnLocked = false;

	//endregion

	//region Constructors

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tuning);

		// Configures the database
		ConfigureDatabase();

		// Find the Views references.
		StartViewContents();

		// Handle the events.
		ControlsEvent();

		// Build a list with all tuning methods.
		BuildTuningMethodsList();

		getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
			@Override
			public void handleOnBackPressed() {
				if (IsReturnLocked)
					return;
				finish();
			}
		});
	}

	//endregion

	//region Methods

	/**
	 * Configures the database server
	 */
	private void ConfigureDatabase()
	{
		// Creates the database.
		TuningDatabase = new DataAccess(this, "Tuner");
	}

	/**
	 * Initialize the views.
	 */
	private void StartViewContents()
	{
		FBHelp             = findViewById(R.id.FloatingActionButtonHelp);
		ListView listView  = findViewById(R.id.ListViewTuningMethods);
		TuningAdapter      = new ArrayAdapter<TuningModel>(this, android.R.layout.simple_list_item_1,
				0)
		{
			@NonNull @Override
			public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
			{
				View row;
				if (convertView == null)
				{
					LayoutInflater inflater = (LayoutInflater)getContext()
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					row = inflater.inflate(R.layout.layout_listview_model, parent, false);
				}
				else
					row = convertView;

				// Find the views.
				TextView textViewTuningType = row.findViewById(R.id.TextViewTuningType);
				TextView textViewTuningInfo = row.findViewById(R.id.TextViewTuningInfo);

				// Set text to the correspondent textView.
				TuningModel tMethods = tuningModels.get(position);
				textViewTuningType.setText(tMethods.getName());
				textViewTuningInfo.setText(tMethods.getDescription());
				return row;
			}
		};
		listView.setAdapter(TuningAdapter);
		listView.setOnItemClickListener(OnTuningListClickListener);
	}

	/**
	 * Handle the views control events.
	 */
	private void ControlsEvent()
	{
		// Handle the click to show help.
		FBHelp.setOnClickListener(v ->
		{
			BottomSheetDialog bottomSheet = new BottomSheetDialog(BottomSheetDialogType.Help);
			bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");
		});
	}

	/**
	 * Build a list with all tuning methods configuration.
	 */
	private void BuildTuningMethodsList()
	{
		tuningModels.add(BuildCHR());
		tuningModels.add(BuildCC());
		tuningModels.add(BuildIAE());
		tuningModels.add(BuildITAE());
		tuningModels.add(BuildIMC());
		// TuningMethods.add(BuildRT());
		tuningModels.add(BuildTL());
		tuningModels.add(BuildZN());
		TuningAdapter.addAll(tuningModels);
	}

	/**
	 * Listener to handle ListView clicks.
	 */
	private final AdapterView.OnItemClickListener OnTuningListClickListener = (av, v, position, id) ->
	{
		// Get the selected device address.
		TuningModel tuningModel = (TuningModel) av.getAdapter().getItem(position);

		OnTuningAdRequest(tuningModel);
	};

	/**
	 * Method to show an ad when tuning button is clicked.
	 * @param tuningModel The selected tuning method.
	 */
	private void OnTuningAdRequest(TuningModel tuningModel)
	{
		TuningDatabase.Update();
		GoToMethod(tuningModel);
	}

	/**
	 * Throw the intent to other activity
	 * @param tuningModel The tuning method to be shown.
	 */
	private void GoToMethod(TuningModel tuningModel)
	{
		// Creates an intent object.
		Intent startTuning;

		// Checks which tuning type is.
		switch (tuningModel.getType())
		{
			case CC:
				startTuning = new Intent(TuningActivity.this, CCActivity.class);
				break;
			case CHR:
				startTuning = new Intent(TuningActivity.this, CHRActivity.class);
				break;
			case IAE:
				startTuning = new Intent(TuningActivity.this, IAEActivity.class);
				break;
			case IMC:
				startTuning = new Intent(TuningActivity.this, IMCActivity.class);
				break;
			case ITAE:
				startTuning = new Intent(TuningActivity.this, ITAEActivity.class);
				break;
			case TL:
				startTuning = new Intent(TuningActivity.this, TLActivity.class);
				break;
			case ZN:
				startTuning = new Intent(TuningActivity.this, ZNActivity.class);
				break;
			default:
				throw new InvalidParameterException(tuningModel.toString());
		}
		startActivity(startTuning);
	}

	//region Tunings

	/**
	 * Builds the CC method.
	 * @return The TuningMethod
	 */
	private TuningModel BuildCC()
	{
		TuningModel t = new TuningModel();
		t.setType(TuningType.CC);
		t.setName(getResources().getString(R.string.tvCohenCoon));
		t.setDescription(getResources().getString(R.string.tvCohenCoonDesc));
		return t;
	}

	/**
	 * Builds the CHR method.
	 * @return The TuningMethod
	 */
	private TuningModel BuildCHR()
	{
		TuningModel t = new TuningModel();
		t.setType(TuningType.CHR);
		t.setName(getResources().getString(R.string.tvCHR));
		t.setDescription(getResources().getString(R.string.tvCHRDesc));
		return t;
	}

	/**
	 * Builds the IAE method.
	 * @return The TuningMethod
	 */
	private TuningModel BuildIAE()
	{
		TuningModel t = new TuningModel();
		t.setType(TuningType.IAE);
		t.setName(getResources().getString(R.string.tvIAE));
		t.setDescription(getResources().getString(R.string.tvIAEDesc));
		return t;
	}

	/**
	 * Builds the IMC method.
	 * @return The TuningMethod
	 */
	private TuningModel BuildIMC()
	{
		TuningModel t = new TuningModel();
		t.setType(TuningType.IMC);
		t.setName(getResources().getString(R.string.tvIMC));
		t.setDescription(getResources().getString(R.string.tvIMCDesc));
		return t;
	}

	/**
	 * Builds the ITAE method.
	 * @return The TuningMethod
	 */
	private TuningModel BuildITAE()
	{
		TuningModel t = new TuningModel();
		t.setType(TuningType.ITAE);
		t.setName(getResources().getString(R.string.tvITAE));
		t.setDescription(getResources().getString(R.string.tvITAEDesc));
		return t;
	}

	/**
	 * Builds the TL method.
	 * @return The TuningMethod
	 */
	private TuningModel BuildTL()
	{
		TuningModel t = new TuningModel();
		t.setType(TuningType.TL);
		t.setName(getResources().getString(R.string.tvTL));
		t.setDescription(getResources().getString(R.string.tvTLDesc));
		return t;
	}

	/**
	 * Builds the ZN method.
	 * @return The TuningMethod
	 */
	private TuningModel BuildZN()
	{
		TuningModel t = new TuningModel();
		t.setType(TuningType.ZN);
		t.setName(getResources().getString(R.string.tvZN));
		t.setDescription(getResources().getString(R.string.tvZNDesc));
		return t;
	}

	//endregion

	//endregion
}
