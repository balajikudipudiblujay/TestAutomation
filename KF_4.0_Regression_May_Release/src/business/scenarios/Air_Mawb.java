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

import app.reuseables.AppReusableMethods;
import app.reuseables.ETransMenu;
public class Air_Mawb extends GenericMethods {
	static ObjectRepository Common=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/CommonObjects.xml");
	//static ObjectRepository orAirMawb=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/OceanExportOBL.xml");
	static ObjectRepository orAirMawb=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/AirMawb.xml");
	//static ObjectRepository orAirHAWB=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/HBL.xml");
	static ObjectRepository orAirHAWB=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/AirHAWB.xml");
	static ObjectRepository orAirBooking=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/AirBooking.xml");
	//static ObjectRepository orAirBooking=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/SeaBooking.xml");

	public static void pictAirExportsMAWB_Add(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ) throws AWTException{
		AppReusableMethods.selectMenu(driver, ETransMenu.HAWB, "HAWB", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "HAWBIDSearch", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "HBLId", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.assertInnerText(driver, null, orAirHAWB.getElement(driver, "GridHAWBId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "HBLId", ScenarioDetailsHashMap), "GridHAWBId", "Comparing hawb id",  ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(3000);
		GenericMethods.selectOptionFromDropDown(driver, null, orAirMawb.getElement(driver, "MAWBAdd_NavigationList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_MAWB", rowNo, "NavigationList", ScenarioDetailsHashMap));
		GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "MAWBAdd_NavigationList_QuickLink", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(6000);
		String Expected_MAWBPageName =ExcelUtils.getCellData("Air_MAWB", rowNo, "MAWB_Page_Name", ScenarioDetailsHashMap);
		String Actual_MAWBPageName = GenericMethods.getInnerText(driver, null, orAirMawb.getElement(driver, "MAWB_PageName", ScenarioDetailsHashMap, 10), 10);
		//System.out.println("Expected_MAWBPageName :"+Expected_MAWBPageName+"Actual_MAWBPageName :"+Actual_MAWBPageName);
		if(Actual_MAWBPageName.equals(Expected_MAWBPageName)){
			pictAirMAWBMainDetails(driver, ScenarioDetailsHashMap, rowNo);
			pictAirMAWBParties(driver, ScenarioDetailsHashMap, rowNo);
			pictAirMAWBAllocation(driver, ScenarioDetailsHashMap, rowNo);				
		}
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(10000);
		String MAWBIdSummary=GenericMethods.getInnerText(driver, null, orAirMawb.getElement(driver, "MAWB_VerificationMsG", ScenarioDetailsHashMap, 20), 2);
		//String MAWBId=MAWBIdSummary.substring(MAWBIdSummary.indexOf(":")+1,MAWBIdSummary.indexOf(",")).trim();
		String MAWBId=MAWBIdSummary.split(" : ")[2].substring(0, 13);
		//GenericMethods.assertTwoValues(bookingIdSummary.substring(0, bookingIdSummary.indexOf(":")), "-> Record Successfully Created with Booking Id ", "Validating Creation Msg", ScenarioDetailsHashMap);
		ExcelUtils.setCellData("Air_HAWB_MAWBMainDetails", rowNo, "MAWB_ID", MAWBId, ScenarioDetailsHashMap);
		//ExcelUtils.setCellDataKF("Air_HAWBMainDetails",rowNo, "BookingId",bookingId, ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(MAWBIdSummary.split(" : ")[1], ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", rowNo, "MAWB_SucessMSG", ScenarioDetailsHashMap), "Validating  MAWB Creation Msg", ScenarioDetailsHashMap);
		if(Actual_MAWBPageName.equals(Expected_MAWBPageName)){
			Air_Mawb_ProvisionalClose(driver, ScenarioDetailsHashMap, rowNo);	
			Air_Mawb_ConfirmonBoard(driver, ScenarioDetailsHashMap, rowNo);	
			Air_Mawb_CloseMAWB(driver, ScenarioDetailsHashMap, rowNo);	
		}
	}

	public static void pictAirMAWBMainDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "CarrierId", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_BookingMAWBMainDetails", rowNo, "CarrierId",ScenarioDetailsHashMap), 2);
		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "ServiceLevelIdImage_Lov", ScenarioDetailsHashMap, 10), orAirBooking,	"OrgCode", 
				ExcelUtils.getCellData("Air_BookingMAWBMainDetails", rowNo, "ServiceLevel",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		String randomMAWB_ID=ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", rowNo, "MAWBID", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(8)+"";
		//System.out.println("randomMAWB_ID:::::"+randomMAWB_ID);
		GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "MAWBID", ScenarioDetailsHashMap, 20),  randomMAWB_ID, 20);
		ExcelUtils.setCellData("Air_HAWB_MAWBMainDetails", rowNo, "MAWBID", randomMAWB_ID, ScenarioDetailsHashMap);
		ExcelUtils.setCellData("Air_MAWB", rowNo, "MAWBID", randomMAWB_ID, ScenarioDetailsHashMap);
		//GenericMethods.replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "MAWBID", 10), 
		//	ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", rowNo, "MAWBID", ScenarioDetailsHashMap), 2);
		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Btn_AirportOfDeparture", ScenarioDetailsHashMap, 10), orAirBooking,"LocationCode", 
				ExcelUtils.getCellData("Air_BookingMAWBMainDetails", rowNo, "AirportOfDeparture",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Btn_AirportOfDestination", ScenarioDetailsHashMap, 10), orAirBooking,"LocationCode", 
				ExcelUtils.getCellData("Air_BookingMAWBMainDetails", rowNo, "AirportOfDestination",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_CarrierCutOffDate", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_BookingMAWBMainDetails", rowNo, "CarrierCutoffDate",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_carrierCutOffTime", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_BookingMAWBMainDetails", rowNo, "CarrierCutoffTime",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETDDate", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_BookingMAWBMainDetails", rowNo, "ETD",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETDTime", ScenarioDetailsHashMap, 10),
				ExcelUtils.getCellData("Air_BookingMAWBMainDetails", rowNo, "ETDTime",ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETADate", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_BookingMAWBMainDetails", rowNo,"ETA", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETATime", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_BookingMAWBMainDetails", rowNo,"ETATime", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_ETAFinalDestDate", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_BookingMAWBMainDetails", rowNo,"Air_ETAFinalDestDate", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_ETAFinalDestTime", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_BookingMAWBMainDetails", rowNo,"Air_ETAFinalDestTime", ScenarioDetailsHashMap), 2);
		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Btn_FlightTo", ScenarioDetailsHashMap, 10), orAirBooking, "LocationCode", 
				ExcelUtils.getCellData("Air_BookingMAWBMainDetails", rowNo,"FlightTo", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "FlightNo", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_BookingMAWBMainDetails", rowNo,"FlightNo", ScenarioDetailsHashMap), 2);
	}

	public static void pictAirMAWBAllocation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		GenericMethods.clickElement(driver, null, Common.getElement(driver, "ALLOCATIONS_Tab", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);
		int TableRowSize  = driver.findElements(By.xpath("html/body/form/div/table/tbody/tr")).size();
		//System.out.println("TableSize :"+TableRowSize);
		ArrayList<String> HAWB_ID = new ArrayList<String>();
		HAWB_ID=ExcelUtils.getAllCellValuesOfColumn("Air_HAWBMainDetails","HBLId", ScenarioDetailsHashMap);
		//System.out.println("...HAWB_ID.size"+HAWB_ID.size());	
		for(int HAWBIDNo=0;HAWBIDNo<HAWB_ID.size();HAWBIDNo++)
		{
			for(int row=1;row<=TableRowSize;row++)
			{
				//String HAWB_ID = ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "HBLId", ScenarioDetailsHashMap);
				Actions builder=new Actions(driver);
				builder.moveToElement(driver.findElement(By.id("dtDisplayHouseHL"+row))).build().perform();
				String grid_HAWBID=driver.findElement(By.id("dtDisplayHouseHL"+row)).getText();
				//System.out.println("for i HAWB_ID.get("+HAWBIDNo+") :"+HAWB_ID.get(HAWBIDNo));
				//System.out.println("grid_HAWBID :"+grid_HAWBID);
				if(grid_HAWBID.equalsIgnoreCase(HAWB_ID.get(HAWBIDNo)))
				{
					//String status=driver.findElement(By.xpath("//tr[td[text()='"+ExcelUtils.getCellData("Air_HAWBMainDetails", j, "HBLId", ScenarioDetailsHashMap)+"']]/td[1]/input")).getAttribute("value");
					String status=driver.findElement(By.xpath("//tr[td[text()='"+HAWB_ID.get(HAWBIDNo)+"']]/td[1]/input")).getAttribute("value");
					//System.out.println("Status value of HAWB_ID.get("+HAWBIDNo+") :"+HAWB_ID.get(HAWBIDNo) + " -- " +status);
					String result="No";
					if(status.equalsIgnoreCase("Y")){
						result="Yes";
						//GenericMethods.assertTwoValues(result, ExcelUtils.getCellData("Air_MAWB", rowNo, "HAWB_ID_Assertion", ScenarioDetailsHashMap), "Validating check box", ScenarioDetailsHashMap);
						//System.out.println("Check box is  already checked");
					}
					else{
						GenericMethods.clickElement(driver, By.id("multiSelectCheckbox"+row), null, 10);
						//driver.findElement(By.id("multiSelectCheckbox"+i)).click();
						//GenericMethods.assertTwoValues(result, ExcelUtils.getCellData("Air_MAWB", rowNo, "HAWB_ID_Assertion", ScenarioDetailsHashMap), "Validating check box", ScenarioDetailsHashMap);
						//System.out.println("Check box is recently checked");
					}
					break;
				}
			}
		}
	}


	public static void pictAirMAWBParties(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "Parties_Tab", ScenarioDetailsHashMap, 10),2);
		GenericMethods.pauseExecution(2000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
	}
	public static void pictAirMAWBPickUpInstructions(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "Tab_PickUpInstructions", ScenarioDetailsHashMap, 10),2);
		GenericMethods.pauseExecution(2000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "Btn_Add", ScenarioDetailsHashMap, 10), 2);
	}



	public static void pictAirMAWBDocCharges_Tab(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.clickElement(driver, null,orAirBooking.getElement(driver, "FreightandOtherCharges_Tab", ScenarioDetailsHashMap, 10),2);
		GenericMethods.pauseExecution(2000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
	}
	public static void Air_Mawb_ProvisionalClose(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) {
		AppReusableMethods.selectMenu(driver, ETransMenu.MAWB,"MAWB", ScenarioDetailsHashMap);
		String MAWB_ID = ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", rowNo, "MAWBID", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirMawb.getElement(driver, "SearchMawbId", ScenarioDetailsHashMap, 5),MAWB_ID, 5);
		orAirMawb.getElement(driver, "MawbSearch_DateFrom", ScenarioDetailsHashMap, 5).clear();
		orAirMawb.getElement(driver, "MawbSearch_DateTo", ScenarioDetailsHashMap, 5).clear();
		GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(4000);
		String MAWBID_Grid=GenericMethods.getInnerText(driver, null, orAirMawb.getElement(driver, "MawbId_Grid", ScenarioDetailsHashMap, 5), 5);
		String XL_MAWB_ID = MAWB_ID;
		//System.out.println("XL_MAWB_ID :"+XL_MAWB_ID);
		StringBuilder xl= new StringBuilder(XL_MAWB_ID);
		String Updated_MAWB_ID=xl.insert(3, "-").insert(8, " ").toString();
		//System.out.println("Updated_MAWB_ID :"+Updated_MAWB_ID);
		//System.out.println("MAWBID_Grid :"+MAWBID_Grid);
		GenericMethods.pauseExecution(3000);
		if(MAWBID_Grid.equalsIgnoreCase(Updated_MAWB_ID))
		{
			GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "MawbId_CheckBox", ScenarioDetailsHashMap, 5) , 5);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				//System.out.println("no Alerts present");
			}
			GenericMethods.selectOptionFromDropDown(driver, null,orAirMawb.getElement(driver, "Mawb_ProvisionalClose", ScenarioDetailsHashMap, 5) ,"Provisional Close for Allocation");
			GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "MAWBAdd_NavigationList_QuickLink", ScenarioDetailsHashMap, 5) , 5);
			GenericMethods.pauseExecution(2000);
			String MAWBPCloseSummary=GenericMethods.getInnerText(driver, null, orAirMawb.getElement(driver, "MAWB_VerificationMsG", ScenarioDetailsHashMap, 20), 2);
			String[] MAWBId = MAWBPCloseSummary.split(" : ");
			//System.out.println(MAWBId[0]);
			//System.out.println(MAWBId[1]);
			GenericMethods.assertTwoValues(MAWBId[1], ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", rowNo, "MAWBProvisionalCloseSummary_MSG", ScenarioDetailsHashMap), "Validating MAWB Provisional Close Summary_MSG", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(MAWBId[2], ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", rowNo, "MAWB_ID", ScenarioDetailsHashMap), "Validating MAWB ID in MAWB Provisional Close Summary_MSG ",ScenarioDetailsHashMap);
		}
		else{
			GenericMethods.assertTwoValues(MAWB_ID,MAWBID_Grid, "Searching MAWB ID in MAWB Grid", ScenarioDetailsHashMap);
		}
	}
	public static void Air_Mawb_ConfirmonBoard(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) {
		AppReusableMethods.selectMenu(driver, ETransMenu.MAWB,"MAWB", ScenarioDetailsHashMap);
		String MAWB_ID = ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", rowNo, "MAWBID", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirMawb.getElement(driver, "SearchMawbId", ScenarioDetailsHashMap, 5),MAWB_ID, 5);
		orAirMawb.getElement(driver, "MawbSearch_DateFrom", ScenarioDetailsHashMap, 5).clear();
		orAirMawb.getElement(driver, "MawbSearch_DateTo", ScenarioDetailsHashMap, 5).clear();
		GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(4000);
		String MAWBID_Grid=GenericMethods.getInnerText(driver, null, orAirMawb.getElement(driver, "MawbId_Grid", ScenarioDetailsHashMap, 5), 5);
		String XL_MAWB_ID = MAWB_ID;
		//System.out.println("XL_MAWB_ID :"+XL_MAWB_ID);
		StringBuilder xl= new StringBuilder(XL_MAWB_ID);
		String Updated_MAWB_ID=xl.insert(3, "-").insert(8, " ").toString();
		//System.out.println("Updated_MAWB_ID :"+Updated_MAWB_ID);
		//System.out.println("MAWBID_Grid :"+MAWBID_Grid+ " is equal to "+"Updated_MAWB_ID :"+Updated_MAWB_ID);
		GenericMethods.pauseExecution(3000);
		if(MAWBID_Grid.equalsIgnoreCase(Updated_MAWB_ID))
		{
			GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "MawbId_CheckBox", ScenarioDetailsHashMap, 5) , 5);
			try{
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {
				//System.out.println("no Alerts present");
			}
			GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "Mawb_COBTab", ScenarioDetailsHashMap, 5) , 10);
			GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "FlightCob_CheckBox", ScenarioDetailsHashMap, 5) , 10);
			GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "MawbId_CheckBox", ScenarioDetailsHashMap, 5) , 10);
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 5) , 10);
			GenericMethods.pauseExecution(4000);
			String ConfirmOnBoardSummary=GenericMethods.getInnerText(driver, null, orAirMawb.getElement(driver, "MAWB_VerificationMsG", ScenarioDetailsHashMap, 20), 2);
			String[] COB = ConfirmOnBoardSummary.split(" :");
			//System.out.println(COB[0]);
			//System.out.println(COB[1]);
			GenericMethods.assertTwoValues(COB[0], ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", rowNo, "ConfirmOnBoard_MSG", ScenarioDetailsHashMap), "Validating Confirm On Board success message", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(COB[1], ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", rowNo, "MAWB_ID", ScenarioDetailsHashMap), "Validating MAWB ID in Confirm On Board success message ",ScenarioDetailsHashMap);
		}
		else{
			GenericMethods.assertTwoValues(MAWB_ID,MAWBID_Grid, "Searching MAWB ID in MAWB Grid", ScenarioDetailsHashMap);
		}
	}

	public static void Air_Mawb_CloseMAWB(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) {
		AppReusableMethods.selectMenu(driver, ETransMenu.MAWB,"MAWB", ScenarioDetailsHashMap);
		String MAWB_ID = ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", rowNo, "MAWBID", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirMawb.getElement(driver, "SearchMawbId", ScenarioDetailsHashMap, 5),MAWB_ID, 5);
		orAirMawb.getElement(driver, "MawbSearch_DateFrom", ScenarioDetailsHashMap, 5).clear();
		orAirMawb.getElement(driver, "MawbSearch_DateTo", ScenarioDetailsHashMap, 5).clear();
		GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(4000);
		String MAWBID_Grid=GenericMethods.getInnerText(driver, null, orAirMawb.getElement(driver, "MawbId_Grid", ScenarioDetailsHashMap, 5), 5);
		String XL_MAWB_ID = MAWB_ID;
		//System.out.println("XL_MAWB_ID :"+XL_MAWB_ID);
		StringBuilder xl= new StringBuilder(XL_MAWB_ID);
		String Updated_MAWB_ID=xl.insert(3, "-").insert(8, " ").toString();
		//System.out.println("Updated_MAWB_ID :"+Updated_MAWB_ID);
		//System.out.println("MAWBID_Grid :"+MAWBID_Grid);
		GenericMethods.pauseExecution(3000);
		if(MAWBID_Grid.equalsIgnoreCase(Updated_MAWB_ID))
		{
			GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "MawbId_CheckBox", ScenarioDetailsHashMap, 5) , 5);
			try{
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {
				//System.out.println("no Alerts present");
			}
			GenericMethods.selectOptionFromDropDown(driver, null,orAirMawb.getElement(driver, "Mawb_ProvisionalClose", ScenarioDetailsHashMap, 5) ,"Close Master");
			GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "Mawb_ProvisionalCloseImage", ScenarioDetailsHashMap, 5) , 5);
			GenericMethods.pauseExecution(4000);
			String MAWBClosedSummary=GenericMethods.getInnerText(driver, null, orAirMawb.getElement(driver, "MAWB_VerificationMsG", ScenarioDetailsHashMap, 20), 2);
			String[] MAWBClosed = MAWBClosedSummary.split(" : ");
			//System.out.println(MAWBClosed[0]);
			//System.out.println(MAWBClosed[1]);
			GenericMethods.assertTwoValues(MAWBClosed[1], ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", rowNo, "MAWBClosed_MSG", ScenarioDetailsHashMap), "Validating MAWB Closed success message", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(MAWBClosed[2], ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", rowNo, "MAWB_ID", ScenarioDetailsHashMap), "Validating MAWB ID in MAWB Closed success message",ScenarioDetailsHashMap);
		}
		else{
			GenericMethods.assertTwoValues(MAWB_ID,MAWBID_Grid, "Searching MAWB ID in MAWB Grid", ScenarioDetailsHashMap);
		}
	}

	public static void Mawb_CloseFor_B2B_Direct(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{

		AppReusableMethods.selectMenu(driver,ETransMenu.HAWB,"HAWB", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirMawb.getElement(driver, "HAWBIDSearch", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "HBLId", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.assertInnerText(driver, null, orAirMawb.getElement(driver, "GridHAWBId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "HBLId", ScenarioDetailsHashMap), "GridHAWBId", "Comparing hawb id",  ScenarioDetailsHashMap);
		String XL_MAWB_ID = ExcelUtils.getCellData("Air_HAWB_MAWBMainDetails", rowNo, "MAWB_ID", ScenarioDetailsHashMap);
		String MAWBID_Grid=GenericMethods.getInnerText(driver, null, orAirMawb.getElement(driver, "Grid_MAWBId", ScenarioDetailsHashMap, 5), 5);
		//System.out.println("XL_MAWB_ID :"+XL_MAWB_ID);
		//System.out.println("MAWBID_Grid :"+MAWBID_Grid);
		GenericMethods.pauseExecution(4000);
		if(MAWBID_Grid.equalsIgnoreCase(XL_MAWB_ID))
		{
			GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "Mawb_COBTab", ScenarioDetailsHashMap, 5) , 10);
			GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "FlightCob_CheckBox", ScenarioDetailsHashMap, 5) , 10);
			GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "MawbId_CheckBox", ScenarioDetailsHashMap, 5) , 10);
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 5) , 10);
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
		GenericMethods.replaceTextofTextField(driver, null, orAirMawb.getElement(driver, "HAWBIDSearch", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "HBLId", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
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

	public static void createMAWB_New(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ) throws AWTException{

		AppReusableMethods.selectMenu(driver, ETransMenu.MAWB, "MAWB", ScenarioDetailsHashMap);
		clickElement(driver, null, Common.getElement(driver, "AddButton", ScenarioDetailsHashMap, 10), 2);

		airMAWBMainDetails(driver, ScenarioDetailsHashMap, rowNo);

	}

	public static void airMAWBMainDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "CarrierId", ScenarioDetailsHashMap, 10),
				ExcelUtils.getCellData("Air_MAWB", RowNo, "Carrier_Id",ScenarioDetailsHashMap), 2);

		String randomMAWB_ID=ExcelUtils.getCellData("Air_MAWB", RowNo, "MAWBID", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(8)+"";
		//System.out.println("randomMAWB_ID:::::"+randomMAWB_ID);
		String XL_MAWB_ID = randomMAWB_ID;
		/*//System.out.println("XL_MAWB_ID :"+XL_MAWB_ID);*/	
		StringBuilder xl= new StringBuilder(XL_MAWB_ID);
		String Updated_MAWB_ID=xl.insert(8, " ").toString();
		replaceTextofTextField(driver, null, orAirHAWB.getElement(driver, "MAWBID", ScenarioDetailsHashMap, 20),  Updated_MAWB_ID, 20);
		ExcelUtils.setCellData("Air_MAWB", RowNo, "MAWBID", Updated_MAWB_ID, ScenarioDetailsHashMap);
		ExcelUtils.setCellData("Air_Arrival", RowNo, "MAWB_ID", Updated_MAWB_ID, ScenarioDetailsHashMap);
		
		if (ExcelUtils.getCellData("Air_MAWB", RowNo, "eAWBRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes")) {
			clickElement(driver, null, orAirMawb.getElement(driver, "CheckBox_EAWB", ScenarioDetailsHashMap, 10), 2);
		}
		AppReusableMethods.selectValueFromLov(driver, orAirMawb.getElement(driver, "OriginDeptIdLov", ScenarioDetailsHashMap, 10), orAirMawb, "OriginDeptId_SearchCode", 
				ExcelUtils.getCellData("Air_MAWB", RowNo, "OriginDept", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		AppReusableMethods.selectValueFromLov(driver, orAirMawb.getElement(driver, "DestBranchIdLov", ScenarioDetailsHashMap, 10), orAirMawb, "DestBranchId_SearchCode",
				ExcelUtils.getCellData("Air_MAWB", RowNo, "DestBranch", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		AppReusableMethods.selectValueFromLov(driver, orAirMawb.getElement(driver, "DestDeptIdLov", ScenarioDetailsHashMap, 10), orAirMawb, "OriginDeptId_SearchCode",
				ExcelUtils.getCellData("Air_MAWB", RowNo, "DestDept", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		AppReusableMethods.selectValueFromLov(driver, orAirMawb.getElement(driver, "NotifyToLov", ScenarioDetailsHashMap, 10), orAirMawb, "DestBranchId_SearchCode", 
				ExcelUtils.getCellData("Air_MAWB", RowNo, "NotifyTo", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		selectOptionFromDropDown(driver, null, orAirBooking.getElement(driver, "FreightTerms", ScenarioDetailsHashMap, 10),
				ExcelUtils.getCellData("Air_MAWB", RowNo, "FreightTerms", ScenarioDetailsHashMap));
		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "ServiceLevelIdImage_Lov", ScenarioDetailsHashMap, 10), orAirBooking,	"OrgCode", 
				ExcelUtils.getCellData("Air_MAWB", RowNo, "ServiceLevel",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Btn_AirportOfDeparture", ScenarioDetailsHashMap, 10), orAirBooking,"LocationCode", 
				ExcelUtils.getCellData("Air_MAWB", RowNo, "PortOfLoad",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		AppReusableMethods.selectValueFromLov(driver, orAirBooking.getElement(driver, "Btn_AirportOfDestination", ScenarioDetailsHashMap, 10), orAirBooking,"LocationCode", 
				ExcelUtils.getCellData("Air_MAWB", RowNo, "PortOfDischarge",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_CarrierCutOffDate", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_MAWB", RowNo, "CarrierCutoff_Date",ScenarioDetailsHashMap), 2);
		replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_carrierCutOffTime", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_MAWB", RowNo, "CarrierCutoff_Time",ScenarioDetailsHashMap), 2);
		replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_serviceCutOffDate", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_MAWB", RowNo, "ServiceCutoff_Date",ScenarioDetailsHashMap), 2);
		replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_serviceCutOffTime", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_MAWB", RowNo, "ServiceCutoff_Time",ScenarioDetailsHashMap), 2);
		replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETDDate", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_MAWB", RowNo, "ETD",ScenarioDetailsHashMap), 2);
		replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETDTime", ScenarioDetailsHashMap, 10),
				ExcelUtils.getCellData("Air_MAWB", RowNo, "ETD_Time",ScenarioDetailsHashMap), 2);
		replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETADate", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_MAWB", RowNo,"ETA", ScenarioDetailsHashMap), 2);
		replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "ETATime", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_MAWB", RowNo,"ETA_Time", ScenarioDetailsHashMap), 2);
		replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_ETAFinalDestDate", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_MAWB", RowNo,"ETAFinal_DestDate", ScenarioDetailsHashMap), 2);
		replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "Air_ETAFinalDestTime", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Air_MAWB", RowNo,"ETAFinal_DestTime", ScenarioDetailsHashMap), 2);
		if (ExcelUtils.getCellData("Air_MAWB_FilghtDetails", RowNo, "FlightScheduleRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes")) {
			AppReusableMethods.selectValueFromLov(driver, orAirMawb.getElement(driver, "Lov_FlightSchedualButton", ScenarioDetailsHashMap, 10), orAirMawb, "SearchTextFlightscheId", ExcelUtils.getCellData("Air_MAWB_FilghtDetails", RowNo, "FlightScheduleId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);

		}else{
			for (int i = 1; i <= ExcelUtils.getCellDataRowCount("Air_MAWB_FilghtDetails", ScenarioDetailsHashMap); i++) {
				if(i==2){
					clickElement(driver, null, orAirMawb.getElement(driver, "Image_FlightDetailsAdd", ScenarioDetailsHashMap, 10), 2);
				}
				AppReusableMethods.selectValueFromLov(driver, locateElement(driver, By.id("flight"+(i-1)+"ToImage"), 10), orAirMawb, "Location_SearchCode", ExcelUtils.getCellData("Air_MAWB_FilghtDetails", i, "FlightTo", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				AppReusableMethods.selectValueFromLov(driver, locateElement(driver, By.id("flight"+(i-1)+"CarrierIdImage"), 10), orAirMawb, "CarrierId", ExcelUtils.getCellData("Air_MAWB_FilghtDetails", i, "Carrier", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				replaceTextofTextField(driver, By.id("flight"+(i-1)+"No"), null, ExcelUtils.getCellData("Air_MAWB_FilghtDetails", i, "FlightNo", ScenarioDetailsHashMap), 10);
				replaceTextofTextField(driver, By.id("flight"+(i-1)+"ServiceCutOffDate"), null, ExcelUtils.getCellData("Air_MAWB_FilghtDetails", i, "ServiceCutoffDate", ScenarioDetailsHashMap), 10);
				replaceTextofTextField(driver, By.id("flight"+(i-1)+"ServiceCutOffTime"), null, ExcelUtils.getCellData("Air_MAWB_FilghtDetails", i, "ServiceCutoffTime", ScenarioDetailsHashMap), 10);
				replaceTextofTextField(driver, By.id("flight"+(i-1)+"DepartureDate"), null, ExcelUtils.getCellData("Air_MAWB_FilghtDetails", i, "DepartureDate", ScenarioDetailsHashMap), 10);
				replaceTextofTextField(driver, By.id("flight"+(i-1)+"DepartureTime"), null, ExcelUtils.getCellData("Air_MAWB_FilghtDetails", i, "DepartureTime", ScenarioDetailsHashMap), 10);
				replaceTextofTextField(driver, By.id("flight"+(i-1)+"ArrivalDate"), null, ExcelUtils.getCellData("Air_MAWB_FilghtDetails", i, "ArrivalDate", ScenarioDetailsHashMap), 10);
				replaceTextofTextField(driver, By.id("flight"+(i-1)+"ArrivalTime"), null, ExcelUtils.getCellData("Air_MAWB_FilghtDetails", i, "ArrivalTime", ScenarioDetailsHashMap), 10);
				
			}
		}
	}
	

	public static void PartyDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		clickElement(driver, null, Common.getElement(driver, "Parties_Tab", ScenarioDetailsHashMap, 10), 2);
	}

	public static void HAWBAllocation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		clickElement(driver, null, Common.getElement(driver, "ALLOCATIONS_Tab", ScenarioDetailsHashMap, 10), 2);
		if(ExcelUtils.getCellData("Air_MAWB", RowNo, "HBLRequiredToUpdate", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			System.out.println("Enetered in Allocation tab");
			ExcelUtils.setCellData_Without_DataSet("Air_HAWBMainDetails", RowNo, "HAWB_Id", ExcelUtils.getCellData("SE_OBL", RowNo, "HBL_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			ExcelUtils.setCellData_Without_DataSet("Air_MAWB", RowNo, "HAWBID", ExcelUtils.getCellData("SE_OBL", RowNo, "HBL_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			ExcelUtils.setCellData_Without_DataSet("Air_Arrival", RowNo, "HAWB_ID", ExcelUtils.getCellData("SE_OBL", RowNo, "HBL_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			ExcelUtils.setCellData_Without_DataSet("Air_Arrival", RowNo, "MAWB_ID", ExcelUtils.getCellData("Air_MAWB", RowNo, "MAWBID", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			ExcelUtils.setCellData_Without_DataSet("Air_POD_DI", RowNo, "House_Id", ExcelUtils.getCellData("SE_OBL", RowNo, "HBL_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);

		}

		GenericMethods.pauseExecution(3000);
		if(ExcelUtils.getCellData("Air_MAWB", RowNo, "MultipleHBLRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			GenericMethods.pauseExecution(3000);
			System.out.println("HBL count = "+driver.findElements(By.xpath("html/body/form/div/table/tbody/tr")).size());
			int count =0;
			for (int countNo = 1; countNo <= ExcelUtils.getCellDataRowCount("Air_MAWBAllocations", ScenarioDetailsHashMap); countNo++) 
			{
			ExcelUtils.setCellData_Without_DataSet("Air_MAWBAllocations", countNo, "HAWBID", ExcelUtils.getCellDataWithoutDataSet("Air_HAWBMainDetails", countNo, "HAWB_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			}
			GenericMethods.pauseExecution(4000);
			for (int HBLCount = 1; HBLCount <= driver.findElements(By.xpath("html/body/form/div/table/tbody/tr")).size(); HBLCount++) 
			{
				ArrayList<String> XLvaluess = new ArrayList<String>();
				XLvaluess=ExcelUtils.getAllCellValuesOfColumn("Air_MAWBAllocations","HAWBID", ScenarioDetailsHashMap);
				System.out.println("XLvaluess*********"+XLvaluess);
				for (int XLCount = 0; XLCount <XLvaluess.size(); XLCount++) 
				{
					System.out.println("Expected value "+XLvaluess.get(XLCount));
					System.out.println("actual value"+getInnerText(driver, By.id("dtDisplayHouseHL"+HBLCount), null, 10));
					
					if(getInnerText(driver, By.id("dtDisplayHouseHL"+HBLCount), null, 10).equals(XLvaluess.get(XLCount)))
					{
						System.out.println("%%%%%%%%HBLCount%%%%%%%%%XLCount%%%%%%%%count%%%%%%%%%"+HBLCount+"--"+XLCount+"--"+count);
						GenericMethods.pauseExecution(2000);
						clickElement(driver, By.id("multiSelectCheckbox"+HBLCount), null, 10);
						count=count+1;
						try{
							GenericMethods.handleAlert(driver, "accept");
						}catch (Exception e) {
							//System.out.println("no Alerts present");
						}
						GenericMethods.pauseExecution(2000);
					}
				}
				if(count==XLvaluess.size())
				{
					break;
				}
			}
		}
		else
		{
			for (int j = 1; j <= ExcelUtils.getCellDataRowCount("Air_MAWB", ScenarioDetailsHashMap); j++) {
				int rowCount=orAirMawb.getElements(driver, "GridRows_MAWBAllocation", ScenarioDetailsHashMap, 10).size();
				for (int i = 1; i <= rowCount; i++) {
					if(getInnerText(driver, By.id("dtDisplayHouseHL"+i), null, 10).equals(ExcelUtils.getCellData("Air_MAWB", j, "HAWBID", ScenarioDetailsHashMap))){
						clickElement(driver, By.id("multiSelectCheckbox"+i), null, 10);
						break;
					}
				}	
			}
		}

	}


	public static void pickUpInstruction(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		clickElement(driver, null, Common.getElement(driver, "PickUpInstructions_Tab", ScenarioDetailsHashMap, 10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		
			for (int i = 1; i <= ExcelUtils.getCellDataRowCount("Air_MAWB_PrsInstruction", ScenarioDetailsHashMap); i++) {
				selectOptionFromDropDown(driver, null, orAirBooking.getElement(driver, "AirPickupRequired", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_MAWB_PrsInstruction", i, "PickUpRunRequired", ScenarioDetailsHashMap));
				
				selectOptionFromDropDown(driver, null, orAirBooking.getElement(driver, "MovementType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_MAWB_PrsInstruction", i, "TypeOfMovement", ScenarioDetailsHashMap));
				int noOfHouse=orAirMawb.getElements(driver, "Prs_HouseCount", ScenarioDetailsHashMap, 10).size();
				for (int j = 0; j < noOfHouse; j++) {
					clickElement(driver, By.id("houseCheckBox"+j), null, 10);			
				}
				selectOptionFromDropDown(driver, null, orAirBooking.getElement(driver, "PickUp_Status", ScenarioDetailsHashMap, 10), 
						ExcelUtils.getCellData("Air_MAWB_PrsInstruction", i, "Status", ScenarioDetailsHashMap));
				if (ExcelUtils.getCellData("Air_MAWB_PrsInstruction", RowNo, "Status", ScenarioDetailsHashMap).equalsIgnoreCase("Partial")) {
				replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "StuffedPieces", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_MAWB_PrsInstruction", i, "Pices", ScenarioDetailsHashMap), 2);
				replaceTextofTextField(driver, null, orAirBooking.getElement(driver, "StuffedPiecesType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_MAWB_PrsInstruction", i, "PicesType", ScenarioDetailsHashMap), 2);
				}
				clickElement(driver, null, orAirMawb.getElement(driver, "MainContainer_AddBtn", ScenarioDetailsHashMap, 10), 2);
			}
		
	}
	
	public static void docCharges(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		clickElement(driver, null, Common.getElement(driver, "FreightandOtherCharges_Tab", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(5000);
		boolean alertPresent=GenericMethods.isAlertFound(driver);
		//System.out.println("insde loopll alert"+alertPresent);
		if(alertPresent){
			GenericMethods.handleAlert(driver, "accept");
		}
		
	}
	
	public static void chargesAndCosts(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		clickElement(driver, null, Common.getElement(driver, "ChargesAndCosts_Tab", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(5000);
		boolean alertPresent=GenericMethods.isAlertFound(driver);
		//System.out.println("insde loopll alert"+alertPresent);
		if(alertPresent){
			GenericMethods.handleAlert(driver, "accept");
		}
		GenericMethods.pauseExecution(3000);
		String msg=getInnerText(driver, null, orAirMawb.getElement(driver, "Msg_MAWBSave", ScenarioDetailsHashMap, 10), 2);
		String jobFileNo=msg.substring(msg.lastIndexOf(":"), msg.length()).trim();
		ExcelUtils.setCellData("Air_MAWB", RowNo, "MAWBJobFileNo", jobFileNo, ScenarioDetailsHashMap);
	}

	public static void MAWB_notes(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		clickElement(driver, null, Common.getElement(driver, "NotesAndStatus_Tab", ScenarioDetailsHashMap, 10), 2);
		for (int i = 1; i <= ExcelUtils.getCellDataRowCount("Air_MAWB_Notes", ScenarioDetailsHashMap); i++) {
			replaceTextofTextField(driver, null, orAirMawb.getElement(driver, "Text_Notes", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_MAWB_Notes", i, "Notes", ScenarioDetailsHashMap), 2);
			selectOptionFromDropDown(driver, null, orAirMawb.getElement(driver, "DropDown_NotesCategory", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_MAWB_Notes", i, "NotesCategory", ScenarioDetailsHashMap));
			selectOptionFromDropDown(driver, null, orAirMawb.getElement(driver, "DropDown_NotesType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_MAWB_Notes", i, "NotesType", ScenarioDetailsHashMap));
		}
		
	}
	
	public static void MAWB_EDoc(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "EDoc_Tab", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(5000);
		try{
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
	}
	
	public static void MAWB_Save(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(10000);
		String MAWBIdSummary=GenericMethods.getInnerText(driver, null, orAirMawb.getElement(driver, "MAWB_VerificationMsG", ScenarioDetailsHashMap, 20), 2);
		//String MAWBId=MAWBIdSummary.substring(MAWBIdSummary.indexOf(":")+1,MAWBIdSummary.indexOf(",")).trim();
		String jobFileNo=MAWBIdSummary.substring(MAWBIdSummary.lastIndexOf(":")+1, MAWBIdSummary.length()).trim();
		ExcelUtils.setCellData("Air_MAWB", RowNo, "MAWBJobFileNo", jobFileNo, ScenarioDetailsHashMap);
		
	}
	
	public static void Air_MawbProvisionalClose(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) {
		AppReusableMethods.selectMenu(driver, ETransMenu.MAWB,"MAWB", ScenarioDetailsHashMap);
		String MAWB_ID = ExcelUtils.getCellData("Air_MAWB", rowNo, "MAWBID", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirMawb.getElement(driver, "SearchMawbId", ScenarioDetailsHashMap, 5),MAWB_ID, 5);
		orAirMawb.getElement(driver, "MawbSearch_DateFrom", ScenarioDetailsHashMap, 5).clear();
		orAirMawb.getElement(driver, "MawbSearch_DateTo", ScenarioDetailsHashMap, 5).clear();
		GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(4000);
		String MAWBID_Grid=GenericMethods.getInnerText(driver, null, orAirMawb.getElement(driver, "MawbId_Grid", ScenarioDetailsHashMap, 5), 5);
		
		//System.out.println("Updated_MAWB_ID :"+Updated_MAWB_ID);
		//System.out.println("MAWBID_Grid :"+MAWBID_Grid);
	GenericMethods.pauseExecution(3000);
		if(MAWBID_Grid.equalsIgnoreCase(MAWB_ID))
		{
			GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "MawbId_CheckBox", ScenarioDetailsHashMap, 5) , 5);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				//System.out.println("no Alerts present");
			}
			GenericMethods.selectOptionFromDropDown(driver, null,orAirMawb.getElement(driver, "Mawb_ProvisionalClose", ScenarioDetailsHashMap, 5) ,ExcelUtils.getCellData("Air_MAWB", rowNo, "ProvisionalCloseOption", ScenarioDetailsHashMap));
			GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "MAWBAdd_NavigationList_QuickLink", ScenarioDetailsHashMap, 5) , 5);
			GenericMethods.pauseExecution(2000);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				//System.out.println("no Alerts present");
			}
			GenericMethods.pauseExecution(2000);
			String MAWBPCloseSummary=GenericMethods.getInnerText(driver, null, orAirMawb.getElement(driver, "MAWB_VerificationMsG", ScenarioDetailsHashMap, 20), 2);
			String[] MAWBId = MAWBPCloseSummary.split(" : ");
			//System.out.println(MAWBId[0]);
			//System.out.println(MAWBId[1]);
			GenericMethods.assertTwoValues(MAWBId[1], ExcelUtils.getCellData("Air_MAWB", rowNo, "MAWBProvisionalCloseSummary_MSG", ScenarioDetailsHashMap), "Validating MAWB Provisional Close Summary_MSG", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(MAWBId[2], ExcelUtils.getCellData("Air_MAWB", rowNo, "MAWBID", ScenarioDetailsHashMap), "Validating MAWB ID in MAWB Provisional Close Summary_MSG ",ScenarioDetailsHashMap);
		}
		else{
			GenericMethods.assertTwoValues(MAWB_ID,MAWBID_Grid, "Searching MAWB ID in MAWB Grid", ScenarioDetailsHashMap);
		}
	}

	public static void Air_MAWB_Close(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) {
		AppReusableMethods.selectMenu(driver, ETransMenu.MAWB,"MAWB", ScenarioDetailsHashMap);
		String MAWB_ID = ExcelUtils.getCellData("Air_MAWB", rowNo, "MAWBID", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirMawb.getElement(driver, "SearchMawbId", ScenarioDetailsHashMap, 5),MAWB_ID, 5);
		orAirMawb.getElement(driver, "MawbSearch_DateFrom", ScenarioDetailsHashMap, 5).clear();
		orAirMawb.getElement(driver, "MawbSearch_DateTo", ScenarioDetailsHashMap, 5).clear();
		GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(4000);
		String MAWBID_Grid=GenericMethods.getInnerText(driver, null, orAirMawb.getElement(driver, "MawbId_Grid", ScenarioDetailsHashMap, 5), 5);
		/*String XL_MAWB_ID = MAWB_ID;
		//System.out.println("XL_MAWB_ID :"+XL_MAWB_ID);
		StringBuilder xl= new StringBuilder(XL_MAWB_ID);
		String Updated_MAWB_ID=xl.insert(3, "-").insert(8, " ").toString();
		//System.out.println("Updated_MAWB_ID :"+Updated_MAWB_ID);
		//System.out.println("MAWBID_Grid :"+MAWBID_Grid);
		 */		GenericMethods.pauseExecution(3000);
		 if(MAWBID_Grid.equalsIgnoreCase(MAWB_ID))
		 {
			 GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "MawbId_CheckBox", ScenarioDetailsHashMap, 5) , 5);
			 try{
				 GenericMethods.handleAlert(driver, "accept");
			 }catch (Exception e) {
				 //System.out.println("no Alerts present");
			 }
			 GenericMethods.selectOptionFromDropDown(driver, null,orAirMawb.getElement(driver, "Mawb_ProvisionalClose", ScenarioDetailsHashMap, 5) ,"Close Master");
			 GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "Mawb_ProvisionalCloseImage", ScenarioDetailsHashMap, 5) , 5);
			 try{
				 GenericMethods.handleAlert(driver, "accept");
			 }catch (Exception e) {
				 //System.out.println("no Alerts present");
			 }
			 GenericMethods.pauseExecution(4000);
			 String MAWBClosedSummary=GenericMethods.getInnerText(driver, null, orAirMawb.getElement(driver, "MAWB_VerificationMsG", ScenarioDetailsHashMap, 20), 2);
			 String[] MAWBClosed = MAWBClosedSummary.split(" : ");
			 //System.out.println(MAWBClosed[0]);
			 //System.out.println(MAWBClosed[1]);
			 GenericMethods.assertTwoValues(MAWBClosed[1], ExcelUtils.getCellData("Air_MAWB", rowNo, "MAWBClosed_MSG", ScenarioDetailsHashMap), "Validating MAWB Closed success message", ScenarioDetailsHashMap);
			 GenericMethods.assertTwoValues(MAWBClosed[2], ExcelUtils.getCellData("Air_MAWB", rowNo, "MAWBID", ScenarioDetailsHashMap), "Validating MAWB ID in MAWB Closed success message",ScenarioDetailsHashMap);
		 }
		 else{
			 GenericMethods.assertTwoValues(MAWB_ID,MAWBID_Grid, "Searching MAWB ID in MAWB Grid", ScenarioDetailsHashMap);
		 }
	}
	
	
	public static void Air_MAWBCOB(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) {
		AppReusableMethods.selectMenu(driver, ETransMenu.MAWB,"MAWB", ScenarioDetailsHashMap);
		String MAWB_ID = ExcelUtils.getCellData("Air_MAWB", rowNo, "MAWBID", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirMawb.getElement(driver, "SearchMawbId", ScenarioDetailsHashMap, 5),MAWB_ID, 5);
		orAirMawb.getElement(driver, "MawbSearch_DateFrom", ScenarioDetailsHashMap, 5).clear();
		orAirMawb.getElement(driver, "MawbSearch_DateTo", ScenarioDetailsHashMap, 5).clear();
		GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(4000);
		String MAWBID_Grid=GenericMethods.getInnerText(driver, null, orAirMawb.getElement(driver, "MawbId_Grid", ScenarioDetailsHashMap, 5), 5);
		/*String XL_MAWB_ID = MAWB_ID;
		//System.out.println("XL_MAWB_ID :"+XL_MAWB_ID);
		StringBuilder xl= new StringBuilder(XL_MAWB_ID);
		String Updated_MAWB_ID=xl.insert(3, "-").insert(8, " ").toString();*/
		//System.out.println("Updated_MAWB_ID :"+Updated_MAWB_ID);
		//System.out.println("MAWBID_Grid :"+MAWBID_Grid+ " is equal to "+"Updated_MAWB_ID :"+Updated_MAWB_ID);
		GenericMethods.pauseExecution(3000);
		if(MAWBID_Grid.equalsIgnoreCase(MAWB_ID))
		{
			GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "MawbId_CheckBox", ScenarioDetailsHashMap, 5) , 5);
			try{
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {
				//System.out.println("no Alerts present");
			}
			GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "Mawb_COBTab", ScenarioDetailsHashMap, 5) , 10);
			if(ExcelUtils.getCellData("Air_MAWB_COB", rowNo, "RevisedScheduleRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes")){
				
				for (int i = 1; i <= ExcelUtils.getCellDataRowCount("Air_MAWB_COB", ScenarioDetailsHashMap); i++) {
					
					AppReusableMethods.selectValueFromLov(driver, locateElement(driver, By.id("flight"+i+"ToImage"), 10), orAirMawb, "Location_SearchCode", ExcelUtils.getCellData("Air_MAWB_COB", i, "FlightTo", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					AppReusableMethods.selectValueFromLov(driver, locateElement(driver, By.id("flight"+i+"CarrierIdImage"), 10), orAirMawb, "CarrierId", ExcelUtils.getCellData("Air_MAWB_COB", i, "Carrier", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					replaceTextofTextField(driver, By.id("flight"+i+"No"), null, ExcelUtils.getCellData("Air_MAWB_COB", i, "FlightNo", ScenarioDetailsHashMap), 10);
					replaceTextofTextField(driver, By.id("flight"+i+"ServiceCutOffDate"), null, ExcelUtils.getCellData("Air_MAWB_COB", i, "ServiceCutoffDate", ScenarioDetailsHashMap), 10);
					replaceTextofTextField(driver, By.id("flight"+i+"ServiceCutOffTime"), null, ExcelUtils.getCellData("Air_MAWB_COB", i, "ServiceCutoffTime", ScenarioDetailsHashMap), 10);
					replaceTextofTextField(driver, By.id("flight"+i+"DepartureDate"), null, ExcelUtils.getCellData("Air_MAWB_COB", i, "DepartureDate", ScenarioDetailsHashMap), 10);
					replaceTextofTextField(driver, By.id("flight"+i+"DepartureTime"), null, ExcelUtils.getCellData("Air_MAWB_COB", i, "DepartureTime", ScenarioDetailsHashMap), 10);
					replaceTextofTextField(driver, By.id("flight"+i+"ArrivalDate"), null, ExcelUtils.getCellData("Air_MAWB_COB", i, "ArrivalDate", ScenarioDetailsHashMap), 10);
					replaceTextofTextField(driver, By.id("flight"+i+"ArrivalTime"), null, ExcelUtils.getCellData("Air_MAWB_COB", i, "ArrivalTime", ScenarioDetailsHashMap), 10);
					
				}
				
			}
			for (int i = 1; i <= ExcelUtils.getCellDataRowCount("Air_MAWB_FilghtDetails", ScenarioDetailsHashMap); i++) {
				GenericMethods.clickElement(driver, By.id("flight"+i+"CobSelected"), null , 10);
			}
			
			GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "MawbId_CheckBox", ScenarioDetailsHashMap, 5) , 10);
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 5) , 10);
			GenericMethods.pauseExecution(4000);
			String ConfirmOnBoardSummary=GenericMethods.getInnerText(driver, null, orAirMawb.getElement(driver, "MAWB_VerificationMsG", ScenarioDetailsHashMap, 20), 2);
			String[] COB = ConfirmOnBoardSummary.split(" :");
			//System.out.println(COB[0]);
			//System.out.println(COB[1]);
			GenericMethods.assertTwoValues(COB[0], ExcelUtils.getCellData("Air_MAWB", rowNo, "ConfirmOnBoard_MSG", ScenarioDetailsHashMap), "Validating Confirm On Board success message", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(COB[1], ExcelUtils.getCellData("Air_MAWB", rowNo, "MAWBID", ScenarioDetailsHashMap), "Validating MAWB ID in Confirm On Board success message ",ScenarioDetailsHashMap);
		}
		else{
			GenericMethods.assertTwoValues(MAWB_ID,MAWBID_Grid, "Searching MAWB ID in MAWB Grid", ScenarioDetailsHashMap);
		}
	}

	public static void Air_MAWBSearch(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.MAWB,"MAWB", ScenarioDetailsHashMap);
		String MAWB_ID = ExcelUtils.getCellData("Air_MAWB", rowNo, "MAWBID", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orAirMawb.getElement(driver, "SearchMawbId", ScenarioDetailsHashMap, 5),MAWB_ID, 5);
		orAirMawb.getElement(driver, "MawbSearch_DateFrom", ScenarioDetailsHashMap, 5).clear();
		orAirMawb.getElement(driver, "MawbSearch_DateTo", ScenarioDetailsHashMap, 5).clear();
		GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(4000);
	}
	public static void Air_MaWBDocCharges(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) throws AWTException {
		GenericMethods.clickElement(driver, null, orAirHAWB.getElement(driver, "Tab_DocCharges", ScenarioDetailsHashMap, 5) , 10);
		GenericMethods.pauseExecution(9000);
		try{
			GenericMethods.handleAlert(driver, "accept");
			System.out.println("*********");
		}catch (Exception e) {
			System.out.println("alert not found");
			e.printStackTrace();
		}
		GenericMethods.pauseExecution(4000);

		for(int GridRow=1;GridRow<=ExcelUtils.getCellDataRowCount("Air_MAWBDocCharges", ScenarioDetailsHashMap);GridRow++)
		{
			GenericMethods.clickElement(driver, By.xpath("html/body/form/table/tbody/tr[8]/td/div/table/tbody/tr["+GridRow+"]"), null, 10);
			//Buy Validation
			System.out.println("Buy Validation"+GridRow);
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
			driver.findElement(By.id("buyRateStr")).sendKeys(ExcelUtils.getCellData("Air_MAWBDocCharges", rowNo, "BuyRate", ScenarioDetailsHashMap));
			//driver.findElement(By.id("buyRateStr")).sendKeys("150");
			GenericMethods.action_Key(driver, Keys.TAB);
			try{
				GenericMethods.handleAlert(driver, "accept");
				System.out.println("alert found");
			}catch (Exception e) {
				System.out.println("alert not found");
				e.printStackTrace();
			}
			try{
				GenericMethods.handleAlert(driver, "accept");
				System.out.println("alert found ***********");
			}catch (Exception e) {
				System.out.println("alert not found***********");
				e.printStackTrace();
			}

			/*try {
					GenericMethods.parseChars("121", robot);
					GenericMethods.pauseExecution(2000);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);
					GenericMethods.pauseExecution(8000);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
			GenericMethods.action_Key(driver, Keys.TAB);
			System.out.println("^^^^^^^^^^^^^^^^^^^^");
			/*GenericMethods.pauseExecution(3000);
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
				driver.findElement(By.id("buyCurrency")).sendKeys("USD");
				GenericMethods.action_Key(driver, Keys.TAB);
			 */	

			//IATA Validation
			System.out.println("IATA Validation");
			GenericMethods.clickElement(driver, By.id("iataRateStr"), null, 10);
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
			//driver.findElement(By.id("iataRateStr")).sendKeys("250");
			driver.findElement(By.id("iataRateStr")).sendKeys(ExcelUtils.getCellData("Air_MAWBDocCharges", rowNo, "SellRate", ScenarioDetailsHashMap));
			GenericMethods.action_Key(driver, Keys.TAB);
			try{
				GenericMethods.handleAlert(driver, "accept");
				System.out.println("alert found");
			}catch (Exception e) {
				System.out.println("alert not found");
				e.printStackTrace();
			}


			/*try {
					GenericMethods.parseChars("121", robot);
					GenericMethods.pauseExecution(2000);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);
					GenericMethods.pauseExecution(9000);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				GenericMethods.pauseExecution(3000);
				driver.findElement(By.id("iataCurrency")).sendKeys("USD");
				GenericMethods.action_Key(driver, Keys.TAB);
			 */
			GenericMethods.clickElement(driver, By.id("button.gridEditBtn"), null, 10);

		}
		GenericMethods.pauseExecution(6000);
		/*	GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "MasterAdditionalInfo_Img", ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(2000);
		GenericMethods.clearText(driver, null,orAirMawb.getElement(driver, "MasterAdditionalInfo", ScenarioDetailsHashMap, 10), 9);
		GenericMethods.replaceTextofTextField(driver, null,orAirMawb.getElement(driver, "MasterAndQuantity", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_MAWBDocCharges", rowNo, "NatureAndQuantity", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orAirMawb.getElement(driver, "MasterAdditionalInfo_Img", ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(2000);
		 */		
		//GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 10);
	}


}
