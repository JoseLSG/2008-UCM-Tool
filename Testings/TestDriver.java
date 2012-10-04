/*
 * Created on 11-Dec-08
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package Testings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Model.Figures.*;
import Model.*;
import Controller.*;
import Controller.LogIn.*;
import Controller.Save.*;
import View.*;

import java.awt.*;

public class TestDriver
{

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{		
		
		testMove();  // Juan
		System.out.println("\n\n");
		
		testSave();  // Ryo
		System.out.println("\n\n");
				
		testDrawLine(); // Kien
		System.out.println("\n\n");
		
		TestButtonPerformed();//Dider
		System.out.println("\n\n");
		
		testLogIn(); // Jose
		System.out.println("\n\n");
		
	}
	
	
	// Test move on Components, Responsabilities and Composites.
	public static void testMove()
	{
		// Setting Composite and REsponsabilities.
		UCMComposite theComposite = new UCMComposite(null, null, null, 0, 0, 100, 100);		
		UCMResponsabilities theResponsability1 = new UCMResponsabilities(theComposite, null, null, 20,20);
		UCMComposite theCompositeChild         = new UCMComposite(theComposite, null, null, 50, 50, 70, 70);		
		
		// Adding responsabilities to Composite.
		theComposite.Add( theResponsability1 );
		theComposite.Add( theCompositeChild );
		
		System.out.println("-------------------------------------------------------------------");
		System.out.println("--------------------------  Testing move. -------------------------");
		System.out.println("-------------------------------------------------------------------");
		
		int x2 = 200;
		int y2 = 200;
				
		// Moving the UCMComposite.
		theComposite.move(x2, y2);
		
		/* ----------------------------->  TEST 1  <--------------------------------
		 * New coordinates for Composite should be:
		 * _x1 = 200,  _y1 = 200,  _x2 = 300,  _y2 = 300		 * 
		 */
		
		System.out.println("\nTest 1.1: Check new coordiantes for parent Composite");
		if( theComposite._x1 == 200 && theComposite._y1== 200 
				&& theComposite._x2 == 300 && theComposite._y2 == 300)
		{
			System.out.println("\tTest is succesfull ");
		}
		else
		{
			System.out.println("\tTest failed");		
		}
		
		
		/* ----------------------------->  TEST 2  <--------------------------------
		 * New coordinates for Responsability child should be:
		 * _x1 = 216,  _y1 = 216,  _x2 = 224,  _y2 = 224		 * 
		 */
		
		System.out.println("\nTest 1.2: Check coordiantes for children UCMResponsability");
		UCMResponsabilities child1 = (UCMResponsabilities) theComposite.vComponents.get(0);
		if( child1._x1 == 216 && child1._y1== 216 
				&& child1._x2 == 224 && child1._y2 == 224)
		{
			System.out.println("\tTest is succesfull ");
		}
		else
		{
			System.out.println("\tTest failed");		
		}
		
		
		/* ----------------------------->  TEST 3  <--------------------------------
		 * New coordinates for Composite child should be:
		 * _x1 = 250,  _y1 = 250,  _x2 = 270,  _y2 = 270		 * 
		 */
		
		System.out.println("\nTest 1.3: Check coordiantes for children UCMComposite");
		UCMComposite child2 = (UCMComposite) theComposite.vComponents.get(1);
		if( child2._x1 == 250 && child2._y1 == 250 
				&& child2._x2 == 270 && child2._y2 == 270)
		{
			System.out.println("\tTest is succesfull ");
		}
		else
		{
			System.out.println("\tTest failed");		
		}
		
	}
		
	public static void testDrawLine()
	{
		Graphics2D gp = new MyGraphic();

            
            
		UCMLine myLine;
		int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
		UCMResponsabilities res1;
		UCMResponsabilities res2;
            
		System.out.println("-------------------------------------------------------------------");
		System.out.println("--------------------------  Testing DrawLine ---------------");
		System.out.println("-------------------------------------------------------------------\n");
    		
        System.out.println("-------------------------------------------------------------------");
	    System.out.println("--------------------------  Testing case 1 ------------------------");
	    System.out.println("-------------------------------------------------------------------");
        myLine = new UCMLine( gp );
        x1 = 10; x2 = 20;
        y1 = 10; y2 = 20;
        res1 = new UCMResponsabilities(null, "res1", null, x1, y1);
        res2= new UCMResponsabilities(null, "res2", null, x2, y2);
        myLine.Add(res1);
        myLine.Add(res2);
        myLine.draw();
        if(myLine._x1 == x1 && myLine._x2 == x2 && myLine._y1 == y1 && myLine._y2 == y2){
        	System.out.println("a line is drawn between 2 points: ("+myLine._x1+", "+myLine._y1+") and ("+myLine._x2+", "+myLine._y2+")");
        	System.out.println("--------------------------  Test successful ------------------------");
                
        }
        else{
        	System.out.println("test case fail");
        }
            
            
        System.out.println("-------------------------------------------------------------------");
	    System.out.println("--------------------------  Testing case 2 ------------------------");
	    System.out.println("-------------------------------------------------------------------");
	    myLine = new UCMLine( gp );
	    x1 = 10; x2 = 5;
        y1 = 10; y2 = 5;
        res1 = new UCMResponsabilities(null, "res1", null, x1, y1);
        myLine.Add(res1);
        myLine.tempDraw(x2, y2);
        myLine.draw();
        if(myLine._x1 == x1 && myLine._x2 == x2 && myLine._y1 == y1 && myLine._y2 == y2){
        	System.out.println("a line is drawn between a point: ("+myLine._x1+", "+myLine._y1+") and mouse coordinators ("+myLine._x2+", "+myLine._y2+")");
        	System.out.println("--------------------------  Test successful ------------------------");      
        }
        
        else{
        	System.out.println("test case fail");
        }
            
        System.out.println("-------------------------------------------------------------------");
	    System.out.println("--------------------------  Testing case 3 ------------------------");
	    System.out.println("-------------------------------------------------------------------");
        myLine = new UCMLine( gp );
        x1 = 10; x2 = 0;
        y1 = 10; y2 = 0;
            
        res1 = new UCMResponsabilities(null, "res1", null, x1, y1);
        myLine.Add(res1);
        myLine.draw();
            
        if(myLine._x1 == x1 && myLine._x2 == x2 && myLine._y1 == y1 && myLine._y2 == y2){
        	System.out.println("nothing is drawn");
            System.out.println("--------------------------  Test successful ------------------------");
                
        }
        else{
                System.out.println("test case fail");
            }
            
            
        System.out.println("-------------------------------------------------------------------");
	    System.out.println("--------------------------  Testing case 4 ------------------------");
	    System.out.println("-------------------------------------------------------------------");
        myLine = new UCMLine( gp );
        x1 = 0; 
        x2 = 0;
        y1 = 0; 
        y2 = 0;
        
        myLine.draw();
        
        if(myLine._x1 == x1 && myLine._x2 == x2 && myLine._y1 == y1 && myLine._y2 == y2){
        	System.out.println("nothing is drawn");
            System.out.println("--------------------------  Test successful ------------------------");
                
        }
        
        else{
        	System.out.println("test case fail");
        }
    
	}
	
	public static void testLogIn(){
		
		// This class has the method to be tested from the original program
		// with 
		class AV extends JFrame implements ActionListener{
			public ArrayList <String[]>userData = new ArrayList(); 
			public JTextField username = new JTextField();
			public JPasswordField password = new JPasswordField();
			public boolean flag = true;
			public boolean terminated = false;
			public String userType;
			
			
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
		}
		
		
		AV Test_Object = new AV();
		Test_Object.setVisible(false);
		
		System.out.println("-------------------------------------------------------------------");
		System.out.println("--------------------------  Testing actionPerformed ---------------");
		System.out.println("-------------------------------------------------------------------");
		
		// ----------- Test case 1 -----------
		System.out.println("Test Case 1 : Username = \"???\", Password = \"123\", userData {\"Jose\",\"123\",\"CEO\"}");
		
		Test_Object.userData.add(new String[]{"Jose", "123","CEO"});

		Test_Object.username.setText("???");
		Test_Object.password.setText("123");
		
		Test_Object.actionPerformed(null);
		
		if (Test_Object.userType == null && Test_Object.terminated == false)
			System.out.println("\tTest Successful");
		else
			System.out.println("\tTest Failure");
		
		System.out.println("\n");
		
		
		// ----------- Test case 2------------
		System.out.println("Test Case 2 : Username = \"Jose\", Password = \"???\", userData {\"Jose\",\"123\",\"CEO\"}");
				
		Test_Object.userData.clear();
		Test_Object.userData.add(new String[]{"Jose", "123","CEO"});

		Test_Object.username.setText("Jose");
		Test_Object.password.setText("???");
		
		Test_Object.actionPerformed(null);
		
		if (Test_Object.userType == null && Test_Object.terminated == false)
			System.out.println("\tTest Successful");
		else
			System.out.println("\tTest Failure");
		
		Test_Object.actionPerformed(null);
		
		System.out.println("\n");
		
		// --------- Test case 3--------------
		System.out.println("Test Case 3 : Username = \"Jose\", Password = \"123\", userData {\"Jose\",\"123\",\"CEO\"}");
				
		Test_Object.userData.clear();
		Test_Object.userData.add(new String[]{"Jose", "123","CEO"});

		Test_Object.username.setText("Jose");
		Test_Object.password.setText("123");
		
		Test_Object.actionPerformed(null);
		
		if (Test_Object.userType == "CEO" && Test_Object.terminated == true)
			System.out.println("\tTest Successful");
		else
			System.out.println("\tTest Failure");
	
		System.out.println("\n");
				
		System.exit(0);
	}
	
	public static void testSave()
    {
        UCMResponsabilities test1;
        UCMEndResponsabilities test2;
        UCMEndResponsabilities test3;
        
        Vector<Integer>_IDs = new Vector<Integer>();

        //System.out.println("Test Case Number\tinput(if any)\tBranch Taken\tStateTaken\toutput(or expected result)");
        System.out.println("------------------------- Test writeResponsibilities " +
                "------------------------------------------------------------");
        Save.setIDs(_IDs);
        _IDs.add(1);
        
        test1 = new UCMResponsabilities(null, "UCMResponsabilities", null, 1, 2);
        System.out.println("\n\t============================== Test1 ================================");
        Save.writeResponsibility(test1);
        System.out.println("\t\t\t\tTest Successful");
        System.out.println("\t============================== End_ ================================\n");
        System.out.println();
        
        _IDs.add(3);
        test2 = new UCMEndResponsabilities(null, 1, 2);
        System.out.println("\t============================== Test2 ================================");
        Save.writeResponsibility(test2);
        System.out.println("\t\t\t\tTest Successful");
        System.out.println("\t============================== End_ ================================\n");
        System.out.println();
        
        test3 = new UCMEndResponsabilities(null, 1, 2);
        test3.onOff();
        System.out.println("\t============================== Test3 ================================");
        Save.writeResponsibility(test3);
        System.out.println("\t\t\t\tTest Successful");
        System.out.println("\t============================== End_ ================================\n");
        System.out.println("-------------------------- End Test writeResponsibilities " +
                "--------------------------------------------------------");
        
    }
	
	public static void TestButtonPerformed()
	{		
		MapModel theModel = new MapModel();
		IController theController = new CEOController(theModel);
		
		//ActionEvent TESTevt = new ActionEvent(null,0,"");
		MainForm mf = ((CEOController)theController)._theMainForm;
		
		//mf.btnHelp = null; //if we change btnHelp to public then a programmer can change to NUll which wont work. so programmer have to be carefumm this.
		//ActionEvent TESTevt = new ActionEvent(null, 0, "");
		//test cases
		for(int i=1;i<6;i++){
			//test case # 1	
			if(i==1){
				ActionEvent TESTevt = new ActionEvent(mf.btnHelp, 0, "");
				TESTevt.setSource(mf.btnHelp);
				mf.actionPerformed(TESTevt);
				System.out.println("---------------------Test Case 1 Success:Help Button is pressed---------------------");
		
			}
	
			//test case # 2
			if(i==5){
				ActionEvent TESTevt1 = new ActionEvent(mf.btnEdit, 0, "");
				TESTevt1.setSource(mf.btnEdit);
				mf.actionPerformed(TESTevt1);}
			System.out.println("---------------------Test Case 2 Success:EDIT Button is pressed---------------------");
			//test case # 3
			if(i==2){
				ActionEvent TESTevt2 = new ActionEvent(mf.btnLoad, 0, "");
				TESTevt2.setSource(mf.btnLoad);
				mf.actionPerformed(TESTevt2);}
			System.out.println("---------------------Test Case 3 Success:LOAD Button is pressed---------------------");
			//test case # 4
			if(i==3){
				ActionEvent TESTevt3 = new ActionEvent(mf.btnSave, 0, "");
				TESTevt3.setSource(mf.btnSave);
				mf.actionPerformed(TESTevt3);}
			System.out.println("---------------------Test Case 4 Success:Save Button is pressed---------------------");
			//test case # 5
			if(i==4){
				ActionEvent TESTevt4 = new ActionEvent(mf.btnTutorial, 0, "");
				TESTevt4.setSource(mf.btnTutorial);
				mf.actionPerformed(TESTevt4);}
			System.out.println("---------------------Test Case 5 Success:Tutorial Button is pressed---------------------");
		}
  }		
	
}
