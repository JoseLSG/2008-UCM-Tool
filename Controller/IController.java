package Controller;

import Model.Figures.UCMComponent;

//************************************************************************************
//Class: 		IController
//Type:  		Interface
//Function: 	Interface for the different kind of controllers.
//				Use the "Strategy Pattern" to define different behaviour.
//************************************************************************************



public interface IController 
{
	// Methods are very straight forward.
	
	public void btnCompClick();
	public void btnRespClick();
	public void btnLineClick();
	
	public void load( String filePath );
	public void save( String filePath );
	public void edit( );
	public void delete(UCMComponent theComponent);
	
	public void mousePressed(int x, int y);
	public void mouseReleased(int x, int y);
	
	public void mouseDragged(int x, int y);
	public void mouseClicked(int x, int y);	
	
}
