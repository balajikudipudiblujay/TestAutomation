package business.scenarios;

import global.reusables.ExcelUtils;
import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import app.reuseables.AppReusableMethods;
import app.reuseables.ETransMenu;

public class AirBooking {
	static ObjectRepository orCommon = new ObjectRepository(
			System.getProperty("user.dir")
			+ "/ObjectRepository/CommonObjects.xml");
	//static ObjectRepository orSeaBooking = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/SeaBooking.xml");
	static ObjectRepository orAirBooking = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/AirBooking.xml");
	public static void copyAirBookingDetails(WebDriver driver,
			HashMap<String, String> ScenarioDetailsHashMap, int RowNo) {
		ArrayList<String> values =new ArrayList<String>();
		AppReusableMethods.selectMenu(driver, ETransMenu.airBooking,"Air Booking",ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(5000);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "CopyButton",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(8000);
		GenericMethods.selectOptionFromDropDown(driver, null, orAirBooking.getElement(driver, "PickUpRequired",ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "PickUp_Required",ScenarioDetailsHashMap));
		//MBL MainDetails
		String HBLBasis = ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "HBLBasis", ScenarioDetailsHashMap);
		String consolidationType = ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "HouseType", ScenarioDetailsHashMap);
		if(!consolidationType.equals("Consolidation"))
		{
			if(!HBLBasis.equalsIgnoreCase("Assembly Shipment"))
			{
				airBookingMBLMainDetails(driver, ScenarioDetailsHashMap, RowNo);	
			}
		}
	
		//Pickup Instruction
		airBookingPickUpInstructions(driver, ScenarioDetailsHashMap, RowNo);

		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(6000);
		String bookingIdSummary=GenericMethods.getInnerText(driver, null, orAirBooking.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 20), 2);
		String CommonRefereNumber = bookingIdSummary.split("COMMON Ref# :")[1].trim().split(",")[0].trim();
		ExcelUtils.setCellData("Air_BookingMainDetails", RowNo, "ReferenceId", CommonRefereNumber, ScenarioDetailsHashMap);
	
		//String bookingId=bookingIdSummary.substring(bookingIdSummary.indexOf(":")+1,bookingIdSummary.indexOf(",")).trim();
		String bookingId = bookingIdSummary.split(" ")[9];
		//System.out.println("bookingId :"+bookingId);
		GenericMethods.assertTwoValues(bookingIdSummary.split(" : ")[1], ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "AirBooking_SuccessMessage", ScenarioDetailsHashMap), "Validating Booking Creation Msg", ScenarioDetailsHashMap);
		//System.out.println("actual :"+bookingIdSummary.split(" : ")[1]);
		//System.out.println("expected :"+ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "AirBooking_SuccessMessage", ScenarioDetailsHashMap));
		ExcelUtils.setCellData("Air_BookingMainDetails", RowNo, "BookingId", bookingId, ScenarioDetailsHashMap);
		//ExcelUtils.setCellData("Air_PRSMainDetails", RowNo, "BookingId", bookingId, ScenarioDetailsHashMap);
		ExcelUtils.setCellData("Air_HAWBMainDetails",RowNo, "BookingId",bookingId, ScenarioDetailsHashMap);
		
	
	}
	public static void airBookingDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		GenericMethods.pauseExecution(10000);
		AppReusableMethods.selectMenu(driver, ETransMenu.airBooking,"Air Booking", ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(10000);
		GenericMethods.selectOptionFromDropDown(driver, null, orAirBooking.getElement(driver, "AirshipmentType",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap));		
		if(ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap).equalsIgnoreCase("House"))
		{
			GenericMethods.selectOptionFromDropDown(driver, null, orAirBooking.getElement(driver, "HawbType",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "HouseType", ScenarioDetailsHashMap));
		}
		else
		{
			GenericMethods.selectOptionFromDropDown(driver, null, orAirBooking.getElement(driver, "OrderType",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "OrderType",ScenarioDetailsHashMap));
		}
		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Btn_Shipper",ScenarioDetailsHashMap, 10), orAirBooking,"PartyId",ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "ShipperNickName",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		//GenericMethods.selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "PickUp_Required",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "PickUpRequired",ScenarioDetailsHashMap));
		
		GenericMethods.selectOptionFromDropDown(driver, null,orAirBooking.getElement(driver, "PickUp_Required",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "PickUp_Required", ScenarioDetailsHashMap));
		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Btn_Consignee",ScenarioDetailsHashMap, 10), orAirBooking,"PartyId",ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "ConsigneeNickName",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ServiceLevel",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "ServiceLevel", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "OrgServiceType",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "ServiceTypeOrig", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "DestServiceType",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "ServiceTypeDest", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "OriginDeptId",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "OriginDept", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "DestinationBranch",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "DestBranch", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "DestDeptId",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "DestDept", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Customer",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "Customer", ScenarioDetailsHashMap), 2);
		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Btn_AirportOfDeparture",ScenarioDetailsHashMap, 10), orAirBooking,"LocationCode",ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "AirportOfDeparture",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Btn_AirportOfDestination",ScenarioDetailsHashMap, 10), orAirBooking,"LocationCode",ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "AirportOfDestination",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Instructions_Remarks",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMainDetails", RowNo,"InstructionsRemarks", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Shipper_Referance",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingMainDetails", RowNo,"ShipperReference", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "PickUpReferance",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingMainDetails", RowNo,"PickUpReference", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ConsigneeReferance",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMainDetails", RowNo,"ConsigneeReference", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "DeliveryReferance",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingMainDetails", RowNo,"DeliveryReference", ScenarioDetailsHashMap), 2);
