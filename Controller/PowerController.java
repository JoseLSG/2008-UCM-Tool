package Controller;

//Aplication
import javax.swing.JOptionPane;

import Model.*;
import View.*;
import Model.Figures.*;
//import Controller.CompLabel.*;


public class PowerController implements IController
{
	private MapModel _theModel;
	private MainForm _theMainForm;
	 
	public int _x1;
	public int _y1;
	public int _x2;
	public int _y2;
	
	private boolean _isDrawingComp, _isDrawingResp, _isDrawingLine;
	private UCMComponent _currentComp = null;
    private UCMLine _tempLine = null;      // for drawing a line, a line needs 2 responsabilities to be formed, so a temporary variable is required 
        
	public PowerController(MapModel theMapModel)
	{
		_theModel = theMapModel;
		_theModel.initialize();
		_theMainForm = new MainForm(_theModel, this);
		
		_x1 = 0; 
		_y1 = 0; 
		_x2 = 0; 
		_y2 = 0;
		_isDrawingComp = false; 
		_isDrawingResp = false;	
        _isDrawingLine = false;
	}
	
	public void btnCompClick() 	
	{
		_isDrawingComp = true;
	}

	public void btnLineClick(){
        _isDrawingLine = true;
        this._currentComp = null;
        this._tempLine = null;
    }	
        

	public void btnRespClick() 	
	{		
		_isDrawingResp = true;
	}	
	
	public void mouseDragged(int x, int y) 	
	{
		if( this._currentComp != null ){			
            if(_currentComp.getType().compareTo("UCMEndResponsabilities")==0){}
            
            else{
                _currentComp.move(x, y);					
            }
                    
            this._theModel.notifyObservers();
                    
		}
		
		else if( this._tempLine != null ){
            this._tempLine.tempDraw(x, y);
            this._theModel.notifyObservers();
        }
                
	}	

	public void mousePressed(int x, int y) 
	{
		if( this._isDrawingComp == true)
		{			
			_x1 = x;
			_y1 = y;
		}
		else if( this._isDrawingResp == true)
		{
			_x1 = x;
			_y1 = y;
			//UCMResponsabilities newResp = new UCMResponsabilities(null, _x1, _y1);			
			_currentComp = this._theModel.getSelectedUCMComponent(_x1, _y1);
			
			if( _currentComp != null ) 
			{		
				/* Add the new component with his parent specified */				
				_currentComp.Add(new UCMResponsabilities(_currentComp, null, null, _x1, _y1));
				
				this._theModel.getSelectedUCMComponent(x, y)._label = JOptionPane.showInputDialog(null, "Enter Component title : ", 
																		"Component Name", 1);	
				_theModel.notifyObservers();
			}
			else  
			{
				/*CompLabel will be used to add the labels to the components*/
				//CompLabel c1 = new CompLabel();
				this._theModel.addFigure(new UCMResponsabilities(null, null, null, _x1, _y1));
					
				this._theModel.getSelectedUCMComponent(x, y)._label = JOptionPane.showInputDialog(null, "Enter Component title : ", 
							"Component Name", 1);	


			}
			
			clearVariables();
		}
		
        else if( this._isDrawingLine == true ){
             // getting 1st ended of the line
             UCMComponent _tempComp = this._theModel.getSelectedUCMComponent(x, y);
                        
              if( _tempComp != null && (_tempComp.getType().compareTo("UCMResponsabilities")==0 
            		  || _tempComp.getType().compareTo("UCMEndResponsabilities")==0 ) ){
            	  			// should be a responsability to make a line
            	  			_tempLine = new UCMLine(this._theMainForm.panelWork.comp2D);
            	  			_tempLine.Add((UCMResponsabilities) _tempComp);// down casting
            	  			_tempLine.tempDraw(x, y);   // fake drawing   
            	  			this._tempLine.tempDraw(x, y);
            	  			this._theModel.addLine(_tempLine);
            	  			this._theModel.notifyObservers();
                    	
                   }
              
              else{
                      this.clearVariables();
              }
        }
		
		else{								
			_currentComp = this._theModel.getSelectedUCMComponent(x, y);
            // is endResponsabilities?
            if(_currentComp!=null && _currentComp.getType().compareTo("UCMEndResponsabilities")==0){
            	( (UCMEndResponsabilities) _currentComp ).onOff();
            }
          
			this._theMainForm.setCurrentComp(_currentComp);			
		}
	}	

