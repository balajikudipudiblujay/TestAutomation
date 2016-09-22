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

public class OceanDestinationHBL {
	static ObjectRepository orCommon = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/CommonObjects.xml");
	static ObjectRepository orOceanArrival = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/OceanArrivals.xml");
	static ObjectRepository orOceanDestinationHBL = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/OceanArrivals.xml");
	
	
	public static void Ocean_DestinationHBL(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		GenericMethods.pauseExecution(2000);
		/*AppReusableMethods.selectMenu(driver, ETransMenu.Sea_DestinationHBL,"Destination HBL", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orOceanDestinationHBL.getElement(driver, "Hbl_No", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo,"HBLId", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(2000);*/
		
		if(ExcelUtils.getCellData("SI_DestinationHBLMaindetails", RowNo, "DeliveryInstructionRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			deliveryInstructions(driver, ScenarioDetailsHashMap, RowNo);
		}
		
		
		

	}
	
	public static void seaDestinationHBL_SearchList(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		GenericMethods.pauseExecution(2000);
		AppReusableMethods.selectMenu(driver, ETransMenu.Sea_DestinationHBL,"Destination HBL", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orOceanDestinationHBL.getElement(driver, "Hbl_No", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, By.id("dateFrom"), null, ExcelUtils.getCellData("SIDestinationHBL_DI", RowNo, "ArrivalDateFrom", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, By.id("dateTo"), null, ExcelUtils.getCellData("SIDestinationHBL_DI", RowNo, "ArrivalDateTo", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
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
	
	//Pavan--Below method will perform Delivery Instructions in Destination HBL
	public static void deliveryInstructions(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		GenericMethods.clickElement(driver, null,orOceanDestinationHBL.getElement(driver, "Tab_DeliveryInstructions", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}

		int RowCount_DestinationHBLDI = ExcelUtils.getCellDataRowCount("SIDestinationHBL_DI", ScenarioDetailsHashMap);
		for(int DIRowCount=1;DIRowCount<=RowCount_DestinationHBLDI;DIRowCount++)
		{
			System.out.println("Delivery Type :"+ExcelUtils.getCellData("SIDestinationHBL_DI", DIRowCount, "DeliveryType", ScenarioDetailsHashMap));
			System.out.println("DIRowCount :"+DIRowCount);
			GenericMethods.pauseExecution(2000);
			Select DeliveryTypeselect=new Select(orOceanDestinationHBL.getElement(driver, "DeliveryInstructions_DeliveryType", ScenarioDetailsHashMap, 10));
			DeliveryTypeselect.selectByVisibleText(ExcelUtils.getCellData("SIDestinationHBL_DI", DIRowCount, "DeliveryType", ScenarioDetailsHashMap));
			Select Delivery_StatusSelect=new Select(orOceanDestinationHBL.getElement(driver, "Delivery_Status", ScenarioDetailsHashMap, 10));
			Delivery_StatusSelect.selectByVisibleText(ExcelUtils.getCellData("SIDestinationHBL_DI", DIRowCount, "DeliveryStatus", ScenarioDetailsHashMap));
			Select typeOfMovementSelect=new Select(orOceanDestinationHBL.getElement(driver, "typeOfMovement", ScenarioDetailsHashMap, 10));
			typeOfMovementSelect.selectByVisibleText(ExcelUtils.getCellData("SIDestinationHBL_DI", DIRowCount, "TypeOfMovement", ScenarioDetailsHashMap));
			AppReusableMethods.selectValueFromLov(driver,  orOceanDestinationHBL.getElement(driver, "PickupPartyIdSrchBtn", ScenarioDetailsHashMap, 2), orOceanArrival, "PartyId",ExcelUtils.getCellData("SIDestinationHBL_DI", DIRowCount, "PickupPartyIdSrch", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			//AppReusableMethods.selectValueFromLov(driver,  orOceanArrival.getElement(driver, "PickupLocationBtn", ScenarioDetailsHashMap, 2), orOceanArrival, "FirmsCode",ExcelUtils.getCellData("SIArrival_DeliveryInstructions", DIRowCount, "Pickup_Location", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			//AppReusableMethods.selectValueFromLov(driver,  orOceanArrival.getElement(driver, "PickupLocation_DestBtn", ScenarioDetailsHashMap, 2), orOceanArrival, "FirmsCode",ExcelUtils.getCellData("SIArrival_DeliveryInstructions", DIRowCount, "Pickup_Location", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orOceanDestinationHBL.getElement(driver, "Pickup_Date", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SIDestinationHBL_DI", DIRowCount, "PickupDate", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orOceanDestinationHBL.getElement(driver, "PickupTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SIDestinationHBL_DI", DIRowCount, "PickupTime", ScenarioDetailsHashMap), 2);
			AppReusableMethods.selectValueFromLov(driver,  orOceanDestinationHBL.getElement(driver, "DeliveryPartyIdSrchBtn", ScenarioDetailsHashMap, 2), orOceanArrival, "PartyId",ExcelUtils.getCellData("SIDestinationHBL_DI", DIRowCount, "DeliveryPartyIdSrch", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			//AppReusableMethods.selectValueFromLov(driver,  orOceanArrival.getElement(driver, "DeliveryLocationBtn", ScenarioDetailsHashMap, 2), orOceanArrival, "FirmsCode",ExcelUtils.getCellData("SIArrival_DeliveryInstructions", DIRowCount, "Delivery_Location", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orOceanDestinationHBL.getElement(driver, "RequiredDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SIDestinationHBL_DI", DIRowCount, "Required_Date", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orOceanDestinationHBL.getElement(driver, "RequiredTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SIDestinationHBL_DI", DIRowCount, "Required_Time", ScenarioDetailsHashMap), 2);
			GenericMethods.selectOptionFromDropDown(driver, null, orOceanDestinationHBL.getElement(driver, "PodType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SIDestinationHBL_DI", DIRowCount, "PodType", ScenarioDetailsHashMap));
			if(ExcelUtils.getCellData("SIDestinationHBL_DI", DIRowCount, "DeliveryStatus", ScenarioDetailsHashMap).equalsIgnoreCase("Partial"))
			{
				System.out.println("DeliveryStatus Partial");
				int TotalContainerCount = driver.findElements(By.xpath("//legend[text()='Container Details']/ancestor::fieldset[1]//tr/td[2]//tr")).size();
				System.out.println("TotalContainerCount :"+TotalContainerCount);
				for(int ContainerCount=1;ContainerCount<=TotalContainerCount;ContainerCount++)
				{
					GenericMethods.clickElement(driver, By.xpath("//legend[text()='Container Details']/ancestor::fieldset[1]//tr/td[2]//tr["+ContainerCount+"]/td/input[3]"), null, 2);
				}
				int TotalHouseCount = driver.findElements(By.xpath("//legend[text()='House Details']/ancestor::fieldset[1]//tbody/tr//tr")).size();
				System.out.println("TotalHouseCount :"+TotalHouseCount);
				for(int HouseCount=2;HouseCount<TotalHouseCount;HouseCount++)
				{
					if(!GenericMethods.isChecked(orOceanDestinationHBL.getElement(driver, "HouseDetails_CheckBox", ScenarioDetailsHashMap, 10)))
					{
						GenericMethods.clickElement(driver, By.xpath("//legend[text()='House Details']/ancestor::fieldset[1]//tbody/tr//tr["+HouseCount+"]/td[2]/input[3]"), null, 2);
					}
				}
				GenericMethods.pauseExecution(3000);
				GenericMethods.replaceTextofTextField(driver, null, orOceanDestinationHBL.getElement(driver, "HouseTotalPartialPieces", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SIDestinationHBL_DI", DIRowCount, "TotalPartialHousePieces", ScenarioDetailsHashMap), 2);
				GenericMethods.pauseExecution(3000);
				GenericMethods.clickElement(driver, null, orOceanDestinationHBL.getElement(driver, "HouseTotalPartialgrossWeight", ScenarioDetailsHashMap, 10), 2);
				System.out.println("Typed");
			}
			GenericMethods.clickElement(driver, null,orOceanDestinationHBL.getElement(driver, "DeliveryInstructionsADDButton", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(3000);
		}
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
	}

	/*public static void releaseHouseDestination(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.oceanReleaseHouse,"Release Destination HBL", ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(3000);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "MawbSearch_DateFrom", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SI_Arrival", RowNo, "ArrivalDateFrom", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "MawbSearch_DateTo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SI_Arrival", RowNo, "ArrivalDateTo", ScenarioDetailsHashMap), 10);
		System.out.println("HBL ID :"+ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "Hbl_No", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap), 10);
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

	}*/
	
	
	public static void validationOfPODDateInDestinationHBL(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		{
			seaDestinationHBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);
			GenericMethods.clickElement(driver, null,orOceanDestinationHBL.getElement(driver, "Tab_DeliveryInstructions", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(3000);
			try{
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {
			}
			//Date Validation Start Author-Sangeeta
			//FUNC062.16-Date Validation(Full POD Date)
			
			String ActualPODDate =  driver.findElement(By.id("podDate")).getAttribute("value");
			String ActualPODTime =  driver.findElement(By.name("requiredTime")).getAttribute("value");
			String ExpectedPODDate = ExcelUtils.getCellData("SI_POD_DI", RowNo, "PODEntry_PODDate", ScenarioDetailsHashMap);
			String ExpectedPODTime = ExcelUtils.getCellData("SI_POD_DI", RowNo, "PODEntry_PODTime", ScenarioDetailsHashMap);
			String ActualPODDateTime = ActualPODDate.concat(" "+ActualPODTime);
			String ExpectedPODateTime = ExpectedPODDate.concat(" "+ExpectedPODTime);
			GenericMethods.assertTwoValues(ActualPODDateTime, ExpectedPODateTime, "After POD Generated for the record, validating defaulting of POD Date from POD Screen to Delivery Instructions screen.", ScenarioDetailsHashMap);
			//Date Validation end
			GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
		}
	}
}
