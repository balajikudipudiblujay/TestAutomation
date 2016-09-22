package business.scenarios;

import global.reusables.ExcelUtils;
import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;

import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.ui.Select;

import app.reuseables.AppReusableMethods;
import app.reuseables.ETransMenu;

public class AirPRS {

	static ObjectRepository Common = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/CommonObjects.xml");
	static ObjectRepository orAirPRS = new ObjectRepository(System.getProperty("user.dir") + "/ObjectRepository/AirPRS.xml");



	/**
	 * This method is to perform Ocean PRS Add Functionality after creating booking.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains Records Row number which was given in
	 *            Testdata
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @Author By: Pavan Bikumandla
	 * @Modified By: Sangeeta Mohanty
	 */
	public static void pictAirPRS_Add_AfterBooking(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Add,"PRS ( Pick Up / Delivery )",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Ocean",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Truck", ScenarioDetailsHashMap,10), 10);
		String ShipmentMode = ExcelUtils.getCellData("Air_PRS_Booking",rowNo, "ShipmentMode", ScenarioDetailsHashMap);
		if (ShipmentMode.equalsIgnoreCase("Air")) 
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		} 
		else if (ShipmentMode.equalsIgnoreCase("Truck"))
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Truck",ScenarioDetailsHashMap, 10), 10);
			String FTLType = ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"FTL", ScenarioDetailsHashMap);
			if (FTLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "FTL", ScenarioDetailsHashMap,10), 10);
			} else 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "LTL",ScenarioDetailsHashMap, 10), 10);
			}
		} else 
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Ocean", ScenarioDetailsHashMap,10), 10);
			String OceanLCLType = ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"LCL", ScenarioDetailsHashMap);
			if (OceanLCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "LCL", ScenarioDetailsHashMap,10), 10);
			}
			String OceanFCLType = ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"FCL", ScenarioDetailsHashMap);
			if (OceanFCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "FCL", ScenarioDetailsHashMap,10), 10);
			} 
		}
		String ExportorImport = ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"ExportOrImport", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null,orAirPRS.getElement(driver, "XportOrImport",ScenarioDetailsHashMap, 10),ExportorImport);

		//Single PRS script
		String PRSType = ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PRSType", ScenarioDetailsHashMap);
		if(PRSType.equalsIgnoreCase("Single"))
		{
			System.out.println("PRS Type :"+PRSType);

			GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingMainDetails", rowNo, "BookingId", ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
			boolean noRecordsMsg = orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
			String noRecords = orAirPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
			if (noRecordsMsg) 
			{
				if (!noRecords.equalsIgnoreCase("No Records Found")) 
				{
					int TableRowSize  = driver.findElements(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr")).size();
					for(int row=1;row<=TableRowSize;row++ )
					{
						String GridReferenceID = GenericMethods.getInnerText(driver, By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[3]"), null, 2);
						System.out.println("GridReferenceID:"+GridReferenceID);
						if(driver.findElement(By.id("refNoSearch")).getAttribute("value").equalsIgnoreCase(GridReferenceID))
						{
						driver.findElement(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[1]/input")).click();
						}
					}
				}
			}

			if(GenericMethods.isFieldEnabled(driver, null, orAirPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orAirPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 10);
				//AppReusableMethods.msgBox("PRS CREATED SUCCESFULLY");
				String OnSavePrsText = GenericMethods.getInnerText(driver,null,orAirPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 10),10);
				String[] PrsTextSplit = OnSavePrsText.split(":");
				String PrsText = PrsTextSplit[1].trim();
				String PrsId = PrsTextSplit[2];
				ExcelUtils.setCellData("Air_PRS_Booking", rowNo, "PRS_Id", PrsId, ScenarioDetailsHashMap);
				ExcelUtils.setCellData("Air_PRS_Booking", rowNo, "BookingId", ExcelUtils.getCellData("Air_BookingMainDetails", rowNo, "BookingId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				ExcelUtils.setCellData("Air_WarehouseMainDtls", rowNo, "PRS_ID", ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PRS_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(PrsText, ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PrsAddMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictAirPRS_SearchList_Booking(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orAirPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PRS_Id",ScenarioDetailsHashMap), "PRS Id","Validating PRS ID", ScenarioDetailsHashMap);
			}

		}
		else
		{
			System.out.println("PRS Type :"+PRSType);
			ArrayList<String> valuess = new ArrayList<String>();
			String PRSReferenceType = ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PRSAddReferenceType", ScenarioDetailsHashMap);
			if(PRSReferenceType.equalsIgnoreCase("Booking id"))
			{
				System.out.println("Booking id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
			}
			else if(PRSReferenceType.equalsIgnoreCase("House reference id")) 
			{
				System.out.println("House reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_HAWBMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap);
			}
			else
			{
				System.out.println("Master reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_MAWB","MAWBJobFileNo", ScenarioDetailsHashMap);
			}
			System.out.println("valuess "+valuess);
			System.out.println("...valuess.size()"+valuess.size());	
			for (int i = 0; i < valuess.size(); i++) 
			{
				System.out.println("valuess.get("+i+") :"+valuess.get(i));
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), valuess.get(i), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(10);
				//System.out.println("noRecordsMsg::"+ orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap, 10).isDisplayed());
				boolean noRecordsMsg = orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
				String noRecords = orAirPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
				if (noRecordsMsg) {
					if (noRecords.equalsIgnoreCase("No Records Found")) 
					{
						//System.out.println("No Records Found ");
						//AppReusableMethods.msgBox("No Records Found");
					}
					else 
					{
						int TableRowSize  = driver.findElements(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr")).size();
						for(int row=1;row<=TableRowSize;row++ )
						{
							String GridReferenceID = GenericMethods.getInnerText(driver, By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[3]"), null, 2);
							System.out.println("GridReferenceID:"+GridReferenceID);
							if(driver.findElement(By.id("refNoSearch")).getAttribute("value").equalsIgnoreCase(GridReferenceID))
							{
							driver.findElement(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[1]/input")).click();
							}
						}
					}
				}
			}
			if(GenericMethods.isFieldEnabled(driver, null, orAirPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10))
			{
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orAirPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 10);
				//AppReusableMethods.msgBox("PRS CREATED SUCCESFULLY");
				String OnSavePrsText = GenericMethods.getInnerText(driver,null,orAirPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 10),10);
				String[] PrsTextSplit = OnSavePrsText.split(":");
				String PrsText = PrsTextSplit[1].trim();
				String PrsId = PrsTextSplit[2];
				System.out.println("PrsText::::" + PrsText + "PrsId::::"+ PrsId);
				System.out.println("valuess.get(RowNo) :"+valuess.get(0));

				ArrayList<String> slno= ExcelUtils.getAllCellValuesOfColumn("Air_PRS_Booking", "sn", ScenarioDetailsHashMap);
				ArrayList<String> slnoWRH= ExcelUtils.getAllCellValuesOfColumn("Air_WarehouseMainDtls", "sn", ScenarioDetailsHashMap);
				for (int RowNo = 0; RowNo < valuess.size(); RowNo++) 
				{
					
					System.out.println("Integer.parseInt(slno.get(RowNo)) :"+Integer.parseInt(slno.get(RowNo)));
					System.out.println("Integer.parseInt(slnoWRH(RowNo)) :"+Integer.parseInt(slnoWRH.get(RowNo)));
					ExcelUtils.setCellDataKF("Air_PRS_Booking",Integer.parseInt(slno.get(RowNo)), "PRS_Id",PrsId, ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("Air_PRS_Booking",Integer.parseInt(slno.get(RowNo)), "BookingId",valuess.get(RowNo), ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("Air_WarehouseMainDtls", Integer.parseInt(slnoWRH.get(RowNo)), "PRS_ID", PrsId, ScenarioDetailsHashMap);
				}
				GenericMethods.assertTwoValues(PrsText, ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PrsAddMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictAirPRS_SearchList_Booking(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orAirPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PRS_Id",ScenarioDetailsHashMap), "PRS Id","Validating PRS ID", ScenarioDetailsHashMap);
			}
		}
	}


	/**
	 * This method is to perform Ocean PRS Add Functionality after creating HAWB.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains Records Row number which was given in
	 *            Testdata
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @Author By: Pavan Bikumandla
	 * @Modified By: Sangeeta Mohanty
	 */
	public static void pictAirPRS_Add_AfterHAWB(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Add,"PRS ( Pick Up / Delivery )",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Ocean",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Truck", ScenarioDetailsHashMap,10), 10);
		String ShipmentMode = ExcelUtils.getCellData("Air_PRS_HAWB",rowNo, "ShipmentMode", ScenarioDetailsHashMap);
		if (ShipmentMode.equalsIgnoreCase("Air")) 
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		} 
		else if (ShipmentMode.equalsIgnoreCase("Truck"))
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Truck",ScenarioDetailsHashMap, 10), 10);
			String FTLType = ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"FTL", ScenarioDetailsHashMap);
			if (FTLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "FTL", ScenarioDetailsHashMap,10), 10);
			} else 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "LTL",ScenarioDetailsHashMap, 10), 10);
			}
		} else 
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Ocean", ScenarioDetailsHashMap,10), 10);
			String OceanLCLType = ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"LCL", ScenarioDetailsHashMap);
			if (OceanLCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "LCL", ScenarioDetailsHashMap,10), 10);
			}
			String OceanFCLType = ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"FCL", ScenarioDetailsHashMap);
			if (OceanFCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "FCL", ScenarioDetailsHashMap,10), 10);
			} 
		}
		String ExportorImport = ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"ExportOrImport", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null,orAirPRS.getElement(driver, "XportOrImport",ScenarioDetailsHashMap, 10),ExportorImport);
		//Single PRS script
		String PRSType = ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PRSType", ScenarioDetailsHashMap);
		if(PRSType.equalsIgnoreCase("Single"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "ShipmentReferenceNo", ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
			boolean noRecordsMsg = orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
			String noRecords = orAirPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
			if (noRecordsMsg) 
			{
				if (!noRecords.equalsIgnoreCase("No Records Found")) 
				{
					int TableRowSize  = driver.findElements(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr")).size();
					for(int row=1;row<=TableRowSize;row++ )
					{
						String GridReferenceID = GenericMethods.getInnerText(driver, By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[3]"), null, 2);
						System.out.println("GridReferenceID:"+GridReferenceID);
						if(driver.findElement(By.id("refNoSearch")).getAttribute("value").equalsIgnoreCase(GridReferenceID))
						{
						driver.findElement(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[1]/input")).click();
						}
					}
				}
			}

			if(GenericMethods.isFieldEnabled(driver, null, orAirPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orAirPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 10);
				String OnSavePrsText = GenericMethods.getInnerText(driver,null,orAirPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 10),10);
				String[] PrsTextSplit = OnSavePrsText.split(":");
				String PrsText = PrsTextSplit[1].trim();
				String PrsId = PrsTextSplit[2];
				ExcelUtils.setCellData("Air_PRS_HAWB", rowNo, "PRS_Id", PrsId, ScenarioDetailsHashMap);
				ExcelUtils.setCellData("Air_PRS_HAWB", rowNo, "BookingId", ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "ShipmentReferenceNo", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				ExcelUtils.setCellData("Air_WarehouseMainDtls", rowNo, "PRS_ID", ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PRS_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(PrsText, ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PrsAddMsg",ScenarioDetailsHashMap), "Validating PRS ADD",ScenarioDetailsHashMap);
				pictAirPRS_SearchList_HAWB(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orAirPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PRS_Id",ScenarioDetailsHashMap), "PRS Id","Validating PRS ID", ScenarioDetailsHashMap);
			}


		}
		else
		{

			ArrayList<String> valuess = new ArrayList<String>();
			String PRSReferenceType = ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PRSAddReferenceType", ScenarioDetailsHashMap);
			if(PRSReferenceType.equalsIgnoreCase("Booking id"))
			{
				System.out.println("Booking id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
			}
			else if(PRSReferenceType.equalsIgnoreCase("House reference id")) 
			{
				System.out.println("House reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_HAWBMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap);
			}
			else
			{
				System.out.println("Master reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_MAWB","MAWBJobFileNo", ScenarioDetailsHashMap);
			}
			System.out.println("valuess "+valuess);
			System.out.println("...valuess.size()"+valuess.size());	
			for (int i = 0; i < valuess.size(); i++) {
				System.out.println("valuess.get("+i+") :"+valuess.get(i));
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), valuess.get(i), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(10);
				//System.out.println("noRecordsMsg::"+ orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap, 10).isDisplayed());
				boolean noRecordsMsg = orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
				String noRecords = orAirPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
				if (noRecordsMsg) {
					if (noRecords.equalsIgnoreCase("No Records Found")) 
					{
						//System.out.println("No Records Found ");
						//AppReusableMethods.msgBox("No Records Found");
					} 
					else
					{
						int TableRowSize  = driver.findElements(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr")).size();
						for(int row=1;row<=TableRowSize;row++ )
						{
							String GridReferenceID = GenericMethods.getInnerText(driver, By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[3]"), null, 2);
							System.out.println("GridReferenceID:"+GridReferenceID);
							if(driver.findElement(By.id("refNoSearch")).getAttribute("value").equalsIgnoreCase(GridReferenceID))
							{
							driver.findElement(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[1]/input")).click();
							}
						}
					}
				}
			}
			if(GenericMethods.isFieldEnabled(driver, null, orAirPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orAirPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 10);
				//AppReusableMethods.msgBox("PRS CREATED SUCCESFULLY");
				String OnSavePrsText = GenericMethods.getInnerText(driver,null,orAirPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 10),10);
				String[] PrsTextSplit = OnSavePrsText.split(":");
				String PrsText = PrsTextSplit[1].trim();
				String PrsId = PrsTextSplit[2];
				System.out.println("PrsText::::" + PrsText + "PrsId::::"+ PrsId);
				System.out.println("valuess.get(RowNo) :"+valuess.get(0));
				ArrayList<String> slno= ExcelUtils.getAllCellValuesOfColumn("Air_PRS_HAWB", "sn", ScenarioDetailsHashMap);
				ArrayList<String> slnoWRH= ExcelUtils.getAllCellValuesOfColumn("Air_WarehouseMainDtls", "sn", ScenarioDetailsHashMap);
				for (int RowNo = 0; RowNo < valuess.size(); RowNo++) 
				{
					System.out.println("slno.get(RowNo)"+slno.get(RowNo));
					ExcelUtils.setCellDataKF("Air_PRS_HAWB",Integer.parseInt(slno.get(RowNo)), "PRS_Id",PrsId, ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("Air_PRS_HAWB",Integer.parseInt(slno.get(RowNo)), "BookingId",valuess.get(RowNo), ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("Air_WarehouseMainDtls", Integer.parseInt(slnoWRH.get(RowNo)), "PRS_ID", PrsId, ScenarioDetailsHashMap);
				}
				GenericMethods.assertTwoValues(PrsText, ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PrsAddMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictAirPRS_SearchList_HAWB(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orAirPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PRS_Id",ScenarioDetailsHashMap), "PRS Id","Validating PRS ID", ScenarioDetailsHashMap);
				//AppReusableMethods.msgBox("ASSERTED PRSID IN SEARCHLIST");

			}
		}
	}


	/**
	 * This method is to perform Ocean PRS Add Functionality after creating  Master/MAWB.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains Records Row number which was given in
	 *            Testdata
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @Author By: Pavan Bikumandla
	 * @Modified By: Sangeeta Mohanty
	 */
	public static void pictAirPRS_Add_AfterMAWB(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Add,"PRS ( Pick Up / Delivery )",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Ocean",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Truck", ScenarioDetailsHashMap,10), 10);
		String ShipmentMode = ExcelUtils.getCellData("Air_PRS_MAWB",rowNo, "ShipmentMode", ScenarioDetailsHashMap);
		if (ShipmentMode.equalsIgnoreCase("Air")) 
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		} 
		else if (ShipmentMode.equalsIgnoreCase("Truck"))
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Truck",ScenarioDetailsHashMap, 10), 10);
			String FTLType = ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"FTL", ScenarioDetailsHashMap);
			if (FTLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "FTL", ScenarioDetailsHashMap,10), 10);
			} else 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "LTL",ScenarioDetailsHashMap, 10), 10);
			}
		} else 
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Ocean", ScenarioDetailsHashMap,10), 10);
			String OceanLCLType = ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"LCL", ScenarioDetailsHashMap);
			if (OceanLCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "LCL", ScenarioDetailsHashMap,10), 10);
			}
			String OceanFCLType = ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"FCL", ScenarioDetailsHashMap);
			if (OceanFCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "FCL", ScenarioDetailsHashMap,10), 10);
			} 
		}
		String ExportorImport = ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"ExportOrImport", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null,orAirPRS.getElement(driver, "XportOrImport",ScenarioDetailsHashMap, 10),ExportorImport);

		//Single PRS script
		String PRSType = ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PRSType", ScenarioDetailsHashMap);
		if(PRSType.equalsIgnoreCase("Single"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_MAWB", rowNo, "MAWBJobFileNo", ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
			boolean noRecordsMsg = orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
			String noRecords = orAirPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
			if (noRecordsMsg) 
			{
				if (!noRecords.equalsIgnoreCase("No Records Found")) 
				{
					int TableRowSize  = driver.findElements(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr")).size();
					for(int row=1;row<=TableRowSize;row++ )
					{
						String GridReferenceID = GenericMethods.getInnerText(driver, By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[3]"), null, 2);
						System.out.println("GridReferenceID:"+GridReferenceID);
						if(driver.findElement(By.id("refNoSearch")).getAttribute("value").equalsIgnoreCase(GridReferenceID))
						{
						driver.findElement(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[1]/input")).click();
						}
					}
					
				}
			}

			if(GenericMethods.isFieldEnabled(driver, null, orAirPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orAirPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 10);
				//AppReusableMethods.msgBox("PRS CREATED SUCCESFULLY");
				String OnSavePrsText = GenericMethods.getInnerText(driver,null,orAirPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 10),10);
				String[] PrsTextSplit = OnSavePrsText.split(":");
				String PrsText = PrsTextSplit[1].trim();
				String PrsId = PrsTextSplit[2];
				ExcelUtils.setCellData("Air_PRS_MAWB", rowNo, "PRS_Id", PrsId, ScenarioDetailsHashMap);
				ExcelUtils.setCellData("Air_PRS_MAWB", rowNo, "BookingId", ExcelUtils.getCellData("Air_MAWB", rowNo, "MAWBJobFileNo", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				ExcelUtils.setCellData("Air_WarehouseMainDtls", rowNo, "PRS_ID", ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PRS_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(PrsText, ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PrsAddMsg",ScenarioDetailsHashMap), "Validating PRS ADD",ScenarioDetailsHashMap);
				pictAirPRS_SearchList_MAWB(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orAirPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PRS_Id",ScenarioDetailsHashMap), "PRS Id","Validating PRS ID", ScenarioDetailsHashMap);
			}

		}
		else
		{

			ArrayList<String> valuess = new ArrayList<String>();
			String PRSReferenceType = ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PRSAddReferenceType", ScenarioDetailsHashMap);
			if(PRSReferenceType.equalsIgnoreCase("Booking id"))
			{
				System.out.println("Booking id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
			}
			else if(PRSReferenceType.equalsIgnoreCase("House reference id")) 
			{
				System.out.println("House reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_HAWBMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap);
			}
			else
			{
				System.out.println("Master reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_MAWB","MAWBJobFileNo", ScenarioDetailsHashMap);
			}
			System.out.println("valuess "+valuess);
			System.out.println("...valuess.size()"+valuess.size());	
			for (int i = 0; i < valuess.size(); i++) {
				System.out.println("valuess.get("+i+") :"+valuess.get(i));
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), valuess.get(i), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(10);
				//System.out.println("noRecordsMsg::"+ orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap, 10).isDisplayed());
				boolean noRecordsMsg = orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
				String noRecords = orAirPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
				if (noRecordsMsg) {
					if (noRecords.equalsIgnoreCase("No Records Found")) 
					{
						//System.out.println("No Records Found ");
						//AppReusableMethods.msgBox("No Records Found");
					}
					else 
					{
						int TableRowSize  = driver.findElements(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr")).size();
						for(int row=1;row<=TableRowSize;row++ )
						{
							String GridReferenceID = GenericMethods.getInnerText(driver, By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[3]"), null, 2);
							System.out.println("GridReferenceID:"+GridReferenceID);
							if(driver.findElement(By.id("refNoSearch")).getAttribute("value").equalsIgnoreCase(GridReferenceID))
							{
							driver.findElement(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[1]/input")).click();
							}
						}
					}
				}
			}
			if(GenericMethods.isFieldEnabled(driver, null, orAirPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orAirPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 10);
				//AppReusableMethods.msgBox("PRS CREATED SUCCESFULLY");
				
				
				String OnSavePrsText = GenericMethods.getInnerText(driver,null,orAirPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 10),10);
				String[] PrsTextSplit = OnSavePrsText.split(":");
				String PrsText = PrsTextSplit[1].trim();
				String PrsId = PrsTextSplit[2];
				//System.out.println("PrsText::::" + PrsText + "PrsId::::"+ PrsId);
				ArrayList<String> slno= ExcelUtils.getAllCellValuesOfColumn("Air_PRS_MAWB", "sn", ScenarioDetailsHashMap);
				ArrayList<String> slnoWRH= ExcelUtils.getAllCellValuesOfColumn("Air_WarehouseMainDtls", "sn", ScenarioDetailsHashMap);
				for (int RowNo = 0; RowNo < valuess.size(); RowNo++) 
				{
					ExcelUtils.setCellDataKF("Air_PRS_MAWB",Integer.parseInt(slno.get(RowNo)), "PRS_Id",PrsId, ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("Air_PRS_MAWB",Integer.parseInt(slno.get(RowNo)), "BookingId",valuess.get(RowNo), ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("Air_WarehouseMainDtls", Integer.parseInt(slnoWRH.get(RowNo)), "PRS_ID", PrsId, ScenarioDetailsHashMap);
				}
				GenericMethods.assertTwoValues(PrsText, ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PrsAddMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictAirPRS_SearchList_MAWB(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orAirPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PRS_Id",ScenarioDetailsHashMap), "PRS Id","Validating PRS ID", ScenarioDetailsHashMap);
			}
		}
	}


	/**
	 * This method is to perform Ocean DRS Add Functionality in Import Scenario.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains Records Row number which was given in
	 *            Testdata
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @Author By: Pavan Bikumandla
	 * @Modified By: Sangeeta Mohanty
	 */
	public static void pictAirDRS_Add(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Add,"PRS ( Pick Up / Delivery )",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Ocean",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Truck", ScenarioDetailsHashMap,10), 10);
		String ShipmentMode = ExcelUtils.getCellData("Air_DRS_HAWB",rowNo, "ShipmentMode", ScenarioDetailsHashMap);
		if (ShipmentMode.equalsIgnoreCase("Air")) 
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		} 
		else if (ShipmentMode.equalsIgnoreCase("Truck"))
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Truck",ScenarioDetailsHashMap, 10), 10);
			String FTLType = ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"FTL", ScenarioDetailsHashMap);
			if (FTLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "FTL", ScenarioDetailsHashMap,10), 10);
			} else 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "LTL",ScenarioDetailsHashMap, 10), 10);
			}
		} else 
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Ocean", ScenarioDetailsHashMap,10), 10);
			String OceanLCLType = ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"LCL", ScenarioDetailsHashMap);
			if (OceanLCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "LCL", ScenarioDetailsHashMap,10), 10);
			}
			String OceanFCLType = ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"FCL", ScenarioDetailsHashMap);
			if (OceanFCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "FCL", ScenarioDetailsHashMap,10), 10);
			} 
		}
		String ExportorImport = ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"ExportOrImport", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null,orAirPRS.getElement(driver, "XportOrImport",ScenarioDetailsHashMap, 10),ExportorImport);

		//Single PRS script
		String PRSType = ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PRSType", ScenarioDetailsHashMap);
		if(PRSType.equalsIgnoreCase("Single"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "ShipmentReferenceNo", ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
			boolean noRecordsMsg = orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
			String noRecords = orAirPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
			if (noRecordsMsg) 
			{
				if (!noRecords.equalsIgnoreCase("No Records Found")) 
				{
					int TableRowSize  = driver.findElements(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr")).size();
					for(int row=1;row<=TableRowSize;row++ )
					{
						String GridReferenceID = GenericMethods.getInnerText(driver, By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[3]"), null, 2);
						System.out.println("GridReferenceID:"+GridReferenceID);
						if(driver.findElement(By.id("refNoSearch")).getAttribute("value").equalsIgnoreCase(GridReferenceID))
						{
						driver.findElement(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[1]/input")).click();
						}
					}
				}
			}

			if(GenericMethods.isFieldEnabled(driver, null, orAirPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orAirPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 10);
				//AppReusableMethods.msgBox("PRS CREATED SUCCESFULLY");
				String OnSavePrsText = GenericMethods.getInnerText(driver,null,orAirPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 10),10);
				String[] PrsTextSplit = OnSavePrsText.split(":");
				String PrsText = PrsTextSplit[1].trim();
				String PrsId = PrsTextSplit[2];
				ExcelUtils.setCellData("Air_DRS_HAWB", rowNo, "PRS_Id", PrsId, ScenarioDetailsHashMap);
				ExcelUtils.setCellData("Air_DRS_HAWB", rowNo, "BookingId", ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "ShipmentReferenceNo", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				ExcelUtils.setCellData("Air_WarehouseMainDtls", rowNo, "PRS_ID", ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PRS_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(PrsText, ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PrsAddMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictAirDRS_SearchList(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orAirPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PRS_Id",ScenarioDetailsHashMap), "PRS Id","Validating PRS ID", ScenarioDetailsHashMap);
			}

		}
		else
		{
			ArrayList<String> valuess = new ArrayList<String>();
			String PRSReferenceType = ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PRSAddReferenceType", ScenarioDetailsHashMap);
			if(PRSReferenceType.equalsIgnoreCase("Booking id"))
			{
				System.out.println("Booking id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
			}
			else if(PRSReferenceType.equalsIgnoreCase("House reference id")) 
			{
				System.out.println("House reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_HAWBMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap);
			}
			else
			{
				System.out.println("Master reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_MAWB","MAWBJobFileNo", ScenarioDetailsHashMap);
			}
			System.out.println("valuess "+valuess);
			System.out.println("...valuess.size()"+valuess.size());	
			for (int i = 0; i < valuess.size(); i++) {
				System.out.println("valuess.get("+i+") :"+valuess.get(i));
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), valuess.get(i), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(10);
				//System.out.println("noRecordsMsg::"+ orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap, 10).isDisplayed());
				boolean noRecordsMsg = orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
				String noRecords = orAirPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
				if (noRecordsMsg) {
					if (noRecords.equalsIgnoreCase("No Records Found")) 
					{
						//System.out.println("No Records Found ");
						//AppReusableMethods.msgBox("No Records Found");
					} 
					else 
					{
						int TableRowSize  = driver.findElements(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr")).size();
						for(int row=1;row<=TableRowSize;row++ )
						{
							String GridReferenceID = GenericMethods.getInnerText(driver, By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[3]"), null, 2);
							System.out.println("GridReferenceID:"+GridReferenceID);
							if(driver.findElement(By.id("refNoSearch")).getAttribute("value").equalsIgnoreCase(GridReferenceID))
							{
							driver.findElement(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[1]/input")).click();
							}
						}
						
					}
				}
			}
			if(GenericMethods.isFieldEnabled(driver, null, orAirPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orAirPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 10);
				//AppReusableMethods.msgBox("PRS CREATED SUCCESFULLY");
				String OnSavePrsText = GenericMethods.getInnerText(driver,null,orAirPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 10),10);
				String[] PrsTextSplit = OnSavePrsText.split(":");
				String PrsText = PrsTextSplit[1].trim();
				String PrsId = PrsTextSplit[2];
				//System.out.println("PrsText::::" + PrsText + "PrsId::::"+ PrsId);
				ArrayList<String> slno= ExcelUtils.getAllCellValuesOfColumn("Air_DRS_HAWB", "sn", ScenarioDetailsHashMap);
				ArrayList<String> slnoWRH= ExcelUtils.getAllCellValuesOfColumn("Air_WarehouseMainDtls", "sn", ScenarioDetailsHashMap);
				for (int RowNo = 0; RowNo < valuess.size(); RowNo++) 
				{
					ExcelUtils.setCellDataKF("Air_DRS_HAWB",Integer.parseInt(slno.get(RowNo)), "PRS_Id",PrsId, ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("Air_DRS_HAWB",Integer.parseInt(slno.get(RowNo)), "BookingId",valuess.get(RowNo), ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("Air_WarehouseMainDtls", Integer.parseInt(slnoWRH.get(RowNo)), "PRS_ID", PrsId, ScenarioDetailsHashMap);
				}
				GenericMethods.assertTwoValues(PrsText, ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PrsAddMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictAirDRS_SearchList(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orAirPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PRS_Id",ScenarioDetailsHashMap), "PRS Id","Validating PRS ID", ScenarioDetailsHashMap);
			}
		}
	}



	/**
	 * This method is to perform Ocean PRS Close Functionality After Booking.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains TestData Record Row number
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @Author By: Pavan Bikumandla
	 * @Modified By: Sangeeta M
	 */
	public static void pictAirPRS_Close_AfterBooking(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Close,	"PRS ( Pick Up / Delivery ) Close",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "PrsIdLOV",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.selectWindow(driver);
		pictAirPRS_SearchList_Booking(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.switchToParent(driver); 
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(3000);
		int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
		//System.out.println("rowCount::::" + rowCount);
		for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
			String PrsStatus = ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"Prs_Status", ScenarioDetailsHashMap);
			if (PrsStatus.equalsIgnoreCase("Partial")) {
				//System.out.println("Inside partial");
				int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PacksReceived",ScenarioDetailsHashMap));
				ReceivedGoods = ReceivedGoods / 2;
				String ReceivedPacks = String.valueOf(ReceivedGoods);
				GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
			}
			else
			{
				//System.out.println("Inside full");
				GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
			}
			GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
			GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "Remarks",ScenarioDetailsHashMap), 10);

		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.pauseExecution(8000);
		String OnSavePrsText = orAirPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
		String[] OnClosePrsSplit = OnSavePrsText.split(":");
		String PrsCloseText = OnClosePrsSplit[1].trim();
		//System.out.println("PrsCloseText::" + PrsCloseText);
		//AppReusableMethods.msgBox("PRS CLOSED SUCCESFULLY");
		GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
		pictAirPRS_SearchList_Booking(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.assertInnerText(driver, null, orAirPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
		//AppReusableMethods.msgBox("ASSERTED PRSCLOSE ID IN SEARCHLIST");
	}



	/**
	 * This method is to perform Ocean PRS Close Functionality After HAWB.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains TestData Record Row number
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @Author By: Pavan Bikumandla
	 * @Modified By: Sangeeta M
	 */
	public static void pictAirPRS_Close_AfterHAWB(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Close,	"PRS ( Pick Up / Delivery ) Close",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "PrsIdLOV",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.selectWindow(driver);
		pictAirPRS_SearchList_HAWB(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(3000);
		int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
		//System.out.println("rowCount::::" + rowCount);
		for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
			String PrsStatus = ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"Prs_Status", ScenarioDetailsHashMap);
			if (PrsStatus.equalsIgnoreCase("Partial")) {
				//System.out.println("Inside partial");
				int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PacksReceived",ScenarioDetailsHashMap));
				ReceivedGoods = ReceivedGoods / 2;
				String ReceivedPacks = String.valueOf(ReceivedGoods);
				GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
			}
			else
			{
				//System.out.println("Inside full");
				GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
			}
			GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
			GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "Remarks",ScenarioDetailsHashMap), 10);

		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.pauseExecution(8000);
		String OnSavePrsText = orAirPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
		String[] OnClosePrsSplit = OnSavePrsText.split(":");
		String PrsCloseText = OnClosePrsSplit[1].trim();
		System.out.println("PrsCloseText::" + PrsCloseText);
		//AppReusableMethods.msgBox("PRS CLOSED SUCCESFULLY");
		GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
		pictAirPRS_SearchList_HAWB(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.assertInnerText(driver, null, orAirPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
		//AppReusableMethods.msgBox("ASSERTED PRSCLOSE ID IN SEARCHLIST");
	}



	/**
	 * This method is to perform Ocean PRS Close Functionality After MAWB.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains TestData Record Row number
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @Author By: Pavan Bikumandla
	 * @Modified By: Sangeeta M
	 */
	public static void pictAirPRS_Close_AfterMAWB(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Close,	"PRS ( Pick Up / Delivery ) Close",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "PrsIdLOV",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.selectWindow(driver);
		pictAirPRS_SearchList_MAWB(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(3000);
		int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
		//System.out.println("rowCount::::" + rowCount);
		for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
			String PrsStatus = ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"Prs_Status", ScenarioDetailsHashMap);
			if (PrsStatus.equalsIgnoreCase("Partial")) {
				//System.out.println("Inside partial");
				int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PacksReceived",ScenarioDetailsHashMap));
				ReceivedGoods = ReceivedGoods / 2;
				String ReceivedPacks = String.valueOf(ReceivedGoods);
				GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
			}
			else
			{
				//System.out.println("Inside full");
				GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
			}
			GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
			GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "Remarks",ScenarioDetailsHashMap), 10);

		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.pauseExecution(8000);
		String OnSavePrsText = orAirPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
		String[] OnClosePrsSplit = OnSavePrsText.split(":");
		String PrsCloseText = OnClosePrsSplit[1].trim();
		System.out.println("PrsCloseText::" + PrsCloseText);
		//AppReusableMethods.msgBox("PRS CLOSED SUCCESFULLY");
		GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
		pictAirPRS_SearchList_MAWB(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.assertInnerText(driver, null, orAirPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
		//AppReusableMethods.msgBox("ASSERTED PRSCLOSE ID IN SEARCHLIST");
	}



	/**
	 * This method is to perform Ocean DRS Close Functionality in Import Scenario.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains TestData Record Row number
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @Author By: Pavan Bikumandla
	 * @Modified By: Sangeeta M
	 */
	public static void pictAirDRS_Close(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Close,	"PRS ( Pick Up / Delivery ) Close",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "PrsIdLOV",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.selectWindow(driver);
		pictAirDRS_SearchList(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(3000);
		int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
		//System.out.println("rowCount::::" + rowCount);
		for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
			String PrsStatus = ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"Prs_Status", ScenarioDetailsHashMap);
			if (PrsStatus.equalsIgnoreCase("Partial")) {
				//System.out.println("Inside partial");
				int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PacksReceived",ScenarioDetailsHashMap));
				ReceivedGoods = ReceivedGoods / 2;
				String ReceivedPacks = String.valueOf(ReceivedGoods);
				GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
			}
			else
			{
				//System.out.println("Inside full");
				GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
			}
			GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
			GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "Remarks",ScenarioDetailsHashMap), 10);

		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.pauseExecution(8000);
		String OnSavePrsText = orAirPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
		String[] OnClosePrsSplit = OnSavePrsText.split(":");
		String PrsCloseText = OnClosePrsSplit[1].trim();
		System.out.println("PrsCloseText::" + PrsCloseText);
		//AppReusableMethods.msgBox("PRS CLOSED SUCCESFULLY");
		GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
		pictAirDRS_SearchList(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.assertInnerText(driver, null, orAirPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
		//AppReusableMethods.msgBox("ASSERTED PRSCLOSE ID IN SEARCHLIST");
	}





	/**
	 * This method is to perform Ocean PRS QuickLink Functionality After Booking.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains TestData Record Row number
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @Author By: Sangeeta	Mohanty
	 * 
	 */
	public static void pictAirPRS_QuickLink_AfterBooking(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		ExcelUtils.getAllCellValuesOfColumn("Air_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
		//System.out.println("in ocean prs add");
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Add,"PRS ( Pick Up / Delivery )",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Ocean",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Truck", ScenarioDetailsHashMap,10), 10);
		String ShipmentMode = ExcelUtils.getCellData("Air_PRS_Booking",rowNo, "ShipmentMode", ScenarioDetailsHashMap);
		if (ShipmentMode.equalsIgnoreCase("Air")) 
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		} 
		else if (ShipmentMode.equalsIgnoreCase("Truck"))
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Truck",ScenarioDetailsHashMap, 10), 10);
			String FTLType = ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"FTL", ScenarioDetailsHashMap);
			if (FTLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "FTL", ScenarioDetailsHashMap,10), 10);
			} else 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "LTL",ScenarioDetailsHashMap, 10), 10);
			}
		} else 
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Ocean", ScenarioDetailsHashMap,10), 10);
			String OceanLCLType = ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"LCL", ScenarioDetailsHashMap);
			if (OceanLCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "LCL", ScenarioDetailsHashMap,10), 10);
			}
			String OceanFCLType = ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"FCL", ScenarioDetailsHashMap);
			if (OceanFCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "FCL", ScenarioDetailsHashMap,10), 10);
			} 

		}
		String ExportorImport = ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"ExportOrImport", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null,orAirPRS.getElement(driver, "XportOrImport",ScenarioDetailsHashMap, 10),ExportorImport);

		//Single PRS script
		String PRSType = ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PRSType", ScenarioDetailsHashMap);
		if(PRSType.equalsIgnoreCase("Single"))
		{
			System.out.println("PRS Type :"+PRSType);
			GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_BookingMainDetails", rowNo, "BookingId", ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
			boolean noRecordsMsg = orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
			String noRecords = orAirPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
			if (noRecordsMsg) 
			{
				if (!noRecords.equalsIgnoreCase("No Records Found")) 
				{
					int TableRowSize  = driver.findElements(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr")).size();
					for(int row=1;row<=TableRowSize;row++ )
					{
						String GridReferenceID = GenericMethods.getInnerText(driver, By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[3]"), null, 2);
						System.out.println("GridReferenceID:"+GridReferenceID);
						if(driver.findElement(By.id("refNoSearch")).getAttribute("value").equalsIgnoreCase(GridReferenceID))
						{
						driver.findElement(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[1]/input")).click();
						}
					}
					
				}
			}

			if(GenericMethods.isFieldEnabled(driver, null, orAirPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orAirPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSNavigationList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PRSNavigationList", ScenarioDetailsHashMap));
				GenericMethods.clickElement(driver, null, orAirPRS.getElement(driver, "PRSNavigationListImg", ScenarioDetailsHashMap, 10), 2)	;
				GenericMethods.pauseExecution(6000);

				//Moving to PRS Close page
				//GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(3000);
				int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
				//System.out.println("rowCount::::" + rowCount);
				for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
					String PrsStatus = ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"Prs_Status", ScenarioDetailsHashMap);
					if (PrsStatus.equalsIgnoreCase("Partial")) {
						//System.out.println("Inside partial");
						int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PacksReceived",ScenarioDetailsHashMap));
						ReceivedGoods = ReceivedGoods / 2;
						String ReceivedPacks = String.valueOf(ReceivedGoods);
						GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
					}
					else
					{
						//System.out.println("Inside full");
						GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
					}
					GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
					GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "Remarks",ScenarioDetailsHashMap), 10);
				}
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
				GenericMethods.pauseExecution(8000);
				String OnSavePrsText = orAirPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();

				String[] OnClosePrsSplit = OnSavePrsText.split(":");
				String PrsCloseText = OnClosePrsSplit[1].trim();
				String PrsId = OnClosePrsSplit[2];
				System.out.println("PrsCloseText::" + PrsCloseText);
				ExcelUtils.setCellData("Air_PRS_Booking", rowNo, "PRS_Id", PrsId, ScenarioDetailsHashMap);
				ExcelUtils.setCellData("Air_PRS_Booking", rowNo, "BookingId", ExcelUtils.getCellData("Air_BookingMainDetails", rowNo, "BookingId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				ExcelUtils.setCellData("Air_WarehouseMainDtls", rowNo, "PRS_ID", ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PRS_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictAirPRS_SearchList_Booking(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orAirPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
			}

		}
		else
		{
			ArrayList<String> valuess = new ArrayList<String>();
			String PRSReferenceType = ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PRSAddReferenceType", ScenarioDetailsHashMap);
			if(PRSReferenceType.equalsIgnoreCase("Booking id"))
			{
				System.out.println("Booking id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
			}
			else if(PRSReferenceType.equalsIgnoreCase("House reference id")) 
			{
				System.out.println("House reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_HAWBMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap);
			}
			else
			{
				System.out.println("Master reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_MAWB","MAWBJobFileNo", ScenarioDetailsHashMap);
			}

			System.out.println("...valuess.size()"+valuess.size());	
			for (int i = 0; i < valuess.size(); i++) {
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), valuess.get(i), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(10);
				//System.out.println("noRecordsMsg::"+ orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap, 10).isDisplayed());
				boolean noRecordsMsg = orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
				String noRecords = orAirPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
				if (noRecordsMsg) 
				{
					if (noRecords.equalsIgnoreCase("No Records Found")) 
					{
						//System.out.println("No Records Found ");
						AppReusableMethods.msgBox("No Records Found");
					} 
					else 
					{
						int TableRowSize  = driver.findElements(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr")).size();
						for(int row=1;row<=TableRowSize;row++ )
						{
							String GridReferenceID = GenericMethods.getInnerText(driver, By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[3]"), null, 2);
							System.out.println("GridReferenceID:"+GridReferenceID);
							if(driver.findElement(By.id("refNoSearch")).getAttribute("value").equalsIgnoreCase(GridReferenceID))
							{
							driver.findElement(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[1]/input")).click();
							}
						}
					
					}
				}
			}

			if(GenericMethods.isFieldEnabled(driver, null, orAirPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(3000);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null,orAirPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSNavigationList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PRSNavigationList", ScenarioDetailsHashMap));
				GenericMethods.clickElement(driver, null, orAirPRS.getElement(driver, "PRSNavigationListImg", ScenarioDetailsHashMap, 10), 2)	;
				GenericMethods.pauseExecution(6000);

				//Moving to PRS Close page
				//GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(3000);
				int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
				//System.out.println("rowCount::::" + rowCount);
				for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
					String PrsStatus = ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"Prs_Status", ScenarioDetailsHashMap);
					if (PrsStatus.equalsIgnoreCase("Partial")) {
						//System.out.println("Inside partial");
						int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PacksReceived",ScenarioDetailsHashMap));
						ReceivedGoods = ReceivedGoods / 2;
						String ReceivedPacks = String.valueOf(ReceivedGoods);
						GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
					}
					else
					{
						//System.out.println("Inside full");
						GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
					}
					GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("Air_PRS_Booking", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
					GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "Remarks",ScenarioDetailsHashMap), 10);

				}
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
				GenericMethods.pauseExecution(8000);
				String OnSavePrsText = orAirPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
				String[] OnClosePrsSplit = OnSavePrsText.split(":");
				String PrsCloseText = OnClosePrsSplit[1].trim();
				String PrsId = OnClosePrsSplit[2];
				//System.out.println("PrsCloseText::" + PrsCloseText);
				//AppReusableMethods.msgBox("PRS CLOSED SUCCESFULLY");
				ArrayList<String> slno= ExcelUtils.getAllCellValuesOfColumn("Air_PRS_Booking", "sn", ScenarioDetailsHashMap);
				ArrayList<String> slnoWRH= ExcelUtils.getAllCellValuesOfColumn("Air_WarehouseMainDtls", "sn", ScenarioDetailsHashMap);
				for (int RowNo = 0; RowNo < valuess.size(); RowNo++) 
				{
					ExcelUtils.setCellDataKF("Air_PRS_Booking",Integer.parseInt(slno.get(RowNo)), "PRS_Id",PrsId, ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("Air_PRS_Booking",Integer.parseInt(slno.get(RowNo)), "BookingId",valuess.get(RowNo), ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("Air_WarehouseMainDtls", Integer.parseInt(slnoWRH.get(RowNo)), "PRS_ID", PrsId, ScenarioDetailsHashMap);
				}
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictAirPRS_SearchList_Booking(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orAirPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
				//AppReusableMethods.msgBox("ASSERTED PRSCLOSE ID IN SEARCHLIST");
			}
		}
	}


	/**
	 * This method is to perform Ocean PRS QuickLink Functionality After HAWB.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains TestData Record Row number
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @Author By: Sangeeta	Mohanty
	 * 
	 */
	public static void pictAirPRS_QuickLink_AfterHAWB(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		ExcelUtils.getAllCellValuesOfColumn("Air_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
		//System.out.println("in ocean prs add");
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Add,"PRS ( Pick Up / Delivery )",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Ocean",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Truck", ScenarioDetailsHashMap,10), 10);
		String ShipmentMode = ExcelUtils.getCellData("Air_PRS_HAWB",rowNo, "ShipmentMode", ScenarioDetailsHashMap);
		if (ShipmentMode.equalsIgnoreCase("Air")) 
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		} 
		else if (ShipmentMode.equalsIgnoreCase("Truck"))
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Truck",ScenarioDetailsHashMap, 10), 10);
			String FTLType = ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"FTL", ScenarioDetailsHashMap);
			if (FTLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "FTL", ScenarioDetailsHashMap,10), 10);
			} else 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "LTL",ScenarioDetailsHashMap, 10), 10);
			}
		} else 
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Ocean", ScenarioDetailsHashMap,10), 10);
			String OceanLCLType = ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"LCL", ScenarioDetailsHashMap);
			if (OceanLCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "LCL", ScenarioDetailsHashMap,10), 10);
			}
			String OceanFCLType = ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"FCL", ScenarioDetailsHashMap);
			if (OceanFCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "FCL", ScenarioDetailsHashMap,10), 10);
			} 

		}
		String ExportorImport = ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"ExportOrImport", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null,orAirPRS.getElement(driver, "XportOrImport",ScenarioDetailsHashMap, 10),ExportorImport);

		//Single PRS script
		String PRSType = ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PRSType", ScenarioDetailsHashMap);
		if(PRSType.equalsIgnoreCase("Single"))
		{
			System.out.println("PRS Type :"+PRSType);
			GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "ShipmentReferenceNo", ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
			boolean noRecordsMsg = orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
			String noRecords = orAirPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
			if (noRecordsMsg) 
			{
				if (!noRecords.equalsIgnoreCase("No Records Found")) 
				{
					int TableRowSize  = driver.findElements(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr")).size();
					for(int row=1;row<=TableRowSize;row++ )
					{
						String GridReferenceID = GenericMethods.getInnerText(driver, By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[3]"), null, 2);
						System.out.println("GridReferenceID:"+GridReferenceID);
						if(driver.findElement(By.id("refNoSearch")).getAttribute("value").equalsIgnoreCase(GridReferenceID))
						{
						driver.findElement(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[1]/input")).click();
						}
					}
					
				}
			}

			if(GenericMethods.isFieldEnabled(driver, null, orAirPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orAirPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSNavigationList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PRSNavigationList", ScenarioDetailsHashMap));
				GenericMethods.clickElement(driver, null, orAirPRS.getElement(driver, "PRSNavigationListImg", ScenarioDetailsHashMap, 10), 2)	;
				GenericMethods.pauseExecution(6000);

				//Moving to PRS Close page
				//GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(3000);
				int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
				//System.out.println("rowCount::::" + rowCount);
				for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
					String PrsStatus = ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"Prs_Status", ScenarioDetailsHashMap);
					if (PrsStatus.equalsIgnoreCase("Partial")) {
						//System.out.println("Inside partial");
						int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PacksReceived",ScenarioDetailsHashMap));
						ReceivedGoods = ReceivedGoods / 2;
						String ReceivedPacks = String.valueOf(ReceivedGoods);
						GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
					}
					else
					{
						//System.out.println("Inside full");
						GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
					}
					GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
					GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "Remarks",ScenarioDetailsHashMap), 10);
				}
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
				GenericMethods.pauseExecution(8000);
				
				
				String OnSavePrsText = orAirPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
				System.out.println("OnSavePrsText"+OnSavePrsText);
				
				String[] OnClosePrsSplit = OnSavePrsText.split(":");
				String PrsCloseText = OnClosePrsSplit[1].trim();
				String PrsId = OnClosePrsSplit[2];
				System.out.println("PrsCloseText::" + PrsCloseText);
				ExcelUtils.setCellData("Air_PRS_HAWB", rowNo, "PRS_Id", PrsId, ScenarioDetailsHashMap);
				ExcelUtils.setCellData("Air_PRS_HAWB", rowNo, "BookingId", ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "ShipmentReferenceNo", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				ExcelUtils.setCellData("Air_WarehouseMainDtls", rowNo, "PRS_ID", ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PRS_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictAirPRS_SearchList_HAWB(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orAirPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
				
			}

		}
		else
		{
			ArrayList<String> valuess = new ArrayList<String>();
			String PRSReferenceType = ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PRSAddReferenceType", ScenarioDetailsHashMap);
			if(PRSReferenceType.equalsIgnoreCase("Booking id"))
			{
				System.out.println("Booking id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
			}
			else if(PRSReferenceType.equalsIgnoreCase("House reference id")) 
			{
				System.out.println("House reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_HAWBMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap);
			}
			else
			{
				System.out.println("Master reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_MAWB","MAWBJobFileNo", ScenarioDetailsHashMap);
			}

			System.out.println("...valuess.size()"+valuess.size());	
			for (int i = 0; i < valuess.size(); i++) {
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), valuess.get(i), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(10);
				//System.out.println("noRecordsMsg::"+ orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap, 10).isDisplayed());
				boolean noRecordsMsg = orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
				String noRecords = orAirPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
				if (noRecordsMsg) {
					if (noRecords.equalsIgnoreCase("No Records Found")) 
					{
						//System.out.println("No Records Found ");
						AppReusableMethods.msgBox("No Records Found");
					} 
					else 
					{
						int TableRowSize  = driver.findElements(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr")).size();
						for(int row=1;row<=TableRowSize;row++ )
						{
							String GridReferenceID = GenericMethods.getInnerText(driver, By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[3]"), null, 2);
							System.out.println("GridReferenceID:"+GridReferenceID);
							if(driver.findElement(By.id("refNoSearch")).getAttribute("value").equalsIgnoreCase(GridReferenceID))
							{
							driver.findElement(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[1]/input")).click();
							}
						}
						
					}
				}
			}
			if(GenericMethods.isFieldEnabled(driver, null, orAirPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(3000);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null,orAirPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSNavigationList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PRSNavigationList", ScenarioDetailsHashMap));
				GenericMethods.clickElement(driver, null, orAirPRS.getElement(driver, "PRSNavigationListImg", ScenarioDetailsHashMap, 10), 2)	;
				GenericMethods.pauseExecution(6000);

				//Moving to PRS Close page
				//GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(3000);
				int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
				//System.out.println("rowCount::::" + rowCount);
				for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
					String PrsStatus = ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"Prs_Status", ScenarioDetailsHashMap);
					if (PrsStatus.equalsIgnoreCase("Partial")) {
						//System.out.println("Inside partial");
						int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PacksReceived",ScenarioDetailsHashMap));
						ReceivedGoods = ReceivedGoods / 2;
						String ReceivedPacks = String.valueOf(ReceivedGoods);
						GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
					}
					else
					{
						//System.out.println("Inside full");
						GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
					}
					GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("Air_PRS_HAWB", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
					GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "Remarks",ScenarioDetailsHashMap), 10);

				}
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
				GenericMethods.pauseExecution(8000);
				String OnSavePrsText = orAirPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
				String[] OnClosePrsSplit = OnSavePrsText.split(":");
				String PrsCloseText = OnClosePrsSplit[1].trim();
				String PrsId = OnClosePrsSplit[2];
				System.out.println("PrsCloseText::" + PrsCloseText);
				//System.out.println("PrsCloseText::" + PrsCloseText);
				//AppReusableMethods.msgBox("PRS CLOSED SUCCESFULLY");
				ArrayList<String> slno= ExcelUtils.getAllCellValuesOfColumn("Air_PRS_HAWB", "sn", ScenarioDetailsHashMap);
				ArrayList<String> slnoWRH= ExcelUtils.getAllCellValuesOfColumn("Air_WarehouseMainDtls", "sn", ScenarioDetailsHashMap);
				for (int RowNo = 0; RowNo < valuess.size(); RowNo++) 
				{
					ExcelUtils.setCellDataKF("Air_PRS_HAWB",Integer.parseInt(slno.get(RowNo)), "PRS_Id",PrsId, ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("Air_PRS_HAWB",Integer.parseInt(slno.get(RowNo)), "BookingId",valuess.get(RowNo), ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("Air_WarehouseMainDtls", Integer.parseInt(slnoWRH.get(RowNo)), "PRS_ID", PrsId, ScenarioDetailsHashMap);
				}
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictAirPRS_SearchList_HAWB(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orAirPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
				//AppReusableMethods.msgBox("ASSERTED PRSCLOSE ID IN SEARCHLIST");
			}
		}
	}


	/**
	 * This method is to perform Ocean PRS QuickLink Functionality After MAWB.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains TestData Record Row number
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @Author By: Sangeeta	Mohanty
	 * 
	 */
	public static void pictAirPRS_QuickLink_AfterMAWB(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		ExcelUtils.getAllCellValuesOfColumn("Air_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
		//System.out.println("in ocean prs add");
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Add,"PRS ( Pick Up / Delivery )",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Ocean",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Truck", ScenarioDetailsHashMap,10), 10);
		String ShipmentMode = ExcelUtils.getCellData("Air_PRS_MAWB",rowNo, "ShipmentMode", ScenarioDetailsHashMap);
		if (ShipmentMode.equalsIgnoreCase("Air")) 
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		} 
		else if (ShipmentMode.equalsIgnoreCase("Truck"))
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Truck",ScenarioDetailsHashMap, 10), 10);
			String FTLType = ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"FTL", ScenarioDetailsHashMap);
			if (FTLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "FTL", ScenarioDetailsHashMap,10), 10);
			} else 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "LTL",ScenarioDetailsHashMap, 10), 10);
			}
		} else 
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Ocean", ScenarioDetailsHashMap,10), 10);
			String OceanLCLType = ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"LCL", ScenarioDetailsHashMap);
			if (OceanLCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "LCL", ScenarioDetailsHashMap,10), 10);
			}
			String OceanFCLType = ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"FCL", ScenarioDetailsHashMap);
			if (OceanFCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "FCL", ScenarioDetailsHashMap,10), 10);
			} 

		}
		String ExportorImport = ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"ExportOrImport", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null,orAirPRS.getElement(driver, "XportOrImport",ScenarioDetailsHashMap, 10),ExportorImport);

		//Single PRS script
		String PRSType = ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PRSType", ScenarioDetailsHashMap);
		if(PRSType.equalsIgnoreCase("Single"))
		{
			System.out.println("PRS Type :"+PRSType);
			GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_MAWB", rowNo, "MAWBJobFileNo", ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
			boolean noRecordsMsg = orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
			String noRecords = orAirPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
			if (noRecordsMsg) 
			{
				if (!noRecords.equalsIgnoreCase("No Records Found")) 
				{
					int TableRowSize  = driver.findElements(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr")).size();
					for(int row=1;row<=TableRowSize;row++ )
					{
						String GridReferenceID = GenericMethods.getInnerText(driver, By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[3]"), null, 2);
						System.out.println("GridReferenceID:"+GridReferenceID);
						if(driver.findElement(By.id("refNoSearch")).getAttribute("value").equalsIgnoreCase(GridReferenceID))
						{
						driver.findElement(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[1]/input")).click();
						}
					}
					
				}
			}

			if(GenericMethods.isFieldEnabled(driver, null, orAirPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orAirPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
			

				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSNavigationList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PRSNavigationList", ScenarioDetailsHashMap));
				GenericMethods.clickElement(driver, null, orAirPRS.getElement(driver, "PRSNavigationListImg", ScenarioDetailsHashMap, 10), 2)	;
				GenericMethods.pauseExecution(6000);

				//Moving to PRS Close page
				//GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(3000);
				int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
				//System.out.println("rowCount::::" + rowCount);
				for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
					String PrsStatus = ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"Prs_Status", ScenarioDetailsHashMap);
					if (PrsStatus.equalsIgnoreCase("Partial")) {
						//System.out.println("Inside partial");
						int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PacksReceived",ScenarioDetailsHashMap));
						ReceivedGoods = ReceivedGoods / 2;
						String ReceivedPacks = String.valueOf(ReceivedGoods);
						GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
					}
					else
					{
						//System.out.println("Inside full");
						GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
					}
					GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
					GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "Remarks",ScenarioDetailsHashMap), 10);
				}
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
				GenericMethods.pauseExecution(8000);
				String OnSavePrsText = orAirPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
				String[] OnClosePrsSplit = OnSavePrsText.split(":");
				String PrsCloseText = OnClosePrsSplit[1].trim();
				String PrsId = OnClosePrsSplit[2];
				ExcelUtils.setCellData("Air_PRS_MAWB", rowNo, "PRS_Id", PrsId, ScenarioDetailsHashMap);
				ExcelUtils.setCellData("Air_PRS_MAWB", rowNo, "BookingId", ExcelUtils.getCellData("Air_MAWB", rowNo, "MAWBJobFileNo", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				ExcelUtils.setCellData("Air_WarehouseMainDtls", rowNo, "PRS_ID", ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PRS_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictAirPRS_SearchList_MAWB(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orAirPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
			}

		}
		else
		{
			ArrayList<String> valuess = new ArrayList<String>();
			String PRSReferenceType = ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PRSAddReferenceType", ScenarioDetailsHashMap);
			if(PRSReferenceType.equalsIgnoreCase("Booking id"))
			{
				System.out.println("Booking id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
			}
			else if(PRSReferenceType.equalsIgnoreCase("House reference id")) 
			{
				System.out.println("House reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_HAWBMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap);
			}
			else
			{
				System.out.println("Master reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_MAWB","MAWBJobFileNo", ScenarioDetailsHashMap);
			}

			System.out.println("...valuess.size()"+valuess.size());	
			for (int i = 0; i < valuess.size(); i++) {
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), valuess.get(i), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(10);
				//System.out.println("noRecordsMsg::"+ orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap, 10).isDisplayed());
				boolean noRecordsMsg = orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
				String noRecords = orAirPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
				if (noRecordsMsg) {
					if (noRecords.equalsIgnoreCase("No Records Found")) 
					{
						//System.out.println("No Records Found ");
						AppReusableMethods.msgBox("No Records Found");
					}
					else 
					{
						int TableRowSize  = driver.findElements(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr")).size();
						for(int row=1;row<=TableRowSize;row++ )
						{
							String GridReferenceID = GenericMethods.getInnerText(driver, By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[3]"), null, 2);
							System.out.println("GridReferenceID:"+GridReferenceID);
							if(driver.findElement(By.id("refNoSearch")).getAttribute("value").equalsIgnoreCase(GridReferenceID))
							{
							driver.findElement(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[1]/input")).click();
							}
						}
						
					}
				}
			}
			if(GenericMethods.isFieldEnabled(driver, null, orAirPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(3000);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null,orAirPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSNavigationList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PRSNavigationList", ScenarioDetailsHashMap));
				GenericMethods.clickElement(driver, null, orAirPRS.getElement(driver, "PRSNavigationListImg", ScenarioDetailsHashMap, 10), 2)	;
				GenericMethods.pauseExecution(6000);

				//Moving to PRS Close page
				//GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(3000);
				int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
				//System.out.println("rowCount::::" + rowCount);
				for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
					String PrsStatus = ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"Prs_Status", ScenarioDetailsHashMap);
					if (PrsStatus.equalsIgnoreCase("Partial")) {
						//System.out.println("Inside partial");
						int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PacksReceived",ScenarioDetailsHashMap));
						ReceivedGoods = ReceivedGoods / 2;
						String ReceivedPacks = String.valueOf(ReceivedGoods);
						GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
					}
					else
					{
						//System.out.println("Inside full");
						GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
					}
					GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("Air_PRS_MAWB", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
					GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "Remarks",ScenarioDetailsHashMap), 10);

				}
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
				GenericMethods.pauseExecution(8000);
				String OnSavePrsText = orAirPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
				String[] OnClosePrsSplit = OnSavePrsText.split(":");
				String PrsCloseText = OnClosePrsSplit[1].trim();
				String PrsId = OnClosePrsSplit[2];
				System.out.println("PrsCloseText::" + PrsCloseText);
				//System.out.println("PrsCloseText::" + PrsCloseText);
				//AppReusableMethods.msgBox("PRS CLOSED SUCCESFULLY");
				ArrayList<String> slno= ExcelUtils.getAllCellValuesOfColumn("Air_PRS_MAWB", "sn", ScenarioDetailsHashMap);
				ArrayList<String> slnoWRH= ExcelUtils.getAllCellValuesOfColumn("Air_WarehouseMainDtls", "sn", ScenarioDetailsHashMap);
				for (int RowNo = 0; RowNo < valuess.size(); RowNo++) 
				{
					ExcelUtils.setCellDataKF("Air_PRS_MAWB",Integer.parseInt(slno.get(RowNo)), "PRS_Id",PrsId, ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("Air_PRS_MAWB",Integer.parseInt(slno.get(RowNo)), "BookingId",valuess.get(RowNo), ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("Air_WarehouseMainDtls", Integer.parseInt(slnoWRH.get(RowNo)), "PRS_ID", PrsId, ScenarioDetailsHashMap);
				}
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictAirPRS_SearchList_MAWB(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orAirPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
				//AppReusableMethods.msgBox("ASSERTED PRSCLOSE ID IN SEARCHLIST");
			}
		}
	}


	/**
	 * This method is to perform Ocean DRS QuickLink Functionality in Import Scenario.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains TestData Record Row number
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @Author By: Sangeeta	Mohanty
	 * 
	 */
	public static void pictAirDRS_QuickLink(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		ExcelUtils.getAllCellValuesOfColumn("Air_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
		//System.out.println("in ocean prs add");
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Add,"PRS ( Pick Up / Delivery )",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Ocean",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Truck", ScenarioDetailsHashMap,10), 10);
		String ShipmentMode = ExcelUtils.getCellData("Air_DRS_HAWB",rowNo, "ShipmentMode", ScenarioDetailsHashMap);
		if (ShipmentMode.equalsIgnoreCase("Air")) 
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		} 
		else if (ShipmentMode.equalsIgnoreCase("Truck"))
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Truck",ScenarioDetailsHashMap, 10), 10);
			String FTLType = ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"FTL", ScenarioDetailsHashMap);
			if (FTLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "FTL", ScenarioDetailsHashMap,10), 10);
			} else 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "LTL",ScenarioDetailsHashMap, 10), 10);
			}
		} else 
		{
			GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "Ocean", ScenarioDetailsHashMap,10), 10);
			String OceanLCLType = ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"LCL", ScenarioDetailsHashMap);
			if (OceanLCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "LCL", ScenarioDetailsHashMap,10), 10);
			}
			String OceanFCLType = ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"FCL", ScenarioDetailsHashMap);
			if (OceanFCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orAirPRS.getElement(driver, "FCL", ScenarioDetailsHashMap,10), 10);
			} 

		}
		String ExportorImport = ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"ExportOrImport", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null,orAirPRS.getElement(driver, "XportOrImport",ScenarioDetailsHashMap, 10),ExportorImport);

		//Single PRS script
		String PRSType = ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PRSType", ScenarioDetailsHashMap);
		if(PRSType.equalsIgnoreCase("Single"))
		{
			System.out.println("PRS Type :"+PRSType);
			GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "ShipmentReferenceNo", ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
			boolean noRecordsMsg = orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
			String noRecords = orAirPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
			if (noRecordsMsg) 
			{
				if (!noRecords.equalsIgnoreCase("No Records Found")) 
				{
					int TableRowSize  = driver.findElements(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr")).size();
					for(int row=1;row<=TableRowSize;row++ )
					{
						String GridReferenceID = GenericMethods.getInnerText(driver, By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[3]"), null, 2);
						System.out.println("GridReferenceID:"+GridReferenceID);
						if(driver.findElement(By.id("refNoSearch")).getAttribute("value").equalsIgnoreCase(GridReferenceID))
						{
						driver.findElement(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[1]/input")).click();
						}
					}
					
				}
			}

			if(GenericMethods.isFieldEnabled(driver, null, orAirPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orAirPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				

				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSNavigationList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PRSNavigationList", ScenarioDetailsHashMap));
				GenericMethods.clickElement(driver, null, orAirPRS.getElement(driver, "PRSNavigationListImg", ScenarioDetailsHashMap, 10), 2)	;
				GenericMethods.pauseExecution(6000);

				//Moving to PRS Close page
				//GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(3000);
				int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
				//System.out.println("rowCount::::" + rowCount);
				for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
					String PrsStatus = ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"Prs_Status", ScenarioDetailsHashMap);
					if (PrsStatus.equalsIgnoreCase("Partial")) {
						//System.out.println("Inside partial");
						int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PacksReceived",ScenarioDetailsHashMap));
						ReceivedGoods = ReceivedGoods / 2;
						String ReceivedPacks = String.valueOf(ReceivedGoods);
						GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
					}
					else
					{
						//System.out.println("Inside full");
						GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
					}
					GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
					GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "Remarks",ScenarioDetailsHashMap), 10);
				}
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
				GenericMethods.pauseExecution(8000);
				String OnSavePrsText = orAirPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
				String[] OnClosePrsSplit = OnSavePrsText.split(":");
				String PrsCloseText = OnClosePrsSplit[1].trim();
				String PrsId = OnClosePrsSplit[2];
				System.out.println("PrsCloseText::" + PrsCloseText);
				ExcelUtils.setCellData("Air_DRS_HAWB", rowNo, "PRS_Id", PrsId, ScenarioDetailsHashMap);
				ExcelUtils.setCellData("Air_DRS_HAWB", rowNo, "BookingId", ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "ShipmentReferenceNo", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				ExcelUtils.setCellData("Air_WarehouseMainDtls", rowNo, "PRS_ID", ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PRS_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictAirDRS_SearchList(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orAirPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
		
			}

		}
		else
		{

			ArrayList<String> valuess = new ArrayList<String>();
			String PRSReferenceType = ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PRSAddReferenceType", ScenarioDetailsHashMap);
			if(PRSReferenceType.equalsIgnoreCase("Booking id"))
			{
				System.out.println("Booking id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
			}
			else if(PRSReferenceType.equalsIgnoreCase("House reference id")) 
			{
				System.out.println("House reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_HAWBMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap);
			}
			else
			{
				System.out.println("Master reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("Air_MAWB","MAWBJobFileNo", ScenarioDetailsHashMap);
			}

			System.out.println("...valuess.size()"+valuess.size());	
			for (int i = 0; i < valuess.size(); i++) {
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), valuess.get(i), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(10);
				//System.out.println("noRecordsMsg::"+ orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap, 10).isDisplayed());
				boolean noRecordsMsg = orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
				String noRecords = orAirPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
				if (noRecordsMsg) {
					if (noRecords.equalsIgnoreCase("No Records Found")) 
					{
						//System.out.println("No Records Found ");
						AppReusableMethods.msgBox("No Records Found");
					}
					else
					{
						int TableRowSize  = driver.findElements(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr")).size();
						for(int row=1;row<=TableRowSize;row++ )
						{
							String GridReferenceID = GenericMethods.getInnerText(driver, By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[3]"), null, 2);
							System.out.println("GridReferenceID:"+GridReferenceID);
							if(driver.findElement(By.id("refNoSearch")).getAttribute("value").equalsIgnoreCase(GridReferenceID))
							{
							driver.findElement(By.xpath("html/body/form/fieldset/table/tbody/tr[4]/td/div[1]/table/tbody/tr["+row+"]/td[1]/input")).click();
							}
						}
						
					}
				}
			}
			if(GenericMethods.isFieldEnabled(driver, null, orAirPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(3000);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null,orAirPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "PRSNavigationList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PRSNavigationList", ScenarioDetailsHashMap));
				GenericMethods.clickElement(driver, null, orAirPRS.getElement(driver, "PRSNavigationListImg", ScenarioDetailsHashMap, 10), 2)	;
				GenericMethods.pauseExecution(6000);

				//Moving to PRS Close page
				//GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(3000);
				int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
				//System.out.println("rowCount::::" + rowCount);
				for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
					String PrsStatus = ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"Prs_Status", ScenarioDetailsHashMap);
					if (PrsStatus.equalsIgnoreCase("Partial")) {
						//System.out.println("Inside partial");
						int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PacksReceived",ScenarioDetailsHashMap));
						ReceivedGoods = ReceivedGoods / 2;
						String ReceivedPacks = String.valueOf(ReceivedGoods);
						GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
					}
					else
					{
						//System.out.println("Inside full");
						GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
					}
					GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("Air_DRS_HAWB", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
					GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "Remarks",ScenarioDetailsHashMap), 10);

				}
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
				GenericMethods.pauseExecution(8000);
				String OnSavePrsText = orAirPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
				String[] OnClosePrsSplit = OnSavePrsText.split(":");
				String PrsCloseText = OnClosePrsSplit[1].trim();
				String PrsId = OnClosePrsSplit[2];
				System.out.println("PrsCloseText::" + PrsCloseText);
				//System.out.println("PrsCloseText::" + PrsCloseText);
				//AppReusableMethods.msgBox("PRS CLOSED SUCCESFULLY");
				ArrayList<String> slno= ExcelUtils.getAllCellValuesOfColumn("Air_DRS_HAWB", "sn", ScenarioDetailsHashMap);
				ArrayList<String> slnoWRH= ExcelUtils.getAllCellValuesOfColumn("Air_WarehouseMainDtls", "sn", ScenarioDetailsHashMap);
				for (int RowNo = 0; RowNo < valuess.size(); RowNo++) 
				{
					ExcelUtils.setCellDataKF("Air_DRS_HAWB",Integer.parseInt(slno.get(RowNo)), "PRS_Id",PrsId, ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("Air_DRS_HAWB",Integer.parseInt(slno.get(RowNo)), "BookingId",valuess.get(RowNo), ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("Air_WarehouseMainDtls", Integer.parseInt(slnoWRH.get(RowNo)), "PRS_ID", PrsId, ScenarioDetailsHashMap);
				}
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictAirDRS_SearchList(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orAirPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
				//AppReusableMethods.msgBox("ASSERTED PRSCLOSE ID IN SEARCHLIST");
			}
		}
	}


	/**
	 * This method is to perform Ocean  PRS Search Functionality for Booking 
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains TestData Record Row number
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @Author By: Sangeeta	Mohanty
	 * 
	 */
	public static void pictAirPRS_SearchList_Booking(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo)

	{
		GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "SearchPrsShipmentMode", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "ShipmentMode", ScenarioDetailsHashMap));
		GenericMethods.pauseExecution(1000);
		GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "SearchPrsShipmentType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "LoadType", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null,orAirPRS.getElement(driver, "SearchPRSExportImportIndicator",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "ExportOrImport", ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "SearchPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PRS_Id", ScenarioDetailsHashMap), 10);
		GenericMethods.pauseExecution(2000);
		String PRSCloseReferenceType = ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "PRSCloseReferenceType", ScenarioDetailsHashMap);
		if(PRSCloseReferenceType.equalsIgnoreCase("Booking id"))
		{
			System.out.println("Booking id");
			GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "SearchPrsReferenceID",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_Booking", rowNo, "BookingId",ScenarioDetailsHashMap), 10);

		}
		else if(PRSCloseReferenceType.equalsIgnoreCase("House reference id"))
		{
			System.out.println("House reference id");
			GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "SearchPrsReferenceID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "ShipmentReferenceNo",ScenarioDetailsHashMap), 10);
			//ExcelUtils.setCellData("Air_PRS_Booking", rowNo, "HBL_ShipmentReferenceNo", ExcelUtils.getAllCellValuesOfColumn("Air_HAWBMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap)+"", ScenarioDetailsHashMap);
		}
		else
		{
			System.out.println("master refernce id");
			GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "SearchPrsReferenceID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_MAWB", rowNo, "MAWBJobFileNo",ScenarioDetailsHashMap), 10);
			//ExcelUtils.setCellData("Air_PRS_Booking", rowNo, "Master_ShipmentReferenceId", ExcelUtils.getAllCellValuesOfColumn("Air_MAWB","MAWBJobFileNo", ScenarioDetailsHashMap)+"", ScenarioDetailsHashMap);
		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap,10), 20);
		GenericMethods.pauseExecution(3000);

	}


	/**
	 * This method is to perform Ocean  PRS Search Functionality after HAWB.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains TestData Record Row number
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @Author By: Sangeeta	Mohanty
	 * 
	 */
	public static void pictAirPRS_SearchList_HAWB(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo)

	{
		GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "SearchPrsShipmentMode", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "ShipmentMode", ScenarioDetailsHashMap));
		GenericMethods.pauseExecution(1000);
		GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "SearchPrsShipmentType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "LoadType", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null,orAirPRS.getElement(driver, "SearchPRSExportImportIndicator",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "ExportOrImport", ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "SearchPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PRS_Id", ScenarioDetailsHashMap), 10);
		GenericMethods.pauseExecution(2000);
		String PRSCloseReferenceType = ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "PRSCloseReferenceType", ScenarioDetailsHashMap);
		if(PRSCloseReferenceType.equalsIgnoreCase("Booking id"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "SearchPrsReferenceID",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_HAWB", rowNo, "BookingId",ScenarioDetailsHashMap), 10);

		}
		else if(PRSCloseReferenceType.equalsIgnoreCase("House reference id"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "SearchPrsReferenceID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "ShipmentReferenceNo",ScenarioDetailsHashMap), 10);
			//ExcelUtils.setCellData("Air_PRS_HAWB", rowNo, "HBL_ShipmentReferenceNo", ExcelUtils.getAllCellValuesOfColumn("Air_HAWBMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap)+"", ScenarioDetailsHashMap);
		}
		else
		{
			GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "SearchPrsReferenceID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_MAWB", rowNo, "MAWBJobFileNo",ScenarioDetailsHashMap), 10);
			//ExcelUtils.setCellData("Air_PRS_HAWB", rowNo, "Master_ShipmentReferenceId", ExcelUtils.getAllCellValuesOfColumn("Air_MAWB","MAWBJobFileNo", ScenarioDetailsHashMap)+"", ScenarioDetailsHashMap);
		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap,10), 20);
		GenericMethods.pauseExecution(3000);

	}


	/**
	 * This method is to perform Ocean  PRS Search Functionality after MAWB.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains TestData Record Row number
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @Author By: Sangeeta	Mohanty
	 * 
	 */
	public static void pictAirPRS_SearchList_MAWB(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo)

	{
		GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "SearchPrsShipmentMode", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "ShipmentMode", ScenarioDetailsHashMap));
		GenericMethods.pauseExecution(1000);
		GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "SearchPrsShipmentType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "LoadType", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null,orAirPRS.getElement(driver, "SearchPRSExportImportIndicator",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "ExportOrImport", ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "SearchPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PRS_Id", ScenarioDetailsHashMap), 10);
		GenericMethods.pauseExecution(2000);
		String PRSCloseReferenceType = ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "PRSCloseReferenceType", ScenarioDetailsHashMap);
		if(PRSCloseReferenceType.equalsIgnoreCase("Booking id"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "SearchPrsReferenceID",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_PRS_MAWB", rowNo, "BookingId",ScenarioDetailsHashMap), 10);

		}
		else if(PRSCloseReferenceType.equalsIgnoreCase("House reference id"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "SearchPrsReferenceID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "ShipmentReferenceNo",ScenarioDetailsHashMap), 10);
			//ExcelUtils.setCellData("Air_PRS_MAWB", rowNo, "HBL_ShipmentReferenceNo", ExcelUtils.getAllCellValuesOfColumn("Air_HAWBMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap)+"", ScenarioDetailsHashMap);
		}
		else
		{
			GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "SearchPrsReferenceID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_MAWB", rowNo, "MAWBJobFileNo",ScenarioDetailsHashMap), 10);
			//ExcelUtils.setCellData("Air_PRS_MAWB", rowNo, "Master_ShipmentReferenceId", ExcelUtils.getAllCellValuesOfColumn("Air_MAWB","MAWBJobFileNo", ScenarioDetailsHashMap)+"", ScenarioDetailsHashMap);
		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap,10), 20);
		GenericMethods.pauseExecution(3000);

	}


	/**
	 * This method is to perform Air  DRS Search Functionality in Import SCeanrio.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains TestData Record Row number
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @Author By: Sangeeta	Mohanty
	 * 
	 */
	public static void pictAirDRS_SearchList(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo)

	{
		GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "SearchPrsShipmentMode", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "ShipmentMode", ScenarioDetailsHashMap));
		GenericMethods.pauseExecution(1000);
		GenericMethods.selectOptionFromDropDown(driver, null, orAirPRS.getElement(driver, "SearchPrsShipmentType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "LoadType", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null,orAirPRS.getElement(driver, "SearchPRSExportImportIndicator",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "ExportOrImport", ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "SearchPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PRS_Id", ScenarioDetailsHashMap), 10);
		GenericMethods.pauseExecution(2000);
		String PRSCloseReferenceType = ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "PRSCloseReferenceType", ScenarioDetailsHashMap);
		if(PRSCloseReferenceType.equalsIgnoreCase("Booking id"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "SearchPrsReferenceID",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DRS_HAWB", rowNo, "BookingId",ScenarioDetailsHashMap), 10);

		}
		else if(PRSCloseReferenceType.equalsIgnoreCase("House reference id"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "SearchPrsReferenceID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo, "ShipmentReferenceNo",ScenarioDetailsHashMap), 10);
			//ExcelUtils.setCellData("Air_DRS_HAWB", rowNo, "HBL_ShipmentReferenceNo", ExcelUtils.getAllCellValuesOfColumn("Air_HAWBMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap)+"", ScenarioDetailsHashMap);
		}
		else
		{
			GenericMethods.replaceTextofTextField(driver, null, orAirPRS.getElement(driver, "SearchPrsReferenceID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("Air_MAWB", rowNo, "MAWBJobFileNo",ScenarioDetailsHashMap), 10);
			//ExcelUtils.setCellData("Air_DRS_HAWB", rowNo, "Master_ShipmentReferenceId", ExcelUtils.getAllCellValuesOfColumn("Air_MAWB","MAWBJobFileNo", ScenarioDetailsHashMap)+"", ScenarioDetailsHashMap);
		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap,10), 20);
		GenericMethods.pauseExecution(3000);

	}


}
