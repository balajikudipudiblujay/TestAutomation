package business.scenarios;

import global.reusables.ExcelUtils;
import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.xalan.transformer.Counter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.ui.Select;

import GlobalReusables.webElement;
import app.reuseables.AppReusableMethods;
import app.reuseables.ETransMenu;

public class MIS_Reports {

	static ObjectRepository Common = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/CommonObjects.xml");
	static ObjectRepository orMISReports = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/CommonObjects.xml");

	
	//Pavan FUNC063.1 - Below method will perform Validations for Reports
	public static void OceanMIS_Sea_Gateway_Validations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo){
		AppReusableMethods.selectMenu(driver, ETransMenu.Ocean_Gateway_Sea_Report, "Gateway Report", ScenarioDetailsHashMap);
		Select DDL_ShipmentMode=new Select(driver.findElement(By.name("shipmentMode")));
		DDL_ShipmentMode.selectByVisibleText(ExcelUtils.getCellData("SE_MISReports", RowNo, "ShipmentMode", ScenarioDetailsHashMap));
		/*GenericMethods.clickElement(driver, By.name("AdvanceSearch3"),null, 10);
		GenericMethods.pauseExecution(3000);
		GenericMethods.selectWindow(driver);
		GenericMethods.replaceTextofTextField(driver,By.id("commandObjectdataObjectcriteriacodevalue") ,null,  ExcelUtils.getCellData("SE_MISReports", RowNo, "Gateway_Terminal", ScenarioDetailsHashMap), 10);
		GenericMethods.pauseExecution(2000);
		GenericMethods.clickElement(driver, By.id("buttonSearch"), null, 2);
		GenericMethods.clickElement(driver, By.id("buttonSelect"), null, 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);*/
		GenericMethods.clickElement(driver, By.name("enter"), null, 10);
		GenericMethods.pauseExecution(10000);
		GenericMethods.selectWindow(driver);
		int grid_RowCount=driver.findElements(By.xpath("//td[text()='Gateway Report']/ancestor::table[2]/following-sibling::table/tbody/tr")).size();
		System.out.println("grid_RowCount:::"+grid_RowCount);
		for (int grid_RowNum = 3; grid_RowNum <= grid_RowCount; grid_RowNum++) {
			String HBL_Id=GenericMethods.getInnerText(driver, By.xpath("//td[text()='Gateway Report']/ancestor::table[2]/following-sibling::table/tbody/tr["+grid_RowNum+"]/td[1]"),null, 10);
			if(HBL_Id.trim().equalsIgnoreCase(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap))){
				GenericMethods.assertInnerText(driver, By.xpath("//td[text()='Gateway Report']/ancestor::table[2]/following-sibling::table/tbody/tr["+grid_RowNum+"]/td[1]"), null, ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), "HBL_Id", "Validating HBL ID in Sea Gateway Reports", ScenarioDetailsHashMap);	
				GenericMethods.clickElement(driver, By.name("close"), null, 10);
				break;
			}

		}
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
	}

	//Pavan FUNC063.2 - Below method will perform Validations for Reports
	public static void OceanMIS_MasterStatus_Validations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) throws AWTException 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.Ocean_MasterStatusReport, "Master Status Report", ScenarioDetailsHashMap);
		Select DDL_ShipmentMode=new Select(driver.findElement(By.name("shipmentType")));
		DDL_ShipmentMode.selectByVisibleText(ExcelUtils.getCellData("SE_MISReports", RowNo, "ShipmentType", ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, By.name("fromdate"), null, ExcelUtils.getCellData("SE_MISReports", RowNo, "FromDate", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, By.name("todate"), null, ExcelUtils.getCellData("SE_MISReports", RowNo, "ToDate", ScenarioDetailsHashMap), 10);
		GenericMethods.clickElement(driver, By.name("submitButton"), null, 10);
		GenericMethods.pauseExecution(10000);
		GenericMethods.selectWindow(driver);
		int grid_RowCount=driver.findElements(By.xpath("//td[text()='Master Status Report - Export']/ancestor::table[2]/following-sibling::table/tbody/tr")).size();
		System.out.println("grid_RowCount:::"+grid_RowCount);
		for (int grid_RowNum = 3; grid_RowNum <= grid_RowCount; grid_RowNum++) {
			String MasterId=GenericMethods.getInnerText(driver, By.xpath("//td[text()='Master Status Report - Export']/ancestor::table[2]/following-sibling::table/tbody/tr["+grid_RowNum+"]/td[1]"),null, 10);
			if(MasterId.trim().equalsIgnoreCase(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Console_ID", ScenarioDetailsHashMap))){
				GenericMethods.assertInnerText(driver, By.xpath("//td[text()='Master Status Report - Export']/ancestor::table[2]/following-sibling::table/tbody/tr["+grid_RowNum+"]/td[1]"), null, ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Console_ID", ScenarioDetailsHashMap), "MasterID", "Validating Master ID in Master Status Reports", ScenarioDetailsHashMap);	
				GenericMethods.clickElement(driver, By.name("Button"), null, 10);
				break;
			}

		}
		
		System.out.println("started");
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_W);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		System.out.println("Ended");
		GenericMethods.pauseExecution(5000);
		GenericMethods.switchToParent(driver);
		GenericMethods.pauseExecution(3000);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
		System.out.println("Came out of report");

	}

	//Pavan FUNC063.3 - Below method will perform Validations for Reports
	public static void OceanMIS_Tonnage_Rate_Validations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) throws AWTException 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.Ocean_TonnageReport, "Tonnage And Rate Report", ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, By.name("cuto"),null, 10);
		GenericMethods.pauseExecution(10000);
		GenericMethods.selectWindow(driver);
		GenericMethods.replaceTextofTextField(driver,By.id("partyId") ,null,  ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Customer", ScenarioDetailsHashMap), 10);
		GenericMethods.pauseExecution(4000);
		if (Common.getElements(driver, "SearchButton",ScenarioDetailsHashMap,10).size() > 0) {
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 2);
		} else{
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton2", ScenarioDetailsHashMap,10), 2);
		}
		GenericMethods.pauseExecution(10000);
		if (Common.getElements(driver, "SaveButton",ScenarioDetailsHashMap,10).size() > 0) {
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 2);
		} else{
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton2",ScenarioDetailsHashMap, 10), 2);
		}
		GenericMethods.pauseExecution(8000);
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);

		GenericMethods.replaceTextofTextField(driver, By.id("fromDateT"), null, ExcelUtils.getCellData("SE_MISReports", RowNo, "FromDate", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, By.id("toDateT"), null, ExcelUtils.getCellData("SE_MISReports", RowNo, "ToDate", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, By.id("currencyIdT"), null, ExcelUtils.getCellData("SE_MISReports", RowNo, "Tonnage_Currency", ScenarioDetailsHashMap), 10);
		Select DDL_ShipmentMode=new Select(driver.findElement(By.name("smode")));
		System.out.println("current URL:::"+driver.getCurrentUrl());
		DDL_ShipmentMode.selectByVisibleText(ExcelUtils.getCellData("SE_MISReports", RowNo, "ShipmentMode", ScenarioDetailsHashMap));
		GenericMethods.clickElement(driver,  By.name("Submit3"), null, 10);
		GenericMethods.pauseExecution(15000);
		GenericMethods.selectWindow(driver);
		GenericMethods.selectFrame(driver, By.name("reportFrame"),null, 2);
		int grid_RowCount=driver.findElements(By.xpath("//td[text()='TONNAGE REPORT']/ancestor::table[1]/following-sibling::table[1]//tr")).size();
		System.out.println("grid_RowCount:::"+grid_RowCount);
		for (int grid_RowNum = 2; grid_RowNum <= grid_RowCount; grid_RowNum++) {
			String HBL_Id=GenericMethods.getInnerText(driver, By.xpath("//td[text()='TONNAGE REPORT']/ancestor::table[1]/following-sibling::table[1]//tr["+grid_RowNum+"]/td[1]"),null, 10);
			if(HBL_Id.trim().equalsIgnoreCase(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap))){
				GenericMethods.assertInnerText(driver, By.xpath("//td[text()='TONNAGE REPORT']/ancestor::table[1]/following-sibling::table[1]//tr["+grid_RowNum+"]/td[1]"), null, ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), "HBLId", "Validating HBL ID in Tonnage and Rate Reports", ScenarioDetailsHashMap);
				GenericMethods.assertInnerText(driver, By.xpath("//td[text()='TONNAGE REPORT']/ancestor::table[1]/following-sibling::table[1]//tr["+grid_RowNum+"]/td[4]"), null, ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "GrossWeight", ScenarioDetailsHashMap), "WeightInKg", "Validating Weight in Tonnage and Rate Reports", ScenarioDetailsHashMap);
				driver.close();
				break;
			}

		}
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
	}
	
	//Pavan FUNC063.4 - Below method will perform Validations for Reports
	public static void OceanMIS_TAT_Validations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.Ocean_TATReport, "TAT REPORT", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, By.name("fromDate"), null, ExcelUtils.getCellData("SE_MISReports", RowNo, "FromDate", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, By.name("toDate"), null, ExcelUtils.getCellData("SE_MISReports", RowNo, "ToDate", ScenarioDetailsHashMap), 10);
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsolidationORB2B",ScenarioDetailsHashMap).equalsIgnoreCase("B2B"))
		{
			GenericMethods.replaceTextofTextField(driver, By.name("destinationId"), null, ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "DestBranch", ScenarioDetailsHashMap), 10);
		}
		else if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsolidationORB2B",ScenarioDetailsHashMap).equalsIgnoreCase("Consolidation")){
			GenericMethods.replaceTextofTextField(driver, By.name("destinationId"), null, ExcelUtils.getCellData("SE_OBL", RowNo, "DestBranch", ScenarioDetailsHashMap), 10);
		}
		
		Select DDL_FromValue=new Select(driver.findElement(By.name("from")));
		DDL_FromValue.selectByVisibleText(ExcelUtils.getCellData("SE_MISReports", RowNo, "DayFrom", ScenarioDetailsHashMap));
		Select DDL_ToValue=new Select(driver.findElement(By.name("to")));
		DDL_ToValue.selectByVisibleText(ExcelUtils.getCellData("SE_MISReports", RowNo, "DayTo", ScenarioDetailsHashMap));
		GenericMethods.clickElement(driver, By.name("selectAll"), null, 10);
		GenericMethods.clickElement(driver, By.name("submitButton"), null, 10);
		GenericMethods.pauseExecution(10000);
		GenericMethods.selectWindow(driver);
		int grid_RowCount=driver.findElements(By.xpath("//b[text()='Origin Terminal check:']/ancestor::table[1]/following-sibling::table[1]/tbody/tr")).size();
		System.out.println("grid_RowCount:::"+grid_RowCount);
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsolidationORB2B",ScenarioDetailsHashMap).equalsIgnoreCase("B2B"))
		{
			for (int grid_RowNum = 2; grid_RowNum <= grid_RowCount; grid_RowNum++) {
				String ConsoleId=GenericMethods.getInnerText(driver, By.xpath("//b[text()='Origin Terminal check:']/ancestor::table[1]/following-sibling::table[1]/tbody/tr["+grid_RowNum+"]/td[1]"),null, 10);
				if(ConsoleId.trim().equalsIgnoreCase(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Console_ID", ScenarioDetailsHashMap))){
					GenericMethods.assertInnerText(driver, By.xpath("//b[text()='Origin Terminal check:']/ancestor::table[1]/following-sibling::table[1]/tbody/tr["+grid_RowNum+"]/td[1]"), null, ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Console_ID", ScenarioDetailsHashMap), "ConsoleId", "Validating Console ID in TAT Reports", ScenarioDetailsHashMap);
					driver.close();
					break;
				}

			}
		}
		else if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsolidationORB2B",ScenarioDetailsHashMap).equalsIgnoreCase("Consolidation")){
			for (int grid_RowNum = 2; grid_RowNum <= grid_RowCount; grid_RowNum++) {
				String ConsoleId=GenericMethods.getInnerText(driver, By.xpath("//b[text()='Origin Terminal check:']/ancestor::table[1]/following-sibling::table[1]/tbody/tr["+grid_RowNum+"]/td[1]"),null, 10);
				if(ConsoleId.trim().equalsIgnoreCase(ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap))){
					GenericMethods.assertInnerText(driver, By.xpath("//b[text()='Origin Terminal check:']/ancestor::table[1]/following-sibling::table[1]/tbody/tr["+grid_RowNum+"]/td[1]"), null, ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap), "ConsoleId", "Validating Console ID in TAT Reports", ScenarioDetailsHashMap);
					driver.close();
					break;
				}

			}
		}
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
	}

	//Pavan FUNC063.5 - Below method will perform Validations for Reports
	public static void OceanMIS_BookingBookedButPartiallyReceived_Validations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.Ocean_BookingBookedButNotReceivedReport, "Outbound-Non System", ScenarioDetailsHashMap);
		int grid_RowCount=driver.findElements(By.xpath("//form[@name='report']//table[2]/tbody/tr")).size();
		System.out.println("grid_RowCount::"+grid_RowCount);
		for (int grid_RowNum = 2; grid_RowNum <= grid_RowCount; grid_RowNum++) {
			String BookingId=GenericMethods.getInnerText(driver, By.xpath("//form[@name='report']//table[2]/tbody/tr["+grid_RowNum+"]/td[1]"),null, 10);
			if(BookingId.trim().equalsIgnoreCase(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "BookingId", ScenarioDetailsHashMap))){
				GenericMethods.assertInnerText(driver, By.xpath("//form[@name='report']//table[2]/tbody/tr["+grid_RowNum+"]/td[1]"), null, ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "BookingId", ScenarioDetailsHashMap), "BookingId", "Validating Booking ID Booked but partially received Reports", ScenarioDetailsHashMap);
				GenericMethods.clickElement(driver,By.linkText("Cancel"),null, 10);
				break;
			}
			else if(grid_RowNum==grid_RowCount){
				GenericMethods.clickElement(driver,By.linkText("Next"),null, 10);
				grid_RowNum=1;
			}
		}

	}

	//Pavan FUNC063.6 - Below method will perform Validations for Reports
	public static void OceanMIS_BookingDone_PRS_NotDone_Validations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.Ocean_BookingDoneButPRSNotDoneReport, "Sea Booking Done But PRS Not Done", ScenarioDetailsHashMap);
		int grid_RowNum ;
		String BookingId;
		int grid_RowCount=driver.findElements(By.xpath("//form[@name='report']//table[2]/tbody/tr")).size();
		System.out.println("grid_RowCount::"+grid_RowCount);
		for (grid_RowNum = 2; grid_RowNum <= grid_RowCount; grid_RowNum++) {
			BookingId=GenericMethods.getInnerText(driver, By.xpath("//form[@name='report']//table[2]/tbody/tr["+grid_RowNum+"]/td[1]"),null, 10);
			System.out.println("BookingId::"+BookingId);
			System.out.println("grid_RowNum::"+grid_RowNum);
			if(BookingId.trim().equalsIgnoreCase(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "BookingId", ScenarioDetailsHashMap))){
				GenericMethods.assertInnerText(driver, By.xpath("//form[@name='report']//table[2]/tbody/tr["+grid_RowNum+"]/td[1]"), null, ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "BookingId", ScenarioDetailsHashMap), "BookingId", "Validating Booking ID in Booking done PRS not Done Reports", ScenarioDetailsHashMap);
				GenericMethods.clickElement(driver,By.linkText("Cancel"),null, 10);
				break;
			}

			else if(grid_RowNum==grid_RowCount){
				GenericMethods.clickElement(driver,By.linkText("Next"),null, 10);
				grid_RowNum=1;
			}
		}

	}
	//Pavan FUNC063.7 - Below method will perform Validations for Reports
	public static void OceanMIS_Booking_Not_Assigned_To_HBL_Validations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.Ocean_BookingNotAssignedToHBLReport, "Booking Not Assigned To HBL ( Outbound )", ScenarioDetailsHashMap);

		int grid_RowCount=driver.findElements(By.xpath("//form[@name='report']//table[2]//tr")).size();
		System.out.println("grid_RowCount::"+grid_RowCount);
		for (int grid_RowNum = 2; grid_RowNum <= grid_RowCount; grid_RowNum++) {
			String BookingId=GenericMethods.getInnerText(driver, By.xpath("//form[@name='report']//table[2]//tr["+grid_RowNum+"]/td[1]"),null, 10);
			if(BookingId.trim().equalsIgnoreCase(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "BookingId", ScenarioDetailsHashMap))){
				GenericMethods.assertInnerText(driver, By.xpath("//form[@name='report']//table[2]//tr["+grid_RowNum+"]/td[1]"), null, ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "BookingId", ScenarioDetailsHashMap), "BookingId", "Validating Booking ID in Booking Not Assigned to HBL Reports", ScenarioDetailsHashMap);
				GenericMethods.clickElement(driver,By.linkText("Cancel"),null, 10);
				break;
			}
			else if(grid_RowNum==grid_RowCount){
				GenericMethods.clickElement(driver,By.linkText("Next"),null, 10);
				grid_RowNum=1;
			}
		}

	}
	//Pavan FUNC063.8 - Below method will perform Validations for Reports
	public static void OceanMIS_Booking_Without_Console_Validations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.Ocean_BookingWithoutAConsoleReport, "Booking Without CONSOL ( Outbound )", ScenarioDetailsHashMap);

		int grid_RowCount=driver.findElements(By.xpath("//form[@name='report']//table[2]//tr")).size();
		System.out.println("grid_RowCount::"+grid_RowCount);
		for (int grid_RowNum = 2; grid_RowNum <= grid_RowCount; grid_RowNum++) {
			String BookingId=GenericMethods.getInnerText(driver, By.xpath("//form[@name='report']//table[2]//tr["+grid_RowNum+"]/td[1]"),null, 10);
			if(BookingId.trim().equalsIgnoreCase(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "BookingId", ScenarioDetailsHashMap))){
				GenericMethods.assertInnerText(driver, By.xpath("//form[@name='report']//table[2]//tr["+grid_RowNum+"]/td[1]"), null, ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "BookingId", ScenarioDetailsHashMap), "BookingId", "Validating Booking ID in Booking Not Assigned to HBL Reports", ScenarioDetailsHashMap);
				GenericMethods.clickElement(driver,By.linkText("Cancel"),null, 10);
				break;
			}
			else if(grid_RowNum==grid_RowCount){
				GenericMethods.clickElement(driver,By.linkText("Next"),null, 10);
				grid_RowNum=1;
			}
		}

	}

	//Pavan FUNC063.9 - Below method will perform Validations for Reports
	public static void OceanMIS_Console_Not_Closed_Validations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.Ocean_ConsoleNotClosed, "CONSOLE Not Closed ( Outbound )", ScenarioDetailsHashMap);
		int grid_RowCount=driver.findElements(By.xpath("//form[@name='report']//table[2]//tr")).size();
		System.out.println("grid_RowCount::"+grid_RowCount);
		for (int grid_RowNum = 2; grid_RowNum <= grid_RowCount; grid_RowNum++) {
			String ConsoleID=GenericMethods.getInnerText(driver, By.xpath("//form[@name='report']//table[2]//tr["+grid_RowNum+"]/td[1]"),null, 10);
			if(ConsoleID.trim().equalsIgnoreCase(ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap))){
				GenericMethods.assertInnerText(driver, By.xpath("//form[@name='report']//table[2]//tr["+grid_RowNum+"]/td[1]"), null, ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap), "ConsoleId", "Validating Console ID in Console Not Closed Reports", ScenarioDetailsHashMap);
				GenericMethods.clickElement(driver,By.linkText("Cancel"),null, 10);
				break;
			}
			else if(grid_RowNum==grid_RowCount){
				GenericMethods.clickElement(driver,By.linkText("Next"),null, 10);
				grid_RowNum=1;
			}
		}

	}
	
	//Pavan FUNC063.10 - Below method will perform Validations for Reports
	public static void OceanMIS_Console_Status_Validations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.Ocean_ConsoleStatusOutbound, "CONSOLE Status ( Outbound )", ScenarioDetailsHashMap);
		int grid_RowCount=driver.findElements(By.xpath("//form[@name='report']//table[2]//tr")).size();
		System.out.println("grid_RowCount::"+grid_RowCount);
		for (int grid_RowNum = 2; grid_RowNum <= grid_RowCount; grid_RowNum++) {
			String ConsoleID=GenericMethods.getInnerText(driver, By.xpath("//form[@name='report']//table[2]//tr["+grid_RowNum+"]/td[1]"),null, 10);
			if(ConsoleID.trim().equalsIgnoreCase(ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap))){
				GenericMethods.assertInnerText(driver, By.xpath("//form[@name='report']//table[2]//tr["+grid_RowNum+"]/td[1]"), null, ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap), "ConsoleId", "Validating Console ID in Console Status Reports", ScenarioDetailsHashMap);
				GenericMethods.clickElement(driver,By.linkText("Cancel"),null, 10);
				break;
			}
			else if(grid_RowNum==grid_RowCount){
				GenericMethods.clickElement(driver,By.linkText("Next"),null, 10);
				grid_RowNum=1;
			}
		}

	}

	//Pavan FUNC063.11 - Below method will perform Validations for Reports
	public static void OceanMIS_HBL_Not_Assigned_To_Console_Validations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.Ocean_HBL_Not_Assigned_To_Console, "HBL Not Assigned To CONSOLE( Outbound )", ScenarioDetailsHashMap);
		int grid_RowCount=driver.findElements(By.xpath("//form[@name='report']//table[2]//tr")).size();
		System.out.println("grid_RowCount::"+grid_RowCount);
		for (int grid_RowNum = 2; grid_RowNum <= grid_RowCount; grid_RowNum++) {
			String HBL_ID=GenericMethods.getInnerText(driver, By.xpath("//form[@name='report']//table[2]//tr["+grid_RowNum+"]/td[1]"),null, 10);
			if(HBL_ID.trim().equalsIgnoreCase(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap))){
				GenericMethods.assertInnerText(driver, By.xpath("//form[@name='report']//table[2]//tr["+grid_RowNum+"]/td[1]"), null, ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), "HBL_Id", "Validating HBL ID in HBL Not Assigned to HBL Reports", ScenarioDetailsHashMap);
				GenericMethods.clickElement(driver,By.linkText("Cancel"),null, 10);
				break;
			}
			else if(grid_RowNum==grid_RowCount){
				GenericMethods.clickElement(driver,By.linkText("Next"),null, 10);
				grid_RowNum=1;
			}
		}

	}
	//Pavan FUNC063.12 - Below method will perform Validations for Reports
	public static void OceanMIS_Inbound_Deconsolidation_Not_Done_Validations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.Ocean_Inbound_DeconsolidaitonNotDone, "Inbound Deconsolidation Not Done", ScenarioDetailsHashMap);
		int grid_RowCount=driver.findElements(By.xpath("//form[@name='report']//table[2]//tr")).size();
		System.out.println("grid_RowCount::"+grid_RowCount);
		for (int grid_RowNum = 2; grid_RowNum <= grid_RowCount; grid_RowNum++) {
			String MBL_ID=GenericMethods.getInnerText(driver, By.xpath("//form[@name='report']//table[2]//tr["+grid_RowNum+"]/td[1]"),null, 10);
			if(MBL_ID.trim().equalsIgnoreCase(ExcelUtils.getCellData("SE_OBL", RowNo, "OBL_Id", ScenarioDetailsHashMap))){
				GenericMethods.assertInnerText(driver, By.xpath("//form[@name='report']//table[2]//tr["+grid_RowNum+"]/td[1]"), null, ExcelUtils.getCellData("SE_OBL", RowNo, "OBL_Id", ScenarioDetailsHashMap), "OBL_Id", "Validating OBL ID in Inbound Deconsolidation Not Done Reports", ScenarioDetailsHashMap);
				GenericMethods.clickElement(driver,By.linkText("Cancel"),null, 10);
				break;
			}
			else if(grid_RowNum==grid_RowCount){
				GenericMethods.clickElement(driver,By.linkText("Next"),null, 10);
				grid_RowNum=1;
			}
		}

	}
	//Pavan FUNC063.13 - Below method will perform Validations for Reports
	public static void OceanMIS_HBL_With_No_DeliveryOrder_Validations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.Ocean_HBL_With_No_DO, "Inbound HBL With No Delivery Order", ScenarioDetailsHashMap);
		int grid_RowCount=driver.findElements(By.xpath("//form[@name='report']//table[2]//tr")).size();
		System.out.println("grid_RowCount::"+grid_RowCount);
		for (int grid_RowNum = 2; grid_RowNum <= grid_RowCount; grid_RowNum++) {
			String HBL_ID=GenericMethods.getInnerText(driver, By.xpath("//form[@name='report']//table[2]//tr["+grid_RowNum+"]/td[1]"),null, 10);
			if(HBL_ID.trim().equalsIgnoreCase(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap))){
				GenericMethods.assertInnerText(driver, By.xpath("//form[@name='report']//table[2]//tr["+grid_RowNum+"]/td[1]"), null, ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), "HBLId", "Validating HBL ID in HBL with No DO Reports", ScenarioDetailsHashMap);
				GenericMethods.clickElement(driver,By.linkText("Cancel"),null, 10);
				break;
			}
			else if(grid_RowNum==grid_RowCount){
				GenericMethods.clickElement(driver,By.linkText("Next"),null, 10);
				grid_RowNum=1;
			}
		}

	}



}
