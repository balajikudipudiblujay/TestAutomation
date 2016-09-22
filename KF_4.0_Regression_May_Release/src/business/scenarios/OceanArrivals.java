package business.scenarios;

import global.reusables.ExcelUtils;
import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.api.robot.Key;

import app.reuseables.AppReusableMethods;
import app.reuseables.ETransMenu;

public class OceanArrivals {
	static ObjectRepository orCommon = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/CommonObjects.xml");
	static ObjectRepository orOceanArrival = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/OceanArrivals.xml");
	public static void arrivalConfirmation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{

		int ArrivalRowCount = ExcelUtils.getCellDataRowCount("SI_Arrival", ScenarioDetailsHashMap);
		for(int RowCount=1;RowCount<=ArrivalRowCount;RowCount++)
		{
			GenericMethods.pauseExecution(2000);
			AppReusableMethods.selectMenu(driver, ETransMenu.oceanArraival,"Arrival Confirmation", ScenarioDetailsHashMap);
			System.out.println("RowCount running :"+RowCount);
			searchmbl(driver, ScenarioDetailsHashMap, RowCount);
			arrival(driver, ScenarioDetailsHashMap, RowCount);
			//mblCharges(driver, ScenarioDetailsHashMap, RowCount);
			arrivalNotice(driver, ScenarioDetailsHashMap, RowCount);
			notesAndStatus(driver, ScenarioDetailsHashMap, RowCount);
			if(ExcelUtils.getCellData("SI_Arrival", RowCount, "DeliveryInstructionRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				deliveryInstructions(driver, ScenarioDetailsHashMap, RowCount);
			}
			GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(3000);
			if(ExcelUtils.getCellData("SI_Arrival", RowCount, "DeliveryInstructionRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				String DeliveryInstructionSummary = GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "DeliveryInstructionMSG", ScenarioDetailsHashMap, 2), 2);
				System.out.println("DeliveryInstructionSummary :"+DeliveryInstructionSummary.split(": ")[0]);
				GenericMethods.assertTwoValues(DeliveryInstructionSummary.split(": ")[1].trim(), ExcelUtils.getCellData("SI_Arrival", RowNo, "DeliveryInstruction_MSG", ScenarioDetailsHashMap), "Verifying Delivery Instruction message", ScenarioDetailsHashMap);
			}
			else
			{
				if(ExcelUtils.getCellData("SI_Arrival", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("LCL")||ExcelUtils.getCellData("SI_Arrival", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("FCL"))
				{
					String ArrivalconfirmationSummary = GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "ArrivalConfirmation_MSG", ScenarioDetailsHashMap, 2), 2);
					System.out.println("ArrivalconfirmationSummary :"+ArrivalconfirmationSummary.split("#")[0]);
					GenericMethods.assertTwoValues(ArrivalconfirmationSummary.split("#")[0], ExcelUtils.getCellData("SI_Arrival", RowNo, "Arrivalconfirmation_Container_MSG", ScenarioDetailsHashMap), "Verifying Arrivalconfirmation message for LCL or FCL shipment", ScenarioDetailsHashMap);
				}
				else
				{
					String ArrivalconfirmationSummary = GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "ArrivalConfirmation_MSG", ScenarioDetailsHashMap, 2), 2);
					System.out.println("ArrivalconfirmationSummary :"+ArrivalconfirmationSummary.split("#")[0]);
					GenericMethods.assertTwoValues(ArrivalconfirmationSummary.split("#")[0], ExcelUtils.getCellData("SI_Arrival", RowNo, "Arrivalconfirmation_MSG", ScenarioDetailsHashMap), "Verifying Arrivalconfirmation message  for RORO or Breakbulk shipment", ScenarioDetailsHashMap);
				}

			}
			System.out.println("Hold Reason Required :"+ExcelUtils.getCellData("SI_Arrival", RowCount, "HoldReason", ScenarioDetailsHashMap));
			if(ExcelUtils.getCellData("SI_Arrival", RowCount, "HoldReason", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				releaseHouseDestination(driver, ScenarioDetailsHashMap, RowCount);
			}
		}

	}
	public static void searchArrival(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo)
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.oceanArraival,"Arrival Confirmation", ScenarioDetailsHashMap);
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap).equalsIgnoreCase("Direct"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "MblId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_Arrival", RowNo,"HBL_ID", ScenarioDetailsHashMap), 2);
		}
		else{
			GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "MblId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_Arrival", RowNo,"MBLId", ScenarioDetailsHashMap), 2);
		}
		//		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "MblId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_Arrival", RowNo,"MBLId", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "SearchMBLDateFrom", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SI_Arrival", RowNo, "ArrivalDateFrom", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "SearchMBLDateTo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SI_Arrival", RowNo, "ArrivalDateTo", ScenarioDetailsHashMap), 10);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.clickElement(driver, null,orOceanArrival.getElement(driver, "MblIdSelectCheckbox1", ScenarioDetailsHashMap, 10), 2);
	}
	public static void searchmbl(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo)
	{
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap).equalsIgnoreCase("Direct"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "MblId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_Arrival", RowNo,"HBL_ID", ScenarioDetailsHashMap), 2);
		}
		else{
			GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "MblId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_Arrival", RowNo,"MBLId", ScenarioDetailsHashMap), 2);
		}
		//		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "MblId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_Arrival", RowNo,"MBLId", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "SearchMBLDateFrom", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SI_Arrival", RowNo, "ArrivalDateFrom", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "SearchMBLDateTo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SI_Arrival", RowNo, "ArrivalDateTo", ScenarioDetailsHashMap), 10);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.clickElement(driver, null,orOceanArrival.getElement(driver, "MblIdSelectCheckbox1", ScenarioDetailsHashMap, 10), 2);
	}
	public static void arrival(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		GenericMethods.clickElement(driver, null,orOceanArrival.getElement(driver, "Tab_Arrival", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		System.out.println("HoldReaxon -- "+ExcelUtils.getCellData("SI_Arrival", RowNo, "HoldReason", ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "ATADate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_Arrival", RowNo,"ATADate", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "ATATime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_Arrival", RowNo,"ATATime", ScenarioDetailsHashMap), 2);
		//System.out.println("HouseDeconsolidationStatus :"+driver.findElement(By.name("houseDeconStatus")).getText());
		String ShipmentType =  ExcelUtils.getCellData("SI_Arrival", RowNo, "ShipmentType", ScenarioDetailsHashMap);
		if(ShipmentType.equalsIgnoreCase("Partial"))
		{
			if(driver.findElement(By.id("partShipment")).getAttribute("value").equalsIgnoreCase("N"))
			{
				GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "PartShipment", ScenarioDetailsHashMap, 10), 2);
			}
			String LoadType =  ExcelUtils.getCellData("SI_Arrival", RowNo, "LoadType", ScenarioDetailsHashMap);
			System.out.println("LoadType :"+LoadType);
			if(LoadType.equalsIgnoreCase("LCL"))
			{
				System.out.println("Hod Reason =="+ExcelUtils.getCellData("SI_Arrival",RowNo, "HoldReason", ScenarioDetailsHashMap));
				if(ExcelUtils.getCellData("SI_Arrival", RowNo, "HoldReason", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
				{
					if(driver.findElement(By.id("isHouseHold")).getAttribute("value").equalsIgnoreCase("N"))
					{
						GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "HouseHoldCheckBox", ScenarioDetailsHashMap, 10), 2);
						AppReusableMethods.selectValueFromLov(driver,  orOceanArrival.getElement(driver, "ReasonCodeLOV", ScenarioDetailsHashMap, 2), orOceanArrival, "Reason_Code",ExcelUtils.getCellData("SI_Arrival", RowNo, "ReasonCode", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
						GenericMethods.pauseExecution(2000);
					}
				}	

				GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "CurrentPieces", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_ArrivalContainers", RowNo, "Current_Pieces", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "CurrentWeight", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_ArrivalContainers", RowNo, "Current_Weight", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "CurrentVolume", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_ArrivalContainers", RowNo, "Current_Volume", ScenarioDetailsHashMap), 2);
				GenericMethods.pauseExecution(2000);
				GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "GridEditButton", ScenarioDetailsHashMap, 2), 10);
				GenericMethods.pauseExecution(2000);
			}
			else if(LoadType.equalsIgnoreCase("FCL"))
			{
				for(int datasetNo=1;datasetNo<=ExcelUtils.getCellDataRowCount("SI_ArrivalContainers", ScenarioDetailsHashMap);datasetNo++)
				{
					GenericMethods.clickElement(driver, By.xpath("html/body/form/table/tbody/tr[5]/td/div/table/tbody/tr["+datasetNo+"]"), null, 2);
					System.out.println("Hod Reason =="+ExcelUtils.getCellData("SI_Arrival",RowNo, "HoldReason", ScenarioDetailsHashMap));
					if(ExcelUtils.getCellData("SI_Arrival",RowNo, "HoldReason", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
					{
						if(driver.findElement(By.id("isHouseHold")).getAttribute("value").equalsIgnoreCase("N"))
						{
							GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "HouseHoldCheckBox", ScenarioDetailsHashMap, 10), 2);
							AppReusableMethods.selectValueFromLov(driver,  orOceanArrival.getElement(driver, "ReasonCodeLOV", ScenarioDetailsHashMap, 2), orOceanArrival, "Reason_Code",ExcelUtils.getCellData("SI_Arrival", RowNo, "ReasonCode", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
							GenericMethods.pauseExecution(2000);
						}
					}	


					if(driver.findElement(By.id("partShipment")).getAttribute("value").equalsIgnoreCase("N"))
					{
						GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "PartShipment", ScenarioDetailsHashMap, 10), 2);
						try{
							GenericMethods.handleAlert(driver, "accept");
						}catch (Exception e) {
							//System.out.println("no Alerts present");
						}
					}
					GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "CurrentPieces", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_ArrivalContainers", datasetNo, "Current_Pieces", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "CurrentWeight", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_ArrivalContainers", datasetNo, "Current_Weight", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "CurrentVolume", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_ArrivalContainers", datasetNo, "Current_Volume", ScenarioDetailsHashMap), 2);
					GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "GridEditButton", ScenarioDetailsHashMap, 2), 10);
					GenericMethods.pauseExecution(3000);
					GenericMethods.clickElement(driver, By.xpath("html/body/form/table/tbody/tr[5]/td/div/table/tbody/tr["+datasetNo+"]"), null, 2);
				}

			}
			else if(LoadType.equalsIgnoreCase("RORO"))
			{
				System.out.println("Hod Reason =="+ExcelUtils.getCellData("SI_Arrival",RowNo, "HoldReason", ScenarioDetailsHashMap));
				if(ExcelUtils.getCellData("SI_Arrival", RowNo, "HoldReason", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
				{
					if(driver.findElement(By.id("isHouseHold")).getAttribute("value").equalsIgnoreCase("N"))
					{
						GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "HouseHoldCheckBox", ScenarioDetailsHashMap, 10), 2);
						AppReusableMethods.selectValueFromLov(driver,  orOceanArrival.getElement(driver, "ReasonCodeLOV", ScenarioDetailsHashMap, 2), orOceanArrival, "Reason_Code",ExcelUtils.getCellData("SI_Arrival", RowNo, "ReasonCode", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
						GenericMethods.pauseExecution(2000);
					}
				}	

				GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "CurrentPieces", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_ArrivalContainers", RowNo, "Current_Pieces", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "CurrentWeight", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_ArrivalContainers", RowNo, "Current_Weight", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "CurrentVolume", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_ArrivalContainers", RowNo, "Current_Volume", ScenarioDetailsHashMap), 2);
				GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "GridEditButton", ScenarioDetailsHashMap, 2), 10);
			}
			else
			{
				System.out.println("Hod Reason =="+ExcelUtils.getCellData("SI_Arrival",RowNo, "HoldReason", ScenarioDetailsHashMap));
				if(ExcelUtils.getCellData("SI_Arrival", RowNo, "HoldReason", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
				{
					if(driver.findElement(By.id("isHouseHold")).getAttribute("value").equalsIgnoreCase("N"))
					{
						GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "HouseHoldCheckBox", ScenarioDetailsHashMap, 10), 2);
						AppReusableMethods.selectValueFromLov(driver,  orOceanArrival.getElement(driver, "ReasonCodeLOV", ScenarioDetailsHashMap, 2), orOceanArrival, "Reason_Code",ExcelUtils.getCellData("SI_Arrival", RowNo, "ReasonCode", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
						GenericMethods.pauseExecution(2000);
					}
				}	

				GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "CurrentPieces", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_ArrivalContainers", RowNo, "Current_Pieces", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "CurrentWeight", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_ArrivalContainers", RowNo, "Current_Weight", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "CurrentVolume", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_ArrivalContainers", RowNo, "Current_Volume", ScenarioDetailsHashMap), 2);
				GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "GridEditButton", ScenarioDetailsHashMap, 2), 10);
			}
		}
		else
		{
			System.out.println("Hod Reason =="+ExcelUtils.getCellData("SI_Arrival",RowNo, "HoldReason", ScenarioDetailsHashMap));
			if(ExcelUtils.getCellData("SI_Arrival", RowNo, "HoldReason", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				if(driver.findElement(By.id("isHouseHold")).getAttribute("value").equalsIgnoreCase("N"))
				{
					GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "HouseHoldCheckBox", ScenarioDetailsHashMap, 10), 2);
					AppReusableMethods.selectValueFromLov(driver,  orOceanArrival.getElement(driver, "ReasonCodeLOV", ScenarioDetailsHashMap, 2), orOceanArrival, "Reason_Code",ExcelUtils.getCellData("SI_Arrival", RowNo, "ReasonCode", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					GenericMethods.pauseExecution(2000);
				}
			}	
			GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "GridEditButton", ScenarioDetailsHashMap, 2), 10);
			GenericMethods.pauseExecution(2000);
		}
		//GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "GridEditButton", ScenarioDetailsHashMap, 2), 10);
		GenericMethods.clickElement(driver, By.xpath("html/body/form/table/tbody/tr[5]/td/div/table/tbody/tr[1]"), null, 10);
		GenericMethods.pauseExecution(2000);
	}
	public static void mblCharges(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		GenericMethods.clickElement(driver, null,orOceanArrival.getElement(driver, "Tab_MBLChargesCost", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(4000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		String ArrivalconfirmationSummary = GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "Arrival_Confirmation_MSG", ScenarioDetailsHashMap, 2), 2);
		System.out.println("ArrivalconfirmationSummary :"+ArrivalconfirmationSummary.split("#")[0]);
		GenericMethods.assertTwoValues(ArrivalconfirmationSummary.split("#")[0], ExcelUtils.getCellData("SI_Arrival", RowNo, "Arrivalconfirmation_MSG", ScenarioDetailsHashMap), "Verifying Arrivalconfirmation message", ScenarioDetailsHashMap);

		//GenericMethods.clickElement(driver, null, oceanArrival.getElement(driver, "conformationText", ScenarioDetailsHashMap, 10), 2);

	}
	public static void arrivalNotice(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		GenericMethods.clickElement(driver, null,orOceanArrival.getElement(driver, "Tab_ArrivalNotice", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(4000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		/*String ChargesSummary = GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "ChargesValidationMSG", ScenarioDetailsHashMap, 2), 2);
		System.out.println("ArrivalconfirmationSummary :"+ChargesSummary.split(" : ")[1]);
		GenericMethods.assertTwoValues(ChargesSummary.split(" : ")[1], ExcelUtils.getCellData("SI_Arrival", RowNo, "ChargesValidation_MSG", ScenarioDetailsHashMap), "Verifying Charges Validation message", ScenarioDetailsHashMap);
		 */
	}
	public static void notesAndStatus(WebDriver driver,
			HashMap<String, String> ScenarioDetailsHashMap, int RowNo) {
		GenericMethods.clickElement(driver, null,orOceanArrival.getElement(driver, "Tab_NotesStatus", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}

	}
	public static void deliveryInstructions(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		GenericMethods.clickElement(driver, null,orOceanArrival.getElement(driver, "Tab_DeliveryInstructions", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}

		//int DIRowTotalCount = ExcelUtils.getCellDataRowCount("SI_Arrival", ScenarioDetailsHashMap);
		//System.out.println("DIRowTotalCount :"+DIRowTotalCount);
		int RowCount_ArrivalDI = ExcelUtils.getCellDataRowCount("SIArrival_DeliveryInstructions", ScenarioDetailsHashMap);
		for(int DIRowCount=1;DIRowCount<=RowCount_ArrivalDI;DIRowCount++)
		{
			System.out.println("Delivery Type :"+ExcelUtils.getCellData("SIArrival_DeliveryInstructions", DIRowCount, "DeliveryType", ScenarioDetailsHashMap));
			System.out.println("DIRowCount :"+DIRowCount);
			GenericMethods.pauseExecution(2000);
			Select DeliveryTypeselect=new Select(orOceanArrival.getElement(driver, "DeliveryInstructions_DeliveryType", ScenarioDetailsHashMap, 10));
			DeliveryTypeselect.selectByVisibleText(ExcelUtils.getCellData("SIArrival_DeliveryInstructions", DIRowCount, "DeliveryType", ScenarioDetailsHashMap));
			Select Delivery_StatusSelect=new Select(orOceanArrival.getElement(driver, "Delivery_Status", ScenarioDetailsHashMap, 10));
			Delivery_StatusSelect.selectByVisibleText(ExcelUtils.getCellData("SIArrival_DeliveryInstructions", DIRowCount, "DeliveryStatus", ScenarioDetailsHashMap));
			Select typeOfMovementSelect=new Select(orOceanArrival.getElement(driver, "typeOfMovement", ScenarioDetailsHashMap, 10));
			typeOfMovementSelect.selectByVisibleText(ExcelUtils.getCellData("SIArrival_DeliveryInstructions", DIRowCount, "TypeOfMovement", ScenarioDetailsHashMap));
			AppReusableMethods.selectValueFromLov(driver,  orOceanArrival.getElement(driver, "PickupPartyIdSrchBtn", ScenarioDetailsHashMap, 2), orOceanArrival, "PartyId",ExcelUtils.getCellData("SIArrival_DeliveryInstructions", DIRowCount, "PickupPartyIdSrch", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			//AppReusableMethods.selectValueFromLov(driver,  orOceanArrival.getElement(driver, "PickupLocationBtn", ScenarioDetailsHashMap, 2), orOceanArrival, "FirmsCode",ExcelUtils.getCellData("SIArrival_DeliveryInstructions", DIRowCount, "Pickup_Location", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			//AppReusableMethods.selectValueFromLov(driver,  orOceanArrival.getElement(driver, "PickupLocation_DestBtn", ScenarioDetailsHashMap, 2), orOceanArrival, "FirmsCode",ExcelUtils.getCellData("SIArrival_DeliveryInstructions", DIRowCount, "Pickup_Location", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "Pickup_Date", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SIArrival_DeliveryInstructions", DIRowCount, "PickupDate", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "PickupTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SIArrival_DeliveryInstructions", DIRowCount, "PickupTime", ScenarioDetailsHashMap), 2);
			AppReusableMethods.selectValueFromLov(driver,  orOceanArrival.getElement(driver, "DeliveryPartyIdSrchBtn", ScenarioDetailsHashMap, 2), orOceanArrival, "PartyId",ExcelUtils.getCellData("SIArrival_DeliveryInstructions", DIRowCount, "DeliveryPartyIdSrch", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			//AppReusableMethods.selectValueFromLov(driver,  orOceanArrival.getElement(driver, "DeliveryLocationBtn", ScenarioDetailsHashMap, 2), orOceanArrival, "FirmsCode",ExcelUtils.getCellData("SIArrival_DeliveryInstructions", DIRowCount, "Delivery_Location", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "RequiredDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SIArrival_DeliveryInstructions", DIRowCount, "Required_Date", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "RequiredTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SIArrival_DeliveryInstructions", DIRowCount, "Required_Time", ScenarioDetailsHashMap), 2);
			GenericMethods.selectOptionFromDropDown(driver, null, orOceanArrival.getElement(driver, "PodType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SIArrival_DeliveryInstructions", DIRowCount, "PodType", ScenarioDetailsHashMap));
			if(ExcelUtils.getCellData("SIArrival_DeliveryInstructions", DIRowCount, "DeliveryStatus", ScenarioDetailsHashMap).equalsIgnoreCase("Partial"))
			{
				System.out.println("DeliveryStatus Partial");
				int TotalContainerCount = driver.findElements(By.xpath("html/body/form/table/tbody/tr[4]/td/fieldset[2]/div/table/tbody/tr/td[1]/fieldset/table/tbody/tr/td[2]/div/table/tbody/tr")).size();
				System.out.println("TotalContainerCount :"+TotalContainerCount);
				for(int ContainerCount=1;ContainerCount<=TotalContainerCount;ContainerCount++)
				{
					GenericMethods.clickElement(driver, By.xpath("html/body/form/table/tbody/tr[4]/td/fieldset[2]/div/table/tbody/tr/td[1]/fieldset/table/tbody/tr/td[2]/div/table/tbody/tr["+ContainerCount+"]/td/input[3]"), null, 2);
				}
				int TotalHouseCount = driver.findElements(By.xpath("html/body/form/table/tbody/tr[4]/td/fieldset[2]/div/table/tbody/tr/td[3]/fieldset/table/tbody/tr/td/div/table/tbody/tr")).size();
				System.out.println("TotalHouseCount :"+TotalHouseCount);
				for(int HouseCount=2;HouseCount<TotalHouseCount;HouseCount++)
				{
					if(!GenericMethods.isChecked(orOceanArrival.getElement(driver, "HouseDetails_CheckBox", ScenarioDetailsHashMap, 10)))
					{
						GenericMethods.clickElement(driver, By.xpath("html/body/form/table/tbody/tr[4]/td/fieldset[2]/div/table/tbody/tr/td[3]/fieldset/table/tbody/tr/td/div/table/tbody/tr["+HouseCount+"]/td[2]/input[3]"), null, 2);
					}
				}
				GenericMethods.pauseExecution(3000);
				/*
				GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "HouseTotalPartialPieces", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SIArrival_DeliveryInstructions", DIRowCount, "TotalPartialHousePieces", ScenarioDetailsHashMap), 2);
				GenericMethods.pauseExecution(3000);
				 */

				//Updated By Sangeeta
				for (int XLCnt = 0; XLCnt < ExcelUtils.getCellDataRowCount("SI_ArrivalContainers", ScenarioDetailsHashMap); XLCnt++) 
				{
					GenericMethods.replaceTextofTextField(driver, By.id("totalPieces_"+XLCnt), null, ExcelUtils.getCellData("SI_ArrivalContainers", XLCnt+1, "Current_Pieces", ScenarioDetailsHashMap), 2);
					GenericMethods.pauseExecution(3000);
				}
				GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "HouseTotalPartialgrossWeight", ScenarioDetailsHashMap, 10), 2);
				System.out.println("Typed");

			}
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null,orOceanArrival.getElement(driver, "DeliveryInstructionsADDButton", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(4000);
		}
	}

	public static void releaseHouseDestination(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{

		AppReusableMethods.selectMenu(driver, ETransMenu.oceanReleaseHouse,"Release Destination HBL", ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(3000);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "MawbSearch_DateFrom", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SI_Arrival", RowNo, "ArrivalDateFrom", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "MawbSearch_DateTo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SI_Arrival", RowNo, "ArrivalDateTo", ScenarioDetailsHashMap), 10);
		System.out.println("HBL ID :"+ExcelUtils.getCellData("SI_ArrivalContainers", RowNo, "HBL_ID", ScenarioDetailsHashMap));
		//GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "Hbl_No", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "Hbl_No", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SI_ArrivalContainers", RowNo, "HBL_ID", ScenarioDetailsHashMap), 10);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);

		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		GenericMethods.pauseExecution(2000);

		String ReleaseHouseMSG_Summary = GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "ReleaseHouse_Success_MSG", ScenarioDetailsHashMap, 2), 2);
		System.out.println("ReleaseHouseMSG_Summary :"+ReleaseHouseMSG_Summary.split(": ")[1]);
		GenericMethods.assertTwoValues(ReleaseHouseMSG_Summary.split(": ")[1], ExcelUtils.getCellData("SI_Arrival", RowNo, "ReleaseHouse_SuccessMSG", ScenarioDetailsHashMap), "Verifying Release House Success Message", ScenarioDetailsHashMap);

	}

	//Sangeetha
	public static void 	ITInfo_Validation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		GenericMethods.pauseExecution(2000);
		AppReusableMethods.selectMenu(driver, ETransMenu.oceanArraival,"Arrival Confirmation", ScenarioDetailsHashMap);
		searchmbl(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.clickElement(driver, null,orOceanArrival.getElement(driver, "Tab_Arrival", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);

		// validation FUNC061.6( Checking House It info  when no data is entered) 
		GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "HouseItInfoLov", ScenarioDetailsHashMap, 2), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectWindow(driver);
		GenericMethods.pauseExecution(2000);
		String HouseInlandPick =GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "HouseInlandPick", ScenarioDetailsHashMap, 10),2);
		String Houseremarks =GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "Houseremarks", ScenarioDetailsHashMap, 10),2);
		driver.close();
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);

		// validation FUNC061.6( Validating data and  in arrival notice, the data should not be default from House it info as any data is not entered.
		GenericMethods.clickElement(driver, null,orOceanArrival.getElement(driver, "Tab_ArrivalNotice", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(4000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		GenericMethods.assertTwoValues(GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "ArrivalNoticeInlandPick", ScenarioDetailsHashMap, 10), 2), HouseInlandPick, "Validating defaulting of Inland Pickup from House IT Info to Arrival Notice", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "ArrivalNoticeAdditionalRemarks", ScenarioDetailsHashMap, 10), 2), Houseremarks, "Validating defaulting of Remarks from House IT Info to Arrival Notice", ScenarioDetailsHashMap);

		// validation FUNC061.4( Checking Master It info  when no data is entered)  
		GenericMethods.clickElement(driver, null,orOceanArrival.getElement(driver, "Tab_Arrival", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "MasteritnfoLov", ScenarioDetailsHashMap, 2), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectWindow(driver);
		String MasteritPort =GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "MasteritPort", ScenarioDetailsHashMap, 2), 10); 
		String MasterLocationOfGoods =GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "LocationOfGoods", ScenarioDetailsHashMap, 2), 10);
		String MasterclearanceLocation =GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "clearanceLocation", ScenarioDetailsHashMap, 2), 10);
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);

		// validation FUNC061.4( Validating data and  in House IT info, the data should not be default from Master it info as any data is not entered.
		GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "HouseItInfoLov", ScenarioDetailsHashMap, 2), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectWindow(driver);
		GenericMethods.pauseExecution(2000);
		GenericMethods.assertTwoValues(driver.findElement(By.id("entryPortDKCode")).getAttribute("value"), MasteritPort , "Validating  itPort Pickup blank value from master IT info to House IT info", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(driver.findElement(By.id("locationOfGoods")).getAttribute("value"),MasterLocationOfGoods , "Validating  LocationOfGoods blank value from master IT info to House IT info", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(driver.findElement(By.id("clearanceLocation")).getAttribute("value"),  MasterclearanceLocation, "Validating  clearance Location blank value from master IT info to House IT info", ScenarioDetailsHashMap);
		driver.close();
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);

		// validation FUNC061.1(Entering data in Master IT info)
		GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "MasteritnfoLov", ScenarioDetailsHashMap, 2), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectWindow(driver);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "MasteritNumber", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_Arrival", RowNo, "ITNumber", ScenarioDetailsHashMap), 10);
		Actions act=new Actions(driver);
		act.moveToElement(GenericMethods.locateElement(driver, By.name("itNumber"), 2));
		act.click();
		act.sendKeys(Keys.TAB).perform();
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "MastergoScheduleDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_Arrival", RowNo, "ITGoScheduledDate", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "MasteritDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_Arrival", RowNo, "ITDate", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "MasteritCarrier", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_Arrival", RowNo, "ITCarrier", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "MasteritPort", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_Arrival", RowNo, "ITPort", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "LocationOfGoods", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_Arrival", RowNo, "ITLocationOfGoods", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "ClearanceLocation", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_Arrival", RowNo, "ITClearanceLocation", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "MasterMoreDate", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_Arrival", RowNo, "ITETAatDischargeDate", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "MasterpapersReceivedDate", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_Arrival", RowNo, "ITPapersReceivedDate", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "MastercarrierDocsDate", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_Arrival", RowNo, "ITCarrierDocsDate", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "MasterdummerageDate", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_Arrival", RowNo, "ITDemurrageFreeTimeDate", ScenarioDetailsHashMap), 10);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(4000);
		String MasterITInfoSummary = GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "ITInfo_ValidationMSG", ScenarioDetailsHashMap, 2), 10);
		GenericMethods.pauseExecution(2000);
		GenericMethods.assertTwoValues(MasterITInfoSummary, ExcelUtils.getCellData("SI_Arrival", RowNo, "ITInfo_Validation_MSG", ScenarioDetailsHashMap), "Validating Master IT Info Sucess message", ScenarioDetailsHashMap);
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);

		// validation FUNC061.1(Checking data in House IT info, data should get defaulted from Master IT info to House IT Info)
		GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "HouseItInfoLov", ScenarioDetailsHashMap, 2), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectWindow(driver);
		GenericMethods.pauseExecution(2000);
		GenericMethods.assertTwoValues(driver.findElement(By.id("locationOfGoods")).getAttribute("value"), ExcelUtils.getCellData("SI_Arrival", RowNo, "ITLocationOfGoods", ScenarioDetailsHashMap), "Validating defaulting of Location Of Goods from Master Info to House IT Info", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(driver.findElement(By.id("clearanceLocation")).getAttribute("value"), ExcelUtils.getCellData("SI_Arrival", RowNo, "ITClearanceLocation", ScenarioDetailsHashMap), "Validating defaulting of clearanceLocation from Master Info to House IT Info", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(driver.findElement(By.id("entryPortDKCode")).getAttribute("value"), ExcelUtils.getCellData("SI_Arrival", RowNo, "ITPort", ScenarioDetailsHashMap), "Validating defaulting of ITP ort from Master Info to House IT Info", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(driver.findElement(By.id("carrier")).getAttribute("value"), ExcelUtils.getCellData("SI_Arrival", RowNo, "ITCarrier", ScenarioDetailsHashMap), "Validating defaulting of carrier from Master Info to House IT Info", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(driver.findElement(By.id("papersReceivedDate")).getAttribute("value"), ExcelUtils.getCellData("SI_Arrival", RowNo, "ITPapersReceivedDate", ScenarioDetailsHashMap), "Validating defaulting of papers Received Date from Master Info to House IT Info", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(driver.findElement(By.id("carrierDocsDate")).getAttribute("value"), ExcelUtils.getCellData("SI_Arrival", RowNo, "ITCarrierDocsDate", ScenarioDetailsHashMap), "Validating defaulting of carrier Docs Date from Master Info to House IT Info", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(driver.findElement(By.id("dummerageDate")).getAttribute("value"), ExcelUtils.getCellData("SI_Arrival", RowNo, "ITDemurrageFreeTimeDate", ScenarioDetailsHashMap), "Validating defaulting of DemurrageFreeTimeDate from Master Info to House IT Info", ScenarioDetailsHashMap);


		//validation FUNC061.3(Updating MasterpapersReceivedDate and MasterdummerageDate in House ITinfo)
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "HouseEntryPort", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_Arrival", RowNo, "HouseEntryPort", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "LocationOfGoods", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_Arrival", RowNo, "HouseLocationofGoods", ScenarioDetailsHashMap), 10);
		String HouseEntryPort = driver.findElement(By.id("entryPortDKCode")).getAttribute("value");
		String HouseLocationOfGoods = driver.findElement(By.id("locationOfGoods")).getAttribute("value");

		//validation FUNC061.2(Entering data in House IT info)
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "HouseUltimateLocationDKCode", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_Arrival", RowNo, "HouseUltimateLocationOfGoods", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "HouseUltimateLocationOfGoods", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_Arrival", RowNo, "HouseUltimateCargoLocation", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "HouseInlandPick", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_Arrival", RowNo, "InlandPickup", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "Houseremarks", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_Arrival", RowNo, "Remarks", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "HouseStorageBegins", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_Arrival", RowNo, "StorageBeginsDate", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "HouseCustomerReference", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_Arrival", RowNo, "HouseCustomerReference", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "HouseMarksnums", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_Arrival", RowNo, "MarksNumbers", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(4000);
		String HouseITInfoSummary = GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "ITInfo_ValidationMSG", ScenarioDetailsHashMap, 2), 10);
		GenericMethods.pauseExecution(2000);
		GenericMethods.assertTwoValues(HouseITInfoSummary, ExcelUtils.getCellData("SI_Arrival", RowNo, "ITInfo_Validation_MSG", ScenarioDetailsHashMap), "Validating Master IT Info Sucess message", ScenarioDetailsHashMap);
		driver.close();
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);

		//validation FUNC061.3(Checking MasterpapersReceivedDate and MasterdummerageDate in Master IT Info, the data should not be default from House It info for .
		GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "MasteritnfoLov", ScenarioDetailsHashMap, 2), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectWindow(driver);
		/*GenericMethods.assertTwoValues(driver.findElement(By.id("itPortDKCode")).getAttribute("value"),HouseEntryPort, "Validating defaulting of House Entry Port from House IT Info to Master IT Info", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(driver.findElement(By.id("locationOfGoods")).getAttribute("value"), HouseLocationOfGoods, "Validating defaulting of House Location Of Goods from House IT Info to Master IT Info", ScenarioDetailsHashMap);
		 */
		GenericMethods.assertTwoValues_NotEqual(driver.findElement(By.id("itPortDKCode")).getAttribute("value"),HouseEntryPort, "Validating defaulting of House Entry Port from House IT Info to Master IT Info", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues_NotEqual(driver.findElement(By.id("locationOfGoods")).getAttribute("value"), HouseLocationOfGoods, "Validating defaulting of House Location Of Goods from House IT Info to Master IT Info", ScenarioDetailsHashMap);

		driver.close();
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);

		//validation FUNC061.2 Validating data defaulting from House ITinfo to Arrival notice screen-data should be defaulted to the Arrival notice screen 
		arrivalNotice(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.assertTwoValues(driver.findElement(By.id("locationOfGoods")).getAttribute("value"), ExcelUtils.getCellData("SI_ArrivalNotice", RowNo, "HouseLocationofGoods", ScenarioDetailsHashMap), "Validating defaulting of Location Of Goods from House IT Info to Arrival Notice Screen", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(driver.findElement(By.id("currentLocation")).getAttribute("value"), ExcelUtils.getCellData("SI_ArrivalNotice", RowNo, "ITClearanceLocation", ScenarioDetailsHashMap), "Validating defaulting of Clearance Location from House IT Info to Arrival Notice Screen", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(driver.findElement(By.id("finalDestination")).getAttribute("value"), ExcelUtils.getCellData("SI_ArrivalNotice", RowNo, "HouseUltimateLocationOfGoods", ScenarioDetailsHashMap), "Validating defaulting of Ultimate Location Of Goods from  House IT Info to Arrival Notice Screen", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(driver.findElement(By.id("ultimateCargoLoc")).getAttribute("value"), ExcelUtils.getCellData("SI_ArrivalNotice", RowNo, "HouseUltimateCargoLocation", ScenarioDetailsHashMap), "Validating defaulting of Ultimate Cargo Location from House IT Info to Arrival Notice Screen", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(driver.findElement(By.id("customerRefNo")).getAttribute("value"), ExcelUtils.getCellData("SI_ArrivalNotice", RowNo, "HouseCustomerReference", ScenarioDetailsHashMap), "Validating defaulting of customer RefNo from House IT Info to Arrival Notice Screen", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "ArrivalNoticeAdditionalRemarks", ScenarioDetailsHashMap, 10), 2), ExcelUtils.getCellData("SI_ArrivalNotice", RowNo, "Remarks", ScenarioDetailsHashMap), "Validating defaulting of Additional Remarks 	from House IT Info to Arrival Notice Screen", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "ArrivalNoticeMarksnums", ScenarioDetailsHashMap, 10), 2), ExcelUtils.getCellData("SI_ArrivalNotice", RowNo, "MarksNumbers", ScenarioDetailsHashMap), "Validating defaulting of Marks & Numbers from House IT Info to Arrival Notice Screen", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "ArrivalNoticeInlandPick", ScenarioDetailsHashMap, 10), 2), ExcelUtils.getCellData("SI_ArrivalNotice", RowNo, "InlandPickup", ScenarioDetailsHashMap), "Validating defaulting of InlandPickup from House IT Info to Arrival Notice Screen", ScenarioDetailsHashMap);

		//validation FUNC061.5 Updating field value in Arrival Notice Screen the data should not b e default from House It info for above fields.
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "ArrivalNoticeMarksnums", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_ArrivalNotice", RowNo, "ArrivalNoticeMarksnums", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "ArrivalNoticeInlandPick", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_ArrivalNotice", RowNo, "ArrivalNoticeInlandPick", ScenarioDetailsHashMap), 2);
		String ArrivalNoticeMarksnums = GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "ArrivalNoticeMarksnums", ScenarioDetailsHashMap, 10), 2);
		String ArrivalNoticeInlandPick = GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "ArrivalNoticeInlandPick", ScenarioDetailsHashMap, 10), 2);
		System.out.println("b4 validation:"+ArrivalNoticeMarksnums);
		System.out.println("b4 validation:"+ArrivalNoticeInlandPick);
		//validation FUNC061.5(Checking updated data and  in House IT Info, the data should not be default from arrival notice.
		GenericMethods.clickElement(driver, null,orOceanArrival.getElement(driver, "Tab_Arrival", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "HouseItInfoLov", ScenarioDetailsHashMap, 2), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectWindow(driver);

		/*GenericMethods.assertTwoValues(ExcelUtils.getCellData("SI_ArrivalNotice", RowNo, "ArrivalNoticeMarksnums", ScenarioDetailsHashMap),ArrivalNoticeMarksnums, "Validating defaulting of Marksnums from Arrival Notice to House IT Info", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(ExcelUtils.getCellData("SI_ArrivalNotice", RowNo, "ArrivalNoticeInlandPick", ScenarioDetailsHashMap), ArrivalNoticeInlandPick, "Validating defaulting of InlandPick from Arrival Notice to House IT Info", ScenarioDetailsHashMap);
		 */
		GenericMethods.assertTwoValues_NotEqual(ExcelUtils.getCellData("SI_ArrivalNotice", RowNo, "ArrivalNoticeMarksnums", ScenarioDetailsHashMap),ArrivalNoticeMarksnums, "Validating defaulting of Marksnums from Arrival Notice to House IT Info", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues_NotEqual(ExcelUtils.getCellData("SI_ArrivalNotice", RowNo, "ArrivalNoticeInlandPick", ScenarioDetailsHashMap), ArrivalNoticeInlandPick, "Validating defaulting of InlandPick from Arrival Notice to House IT Info", ScenarioDetailsHashMap);



		driver.close();
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);

		GenericMethods.clickElement(driver, null,orOceanArrival.getElement(driver, "Tab_SearchList", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);

	}

	public static void arrivalNotice_Milestone(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		searchArrival(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.clickElement(driver, null,orOceanArrival.getElement(driver, "Tab_ArrivalNotice", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(4000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}

		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "GroupPrintIcon", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(6000);
		GenericMethods.selectWindow(driver);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SelectAllChkBox", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);
		//GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "ArrivalNoticeUS_ChkBox", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "ArrivalNoticeASIA_ChkBox", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);
		try{
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			GenericMethods.pauseExecution(4000);
		} catch (AWTException e) {e.printStackTrace();
		} catch (Exception e){e.printStackTrace();
		}
		driver.close();
		GenericMethods.pauseExecution(2000);
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);
		/*String ChargesSummary = GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "ChargesValidationMSG", ScenarioDetailsHashMap, 2), 2);
		System.out.println("ArrivalconfirmationSummary :"+ChargesSummary.split(" : ")[1]);
		GenericMethods.assertTwoValues(ChargesSummary.split(" : ")[1], ExcelUtils.getCellData("SI_Arrival", RowNo, "ChargesValidation_MSG", ScenarioDetailsHashMap), "Verifying Charges Validation message", ScenarioDetailsHashMap);
		 */
	}

}
