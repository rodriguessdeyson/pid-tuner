package com.domain.models.tuning;
import android.os.Parcel;
import android.os.Parcelable;

import com.domain.models.tuning.types.*;

import androidx.annotation.NonNull;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Create a tuning model.
 */
public class TuningModel implements Parcelable
{
	// region Attributes

	/**
	 * Name of the tuning.
	 */
	private String Name;

	/**
	 * Small tuning description.
	 */
	private String Description;

	/**
	 * Indicator of the tuning type.
	 */
	private TuningType Type;

	/**
	 * Transfer function of the tuning.
	 */
	private TransferFunction TransferFunction;

	/**
	 * Turning method configuration.
	 */
	private ArrayList<ProcessType> ProcessTypes;


	//endregion

	//region Constructor

	/**
	 * Initialize a new object of type TuningModel.
	 */
	public TuningModel() {}

	/**
	 * Initialize a new object of type TuningModel.
	 * @param name Tuning method name.
	 * @param description Tuning description.
	 * @param type Type of model.
	 * @param transferFunction Transfer function object.
	 */
	public TuningModel(String name, String description, TuningType type,
					   TransferFunction transferFunction)
	{
		setName(name);
		setDescription(description);
		setType(type);
		setTransferFunction(transferFunction);
	}

	/**
	 * Initialize a new object of type TuningModel.
	 * @param name Tuning method name.
	 * @param description Tuning description.
	 * @param type Type of model.
	 * @param transferFunction Transfer function object.
	 * @param processTypes Set of process types.
	 */
	public TuningModel(String name, String description, TuningType type,
					   TransferFunction transferFunction, ArrayList<ProcessType> processTypes)
	{
		this(name, description, type, transferFunction);
		setProcessTypes(processTypes);
	}

	//endregion

	//region Parcelable

	protected TuningModel(Parcel in) {
		Name = in.readString();
		Description = in.readString();
		Type = in.readParcelable(TuningType.class.getClassLoader());
		TransferFunction = in.readParcelable(com.domain.models.tuning.TransferFunction.class.getClassLoader());
		ProcessTypes = in.createTypedArrayList(ProcessType.CREATOR);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(Name);
		dest.writeString(Description);
		dest.writeParcelable(Type, flags);
		dest.writeParcelable(TransferFunction, flags);
		dest.writeTypedList(ProcessTypes);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<TuningModel> CREATOR = new Creator<TuningModel>() {
		@Override
		public TuningModel createFromParcel(Parcel in) {
			return new TuningModel(in);
		}

		@Override
		public TuningModel[] newArray(int size) {
			return new TuningModel[size];
		}
	};

	//endregion

	//region Methods

	/**
	 * Sets the tuning name.
	 * @param name Tuning name selected.
	 */
	public void setName(String name)
	{
		if (name.trim().isEmpty())
		{
			throw new InvalidParameterException("Name must be not null");
		}

		Name = name;
	}

	/**
	 * Sets the tuning description.
	 * @param description The tuning description.
	 */
	public void setDescription(String description)
	{
		if (description.trim().isEmpty())
			throw new InvalidParameterException("Description must be not null");
		Description = description;
	}

	/**
	 * Sets the tuning type.
	 * @param type The tuning type selected.
	 */
	public void setType(TuningType type)
	{
		Type = type;
	}

	/**
	 * Sets the tuning method configuration.
	 * @param transferFunction Tuning configuration.
	 */
	public void setTransferFunction(TransferFunction transferFunction)
	{
		if (transferFunction == null)
			throw new InvalidParameterException("transferFunction must be not null or empty");

		this.TransferFunction = transferFunction;
	}

	public void setProcessTypes(ArrayList<ProcessType> processTypes)
	{
		ProcessTypes = processTypes;
	}

	/**
	 * Gets the tuning name.
	 * @return Tuning name.
	 */
	public String getName()
	{
		return Name;
	}

	/**
	 * Gets the tuning description.
	 * @return Tuning description.
	 */
	public String getDescription()
	{
		return Description;
	}

	/**
	 * Gets the tuning type.
	 * @return Tuning type.
	 */
	public TuningType getType()
	{
		return Type;
	}

	/**
	 * Gets the tuning configuration process types.
	 * @return Tuning configuration process types.
	 */
	public ArrayList<ProcessType> getProcessTypes()
	{
		return ProcessTypes;
	}

	/**
	 * Gets the transfer function parameters
	 * @return TransferFunction.
	 */
	public TransferFunction getTransferFunction()
	{
		return TransferFunction;
	}

	@NonNull
	@Override
	public String toString()
	{
		return getName();
	}

	//endregion
}
