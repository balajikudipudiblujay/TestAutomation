package business.scenarios;

import global.reusables.ExcelUtils;
import global.reusables.GenerateControlfiles;
import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;

import java.awt.Robot;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.event.KeyEvent;

import DriverMethods.WebDriverInitilization;
import app.reuseables.AppReusableMethods;
import app.reuseables.ETransMenu;

public class SeaBooking extends WebDriverInitilization{
	static String knownShipper;
	static ObjectRepository orCommon = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/CommonObjects.xml");
	static ObjectRepository orSeaBooking = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/SeaBooking.xml");
	static ObjectRepository orDecisionTable = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/DecisionTable.xml");
	public static void copySeaBookingDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) {
		AppReusableMethods.selectMenu(driver, ETransMenu.seaBooking,"Sea Booking",ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(6000);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "CopyButton", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(12000);
		seaBookingCargoPackDetails(driver, ScenarioDetailsHashMap, RowNo);
	}
	public static void seaBookingDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) {

		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "LoadTypeSBC",ScenarioDetailsHashMap).equalsIgnoreCase("FCL")){
			System.out.println("ÏNSIDE-------------------IF"+ExcelUtils.getCellData("SE_BookingMBLMainDetails",  RowNo, "NoOfContainers",ScenarioDetailsHashMap));
			//GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Textbox_NoOfContainers",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMBLMainDetails",  RowNo, "NoOfContainers",ScenarioDetailsHashMap), 5);
		}
		AppReusableMethods.selectMenu(driver, ETransMenu.seaBooking, "Sea Booking",ScenarioDetailsHashMap);

		//RoutePlan_Details(driver, ScenarioDetailsHashMap, RowNo);
		//seaBookingContainerDetails(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.pauseExecution(2000);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(20000);
		//ExcelUtils.getAllCellValuesOfColumn("SE_BookingMainDetails", "BookingId",ScenarioDetailsHashMap);

		/*
		 * GenericMethods.selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "ShipmentType", 10),ExcelUtils.getCellData("EXBookingMainDetails", RowNo, "ShipmentType",ScenarioDetailsHashMap));
		 * GenericMethods.selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "Consolidation", 10),ExcelUtils.getCellData("EXBookingMainDetails", RowNo,"Consolidation", ScenarioDetailsHashMap));
		 */

		//Pavan FUNC017.4 Below condtion will perform TimeZone validation for Created Date and Time
		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Time_Zone_Required",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			AppReusableMethods.validateTimeZoneFormat(driver, orSeaBooking.getElement(driver, "CreatedDate", ScenarioDetailsHashMap,10), orSeaBooking.getElement(driver, "CreatedTime", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Time_Zone_Value", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		}//End FUNC017.4

		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "BookingId_Type",ScenarioDetailsHashMap).equalsIgnoreCase("Manual"))
		{
			GenericMethods.clickElement(driver, null, orSeaBooking.getElement(driver, "Rdb_BookingID", ScenarioDetailsHashMap,10),10);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Textbox_BookingId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"BookingId_Manual", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(5), 2);

		}

		//Sandeep - Below method is to verify  functional validation FUNC064
		System.out.println("Terminal="+ExcelUtils.getCellData("Terminals", RowNo, "Sea_OriginTerminal", ScenarioDetailsHashMap));
		//GenericMethods.assertTwoValues(driver.findElement(By.id("originTerminalId")).getAttribute("value"), (ExcelUtils.getCellData("Terminals", RowNo, "Sea_OriginTerminal", ScenarioDetailsHashMap)).split("#")[0], "FUNC064 Verifying Department Defaultation with Branch mapped with single department", ScenarioDetailsHashMap);
		//code ends here
		GenericMethods.pauseExecution(9000);
		GenericMethods.selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "Booking_ShipmentType", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ShipmentType",ScenarioDetailsHashMap));
		//System.out.println("rowno***"+RowNo);
		//Sandeep,Jan15th2015,Below if condition will be executed if ShipmentType = House, if not, else condition will be executed where in OrderType details will be selected.
		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ShipmentType",ScenarioDetailsHashMap).equalsIgnoreCase("House")){
			System.out.println("va"+ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "HouseType",ScenarioDetailsHashMap));
			GenericMethods.selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "HouseType", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "HouseType",ScenarioDetailsHashMap));
			//GenericMethods.selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "HouseType", ScenarioDetailsHashMap,10), "Consolidation");		

		}
		else
		{
			GenericMethods.selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "HBLOrderType", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "OrderType",ScenarioDetailsHashMap));
		}


		GenericMethods.selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "LoadType", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "LoadTypeSBC",ScenarioDetailsHashMap));

		AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_Customer", ScenarioDetailsHashMap,10), orSeaBooking, "PartyId", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"PartyId", ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_OriginDept",ScenarioDetailsHashMap, 10), orSeaBooking, "OrgCode",ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"OriginDept", ScenarioDetailsHashMap),ScenarioDetailsHashMap);

		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"DestBranch", ScenarioDetailsHashMap)!="")
		{
			AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_DestinationBranch",ScenarioDetailsHashMap,10), orSeaBooking, "PartyId",ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"DestBranch", ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		}


		//	GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Btn_DestDeptId", 10), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "DestDeptId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"DestDept", ScenarioDetailsHashMap), 2);
		/*AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_DestDeptId", 10), orSeaBooking, "PartyId",ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"DestDept", ScenarioDetailsHashMap));*/


		GenericMethods.selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "FreightTerms", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "FreightTerms",ScenarioDetailsHashMap));


		AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_ServiceLevelImage",ScenarioDetailsHashMap, 10), orSeaBooking,"OrgCode", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ServiceLevel",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_ServiceTypeOrg",ScenarioDetailsHashMap, 10), orSeaBooking,"ServiceType", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ServiceTypeOrig",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_ServiceTypeDest",ScenarioDetailsHashMap, 10), orSeaBooking,"ServiceType", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ServiceTypeDest",ScenarioDetailsHashMap),ScenarioDetailsHashMap);



		//Sandeep- FUNC018.1- Below method is added to perform party ID mode validation.
		if(!ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PartyIdModeRequired",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_Shipper",ScenarioDetailsHashMap, 10), orSeaBooking,"PartyId", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Shipper_PartyId",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		}
		//GenericMethods.selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "PickUpRequired", 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PickUpRequired",ScenarioDetailsHashMap));

		//Sandeep- FUNC018.1- Below method is added to perform party ID mode validation.
		// if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PartyIdModeRequired",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		else
		{
			if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PartyIdModeType",ScenarioDetailsHashMap).equalsIgnoreCase("PartyNickName"))
			{
				String PartyNickName = AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_Shipper",ScenarioDetailsHashMap, 10), orSeaBooking,"PartyId", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Shipper_PartyId",ScenarioDetailsHashMap),"PartyNickName",ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, By.id("shipperIdNickName"), null, Keys.TAB, 30);
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(driver.findElement(By.id("shipperIdNickName")).getAttribute("previousvalue"), PartyNickName, "Verifying whether Shipper Name is displayed", ScenarioDetailsHashMap);
			}
			else
			{

				AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_Shipper",ScenarioDetailsHashMap, 10), orSeaBooking,"PartyId", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Shipper_PartyId",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, By.id("shipperIdNickName"), null, Keys.TAB, 30);
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(driver.findElement(By.id("shipperIdNickName")).getAttribute("previousvalue"), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Shipper_PartyId",ScenarioDetailsHashMap), "Verifying whether Shipper Is is displayed", ScenarioDetailsHashMap);
			}
		}

if(!(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "LoadTypeSBC", ScenarioDetailsHashMap).equalsIgnoreCase("RORO")||ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "LoadTypeSBC", ScenarioDetailsHashMap).equalsIgnoreCase("BREAKBULK")))
{
		GenericMethods.selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "AirPickupRequired", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PickUp_Required", ScenarioDetailsHashMap));
}
		//Pavan FUNC010.5,FUNC010.6 --Below condition will perform Validations
