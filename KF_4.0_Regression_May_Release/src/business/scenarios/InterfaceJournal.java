package business.scenarios;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import global.reusables.ExcelUtils;
import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;
import DriverMethods.WebDriverInitilization;
import app.reuseables.AppReusableMethods;
import app.reuseables.ETransMenu;

public class InterfaceJournal extends WebDriverInitilization{

	static ObjectRepository orCommon=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/CommonObjects.xml");
	static ObjectRepository orHBL=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/HBL.xml");
	static ObjectRepository orSeaBooking=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/SeaBooking.xml");
	static ObjectRepository orInterfaceJournal=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/InterfaceJournal.xml");
	static ObjectRepository orAuditTrail=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/AuditTrail.xml");

	public static void SearchdocNo(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) 
	{
		AppReusableMethods.selectMenu(driver,ETransMenu.InterfaceLogData,"Interface Journal", ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(4000);
		ExcelUtils.setCellData("InterfaceJournal_Milestones", RowNo, "BookingId", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "BookingId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null , orInterfaceJournal.getElement(driver, "DocNo_Search", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingId", ScenarioDetailsHashMap), 10);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 2), 10);
		GenericMethods.pauseExecution(3000);
	}
	public static void CapturingDocIDFromAuditTrail(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) 
	{
		SeaHBL.seaHBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "AuditButton",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null,orAuditTrail.getElement(driver, "InterfaceAudit",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		System.out.println("getattribute value"+GenericMethods.readValue(driver, orAuditTrail.getElement(driver, "InterfaceAudit_DocId", ScenarioDetailsHashMap, 10), null));
		System.out.println("getinnertext="+GenericMethods.getInnerText(driver, null, orAuditTrail.getElement(driver, "InterfaceAudit_DocId", ScenarioDetailsHashMap, 10), 2));
		String ActualDocID = GenericMethods.readValue(driver, orAuditTrail.getElement(driver, "InterfaceAudit_DocId", ScenarioDetailsHashMap, 10), null);
		ExcelUtils.setCellData("InterfaceJournal_Milestones", RowNo, "DocID", ActualDocID, ScenarioDetailsHashMap);
		ExcelUtils.setCellData("SE_HBLMainDetails", RowNo, "DocID", ActualDocID, ScenarioDetailsHashMap);
	}

	public static void SearchdocId(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) 

	{
		AppReusableMethods.selectMenu(driver,ETransMenu.InterfaceLogData,"Interface Journal", ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(4000);
		//ExcelUtils.setCellData("InterfaceJournal_Milestones", RowNo, "BookingId", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "DocID", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null , orInterfaceJournal.getElement(driver, "DocId_Search", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DocID", ScenarioDetailsHashMap), 10);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 2), 10);
		GenericMethods.pauseExecution(3000);
	}

	public static void BookingCreatedEvent(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocNo(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "Booking_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "Booking_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(7000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					//GenericMethods.pauseExecution(7000);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "Booking_StatusEventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "Booking_StatusEventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "Booking_StatusEventCode", ScenarioDetailsHashMap)+" after creating Booking", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "Booking_StatusEventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "Booking_StatusEventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after creating Booking", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingTextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingTextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingTextLiteral", ScenarioDetailsHashMap)+" after creating Booking", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingTextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingTextLiteral_WithTag", ScenarioDetailsHashMap)+" tag is not found after creating Booking", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}
		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "Booking_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "Booking_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "Booking_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "Booking_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(10000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
		GenericMethods.replaceTextofTextField(driver, By.id("subType"), null, "435354", 10);


	}

	public static void BookingModifiedEvent(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocNo(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingModify_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingModify_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(7000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					//GenericMethods.pauseExecution(7000);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingModify_StatusEventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingModify_StatusEventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingModify_StatusEventCode", ScenarioDetailsHashMap)+" after modify Booking", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingModify_StatusEventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingModify_StatusEventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after modify Booking", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingModifyTextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingModifyTextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingModifyTextLiteral", ScenarioDetailsHashMap)+" after modify Booking", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingModifyTextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingModifyTextLiteral_WithTag", ScenarioDetailsHashMap)+"  tag is not found after modify Booking", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}
		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingModify_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingModify_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingModify_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BookingModify_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(10000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
		GenericMethods.replaceTextofTextField(driver, By.id("subType"), null, "435354", 10);


	}

	public static void BookingPICKUP_INSTRUCTIONSEvent(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocNo(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false;
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PICKUPINSTRUCTIONS_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PICKUPINSTRUCTIONS_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(3000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					//GenericMethods.pauseExecution(7000);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PICKUPINSTRUCTIONS_StatusEventCoded_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PICKUPINSTRUCTIONS_StatusEventCoded", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PICKUPINSTRUCTIONS_StatusEventCoded", ScenarioDetailsHashMap)+" after creating Pickup Instruction", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PICKUPINSTRUCTIONS_StatusEventCoded_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PICKUPINSTRUCTIONS_StatusEventCoded_WithTag", ScenarioDetailsHashMap)+" tag is not found after creating Pickup Instruction", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PICKUPINSTRUCTIONS_TextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PICKUPINSTRUCTIONS_TextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PICKUPINSTRUCTIONS_TextLiteral", ScenarioDetailsHashMap)+" after creating Pickup Instruction", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PICKUPINSTRUCTIONS_TextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PICKUPINSTRUCTIONS_TextLiteral_WithTag", ScenarioDetailsHashMap)+"  tag is not found after creating Pickup Instruction", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}
		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PICKUPINSTRUCTIONS_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PICKUPINSTRUCTIONS_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PICKUPINSTRUCTIONS_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PICKUPINSTRUCTIONS_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}
		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(10000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
		GenericMethods.replaceTextofTextField(driver, By.id("subType"), null, "435354", 10);

	}

	public static void BookingPRSEvent(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocNo(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PRS_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PRS_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PRS_StatusEventCoded_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PRS_StatusEventCoded", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PRS_StatusEventCoded", ScenarioDetailsHashMap)+" after creating PRS", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PRS_StatusEventCoded_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PRS_StatusEventCoded_WithTag", ScenarioDetailsHashMap)+" tag  is not found after creating PRS", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PRS_TextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PRS_TextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PRS_TextLiteral", ScenarioDetailsHashMap)+" after creating PRS", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PRS_TextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PRS_TextLiteral_WithTag", ScenarioDetailsHashMap)+" tag is not found after creating PRS", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PRS_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PRS_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PRS_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PRS_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}
		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(10000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
		GenericMethods.replaceTextofTextField(driver, By.id("subType"), null, "435354", 10);
	}

	public static void OriginWarehouseEvent(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocNo(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ORIGIN_WAREHOUSE_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ORIGIN_WAREHOUSE_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ORIGIN_WAREHOUSE_StatusEventCoded_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ORIGIN_WAREHOUSE_StatusEventCoded", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ORIGIN_WAREHOUSE_StatusEventCoded", ScenarioDetailsHashMap)+" after creating Warehouse", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ORIGIN_WAREHOUSE_StatusEventCoded_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ORIGIN_WAREHOUSE_StatusEventCoded_WithTag", ScenarioDetailsHashMap)+" tag  is not found after creating Warehouse", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ORIGIN_WAREHOUSE_TextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ORIGIN_WAREHOUSE_TextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ORIGIN_WAREHOUSE_TextLiteral", ScenarioDetailsHashMap)+" after creating Warehouse", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ORIGIN_WAREHOUSE_TextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ORIGIN_WAREHOUSE_TextLiteral_WithTag", ScenarioDetailsHashMap)+" tag is not found after creating Warehouse", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ORIGIN_WAREHOUSE_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ORIGIN_WAREHOUSE_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ORIGIN_WAREHOUSE_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ORIGIN_WAREHOUSE_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(10000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
		GenericMethods.replaceTextofTextField(driver, By.id("subType"), null, "435354", 10);
	}

	public static void HBLCreateEvent(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBL_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBL_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLStatusEventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBL_StatusEventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBL_StatusEventCode", ScenarioDetailsHashMap)+" after creating HBL", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLStatusEventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLStatusEventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after creating HBL", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLTextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLTextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLTextLiteral", ScenarioDetailsHashMap)+" after creating HBL", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLTextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLTextLiteral_WithTag", ScenarioDetailsHashMap)+" tag is not found after creating HBL", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBL_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBL_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBL_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBL_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}
		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
		GenericMethods.replaceTextofTextField(driver, By.id("subType"), null, "435354", 10);
	}

	public static void HBL_CHARGE_SATISFIED_Event(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "CHARGE_SATISFIED_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "CHARGE_SATISFIED_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "CHARGE_SATISFIEDEventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "CHARGE_SATISFIEDEventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "CHARGE_SATISFIEDEventCode", ScenarioDetailsHashMap)+" after selects the charge status as SATISFIED in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "CHARGE_SATISFIEDEventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "CHARGE_SATISFIEDEventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after selects the charge status as SATISFIED in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "CHARGE_SATISFIEDTextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "CHARGE_SATISFIEDTextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ChargeDueTextLiteral", ScenarioDetailsHashMap)+" after selects the charge status as SATISFIED in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "CHARGE_SATISFIEDTextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "CHARGE_SATISFIEDTextLiteral_WithTag", ScenarioDetailsHashMap)+" after selects the charge status as SATISFIED in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "CHARGE_SATISFIED_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "CHARGE_SATISFIED_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "CHARGE_SATISFIED_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "CHARGE_SATISFIED_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}
		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
		GenericMethods.replaceTextofTextField(driver, By.id("subType"), null, "435354", 10);
	}

	public static void HBL_CHARGE_DUE_Event(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ChargeDue_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ChargeDue_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLStatusEventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ChargeDue_StatusEventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ChargeDue_StatusEventCode", ScenarioDetailsHashMap)+" after selects the charge status as DUE in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ChargeDueStatusEventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLStatusEventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after selects the charge status as DUE in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ChargeDueTextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ChargeDueTextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ChargeDueTextLiteral", ScenarioDetailsHashMap)+" after selects the charge status as DUE in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ChargeDueTextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ChargeDueTextLiteral_WithTag", ScenarioDetailsHashMap)+" after selects the charge status as DUE in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ChargeDue_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ChargeDue_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ChargeDue_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ChargeDue_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
		GenericMethods.replaceTextofTextField(driver, By.id("subType"), null, "435354", 10);
	}

	public static void HBL_BL_DUE_Event(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false;
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BLDue_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BLDue_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BLDueStatusEventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BLDue_StatusEventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BLDue_StatusEventCode", ScenarioDetailsHashMap)+" after selects the freight status as DUE in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BLDueStatusEventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BLDueStatusEventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after selects the freight status as DUE in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BLDueTextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BLDueTextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BLDueTextLiteral", ScenarioDetailsHashMap)+" after selects the freight status as DUE in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BLDueTextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BLDueTextLiteral_WithTag", ScenarioDetailsHashMap)+" after selects the freight status as DUE in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true;
			}
			if(count==1)
			{
				break;
			}
			

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BLDue_OperationType", ScenarioDetailsHashMap) +" AND "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BLDue_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BLDue_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BLDue_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
		GenericMethods.replaceTextofTextField(driver, By.id("subType"), null, "435354", 10);
	}

	public static void HBL_BL_SATISFIED_Event(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false;
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BL_SATISFIED_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BL_SATISFIED_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BL_SATISFIEDEventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BL_SATISFIEDEventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BL_SATISFIEDEventCode", ScenarioDetailsHashMap)+" after selects the freight status as SATISFIED in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BL_SATISFIEDEventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BL_SATISFIEDEventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after selects the freight status as SATISFIED in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BL_SATISFIEDTextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BL_SATISFIEDTextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BL_SATISFIEDTextLiteral", ScenarioDetailsHashMap)+" after selects the freight status as SATISFIED in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BL_SATISFIEDTextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BL_SATISFIEDTextLiteral_WithTag", ScenarioDetailsHashMap)+" after selects the freight status as SATISFIED in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true;
			}
			if(count==1)
			{
				break;
			}
			

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BL_SATISFIED_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BL_SATISFIED_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BL_SATISFIED_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "BL_SATISFIED_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
		GenericMethods.replaceTextofTextField(driver, By.id("subType"), null, "435354", 10);
	}

	public static void HBL_SUBMIT_TO_CUSTOMS_Event(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false;
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SUBMIT_TO_CUSTOMS_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SUBMIT_TO_CUSTOMS_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SUBMIT_TO_CUSTOMSEventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SUBMIT_TO_CUSTOMSEventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SUBMIT_TO_CUSTOMSEventCode", ScenarioDetailsHashMap)+" after selects the Customs as submit To Custom in Notes and Alert", ScenarioDetailsHashMap);						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SUBMIT_TO_CUSTOMSEventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SUBMIT_TO_CUSTOMSEventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after selects the Customs as submit To Custom in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SUBMIT_TO_CUSTOMSTextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SUBMIT_TO_CUSTOMSTextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SUBMIT_TO_CUSTOMSTextLiteral", ScenarioDetailsHashMap)+" after selects the Customs as submit To Custom in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SUBMIT_TO_CUSTOMSTextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SUBMIT_TO_CUSTOMSTextLiteral_WithTag", ScenarioDetailsHashMap)+" after selects the Customs as submit To Custom in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true;
			}
			if(count==1)
			{
				break;
			}
			

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SUBMIT_TO_CUSTOMS_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SUBMIT_TO_CUSTOMS_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SUBMIT_TO_CUSTOMS_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SUBMIT_TO_CUSTOMS_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
	}

	public static void HBL_SHIPMENT_RELEASED_Event(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false;
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_RELEASED_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_RELEASED_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_RELEASEDEventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_RELEASEDEventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_RELEASEDEventCode", ScenarioDetailsHashMap)+" after selects the Shipment Status as Shipment Released in Notes and Alert", ScenarioDetailsHashMap);						
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_RELEASEDEventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_RELEASEDEventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after selects the Shipment Status as Shipment Released in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_RELEASEDTextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_RELEASEDTextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_RELEASEDTextLiteral", ScenarioDetailsHashMap)+" after selects the Shipment Status as Shipment Released in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_RELEASEDTextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_RELEASEDTextLiteral_WithTag", ScenarioDetailsHashMap)+" after selects the Customs as Shipment Released in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true;
			}
			if(count==1)
			{
				break;
			}
			

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_RELEASED_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_RELEASED_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_RELEASED_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_RELEASED_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
	}

	public static void HBL_SHIPMENT_ON_HOLD_Event(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false;
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_ON_HOLD_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_ON_HOLD_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_ON_HOLDEventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_ON_HOLDEventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_ON_HOLDEventCode", ScenarioDetailsHashMap)+" after selects the Shipment Status as Shipment on Hold in Notes and Alert", ScenarioDetailsHashMap);						
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_ON_HOLDEventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_ON_HOLDEventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after selects the Shipment Status as Shipment on Hold in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_ON_HOLDTextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_ON_HOLDTextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_ON_HOLDTextLiteral", ScenarioDetailsHashMap)+" after selects the Shipment Status as Shipment on Hold in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_ON_HOLDTextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_ON_HOLDTextLiteral_WithTag", ScenarioDetailsHashMap)+" after selects the Customs as Shipment on Hold in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true;
			}
			if(count==1)
			{
				break;
			}
			

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_ON_HOLDTextLiteral", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_ON_HOLD_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_ON_HOLD_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_ON_HOLD_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
	}

	public static void HBL_SHIPMENT_PARTIALLYRELEASED_Event(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false;
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_PAR_RELEASED_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_PAR_RELEASED_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_PAR_RELEASEDEventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_PAR_RELEASEDEventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_PAR_RELEASEDEventCode", ScenarioDetailsHashMap)+" after selects the Shipment Status as Shipment Partially Released in Notes and Alert", ScenarioDetailsHashMap);						
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_PAR_RELEASEDEventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_PAR_RELEASEDEventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after selects the Shipment Status as Shipment Partially Released in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_PAR_RELEASEDTextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_PAR_RELEASEDTextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_PAR_RELEASEDTextLiteral", ScenarioDetailsHashMap)+" after selects the Shipment Status as Shipment Partially Released in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_PAR_RELEASEDTextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_PAR_RELEASEDTextLiteral_WithTag", ScenarioDetailsHashMap)+" after selects the Customs as Shipment Partially Released in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true;
			}
			if(count==1)
			{
				break;
			}
			

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_PAR_RELEASED_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_PAR_RELEASED_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_PAR_RELEASED_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SHIPMENT_PAR_RELEASED_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
	}

	public static void HBL_INTERNAL_NOTES_Event(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false;
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INTERNAL_NOTES_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INTERNAL_NOTES_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INTERNAL_NOTES_EventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INTERNAL_NOTES_EventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INTERNAL_NOTES_EventCode", ScenarioDetailsHashMap)+" after selects the Notes Category as Shipment Internal Notes in Notes and Alert", ScenarioDetailsHashMap);						
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INTERNAL_NOTES_EventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INTERNAL_NOTES_EventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after selects the Notes Category as Shipment Internal Notes in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INTERNAL_NOTES_TextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INTERNAL_NOTES_TextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INTERNAL_NOTES_TextLiteral", ScenarioDetailsHashMap)+" after selects the Notes Category as Shipment Internal Notes in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INTERNAL_NOTES_TextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INTERNAL_NOTES_TextLiteral_WithTag", ScenarioDetailsHashMap)+" after selects the Notes Category as Shipment Internal Notes in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true;
			}
			if(count==1)
			{
				break;
			}
			

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INTERNAL_NOTES_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INTERNAL_NOTES_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INTERNAL_NOTES_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INTERNAL_NOTES_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
	}

	public static void HBL_EXTERNAL_NOTES_Event(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false;
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "EXTERNAL_NOTES_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "EXTERNAL_NOTES_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "EXTERNAL_NOTES_EventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "EXTERNAL_NOTES_EventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "EXTERNAL_NOTES_EventCode", ScenarioDetailsHashMap)+" after selects the Notes Category as Shipment External Notes in Notes and Alert", ScenarioDetailsHashMap);						
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "EXTERNAL_NOTES_EventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "EXTERNAL_NOTES_EventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after selects the Notes Category as Shipment External Notes in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "EXTERNAL_NOTES_TextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "EXTERNAL_NOTES_TextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "EXTERNAL_NOTES_TextLiteral", ScenarioDetailsHashMap)+" after selects the Notes Category as Shipment External Notes in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "EXTERNAL_NOTES_TextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "EXTERNAL_NOTES_TextLiteral_WithTag", ScenarioDetailsHashMap)+" after selects the Notes Category as Shipment External Notes in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true;
			}
			if(count==1)
			{
				break;
			}
			

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "EXTERNAL_NOTES_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "EXTERNAL_NOTES_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "EXTERNAL_NOTES_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "EXTERNAL_NOTES_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
	}

	public static void HBL_TRACK_NOTES_Event(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false;
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "TRACK_NOTES_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "TRACK_NOTES_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "TRACK_NOTES_EventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "TRACK_NOTES_EventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "TRACK_NOTES_EventCode", ScenarioDetailsHashMap)+" after selects the Notes Category as Shipment Track Notes in Notes and Alert", ScenarioDetailsHashMap);						
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "TRACK_NOTES_EventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "TRACK_NOTES_EventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after selects the Notes Category as Shipment Track Notes in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "TRACK_NOTES_TextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "TRACK_NOTES_TextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "TRACK_NOTES_TextLiteral", ScenarioDetailsHashMap)+" after selects the Notes Category as Shipment Track Notes in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "TRACK_NOTES_TextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "TRACK_NOTES_TextLiteral_WithTag", ScenarioDetailsHashMap)+" after selects the Notes Category as Shipment Track Notes in Notes and Alert", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true;
			}
			if(count==1)
			{
				break;
			}
			

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "TRACK_NOTES_OperationType", ScenarioDetailsHashMap) +" and "+
					ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "TRACK_NOTES_EventType", ScenarioDetailsHashMap), "Operation Type "+
					ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "TRACK_NOTES_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+
					ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "TRACK_NOTES_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
	}
	
	public static void HBL_INVOICE_ISSUED_Event(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INVOICE_ISSUED_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INVOICE_ISSUED_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INVOICE_ISSUEDEventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INVOICE_ISSUEDEventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INVOICE_ISSUEDEventCode", ScenarioDetailsHashMap)+" after invoice raise against HBL", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INVOICE_ISSUEDEventCode", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INVOICE_ISSUEDEventCode", ScenarioDetailsHashMap)+" after invoice raise against HBL", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INVOICE_ISSUEDTextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INVOICE_ISSUEDTextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INVOICE_ISSUEDTextLiteral", ScenarioDetailsHashMap)+" after invoice raise against HBL", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INVOICE_ISSUEDTextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INVOICE_ISSUEDTextLiteral_WithTag", ScenarioDetailsHashMap)+" after invoice raise against HBL", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INVOICE_ISSUED_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INVOICE_ISSUED_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INVOICE_ISSUED_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "INVOICE_ISSUED_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}


		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
		GenericMethods.replaceTextofTextField(driver, By.id("subType"), null, "435354", 10);
	}


	public static void HBLModifyEvent(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLModify_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLModify_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLModify_StatusEventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLModify_StatusEventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLModify_StatusEventCode", ScenarioDetailsHashMap)+" after modifing HBL", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLModify_StatusEventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLModify_StatusEventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after modifing HBL", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLModifyTextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLModifyTextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLModifyTextLiteral", ScenarioDetailsHashMap)+" after modifing HBL", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLModifyTextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLModifyTextLiteral_WithTag", ScenarioDetailsHashMap)+" tag is not found after modifing HBL", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLModify_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLModify_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLModify_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "HBLModify_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
		GenericMethods.replaceTextofTextField(driver, By.id("subType"), null, "435354", 10);
	}

	public static void MBLCreateEvent(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBLStatusEventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_StatusEventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_StatusEventCode", ScenarioDetailsHashMap)+" after creating MBL", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBLStatusEventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBLStatusEventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after creating MBL", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBLTextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBLTextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBLTextLiteral", ScenarioDetailsHashMap)+" after creating MBL", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBLTextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBLTextLiteral_WithTag", ScenarioDetailsHashMap)+" tag is not found after creating MBL", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
		GenericMethods.replaceTextofTextField(driver, By.id("subType"), null, "435354", 10);
	}

	public static void MBL_LOADED_ON_BOARDEvent(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MB_LOADED_ON_BOARDL_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_LOADED_ON_BOARD_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_LOADED_ON_BOARDStatusEventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_LOADED_ON_BOARD_StatusEventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_LOADED_ON_BOARD_StatusEventCode", ScenarioDetailsHashMap)+" after creating MBL", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_LOADED_ON_BOARDStatusEventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_LOADED_ON_BOARDStatusEventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after creating MBL", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_LOADED_ON_BOARDTextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_LOADED_ON_BOARDTextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_LOADED_ON_BOARDTextLiteral", ScenarioDetailsHashMap)+" after creating MBL", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_LOADED_ON_BOARDTextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_LOADED_ON_BOARDTextLiteral_WithTag", ScenarioDetailsHashMap)+" tag is not found after creating MBL", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MB_LOADED_ON_BOARDL_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_LOADED_ON_BOARD_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MB_LOADED_ON_BOARDL_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_LOADED_ON_BOARD_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}


		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
		GenericMethods.replaceTextofTextField(driver, By.id("subType"), null, "435354", 10);
	}

	public static void MBL_SSL_RELEASEDEvent(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SSL_RELEASED_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SSL_RELEASED_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SSL_RELEASED_EventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SSL_RELEASED_EventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SSL_RELEASED_EventCode", ScenarioDetailsHashMap)+" after selecting SSL as Released in Ocean Consolidation Notes and Status tab", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SSL_RELEASED_EventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SSL_RELEASED_EventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after selecting SSL as Released in Ocean Consolidation Notes and Status tab", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SSL_RELEASED_TextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SSL_RELEASED_TextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SSL_RELEASED_TextLiteral", ScenarioDetailsHashMap)+" after selecting SSL as Released in Ocean Consolidation Notes and Status tab", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SSL_RELEASED_TextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SSL_RELEASED_TextLiteral_WithTag", ScenarioDetailsHashMap)+" tag is not found after selecting SSL as Released in Ocean Consolidation Notes and Status tab", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SSL_RELEASED_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SSL_RELEASED_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SSL_RELEASED_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SSL_RELEASED_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}


		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
		//GenericMethods.replaceTextofTextField(driver, By.id("subType"), null, "435354", 10);
	}

	public static void MBLCloseEvent(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_Closed_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_Closed_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_Closed_EventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_Closed_EventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_Closed_EventCode", ScenarioDetailsHashMap)+" after closing MBL", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_Closed_EventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_Closed_EventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after closing MBL", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_Closed_TextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_Closed_TextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_Closed_TextLiteral", ScenarioDetailsHashMap)+" after closing MBL", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_Closed_TextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_Closed_TextLiteral_WithTag", ScenarioDetailsHashMap)+" tag is not found after closing MBL", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_Closed_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_Closed_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_Closed_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "MBL_Closed_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
		GenericMethods.replaceTextofTextField(driver, By.id("subType"), null, "435354", 10);
	}

	public static void PreArrivalEvent(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PreArrivalEvent_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PreArrivalEvent_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PreArrivalEvent_EventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PreArrivalEvent_EventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PreArrivalEvent_EventCode", ScenarioDetailsHashMap)+" after arrival confirmation", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PreArrivalEvent_EventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PreArrivalEvent_EventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after arrival confirmation", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PreArrivalEvent_TextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PreArrivalEvent_TextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PreArrivalEvent_TextLiteral", ScenarioDetailsHashMap)+" after arrival confirmation", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PreArrivalEvent_TextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PreArrivalEvent_TextLiteral_WithTag", ScenarioDetailsHashMap)+" tag is not found after arrival confirmation", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PreArrivalEvent_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PreArrivalEvent_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PreArrivalEvent_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PreArrivalEvent_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
		GenericMethods.replaceTextofTextField(driver, By.id("subType"), null, "435354", 10);
	}

	public static void OCEAN_DECONSOLIDATIONEvent(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "OCEAN_DECONSOLIDATION_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "OCEAN_DECONSOLIDATION_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "OCEAN_DECONSOLIDATION_EventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "OCEAN_DECONSOLIDATION_EventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "OCEAN_DECONSOLIDATION_EventCode", ScenarioDetailsHashMap)+" after arrival confirmation done and modified", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "OCEAN_DECONSOLIDATION_EventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "OCEAN_DECONSOLIDATION_EventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after arrival confirmation done and modified", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "OCEAN_DECONSOLIDATION_TextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "OCEAN_DECONSOLIDATION_TextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "OCEAN_DECONSOLIDATION_TextLiteral", ScenarioDetailsHashMap)+" after arrival confirmation done and modified", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "OCEAN_DECONSOLIDATION_TextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "OCEAN_DECONSOLIDATION_TextLiteral_WithTag", ScenarioDetailsHashMap)+" tag is not found after arrival confirmation done and modified", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "OCEAN_DECONSOLIDATION_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "OCEAN_DECONSOLIDATION_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "OCEAN_DECONSOLIDATION_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "OCEAN_DECONSOLIDATION_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
	}


	public static void OCEAN_ARRIVALNOTICE_GRPRNT_Event(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ArrivalNoticeDeliveryArranged_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ArrivalNoticeDeliveryArranged_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ArrivalNoticeDeliveryArrangedEventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ArrivalNoticeDeliveryArranged_EventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ArrivalNoticeDeliveryArranged_EventCode", ScenarioDetailsHashMap)+" after printing Arrival Notice from Group Print option", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ArrivalNoticeDeliveryArrangedEventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ArrivalNoticeDeliveryArrangedEventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after printing Arrival Notice from Group Print option", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ArrivalNoticeDeliveryArranged_TextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ArrivalNoticeDeliveryArranged_TextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ArrivalNoticeDeliveryArranged_TextLiteral", ScenarioDetailsHashMap)+" after printing Arrival Notice from Group Print option", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ArrivalNoticeDeliveryArranged_TextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ArrivalNoticeDeliveryArranged_TextLiteral_WithTag", ScenarioDetailsHashMap)+" tag is not found after printing Arrival Notice from Group Print option", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ArrivalNoticeDeliveryArranged_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ArrivalNoticeDeliveryArranged_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ArrivalNoticeDeliveryArranged_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "ArrivalNoticeDeliveryArranged_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
	}
	public static void OCEAN_DRSOpenEvent(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Open_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Open_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Open_EventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Open_EventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Open_EventCode", ScenarioDetailsHashMap)+" after DRS Open", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Open_EventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Open_EventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after DRS Open", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Open_TextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Open_TextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Open_TextLiteral", ScenarioDetailsHashMap)+" after DRS Open", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Open_TextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Open_TextLiteral_WithTag", ScenarioDetailsHashMap)+" tag is not found after DRS Open", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Open_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Open_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Open_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Open_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
	}

	public static void OCEAN_DRSCloseEvent(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Close_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Close_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Close_EventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Close_EventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Close_EventCode", ScenarioDetailsHashMap)+" after DRS close", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Close_EventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Close_EventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after DRS close", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Close_TextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Close_TextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Close_TextLiteral", ScenarioDetailsHashMap)+" after DRS close", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Close_TextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Close_TextLiteral_WithTag", ScenarioDetailsHashMap)+" tag is not found after DRS close", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Close_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Close_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Close_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DRS_Close_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
	}

	public static void OCEAN_PODEvent(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "POD_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "POD_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "POD_EventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "POD_EventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "POD_EventCode", ScenarioDetailsHashMap)+" after POD Done", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "POD_EventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "POD_EventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after POD Done", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "POD_TextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "POD_TextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "POD_TextLiteral", ScenarioDetailsHashMap)+" after POD Done", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "POD_TextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "POD_TextLiteral_WithTag", ScenarioDetailsHashMap)+" tag is not found after POD Done", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "POD_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "POD_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "POD_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "POD_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
	}


	public static void OCEAN_FULLPODEvent(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "FULLPOD_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "FULLPOD_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "FULLPOD_EventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "FULLPOD_EventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "FULLPOD_EventCode", ScenarioDetailsHashMap)+" after entering POD Date while creating POD", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "FULLPOD_EventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "FULLPOD_EventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after entering POD Date while creating POD", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "FULLPOD_TextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "FULLPOD_TextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "FULLPOD_TextLiteral", ScenarioDetailsHashMap)+" after entering POD Date while creating POD", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "FULLPOD_TextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "FULLPOD_TextLiteral_WithTag", ScenarioDetailsHashMap)+" tag is not found after entering POD Date while creating POD", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "FULLPOD_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "FULLPOD_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "FULLPOD_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "FULLPOD_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
	}

	public static void OCEAN_PU_CONFIRMATION_PODEvent(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PU_CONFIRMATION_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PU_CONFIRMATION_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PU_CONFIRMATION_EventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PU_CONFIRMATION_EventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PU_CONFIRMATION_EventCode", ScenarioDetailsHashMap)+" after entering  PU Confirmation Date while creating POD", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PU_CONFIRMATION_EventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PU_CONFIRMATION_EventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after entering PU Confirmation Date while creating POD", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PU_CONFIRMATION_TextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PU_CONFIRMATION_TextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PU_CONFIRMATION_TextLiteral", ScenarioDetailsHashMap)+" after entering PU Confirmation Date while creating POD", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PU_CONFIRMATION_TextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PU_CONFIRMATION_TextLiteral_WithTag", ScenarioDetailsHashMap)+" tag is not found after entering PU Confirmation Date while creating POD", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PU_CONFIRMATION_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PU_CONFIRMATION_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PU_CONFIRMATION_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "PU_CONFIRMATION_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
	}


	public static void OCEAN_DO_ISSUANCE_PODEvent(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		SearchdocId(driver, ScenarioDetailsHashMap, RowNo);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DO_ISSUANCE_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DO_ISSUANCE_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DO_ISSUANCE_EventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DO_ISSUANCE_EventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DO_ISSUANCE_EventCode", ScenarioDetailsHashMap)+" after entering DO Issuance Date while creating POD", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DO_ISSUANCE_EventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DO_ISSUANCE_EventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after entering DO Issuance Date while creating POD", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DO_ISSUANCE_TextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DO_ISSUANCE_TextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DO_ISSUANCE_TextLiteral", ScenarioDetailsHashMap)+" after entering DO Issuance Date while creating POD", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DO_ISSUANCE_TextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DO_ISSUANCE_TextLiteral_WithTag", ScenarioDetailsHashMap)+" tag is not found after entering DO Issuance Date while creating POD", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DO_ISSUANCE_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DO_ISSUANCE_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DO_ISSUANCE_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DO_ISSUANCE_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
	}


	public static void SETTLEMENT_CLOSED_Event(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		AppReusableMethods.selectMenu(driver,ETransMenu.InterfaceLogData,"Interface Journal", ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(4000);
		//ExcelUtils.setCellData("InterfaceJournal_Milestones", RowNo, "BookingId", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "DocID", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null , orInterfaceJournal.getElement(driver, "DocNo_Search", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "DocID", ScenarioDetailsHashMap), 10);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 2), 10);
		GenericMethods.pauseExecution(3000);
		System.out.println("Grid RowCount"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size());
		int count=0;
		Boolean flag = false; 
		for (int GridRow = 1; GridRow <= driver.findElements(By.xpath("html/body/form/table/tbody/tr[6]/td/div[1]/table/tbody/tr")).size(); GridRow++) 
		{
			String ActualOperationType = GenericMethods.getInnerText(driver, By.id("dtoperationType"+GridRow), null, 2);
			String ActualEventType = GenericMethods.getInnerText(driver, By.id("dteventType"+GridRow), null, 2);
			System.out.println("ActualOperationType"+ActualOperationType);
			System.out.println("ActualEventType"+ActualEventType);
			if(ActualOperationType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SETTLEMENT_CLOSED_OperationType", ScenarioDetailsHashMap))&&ActualEventType.equalsIgnoreCase(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SETTLEMENT_CLOSED_EventType", ScenarioDetailsHashMap)))
			{
				try {
					GenericMethods.pauseExecution(5000);
					GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData"+GridRow+"']/a/img"), null, 10);
					System.out.println("b4****"+driver.getTitle());
					GenericMethods.selectWindow(driver);
					System.out.println("a4****"+driver.getTitle());
					Thread.sleep(2000);
					String BookingPageSource = driver.getPageSource();
					//System.out.println("BookingPageSource"+BookingPageSource);
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SETTLEMENT_CLOSED_EventCode_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_StatusEventCode = (BookingPageSource.split("<StatusEventCoded>")[1]).split("</StatusEventCoded>")[0];
						GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SETTLEMENT_CLOSED_EventCode", ScenarioDetailsHashMap), "Validating milestone StatusEventCoded as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SETTLEMENT_CLOSED_EventCode", ScenarioDetailsHashMap)+" after doing profit share settelment", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SETTLEMENT_CLOSED_EventCode_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SETTLEMENT_CLOSED_EventCode_WithTag", ScenarioDetailsHashMap)+" tag is not found after doing profit share settelment", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}
					if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SETTLEMENT_CLOSED_TextLiteral_WithTag", ScenarioDetailsHashMap)))
					{
						String ActualBooking_TextLiteral = (BookingPageSource.split("<TextLiteral>")[1]).split("</TextLiteral>")[0];
						GenericMethods.assertTwoValues(ActualBooking_TextLiteral, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SETTLEMENT_CLOSED_TextLiteral", ScenarioDetailsHashMap), "Validating milestone TextLiteral as "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SETTLEMENT_CLOSED_TextLiteral", ScenarioDetailsHashMap)+" after doing profit share settelment", ScenarioDetailsHashMap);
						System.out.println("tag available");

					}
					else
					{
						GenericMethods.assertTwoValues("EMPTY", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SETTLEMENT_CLOSED_TextLiteral_WithTag", ScenarioDetailsHashMap), ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SETTLEMENT_CLOSED_TextLiteral_WithTag", ScenarioDetailsHashMap)+" tag is not found after doing profit share settelment", ScenarioDetailsHashMap);
						System.out.println("tag Not available");
					}

					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(4000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					GenericMethods.pauseExecution(4000);
				} catch (AWTException e) {e.printStackTrace();
				} catch (Exception e){e.printStackTrace();
				Thread.sleep(2000);
				}
				count=count+1;
				flag = true; 
			}
			if(count==1)
			{
				break;
			}

		}
		if(!flag.equals(true))

		{
			GenericMethods.assertTwoValues("null", ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SETTLEMENT_CLOSED_OperationType", ScenarioDetailsHashMap) +" and "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SETTLEMENT_CLOSED_EventType", ScenarioDetailsHashMap), "Operation Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SETTLEMENT_CLOSED_OperationType", ScenarioDetailsHashMap) +" and "+" Event Type "+ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "SETTLEMENT_CLOSED_EventType", ScenarioDetailsHashMap)+" are not availble in Interface Journal grid", ScenarioDetailsHashMap);
		}

		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
	}



	public static void BookingCreatedEventbckup(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ) throws Exception
	{
		AppReusableMethods.selectMenu(driver,ETransMenu.InterfaceLogData,"Interface Journal", ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(7000);
		ExcelUtils.setCellData("InterfaceJournal_Milestones", RowNo, "BookingId", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "BookingId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, By.id("docNo"), null, "ABKBGLLUSNY02506", 10);
		GenericMethods.clickElement(driver, By.id("button.search"), null, 10);
		try {
			GenericMethods.pauseExecution(7000);
			GenericMethods.clickElement(driver, By.xpath(".//*[@id='dtrequestData5']/a/img"), null, 10);
			//GenericMethods.pauseExecution(7000);
			System.out.println("b4****"+driver.getTitle());
			GenericMethods.selectWindow(driver);
			System.out.println("a4****"+driver.getTitle());
			//driver.switchTo().window("");
			//String fileName = driver.findElement(By.xpath(".//*[@id='dtrequestData5']/a")).getAttribute("value");
			//System.out.println("fileName"+fileName);
			//driver.get("https://10.200.35.11:7022/KF/ETJournalController.fsc?moduleName=ET_JRNL&fromNavigation=SEARCH_LIST&toNavigation=REQ_RESP_DATA_WINDOW&operation=&subOperation=&fileName="+fileName+"&docFormat=%20XML");
			Thread.sleep(2000);
			String BookingPageSource = driver.getPageSource();
			//System.out.println("BookingPageSource"+BookingPageSource);
			if(BookingPageSource.contains(ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "Booking_StatusEventCode", ScenarioDetailsHashMap)))
			{
				String ActualBooking_StatusEventCode = BookingPageSource.split("<StatusEventCoded>1001</StatusEventCoded>")[0];
				GenericMethods.assertTwoValues(ActualBooking_StatusEventCode, ExcelUtils.getCellData("InterfaceJournal_Milestones", RowNo, "Booking_StatusEventCode", ScenarioDetailsHashMap), "Validating <StatusEventCoded>1001</StatusEventCoded> in XML", ScenarioDetailsHashMap);

				System.out.println("<StatusEventCoded>1001</StatusEventCoded> available");
			}
			else
			{
				System.out.println("Not available");
			}

			GenericMethods.pauseExecution(4000);
			System.out.println("&&&&&&&&&&&&&&&&&&&");
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ESCAPE);
			robot.keyRelease(KeyEvent.VK_ESCAPE);
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^");
			GenericMethods.pauseExecution(4000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_W);
			robot.keyRelease(KeyEvent.VK_W);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			GenericMethods.pauseExecution(4000);
		} catch (AWTException e) {e.printStackTrace();
		} catch (Exception e){e.printStackTrace();
		}

		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		Thread.sleep(2000);
		GenericMethods.switchToParent(driver);
		System.out.println("a4 switchToParent****"+driver.getTitle());
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		GenericMethods.pauseExecution(10000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectFrame(driver, null,orCommon.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
		GenericMethods.replaceTextofTextField(driver, By.id("subType"), null, "435354", 10);


	}

}