package Model.Figures;

import java.awt.geom.*;
import java.awt.*;
import java.util.Vector;

import javax.swing.JOptionPane;

//************************************************************************************
// Class: 		UCMComposite
// Type:  		Concrete
// Function: 	Represent the Composites components on the map.
//				It follows the "Composite Pattern".
//************************************************************************************

public class UCMComposite extends UCMComponent
{
	// Border of the figures
	int BORDER = 8;	
	
	// The UCMComponents inside the Component.
	public Vector<UCMComponent> vComponents = new Vector<UCMComponent>();
	
	// Keeps a list use to detect wich Component is selected when the user 
	// click on the Graphic
	Vector<UCMComponent> possibleComponents = new Vector<UCMComponent>();
	
	// Constructor
	// Creates a UCMComposite base on the "Graphics" object and the coordinates
	public UCMComposite(UCMComponent parentComponent, String compLabel, Graphics2D theComp2D, 
			int x1, int y1, int x2, int y2)
	{
		super(parentComponent, compLabel, theComp2D);					
		
		this._x1 = x1;
		this._y1 = y1;
		this._x2 = x2;
		this._y2 = y2;
	}
	
	// Draw the UCMComposite object
	public void draw()
	{				
		comp2D.setColor(Color.BLACK);

		// Draw the Composite object.
		this.comp2D.draw( new Rectangle2D.Float(this._x1, this._y1, this._x2 - this._x1, this._y2 - this._y1 ));		


		// Draw Corresponding label
	    if(this._label != null)
	    	this.comp2D.drawString(this._label, this._x1 +4, this._y1 - 2);

		
		
		// Draw all its children.
		for( int i =0; i < vComponents.size() ; i++)
		{
			((UCMComponent) vComponents.get(i)).setGraphics2D(comp2D);
			((UCMComponent) vComponents.get(i)).draw(); 
		}			
	}	
	
	// Get the selected component inside the Composite Recursevly.
	public UCMComponent getComponentSelected(int xm, int ym)
	{		
		UCMComponent selectedComponent;
		UCMComponent tempComponent;
		
		// Add itself to the list of possible components
		if(super.getComponentSelected(xm, ym) != null)
		{
			possibleComponents.add(this);
		
			// All possible children selected.
			for( int i =0; i < vComponents.size() ; i++)
			{
				UCMComponent tempComp = (UCMComponent) vComponents.get(i);			
				if( tempComp.getComponentSelected(xm, ym)!= null )			
					possibleComponents.add(tempComp.getComponentSelected(xm, ym));						
			}
			
			selectedComponent = (UCMComponent) possibleComponents.get(0);
			// Getting the components with less width and height.
			if( possibleComponents.size() > 0)
			{							
				for( int i =0; i < possibleComponents.size() ; i++)
				{
					tempComponent = (UCMComponent) possibleComponents.get(i);
					if( (selectedComponent.getWidth() > tempComponent.getWidth()) && 
							(selectedComponent.getHeigh() > tempComponent.getWidth() ))
					{
						selectedComponent = tempComponent;
					}
				}	
				
				possibleComponents.clear();				
			}
			
			return selectedComponent;
		}
		
		else
		{
			return null;
		}		
		
	}
	
	// Add a UCMComponent to the list of UCMComponents 
	public void Add( UCMComponent newComponent )
	{
		vComponents.add(newComponent);
	}
	
	// Get the type "UCMComposite"
	public String getType()
	{
		return "UCMComposite";	
	}
	
	// Move the Composite to the new position
	public void move( int x1, int y1)
	{
		int tempX1 = this._x1;
		int tempY1 = this._y1;
		
		// New coordinates for the Components, x1 y1 x2 y2.
		this._x1 = x1;
		this._y1 = y1;
		this._x2 = this._x1 + this._x2 - tempX1;
		this._y2 = this._y1 + this._y2 - tempY1;	
		
		// For each children calculates the new coordintes and move them.
		for( int i =0; i < vComponents.size() ; i++)
		{
			UCMComponent tempComp = (UCMComponent) vComponents.get(i);	
			// New coordenates for each child.
			tempComp.move(this._x1 + tempComp._x1 - tempX1 , this._y1 + tempComp._y1 - tempY1);					
		}
		
	}
	
	// Draw the Composite as selected.
	public void drawAsSelected() 
	{
		comp2D.setColor(Color.WHITE);
		comp2D.draw(new Rectangle2D.Float(this._x1 - (BORDER/2), this._y1 - (BORDER/2), 
				this._x2 - this._x1 + BORDER, this._y2 - this._y1 + BORDER));
		
		comp2D.draw(new Rectangle2D.Float((this._x1 + this._x2)/2 - (BORDER / 2), 
				this._y1 - BORDER, BORDER, BORDER));
		
		comp2D.draw(new Rectangle2D.Float((this._x1 + this._x2)/2 - (BORDER / 2), 
				this._y2, BORDER, BORDER));
		
		comp2D.draw(new Rectangle2D.Float(this._x1 - BORDER, 
				(this._y1 + this._y2)/2 - (BORDER / 2), BORDER, BORDER));
				
		comp2D.draw(new Rectangle2D.Float(this._x2, 
				(this._y1 + this._y2)/2 - (BORDER / 2), BORDER, BORDER));			
	}

	
	
}

