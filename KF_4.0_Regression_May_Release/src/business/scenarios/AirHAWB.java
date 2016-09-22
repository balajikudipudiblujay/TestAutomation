package business.scenarios;

import global.reusables.ExcelUtils;
import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import app.reuseables.AppReusableMethods;
import app.reuseables.AppReusableMethods;
import app.reuseables.ETransMenu;

public class AirHAWB {
	static ObjectRepository orCommon=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/CommonObjects.xml");
	static ObjectRepository orAirHAWB=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/AirHAWB.xml");
	static ObjectRepository orAirBooking = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/AirBooking.xml");
	static ObjectRepository orAirMawb=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/AirMawb.xml");

	public static void pictHAWBDetailsFlow(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo ){

		AppReusableMethods.selectMenu(driver,ETransMenu.HAWB,"HAWB", ScenarioDetailsHashMap);
		if(ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "HAWBBasis",ScenarioDetailsHashMap).equalsIgnoreCase("Assembly Shipment"))
		{
			System.out.println("hello");
			ArrayList<String> BookingIdValues =new ArrayList<String>();
			BookingIdValues=ExcelUtils.getAllCellValuesOfColumn("Air_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
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
			ExcelUtils.setCellData("Air_HAWBMainDetails", RowNo, "BookingId", BookingIDs.substring(0,BookingIDs.length()-1), ScenarioDetailsHashMap);
		}

		AppReusableMethods.selectMenu(driver,ETransMenu.HAWB,"HAWB", ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "AddButton", ScenarioDetailsHashMap, 10), 2);
		hawbMainDetails(driver, ScenarioDetailsHashMap, RowNo, ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "HAWBBasis", ScenarioDetailsHashMap));
		GenericMethods.pauseExecution(2000);

		String HAWBBasis = ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "HAWBBasis", ScenarioDetailsHashMap);
		String ShipmentType = ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ConsolidationORB2B", ScenarioDetailsHashMap);
		if(ShipmentType.equalsIgnoreCase("B2B")&& HAWBBasis.equalsIgnoreCase("Assembly Shipment"))
		{
			HAWBAssembly(driver, ScenarioDetailsHashMap, RowNo);	
			mawbMainDetails(driver, ScenarioDetailsHashMap, RowNo, ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ConsolidationORB2B", ScenarioDetailsHashMap));
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				//System.out.println("no Alerts present");
			}
		}
		else if (ShipmentType.equalsIgnoreCase("Consolidation")&& HAWBBasis.equalsIgnoreCase("Assembly Shipment"))
		{
			HAWBAssembly(driver, ScenarioDetailsHashMap, RowNo);	
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				//System.out.println("no Alerts present");
			}
		}
		else
		{
			System.out.println("ïn mawbmaindetails");
			mawbMainDetails(driver, ScenarioDetailsHashMap, RowNo, ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ConsolidationORB2B", ScenarioDetailsHashMap));
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				//System.out.println("no Alerts present");
			}

		}


		//		HAWBParties(driver, ScenarioDetailsHashMap, RowNo);
		if(ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "HAWBBasis", ScenarioDetailsHashMap).equalsIgnoreCase("Adhoc Shipment"))
		{
			hawbCargoPackDetails(driver, ScenarioDetailsHashMap, RowNo);
		}
		if(!HAWBBasis.equalsIgnoreCase("Assembly Shipment")&&
				!ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "PickUpRequired", ScenarioDetailsHashMap).equalsIgnoreCase("No Pick Up"))
		{
			HAWBPickUpInstructions(driver, ScenarioDetailsHashMap, RowNo);
		}
		GenericMethods.pauseExecution(2000);

	}

	public static void hawbMainDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo,String HAWBBasis)
	{
		if(HAWBBasis.equals("From Booking"))
		{
			String ShipmentType = ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap);
			//System.out.println("ShipmentType "+ ShipmentType);
			GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "HAWBBasis", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "HAWBBasis",ScenarioDetailsHashMap));
			if(ShipmentType.equalsIgnoreCase("House"))
			{
				//				GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "HAWB_ShipmentType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap));
				GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "HawbType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ConsolidationORB2B", ScenarioDetailsHashMap));
			}
			else
			{
				//				GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "HAWB_ShipmentType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap));
				GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "HouseOrderType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "OrderType",ScenarioDetailsHashMap));
				GenericMethods.isFieldEnabled(driver, null, orAirHAWB.getElement(driver, "HawbType", ScenarioDetailsHashMap, 10), 2);
				//System.out.println("HAWB ="+GenericMethods.isFieldEnabled(driver, null, orAirHAWB.getElement(driver, "HawbType", 10), 2));
			}
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "HAWB_BookingIdLov", ScenarioDetailsHashMap, 10), orAirHAWB, "BookingId", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "BookingId",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(5000);
			//			GenericMethods.selectOptionFromDropDown(driver, null, orAirBooking.getElement(driver, "HawbType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "HouseType",ScenarioDetailsHashMap));
			//			GenericMethods.selectOptionFromDropDown(driver, null, orAirBooking.getElement(driver, "HawbType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ConsolidationORB2B",ScenarioDetailsHashMap));
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "HAWB_InvoiceBranchLov", ScenarioDetailsHashMap, 10), orAirHAWB, "LovField", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "InvoiceBranch"	, ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "HAWB_InvoiceDept", ScenarioDetailsHashMap, 10	), orAirHAWB, "Code", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "InvoiceDept", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "CargoReadyDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "CargoReadyDate", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "CargoReadyTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "CargoReadyTime", ScenarioDetailsHashMap), 2);
			//GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "PickUpDate", 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "PickUpDate", ScenarioDetailsHashMap), 2);
			//GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "pickupTime", 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "PickUpTime", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "RequiredDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "RequiredDate", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "RequiredTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "RequiredDateTime", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "documentRequiredDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "DocumentsRequiredDate", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "documentRequiredTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "DocumentsRequiredTime", ScenarioDetailsHashMap), 2);
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "CustomerLOV", ScenarioDetailsHashMap, 10), orAirHAWB, "LovField", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "Customer",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "CustomerReference", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "CustomerReference", ScenarioDetailsHashMap), 2);
			//			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "ConsigneeLov", 10), orAirHAWB, "LovField", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ConsigneeNickName", ScenarioDetailsHashMap));
			GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "CargoReceived", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "CargoRecieved", ScenarioDetailsHashMap));
			
			GenericMethods.pauseExecution(5000);
			GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "AirPickupRequired", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "PickUpRequired", ScenarioDetailsHashMap));
			
			//			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "CarrierBookingreferance", 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "CarrierBookingReference", ScenarioDetailsHashMap), 2);
			//GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "Customer", 10), ExcelUtils.getCellData("", RowNo, "Customer", ScenarioDetailsHashMap), "Customer", "Validating Customer Name", ScenarioDetailsHashMap);
			/*GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "OriginBranchHbl", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "OrignBranch", ScenarioDetailsHashMap), "OriginBranchHbl", "validating Origin Location", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "OriginDept", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "OriginDept", ScenarioDetailsHashMap), "OriginDept", "Validating Origin Dept", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "DesiTerminalHbl", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "DestBranch", ScenarioDetailsHashMap), "DesiTerminalHbl", "Validating Destination Branch", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "DesiDeptHbl", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "DestDept", ScenarioDetailsHashMap), "DesiDeptHbl", "Validating Destination Dept", ScenarioDetailsHashMap);
			 */	//GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "ShipperName", 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ShipperNickName", ScenarioDetailsHashMap), "ShipperName", "Validating Shipper Name", ScenarioDetailsHashMap);
			//GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "ConsigneeName", 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ConsigneeNickName", ScenarioDetailsHashMap), "ConsigneeName", "Validating ConsigneeName ", ScenarioDetailsHashMap);
			//GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "ServiceLevel", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ServiceLevel", ScenarioDetailsHashMap), "ServiceLevel", "Validating ServiceLevel", ScenarioDetailsHashMap);
			//GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "ShipmentTypeOrigin", 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ServiceTypeOrig", ScenarioDetailsHashMap), "ShipmentTypeOrigin", "Validating ShipmentTypeOrigin", ScenarioDetailsHashMap);
			//GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "destinationShipmentTerms", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ServiceTypeDest", ScenarioDetailsHashMap), "shipmentTermDest", "Validating shipmentTermDest", ScenarioDetailsHashMap);
			/*GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "AirportOfDeparture", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "PortOfLoading", ScenarioDetailsHashMap), "PortOfLoading", "Validating PortOfLoading", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "AirportOfDestination", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "PortOfDischarge", ScenarioDetailsHashMap), "PortOfDischarge", "Validating PortOfDischarge", ScenarioDetailsHashMap);
			 */	
			/*GenericMethods.pauseExecution(5000);
			Select SecureShipment = new Select(driver.findElement(By.name("secureShipment")));
			String SecureShipmentText = SecureShipment.getFirstSelectedOption().getText();
			GenericMethods.pauseExecution(3000);
			System.out.println("SecureShipmentText"+SecureShipmentText);
			if(SecureShipmentText.equals("Yes"))
			{
				SecureShipment.selectByVisibleText("No");
			}*/
		
		}
		else if(HAWBBasis.equals("Adhoc Shipment"))
		{
			String ShipmentType = ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap);
			System.out.println("ShipmentType "+ ShipmentType);
			GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "HAWBBasis", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "HAWBBasis",ScenarioDetailsHashMap));
			if(ShipmentType.equalsIgnoreCase("House"))
			{
				GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "HAWB_ShipmentType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap));
				GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "HawbType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ConsolidationORB2B", ScenarioDetailsHashMap));
			}
			else
			{
				GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "HAWB_ShipmentType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap));
				GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "HouseOrderType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "OrderType",ScenarioDetailsHashMap));
				GenericMethods.isFieldEnabled(driver, null, orAirHAWB.getElement(driver, "HawbType", ScenarioDetailsHashMap, 10), 2);
				//System.out.println("HAWB ="+GenericMethods.isFieldEnabled(driver, null, orAirHAWB.getElement(driver, "HawbType", 10), 2));
			}
			//GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "HAWB_ShipmentType", 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap));
			GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "PickUpLoadtype", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "LoadType", ScenarioDetailsHashMap));
			//GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "HawbType", 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ConsolidationORB2B", ScenarioDetailsHashMap));
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "CustomerLov", ScenarioDetailsHashMap, 10), orAirHAWB, "LovField", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "Customer", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "OriginDeptLov", ScenarioDetailsHashMap, 10), orAirHAWB, "Code", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "OriginDept", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "DesiTerminalHblLov", ScenarioDetailsHashMap, 10), orAirHAWB, "LovField", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "DestBranch", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "DesiDeptHblLov", ScenarioDetailsHashMap, 10), orAirHAWB, "Code", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "DestDept", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "HAWB_InvoiceBranchLov", ScenarioDetailsHashMap, 10), orAirHAWB, "LovField", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "InvoiceBranch"	, ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "HAWB_InvoiceDept", ScenarioDetailsHashMap, 10	), orAirHAWB, "Code", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "InvoiceDept", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "ShipperIdLov", ScenarioDetailsHashMap, 10), orAirHAWB, "LovField", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ShipperNickName", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(5000);
			GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "AirPickupRequired", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "PickUpRequired", ScenarioDetailsHashMap));
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "ConsigneeLov", ScenarioDetailsHashMap, 10), orAirHAWB, "LovField", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ConsigneeNickName", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "NotifyPartyLov", ScenarioDetailsHashMap, 10), orAirHAWB, "LovField", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "NotifyParty", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "FreightTerms", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "FreightTerms", ScenarioDetailsHashMap));
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "ServiceLevelLov", ScenarioDetailsHashMap, 10), orAirHAWB, "Code", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ServiceLevel", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "Btn_ServiceTypeOrg", ScenarioDetailsHashMap, 10), orAirHAWB, "ServiceTypeField", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ServiceTypeOrig", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "Btn_ServiceTypeDest", ScenarioDetailsHashMap, 10), orAirHAWB, "ServiceTypeField", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ServiceTypeDest", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "Btn_placeOfReceiptImage", ScenarioDetailsHashMap, 10), orAirHAWB, "PortOfLoadingField",ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "PortofLoading", ScenarioDetailsHashMap), ScenarioDetailsHashMap );
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "Btn_placeOfDelivery", ScenarioDetailsHashMap, 10), orAirHAWB, "PortOfLoadingField", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "PortofDischarge", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Btn_AirportOfDeparture", ScenarioDetailsHashMap, 10), orAirHAWB,"PortOfLoadingField",ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "AirportOfDeparture",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Btn_AirportOfDestination", ScenarioDetailsHashMap, 10), orAirHAWB,"PortOfLoadingField",ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "AirportOfDestination",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			
			/*GenericMethods.pauseExecution(5000);
			Select SecureShipment = new Select(driver.findElement(By.name("secureShipment")));
			String SecureShipmentText = SecureShipment.getFirstSelectedOption().getText();
			System.out.println("SecureShipmentText :"+SecureShipmentText);
			GenericMethods.pauseExecution(3000);
			if(SecureShipmentText.equalsIgnoreCase("Yes"))
			{
				System.out.println("SecureShipment b4");
				SecureShipment.selectByVisibleText("No");
				System.out.println("SecureShipment a4");
			}*/

		}
		else if(HAWBBasis.equals("Assembly Shipment"))
		{
			String ShipmentType = ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap);
			System.out.println("ShipmentType "+ ShipmentType);
			GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "HAWBBasis", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "HAWBBasis",ScenarioDetailsHashMap));
			if(ShipmentType.equalsIgnoreCase("House"))
			{
				GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "HAWB_ShipmentType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap));
				GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "HawbType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ConsolidationORB2B", ScenarioDetailsHashMap));
			}
			else
			{
				GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "HAWB_ShipmentType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap));
				GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "HouseOrderType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "OrderType",ScenarioDetailsHashMap));
				GenericMethods.isFieldEnabled(driver, null, orAirHAWB.getElement(driver, "HawbType", ScenarioDetailsHashMap, 10), 2);
				//System.out.println("HAWB ="+GenericMethods.isFieldEnabled(driver, null, orAirHAWB.getElement(driver, "HawbType", ScenarioDetailsHashMap, 10), 2));
			}
			GenericMethods.pauseExecution(2000);
			String AssignInHAWB_FromBooking = ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "AssignInHAWB_FromBooking", ScenarioDetailsHashMap);
			//System.out.println(ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "AssignInHAWB_FromBooking", ScenarioDetailsHashMap));
			if(AssignInHAWB_FromBooking.equalsIgnoreCase("No"))
			{
				AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "HAWB_BookingIdLov", ScenarioDetailsHashMap, 10), orAirHAWB, "BookingId", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "BookingId",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			}
			else
			{
				AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "OriginDeptLov", ScenarioDetailsHashMap, 10), orAirHAWB, "Code", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "OriginDept", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "ShipperIdLov", ScenarioDetailsHashMap, 10), orAirHAWB, "LovField", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ShipperNickName", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "AirPickupRequired", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "PickUpRequired", ScenarioDetailsHashMap));
				AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "ConsigneeLov", ScenarioDetailsHashMap, 10), orAirHAWB, "LovField", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ConsigneeNickName", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "Btn_AirportOfDeparture", ScenarioDetailsHashMap, 10), orAirHAWB, "PortOfLoadingField", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "AirportOfDeparture", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "Btn_AirportOfDestination", ScenarioDetailsHashMap, 10), orAirHAWB, "PortOfLoadingField", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "AirportOfDestination", ScenarioDetailsHashMap), ScenarioDetailsHashMap);

			}
			GenericMethods.pauseExecution(5000);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "DesiTerminalHbl", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "DestBranch", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "DesiDeptHbl", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "DestDept", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "HAWB_InvoiceBranch", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "InvoiceBranch", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "HAWBinvoiceDept", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "InvoiceDept", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "CargoReadyDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "CargoReadyDate", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "CargoReadyTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "CargoReadyTime", ScenarioDetailsHashMap), 2);
			//GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "PickUpDate", 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "PickUpDate", ScenarioDetailsHashMap), 2);
			//GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "pickupTime", 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "PickUpTime", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "RequiredDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "RequiredDate", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "RequiredTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "RequiredDateTime", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "documentRequiredDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "DocumentsRequiredDate", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "documentRequiredTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "DocumentsRequiredTime", ScenarioDetailsHashMap), 2);
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "CustomerLOV", ScenarioDetailsHashMap, 10), orAirHAWB, "LovField", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "Customer",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "CustomerReference", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "CustomerReference", ScenarioDetailsHashMap), 2);
			//AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "ConsigneeLov", 10), orAirHAWB, "LovField", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ConsigneeNickName", ScenarioDetailsHashMap));
			GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "CargoReceived", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "CargoRecieved", ScenarioDetailsHashMap));
			//GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "CarrierBookingreferance", 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "CarrierBookingReference", ScenarioDetailsHashMap), 2);
			//GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "Customer", 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "Customer", ScenarioDetailsHashMap), "Customer", "Validating Customer Name", ScenarioDetailsHashMap);

			//pavan Commented validation
			/*GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "OriginBranchHbl", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "OrignBranch", ScenarioDetailsHashMap), "OriginBranchHbl", "validating Origin Location", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "OriginDept", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "OriginDept", ScenarioDetailsHashMap), "OriginDept", "Validating Origin Dept", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "DesiTerminalHbl", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "DestBranch", ScenarioDetailsHashMap), "DesiTerminalHbl", "Validating Destination Branch", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "DesiDeptHbl", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "DestDept", ScenarioDetailsHashMap), "DesiDeptHbl", "Validating Destination Dept", ScenarioDetailsHashMap);
			//GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "ShipperName", 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ShipperNickName", ScenarioDetailsHashMap), "ShipperName", "Validating Shipper Name", ScenarioDetailsHashMap);
			//GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "ConsigneeName", 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ConsigneeNickName", ScenarioDetailsHashMap), "ConsigneeName", "Validating ConsigneeName ", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "ServiceLevel", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ServiceLevel", ScenarioDetailsHashMap), "ServiceLevel", "Validating ServiceLevel", ScenarioDetailsHashMap);
			//GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "ShipmentTypeOrigin", 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ServiceTypeOrig", ScenarioDetailsHashMap), "ShipmentTypeOrigin", "Validating ShipmentTypeOrigin", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "destinationShipmentTerms", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ServiceTypeDest", ScenarioDetailsHashMap), "shipmentTermDest", "Validating shipmentTermDest", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "AirportOfDeparture", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "PortOfLoading", ScenarioDetailsHashMap), "PortOfLoading", "Validating PortOfLoading", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "AirportOfDestination", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "PortOfDischarge", ScenarioDetailsHashMap), "PortOfDischarge", "Validating PortOfDischarge", ScenarioDetailsHashMap);
			 */
			/*GenericMethods.pauseExecution(5000);
			Select SecureShipment = new Select(driver.findElement(By.name("secureShipment")));
			String SecureShipmentText = SecureShipment.getFirstSelectedOption().getText();
			System.out.println("SecureShipmentText"+SecureShipmentText);
			GenericMethods.pauseExecution(3000);
			if(SecureShipmentText.equalsIgnoreCase("Yes"))
			{
				System.out.println("SecureShipment b4");
				SecureShipment.selectByVisibleText("No");
				System.out.println("SecureShipment a4");
			}*/
		}
		//Below Feb 6th 2015 Condition is for if Hawb Id is Manual
		if(!ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "HAWBId_Selection", ScenarioDetailsHashMap).equalsIgnoreCase("Auto")){
			GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "CHB_ManualHAWB", ScenarioDetailsHashMap, 10), 10);
			String randomHAWB_ID;
			if(ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "TraxonRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes")){
				randomHAWB_ID=ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "HAWBIdName", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(2);
				System.out.println("randomHAWB_ID::"+randomHAWB_ID);
			}
			else
			{
				randomHAWB_ID=ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "HAWBIdName", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(5);
				System.out.println("randomHAWB_ID::"+randomHAWB_ID);
			}


			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "Textbox_Hawb_Id", ScenarioDetailsHashMap, 10), randomHAWB_ID, 2);
		}
		if(ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "eAWB_Required", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			HAWB_eAWB_Module(driver, ScenarioDetailsHashMap, RowNo);
		}
		if(ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "MawbID_ReferenceSelectionRequired",ScenarioDetailsHashMap).equalsIgnoreCase("yes")){
			HAWB_MAWB_Id_Reference_Selection(driver, ScenarioDetailsHashMap, RowNo);
		}
		GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "CustomerReference", ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(1000);

	}

	public static void mawbMainDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo,String consolidationType){
		//if(consolidationType.equals("B2B")||GenericMethods.isFieldEnabled(driver, null, orAirHAWB.getElement(driver, "HawbType", 10), 2))
		//System.out.println("consolidationType :"+ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ConsolidationORB2B", ScenarioDetailsHashMap));
		if(!consolidationType.equals("Consolidation"))
		{
			//System.out.println("Entered in if");
			GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "MAWBMainDetails", ScenarioDetailsHashMap, 10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");
				GenericMethods.pauseExecution(3000);
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {
				//System.out.println("no Alerts present");
			}
			String ShipmentType = ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ConsolidationORB2B", ScenarioDetailsHashMap);
			String HAWBBasis = ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "HAWBBasis", ScenarioDetailsHashMap);
			if(ShipmentType.equalsIgnoreCase("B2B")&& HAWBBasis.equalsIgnoreCase("Adhoc Shipment")||
					(ShipmentType.equalsIgnoreCase("B2B")&& HAWBBasis.equalsIgnoreCase("Assembly Shipment")))
			{
				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "CarrierId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "CarrierId",ScenarioDetailsHashMap), 10);
				AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "ServiceLevelIdImage_Lov", ScenarioDetailsHashMap, 10), orAirBooking,	"OrgCode", 
						ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "ServiceLevel",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				String randomMAWB_ID=ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "MAWBID", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(8)+"";
				String XL_MAWB_ID = randomMAWB_ID;
				StringBuilder xl= new StringBuilder(XL_MAWB_ID);
				String Updated_MAWB_ID=xl.insert(8, " ").toString();

				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "MAWBID", ScenarioDetailsHashMap, 20),  Updated_MAWB_ID, 20);
				GenericMethods.pauseExecution(3000);
				//				StringBuilder xl= new StringBuilder(randomMAWB_ID);
				//				String Updated_MAWB_ID=xl.insert(3, "-").insert(8, " ").toString();
				ExcelUtils.setCellData("Air_HAWB_MAWBMainDetails", RowNo, "MAWB_ID", Updated_MAWB_ID, ScenarioDetailsHashMap);
				for (int i = 1; i <= ExcelUtils.getSubScenarioRowCount("Air_Arrival", ScenarioDetailsHashMap); i++) {
					ExcelUtils.setCellData_Without_DataSet("Air_Arrival", i, "MAWB_ID",Updated_MAWB_ID, ScenarioDetailsHashMap);
				}
				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "AirportOfDeparture", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "AirportOfDeparture", ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "AirportOfDestination", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "AirportOfDestination", ScenarioDetailsHashMap), 10);
				/*GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_ETAFinalDestDate", ScenarioDetailsHashMap, 10), 
						ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo,"Air_ETAFinalDestDate", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_ETAFinalDestDate", ScenarioDetailsHashMap, 10), 
						ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo,"Air_ETAFinalDestDate", ScenarioDetailsHashMap), 2);
				 */	GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETDDate", ScenarioDetailsHashMap, 10), 
						 ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo,"ETD", ScenarioDetailsHashMap), 2);
				 GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETDTime", ScenarioDetailsHashMap, 10), 
						 ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo,"ETDTime", ScenarioDetailsHashMap), 2);
				 GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETADate", ScenarioDetailsHashMap, 10), 
						 ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo,"ETA", ScenarioDetailsHashMap), 2);
				 GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETATime", ScenarioDetailsHashMap, 10), 
						 ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo,"ETATime", ScenarioDetailsHashMap), 2);
				 /*GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_ETAFinalDestDate", ScenarioDetailsHashMap, 10), 
						ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo,"Air_ETAFinalDestDate", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_ETAFinalDestTime", ScenarioDetailsHashMap, 10), 
						ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo,"Air_ETAFinalDestTime", ScenarioDetailsHashMap), 2);
				  */
				 //				HAWB_MAWB_FlightSchedule_MainDetails(driver, ScenarioDetailsHashMap, RowNo);
				 /*AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Btn_FlightTo", ScenarioDetailsHashMap, 10), orAirBooking, "LocationCode", 
						ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo,"FlightTo", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "FlightNo", ScenarioDetailsHashMap, 10), 
						ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo,"FlightNo", ScenarioDetailsHashMap), 2);*/
			}
			else if(ShipmentType.equalsIgnoreCase("B2B")&& HAWBBasis.equalsIgnoreCase("From Booking"))
			{

				GenericMethods.assertValue(driver, null, orAirBooking.getElement(driver, "CarrierId", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "CarrierId",ScenarioDetailsHashMap), "Carrier", "Validating Carrier in MAWB Main Details tab", ScenarioDetailsHashMap);
				AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "ServiceLevelIdImage_Lov", ScenarioDetailsHashMap, 10), orAirBooking,	"OrgCode", 
						ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "ServiceLevel",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				String randomMAWB_ID=ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "MAWBID", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(8)+"";
				String XL_MAWB_ID = randomMAWB_ID;
				StringBuilder xl= new StringBuilder(XL_MAWB_ID);
				String Updated_MAWB_ID=xl.insert(8, " ").toString();
				GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "MAWBID", ScenarioDetailsHashMap, 20),  Updated_MAWB_ID, 20);
				GenericMethods.pauseExecution(3000);
				System.out.println("Updated_MAWB_ID::"+Updated_MAWB_ID);
				ExcelUtils.setCellData("Air_HAWB_MAWBMainDetails",RowNo, "MAWB_ID", Updated_MAWB_ID, ScenarioDetailsHashMap);
				for (int i = 1; i <= ExcelUtils.getSubScenarioRowCount("Air_Arrival", ScenarioDetailsHashMap); i++) {
					ExcelUtils.setCellData_Without_DataSet("Air_Arrival", i, "MAWB_ID",Updated_MAWB_ID, ScenarioDetailsHashMap);
				}
				/*GenericMethods.assertValue(driver, null, orAirBooking.getElement(driver, "AirportOfDeparture", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "AirportOfDeparture",ScenarioDetailsHashMap), "AirportOfDeparture", "Validating Airport Of Departure in MAWB Main Details tab", ScenarioDetailsHashMap);
				GenericMethods.assertValue(driver, null, orAirBooking.getElement(driver, "AirportOfDestination", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "AirportOfDestination",ScenarioDetailsHashMap), "AirportOfDestination", "Validating Airport Of Destination in MAWB Main Details tab", ScenarioDetailsHashMap);
				 *///				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_ETAFinalDestDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo,"Air_ETAFinalDestDate", ScenarioDetailsHashMap), 2);
				//				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_ETAFinalDestTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo,"Air_ETAFinalDestTime", ScenarioDetailsHashMap), 2);

				/*	GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "FlightTo", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "FlightTo",ScenarioDetailsHashMap), "FlightTo", "Validating FlightTo in MAWB Main Details tab", ScenarioDetailsHashMap);
				GenericMethods.assertValue(driver, null, orAirHAWB.getElement(driver, "FlightNo", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "FlightNo",ScenarioDetailsHashMap), "FlightNo", "Validating FlightNo in MAWB Main Details tab", ScenarioDetailsHashMap);
				 */
			}
			/*else if(ShipmentType.equalsIgnoreCase("B2B")&& HAWBBasis.equalsIgnoreCase("Assembly Shipment"))
			{
				System.out.println("Entered in elseif Assembly Shipment");
				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "CarrierId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "CarrierId",ScenarioDetailsHashMap), 10);
				AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "ServiceLevelIdImage_Lov", ScenarioDetailsHashMap, 10), orAirBooking,	"OrgCode", 
						ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "ServiceLevel",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				String randomMAWB_ID=ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "MAWBID", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(8)+"";
				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "MAWBID", ScenarioDetailsHashMap, 20),  randomMAWB_ID, 20);
				GenericMethods.pauseExecution(3000);
				StringBuilder xl= new StringBuilder(randomMAWB_ID);
				String Updated_MAWB_ID=xl.insert(3, "-").insert(8, " ").toString();
				ExcelUtils.setCellData("Air_HAWB_MAWBMainDetails", RowNo, "MAWBID", randomMAWB_ID, ScenarioDetailsHashMap);
				ExcelUtils.setCellData("Air_HAWB_MAWBMainDetails", RowNo, "MAWB_ID", Updated_MAWB_ID, ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "AirportOfDeparture", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "AirportOfDeparture", ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "AirportOfDestination", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "AirportOfDestination", ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_ETAFinalDestDate", ScenarioDetailsHashMap, 10), 
						ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo,"Air_ETAFinalDestDate", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_ETAFinalDestDate", ScenarioDetailsHashMap, 10), 
						ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo,"Air_ETAFinalDestDate", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETDDate", ScenarioDetailsHashMap, 10), 
						ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo,"ETD", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETDTime", ScenarioDetailsHashMap, 10), 
						ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo,"ETDTime", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETADate", ScenarioDetailsHashMap, 10), 
						ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo,"ETA", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETATime", ScenarioDetailsHashMap, 10), 
						ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo,"ETATime", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_ETAFinalDestDate", ScenarioDetailsHashMap, 10), 
						ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo,"Air_ETAFinalDestDate", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_ETAFinalDestTime", ScenarioDetailsHashMap, 10), 
						ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo,"Air_ETAFinalDestTime", ScenarioDetailsHashMap), 2);

				Commented below lines because it will not work for multiple Schedules and added below HAWB_MAWB_FlightSchedule_MainDetails method instead
				  AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Btn_FlightTo", ScenarioDetailsHashMap, 10), orAirBooking, "LocationCode", 
						ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo,"FlightTo", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "FlightNo", ScenarioDetailsHashMap, 10), 
						ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo,"FlightNo", ScenarioDetailsHashMap), 2);

			}*/
			if(ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "FlightScheduleRequired",ScenarioDetailsHashMap).equalsIgnoreCase("HAWB"))
			{	
				orAirHAWB.getElement(driver, "Textbox_FlightScheduleId", ScenarioDetailsHashMap, 10).clear();
				orAirHAWB.getElement(driver, "DesiTerminalHbl", ScenarioDetailsHashMap, 10).click();
				HAWB_MAWB_FlightSchedule_MainDetails(driver, ScenarioDetailsHashMap, RowNo);
			}

		}
	}

	//Pavan Feb 10th 2015 Modified below method as per Matrix sheet
	public static void hawbCargoPackDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "CargoDetails", ScenarioDetailsHashMap, 10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		GenericMethods.pauseExecution(3000);
		String packageValue=orAirHAWB.getElement(driver, "Texbox_Packages", ScenarioDetailsHashMap, 10).getAttribute("previousvalue");
		System.out.println("packageValue>>"+packageValue+"<<<");

		if(!ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "CargoPackReq",ScenarioDetailsHashMap).equalsIgnoreCase("Booking"))
		{
			if(packageValue!=null||packageValue!="")
			{
				GenericMethods.clickElement(driver, null,orAirHAWB.getElement(driver, "AddPacks", ScenarioDetailsHashMap, 10), 2);
				try{
					GenericMethods.handleAlert(driver, "accept");
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			GenericMethods.clickElement(driver, null, orAirBooking.getElement(driver, "Btn_Package_Add", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_Packages", ScenarioDetailsHashMap, 10), 
					ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "Packages",ScenarioDetailsHashMap), 2);
			/*AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Img_Type", ScenarioDetailsHashMap, 10), orAirBooking,"Packtype", 
				ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "Type",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			 */
			GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Type", ScenarioDetailsHashMap, 10), 
					ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "Type",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "SubPack", ScenarioDetailsHashMap, 10), 
					ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "SubPack",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "SubPackType", ScenarioDetailsHashMap, 10), 
					ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "SubType",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "PackGrossWeight", ScenarioDetailsHashMap, 10), 
					ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "GrossWeight",ScenarioDetailsHashMap), 2);
			GenericMethods.selectOptionFromDropDown(driver, null, orAirBooking.getElement(driver, "UOW", ScenarioDetailsHashMap, 10), 
					ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "UOW",ScenarioDetailsHashMap));
			GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "PackNetWeight", ScenarioDetailsHashMap, 10), 
					ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "NetWeight",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Length", ScenarioDetailsHashMap, 10), 
					ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "Length",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Width", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "Width",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Height", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "Height",ScenarioDetailsHashMap), 2);
			GenericMethods.selectOptionFromDropDown(driver, null, orAirBooking.getElement(driver, "UOM", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "UOM",ScenarioDetailsHashMap));
			GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Volume", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "Volume",ScenarioDetailsHashMap), 2);
			AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Img_PackCommodity", ScenarioDetailsHashMap, 10), orAirBooking,"Code", ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "Commodity",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Img_FRTClass", ScenarioDetailsHashMap, 10), orAirBooking,"Code", ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "FRTClass",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ScheduleB", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "ScheduleB",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "GoodsValue", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "GoodsValue",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "PackageCurrency", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "GoodsCurrency",ScenarioDetailsHashMap), 2);
			GenericMethods.clickElement(driver, null, orAirBooking.getElement(driver, "Btn_Package_Add", ScenarioDetailsHashMap, 10), 2);
			//Invoice
			if (ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "SSCCFormat",ScenarioDetailsHashMap).equalsIgnoreCase("Standard")) {
				GenericMethods.clickElement(driver, null,orAirHAWB.getElement(driver, "Standard",ScenarioDetailsHashMap, 10), 2);	
			}else{
				GenericMethods.clickElement(driver, null,orAirHAWB.getElement(driver, "NonStandard",ScenarioDetailsHashMap, 10), 2);	
			}
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "PoNumber",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "PoNumber",ScenarioDetailsHashMap), 2);
			GenericMethods.pauseExecution(1000);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "Texbox_PartId_Export",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "PartId_Export",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "Textbox_GrossWeight",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "GrossWeight",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "NetWeight",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "NetWeight",ScenarioDetailsHashMap), 2);
			GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "DDL_UOW",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "UOW",ScenarioDetailsHashMap));
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "StatQty",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "StatQty1",ScenarioDetailsHashMap), 2);
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "StatQtyTypeLov",ScenarioDetailsHashMap, 10), orAirHAWB,"Lov_PackType", ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "StatQty1Type",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "StatQty2",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "StatQty2",ScenarioDetailsHashMap), 2);
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "StatQty2TypeLov",ScenarioDetailsHashMap, 10), orAirHAWB,"Lov_PackType", ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "StatQty2Type",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "InvoiceCommodityLov",ScenarioDetailsHashMap, 10), orAirHAWB,"Code", ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "Commodity",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "InvHSCommodityLov",ScenarioDetailsHashMap, 10), orAirHAWB,"Code", ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "HSCommodity",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "InvGoodsDescription",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "GoodsDescription",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "InvGoodsValue",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "GoodsValue",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "InvCurrencyimp",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_HawbCargoDetails", RowNo, "GoodsCurrency",ScenarioDetailsHashMap), 2);
			GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "Btn_Invoice_Add",ScenarioDetailsHashMap, 10), 2);

		}
	}

	public static void HAWBAssembly(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "ASSEMBLY_Tab", ScenarioDetailsHashMap, 10),2);
		try{
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}
		GenericMethods.pauseExecution(3000);

		String NumOfBookings_TestData [] =ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "BookingId", ScenarioDetailsHashMap).split(",");
		String BookingId="";
		System.out.println("NumOfBookings::"+NumOfBookings_TestData.length);

		/*ArrayList<String> BookingID = new ArrayList<String>();
		BookingID=ExcelUtils.getAllCellValuesOfColumn("Air_BookingMainDetails",
				"BookingId", ScenarioDetailsHashMap);
		//System.out.println("BookingID Size :"+BookingID.size());
		 */		String AssignInHAWB_FromBooking = ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "AssignInHAWB_FromBooking", ScenarioDetailsHashMap);
		 if(AssignInHAWB_FromBooking.equalsIgnoreCase("No"))
		 {
			 for (int Booking_RowCount = 1; Booking_RowCount <= NumOfBookings_TestData.length; Booking_RowCount++) 
			 {
				 int Booking_Split=Booking_RowCount-1;
				 BookingId=NumOfBookings_TestData[Booking_Split];

				 if(ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "AssignInHAWB", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
				 {
					 GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "Assembly_bookingIdSearch", ScenarioDetailsHashMap, 10),BookingId , 2);
					 GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
					 GenericMethods.pauseExecution(2000);
					 GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "UnAllocatedShipperNicknameGrid", ScenarioDetailsHashMap, 10), 2);
					 GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "Assembly_NextBtnGrid", ScenarioDetailsHashMap, 10), 2);
				 }
			 }
		 }
		 else
		 {
			 //System.out.println("AssignInHAWB_FromBooking = Yes");
			 for (int Booking_RowCount = 1; Booking_RowCount <= NumOfBookings_TestData.length; Booking_RowCount++) 
			 {
				 int Booking_Split=Booking_RowCount-1;
				 BookingId=NumOfBookings_TestData[Booking_Split];
				 if(ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "AssignInHAWB", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
				 {
					 GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "Assembly_bookingIdSearch", ScenarioDetailsHashMap, 10),BookingId, 2);
					 GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
					 GenericMethods.pauseExecution(2000);
					 GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "UnAllocatedShipperNicknameGrid", ScenarioDetailsHashMap, 10), 2);
					 GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "Assembly_NextBtnGrid", ScenarioDetailsHashMap, 10), 2);

				 }
			 }
		 }
		 /*
		  * Commented because as of now cargo pack calculation is not required
		  * 
		  * try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}
		GenericMethods.pauseExecution(3000);

		//Validating Total Pieces
		ArrayList<String> CargoDetails_Pcs = new ArrayList<String>();
		CargoDetails_Pcs=ExcelUtils.getAllCellValuesOfColumn("Air_BookingCargoDetails",
				"Packages", ScenarioDetailsHashMap);
		//System.out.println("CargoDetails_Pcs() "+CargoDetails_Pcs.size());
		//System.out.println(driver.findElement(By.id("noOfBookings")).getAttribute("previousvalue"));
		//Validating Total No Of Booking
		GenericMethods.assertTwoValues(CargoDetails_Pcs.size()+"", driver.findElement(By.id("noOfBookings")).getAttribute("previousvalue"), "Validating No Of Booking in Assembly tab", ScenarioDetailsHashMap);
		int TotPcs=0;
		for(int Pcs=0;Pcs<CargoDetails_Pcs.size();Pcs++)
		{
			//System.out.println("CargoDeatails pcs :"+CargoDetails.get(CargoSize));
			int Result=TotPcs+Integer.parseInt(CargoDetails_Pcs.get(Pcs));
			TotPcs=Result;
		}
		//System.out.println("TotPcs:-----"+TotPcs);
		String PiecesText =driver.findElement(By.id("pieces")).getAttribute("previousvalue");
		//System.out.println("PiecesText:-------------"+PiecesText);
		GenericMethods.assertTwoValues(driver.findElement(By.id("pieces")).getAttribute("previousvalue"), TotPcs+"", "Validating Total Pieces in Assembly tab", ScenarioDetailsHashMap);

		//Validating Total actual Weight
		ArrayList<String> CargoDetails_ActWgt = new ArrayList<String>();
		CargoDetails_ActWgt=ExcelUtils.getAllCellValuesOfColumn("Air_BookingCargoDetails",
				"GrossWeight", ScenarioDetailsHashMap);
		String pattern = "##.000";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);

		double TotActWgt=0;
		for(int ActWgt=0;ActWgt<CargoDetails_ActWgt.size();ActWgt++)
		{
			double Result=TotActWgt+Double.parseDouble(CargoDetails_ActWgt.get(ActWgt));
			TotActWgt=Result;
		}
		String TotalActWgt = decimalFormat.format(TotActWgt);
		//System.out.println("TotActWgt:-----"+TotActWgt);
		String actualWeight =driver.findElement(By.id("actualWeight")).getAttribute("previousvalue");
		//System.out.println("actualWeight:-------------"+actualWeight);
		String TotalActualWgt[] = actualWeight.split(" ");
		//System.out.println(TotalActWgt);
		GenericMethods.assertTwoValues(TotalActWgt+"", TotalActualWgt[0], "Validating Total Actual Weight in Assembly tab", ScenarioDetailsHashMap);

		//Validating Total volume
		ArrayList<String> CargoDetails_Vol = new ArrayList<String>();
		CargoDetails_Vol=ExcelUtils.getAllCellValuesOfColumn("Air_BookingCargoDetails",
				"Volume", ScenarioDetailsHashMap);
		double TotVol=0;
		for(int Vol=0;Vol<CargoDetails_Vol.size();Vol++)
		{
			double Result=TotVol+Double.parseDouble(CargoDetails_Vol.get(Vol));
			TotVol=Result;
		}
		String TotalVol = decimalFormat.format(TotVol);
		//System.out.println("TotVol:-----"+TotVol);
		String volume =driver.findElement(By.id("volume")).getAttribute("previousvalue");
		//System.out.println("volume:-------------"+volume);
		String TotalVolume[] = volume.split(" ");
		System.out.println(TotalVol);
		GenericMethods.assertTwoValues(TotalVol+"", TotalVolume[0], "Validating Total volume in Assembly tab", ScenarioDetailsHashMap);*/
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
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "AssemblyTab_Textbox_BookingID", ScenarioDetailsHashMap, 10), BookingId, 10);
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(6000);
			GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "AssemblyTab_Grid_Shipper", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "AssemblyTab_Button_Allocate", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(6000);
		}

	}

	public static void HAWBPickUpInstructions(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "Tab_PickUpInstructions", ScenarioDetailsHashMap, 10),2);
		GenericMethods.pauseExecution(2000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}
		GenericMethods.pauseExecution(1000);
		GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "Status", ScenarioDetailsHashMap,10) , ExcelUtils.getCellData("Air_HAWBPickUpInstructions", RowNo, "Status",ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null, orAirHAWB.getElement(driver, "TypeofMovement", ScenarioDetailsHashMap,10) , ExcelUtils.getCellData("Air_HAWBPickUpInstructions", RowNo, "TypeOfMovement",ScenarioDetailsHashMap));
		GenericMethods.pauseExecution(1500);

		if(ExcelUtils.getCellData("Air_HAWBPickUpInstructions", RowNo, "Status",ScenarioDetailsHashMap).equalsIgnoreCase("Partial"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "TotalPieces", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_HAWBPickUpInstructions", RowNo, "Pieces",ScenarioDetailsHashMap), 10);
			GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "PiecesType", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_HAWBPickUpInstructions", RowNo, "Type",ScenarioDetailsHashMap), 10);
		}

		GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "Btn_Add", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);
	}

	public static void HAWBParties(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "PartyDetails", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);
		try{
			GenericMethods.handleAlert(driver, "accept");

		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		GenericMethods.pauseExecution(2000);
	}


	public static void HAWBAssemblyValidation_FromBooking(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		AppReusableMethods.selectMenu(driver,ETransMenu.HAWB,"HAWB", ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(5000);
		GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "HAWBIDSearch", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "HAWB_Id", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.assertInnerText(driver, null, orAirHAWB.getElement(driver, "GridHAWBId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "HAWB_Id", ScenarioDetailsHashMap), "GridHAWBId", "Comparing hawb id",  ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(1000);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "ASSEMBLY_Tab", ScenarioDetailsHashMap, 10),2);
		try{
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}
		GenericMethods.pauseExecution(3000);
		int AssemblyAllocatedTableRowCount = driver.findElements(By.xpath("html/body/form/div/table/tbody/tr[4]/td/fieldset/table[2]/tbody/tr/td[3]/table/tbody/tr/td/div/table/tbody/tr")).size();
		System.out.println("AssemblyAllocatedTableRowCount :"+AssemblyAllocatedTableRowCount);
		//System.out.println("AssemblyAllocatedTableRowCount :"+AssemblyAllocatedTableRowCount);
		Boolean BookingIDAvailability = false;
		String BookingIDXL = ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "Assembly_BookingID", ScenarioDetailsHashMap);
		String BookingIDApp = null;
		for (int RowId = 1; RowId < AssemblyAllocatedTableRowCount; RowId++) 
		{
			BookingIDApp = driver.findElement(By.id("dtBookingId"+RowId)).getText();
			//System.out.println("BookingIDXL :"+BookingIDXL);
			//System.out.println("BookingIDApp :"+BookingIDApp);
			if(BookingIDXL.equalsIgnoreCase(BookingIDApp))
			{
				System.out.println("Assembly Operation successfully done :)");
				BookingIDAvailability = true;
				break;
			}
		}
		if(BookingIDAvailability)
		{
			GenericMethods.assertTwoValues(BookingIDApp, BookingIDXL, "Validating Booking Id from HAWB-Assembly-Consolidation", ScenarioDetailsHashMap);
		}		
		else
		{
			GenericMethods.assertTwoValues("", BookingIDXL, "Validating Booking Id from HAWB-Assembly-Consolidation", ScenarioDetailsHashMap);
		}

		HAWBParties(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "ASSEMBLY_Tab", ScenarioDetailsHashMap, 10),2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}
		GenericMethods.pauseExecution(5000);

		//Validating Total Pieces
		int TotPcs=0;
		for (int Pcs = 1; Pcs < AssemblyAllocatedTableRowCount; Pcs++) 
		{
			String CargoDetails_Pcs= driver.findElement(By.id("dtPieces"+Pcs)).getText();
			int Result=TotPcs+Integer.parseInt(CargoDetails_Pcs);
			TotPcs=Result;
		}

		//System.out.println("TotPcs:-----"+TotPcs);
		String PiecesText =driver.findElement(By.id("pieces")).getAttribute("previousvalue");
		//System.out.println("PiecesText:-------------"+PiecesText);
		GenericMethods.assertTwoValues(driver.findElement(By.id("pieces")).getAttribute("previousvalue"), TotPcs+"", "Validating Total Pieces in Assembly tab", ScenarioDetailsHashMap);


		//Validating Total actual Weight
		double TotActWgt=0;
		for(int ActWgt=1;ActWgt<AssemblyAllocatedTableRowCount;ActWgt++)
		{
			String CargoDetails_ActWgt= driver.findElement(By.id("dtGrossWeightStr"+ActWgt)).getText();
			double Result=TotActWgt+Double.parseDouble(CargoDetails_ActWgt);
			TotActWgt=Result;
		}
		String pattern = "##.000";
		DecimalFormat decimalFormatWgt = new DecimalFormat(pattern);
		String TotalActualWeight = decimalFormatWgt.format(TotActWgt);
		//	System.out.println("Actual TotActWgt:-----"+TotalActualWeight);
		String actualWeight =driver.findElement(By.id("actualWeight")).getAttribute("previousvalue");
		//System.out.println("actualWeight:-------------"+actualWeight);
		String TotalActualWgt[] = actualWeight.split(" ");
		//System.out.println(TotalActualWgt[0]);
		GenericMethods.assertTwoValues(TotalActualWeight, TotalActualWgt[0], "Validating Total Actual Weight in Assembly tab", ScenarioDetailsHashMap);

		//Validating Total volume
		double TotVol=0;
		for(int Vol=1;Vol<AssemblyAllocatedTableRowCount;Vol++)
		{
			String CargoDetails_Vol= driver.findElement(By.id("dtVolumeStr"+Vol)).getText();
			double Result=TotVol+Double.parseDouble(CargoDetails_Vol);
			TotVol=Result;
		}

		DecimalFormat decimalFormatVol = new DecimalFormat(pattern);
		String Totalvolume = decimalFormatVol.format(TotVol);
		//System.out.println("Actual TotVol:-----"+Totalvolume);
		String volume =driver.findElement(By.id("volume")).getAttribute("previousvalue");
		//System.out.println("volume:-------------"+volume);
		String TotalVolume[] = volume.split(" ");
		//System.out.println("Expected :"+TotalVolume[0]);
		GenericMethods.assertTwoValues(Totalvolume, TotalVolume[0], "Validating Total volume in Assembly tab", ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
	}
	//Pavan Feb 9th 2015 Below method is to enter RoutePlan Details in Route Plan Window.
	public static void HAWB_RoutePlan_Details(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
		GenericMethods.clickElement(driver, null,orAirHAWB.getElement(driver, "RoutePlanTab", ScenarioDetailsHashMap,10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
		GenericMethods.pauseExecution(3000);
		int RoutePlan_rowCount=ExcelUtils.getCellDataRowCount("Air_HawbRoutePlanDetails", ScenarioDetailsHashMap);
		System.out.println("value:::"+RoutePlan_rowCount);
		for (int RoutePlanRowNo = 1; RoutePlanRowNo <= RoutePlan_rowCount; RoutePlanRowNo++) 
		{
			int rowId =  RoutePlanRowNo+1;
			GenericMethods.clickElement(driver, By.xpath("//tr[@id='"+RoutePlanRowNo+"']/td[12]/img"),null, 2);
			WebElement lovelement = driver.findElement(By.xpath("//tr[@id='"+RoutePlanRowNo+"']/td[4]/a/img")); 
			AppReusableMethods.selectValueFromLov(driver, lovelement, orAirHAWB, "Lov_Textbox_PartyID", ExcelUtils.getCellData("Air_HawbRoutePlanDetails", RoutePlanRowNo, "Party",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(2000);
			GenericMethods.replaceTextofTextField(driver, By.xpath("//tr[@id='"+RoutePlanRowNo+"']/td[4]/input"), null, Keys.TAB, 2);
			GenericMethods.pauseExecution(2000);
			Select mode_DropdownValues = new Select(driver.findElement(By.xpath("//tr[@id='"+rowId+"']/td[5]/select")));
			mode_DropdownValues.selectByVisibleText(ExcelUtils.getCellData("Air_HawbRoutePlanDetails", RoutePlanRowNo, "Mode",ScenarioDetailsHashMap));
		}
	}


	//Below method will perform FlightScheduleMaster or FlightSchedule Maindetails based on test data if condition will be executed when FlightScheduleMaster=Yes if not else part will execute that is Flight schedule main details
	public static void HAWB_MAWB_FlightSchedule_MainDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){

		if(ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "FlightScheduleMaster",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "Lov_FlightScheduleId",ScenarioDetailsHashMap, 10), orAirHAWB,"Lov_Textbox_FlightScheduleId", ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "FlightScheduleID",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver, null,orAirHAWB.getElement(driver, "OriginBranchHbl",ScenarioDetailsHashMap, 10),2);
			GenericMethods.pauseExecution(2000);
		}

		else
		{
			if(ExcelUtils.getCellDataRowCount("Air_HAWB_MAWBMainDetails", ScenarioDetailsHashMap)>1)
			{
				GenericMethods.clickElement(driver, null,orAirHAWB.getElement(driver, "Button_FlightDetails",ScenarioDetailsHashMap, 10),2);
			}

			for (int FlightScheduleRowNo = 1; FlightScheduleRowNo <= ExcelUtils.getCellDataRowCount("Air_HAWB_MAWBMainDetails", ScenarioDetailsHashMap); FlightScheduleRowNo++) 
			{
				int GridRowID = FlightScheduleRowNo+1;
				String GridXpathPrefix ="//td[@id='MASTERDATAAREA']/fieldset[2]/table[2]/tbody/tr["+GridRowID+"]/";
				AppReusableMethods.selectValueFromLov(driver, GenericMethods.locateElement(driver, By.xpath(GridXpathPrefix+"td[1]/a/img"), 10), orAirBooking,"Zoom_FlightTo_LocationCodeTextbox",ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", FlightScheduleRowNo, "Grid_FlightTo",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
				AppReusableMethods.selectValueFromLov(driver, GenericMethods.locateElement(driver, By.xpath(GridXpathPrefix+"td[2]/a/img"), 10), orAirBooking,"Zoom_Carrier_CarrierIdTextbox",ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", FlightScheduleRowNo, "Grid_Carrier",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, By.xpath(GridXpathPrefix+"td[3]/input"), null,ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", FlightScheduleRowNo,"Grid_FlightNo", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, By.xpath(GridXpathPrefix+"td[4]/input"), null,ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", FlightScheduleRowNo,"Grid_Day", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, By.xpath(GridXpathPrefix+"td[5]/input[1]"), null,ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", FlightScheduleRowNo,"Grid_ServiceCutOffDate", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, By.xpath(GridXpathPrefix+"td[5]/input[2]"), null,ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", FlightScheduleRowNo,"Grid_ServiceCutOffTime", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, By.xpath(GridXpathPrefix+"td[6]/input[1]"), null,ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", FlightScheduleRowNo,"Grid_DepartureDate", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, By.xpath(GridXpathPrefix+"td[6]/input[2]"), null,ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", FlightScheduleRowNo,"Grid_DepartureTime", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, By.xpath(GridXpathPrefix+"td[7]/input[1]"), null,ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", FlightScheduleRowNo,"Grid_ArrivalDate", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, By.xpath(GridXpathPrefix+"td[7]/input[2]"), null,ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", FlightScheduleRowNo,"Grid_ArrivalTime", ScenarioDetailsHashMap), 2);
			}
		}

	}

	//Pavan March5th 2015 Here in Below method is to Search a record
	public static void HAWB_Search(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		GenericMethods.pauseExecution(2000);
		AppReusableMethods.selectMenu(driver,ETransMenu.HAWB,"HAWB", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "HAWBIDSearch", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "HAWB_Id", ScenarioDetailsHashMap), 2);		
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);

	}
	//Pavan Feb10th 2015 Here in Below method is to Save the HAWB
	public static void HAWB_Save(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {

		}
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}
		String hawbidSummary=GenericMethods.getInnerText(driver, null, orAirHAWB.getElement(driver, "HawbSaveDetail", ScenarioDetailsHashMap, 20), 2);
		String HAWBId=hawbidSummary.split(" : ")[2].split(",")[0].trim();
		String ShipmentReferenceNo = hawbidSummary.split(" : ")[3].split(",")[0].trim();
		String CommonReferenceNumber = hawbidSummary.split("COMMON Ref# :")[1].trim().split(",")[0].trim();
		ExcelUtils.setCellData("Air_HAWBMainDetails", RowNo, "HAWBCommonReferenceNo", CommonReferenceNumber, ScenarioDetailsHashMap);
		System.out.println("HAWBId ---"+HAWBId);
		ExcelUtils.setCellData("Air_HAWBMainDetails", RowNo, "ShipmentReferenceNo", ShipmentReferenceNo, ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(hawbidSummary.split(" : ")[1], ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "StatusMsg", ScenarioDetailsHashMap), "Validating Creation Msg", ScenarioDetailsHashMap);
		ExcelUtils.setCellData("Air_HAWBMainDetails", RowNo, "HAWB_Id", HAWBId, ScenarioDetailsHashMap);

		if(ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ShipmentType",ScenarioDetailsHashMap).equalsIgnoreCase("Direct"))
		{
			ExcelUtils.setCellData("Air_HAWB_MAWBMainDetails",RowNo, "MAWB_ID", HAWBId, ScenarioDetailsHashMap);

		}

		//Below condtion is for Updating HAWB Id into Arrival,DO,POD,DI sheets based on respective scenarios
		if(ExcelUtils.getSubScenarioRowCount("Air_HAWBMainDetails", ScenarioDetailsHashMap)>1){
			for (int i = 1; i <= ExcelUtils.getSubScenarioRowCount("Air_Arrival", ScenarioDetailsHashMap); i++) {
				ExcelUtils.setCellData_Without_RowNum("Air_Arrival", "HAWB_Id",HAWBId, ScenarioDetailsHashMap);
				if(ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ShipmentType",ScenarioDetailsHashMap).equalsIgnoreCase("Direct"))
				{
					ExcelUtils.setCellData_Without_RowNum("Air_Arrival", "MAWB_ID",HAWBId, ScenarioDetailsHashMap);
				}
			}
			for (int i = 1; i <= ExcelUtils.getSubScenarioRowCount("Air_DO", ScenarioDetailsHashMap); i++) {
				ExcelUtils.setCellData_Without_RowNum("Air_DO", "HAWB_ID",HAWBId, ScenarioDetailsHashMap);
			}
			for (int i = 1; i <= ExcelUtils.getSubScenarioRowCount("Air_POD_DO", ScenarioDetailsHashMap); i++) {
				ExcelUtils.setCellData_Without_RowNum("Air_POD_DO", "House_Id", HAWBId, ScenarioDetailsHashMap);
			}
			for (int i = 1; i <= ExcelUtils.getSubScenarioRowCount("Air_POD_DI", ScenarioDetailsHashMap); i++) {
				ExcelUtils.setCellData_Without_RowNum("Air_POD_DI", "House_Id", HAWBId, ScenarioDetailsHashMap);
			}

		}
		else{
			for (int i = 1; i <= ExcelUtils.getSubScenarioRowCount("Air_Arrival", ScenarioDetailsHashMap); i++) {
				ExcelUtils.setCellData_Without_DataSet("Air_Arrival", i, "HAWB_Id",HAWBId, ScenarioDetailsHashMap);
				if(ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "ShipmentType",ScenarioDetailsHashMap).equalsIgnoreCase("Direct"))
				{
					ExcelUtils.setCellData_Without_DataSet("Air_Arrival", i, "MAWB_ID",HAWBId, ScenarioDetailsHashMap);
				}
			}
			for (int i = 1; i <= ExcelUtils.getSubScenarioRowCount("Air_DO", ScenarioDetailsHashMap); i++) {
				ExcelUtils.setCellData_Without_DataSet("Air_DO", i, "HAWB_ID",HAWBId, ScenarioDetailsHashMap);
			}
			for (int i = 1; i <= ExcelUtils.getSubScenarioRowCount("Air_POD_DO", ScenarioDetailsHashMap); i++) {
				ExcelUtils.setCellData_Without_DataSet("Air_POD_DO", i,"House_Id", HAWBId, ScenarioDetailsHashMap);
			}
			for (int i = 1; i <= ExcelUtils.getSubScenarioRowCount("Air_POD_DI", ScenarioDetailsHashMap); i++) {
				ExcelUtils.setCellData_Without_DataSet("Air_POD_DI", i,"House_Id", HAWBId, ScenarioDetailsHashMap);
			}

		}
		/*//Updating Hawb Id into Air_MAWB sheet
		for (int i = 1; i <= ; i++) {

		}*/

		if(ExcelUtils.getSubScenarioRowCount("Air_MAWB", ScenarioDetailsHashMap)== 2 && ScenarioDetailsHashMap.get("DataSetNo").equals("2")  ){
			ExcelUtils.setCellData_Without_DataSet("Air_MAWB",2, "HAWBID", HAWBId, ScenarioDetailsHashMap);
		}
		else{
			for (int i = 1; i <= ExcelUtils.getSubScenarioRowCount("Air_MAWB", ScenarioDetailsHashMap); i++) {
				ExcelUtils.setCellData_Without_DataSet("Air_MAWB",i, "HAWBID", HAWBId, ScenarioDetailsHashMap);
			}
		}
		
		
		

		GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "HAWBIDSearch", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "HAWB_Id", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.assertInnerText(driver, null, orAirHAWB.getElement(driver, "GridHAWBId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "HAWB_Id", ScenarioDetailsHashMap), "GridHAWBId", "Comparing hawb id",  ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(5000);

	}

	//Pavan Feb10th 2015 Here in Below Condition if eAWB_Required=Yes only AFR module will be selected.
	//Based on the conditions which are mentioned in Automation Scenario Matrix for HAWB module eAWB is set to Yes in Test Data. And need to give the same if wants to executes eAWB module
	public static void HAWB_eAWB_Module(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		boolean eAWB_Status=false;
		eAWB_Status=GenericMethods.isChecked(orAirHAWB.getElement(driver, "CHB_eAWB", ScenarioDetailsHashMap, 10));
		System.out.println("eAWB::"+eAWB_Status);
		if(!eAWB_Status){
			GenericMethods.clickElement(driver, null,orAirHAWB.getElement(driver, "CHB_eAWB", ScenarioDetailsHashMap,10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
			}
		}
	}

	//Pavan Feb10th 2015 Below method is to Select MawbId Reference selection if MawbID_ReferenceSelectionRequired=Yes in test data
	public static void HAWB_MAWB_Id_Reference_Selection(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		//			AppReusableMethods.selectValueFromLov(driver, orAirHAWB.getElement(driver, "Lov_MawbID",ScenarioDetailsHashMap, 10), orAirHAWB,"Lov_Textbox_MawbID", ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "MawbID_ReferenceSelection",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "Lov_MawbID",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectWindow(driver);
		GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "Lov_Textbox_MawbID",ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "MawbID_ReferenceSelection",ScenarioDetailsHashMap), 10);
		GenericMethods.pauseExecution(2000);
		if (orCommon.getElements(driver, "SearchButton",ScenarioDetailsHashMap,10).size() > 0) {
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 2);
		} else{
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton2", ScenarioDetailsHashMap,10), 2);
		}
		GenericMethods.pauseExecution(8000);
		if (orCommon.getElements(driver, "SaveButton",ScenarioDetailsHashMap,10).size() > 0) {
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 2);
		} else{
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton2",ScenarioDetailsHashMap, 10), 2);
		}
		try{
			GenericMethods.handleAlert(driver, "accept");

		}catch (Exception e) {
		}
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);

	}

	/*//Pavan:March 05th 2015,Below method is to Close the Master for Houses B2B and Direct shipment.
	public static void HAWB_MasterClose(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.pauseExecution(1500);
		GenericMethods.clickElement(driver, null,orAirHAWB.getElement(driver, "MawbCloseBtn", ScenarioDetailsHashMap,10), 2);
		String hawbidSummary=GenericMethods.getInnerText(driver, null, orAirHAWB.getElement(driver, "HawbSaveDetail", ScenarioDetailsHashMap, 20), 2);
		String HawbId_Summary=hawbidSummary.split(" : ")[1].trim();
		System.out.println("HblId_Summary:::"+HawbId_Summary+":::testdata::::"+ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "MasterClose_Msg", ScenarioDetailsHashMap));
		GenericMethods.assertTwoValues(HawbId_Summary,ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "MasterClose_Msg", ScenarioDetailsHashMap)+ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "HAWB_Id", ScenarioDetailsHashMap), "Validating Master Close", ScenarioDetailsHashMap);
	}
	 */

	public static void Mawb_CloseFor_B2B_Direct(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{

		AppReusableMethods.selectMenu(driver,ETransMenu.HAWB,"HAWB", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirMawb.getElement(driver, "HAWBIDSearch", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "HAWB_Id", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.assertInnerText(driver, null, orAirMawb.getElement(driver, "GridHAWBId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "HAWB_Id", ScenarioDetailsHashMap), "GridHAWBId", "Comparing hawb id",  ScenarioDetailsHashMap);
		String XL_MAWB_ID = ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", rowNo, "MAWB_ID", ScenarioDetailsHashMap);
		String MAWBID_Grid=GenericMethods.getInnerText(driver, null, orAirMawb.getElement(driver, "Grid_MAWBId", ScenarioDetailsHashMap, 5), 5);
		GenericMethods.pauseExecution(4000);
		if(MAWBID_Grid.equalsIgnoreCase(XL_MAWB_ID))
		{
			System.out.println("ind iffff");
			GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "Mawb_COBTab", ScenarioDetailsHashMap, 5) , 10);
			try{
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {
				System.out.println("no Alerts present");
			}
			/*int Cob_RowCount=driver.findElements(By.xpath("//legend[text()='Shipment Summary']/ancestor::fieldset/table[3]//tr")).size();
			System.out.println("Cob_RowCount::"+Cob_RowCount);*/
			int Cob_CheckBox_RowCount = 3;
			for (int RowCount = 1; RowCount <= ExcelUtils.getCellDataRowCount("Air_HAWB_MAWBMainDetails", ScenarioDetailsHashMap) ;RowCount++) {
				
				GenericMethods.pauseExecution(2000);
				GenericMethods.clickElement(driver, By.xpath("//legend[text()='Shipment Summary']/ancestor::fieldset/table[3]//tr["+Cob_CheckBox_RowCount+"]/td[9]/input"), null , 10);
				if(ExcelUtils.getCellDataRowCount("Air_HAWB_MAWBMainDetails", ScenarioDetailsHashMap)>1){
					Cob_CheckBox_RowCount++;
				}
				else{
					break;	
				}

			}
			int Mawb_RowCount=driver.findElements(By.xpath("//table[@id='cobMasterDtls']/tbody/tr")).size();
			System.out.println("Mawb_RowCount::"+Mawb_RowCount);
			for (int Mawb_CheckBox_RowCount = 1; Mawb_CheckBox_RowCount <= Mawb_RowCount; Mawb_CheckBox_RowCount++) {
				GenericMethods.clickElement(driver, By.xpath("//table[@id='cobMasterDtls']/tbody/tr["+Mawb_CheckBox_RowCount+"]/td[7]/input"), null , 10);	
			}

			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 5) , 10);
			GenericMethods.pauseExecution(4000);
			String ConfirmOnBoardSummary=GenericMethods.getInnerText(driver, null, orAirMawb.getElement(driver, "MAWB_Verification_MsG", ScenarioDetailsHashMap, 20), 2);
			String[] COB = ConfirmOnBoardSummary.split(" :");
			String COBXL = ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", rowNo, "MAWBCOBMSG_B2B", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(COB[0], COBXL, "Validating Confirm On Board success message", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(COB[1], ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", rowNo, "MAWB_ID", ScenarioDetailsHashMap), "Validating HAWB ID in Confirm On Board success message ",ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(5000);
		}
		else{
			GenericMethods.assertTwoValues(XL_MAWB_ID,MAWBID_Grid, "MAWB_ID in MAWBID_Grid is not found", ScenarioDetailsHashMap);
		}
		GenericMethods.pauseExecution(3000);
		GenericMethods.replaceTextofTextField(driver, null, orAirMawb.getElement(driver, "HAWBIDSearch", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "HAWB_Id", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(4000);

		if(MAWBID_Grid.equalsIgnoreCase(XL_MAWB_ID))
		{
			GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "MawbCloseBtn", ScenarioDetailsHashMap, 5) , 5);
		}
		GenericMethods.pauseExecution(4000);
		String MAWBCloseSummary=GenericMethods.getInnerText(driver, null, orAirMawb.getElement(driver, "MAWB_Verification_MsG", ScenarioDetailsHashMap, 20), 2);
		String[] CloseSum = MAWBCloseSummary.split(": ");
		String CloseSumXL = ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", rowNo, "MAWBClosedSuccessMSG_B2B", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(CloseSum[1],CloseSumXL , "Validating MAWB close success message", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(CloseSum[2], ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", rowNo, "MAWB_ID", ScenarioDetailsHashMap), "Validating MAWB ID in MAWB close success message ",ScenarioDetailsHashMap);
	}
	// Sangeetha-Masterclose code


	public static void Air_HAWB_Close(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.HAWB,"HAWB", ScenarioDetailsHashMap);
		String HAWB_ID = ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "HAWB_Id", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "HAWBIDSearch", ScenarioDetailsHashMap, 5),HAWB_ID, 5);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(4000);
		String HAWBID_Grid=GenericMethods.getInnerText(driver, null, orAirHAWB.getElement(driver, "GridHAWBId", ScenarioDetailsHashMap, 5), 5);
		GenericMethods.pauseExecution(3000);
		if(HAWBID_Grid.equalsIgnoreCase(HAWB_ID))
		{
			//GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "MawbId_CheckBox", ScenarioDetailsHashMap, 5) , 5);
			try{
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {
				System.out.println("no Alerts present");
			}
			//GenericMethods.selectOptionFromDropDown(driver, null,orAirMawb.getElement(driver, "Mawb_ProvisionalClose", ScenarioDetailsHashMap, 5) ,"Close Master");
			GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "MawbCloseBtn", ScenarioDetailsHashMap, 5) , 5);
			GenericMethods.pauseExecution(4000);
			try{
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {
			}
			GenericMethods.pauseExecution(8000);
			String MAWBClosedSummary=GenericMethods.getInnerText(driver, null, orAirHAWB.getElement(driver, "MasterClose_VerificationMsG", ScenarioDetailsHashMap, 20), 2);
			String[] MAWBClosed = MAWBClosedSummary.split(": ");
			System.out.println(MAWBClosed[0]);
			System.out.println(MAWBClosed[1]);
			GenericMethods.assertTwoValues(MAWBClosed[1], ExcelUtils.getCellData("Air_HAWB_COB", rowNo, "MasterClose_Validation_MSG", ScenarioDetailsHashMap), "Validating MAWB Closed success message", ScenarioDetailsHashMap);
		}
		else{
			GenericMethods.assertTwoValues(HAWB_ID,HAWBID_Grid, "Searching MAWB ID in MAWB Grid is not found", ScenarioDetailsHashMap);
		}
	}


	public static void Air_HAWBCOB(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) {
		AppReusableMethods.selectMenu(driver, ETransMenu.HAWB,"HAWB", ScenarioDetailsHashMap);
		String HAWB_ID = ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "HAWB_Id", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "HAWBIDSearch", ScenarioDetailsHashMap, 5),HAWB_ID, 5);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(4000);
		String HAWBID_Grid=GenericMethods.getInnerText(driver, null, orAirHAWB.getElement(driver, "GridHAWBId", ScenarioDetailsHashMap, 5), 5);
		GenericMethods.pauseExecution(3000);
		if(HAWBID_Grid.equalsIgnoreCase(HAWB_ID))
		{
			try{
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {
				System.out.println("no Alerts present");
			}
			GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "COB_Tab", ScenarioDetailsHashMap, 5) , 10);
			try{
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {
				System.out.println("no Alerts present");
			}
			if(ExcelUtils.getCellData("Air_HAWB_COB", rowNo, "RevisedScheduleRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes")){

				for (int i = 1; i <= ExcelUtils.getCellDataRowCount("Air_HAWB_COB", ScenarioDetailsHashMap); i++) {

					AppReusableMethods.selectValueFromLov(driver, GenericMethods.locateElement(driver, By.id("flight"+i+"ToImage"), 10), orAirMawb, "Location_SearchCode", ExcelUtils.getCellData("Air_HAWB_COB", i, "FlightTo", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					AppReusableMethods.selectValueFromLov(driver, GenericMethods.locateElement(driver, By.id("flight"+i+"CarrierIdImage"), 10), orAirMawb, "CarrierId", ExcelUtils.getCellData("Air_HAWB_COB", i, "Carrier", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					GenericMethods.replaceTextofTextField(driver, By.id("flight"+i+"No"), null, ExcelUtils.getCellData("Air_HAWB_COB", i, "FlightNo", ScenarioDetailsHashMap), 10);
					GenericMethods.replaceTextofTextField(driver, By.id("flight"+i+"ServiceCutOffDate"), null, ExcelUtils.getCellData("Air_HAWB_COB", i, "ServiceCutoffDate", ScenarioDetailsHashMap), 10);
					GenericMethods.replaceTextofTextField(driver, By.id("flight"+i+"ServiceCutOffTime"), null, ExcelUtils.getCellData("Air_HAWB_COB", i, "ServiceCutoffTime", ScenarioDetailsHashMap), 10);
					GenericMethods.replaceTextofTextField(driver, By.id("flight"+i+"DepartureDate"), null, ExcelUtils.getCellData("Air_HAWB_COB", i, "DepartureDate", ScenarioDetailsHashMap), 10);
					GenericMethods.replaceTextofTextField(driver, By.id("flight"+i+"DepartureTime"), null, ExcelUtils.getCellData("Air_HAWB_COB", i, "DepartureTime", ScenarioDetailsHashMap), 10);
					GenericMethods.replaceTextofTextField(driver, By.id("flight"+i+"ArrivalDate"), null, ExcelUtils.getCellData("Air_HAWB_COB", i, "ArrivalDate", ScenarioDetailsHashMap), 10);
					GenericMethods.replaceTextofTextField(driver, By.id("flight"+i+"ArrivalTime"), null, ExcelUtils.getCellData("Air_HAWB_COB", i, "ArrivalTime", ScenarioDetailsHashMap), 10);

				}

			}
			for (int i = 1; i <= ExcelUtils.getCellDataRowCount("Air_HAWB_FilghtDetails", ScenarioDetailsHashMap); i++) {
				GenericMethods.clickElement(driver, By.id("flight"+i+"CobSelected"), null , 10);
			}
			for(int MAWBCOBGridRowNo=1;MAWBCOBGridRowNo<=driver.findElements(By.xpath("html/body/form/table/tbody/tr[4]/td/div/table/tbody/tr")).size();MAWBCOBGridRowNo++)
			{
				System.out.println("Excel value:"+ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "HAWB_Id", ScenarioDetailsHashMap));
				System.out.println("Grid Row count:"+driver.findElements(By.xpath("html/body/form/table/tbody/tr[4]/td/div/table/tbody/tr")).size());
				System.out.println("MAWBCOBGridRowNo:"+MAWBCOBGridRowNo);
				/*String MAWB_IDGrid = driver.findElement(By.id("dtMasterDocId"+MAWBCOBGridRowNo)).getText();
				System.out.println("MAWB_IDGrid:"+MAWB_IDGrid);
				if(ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "HAWB_Id", ScenarioDetailsHashMap).equalsIgnoreCase(MAWB_IDGrid))
				{
					GenericMethods.clickElement(driver, By.id("multiSelectCheckbox"+MAWBCOBGridRowNo),null , 10);
				}*/

				GenericMethods.clickElement(driver, By.id("multiSelectCheckbox"+MAWBCOBGridRowNo),null , 10);
			}
			//GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "MAWBCOBCheckbox", ScenarioDetailsHashMap, 5) , 10);
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 5) , 10);
			GenericMethods.pauseExecution(8000);
			String ConfirmOnBoardSummary=GenericMethods.getInnerText(driver, null, orAirHAWB.getElement(driver, "MAWBCOB_VerificationMsG", ScenarioDetailsHashMap, 20), 2);
			String[] COB = ConfirmOnBoardSummary.split(" :");
			System.out.println(COB[0]);
			System.out.println(COB[1]);
			GenericMethods.assertTwoValues(COB[0], ExcelUtils.getCellData("Air_HAWB_COB", rowNo, "ConfirmOnBoard_Validation_MSG", ScenarioDetailsHashMap), "Validating Confirm On Board success message", ScenarioDetailsHashMap);
		}
		else{
			GenericMethods.assertTwoValues(HAWB_ID,HAWBID_Grid, "Searching HAWB ID in HAWB Grid is not found", ScenarioDetailsHashMap);
		}
	}

	public static void Air_DocCharges(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) throws AWTException {
		GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "Tab_DocCharges", ScenarioDetailsHashMap, 5) , 10);
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


		//      GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "Tab_DocCharges", ScenarioDetailsHashMap, 5) , 10);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}
		GenericMethods.clickElement(driver, By.xpath("//tr[@id='CHARGES_DATA_TABLE']/td/div/table/tbody//tr[1]/td[2]"), null , 10);
		int grid_RowCount=driver.findElements(By.xpath("//tr[@id='CHARGES_DATA_TABLE']/td/div/table/tbody//tr")).size();
		System.out.println("grid_RowCount:::"+grid_RowCount);
		String ChargeCode="";
		for (int i = 1; i <= grid_RowCount; i++) {
			GenericMethods.clickElement(driver, By.xpath("//tr[@id='CHARGES_DATA_TABLE']/td/div/table/tbody//tr["+i+"]/td[2]"), null , 10);
			ChargeCode=GenericMethods.getInnerText(driver, By.xpath("//tr[@id='CHARGES_DATA_TABLE']/td/div/table/tbody//tr["+i+"]/td[2]"), null, 10);
			if(ChargeCode.equalsIgnoreCase(ExcelUtils.getCellData("Air_DocCharges", rowNo, "Iata_ChargeCode", ScenarioDetailsHashMap))){
				GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "Textbox_IataChargeBasis", ScenarioDetailsHashMap, 5), ExcelUtils.getCellData("Air_DocCharges", rowNo, "Iata_ChargeBasis", ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "Textbox_IataMinAmount", ScenarioDetailsHashMap, 5), ExcelUtils.getCellData("Air_DocCharges", rowNo, "Iata_MinAmount", ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "Textbox_IataRate", ScenarioDetailsHashMap, 5), ExcelUtils.getCellData("Air_DocCharges", rowNo, "Iata_Amount", ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "Textbox_IataCurrency", ScenarioDetailsHashMap, 5), ExcelUtils.getCellData("Air_DocCharges", rowNo, "Iata_Currency", ScenarioDetailsHashMap), 10);
				GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "Button_GridEdit", ScenarioDetailsHashMap, 5) , 10);
				break;
			}
		}

		//      GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "Tab_eAWB_EDI_Interface", ScenarioDetailsHashMap, 5) , 10);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}

	}

	
	public static void Air_HAWB_DocCharges(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) throws AWTException {
		GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "Tab_DocCharges", ScenarioDetailsHashMap, 5) , 10);
		GenericMethods.pauseExecution(9000);
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
		for(int GridRow=1;GridRow<=ExcelUtils.getCellDataRowCount("Air_DocCharges", ScenarioDetailsHashMap);GridRow++)
		{
			GenericMethods.clickElement(driver, By.xpath("html/body/form/table/tbody/tr[8]/td/div/table/tbody/tr["+GridRow+"]"), null, 10);
			if(ExcelUtils.getCellData("Air_DocCharges", rowNo, "ConsolidationORB2B", ScenarioDetailsHashMap).equalsIgnoreCase("B2B"))
			{
				//Buy Validation
				System.out.println("Buy Validation");
				System.out.println("Sell Validation");
				GenericMethods.clickElement(driver, By.id("buyRateStr"), null, 10);
				Robot robot = new Robot();
				try {
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_A);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					robot.keyRelease(KeyEvent.VK_A);
					GenericMethods.pauseExecution(1000);
					robot.keyPress(KeyEvent.VK_BACK_SPACE);
					robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				} catch (Exception e) {
				}
				GenericMethods.pauseExecution(2000);
				driver.findElement(By.id("buyRateStr")).sendKeys(ExcelUtils.getCellData("Air_DocCharges", rowNo, "BuyRate", ScenarioDetailsHashMap));
				GenericMethods.action_Key(driver, Keys.TAB);
				try{
					GenericMethods.handleAlert(driver, "accept");
					System.out.println("alert found");
				}catch (Exception e) {
					System.out.println("alert not found");
					e.printStackTrace();
				}
				GenericMethods.pauseExecution(2000);
				try {
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_A);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					robot.keyRelease(KeyEvent.VK_A);
					GenericMethods.pauseExecution(1000);
					robot.keyPress(KeyEvent.VK_BACK_SPACE);
					robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				} catch (Exception e) {
				}
				GenericMethods.action_Key(driver, Keys.TAB);
				driver.findElement(By.id("buyCurrency")).sendKeys(ExcelUtils.getCellData("Air_DocCharges", rowNo, "BuyCurrency", ScenarioDetailsHashMap));
				GenericMethods.action_Key(driver, Keys.TAB);
				
				//Sell Validation
				System.out.println("Sell Validation");
				GenericMethods.clickElement(driver, By.id("sellRateStr"), null, 10);
				//Robot robot = new Robot();
				try {
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_A);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					robot.keyRelease(KeyEvent.VK_A);
					GenericMethods.pauseExecution(1000);
					robot.keyPress(KeyEvent.VK_BACK_SPACE);
					robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				} catch (Exception e) {
				}
				GenericMethods.pauseExecution(2000);
				//driver.findElement(By.id("sellRateStr")).sendKeys("150");
				driver.findElement(By.id("sellRateStr")).sendKeys(ExcelUtils.getCellData("Air_DocCharges", rowNo, "BuyRate", ScenarioDetailsHashMap));
				
				GenericMethods.action_Key(driver, Keys.TAB);
				try{
					GenericMethods.handleAlert(driver, "accept");
					System.out.println("alert found");
				}catch (Exception e) {
					System.out.println("alert not found");
					e.printStackTrace();
				}
				GenericMethods.pauseExecution(2000);
				try {
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_A);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					robot.keyRelease(KeyEvent.VK_A);
					GenericMethods.pauseExecution(1000);
					robot.keyPress(KeyEvent.VK_BACK_SPACE);
					robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				} catch (Exception e) {
				}
				GenericMethods.action_Key(driver, Keys.TAB);
				//driver.findElement(By.id("sellCurrency")).sendKeys("USD");
				driver.findElement(By.id("sellCurrency")).sendKeys(ExcelUtils.getCellData("Air_DocCharges", rowNo, "BuyCurrency", ScenarioDetailsHashMap));
				GenericMethods.action_Key(driver, Keys.TAB);
				GenericMethods.clickElement(driver, By.id("button.gridEditBtn"), null, 10);
	
			}
			else
			{
				//Sell Validation
				System.out.println("Sell Validation");
				Robot robot = new Robot();
				try {
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					GenericMethods.pauseExecution(5000);
				} catch (Exception e) {
				}
				GenericMethods.clickElement(driver, By.id("sellRateStr"), null, 10);
				
				try {
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_A);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					robot.keyRelease(KeyEvent.VK_A);
					GenericMethods.pauseExecution(1000);
					robot.keyPress(KeyEvent.VK_BACK_SPACE);
					robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				} catch (Exception e) {
				}
				GenericMethods.pauseExecution(2000);
				//driver.findElement(By.id("sellRateStr")).sendKeys("150");
				driver.findElement(By.id("sellRateStr")).sendKeys(ExcelUtils.getCellData("Air_DocCharges", rowNo, "BuyRate", ScenarioDetailsHashMap));
				
				GenericMethods.action_Key(driver, Keys.TAB);
				try{
					GenericMethods.handleAlert(driver, "accept");
					System.out.println("alert found");
				}catch (Exception e) {
					System.out.println("alert not found");
					e.printStackTrace();
				}
				GenericMethods.pauseExecution(2000);
				try {
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_A);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					robot.keyRelease(KeyEvent.VK_A);
					GenericMethods.pauseExecution(1000);
					robot.keyPress(KeyEvent.VK_BACK_SPACE);
					robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				} catch (Exception e) {
				}
				GenericMethods.action_Key(driver, Keys.TAB);
				//driver.findElement(By.id("sellCurrency")).sendKeys("USD");
				driver.findElement(By.id("sellCurrency")).sendKeys(ExcelUtils.getCellData("Air_DocCharges", rowNo, "BuyCurrency", ScenarioDetailsHashMap));
				GenericMethods.action_Key(driver, Keys.TAB);
				GenericMethods.clickElement(driver, By.id("button.gridEditBtn"), null, 10);
		
			}
		}
		GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "HouseAdditionalInfo_Img", ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(2000);
		GenericMethods.clearText(driver, null,orAirHAWB.getElement(driver, "HouseAdditionalInfo", ScenarioDetailsHashMap, 10), 9);
		GenericMethods.replaceTextofTextField(driver, null,orAirHAWB.getElement(driver, "NatureAndQuantity", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "NatureAndQuantity", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "HouseAdditionalInfo_Img", ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(2000);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 10);
		

		//      GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "Tab_eAWB_EDI_Interface", ScenarioDetailsHashMap, 5) , 10);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}

	}



}
