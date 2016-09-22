package app.reuseables;

import global.reusables.ExcelUtils;
import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;


import DriverMethods.WebDriverInitilization;

public class AppReusableMethods {
	static ObjectRepository orCommon=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/CommonObjects.xml");
	/**
	 * This Method Will Select Two frames ie Text & Main Frame
	 * @param driver Instance of WebDriver
	 * @author PriyaRanjanM
	 */
	public static void selectMainFrame(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap){
		GenericMethods.selectFrame(driver, null, orCommon.getElement(driver, "TextFrame",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.selectFrame(driver, null, orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
	}

	/**
	 * This Method will Select origin terminal.
	 * @param driver Instance of WebDriver
	 * @param ScenarioDetailsHashMap
	 * @author PriyaRanjanM
	 */
	public static void switchOriginTerminalSea(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo){
		
		System.out.println("rovew value ::"+rowNo);
		boolean flag=true;
		String BrowserName = ScenarioDetailsHashMap.get("BrowserType");
		selectMainFrame(driver,ScenarioDetailsHashMap);
		try{
			System.out.println("inside catch block");
			((JavascriptExecutor) driver).executeScript("showPositions();");
//			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SwitchTerminalButton2", 10), 2);
		}catch (Exception e) {
			
			
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SwitchTerminalButton", ScenarioDetailsHashMap,10), 2);
		}
		GenericMethods.pauseExecution(4000);

		GenericMethods.selectWindow(driver);
		List<WebElement> we=GenericMethods.locateElements(driver, By.xpath("//span[@id='wrappedpositionList-rows']/span"), 10);
		
		String originTerminal=ExcelUtils.getCellData("Terminals", rowNo, "Sea_OriginTerminal",ScenarioDetailsHashMap);
		
		System.out.println("originTerminal***"+originTerminal);
		System.out.println("size="+we.size()+originTerminal);
		String[] terminal=originTerminal.split("#");
		for(int i=1;i<we.size()-1;i++)
		{
			WebElement elem=GenericMethods.locateElement(driver, By.id("wrappedpositionList-cell-1-"+(i-1)+"-box-text"), 10);
			WebElement elemRole=GenericMethods.locateElement(driver, By.id("wrappedpositionList-cell-2-"+(i-1)+"-box-text"), 10);
			Actions act=new Actions(driver);
			act.moveToElement(elem);
			act.click();
			act.sendKeys(Keys.ARROW_DOWN).perform();
//			GenericMethods.scrollDown(driver);
			if(elem.getText().equals(terminal[0])&& elemRole.getText().equals(terminal[1])){
				GenericMethods.clickElement(driver, null, we.get(i), 10);
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SelectButton",ScenarioDetailsHashMap, 10), 2);
				flag=false;
				break;
			}/*else{
				GenericMethods.type(driver, null, we.get(i), Keys.ARROW_DOWN, 10);
			}*/
		}
		if(flag== true)
		{
			System.out.println("given terminal is not present");
		}
		Assert.assertEquals(flag, false);
		GenericMethods.pauseExecution(2000);

		GenericMethods.switchToParent(driver);
	}
	/**
	 * This Method will Select origin terminal.
	 * @param driver Instance of WebDriver
	 * @param ScenarioDetailsHashMap
	 * @author PriyaRanjanM
	 * 
	 */
	public static void switchOriginTerminalAir(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo){
		
		System.out.println("rovew value ::"+rowNo);
		boolean flag=true;
		String BrowserName = ScenarioDetailsHashMap.get("BrowserType");
		selectMainFrame(driver,ScenarioDetailsHashMap);
		try{
			System.out.println("inside catch block");
			((JavascriptExecutor) driver).executeScript("showPositions();");
//			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SwitchTerminalButton2", 10), 2);
		}catch (Exception e) {
			
			
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SwitchTerminalButton", ScenarioDetailsHashMap,10), 2);
		}
		GenericMethods.pauseExecution(4000);

		GenericMethods.selectWindow(driver);
		List<WebElement> we=GenericMethods.locateElements(driver, By.xpath("//span[@id='wrappedpositionList-rows']/span"), 10);
		String originTerminal=ExcelUtils.getCellData("Terminals", rowNo, "Air_OriginTerminal",ScenarioDetailsHashMap);
		System.out.println("originTerminal***"+originTerminal);
		System.out.println("size="+we.size()+originTerminal);
		String[] terminal=originTerminal.split("#");
		for(int i=1;i<we.size()-1;i++)
		{
			WebElement elem=GenericMethods.locateElement(driver, By.id("wrappedpositionList-cell-1-"+(i-1)+"-box-text"), 10);
			WebElement elemRole=GenericMethods.locateElement(driver, By.id("wrappedpositionList-cell-2-"+(i-1)+"-box-text"), 10);
			Actions act=new Actions(driver);
			act.moveToElement(elem);
			act.click();
			act.sendKeys(Keys.ARROW_DOWN).perform();
//			GenericMethods.scrollDown(driver);
			if(elem.getText().equals(terminal[0])&& elemRole.getText().equals(terminal[1])){
				GenericMethods.clickElement(driver, null, we.get(i), 10);
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SelectButton",ScenarioDetailsHashMap, 10), 2);
				flag=false;
				break;
			}/*else{
				GenericMethods.type(driver, null, we.get(i), Keys.ARROW_DOWN, 10);
			}*/
		}
		if(flag== true)
		{
			System.out.println("given terminal is not present");
		}
		Assert.assertEquals(flag, false);
		GenericMethods.pauseExecution(2000);

		GenericMethods.switchToParent(driver);
	}

	/**
	 * This Method will Select origin terminal.
	 * @param driver Instance of WebDriver
	 * @param ScenarioDetailsHashMap
	 * @author PriyaRanjanM
	 */
	public static void switchDestinationTerminalSea(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo){
		
		System.out.println("rovew value ::"+rowNo);
		boolean flag=true;
		String BrowserName = ScenarioDetailsHashMap.get("BrowserType");
		selectMainFrame(driver,ScenarioDetailsHashMap);
		try{
			System.out.println("inside catch block");
			((JavascriptExecutor) driver).executeScript("showPositions();");
//			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SwitchTerminalButton2", 10), 2);
		}catch (Exception e) {
			
			
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SwitchTerminalButton", ScenarioDetailsHashMap,10), 2);
		}
		GenericMethods.pauseExecution(4000);

		GenericMethods.selectWindow(driver);
		List<WebElement> we=GenericMethods.locateElements(driver, By.xpath("//span[@id='wrappedpositionList-rows']/span"), 10);
		String originTerminal=ExcelUtils.getCellData("Terminals", rowNo, "Sea_DestinationTerminal",ScenarioDetailsHashMap);
		System.out.println("size="+we.size()+originTerminal);
		String[] terminal=originTerminal.split("#");
		System.out.println(terminal[0]+"dsffsd"+terminal[1]);
		
		for(int i=1;i<we.size()-1;i++)
		{
			WebElement elem=GenericMethods.locateElement(driver, By.id("wrappedpositionList-cell-1-"+(i-1)+"-box-text"), 10);
			WebElement elemRole=GenericMethods.locateElement(driver, By.id("wrappedpositionList-cell-2-"+(i-1)+"-box-text"), 10);
			Actions act=new Actions(driver);
			act.moveToElement(elem);
			act.click();
			act.sendKeys(Keys.ARROW_DOWN).perform();
//			System.out.println("ghghjghj"+GenericMethods.getInnerText(driver, By.id("wrappedpositionList-cell-1-"+(i-1)+"-box-text"), null, 10));
			
			if(elem.getText().equals(terminal[0])&& elemRole.getText().equals(terminal[1])){
				GenericMethods.clickElement(driver, null, elem, 10);
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SelectButton",ScenarioDetailsHashMap, 10), 2);
				flag=false;
				break;
			}
		}
		if(flag== true)
		{
			System.out.println("given terminal is not present");
		}
		Assert.assertEquals(flag, false);
		GenericMethods.pauseExecution(2000);

		GenericMethods.switchToParent(driver);
	}

	/**
	 * This Method will Select origin terminal.
	 * @param driver Instance of WebDriver
	 * @param ScenarioDetailsHashMap
	 * @author PriyaRanjanM
	 */
	public static void switchDestinationTerminalAir(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo){
		
		System.out.println("rovew value ::"+rowNo);
		boolean flag=true;
		String BrowserName = ScenarioDetailsHashMap.get("BrowserType");
		selectMainFrame(driver,ScenarioDetailsHashMap);
		try{
			System.out.println("inside catch block");
			((JavascriptExecutor) driver).executeScript("showPositions();");
//			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SwitchTerminalButton2", 10), 2);
		}catch (Exception e) {
			
			
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SwitchTerminalButton", ScenarioDetailsHashMap,10), 2);
		}
		GenericMethods.pauseExecution(4000);

		GenericMethods.selectWindow(driver);
		List<WebElement> we=GenericMethods.locateElements(driver, By.xpath("//span[@id='wrappedpositionList-rows']/span"), 10);
		String originTerminal=ExcelUtils.getCellData("Terminals", rowNo, "Air_DestinationTerminal",ScenarioDetailsHashMap);
		System.out.println("size="+we.size()+originTerminal);
		String[] terminal=originTerminal.split("#");
		System.out.println(terminal[0]+"dsffsd"+terminal[1]);
		
		for(int i=1;i<we.size()-1;i++)
		{
			WebElement elem=GenericMethods.locateElement(driver, By.id("wrappedpositionList-cell-1-"+(i-1)+"-box-text"), 10);
			WebElement elemRole=GenericMethods.locateElement(driver, By.id("wrappedpositionList-cell-2-"+(i-1)+"-box-text"), 10);
			Actions act=new Actions(driver);
			act.moveToElement(elem);
			act.click();
			act.sendKeys(Keys.ARROW_DOWN).perform();
//			System.out.println("ghghjghj"+GenericMethods.getInnerText(driver, By.id("wrappedpositionList-cell-1-"+(i-1)+"-box-text"), null, 10));
			
			if(elem.getText().equals(terminal[0])&& elemRole.getText().equals(terminal[1])){
				GenericMethods.clickElement(driver, null, elem, 10);
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SelectButton",ScenarioDetailsHashMap, 10), 2);
				flag=false;
				break;
			}
		}
		if(flag== true)
		{
			System.out.println("given terminal is not present");
		}
		Assert.assertEquals(flag, false);
		GenericMethods.pauseExecution(2000);

		GenericMethods.switchToParent(driver);
	}
		
	/**
	 * This Method will navigate to specific page selected from menu. 
	 * @param driver Instance of WebDriver
	 * @param javaScript Java Script for a particular menu
	 * @param Header Title/Header of the selected page.
	 * @author PriyaRanjanM
	 */
	public static void selectMenu(WebDriver driver,String javaScript,String Header,HashMap<String, String> ScenarioDetailsHashMap){
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(10000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		//		GenericKeyword.locateElement(driver, By.id("menu0"), 15);
		((JavascriptExecutor) driver).executeScript(javaScript);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		String title=GenericMethods.getInnerText(driver, null, orCommon.getElement(driver, "BannerTitle", ScenarioDetailsHashMap,10), 2);
		System.out.println("title"+title);
		Assert.assertEquals(title, Header);
	}
	
	/**
	 * This method will select value from Lov.
	 * @param driver Instance of WebDriver
	 * @param lovImg Webelement for the Lov
	 * @param obj Object for object repository.
	 * @param searchId name of search field in OR.
	 * @param searchValue Value to be selected from lov.
	 * @author PriyaRanjanM
	 */
	public static void selectValueFromLov(WebDriver driver,WebElement lovImg,ObjectRepository obj,String searchId,String searchValue,HashMap<String, String> ScenarioDetailsHashMap){
		String PartyData = "";
		GenericMethods.clickElement(driver, null, lovImg, 10);
		GenericMethods.pauseExecution(6000);
		GenericMethods.selectWindow(driver);
		GenericMethods.replaceTextofTextField(driver, null, obj.getElement(driver, searchId,ScenarioDetailsHashMap,10), searchValue, 10);
		GenericMethods.pauseExecution(2000);
		/*Code modified by Prasanna as we have different locator values for Search and Save.
		*/
		System.out.println("Typed"+""+orCommon.getElements(driver, "SearchButton",ScenarioDetailsHashMap,10).size());
		try {
			if (orCommon.getElement(driver, "SearchButton",ScenarioDetailsHashMap,10).isEnabled()) {
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 2);
			}	
		} catch (Exception e) {
			if (orCommon.getElement(driver, "SearchButton2",ScenarioDetailsHashMap,10).isEnabled()) {
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton2",ScenarioDetailsHashMap, 10), 2);
			}e.printStackTrace();
		}
		/*if (orCommon.getElements(driver, "SearchButton",ScenarioDetailsHashMap,10).size() > 0) {
			System.out.println("First search");
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 2);
			
		} else{
			System.out.println("secnd search");
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton2", ScenarioDetailsHashMap,10), 2);
		}*/
		
		
		
		
		GenericMethods.pauseExecution(8000);
		try {
			if (orCommon.getElement(driver, "SaveButton",ScenarioDetailsHashMap,10).isEnabled()) {
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 2);
			}	
		} catch (Exception e) {
			if (orCommon.getElement(driver, "SaveButton2",ScenarioDetailsHashMap,10).isEnabled()) {
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton2",ScenarioDetailsHashMap, 10), 2);
			}e.printStackTrace();
		}
		/*if (orCommon.getElements(driver, "SaveButton",ScenarioDetailsHashMap,10).size() > 0) {
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 2);
		} else{
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton2",ScenarioDetailsHashMap, 10), 2);
		}*/
//		System.out.println("element selected");
		GenericMethods.switchToParent(driver);
		selectMainFrame(driver,ScenarioDetailsHashMap);
 
	}
	
	/**
	 * This method will select value from Lov and will return Party Details as string
	 * @param driver Instance of WebDriver
	 * @param lovImg Webelement for the Lov
	 * @param obj Object for object repository.
	 * @param searchId name of search field in OR.
	 * @param searchValue Value to be selected from lov.
	 * @author Sandeep Jain
	 */
	public static String selectValueFromLov(WebDriver driver,WebElement lovImg,ObjectRepository obj,String searchId,String searchValue,String PartyIDType,HashMap<String, String> ScenarioDetailsHashMap){
	 
		System.out.println("hello custom lovvvvvvvvvv****************");
		GenericMethods.clickElement(driver, null, lovImg, 10);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectWindow(driver);
		GenericMethods.replaceTextofTextField(driver, null, obj.getElement(driver, searchId,ScenarioDetailsHashMap,10), searchValue, 10);
		GenericMethods.pauseExecution(2000);
		/*Code modified by Prasanna as we have different locator values for Search and Save.
		*/
		System.out.println("Typed"+""+orCommon.getElements(driver, "SearchButton",ScenarioDetailsHashMap,10).size());
		try {
			if (orCommon.getElement(driver, "SearchButton",ScenarioDetailsHashMap,10).isEnabled()) {
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 2);
			}	
		} catch (Exception e) {
			if (orCommon.getElement(driver, "SearchButton2",ScenarioDetailsHashMap,10).isEnabled()) {
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton2",ScenarioDetailsHashMap, 10), 2);
			}e.printStackTrace();
		}
		/*if (orCommon.getElements(driver, "SearchButton",ScenarioDetailsHashMap,10).size() > 0) {
			System.out.println("First search");
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 2);
			
		} else{
			System.out.println("secnd search");
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton2", ScenarioDetailsHashMap,10), 2);
		}*/
		
		int Grid_RowSize = driver.findElements(By.xpath("//table[@id='dataTable']/tbody/tr")).size();
		for (int RowID = 1; RowID <= Grid_RowSize; RowID++) 
		{
		
			if(GenericMethods.getInnerText(driver, By.xpath("//table[@id='dataTable']/tbody/tr["+RowID+"]/td[1]"), null, 10).equalsIgnoreCase(searchValue))
			{
				System.out.println("insideeeeeeeeeeeeeeeee********");
				PartyIDType =GenericMethods.getInnerText(driver, By.xpath("//table[@id='dataTable']/tbody/tr["+RowID+"]/td[2]"), null, 10);
				
				
			}
		}
		
		System.out.println("partryiiiidddddddd********"+PartyIDType);
		GenericMethods.pauseExecution(8000);
		try {
			if (orCommon.getElement(driver, "SaveButton",ScenarioDetailsHashMap,10).isEnabled()) {
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 2);
			}	
		} catch (Exception e) {
			if (orCommon.getElement(driver, "SaveButton2",ScenarioDetailsHashMap,10).isEnabled()) {
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton2",ScenarioDetailsHashMap, 10), 2);
			}e.printStackTrace();
		}
		/*if (orCommon.getElements(driver, "SaveButton",ScenarioDetailsHashMap,10).size() > 0) {
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 2);
		} else{
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton2",ScenarioDetailsHashMap, 10), 2);
		}*/
//		System.out.println("element selected");
		GenericMethods.switchToParent(driver);
		selectMainFrame(driver,ScenarioDetailsHashMap);
		return PartyIDType;
	}
	public static void msgBox(final String s) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				msgBox1(s);
			}
		});
	}

	@SuppressWarnings("serial")
	public static void msgBox1(final String s) {
		final JLabel label = new JLabel();
		int timerDelay = 1000;
		new Timer(timerDelay, new ActionListener() {
			int timeLeft = 2;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (timeLeft > 0) {
					label.setText(s);
					timeLeft--;
				} else {
					((Timer) e.getSource()).stop();
					Window win = SwingUtilities.getWindowAncestor(label);
					win.setVisible(false);
				}
			}
		}) {
			{
				setInitialDelay(0);
			}
		}.start();

		JOptionPane.showMessageDialog(null, label);

	}
	
	
	public static String roundOffDecimalPlaces(double num,String noOfDecimalPlaces)
	{
		DecimalFormat decim=null;
		
		if(noOfDecimalPlaces.equals("0")){
			decim = new DecimalFormat("0");
		}else if (noOfDecimalPlaces.equals("1")) {
			decim = new DecimalFormat("0.0");
		}else if (noOfDecimalPlaces.equals("2")) {
			decim = new DecimalFormat("0.00");
		}else if (noOfDecimalPlaces.equals("3")) {
			decim = new DecimalFormat("0.000");
		}else if (noOfDecimalPlaces.equals("4")) {
			decim = new DecimalFormat("0.0000");
		}
		
		
		return decim.format(num);
	}
	/**
	 * This Method will validate the format of the given date.
	 * @param driver Instance of WebDriver
	 * @param dateTextBox
	 * @param testData
	 * @param ScenarioDetailsHashMap
	 * @author Prasanna Modugu
	 */
	public static void validateDateFormat(WebDriver driver,WebElement dateTextBox, String testData,HashMap<String, String> ScenarioDetailsHashMap){
		try {
		String expectedFormat=GenericMethods.getPropertyValue("dateFormat_MDM", WebDriverInitilization.configurationStructurePath);
		String actualDateValue=dateTextBox.getAttribute("value");
		System.out.println("actualDateValue***"+actualDateValue);
		if (actualDateValue.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})|([0-9]{2})/([0-9]{2})/([0-9]{4})|([0-9]{4})-([0-9]{2})-([0-9]{2})|([0-9]{4})/([0-9]{2})/([0-9]{2})|([0-9]{2})-([0-9]{2})-([0-9]{2})|([0-9]{2})/([0-9]{2})/([0-9]{2})"))
		{
			System.out.println(testData+"****testData");
			System.out.println(expectedFormat+"****expectedFormat");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(expectedFormat);
			Date date=simpleDateFormat.parse(testData);
			String formattedDate=simpleDateFormat.format(date);
//			String formattedDate = new SimpleDateFormat(expectedFormat).format(testData);
			//			DD-MM-YYYY|DD/MM/YYYY|MM-DD-YYYY|MM/DD/YYYY|YYYY-MM-DD|YYYY/MM/DD|DD-MM-YY|DD/MM/YY|MM-DD-YY|MM/DD/YY|YY-MM-DD|YY/MM/DD|
			GenericMethods.assertTwoValues(formattedDate, actualDateValue, "Comparing date format.", ScenarioDetailsHashMap);
//			Assert.assertEquals(formattedDate, actualDateValue);	
			System.out.println(formattedDate+"****formattedDate"+expectedFormat+"***expectedFormat");
		}
		} catch (Exception e) {
		e.printStackTrace();
		System.out.println("Invalid date / date format.");
		}
	}
	/**
	 * This Method will validate the format of the given number.
	 * @param driver Instance of WebDriver
	 * @param numberTextBox
	 * @param testData
	 * @param ScenarioDetailsHashMap
	 * @author Prasanna Modugu
	 */	
	public static void validateNumberFormat(WebDriver driver,WebElement numberTextBox, String testData,HashMap<String, String> ScenarioDetailsHashMap){
		try {
			String expectedFormat=GenericMethods.getPropertyValue("numberFormat_MDM", WebDriverInitilization.configurationStructurePath);
			String actualNumberValue=numberTextBox.getAttribute("value");
			KewillNumberFormat fs=new KewillNumberFormat(expectedFormat);
			double amount = Double.parseDouble(testData);
			String expectedNumberValue=fs.format(amount);
			GenericMethods.assertTwoValues(actualNumberValue, expectedNumberValue, "Number Format.", ScenarioDetailsHashMap);
//			Assert.assertEquals(actualNumberValue, expectedNumberValue);	
		} catch (Exception e) {
		e.printStackTrace();
		System.out.println("Invalid Number / Number format.");
		}
	}
	/**
	* This Method will validate TimeZone of the application.
	* @param driver Instance of WebDriver
	* @param timeTextBox
	* @param timeZone
	* @param ScenarioDetailsHashMap
	* @author Prasanna Modugu
	*/	
	public static void validateTimeZoneFormat(WebDriver driver,WebElement dateTextBox,WebElement timeTextBox, String timeZone,HashMap<String, String> ScenarioDetailsHashMap){
		try {
			String[] time=timeZone.split("-");
			if (time.length==1) {
				System.out.println("in if");
				timeZone=timeZone.replace("+", "&");
				time=timeZone.split("&");
			}
			System.out.println(time.length+"***length");
			System.out.println(time[1]+"***length");
			String[] hhMM=time[1].split(":");
			System.out.println(hhMM[0]);
			System.out.println(hhMM[1]);
			int hours=Integer.parseInt(hhMM[0]);
			int mins=Integer.parseInt(hhMM[1]);
			System.out.println(hours+"***hrs"+mins+"***mins");
			Calendar currentdate = Calendar.getInstance();
			DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			TimeZone obj = TimeZone.getTimeZone("GMT");
			formatter.setTimeZone(obj);
			System.out.println("Local:: " +currentdate.getTime());
			System.out.println("GMT:: "+formatter.format(currentdate.getTime()));
//			Calendar cal = Calendar.getInstance(); // creates calendar
			currentdate.setTime(currentdate.getTime()); // sets calendar time/date
			if (timeZone.contains("&")) {
				currentdate.add(Calendar.HOUR_OF_DAY, -hours); // adding hours	
				currentdate.add(Calendar.MINUTE, -mins);// adding minutes
			}
			else{
				currentdate.add(Calendar.HOUR_OF_DAY, hours); // adding hours
				currentdate.add(Calendar.MINUTE, mins);// adding minutes
			}
			System.out.println(formatter.format(currentdate.getTime())+" time");
			formatter = new SimpleDateFormat("dd-MM-yyyy");
			String expDate=formatter.format(currentdate.getTime());
			System.out.println(expDate+" Date");
			formatter = new SimpleDateFormat("HH:mm");
			String expTime=formatter.format(currentdate.getTime());
			System.out.println(expTime+" Time");
			String actualDateValue=dateTextBox.getAttribute("value");
			GenericMethods.assertTwoValues(actualDateValue, expDate, "Comparing Time Zome Date", ScenarioDetailsHashMap);
//			Assert.assertEquals(actualDateValue, onlyDate);	
			String actualTimeValue=timeTextBox.getAttribute("value");
//			Assert.assertEquals(actualTimeValue, onlyTime);	
			GenericMethods.assertTwoValues(actualTimeValue, expTime, "Comparing Time Zome Time", ScenarioDetailsHashMap);
		} catch (Exception e) {
		e.printStackTrace();
		System.out.println("Invalid Time Zone / Time Zone format.");
		}
	}
	/**
	* This Method will unblock the shipments.
	* @param driver Instance of WebDriver
	* @param searchValue
	* @param ScenarioDetailsHashMap
	* @author Prasanna Modugu
	*/		
	public static void unblockShipment (WebDriver driver, String searchValue,HashMap<String, String> ScenarioDetailsHashMap){
		try {
			AppReusableMethods.selectMenu(driver, ETransMenu.blockedShipments,"Blocked Shipment Report",ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(5000);
			GenericMethods.replaceTextofTextField(driver, null, orCommon.getElement(driver, "Text_DocumentNo", ScenarioDetailsHashMap, 5), searchValue, 5);
//			GenericMethods.replaceTextofTextField(driver, null, searchTextBox, searchValue, 5);
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 5), 5);
			GenericMethods.clickElement(driver, By.xpath("//a[@value='"+searchValue+"']/ancestor::tr[1]/td/input[@name='dtUnblockLevel1ChkBox']"), null, 5);
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 5), 5);
			//GenericMethods.assertInnerText(driver, By.xpath("//tr[@id='TOOLBARROW']/following-sibling::tr[1]//b"),null, "Shipments cleared sucessufully","Shipments Unblocked","Shipments Unblocked", ScenarioDetailsHashMap);
				}
	catch(Exception e){
		e.printStackTrace();
	}	
	}
	public static void checkNotesAndAlerts(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
//      GenericMethods.replaceTextofTextField(driver, null, shipper,ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBL_Shipper", ScenarioDetailsHashMap), 5);
//      GenericMethods.replaceTextofTextField(driver, null, consignee,ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBL_Consignee", ScenarioDetailsHashMap), 5);
        GenericMethods.pauseExecution(5000);
        GenericMethods.selectFrame(driver,By.name("notesAndAlertsIframe"),null,5);
        GenericMethods.assertValue(driver, By.xpath("//td[contains(text(),'Notes And Alert Information')]"), null, "Notes And Alert Information", "Notes And Alert", "Notes And Alert", ScenarioDetailsHashMap);
        GenericMethods.assertValue(driver, By.xpath("//span[text()='ALRT103']"), null, "ALRT103", "Notes And Alert", "Notes And Alert", ScenarioDetailsHashMap);
        GenericMethods.clickElement(driver, By.name("Proceed"), null, 5);
//      GenericMethods.clickElement(driver, By.name("discard"), null, 5);
        GenericMethods.pauseExecution(5000);
        GenericMethods.switchToDefaultContent(driver);
        selectMainFrame(driver,ScenarioDetailsHashMap);
  }


	
	/**
	* This Method will Handle Notes and Alert Pop Up and will discard the same
	* @param driver Instance of WebDriver
	* @param searchValue
	* @param ScenarioDetailsHashMap
	* @author Pavan Bikumandla
	*/	
	public static void checkNotesAndAlert_PopUp(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		GenericMethods.selectFrame(driver,By.name("notesAndAlertsIframe"),null,5);
		GenericMethods.assertValue(driver, By.xpath("//td[contains(text(),'Notes And Alert Information')]"), null, "Notes And Alert Information", "Notes And Alert", "Notes And Alert", ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, By.name("discard"), null, 5);
		GenericMethods.switchToDefaultContent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
	}
	
	static ObjectRepository hblChargesAndCosts=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/ChargesAndCosts.xml");
	/**
	* This Method is used to check for the all charge codes and status when we do different operations(RAT17).
	* @param driver Instance of WebDriver
	* @param searchValue
	* @param ScenarioDetailsHashMap
	* @author Prasanna Modugu
	*/	
	public static void chargesAndCosts(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		try {
		GenericMethods.clickElement(driver, null, hblChargesAndCosts.getElement(driver, "Tab_HBLChargesCode", ScenarioDetailsHashMap, 5), 5);
		GenericMethods.pauseExecution(5000);
		GenericMethods.handleAlert(driver, "accept");
		List <WebElement> list=driver.findElements(By.xpath("//input[@value='READY' and @name='chargesVOList.salesStatus']"));
		int noOfRecords=list.size();
		String status=null;
		for (int i = 1; i <= noOfRecords; i++) {
			status=GenericMethods.getInnerText(driver, By.xpath("//span[@id='spanchargesVOList.salesStatus"+i+"']"), null, 5);
			GenericMethods.assertTwoValues(status, "READY", "Status before accept.", ScenarioDetailsHashMap);
			}
		GenericMethods.clickElement(driver, By.xpath("//input[@name='accept']"), null, 5);
		for (int i = 1; i <= noOfRecords; i++) {
			status=GenericMethods.getInnerText(driver, By.id("spanchargesVOList.salesStatus"+i), null, 5);
			GenericMethods.assertTwoValues(status, "ACCEPTED", "Status after accept.", ScenarioDetailsHashMap);
			}
		GenericMethods.clickElement(driver, By.xpath("//input[@name='postSales']"), null, 5);
		GenericMethods.pauseExecution(3000);
		GenericMethods.selectWindow(driver);
		driver.close();
		GenericMethods.pauseExecution(1000);
		GenericMethods.switchToParent(driver);
        AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(2000);
		for (int i = 1; i <= noOfRecords; i++) {
			status=GenericMethods.getInnerText(driver, By.xpath("//*[@id='chargesVOList.salesStatusH"+i+"']"), null, 5);
			GenericMethods.assertTwoValues(status, "INVOICED", "Status after Post Sales.", ScenarioDetailsHashMap);
		}
		String invoiceIdSummary=GenericMethods.getInnerText(driver, By.xpath("//font[@color='green']/b"), null, 2);
		String invoiceId=invoiceIdSummary.split(":")[1].trim()+" ";
		invoiceId=invoiceId.substring(0,invoiceId.indexOf(" "));
		ExcelUtils.setCellData("CostAndCharges", rowNo, "SalesInvoiceNo", invoiceId, ScenarioDetailsHashMap);
		ExcelUtils.setCellData("Ocean_CreaditNote", rowNo, "SalesInvoiceNo", invoiceId, ScenarioDetailsHashMap);
		
		//GenericMethods.assertTwoValues(invoiceIdSummary.split(":")[0], "AUTOMATIONSHIPP Invoice has been posted to Accounts with Transaction Id", "Post Sales.", ScenarioDetailsHashMap);
		String InvoiceSmryMSG = invoiceIdSummary.split(":")[0];
		String[] arrMSG=InvoiceSmryMSG.split(" ");
		String InvoiceMessge = InvoiceSmryMSG.replace(arrMSG[0], "").trim();
		System.out.print("InvoiceMessge ="+InvoiceMessge);
		GenericMethods.assertTwoValues(InvoiceMessge, "Invoice has been posted to Accounts with Transaction Id", "Validating Post Sales success message", ScenarioDetailsHashMap);
		
		
		System.out.println(invoiceIdSummary.split(":")[0]+"succ msg***");
		System.out.println("invoiceId::"+invoiceId);	
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
	/**
	* This Method is used to check the Quote/Contract status after selecting contract.(RAT19)
	* @param driver Instance of WebDriver
	* @param searchValue
	* @param ScenarioDetailsHashMap
	* @author Prasanna Modugu
	*/		
	public static void quoteOrContractStatus(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
	try{
		GenericMethods.action_Key(driver, Keys.TAB);
		GenericMethods.assertTwoValues(hblChargesAndCosts.getElement(driver, "QuoteOrContractStatus", ScenarioDetailsHashMap, 5).getAttribute("value"), "Provisional", "Quote / Contract Status", ScenarioDetailsHashMap);
	}catch(Exception e){
		e.printStackTrace();
	}
	
	}
	/**
	* This Method is used to verify the Freight & Other charges with Charge and Costs.
	* @param driver Instance of WebDriver
	* @param searchValue
	* @param ScenarioDetailsHashMap
	* @author Prasanna Modugu
	*/		
	public static void checkChargeDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
	try {
	int rowCount=ExcelUtils.getCellDataRowCount("CostAndCharges", ScenarioDetailsHashMap);
	for (int i = 1; i <= rowCount; i++) {		
		GenericMethods.assertValue(driver, By.xpath("//th[text()='Charge Code']/ancestor::thead/following-sibling::tbody/tr/td[@smryfieldid='chargeCode']"), null, ExcelUtils.getCellData("CostAndCharges", rowNo, "ChargeCode", ScenarioDetailsHashMap), "ChargeCode", "ChargeCode", ScenarioDetailsHashMap);
	}
	for (int i = 1; i <= rowCount; i++) {		
		GenericMethods.assertValue(driver, By.xpath("//th[text()='Charge Code']/ancestor::thead/following-sibling::tbody/tr/td/span[conatins(@id,'spanchargesVOList.chargeCode')]"), null, ExcelUtils.getCellData("CostAndCharges", rowNo, "ChargeCode", ScenarioDetailsHashMap), "ChargeCode", "ChargeCode", ScenarioDetailsHashMap);
	}			
	} catch (Exception e) {
	e.printStackTrace();
	}
	}
	/**
	* This Method will check Charges and Rates which were downloaded with ready status and by default selected(RAT24)
	* @param driver Instance of WebDriver
	* @param searchValue
	* @param ScenarioDetailsHashMap
	* @author Prasanna Modugu
	*/	
	public static void chargesAndRates(WebDriver driver,WebElement tabName,String selectValue,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		try{
		GenericMethods.clickElement(driver, null, tabName, 5);
		GenericMethods.pauseExecution(5000);
		GenericMethods.handleAlert(driver, "accept");
		GenericMethods.selectOptionFromDropDown(driver,null , hblChargesAndCosts.getElement(driver, "ProfitCentre", ScenarioDetailsHashMap, 5), selectValue);
		List <WebElement> list=driver.findElements(By.xpath("//input[@value='READY' and @name='chargesVOList.salesStatus']"));
		int noOfRecords=list.size();
		//Sangeeta added:- This piece of code will work when Buy Rate check box is not checked in HBLChargecost Tab 
		//************
		for (int i = 1; i <= noOfRecords; i++) {
			System.out.println("Row isSelected :"+i+ " = "+ driver.findElement(By.id("multiSelectCheckboxchargeGrid"+i)).isSelected());
			if(!driver.findElement(By.id("multiSelectCheckboxchargeGrid"+i)).isSelected())
			{
				GenericMethods.pauseExecution(3000);
				System.out.println("i ="+i);
				GenericMethods.clickElement(driver, By.id("multiSelectCheckboxchargeGrid"+i), null, 10);
				GenericMethods.pauseExecution(3000);
				
			}
		}
		//***************
		String status=null;
		for (int i = 1; i <= noOfRecords; i++) {
			status=GenericMethods.getInnerText(driver, By.xpath("//span[@id='spanchargesVOList.salesStatus"+i+"']"), null, 5);
			GenericMethods.assertTwoValues(status, "READY", "Charge Code Status.", ScenarioDetailsHashMap);
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		}	

/**
 	* This Method is used to generate sales invoice by changing local currency with the invoice currency.
* @param driver Instance of WebDriver
* @param searchValue
* @param ScenarioDetailsHashMap
* @author Prasanna Modugu
*/	
	public static void invoiceCurrency_ChargesAndRates(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
	try {
	GenericMethods.clickElement(driver, null, hblChargesAndCosts.getElement(driver, "Tab_HBLChargesCode", ScenarioDetailsHashMap, 5), 5);
	GenericMethods.pauseExecution(5000);
	GenericMethods.handleAlert(driver, "accept");
	
	List <WebElement> list=driver.findElements(By.xpath("//input[@value='READY' and @name='chargesVOList.salesStatus']"));
	int noOfRecords=list.size();
	String status=null;
	GenericMethods.clickElement(driver, By.xpath("//input[@name='Post Sales']"), null, 5);
	for (int i = 1; i <= noOfRecords; i++) {
		status=GenericMethods.getInnerText(driver, By.xpath("//span[@id='spanchargesVOList.salesStatus"+i+"']"), null, 5);
		GenericMethods.assertTwoValues(status, "INVOICED", "Status after Post Sales.", ScenarioDetailsHashMap);
	}
	String msgPostSales=GenericMethods.getInnerText(driver, By.xpath("//b[contains(text(),'has been posted')]"), null, 5);
	String [] ShipmentRefTextSplit=msgPostSales.split(":");
	String salesInvoiceId =ShipmentRefTextSplit[1].trim();
	ExcelUtils.setCellData("CostAndCharges", rowNo, "SalesInvoiceNo", salesInvoiceId, ScenarioDetailsHashMap);
	GenericMethods.assertTwoValues(ShipmentRefTextSplit[1], "AUTOMATIONSHIPP Invoice has been posted to Accounts with Transaction Id", "Post Sales.", ScenarioDetailsHashMap);
	} catch (Exception e) {
	e.printStackTrace();
	}
	}	

	
	
}