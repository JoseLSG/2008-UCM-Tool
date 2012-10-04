package Controller.LogIn;

import javax.swing.*;

import Controller.IController;
import Controller.PowerController;
import Model.MapModel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class login_Manager extends JFrame implements ActionListener
{
	private Toolkit toolkit;

	private final int SCREEN_WIDTH = 280;
	private final int SCREEN_HEIGHT = 180;
	
	private final int BUTTON_WIDTH = 80;
	private final int BUTTON_HEIGHT = 20;
	
	private final int BUTTON_X_POS = SCREEN_WIDTH/2 - BUTTON_WIDTH/2;
	private final int BUTTON_Y_POS = SCREEN_HEIGHT/2 - BUTTON_HEIGHT/2;
	
	private JTextField username;
	private JPasswordField password;
	
	private JLabel nameLabel;
	private JLabel passLabel;
	
	private JPanel mainPanel;
	private JPanel loginPanel;
	private JPanel group1Panel;
	private JPanel userPanel;
	private JPanel passPanel;
	
	private JButton login;
	
	private ArrayList userData;
	
	private boolean flag = true;
	private boolean terminated = false;
	
	private String userType;
	
	public login_Manager()	
	{
		userData = XMLReader.read("Controller/LogIn/data.xml");
			
		set_swing_comp();
		
	// Button creation
      	login = new JButton("Login");
      	login.setSize(80, 20);

    	login.addActionListener(this);

	// button creation END 
    	
    	userPanel.add(Box.createRigidArea(new Dimension(20,0)));
    	userPanel.add(nameLabel);
    	userPanel.add(Box.createRigidArea(new Dimension(5,0)));
    	userPanel.add(username);
    	userPanel.add(Box.createRigidArea(new Dimension(20,0)));
    	
    	passPanel.add(Box.createRigidArea(new Dimension(20,0)));
    	passPanel.add(passLabel);
    	passPanel.add(Box.createRigidArea(new Dimension(6,0)));
    	passPanel.add(password);
    	passPanel.add(Box.createRigidArea(new Dimension(20,0)));
    	
    	group1Panel.add(Box.createRigidArea(new Dimension(0,20)));
    	group1Panel.add(userPanel);
    	group1Panel.add(Box.createRigidArea(new Dimension(0,10)));
    	group1Panel.add(passPanel);

    	loginPanel.add(login); // adding to panel the button
    	
    	mainPanel.add(Box.createRigidArea(new Dimension(0,5)));
    	mainPanel.add(group1Panel);
    	mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
    	mainPanel.add(loginPanel);
    	mainPanel.add(Box.createRigidArea(new Dimension(0,20)));
    	
    	
    	add(mainPanel);
    	setVisible(true);
    }	

	private void set_swing_comp(){
		setTitle("Login Manager"); //frame title		
		setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
		setResizable(false);
	
      	toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize(); //Monitor screen size

        setLocation((size.width - getWidth())/2, (size.height - getHeight())/2); // set  at centre of monitor screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
       	
        mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		
       	loginPanel = new JPanel();
       	loginPanel.setLayout(new BoxLayout(loginPanel,BoxLayout.X_AXIS));
    	
     	group1Panel = new JPanel();	
     	group1Panel.setLayout(new BoxLayout(group1Panel,BoxLayout.Y_AXIS));
     	
     	userPanel = new JPanel();	
     	userPanel.setLayout(new BoxLayout(userPanel,BoxLayout.X_AXIS));
     	
     	passPanel = new JPanel();	
     	passPanel.setLayout(new BoxLayout(passPanel,BoxLayout.X_AXIS));
		
		username = new JTextField(10);
		password = new JPasswordField(10);
		
		nameLabel = new JLabel("Username:");		
		passLabel = new JLabel("Password:");
	}
	
	public void actionPerformed(ActionEvent event) {
		
		for (Object s : userData) {
			
	    	if( username.getText().equals( ((String[])s)[0] ) ){
	    		
	    		flag = false;
	    		
	    		if(password.getText().equals( ((String[])s)[1] ) ){		
	    			userType = ((String[])s)[2];
	    			setVisible(false);
	    			terminated = true;
	    			
	    		}
	    		
	    		else{ 
	    			JOptionPane.showMessageDialog(null, "Password Incorect");	    			
	    		}
	    		
	    		break;
	    	} 
	
		}
	
		if(flag){
			JOptionPane.showMessageDialog(null, "username Incorect");
		}
		
		flag = true;

	}//
	
	public String UserType(){ return userType; }
	
	public boolean isTerminated(){ return terminated; }
	

}



