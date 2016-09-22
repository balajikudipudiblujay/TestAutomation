package business.scenarios;

import global.reusables.ExcelUtils;
import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;

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

public class AirImport_Arrival {
	static ObjectRepository orCommon = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/CommonObjects.xml");
	static ObjectRepository orAirArrival = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/AirArrivals.xml");
	public static void arrivalConfirmation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{

		int ArrivalRowCount = ExcelUtils.getCellDataRowCount("Air_Arrival", ScenarioDetailsHashMap);
		System.out.println("ArrivalRowCount :"+ArrivalRowCount);
		for(int RowCount=1;RowCount<=ArrivalRowCount;RowCount++)
		{
			GenericMethods.pauseExecution(2000);
			AppReusableMethods.selectMenu(driver, ETransMenu.AirArrivals,"Arrival Confirmation", ScenarioDetailsHashMap);
			System.out.println("RowCount running :"+RowCount);
			searchMAWB(driver, ScenarioDetailsHashMap, RowCount);
			arrival(driver, ScenarioDetailsHashMap, RowCount);
			//mawbChargesCost(driver, ScenarioDetailsHashMap, RowCount);
			arrivalNotice(driver, ScenarioDetailsHashMap, RowCount);
			notesAndStatus(driver, ScenarioDetailsHashMap, RowCount);
			if(ExcelUtils.getCellData("Air_Arrival", RowCount, "DeliveryInstructionRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				deliveryInstructions(driver, ScenarioDetailsHashMap, RowCount);
			}

			GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(3000);
			if(ExcelUtils.getCellData("Air_Arrival", RowCount, "DeliveryInstructionRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				String DeliveryInstructionSummary = GenericMethods.getInnerText(driver, null, orAirArrival.getElement(driver, "DeliveryInstructionMSG", ScenarioDetailsHashMap, 2), 2);
				System.out.println("DeliveryInstructionSummary :"+DeliveryInstructionSummary.split(" : ")[1]);
				GenericMethods.assertTwoValues(DeliveryInstructionSummary.split(" : ")[1], ExcelUtils.getCellData("Air_Arrival", RowNo, "DeliveryInstruction_MSG", ScenarioDetailsHashMap), "Verifying Delivery Instruction message", ScenarioDetailsHashMap);
			}
			else
			{
				String ArrivalconfirmationSummary = GenericMethods.getInnerText(driver, null, orAirArrival.getElement(driver, "ArrivalConfirmation_MSG", ScenarioDetailsHashMap, 2), 2);
				System.out.println("ArrivalconfirmationSummary :"+ArrivalconfirmationSummary.split(" : ")[1]);
				GenericMethods.assertTwoValues(ArrivalconfirmationSummary.split(" : ")[1], ExcelUtils.getCellData("Air_Arrival", RowNo, "Arrivalconfirmation_MSG", ScenarioDetailsHashMap), "Verifying Arrivalconfirmation message for air shipment", ScenarioDetailsHashMap);

			}
			if(ExcelUtils.getCellData("Air_Arrival", RowCount, "HoldReason", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				releaseHouseDestination(driver, ScenarioDetailsHashMap, RowCount);
			}

		}

	}
	public static void searchMAWB(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo)
	{
		if(ExcelUtils.getCellData("Air_MAWB", RowNo, "HBLRequiredToUpdate", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			ExcelUtils.setCellData_Without_DataSet("Air_Arrival", RowNo, "MAWB_ID", ExcelUtils.getCellData("Air_MAWB", RowNo, "MAWBID", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		}
		if(ExcelUtils.getCellData("Air_Arrival", RowNo, "ShipmentType", ScenarioDetailsHashMap).equalsIgnoreCase("Direct"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "Search_MawbId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_Arrival", RowNo,"HAWB_ID", ScenarioDetailsHashMap), 2);
		}
		else{
			GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "Search_MawbId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_Arrival", RowNo,"MAWB_ID", ScenarioDetailsHashMap), 2);
		}
		//		GenericMethods.replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "SearchMawbId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_Arrival", RowNo,"MAWB_ID", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "MawbSearch_DateFrom", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_Arrival", RowNo, "ArrivalDateFrom", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "MawbSearch_DateTo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_Arrival", RowNo, "ArrivalDateTo", ScenarioDetailsHashMap), 10);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
		//GenericMethods.clickElement(driver, null,orAirArrival.getElement(driver, "MblIdSelectCheckbox1", ScenarioDetailsHashMap, 10), 2);
	}
	public static void arrival(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		GenericMethods.pauseExecution(2000);
		GenericMethods.clickElement(driver, null,orAirArrival.getElement(driver, "Tab_MainDetails", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "Arrival_ConfirmDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_Arrival", RowNo,"ATADate", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "Arrival_ConfirmTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_Arrival", RowNo,"ATATime", ScenarioDetailsHashMap), 2);
		//System.out.println("HouseDeconsolidationStatus :"+driver.findElement(By.name("houseDeconStatus")).getText());
		String PartShipment =  ExcelUtils.getCellData("Air_Arrival", RowNo, "PartShipment", ScenarioDetailsHashMap);
		if(PartShipment.equalsIgnoreCase("Yes"))
		{
			String LoadType =  ExcelUtils.getCellData("Air_Arrival", RowNo, "LoadType", ScenarioDetailsHashMap);
			System.out.println("LoadType :"+LoadType);
			if(LoadType.equalsIgnoreCase("Loose"))
			{
				for(int datasetNo=1;datasetNo<=driver.findElements(By.xpath("html/body/form/div/table/tbody/tr")).size();datasetNo++)
				{
					GenericMethods.clickElement(driver, By.xpath("html/body/form/div/table/tbody/tr["+datasetNo+"]"), null, 2);
					GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "ServiceLevel", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_Arrival", RowNo, "ServiceLevel", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "ServiceType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_Arrival", RowNo, "ServiceType", ScenarioDetailsHashMap), 2);
					System.out.println("Hod Reason =="+ExcelUtils.getCellData("Air_Arrival",RowNo, "HoldReason", ScenarioDetailsHashMap));
					if(ExcelUtils.getCellData("Air_Arrival",RowNo, "HoldReason", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
					{
						if(driver.findElement(By.id("hold")).getAttribute("value").equalsIgnoreCase("N"))
						{
							GenericMethods.clickElement(driver, null, orAirArrival.getElement(driver, "HouseHoldCheckBox", ScenarioDetailsHashMap, 10), 2);
							AppReusableMethods.selectValueFromLov(driver,  orAirArrival.getElement(driver, "ReasonCodeLOV", ScenarioDetailsHashMap, 2), orAirArrival, "Reason_Code",ExcelUtils.getCellData("Air_Arrival", RowNo, "ReasonCode", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
							GenericMethods.pauseExecution(2000);
						}
					}	


					if(driver.findElement(By.id("igmPartChk")).getAttribute("value").equalsIgnoreCase("N"))
					{
						GenericMethods.clickElement(driver, null, orAirArrival.getElement(driver, "PartShipment", ScenarioDetailsHashMap, 10), 2);
						try{
							GenericMethods.handleAlert(driver, "accept");
						}catch (Exception e) {
							//System.out.println("no Alerts present");
						}
					}
					GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "CurrentPieces", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("Air_Arrival", datasetNo, "Current_Pieces", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "CurrentWeight", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("Air_Arrival", datasetNo, "Current_Weight", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "CurrentVolume", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("Air_Arrival", datasetNo, "Current_Volume", ScenarioDetailsHashMap), 2);

					if(ExcelUtils.getCellData("Air_Arrival", RowNo, "Delivery_Required", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
					{
						GenericMethods.clickElement(driver, null, orAirArrival.getElement(driver, "Delivery_Required", ScenarioDetailsHashMap, 2), 4);
						System.out.println("Delivery Required clicked");
					}
					GenericMethods.clickElement(driver, null, orAirArrival.getElement(driver, "GridEditButton", ScenarioDetailsHashMap, 2), 10);
					GenericMethods.pauseExecution(3000);
					GenericMethods.clickElement(driver, By.xpath("html/body/form/div/table/tbody/tr["+datasetNo+"]"), null, 2);
				}
			}
		}
		else
		{
			GenericMethods.clickElement(driver, By.xpath("html/body/form/div/table/tbody/tr["+1+"]"), null, 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "ServiceLevel", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_Arrival", RowNo, "ServiceLevel", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "ServiceType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_Arrival", RowNo, "ServiceType", ScenarioDetailsHashMap), 2);
			System.out.println("Hod Reason =="+ExcelUtils.getCellData("Air_Arrival",RowNo, "HoldReason", ScenarioDetailsHashMap));
			if(ExcelUtils.getCellData("Air_Arrival", RowNo, "HoldReason", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				
				if(driver.findElement(By.id("hold")).getAttribute("value").equalsIgnoreCase("N"))
				{
					GenericMethods.clickElement(driver, null, orAirArrival.getElement(driver, "HouseHoldCheckBox", ScenarioDetailsHashMap, 10), 2);
					AppReusableMethods.selectValueFromLov(driver,  orAirArrival.getElement(driver, "ReasonCodeLOV", ScenarioDetailsHashMap, 2), orAirArrival, "Reason_Code",ExcelUtils.getCellData("Air_Arrival", RowNo, "ReasonCode", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					GenericMethods.pauseExecution(2000);
				}
			}	
			if(ExcelUtils.getCellData("Air_Arrival", RowNo, "Delivery_Required", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				GenericMethods.clickElement(driver, null, orAirArrival.getElement(driver, "Delivery_Required", ScenarioDetailsHashMap, 2), 4);
				System.out.println("Delivery Required clicked");
			}
			GenericMethods.clickElement(driver, null, orAirArrival.getElement(driver, "GridEditButton", ScenarioDetailsHashMap, 2), 10);
			GenericMethods.pauseExecution(2000);
		}
		//GenericMethods.clickElement(driver, null, orOceanArrival.getElement(driver, "GridEditButton", ScenarioDetailsHashMap, 2), 10);
		GenericMethods.clickElement(driver, By.xpath("html/body/form/div/table/tbody/tr[1]"), null, 10);
		GenericMethods.pauseExecution(2000);
	}
	public static void mawbChargesCost(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		GenericMethods.clickElement(driver, null,orAirArrival.getElement(driver, "MAWBCHARGESCOST_Tab", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(4000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		String ArrivalconfirmationSummary = GenericMethods.getInnerText(driver, null, orAirArrival.getElement(driver, "Arrival_Confirmation_MSG", ScenarioDetailsHashMap, 2), 2);
		System.out.println("ArrivalconfirmationSummary :"+ArrivalconfirmationSummary.split("#")[0]);
		GenericMethods.assertTwoValues(ArrivalconfirmationSummary.split("#")[0], ExcelUtils.getCellData("Air_Arrival", RowNo, "Arrivalconfirmation_MSG", ScenarioDetailsHashMap), "Verifying Arrivalconfirmation message", ScenarioDetailsHashMap);
		//GenericMethods.clickElement(driver, null, oceanArrival.getElement(driver, "conformationText", ScenarioDetailsHashMap, 10), 2);
	}
	public static void arrivalNotice(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		GenericMethods.clickElement(driver, null,orAirArrival.getElement(driver, "Tab_ArrivalNotice", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(4000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		/*String ChargesSummary = GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "ChargesValidationMSG", ScenarioDetailsHashMap, 2), 2);
		System.out.println("ArrivalconfirmationSummary :"+ChargesSummary.split(" : ")[1]);
		GenericMethods.assertTwoValues(ChargesSummary.split(" : ")[1], ExcelUtils.getCellData("Air_Arrival", RowNo, "ChargesValidation_MSG", ScenarioDetailsHashMap), "Verifying Charges Validation message", ScenarioDetailsHashMap);
		 */
	}
	public static void notesAndStatus(WebDriver driver,
			HashMap<String, String> ScenarioDetailsHashMap, int RowNo) {
		GenericMethods.clickElement(driver, null,orAirArrival.getElement(driver, "Tab_NotesStatus", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
	}
	public static void deliveryInstructions(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		GenericMethods.clickElement(driver, null,orAirArrival.getElement(driver, "Air_DeliveryInstructions", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(5000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}

		int RowCount_ArrivalDI = ExcelUtils.getCellDataRowCount("Air_Arrival_DeliveryInstruction", ScenarioDetailsHashMap);
		for(int DIRowCount=1;DIRowCount<=RowCount_ArrivalDI;DIRowCount++)
		{
			System.out.println("Delivery Type :"+ExcelUtils.getCellData("Air_Arrival_DeliveryInstruction", DIRowCount, "DeliveryType", ScenarioDetailsHashMap));
			System.out.println("DIRowCount :"+DIRowCount);
			GenericMethods.pauseExecution(2000);
			Select DeliveryTypeselect=new Select(orAirArrival.getElement(driver, "DeliveryInstructions_DeliveryType", ScenarioDetailsHashMap, 10));
			DeliveryTypeselect.selectByVisibleText(ExcelUtils.getCellData("Air_Arrival_DeliveryInstruction", DIRowCount, "DeliveryType", ScenarioDetailsHashMap));
			Select Delivery_StatusSelect=new Select(orAirArrival.getElement(driver, "Delivery_Status", ScenarioDetailsHashMap, 10));
			Delivery_StatusSelect.selectByVisibleText(ExcelUtils.getCellData("Air_Arrival_DeliveryInstruction", DIRowCount, "DeliveryStatus", ScenarioDetailsHashMap));
			Select typeOfMovementSelect=new Select(orAirArrival.getElement(driver, "typeOfMovement", ScenarioDetailsHashMap, 10));
			typeOfMovementSelect.selectByVisibleText(ExcelUtils.getCellData("Air_Arrival_DeliveryInstruction", DIRowCount, "TypeOfMovement", ScenarioDetailsHashMap));
			AppReusableMethods.selectValueFromLov(driver,  orAirArrival.getElement(driver, "PickupPartyIdSrchBtn", ScenarioDetailsHashMap, 2), orAirArrival, "PartyId",ExcelUtils.getCellData("Air_Arrival_DeliveryInstruction", DIRowCount, "PickupPartyIdSrch", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			//AppReusableMethods.selectValueFromLov(driver,  orOceanArrival.getElement(driver, "PickupLocationBtn", ScenarioDetailsHashMap, 2), orOceanArrival, "FirmsCode",ExcelUtils.getCellData("Air_Arrival_DeliveryInstruction", DIRowCount, "Pickup_Location", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			//AppReusableMethods.selectValueFromLov(driver,  orOceanArrival.getElement(driver, "PickupLocation_DestBtn", ScenarioDetailsHashMap, 2), orOceanArrival, "FirmsCode",ExcelUtils.getCellData("Air_Arrival_DeliveryInstruction", DIRowCount, "Pickup_Location", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "Pickup_Date", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_Arrival_DeliveryInstruction", DIRowCount, "PickupDate", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "PickupTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_Arrival_DeliveryInstruction", DIRowCount, "PickupTime", ScenarioDetailsHashMap), 2);
			AppReusableMethods.selectValueFromLov(driver,  orAirArrival.getElement(driver, "DeliveryPartyIdSrchBtn", ScenarioDetailsHashMap, 2), orAirArrival, "PartyId",ExcelUtils.getCellData("Air_Arrival_DeliveryInstruction", DIRowCount, "DeliveryPartyIdSrch", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			//AppReusableMethods.selectValueFromLov(driver,  orOceanArrival.getElement(driver, "DeliveryLocationBtn", ScenarioDetailsHashMap, 2), orOceanArrival, "FirmsCode",ExcelUtils.getCellData("Air_Arrival_DeliveryInstruction", DIRowCount, "Delivery_Location", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "RequiredDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_Arrival_DeliveryInstruction", DIRowCount, "Required_Date", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "RequiredTime", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_Arrival_DeliveryInstruction", DIRowCount, "Required_Time", ScenarioDetailsHashMap), 2);
			GenericMethods.selectOptionFromDropDown(driver, null, orAirArrival.getElement(driver, "PodType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_Arrival_DeliveryInstruction", DIRowCount, "PodType", ScenarioDetailsHashMap));
			if(ExcelUtils.getCellData("Air_Arrival_DeliveryInstruction", DIRowCount, "DeliveryStatus", ScenarioDetailsHashMap).equalsIgnoreCase("Partial"))
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
					if(!GenericMethods.isChecked(orAirArrival.getElement(driver, "HouseDetails_CheckBox", ScenarioDetailsHashMap, 10)))
					{
						GenericMethods.clickElement(driver, By.xpath("html/body/form/table/tbody/tr[4]/td/fieldset[2]/div/table/tbody/tr/td[3]/fieldset/table/tbody/tr/td/div/table/tbody/tr["+HouseCount+"]/td[2]/input[3]"), null, 2);
					}
				}
				GenericMethods.pauseExecution(3000);
				GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "HouseTotalPartialPieces", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("Air_Arrival_DeliveryInstruction", DIRowCount, "TotalPartialHousePieces", ScenarioDetailsHashMap), 2);
				GenericMethods.pauseExecution(3000);
				GenericMethods.clickElement(driver, null, orAirArrival.getElement(driver, "HouseTotalPartialgrossWeight", ScenarioDetailsHashMap, 10), 2);
				System.out.println("Typed");
			}
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null,orAirArrival.getElement(driver, "DeliveryInstructionsADDButton", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(4000);
		}
	}
	public static void releaseHouseDestination(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{

		AppReusableMethods.selectMenu(driver, ETransMenu.airReleaseHouse,"Release Destination HAWB", ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(3000);
		System.out.println("HBL ID :"+ExcelUtils.getCellData("Air_Arrival", RowNo, "HAWB_ID", ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "Hawb_No", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_Arrival", RowNo, "HAWB_ID", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "ReleaseHouseSearch_DateFrom", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_Arrival", RowNo, "ArrivalDateFrom", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orAirArrival.getElement(driver, "ReleaseHouseSearch_DateTo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_Arrival", RowNo, "ArrivalDateTo", ScenarioDetailsHashMap), 10);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		GenericMethods.pauseExecution(2000);
		String ReleaseHouseMSG_Summary = GenericMethods.getInnerText(driver, null, orAirArrival.getElement(driver, "ReleaseHouse_Success_MSG", ScenarioDetailsHashMap, 2), 2);
		System.out.println("ReleaseHouseMSG_Summary :"+ReleaseHouseMSG_Summary.split(" : ")[1].split(":")[0]);
		GenericMethods.assertTwoValues(ReleaseHouseMSG_Summary.split(" : ")[1].split(":")[0], ExcelUtils.getCellData("Air_Arrival", RowNo, "ReleaseHouse_SuccessMSG", ScenarioDetailsHashMap), "Verifying Release House Success Message", ScenarioDetailsHashMap);

	}
	
	public static void arrivalDI_After_DestinationHAWBDI(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		GenericMethods.pauseExecution(2000);
		AppReusableMethods.selectMenu(driver, ETransMenu.AirArrivals,"Arrival Confirmation", ScenarioDetailsHashMap);
		searchMAWB(driver, ScenarioDetailsHashMap, RowNo);
		if(ExcelUtils.getCellData("Air_Arrival", RowNo, "DeliveryInstructionRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			deliveryInstructions(driver, ScenarioDetailsHashMap, RowNo);
		}
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
	}
	
}
