
import Model.*;
import Controller.*;
import Controller.LogIn.*;
import java.io.File;

public class Program 
{
	private static String userType = null;

	public static void main(String[] args) 
	{
		login_Manager login_Manager = new login_Manager();
		
        while(true){
			
			if(login_Manager.isTerminated()){
				
				userType = login_Manager.UserType();
				Run_MVC_userAccess();
				break;
			}
			
		}	
                                 		
	}
	
	private static void Run_MVC_userAccess(){
		
	// Creates the MapModel for the application.
		MapModel theModel = new MapModel();
		
		if(userType.equalsIgnoreCase("CEO")){
		// Start the Controller that will also initialize and show the MainView.
			IController theController = new CEOController(theModel);
		}
		
		if(userType.equalsIgnoreCase("Power")){
			IController theController = new PowerController(theModel);
		}
		
		if(userType.equalsIgnoreCase("Market")){
			IController theController = new MarketingController(theModel);
		}
		
	}
	
}
