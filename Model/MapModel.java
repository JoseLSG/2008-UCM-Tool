package Model;

import java.util.Vector;

//Application
import Model.Figures.*;
import View.IObserver;

//************************************************************************************
//	Class: 		MapModel
//	Type:  		Concrete
//	Function: 	Concrete class use to represent the Use Case Map.
//				It implments the IModel interface.
//************************************************************************************

public class MapModel implements IModel
{
	// List of Componets that build the map.
	public Vector<UCMComponent> myComponentList ; 
        
    public Vector<UCMLine> myLineList ; 
	
	// List of Observers.
	private Vector<IObserver> _theObservers ; 
	
        public int numOfEndResponsabilities = 40;
        
	// Initialize the the lists.
	public void initialize()
	{
		myComponentList = new Vector<UCMComponent>();
        _theObservers = new Vector<IObserver>();
        myLineList = new Vector<UCMLine>();
                
        int i = 0;
        
        for(i = 0; i < numOfEndResponsabilities; i++){
        	myComponentList.add(new UCMEndResponsabilities(null, 0, 0));
        }
        this.notifyObservers();
	}
	
	// Subscribe an observer to the list of observers
	public void addObserver(IObserver theObserver) 
	{
		_theObservers.add(theObserver);
	}
	
	// Unsubscribe an observer form the list of observers
	public void removeObserver(IObserver theObserver) 
	{
		_theObservers.remove(theObserver);
	}
	
	// Notify observsers that state has changed
	public void notifyObservers() 
	{
		for( IObserver theObserver: _theObservers)
		{
			// Calls the update method on each observer.
			theObserver.update();
		}
	}
	
	// Get the state of the Map. The state is defined by the list
	// of all methods in the map.
	public Vector<UCMComponent> getState()
	{
		return myComponentList;	
	}
	
	// Add a new figure to the map
	public void addFigure( UCMComponent theComponent) 
	{
		
		myComponentList.add(theComponent);
		this.notifyObservers();
	}
        
        
        
        // get all lines
    public Vector<UCMLine> getLines()
	{
		return myLineList;	
	}
        // add new line
    public void addLine( UCMLine theLine) 
	{
		int i = 0;
        int size = myLineList.size();
        boolean found = false;
        
        for(i = 0; i < size; i++){
        	if(myLineList.get(i).equals(theLine)){
        		found = true;
                System.out.println("duplication found!");
                break;
            }
        }
        
        if(!found){
        	myLineList.add(theLine);
        	this.notifyObservers();
        }
		
	}
        // remove line  
    public void removeLine( UCMLine theLine) 
	{
		int i = 0;
        int size = myLineList.size();
		
        for(i = 0; i < size; i++){
        	if( myLineList.get(i) == theLine ){
        		myLineList.remove(i);
                break;
            }
        }
        
        this.notifyObservers();
	}

	// Get the selecte component of the map
	public UCMComponent getSelectedUCMComponent(int coordx, int coordy)
	{
		UCMComponent selectedComponent = null;
		int _xm = coordx;
		int _ym = coordy;
	
		// If there are elements on the map		
		if( myComponentList.size() > 0)
		{						
			for( int i = 0; i < myComponentList.size(); i++)
			{		
				// For each element search if was selected (!= null)
				selectedComponent = (UCMComponent) myComponentList.get(i);
				if( selectedComponent.getComponentSelected(_xm, _ym)!= null)
				{
					selectedComponent = selectedComponent.getComponentSelected(_xm, _ym);
					
					// If the composite returns an element, return it after cleaning variables.
					return selectedComponent;
				}				
			}
		}
                
        // another possiblity: a selected line
        for( int i = 0; i < myLineList.size(); i++){		
        	
			// For each element search if was selected (!= null)
			selectedComponent = (UCMLine) myLineList.get(i);
			
			if( selectedComponent.getComponentSelected(_xm, _ym)!= null)
			{
				selectedComponent = selectedComponent.getComponentSelected(_xm, _ym);
					
				// If the composite returns an element, return it after cleaning variables.
				return selectedComponent;
			}				
        }
                
		
		return null;	
	}

}
