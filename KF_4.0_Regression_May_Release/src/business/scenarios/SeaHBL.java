package business.scenarios;

import global.reusables.CommonBean;
import global.reusables.DumpExcelFiles;
import global.reusables.ExcelUtils;
import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;
import global.reusables.ReadingMail;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.poi.ss.formula.SheetNameFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;

import DriverMethods.WebDriverInitilization;
import app.reuseables.AppReusableMethods;
import app.reuseables.ETransMenu;

public class SeaHBL extends WebDriverInitilization{

	static ObjectRepository orCommon=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/CommonObjects.xml");
	static ObjectRepository orHBL=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/HBL.xml");
	static ObjectRepository orSeaBooking=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/SeaBooking.xml");
	static ObjectRepository orDecisionTable = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/DecisionTable.xml");


	public static void pictHBlDetailsFlow(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ){
		//Sandeep- Below method will fetch all the Booking IDs from SE_BookingMainDetails sheet and update the same in SE_HBLMainDetails sheet of BookingId column.  
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLBasis",ScenarioDetailsHashMap).equalsIgnoreCase("Assembly Shipment"))
		{
			ArrayList<String> BookingIdValues =new ArrayList<String>();
			BookingIdValues=ExcelUtils.getAllCellValuesOfColumn("SE_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
			String BookingIDs = "";
			for (int BookingId = 0; BookingId < BookingIdValues.size(); BookingId++) 
			{
				if(BookingIdValues.get(BookingId)!= null && BookingIdValues.get(BookingId)!="")
				{
					BookingIDs = BookingIDs +BookingIdValues.get(BookingId)+","; 
				}
			}
			System.out.println("booking values----"+BookingIDs);
			System.out.println("booking values-&&&&&&---"+BookingIDs.substring(0,BookingIDs.length()-1));
			ExcelUtils.setCellData("SE_HBLMainDetails", RowNo, "BookingId", BookingIDs.substring(0,BookingIDs.length()-1), ScenarioDetailsHashMap);
		}
		AppReusableMethods.selectMenu(driver,ETransMenu.Hbl,"HBL", ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "AddButton", ScenarioDetailsHashMap, 10), 2);
		HblMaindtails(driver, ScenarioDetailsHashMap, RowNo);
		try{
			GenericMethods.handleAlert(driver, "accept");

		}catch (Exception e) {
			e.printStackTrace();
		}
		MBLMainDetails(driver, ScenarioDetailsHashMap, RowNo);
		try{
			GenericMethods.handleAlert(driver, "accept");

		}catch (Exception e) {
			e.printStackTrace();
		}
		/*	GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			e.printStackTrace();

		}

		String hblidSummary=GenericMethods.getInnerText(driver, null, orHBL.getElement(driver, "HblSaveDetail", ScenarioDetailsHashMap, 20), 2);
		String HblId=hblidSummary.split(" : ")[2].split(",")[0].trim();
		System.out.println("HblId::"+HblId);
		GenericMethods.assertTwoValues(hblidSummary.split(" : ")[1], ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBL_CreationMsg", ScenarioDetailsHashMap), "Validating HBL Creation Message", ScenarioDetailsHashMap);
		ExcelUtils.setCellData("SE_HBLMainDetails", RowNo, "HBLId", HblId, ScenarioDetailsHashMap);
		ExcelUtils.setCellData("SE_OBL", RowNo, "HBL_ID", HblId, ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "HblNo", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
		String ShipmentReferenceNumber=GenericMethods.getInnerText(driver, null, orHBL.getElement(driver, "GridShipmentReferenceNumber", ScenarioDetailsHashMap, 10), 10);
		ExcelUtils.setCellData("SE_HBLMainDetails", RowNo, "ShipmentReferenceNo", ShipmentReferenceNumber, ScenarioDetailsHashMap);
		GenericMethods.assertInnerText(driver, null, orHBL.getElement(driver, "GridHblId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), "GridHblId", "Validating HBL Id",  ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(1000);
		 */}
	public static void HblMaindtails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLBasis", ScenarioDetailsHashMap).equalsIgnoreCase("From Booking"))
		{
			//Pavan Here in Below Condition if AFR_Required=Yes only AFR module will be selected.
			//Based on the conditions which are mentioned in Automation Scenario Matrix for HBL module AFR is set to Yes in Test Data. And need to give the same if wants to executes AFR module
			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "AFR_Required", ScenarioDetailsHashMap).equalsIgnoreCase("yes")){
				HBL_AFR_Module(driver, ScenarioDetailsHashMap, RowNo);
			}
			GenericMethods.pauseExecution(1000);
			GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "HBLBasis", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLBasis",ScenarioDetailsHashMap));
			GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "LoadType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType", ScenarioDetailsHashMap));
			GenericMethods.pauseExecution(1000);
			AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "BookingIdLov", ScenarioDetailsHashMap, 10), orHBL, "BookingId", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "BookingId",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(5000);
			GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "DDL_ShipmentType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap));

			//Pavan 2015 Below if condition will be executed if ShipmentType is House, if not else condition will be executed where in OrderType details will be selected. 
			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap).equalsIgnoreCase("House")){
				GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "HouseType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsolidationORB2B", ScenarioDetailsHashMap));

			}else{
				GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "OrderType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "OrderType", ScenarioDetailsHashMap));
			}
			//			GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "LoadType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType", ScenarioDetailsHashMap));


			//Sangeeta Added for Inttra Letter of Credit tag
			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "InttraLetterOfCreditRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "ReferenceFields_Btn", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.pauseExecution(6000);
				GenericMethods.selectWindow(driver);
				GenericMethods.pauseExecution(6000);
				GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "ReferenceType_DropDown", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ReferenceType", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "ReferenceType_ReferenceNo", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ReferenceNo", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "ReferenceEffectiveDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ReferenceTypeExpiryDate", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Reference_EffectiveDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ReferenceTypeEffectiveDate", ScenarioDetailsHashMap), 2);
				GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "Reference_GridAddBtn", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.pauseExecution(2000);
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.pauseExecution(6000);
				GenericMethods.switchToParent(driver);
				GenericMethods.pauseExecution(6000);
				AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
				GenericMethods.pauseExecution(6000);
			}

			
			//Sandeep- FUNC018.1- Below method is added to perform party ID mode validation.
			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PartyIdModeRequired",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
			{
				String Originalvalue = driver.findElement(By.id("shipperIdNickName")).getAttribute("previousvalue");
				if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PartyIdModeType",ScenarioDetailsHashMap).equalsIgnoreCase("PartyNickName"))
				{
					String PartyNickName = AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ShipperIdLov",ScenarioDetailsHashMap, 10), orHBL,"LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipperNickName",ScenarioDetailsHashMap),"PartyNickName",ScenarioDetailsHashMap);
					driver.findElement(By.id("shipperIdNickName")).sendKeys(Keys.TAB);
					GenericMethods.pauseExecution(3000);
					GenericMethods.assertTwoValues(driver.findElement(By.id("shipperIdNickName")).getAttribute("previousvalue"), PartyNickName, "Verifying whether Shipper Name is displayed", ScenarioDetailsHashMap);
				}
				else
				{
					AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ShipperIdLov", ScenarioDetailsHashMap, 10), orHBL, "LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipperNickName", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					driver.findElement(By.id("shipperIdNickName")).sendKeys(Keys.TAB);
					GenericMethods.pauseExecution(3000);
					GenericMethods.assertTwoValues(driver.findElement(By.id("shipperIdNickName")).getAttribute("previousvalue"), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipperNickName",ScenarioDetailsHashMap), "Verifying whether Shipper Is is displayed", ScenarioDetailsHashMap);
				}

				GenericMethods.replaceTextofTextField(driver, By.id("shipperIdNickName"), null, Originalvalue, 2);

			}//code ends here
			//AppReusableMethods.quoteOrContractStatus(driver, ScenarioDetailsHashMap, RowNo);
			//Sandeep- FUNC018.1- Below method is added to perform party ID mode validation.
			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PartyIdModeRequired",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
			{
				String Originalvalue = driver.findElement(By.id("consigneeIdNickName")).getAttribute("previousvalue");

				if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PartyIdModeType",ScenarioDetailsHashMap).equalsIgnoreCase("PartyNickName"))
				{
					String PartyNickName = AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ConsigneeLov",ScenarioDetailsHashMap, 10), orHBL,"LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsigneeNickName",ScenarioDetailsHashMap),"PartyNickName",ScenarioDetailsHashMap);
					driver.findElement(By.id("consigneeIdNickName")).sendKeys(Keys.TAB);
					GenericMethods.pauseExecution(3000);
					GenericMethods.assertTwoValues(driver.findElement(By.id("consigneeIdNickName")).getAttribute("previousvalue"), PartyNickName, "Verifying whether Shipper Name is displayed", ScenarioDetailsHashMap);
				}
				else
				{
					AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ConsigneeLov", ScenarioDetailsHashMap, 10), orHBL, "LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsigneeNickName", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					driver.findElement(By.id("consigneeIdNickName")).sendKeys(Keys.TAB);
					GenericMethods.pauseExecution(3000);
					GenericMethods.assertTwoValues(driver.findElement(By.id("consigneeIdNickName")).getAttribute("previousvalue"), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsigneeNickName",ScenarioDetailsHashMap), "Verifying whether Shipper Is is displayed", ScenarioDetailsHashMap);
				}

				GenericMethods.replaceTextofTextField(driver, By.id("consigneeIdNickName"), null, Originalvalue, 2);
			}//code ends here
			
			
			
			GenericMethods.pauseExecution(3000);
			AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "CustomerLov", ScenarioDetailsHashMap, 10), orHBL, "LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Customer", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "InvoiceBranchLov", ScenarioDetailsHashMap, 10), orHBL, "LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "InvoiceBranch"	, ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "InvoiceDeptLov", ScenarioDetailsHashMap, 10	), orHBL, "Code", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "InvoiceDept", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "DeliveryDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "DeliveryDate", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "DeliveryTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "DeliveryTime", ScenarioDetailsHashMap), 2);
			GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "CargoReceived", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "CargoRecieved", ScenarioDetailsHashMap));

			if(!ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("Break bulk")&&
					!ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("RORO")){
				Select pickUp_DropdownValue = new Select(orHBL.getElement(driver, "DDL_PickUpRequired", ScenarioDetailsHashMap, 10));
				pickUp_DropdownValue.selectByVisibleText(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PickUpRequired",ScenarioDetailsHashMap));
			}

			//Below 2015 Condition is for if HBL Id is Manual
			if(!ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId_Selection", ScenarioDetailsHashMap).equalsIgnoreCase("Auto")){
				GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "CHB_ManualHBL", ScenarioDetailsHashMap, 10), 10);
				String randomHBL_ID=ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLIdName", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(5);
				System.out.println("randomHBL_ID::"+randomHBL_ID);
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "HBLId", ScenarioDetailsHashMap, 10), randomHBL_ID, 2);
			}
			//Priya :Validations for Func13.17,13.18
			String shipper="";
			String consignee="";
			try{
				shipper=ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "NotesShipper", ScenarioDetailsHashMap);
				consignee=ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "NotesConsignee", ScenarioDetailsHashMap);
			}catch (NullPointerException e) {

				shipper="";
				consignee="";
			}
			if(!shipper.equals("")&& !consignee.equals("")){
				AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ShipperIdLov", ScenarioDetailsHashMap, 10), orHBL, "LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "NotesShipper", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ConsigneeLov", ScenarioDetailsHashMap, 10), orHBL, "LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "NotesConsignee", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
				GenericMethods.pauseExecution(2000);
				GenericMethods.handleAlert(driver, "accept");
				GenericMethods.pauseExecution(5000);
				GenericMethods.selectFrame(driver,By.name("notesAndAlertsIframe"),null,5);
				GenericMethods.assertValue(driver, By.xpath("//td[contains(text(),'Notes And Alert Information')]"), null, "Notes And Alert Information", "Notes And Alert", "Notes And Alert", ScenarioDetailsHashMap);
				int rows=GenericMethods.locateElements(driver, By.xpath("//span[@id='wrappedTBB-rows']/span[@aw='row']"), 10).size();
				String status="Not found";
				for (int i = 0; i < rows; i++) {
					if(GenericMethods.getInnerText(driver, By.id("wrappedTBB-cell-0-"+i+"-box-text"), null, 10).equals(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "AlertId", ScenarioDetailsHashMap))){
						GenericMethods.assertInnerText(driver, By.id("wrappedTBB-cell-0-"+i+"-box-text"), null, ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "AlertId", ScenarioDetailsHashMap), "AlertId", "Validateing Alert id presence in Grid", ScenarioDetailsHashMap);
						status="Found";
						break;
					}
				}
				if(status.equalsIgnoreCase("Not found")){
					GenericMethods.assertTwoValues(status, "Found", "Validateing Alert id presence in Grid", ScenarioDetailsHashMap);

				}
				//	            GenericMethods.clickElement(driver, By.name("Proceed"), null, 5);
				GenericMethods.clickElement(driver, By.name("discard"), null, 5);
				GenericMethods.pauseExecution(5000);
				GenericMethods.switchToDefaultContent(driver);
				AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);



				AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ShipperIdLov", ScenarioDetailsHashMap, 10), orHBL, "LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipperNickName", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ConsigneeLov", ScenarioDetailsHashMap, 10), orHBL, "LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsigneeNickName", ScenarioDetailsHashMap), ScenarioDetailsHashMap);

			}
			//Date Validation end
			/*GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "Customer", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Customer", ScenarioDetailsHashMap), "Customer", "Validating Customer Name", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "OriginBranchHbl", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "OrignBranch", ScenarioDetailsHashMap), "OriginBranchHbl", "validating Origin Location", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "OriginDept", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "OriginDept", ScenarioDetailsHashMap), "OriginDept", "Validating Origin Dept", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "DesiTerminalHbl", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "DestBranch", ScenarioDetailsHashMap), "DesiTerminalHbl", "Validating Destination Branch", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "DesiDeptHbl", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "DestDept", ScenarioDetailsHashMap), "DesiDeptHbl", "Validating Destination Dept", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "ServiceLevel", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ServiceLevel", ScenarioDetailsHashMap), "ServiceLevel", "Validating ServiceLevel", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "ShipmentTypeDest", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ServiceTypeDest", ScenarioDetailsHashMap), "shipmentTermDest", "Validating shipmentTermDest", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "PortOfLoading", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PortOfLoading", ScenarioDetailsHashMap), "PortOfLoading", "Validating PortOfLoading", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "PortOfDispatch", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PortOfDischarge", ScenarioDetailsHashMap), "PortOfDischarge", "Validating PortOfDischarge", ScenarioDetailsHashMap);
			 */

			//Sangeeta DeliveryRequired valiadtion FUNC-65
			System.out.println("Delivery going to Rquired selected" +ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "DeliveryRequiredValidation", ScenarioDetailsHashMap));
			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "DeliveryRequiredValidation", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				System.out.println("Delivery REquired selected");
				GenericMethods.selectOptionFromDropDown(driver, null,orHBL.getElement(driver, "DeliveryRequired", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "DeliveryRequired", ScenarioDetailsHashMap));
			}


			//eRatingValidation starts here
			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "eRatingValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
			{
				GenericMethods.selectOptionFromDropDown(driver, null,orHBL.getElement(driver, "contract_Quote_TariffDropdown", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Contract_Quote_Tariff", ScenarioDetailsHashMap));
				if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "RatingType", ScenarioDetailsHashMap).equalsIgnoreCase("ManualRate"))
				{
					orHBL.getElement(driver, "ContractId", ScenarioDetailsHashMap, 10).clear();
					GenericMethods.pauseExecution(2000);
					GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "contract_Quote_TariffLOV", ScenarioDetailsHashMap, 10), 10);
					GenericMethods.pauseExecution(5000);
					GenericMethods.selectWindow(driver);
					GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "GridOrigin", ScenarioDetailsHashMap, 10), 2);
					GenericMethods.pauseExecution(4000);
					try {
						if (orCommon.getElement(driver, "SaveButton",ScenarioDetailsHashMap,10).isEnabled()) {
							GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 2);
						}	
					} catch (Exception e) {
						if (orCommon.getElement(driver, "SaveButton2",ScenarioDetailsHashMap,10).isEnabled()) {
							GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton2",ScenarioDetailsHashMap, 10), 2);
						}e.printStackTrace();
					}
					GenericMethods.switchToParent(driver);
					AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);

					//Sandeep - Below method is to verify  functional validation RAT 20(Verifying defaulting value of Agent Contract/Quote Id field)
					if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ContractQuote_Required", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
					{
						GenericMethods.replaceTextofTextField(driver, By.id("agentRatingId"), null, Keys.TAB, 10);
						GenericMethods.pauseExecution(8000);
						GenericMethods.assertTwoValues(driver.findElement(By.id("agentRatingId")).getAttribute("previousvalue"), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "AgentContractQuote_ID", ScenarioDetailsHashMap), "Verifying RAT 20(Verifying defaulting value of Agent Contract/Quote Id field)", ScenarioDetailsHashMap);
					}
					//Code added to check Quote / Contract Status(RAT19)- By Prasanna Modugu
					AppReusableMethods.quoteOrContractStatus(driver, ScenarioDetailsHashMap, RowNo);
				}
			}
			//End here


		}else
		{

			GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "HBLBasis", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLBasis",ScenarioDetailsHashMap));
			GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "DDL_ShipmentType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap));

			//Pavan Below if condition will be executed if ShipmentType is House, if not else condition will be executed where in OrderType details will be selected. 
			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap).equalsIgnoreCase("House")){
				GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "HouseType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsolidationORB2B", ScenarioDetailsHashMap));

			}
			else{
				GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "OrderType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "OrderType", ScenarioDetailsHashMap));
			}
			/*//Pavan Here in Below Condition if AFR_Required=Yes only AFR module will be selected.
			//Based on the conditions which are mentioned in Automation Scenario Matrix for HBL module AFR is set to Yes in Test Data. And need to give the same if wants to executes AFR module
			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "AFR_Required", ScenarioDetailsHashMap).equalsIgnoreCase("yes")){
				HBL_AFR_Module(driver, ScenarioDetailsHashMap, RowNo);
			}*/

			

			//Sangeeta Added for Inttra Letter of Credit tag
			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "InttraLetterOfCreditRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "ReferenceFields_Btn", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.pauseExecution(6000);
				GenericMethods.selectWindow(driver);
				GenericMethods.pauseExecution(6000);
				GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "ReferenceType_DropDown", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ReferenceType", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "ReferenceType_ReferenceNo", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ReferenceNo", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "ReferenceEffectiveDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ReferenceTypeExpiryDate", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Reference_EffectiveDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ReferenceTypeEffectiveDate", ScenarioDetailsHashMap), 2);
				GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "Reference_GridAddBtn", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.pauseExecution(2000);
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.pauseExecution(6000);
				GenericMethods.switchToParent(driver);
				GenericMethods.pauseExecution(6000);
				AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
				GenericMethods.pauseExecution(6000);
			}

			
			GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "LoadType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType", ScenarioDetailsHashMap));
			AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "OriginDeptLov", ScenarioDetailsHashMap, 10), orHBL, "Code", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "OriginDept", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(1000);
			AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "CustomerLov", ScenarioDetailsHashMap, 10), orHBL, "LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Customer", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "DesiTerminalHblLov", ScenarioDetailsHashMap, 10), orHBL, "LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "DestBranch", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "DesiDeptHblLov", ScenarioDetailsHashMap, 10), orHBL, "Code", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "DestDept", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "InvoiceBranchLov", ScenarioDetailsHashMap, 10), orHBL, "LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "InvoiceBranch"	, ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "InvoiceDeptLov", ScenarioDetailsHashMap, 10	), orHBL, "Code", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "InvoiceDept", ScenarioDetailsHashMap), ScenarioDetailsHashMap);


			//Sandeep- FUNC018.1- Below method is added to perform party ID mode validation.
			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PartyIdModeRequired",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
			{
				if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PartyIdModeType",ScenarioDetailsHashMap).equalsIgnoreCase("PartyNickName"))
				{
					String PartyNickName = AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ShipperIdLov",ScenarioDetailsHashMap, 10), orHBL,"LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipperNickName",ScenarioDetailsHashMap),"PartyNickName",ScenarioDetailsHashMap);
					driver.findElement(By.id("shipperIdNickName")).sendKeys(Keys.TAB);
					GenericMethods.pauseExecution(3000);
					GenericMethods.assertTwoValues(driver.findElement(By.id("shipperIdNickName")).getAttribute("previousvalue"), PartyNickName, "Verifying whether Shipper Name is displayed", ScenarioDetailsHashMap);
				}
				else
				{
					AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ShipperIdLov", ScenarioDetailsHashMap, 10), orHBL, "LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipperNickName", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					driver.findElement(By.id("shipperIdNickName")).sendKeys(Keys.TAB);
					GenericMethods.pauseExecution(3000);
					GenericMethods.assertTwoValues(driver.findElement(By.id("shipperIdNickName")).getAttribute("previousvalue"), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Shipper_PartyId",ScenarioDetailsHashMap), "Verifying whether Shipper Is is displayed", ScenarioDetailsHashMap);
				}
			}
			else
			{
				AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ShipperIdLov", ScenarioDetailsHashMap, 10), orHBL, "LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipperNickName", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			}//code ends here

			if(!ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("Break bulk")&&
					!ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("RORO")){
				Select pickUp_DropdownValue = new Select(orHBL.getElement(driver, "DDL_PickUpRequired", ScenarioDetailsHashMap, 10));
				pickUp_DropdownValue.selectByVisibleText(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PickUpRequired",ScenarioDetailsHashMap));

				//Pavan --Below condition will perform Validations 
				if(!ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("Break bulk")&&
						!ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("RORO")){

					if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PickUpRequired",ScenarioDetailsHashMap).equalsIgnoreCase("PickUp Instructions"))
					{
						GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "Textbox_PickupInstructions_Party",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PickUpNickName", ScenarioDetailsHashMap), "HBL_PickUpInstruction_Party", "Validating Pickup Party after Selecting PickUpInstruction", ScenarioDetailsHashMap);
						GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "Tab_PickUpInstructions",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PickUpRequired", ScenarioDetailsHashMap), "HBL_PickUpInstruction_Party", "Validating PickUpInstructions Tab", ScenarioDetailsHashMap);

					}//FUNC010.5,FUNC010.6 End	
				}

			}

			//Sandeep- FUNC018.1- Below method is added to perform party ID mode validation.
			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PartyIdModeRequired",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
			{
				if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PartyIdModeType",ScenarioDetailsHashMap).equalsIgnoreCase("PartyNickName"))
				{
					String PartyNickName = AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ConsigneeLov",ScenarioDetailsHashMap, 10), orHBL,"LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsigneeNickName",ScenarioDetailsHashMap),"PartyNickName",ScenarioDetailsHashMap);
					driver.findElement(By.id("consigneeIdNickName")).sendKeys(Keys.TAB);
					GenericMethods.pauseExecution(3000);
					GenericMethods.assertTwoValues(driver.findElement(By.id("consigneeIdNickName")).getAttribute("previousvalue"), PartyNickName, "Verifying whether Shipper Name is displayed", ScenarioDetailsHashMap);
				}
				else
				{
					AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ConsigneeLov", ScenarioDetailsHashMap, 10), orHBL, "LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsigneeNickName", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					driver.findElement(By.id("consigneeIdNickName")).sendKeys(Keys.TAB);
					GenericMethods.pauseExecution(3000);
					GenericMethods.assertTwoValues(driver.findElement(By.id("consigneeIdNickName")).getAttribute("previousvalue"), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Shipper_PartyId",ScenarioDetailsHashMap), "Verifying whether Shipper Is is displayed", ScenarioDetailsHashMap);
				}
			}
			else
			{
				AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ConsigneeLov", ScenarioDetailsHashMap, 10), orHBL, "LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsigneeNickName", ScenarioDetailsHashMap), ScenarioDetailsHashMap);

			}//code ends here

			//Sangeeta DeliveryRequired valiadtion FUNC-65
			System.out.println("Delivery going to Rquired selected" +ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "DeliveryRequiredValidation", ScenarioDetailsHashMap));
			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "DeliveryRequiredValidation", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				System.out.println("Delivery REquired selected");
				GenericMethods.selectOptionFromDropDown(driver, null,orHBL.getElement(driver, "DeliveryRequired", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "DeliveryRequired", ScenarioDetailsHashMap));
			}


			//AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "NotifyPartyLov", ScenarioDetailsHashMap, 10), orHBL, "LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "NotifyParty", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "FreightTerms", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "FreightTerms", ScenarioDetailsHashMap));
			AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ServiceLevelLov", ScenarioDetailsHashMap, 10), orHBL, "Code", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ServiceLevel", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ShipmentTypeOriginLov", ScenarioDetailsHashMap, 10), orHBL, "ServiceTypeField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ServiceTypeOrig", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ShipmentTypeDestLov", ScenarioDetailsHashMap, 10), orHBL, "ServiceTypeField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ServiceTypeDest", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(1000);
			//Pavan FUNC007: --Below condition will perform Validations 
			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Location_Mandatory",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
			{
				Location_Terminal_Validations(driver, ScenarioDetailsHashMap, RowNo);
			}
			else{
				AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "PortOfLoadingLov", ScenarioDetailsHashMap, 10), orHBL, "PortOfLoadingField",ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PortofLoading", ScenarioDetailsHashMap), ScenarioDetailsHashMap );
				AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "LOV_PortOfDischargeLov", ScenarioDetailsHashMap, 10), orHBL, "PortOfLoadingField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PortofDischarge", ScenarioDetailsHashMap), ScenarioDetailsHashMap);

			}//FUNC007 End
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "PlaceOfReceipt", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PlaceofReceipt", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "PlaceOfDelevery", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PlaceofDelivery", ScenarioDetailsHashMap), 2);

			//Below Condition will work if HBL Id is equal to Manual
			if(!ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId_Selection", ScenarioDetailsHashMap).equalsIgnoreCase("Auto")){
				GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "CHB_ManualHBL", ScenarioDetailsHashMap, 10), 10);
				String randomHBL_ID=ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLIdName", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(5);
				System.out.println("randomHBL_ID::"+randomHBL_ID);
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "HBLId", ScenarioDetailsHashMap, 10), randomHBL_ID, 2);
			}
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "HBLDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLDate", ScenarioDetailsHashMap), 2);

			Select CargoReceived_DropdownValue = new Select(orHBL.getElement(driver, "CargoReceived", ScenarioDetailsHashMap, 10));
			CargoReceived_DropdownValue.selectByVisibleText(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "CargoRecieved",ScenarioDetailsHashMap));
			//			GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "CargoReceived", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "CargoRecieved", ScenarioDetailsHashMap));
			//Below Condition will work if CarrierBooking reference is equal to Select
			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "CarrierBookingReferenceRequired",ScenarioDetailsHashMap).equalsIgnoreCase("yes")){
				//02032015--Selecting CarrierBookingRefence from SE_OBL 
				//				AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "CarrierBookingLov", ScenarioDetailsHashMap,10), orHBL,"Lov_Text_CarrierBookingNum", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "CarrierBooking_Reference",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
				GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "CarrierBookingLov", ScenarioDetailsHashMap,10), 10);
				GenericMethods.pauseExecution(2000);
				GenericMethods.selectWindow(driver);
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "ShipmentRefNo", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(2000);
				GenericMethods.selectOptionFromDropDown(driver, null,orHBL.getElement(driver, "LoadType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL", RowNo, "ConsolType", ScenarioDetailsHashMap));
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
				GenericMethods.switchToParent(driver);
				AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);

			}
			if(ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "VesselScheduleMasterMainDetails",ScenarioDetailsHashMap).equalsIgnoreCase("yes")){
				AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "VesselLov", ScenarioDetailsHashMap,10), orHBL,"Lov_Text_VesselID", ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "VesselScheduleId",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
			}
			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLBasis",ScenarioDetailsHashMap).equalsIgnoreCase("Assembly Shipment")){
				Assembly_Details(driver, ScenarioDetailsHashMap, RowNo);
			}
			System.out.println("sangeeta"+ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLBasis", ScenarioDetailsHashMap));

			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLBasis", ScenarioDetailsHashMap).equalsIgnoreCase("Adhoc Shipment"))
			{	
				//eRatingValidation starts here
				if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "eRatingValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
				{
					GenericMethods.selectOptionFromDropDown(driver, null,orHBL.getElement(driver, "contract_Quote_TariffDropdown", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Contract_Quote_Tariff", ScenarioDetailsHashMap));
					if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "RatingType", ScenarioDetailsHashMap).equalsIgnoreCase("ManualRate"))
					{
						orHBL.getElement(driver, "ContractId", ScenarioDetailsHashMap, 10).clear();
						GenericMethods.pauseExecution(2000);
						GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "contract_Quote_TariffLOV", ScenarioDetailsHashMap, 10), 10);
						GenericMethods.pauseExecution(5000);
						GenericMethods.selectWindow(driver);
						GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "GridOrigin", ScenarioDetailsHashMap, 10), 2);
						GenericMethods.pauseExecution(4000);
						try {
							if (orCommon.getElement(driver, "SaveButton",ScenarioDetailsHashMap,10).isEnabled()) {
								GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 2);
							}	
						} catch (Exception e) {
							if (orCommon.getElement(driver, "SaveButton2",ScenarioDetailsHashMap,10).isEnabled()) {
								GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton2",ScenarioDetailsHashMap, 10), 2);
							}e.printStackTrace();
						}
						GenericMethods.switchToParent(driver);
						AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);

						//Sandeep - Below method is to verify  functional validation RAT 20(Verifying defaulting value of Agent Contract/Quote Id field)
						if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ContractQuote_Required", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
						{
							GenericMethods.replaceTextofTextField(driver, By.id("agentRatingId"), null, Keys.TAB, 10);
							GenericMethods.pauseExecution(2000);
							GenericMethods.assertTwoValues(driver.findElement(By.id("agentRatingId")).getAttribute("previousvalue"), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "AgentContractQuote_ID", ScenarioDetailsHashMap), "RAT 20 Verifying defaulting value of Agent Contract/Quote Id field", ScenarioDetailsHashMap);
						}//code end
						GenericMethods.pauseExecution(2000);
						//Code added to check Quote / Contract Status(RAT19)- By Prasanna Modugu
						AppReusableMethods.quoteOrContractStatus(driver, ScenarioDetailsHashMap, RowNo);
					}


				}
				//End here

				GenericMethods.pauseExecution(3000);
				if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "DateValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
				{
					//Date Validation start:Author Sangeeta
					GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "CargoReadyDate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo,"CargoReadyDate", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "CargoReadyDateTime", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo,"CargoReadyTime", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "PickUpDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PickUpDate",ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "PickUpDateTime", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PickUpTime",ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "RequiredDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "RequiredDate",ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "RequiredDateTime", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo,"RequiredDateTime", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "DocRequiredDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "DocumentsRequiredDate",ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "DocRequiredDateTime", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo,"DocumentsRequiredTime", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "ETAFinalDest", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo,"ETAFinalDate", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "ETAFinalDestTime", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo,"ETAFinalTime", ScenarioDetailsHashMap), 2);

					//FUNC062.1-Date Validation(Cargo Ready Date / Time)
					String ActualCargoReadyDate =  driver.findElement(By.id("cargoReadyDate")).getAttribute("value");
					String ActualCargoReadyTime =  driver.findElement(By.name("cargoReadyDateTime")).getAttribute("value");
					String ExpectedCargoReadyDate = ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierCutoffDate", ScenarioDetailsHashMap);
					String ExpectedCargoReadyTime = ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierCutoffTime", ScenarioDetailsHashMap);
					String ActualCargoReadyDateTime = ActualCargoReadyDate.concat(" "+ActualCargoReadyTime);
					String ExpectedCargoReadyDateTime = ExpectedCargoReadyDate.concat(" "+ExpectedCargoReadyTime);
					try {
						GenericMethods.compareDatesByCompareTo("dd-MM-yyyy hh:mm", ActualCargoReadyDateTime, ExpectedCargoReadyDateTime, "Less", "Cargo Ready Date / Time is less than Carrier cut off Date & time", "Cargo Ready Date / Time is not less than Carrier cut off Date & time", ScenarioDetailsHashMap);
					} catch (ParseException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}	 

					//FUNC062.2-Date Validation(Pick Up Date / Time)
					String ActualPickUpDate =  driver.findElement(By.id("pickUpDate")).getAttribute("value");
					String ActualPickUpTime =  driver.findElement(By.name("pickUpDateTime")).getAttribute("value");
					String ExpectedPickUpDate = ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ServiceCutoffDate", ScenarioDetailsHashMap);
					String ExpectedPickUpTime = ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ServiceCutoffTime", ScenarioDetailsHashMap);
					String ActualPickUpDateTime = ActualPickUpDate.concat(" "+ActualPickUpTime);
					String ExpectedPickUpDateTime = ExpectedPickUpDate.concat(" "+ExpectedPickUpTime);
					try {
						GenericMethods.compareDatesByCompareTo("dd-MM-yyyy hh:mm", ActualPickUpDateTime, ExpectedPickUpDateTime, "Less", " Pick Up Date / Time is less than Service Cut off date", " Pick Up Date / Time is not less than Service Cut off date", ScenarioDetailsHashMap);
					} catch (ParseException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					//FUNC062.4-Date Validation(Required Date/ Time)
					String ActualRequiredDate =  driver.findElement(By.id("requiredDate")).getAttribute("value");
					String ActualRequiredTime =  driver.findElement(By.name("requiredDateTime")).getAttribute("value");
					String ExpectedRequiredDate = ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ETAFinalDate", ScenarioDetailsHashMap);
					String ExpectedRequiredTime = ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ETAFinalTime", ScenarioDetailsHashMap);
					String ActualRequiredDateTime = ActualRequiredDate.concat(" "+ActualRequiredTime);
					String ExpectedRequiredDateTime = ExpectedRequiredDate.concat(" "+ExpectedRequiredTime);
					try {
						GenericMethods.compareDatesByCompareTo("dd-MM-yyyy hh:mm", ActualRequiredDateTime, ExpectedRequiredDateTime, "Greater", "Required Date / Time is greater than the ETA Final Destination", "Required Date / Time is not greater than the ETA Final Destination", ScenarioDetailsHashMap);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					//FUNC062.6-Date Validation(Document Req Date / Time)
					String ActualDocRequiredDate =  driver.findElement(By.id("docRequiredDate")).getAttribute("value");
					String ActualDocRequiredTime =  driver.findElement(By.name("docRequiredDateTime")).getAttribute("value");
					String ExpectedDocRequiredDate = ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierCutoffDate", ScenarioDetailsHashMap);
					String ExpectedDocRequiredTime = ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierCutoffTime", ScenarioDetailsHashMap);
					String ActualDocRequiredDateTime = ActualDocRequiredDate.concat(" "+ActualDocRequiredTime);
					String ExpectedDocRequiredDateTime = ExpectedDocRequiredDate.concat(" "+ExpectedDocRequiredTime);
					try {
						GenericMethods.compareDatesByCompareTo("dd-MM-yyyy hh:mm", ActualDocRequiredDateTime, ExpectedDocRequiredDateTime, "Less", "Document Req Date / Time is less than carrier cut off date.", "Document Req Date / Time is not less than carrier cut off date.", ScenarioDetailsHashMap);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					//FUNC062.7-Date Validation(ETA Final Dest / Time)
					String ActualEtaFinalDestDate =  driver.findElement(By.id("etaFinalDest")).getAttribute("value");
					String ActualEtaFinalDestTime =  driver.findElement(By.name("etaFinalTime")).getAttribute("value");
					String ExpectedEtaFinalDestDate = ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ETA", ScenarioDetailsHashMap);    
					String ExpectedEtaFinalDestTime = ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ETATime", ScenarioDetailsHashMap);
					String ActualEtaFinalDestDateTime = ActualEtaFinalDestDate.concat(" "+ActualEtaFinalDestTime);
					String ExpectedEtaFinalDestDateTime = ExpectedEtaFinalDestDate.concat(" "+ExpectedEtaFinalDestTime);
					try {
						GenericMethods.compareDatesByCompareTo("dd-MM-yyyy hh:mm", ActualEtaFinalDestDateTime, ExpectedEtaFinalDestDateTime, "Greater", "ETA Final Dest / Time is greater than ETA date", "ETA Final Dest / Time is not greater than ETA date", ScenarioDetailsHashMap);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}


				//Date Validation end
				//Priya :Validations for Func13.17,13.18
				String shipper="";
				String consignee="";
				try{
					shipper=ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "NotesShipper", ScenarioDetailsHashMap);
					consignee=ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "NotesConsignee", ScenarioDetailsHashMap);
				}catch (NullPointerException e) {

					shipper="";
					consignee="";
				}
				if(!shipper.equals("")&& !consignee.equals("")){
					AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ShipperIdLov", ScenarioDetailsHashMap, 10), orHBL, "LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "NotesShipper", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ConsigneeLov", ScenarioDetailsHashMap, 10), orHBL, "LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "NotesConsignee", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
					GenericMethods.pauseExecution(2000);
					GenericMethods.handleAlert(driver, "accept");
					GenericMethods.pauseExecution(5000);
					GenericMethods.selectFrame(driver,By.name("notesAndAlertsIframe"),null,5);
					GenericMethods.assertValue(driver, By.xpath("//td[contains(text(),'Notes And Alert Information')]"), null, "Notes And Alert Information", "Notes And Alert", "Notes And Alert", ScenarioDetailsHashMap);
					int rows=GenericMethods.locateElements(driver, By.xpath("//span[@id='wrappedTBB-rows']/span[@aw='row']"), 10).size();
					String status="Not found";
					for (int i = 1; i <= rows; i++) {
						if(GenericMethods.getInnerText(driver, By.id("wrappedTBB-cell-0-"+i+"-box-text"), null, 10).equals(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "AlertId", ScenarioDetailsHashMap))){
							GenericMethods.assertInnerText(driver, By.id("wrappedTBB-cell-0-"+i+"-box-text"), null, ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "AlertId", ScenarioDetailsHashMap), "AlertId", "Validateing Alert id presence in Grid", ScenarioDetailsHashMap);
							status="Found";
							break;
						}
					}
					if(status.equalsIgnoreCase("Not found")){
						GenericMethods.assertTwoValues(status, "Found", "Validateing Alert id presence in Grid", ScenarioDetailsHashMap);

					}
					//		            GenericMethods.clickElement(driver, By.name("Proceed"), null, 5);
					GenericMethods.clickElement(driver, By.name("discard"), null, 5);
					GenericMethods.pauseExecution(5000);
					GenericMethods.switchToDefaultContent(driver);
					AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);



					AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ShipperIdLov", ScenarioDetailsHashMap, 10), orHBL, "LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipperNickName", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ConsigneeLov", ScenarioDetailsHashMap, 10), orHBL, "LovField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsigneeNickName", ScenarioDetailsHashMap), ScenarioDetailsHashMap);

				}
				//Date Validation end


				GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "CargoDetails", ScenarioDetailsHashMap, 10), 2);
				try{
					GenericMethods.handleAlert(driver, "accept");

				}catch (Exception e) {
					e.printStackTrace();
				}
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "TotalPieces", ScenarioDetailsHashMap, 10), "2", 2);
			}

		}

	}
	public static void Assembly_Details(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "ASSEMBLY_Tab", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(6000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
		String NumOfBookings_TestData [] =ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "BookingId", ScenarioDetailsHashMap).split(",");
		String BookingId="";
		System.out.println("NumOfBookings::"+NumOfBookings_TestData.length);
		int UnAllocated_Grid_RowCount=driver.findElements(By.xpath("//legend[text()='Booking Details']/ancestor::td/fieldset/table[2]/tbody//td[1]/table/tbody//tbody//tr")).size();
		int Allocated_Grid_RowCount=driver.findElements(By.xpath("//legend[text()='Booking Details']/ancestor::td/fieldset/table[2]/tbody/tr/td[3]/table//table/tbody/tr")).size();

		for (int Booking_RowCount = 1; Booking_RowCount <= NumOfBookings_TestData.length; Booking_RowCount++) {
			int Booking_Split=Booking_RowCount-1;
			BookingId=NumOfBookings_TestData[Booking_Split];
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "AssemblyTab_Textbox_BookingID", ScenarioDetailsHashMap, 10), BookingId, 10);
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(6000);
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "AssemblyTab_Grid_Shipper", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "AssemblyTab_Button_Allocate", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(2000);
			try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
			GenericMethods.pauseExecution(2000);
			try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
			GenericMethods.pauseExecution(6000);
		}

	}

	public static void MBLMainDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.pauseExecution(6000);
		GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "MBLMainDetails", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(6000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}




		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsolidationORB2B", ScenarioDetailsHashMap).equalsIgnoreCase("B2B")||
				ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsolidationORB2B", ScenarioDetailsHashMap).equalsIgnoreCase("")){
			GenericMethods.pauseExecution(3000);
			System.out.println("inside Bb2b condition");
			if(ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "VesselScheduleReq", ScenarioDetailsHashMap).equalsIgnoreCase("Multiple"))
			{
				orHBL.getElement(driver, "Textbox_VesselScheduleID", ScenarioDetailsHashMap, 10).clear();
				GenericMethods.pauseExecution(1000);
				orHBL.getElement(driver, "OriginBranchHbl", ScenarioDetailsHashMap, 10).click();
				//Pavan Here in Below Condition if AFR_Required=Yes only AFR module will be selected. Same for INTRAA module alos
				//Based on the conditions which are mentioned in Automation Scenario Matrix for HBL module AFR is set to Yes in Test Data. And need to give the same if wants to executes AFR module
				if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "AFR_Required", ScenarioDetailsHashMap).equalsIgnoreCase("yes")){
					HBL_AFR_Module(driver, ScenarioDetailsHashMap, RowNo);
				}

				if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Inttra_Required", ScenarioDetailsHashMap).equalsIgnoreCase("yes")){	
					HBL_INTTRA_Module(driver, ScenarioDetailsHashMap, RowNo);
				}
				AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "CarrierIdLov", ScenarioDetailsHashMap, 10), orHBL, "CarrierId", ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				/*String random_MBLID=ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "MBL_ID", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(4);
				System.out.println("random_MBLID::"+random_MBLID);
				ExcelUtils.setCellData("SE_HBL_MBLDetails", RowNo, "MBL_ID", random_MBLID, ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "MBLId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "MBL_ID", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_MBLDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "MBL_Date", ScenarioDetailsHashMap), 2);
				 */	
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_LoadedOnBoard_Date", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "LoadedOnBoard_Date", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_LoadedOnBoard_Time", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "LoadedOnBoard_Time", ScenarioDetailsHashMap), 2);

				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "CarrierBookingreferance", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierBookingRefNo", ScenarioDetailsHashMap), 2);
				GenericMethods.pauseExecution(2000);
				AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ServiceLevelLov", ScenarioDetailsHashMap, 10), orHBL, "Code", ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ServiceLevel", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				//Below method will perform VesselScheduleMaster or Maindetails based on test data
				HBL_VesselScheduleMaster_MainDetails(driver, ScenarioDetailsHashMap, RowNo);

			}
			else if(ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "VesselScheduleReq", ScenarioDetailsHashMap).equalsIgnoreCase("Single"))
			{
				String random_MBLID=ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "MBL_ID", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(4);
				System.out.println("random_MBLID else::"+random_MBLID);
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "MBLId", ScenarioDetailsHashMap, 10), random_MBLID, 2);
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_MBLDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "MBL_Date", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_LoadedOnBoard_Date", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "LoadedOnBoard_Date", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_LoadedOnBoard_Time", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "LoadedOnBoard_Time", ScenarioDetailsHashMap), 2);

				ExcelUtils.setCellData("SE_HBL_MBLDetails", RowNo, "MBL_ID", random_MBLID, ScenarioDetailsHashMap);
				ExcelUtils.setCellData("SE_OBLContainers", RowNo, "MBL_ID", random_MBLID, ScenarioDetailsHashMap);
				for (int k = 1; k <= ExcelUtils.getCellDataRowCount("SI_Arrival", ScenarioDetailsHashMap); k++) {
					System.out.println("insde SI_Arrival ");
					ExcelUtils.setCellData("SI_Arrival", k, "MBLId", ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "MBL_ID", ScenarioDetailsHashMap), ScenarioDetailsHashMap);

				}
				for (int i = 1; i <= ExcelUtils.getCellDataRowCount("SI_ArrivalContainers", ScenarioDetailsHashMap); i++) {
					System.out.println("insde SI_ArrivalContainers ");
					ExcelUtils.setCellData("SI_ArrivalContainers", i, "MBLId", random_MBLID, ScenarioDetailsHashMap);

				}


			}


			//eRatingValidation starts here
			if(ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "eRatingValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
			{
				GenericMethods.selectOptionFromDropDown(driver, null,orHBL.getElement(driver, "MBL_Contracttype", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "Contract_Quote_Tariff", ScenarioDetailsHashMap));
				if(ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "RatingType", ScenarioDetailsHashMap).equalsIgnoreCase("ManualRate"))
				{

					orHBL.getElement(driver, "CarrierContractId", ScenarioDetailsHashMap, 10).clear();
					GenericMethods.pauseExecution(2000);
					GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "contract_Quote_TariffLOV", ScenarioDetailsHashMap, 10), 10);
					GenericMethods.pauseExecution(5000);
					GenericMethods.selectWindow(driver);
					GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "GridOrigin", ScenarioDetailsHashMap, 10), 2);
					GenericMethods.pauseExecution(4000);
					try {
						if (orCommon.getElement(driver, "SaveButton",ScenarioDetailsHashMap,10).isEnabled()) {
							GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 2);
						}	
					} catch (Exception e) {
						if (orCommon.getElement(driver, "SaveButton2",ScenarioDetailsHashMap,10).isEnabled()) {
							GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton2",ScenarioDetailsHashMap, 10), 2);
						}e.printStackTrace();
					}
					GenericMethods.switchToParent(driver);
					AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
				}
			}
			//End here

			/*else{
					AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "CarrierIdLov", ScenarioDetailsHashMap, 10), orHBL, "CarrierId", ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "CarrierBookingreferance", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierBookingRefNo", ScenarioDetailsHashMap), 2);
					GenericMethods.pauseExecution(2000);
					AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ServiceLevelLov", ScenarioDetailsHashMap, 10), orHBL, "Code", ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ServiceLevel", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "CutOffDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierCutoffDate", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "CutOfTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierCutoffTime", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "EtdDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ETD", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "EtdTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ETDTime", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "EtaDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ETA", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "EtaTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ETATime", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "EtaFinalDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ETAFinalDate", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "EtaFinalTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ETAFinalTime", ScenarioDetailsHashMap), 2);
					HBL_VesselScheduleMaster_MainDetails(driver, ScenarioDetailsHashMap, RowNo);

				}*/

		}
		/*
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap).equalsIgnoreCase("Direct")){
			String random_MBLID=ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "MBL_ID", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(4);
			System.out.println("random_MBLID::"+random_MBLID);
			ExcelUtils.setCellData("SE_HBL_MBLDetails", RowNo, "MBL_ID", random_MBLID, ScenarioDetailsHashMap);
		 */	

		/* GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "MBLId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "MBL_ID", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_MBLDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "MBL_Date", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_LoadedOnBoard_Date", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "LoadedOnBoard_Date", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_LoadedOnBoard_Time", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "LoadedOnBoard_Time", ScenarioDetailsHashMap), 2);
		 */
		//		}

		//Sandeep- Below code is to perform INTTRA functionality.
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Inttra_Required", ScenarioDetailsHashMap).equalsIgnoreCase("yes")){	
			GenericMethods.assertTwoValues(GenericMethods.isFieldEnabled(driver, null, orHBL.getElement(driver, "CHB_INTTRA", ScenarioDetailsHashMap, 10), 10)+"", true+"", "Verifying whether INTTRA SI checkbox is enabled", ScenarioDetailsHashMap);
			if(!GenericMethods.isChecked(orHBL.getElement(driver, "CHB_INTTRA", ScenarioDetailsHashMap, 10)))
			{
				GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "CHB_INTTRA", ScenarioDetailsHashMap, 10), 2);
				try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e){e.printStackTrace();}

				//assertion added sangeeta
				GenericMethods.assertTwoValues(GenericMethods.getInnerText(driver, null, orHBL.getElement(driver, "Tab_INTTRA", ScenarioDetailsHashMap, 10), 2), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "InttraTab_Name", ScenarioDetailsHashMap), "Validating availability of Inttra tab after checking inttra check box in MBL Main Details tab of HBL module", ScenarioDetailsHashMap);

				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_ServiceLevelOrigin", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ServiceTypeOrig", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_ServiceLevelDest", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ServiceTypeDest", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "CarrierBookingreferance", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierBookingRefNo", ScenarioDetailsHashMap)+ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "MBL_ID", ScenarioDetailsHashMap), 2);
				ExcelUtils.setCellData("SE_HBL_MBLDetails", RowNo, "CarrierBookingRefNo", ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierBookingRefNo", ScenarioDetailsHashMap)+ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "MBL_ID", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			}
		}

		if(!ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("Break bulk")&&
				!ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("RORO")){

			if (ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PickUpRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Pick Up Run")){
				seaHBL_PickUpInstructions(driver, ScenarioDetailsHashMap, RowNo);
			}
		}
	}
	//Masthan-Below Method to perform Vessel Schedule Validation

	public static void Validate_VesselShedule(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{

		GenericMethods.pauseExecution(3000);
		for (int RoutePlanRowNo = 1; RoutePlanRowNo <= ExcelUtils.getCellDataRowCount("SE_HBL_MBLDetails", ScenarioDetailsHashMap); RoutePlanRowNo++) 
		{
			if(ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselSheduleType",ScenarioDetailsHashMap).equalsIgnoreCase("Single")){
				int VesselscheduleRowID = RoutePlanRowNo-1;

				String POL=GenericMethods.locateElement(driver, By.id("routePortOfLoading"+VesselscheduleRowID+"DKCode"), 2).getAttribute("value");
				String POD=GenericMethods.locateElement(driver, By.id("routePortOfDispatch"+VesselscheduleRowID+"DKCode"), 2).getAttribute("value");
				String Vessel=GenericMethods.locateElement(driver, By.id("routeVesselName"+VesselscheduleRowID), 2).getAttribute("value");
				String Voyage=GenericMethods.locateElement(driver, By.id("routeVoyage"+VesselscheduleRowID), 2).getAttribute("value");
				GenericMethods.assertTwoValues(POL, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselPOL",ScenarioDetailsHashMap),"Validating Loading Port In Vessel Shedule", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(POD, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselPOD",ScenarioDetailsHashMap),"Validating Discharge Port In Vessel Shedule", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(Vessel, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "Vessel",ScenarioDetailsHashMap),"Validating Vessel In Vessel Shedule", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(Voyage, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselVoyage",ScenarioDetailsHashMap),"Validating Voyage In Vessel Shedule", ScenarioDetailsHashMap);


			}
			else if(ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselSheduleType",ScenarioDetailsHashMap).equalsIgnoreCase("Multiple")){
				int VesselscheduleRowID = RoutePlanRowNo-1;
				String POL=GenericMethods.locateElement(driver, By.id("routePortOfLoading"+VesselscheduleRowID+"DKCode"), 2).getAttribute("value");
				String POT=GenericMethods.locateElement(driver, By.id("routePortOfDispatch"+VesselscheduleRowID+"DKCode"), 2).getAttribute("value");
				String Vessel=GenericMethods.locateElement(driver, By.id("routeVesselName"+VesselscheduleRowID), 2).getAttribute("value");
				String Voyage=GenericMethods.locateElement(driver, By.id("routeVoyage"+VesselscheduleRowID), 2).getAttribute("value");
				VesselscheduleRowID = RoutePlanRowNo;
				String POT1=GenericMethods.locateElement(driver, By.id("routePortOfLoading"+VesselscheduleRowID+"DKCode"), 2).getAttribute("value");
				String POD=GenericMethods.locateElement(driver, By.id("routePortOfDispatch"+VesselscheduleRowID+"DKCode"), 2).getAttribute("value");
				String VesselTranship=GenericMethods.locateElement(driver, By.id("routeVesselName"+VesselscheduleRowID), 2).getAttribute("value");
				String VoyageTrnaship=GenericMethods.locateElement(driver, By.id("routeVoyage"+VesselscheduleRowID), 2).getAttribute("value");
				GenericMethods.assertTwoValues(POL, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselPOL",ScenarioDetailsHashMap),"Validating Loading Port In Vessel Shedule", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(POD, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselPOD",ScenarioDetailsHashMap),"Validating Discharge Port In Vessel Shedule", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(Vessel, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "Vessel",ScenarioDetailsHashMap),"Validating Vessel In Vessel Shedule", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(Voyage, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselVoyage",ScenarioDetailsHashMap),"Validating Voyage In Vessel Shedule", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(POT, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "POT",ScenarioDetailsHashMap),"Validating Transhipment Port In Vessel Shedule", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(VesselTranship, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "TranshipmentVessel",ScenarioDetailsHashMap),"Validating Transhipment Vessel In Vessel Shedule", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(VoyageTrnaship, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "TranshipmentVoyage",ScenarioDetailsHashMap),"Validating Transhipment Voyage In Vessel Shedule", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(POT, POT1,"Validating Transhipment Ports In Vessel Shedule Grid", ScenarioDetailsHashMap);

			}
		}

	}


	//Below method will perform VesselScheduleMaster or VesselSchedule Maindetails based on test data if condition will be executed when VesselScheduleMaster=Yes if not else part will execute that is vessel schedule main details
	public static void HBL_VesselScheduleMaster_MainDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){

		if (ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "VesselScheduleMaster", ScenarioDetailsHashMap).equalsIgnoreCase("yes")){
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "Button_LOV_VesselScheduleID", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(2000);
			GenericMethods.selectWindow(driver);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "LOV_Text_VesselScheduleID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "VesselScheduleId", ScenarioDetailsHashMap), 2);
			if (orCommon.getElements(driver, "SearchButton",ScenarioDetailsHashMap,10).size() > 0) {
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 2);
			} else{
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton2", ScenarioDetailsHashMap,10), 2);
			}
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "LOV_Grid_VesselScheduleID", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(3000);
			if (orCommon.getElements(driver, "SaveButton",ScenarioDetailsHashMap,10).size() > 0) {
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 2);
			} else{
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton2",ScenarioDetailsHashMap, 10), 2);
			}
			GenericMethods.switchToParent(driver);
			AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "StuffingId", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(1000);

			String VesselEtdDate = orHBL.getElement(driver, "VesselETD", ScenarioDetailsHashMap, 10).getAttribute("value");
			String VesselEtaDate = orHBL.getElement(driver, "VesselETA", ScenarioDetailsHashMap, 10).getAttribute("value");

			for (int i = 1; i < ExcelUtils.getCellDataRowCount("SI_Arrival", ScenarioDetailsHashMap); i++) {
				ExcelUtils.setCellData("SI_Arrival", i, "ArrivalDateFrom",VesselEtdDate , ScenarioDetailsHashMap);
				ExcelUtils.setCellData("SI_Arrival", i, "ArrivalDateTo", VesselEtaDate, ScenarioDetailsHashMap);

			}	
			/*GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "CarrierId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierId", ScenarioDetailsHashMap), "CarrierId", "Validating Carrier Id",  ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "POl", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "VesselPOL", ScenarioDetailsHashMap), "POL", "validating Vessel POL ", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "POD", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "VesselPOD", ScenarioDetailsHashMap), "POD", "Validating vessel POD", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "Vessel", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "Vessel", ScenarioDetailsHashMap), "Vessel", "Validating Vessel", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "Voyage", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "VesselVoyage", ScenarioDetailsHashMap), "Voyage", "Validating Voyage", ScenarioDetailsHashMap);
			 */		
			Validate_VesselShedule(driver,ScenarioDetailsHashMap,RowNo);
		}
		else if(ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "VesselSchedule_DirectEntry", ScenarioDetailsHashMap).equalsIgnoreCase("yes")){
			orHBL.getElement(driver, "Textbox_VesselScheduleID", ScenarioDetailsHashMap, 10).clear();
			GenericMethods.pauseExecution(1000);
			orHBL.getElement(driver, "OriginBranchHbl", ScenarioDetailsHashMap, 10).click();

			for (int RoutePlanRowNo = 1; RoutePlanRowNo <= ExcelUtils.getCellDataRowCount("SE_HBL_MBLDetails", ScenarioDetailsHashMap); RoutePlanRowNo++) 
			{
				int VesselscheduleRowID = RoutePlanRowNo-1;

				if(RoutePlanRowNo == 1)
				{
					GenericMethods.replaceTextofTextField(driver, By.id("routeVesselName"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "Vessel",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeVoyage"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselVoyage",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtdDate"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselETD",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtdTime"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselTime",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtaDate"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselETA",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtaTime"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselETATime",ScenarioDetailsHashMap), 5);
				}
				else if(RoutePlanRowNo > 1)
				{
					int PODRowID = VesselscheduleRowID-1;
					int RowPlanID =RoutePlanRowNo-1; 
					GenericMethods.pauseExecution(1000);
					GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "VesselAdd", ScenarioDetailsHashMap,10), 2);
					GenericMethods.replaceTextofTextField(driver, By.id("routePortOfDispatch"+PODRowID+"DKCode"), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselPOD",ScenarioDetailsHashMap), 5);
					GenericMethods.pauseExecution(1000);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtaDate"+PODRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RowPlanID, "VesselETA",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtaTime"+PODRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RowPlanID, "VesselETATime",ScenarioDetailsHashMap), 5);
					GenericMethods.pauseExecution(1000);
					GenericMethods.replaceTextofTextField(driver, By.id("routeVesselName"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "Vessel",ScenarioDetailsHashMap), 5);
					GenericMethods.pauseExecution(1000);
					GenericMethods.replaceTextofTextField(driver, By.id("routeVoyage"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselVoyage",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtdDate"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselETD",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtdTime"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselTime",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtaDate"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselETA",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtaTime"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselETATime",ScenarioDetailsHashMap), 5);
				}
			}

			String VesselEtdDate = orHBL.getElement(driver, "VesselETD", ScenarioDetailsHashMap, 10).getAttribute("value");
			String VesselEtaDate = orHBL.getElement(driver, "VesselETA", ScenarioDetailsHashMap, 10).getAttribute("value");
			for (int i = 1; i < ExcelUtils.getCellDataRowCount("SI_Arrival", ScenarioDetailsHashMap); i++) {
				ExcelUtils.setCellData("SI_Arrival", i, "ArrivalDateFrom",VesselEtdDate , ScenarioDetailsHashMap);
				ExcelUtils.setCellData("SI_Arrival", i, "ArrivalDateTo", VesselEtaDate, ScenarioDetailsHashMap);

			}

			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "CutOffDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierCutoffDate", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "CutOfTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierCutoffTime", ScenarioDetailsHashMap), 2);
		}
		else{
			for (int RoutePlanRowNo = 1; RoutePlanRowNo <= ExcelUtils.getCellDataRowCount("SE_HBL_MBLDetails", ScenarioDetailsHashMap); RoutePlanRowNo++) 
			{
				int VesselscheduleRowID = RoutePlanRowNo-1;

				if(RoutePlanRowNo == 1)
				{
					GenericMethods.replaceTextofTextField(driver, By.id("routeVesselName"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "Vessel",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeVoyage"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselVoyage",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtdDate"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselETD",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtdTime"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselTime",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtaDate"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselETA",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtaTime"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselETATime",ScenarioDetailsHashMap), 5);
				}
				else if(RoutePlanRowNo > 1)
				{
					int PODRowID = VesselscheduleRowID-1;
					int RowPlanID =RoutePlanRowNo-1; 
					GenericMethods.pauseExecution(1000);
					GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "VesselAdd", ScenarioDetailsHashMap,10), 2);
					GenericMethods.replaceTextofTextField(driver, By.id("routePortOfDispatch"+PODRowID+"DKCode"), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselPOD",ScenarioDetailsHashMap), 5);
					GenericMethods.pauseExecution(1000);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtaDate"+PODRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RowPlanID, "VesselETA",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtaTime"+PODRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RowPlanID, "VesselETATime",ScenarioDetailsHashMap), 5);
					GenericMethods.pauseExecution(1000);
					GenericMethods.replaceTextofTextField(driver, By.id("routeVesselName"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "Vessel",ScenarioDetailsHashMap), 5);
					GenericMethods.pauseExecution(1000);
					GenericMethods.replaceTextofTextField(driver, By.id("routeVoyage"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselVoyage",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtdDate"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselETD",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtdTime"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselTime",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtaDate"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselETA",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtaTime"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RoutePlanRowNo, "VesselETATime",ScenarioDetailsHashMap), 5);
				}
			}
			String VesselEtdDate = orHBL.getElement(driver, "VesselETD", ScenarioDetailsHashMap, 10).getAttribute("value");
			String VesselEtaDate = orHBL.getElement(driver, "VesselETA", ScenarioDetailsHashMap, 10).getAttribute("value");
			for (int i = 1; i < ExcelUtils.getCellDataRowCount("SI_Arrival", ScenarioDetailsHashMap); i++) {
				ExcelUtils.setCellData("SI_Arrival", i, "ArrivalDateFrom",VesselEtdDate , ScenarioDetailsHashMap);
				ExcelUtils.setCellData("SI_Arrival", i, "ArrivalDateTo", VesselEtaDate, ScenarioDetailsHashMap);

			}

			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "CutOffDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierCutoffDate", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "CutOfTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierCutoffTime", ScenarioDetailsHashMap), 2);
		}

		String random_MBLID=ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "MBL_ID", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(4);
		System.out.println("random_MBLID::"+random_MBLID);
		ExcelUtils.setCellData("SE_HBL_MBLDetails", RowNo, "MBL_ID", random_MBLID, ScenarioDetailsHashMap);
		ExcelUtils.setCellData("SE_OBLContainers", RowNo, "MBL_ID", random_MBLID, ScenarioDetailsHashMap);
		for (int k = 1; k <= ExcelUtils.getCellDataRowCount("SI_Arrival", ScenarioDetailsHashMap); k++) {
			System.out.println("insde SI_Arrival ");
			ExcelUtils.setCellData("SI_Arrival", k, "MBLId", ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "MBL_ID", ScenarioDetailsHashMap), ScenarioDetailsHashMap);

		}
		for (int i = 1; i <= ExcelUtils.getCellDataRowCount("SI_ArrivalContainers", ScenarioDetailsHashMap); i++) {
			System.out.println("insde SI_ArrivalContainers ");
			ExcelUtils.setCellData("SI_ArrivalContainers", i, "MBLId", random_MBLID, ScenarioDetailsHashMap);

		}


		/*//Pavan Updating MBL_ID into OBL_Containers Sheet for further reference
	if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsolidationORB2B",ScenarioDetailsHashMap).equalsIgnoreCase("B2B")||
		ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipmentType",ScenarioDetailsHashMap).equalsIgnoreCase("Direct"))
	{
		ExcelUtils.setCellData("SE_HBL_MBLDetails", RowNo, "MBL_ID", random_MBLID, ScenarioDetailsHashMap);
		ExcelUtils.setCellData("SE_OBLContainers", RowNo, "MBL_ID", random_MBLID, ScenarioDetailsHashMap);
		for (int k = 1; k <= ExcelUtils.getCellDataRowCount("SI_Arrival", ScenarioDetailsHashMap); k++) {
			System.out.println("insde SI_Arrival ");
			ExcelUtils.setCellData("SI_Arrival", k, "MBLId", ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "MBL_ID", ScenarioDetailsHashMap), ScenarioDetailsHashMap);

		}
		for (int i = 1; i <= ExcelUtils.getCellDataRowCount("SI_ArrivalContainers", ScenarioDetailsHashMap); i++) {
			System.out.println("insde SI_ArrivalContainers ");
			ExcelUtils.setCellData("SI_ArrivalContainers", i, "MBLId", random_MBLID, ScenarioDetailsHashMap);

		}

		}
	else{
		ExcelUtils.setCellData("SE_HBL_MBLDetails", RowNo, "MBL_ID", random_MBLID, ScenarioDetailsHashMap);
	}*/
		GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "MBLId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "MBL_ID", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_MBLDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "MBL_Date", ScenarioDetailsHashMap), 2);

		GenericMethods.scrollDown(driver);
		GenericMethods.pauseExecution(1000);

		GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "ContainerType",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBL_Container", RowNo, "ContainerType",ScenarioDetailsHashMap));
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType",ScenarioDetailsHashMap).equalsIgnoreCase("FCL")){
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "NoOfContainers",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails",  RowNo, "NumOfContainers",ScenarioDetailsHashMap), 5);
		}
		if(!ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType",ScenarioDetailsHashMap).equalsIgnoreCase("BREAK BULK")&&
				!ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType",ScenarioDetailsHashMap).equalsIgnoreCase("RORO")){
			GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "CAddButton", ScenarioDetailsHashMap,10), 2);
		}

	}

	//Pavan 20thJan 2015 Below method is to enter Values in Cargo pack details
	public static void HBL_CargoPackDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "CargoDetails", ScenarioDetailsHashMap, 10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");

		}catch (Exception e) {
			e.printStackTrace();
		}
		GenericMethods.pauseExecution(3000);
		String packageValue=orHBL.getElement(driver, "Textbox_Packages", ScenarioDetailsHashMap, 10).getAttribute("previousvalue");
		System.out.println("packageValue>>"+packageValue+"<<<");

		if(!ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "CargoPackReq",ScenarioDetailsHashMap).equalsIgnoreCase("Booking"))
		{
			if(packageValue!=null||packageValue!="")
			{
				GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "AddPacks", ScenarioDetailsHashMap, 10), 2);
				try{
					GenericMethods.handleAlert(driver, "accept");
				}catch (Exception e) {
					e.printStackTrace();
				}
			}

			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_Packages", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Packages", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "PackagesType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Type", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "SubPackages", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "SubPack", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "SubPackagesType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "SubType", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "ContainerPackGrossWeight", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "GrossWeight", ScenarioDetailsHashMap), 2);
			GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "PackUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "UOW", ScenarioDetailsHashMap));
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "PackNetWeight", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "NetWeight", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "PackLength", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Length", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "PackWidth", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Width", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "PackHeight", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Height", ScenarioDetailsHashMap), 2);
			GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "PackUOM", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "UOM", ScenarioDetailsHashMap));
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Commodity", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Commodity", ScenarioDetailsHashMap), 2);
			//GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "HsCommodity", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "HSCommodity", ScenarioDetailsHashMap), 2);


			//Adding Goods Description 	Marks & Numbers in Cargo details
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "MarksNumberImg", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.selectWindow(driver);
			GenericMethods.pauseExecution(4000);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "MarksAndNumber", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "MarksAndNumbers", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "ExtendedDescription", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Goods_Description", ScenarioDetailsHashMap), 2);
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 3);
			GenericMethods.switchToParent(driver);
			AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(4000);

			GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "AddPacks", ScenarioDetailsHashMap, 10), 2);

			//Invoice
			/*if (ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "SSCCFormat",ScenarioDetailsHashMap).equalsIgnoreCase("Standard")) {
				GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "Standard",ScenarioDetailsHashMap, 10), 2);	
			}else{
				GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "NonStandard",ScenarioDetailsHashMap, 10), 2);	
			}
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "PONumber",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "PoNumber",ScenarioDetailsHashMap), 2);
			GenericMethods.pauseExecution(1000);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "PartId_Export",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "PartId_Export",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_GrossWeight",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "GrossWeight",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "NetWeight",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "NetWeight",ScenarioDetailsHashMap), 2);
			GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "Textbox_UOW",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "UOW",ScenarioDetailsHashMap));
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "StatQty",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "StatQty1",ScenarioDetailsHashMap), 2);
			AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "StatQty1TypeLov",ScenarioDetailsHashMap, 10), orHBL,"Lov_PackType", ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "StatQty1Type",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "StatQty2",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "StatQty2",ScenarioDetailsHashMap), 2);
			AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "StatQty2TypeLov",ScenarioDetailsHashMap, 10), orHBL,"Lov_PackType", ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "StatQty2Type",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "Textbox_InvoiceCommodityLov",ScenarioDetailsHashMap, 10), orHBL,"Code", ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Commodity",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "InvHSCommodityLov",ScenarioDetailsHashMap, 10), orHBL,"Code", ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "HSCommodity",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "InvGoodsDescription",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "GoodsDescription",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "InvGoodsValue",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "GoodsValue",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "InvCurrencyimp",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "GoodsCurrency",ScenarioDetailsHashMap), 2);

			if(driver.findElement(By.id("commInvButton")).isDisplayed())
			{
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "Btn_CommInvButton", ScenarioDetailsHashMap,10), 2);
			GenericMethods.pauseExecution(2000);
			}
			else
			{
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "InvoiceNumber", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "InvoiceNumber", ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(2000);
			}
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "Btn_Invoice_Add",ScenarioDetailsHashMap, 10), 2);
			 */		String GrossWeight=orSeaBooking.getElement(driver, "Readonly_GrossWeight", ScenarioDetailsHashMap,10).getAttribute("value");
			 System.out.println("GrossWeight:::"+GrossWeight);
			 ExcelUtils.setCellData("SE_MBLCargoPackDetails", RowNo, "GrossWeight", GrossWeight, ScenarioDetailsHashMap);

		}

	}

	//Pavan 20thJan 2015 Below method is to enter Values in AFR Module
	public static void HBL_AFR_Module(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		boolean AFR_Status=false;
		//Pavan Here in Below Condition if Inttra_Required=Yes only AFR module will be selected.
		//Based on the conditions which are mentioned in Automation Scenario Matrix for HBL module AFR is set to Yes in Test Data. And need to give the same if wants to executes AFR module
		AFR_Status=GenericMethods.isChecked(orHBL.getElement(driver, "CHB_AFR", ScenarioDetailsHashMap, 10));
		System.out.println("AFR_Status::"+AFR_Status);
		if(!AFR_Status){
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "CHB_AFR", ScenarioDetailsHashMap, 10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	//Pavan 20thJan 2015 Below method is to enter Values in AFR Module
	public static void HBL_INTTRA_Module(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		boolean Inttra_Status=false;
		//Pavan Here in Below Condition if Inttra_Required=Yes only Inttra module will be selected.
		//Pavan Based on the conditions which are mentioned in Automation Scenario Matrix for HBL module Inttra is set to Yes in Test Data. And need to give the same if wants to executes Inttra module
		Inttra_Status=GenericMethods.isChecked(orHBL.getElement(driver, "CHB_INTTRA", ScenarioDetailsHashMap, 10));
		System.out.println("Inttra_Status::"+Inttra_Status);
		if(!Inttra_Status){
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "CHB_INTTRA", ScenarioDetailsHashMap, 10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				e.printStackTrace();
			}
			orHBL.getElement(driver, "CarrierId", ScenarioDetailsHashMap, 10).clear();
			AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "CarrierIdLov", ScenarioDetailsHashMap, 10), orHBL, "CarrierId", ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			//			HBL_VesselScheduleMaster_MainDetails(driver, ScenarioDetailsHashMap, RowNo);
		}
	}
	//Pavan 2015 Below method is to enter RoutePlan Details in Route Plan Window.
	public static void HBL_RoutePlan_Details(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "RoutePlanTab", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(3000);
		int RoutePlan_rowCount=ExcelUtils.getCellDataRowCount("SE_HBLRoutePlanDetails", ScenarioDetailsHashMap);
		System.out.println("value:::"+RoutePlan_rowCount);
		for (int RoutePlanRowNo = 1; RoutePlanRowNo <= RoutePlan_rowCount; RoutePlanRowNo++) 
		{
			int rowId =  RoutePlanRowNo+1;
			GenericMethods.clickElement(driver, By.xpath("//tr[@id='"+RoutePlanRowNo+"']/td[12]/img"),null, 2);
			WebElement lovelement = driver.findElement(By.xpath("//tr[@id='"+RoutePlanRowNo+"']/td[4]/a/img")); 
			AppReusableMethods.selectValueFromLov(driver, lovelement, orHBL, "Lov_Textbox_PartyID", ExcelUtils.getCellData("SE_HBLRoutePlanDetails", RoutePlanRowNo, "Party",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(2000);
			GenericMethods.replaceTextofTextField(driver, By.xpath("//tr[@id='"+RoutePlanRowNo+"']/td[4]/input"), null, Keys.TAB, 2);
			GenericMethods.pauseExecution(2000);
			Select mode_DropdownValues = new Select(driver.findElement(By.xpath("//tr[@id='"+rowId+"']/td[5]/select")));
			mode_DropdownValues.selectByVisibleText(ExcelUtils.getCellData("SE_HBLRoutePlanDetails", RoutePlanRowNo, "Mode",ScenarioDetailsHashMap));
		}
		//		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
	}

	public static void seaHBL_SearchList(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		GenericMethods.pauseExecution(2000);
		AppReusableMethods.selectMenu(driver,ETransMenu.Hbl,"HBL", ScenarioDetailsHashMap);
		/*if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("Break Bulk")){
		GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "ShipmentRefNo", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), 2);
	}
	else{
		GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "HblNo", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), 2);
	}*/
		GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "HblNo", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), 2);

		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);

	}

	static int stuffed_CargoPackCount = 1;
	static boolean containerCreation = false;
	static int ContainerCreationID = 1;
	//Pavan:Jan 23rd 2015,Below method is to enter Container Details in Container Tab of Booking Module.
	public static void seaHBL_MBLContainerDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		String HBLId="";
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "ContainerDetails_Tab", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(2000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}
		int rowCount=ExcelUtils.getCellDataRowCount("SE_HBL_MBL_Container", ScenarioDetailsHashMap);
		System.out.println("pictOceanExportsOBL_ContainerDetails rowCount::"+rowCount);
		//Creation of Containers
		for (int ContainerCreationRowID = 1; ContainerCreationRowID <= rowCount; ContainerCreationRowID++) 
		{
			//Saving HBL_ID into Test data for further purpose
			HBLId=orHBL.getElement(driver, "SummaryHBlId", ScenarioDetailsHashMap, 10).getAttribute("value");
			ExcelUtils.setCellData("SE_HBL_MBL_Container", ContainerCreationRowID, "HBL_ID", HBLId, ScenarioDetailsHashMap);
			//Saving HBL_ID into SE_OBLContainers Test data for further purpose
			ExcelUtils.setCellData("SE_OBLContainers", ContainerCreationRowID, "HBL_ID", HBLId, ScenarioDetailsHashMap);


			boolean grid_ContainerNumberAvailability=false;
			WebElement ContainerDropdown_element = driver.findElement(By.xpath("//td[@id='CNTRDTLSAREA']/table/tbody/tr/td/table[1]/tbody/tr[2]/td[10]/select"));
			List<WebElement> dropdownvalues= new Select(ContainerDropdown_element).getOptions(); 
			for (int DropdownValue = 1; DropdownValue < dropdownvalues.size(); DropdownValue++) 
			{
				if(dropdownvalues.get(DropdownValue).getText().trim().equals(ExcelUtils.getCellData("SE_HBL_MBL_Container", ContainerCreationRowID, "ContainerNumber", ScenarioDetailsHashMap)))
				{
					grid_ContainerNumberAvailability=true;
					break;
				}

			}

			if(!grid_ContainerNumberAvailability)
			{
				if(containerCreation){
					GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "Button_ContainerDetailsAdd", ScenarioDetailsHashMap,10), 10);
					GenericMethods.pauseExecution(2000);
					ContainerCreationID=ContainerCreationID+1;
				}
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.id("containerType"+ContainerCreationID)));
				GenericMethods.pauseExecution(1000);
				try {
					GenericMethods.selectOptionFromDropDown(driver, By.id("containerType"+ContainerCreationID), null, ExcelUtils.getCellData("SE_HBL_MBL_Container", ContainerCreationRowID, "ContainerType", ScenarioDetailsHashMap));	
				} catch (Exception e) {

				}

				GenericMethods.replaceTextofTextField(driver, By.id("containerNo"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_HBL_MBL_Container", ContainerCreationRowID, "ContainerNumber", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("containerNo"+ContainerCreationID), null,  Keys.TAB, 20);
				GenericMethods.pauseExecution(3000);
				try{
					GenericMethods.handleAlert(driver, "accept");
				}catch (Exception e) {
				}
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, By.id("sealNo1"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_HBL_MBL_Container", ContainerCreationRowID, "Seal_Num1", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("sealNo2"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_HBL_MBL_Container", ContainerCreationRowID, "Seal_Num2", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("contGrossWt"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_HBL_MBL_Container", ContainerCreationRowID, "GrossWeight", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("contVolume"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_HBL_MBL_Container", ContainerCreationRowID, "Volume", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("pickUpReqDate"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_HBL_MBL_Container", ContainerCreationRowID, "PickUp_Date", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("pickUpTime"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_HBL_MBL_Container", ContainerCreationRowID, "PickUp_Time", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("cargoReadyDate"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_HBL_MBL_Container", ContainerCreationRowID, "CargoReady_Date", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("cargoReadyTime"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_HBL_MBL_Container", ContainerCreationRowID, "CargoReady_Time", ScenarioDetailsHashMap), 20);
				GenericMethods.pauseExecution(2000);
				containerCreation = true;
				//Pavan Updating ContainerNumber,SealNum1 and SealNum2 into OBL_Containers Sheet for further reference
				ExcelUtils.setCellData("SE_OBLContainers", ContainerCreationRowID, "Container_Number", ExcelUtils.getCellData("SE_HBL_MBL_Container", ContainerCreationRowID, "ContainerNumber", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				ExcelUtils.setCellData("SE_OBLContainers", ContainerCreationRowID, "Seal_Num1",  ExcelUtils.getCellData("SE_HBL_MBL_Container", ContainerCreationRowID, "Seal_Num1", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				ExcelUtils.setCellData("SE_OBLContainers", ContainerCreationRowID, "Seal_Num2",  ExcelUtils.getCellData("SE_HBL_MBL_Container", ContainerCreationRowID, "Seal_Num2", ScenarioDetailsHashMap), ScenarioDetailsHashMap);

			}
		}


		//Stuffing the Cargo Packs into Container
		for (int testdata_stuffingCargoPackDetails = 1; testdata_stuffingCargoPackDetails <= ExcelUtils.getCellDataRowCount("SE_HBL_MBL_Container", ScenarioDetailsHashMap); testdata_stuffingCargoPackDetails++) 
		{
			System.out.println("hello");
			int ContainerAssigningRowID =testdata_stuffingCargoPackDetails+1;
			String xpathPrefix="//td[@id='CNTRDTLSAREA']/table/tbody/tr/td/table["+stuffed_CargoPackCount+"]/tbody/tr["+ContainerAssigningRowID;
			try {
				GenericMethods.selectOptionFromDropDown(driver, By.xpath(xpathPrefix+"]/td[10]/select"), null, ExcelUtils.getCellData("SE_HBL_MBL_Container", testdata_stuffingCargoPackDetails, "ContainerNumber", ScenarioDetailsHashMap));	
			} catch (Exception e) {
				// TODO: handle exception
			}

			GenericMethods.pauseExecution(1000);
			GenericMethods.replaceTextofTextField(driver, By.xpath(xpathPrefix+"]/td[11]/input"), null,  ExcelUtils.getCellData("SE_HBL_MBL_Container", testdata_stuffingCargoPackDetails, "Container_Packages", ScenarioDetailsHashMap), 20);
			GenericMethods.pauseExecution(1000);
			GenericMethods.replaceTextofTextField(driver, By.xpath(xpathPrefix+"]/td[11]/input"), null,  Keys.TAB, 20);
		}
		stuffed_CargoPackCount=stuffed_CargoPackCount+1;

	}
	public static void seaHBL_Save(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo) throws InterruptedException
	{
		//		Added code to CheckChargeDetails and ChargesAndCosts
		/*AppReusableMethods.checkChargeDetails(driver, ScenarioDetailsHashMap, RowNo);
		AppReusableMethods.chargesAndCosts(driver, ScenarioDetailsHashMap, RowNo);*/
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
		String hblidSummary=GenericMethods.getInnerText(driver, null, orHBL.getElement(driver, "HblSaveDetail", ScenarioDetailsHashMap, 20), 2);
		String HblId=hblidSummary.split(" : ")[2].split(",")[0].trim();
		System.out.println("HblId::"+HblId);
		String CommonReference = (hblidSummary.split(" : ")[4]).split(",")[0].trim();
		System.out.println("CommonReference :"+CommonReference);
		//GenericMethods.assertTwoValues(hblidSummary.split(" : ")[1], ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBL_CreationMsg", ScenarioDetailsHashMap), "Validating HBL Creation Message", ScenarioDetailsHashMap);
		ExcelUtils.setCellData("SE_HBLMainDetails", RowNo, "HBLId", HblId, ScenarioDetailsHashMap);
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "InttraRequiredInOBL", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			ExcelUtils.setCellData("SE_OBL_INTTRA", RowNo, "HBLId", HblId, ScenarioDetailsHashMap);
		}
		ExcelUtils.setCellData("SE_HBLMainDetails", RowNo, "CommonReferenceNo", CommonReference, ScenarioDetailsHashMap);

		//Priya: added for Profit Share Validation
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ProfitShareRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes")){

			ExcelUtils.setCellData("Ocean_ProfitShareSatlement", RowNo, "HBLId", HblId, ScenarioDetailsHashMap);
		}


		for (int i = 1; i <= ExcelUtils.getCellDataRowCount("SI_Arrival", ScenarioDetailsHashMap); i++) {
			System.out.println("insde SI_Arrival11 ");
			ExcelUtils.setCellData("SI_Arrival", i, "HBL_ID",HblId, ScenarioDetailsHashMap);

		}
		for (int i = 1; i <= ExcelUtils.getCellDataRowCount("SI_ArrivalContainers", ScenarioDetailsHashMap); i++) {
			System.out.println("insde SI_ArrivalContainers 222");
			ExcelUtils.setCellData("SI_ArrivalContainers", i, "HBL_ID", HblId, ScenarioDetailsHashMap);

		}

		//Pavan--19032015--Updating HBL Id into Booking Sheet
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLBasis",ScenarioDetailsHashMap).equalsIgnoreCase("Assembly Shipment"))
		{
			for (int i = 1; i <= ExcelUtils.getSubScenarioRowCount("SE_BookingMainDetails", ScenarioDetailsHashMap); i++) {
				ExcelUtils.setCellData_Without_DataSet("SE_BookingMainDetails", i, "Assembly_HBLID", HblId, ScenarioDetailsHashMap);
			}
		}

		//	if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("Break Bulk")){

		GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "HblNo", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), 2);

		/*if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap).equalsIgnoreCase("Direct")){
		GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "ShipmentRefNo", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), 2);
	}
	else{
		GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "HblNo", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), 2);
	}*/

		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
		String ShipmentReferenceNumber=GenericMethods.getInnerText(driver, null, orHBL.getElement(driver, "GridShipmentReferenceNumber", ScenarioDetailsHashMap, 10), 10);
		ExcelUtils.setCellData("SE_HBLMainDetails", RowNo, "ShipmentReferenceNo", ShipmentReferenceNumber, ScenarioDetailsHashMap);

		if(!ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap).equalsIgnoreCase("Direct")){
			GenericMethods.assertInnerText(driver, null, orHBL.getElement(driver, "GridHblId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), "GridHblId", "Validating HBL Id",  ScenarioDetailsHashMap);
		}
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsolidationORB2B", ScenarioDetailsHashMap).equalsIgnoreCase("B2B")||
				ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap).equalsIgnoreCase("Direct")){
			String ConsoleID=GenericMethods.getInnerText(driver, null, orHBL.getElement(driver, "Grid_ConsoleId", ScenarioDetailsHashMap, 10), 10);
			ExcelUtils.setCellData("SE_HBLMainDetails", RowNo, "Console_ID", ConsoleID, ScenarioDetailsHashMap);
		}

		GenericMethods.pauseExecution(4000);
		//Masthan--Script added for Audit Trail Functionality
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "AuditTrailRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes")){

			SeaHBL_AuditTrail(driver,ScenarioDetailsHashMap,RowNo);

		}

		/*//Pavan FUNC029,FUNC030 Below method will Unblock shipment, which was blocked by using blocked customer.
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Unblock_Shipment", ScenarioDetailsHashMap).equalsIgnoreCase("Yes")){
			AppReusableMethods.unblockShipment(driver, ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, By.xpath("//b[contains(text(),'Shipments cleared sucessufully')]"), null, "Shipments cleared sucessufully", "Blocked_Shipments", "Validating Blocked Shipments", ScenarioDetailsHashMap);
		}//End FUNC029,FUNC030
		 */		
		//Updating HBL Id into SE_OBL sheet
		for (int i = 1; i <= ExcelUtils.getCellDataRowCount("SE_OBL", ScenarioDetailsHashMap); i++) {
			ScenarioDetailsHashMap.put("DataSetNo", i+"");
			ExcelUtils.setCellData("SE_OBL",RowNo, "HBL_ID", HblId, ScenarioDetailsHashMap);
			System.out.println("ScenarioDetailsHashMap.get(RowNo)"+ScenarioDetailsHashMap.get(RowNo));
			System.out.println("Excel update :::"+ExcelUtils.getCellData("SE_OBL", i, "HBL_ID", ScenarioDetailsHashMap));
		}
		//Priya: added for Profit Share Validation
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ProfitShareRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes")){

			ExcelUtils.setCellData("Ocean_ProfitShareSatlement", RowNo, "OblId", ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "MBL_ID", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		}
		ScenarioDetailsHashMap.put("DataSetNo", RowNo+"");
		System.out.println("DataSetNo out loop value::"+ScenarioDetailsHashMap.get("DataSetNo"));
		
	}

	//Masthan--this method is to validate the Audit Trail-Masthan
	public static void SeaHBL_AuditTrail(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo) throws InterruptedException
	{
		ObjectRepository orAuditTrail = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/AuditTrail.xml");

		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_MainDetails", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(3000);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "InstructionsRemarks", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo,"InstructionRemarks", ScenarioDetailsHashMap), 2);			
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			System.out.println("no Alerts present");
		}

		GenericMethods.pauseExecution(3000);

		String HBLIdSummary=GenericMethods.getInnerText(driver, null, orSeaBooking.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 20), 2);

		String HBLID=HBLIdSummary.split(":")[2].trim();
		String modifyMes=HBLIdSummary.split(":")[1].trim();
		GenericMethods.assertTwoValues(HBLID, ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), "Validating HBL Id", ScenarioDetailsHashMap);
		/*GenericMethods.assertTwoValues(modifyMes, ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ModifyMessage", ScenarioDetailsHashMap), "Validating Booking Modify Message", ScenarioDetailsHashMap);
		System.out.println("****"+modifyMes+"*****");
		System.out.println("****"+ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ModifyMessage", ScenarioDetailsHashMap)+"*****");
		 */
		GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "HblNo", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);

		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "AuditButton",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		String title=GenericMethods.getInnerText(driver, null, orCommon.getElement(driver, "BannerTitle", ScenarioDetailsHashMap,10), 2);
		GenericMethods.assertTwoValues(title,"Audit Trail", "Validating Page title for Audit Trail", ScenarioDetailsHashMap);
		String HBLID_grid=GenericMethods.getInnerText(driver, null, orAuditTrail.getElement(driver, "Booking_ID", ScenarioDetailsHashMap,10), 2);
		String User_grid=GenericMethods.getInnerText(driver, null, orAuditTrail.getElement(driver, "UserName", ScenarioDetailsHashMap,10), 2);
		String UserName=GenericMethods.getPropertyValue("userName", WebDriverInitilization.configurationStructurePath);
		GenericMethods.assertTwoValues(HBLID_grid,HBLID, "Validating Booking ID in Audit Trail grid", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(User_grid,UserName, "Validating UserName in Audit Trail grid", ScenarioDetailsHashMap);
		String actionID=GenericMethods.getInnerText(driver, null, orAuditTrail.getElement(driver, "ActionID", ScenarioDetailsHashMap,10), 2);
		GenericMethods.clickElement(driver, null,orAuditTrail.getElement(driver, "AuditFieldChanges_Tab",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		String InsRemarks=ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "InstructionRemarksField", ScenarioDetailsHashMap);

		Actions builder=new Actions(driver);
		int i=1;
		while(i>0){
			if(actionID.equalsIgnoreCase(driver.findElement(By.id("dtAuditMasterDtlId"+i)).getText())){

				if(driver.findElement(By.id("dtFieldName"+i)).getText().equalsIgnoreCase(InsRemarks)){
					String Original_Grid=driver.findElement(By.id("dtOriginalValue"+i)).getText();
					GenericMethods.highlightElement(driver.findElement(By.id("dtOriginalValue"+i)), driver);
					String Modified_Grid=driver.findElement(By.id("dtModifiedValue"+i)).getText();
					GenericMethods.highlightElement(driver.findElement(By.id("dtModifiedValue"+i)), driver);
					String Modified=ExcelUtils.getCellData("SE_HBLMainDetails", RowNo,"InstructionRemarks", ScenarioDetailsHashMap);
					String Original=null;
					if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLBasis", ScenarioDetailsHashMap).equalsIgnoreCase("From Booking"))
						Original=ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"InstructionsRemarks", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(Original_Grid,Original, "Validating Instruction Original Value in Audit Trail grid", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(Modified_Grid,Modified, "Validating Instruction Modified Value in Audit Trail grid", ScenarioDetailsHashMap);
					System.out.println("Instruction Remarks");
				}	
			}
			i=i+1;
			if (driver.findElements(By.id("dtAuditMasterDtlId"+i)).size()>0)
				builder.moveToElement(driver.findElement(By.id("dtAuditMasterDtlId"+i))).build().perform();
			else
				i=0;
		}

	}

	public static void seaHBL_PickUpInstructions(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "PickUpInstructions_Tab", ScenarioDetailsHashMap,10),2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		GenericMethods.pauseExecution(3000);
		Select pickUpStatus_DropdownValue = new Select(orHBL.getElement(driver, "PickUp_Status", ScenarioDetailsHashMap, 10));
		pickUpStatus_DropdownValue.selectByVisibleText(ExcelUtils.getCellData("SE_HBLPickUpInstructions", RowNo, "Status",ScenarioDetailsHashMap));


		//Pavan FUNC010.4,FUNC010.7,FUNC010.8,FUNC010.9--Below condition will perform Validations 
		if(!ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("Break bulk")&&
				!ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("RORO")){

			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PickUpRequired",ScenarioDetailsHashMap).equalsIgnoreCase("PickUp Instructions"))
			{
				GenericMethods.assertDropDownText(driver, null, orHBL.getElement(driver, "DDL_TypeofMovement", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLPickUpInstructions", RowNo, "TypeOfMovement",ScenarioDetailsHashMap), "HBL_MovementType", "Validating Default Movement Type", ScenarioDetailsHashMap);
				GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "PickUpLocation", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PickUpNickName",ScenarioDetailsHashMap), "HBL_PickUpLocation", "Validating Pickup Location Party", ScenarioDetailsHashMap);
				GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "PickUpAddress", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PickUp_Address",ScenarioDetailsHashMap), "HBL_PickUpAddress", "Validating Pickup Location Party Address", ScenarioDetailsHashMap);
				GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "PickUpLocation", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PickUpNickName",ScenarioDetailsHashMap), "HBL_PickUpLocation", "Validating Selected ShipperId into PickUp Instructions Screens Pickup Location Field", ScenarioDetailsHashMap);
			}
		}//FUNC010.4,FUNC010.7,FUNC010.8,FUNC010.9 End

		Select pickUpInstructionMovement_DropdownValue = new Select(orHBL.getElement(driver, "DDL_TypeofMovement", ScenarioDetailsHashMap, 10));
		pickUpInstructionMovement_DropdownValue.selectByVisibleText(ExcelUtils.getCellData("SE_HBLPickUpInstructions", RowNo, "TypeOfMovement",ScenarioDetailsHashMap));

		//		GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "PickUp_Status", ScenarioDetailsHashMap,10) , ExcelUtils.getCellData("SE_HBLPickUpInstructions", RowNo, "Status",ScenarioDetailsHashMap));
		//		GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "TypeofMovement", ScenarioDetailsHashMap,10) , ExcelUtils.getCellData("SE_HBLPickUpInstructions", RowNo, "TypeOfMovement",ScenarioDetailsHashMap));
		GenericMethods.pauseExecution(1500);
		String Prefix_ContainterCheckbox="//td[text()=' Container No.']/ancestor::tr[1]/td[2]/div[1]/table[1]/tbody/tr";
		int ContainerCheckboxescount = driver.findElements(By.xpath(Prefix_ContainterCheckbox)).size();
		for (int Grid_CheckboxRowId = 1; Grid_CheckboxRowId <= ContainerCheckboxescount; Grid_CheckboxRowId++) 
		{
			GenericMethods.clickElement(driver, By.xpath(Prefix_ContainterCheckbox+"["+Grid_CheckboxRowId+"]/td[1]"), null, 10);			
		}
		GenericMethods.pauseExecution(1500);

		//Pavan FUNC010.11,FUNC010.12,FUNC010.13,FUNC010.14,FUNC010.15--Below line will perform Validations 
		if(!ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("Break bulk")&&
				!ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("RORO")){

			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PickUpRequired",ScenarioDetailsHashMap).equalsIgnoreCase("Pick Up Run"))
			{
				GenericMethods.assertDropDownText(driver, null, orHBL.getElement(driver, "DDL_TypeofMovement", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLPickUpInstructions", RowNo, "TypeOfMovement",ScenarioDetailsHashMap), "HBL_MovementType", "Validating Movement Type in HBL Module", ScenarioDetailsHashMap);
			}
		}
		//FUNC010.11,FUNC010.12,FUNC010.13,FUNC010.14,FUNC010.15 End

		GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "Btn_Add", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(8000);
	}

	//Pavan:Feb 16th 2015,Below method is to Close the Master for Houses B2B.
	public static void seaHBL_MasterClose(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.pauseExecution(1500);
		GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "Button_MasterClose", ScenarioDetailsHashMap,10), 2);
		String hblidSummary=GenericMethods.getInnerText(driver, null, orHBL.getElement(driver, "HblSaveDetail", ScenarioDetailsHashMap, 20), 2);
		String HblId_Summary=hblidSummary.split(" : ")[1].trim();
		System.out.println("HblId_Summary:::"+HblId_Summary+":::testdata::::"+ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "MasterClose_Msg", ScenarioDetailsHashMap));
		GenericMethods.assertTwoValues(HblId_Summary,ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "MasterClose_Msg", ScenarioDetailsHashMap)+ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), "Validating Master Close", ScenarioDetailsHashMap);
	}
	//Sandeep: March 17th 2015, added below code to verify PU OR Label Text
	public static void PU_OR_LABEL_CHANGE(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.assertInnerText(driver, null, orSeaBooking.getElement(driver, "Tab_PickUpInstructions", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("SE_HBLPickUpInstructions", RowNo, "AfterSetupPickupTabText", ScenarioDetailsHashMap), "PickupTabText", "Validating Pickup Tab Text value after setup", ScenarioDetailsHashMap);
	}

	//FUNC18.4-Parties tab should be saved with provided special characters(/ - , . ()&_"+'@# including space)
	public static void partiesTabValidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		GenericMethods.pauseExecution(3000);
		System.out.println("in party click");
		orHBL.getElement(driver, "PartyDetails", ScenarioDetailsHashMap,10).click();
		//GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_Parties", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(3000);

		//logic to traverse table
		int partyTable= orHBL.getElements(driver, "table_addressDtlsGrid", ScenarioDetailsHashMap, 10).size();
		if(partyTable>0){
			for (int i = 1; i < partyTable; i++) {
				String partyXpath="//*[@id='divaddressDtlsGrid']/table/tbody/tr["+i+"]/td[4]";
				String salePerson="//*[@id='divaddressDtlsGrid']/table/tbody/tr["+i+"]/td[1]";
				String partyName=driver.findElement(By.xpath(salePerson)).getText();
				if(partyName.equalsIgnoreCase("SALES PERSON")){
					GenericMethods.assertInnerText(driver, By.xpath("//*[@id='divaddressDtlsGrid']/table/tbody/tr["+i+"]/td[3]"), null, ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "SalesPerson", ScenarioDetailsHashMap), "PARTYID", "Validation of Customer Selection to Sales Person mapping", ScenarioDetailsHashMap);
					GenericMethods.clickElement(driver, By.xpath(partyXpath), null, 10);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_street", ScenarioDetailsHashMap,10),  ExcelUtils.getCellData("SE_BookingPartyDetails", RowNo, "PartyAddress", ScenarioDetailsHashMap), 2);
					GenericMethods.pauseExecution(3000);

					WebElement myDynamicElement1 = new WebDriverWait(driver, 10).until(
							ExpectedConditions.presenceOfElementLocated(
									By.id("button.gridEditBtn")
							)
					);
					GenericMethods.clickElement(driver, null, myDynamicElement1, 2);	
					GenericMethods.pauseExecution(3000);
					GenericMethods.clickElement(driver, By.xpath(partyXpath), null, 10);
					GenericMethods.assertInnerText(driver, By.xpath(partyXpath),null,ExcelUtils.getCellData("SE_BookingPartyDetails", RowNo, "PartyAddress", ScenarioDetailsHashMap) , "PARTY ADDR", "Validating booking save on entering party tab address with special characters"  , ScenarioDetailsHashMap);
				}
			}
		}

	}


	//Pavan --Below method will perform validation for Location to Terminal Mapping 
	public static void Location_Terminal_Validations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "PortOfLoadingLov", ScenarioDetailsHashMap,10), orHBL,"PortOfLoadingField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Location_POL",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "LOV_PortOfDischargeLov", ScenarioDetailsHashMap,10), orHBL,"PortOfLoadingField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Location_POD",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_Parties", ScenarioDetailsHashMap,10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}
		String Location_Terminal_Msg=orHBL.getElement(driver, "Summary_Text_Location_Terminal", ScenarioDetailsHashMap,10).getText();
		ExcelUtils.setCellData("SE_HBLMainDetails", RowNo, "Location_Validation_Alert", Location_Terminal_Msg, ScenarioDetailsHashMap);
		System.out.println("Location_Terminal_Msg::"+Location_Terminal_Msg);
		if(Location_Terminal_Msg.equalsIgnoreCase(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Location_Validation_Alert", ScenarioDetailsHashMap))){
			GenericMethods.assertTwoValues(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Location_Validation_Msg", ScenarioDetailsHashMap), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Location_Validation_Msg", ScenarioDetailsHashMap), "Validating PortOfLanding Location to Terminal Mapping in HBL Module", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Location_Validation_Msg", ScenarioDetailsHashMap), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Location_Validation_Msg", ScenarioDetailsHashMap), "Validating PortOfDischarge Location to Terminal Mapping in HBL Module", ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "PortOfLoadingLov", ScenarioDetailsHashMap, 10), orHBL, "PortOfLoadingField",ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PortofLoading", ScenarioDetailsHashMap), ScenarioDetailsHashMap );
			AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "LOV_PortOfDischargeLov", ScenarioDetailsHashMap, 10), orHBL, "PortOfLoadingField", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PortofDischarge", ScenarioDetailsHashMap), ScenarioDetailsHashMap);

		}

	}
	//FUNC007 End


	//Sangeetha- Date Validation CreatedDate And LastModified Date
	public static void validationOfCreatedDateAndLastModifiedDate(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		seaHBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "HBL_MAIN_DETAILS_Tab", ScenarioDetailsHashMap,10),2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		//Date Validation Start Author-Sangeeta
		//FUNC062.9-Date Validation(Created Date / Time)
		String ActualCreatedDate =  driver.findElement(By.id("createdDate")).getAttribute("value");
		String ActualCreatedTime =  driver.findElement(By.name("createdTime")).getAttribute("value");
		String ExpectedCreatedDate = ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "CreatedDate", ScenarioDetailsHashMap);
		String ExpectedCreatedTime = ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "CreatedTime", ScenarioDetailsHashMap);
		String ActualCreatedDateTime = ActualCreatedDate.concat(" "+ActualCreatedTime);
		String ExpectedCreatedateTime = ExpectedCreatedDate.concat(" "+ExpectedCreatedTime);
		try {
			GenericMethods.compareDatesByCompareTo("dd-MM-yyyy hh:mm", ActualCreatedDateTime, ExpectedCreatedateTime, "Equal", "Created Date / Time is defaulted automatically current date and time", "Created Date / Time is not defaulted automatically current date and time", ScenarioDetailsHashMap);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 


		//FUNC062.10-Date Validation(Last Modified Date / Time)
		String ActualLastModifiedDate =  driver.findElement(By.id("modifiedDate")).getAttribute("value");
		String ActualLastModifiedTime =  driver.findElement(By.name("modifiedTime")).getAttribute("value");
		String ExpectedLastModifiedDate = ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "CreatedDate", ScenarioDetailsHashMap);
		String ExpectedLastModifiedTime = ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "CreatedTime", ScenarioDetailsHashMap);
		String ActualLastModifiedDateTime = ActualLastModifiedDate.concat(" "+ActualLastModifiedTime);
		String ExpectedLastModifiedDateTime = ExpectedLastModifiedDate.concat(" "+ExpectedLastModifiedTime);
		try {
			GenericMethods.compareDatesByCompareTo("dd-MM-yyyy hh:mm", ActualLastModifiedDateTime, ExpectedLastModifiedDateTime, "Equal", "Last Modified Date / Time is defaulted automatically current date and time", "Last Modified Date / Time is not defaulted automatically current date and time", ScenarioDetailsHashMap);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		//Date Validation end
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
	}

	//Pavan FUNC0033--Below method will perform copy functionality
	public static void copySeaHBLDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) {
		GenericMethods.pauseExecution(6000);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "CopyButton", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(12000);
		GenericMethods.assertDropDownText(driver, null, orHBL.getElement(driver, "HBLBasis", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLBasis", ScenarioDetailsHashMap), "HBL_Basis", "Validating HBL Basis in Copy Functionality", ScenarioDetailsHashMap);
		GenericMethods.assertDropDownText(driver, null, orHBL.getElement(driver, "LoadType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "LoadType", ScenarioDetailsHashMap), "HBL_Load_Type", "Validating Load Type in Copy Functionality", ScenarioDetailsHashMap);
		GenericMethods.assertDropDownText(driver, null, orHBL.getElement(driver, "HouseType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsolidationORB2B", ScenarioDetailsHashMap), "HBL_HouseType", "Validating Houses Type in Copy Functionality", ScenarioDetailsHashMap);
		GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "ShipperName", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipperNickName", ScenarioDetailsHashMap), "HBL_ShipperName", "Validating ShipperName in Copy Functionality", ScenarioDetailsHashMap);
		GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "ConsigneeName", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsigneeNickName", ScenarioDetailsHashMap), "HBL_ConsigneeName", "Validating ConsigneeName in Copy Functionality", ScenarioDetailsHashMap);
		GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "PortOfLoading", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PortofLoading", ScenarioDetailsHashMap), "HBL_POL", "Validating Port of Loading in Copy Functionality", ScenarioDetailsHashMap);
		GenericMethods.assertValue(driver, null, orHBL.getElement(driver, "PortOfDischarge", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PortofDischarge", ScenarioDetailsHashMap), "HBL_POD", "Validating Port of Discharge in Copy Functionality", ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}
		GenericMethods.pauseExecution(2000);
		GenericMethods.assertTwoValues(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "CopyShipment_Msg", ScenarioDetailsHashMap), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "CopyShipment_Msg", ScenarioDetailsHashMap), "Validating HBL shipment Created by copy Functionality", ScenarioDetailsHashMap);
	}


	//FUNC012.1 and	FUNC012.2 validation Start(Author-Sangeeta M)
	public static void seaHBL_FreightAndOtherChargesValidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "FreightChargesValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			seaHBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "CargoDetails", ScenarioDetailsHashMap, 10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				e.printStackTrace();
			}
			GenericMethods.pauseExecution(3000);

			String ExpTotalPieces = driver.findElement(By.name("totalPieces")).getAttribute("value");
			String ExpTotalVolume = new DecimalFormat("##.00").format(Float.parseFloat(driver.findElement(By.name("volume")).getAttribute("value")));
			System.out.println("ExpTotalVolume"+ExpTotalVolume);
			String ExpTotalGrossWeight = new DecimalFormat("##.00").format(Float.parseFloat(driver.findElement(By.name("actualWeight")).getAttribute("value")));
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "FreightAndOtherCharges", ScenarioDetailsHashMap, 10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				e.printStackTrace();
			}
			GenericMethods.pauseExecution(3000);

			//Closing opened Mail window
			GenericMethods.pauseExecution(4000);
			try{
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {
			}
			GenericMethods.pauseExecution(4000);

			try {
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_F);
				robot.keyPress(KeyEvent.VK_C);
				robot.keyRelease(KeyEvent.VK_ALT);
				robot.keyRelease(KeyEvent.VK_F);
				robot.keyRelease(KeyEvent.VK_C);
				GenericMethods.pauseExecution(1000);
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				GenericMethods.pauseExecution(1000);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
			} catch (Exception e) {
			}
			String ActTotalPieces = driver.findElement(By.name("totalPiecesTemp")).getAttribute("value");
			String ActTotalVolume = new DecimalFormat("##.00").format(Float.parseFloat(driver.findElement(By.name("volumeTemp")).getAttribute("value")));	
			String ActTotalGrossWeight = new DecimalFormat("##.00").format(Float.parseFloat(driver.findElement(By.name("weightTemp")).getAttribute("value")));
			GenericMethods.assertTwoValues(ActTotalPieces, ExpTotalPieces,"Validating defaulting of the Charge Details of Pieces from Cargo Details screen.", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(ActTotalVolume, ExpTotalVolume,"Validating defaulting of the Charge Details of Volumes from Cargo Details screen.", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(ActTotalGrossWeight,ExpTotalGrossWeight, "Validating defaulting of the Charge Details of Weight from Cargo Details screen.", ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "ChargeBasisGrid", ScenarioDetailsHashMap, 10), 2);
			if(ExcelUtils.getCellData("SE_HBLFreightOtherCharges", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("LCL"))
			{
				GenericMethods.assertTwoValues(driver.findElement(By.id("buyChargeBasis")).getAttribute("value"), "B061", "Verifying the Freight default Charge Basis as B061 When load type is LCL", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(driver.findElement(By.id("sellChargeBasis")).getAttribute("value"), "B061", "Verifying the Freight default Charge Basis as B061 When load type is LCL", ScenarioDetailsHashMap);
				//GenericMethods.assertTwoValues(driver.findElement(By.id("buyQuantityStr")).getAttribute("value"), TotalVolume, "Verifying the Freight Buy Quantity as Total Charge Volume When load type is LCL", ScenarioDetailsHashMap);
				//GenericMethods.assertTwoValues(driver.findElement(By.id("sellQuantityStr")).getAttribute("value"), TotalVolume, "Verifying the Freight Sell Quantity as Total Charge Volume When load type is LCL", ScenarioDetailsHashMap);
			}
			else if(ExcelUtils.getCellData("SE_HBLFreightOtherCharges", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("FCL"))
			{
				ArrayList<String> AppContainerTypeValues = new ArrayList<String>();
				for(int dtsetNo=1;dtsetNo<=ExcelUtils.getCellDataRowCount("SE_OBLContainersDetails", ScenarioDetailsHashMap);dtsetNo++)
				{
					AppContainerTypeValues.add(ExcelUtils.getCellData("SE_OBLContainersDetails", dtsetNo, "ContainerType", ScenarioDetailsHashMap));
				}
				ArrayList<String> UniqueContainerTypeValues = new ArrayList<String>();
				for (int row = 0; row < AppContainerTypeValues.size(); row++) 
				{
					if(!UniqueContainerTypeValues.contains(AppContainerTypeValues.get(row)))
					{
						UniqueContainerTypeValues.add(AppContainerTypeValues.get(row));	
					}
				}
				GenericMethods.assertTwoValues(driver.findElements(By.xpath("html/body/form/table/tbody/tr[8]/td/div/table/tbody/tr")).size()+"", UniqueContainerTypeValues.size()+"", "Verifying more than one Fright Line when user provides more than one Container Type.", ScenarioDetailsHashMap);
				for(int GridRow=1;GridRow<=driver.findElements(By.xpath("html/body/form/table/tbody/tr[8]/td/div/table/tbody/tr")).size();GridRow++)
				{
					GenericMethods.clickElement(driver, By.xpath("html/body/form/table/tbody/tr[8]/td/div/table/tbody/tr["+GridRow+"]"), null, 10);
					GenericMethods.assertTwoValues(driver.findElement(By.id("buyChargeBasis")).getAttribute("value"), "B060", "Verifying the Freight default Charge Basis as B060 When load type is FCL", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(driver.findElement(By.id("sellChargeBasis")).getAttribute("value"), "B060", "Verifying the Freight default Charge Basis as B060 When load type is FCL", ScenarioDetailsHashMap);
				}
			}
			for(int GridRow=1;GridRow<=driver.findElements(By.xpath("html/body/form/table/tbody/tr[8]/td/div/table/tbody/tr")).size();GridRow++)
			{
				GenericMethods.clickElement(driver, By.xpath("html/body/form/table/tbody/tr[8]/td/div/table/tbody/tr["+GridRow+"]"), null, 10);
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "BuyMinAmount", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SE_HBLFreightOtherCharges", GridRow, "MinBuyAmount", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "SellMinAmount", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SE_HBLFreightOtherCharges", GridRow, "MinSellAmount", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "BuyMinRate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLFreightOtherCharges", GridRow, "BuyRate", ScenarioDetailsHashMap), 2);
				GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "BuyAmount", ScenarioDetailsHashMap, 10), 2);
				String BuyQuantity = driver.findElement(By.name("buyQuantityStr")).getAttribute("value");
				String BuyRate = driver.findElement(By.name("buyRateStr")).getAttribute("value");
				float MinumBuyAmount = Float.parseFloat(driver.findElement(By.id("buyMinAmountStr")).getAttribute("value"));
				float ActualBuyAmount = (Float.parseFloat(BuyQuantity))*(Float.parseFloat(BuyRate));
				float ExpBuyAmount = Float.parseFloat(driver.findElement(By.name("buyAmountStr")).getAttribute("value"));
				if(MinumBuyAmount>ActualBuyAmount)
				{
					GenericMethods.assertTwoValues(MinumBuyAmount+"", ExpBuyAmount+"", "validating calculation of BuyAmount", ScenarioDetailsHashMap);
				}
				else
				{
					GenericMethods.assertTwoValues(ActualBuyAmount+"", ExpBuyAmount+"", "validating calculation of BuyAmount", ScenarioDetailsHashMap);
				}
				GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "SellRate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLFreightOtherCharges", RowNo, "SellRate", ScenarioDetailsHashMap), 2);
				GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "SellAmount", ScenarioDetailsHashMap, 10), 2);
				String SellQuantity = driver.findElement(By.name("sellQuantityStr")).getAttribute("value");
				String SellRate = driver.findElement(By.name("sellRateStr")).getAttribute("value");
				float MinumSellAmount = Float.parseFloat(driver.findElement(By.id("sellMinAmountStr")).getAttribute("value"));
				float ActualSellAmount = (Float.parseFloat(SellQuantity))*(Float.parseFloat(SellRate));
				float ExpSellAmount = Float.parseFloat(driver.findElement(By.name("sellAmountStr")).getAttribute("value"));
				if(MinumSellAmount>ActualSellAmount)
				{
					GenericMethods.assertTwoValues(MinumSellAmount+"", ExpSellAmount+"", "validating calculation of SellAmount", ScenarioDetailsHashMap);
				}
				else
				{
					GenericMethods.assertTwoValues(ActualSellAmount+"", ExpSellAmount+"", "validating calculation of SellAmount", ScenarioDetailsHashMap);
				}
				if(ExcelUtils.getCellData("SE_HBLFreightOtherCharges", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("LCL"))
				{
					String Buy_Quantity = new DecimalFormat("##.00").format(Float.parseFloat(BuyQuantity));
					//System.out.println("Buy_Quantity"+Buy_Quantity);	
					String Sell_Quantity = new DecimalFormat("##.00").format(Float.parseFloat(SellQuantity));
					//System.out.println("Sell_Quantity"+Sell_Quantity);
					GenericMethods.assertTwoValues(Buy_Quantity, ExpTotalVolume, "Verifying the Freight Buy Quantity as Total Charge Volume When load type is LCL", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(Sell_Quantity, ExpTotalVolume, "Verifying the Freight Sell Quantity as Total Charge Volume When load type is LCL", ScenarioDetailsHashMap);
				}
				GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "FrieghtChrgesEditBtn", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.pauseExecution(2000);
			}
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "ChargeBasisGrid", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {

			}
			GenericMethods.pauseExecution(5000);
		}

	}
	//Pavan FUNC029,FUNC030 Below method will Unblock shipment, which was blocked by using blocked customer.
	public static void HBL_Unblock_Shipment(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Unblock_Shipment", ScenarioDetailsHashMap).equalsIgnoreCase("Yes")){
			AppReusableMethods.unblockShipment(driver, ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			String UnblockMSG = GenericMethods.getInnerText(driver, null, orCommon.getElement(driver, "UnblockSuccessMSG", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.assertTwoValues(UnblockMSG.split(":")[1].trim(), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "UnblockSuccessMSG", ScenarioDetailsHashMap), "Validating HBL Blocked Shipments", ScenarioDetailsHashMap);
			//GenericMethods.assertValue(driver, By.xpath("//b[contains(text(),'Shipments cleared sucessufully')]"), null, "Shipments cleared sucessufully", "Blocked_Shipments", "Validating HBL Blocked Shipments", ScenarioDetailsHashMap);
		}
	}//End FUNC029,FUNC030

	//Pavan FUNC029,FUNC030 Below method will Unblock shipment, which was blocked by using blocked customer.
	public static void Booking_Unblock_Shipment(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		AppReusableMethods.unblockShipment(driver, ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "BookingId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		String UnblockMSG = GenericMethods.getInnerText(driver, null, orCommon.getElement(driver, "UnblockSuccessMSG", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.assertTwoValues(UnblockMSG.split(":")[1].trim(), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "UnblockSuccessMSG", ScenarioDetailsHashMap), "Validating Booking Blocked Shipments", ScenarioDetailsHashMap);
		//GenericMethods.assertValue(driver, By.xpath("//b[contains(text(),'Shipments cleared sucessufully')]"), null, "Shipments cleared sucessufully", "Blocked_Shipments", "Validating Booking Blocked Shipments", ScenarioDetailsHashMap);
	}//End FUNC029,FUNC030


	//Sangeeta- RAT03/04 - Below method is to  validating  selected Contract/Quote is Hazardous or not
	public static void  HazardousValidationForRating(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		seaHBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);	
		GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "CargoDetails", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(6000);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
		GenericMethods.pauseExecution(7000);
		GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "GoHazardous_Button", ScenarioDetailsHashMap,10), 2);	
		GenericMethods.pauseExecution(2000);
		GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "Hazardous_CheckBox", ScenarioDetailsHashMap,10), 2);	
		GenericMethods.pauseExecution(4000);
		try{
			GenericMethods.selectWindow(driver);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Hazardous_NumofPack", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Haz_ItemsNo", ScenarioDetailsHashMap), 2);
			GenericMethods.selectOptionFromDropDown(driver,null,orHBL.getElement(driver, "Hazardous_PackType", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Haz_ItemType", ScenarioDetailsHashMap));
			GenericMethods.action_Key(driver, Keys.TAB);
			orHBL.getElement(driver, "Hazardous_netQuantity", ScenarioDetailsHashMap,10).sendKeys(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Haz_NetWeight", ScenarioDetailsHashMap));
			GenericMethods.action_Key(driver, Keys.TAB);
			GenericMethods.selectOptionFromDropDown(driver,null,orHBL.getElement(driver, "Hazardous_netQuantityUom", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Haz_NetWeightUnit", ScenarioDetailsHashMap));
			GenericMethods.action_Key(driver, Keys.TAB);
			orHBL.getElement(driver, "Hazardous_grossWeight", ScenarioDetailsHashMap,10).sendKeys(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "HazGrossWeight", ScenarioDetailsHashMap));
			GenericMethods.action_Key(driver, Keys.TAB);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Hazardous_UNDGNumber", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Haz_UNDGNumber", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Hazardous_contactName", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "HazContactName", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Hazardous_contactNumber", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Haz_ContactNumber", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Hazardous_properShippingName", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Haz_ProperShippingName", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Hazardous_technicalName", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Haz_TechnicalName", ScenarioDetailsHashMap), 2);
			GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);	
			GenericMethods.pauseExecution(4000);
			GenericMethods.switchToParent(driver);
			AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
		}catch(Exception e){
			GenericMethods.assertTwoValues("PopUp Screen Not Opened", "PopUp Screen Opened", "Verifying Haz PopUp Screen", ScenarioDetailsHashMap);

		}
		GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "AddPacks", ScenarioDetailsHashMap, 10), 2);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
		//"EKFC10124 : Selected Contract/Quote is Not Hazardous.";
		System.out.println(GenericMethods.getInnerText(driver, null, orHBL.getElement(driver, "HazMSG", ScenarioDetailsHashMap, 2), 10));
		System.out.println(driver.findElement(By.xpath("html/body/form/table[1]/tbody/tr[2]/td/table/tbody/tr[2]/td/table[2]/tbody/tr/td/font/b")).getText());
		String HazErrMSGSummary = GenericMethods.getInnerText(driver, null, orHBL.getElement(driver, "HazMSG", ScenarioDetailsHashMap, 2), 10);
		String[] HazErrorMSGApp =HazErrMSGSummary.split(" : ");
		String HazErrMSGApp = HazErrorMSGApp[1];
		GenericMethods.pauseExecution(3000);
		if((ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "eRatingValidationRequired", ScenarioDetailsHashMap)).equalsIgnoreCase("Yes"))
		{
			if(HazErrMSGApp.contains("Hazardous"))
			{
				GenericMethods.assertTwoValues(HazErrMSGApp, ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "HazErrorMSG", ScenarioDetailsHashMap), "Validating Haz details  when selected Contract/Quote is not Hazardous", ScenarioDetailsHashMap);
				GenericMethods.pauseExecution(2000);
				GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "Hazardous_CheckBox", ScenarioDetailsHashMap,10), 2);
				try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
				GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "AddPacks", ScenarioDetailsHashMap, 10), 2);
				try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
				GenericMethods.pauseExecution(3000);
				GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
				try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
				GenericMethods.pauseExecution(3000);
			}
			else
			{
				GenericMethods.assertTwoValues(HazErrMSGApp, ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "HazErrorMSG", ScenarioDetailsHashMap), "Validating Haz details  when selected Contract/Quote is not Hazardous", ScenarioDetailsHashMap);
			}
		}
		else
		{
			GenericMethods.pauseExecution(2000);
			String ExpectedMessge = "Record Successfully Modified with HBL No";
			GenericMethods.assertTwoValues(HazErrMSGApp, ExpectedMessge, "Validating Haz details  when selected Contract/Quote is  Hazardous", ScenarioDetailsHashMap);
		}
	}

	//Sangeeta- FUNC02 - Below method is to HS commodity if Quote Commodity and Cargo details HS commodity is not matched 
	public static void  HScommodityValidationForRating(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		seaHBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);	
		GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "CargoDetails", ScenarioDetailsHashMap, 10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");

		}catch (Exception e) {
			e.printStackTrace();
		}
		GenericMethods.pauseExecution(7000);
		AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "Img_PackHsCommodity", ScenarioDetailsHashMap,10), orHBL,"Code", ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "HSCommodity_Rating",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "ScheduleB_Pack", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Schedule_B",ScenarioDetailsHashMap), 2);
		GenericMethods.pauseExecution(3000);
		//EKFC10324 : HsCommodity entered is not valid for contract selected. Valid commodities:  1343
		//System.out.println("After1"+driver.findElement(By.id("packHsCommodity")).getAttribute("displayerrorname"));
		String HSCommodityErrMSGApp = driver.findElement(By.id("packHsCommodity")).getAttribute("displayerrorname");
		String[] CommodityMSG = HSCommodityErrMSGApp.split(" Valid commodities: ");
		String HSCommodityErrorMSGApp =CommodityMSG[0].split(" : ")[1];
		System.out.println("HSCommodityErrorMSGApp"+HSCommodityErrorMSGApp);
		if(!HSCommodityErrMSGApp.equalsIgnoreCase(""))
		{
			GenericMethods.assertTwoValues(HSCommodityErrorMSGApp, ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "HSCommodityErrorMSG", ScenarioDetailsHashMap), "Validating HS Commodity in Cargo details when Quote Commodity is not matched with HS commodity", ScenarioDetailsHashMap);
		}
		else
		{
			String HSCommodityValidMSG = "HsCommodity entered is valid for contract selected";
			GenericMethods.assertTwoValues(HSCommodityValidMSG, ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "HSCommodityErrorMSG", ScenarioDetailsHashMap), "Validating HS Commodity in Cargo details when Quote Commodity is not matched with HS commodity", ScenarioDetailsHashMap);
		}
		AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "Img_PackHsCommodity", ScenarioDetailsHashMap,10), orHBL,"Code", ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "HSCommodity",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "ScheduleB_Pack", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Schedule_B",ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
	}


	//Sangeeta M(RAT11,RAT12,RAT14,RAT16)- This method will Freight and Other charges all validation
	public static void seaHBL_FreightAndOtherCharges(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "eRatingFreightChargesValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			seaHBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "FreightAndOtherCharges", ScenarioDetailsHashMap, 10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				e.printStackTrace();
			}
			GenericMethods.pauseExecution(3000);

			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "RatingType", ScenarioDetailsHashMap).equalsIgnoreCase("AutoRate"))
			{
				GenericMethods.assertTwoValues(GenericMethods.getInnerText(driver, null, orHBL.getElement(driver, "AutoRateMSG", ScenarioDetailsHashMap, 10	), 2), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "AutoRate_SucessMSG", ScenarioDetailsHashMap), "Validating Auto Rate Sucess Message", ScenarioDetailsHashMap);
				try{
					GenericMethods.handleAlert(driver, "accept");

				}catch (Exception e) {
					e.printStackTrace();
				}
				GenericMethods.pauseExecution(3000);

				//Closing opened Mail window
				GenericMethods.pauseExecution(4000);
				try{
					GenericMethods.handleAlert(driver, "accept");
				}catch (Exception e) {
				}
				GenericMethods.pauseExecution(4000);
				GenericMethods.pauseExecution(4000);
				String HBLId=ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap);
				try {
					GenericMethods.pauseExecution(4000);
					Robot robot = new Robot();
					/*robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_S);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					robot.keyRelease(KeyEvent.VK_S);*/


					robot.keyPress(KeyEvent.VK_ALT);
					robot.keyPress(KeyEvent.VK_F);
					robot.keyPress(KeyEvent.VK_C);
					robot.keyRelease(KeyEvent.VK_ALT);
					robot.keyRelease(KeyEvent.VK_F);
					robot.keyRelease(KeyEvent.VK_C);
					GenericMethods.pauseExecution(1000);
					//					robot.keyPress(KeyEvent.VK_TAB);
					//					robot.keyRelease(KeyEvent.VK_TAB);
					GenericMethods.pauseExecution(1000);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);
					GenericMethods.pauseExecution(4000);
					//MAILCheck(ScenarioDetailsHashMap, HBLId);

					GenericMethods.assertTwoValues("Mail PopUp Opened", "Mail PopUp Opened", "Verifying PopUp for AutoRate with HBL-->"+HBLId, ScenarioDetailsHashMap);


				} catch (Exception e) {

					GenericMethods.assertTwoValues("PopUp Not found", "PopUp found", "Verifying PopUp for AutoRate with HBL-->"+HBLId, ScenarioDetailsHashMap);

				}

			}
			System.out.println(driver.findElements(By.xpath("html/body/form/table/tbody/tr[8]/td/div/table/tbody/tr")).size());

			//Number of row count of charge code row = Number of row in SE_HBLFreightOtherCharges sheet
			/*for(int GridRow=1;GridRow<=driver.findElements(By.xpath("html/body/form/table/tbody/tr[8]/td/div/table/tbody/tr")).size();GridRow++)
			{*/
			for(int GridRow=1;GridRow<=ExcelUtils.getCellDataRowCount("SE_HBLFreightOtherCharges", ScenarioDetailsHashMap);GridRow++)
			{

				GenericMethods.clickElement(driver, By.xpath("html/body/form/table/tbody/tr[8]/td/div/table/tbody/tr["+GridRow+"]"), null, 10);
				if(ExcelUtils.getCellData("SE_HBLFreightOtherCharges", RowNo, "ConsolidationORB2B", ScenarioDetailsHashMap).equalsIgnoreCase("B2B"))
				{
					String ActualDefaultContractID = driver.findElement(By.xpath("//input[@id='ratingIdTemp']")).getAttribute("value");
					if(ExcelUtils.getCellData("SE_HBLFreightOtherCharges", RowNo, "CustomerContract", ScenarioDetailsHashMap).equalsIgnoreCase(ActualDefaultContractID))
					{

					}

					//Buy Validation
					String Buy_Quantity = driver.findElement(By.name("buyQuantityStr")).getAttribute("value");
					String BuyQuantity;
					if(Buy_Quantity.contains(" "))
					{
						BuyQuantity = Buy_Quantity.replace(" ", "");
					}
					else
					{
						BuyQuantity = Buy_Quantity;
					}
					String Buy_Rate = driver.findElement(By.name("buyRateStr")).getAttribute("value");
					String BuyRate;
					if(Buy_Rate.contains(" "))
					{
						BuyRate = Buy_Rate.replace(" ", "");
					}
					else
					{
						BuyRate = Buy_Rate;
					}

					//Minimum BuyAmount Calculation
					String Min_BuyAmount =  driver.findElement(By.id("buyMinAmountStr")).getAttribute("value");
					String MinBuy_Amount;
					if(Min_BuyAmount.contains(" "))
					{
						MinBuy_Amount = Min_BuyAmount.replace(" ", "");
					}
					else
					{
						MinBuy_Amount = Min_BuyAmount;
					}
					float MinBuyAmount = Float.parseFloat(MinBuy_Amount);
					//System.out.println(" b4 MinBuyAmount"+MinBuyAmount);
					String MinimumBuy_Amount = new DecimalFormat("##.00").format(MinBuyAmount);
					//System.out.println(" a4 MinBuyAmount"+MinimumBuy_Amount);

					//Buy Amount Calculation
					float Expected_BuyAmount = (Float.parseFloat(BuyQuantity))*(Float.parseFloat(BuyRate));
					String Exp_BuyAmount = String.valueOf(Expected_BuyAmount);
					String ExpBuy_Amount;
					if(Exp_BuyAmount.contains(" "))
					{
						ExpBuy_Amount = Exp_BuyAmount.replace(" ", "");
					}
					else
					{
						ExpBuy_Amount = Exp_BuyAmount;
					}
					float ExpBuyAmount = Float.parseFloat(ExpBuy_Amount);
					//System.out.println(" b4 ExpBuyAmount"+ExpBuyAmount);
					String ExpectedBuy_Amount = new DecimalFormat("##.00").format(ExpBuyAmount);
					//System.out.println(" a4 ExpectedBuy_Amount"+ExpectedBuy_Amount);


					String Actual_Buyamount = driver.findElement(By.name("buyAmountStr")).getAttribute("value");
					String ActBuy_Amount;
					if(Actual_Buyamount.contains(" "))
					{
						ActBuy_Amount = Actual_Buyamount.replace(" ", "");
					}
					else
					{
						ActBuy_Amount = Actual_Buyamount;
					}
					float ActBuyAmount = Float.parseFloat(ActBuy_Amount);
					//System.out.println(" b4 ActualBuy_Amount"+ActBuyAmount);
					String ActualBuy_Amount = new DecimalFormat("##.00").format(ActBuyAmount);
					//System.out.println(" a4 ActualBuy_Amount"+ActualBuy_Amount);

					if(Float.parseFloat(MinimumBuy_Amount)>Float.parseFloat(ExpectedBuy_Amount))
					{
						GenericMethods.assertTwoValues(MinimumBuy_Amount+"",ExpectedBuy_Amount+"", "validating calculation of BuyAmount of row : "+GridRow, ScenarioDetailsHashMap);
					}
					else
					{
						GenericMethods.assertTwoValues(ActualBuy_Amount+"",ExpectedBuy_Amount+"",  "validating calculation of BuyAmount of row : "+GridRow, ScenarioDetailsHashMap);
					}



					//Sell Validation
					String Sell_Quantity = driver.findElement(By.name("sellQuantityStr")).getAttribute("value");
					String SellQuantity;
					if(Sell_Quantity.contains(" "))
					{
						SellQuantity = Sell_Quantity.replace(" ", "");
					}
					else
					{
						SellQuantity = Sell_Quantity;
					}
					String Sell_Rate = driver.findElement(By.name("sellRateStr")).getAttribute("value");
					String SellRate;
					if(Sell_Rate.contains(" "))
					{
						SellRate = Sell_Rate.replace(" ", "");
					}
					else
					{
						SellRate = Sell_Rate;
					}

					//Minimum Sell Amount Calculation
					float Minimum_SellAmount = Float.parseFloat(driver.findElement(By.id("sellMinAmountStr")).getAttribute("value"));
					String MinSell_Amount = String.valueOf(Minimum_SellAmount);
					String MinSellAmount;
					if(MinSell_Amount.contains(" "))
					{
						MinSellAmount = MinSell_Amount.replace(" ", "");
					}
					else
					{
						MinSellAmount = MinSell_Amount;
					}
					float Min_SellAmount = Float.parseFloat(MinSellAmount);
					//System.out.println(" b4 MinBuyAmount"+Min_SellAmount);
					String MinimumSell_Amount = new DecimalFormat("##.00").format(Min_SellAmount);
					//System.out.println(" a4 MinimumSell_Amount"+MinimumSell_Amount);


					//Sell Amount Calculation
					float Expected_SellAmount = (Float.parseFloat(SellQuantity))*(Float.parseFloat(SellRate));
					String Exp_SellAmount = String.valueOf(Expected_SellAmount);
					String ExpSell_Amount;
					if(Exp_SellAmount.contains(" "))
					{
						ExpSell_Amount = Exp_SellAmount.replace(" ", "");
					}
					else
					{
						ExpSell_Amount = Exp_SellAmount;
					}

					float ExpSellAmount = Float.parseFloat(ExpSell_Amount);
					//System.out.println(" b4 ExpBuyAmount"+ExpSellAmount);
					String ExpectedSell_Amount = new DecimalFormat("##.00").format(ExpSellAmount);
					//System.out.println(" a4 ExpectedBuy_Amount"+ExpectedSell_Amount);

					String ActualSellamount = driver.findElement(By.name("sellAmountStr")).getAttribute("value");
					String ActSell_Amount;
					if(ActualSellamount.contains(" "))
					{
						ActSell_Amount = ActualSellamount.replace(" ", "");
					}
					else
					{
						ActSell_Amount = ActualSellamount;
					}
					float ActSellAmount = Float.parseFloat(ActSell_Amount);
					//System.out.println(" b4 ActSellAmount"+ActSellAmount);
					String ActualSell_Amount = new DecimalFormat("##.00").format(ActSellAmount);
					//System.out.println(" a4 ActualSell_Amount"+ActualSell_Amount);

					if((Float.parseFloat(MinimumSell_Amount))>Float.parseFloat(ExpectedSell_Amount))
					{
						GenericMethods.assertTwoValues(MinimumSell_Amount+"", ExpectedSell_Amount+"", "validating calculation of SellAmount in row : "+GridRow, ScenarioDetailsHashMap);
					}
					else
					{
						GenericMethods.assertTwoValues(ActualSell_Amount+"", ExpectedSell_Amount+"", "validating calculation of SellAmount in row : "+GridRow, ScenarioDetailsHashMap);
					}
					GenericMethods.assertTwoValues(driver.findElement(By.id("dtChargeCode"+GridRow)).getText(), ExcelUtils.getCellData("SE_HBLFreightOtherCharges", GridRow, "Charge_Code", ScenarioDetailsHashMap), "Validating Charge code in row : "+GridRow, ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(driver.findElement(By.id("buyChargeBasis")).getAttribute("value"), ExcelUtils.getCellData("SE_HBLFreightOtherCharges", GridRow, "BuyCharge_Basis", ScenarioDetailsHashMap), "Validating Buy Charge Basis in row : "+GridRow, ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(driver.findElement(By.id("sellChargeBasis")).getAttribute("value"), ExcelUtils.getCellData("SE_HBLFreightOtherCharges", GridRow, "SellCharge_Basis", ScenarioDetailsHashMap), "Validating Sell Charge Basis in row : "+GridRow, ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(driver.findElement(By.id("buyRateStr")).getAttribute("value"), ExcelUtils.getCellData("SE_HBLFreightOtherCharges", GridRow, "DefaulBuytRate", ScenarioDetailsHashMap), "Validating Buy Rate for row : "+GridRow, ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(driver.findElement(By.id("sellRateStr")).getAttribute("value"), ExcelUtils.getCellData("SE_HBLFreightOtherCharges", GridRow, "DefaultSellRate", ScenarioDetailsHashMap), "Validating Sell Rate in row : "+GridRow, ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(driver.findElement(By.id("buyCurrency")).getAttribute("value"), ExcelUtils.getCellData("SE_HBLFreightOtherCharges", GridRow, "BuyCurrency", ScenarioDetailsHashMap), "Validating Buy Currency for row : "+GridRow, ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(driver.findElement(By.id("sellCurrency")).getAttribute("value"), ExcelUtils.getCellData("SE_HBLFreightOtherCharges", GridRow, "SellCurrency", ScenarioDetailsHashMap), "Validating Sell Currency in row : "+GridRow, ScenarioDetailsHashMap);
					Select buyPC = new Select(driver.findElement(By.id("buyPcIndicator")));
					String actualBuyselectedValue = buyPC.getFirstSelectedOption().getText();
					GenericMethods.assertTwoValues(actualBuyselectedValue, ExcelUtils.getCellData("SE_HBLFreightOtherCharges", GridRow, "BuyPrepaidOrCollect", ScenarioDetailsHashMap), "Validating Buy Prepaid/Collect in row : "+GridRow, ScenarioDetailsHashMap);
					Select SellPC = new Select(driver.findElement(By.id("sellPcIndicator")));
					String actualSellselectedValue = SellPC.getFirstSelectedOption().getText();
					GenericMethods.assertTwoValues(actualSellselectedValue, ExcelUtils.getCellData("SE_HBLFreightOtherCharges", GridRow, "SellPrepaidOrCollect", ScenarioDetailsHashMap), "Validating Sell Prepaid/Collect in row : "+GridRow, ScenarioDetailsHashMap);
				}
				else
				{
					//Sell Validation
					String Sell_Quantity = driver.findElement(By.name("sellQuantityStr")).getAttribute("value");
					String SellQuantity;
					if(Sell_Quantity.contains(" "))
					{
						SellQuantity = Sell_Quantity.replace(" ", "");
					}
					else
					{
						SellQuantity = Sell_Quantity;
					}
					String Sell_Rate = driver.findElement(By.name("sellRateStr")).getAttribute("value");
					String SellRate;
					if(Sell_Rate.contains(" "))
					{
						SellRate = Sell_Rate.replace(" ", "");
					}
					else
					{
						SellRate = Sell_Rate;
					}

					//Minimum Sell Amount Calculation
					float Minimum_SellAmount = Float.parseFloat(driver.findElement(By.id("sellMinAmountStr")).getAttribute("value"));
					String MinSell_Amount = String.valueOf(Minimum_SellAmount);
					String MinSellAmount;
					if(MinSell_Amount.contains(" "))
					{
						MinSellAmount = MinSell_Amount.replace(" ", "");
					}
					else
					{
						MinSellAmount = MinSell_Amount;
					}
					float Min_SellAmount = Float.parseFloat(MinSellAmount);
					//System.out.println(" b4 MinBuyAmount"+Min_SellAmount);
					String MinimumSell_Amount = new DecimalFormat("##.00").format(Min_SellAmount);
					//System.out.println(" a4 MinimumSell_Amount"+MinimumSell_Amount);


					//Sell Amount Calculation
					float Expected_SellAmount = (Float.parseFloat(SellQuantity))*(Float.parseFloat(SellRate));
					String Exp_SellAmount = String.valueOf(Expected_SellAmount);
					String ExpSell_Amount;
					if(Exp_SellAmount.contains(" "))
					{
						ExpSell_Amount = Exp_SellAmount.replace(" ", "");
					}
					else
					{
						ExpSell_Amount = Exp_SellAmount;
					}

					float ExpSellAmount = Float.parseFloat(ExpSell_Amount);
					//System.out.println(" b4 ExpBuyAmount"+ExpSellAmount);
					String ExpectedSell_Amount = new DecimalFormat("##.00").format(ExpSellAmount);
					//System.out.println(" a4 ExpectedBuy_Amount"+ExpectedSell_Amount);

					String ActualSellamount = driver.findElement(By.name("sellAmountStr")).getAttribute("value");
					String ActSell_Amount;
					if(ActualSellamount.contains(" "))
					{
						ActSell_Amount = ActualSellamount.replace(" ", "");
					}
					else
					{
						ActSell_Amount = ActualSellamount;
					}
					float ActSellAmount = Float.parseFloat(ActSell_Amount);
					//System.out.println(" b4 ActSellAmount"+ActSellAmount);
					String ActualSell_Amount = new DecimalFormat("##.00").format(ActSellAmount);
					//System.out.println(" a4 ActualSell_Amount"+ActualSell_Amount);

					//Commented for sell Amount calculation part- Sangeeta
					/*if((Float.parseFloat(MinimumSell_Amount))>Float.parseFloat(ExpectedSell_Amount))
					{
						GenericMethods.assertTwoValues(MinimumSell_Amount+"", ExpectedSell_Amount+"", "validating calculation of SellAmount in row : "+GridRow, ScenarioDetailsHashMap);
					}
					else
					{
						GenericMethods.assertTwoValues(ActualSell_Amount+"", ExpectedSell_Amount+"", "validating calculation of SellAmount in row : "+GridRow, ScenarioDetailsHashMap);
					}*/
					GenericMethods.assertTwoValues(driver.findElement(By.id("dtChargeCode"+GridRow)).getText(), ExcelUtils.getCellData("SE_HBLFreightOtherCharges", GridRow, "Charge_Code", ScenarioDetailsHashMap), "Validating Charge code in row : "+GridRow, ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(driver.findElement(By.id("sellChargeBasis")).getAttribute("value"), ExcelUtils.getCellData("SE_HBLFreightOtherCharges", GridRow, "SellCharge_Basis", ScenarioDetailsHashMap), "Validating Sell Charge Basis in row : "+GridRow, ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(driver.findElement(By.id("sellRateStr")).getAttribute("value"), ExcelUtils.getCellData("SE_HBLFreightOtherCharges", GridRow, "DefaultSellRate", ScenarioDetailsHashMap), "Validating Sell Rate in row : "+GridRow, ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(driver.findElement(By.id("sellCurrency")).getAttribute("value"), ExcelUtils.getCellData("SE_HBLFreightOtherCharges", GridRow, "SellCurrency", ScenarioDetailsHashMap), "Validating Sell Currency in row : "+GridRow, ScenarioDetailsHashMap);
					Select SellPC = new Select(driver.findElement(By.id("sellPcIndicator")));
					String actualSellselectedValue = SellPC.getFirstSelectedOption().getText();
					GenericMethods.assertTwoValues(actualSellselectedValue, ExcelUtils.getCellData("SE_HBLFreightOtherCharges", GridRow, "SellPrepaidOrCollect", ScenarioDetailsHashMap), "Validating Sell Prepaid/Collect in row : "+GridRow, ScenarioDetailsHashMap);
				}
			}
		}
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
	}

	//Masthan RAT23 Start(Reading erating mail popup)
	public static void eRatingMailCheck(WebDriver driver,		
			HashMap<String, String> ScenarioDetailsHashMap,int RowNo) {
		GenericMethods.pauseExecution(2000);
		//String message=ReadingMail.check("outlook.office365.com", "imaps", "", "");
		String configurationStructurePath = System.getProperty("user.dir")+"/Configurations/base.properties";
		String MailId=GenericMethods.getPropertyValue("PopUpCheckMailId", configurationStructurePath);
		String Password=GenericMethods.getPropertyValue("PopUpCheckMailPassword", configurationStructurePath);
		String message=ReadingMail.check("outlook.office365.com", "imaps",MailId,Password);
		String HBLId=ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap);
		if(message.contains(HBLId))
			GenericMethods.assertTwoValues("HBL ID found In Mail PopUp", "HBL ID found In Mail PopUp", "Verifying PopUp for AutoRate with HBL-->"+HBLId, ScenarioDetailsHashMap);
		else
			GenericMethods.assertTwoValues("PopUp found without HBL ID", "PopUp found", "Verifying PopUp for AutoRate with HBL-->"+HBLId, ScenarioDetailsHashMap);
	}

	//RAT17- By Prasanna Modugu
	public static void chargesAndCosts(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		try{	
			seaHBL_SearchList(driver, ScenarioDetailsHashMap, rowNo);
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "ChargesOrCost", ScenarioDetailsHashMap, 5), 5);
			GenericMethods.pauseExecution(5000);
			GenericMethods.handleAlert(driver, "accept");
			List <WebElement> list=driver.findElements(By.xpath("//input[@value='READY' and @name='chargesVOList.salesStatus']"));
			int noOfRecords=list.size();

			//Sangeeta added:- This piece of code will work when Buy Rate check box is not checked in HBLChargecost Tab
			//************
			int RowCount = ExcelUtils.getCellDataRowCount("CostAndCharges", ScenarioDetailsHashMap);
			System.out.println("RowCount :"+RowCount);;
			for (int i = 1; i <= RowCount; i++) {
				//System.out.println("Row isSelected :"+i+ " = "+ driver.findElement(By.id("multiSelectCheckboxchargeGrid"+i)).isSelected());
				if(!driver.findElement(By.id("multiSelectCheckboxchargeGrid"+i)).isSelected())
				{
					GenericMethods.pauseExecution(3000);
					//System.out.println("i ="+i);
					GenericMethods.clickElement(driver, By.id("multiSelectCheckboxchargeGrid"+i), null, 10);
					GenericMethods.doubleClickOnElement(driver, By.id("salesPriceAmt"+i), null, 10);
					GenericMethods.pauseExecution(4000);
					driver.findElement(By.id("chargesVOList.salesPriceAmt"+i)).clear();
					try{
						GenericMethods.handleAlert(driver, "accept");
					}catch (Exception e) {

					}
					driver.findElement(By.id("chargesVOList.salesPriceAmt"+i)).sendKeys(ExcelUtils.getCellData("CostAndCharges", i, "SalesPrice_Amount", ScenarioDetailsHashMap));
					try{
						GenericMethods.handleAlert(driver, "accept");
					}catch (Exception e) {

					}
					GenericMethods.pauseExecution(4000);
					GenericMethods.action_Key(driver, Keys.TAB);
					GenericMethods.pauseExecution(7000);
					GenericMethods.action_Key(driver, Keys.ESCAPE);
					GenericMethods.pauseExecution(2000);
				}
			}
			//***************

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
			invoiceId=invoiceId.split("\n")[0].trim()+" ";
			ExcelUtils.setCellData("CostAndCharges", rowNo, "SalesInvoiceNo", invoiceId, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("Ocean_CreaditNote", rowNo, "SalesInvoiceNo", invoiceId, ScenarioDetailsHashMap);
			String InvoiceSmryMSG = invoiceIdSummary.split(":")[0];
			String[] arrMSG=InvoiceSmryMSG.split(" ");
			String InvoiceMessge = InvoiceSmryMSG.replace(arrMSG[0], "").trim();
			System.out.print("InvoiceMessge ="+InvoiceMessge);
			GenericMethods.assertTwoValues(InvoiceMessge, "Invoice has been posted to Accounts with Transaction Id", "Validating Post Sales success message", ScenarioDetailsHashMap);
			System.out.println(invoiceIdSummary.split(":")[0]+"succ msg***");
			System.out.println("invoiceId::"+invoiceId);	
		}catch(Exception e){
			e.printStackTrace();
		}

		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
	}
	//RAT18
	public static void checkChargeDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ){
		try{
			seaHBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);
			GenericMethods.pauseExecution(2000);
			int rowCount=ExcelUtils.getCellDataRowCount("CostAndCharges", ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "FreightAndOtherCharges", ScenarioDetailsHashMap, 5), 5);
			for (int i = 1; i <= rowCount; i++) {		
				GenericMethods.assertValue(driver, By.xpath("//*[@id='dtChargeCode"+i+"']"), null, ExcelUtils.getCellData("CostAndCharges", i, "ChargeCode", ScenarioDetailsHashMap), "ChargeCode", "ChargeCode", ScenarioDetailsHashMap);
			}
			GenericMethods.pauseExecution(5000);
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "ChargesOrCost", ScenarioDetailsHashMap, 5), 5);
			for (int i = 1; i <= rowCount; i++) {		
				GenericMethods.assertValue(driver, By.xpath("//*[@id='spanchargesVOList.chargeCode"+i+"']"), null, ExcelUtils.getCellData("CostAndCharges", i, "ChargeCode", ScenarioDetailsHashMap), "ChargeCode", "ChargeCode", ScenarioDetailsHashMap);
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
	}


	//RAT24-Prasanna Modugu

	public static void hblChargesAndCost(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		try{
			if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "FreightChargesValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{

			}
			seaHBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);
			GenericMethods.pauseExecution(2000);
			//Code added to check Quote / Contract Status(RAT24)- By Prasanna Modugu
			String profitCenter=ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "InvoiceBranch",ScenarioDetailsHashMap)+"/"+ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "InvoiceDept",ScenarioDetailsHashMap);
			System.out.println("profitCenter****"+profitCenter);
			AppReusableMethods.chargesAndRates(driver,  orHBL.getElement(driver, "ChargesOrCost", ScenarioDetailsHashMap, 10), profitCenter, ScenarioDetailsHashMap, RowNo);
			//Code added to check Quote / Contract Status(RAT19)- By Prasanna Modugu
			/*GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "HBL_MAIN_DETAILS_Tab", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(3000);
			AppReusableMethods.quoteOrContractStatus(driver, ScenarioDetailsHashMap, RowNo);*/	
			GenericMethods.pauseExecution(3000);
		}catch(Exception e){
			e.printStackTrace();
		}	
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
	}

	//	RAT26 
	/**
	 * This Method is used to generate sales invoice by changing invoice currency with local currency.
	 * @param driver Instance of WebDriver
	 * @param searchValue
	 * @param ScenarioDetailsHashMap
	 * @author Prasanna Modugu
	 */
	public static void invoiceCurrencyAsLocalCurrency(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		try{		
			seaHBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "ChargesOrCost", ScenarioDetailsHashMap, 10), 5);
			GenericMethods.pauseExecution(5000);
			GenericMethods.handleAlert(driver, "accept");
			if (driver.findElement(By.id("multiSelectCheckBoxchargeGrid")).isSelected()) {
				driver.findElement(By.id("multiSelectCheckBoxchargeGrid")).click();
			}
			GenericMethods.pauseExecution(1000);
			if (!driver.findElement(By.id("multiSelectCheckboxchargeGrid1")).isSelected()) {
				driver.findElement(By.id("multiSelectCheckboxchargeGrid1")).click();
			}
			String salesAmtCurr=driver.findElement(By.id("salesPriceCurrency1")).getText();
			GenericMethods.doubleClickOnElement(driver, By.id("salesInvCurrency1"), null, 5);
			driver.switchTo().activeElement().clear();
			driver.switchTo().activeElement().sendKeys(salesAmtCurr);
			GenericMethods.action_Key(driver, Keys.TAB);
			GenericMethods.pauseExecution(3000);
			GenericMethods.clickElement(driver, By.name("postSales"), null, 5);
			GenericMethods.pauseExecution(3000);
			GenericMethods.selectWindow(driver);
			driver.close();
			GenericMethods.pauseExecution(1000);
			GenericMethods.switchToParent(driver);
			AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(2000);
			String invoiceIdSummary=GenericMethods.getInnerText(driver, By.xpath("//font[@color='green']/b"), null, 2);
			String invoiceId=invoiceIdSummary.split(":")[1].trim()+" ";
			invoiceId=invoiceId.substring(0,invoiceId.indexOf(" "));
			invoiceId=invoiceIdSummary.split("\n")[0].trim()+" ";
			invoiceId=invoiceId.split("\n")[0].trim()+" ";
			System.out.println(invoiceIdSummary.split(":")[0]+"succ msg***");
			System.out.println("invoiceId::"+invoiceId);
			ExcelUtils.setCellData("CostAndCharges", RowNo, "SalesInvoiceNo", invoiceId, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("Ocean_CreaditNote", RowNo, "SalesInvoiceNo", invoiceId, ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, By.id("salesInvCurrency1"), null, salesAmtCurr, "Invoice Currency", "Invoice Currency as foreign currency.", ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(3000);
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	//	RAT27
	/**
	 * This Method is used to generate sales invoice by changing invoice currency with foreign currency.
	 * @param driver Instance of WebDriver
	 * @param searchValue
	 * @param ScenarioDetailsHashMap
	 * @author Prasanna Modugu
	 */
	public static void invoiceCurrencyAsForeignCurrency(WebDriver driver, HashMap<String, String> ScenarioDetailsHashMap,int RowNo)	{
		try{
			String foreignCurrency="USD";	
			seaHBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "ChargesOrCost", ScenarioDetailsHashMap, 10), 5);
			GenericMethods.pauseExecution(5000);
			GenericMethods.handleAlert(driver, "accept");
			if (driver.findElement(By.id("multiSelectCheckBoxchargeGrid")).isSelected()) {
				driver.findElement(By.id("multiSelectCheckBoxchargeGrid")).click();
			}
			GenericMethods.pauseExecution(1000);
			if (!driver.findElement(By.id("multiSelectCheckboxchargeGrid1")).isSelected()) {
				driver.findElement(By.id("multiSelectCheckboxchargeGrid1")).click();
				driver.findElement(By.id("multiSelectCheckboxchargeGrid2")).click();
			}
			GenericMethods.doubleClickOnElement(driver, By.id("salesInvCurrency1"), null, 5);
			driver.switchTo().activeElement().clear();
			driver.switchTo().activeElement().sendKeys(foreignCurrency);
			GenericMethods.action_Key(driver, Keys.TAB);
			GenericMethods.doubleClickOnElement(driver, By.id("salesInvCurrency2"), null, 5);
			driver.switchTo().activeElement().clear();
			driver.switchTo().activeElement().sendKeys(foreignCurrency);
			GenericMethods.action_Key(driver, Keys.TAB);
			GenericMethods.pauseExecution(3000);
			GenericMethods.clickElement(driver, By.name("postSales"), null, 5);
			GenericMethods.pauseExecution(3000);
			GenericMethods.selectWindow(driver);
			driver.close();
			GenericMethods.pauseExecution(1000);
			GenericMethods.switchToParent(driver);
			AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(2000);
			String invoiceIdSummary=GenericMethods.getInnerText(driver, By.xpath("//font[@color='green']/b"), null, 2);
			String invoiceId=invoiceIdSummary.split(":")[1].trim()+" ";
			invoiceId=invoiceId.substring(0,invoiceId.indexOf(" "));
			invoiceId=invoiceId.split("\n")[0].trim()+" ";
			System.out.println(invoiceIdSummary.split(":")[0]+"succ msg***");
			System.out.println("invoiceId::"+invoiceId);
			ExcelUtils.setCellData("CostAndCharges", RowNo, "SalesInvoiceNo", invoiceId, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("Ocean_CreaditNote", RowNo, "SalesInvoiceNo", invoiceId, ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, By.id("salesInvCurrency1"), null, foreignCurrency, "Invoice Currency", "Invoice Currency as foreign currency.", ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(3000);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void seaHBL_SignificantDateValidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "SignificantDateValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			seaHBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "HBL_MAIN_DETAILS_Tab", ScenarioDetailsHashMap, 10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				e.printStackTrace();
			}
			GenericMethods.pauseExecution(3000);
			String HBLDate = driver.findElement(By.id("hblDate")).getAttribute("value");
			GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "ChargesOrCost", ScenarioDetailsHashMap, 10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				e.printStackTrace();
			}
			GenericMethods.pauseExecution(3000);
			String SignificantDate = driver.findElement(By.name("execDate")).getAttribute("value");
			GenericMethods.assertTwoValues(SignificantDate, HBLDate, "Validating Significant date in HBL Charges/Costs page.", ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {

			}
		}
	}

	public static void hblChargesAndCostsValidations(WebDriver driver, HashMap<String, String> ScenarioDetailsHashMap,int RowNo)	{
		//		Added code to CheckChargeDetails and ChargesAndCosts
		try{
			hblChargesAndCost(driver, ScenarioDetailsHashMap, RowNo);
			checkChargeDetails(driver, ScenarioDetailsHashMap, RowNo);
			chargesAndCosts(driver, ScenarioDetailsHashMap, RowNo);
			//invoiceCurrencyAsLocalCurrency(driver, ScenarioDetailsHashMap, RowNo);
			//invoiceCurrencyAsForeignCurrency(driver, ScenarioDetailsHashMap, RowNo);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//Sandeep- Below method is to perform INTTRA functionality.
	public static void INTTRA_Tab(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo)
	{
		GenericMethods.pauseExecution(6000);
		GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "Tab_INTTRA", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(6000);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e){}

		//added by sangeeta
		GenericMethods.pauseExecution(5000);
		INTTRA_Tab_DefaultvaluesValidation(driver, ScenarioDetailsHashMap, RowNo);

		GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_INTTRA_Currency", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "Currency",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_INTTRA_ShipperDeclaredValue", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "ShipperDeclaredValue",ScenarioDetailsHashMap), 2);
		GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "DDL_INTTRA_AllCharges", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "AllCharges",ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_INTTRA_BL_Comments", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "B_L_Comments",ScenarioDetailsHashMap), 2);
		AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "LOV_INTTRA_BL_Location", ScenarioDetailsHashMap,10), orHBL,"Textbox_LOV_BL_LocationField", ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "B_L_Location",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_INTTRA_BL_ReleaseDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "B_L_ReleaseDate",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_INTTRA_OriginalBLFrt", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "OriginalBLsFRTNonFRT",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_INTTRA_CopyBLFrt", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "B_L_Copy",ScenarioDetailsHashMap), 2);
		Select DDL_ManifestFilerStatus = new Select(driver.findElement(By.id("manifestFilerstatus"))); 
		GenericMethods.assertTwoValues(DDL_ManifestFilerStatus.getFirstSelectedOption().getText(),  ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "ManifestFilerStatus",ScenarioDetailsHashMap), "Verifying Manifest Filer Status dropdown defaulted value", ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "BTN_INTTRA_Generate", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e){}

		String INTTRASuccessSummary=GenericMethods.getInnerText(driver, null, orHBL.getElement(driver, "Message_INTTRA_Message_SuccessfulSave", ScenarioDetailsHashMap, 20), 2);
		System.out.println("11111---"+INTTRASuccessSummary);
		System.out.println("222222"+INTTRASuccessSummary.split(" : ")[1]+" : "+(INTTRASuccessSummary.split(" : ")[2].replace("\n", " ")).split(" ")[0]);
		GenericMethods.assertTwoValues(INTTRASuccessSummary.split(" : ")[1]+" : "+(INTTRASuccessSummary.split(" : ")[2].replace("\n", " ")).split(" ")[0], ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "Message_SuccessfulSave",ScenarioDetailsHashMap)+ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId",ScenarioDetailsHashMap), "Verifying Success Message in INTTRA Tab", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(INTTRASuccessSummary.split(" : ")[3], ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "Message_RecordSubmitted",ScenarioDetailsHashMap), "Verifying Record Submitted Message in INTTRA Tab", ScenarioDetailsHashMap);

		GenericMethods.assertInnerText(driver, By.id("dtStatus1"), null,  ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "Status",ScenarioDetailsHashMap), "Grid_INTTRA Status", "Verifying INTTRA Status in the grid", ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			System.out.println("no Alerts present");
		}

		GenericMethods.pauseExecution(3000);
	}

	//Sandeep-  INTTRA- Below method is to get xml file for performing validations
	public static void HBL_AuditTrail_InterfaceAudit_Tab(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "AuditButton",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);

		for (int Actions_GridRowID = 1; Actions_GridRowID <= driver.findElements(By.xpath("//table[@id='auditTrailScrhGrid']/tbody/tr")).size(); Actions_GridRowID++) 
		{
			String Actions_gridPrefix = "//table[@id='auditTrailScrhGrid']/tbody/tr["+Actions_GridRowID+"]/";

			//Below if condition is to click on record in the grid for which From Screen = INTTRA_SI_REQUEST
			if(GenericMethods.getInnerText(driver, By.xpath(Actions_gridPrefix+"td[13]"), null, 10).equalsIgnoreCase("INTTRA_SI_REQUEST"))
			{
				GenericMethods.clickElement(driver, By.xpath(Actions_gridPrefix+"td[13]"),null, 2);
				GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "Tab_AuditTrail_InterfaceAudit",ScenarioDetailsHashMap, 10), 2);

				for (int Interface_GridRowID = 1; Interface_GridRowID <= driver.findElements(By.xpath("//table[@id='dataTable']/tbody/tr")).size(); Interface_GridRowID++) 
				{
					String Interface_gridPrefix = "//table[@id='dataTable']/tbody/tr["+Interface_GridRowID+"]/";

					//Below if condition is to generate xml from the record in the grid for which Event Type = STANDARD_EDI_INTTRA and save it in workspace(\\INTRA\ScenarioFiles) folder.
					if(GenericMethods.getInnerText(driver, By.xpath(Interface_gridPrefix+"td[7]"), null, 10).equals( ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "EventType",ScenarioDetailsHashMap)))
					{
						String Workspace_INTTRAPath = System.getProperty("user.dir")+ "\\INTTRA\\ScenarioFiles\\" + res.getExectionDateTime()+"_";
						String JournalID = GenericMethods.getInnerText(driver, By.xpath(Interface_gridPrefix+"td[20]"), null, 10);
						System.out.println("journal id::--"+JournalID);
						ExcelUtils.setCellData("SE_HBLMainDetails", RowNo, "JournalID", JournalID, ScenarioDetailsHashMap);

						try {


							GenericMethods.clickElement(driver, By.xpath(Interface_gridPrefix+"td[10]/a/img"), null, 10);
							GenericMethods.pauseExecution(3000);

							Robot rb=new Robot();
							rb.keyPress(KeyEvent.VK_CONTROL);
							rb.keyPress(KeyEvent.VK_S);
							rb.keyRelease(KeyEvent.VK_S);
							rb.keyRelease(KeyEvent.VK_CONTROL);
							GenericMethods.pauseExecution(2000);
							rb.keyPress(KeyEvent.VK_HOME);
							rb.keyRelease(KeyEvent.VK_HOME);
							GenericMethods.pauseExecution(2000);
							GenericMethods.parseChars(Workspace_INTTRAPath, rb);
							rb.keyPress(KeyEvent.VK_ENTER);
							rb.keyRelease(KeyEvent.VK_ENTER);
							GenericMethods.pauseExecution(4000);
							rb.keyPress(KeyEvent.VK_ALT);
							rb.keyPress(KeyEvent.VK_F4);
							rb.keyRelease(KeyEvent.VK_F4);
							rb.keyRelease(KeyEvent.VK_ALT);




						} catch (AWTException e) {e.printStackTrace();
						} catch (Exception e){e.printStackTrace();
						}
						break;// Terminating the execution in Interface Audit screen
					}	
				}
				break;// Terminating the execution in Audit Actions screen
			}
		}

		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "CloseButton",ScenarioDetailsHashMap, 10), 2);

		try {
			XMLVAlidation(driver, ScenarioDetailsHashMap, RowNo);
			INTTRA_XMLFiles(driver, ScenarioDetailsHashMap, RowNo);
		} catch (ParserConfigurationException e) {e.printStackTrace();
		} catch (SAXException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();
		} catch (TransformerException e) {e.printStackTrace();
		}
	}



	//Sandeep- Below method is perform comparision of XML files ( ETJournalController.fsc.xml and Journal.xml(FTP Location)) 
	public static void XMLVAlidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo)
	{
		String Workspace_INTTRAPath = System.getProperty("user.dir")+ "\\INTTRA\\ScenarioFiles\\" + res.getExectionDateTime()+"_";
		try {
			//Below script is to copy JournalID xml file from FTP location and copy it in "\\INTTRA\\ScenarioFiles" folder path in workspace.
			GenericMethods.CopyFileFromFtp("10.200.35.11", "kf/outbound/inttra/si/in/", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "JournalID", ScenarioDetailsHashMap)+"_", Workspace_INTTRAPath, "kfftp", "aCmgRZ1%2Ycf");
			GenericMethods.pauseExecution(5000);
			System.out.println("************************1111111111111111************************");
			GenericMethods.CompareXMLFiles(GenericMethods.getFilePath(System.getProperty("user.dir")+"\\INTTRA\\ScenarioFiles\\",res.getExectionDateTime()+"_"+ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "JournalID", ScenarioDetailsHashMap)+"_"), System.getProperty("user.dir")+"\\INTTRA\\Processed.xml",ScenarioDetailsHashMap);
			System.out.println("************************2222222222222222************************");
			GenericMethods.CompareXMLFiles(GenericMethods.getFilePath(System.getProperty("user.dir")+"\\INTTRA\\ScenarioFiles\\",res.getExectionDateTime()+"_ETJournalController"), System.getProperty("user.dir")+"\\INTTRA\\ETJournalController.fsc.xml",ScenarioDetailsHashMap);
			System.out.println("************************2222endddddddddddddd222************************");
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//Sandeep- Below method is to perform Validation in INTTRA Tab (SI Submission and SI Response tab) of HBL module.
	public static void INTTRA_Validations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo)
	{
		GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "Tab_INTTRA", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(6000);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e){}
		GenericMethods.assertInnerText(driver, By.id("dtStatus1"), null,  ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "Status",ScenarioDetailsHashMap), "Grid_INTTRA Status", "Verifying INTTRA Status in the grid", ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "Tab_INTTRA_SIResponse", ScenarioDetailsHashMap, 10), 2);

		for (int StatusNo = 1; StatusNo <= ExcelUtils.getCellDataRowCount("SE_HBLINTTRA_SIResponse", ScenarioDetailsHashMap); StatusNo++) 
		{
			boolean StatusAvailability =false;
			String ExcelTestdata_Status = ExcelUtils.getCellData("SE_HBLINTTRA_SIResponse", StatusNo, "Status", ScenarioDetailsHashMap);
			for (int Response_GridNo = 1; Response_GridNo <= driver.findElements(By.xpath("//table[@id='inttraResponsetDtlsGrid']/tbody/tr")).size(); Response_GridNo++) 
			{
				String gridXpathPrefix = "//table[@id='inttraResponsetDtlsGrid']/tbody/tr["+Response_GridNo+"]/";
				String GridStatus= GenericMethods.getInnerText(driver, By.xpath(gridXpathPrefix+"td[7]"), null, 10);
				if(ExcelTestdata_Status.equals(GridStatus))
				{
					GenericMethods.assertInnerText(driver, By.xpath(gridXpathPrefix+"td[7]"), null, ExcelTestdata_Status, "Status", "Verifying INTTRA Status in the grid", ScenarioDetailsHashMap);
					GenericMethods.assertInnerText(driver, By.xpath(gridXpathPrefix+"td[9]"), null, ExcelUtils.getCellData("SE_HBLINTTRA_SIResponse", StatusNo, "VersionNo", ScenarioDetailsHashMap), "Version No", "Verifying INTTRA Version No in the grid", ScenarioDetailsHashMap);
					StatusAvailability = true;
				}
			}
			if(!StatusAvailability)
			{
				GenericMethods.assertTwoValues(ExcelTestdata_Status, " ", "Verifying INTTRA status is available in the grid", ScenarioDetailsHashMap);
			}
		}
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "CloseButton", ScenarioDetailsHashMap, 10), 2);
	}
	//Sandeep- Below method is to enter Contact Name details for Party Type= Carrier  
	public static void PartiesTab_PartyTypeDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.pauseExecution(3000);
		orHBL.getElement(driver, "PartyDetails", ScenarioDetailsHashMap,10).click();
		try{
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		GenericMethods.pauseExecution(3000);
		//logic to traverse table
		int partyTable= orHBL.getElements(driver, "table_addressDtlsGrid", ScenarioDetailsHashMap, 10).size();
		if(partyTable>0){
			for (int Grid_RowNo = 1; Grid_RowNo < partyTable; Grid_RowNo++) {
				String partyXpath="//div[@id='divaddressDtlsGrid']/table/tbody/tr["+Grid_RowNo+"]/td[1]";
				if(GenericMethods.getInnerText(driver, By.xpath(partyXpath), null, 10).equalsIgnoreCase("CARRIER"))
				{
					GenericMethods.clickElement(driver, By.xpath(partyXpath), null, 10);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Textbox_PartyTab_ContactName", ScenarioDetailsHashMap,10),  ExcelUtils.getCellData("SE_BookingPartyDetails", RowNo, "Carrier_ContactName", ScenarioDetailsHashMap), 2);
					GenericMethods.pauseExecution(3000);
					WebElement myDynamicElement1 = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.id("button.gridEditBtn")));
					GenericMethods.clickElement(driver, null, myDynamicElement1, 2);	
					GenericMethods.pauseExecution(3000);
					GenericMethods.clickElement(driver, By.xpath(partyXpath), null, 10);
				}
			}
		}
	}

	//Sandeep- Below method is to generate Aperak_Jet.xml and carrierstatus_Jet(for all 9 status)
	public static void INTTRA_XMLFiles(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{

		//Acknowledgement
		DumpExcelFiles.copyExcel(System.getProperty("user.dir") + "/INTTRA/Aperak_Jet.xml", System.getProperty("user.dir") + "/INTTRA/ScenarioFiles/"+ res.getExectionDateTime()+"_Aperak_Jet.xml");
		String ACK_Filename= System.getProperty("user.dir") + "/INTTRA/ScenarioFiles/"+ res.getExectionDateTime()+"_Aperak_Jet.xml";

		GenericMethods.UpdateXMLValues(ACK_Filename, "DateTime",  GenericMethods.GetTestdataDate(ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "MBL_Date", ScenarioDetailsHashMap))+"0000");
		GenericMethods.UpdateXMLValues(ACK_Filename, "DocumentIdentifier",  ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Console_ID", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLValues(ACK_Filename, "ShipmentIdentifier",  ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Console_ID", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLValues(ACK_Filename, "ReferenceInformation",  ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Console_ID", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLValues_ParentNodeAttributeValue(ACK_Filename, "PartnerRole","Carrier","PartnerIdentifier",  ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierId", ScenarioDetailsHashMap));
		GenericMethods.UploadFileToFTP("10.200.35.11", "kfftp", "aCmgRZ1%2Ycf", ACK_Filename, "kf/inbound/inttra/import/acknowledgment/"+res.getExectionDateTime()+"_Aperak_Jet.xml");
		System.out.println("acknowledgement sumitter");
		//Status
		for (int FileNo = 1; FileNo <= ExcelUtils.getCellDataRowCount("SE_HBLINTTRA_SIResponse", ScenarioDetailsHashMap); FileNo++) 
		{
			DumpExcelFiles.copyExcel(System.getProperty("user.dir") + "/INTTRA/carrierstatus_Jet.xml", System.getProperty("user.dir") + "/INTTRA/ScenarioFiles/"+ res.getExectionDateTime()+"_carrierstatus_Jet_"+ExcelUtils.getCellData("SE_HBLINTTRA_SIResponse", FileNo, "Status", ScenarioDetailsHashMap)+".xml");
			String Status_Filename= System.getProperty("user.dir") + "/INTTRA/ScenarioFiles/"+ res.getExectionDateTime()+"_carrierstatus_Jet_"+ExcelUtils.getCellData("SE_HBLINTTRA_SIResponse", FileNo, "Status", ScenarioDetailsHashMap)+".xml";
			GenericMethods.UpdateXMLValues(Status_Filename, "DocumentIdentifier",  ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "Console_ID", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues_CurrentNodeAttributeValue(Status_Filename, "DateType","Document","DateTime", GenericMethods.GetTestdataDate(ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "MBL_Date", ScenarioDetailsHashMap))+"0000" );
			GenericMethods.UpdateXMLValues(Status_Filename, "EventCode",  ExcelUtils.getCellData("SE_HBLINTTRA_SIResponse", FileNo, "Status", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues_CurrentNodeAttributeValue(Status_Filename, "DateType","StatusChange","DateTime", GenericMethods.GetTestdataDate(ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "MBL_Date", ScenarioDetailsHashMap))+"0000" );
			GenericMethods.UpdateXMLValues_CurrentNodeAttributeValue(Status_Filename, "ReferenceType","BookingNumber","ReferenceInformation",  ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierBookingRefNo", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues(Status_Filename, "CarrierSCAC",  ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierId", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues(Status_Filename, "ConveyanceName",  ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "Container_Vessel", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues(Status_Filename, "VoyageTripNumber",  ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "Container_Voyage", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues_ParentNodeAttributeValue(Status_Filename, "PartnerRole","Carrier","PartnerIdentifier",  ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierId", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues_CurrentNodeAttributeValue(Status_Filename, "DateType","DepartureEstimated","DateTime",  GenericMethods.GetTestdataDate(ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ETD", ScenarioDetailsHashMap))+ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ETDTime", ScenarioDetailsHashMap).replace(":", ""));
			GenericMethods.UpdateXMLValues_CurrentNodeAttributeValue(Status_Filename, "DateType","ArrivalEstimated","DateTime",  GenericMethods.GetTestdataDate( ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ETA", ScenarioDetailsHashMap))+ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ETATime", ScenarioDetailsHashMap).replace(":", ""));
			GenericMethods.UpdateXMLValues(Status_Filename, "EquipmentIdentifier",  ExcelUtils.getCellData("SE_BookingContainerDetails", RowNo, "ContainerNumber", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues(Status_Filename, "EquipmentTypeCode",  ExcelUtils.getCellData("SE_BookingContainerDetails", RowNo, "ContainerType", ScenarioDetailsHashMap));
			GenericMethods.UploadFileToFTP("10.200.35.11", "kfftp", "aCmgRZ1%2Ycf", Status_Filename, "kf/inbound/inttra/import/status/"+res.getExectionDateTime()+"_carrierstatus_Jet_"+ExcelUtils.getCellData("SE_HBLINTTRA_SIResponse", FileNo, "Status", ScenarioDetailsHashMap)+".xml");

			try {
				GenericMethods.FTP_RecordAvailability("10.200.35.11","kf/inbound/inttra/import/status/", res.getExectionDateTime()+"_carrierstatus_Jet_"+ExcelUtils.getCellData("SE_HBLINTTRA_SIResponse", FileNo, "Status", ScenarioDetailsHashMap),"kfftp", "aCmgRZ1%2Ycf",5);
			} catch (InterruptedException e) {e.printStackTrace();}
			//Wait time for file to upload in FTP
			//GenericMethods.pauseExecution(180000);
			System.out.println("status sumitted----"+ExcelUtils.getCellData("SE_HBLINTTRA_SIResponse", FileNo, "Status", ScenarioDetailsHashMap));
		}

		//Below script is to update value to update INTTRA Status
		ExcelUtils.setCellData("SE_HBLINTTRA", RowNo, "Status","ACCEPTED",ScenarioDetailsHashMap);
	}
	public static void INTTRA_Tab_DefaultvaluesValidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo)
	{

		GenericMethods.assertTwoValues(GenericMethods.getSelectedOptionOfDropDown(driver, null, orHBL.getElement(driver, "Inttra_CommentsType", ScenarioDetailsHashMap, 10)), ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "Inttra_CommentsType", ScenarioDetailsHashMap), "Validating Comments Type field value defaultation in Inttra tab of HBL module", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.getSelectedOptionOfDropDown(driver, null, orHBL.getElement(driver, "Inttra_OceanFreightPaybleAt", ScenarioDetailsHashMap, 10)), ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "Inttra_OceanFreightPaybleAt", ScenarioDetailsHashMap), "Validating Ocean Freight Payable at field value defaultation in Inttra tab of HBL module", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.getSelectedOptionOfDropDown(driver, null, orHBL.getElement(driver, "Inttra_BLDocType", ScenarioDetailsHashMap, 10)), ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "Inttra_BLDocType", ScenarioDetailsHashMap), "Validating B/L Doc Type field value defaultation in Inttra tab of HBL module", ScenarioDetailsHashMap);
		//GenericMethods.assertTwoValues(GenericMethods.getSelectedOptionOfDropDown(driver, null, orHBL.getElement(driver, "Inttra_AllCharges", ScenarioDetailsHashMap, 10)), ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "Inttra_AllCharges", ScenarioDetailsHashMap), "Validating All Charges field value defaultation in Inttra tab of HBL module", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.getSelectedOptionOfDropDown(driver, null, orHBL.getElement(driver, "Inttra_OriginalBlFreightType", ScenarioDetailsHashMap, 10)), ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "Inttra_OriginalBlFreightType", ScenarioDetailsHashMap), "Validating Original B/Ls FRT / Non FRT field value defaultation in Inttra tab of HBL module", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.getSelectedOptionOfDropDown(driver, null, orHBL.getElement(driver, "Inttra_NonNegotiableFreightType", ScenarioDetailsHashMap, 10)), ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "Inttra_NonNegotiableFreightType", ScenarioDetailsHashMap), "Validating Non-Negotiable FRT / Non FRT field value defaultation in Inttra tab of HBL module", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.getSelectedOptionOfDropDown(driver, null, orHBL.getElement(driver, "Inttra_SeaWayType", ScenarioDetailsHashMap, 10)), ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "Inttra_SeaWayType", ScenarioDetailsHashMap), "Validating Sea Way / Express field value defaultation in Inttra tab of HBL module", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.getSelectedOptionOfDropDown(driver, null, orHBL.getElement(driver, "Inttra_CopyBlFreightType", ScenarioDetailsHashMap, 10)), ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "Inttra_CopyBlFreightType", ScenarioDetailsHashMap), "Validating CB/L Copy field value defaultation in Inttra tab of HBL module", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.getSelectedOptionOfDropDown(driver, null, orHBL.getElement(driver, "Inttra_ManifestFilerstatus", ScenarioDetailsHashMap, 10)), ExcelUtils.getCellData("SE_HBLINTTRA", RowNo, "Inttra_ManifestFilerstatus", ScenarioDetailsHashMap), "Validating Manifest Filer Status field value defaultation in Inttra tab of HBL module", ScenarioDetailsHashMap);

	}
	public static void HBL_SaveWithoutValidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo) throws InterruptedException
	{
		GenericMethods.pauseExecution(5000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
		GenericMethods.pauseExecution(5000);
	}

	public static void SeaHBLINTTRA_Checkboxvalidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		GenericMethods.pauseExecution(8000);
		AppReusableMethods.selectMenu(driver,ETransMenu.Hbl,"HBL", ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "CopyButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(8000);
		boolean InttraCheckBox_Status = false;
		GenericMethods.pauseExecution(2000);
		Select ShipmentTypeDropDown = new Select(driver.findElement(By.id("shipmentType")));
		System.out.println(ShipmentTypeDropDown.getOptions().size());//will get all options as List<WebElement>
		int TotalSTdropdowncount =ShipmentTypeDropDown.getOptions().size();
		for (int DDSTCount = 0; DDSTCount < TotalSTdropdowncount; DDSTCount++)
		{
			String STdropdownvalue = ShipmentTypeDropDown.getOptions().get(DDSTCount).getText();
			ShipmentTypeDropDown.selectByVisibleText(STdropdownvalue);
			GenericMethods.pauseExecution(2000);
			System.out.println(ShipmentTypeDropDown.getOptions().get(DDSTCount).getText());
			System.out.println("STdropdownvaluehouse="+STdropdownvalue);
			if(STdropdownvalue.equalsIgnoreCase("House"))
			{
				GenericMethods.pauseExecution(2000);
				Select HouseTypeDropDown = new Select(driver.findElement(By.id("houseType")));
				System.out.println(HouseTypeDropDown.getOptions().size());//will get all options as List<WebElement>
				int TotalHTdropdowncount =HouseTypeDropDown.getOptions().size();
				for (int DDHTCount = 0; DDHTCount < TotalHTdropdowncount; DDHTCount++)
				{
					String HTdropdownvalue = HouseTypeDropDown.getOptions().get(DDHTCount).getText();
					HouseTypeDropDown.selectByVisibleText(HTdropdownvalue);
					GenericMethods.pauseExecution(2000);
					System.out.println(HouseTypeDropDown.getOptions().get(DDHTCount).getText());
					System.out.println("HTdropdownvalue Consolidation="+HTdropdownvalue);
					if(HTdropdownvalue.equalsIgnoreCase("Consolidation"))
					{
						GenericMethods.pauseExecution(2000);
						Select ConsoleTypeDropDown = new Select(driver.findElement(By.name("consoleType")));
						System.out.println(ConsoleTypeDropDown.getOptions().size());//will get all options as List<WebElement>
						int TotalCTdropdowncount =ConsoleTypeDropDown.getOptions().size();
						for (int DDCTCount = 0; DDCTCount < TotalCTdropdowncount; DDCTCount++)
						{
							GenericMethods.pauseExecution(6000);
							String CTdropdownvalue = ConsoleTypeDropDown.getOptions().get(DDCTCount).getText();
							ConsoleTypeDropDown.selectByVisibleText(CTdropdownvalue);
							GenericMethods.pauseExecution(2000);
							System.out.println(ConsoleTypeDropDown.getOptions().get(DDCTCount).getText());
							GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "MBLMainDetails", ScenarioDetailsHashMap, 10), 2);
							GenericMethods.pauseExecution(3000);
							try{
								GenericMethods.handleAlert(driver, "accept");
							}catch (Exception e) {

							}
							InttraCheckBox_Status = GenericMethods.isFieldEnabled(driver, null, orHBL.getElement(driver, "InttraSi", ScenarioDetailsHashMap, 10), 2);
							System.out.println("CTdropdownvalue"+DDCTCount+CTdropdownvalue);
							if(CTdropdownvalue.equalsIgnoreCase("LCL"))
							{
								GenericMethods.assertTwoValues(InttraCheckBox_Status+"", false+"", "Validating inttra check box while Shipment Type is selected as 'House', House Type as 'Consolidation', Consol Type is selected as 'LCL'", ScenarioDetailsHashMap);

							}
							if(CTdropdownvalue.equalsIgnoreCase("FCL"))
							{
								GenericMethods.assertTwoValues(InttraCheckBox_Status+"", false+"", "Validating inttra check box while Shipment Type is selected as 'House', House Type as 'Consolidation', Consol Type is selected as 'FCL'", ScenarioDetailsHashMap);

							}
							if(CTdropdownvalue.equalsIgnoreCase("BREAK BULK"))
							{
								GenericMethods.assertTwoValues(InttraCheckBox_Status+"", false+"", "Validating inttra check box while Shipment Type is selected as 'House', House Type as 'Consolidation', Consol Type is selected as 'BREAK BULK'", ScenarioDetailsHashMap);
							}
							if(CTdropdownvalue.equalsIgnoreCase("RORO"))
							{
								GenericMethods.assertTwoValues(InttraCheckBox_Status+"", false+"", "Validating inttra check box while Shipment Type is selected as 'House', House Type as 'Consolidation', Consol Type is selected as 'RORO'", ScenarioDetailsHashMap);
							}
							GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "HBL_MAIN_DETAILS_Tab", ScenarioDetailsHashMap, 10), 2);
							GenericMethods.pauseExecution(3000);
							try{
								GenericMethods.handleAlert(driver, "accept");
							}catch (Exception e) {

							}
							GenericMethods.pauseExecution(3000);
						}

					}
					GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "MBLMainDetails", ScenarioDetailsHashMap, 10), 2);
					GenericMethods.pauseExecution(3000);
					try{
						GenericMethods.handleAlert(driver, "accept");
					}catch (Exception e) {

					}
					AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "CarrierIdLov", ScenarioDetailsHashMap, 10), orHBL, "CarrierId", ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "CarrierBookingreferance", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierBookingRefNo", ScenarioDetailsHashMap), 2);
					GenericMethods.pauseExecution(2000);
					AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "ServiceLevelLov", ScenarioDetailsHashMap, 10), orHBL, "Code", ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ServiceLevel", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "CutOffDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierCutoffDate", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "CutOfTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "CarrierCutoffTime", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "EtdDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ETD", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "EtdTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ETDTime", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "EtaDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ETA", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "EtaTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "ETATime", ScenarioDetailsHashMap), 2);
					HBL_VesselScheduleMaster_MainDetails(driver, ScenarioDetailsHashMap, RowNo);
					GenericMethods.replaceTextofTextField(driver, By.id("routeVesselName0"), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "Vessel",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeVoyage0"), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "VesselVoyage",ScenarioDetailsHashMap), 5);
					//GenericMethods.replaceTextofTextField(driver, By.id("routeEtdDate0"), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "VesselETD",ScenarioDetailsHashMap), 5);
					//GenericMethods.replaceTextofTextField(driver, By.id("routeEtdTime0"), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "VesselTime",ScenarioDetailsHashMap), 5);
					//GenericMethods.replaceTextofTextField(driver, By.id("routeEtaDate0"), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "VesselETA",ScenarioDetailsHashMap), 5);
					//GenericMethods.replaceTextofTextField(driver, By.id("routeEtaTime0"), null, ExcelUtils.getCellData("SE_HBL_MBLDetails", RowNo, "VesselETATime",ScenarioDetailsHashMap), 5);
					System.out.println("HTdropdownvalue B2B="+HTdropdownvalue);
					if(HTdropdownvalue.equalsIgnoreCase("B2B"))
					{
						GenericMethods.pauseExecution(2000);
						Select ConsoleTypeDropDown = new Select(driver.findElement(By.id("consoleType")));
						System.out.println(ConsoleTypeDropDown.getOptions().size());//will get all options as List<WebElement>
						int TotalCTdropdowncount =ConsoleTypeDropDown.getOptions().size();
						for (int DDCTCount = 0; DDCTCount < TotalCTdropdowncount; DDCTCount++)
						{
							String CTdropdownvalue = ConsoleTypeDropDown.getOptions().get(DDCTCount).getText();
							ConsoleTypeDropDown.selectByVisibleText(CTdropdownvalue);
							GenericMethods.pauseExecution(2000);
							System.out.println(ConsoleTypeDropDown.getOptions().get(DDCTCount).getText());
							GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "MBLMainDetails", ScenarioDetailsHashMap, 10), 2);
							GenericMethods.pauseExecution(3000);
							try{
								GenericMethods.handleAlert(driver, "accept");
							}catch (Exception e) {

							}
							InttraCheckBox_Status = GenericMethods.isFieldEnabled(driver, null, orHBL.getElement(driver, "InttraSi", ScenarioDetailsHashMap, 10), 2);

							if(CTdropdownvalue.equalsIgnoreCase("LCL"))
							{
								GenericMethods.assertTwoValues(InttraCheckBox_Status+"", false+"", "Validating inttra check box while Shipment Type is selected as 'House', House Type as 'B2B', Consol Type is selected as 'LCL'", ScenarioDetailsHashMap);

							}
							else if(CTdropdownvalue.equalsIgnoreCase("FCL"))
							{
								GenericMethods.assertTwoValues(InttraCheckBox_Status+"", true+"", "Validating inttra check box while Shipment Type is selected as 'House', House Type as 'B2B', Consol Type is selected as 'FCL'", ScenarioDetailsHashMap);

							}
							else if(CTdropdownvalue.equalsIgnoreCase("BREAK BULK"))
							{
								GenericMethods.assertTwoValues(InttraCheckBox_Status+"", false+"", "Validating inttra check box while Shipment Type is selected as 'House', House Type as 'B2B', Consol Type is selected as 'BREAK BULK'", ScenarioDetailsHashMap);
							}
							else if(CTdropdownvalue.equalsIgnoreCase("RORO"))
							{
								GenericMethods.assertTwoValues(InttraCheckBox_Status+"", false+"", "Validating inttra check box while Shipment Type is selected as 'House', House Type as 'B2B', Consol Type is selected as 'RORO'", ScenarioDetailsHashMap);
							}
							GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "HBL_MAIN_DETAILS_Tab", ScenarioDetailsHashMap, 10), 2);
							GenericMethods.pauseExecution(3000);
							try{
								GenericMethods.handleAlert(driver, "accept");
							}catch (Exception e) {

							}
						}
					}

				}



			}
			else //Direct
			{
				System.out.println("STdropdownvalueDirect="+STdropdownvalue);

			}
		}
	}
	//Sangeeta- FUNC041 - Below method is to perform unblocking of the shipment for Denied party shipment
	public static void Licensee_DeniedPartyUnblocking(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.Licensee_DeniedPartyUnblocking, "Denied Party",ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DeniedParty_ShipmentReference", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo,"HBLId", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap,10), 2);
		GenericMethods.clickElement(driver, By.id("multiSelectCheckbox1"),null, 2);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "MainDetails", ScenarioDetailsHashMap,10), 2);

		for (int GridRowID = 1; GridRowID <= driver.findElements(By.xpath("//table[@id='deniedPartyUnBlockingGrid']/tbody/tr")).size(); GridRowID++) 
		{
			String xpathPrefix = "//table[@id='deniedPartyUnBlockingGrid']/tbody/tr["+GridRowID+"]/";
			GenericMethods.clickElement(driver, By.xpath(xpathPrefix+"td[1]/input"),null, 2);
			System.out.println("value-1111---"+GenericMethods.getInnerText(driver,  By.xpath(xpathPrefix+"td[5]"),null, 10));
			System.out.println("value-2222---"+GenericMethods.getInnerText(driver,  By.xpath(xpathPrefix+"td[9]"),null, 10));
		}

		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DeniedParty_ReasonCode", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo,"DeniedParty_ReasonCode", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DeniedParty_Comments", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo,"DeniedParty_Comments", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null,orDecisionTable.getElement(driver, "DeniedParty_ApplyComments", ScenarioDetailsHashMap,10), 2);
		GenericMethods.clickElement(driver, null,orDecisionTable.getElement(driver, "DeniedParty_ApproveAll", ScenarioDetailsHashMap,10), 2);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
		ExcelUtils.setCellData("SE_BookingMainDetails", RowNo, "DeniedStatus", "N", ScenarioDetailsHashMap);
	}
	public static void HBL_NotesStatus(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		seaHBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "NotesAndStatus", ScenarioDetailsHashMap, 10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
		GenericMethods.pauseExecution(5000);
		GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "FreightStatus", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLNotesAndStatus", RowNo, "FreightStatus", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "Notes_ChargeStatus", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLNotesAndStatus", RowNo, "ChargeStatus", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "CustomsStatus", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLNotesAndStatus", RowNo, "CustomsStatus", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "Notes_deliveryStatus", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLNotesAndStatus", RowNo, "DeliveryStatus", ScenarioDetailsHashMap));
		//GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "Notes_OrderStatus", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLNotesAndStatus", RowNo, "DeliveryStatus", ScenarioDetailsHashMap));
		String NotesText = ExcelUtils.getCellData("SE_HBLNotesAndStatus", RowNo, "Textarea", ScenarioDetailsHashMap);
		System.out.println("NotesText"+NotesText);
		GenericMethods.pauseExecution(9000);
		//driver.findElement(By.name("Notes_Textarea")).click();
		GenericMethods.pauseExecution(2000);
		//driver.findElement(By.name("Notes_Textarea")).sendKeys("Notesadded");
		driver.findElement(By.xpath("//*[text()='Notes']/following-sibling::td[1]/textarea")).sendKeys(ExcelUtils.getCellData("SE_HBLNotesAndStatus", RowNo, "Textarea", ScenarioDetailsHashMap));
		//GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Notes_Textarea", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLNotesAndStatus", RowNo, "Textarea", ScenarioDetailsHashMap), 1);
		//GenericMethods.replaceTextofTextField(driver, By.xpath("//*[text()='Notes']/following-sibling::td[1]/textarea"), null, ExcelUtils.getCellData("SE_HBLNotesAndStatus", RowNo, "Textarea", ScenarioDetailsHashMap), 1);
		GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "NotesCategory", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLNotesAndStatus", RowNo, "NotesCategory", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "NotesType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLNotesAndStatus", RowNo, "NotesType", ScenarioDetailsHashMap));
		GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "CAddButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
		GenericMethods.pauseExecution(2000);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
		GenericMethods.pauseExecution(5000);
	}
	public static void HBL_MainDetailTab(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "MainDetails", ScenarioDetailsHashMap, 10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
	}


}
