package com.rad.pidtuner;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rad.pidtuner.database.DataAccess;
import com.rad.pidtuner.methods.CCActivity;
import com.rad.pidtuner.methods.CHRActivity;
import com.rad.pidtuner.methods.IAEActivity;
import com.rad.pidtuner.methods.IMCActivity;
import com.rad.pidtuner.methods.ITAEActivity;
import com.rad.pidtuner.methods.TLActivity;
import com.rad.pidtuner.methods.ZNActivity;
import com.tunings.models.TuningMethod;
import com.tunings.models.TuningType;

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
	private final ArrayList<TuningMethod> TuningMethods = new ArrayList<>();

	/**
	 * Object adapter for list view to show tuning methods.
	 */
	private ArrayAdapter<TuningMethod> TuningAdapter;

	/**
	 * Opens the tables of the methods.
	 */
	private Button ButtonTuningMethodsTables;

	/**
	 * FloatingActionButton to handle the Help event.
	 */
	private FloatingActionButton FBHelp;

	/**
	 * FloatingActionButton to handle the Close event.
	 */
	private FloatingActionButton FBClose;

	/**
	 * Reference to help layout.
	 */
	private View HelpContainer;

	/**
	 * Reference to help layout.
	 */
	private View HelpTables;

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
		FBHelp                    = findViewById(R.id.FloatingActionButtonHelp);
		FBClose                   = findViewById(R.id.FloatingActionButtonClose);
		HelpContainer             = findViewById(R.id.HelpLayout);
		HelpTables                = findViewById(R.id.IncludeTables);
		ButtonTuningMethodsTables = findViewById(R.id.ButtonDetailedTables);

		ListView listView  = findViewById(R.id.ListViewTuningMethods);
		TuningAdapter      = new ArrayAdapter<TuningMethod>(this, android.R.layout.simple_list_item_1,
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
					row = inflater.inflate(R.layout.listview_tunings, parent, false);
				}
				else
					row = convertView;

				// Find the views.
				TextView textViewTuningType = row.findViewById(R.id.TextViewTuningType);
				TextView textViewTuningInfo = row.findViewById(R.id.TextViewTuningInfo);

				// Set text to the correspondent textView.
				TuningMethod tMethods = TuningMethods.get(position);
				textViewTuningType.setText(tMethods.getTuningName());
				textViewTuningInfo.setText(tMethods.getTuningDescription());
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
		// Handle the click to show tables.
		ButtonTuningMethodsTables.setOnClickListener(v ->
		{
			HelpTables.setVisibility(View.VISIBLE);
			IsReturnLocked = true;
		});

		// Handle the click to show help.
		FBHelp.setOnClickListener(v ->
		{
			HelpContainer.setVisibility(View.VISIBLE);
			IsReturnLocked = true;
		});

		// Handle the click to close help.
		FBClose.setOnClickListener( v ->
		{
			if (HelpTables.getVisibility() == View.VISIBLE)
				HelpTables.setVisibility(View.GONE);
			else
			{
				HelpContainer.setVisibility(View.INVISIBLE);
				IsReturnLocked = false;
			}
		});
	}

	/**
	 * Build a list with all tuning methods configuration.
	 */
	private void BuildTuningMethodsList()
	{
		TuningMethods.add(BuildCC());
		TuningMethods.add(BuildCHR());
		TuningMethods.add(BuildIAE());
		TuningMethods.add(BuildITAE());
		TuningMethods.add(BuildIMC());
		// TuningMethods.add(BuildRT());
		TuningMethods.add(BuildZN());
		TuningMethods.add(BuildTL());
		TuningAdapter.addAll(TuningMethods);
	}

	/**
	 * Listener to handle ListView clicks.
	 */
	private final AdapterView.OnItemClickListener OnTuningListClickListener = (av, v, position, id) ->
	{
		// Get the selected device address.
		TuningMethod tuning = (TuningMethod) av.getAdapter().getItem(position);

		OnTuningAdRequest(tuning);
	};

	/**
	 * Method to show an ad when tuning button is clicked.
	 * @param tuning The selected tuning method.
	 */
	private void OnTuningAdRequest(TuningMethod tuning)
	{
		TuningDatabase.Update();
		GoToMethod(tuning);
	}

	/**
	 * Throw the intent to other activity
	 * @param tuning The tuning method to be shown.
	 */
	private void GoToMethod(TuningMethod tuning)
	{
		// Creates an intent object.
		Intent startTuning;

		// Checks which tuning type is.
		switch (tuning.getTuningType())
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
				throw new InvalidParameterException(tuning.toString());
		}
		ActivityOptions options = ActivityOptions.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out);
		startActivity(startTuning, options.toBundle());
	}

	//region Tunings

	/**
	 * Builds the CC method.
	 * @return The TuningMethod
	 */
	private TuningMethod BuildCC()
	{
		TuningMethod t = new TuningMethod();
		t.setTuningType(TuningType.CC);
		t.setTuningName(getResources().getString(R.string.tvCohenCoon));
		t.setTuningDescription(getResources().getString(R.string.tvCohenCoonDesc));
		return t;
	}

	/**
	 * Builds the CHR method.
	 * @return The TuningMethod
	 */
	private TuningMethod BuildCHR()
	{
		TuningMethod t = new TuningMethod();
		t.setTuningType(TuningType.CHR);
		t.setTuningName(getResources().getString(R.string.tvCHR));
		t.setTuningDescription(getResources().getString(R.string.tvCHRDesc));
		return t;
	}

	/**
	 * Builds the IAE method.
	 * @return The TuningMethod
	 */
	private TuningMethod BuildIAE()
	{
		TuningMethod t = new TuningMethod();
		t.setTuningType(TuningType.IAE);
		t.setTuningName(getResources().getString(R.string.tvIAE));
		t.setTuningDescription(getResources().getString(R.string.tvIAEDesc));
		return t;
	}

	/**
	 * Builds the IMC method.
	 * @return The TuningMethod
	 */
	private TuningMethod BuildIMC()
	{
		TuningMethod t = new TuningMethod();
		t.setTuningType(TuningType.IMC);
		t.setTuningName(getResources().getString(R.string.tvIMC));
		t.setTuningDescription(getResources().getString(R.string.tvIMCDesc));
		return t;
	}

	/**
	 * Builds the ITAE method.
	 * @return The TuningMethod
	 */
	private TuningMethod BuildITAE()
	{
		TuningMethod t = new TuningMethod();
		t.setTuningType(TuningType.ITAE);
		t.setTuningName(getResources().getString(R.string.tvITAE));
		t.setTuningDescription(getResources().getString(R.string.tvITAEDesc));
		return t;
	}

	/**
	 * Builds the TL method.
	 * @return The TuningMethod
	 */
	private TuningMethod BuildTL()
	{
		TuningMethod t = new TuningMethod();
		t.setTuningType(TuningType.TL);
		t.setTuningName(getResources().getString(R.string.tvTL));
		t.setTuningDescription(getResources().getString(R.string.tvTLDesc));
		return t;
	}

	/**
	 * Builds the ZN method.
	 * @return The TuningMethod
	 */
	private TuningMethod BuildZN()
	{
		TuningMethod t = new TuningMethod();
		t.setTuningType(TuningType.ZN);
		t.setTuningName(getResources().getString(R.string.tvZN));
		t.setTuningDescription(getResources().getString(R.string.tvZNDesc));
		return t;
	}

	//endregion

	//endregion
}
