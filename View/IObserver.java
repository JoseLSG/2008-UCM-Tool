package View;

//************************************************************************************
//Class: 		IObserver
//Type:  		Interface
//Function: 	Interface fo the Observers. Follows the "Observer pattern".
//				the update method will get the new state of the model.
//************************************************************************************

public interface IObserver 
{
	// Get the new state of the model.
	public void update();
}
