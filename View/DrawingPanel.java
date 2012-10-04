package View;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;
import java.util.Vector;

//Application
import Model.*;
import Controller.*;
import Model.Figures.*;

//************************************************************************************
//	Class: 		DrawingPanel
//	Type:  		Concrete
//	Function: 	Class representes the panel where all the figures will be drawn.
//				Implements the Observer Pattern. It is an Observer.
//************************************************************************************


public class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener, IObserver
{
	// serialVersionUID that removes the warning
	private static final long serialVersionUID = 1L;

	// The "graphic" where all figures will be drawn.
	public Graphics2D comp2D = null;	
	
	// List of figures to draw.
	Vector<UCMComponent> myComponentList = new Vector<UCMComponent>();
	
    // list of line
    public Vector<UCMLine> myLineList = new Vector<UCMLine>();
        
	// Component selected.
	UCMComponent _currentComp = null;
		
	// Model used by the application.
	MapModel _theModel;
	
	// Controller for the View.
	IController _theController;
	
	
	// Constructor.
	// Initialize the panel. Needs the MapModel and the Controller
	public DrawingPanel(MapModel theModel, IController theController)
	{		
		_theModel = theModel;
		_theController = theController;
		
		// Add the observer to the list of observers.
		_theModel.addObserver(this);
		
		// Adding events listeners to the panel.
		/* Set focusable allows the panelto get the focus */
		this.setFocusable(true);
		this.addKeyListener(this);
		this.addMouseListener(this);		
		this.addMouseMotionListener(this);
		
	}
	
	// Perform the drawing of every figure.
	public void paintComponent(Graphics comp)
	{			
		// Drawing the background.
		comp2D = (Graphics2D) comp;		
		comp2D.setColor(Color.BLUE);
        comp2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Rectangle2D.Float background = new Rectangle2D.Float(0F, 0F, (
				float)getSize().width, (float)getSize().height);
		comp2D.fill(background);		
		
                
                
        // draw line
        for( int i = 0; i < myLineList.size(); i++)
		{				
			UCMLine l = (UCMLine) myLineList.get(i);
                        
            l.setGraphics2D(comp2D);
			l.draw();
	
            if( l.done() == false ){
            	
               if( l.res1 != null ){
            	   l.res1.drawAsSelected();	// hehe, i love this <3
               }
            }
                        
		}
                        
        int margin = 8;
        float stageWidth = (float)getSize().width - margin;
        float stageHeight = (float)getSize().height - margin;
                
        double perimeter = (stageWidth + stageHeight)*2.0;
        double space = perimeter/_theModel.numOfEndResponsabilities;
        int step = 0;   // 4 steps in total, represent the 4 borders of the main drawing panel
        int curX = margin/2;   // x of ENDresponsability (NOTE: it is NOT a normal Responsabilities, it's an ENDresponsability!!!!)
        int curY = margin/2;   // y of ENDresponsability
                
        // Drawing the figures.
		for( int i = 0; i < myComponentList.size(); i++)
		{			
                        UCMComponent c = myComponentList.get(i);
                        
                        if(c.getType().compareTo("UCMEndResponsabilities") == 0){
                            UCMEndResponsabilities c1 = (UCMEndResponsabilities) c;
                            // need to set new X, Y for this
                            // why? because the main drawing panel can be resized
                            // so these speacial responsabilities should always "stick" to the borders of the main drawing panel
                            c1.move(curX, curY);
                            c1.setBorderType(step);
                            if(step == 0){
                                // top border
                                curX += Math.round(space);
                                if(curX > stageWidth){
                                    step = 1;
                                    curY = (int) Math.round(space - (stageWidth - (curX - Math.round(space))));
                                    curX = Math.round(stageWidth);
                                }
                            }
                            else if(step == 1){
                                // right border
                                curY += Math.round(space);
                                if(curY > stageHeight){
                                    step = 2;
                                    curX = (int) Math.round( stageWidth - ( space - ( stageHeight - ( curY - Math.round(space) ) ) ) );
                                    curY = Math.round(stageHeight);
                                }
                            }
                            else if(step == 2){
                                // bottom border
                                curX -= Math.round(space);
                                if(curX < 0){
                                    step = 3;
                                    curY = (int) Math.round( stageHeight - ( space - ( curX + Math.round(space) ) ) );
                                    curX = margin/2;
                                }
                            }
                            else if(step == 3){
                               curY -= Math.round(space);
                            }
                            else{
                                
                            }
                        }
                        
			c.setGraphics2D(comp2D);
			c.draw(); 			
		}
                
                
                // if a resposability is selected, we also want to highlight all the lines connected to this responsibility
		if(_currentComp != null)
		{
			_currentComp.drawAsSelected();		
			
            // what if whe are selecting a responsability?
            if(_currentComp.getType().compareTo("UCMResponsabilities") == 0 || _currentComp.getType().compareTo("UCMEndResponsabilities") == 0){
            	for( int i = 0; i < myLineList.size(); i++)
            	{				
            		UCMLine l = (UCMLine) myLineList.get(i);
            		if(l.contains((UCMResponsabilities)_currentComp)){  // down casting
            			l.drawAsSelected();
            		}
            	}    
            }
		}
		
	}	
		
	
	// Update method get the state of the map and repaint itself.
	public void update()
	{
		myComponentList = _theModel.getState();		
        myLineList = _theModel.getLines();		
		this.repaint();
	}
	
	// Event when mosuse is pressed
	public void mousePressed(MouseEvent evt) 
	{		
		// Action is passed to the controller.
		_theController.mousePressed(evt.getX(), evt.getY());		
	}

	// Event when mosuse is released
	public void mouseReleased(MouseEvent evt) 
	{	
		_theController.mouseReleased(evt.getX(), evt.getY());		
	}

	// Event when mosuse is dragged
	public void mouseDragged(MouseEvent evt) 
	{		
		_theController.mouseDragged(evt.getX(), evt.getY());	
	}	
			
	// Event when mosuse is clicked
	public void mouseClicked(MouseEvent evt) 
	{				
		_theController.mouseClicked(evt.getX(), evt.getY());
	}

	public void mouseEntered(MouseEvent evt) {}

	public void mouseExited(MouseEvent evt) {}
	
	public void mouseMoved(MouseEvent evt) {}
	
	// Set the selected component, the one that will be draw as selected.
	public void setCurrentComp(UCMComponent theCurrentComp)
	{
		this._currentComp = theCurrentComp;
		this.repaint();
	}

	public void keyPressed(KeyEvent e) 
	{
		/* If user press the delete keyStroke */
		if( e.getKeyCode() == KeyEvent.VK_DELETE)
		{		
			this._theController.delete(_currentComp);	
		}	
	}

	public void keyReleased(KeyEvent e) 
	{
		
	}

	public void keyTyped(KeyEvent e) 
	{
		
	}
	
}