//		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETAFinalDate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingMainDetails", RowNo,"ETAFinalDest", ScenarioDetailsHashMap), 2);
//		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETAFinalDestTime",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "ETAFinalTime", ScenarioDetailsHashMap), 2);
		GenericMethods.pauseExecution(10000);
		
		
		if(ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "eAWBRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "Checkbox_eAWB",ScenarioDetailsHashMap, 10), 2);
		}
		
		//Sandeep- MAWBID field is enabled in main details screen only for House Type = B2B
		if(ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "HouseType", ScenarioDetailsHashMap).equalsIgnoreCase("B2B"))
		{
			if(ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "MAWBIdRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Zoom_MAWBId",ScenarioDetailsHashMap, 10), orAirBooking,"Zoom_MAWBId_MAWBIdTextbox",ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "MAWBId",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
			}
		}	
		
		
		
		//MBL MainDetails
		if(!ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "HouseType", ScenarioDetailsHashMap).equalsIgnoreCase("Consolidation"))
		{
			if(!ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "HBLBasis", ScenarioDetailsHashMap).equalsIgnoreCase("Assembly Shipment"))
			{
				airBookingMBLMainDetails(driver, ScenarioDetailsHashMap, RowNo);	
			}
		}	

		GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "Tab_CargoDetails",ScenarioDetailsHashMap, 10), 2);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
		
		GenericMethods.pauseExecution(5000);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Textbox_TotalPieces", ScenarioDetailsHashMap, 10),"37", 2);
		GenericMethods.pauseExecution(5000);
		
		/*// CargoDetails
		airBookingCargoPackDetails(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.pauseExecution(3000);
		
		
		//Pickup instruction
		airBookingPickUpInstructions(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.pauseExecution(4000);
		//eDoc
		GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "Tab_eDoc",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);*/
		
		
	}

	public static void airBookingMBLMainDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "Tab_MAWB_MAIN_DETAILS",ScenarioDetailsHashMap, 10),2);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Carrier_ID",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo, "CarrierId",ScenarioDetailsHashMap), 2);
		//GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "ServiceLevel_ID",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "ServiceLevel", ScenarioDetailsHashMap), 2);
		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "ServiceLevelIdImage_Lov",ScenarioDetailsHashMap, 10), orAirBooking,"Code",ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo, "ServiceLevel",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Btn_AirportOfDeparture",ScenarioDetailsHashMap, 10), orAirBooking,"LocationCode",ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo, "AirportOfDeparture",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Btn_AirportOfDestination",ScenarioDetailsHashMap, 10), orAirBooking,"LocationCode",ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo, "AirportOfDestination",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		
		String randomMAWB_ID=ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo, "Prefix_MAWBID", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(8)+"";
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "MAWBID",ScenarioDetailsHashMap, 20),  randomMAWB_ID, 20);
		GenericMethods.pauseExecution(3000);
		StringBuilder xl= new StringBuilder(randomMAWB_ID);
		String Updated_MAWB_ID=xl.insert(3, "-").insert(8, " ").toString();
		ExcelUtils.setCellData("Air_BookingMAWBMainDetails", RowNo, "Prefix_MAWBID", randomMAWB_ID, ScenarioDetailsHashMap);
		ExcelUtils.setCellData("Air_BookingMAWBMainDetails", RowNo, "MAWB_ID", Updated_MAWB_ID, ScenarioDetailsHashMap);
		
