package Model.Figures;

import java.awt.geom.*;
import java.awt.*;

//************************************************************************************
// Class: 		UCMResponsabilities
// Type:  		Concrete
// Function: 	Represent the responsabilities components in the map.
//************************************************************************************

public class UCMResponsabilities extends UCMComponent
{
	// Border of the figures
	int BORDER = 8;		
	
	// Constructor. Create a UCMResponsability base on the coordinates.
	public UCMResponsabilities(UCMComponent parentComponent, String compLabel, Graphics2D theComp2D, 
			int x1, int y1)
	{
		super(parentComponent, compLabel, theComp2D);		
		this._x1 = x1 - (BORDER / 2);
		this._y1 = y1 - (BORDER / 2);
		this._x2 = x1 + (BORDER / 2);
		this._y2 = y1 + (BORDER / 2);
	}
	
	// Draw the responsability on the "Graphic" object.
	public void draw()
	{		
		comp2D.setColor(Color.PINK);
		this.comp2D.draw( new Rectangle2D.Float(this._x1, this._y1 ,this._x2 - this._x1, this._y2 - this._y1 ));			

		if(this._label != null)
			this.comp2D.drawString(this._label, this._x1 , this._y1 - 2);

	}
	
	// Adding UCMCompoments inside responsabilities is not allowed
	public void Add( UCMComponent newComponent ){}
	
	// Returns the type "UCMResponsabilities"
	public String getType()
	{
		return "UCMResponsabilities";	
	}

	// Draw the responsibility as selected.
	public void drawAsSelected() 
	{		
		comp2D.setColor(Color.RED);
		this.comp2D.draw( new Rectangle2D.Float(this._x1, this._y1 ,this._x2 - this._x1, this._y2 - this._y1 ));
	}
	
}