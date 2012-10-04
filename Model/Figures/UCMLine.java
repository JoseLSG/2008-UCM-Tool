/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.Figures;
import java.awt.geom.*;
import java.awt.*;

public class UCMLine extends UCMComponent
{
	// Border of the figures
	int BORDER = 8;	
	
	// The UCMComponents inside the Component.
	public UCMResponsabilities res1;
        public UCMResponsabilities res2;
	
	// Constructor
	// Creates a UCMLine base on the "Graphics" object and the coordinates
	public UCMLine(Graphics2D theComp2D)
	{
		super(null, null, theComp2D);
	}
	
	
        // Draw the UCMComposite object
	public void draw()
	{				
		this.draw(Color.WHITE);
	}	
        
	public void draw(Color c){
		comp2D.setColor(c);
        if(res1 != null){
        	_x1 = (res1._x2 - res1.BORDER/2);
            _y1 = (res1._y2 - res1.BORDER/2);
            
            if(res2 != null){
            	
                  _x2 = (res2._x2 - res2.BORDER/2);
                  _y2 = (res2._y2 - res2.BORDER/2);
                  this.comp2D.drawLine(_x1, _y1, _x2, _y2);
                  
                  if(this._label != null){
     	 				this.comp2D.drawString(this._label, (_x1 + _x2)/2 , ((_y1 + _y2)/2) );
      				}
                  
            }
            
            else{	
                  if(_x2 != 0 && _y2 != 0){          	  
                    this.comp2D.drawLine(_x1, _y1, _x2, _y2); 
                  }
            }
       }
           
   }
        
    public boolean contains(UCMResponsabilities r){
    	return ( (res1 != null && res1 == r) || (res2 != null && res2 == r) );
    }
        
        
    public boolean equals(UCMLine anotherLine){
    	return (this.contains(anotherLine.res1) && this.contains(anotherLine.res2));    
    }
        
	public void tempDraw(int x, int y){
    	this._x2 = x;
    	this._y2 = y;
	}
        
	@Override
	public UCMComponent getComponentSelected(int xm, int ym)
	{		
		if(this.done()==true){
			_x1 = (res1._x2 - res1.BORDER/2);
            _y1 = (res1._y2 - res1.BORDER/2);
            _x2 = (res2._x2 - res2.BORDER/2);
            _y2 = (res2._y2 - res2.BORDER/2);
                
            int tolerant = 10;
            
            if( Math.abs( distanceToSegment(xm, ym, _x1, _y1, _x2, _y2) ) <= tolerant ){
            	return this;
            }
        }
            
        return null;
            
	}
           
	public void Add( UCMResponsabilities r )
	{
		if(res1 == null){
			res1 = r;
        	_x1 = res1._x1;
        	_y1 = res1._y1;
		}
		
		else if(res2 == null){
			if(res1 != res2){
        	res2 = r;
        	_x1 = res2._x1;
        	_y1 = res2._y1;
			}
        }
		
		else{
                // nothing null here... why borther to add?...
            }
	}
        	
	// Get the type "UCMComposite"
	public String getType()
	{
		return "UCMLine";	
	}
	
	public boolean done(){
		return (res1 != null && res2 != null);
	}
	
	// Draw the Composite as selected.
	public void drawAsSelected() 
	{
		this.draw(Color.CYAN);
	}

        
        
        
        
        @Override
        public void Add(UCMComponent newComponent) {
        
        }

	
        /**
         * Wrapper function to accept the same arguments as the other examples
         * 
         * @param x3
         * @param y3
         * @param x1
         * @param y1
         * @param x2
         * @param y2
         * @return
         */
        public static double distanceToSegment(double x3, double y3, double x1, double y1, double x2, double y2) {
            final Point2D p3 = new Point2D.Double(x3, y3);
            final Point2D p1 = new Point2D.Double(x1, y1);
            final Point2D p2 = new Point2D.Double(x2, y2);
            return distanceToSegment(p1, p2, p3);
        }

        /**
         * Returns the distance of p3 to the segment defined by p1,p2;
         * 
         * @param p1
         *                First point of the segment
         * @param p2
         *                Second point of the segment
         * @param p3
         *                Point to which we want to know the distance of the segment
         *                defined by p1,p2
         * @return The distance of p3 to the segment defined by p1,p2
         */
        public static double distanceToSegment(Point2D p1, Point2D p2, Point2D p3) {

            final double xDelta = p2.getX() - p1.getX();
            final double yDelta = p2.getY() - p1.getY();

            if ((xDelta == 0) && (yDelta == 0)) {
                throw new IllegalArgumentException("p1 and p2 cannot be the same point");
            }

            final double u = ((p3.getX() - p1.getX()) * xDelta + (p3.getY() - p1.getY()) * yDelta) / (xDelta * xDelta + yDelta * yDelta);

            final Point2D closestPoint;
            if (u < 0) {
                closestPoint = p1;
            } else if (u > 1) {
                closestPoint = p2;
            } else {
                closestPoint = new Point2D.Double(p1.getX() + u * xDelta, p1.getY() + u * yDelta);
            }

            return closestPoint.distance(p3);
        }
	
}