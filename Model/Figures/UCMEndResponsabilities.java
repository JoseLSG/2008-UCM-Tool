package Model.Figures;

import java.awt.geom.*;
import java.awt.*;

//************************************************************************************
// Class: 		UCMResponsabilities
// Type:  		Concrete
// Function: 	Represent the responsabilities components in the map.
//************************************************************************************

public class UCMEndResponsabilities extends UCMResponsabilities
{
	
	// Border of the figures
	int BORDER = 10;		
	private boolean end = false;
    private int borderType = 0;
        
	// Constructor. Create a UCMResponsability base on the coordinates.
	public UCMEndResponsabilities(Graphics2D theComp2D, int x1, int y1)
	{
		super(null, null,theComp2D, x1, y1);		
	}
		
	// Returns the type "UCMResponsabilities"
	public String getType()
	{
		return "UCMEndResponsabilities";	
	}
        
    public void onOff(){
    	end = !end;
    }
        
    public void setBorderType(int i){
    	borderType = i;
    }
               
    public void draw() {	
    	draw(Color.ORANGE);                
	}
        
    public void draw(Color c){
        	
    	comp2D.setColor(c);
        if(end){
                if(borderType == 1){
                    // right
                    this.comp2D.fillRect(_x1+BORDER/2, _y1, BORDER/2, BORDER);
                }
                else if(borderType == 2){
                    // bottom
                    this.comp2D.fillRect(_x1, _y1+BORDER/2, BORDER, BORDER/2);
                }
                else if(borderType == 3){
                    // left
                    this.comp2D.fillRect(_x1, _y1, BORDER/2, BORDER);
                }
                else{
                    this.comp2D.fillRect(_x1, _y1, BORDER, BORDER/2);
                }
                
        }
        else{
                this.comp2D.fillOval(_x1, _y1, BORDER, BORDER);
            }
	}
             
        
    public void move( int x1, int y1){
		// can't move
    	this._x1 = x1 - (BORDER / 2);
        this._y1 = y1 - (BORDER / 2);
        this._x2 = x1 + (BORDER / 2);
        this._y2 = y1 + (BORDER / 2);
	}

	// Draw the responsability as selected.
	public void drawAsSelected() 
	{		
    	draw(Color.CYAN);
	}
	
    public boolean getEnd()
    {
    	return end;
    }
}