package Controller;

import javax.swing.JOptionPane;
import Model.MapModel;
import Model.Figures.UCMComponent;
import View.MainForm;


public class MarketingController implements IController
{	
	private MapModel _theModel;
	private MainForm _theMainForm;
	private UCMComponent _currentComp = null;
	
	public MarketingController(MapModel theMapModel)
	{
		_theModel = theMapModel;
		_theModel.initialize();			
		_theMainForm = new MainForm(_theModel, this);;
	}

	public void btnCompClick() 	{}

	public void btnLineClick()  {}

	public void btnRespClick() {}

	public void mouseClicked(int x, int y)
	{
		_currentComp = this._theModel.getSelectedUCMComponent(x, y);	
		_theMainForm.setCurrentComp(_currentComp);
	}

	public void mouseDragged(int x, int y) {}

	public void mousePressed(int x, int y) {}

	public void mouseReleased(int x, int y) {}

	public void load( String filePath ) 
	{
        Controller.Save.SaveLoadManager.load(filePath);
        _theModel.myComponentList = Controller.Save.Load.myComponentList;
        _theModel.myLineList = Controller.Save.Load.myLineList;
        _theModel.notifyObservers();
	}
	
	public void save(String filePath) {
		 Controller.Save.SaveLoadManager.save(filePath, _theModel);
	}

	public void delete(UCMComponent theComponent) {}

	public void edit() 
	{
		
		UCMComponent compToEdit = this._currentComp;
		if(compToEdit == null ) return;

		compToEdit._label = JOptionPane.showInputDialog(null, "Enter Component title : ", 
				"Component Name", 1);
				
		// Notify Observers of change
		this._theModel.notifyObservers();
		
		// After performing editing clean the current component.
		_currentComp = null;
		_theMainForm.setCurrentComp(null);
		
	}


}
