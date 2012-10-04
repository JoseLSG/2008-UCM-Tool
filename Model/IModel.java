package Model;

import View.IObserver;

//************************************************************************************
//	Class: 		IModel
//	Type:  		Interface
//	Function: 	Interface for the Model. Follows the "Observer pattern".
//				IModel is the "subject" that will have many observers to notify
//				each time it changes its state
//************************************************************************************

public interface IModel 
{
	// Noify all observers that its state has changed.
	public void notifyObservers();
	
	// Add an observer to the list of observers.
	public void addObserver(IObserver theObserver);
	
	// Remove observers from the list of observers.
	public void removeObserver(IObserver theObserver);
}
