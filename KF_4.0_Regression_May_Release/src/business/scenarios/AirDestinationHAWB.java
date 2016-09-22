package business.scenarios;

import global.reusables.ExcelUtils;
import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import app.reuseables.AppReusableMethods;
import app.reuseables.ETransMenu;

public class AirDestinationHAWB {
	static ObjectRepository orCommon = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/CommonObjects.xml");
	static ObjectRepository orOceanArrival = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/OceanArrivals.xml");
	static ObjectRepository orAirHAWB=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/AirHAWB.xml");
	static ObjectRepository orAirDestinationHAWB = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/AirDestinationHAWB.xml");
	
	public static void airDestinationHAWB_SearchList(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		GenericMethods.pauseExecution(2000);
		AppReusableMethods.selectMenu(driver, ETransMenu.AIR_DestinationHAWB,"Destination HAWB", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "HAWBIDSearch", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "HAWB_Id", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
	}
	
	public static void Air_DestinationHAWB(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		GenericMethods.pauseExecution(2000);
		if(ExcelUtils.getCellData("AI_DestinationHawbMaindetails", RowNo, "DeliveryInstructionRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			deliveryInstructions(driver, ScenarioDetailsHashMap, RowNo);
		}

	}
	//Pavan--Below method will perform Delivery Instructions in Destination HAWB
	public static void deliveryInstructions(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		GenericMethods.clickElement(driver, null,orAirDestinationHAWB.getElement(driver, "Air_DeliveryInstructions", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}

		int RowCount_DestinationHBLDI = ExcelUtils.getCellDataRowCount("AI_DestinationHawb_DI", ScenarioDetailsHashMap);
		for(int DIRowCount=1;DIRowCount<=RowCount_DestinationHBLDI;DIRowCount++)
		{
			System.out.println("Delivery Type :"+ExcelUtils.getCellData("AI_DestinationHawb_DI", DIRowCount, "DeliveryType", ScenarioDetailsHashMap));
			System.out.println("DIRowCount :"+DIRowCount);
			GenericMethods.pauseExecution(2000);
			Select DeliveryTypeselect=new Select(orAirDestinationHAWB.getElement(driver, "DeliveryInstructions_DeliveryType", ScenarioDetailsHashMap, 10));
			DeliveryTypeselect.selectByVisibleText(ExcelUtils.getCellData("AI_DestinationHawb_DI", DIRowCount, "DeliveryType", ScenarioDetailsHashMap));
			Select Delivery_StatusSelect=new Select(orAirDestinationHAWB.getElement(driver, "Delivery_Status", ScenarioDetailsHashMap, 10));
			Delivery_StatusSelect.selectByVisibleText(ExcelUtils.getCellData("AI_DestinationHawb_DI", DIRowCount, "DeliveryStatus", ScenarioDetailsHashMap));
			Select typeOfMovementSelect=new Select(orAirDestinationHAWB.getElement(driver, "typeOfMovement", ScenarioDetailsHashMap, 10));
			typeOfMovementSelect.selectByVisibleText(ExcelUtils.getCellData("AI_DestinationHawb_DI", DIRowCount, "TypeOfMovement", ScenarioDetailsHashMap));
			AppReusableMethods.selectValueFromLov(driver,  orAirDestinationHAWB.getElement(driver, "PickupPartyIdSrchBtn", ScenarioDetailsHashMap, 2), orOceanArrival, "PartyId",ExcelUtils.getCellData("AI_DestinationHawb_DI", DIRowCount, "PickupPartyIdSrch", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orAirDestinationHAWB.getElement(driver, "Pickup_Date", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("AI_DestinationHawb_DI", DIRowCount, "PickupDate", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirDestinationHAWB.getElement(driver, "PickupTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("AI_DestinationHawb_DI", DIRowCount, "PickupTime", ScenarioDetailsHashMap), 2);
			AppReusableMethods.selectValueFromLov(driver,  orAirDestinationHAWB.getElement(driver, "DeliveryPartyIdSrchBtn", ScenarioDetailsHashMap, 2), orOceanArrival, "PartyId",ExcelUtils.getCellData("AI_DestinationHawb_DI", DIRowCount, "DeliveryPartyIdSrch", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orAirDestinationHAWB.getElement(driver, "RequiredDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("AI_DestinationHawb_DI", DIRowCount, "Required_Date", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirDestinationHAWB.getElement(driver, "RequiredTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("AI_DestinationHawb_DI", DIRowCount, "Required_Time", ScenarioDetailsHashMap), 2);
			GenericMethods.selectOptionFromDropDown(driver, null, orAirDestinationHAWB.getElement(driver, "PodType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("AI_DestinationHawb_DI", DIRowCount, "PodType", ScenarioDetailsHashMap));
			
			if(ExcelUtils.getCellData("AI_DestinationHawb_DI", DIRowCount, "DeliveryStatus", ScenarioDetailsHashMap).equalsIgnoreCase("Partial"))
			{
				int TotalHouseCount = driver.findElements(By.xpath("//legend[text()='House Details']/ancestor::fieldset[1]//tbody/tr//tr")).size();
				System.out.println("TotalHouseCount :"+TotalHouseCount);
				for(int HouseCount=2;HouseCount<TotalHouseCount;HouseCount++)
				{
					if(!GenericMethods.isChecked(orAirDestinationHAWB.getElement(driver, "HouseDetails_CheckBox", ScenarioDetailsHashMap, 10)))
					{
						GenericMethods.clickElement(driver, By.xpath("//legend[text()='House Details']/ancestor::fieldset[1]//tbody/tr//tr["+HouseCount+"]/td[2]/input[3]"), null, 2);
					}
				}
				GenericMethods.pauseExecution(3000);
				GenericMethods.replaceTextofTextField(driver, null, orAirDestinationHAWB.getElement(driver, "HouseTotalPartialPieces", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("AI_DestinationHawb_DI", DIRowCount, "TotalPartialHousePieces", ScenarioDetailsHashMap), 2);
				GenericMethods.pauseExecution(3000);
				GenericMethods.clickElement(driver, null, orAirDestinationHAWB.getElement(driver, "HouseTotalPartialgrossWeight", ScenarioDetailsHashMap, 10), 2);
				System.out.println("Typed");
			}
			GenericMethods.clickElement(driver, null,orAirDestinationHAWB.getElement(driver, "DeliveryInstructionsADDButton", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(3000);
		}
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
	}
	
	
	
	/*public static void arrivalConfirmation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{

		int ArrivalRowCount = ExcelUtils.getCellDataRowCount("SI_Arrival", ScenarioDetailsHashMap);
		for(int RowCount=1;RowCount<=ArrivalRowCount;RowCount++)
		{
			GenericMethods.pauseExecution(2000);
			AppReusableMethods.selectMenu(driver, ETransMenu.oceanArraival,"Arrival Confirmation", ScenarioDetailsHashMap);

			System.out.println("RowCount running :"+RowCount);
			searchmbl(driver, ScenarioDetailsHashMap, RowCount);
			arrival(driver, ScenarioDetailsHashMap, RowCount);
			mblCharges(driver, ScenarioDetailsHashMap, RowCount);
			arrivalNotice(driver, ScenarioDetailsHashMap, RowCount);
			notesAndStatus(driver, ScenarioDetailsHashMap, RowCount);
			
			if(ExcelUtils.getCellData("SI_Arrival", RowCount, "DeliveryInstructionRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				System.out.println("Delvery Required");
				deliveryInstructions(driver, ScenarioDetailsHashMap, RowCount);
			}
			else
			{
				System.out.println("Delvery not Required");
			}

			
			GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		String DeliveryInstructionSummary = GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "DeliveryInstructionMSG", ScenarioDetailsHashMap, 2), 2);
		System.out.println("DeliveryInstructionSummary :"+DeliveryInstructionSummary.split(": ")[0]);
		GenericMethods.assertTwoValues(DeliveryInstructionSummary.split(": ")[0], ExcelUtils.getCellData("SI_Arrival", RowNo, "DeliveryInstruction_MSG", ScenarioDetailsHashMap), "Verifying Delivery Instruction message", ScenarioDetailsHashMap);

		if(ExcelUtils.getCellData("SI_Arrival", RowNo, "HoldReason", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			releaseHouseDestination(driver, ScenarioDetailsHashMap, RowNo);
		}
		}

	}
	public static void searchmbl(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo)
	{
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "MblId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_Arrival", RowNo,"MBLId", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "SearchMBLDateFrom", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SI_Arrival", RowNo, "ArrivalDateFrom", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "SearchMBLDateTo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SI_Arrival", RowNo, "ArrivalDateTo", ScenarioDetailsHashMap), 10);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.clickElement(driver, null,orOceanArrival.getElement(driver, "MblIdSelectCheckbox1", ScenarioDetailsHashMap, 10), 2);

	}
	public static void arrival(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		GenericMethods.clickElement(driver, null,orOceanArrival.getElement(driver, "Tab_Arrival", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
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
				if(ExcelUtils.getCellData("SI_ArrivalContainers", RowNo, "HoldReason", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
				{
					if(driver.findElement(By.id("isHouseHold")).getAttribute("value").equalsIgnoreCase("N"))
					{
						GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "HouseHoldCheckBox", ScenarioDetailsHashMap, 10), 2);
						AppReusableMethods.selectValueFromLov(driver,  orOceanArrival.getElement(driver, "ReasonCodeLOV", ScenarioDetailsHashMap, 2), orOceanArrival, "Reason_Code",ExcelUtils.getCellData("SI_ArrivalContainers", RowNo, "ReasonCode", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
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
					if(ExcelUtils.getCellData("SI_ArrivalContainers", datasetNo, "HoldReason", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
					{
						if(driver.findElement(By.id("isHouseHold")).getAttribute("value").equalsIgnoreCase("N"))
						{
							GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "HouseHoldCheckBox", ScenarioDetailsHashMap, 10), 2);
							AppReusableMethods.selectValueFromLov(driver,  orOceanArrival.getElement(driver, "ReasonCodeLOV", ScenarioDetailsHashMap, 2), orOceanArrival, "Reason_Code",ExcelUtils.getCellData("SI_ArrivalContainers", datasetNo, "ReasonCode", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
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
				if(ExcelUtils.getCellData("SI_ArrivalContainers", RowNo, "HoldReason", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
				{
					if(driver.findElement(By.id("isHouseHold")).getAttribute("value").equalsIgnoreCase("N"))
					{
						GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "HouseHoldCheckBox", ScenarioDetailsHashMap, 10), 2);
						AppReusableMethods.selectValueFromLov(driver,  orOceanArrival.getElement(driver, "ReasonCodeLOV", ScenarioDetailsHashMap, 2), orOceanArrival, "Reason_Code",ExcelUtils.getCellData("SI_ArrivalContainers", RowNo, "ReasonCode", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
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
				if(ExcelUtils.getCellData("SI_ArrivalContainers", RowNo, "HoldReason", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
				{
					if(driver.findElement(By.id("isHouseHold")).getAttribute("value").equalsIgnoreCase("N"))
					{
						GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "HouseHoldCheckBox", ScenarioDetailsHashMap, 10), 2);
						AppReusableMethods.selectValueFromLov(driver,  orOceanArrival.getElement(driver, "ReasonCodeLOV", ScenarioDetailsHashMap, 2), orOceanArrival, "Reason_Code",ExcelUtils.getCellData("SI_ArrivalContainers", RowNo, "ReasonCode", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
						GenericMethods.pauseExecution(2000);
					}
				}	

				GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "CurrentPieces", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_ArrivalContainers", RowNo, "Current_Pieces", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "CurrentWeight", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_ArrivalContainers", RowNo, "Current_Weight", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "CurrentVolume", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_ArrivalContainers", RowNo, "Current_Volume", ScenarioDetailsHashMap), 2);
				GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "GridEditButton", ScenarioDetailsHashMap, 2), 10);
			}
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
		String ChargesSummary = GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "ChargesValidationMSG", ScenarioDetailsHashMap, 2), 2);
		System.out.println("ArrivalconfirmationSummary :"+ChargesSummary.split(" : ")[1]);
		GenericMethods.assertTwoValues(ChargesSummary.split(" : ")[1], ExcelUtils.getCellData("SI_Arrival", RowNo, "ChargesValidation_MSG", ScenarioDetailsHashMap), "Verifying Charges Validation message", ScenarioDetailsHashMap);

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

	}*/
	
	

}
