package com.rad.pidtuner.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataAccess
{
	//region Attributes

	/**
	 * Local instance of SQLiteDatabase to manipulate the .db file.
	 */
	private SQLiteDatabase SQLiteDatabase;

	/**
	 * Activity context references.
	 */
	private Context AppContext;

	/**
	 * Name of the database to be opened.
	 */
	private String Database;

	//endregion

	//region Constructor

	/**
	 * Initialize an objective of type DatabaseAccess.
	 * @param ctx Activity context.
	 * @param database Database name to use.
	 */
	public DataAccess(Context ctx, String database)
	{
		this.AppContext = ctx;
		this.Database = database;
	}

	//endregion

	//region Methods

	/**
	 * Creates a new SQLite database.
	 */
	public void CreateDatabase()
	{
		// Create the database informed.
		SQLiteDatabase = AppContext
				.openOrCreateDatabase(Database, Context.MODE_PRIVATE, null);

		// Create a table to persist all settings.
		SQLiteDatabase.execSQL(
				"CREATE TABLE IF NOT EXISTS UserSettings(" +
				"Id             INTEGER PRIMARY KEY AUTOINCREMENT," +
				"SameParameters INTEGER NOT NULL," +
				"Gain           INTEGER," +
				"Time           INTEGER," +
				"Transport      INTEGER," +
				"Ku             INTEGER," +
				"Pu             INTEGER," +
				"Lambda         INTEGER," +
				"DecimalPlaces  INTEGER NOT NULL)");

		// Create a table to manipulate add show.
		SQLiteDatabase.execSQL(
				"CREATE TABLE IF NOT EXISTS AdShowed(" +
					"Id             INTEGER PRIMARY KEY AUTOINCREMENT," +
					"ShowedTime     TEXT NOT NULL)");
	}

	/**
	 * Opens an existing database by its name.
	 */
	private void OpenDatabase()
	{
		SQLiteDatabase = AppContext
				.openOrCreateDatabase(Database, Context.MODE_PRIVATE, null);
	}

	/**
	 * Closes an existing opened database.
	 */
	private void CloseDatabase()
	{
		if (SQLiteDatabase.isOpen())
			SQLiteDatabase.close();
	}

	/**
	 * Inserts into opened database a new configuration.
	 * @param configuration A configuration of the device.
	 */
	public void Insert(SettingModel configuration)
	{
		OpenDatabase();
		SQLiteDatabase.execSQL(
			"INSERT INTO UserSettings("                     +
				"SameParameters,"                           +
				"Gain     ,"                                +
				"Time     ,"                                +
				"Transport,"                                +
				"Ku       ,"                                +
				"Pu       ,"                                +
				"Lambda   ,"                                +
				"DecimalPlaces)"                            +
				"VALUES("                                   +
				"'"+ configuration.getSameParameters() +"'," +
				"'"+ configuration.getGain() +"',"          +
				"'"+ configuration.getTime() +"',"          +
				"'"+ configuration.getTransport() +"',"     +
				"'"+ configuration.getKu() +"',"            +
				"'"+ configuration.getPu() +"',"            +
				"'"+ configuration.getLambda() +"',"        +
				"'"+ configuration.getDecimalPlaces() +"')" + ";");
		CloseDatabase();
	}

	/**
	 * Inserts the dateTime that an ad was showed.
	 * @param dateTime Time in text of showing.
	 */
	public void Insert(String dateTime)
	{
		OpenDatabase();
		SQLiteDatabase.execSQL(
			"INSERT INTO AdShowed(" +
				"ShowedTime)"    +
				"VALUES("           +
				"'"+ dateTime +"')" + ";");
		CloseDatabase();
	}

	/**
	 * Updates the dateTime that an ad was showed.
	 */
	public void Update()
	{
		@SuppressLint("SimpleDateFormat")
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		Date date = new Date();
		OpenDatabase();
		SQLiteDatabase.execSQL(
				"UPDATE AdShowed SET " +
						"ShowedTime =" + "'" + dateFormat.format(date) + "'" +
						"WHERE Id =" + "'" + 1 + "'" + ";");
		CloseDatabase();
	}

	/**
	 * Gets the last time an ad was shown
	 * @return THe DateTime of shown;
	 */
	public Date GetTimeLastAdShowed()
	{
		Date lastShowed = null;
		String selectQuery = "SELECT * FROM AdShowed;";

		OpenDatabase();
		Cursor dbCursor = SQLiteDatabase.rawQuery(selectQuery, null);

		// If rows exist, get the values.
		if (dbCursor.getCount() > 0)
		{
			@SuppressLint("SimpleDateFormat")
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			try
			{
				while (dbCursor.moveToNext())
				{
					lastShowed = format.parse(dbCursor.getString(1));
				}
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
		}
		dbCursor.close();
		CloseDatabase();
		return lastShowed;
	}

	/**
	 * Updates an existing configuration to a new configuration.
	 * @param configuration A new configuration.
	 */
	public void Update(SettingModel configuration)
	{
		OpenDatabase();
		SQLiteDatabase.execSQL(
			"UPDATE UserSettings SET " +
				"SameParameters =" + "'" + configuration.getSameParameters() + "'," +
				"Gain           =" + "'" + configuration.getGain()          + "'," +
				"Time           =" + "'" + configuration.getTime()          + "'," +
				"Transport      =" + "'" + configuration.getTransport()     + "'," +
				"Ku             =" + "'" + configuration.getKu()            + "'," +
				"Pu             =" + "'" + configuration.getPu()            + "'," +
				"Lambda         =" + "'" + configuration.getLambda()        + "'," +
				"DecimalPlaces  =" + "'" + configuration.getDecimalPlaces() + "'" +
				"WHERE Id =" + "'" + 1 + "'" + ";");
		CloseDatabase();
	}

	/**
	 * Reads the configurations already in database.
	 * @return A SettingModel configurations.
	 */
	public SettingModel ReadConfiguration()
	{
		// Create an configuration list with all devices configured.
		SettingModel configuration = null;

		// Creates the query to retrieve all devices.
		String selectQuery = "SELECT * FROM UserSettings;";

		// Get all devices configured.
		OpenDatabase();
		Cursor dbCursor = SQLiteDatabase.rawQuery(selectQuery, null);

		// If rows exist, get the values.
		if (dbCursor.getCount() > 0)
		{
			configuration = new SettingModel();
			while(dbCursor.moveToNext())
			{
				configuration.setSameParameters(dbCursor.getInt(1));
				configuration.setGain(dbCursor.getDouble(2));
				configuration.setTime(dbCursor.getDouble(3));
				configuration.setTransport(dbCursor.getDouble(4));
				configuration.setKu(dbCursor.getDouble(5));
				configuration.setPu(dbCursor.getDouble(6));
				configuration.setLambda(dbCursor.getDouble(7));
				configuration.setDecimalPlaces(dbCursor.getInt(8));
			}
		}
		dbCursor.close();
		return configuration;
	}

	//endregion
}
