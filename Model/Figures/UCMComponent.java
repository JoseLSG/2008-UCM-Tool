package Model.Figures;

import java.awt.*;

//************************************************************************************
// Class: 		UCMComponent
// Type:  		Abstract
// Function: 	Abstract class use to represent different components in the map.
//************************************************************************************

public abstract class UCMComponent 
{
	// UCMComponent parent
	public UCMComponent _parentComponent = null;
	
	// Graphics where all Components will be drawn
	public Graphics2D comp2D = null;			
	
	// Border of the figures
	int BORDER = 8;
	
	// Coordinates of the figures
	public int _x1;
	public int _y1;
	public int _x2;
	public int _y2;
	
	public String _label; // Label on top appearing on top of the component drawn
		
	// Default constructor
	public UCMComponent()
	{		
	}
	
	// Pass the Graphics where the component will be drawn
	public UCMComponent(UCMComponent parentComponent, String compName, Graphics2D theComp2D )
	{
		_parentComponent = parentComponent;
		_label = compName;
		comp2D = theComp2D;		
	}
	
	// Set the Graphics where the component willbe drawn
	public void setGraphics2D( Graphics2D newGraphics2D )
	{
		comp2D = newGraphics2D;
	}
	
	// Get the component selected.
	// If no component is selected it returns null.
	public UCMComponent getComponentSelected(int xm, int ym)
	{		
		if( (xm >= _x1) && (xm <= _x2) && ( ym >= _y1) && ( ym <= _y2) )		
			return this;	
		else 
			return null;		
	}
	
	// Move the component to the new coordinates
	public void move( int x1, int y1)
	{
		int tempX1 = this._x1;
		int tempY1 = this._y1;
		
		// New coordinates for the Components, x1 y1 x2 y2.
		this._x1 = x1;
		this._y1 = y1;
		this._x2 = this._x1 + this._x2 - tempX1;
		this._y2 = this._y1 + this._y2 - tempY1;		
	}
	
	/* Return the parent component*/
	public UCMComponent getParentComp()
	{
		return this._parentComponent;		
	}
				
	// Get tje width of the component
	public int getWidth()
	{
		return Math.abs(_x2 - _x1);
	}
	
	// Get the heigh of the component
	public int getHeigh()
	{
		return Math.abs(_y2 - _y1);
	}
		
	// Get the type of the component
	public abstract String getType();
	
	// Draw the component
	public abstract void draw();	
	
	// Draw the component as selected
	public abstract void drawAsSelected();	
	
	// Add a new component inside the component
	public abstract void Add( UCMComponent newComponent );
	
			
}