	public void mouseReleased(int x, int y) 
	{
        	
		if( this._isDrawingComp == true)
		{
			_x2 = x;
			_y2 = y;				
			//UCMComposite newComposite = new UCMComposite(null, _x1, _y1, _x2, _y2);		
			_currentComp = this._theModel.getSelectedUCMComponent(_x2, _y2);
			
			if( _currentComp != null ) 
			{
				_currentComp.Add(new UCMComposite(_currentComp, null, null, _x1, _y1, _x2, _y2));
				_theModel.notifyObservers();
				
				this._theModel.getSelectedUCMComponent(_x2, _y2)._label = JOptionPane.showInputDialog(null, "Enter Component title : ", 
																			"Component Name", 1);	

			}
			else 
			{
				this._theModel.addFigure(new UCMComposite(null, null, null, _x1, _y1, _x2, _y2));		
		
				this._theModel.getSelectedUCMComponent(_x2, _y2)._label = JOptionPane.showInputDialog(null, "Enter Component title : ", 
			     																"Component Name", 1);	
			}
			
			clearVariables();
		}
		else if( this._isDrawingResp == true)
		{	
			clearVariables();
		}
		else if( this._isDrawingLine == true)
		{	
            UCMComponent _tempComp;
			if(_tempLine != null){
                  // getting 2nd ended of the line
                  _tempComp = this._theModel.getSelectedUCMComponent(x, y);
                  if(_tempComp != null && (_tempComp.getType().compareTo("UCMResponsabilities")==0 || _tempComp.getType().compareTo("UCMEndResponsabilities")==0 ) ){
                       // should be a responsability to make a line
                       // can we actually connect 2 ENDResponsibilities together?
                               if(_tempLine.res1.getType().compareTo("UCMEndResponsabilities") == 0 && _tempComp.getType().compareTo("UCMEndResponsabilities")==0 ){
                                   this._theModel.removeLine(_tempLine);    // no, we can't      
                               }
                               else{
                                   if( _tempLine.contains((UCMResponsabilities) _tempComp) == false){
                                        _tempLine.Add((UCMResponsabilities) _tempComp);// down casting
                                                     
                                        this._tempLine._label = JOptionPane.showInputDialog(null, "Enter Component title : ", 
        		  								"Component Name", 1);	
                                   }
                                   
                                   else{
                                        this._theModel.removeLine(_tempLine);
                                   }
                               }
                               
                               this._theModel.notifyObservers();
                           }
                  
                           else{
                               this._theModel.removeLine(_tempLine);
                           }
                        }
			
			else{
                 // remove line form list
                 this._theModel.removeLine(_tempLine);
            }
                        
            this.clearVariables();
                            
		}
		else{
            // clearVariables();
        }	

	}
	
	public void clearVariables()
	{
		_x1 = 0;
		_y1 = 0;
		_x2 = 0;
		_y2 = 0;
		this._isDrawingComp = false;
		this._isDrawingResp = false;
        this._isDrawingLine = false;
        _tempLine = null;
		_currentComp = null;
		this._theMainForm.setCurrentComp(null);
	}
	
	public void mouseClicked(int x, int y) 	{}
        
	public void load( String filePath ) {
        Controller.Save.SaveLoadManager.load(filePath);
        _theModel.myComponentList = Controller.Save.Load.myComponentList;
        _theModel.myLineList = Controller.Save.Load.myLineList;
        _theModel.notifyObservers();
	}
	
	public void save( String filePath ){
		Controller.Save.SaveLoadManager.save(filePath, _theModel);
	}
		
	public void delete(UCMComponent theComponent) {
		if( theComponent == null )
		{			
			return;
		}		
		
		UCMComponent parentComp = theComponent.getParentComp();
		UCMComponent CompToDelete = theComponent;
		theComponent = null;
		
		if(CompToDelete instanceof UCMLine)
		{
			this._theModel.removeLine((UCMLine)CompToDelete);
			this._theModel.notifyObservers();
			return;
		}
		
		/*If parent comp is null, the Comp belongs to the main list of figures*/
		if( parentComp == null )
		{
			if(this._theModel.myComponentList.contains(CompToDelete))
			{
				/* Remove it from the main list */
				this._theModel.myComponentList.remove(CompToDelete);
				this._theModel.notifyObservers();
			}
		}
		
		else
		{
			/* If the parent is not null, the figure belongs to a composite */
			UCMComposite parent = (UCMComposite)CompToDelete.getParentComp();
			if( parent.vComponents.contains(CompToDelete))
			{
				parent.vComponents.remove(CompToDelete);
				this._theModel.notifyObservers();
			}
		}
	}

	public void edit() {		
		UCMComponent compToEdit = this._currentComp;
		if(compToEdit == null ) return;

		compToEdit._label = JOptionPane.showInputDialog(null, "Enter Component title : ", 
							"Component Name", 1);
				
		this._theModel.notifyObservers();
	}
	

}