if(!(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "LoadTypeSBC", ScenarioDetailsHashMap).equalsIgnoreCase("RORO")||ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "LoadTypeSBC", ScenarioDetailsHashMap).equalsIgnoreCase("BREAKBULK")))
{
		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PickUp_Required",ScenarioDetailsHashMap).equalsIgnoreCase("PickUp Instructions"))
		{
			GenericMethods.assertValue(driver, null, orSeaBooking.getElement(driver, "Textbox_PickupInstructions_Party",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PickUpNickName", ScenarioDetailsHashMap), "PickUpInstruction_Party", "Validating Pickup Party after Selecting PickUpInstruction", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orSeaBooking.getElement(driver, "Tab_PickUpInstructions",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PickUp_Required", ScenarioDetailsHashMap), "PickUpInstruction_Party", "Validating PickUpInstructions Tab", ScenarioDetailsHashMap);

		}
}//FUNC010.5,FUNC010.6 End 




		//Sandeep- FUNC018.1- Below method is added to perform party ID mode validation.
		if(!ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PartyIdModeRequired",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_Consignee",ScenarioDetailsHashMap, 10), orSeaBooking, "PartyId", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ConsigneeNickName", ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		}

		//Sandeep- FUNC018.1- Below method is added to perform party ID mode validation.
		//if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PartyIdModeRequired",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		else
		{
			if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PartyIdModeType",ScenarioDetailsHashMap).equalsIgnoreCase("PartyNickName"))
			{
				String PartyNickName = AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_Consignee",ScenarioDetailsHashMap, 10), orSeaBooking,"PartyId", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ConsigneeNickName",ScenarioDetailsHashMap),"PartyNickName",ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, By.id("consigneeIdNickName"), null, Keys.TAB, 30);
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(driver.findElement(By.id("consigneeIdNickName")).getAttribute("previousvalue"), PartyNickName, "Verifying whether Consignee Name is displayed", ScenarioDetailsHashMap);
			}
			else
			{
				AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_Consignee",ScenarioDetailsHashMap, 10), orSeaBooking, "PartyId", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ConsigneeNickName", ScenarioDetailsHashMap),ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, By.id("consigneeIdNickName"), null, Keys.TAB, 30);
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(driver.findElement(By.id("consigneeIdNickName")).getAttribute("previousvalue"), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ConsigneeNickName",ScenarioDetailsHashMap), "Verifying whether Consignee Name is displayed", ScenarioDetailsHashMap);
			}
		}//code ends here

		//Pavan FUNC013.1 --Below condition will enter Notes related Shipper and Consignee inorder to get Notes and alert
		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Notes_Alert_Required",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Shipper",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Notes_Shipper",ScenarioDetailsHashMap), 10);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Consignee",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Notes_Consignee",ScenarioDetailsHashMap), 10);

		}//End FUNC013.1
		
		//Sangeeta DeliveryRequired valiadtion FUNC-65
		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "DeliveryRequiredValidation", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			GenericMethods.pauseExecution(3000);

			GenericMethods.pauseExecution(3000);
			Select DelReqd =  new Select(driver.findElement(By.id("deliveryRequired")));
			DelReqd.selectByVisibleText(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "DeliveryRequired", ScenarioDetailsHashMap));
		//GenericMethods.selectOptionFromDropDown(driver, null,orSeaBooking.getElement(driver, "DeliveryRequired", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "DeliveryRequired", ScenarioDetailsHashMap));
		}

		//code added by Masthan for Port Codes Validation
		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "UNLIATAReq",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			SelectPortwithValidations(driver,"Btn_PortOfLoading",ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PortOfLoading",ScenarioDetailsHashMap),ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"POLUNLIATASheduleCodes",ScenarioDetailsHashMap),ScenarioDetailsHashMap,RowNo);
			SelectPortwithValidations(driver,"Btn_PortOfDispatch",ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PortOfDischarge",ScenarioDetailsHashMap),ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"PODUNLIATASheduleCodes",ScenarioDetailsHashMap),ScenarioDetailsHashMap,RowNo);
		}else{
			AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_PortOfLoading", ScenarioDetailsHashMap,10), orSeaBooking,"LocationCode", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PortOfLoading",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_PortOfDispatch", ScenarioDetailsHashMap,10), orSeaBooking,"LocationCode", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PortOfDischarge",ScenarioDetailsHashMap),ScenarioDetailsHashMap);	
		}

		//Code ends here-Masthan

		//Pavan FUNC007 --Below condition will perform Validations 
		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Location_Mandatory",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			Location_Terminal_Validations(driver, ScenarioDetailsHashMap, RowNo);
		}
		else{
			AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_PortOfLoading", ScenarioDetailsHashMap,10), orSeaBooking,"LocationCode", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PortOfLoading",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_PortOfDispatch", ScenarioDetailsHashMap,10), orSeaBooking,"LocationCode", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PortOfDischarge",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		}//FUNC007 End

		//Srikala-Func001.1 known shipper
		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "MDM_KnownShipper",ScenarioDetailsHashMap)!=null &&
				ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "MDM_KnownShipper",ScenarioDetailsHashMap).equalsIgnoreCase("YES"))
		{
			knownshipperValidation(driver, ScenarioDetailsHashMap, RowNo);
		}
		//End here

		//Sandeep- Below script is added for INTTRA Functionality
		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Inttra_Required", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "SeaPlaceofReceipt", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PlaceofReceipt", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "SeaPlaceofDelivery", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PlaceofDelivery", ScenarioDetailsHashMap), 2);
		}
		
		
		//eRatingValidation starts here

		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "eRatingValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "SeaPlaceofReceipt", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PlaceofReceipt", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "SeaPlaceofDelivery", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PlaceofDelivery", ScenarioDetailsHashMap), 2);
			
			if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "RatingType", ScenarioDetailsHashMap).equalsIgnoreCase("ManualRate"))
			{
				GenericMethods.selectOptionFromDropDown(driver, null,orSeaBooking.getElement(driver, "contract_Quote_TariffDropdown", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Contract_Quote_Tariff", ScenarioDetailsHashMap));
				GenericMethods.clickElement(driver, null, orSeaBooking.getElement(driver, "contract_Quote_TariffLOV", ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(2000);
				GenericMethods.selectWindow(driver);
				GenericMethods.pauseExecution(8000);
				GenericMethods.clickElement(driver, null, orSeaBooking.getElement(driver, "Search_Origin", ScenarioDetailsHashMap, 10), 2);
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




		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "ETAFinalDestDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"ETAFinalDest", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "ETAFinalDestTime", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"ETAFinalTime", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "InstructionsRemarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"InstructionsRemarks", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "ShipperReferance", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"ShipperReference", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "PickUpReferance",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"PickUpReference", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "ConsigneeReferance", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"ConsigneeReference", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "DeliveryReferance", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"DeliveryReference", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "CargoReadyDate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"CargoReadyDate", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "CargoReadyDateTime", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"CargoReadyTime", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "PickUpDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PickUpDate",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "PickUpDateTime", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PickUpTime",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "RequiredDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "RequiredDate",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "RequiredDateTime", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"RequiredTime", ScenarioDetailsHashMap), 2);

		//Date Validation start:Author Sangeeta
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "DocRequiredDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "DocumentsRequiredDate",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "DocRequiredTime", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"DocumentsRequiredTime", ScenarioDetailsHashMap), 2);

		//FUNC062.1-Date Validation(Cargo Ready Date / Time)
		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "DateValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			String ActualCargoReadyDate =  driver.findElement(By.id("cargoReadyDate")).getAttribute("value");
			String ActualCargoReadyTime =  driver.findElement(By.name("cargoReadyDateTime")).getAttribute("value");
			String ExpectedCargoReadyDate = ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "CarrierCutoffDate", ScenarioDetailsHashMap);
			String ExpectedCargoReadyTime = ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "CarrierCutoffTime", ScenarioDetailsHashMap);
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
			String ExpectedPickUpDate = ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "ServiceCutoffDate", ScenarioDetailsHashMap);
			String ExpectedPickUpTime = ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "ServiceCutoffTime", ScenarioDetailsHashMap);
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
			String ExpectedRequiredDate = ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "ETAFinalDest", ScenarioDetailsHashMap);
			String ExpectedRequiredTime = ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "ETAFinalDestTime", ScenarioDetailsHashMap);
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
			String ExpectedDocRequiredDate = ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "CarrierCutoffDate", ScenarioDetailsHashMap);
			String ExpectedDocRequiredTime = ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "CarrierCutoffTime", ScenarioDetailsHashMap);
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
			String ExpectedEtaFinalDestDate = ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "ETA", ScenarioDetailsHashMap);    
			String ExpectedEtaFinalDestTime = ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "ETATime", ScenarioDetailsHashMap);
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


		if( ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "CarrierBookingReferenceRequired",ScenarioDetailsHashMap).equalsIgnoreCase("yes")){
			AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Button_CarrierBookingRef", ScenarioDetailsHashMap,10), orSeaBooking,"Zoom_CarrierBookingRef_CarrierBookingIDTextbox", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "CarrierBookingReference",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		}



		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}


		if( ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Assembly_HBLID_Required",ScenarioDetailsHashMap).equalsIgnoreCase("yes")){
			AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "LOV_HBLAssemblyID", ScenarioDetailsHashMap,10), orSeaBooking,"Textbox_LOV_HBLID", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Assembly_HBLID",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(3000);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "RequiredDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "RequiredDate",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "RequiredDateTime", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"RequiredTime", ScenarioDetailsHashMap), 2);
			seaBookingCargoPackDetails(driver, ScenarioDetailsHashMap, RowNo);
			try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
		}

		//Priya: Below code is to check Func01.4
		String shipper="";

		try{
			shipper=ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "POAShipper", ScenarioDetailsHashMap);
			System.out.println("shipper"+shipper);
		}catch (NullPointerException e) {

			shipper="";
		}
		if(!shipper.equals("")){
			AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_Shipper",ScenarioDetailsHashMap, 10), orSeaBooking,"PartyId", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "POAShipper",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_CargoDetails", ScenarioDetailsHashMap,10), 2);
			GenericMethods.pauseExecution(2000);
			System.out.println("alert msg ="+GenericMethods.getAlertText(driver));
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			System.out.println("alertText"+alertText);
			String alertActualText = (alertText.split(" : ")[1].trim())+ " : "+(alertText.split(" : ")[2].trim());
			System.out.println("alertActualText"+alertActualText);
			System.out.println("alertExpectedText"+ ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "AlertMessage",ScenarioDetailsHashMap));
			GenericMethods.assertTwoValues(alertActualText, ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "POAMsg",ScenarioDetailsHashMap), "Validating Power of Attorney Expiry Date is expired", ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(3000);
			//GenericMethods.assertAlertMessage(driver, ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "POAMsg", ScenarioDetailsHashMap), "POA Alert MSg", "Validateing Power of Attorney Expiry Date is expired ", ScenarioDetailsHashMap);
	GenericMethods.handleAlert(driver, "dismiss");
			AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_Shipper",ScenarioDetailsHashMap, 10), orSeaBooking,"PartyId", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Shipper_PartyId",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		}



		// MBL MainDetails
		if (!ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "HouseType",ScenarioDetailsHashMap).equalsIgnoreCase("Consolidation")
				&& !ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "HBLBasis",ScenarioDetailsHashMap).equalsIgnoreCase("Assembly Shipment")
		) {
			seaBookingMBLMainDetails(driver, ScenarioDetailsHashMap, RowNo);	
		}
		/*if( ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "CarrierBookingReferenceRequired",ScenarioDetailsHashMap).equalsIgnoreCase("yes")
				&& ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "LoadTypeSBC",ScenarioDetailsHashMap).equalsIgnoreCase("LCL")		
		)
		{
			GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_CargoDetails", ScenarioDetailsHashMap,10), 2);
			GenericMethods.pauseExecution(6000);
			try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Textbox_TotalPieces", ScenarioDetailsHashMap, 10),"37", 2);
			GenericMethods.pauseExecution(10000);
		}
		if( ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "LoadTypeSBC",ScenarioDetailsHashMap).equalsIgnoreCase("LCL")		
		)
		{
			GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_CargoDetails", ScenarioDetailsHashMap,10), 2);
			GenericMethods.pauseExecution(6000);
			try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Textbox_TotalPieces", ScenarioDetailsHashMap, 10),"37", 2);
			GenericMethods.pauseExecution(10000);
		}*/

		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_CargoDetails", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(6000);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Textbox_TotalPieces", ScenarioDetailsHashMap, 10),"37", 2);
		GenericMethods.pauseExecution(10000);

		/*
		// PickUpInstructions
		seaBookingPickUpInstructions(driver, ScenarioDetailsHashMap, RowNo);

		//eDoc 
		seaBookingeDoc(driver, ScenarioDetailsHashMap, RowNo);
		 */


		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);	
		GenericMethods.pauseExecution(2000);

		//Pavan FUNC013.1 Below condition will Handle Notes and Alerts while saving
		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Notes_Alert_Required",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			AppReusableMethods.checkNotesAndAlert_PopUp(driver, ScenarioDetailsHashMap, RowNo);
			GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_MainDetails", ScenarioDetailsHashMap,10), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Shipper",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Shipper_PartyId",ScenarioDetailsHashMap), 10);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Consignee",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ConsigneeNickName",ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);	
		}//End FUNC013.1 


		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
		String bookingIdSummary=GenericMethods.getInnerText(driver, null, orSeaBooking.getElement(driver, "VerificationMsg", ScenarioDetailsHashMap,20), 2);
		String bookingId = (bookingIdSummary.split(" : ")[2]).split(",")[0];
		String Reference = (bookingIdSummary.split(" : ")[3]).split(",")[0];
		//String bookingId=bookingIdSummary.substring(bookingIdSummary.indexOf(":")+1,bookingIdSummary.indexOf(",")).trim();
		//GenericMethods.assertTwoValues(bookingIdSummary.substring(0, bookingIdSummary.indexOf(":")), "-> Record Successfully Created with Booking Id ", "Validating Creation Msg", ScenarioDetailsHashMap);
		//System.out.println("bookingId :"+bookingId);

		ExcelUtils.setCellData("SE_BookingMainDetails", RowNo, "BookingId", bookingId, ScenarioDetailsHashMap);
		ExcelUtils.setCellData("SE_BookingMainDetails", RowNo, "ReferenceId", Reference, ScenarioDetailsHashMap);
		ExcelUtils.setCellData("SE_HBLMainDetails", RowNo, "BookingId", bookingId, ScenarioDetailsHashMap);

		//ExcelUtils.setCellDataKF("SE_HBLMainDetails",RowNo, "BookingId",bookingId, ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(bookingIdSummary.split(" : ")[1], "Record Successfully Created with Booking Id", "Validating Creation Msg", ScenarioDetailsHashMap);


		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"DeniedRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			for (int GridRowID = 1; GridRowID <= driver.findElements(By.xpath("//table[@id='dataTable']/tbody/tr")).size(); GridRowID++) 
			{
				String xpathprefix = "//table[@id='dataTable']/tbody/tr["+GridRowID+"]/";
				if(GenericMethods.getInnerText(driver, By.xpath(xpathprefix+"td[1]"), null, 10).equalsIgnoreCase(bookingId))
				{
					GenericMethods.assertInnerText(driver, By.xpath(xpathprefix+"td[14]"), null, ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"DeniedStatus", ScenarioDetailsHashMap), "Is Denied", "Verifying DeniedStatus for the shipment before refreshing", ScenarioDetailsHashMap);
					GenericMethods.pauseExecution(4000);
					GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
					GenericMethods.pauseExecution(4000);
					ExcelUtils.setCellData("SE_BookingMainDetails", RowNo, "DeniedStatus", "Y", ScenarioDetailsHashMap);
					GenericMethods.assertInnerText(driver, By.xpath(xpathprefix+"td[14]"), null, ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"DeniedStatus", ScenarioDetailsHashMap), "Is Denied", "Verifying DeniedStatus for the shipment after refreshing", ScenarioDetailsHashMap);
					
					break;
				}

			}
		}



	}
	//Masthan- March 19th 2015
	public static void SelectPortwithValidations(WebDriver driver,String LovImage,String portCode,String UNLIATACodes,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, LovImage, ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectWindow(driver);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver,"LocationCode",ScenarioDetailsHashMap,10),portCode, 10);
		GenericMethods.pauseExecution(2000);
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
		GenericMethods.pauseExecution(4000);
		String Codes=UNLIATACodes;
		String[] data=Codes.split("/");
		String UNL_Grid=driver.findElement(By.id("dtUnilocCode1")).getAttribute("title");
		String IATA_Grid=driver.findElement(By.id("dtIataCode1")).getAttribute("title");
		String SheduleD_Grid=driver.findElement(By.id("dtScheduleDCode1")).getAttribute("title");
		String SheduleK_Grid=driver.findElement(By.id("dtScheduleKCode1")).getAttribute("title");
		GenericMethods.assertTwoValues(UNL_Grid, data[0], "Validating UNILOC code", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(IATA_Grid, data[1], "Validating IATA code", ScenarioDetailsHashMap);
		if(data[3].equalsIgnoreCase("D"))
			GenericMethods.assertTwoValues(SheduleD_Grid, data[2], "Validating Schedule D Code", ScenarioDetailsHashMap);
		if(data[3].equalsIgnoreCase("K"))
			GenericMethods.assertTwoValues(SheduleK_Grid, data[2], "Validating Schedule K Code", ScenarioDetailsHashMap);

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


	public static void seaBookingMBLMainDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		// MBL MainDetails
		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_MBLMainDetails", ScenarioDetailsHashMap,10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");

		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		GenericMethods.pauseExecution(6000);
		AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Img_CarrierId",ScenarioDetailsHashMap, 10), orSeaBooking,	"CarrierId", ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "CarrierId",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Textbox_CarrierBookingRefNo",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "CarrierBookingRefNo",ScenarioDetailsHashMap), 2);
		AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_ServiceLevelImage",ScenarioDetailsHashMap, 10), orSeaBooking,"Code", ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "ServiceLevel",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "CarrierCutOffDate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "CarrierCutoffDate",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "CarrierCutOfTime", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "CarrierCutoffTime",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "ETD",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "ETDTime", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "ETDTime",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo,"ETA", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo,"ETATime", ScenarioDetailsHashMap), 2);

		//Sandeep- Below script is added for INTTRA Functionality
		if(ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "Inttra_Required", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "MBLPlaceOfReceipt",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo,"PlaceofReceipt", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "MBLPlaceOfDelivery",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo,"PlaceofDelivery", ScenarioDetailsHashMap), 2);
		}
		
		//eRatingValidation starts here

		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "eRatingValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "MBLServiceTypeOrigin",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo,"ServiceTyoeOrigin", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "MBLServiceTypeDest",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo,"ServiceTypeDest", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "MBLPlaceOfReceipt",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo,"PlaceofReceipt", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "MBLPortOfLoading",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo,"PortofLoading", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "MBLPortOfDispatch",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo,"PortofDischarge", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "MBLPlaceOfDelivery",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo,"PlaceofDelivery", ScenarioDetailsHashMap), 2);
			if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "RatingType", ScenarioDetailsHashMap).equalsIgnoreCase("ManualRate"))
			{
				GenericMethods.selectOptionFromDropDown(driver, null,orSeaBooking.getElement(driver, "MBLContracttype", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "Contract_Quote_Tariff", ScenarioDetailsHashMap));
				GenericMethods.clickElement(driver, null, orSeaBooking.getElement(driver, "contract_Quote_TariffLOV", ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(2000);
				GenericMethods.selectWindow(driver);
				GenericMethods.pauseExecution(8000);
				GenericMethods.clickElement(driver, null, orSeaBooking.getElement(driver, "Search_Origin", ScenarioDetailsHashMap, 10), 2);
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

		
		
		
		if(ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "VesselScheduleRequired",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Zoom_VesselSchedule",ScenarioDetailsHashMap, 10), orSeaBooking,	"Zoom_VesselSchedule_VesselScheduleIDTextbox", ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "VesselScheduleID",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
			Validate_VesselShedule(driver,ScenarioDetailsHashMap,RowNo);
		}
		else
		{
			for (int RoutePlanRowNo = 1; RoutePlanRowNo <= ExcelUtils.getCellDataRowCount("SE_BookingMBLMainDetails", ScenarioDetailsHashMap); RoutePlanRowNo++) 
			{
				int VesselscheduleRowID = RoutePlanRowNo-1;

				if(RoutePlanRowNo == 1)
				{
					GenericMethods.replaceTextofTextField(driver, By.id("routeVesselName"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_Vessel",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeVoyage"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_Voyage",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtdDate"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_ETDDate",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtdTime"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_ETDTime",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtaDate"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_ETADate",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtaTime"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_ETATime",ScenarioDetailsHashMap), 5);
				}
				else if(RoutePlanRowNo > 1)
				{
					int PODRowID = VesselscheduleRowID-1;
					int RowPlanID =RoutePlanRowNo-1; 
					GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Button_VesselAdd", ScenarioDetailsHashMap,10), 2);
					GenericMethods.replaceTextofTextField(driver, By.id("routePortOfDispatch"+PODRowID+"DKCode"), null, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_POD",ScenarioDetailsHashMap), 5);

					GenericMethods.replaceTextofTextField(driver, By.id("routeEtaDate"+PODRowID), null, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowPlanID, "Container_ETADate",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtaTime"+PODRowID), null, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowPlanID, "Container_ETATime",ScenarioDetailsHashMap), 5);

					GenericMethods.replaceTextofTextField(driver, By.id("routeVesselName"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_Vessel",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeVoyage"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_Voyage",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtdDate"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_ETDDate",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtdTime"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_ETDTime",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtaDate"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_ETADate",ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, By.id("routeEtaTime"+VesselscheduleRowID), null, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_ETATime",ScenarioDetailsHashMap), 5);
				}
			}
		}
		GenericMethods.selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "DDL_ContainerType",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMBLMainDetails", RowNo, "ContainerType",ScenarioDetailsHashMap));

		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "LoadTypeSBC",ScenarioDetailsHashMap).equalsIgnoreCase("FCL")){
			System.out.println("ÏNSIDE-------------------IF");
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Textbox_NoOfContainers",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMBLMainDetails",  RowNo, "NoOfContainers",ScenarioDetailsHashMap), 5);
		}
		try{
			GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Button_ContainerAdd", ScenarioDetailsHashMap,10), 2);}catch(Exception ex){}

	}


	//Masthan-This method is developed for the validations in Vessel Schedule-Masthan

	public static void Validate_VesselShedule(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		Actions action = new Actions(driver); 
		action.sendKeys(Keys.TAB).build().perform();
		GenericMethods.pauseExecution(5000);
		for (int RoutePlanRowNo = 1; RoutePlanRowNo <= ExcelUtils.getCellDataRowCount("SE_BookingMBLMainDetails", ScenarioDetailsHashMap); RoutePlanRowNo++) 
		{
			if(ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "VesselSheduleType",ScenarioDetailsHashMap).equalsIgnoreCase("Single")){
				int VesselscheduleRowID = RoutePlanRowNo-1;

				String POL=GenericMethods.locateElement(driver, By.id("routePortOfLoading"+VesselscheduleRowID+"DKCode"), 2).getAttribute("value");
				String POD=GenericMethods.locateElement(driver, By.id("routePortOfDispatch"+VesselscheduleRowID+"DKCode"), 2).getAttribute("value");
				String Vessel=GenericMethods.locateElement(driver, By.id("routeVesselName"+VesselscheduleRowID), 2).getAttribute("value");
				String Voyage=GenericMethods.locateElement(driver, By.id("routeVoyage"+VesselscheduleRowID), 2).getAttribute("value");
				GenericMethods.assertTwoValues(POL, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_POL",ScenarioDetailsHashMap),"Validating Loading Port In Vessel Shedule", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(POD, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_POD",ScenarioDetailsHashMap),"Validating Discharge Port In Vessel Shedule", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(Vessel, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_Vessel",ScenarioDetailsHashMap),"Validating Vessel In Vessel Shedule", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(Voyage, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_Voyage",ScenarioDetailsHashMap),"Validating Voyage In Vessel Shedule", ScenarioDetailsHashMap);


			}
			else if(ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "VesselSheduleType",ScenarioDetailsHashMap).equalsIgnoreCase("Multiple")){
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
				GenericMethods.assertTwoValues(POL, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_POL",ScenarioDetailsHashMap),"Validating Loading Port In Vessel Shedule", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(POD, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_POD",ScenarioDetailsHashMap),"Validating Discharge Port In Vessel Shedule", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(Vessel, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_Vessel",ScenarioDetailsHashMap),"Validating Vessel In Vessel Shedule", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(Voyage, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "Container_Voyage",ScenarioDetailsHashMap),"Validating Voyage In Vessel Shedule", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(POT, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "POT",ScenarioDetailsHashMap),"Validating Transhipment Port In Vessel Shedule", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(VesselTranship, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "TranshipmentVessel",ScenarioDetailsHashMap),"Validating Transhipment Vessel In Vessel Shedule", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(VoyageTrnaship, ExcelUtils.getCellData("SE_BookingMBLMainDetails", RoutePlanRowNo, "TranshipmentVoyage",ScenarioDetailsHashMap),"Validating Transhipment Voyage In Vessel Shedule", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(POT, POT1,"Validating Transhipment Ports In Vessel Shedule Grid", ScenarioDetailsHashMap);

			}
		}

	}


	public static void seaBookingCargoPackDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_CargoDetails", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(6000);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
		GenericMethods.pauseExecution(6000);
		int rowCount=ExcelUtils.getCellDataRowCount("SE_BookingCargoDetails", ScenarioDetailsHashMap);
		for (int Testdata_CargoRowId = 1; Testdata_CargoRowId <= rowCount; Testdata_CargoRowId++) {
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Packages", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "Packages",ScenarioDetailsHashMap), 2);
			//AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Img_Type", ScenarioDetailsHashMap,10), orSeaBooking,"BoxType", ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "Type",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Type", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "Type",ScenarioDetailsHashMap), 2);
			//GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Type", 10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "Type",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "SubPack",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "SubPack",ScenarioDetailsHashMap), 2);
			//AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Img_SubPackType", 10), orSeaBooking,"BoxType", ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "SubType",ScenarioDetailsHashMap));
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "SubPackType", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "SubType",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "PackGrossWeight",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "GrossWeight",ScenarioDetailsHashMap), 2);
			GenericMethods.selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "UOW", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "UOW",ScenarioDetailsHashMap));
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "PackNetWeight", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "NetWeight",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Length", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "Length",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Width", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "Width",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Height",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "Height",ScenarioDetailsHashMap), 2);
			GenericMethods.selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "UOM", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "UOM",ScenarioDetailsHashMap));
			//Commented for FUNC 22
			//GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Volume", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "Volume",ScenarioDetailsHashMap), 2);

			//code added by masthan for SSCC validations 21.12
			if(ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "CommodityValidataion",ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "pack_Commodity", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "DefaultCommodity",ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "pack_Commodity", ScenarioDetailsHashMap,10),Keys.TAB, 2);
				GenericMethods.pauseExecution(3000);
				orSeaBooking.getElement(driver, "pack_HsCommodity", ScenarioDetailsHashMap,10).click();
				orSeaBooking.getElement(driver, "pack_ScheduleBNo", ScenarioDetailsHashMap,10).click();
				orSeaBooking.getElement(driver, "good_Description", ScenarioDetailsHashMap,10).click();
				orSeaBooking.getElement(driver, "pack_HsCommodity", ScenarioDetailsHashMap,10).click();
				GenericMethods.pauseExecution(2000);
				String HSCommodity=orSeaBooking.getElement(driver, "pack_HsCommodity", ScenarioDetailsHashMap,10).getAttribute("previousvalue");
				String SheduleB=orSeaBooking.getElement(driver, "pack_ScheduleBNo", ScenarioDetailsHashMap,10).getAttribute("previousvalue");
				String Description=orSeaBooking.getElement(driver, "good_Description", ScenarioDetailsHashMap,10).getAttribute("previousvalue");
				GenericMethods.assertTwoValues(HSCommodity,ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "DefaultHSCommodity",ScenarioDetailsHashMap), "Verifying Defaulted HS Commodity", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(SheduleB, ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "DefaultSheduleB",ScenarioDetailsHashMap), "Verifying Defaulted Shedule B", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(Description, ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "DefaultGoodDescription",ScenarioDetailsHashMap), "Verifying Defaulted Good Description", ScenarioDetailsHashMap);

			}
			

			//Adding Goods Description 	Marks & Numbers in Cargo details added by sangeeta
			GenericMethods.clickElement(driver, null, orSeaBooking.getElement(driver, "MarksNumberImg", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(8000);
			GenericMethods.selectWindow(driver);
			GenericMethods.pauseExecution(8000);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "MarksAndNumber", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "MarksAndNumbers", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "ExtendedDescription", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Goods_Description", ScenarioDetailsHashMap), 2);
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 3);
			GenericMethods.switchToParent(driver);
			AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(4000);

			//code ends here-Masthan

			AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Img_PackCommodity",ScenarioDetailsHashMap, 10), orSeaBooking,"Code", ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "Commodity",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Img_PackHsCommodity", ScenarioDetailsHashMap,10), orSeaBooking,"Code", ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "HSCommodity",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
			
			
			if(!ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "FRTClass",ScenarioDetailsHashMap).equals(""))
			{
				AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Img_FRTClass", ScenarioDetailsHashMap,10), orSeaBooking,"Code", ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "FRTClass",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
			}
			
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "ScheduleB_Pack", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "Schedule_B",ScenarioDetailsHashMap), 2);
			
			if( ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "MarksAndNumbersRequired",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
			{
				GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "LOV_MarksandNumbers", ScenarioDetailsHashMap,10), 2);
				GenericMethods.pauseExecution(2000);
				GenericMethods.selectWindow(driver);
				GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Textbox_LOV_MarksandNumbers",ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "MarksAndNumbers",ScenarioDetailsHashMap), 10);
				GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
				GenericMethods.pauseExecution(2000);
				GenericMethods.switchToParent(driver);
				AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
			}

			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "GoodsValue", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "GoodsValue",ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "PackageCurrency", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "GoodsCurrency",ScenarioDetailsHashMap), 2);

			//code added by masthan for SSCC validations 21.10,11

			if(ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "HAZrequired",ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "GoHazardous_Button", ScenarioDetailsHashMap,10), 2);	
				GenericMethods.pauseExecution(2000);
				try{
					GenericMethods.selectWindow(driver);
					if(driver.findElements(By.id("UNDGNumber")).size()>0)
						GenericMethods.assertTwoValues("PopUp Screen Opened", "PopUp Screen Not Opened", "Verifying Haz PopUp Screen without checking HAZ", ScenarioDetailsHashMap);
					else
						GenericMethods.assertTwoValues("PopUp Screen Not Opened", "PopUp Screen Not Opened", "Verifying Haz PopUp Screen without checking HAZ", ScenarioDetailsHashMap);
				}catch(Exception e){
					GenericMethods.assertTwoValues("PopUp Screen Not Opened", "PopUp Screen Not Opened", "Verifying Haz PopUp Screen without checking HAZ", ScenarioDetailsHashMap);
				}



				GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Hazardous_CheckBox", ScenarioDetailsHashMap,10), 2);	
				GenericMethods.pauseExecution(4000);
				try{
					GenericMethods.selectWindow(driver);
					if(driver.findElements(By.id("UNDGNumber")).size()>0)
						GenericMethods.assertTwoValues("PopUp Screen Opened", "PopUp Screen Opened", "Verifying Haz PopUp Screen", ScenarioDetailsHashMap);
					GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Hazardous_NumofPack", ScenarioDetailsHashMap,10),"1", 2);
					GenericMethods.selectOptionFromDropDown(driver,null,orSeaBooking.getElement(driver, "Hazardous_PackType", ScenarioDetailsHashMap,10), "BAG");
					GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Hazardous_netQuantity", ScenarioDetailsHashMap,10),"1", 2);
					GenericMethods.selectOptionFromDropDown(driver,null,orSeaBooking.getElement(driver, "Hazardous_netQuantityUom", ScenarioDetailsHashMap,10), "Kgs");
					GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Hazardous_grossWeight", ScenarioDetailsHashMap,10),"1", 2);
					GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Hazardous_UNDGNumber", ScenarioDetailsHashMap,10),"123" , 2);
					GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Hazardous_contactName", ScenarioDetailsHashMap,10),"Automation Team", 2);
					GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Hazardous_contactNumber", ScenarioDetailsHashMap,10),"123456789", 2);
					GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Hazardous_properShippingName", ScenarioDetailsHashMap,10),"Kewill Automation", 2);
					GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Hazardous_technicalName", ScenarioDetailsHashMap,10),"Kewill Automation", 2);
					GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);	
					GenericMethods.pauseExecution(4000);
					GenericMethods.switchToParent(driver);
					AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
				}catch(Exception e){
					GenericMethods.assertTwoValues("PopUp Screen Not Opened", "PopUp Screen Opened", "Verifying Haz PopUp Screen", ScenarioDetailsHashMap);

				}
				GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Hazardous_CheckBox", ScenarioDetailsHashMap,10), 2);	
			}


			GenericMethods.clickElement(driver, null, orSeaBooking.getElement(driver, "Btn_Package_Add", ScenarioDetailsHashMap,10), 2);
			//Invoice
			if(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "UploadRequired", ScenarioDetailsHashMap).equalsIgnoreCase("No")){
				if (ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "SSCCFormat",ScenarioDetailsHashMap).equalsIgnoreCase("Standard")) {
					GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Standard", ScenarioDetailsHashMap,10), 2);	
				}else{
					GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "NonStandard",ScenarioDetailsHashMap, 10), 2);	
				}

				//code added by masthan for SSCC validations 21.5
				if(ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "SSCCValidationsReq",ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
				{
					String Packnum=ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "PackCount",ScenarioDetailsHashMap);
					System.out.println("-->"+Packnum+"-->"+Testdata_CargoRowId);
					int num=Integer.parseInt(Packnum)-1;
					System.out.println("Masthan Number1-->"+num+"-->"+Testdata_CargoRowId);
					if(!orSeaBooking.getElement(driver, "SSCCNo", ScenarioDetailsHashMap,10).isEnabled())
					{
						GenericMethods.assertTwoValues("Disabled", "Disabled", "Verifying Disable mode of SSCCNo field", ScenarioDetailsHashMap);
					}else{
						GenericMethods.assertTwoValues("Enabled", "Disabled", "Verifying Disable mode of SSCCNo field", ScenarioDetailsHashMap);

					}
					GenericMethods.clickElement(driver, By.id("myGrid1-cell-14-"+num+"-box-text"), null, 2);
					if(orSeaBooking.getElement(driver, "SSCCNo", ScenarioDetailsHashMap,10).isEnabled())
					{
						GenericMethods.assertTwoValues("Enabled", "Enabled", "Verifying Enable mode of SSCCNo field", ScenarioDetailsHashMap);
					}else{
						GenericMethods.assertTwoValues("Disabled", "Enabled", "Verifying Enable mode of SSCCNo field", ScenarioDetailsHashMap);

					}
					//code added by masthan for SSCC validations 21.6
					if (ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "SSCCFormat",ScenarioDetailsHashMap).equalsIgnoreCase("Standard")){

						GenericMethods.replaceTextofTextField(driver,null,orSeaBooking.getElement(driver, "SSCCNo", ScenarioDetailsHashMap,10) ,ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "SSCCNumber",ScenarioDetailsHashMap), 2);
						GenericMethods.replaceTextofTextField(driver,null,orSeaBooking.getElement(driver, "SSCCNo", ScenarioDetailsHashMap,10) , Keys.TAB, 2);
						try{
							String errMes=orSeaBooking.getElement(driver, "SSCCNo", ScenarioDetailsHashMap,10).getAttribute("displayerrorname");
							GenericMethods.assertTwoValues(errMes, ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "SSCCErrorMessage",ScenarioDetailsHashMap), "Verifying Error Message of SSCCNo field", ScenarioDetailsHashMap);

						}catch(Exception e){
							GenericMethods.assertTwoValues("false", "true", "Error Message for SSCCNo not present", ScenarioDetailsHashMap);

						}
					}
					else{
						GenericMethods.replaceTextofTextField(driver,null,orSeaBooking.getElement(driver, "SSCCNo", ScenarioDetailsHashMap,10) ,  ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "SSCCNumber",ScenarioDetailsHashMap), 2);
						GenericMethods.replaceTextofTextField(driver,null,orSeaBooking.getElement(driver, "SSCCNo", ScenarioDetailsHashMap,10) , Keys.TAB, 2);
						try{
							String ssCC=orSeaBooking.getElement(driver, "SSCCNo", ScenarioDetailsHashMap,10).getAttribute("previousvalue");
							if(ssCC.equalsIgnoreCase( ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "SSCCNumber",ScenarioDetailsHashMap)))
								GenericMethods.assertTwoValues("true", "true", "Able to Enter Non Standard SSCC with Non Standard Radio Button", ScenarioDetailsHashMap);

						}catch(Exception e){
							GenericMethods.assertTwoValues("false", "true", "Unable to Enter Non Standard SSCC with Non Standard Radio Button", ScenarioDetailsHashMap);

						}


					}
					//code added by masthan for SSCC validations 21.7




				}




				//code ends here-masthan
				//GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "InvoiceNumber", 10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "InvoiceNumber",ScenarioDetailsHashMap), 2);
				//GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "InvoiceDate", 10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "InvoiceDate",ScenarioDetailsHashMap), 2);					
				GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "PoNumber", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "PoNumber",ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "PartId_Export", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "PartId_Export",ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "GrossWeight", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "GrossWeight",ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "NetWeight", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "NetWeight",ScenarioDetailsHashMap), 2);
				GenericMethods.selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "UOW", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "UOW",ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "StatQty1", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "StatQty1",ScenarioDetailsHashMap), 2);
				//AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "StatQty1TypeLov", ScenarioDetailsHashMap,10), orSeaBooking,"BoxType", ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "StatQty1Type",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "StatQty1Type", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "StatQty1Type",ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "StatQty2", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "StatQty2",ScenarioDetailsHashMap), 2);
				//AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "StatQty2TypeLov", ScenarioDetailsHashMap,10), orSeaBooking,"BoxType", ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "StatQty2Type",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "StatQty2Type", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "StatQty2Type",ScenarioDetailsHashMap), 2);
				AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "InvoiceCommodityLov",ScenarioDetailsHashMap, 10), orSeaBooking,"Code", ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "Commodity",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
				AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "InvHSCommodityLov",ScenarioDetailsHashMap, 10), orSeaBooking,"Code", ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "HSCommodity",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
				AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_ScheduleB", ScenarioDetailsHashMap,10), orSeaBooking,"Code", ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "ScheduleB",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "InvGoodsDescription",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "GoodsDescription",ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "InvGoodsValue", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "GoodsValue",ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "currency", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "GoodsCurrency",ScenarioDetailsHashMap), 2);
				if(driver.findElement(By.id("commInvButton")).isDisplayed())
				{
				GenericMethods.clickElement(driver, null, orSeaBooking.getElement(driver, "Btn_CommInvButton", ScenarioDetailsHashMap,10), 2);
				GenericMethods.pauseExecution(2000);
				}
				else
				{
					GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "InvoiceNumber", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "InvoiceNumber", ScenarioDetailsHashMap), 10);
					GenericMethods.pauseExecution(2000);
				}
				GenericMethods.clickElement(driver, null, orSeaBooking.getElement(driver, "Btn_Invoice_Add", ScenarioDetailsHashMap,10), 2);
				
				//code added by masthan for SSCC validations 21.7
				GenericMethods.pauseExecution(2000);
				try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
				GenericMethods.pauseExecution(2000);
				if(ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "SSCCValidationsReq",ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
				{
					String Invoicenum=ExcelUtils.getCellData("SE_BookingCargoDetails", Testdata_CargoRowId, "InvoiceCount",ScenarioDetailsHashMap);
					System.out.println("-->"+Invoicenum+"-->"+Testdata_CargoRowId);
					int num=Integer.parseInt(Invoicenum)-1;
					if(driver.findElements(By.id("invPoGrid-cell-34-"+num+"-box-text")).size()>0){

						GenericMethods.assertTwoValues("PO Line Added", "PO Line Added", "Verifying PO Line in Grid", ScenarioDetailsHashMap);

					}else{
						GenericMethods.assertTwoValues("PO Line Not Added", "PO Line Added", "Verifying PO Line in Grid", ScenarioDetailsHashMap);
					}

					GenericMethods.clickElement(driver, By.id("myGrid1-cell-14-"+num+"-box-text"), null, 2);
					GenericMethods.clickElement(driver, null, orSeaBooking.getElement(driver, "Btn_Package_Add", ScenarioDetailsHashMap,10), 2);

				}
			}
		}
		//Pavan Added below code for MIS Reports Validations
		String GrossWeight=orSeaBooking.getElement(driver, "Readonly_GrossWeight", ScenarioDetailsHashMap,10).getAttribute("value");
		System.out.println("GrossWeight:::"+GrossWeight);
		ExcelUtils.setCellData("SE_BookingCargoDetails", RowNo, "GrossWeight", GrossWeight, ScenarioDetailsHashMap);
		ExcelUtils.setCellData("SE_MBLCargoPackDetails", RowNo, "GrossWeight", GrossWeight, ScenarioDetailsHashMap);
		//End

		//Below Static variables are written inorder to use these values while stuffing containers in "Container Details" tab.
		stuffed_CargoPackCount = 1;
		containerCreation = false;
		ContainerCreationID = 1;
	}

	public static void uploadInvoiceDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo) throws IOException, SQLException, ClassNotFoundException{
		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_CargoDetails", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(6000);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
		GenericMethods.clickElement(driver, null, orSeaBooking.getElement(driver, "Button_Upload", ScenarioDetailsHashMap, 10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
			}
		catch (Exception e) {}
		GenericMethods.pauseExecution(2000);
		GenericMethods.selectWindow(driver);
		//		GenericMethods.clickElement(driver, null, orSeaBooking.getElement(driver, "Button_Browse", ScenarioDetailsHashMap, 10), 2);
		orSeaBooking.getElement(driver, "Button_Browse", ScenarioDetailsHashMap, 10).sendKeys("\n");
		GenericMethods.pauseExecution(8000);
		String filePath=System.getProperty("user.dir")+"\\Configurations\\PoDetails.xls";
		System.out.println("file no"+filePath);
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\Configurations\\upload.exe "+filePath);
		GenericMethods.pauseExecution(15000);
		//		GenericMethods.clickElement(driver, null, , 2);
		orSeaBooking.getElement(driver, "Button_Submit", ScenarioDetailsHashMap, 10).sendKeys("\n");
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver, ScenarioDetailsHashMap);
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		Connection con_GetExcelDetails = DriverManager
		.getConnection(
				GenericMethods.getPropertyValue("conString1", GenerateControlfiles.path)
				+System.getProperty("user.dir")+"\\Configurations\\PoDetails.xls"
				+GenericMethods.getPropertyValue("conString2", GenerateControlfiles.path));

		Statement stmt_GetExcelDetails = con_GetExcelDetails.createStatement();

		ResultSet RS_GetCellDetails = stmt_GetExcelDetails.executeQuery
		("select * from [PO_Details$]");
		int counter = 0;
		while(RS_GetCellDetails.next() && RS_GetCellDetails != null)
		{
			GenericMethods.assertInnerText(driver, By.id("invPoGrid-cell-0-"+counter+"-box-text"), null, RS_GetCellDetails.getString("SSCC NO"), "Grid SSCC NO", "Validateing Uploaded SSCC No in invoice Grid", ScenarioDetailsHashMap);
			GenericMethods.assertInnerText(driver, By.xpath("//span[@id='invPoGrid-cell-35-"+counter+"-box-text']/a"), null, RS_GetCellDetails.getString("Invoice Number"), "Grid Invoice NO", "Validateing Uploaded Invoice No in invoice Grid", ScenarioDetailsHashMap);
			//             GenericMethods.assertInnerText(driver, By.id("invPoGrid-cell-21-"+counter+"-box-text"), null, RS_GetCellDetails.getString("Invoice Date"), "Grid Invoice Date", "Validateing Uploaded InVoice Date in invoice Grid", ScenarioDetailsHashMap);
			GenericMethods.assertInnerText(driver, By.id("invPoGrid-cell-28-"+counter+"-box-text"), null, RS_GetCellDetails.getString("Payment Term (Import)"), "Grid Payment term Import", "Validateing Uploaded Payment term Import in invoice Grid", ScenarioDetailsHashMap);
			GenericMethods.assertInnerText(driver, By.id("invPoGrid-cell-37-"+counter+"-box-text"), null, RS_GetCellDetails.getString("Payment Term (Export)"), "Grid Payment Term Export", "Validateing Uploaded Payment Term Export in invoice Grid", ScenarioDetailsHashMap);
			GenericMethods.assertInnerText(driver, By.id("invPoGrid-cell-2-"+counter+"-box-text"), null, RS_GetCellDetails.getString("PO Number"), "Grid PO Number", "Validateing Uploaded PO Number in invoice Grid", ScenarioDetailsHashMap);
			GenericMethods.assertInnerText(driver, By.id("invPoGrid-cell-22-"+counter+"-box-text"), null, RS_GetCellDetails.getString("SO Number"), "Grid SO Number", "Validateing Uploaded SO Number in invoice Grid", ScenarioDetailsHashMap);
			GenericMethods.assertInnerText(driver, By.id("invPoGrid-cell-3-"+counter+"-box-text"), null, RS_GetCellDetails.getString("Part Id"), "Grid Part Id Export", "Validateing Uploaded Part Id Export in invoice Grid", ScenarioDetailsHashMap);
			GenericMethods.assertInnerText(driver, By.id("invPoGrid-cell-13-"+counter+"-box-text"), null, RS_GetCellDetails.getString("Stat Qty1"), "Grid Stat Qty1", "Validateing Uploaded Stat Qty1 in invoice Grid", ScenarioDetailsHashMap);
			GenericMethods.assertInnerText(driver, By.id("invPoGrid-cell-7-"+counter+"-box-text"), null, RS_GetCellDetails.getString("Goods Description"), "Grid Goods Description", "Validateing Uploaded Goods Description in invoice Grid", ScenarioDetailsHashMap);
			//             System.out.println("value="+GenericMethods.getInnerText(driver, By.id("invPoGrid-cell-32-"+counter+"-box-text"), null, 10));
			//             GenericMethods.assertInnerText(driver, By.id("invPoGrid-cell-32-"+counter+"-box-text"), null, RS_GetCellDetails.getString("Bonded / Normal"), "Grid Bonded / Normal", "Validateing Uploaded Bonded / Normal in invoice Grid", ScenarioDetailsHashMap);
			//             GenericMethods.assertInnerText(driver, By.id("invPoGrid-cell-33-"+counter+"-box-text"), null, RS_GetCellDetails.getString("Inco Term (Import)"), "Grid Inco Term (Import)", "Validateing Uploaded Inco Term (Import)in invoice Grid", ScenarioDetailsHashMap);
			counter =counter+1;

		}
		con_GetExcelDetails.close();

		GenericMethods.clickElement(driver, null, orSeaBooking.getElement(driver, "ExportButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);
		filePath= System.getProperty("user.dir")+ "\\Reports\\"
		+ res.getExectionDateTime() + "\\PoDetails.xls";
		System.out.println("file no"+filePath);
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\Configurations\\Download.exe "+filePath);
		GenericMethods.pauseExecution(15000);

	}
	public static void PU_OR_LABEL_CHANGE(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.assertInnerText(driver, null, orSeaBooking.getElement(driver, "Tab_PickUpInstructions", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("SE_BookingPickUpInstructions", RowNo, "AfterSetupPickupTabText", ScenarioDetailsHashMap), "PickupTabText", "Validating Pickup Tab Text value after setup", ScenarioDetailsHashMap);
	}

	//Pavan Added Validations for Pickup Instructions Tab
	public static void seaBookingPickUpInstructions(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{

		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_PickUpInstructions", ScenarioDetailsHashMap,10),2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		GenericMethods.pauseExecution(3000);
		GenericMethods.selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "PickUp_Status", ScenarioDetailsHashMap,10) , ExcelUtils.getCellData("SE_BookingPickUpInstructions", RowNo, "Status",ScenarioDetailsHashMap));


		//Pavan FUNC010.4,FUNC010.7,FUNC010.8,FUNC010.9--Below condition will perform Validations 
		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PickUp_Required",ScenarioDetailsHashMap).equalsIgnoreCase("PickUp Instructions"))
		{
			GenericMethods.assertDropDownText(driver, null, orSeaBooking.getElement(driver, "MovementType", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingPickUpInstructions", RowNo, "TypeOfMovement",ScenarioDetailsHashMap), "Booking_MovementType", "Validating Default Movement Type", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orSeaBooking.getElement(driver, "PickupLocationNickName", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PickUpNickName",ScenarioDetailsHashMap), "Booking_PickUpLocation", "Validating Pickup Location Party", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orSeaBooking.getElement(driver, "PickUpAddress", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PickUp_Address",ScenarioDetailsHashMap), "Booking_PickUpAddress", "Validating Pickup Location Party Address", ScenarioDetailsHashMap);
			GenericMethods.assertValue(driver, null, orSeaBooking.getElement(driver, "PickupLocationNickName", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PickUpNickName",ScenarioDetailsHashMap), "Booking_PickUpLocation", "Validating Selected ShipperId into PickUp Instructions Screens Pickup Location Field", ScenarioDetailsHashMap);
		}//FUNC010.4,FUNC010.7,FUNC010.8,FUNC010.9 End

		//Pavan FUNC010.10--Below condition will perform Validations 
		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "LoadTypeSBC",ScenarioDetailsHashMap).equalsIgnoreCase("LCL") &&
				ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PickUp_Required",ScenarioDetailsHashMap).equalsIgnoreCase("PickUp Instructions"))
		{
			GenericMethods.assertDropDownText(driver, null, orSeaBooking.getElement(driver, "MovementType", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingPickUpInstructions", RowNo, "TypeOfMovement_LCL",ScenarioDetailsHashMap), "Booking_MovementType", "Validating Default Movement Type for LCL Load Type", ScenarioDetailsHashMap);

		}//FUNC010.10End

		GenericMethods.selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "MovementType", ScenarioDetailsHashMap,10) , ExcelUtils.getCellData("SE_BookingPickUpInstructions", RowNo, "TypeOfMovement",ScenarioDetailsHashMap));
		GenericMethods.pauseExecution(1500);
		String Prefix_ContainterCheckbox="//td[text()=' Container No.']/ancestor::tr[1]/td[2]/div[1]/table[1]/tbody/tr";
		int ContainerCheckboxescount = driver.findElements(By.xpath(Prefix_ContainterCheckbox)).size();
		for (int Grid_CheckboxRowId = 1; Grid_CheckboxRowId <= ContainerCheckboxescount; Grid_CheckboxRowId++) 
		{
			GenericMethods.clickElement(driver, By.xpath(Prefix_ContainterCheckbox+"["+Grid_CheckboxRowId+"]/td[1]"), null, 10);			
		}
		GenericMethods.pauseExecution(1500);


		if(ExcelUtils.getCellData("SE_BookingPickUpInstructions", RowNo, "Status",ScenarioDetailsHashMap).equalsIgnoreCase("Partial"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "StuffedPieces", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingPickUpInstructions", RowNo, "Pieces",ScenarioDetailsHashMap), 10);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "StuffedPiecesType", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingPickUpInstructions", RowNo, "Type",ScenarioDetailsHashMap), 10);
		}
		//Pavan FUNC010.11,FUNC010.12,FUNC010.13,FUNC010.14,FUNC010.15 --Below line will perform Validations 
		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PickUp_Required",ScenarioDetailsHashMap).equalsIgnoreCase("Pick Up Run"))
		{
			GenericMethods.assertDropDownText(driver, null, orSeaBooking.getElement(driver, "MovementType", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingPickUpInstructions", RowNo, "TypeOfMovement",ScenarioDetailsHashMap), "Booking_MovementType", "Validating Movement Type in Booking Module", ScenarioDetailsHashMap);
		}
		//FUNC010.11,FUNC010.12,FUNC010.13,FUNC010.14,FUNC010.15 End


		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Btn_Add", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(8000);
		/*int rowCount=ExcelUtils.getCellDataRowCount("SE_BookingCargoDetails", ScenarioDetailsHashMap);
		for (int i = 0; i < rowCount; i++) {
		}*/
	}

	public static void seaBookingeDoc(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_eDoc",ScenarioDetailsHashMap, 10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}

	}


	//Sandeep-Jan15th 2015, Below method is to enter RoutePlan Details in Route Plan Window.
	public static void RoutePlan_Details(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		//System.out.println("inside value");
		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_MainDetails", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(3000);
		GenericMethods.selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "DDL_QuickLink", ScenarioDetailsHashMap,10) , "Route Plan");
		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Button_QuickLink", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(3000);

		try{
			GenericMethods.handleAlert(driver, "accept");

		}catch (Exception e) {
			//System.out.println("no Alerts present");

		}
		int RoutePlan_rowCount=ExcelUtils.getCellDataRowCount("SE_BookingRoutePlanDetails", ScenarioDetailsHashMap);
		//System.out.println("value:::"+RoutePlan_rowCount);
		for (int RoutePlanRowNo = 1; RoutePlanRowNo <= RoutePlan_rowCount; RoutePlanRowNo++) 
		{System.out.println("RoutePlanRowNo===="+RoutePlanRowNo);
		int rowId =  RoutePlanRowNo+1;
		GenericMethods.clickElement(driver, By.xpath("//tr[@id='"+RoutePlanRowNo+"']/td[12]/img"),null, 2);
		WebElement lovelement = driver.findElement(By.xpath("//tr[@id='"+RoutePlanRowNo+"']/td[4]/a/img")); 
		AppReusableMethods.selectValueFromLov(driver, lovelement, orSeaBooking, "PartyId", ExcelUtils.getCellData("SE_BookingRoutePlanDetails", RoutePlanRowNo, "Party",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(2000);
		GenericMethods.replaceTextofTextField(driver, By.xpath("//tr[@id='"+RoutePlanRowNo+"']/td[4]/input"), null, Keys.TAB, 2);
		//Added for route plan select
		Select select = new Select(driver.findElement(By.xpath("//tr[@id='"+rowId+"']/td[5]/select")));
		select.selectByVisibleText(ExcelUtils.getCellData("SE_BookingRoutePlanDetails", RoutePlanRowNo, "Mode",ScenarioDetailsHashMap));
		//GenericMethods.selectOptionFromDropDown(driver, By.xpath("//tr[@id='"+rowId+"']/td[5]/select"), null , ExcelUtils.getCellData("SE_BookingRoutePlanDetails", RoutePlanRowNo, "Mode",ScenarioDetailsHashMap));
		}
		//		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
	}
	static int stuffed_CargoPackCount = 1;
	static boolean containerCreation = false;
	static int ContainerCreationID = 1;
	//Sandeep:Jan 15th 2015,Below method is to enter Container Details in Container Tab of Booking Module.
	public static void seaBookingContainerDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){

		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "ContainerDetails_Tab", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(3000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}
		int rowCount=ExcelUtils.getCellDataRowCount("SE_BookingContainerDetails", ScenarioDetailsHashMap);
		//System.out.println("pictOceanExportsOBL_ContainerDetails rowCount::"+rowCount);
		//Creation of Containers
		for (int ContainerCreationRowID = 1; ContainerCreationRowID <= rowCount; ContainerCreationRowID++) 
		{
			boolean grid_ContainerNumberAvailability=false;
			WebElement ContainerDropdown_element = driver.findElement(By.xpath("//td[@id='CNTRDTLSAREA']/table/tbody/tr/td/table[1]/tbody/tr[2]/td[10]/select"));
			List<WebElement> dropdownvalues= new Select(ContainerDropdown_element).getOptions(); 
			for (int DropdownValue = 1; DropdownValue < dropdownvalues.size(); DropdownValue++) 
			{
				if(dropdownvalues.get(DropdownValue).getText().trim().equals(ExcelUtils.getCellData("SE_BookingContainerDetails", ContainerCreationRowID, "ContainerNumber", ScenarioDetailsHashMap)))
				{
					grid_ContainerNumberAvailability=true;
					break;
				}
			}
			if(!grid_ContainerNumberAvailability)
			{
				if(containerCreation){
					GenericMethods.clickElement(driver, null, orSeaBooking.getElement(driver, "Button_ContainerDetailsAdd", ScenarioDetailsHashMap,10), 10);
					GenericMethods.pauseExecution(2000);
					ContainerCreationID=ContainerCreationID+1;
				}
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.id("containerType"+ContainerCreationID)));
				GenericMethods.pauseExecution(3000);
				try{
					GenericMethods.selectOptionFromDropDown(driver, By.id("containerType"+ContainerCreationID), null, ExcelUtils.getCellData("SE_BookingContainerDetails", ContainerCreationRowID, "ContainerType", ScenarioDetailsHashMap));
					GenericMethods.pauseExecution(3000);
				}catch (Exception e) {
				}
				GenericMethods.replaceTextofTextField(driver, By.id("containerNo"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_BookingContainerDetails", ContainerCreationRowID, "ContainerNumber", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("containerNo"+ContainerCreationID), null,  Keys.TAB, 20);
				GenericMethods.pauseExecution(7000);
				try{
					GenericMethods.handleAlert(driver, "accept");
				}catch (Exception e) {
				}
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, By.id("sealNo1"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_BookingContainerDetails", ContainerCreationRowID, "Seal_Num1", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("sealNo2"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_BookingContainerDetails", ContainerCreationRowID, "Seal_Num2", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("contGrossWt"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_BookingContainerDetails", ContainerCreationRowID, "GrossWeight", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("contVolume"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_BookingContainerDetails", ContainerCreationRowID, "Volume", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("pickUpReqDate"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_BookingContainerDetails", ContainerCreationRowID, "PickUp_Date", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("pickUpTime"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_BookingContainerDetails", ContainerCreationRowID, "PickUp_Time", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("cargoReadyDate"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_BookingContainerDetails", ContainerCreationRowID, "CargoReady_Date", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("cargoReadyTime"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_BookingContainerDetails", ContainerCreationRowID, "CargoReady_Time", ScenarioDetailsHashMap), 20);
				GenericMethods.pauseExecution(2000);
				containerCreation = true;
			}
			//Masthan added for container validations

			if(ExcelUtils.getCellData("SE_BookingContainerDetails", ContainerCreationRowID, "ContainerValidationsReq", ScenarioDetailsHashMap).equalsIgnoreCase("Yes")){

				Validate_DuplicateContainers(driver,ContainerCreationRowID,ScenarioDetailsHashMap,RowNo);

			}	
		}
		//Stuffing the Cargo Packs into Container
		for (int testdata_stuffingCargoPackDetails = 1; testdata_stuffingCargoPackDetails <= ExcelUtils.getCellDataRowCount("SE_BookingContainerDetails", ScenarioDetailsHashMap); testdata_stuffingCargoPackDetails++) 
		{
			//System.out.println("hello"+testdata_stuffingCargoPackDetails);
			int ContainerAssigningRowID =testdata_stuffingCargoPackDetails+1;
			String xpathPrefix="//td[@id='CNTRDTLSAREA']/table/tbody/tr/td/table["+stuffed_CargoPackCount+"]/tbody/tr["+ContainerAssigningRowID+"]";
			try{
				GenericMethods.selectOptionFromDropDown(driver, By.xpath(xpathPrefix+"/td[10]/select"), null, ExcelUtils.getCellData("SE_BookingContainerDetails", testdata_stuffingCargoPackDetails, "ContainerNumber", ScenarioDetailsHashMap));
				GenericMethods.pauseExecution(1000);
			}catch (Exception e) {
			}
			GenericMethods.replaceTextofTextField(driver, By.xpath(xpathPrefix+"/td[11]/input[1]"), null,  ExcelUtils.getCellData("SE_BookingContainerDetails", testdata_stuffingCargoPackDetails, "Container_Packages", ScenarioDetailsHashMap), 20);
			GenericMethods.replaceTextofTextField(driver, By.xpath(xpathPrefix+"/td[11]/input[1]"), null,  Keys.TAB, 20);

			//Added by Masthan
			if(ExcelUtils.getCellData("SE_BookingContainerDetails", testdata_stuffingCargoPackDetails, "ContainerValidationsReq", ScenarioDetailsHashMap).equalsIgnoreCase("Yes")){
				Validate_ContainerWeight(driver,xpathPrefix,testdata_stuffingCargoPackDetails,ScenarioDetailsHashMap,RowNo);
			}
		}

		stuffed_CargoPackCount=stuffed_CargoPackCount+1;

	}
	public static void Validate_ContainerWeight(WebDriver driver,String xpathPrefix,int testdata_stuffingCargoPackDetails,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		if(testdata_stuffingCargoPackDetails==1){
			GenericMethods.replaceTextofTextField(driver, By.xpath(xpathPrefix+"/td[11]/input[1]"), null,  ExcelUtils.getCellData("SE_BookingContainerDetails", testdata_stuffingCargoPackDetails, "ModifiedContainerPackages", ScenarioDetailsHashMap), 20);
			GenericMethods.replaceTextofTextField(driver, By.xpath(xpathPrefix+"/td[11]/input[1]"), null,  Keys.TAB, 20);
			GenericMethods.clickElement(driver,By.id("PICKUP_INSTRUCTIONS"),null, 10);
			GenericMethods.pauseExecution(3000);
			GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "ContainerDetails_Tab", ScenarioDetailsHashMap, 10),30);
			GenericMethods.pauseExecution(3000);
			String grossModified=GenericMethods.locateElement(driver, By.xpath(xpathPrefix+"/td[13]/input[1]"), 3).getAttribute("previousvalue");
			String VolumeModified=GenericMethods.locateElement(driver, By.xpath(xpathPrefix+"/td[14]/input[1]"), 3).getAttribute("previousvalue");
			System.out.println("Gross&Volume"+grossModified+" "+VolumeModified);
			String PackagesUpdated=GenericMethods.locateElement(driver, By.xpath("//*[@id='stuffingTable_0']/tbody/tr[3]/td[11]/input[1]"), 3).getAttribute("value");
			String grossUpdated=GenericMethods.locateElement(driver, By.xpath("//*[@id='stuffingTable_0']/tbody/tr[3]/td[13]/input[1]"),3).getAttribute("previousvalue");
			String VolumeUpdated=GenericMethods.locateElement(driver, By.xpath("//*[@id='stuffingTable_0']/tbody/tr[3]/td[14]/input[1]"),3).getAttribute("previousvalue");
			System.out.println("Gross Weight U-->"+grossUpdated);
			System.out.println("Volume U-->"+VolumeUpdated);
			GenericMethods.assertTwoValues(grossModified,ExcelUtils.getCellData("SE_BookingContainerDetails", testdata_stuffingCargoPackDetails, "UpdatedGrossWeight", ScenarioDetailsHashMap), "Verifying Modified Gross Weight", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(VolumeModified,ExcelUtils.getCellData("SE_BookingContainerDetails", testdata_stuffingCargoPackDetails, "UpdatedVolume", ScenarioDetailsHashMap), "Verifying Modified Volume", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(PackagesUpdated,ExcelUtils.getCellData("SE_BookingContainerDetails", testdata_stuffingCargoPackDetails, "UpdatedContainerPackages", ScenarioDetailsHashMap), "Verifying Updated Packages", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(grossUpdated,ExcelUtils.getCellData("SE_BookingContainerDetails", testdata_stuffingCargoPackDetails, "UpdatedGrossWeight", ScenarioDetailsHashMap), "Verifying Updated Gross Weight", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(VolumeUpdated,ExcelUtils.getCellData("SE_BookingContainerDetails", testdata_stuffingCargoPackDetails, "UpdatedVolume", ScenarioDetailsHashMap), "Verifying Updated Volume", ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver,By.xpath("//*[@id='stuffingTable_0']/tbody/tr[3]/td[16]/img"),null, 10);

		}

	}
	public static void Validate_DuplicateContainers(WebDriver driver,int ContainerCreationRowID,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		GenericMethods.clickElement(driver, null, orSeaBooking.getElement(driver, "Button_ContainerDetailsAdd", ScenarioDetailsHashMap,10), 10);
		int ContainerRow=ContainerCreationID+1;
		//code for container numbers
		GenericMethods.replaceTextofTextField(driver, By.id("containerNo"+ContainerRow), null,  ExcelUtils.getCellData("SE_BookingContainerDetails", ContainerCreationRowID, "ContainerNumber", ScenarioDetailsHashMap), 20);
		GenericMethods.replaceTextofTextField(driver, By.id("containerNo"+ContainerRow), null,  Keys.TAB, 20);
		GenericMethods.pauseExecution(3000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}
		GenericMethods.pauseExecution(2000);
		try{
			Alert alert=driver.switchTo().alert();	
			String alertMessage=alert.getText();
			alert.accept();
			if(alertMessage.contains(ExcelUtils.getCellData("SE_BookingContainerDetails", ContainerCreationRowID, "DuplicateAlertMessage", ScenarioDetailsHashMap))){
				GenericMethods.assertTwoValues(alertMessage, alertMessage, "Verifying alert for Duplicate Container numbers", ScenarioDetailsHashMap);
			}else{
				GenericMethods.assertTwoValues(alertMessage,"alert message not valid", "Verifying alert for Duplicate Container numbers", ScenarioDetailsHashMap);

			}
		}catch (Exception e) {
			GenericMethods.assertTwoValues("true","false", "alert for Duplicate containers not displayed", ScenarioDetailsHashMap);
		}

		//code for seal1

		GenericMethods.replaceTextofTextField(driver, By.id("sealNo1"+ContainerRow), null,  ExcelUtils.getCellData("SE_BookingContainerDetails", ContainerCreationRowID, "Seal_Num1", ScenarioDetailsHashMap), 20);
		GenericMethods.replaceTextofTextField(driver, By.id("sealNo1"+ContainerRow), null,  Keys.TAB, 20);
		GenericMethods.pauseExecution(2000);
		try{
			Alert alert=driver.switchTo().alert();	
			String alertMessage=alert.getText();
			alert.accept();
			if(alertMessage.contains(ExcelUtils.getCellData("SE_BookingContainerDetails", ContainerCreationRowID, "DuplicateAlertMessage", ScenarioDetailsHashMap))){
				GenericMethods.assertTwoValues(alertMessage, alertMessage, "Verifying alert for Duplicate Seal1 numbers", ScenarioDetailsHashMap);
			}else{
				GenericMethods.assertTwoValues(alertMessage,"alert message not valid", "Verifying alert for Duplicate Seal1 numbers", ScenarioDetailsHashMap);

			}
		}catch (Exception e) {
			GenericMethods.assertTwoValues("true","false", "alert for Duplicate Seal1 not displayed", ScenarioDetailsHashMap);
		}
		//code for seal2
		GenericMethods.replaceTextofTextField(driver, By.id("sealNo2"+ContainerRow), null,  ExcelUtils.getCellData("SE_BookingContainerDetails", ContainerCreationRowID, "Seal_Num2", ScenarioDetailsHashMap), 20);
		GenericMethods.replaceTextofTextField(driver, By.id("sealNo2"+ContainerRow), null,  Keys.TAB, 20);
		GenericMethods.pauseExecution(2000);
		try{
			Alert alert=driver.switchTo().alert();	
			String alertMessage=alert.getText();
			alert.accept();
			if(alertMessage.contains(ExcelUtils.getCellData("SE_BookingContainerDetails", ContainerCreationRowID, "DuplicateAlertMessage", ScenarioDetailsHashMap))){
				GenericMethods.assertTwoValues(alertMessage, alertMessage, "Verifying alert for Duplicate Seal2 numbers", ScenarioDetailsHashMap);
			}else{
				GenericMethods.assertTwoValues(alertMessage,"alert message not valid", "Verifying alert for Duplicate Seal2 numbers", ScenarioDetailsHashMap);

			}
		}catch (Exception e) {
			GenericMethods.assertTwoValues("true","false", "alert for Duplicate Seal2 not displayed", ScenarioDetailsHashMap);
		}

		GenericMethods.clickElement(driver,By.id("multiSelectCheckboxcontainerDetailsGrid"+ContainerRow),null, 10);
		GenericMethods.clickElement(driver, null, orSeaBooking.getElement(driver, "Button_ContainerDetailsRemove", ScenarioDetailsHashMap,10), 10);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}
		GenericMethods.pauseExecution(2000);


	}
	public static void Booking_SearchList(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.seaBooking, "Sea Booking",ScenarioDetailsHashMap);

		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Textbox_BookingId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"BookingId", ScenarioDetailsHashMap), 2);		
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(3000);
	}

	public static void Booking_Save(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(3000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}
	}


	//FUNC001.1	-Whenever Known Shipper selected (if POL entered), the Known Shipper the Check box should be select automatically and Known check box should be shown in  protected mode
	public static void knownshipperValidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		knownShipper= GenericMethods.readValue(driver, orSeaBooking.getElement(driver, "checkbox_knownShipper", ScenarioDetailsHashMap, 10), null);
		if(knownShipper.equalsIgnoreCase("Y"))
		{
			GenericMethods.assertTwoValues(knownShipper, "Y", "Known Shipper Protected Mode", ScenarioDetailsHashMap);
		}
		else
		{
			GenericMethods.assertTwoValues(knownShipper, "N", "Known Shipper Protected Mode", ScenarioDetailsHashMap);
		}
	}
	//FUNC18.4-Parties tab should be saved with provided special characters(/ - , . ()&_"+'@# including space)
	public static void partiesTabValidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_Parties", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(3000);

		//logic to traverse table
		int partyTable= orSeaBooking.getElements(driver, "table_addressDtlsGrid", ScenarioDetailsHashMap, 10).size();
		if(partyTable>0){
			for (int i = 1; i < partyTable; i++) {
				String partyXpath="//*[@id='divaddressDtlsGrid']/table/tbody/tr["+i+"]/td[4]";
				String salePerson="//*[@id='divaddressDtlsGrid']/table/tbody/tr["+i+"]/td[1]";
				String partyName=driver.findElement(By.xpath(salePerson)).getText();
				if(partyName.equalsIgnoreCase("SALES PERSON")){
					GenericMethods.assertInnerText(driver, By.xpath("//*[@id='divaddressDtlsGrid']/table/tbody/tr["+i+"]/td[3]"), null, ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "SalesPerson", ScenarioDetailsHashMap), "PARTYID", "Validation of Customer Selection to Sales Person mapping", ScenarioDetailsHashMap);
					GenericMethods.clickElement(driver, By.xpath(partyXpath), null, 10);
					GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Textbox_street", ScenarioDetailsHashMap,10),  ExcelUtils.getCellData("SE_BookingPartyDetails", RowNo, "PartyAddress", ScenarioDetailsHashMap), 2);
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
	//FUNC001.3	-Whenever Known Shipper selected and trying to save the record, alert should come as " “POA has not been obtained for this Shipper” while creating a Booking
	public static void validateKnownShipperPartyTab(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_MainDetails", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(3000);
		knownshipperValidation(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_Parties", ScenarioDetailsHashMap,10), 2);
		if(knownShipper.equalsIgnoreCase("Y")){
			try{
				GenericMethods.pauseExecution(3000);
				Alert alert = driver.switchTo().alert();
				String alertText = alert.getText();
				System.out.println("alertText"+alertText);
				String alertActualText = (alertText.split(" : ")[1].trim())+ " : "+(alertText.split(" : ")[2].trim());
				System.out.println("alertActualText"+alertActualText);
				System.out.println("alertExpectedText"+ ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "AlertMessage",ScenarioDetailsHashMap));
				GenericMethods.assertTwoValues(alertActualText, ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "AlertMessage",ScenarioDetailsHashMap), "Validating Alert Message for KNOWSHIPPER should get POA", ScenarioDetailsHashMap);
				GenericMethods.pauseExecution(3000);
				alert.accept();
				//GenericMethods.assertAlertMessage(driver, ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "AlertMessage",ScenarioDetailsHashMap), "KnownShipperPOA", "Validating Alert Message for KNOWSHIPPER should get POA", ScenarioDetailsHashMap);
				//GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e)
			{}
		}
		GenericMethods.pauseExecution(3000);

		partiesTabValidation(driver, ScenarioDetailsHashMap, RowNo);
	}


	//Sandeep- Below method is to verify whether fields are available in Invoice Details section. 
	public static void CargoDetailsTab_FieldsAvailbility(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.assertTwoValues(GenericMethods.isFieldEnabled(driver, null,orSeaBooking.getElement(driver, "SONumber", ScenarioDetailsHashMap,10), 10)+"", "true", "Verifying SO Number field available in the Invoice Details section", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.isFieldEnabled(driver, null,orSeaBooking.getElement(driver, "InvoiceNumber", ScenarioDetailsHashMap,10), 10)+"", "true", "Verifying Manual / Invoice No field available in the Invoice Details section", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.isFieldEnabled(driver, null,orSeaBooking.getElement(driver, "UnitPriceExport", ScenarioDetailsHashMap,10), 10)+"", "true", "Verifying Unit Price (Export)field available in the Invoice Details section", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.isFieldEnabled(driver, null,orSeaBooking.getElement(driver, "PartIdImport", ScenarioDetailsHashMap,10), 10)+"", "true", "Verifying Part Id (Import) field available in the Invoice Details section", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.isFieldEnabled(driver, null,orSeaBooking.getElement(driver, "UnitPriceImport", ScenarioDetailsHashMap,10), 10)+"", "true", "Verifying Unit Price (Import) field available in the Invoice Details section", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.isFieldEnabled(driver, null,orSeaBooking.getElement(driver, "POVolume", ScenarioDetailsHashMap,10), 10)+"", "true", "Verifying Volume field available in the Invoice Details section", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.isFieldEnabled(driver, null,orSeaBooking.getElement(driver, "Currency", ScenarioDetailsHashMap,10), 10)+"", "true", "Verifying Currency (Import) field available in the Invoice Details section", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.isFieldEnabled(driver, null,orSeaBooking.getElement(driver, "OriginCountry", ScenarioDetailsHashMap,10), 10)+"", "true", "Verifying Country Of Origin field available in the Invoice Details section", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.isFieldEnabled(driver, null,orSeaBooking.getElement(driver, "IsBonded", ScenarioDetailsHashMap,10), 10)+"", "true", "Verifying Bonded / Normal field available in the Invoice Details section", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.isFieldEnabled(driver, null,orSeaBooking.getElement(driver, "IncoExpTerms", ScenarioDetailsHashMap,10), 10)+"", "true", "Verifying INCO Terms(Export) field available in the Invoice Details section", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.isFieldEnabled(driver, null,orSeaBooking.getElement(driver, "IncoTermLocation", ScenarioDetailsHashMap,10), 10)+"", "true", "Verifying Location field available in the Invoice Details section", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.isFieldEnabled(driver, null,orSeaBooking.getElement(driver, "IncoImpTerms", ScenarioDetailsHashMap,10), 10)+"", "true", "Verifying INCO Terms(Import) field available in the Invoice Details section", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.isFieldEnabled(driver, null,orSeaBooking.getElement(driver, "IncoTermImpLocation", ScenarioDetailsHashMap,10), 10)+"", "true", "Verifying Location(Import) field available in the Invoice Details section", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.isFieldEnabled(driver, null,orSeaBooking.getElement(driver, "LOV_AdditonalInfo", ScenarioDetailsHashMap,10), 10)+"", "true", "Verifying Additional Info field available in the Invoice Details section", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.isFieldEnabled(driver, null,orSeaBooking.getElement(driver, "Btn_CommInvButton", ScenarioDetailsHashMap,10), 10)+"", "true", "Verifying Comm. Invoice field available in the Invoice Details section", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.isFieldEnabled(driver, null,orSeaBooking.getElement(driver, "Btn_PackingList", ScenarioDetailsHashMap,10), 10)+"", "true", "Verifying Packing List field available in the Invoice Details section", ScenarioDetailsHashMap);
	}


	//Pavan FUNC007--Below method will perform validation for Location to Terminal Mapping 
	public static void Location_Terminal_Validations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_PortOfLoading", ScenarioDetailsHashMap,10), orSeaBooking,"LocationCode", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Location_POL",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_PortOfDispatch", ScenarioDetailsHashMap,10), orSeaBooking,"LocationCode", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Location_POD",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_Parties", ScenarioDetailsHashMap,10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}
		String Location_Terminal_Msg=orSeaBooking.getElement(driver, "Summary_Text_Location_Terminal", ScenarioDetailsHashMap,10).getText();
		ExcelUtils.setCellData("SE_BookingMainDetails", RowNo, "Location_Validation_Alert", Location_Terminal_Msg, ScenarioDetailsHashMap);
		System.out.println("Location_Terminal_Msg::"+Location_Terminal_Msg);
		if(Location_Terminal_Msg.equalsIgnoreCase(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Location_Validation_Alert", ScenarioDetailsHashMap))){
			GenericMethods.assertTwoValues(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Location_Validation_Msg", ScenarioDetailsHashMap), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Location_Validation_Msg", ScenarioDetailsHashMap), "Validating PortOfLanding Location to Terminal Mapping in Booking Module", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Location_Validation_Msg", ScenarioDetailsHashMap), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Location_Validation_Msg", ScenarioDetailsHashMap), "Validating PortOfDischarge Location to Terminal Mapping in Booking Module", ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_PortOfLoading", ScenarioDetailsHashMap,10), orSeaBooking,"LocationCode", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PortOfLoading",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
			AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_PortOfDispatch", ScenarioDetailsHashMap,10), orSeaBooking,"LocationCode", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PortOfDischarge",ScenarioDetailsHashMap),ScenarioDetailsHashMap);

		}

	}//FUNC007 End

	//Sangeetha- Date Validation CreatedDate And LastModified Date
	public static void validationOfCreatedDateAndLastModifiedDate(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		Booking_SearchList(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_MainDetails", ScenarioDetailsHashMap,10),2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		//Date Validation Start Author-Sangeeta
		//FUNC062.9-Date Validation(Created Date / Time)
		String ActualCreatedDate =  driver.findElement(By.id("createdDate")).getAttribute("value");
		String ActualCreatedTime =  driver.findElement(By.name("createdTime")).getAttribute("value");
		String ExpectedCreatedDate = ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "CreatedDate", ScenarioDetailsHashMap);
		String ExpectedCreatedTime = ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "CreatedTime", ScenarioDetailsHashMap);
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
		String ExpectedLastModifiedDate = ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "CreatedDate", ScenarioDetailsHashMap);
		String ExpectedLastModifiedTime = ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "CreatedTime", ScenarioDetailsHashMap);
		String ActualLastModifiedDateTime = ActualLastModifiedDate.concat(" "+ActualLastModifiedTime);
		String ExpectedLastModifiedDateTime = ExpectedLastModifiedDate.concat(" "+ExpectedLastModifiedTime);
		try {
			GenericMethods.compareDatesByCompareTo("dd-MM-yyyy hh:mm", ActualLastModifiedDateTime, ExpectedLastModifiedDateTime, "Greater", "Last Modified Date / Time is greater than Created Date / Time", "Last Modified Date / Time is not greater than Created Date / Time", ScenarioDetailsHashMap);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		//Date Validation end
		Booking_Save(driver, ScenarioDetailsHashMap, RowNo);
	}


	//Sandeep- FUNC48 - Below method is to perform seach functionality in booking screen after entering details for all search fields. 
	public static void BookingSearchList_Validations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.seaBooking, "Sea Booking",ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Textbox_BookingId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"BookingId", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Search_BookingDate_From", ScenarioDetailsHashMap,10), GenericMethods.getDate(-2), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Search_BookingDate_To", ScenarioDetailsHashMap,10), GenericMethods.getDate(+2), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Search_PickupDate_From", ScenarioDetailsHashMap,10), GenericMethods.getDate(-365), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Search_PickupDate_To", ScenarioDetailsHashMap,10), GenericMethods.getDate(+365), 2);
		GenericMethods.selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "Search_ConsoleType", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "LoadTypeSBC",ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Search_Reference", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"ReferenceId", ScenarioDetailsHashMap), 2);	
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Search_PortOfLoading", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"PortOfLoading", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Search_DestinationBranch", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"DestBranch", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Search_PortOfDischarge", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"PortOfDischarge", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Search_ServiceLevel", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"ServiceLevel", ScenarioDetailsHashMap), 2);
		AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_Customer", ScenarioDetailsHashMap,10), orSeaBooking, "PartyId", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"PartyId", ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_Shipper",ScenarioDetailsHashMap, 10), orSeaBooking,"PartyId", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Shipper_PartyId",ScenarioDetailsHashMap),ScenarioDetailsHashMap);		
		AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Btn_Consignee",ScenarioDetailsHashMap, 10), orSeaBooking, "PartyId", ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ConsigneeNickName", ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "Search_Status", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Shipment_Status", ScenarioDetailsHashMap));
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(3000);
		GenericMethods.assertInnerText(driver, By.id("dtBookingId1"), null, ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"BookingId", ScenarioDetailsHashMap), "Booking ID", "Verifying Booking ID details in grid.", ScenarioDetailsHashMap);

	}

	//Pavan FUNC0033 Below method will perform save functionality for Copied shipments
	public static void copySeaBooking(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) {
		AppReusableMethods.selectMenu(driver, ETransMenu.seaBooking,"Sea Booking",ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(6000);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "CopyButton", ScenarioDetailsHashMap,10), 2);
		GenericMethods.assertDropDownText(driver, null, orSeaBooking.getElement(driver, "LoadType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "LoadTypeSBC", ScenarioDetailsHashMap), "Booking_Load_Type", "Validating Load Type in Copy Functionality", ScenarioDetailsHashMap);
		GenericMethods.assertDropDownText(driver, null, orSeaBooking.getElement(driver, "HouseType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "HouseType", ScenarioDetailsHashMap), "Booking_HouseType", "Validating Houses Type in Copy Functionality", ScenarioDetailsHashMap);
		GenericMethods.assertValue(driver, null, orSeaBooking.getElement(driver, "Shipper", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ShipperNickName", ScenarioDetailsHashMap), "Booking_ShipperName", "Validating ShipperName in Copy Functionality", ScenarioDetailsHashMap);
		GenericMethods.assertValue(driver, null, orSeaBooking.getElement(driver, "Consignee", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ConsigneeNickName", ScenarioDetailsHashMap), "Booking_ConsigneeName", "Validating ConsigneeName in Copy Functionality", ScenarioDetailsHashMap);
		GenericMethods.assertValue(driver, null, orSeaBooking.getElement(driver, "PortOfLanding", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PortOfLoading", ScenarioDetailsHashMap), "Booking_POL", "Validating Port of Loading in Copy Functionality", ScenarioDetailsHashMap);
		GenericMethods.assertValue(driver, null, orSeaBooking.getElement(driver, "PortOfDischarge", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PortOfDischarge", ScenarioDetailsHashMap), "Booking_POD", "Validating Port of Discharge in Copy Functionality", ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(12000);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(3000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}

		GenericMethods.assertTwoValues(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "CopyShipment_Msg", ScenarioDetailsHashMap), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "CopyShipment_Msg", ScenarioDetailsHashMap), "Validating Booking Shipment Created by copy Functionality", ScenarioDetailsHashMap);
	}//FUNC0033 End 

	//Mastan: Validation for Audit Trail
	public static void SeaBooking_AuditTrail(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo) throws InterruptedException
	{
		ObjectRepository orAuditTrail = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/AuditTrail.xml");
		Booking_SearchList(driver,ScenarioDetailsHashMap,RowNo);
		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_MainDetails", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(3000);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "InstructionsRemarks", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"ModifyInstructionRemarks", ScenarioDetailsHashMap), 2);		
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "ShipperReferance", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"ModifyShipperReference", ScenarioDetailsHashMap), 2);		
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "ConsigneeReferance", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"ModifyConsigneeReference", ScenarioDetailsHashMap), 2);		

		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			System.out.println("no Alerts present");
		}

		GenericMethods.pauseExecution(3000);

		String bookingIdSummary=GenericMethods.getInnerText(driver, null, orSeaBooking.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 20), 2);

		String bookingID=bookingIdSummary.split(":")[2].trim();
		String modifyMes=bookingIdSummary.split(":")[1].trim();
		GenericMethods.assertTwoValues(bookingID, ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "BookingId", ScenarioDetailsHashMap), "Validating Booking Id", ScenarioDetailsHashMap);
		/*GenericMethods.assertTwoValues(modifyMes, ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ModifyMessage", ScenarioDetailsHashMap), "Validating Booking Modify Message", ScenarioDetailsHashMap);
		System.out.println("****"+modifyMes+"*****");
		System.out.println("****"+ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ModifyMessage", ScenarioDetailsHashMap)+"*****");
		 */
		Booking_SearchList(driver,ScenarioDetailsHashMap,RowNo);

		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "AuditButton",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		String title=GenericMethods.getInnerText(driver, null, orCommon.getElement(driver, "BannerTitle", ScenarioDetailsHashMap,10), 2);
		GenericMethods.assertTwoValues(title,"Audit Trail", "Validating Page title for Audit Trail", ScenarioDetailsHashMap);
		String bookingID_grid=GenericMethods.getInnerText(driver, null, orAuditTrail.getElement(driver, "Booking_ID", ScenarioDetailsHashMap,10), 2);
		String User_grid=GenericMethods.getInnerText(driver, null, orAuditTrail.getElement(driver, "UserName", ScenarioDetailsHashMap,10), 2);
		String UserName=GenericMethods.getPropertyValue("userName", WebDriverInitilization.configurationStructurePath);
		GenericMethods.assertTwoValues(bookingID_grid,bookingID, "Validating Booking ID in Audit Trail grid", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(User_grid,UserName, "Validating UserName in Audit Trail grid", ScenarioDetailsHashMap);
		String actionID=GenericMethods.getInnerText(driver, null, orAuditTrail.getElement(driver, "ActionID", ScenarioDetailsHashMap,10), 2);
		GenericMethods.clickElement(driver, null,orAuditTrail.getElement(driver, "AuditFieldChanges_Tab",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		String InsRemarks=ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "InstructionRemarksField", ScenarioDetailsHashMap);
		String shipperRef=ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ShipperRefField", ScenarioDetailsHashMap);
		String consigneeRef=ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ConsigneeRefField", ScenarioDetailsHashMap);
		Actions builder=new Actions(driver);
		int i=1;
		while(i>0){
			if(actionID.equalsIgnoreCase(driver.findElement(By.id("dtAuditMasterDtlId"+i)).getText())){

				if(driver.findElement(By.id("dtFieldName"+i)).getText().equalsIgnoreCase(InsRemarks)){
					String Original_Grid=driver.findElement(By.id("dtOriginalValue"+i)).getText();
					GenericMethods.highlightElement(driver.findElement(By.id("dtOriginalValue"+i)), driver);
					String Modified_Grid=driver.findElement(By.id("dtModifiedValue"+i)).getText();
					GenericMethods.highlightElement(driver.findElement(By.id("dtModifiedValue"+i)), driver);
					String Original=ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"InstructionsRemarks", ScenarioDetailsHashMap);
					String Modified=ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"ModifyInstructionRemarks", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(Original_Grid,Original, "Validating Instruction Original Value in Audit Trail grid", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(Modified_Grid,Modified, "Validating Instruction Modified Value in Audit Trail grid", ScenarioDetailsHashMap);
					System.out.println("Instruction Remarks");
				}
				else if(driver.findElement(By.id("dtFieldName"+i)).getText().equalsIgnoreCase(shipperRef)){
					String Original_Grid=driver.findElement(By.id("dtOriginalValue"+i)).getText();
					GenericMethods.highlightElement(driver.findElement(By.id("dtOriginalValue"+i)), driver);
					String Modified_Grid=driver.findElement(By.id("dtModifiedValue"+i)).getText();
					GenericMethods.highlightElement(driver.findElement(By.id("dtModifiedValue"+i)), driver);
					String Original=ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"ShipperReference", ScenarioDetailsHashMap);
					String Modified=ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"ModifyShipperReference", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(Original_Grid,Original, "Validating Shipper Ref Original Value in Audit Trail grid", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(Modified_Grid,Modified, "Validating Shipper Ref Modified Value in Audit Trail grid", ScenarioDetailsHashMap);	
					System.out.println("Shipper Ref");
				}
				else if(driver.findElement(By.id("dtFieldName"+i)).getText().equalsIgnoreCase(consigneeRef)){
					String Original_Grid=driver.findElement(By.id("dtOriginalValue"+i)).getText();
					GenericMethods.highlightElement(driver.findElement(By.id("dtOriginalValue"+i)), driver);
					String Modified_Grid=driver.findElement(By.id("dtModifiedValue"+i)).getText();
					GenericMethods.highlightElement(driver.findElement(By.id("dtModifiedValue"+i)), driver);
					String Original=ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"ConsigneeReference", ScenarioDetailsHashMap);
					String Modified=ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"ModifyConsigneeReference", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(Original_Grid,Original, "Validating Consignee Ref Original Value in Audit Trail grid", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(Modified_Grid,Modified, "Validating Consignee Ref Modified Value in Audit Trail grid", ScenarioDetailsHashMap);	
					System.out.println("Consignee Ref");
				}   		
			}
			i=i+1;
			if (driver.findElements(By.id("dtAuditMasterDtlId"+i)).size()>0)
				builder.moveToElement(driver.findElement(By.id("dtAuditMasterDtlId"+i))).build().perform();
			else
				i=0;
		}

	}

	//Sandeep- FUNC041

	//Sandeep- FUNC041-Below method is to perform validation for Denied Status in Search Screen grid.
	public static void Booking_TCS_DeniedParty(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		if(ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"DeniedRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			Booking_SearchList(driver, ScenarioDetailsHashMap, RowNo);
			GenericMethods.assertInnerText(driver, By.id("dtDenied1"), null, ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"DeniedStatus", ScenarioDetailsHashMap), "Is Denied", "Verifying Denied Status for shipment shipment in grid", ScenarioDetailsHashMap);

		}
	}


	//Sandeep- FUNC041- Below method is to perform Audit Trail for Denied Party functionlaity
	public static void Booking_DeniedParty_Audit(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo)
	{

		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "AuditButton", ScenarioDetailsHashMap,10), 2);
		GenericMethods.assertInnerText(driver, By.id("dtUserId1"), null, GenericMethods.getPropertyValue("userName", System.getProperty("user.dir")+"/Configurations/base.properties"), "User ID", "Verifying User ID column in the grid", ScenarioDetailsHashMap);
		GenericMethods.assertInnerText(driver, By.id("dtRemarks1"), null, ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"Audit_Remarks", ScenarioDetailsHashMap), "Remarks", "Verifying Remarks column in the grid", ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "CloseButton", ScenarioDetailsHashMap,10), 2);
	}

	//Sandeep- FUNC041 - Below method is to perform unblocking of the shipment for Denied party shipment
	public static void Licensee_DeniedPartyUnblocking(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.Licensee_DeniedPartyUnblocking, "Denied Party",ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DeniedParty_ShipmentReference", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"BookingId", ScenarioDetailsHashMap), 2);
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

		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DeniedParty_ReasonCode", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"DeniedParty_ReasonCode", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DeniedParty_Comments", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo,"DeniedParty_Comments", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null,orDecisionTable.getElement(driver, "DeniedParty_ApplyComments", ScenarioDetailsHashMap,10), 2);
		GenericMethods.clickElement(driver, null,orDecisionTable.getElement(driver, "DeniedParty_ApproveAll", ScenarioDetailsHashMap,10), 2);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
		ExcelUtils.setCellData("SE_BookingMainDetails", RowNo, "DeniedStatus", "N", ScenarioDetailsHashMap);
	}

	//Sandeep- FUNC041 - Below method is to perform validation in Party Tab.
	public static void Booking_PartyTab_TCSValidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo)
	{
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "Parties_Tab", ScenarioDetailsHashMap,10), 2);
		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Button_PSHistory", ScenarioDetailsHashMap,10), 2);
		GenericMethods.selectWindow(driver);


		for (int TestData_PartyDetails = 1; TestData_PartyDetails <= ExcelUtils.getCellDataRowCount("SE_BookingPartyDetails", ScenarioDetailsHashMap); TestData_PartyDetails++) 
		{
			for (int GridRowID = 1; GridRowID <= driver.findElements(By.xpath("//table[@id='partyStatusGrid']/tbody/tr")).size(); GridRowID++) 
			{
				String xpathPrefix = "//table[@id='partyStatusGrid']/tbody/tr["+GridRowID+"]/";
				if(ExcelUtils.getCellData("SE_BookingPartyDetails", TestData_PartyDetails,"PartyRoles", ScenarioDetailsHashMap).equalsIgnoreCase(GenericMethods.getInnerText(driver, By.xpath(xpathPrefix+"td[3]"), null, 10))
						&& ExcelUtils.getCellData("SE_BookingPartyDetails", TestData_PartyDetails,"PSStatus", ScenarioDetailsHashMap).equalsIgnoreCase(GenericMethods.getInnerText(driver, By.xpath(xpathPrefix+"td[4]"), null, 10))
				)
				{
					GenericMethods.assertInnerText(driver, By.xpath(xpathPrefix+"td[3]"), null, ExcelUtils.getCellData("SE_BookingPartyDetails", TestData_PartyDetails,"PartyRoles", ScenarioDetailsHashMap), "Party Roles", "Verifying Party Roles in the PS History window", ScenarioDetailsHashMap);
					GenericMethods.assertInnerText(driver, By.xpath(xpathPrefix+"td[4]"), null, ExcelUtils.getCellData("SE_BookingPartyDetails", TestData_PartyDetails,"PSStatus", ScenarioDetailsHashMap), "PSStatus", "Verifying PSStatus in the PS History window", ScenarioDetailsHashMap);
					GenericMethods.assertInnerText(driver, By.xpath(xpathPrefix+"td[9]"), null, ExcelUtils.getCellData("SE_BookingPartyDetails", TestData_PartyDetails,"ServerityLevel", ScenarioDetailsHashMap), "Serverity Level", "Verifying Serverity Level in the PS History window", ScenarioDetailsHashMap);
					break;					

				}
			}
		}

		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "CloseButton", ScenarioDetailsHashMap,10), 2);
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "CloseButton", ScenarioDetailsHashMap,10), 2);
	}

	//Sangeeta- FUNC02 - Below method is to HS commodity if Quote Commodity and Cargo details HS commodity is not matched 
	public static void  HScommodityValidationForRating(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		Booking_SearchList(driver, ScenarioDetailsHashMap, RowNo);	
		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_CargoDetails", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(6000);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
		GenericMethods.pauseExecution(7000);
		AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Img_PackHsCommodity", ScenarioDetailsHashMap,10), orSeaBooking,"Code", ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "HSCommodity_Rating",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "ScheduleB_Pack", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Schedule_B",ScenarioDetailsHashMap), 2);
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
		AppReusableMethods.selectValueFromLov(driver, orSeaBooking.getElement(driver, "Img_PackHsCommodity", ScenarioDetailsHashMap,10), orSeaBooking,"Code", ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "HSCommodity",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "ScheduleB_Pack", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Schedule_B",ScenarioDetailsHashMap), 2);
		Booking_Save(driver, ScenarioDetailsHashMap, RowNo);
	}
	//Sangeeta- RAT03/04 - Below method is to  validating  selected Contract/Quote is Hazardous or not
	public static void  HazardousValidationForRating(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		Booking_SearchList(driver, ScenarioDetailsHashMap, RowNo);	
		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_CargoDetails", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(6000);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
		GenericMethods.pauseExecution(7000);
		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "GoHazardous_Button", ScenarioDetailsHashMap,10), 2);	
		GenericMethods.pauseExecution(2000);
		try{
			GenericMethods.selectWindow(driver);
			if(driver.findElements(By.id("UNDGNumber")).size()>0)
				GenericMethods.assertTwoValues("PopUp Screen Opened", "PopUp Screen Not Opened", "Verifying Haz PopUp Screen without checking HAZ", ScenarioDetailsHashMap);
			else
				GenericMethods.assertTwoValues("PopUp Screen Not Opened", "PopUp Screen Not Opened", "Verifying Haz PopUp Screen without checking HAZ", ScenarioDetailsHashMap);
		}catch(Exception e){
			GenericMethods.assertTwoValues("PopUp Screen Not Opened", "PopUp Screen Not Opened", "Verifying Haz PopUp Screen without checking HAZ", ScenarioDetailsHashMap);
		}



		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Hazardous_CheckBox", ScenarioDetailsHashMap,10), 2);	
		GenericMethods.pauseExecution(4000);
		try{
			GenericMethods.selectWindow(driver);
			if(driver.findElements(By.id("UNDGNumber")).size()>0)
				GenericMethods.assertTwoValues("PopUp Screen Opened", "PopUp Screen Opened", "Verifying Haz PopUp Screen", ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Hazardous_NumofPack", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Haz_ItemsNo", ScenarioDetailsHashMap), 2);
			GenericMethods.selectOptionFromDropDown(driver,null,orSeaBooking.getElement(driver, "Hazardous_PackType", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Haz_ItemType", ScenarioDetailsHashMap));
			GenericMethods.action_Key(driver, Keys.TAB);
			orSeaBooking.getElement(driver, "Hazardous_netQuantity", ScenarioDetailsHashMap,10).sendKeys(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Haz_NetWeight", ScenarioDetailsHashMap));
			GenericMethods.action_Key(driver, Keys.TAB);
			GenericMethods.selectOptionFromDropDown(driver,null,orSeaBooking.getElement(driver, "Hazardous_netQuantityUom", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Haz_NetWeightUnit", ScenarioDetailsHashMap));
			GenericMethods.action_Key(driver, Keys.TAB);
			orSeaBooking.getElement(driver, "Hazardous_grossWeight", ScenarioDetailsHashMap,10).sendKeys(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "HazGrossWeight", ScenarioDetailsHashMap));
			GenericMethods.action_Key(driver, Keys.TAB);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Hazardous_UNDGNumber", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Haz_UNDGNumber", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Hazardous_contactName", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "HazContactName", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Hazardous_contactNumber", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Haz_ContactNumber", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Hazardous_properShippingName", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Haz_ProperShippingName", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "Hazardous_technicalName", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Haz_TechnicalName", ScenarioDetailsHashMap), 2);
			GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);	
			GenericMethods.pauseExecution(4000);
			GenericMethods.switchToParent(driver);
			AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
		}catch(Exception e){
			GenericMethods.assertTwoValues("PopUp Screen Not Opened", "PopUp Screen Opened", "Verifying Haz PopUp Screen", ScenarioDetailsHashMap);

		}
		GenericMethods.clickElement(driver, null, orSeaBooking.getElement(driver, "Btn_Package_Add", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(3000);
		Booking_Save(driver, ScenarioDetailsHashMap, RowNo);
		//"EKFC10124 : Selected Contract/Quote is Not Hazardous.";
		System.out.println(GenericMethods.getInnerText(driver, null, orSeaBooking.getElement(driver, "HazMSG", ScenarioDetailsHashMap, 2), 10));
		System.out.println(driver.findElement(By.xpath("html/body/form/table[1]/tbody/tr[2]/td/table/tbody/tr[2]/td/table[2]/tbody/tr/td/font/b")).getText());
		String HazErrMSGSummary = GenericMethods.getInnerText(driver, null, orSeaBooking.getElement(driver, "HazMSG", ScenarioDetailsHashMap, 2), 10);
		String[] HazErrorMSGApp =HazErrMSGSummary.split(" : ");
		String HazErrMSGApp = HazErrorMSGApp[1];
		System.out.println(HazErrMSGApp);
		GenericMethods.pauseExecution(3000);
		GenericMethods.assertTwoValues(HazErrMSGApp, ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "HazErrorMSG", ScenarioDetailsHashMap), "Entering the Cargo details with giving the Haz details while validating  selected Contract/Quote is not Hazardous", ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(2000);
		if(HazErrMSGApp.equalsIgnoreCase(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "HazErrorMSG", ScenarioDetailsHashMap)))
		{
			GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Hazardous_CheckBox", ScenarioDetailsHashMap,10), 2);
		}
		GenericMethods.clickElement(driver, null, orSeaBooking.getElement(driver, "Btn_Package_Add", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(3000);
		Booking_Save(driver, ScenarioDetailsHashMap, RowNo);	

	}	




}