//		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_CarrierCutOffDate",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo, "CarrierCutoffDate",ScenarioDetailsHashMap), 2);
//		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_carrierCutOffTime",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo, "CarrierCutoffTime",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo, "ETD",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo, "ETDTime",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo,"ETA", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo,"ETATime", ScenarioDetailsHashMap), 2);
//		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_ETAFinalDestDate",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo,"ETAFinalDest", ScenarioDetailsHashMap), 2);
//		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_ETAFinalDestTime",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo,"ETAFinalDestTime", ScenarioDetailsHashMap), 2);
		
		if(ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo, "FlightScheduleRequired",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Zoom_FlightSchedule",ScenarioDetailsHashMap, 10), orAirBooking,	"Zoom_FlightSchedule_FlightScheduleIDTextbox", ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo, "FlightScheduleId",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "DestinationBranch",ScenarioDetailsHashMap, 10),2);
			GenericMethods.pauseExecution(3000);
		
		}
		
		else
		{
			if(ExcelUtils.getCellDataRowCount("Air_BookingMAWBMainDetails", ScenarioDetailsHashMap)>1)
			{
				GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "Button_FlightDetails",ScenarioDetailsHashMap, 10),2);
			}
			
			for (int FlightScheduleRowNo = 1; FlightScheduleRowNo <= ExcelUtils.getCellDataRowCount("Air_BookingMAWBMainDetails", ScenarioDetailsHashMap); FlightScheduleRowNo++) 
			{
				int GridRowID = FlightScheduleRowNo+1;
				String GridXpathPrefix ="//td[@id='MASTERDATAAREA']/fieldset[2]/table[2]/tbody/tr["+GridRowID+"]/";
				AppReusableMethods.selectValueFromLov(driver, GenericMethods.locateElement(driver, By.xpath(GridXpathPrefix+"td[1]/a/img"), 10), orAirBooking,"Zoom_FlightTo_LocationCodeTextbox",ExcelUtils.getCellData("Air_BookingMAWBMainDetails", FlightScheduleRowNo, "Grid_FlightTo",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
				AppReusableMethods.selectValueFromLov(driver, GenericMethods.locateElement(driver, By.xpath(GridXpathPrefix+"td[2]/a/img"), 10), orAirBooking,"Zoom_Carrier_CarrierIdTextbox",ExcelUtils.getCellData("Air_BookingMAWBMainDetails", FlightScheduleRowNo, "Grid_Carrier",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, By.xpath(GridXpathPrefix+"td[3]/input"), null,ExcelUtils.getCellData("Air_BookingMAWBMainDetails", FlightScheduleRowNo,"Grid_FlightNo", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, By.xpath(GridXpathPrefix+"td[4]/input"), null,ExcelUtils.getCellData("Air_BookingMAWBMainDetails", FlightScheduleRowNo,"Grid_Day", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, By.xpath(GridXpathPrefix+"td[5]/input[1]"), null,ExcelUtils.getCellData("Air_BookingMAWBMainDetails", FlightScheduleRowNo,"Grid_ServiceCutOffDate", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, By.xpath(GridXpathPrefix+"td[5]/input[2]"), null,ExcelUtils.getCellData("Air_BookingMAWBMainDetails", FlightScheduleRowNo,"Grid_ServiceCutOffTime", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, By.xpath(GridXpathPrefix+"td[6]/input[1]"), null,ExcelUtils.getCellData("Air_BookingMAWBMainDetails", FlightScheduleRowNo,"Grid_DepartureDate", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, By.xpath(GridXpathPrefix+"td[6]/input[2]"), null,ExcelUtils.getCellData("Air_BookingMAWBMainDetails", FlightScheduleRowNo,"Grid_DepartureTime", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, By.xpath(GridXpathPrefix+"td[7]/input[1]"), null,ExcelUtils.getCellData("Air_BookingMAWBMainDetails", FlightScheduleRowNo,"Grid_ArrivalDate", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, By.xpath(GridXpathPrefix+"td[7]/input[2]"), null,ExcelUtils.getCellData("Air_BookingMAWBMainDetails", FlightScheduleRowNo,"Grid_ArrivalTime", ScenarioDetailsHashMap), 2);
			}
		}
		
		/*AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Btn_FlightTo",ScenarioDetailsHashMap, 10), orAirBooking, "LocationCode",ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo,"FlightTo", ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "FlightNo",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingMAWBMainDetails", RowNo,"FlightNo", ScenarioDetailsHashMap), 2);*/
	}

	public static void airBookingCargoPackDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "Tab_CargoDetails",ScenarioDetailsHashMap, 10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		GenericMethods.pauseExecution(5000);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_Packages",ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "Packages",ScenarioDetailsHashMap), 2);
/*		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Img_Type",ScenarioDetailsHashMap, 10), orAirBooking,"Packtype", 
				ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "Type",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
*/
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Type", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "Type",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "SubPack",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "SubPack",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "SubPackType",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "SubType",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "PackGrossWeight",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "GrossWeight",ScenarioDetailsHashMap), 2);
		GenericMethods.selectOptionFromDropDown(driver, null, orAirBooking.getElement(driver, "UOW",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "UOW",ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "PackNetWeight",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "NetWeight",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Length",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "Length",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Width",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "Width",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Height",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "Height",ScenarioDetailsHashMap), 2);
		GenericMethods.selectOptionFromDropDown(driver, null, orAirBooking.getElement(driver, "UOM",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "UOM",ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Volume",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "Volume",ScenarioDetailsHashMap), 2);
		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Img_PackCommodity",ScenarioDetailsHashMap, 10), orAirBooking,"Code", ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "Commodity",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Img_PackHsCommodity",ScenarioDetailsHashMap, 10), orAirBooking,"Code", ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "HSCommodity",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Img_FRTClass",ScenarioDetailsHashMap, 10), orAirBooking,"Code", ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "FRTClass",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		//GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "ScheduleB",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "ScheduleB",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "GoodsValue",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "GoodsValue",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "PackageCurrency",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "GoodsCurrency",ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orAirBooking.getElement(driver, "Btn_Package_Add",ScenarioDetailsHashMap, 10), 2);
		//Invoice
		if (ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "SSCCFormat",ScenarioDetailsHashMap).equalsIgnoreCase("Standard")) {
			GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "Standard",ScenarioDetailsHashMap, 10), 2);	
		}else{
			GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "NonStandard",ScenarioDetailsHashMap, 10), 2);	
		}
		//random invoice ID generation
		//String randomInvoice_ID=ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", RowNo, "MAWBID", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(8)+"";
		//System.out.println("randomMAWB_ID:::::"+randomInvoice_ID);
		//GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "InvoiceNumber",ScenarioDetailsHashMap, 10), randomInvoice_ID, 2);
		//GenericMethods.replaceTextofTextField(driver, null, orSeaBooking.getElement(driver, "InvoiceDate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "InvoiceDate",ScenarioDetailsHashMap), 2);					
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "PoNumber",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "PoNumber",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "PartId_Export",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "PartId_Export",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "GrossWeight",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "GrossWeight",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "NetWeight",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "NetWeight",ScenarioDetailsHashMap), 2);
		GenericMethods.selectOptionFromDropDown(driver, null, orAirBooking.getElement(driver, "UOW",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "UOW",ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "StatQty1",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "StatQty1",ScenarioDetailsHashMap), 2);
		//AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "StatQty1TypeLov",ScenarioDetailsHashMap, 10), orAirBooking,"BoxType", ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "StatQty1Type",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "StatQty1Type", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "StatQty1Type",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "StatQty2",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "StatQty2",ScenarioDetailsHashMap), 2);
		//AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "StatQty2TypeLov",ScenarioDetailsHashMap, 10), orAirBooking,"BoxType", ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "StatQty2Type",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "StatQty2Type", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "StatQty2Type",ScenarioDetailsHashMap), 2);
		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "InvoiceCommodityLov",ScenarioDetailsHashMap, 10), orAirBooking,"Code", ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "Commodity",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "InvHSCommodityLov",ScenarioDetailsHashMap, 10), orAirBooking,"Code", ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "HSCommodity",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "InvGoodsDescription",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "GoodsDescription",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "InvGoodsValue",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "GoodsValue",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "InvCurrencyimp",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_BookingCargoDetails", RowNo, "GoodsCurrency",ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orAirBooking.getElement(driver, "Btn_CommInvButton",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.clickElement(driver, null, orAirBooking.getElement(driver, "Btn_Invoice_Add",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "Btn_Invoice_Add",ScenarioDetailsHashMap, 10), 2);
	}

	public static void airBookingPickUpInstructions(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		//GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_CargoDetails",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "Tab_PickUpInstructions",ScenarioDetailsHashMap, 10),2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "Btn_Add",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(8000);
	}

	public static void airAssemblyBookingDetails(WebDriver driver,
			HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.airBooking,"Air Booking",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(10000);
		String ShipmentType = ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap);
		//System.out.println("ShipmentType "+ ShipmentType);
		if(ShipmentType.equalsIgnoreCase("House"))
		{
			GenericMethods.selectOptionFromDropDown(driver, null, orAirBooking.getElement(driver, "AirshipmentType",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap));
			GenericMethods.selectOptionFromDropDown(driver, null, orAirBooking.getElement(driver, "HawbType",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "HouseType", ScenarioDetailsHashMap));
		}
		else
		{
			GenericMethods.selectOptionFromDropDown(driver, null, orAirBooking.getElement(driver, "AirshipmentType",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "ShipmentType", ScenarioDetailsHashMap));
			GenericMethods.selectOptionFromDropDown(driver, null, orAirBooking.getElement(driver, "OrderType",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "OrderType",ScenarioDetailsHashMap));
			GenericMethods.isFieldEnabled(driver, null, orAirBooking.getElement(driver, "HawbType",ScenarioDetailsHashMap, 10), 2);

		}
		//Selecting Assembly HAWB Id
		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "HAWB_AssemblyIdBtn",ScenarioDetailsHashMap, 10), orAirBooking,"HAWB_AssemblySearch", 
				ExcelUtils.getCellData("Air_HAWBMainDetails", RowNo, "HBLId",ScenarioDetailsHashMap),ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(6000);
		GenericMethods.selectOptionFromDropDown(driver, null,orAirBooking.getElement(driver, "PickUp_Required",ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "PickUp_Required", ScenarioDetailsHashMap));

		// CargoDetails
		airBookingCargoPackDetails(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.pauseExecution(3000);

		airBookingPickUpInstructions(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.pauseExecution(4000);
		//eDoc
		GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "Tab_eDoc",ScenarioDetailsHashMap, 10), 2);
		
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 2);
		String bookingIdSummary=GenericMethods.getInnerText(driver, null, orAirBooking.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 20), 2);
		//String bookingId=bookingIdSummary.substring(bookingIdSummary.indexOf(":")+1,bookingIdSummary.indexOf(",")).trim();
		String bookingId = bookingIdSummary.split(" ")[9];
		ExcelUtils.setCellData("Air_BookingMainDetails", RowNo, "Assembly_BookingID", bookingId, ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(bookingIdSummary.split(" : ")[1], ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "AirBooking_SuccessMessage", ScenarioDetailsHashMap), "Validating Booking Creation Msg", ScenarioDetailsHashMap);	
	}

	public static void Booking_SearchList(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.airBooking,"Air Booking",ScenarioDetailsHashMap);

		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Textbox_BookingId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_BookingMainDetails", RowNo,"BookingId", ScenarioDetailsHashMap), 2);		
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(3000);
	}

	public static void Booking_Save(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(5000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		String bookingIdSummary=GenericMethods.getInnerText(driver, null, orAirBooking.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 20), 2);
		String CommonReferenceNumber = bookingIdSummary.split("COMMON Ref# :")[1].trim().split(",")[0].trim();
		ExcelUtils.setCellData("Air_BookingMainDetails", RowNo, "ReferenceId", CommonReferenceNumber, ScenarioDetailsHashMap);
		//String bookingId=bookingIdSummary.substring(bookingIdSummary.indexOf(":")+1,bookingIdSummary.indexOf(",")).trim();
		String bookingId = bookingIdSummary.split(" ")[9];
		GenericMethods.assertTwoValues(bookingIdSummary.split(" : ")[1], ExcelUtils.getCellData("Air_BookingMainDetails", RowNo, "AirBooking_SuccessMessage", ScenarioDetailsHashMap), "Validating Booking Creation Msg", ScenarioDetailsHashMap);
		ExcelUtils.setCellData("Air_BookingMainDetails", RowNo, "BookingId", bookingId, ScenarioDetailsHashMap);
		//ExcelUtils.setCellData("Air_PRSMainDetails", RowNo, "BookingId", bookingId, ScenarioDetailsHashMap);
		ExcelUtils.setCellData("Air_HAWBMainDetails",RowNo, "BookingId",bookingId, ScenarioDetailsHashMap);
		ExcelUtils.setCellData("Air_WarehouseMainDtls",RowNo, "Booking_ID",bookingId, ScenarioDetailsHashMap);
	}


	//Sandeep-Jan15th 2015, Below method is to enter RoutePlan Details in Route Plan Window.
	public static void RoutePlan_Details(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		//System.out.println("inside value");
		GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "Tab_MainDetails", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(4000);
		GenericMethods.selectOptionFromDropDown(driver, null, orAirBooking.getElement(driver, "DDL_QuickLink", ScenarioDetailsHashMap,10) , "RoutePlan");
		GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "Button_QuickLink", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(5000);

		try{
			GenericMethods.handleAlert(driver, "accept");

		}catch (Exception e) {
			//System.out.println("no Alerts present");

		}
		
		int RoutePlan_rowCount=ExcelUtils.getCellDataRowCount("Air_BookingRoutePlanDetails", ScenarioDetailsHashMap);
		for (int RoutePlanRowNo = 1; RoutePlanRowNo <= RoutePlan_rowCount; RoutePlanRowNo++) 
		{
			int rowId =  RoutePlanRowNo+1;
			GenericMethods.clickElement(driver, By.xpath("//tr[@id='"+RoutePlanRowNo+"']/td[12]/img"),null, 2);
			GenericMethods.pauseExecution(2000);
			AppReusableMethods.selectValueFromLov(driver, GenericMethods.locateElement(driver, By.xpath("//tr[@id='"+RoutePlanRowNo+"']/td[4]/a/img"), 5), orAirBooking, "PartyId", ExcelUtils.getCellData("Air_BookingRoutePlanDetails", RoutePlanRowNo, "Party",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(2000);
			GenericMethods.replaceTextofTextField(driver, By.xpath("//tr[@id='"+RoutePlanRowNo+"']/td[4]/input"), null, Keys.TAB, 2);
			Select modeType = new Select(driver.findElement(By.xpath("//tr[@id='"+rowId+"']/td[5]/select")));
			modeType.selectByVisibleText(ExcelUtils.getCellData("Air_BookingRoutePlanDetails", RoutePlanRowNo, "Mode",ScenarioDetailsHashMap));
		}
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
	}
}
