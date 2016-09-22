package app.reuseables;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.PauseAction;

import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;

import DriverMethods.StartUp;

public class ETransStartUp extends StartUp{
	static ObjectRepository orLogin=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/Login.xml");
	public static void launchApp(WebDriver driver,String browserType,HashMap<String, String> ScenarioDetailsHashMap)
	{
		System.out.println("Browser"+browserType);
		String UName=null;
		String password=null;
		System.out.println("browserType***"+browserType);
		if(browserType.equalsIgnoreCase("chrome")){
			 UName=GenericMethods.getPropertyValue("userName", configurationStructurePath);
			 password=GenericMethods.getPropertyValue("Password", configurationStructurePath);
		}else if(browserType.equalsIgnoreCase("ie"))
		{
			 UName=GenericMethods.getPropertyValue("userNameie", configurationStructurePath);
			 password=GenericMethods.getPropertyValue("Passwordie", configurationStructurePath);
		}else{
			 UName=GenericMethods.getPropertyValue("userNamefirefox", configurationStructurePath);
			 password=GenericMethods.getPropertyValue("Passwordfirefox", configurationStructurePath);
		}
		
		GenericMethods.replaceTextofTextField(driver, null, orLogin.getElement(driver, "UserName", ScenarioDetailsHashMap,10),UName , 2);
		GenericMethods.replaceTextofTextField(driver, null, orLogin.getElement(driver, "Password", ScenarioDetailsHashMap,10), password, 2);
		GenericMethods.clickElement(driver, null, orLogin.getElement(driver, "LoginButton", ScenarioDetailsHashMap,10), 2);
		try{
			GenericMethods.clickElement(driver, null, orLogin.getElement(driver, "Invalidate", ScenarioDetailsHashMap,10), 2);
			GenericMethods.pauseExecution(2000);
		}catch (Exception e) {
			System.out.println("Invalidate not Required");
		}
		//GenericMethods.clickElement(driver, null, orLogin.getElement(driver, "Save", 10), 2);
	}
	
	

}
