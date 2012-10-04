package Controller;

//Aplication
import Model.*;
import Model.Figures.UCMComponent;
import View.*;

public class CEOController implements IController
{
	private MapModel _theModel;
	
	public MainForm _theMainForm;
	
	public CEOController(MapModel theMapModel)
	{
		_theModel = theMapModel;
		_theModel.initialize();			
		_theMainForm = new MainForm(_theModel, this);;
	}
	

	public void btnCompClick() 	{}

	public void btnLineClick() 	{}	

	public void btnRespClick() 	{}	

	public void mouseClicked(int x, int y) 	{}
	
	public void mouseDragged(int x, int y) 	{}	

	public void mousePressed(int x, int y) 	{}

	public void mouseReleased(int x, int y) {}
	
	public void load( String filePath ) 
	{
        Controller.Save.SaveLoadManager.load(filePath);
        _theModel.myComponentList = Controller.Save.Load.myComponentList;
        _theModel.myLineList = Controller.Save.Load.myLineList;
        _theModel.notifyObservers();
	}
	
	public void save( String filePath ) {
		 Controller.Save.SaveLoadManager.save(filePath, _theModel);
	}

	public void delete(UCMComponent theComponent) {}


	public void edit() 
	{		
	}	

}
