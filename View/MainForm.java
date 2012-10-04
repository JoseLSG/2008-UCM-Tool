package View;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import static javax.swing.JFileChooser.*;

//Aplication 
import Model.*;
import Controller.*;
import Model.Figures.UCMComponent;
import Controller.Save.UcmFilter;
import java.io.FileReader;//d
import java.io.IOException;//d

//************************************************************************************
//	Class: 		MainForm
//	Type:  		Concrete
//	Function: 	Concrete class use to drawn the main GUI of the application.
//				It contains the menubaa, toolbar and DrawingPanel.
//************************************************************************************

public class MainForm extends JFrame implements ActionListener
{		
	// SerialVersionUID that removes the warning.
	private static final long serialVersionUID = 1L;
	
	// All components of the Menu Bar.
	ImageIcon iconLoad = new ImageIcon("Icon/shape_square.png");
	
	JButton btnComponent = new JButton(new ImageIcon("Icon/composite.png"));
	JButton btnReq = new JButton(new ImageIcon("Icon/resp.png"));
	JButton btnPath = new JButton(new ImageIcon("Icon/line.png"));	
	
	//load,Save,Edit,Help, tutorial. this was a private variable but in order
	//to test main GUI buttons is should be access to automatically press but
	//ton call from TestActuion(buthelp).Comments By DIDER.
	public JMenuItem btnLoad = new JMenuItem("Load");
	public JMenuItem btnSave = new JMenuItem("Save");
	public JMenuItem btnEdit = new JMenuItem("Edit");	
	public JMenuItem btnHelp = new JMenuItem("Help");//d
	public JMenuItem btnTutorial = new JMenuItem("Tutorial");//d
	
	JMenuItem btnEdit_label = new JMenuItem("Edit Label");
	
	public DrawingPanel panelWork = null;	
        
    //file chooser
    JFileChooser chooser = new JFileChooser();
        
	
	// Model used by the application.
	MapModel _theModel;
	
	// Controller use by the application.
	IController _theController;
	
	// Constructor.
	// Specify the MapModel and the Controller fo the View.
	public MainForm(MapModel theModel, IController theController)
	{	
		super("Main form");
		_theModel = theModel;		
		_theController = theController;
		
		// Set the panelWork (where all figures will be drawn)
		panelWork = new DrawingPanel(_theModel, _theController);			
		initializeZwing();			
		
		/* All key strokes will be directed to the drawing panel */
		panelWork.requestFocus();
	}
	
	// Initialize every comonent of the GUI (bars and buttons, etc)
	public void initializeZwing()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(40, 40, 800, 400	);
		
		//*****************************************************************
		//*************************** ToolBar *****************************		
		btnComponent.addActionListener(this);		
		btnReq.addActionListener(this);
		btnPath.addActionListener(this);
		
		JToolBar toolBarArtifacts = new JToolBar(1);	
		toolBarArtifacts.add(btnComponent);
		toolBarArtifacts.add(btnReq);
		toolBarArtifacts.add(btnPath);		
		
		//*****************************************************************
		//*************************** MenuBar *****************************			
		JMenu menuFile = new JMenu("File");
		JMenu menuEdit = new JMenu("Edit");
		JMenu menuHelp = new JMenu("Help");
		
		btnLoad.addActionListener(this);
		btnSave.addActionListener(this);
		btnEdit.addActionListener(this);
		btnHelp.addActionListener(this);
		btnTutorial.addActionListener(this);

		btnEdit_label.addActionListener(this);
		
		JMenuBar menuBarMain = new JMenuBar();
		menuFile.add(btnLoad);
		menuFile.add(btnSave);
		
		menuEdit.add(btnEdit_label);
		
		menuBarMain.add(menuFile);
		menuBarMain.add(menuEdit);
		menuBarMain.add(menuHelp);
		menuHelp.add(btnHelp);
		menuHelp.addSeparator();
		menuHelp.add(btnTutorial);
        //*****************************************************************
        //************************File Chooser*****************************
        chooser.addChoosableFileFilter(new UcmFilter());
        chooser.setAcceptAllFileFilterUsed(false); //accept only ucm extension files
                
		//*****************************************************************		
		BorderLayout bord = new BorderLayout();
		this.setLayout(bord);	
		this.add("North", menuBarMain);
		this.add("West" , toolBarArtifacts);		
		this.add("Center" , panelWork);		
		this.setVisible(true);			
	}
	
	// Catch the action that was performed on the GUI.
	public void actionPerformed(ActionEvent evt) 
	{		
		Object source = evt.getSource();
		if(source == btnLoad)
		{
                    if (chooser.showOpenDialog(btnLoad) == APPROVE_OPTION) 
                        _theController.load(chooser.getSelectedFile().getAbsolutePath());
		}
		else if( source == btnSave)
		{
                    if (chooser.showSaveDialog(btnSave) == APPROVE_OPTION) 
                        _theController.save(chooser.getSelectedFile().getAbsolutePath());
		}
		else if( source == btnEdit)
		{			
		}
		
		else if( source == btnEdit_label)
		{			
			// Get the current component Label and ask user for new Label
			_theController.edit();
		}
		
		else if( source == btnHelp)
		{	
 			try {		
 				JTextArea ta = new JTextArea(50, 50);	
			 	ta.read(new FileReader("//E:\\comp354\\Project_354_v3.0\\HeplTutorial\\help.txt"), null);
 				ta.setEditable(false);			
 				JOptionPane.showMessageDialog(btnHelp, new JScrollPane(ta));		}
 			catch (IOException ioe) {			
 				ioe.printStackTrace();	
 			}
 				
		}
		
		else if( source == btnTutorial)
		{	
			int online=JOptionPane.showInternalConfirmDialog(btnTutorial, 
				"Go to Online Help and Tutorial?", "information",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if( online == 0)
				HelpTutorial.HelpTutorialBrowser.displayURL("http://users.encs.concordia.ca/~sa_husse/HeplTutorial/");
		  
			else if( online==1)
			   HelpTutorial.HelpTutorialBrowser.displayURL("file://E:\\comp354\\Project_354_v2.4\\HeplTutorial\\index.html");
		    
		    else{}
			
		}
		
		else if( source == btnComponent)
		{			
			_theController.btnCompClick();
		}
		else if( source == btnReq)
		{
			_theController.btnRespClick();
		}
		else if( source == btnPath)
		{
			_theController.btnLineClick();
		}
		
		/* All key strokes will be directed to the drawing panel */
		panelWork.requestFocus();
	}	
	
	// Set the Controller for the View.
	public void setController(IController theController)
	{
		_theController = theController;		
	}
	
	// Set the selected Component.
	public void setCurrentComp(UCMComponent theCurrentComp)
	{
		panelWork.setCurrentComp(theCurrentComp);	
	}
	
}
