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
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

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
import java.util.HashMap;
import java.util.Map;

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
		controlsEvent();

		// Build a list with all tuning methods.
		buildTuningMethodsList();

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
		listView.setOnItemClickListener(onTuningListClickListener);
	}

	/**
	 * Handle the views control events.
	 */
	private void controlsEvent()
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
	private void buildTuningMethodsList()
	{
		tuningModels.add(buildCHR());
		tuningModels.add(buildCC());
		tuningModels.add(buildIAE());
		tuningModels.add(buildITAE());
		tuningModels.add(buildIMC());
		// TuningMethods.add(BuildRT());
		tuningModels.add(buildTL());
		tuningModels.add(buildZN());
		TuningAdapter.addAll(tuningModels);
	}

	/**
	 * Listener to handle ListView clicks.
	 */
	private final AdapterView.OnItemClickListener onTuningListClickListener = (av, v, position, id) ->
	{
		TuningModel tuningModel = (TuningModel) av.getAdapter().getItem(position);
		onTuningAdRequest(tuningModel);
		goToMethod(tuningModel, v);
	};

	/**
	 * Method to show an ad when tuning button is clicked.
	 * @param tuningModel The selected tuning method.
	 */
	private void onTuningAdRequest(TuningModel tuningModel)
	{
		TuningDatabase.Update();
	}

	/**
	 * Throw the intent to other activity
	 * @param tuningModel The tuning method to be shown.
	 */
	private void goToMethod(TuningModel tuningModel, View view)
	{
		// Create a map to link the tuning type to the corresponding activity class
		Map<TuningType, Class<?>> activityMap = new HashMap<>();
		activityMap.put(TuningType.CC, CCActivity.class);
		activityMap.put(TuningType.CHR, CHRActivity.class);
		activityMap.put(TuningType.IAE, IAEActivity.class);
		activityMap.put(TuningType.IMC, IMCActivity.class);
		activityMap.put(TuningType.ITAE, ITAEActivity.class);
		activityMap.put(TuningType.TL, TLActivity.class);
		activityMap.put(TuningType.ZN, ZNActivity.class);

		// Retrieve the activity class based on the tuning type
		Class<?> activityClass = activityMap.get(tuningModel.getType());

		if (activityClass == null) {
			throw new InvalidParameterException(tuningModel.toString());
		}

		// Inside your onItemClick listener for the ListView
		Intent startTuning = new Intent(TuningActivity.this, activityClass);

		// Get the view you want to transition
		View titleView = view.findViewById(R.id.TextViewTuningType); // 'view' is the clicked item in the ListView

		// Set up the shared element transition
		ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
				TuningActivity.this,
				titleView, // The view to transition
				ViewCompat.getTransitionName(titleView) // The transition name
		);

		// Start the activity with the animation
		startActivity(startTuning, options.toBundle());
	}

	//region Tunings

	/**
	 * Builds the CC method.
	 * @return The TuningMethod
	 */
	private TuningModel buildCC()
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
	private TuningModel buildCHR()
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
	private TuningModel buildIAE()
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
	private TuningModel buildIMC()
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
	private TuningModel buildITAE()
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
	private TuningModel buildTL()
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
	private TuningModel buildZN()
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
