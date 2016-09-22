package DriverMethods;

import global.reusables.CommonBean;
import global.reusables.GenericMethods;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import app.reuseables.ETransStartUp;


public class WebDriverInitilization {
	public static CommonBean res;
	public static String configurationStructurePath = System.getProperty("user.dir")+"/Configurations/base.properties";
	public static String reportStructurePath = System.getProperty("user.dir")+GenericMethods.getPropertyValue("reportFolderPath",configurationStructurePath);
	public static WebDriver driver= null;
	public static InternetExplorerDriverService service;
	/**
	 * This method will create a instance of webDriver based on browser value passed as argument.
	 * @param browser Browser name.
	 * @return Instance of webDriver.
	 * @author Priyaranjan
	 * @throws InterruptedException 
	 */
	public static WebDriver driverInt(String browser) 
	{
		System.out.println("browser***"+browser);
		WebDriver driver1=null;
		String url=GenericMethods.getPropertyValue("url",configurationStructurePath);
		if(browser.equalsIgnoreCase("ie")){

			String path=System.getProperty("user.dir")+"/ReferenceLibraries/IEDriverServer.exe";
			System.setProperty("webdriver.ie.driver",path);
			DesiredCapabilities dc=DesiredCapabilities.internetExplorer();
			LoggingPreferences logging = new LoggingPreferences();
			logging.enable(LogType.DRIVER, Level.FINEST);
			dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
			dc.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,true);
			driver1= new InternetExplorerDriver(dc);

		}else if(browser.equalsIgnoreCase("FireFox"))
		{
			DesiredCapabilities dc=DesiredCapabilities.firefox();
			LoggingPreferences logging = new LoggingPreferences();
			logging.enable(LogType.DRIVER, Level.FINEST);
			dc.setCapability("requireWindowFocus", true);
			dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
			FirefoxProfile profile= new FirefoxProfile();
			profile.setPreference("network.http.phishy-userpass-length", 255);
			profile.setPreference("network.automatic-ntlm-auth.trusted-uris", "0.97");
			driver1= new FirefoxDriver(profile);

		}else if(browser.equalsIgnoreCase("Chrome"))
		{
			Map<String, Object> prefs = new HashMap<String, Object>();
			
			prefs.put("download.prompt_for_download", true);
			DesiredCapabilities dc=DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);
			options.addArguments("--always-authorize-plugins");
			options.addArguments("--allow-outdated-plugins");
			dc.setCapability(ChromeOptions.CAPABILITY, options);
			options.addArguments(Arrays.asList("--start-maximized", "allow-running-insecure-content", "ignore-certificate-errors"));
			dc.setCapability(ChromeOptions.CAPABILITY, options);
			/*DesiredCapabilities dc=DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
//			dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
			options.addArguments(Arrays.asList("--start-maximized", "allow-running-insecure-content", "ignore-certificate-errors","disable-web-security"));
			dc.setCapability(ChromeOptions.CAPABILITY, options);*/
			String path=System.getProperty("user.dir")+"/ReferenceLibraries/chromedriver.exe";
			System.setProperty("webdriver.chrome.driver",path);
			driver1= new ChromeDriver(dc);
			
		}
		
		driver1.get(url);
		
		driver1.manage().window().maximize();
		return driver1;
	}


}
