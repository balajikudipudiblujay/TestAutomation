package business.scenarios;

import global.reusables.ExcelUtils;
import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;

import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.ui.Select;

import DriverMethods.WebDriverInitilization;
import app.reuseables.AppReusableMethods;
import app.reuseables.ETransMenu;

public class OceanPRS {

	static ObjectRepository Common = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/CommonObjects.xml");
	static ObjectRepository orOceanPRS = new ObjectRepository(System.getProperty("user.dir") + "/ObjectRepository/OceanPRS.xml");
	static ObjectRepository orSeaBooking = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/SeaBooking.xml");





	/**
	 * This method is to perform Ocean PRS Add Functionality.
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
	public static void pictOceanPRS_Add_AfterBooking(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Add,"PRS ( Pick Up / Delivery )",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Ocean",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Truck", ScenarioDetailsHashMap,10), 10);
		String ShipmentMode = ExcelUtils.getCellData("SE_PRS_Booking",rowNo, "ShipmentMode", ScenarioDetailsHashMap);
		if (ShipmentMode.equalsIgnoreCase("Air")) 
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		} 
		else if (ShipmentMode.equalsIgnoreCase("Truck"))
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Truck",ScenarioDetailsHashMap, 10), 10);
			String FTLType = ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"FTL", ScenarioDetailsHashMap);
			if (FTLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "FTL", ScenarioDetailsHashMap,10), 10);
			} else 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "LTL",ScenarioDetailsHashMap, 10), 10);
			}
		} else 
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Ocean", ScenarioDetailsHashMap,10), 10);
			String OceanLCLType = ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"LCL", ScenarioDetailsHashMap);
			if (OceanLCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "LCL", ScenarioDetailsHashMap,10), 10);
			}
			String OceanFCLType = ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"FCL", ScenarioDetailsHashMap);
			if (OceanFCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "FCL", ScenarioDetailsHashMap,10), 10);
			} 
		}
		String ExportorImport = ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"ExportOrImport", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "XportOrImport",ScenarioDetailsHashMap, 10),ExportorImport);
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "TerminalRelease",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "TerminalReleaseStatus", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "shipmentRelease",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "ShipmentReleaseStatus", ScenarioDetailsHashMap));

		//Single PRS script
		String PRSType = ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PRSType", ScenarioDetailsHashMap);
		if(PRSType.equalsIgnoreCase("Single"))
		{
			System.out.println("PRS Type :"+PRSType);

			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", rowNo, "BookingId", ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
			boolean noRecordsMsg = orOceanPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
			String noRecords = orOceanPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
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

			if(GenericMethods.isFieldEnabled(driver, null, orOceanPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orOceanPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 10);
				//AppReusableMethods.msgBox("PRS CREATED SUCCESFULLY");
				String OnSavePrsText = GenericMethods.getInnerText(driver,null,orOceanPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 10),10);
				String[] PrsTextSplit = OnSavePrsText.split(":");
				String PrsText = PrsTextSplit[1].trim();
				String PrsId = PrsTextSplit[2];
				ExcelUtils.setCellData("SE_PRS_Booking", rowNo, "PRS_Id", PrsId, ScenarioDetailsHashMap);
				ExcelUtils.setCellData("SE_PRS_Booking", rowNo, "BookingId", ExcelUtils.getCellData("SE_BookingMainDetails", rowNo, "BookingId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(PrsText, ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PrsAddMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictOceanPRS_SearchList_Booking(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orOceanPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PRS_Id",ScenarioDetailsHashMap), "PRS Id","Validating PRS ID", ScenarioDetailsHashMap);
			}

		}
		else
		{
			System.out.println("PRS Type :"+PRSType);
			ArrayList<String> valuess = new ArrayList<String>();
			String PRSReferenceType = ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PRSAddReferenceType", ScenarioDetailsHashMap);
			if(PRSReferenceType.equalsIgnoreCase("Booking id"))
			{
				System.out.println("Booking id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
			}
			else if(PRSReferenceType.equalsIgnoreCase("House reference id")) 
			{
				System.out.println("House reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_HBLMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap);
			}
			else
			{
				System.out.println("Master reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_OBL","Shipment_ReferenceId", ScenarioDetailsHashMap);
			}
			System.out.println("valuess "+valuess);
			System.out.println("...valuess.size()"+valuess.size());	
			for (int i = 0; i < valuess.size(); i++) 
			{
				System.out.println("valuess.get("+i+") :"+valuess.get(i));
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), valuess.get(i), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(10);
				//System.out.println("noRecordsMsg::"+ orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap, 10).isDisplayed());
				boolean noRecordsMsg = orOceanPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
				String noRecords = orOceanPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
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
			if(GenericMethods.isFieldEnabled(driver, null, orOceanPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10))
			{
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orOceanPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 10);
				//AppReusableMethods.msgBox("PRS CREATED SUCCESFULLY");
				String OnSavePrsText = GenericMethods.getInnerText(driver,null,orOceanPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 10),10);
				String[] PrsTextSplit = OnSavePrsText.split(":");
				String PrsText = PrsTextSplit[1].trim();
				String PrsId = PrsTextSplit[2];
				System.out.println("PrsText::::" + PrsText + "PrsId::::"+ PrsId);
				ArrayList<String> slno= ExcelUtils.getAllCellValuesOfColumn("SE_PRS_Booking", "sn", ScenarioDetailsHashMap);
				System.out.println("slno"+slno);
				for (int RowNo = 0; RowNo < valuess.size(); RowNo++) 
				{

					System.out.println("Integer.parseInt(slno.get("+RowNo+")) :"+Integer.parseInt(slno.get(RowNo)));
					//System.out.println("Integer.parseInt(slnoWRH(RowNo)) :"+Integer.parseInt(slnoWRH.get(RowNo)));
					ExcelUtils.setCellDataKF("SE_PRS_Booking",Integer.parseInt(slno.get(RowNo)), "PRS_Id",PrsId, ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("SE_PRS_Booking",Integer.parseInt(slno.get(RowNo)), "BookingId",valuess.get(RowNo), ScenarioDetailsHashMap);
				}
				GenericMethods.assertTwoValues(PrsText, ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PrsAddMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictOceanPRS_SearchList_Booking(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orOceanPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PRS_Id",ScenarioDetailsHashMap), "PRS Id","Validating PRS ID", ScenarioDetailsHashMap);
			}
		}
	}


	/**
	 * This method is to perform Ocean PRS Add Functionality after creating HBL.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains Records Row number which was given in
	 *            Testdata
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @throws InterruptedException 
	 * @Author By: Pavan Bikumandla
	 * @Modified By: Sangeeta Mohanty
	 */
	public static void pictOceanPRS_Add_AfterHBL(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Add,"PRS ( Pick Up / Delivery )",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Ocean",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Truck", ScenarioDetailsHashMap,10), 10);
		String ShipmentMode = ExcelUtils.getCellData("SE_PRS_HBL",rowNo, "ShipmentMode", ScenarioDetailsHashMap);
		if (ShipmentMode.equalsIgnoreCase("Air")) 
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		} 
		else if (ShipmentMode.equalsIgnoreCase("Truck"))
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Truck",ScenarioDetailsHashMap, 10), 10);
			String FTLType = ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"FTL", ScenarioDetailsHashMap);
			if (FTLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "FTL", ScenarioDetailsHashMap,10), 10);
			} else 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "LTL",ScenarioDetailsHashMap, 10), 10);
			}
		} else 
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Ocean", ScenarioDetailsHashMap,10), 10);
			String OceanLCLType = ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"LCL", ScenarioDetailsHashMap);
			if (OceanLCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "LCL", ScenarioDetailsHashMap,10), 10);
			}
			String OceanFCLType = ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"FCL", ScenarioDetailsHashMap);
			if (OceanFCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "FCL", ScenarioDetailsHashMap,10), 10);
			} 
		}
		String ExportorImport = ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"ExportOrImport", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "XportOrImport",ScenarioDetailsHashMap, 10),ExportorImport);
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "TerminalRelease",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "TerminalReleaseStatus", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "shipmentRelease",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "ShipmentReleaseStatus", ScenarioDetailsHashMap));

		//Single PRS script
		String PRSType = ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PRSType", ScenarioDetailsHashMap);
		if(PRSType.equalsIgnoreCase("Single"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "ShipmentReferenceNo", ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
			boolean noRecordsMsg = orOceanPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
			String noRecords = orOceanPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
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

			if(GenericMethods.isFieldEnabled(driver, null, orOceanPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orOceanPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 10);
				String OnSavePrsText = GenericMethods.getInnerText(driver,null,orOceanPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 10),10);
				String[] PrsTextSplit = OnSavePrsText.split(":");
				String PrsText = PrsTextSplit[1].trim();
				String PrsId = PrsTextSplit[2];
				ExcelUtils.setCellData("SE_PRS_HBL", rowNo, "PRS_Id", PrsId, ScenarioDetailsHashMap);
				ExcelUtils.setCellData("SE_PRS_HBL", rowNo, "BookingId", ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "ShipmentReferenceNo", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(PrsText, ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PrsAddMsg",ScenarioDetailsHashMap), "Validating PRS ADD",ScenarioDetailsHashMap);
				pictOceanPRS_SearchList_HBL(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orOceanPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PRS_Id",ScenarioDetailsHashMap), "PRS Id","Validating PRS ID", ScenarioDetailsHashMap);
				if(ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "AuditTrailRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes")){
			           
			           try {
						PRSHBL_AuditTrail(driver,ScenarioDetailsHashMap,rowNo);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			           
			          }
			}


		}
		else
		{

			ArrayList<String> valuess = new ArrayList<String>();
			String PRSReferenceType = ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PRSAddReferenceType", ScenarioDetailsHashMap);
			if(PRSReferenceType.equalsIgnoreCase("Booking id"))
			{
				System.out.println("Booking id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
			}
			else if(PRSReferenceType.equalsIgnoreCase("House reference id")) 
			{
				System.out.println("House reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_HBLMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap);
			}
			else
			{
				System.out.println("Master reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_OBL","Shipment_ReferenceId", ScenarioDetailsHashMap);
			}
			System.out.println("valuess "+valuess);
			System.out.println("...valuess.size()"+valuess.size());	
			for (int i = 0; i < valuess.size(); i++) {
				System.out.println("valuess.get("+i+") :"+valuess.get(i));
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), valuess.get(i), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(10);
				//System.out.println("noRecordsMsg::"+ orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap, 10).isDisplayed());
				boolean noRecordsMsg = orOceanPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
				String noRecords = orOceanPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
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
			if(GenericMethods.isFieldEnabled(driver, null, orOceanPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orOceanPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 10);
				//AppReusableMethods.msgBox("PRS CREATED SUCCESFULLY");
				String OnSavePrsText = GenericMethods.getInnerText(driver,null,orOceanPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 10),10);
				String[] PrsTextSplit = OnSavePrsText.split(":");
				String PrsText = PrsTextSplit[1].trim();
				String PrsId = PrsTextSplit[2];
				System.out.println("PrsText::::" + PrsText + "PrsId::::"+ PrsId);
				System.out.println("valuess.get(RowNo) :"+valuess.get(0));
				ArrayList<String> slno= ExcelUtils.getAllCellValuesOfColumn("SE_PRS_HBL", "sn", ScenarioDetailsHashMap);
				for (int RowNo = 0; RowNo < valuess.size(); RowNo++) 
				{
					System.out.println("slno.get(RowNo)"+slno.get(RowNo));
					ExcelUtils.setCellDataKF("SE_PRS_HBL",Integer.parseInt(slno.get(RowNo)), "PRS_Id",PrsId, ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("SE_PRS_HBL",Integer.parseInt(slno.get(RowNo)), "BookingId",valuess.get(RowNo), ScenarioDetailsHashMap);
				}
				GenericMethods.assertTwoValues(PrsText, ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PrsAddMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictOceanPRS_SearchList_HBL(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orOceanPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PRS_Id",ScenarioDetailsHashMap), "PRS Id","Validating PRS ID", ScenarioDetailsHashMap);
				if(ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "AuditTrailRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes")){
			           
			           try {
						PRSHBL_AuditTrail(driver,ScenarioDetailsHashMap,rowNo);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			           
			          }
				//AppReusableMethods.msgBox("ASSERTED PRSID IN SEARCHLIST");

			}
		}
	}


	public static void PRSHBL_AuditTrail(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo) throws InterruptedException
	{
		ObjectRepository orAuditTrail = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/AuditTrail.xml");

		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_MainDetails", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(3000);
		GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "HandelingInstructions", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_HBL", RowNo,"HandlingInstructionsModified", ScenarioDetailsHashMap), 2);			
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			System.out.println("no Alerts present");
		}
		GenericMethods.pauseExecution(3000);
		String PRSIdSummary=GenericMethods.getInnerText(driver, null, orSeaBooking.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 20), 2);
		String PRSID=PRSIdSummary.split(":")[2].trim();
		String modifyMes=PRSIdSummary.split(":")[1].trim();
		GenericMethods.assertTwoValues(PRSID, ExcelUtils.getCellData("SE_PRS_HBL", RowNo, "PRS_Id", ScenarioDetailsHashMap), "Validating PRS Id", ScenarioDetailsHashMap);
		/*GenericMethods.assertTwoValues(modifyMes, ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ModifyMessage", ScenarioDetailsHashMap), "Validating Booking Modify Message", ScenarioDetailsHashMap);
		System.out.println("****"+modifyMes+"*****");
		System.out.println("****"+ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ModifyMessage", ScenarioDetailsHashMap)+"*****");
		 */
		pictOceanPRS_SearchList_HBL(driver, ScenarioDetailsHashMap, RowNo);

		GenericMethods.pauseExecution(4000);


		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AuditButton",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		String title=GenericMethods.getInnerText(driver, null, Common.getElement(driver, "BannerTitle", ScenarioDetailsHashMap,10), 2);
		GenericMethods.assertTwoValues(title,"Audit Trail", "Validating Page title for Audit Trail", ScenarioDetailsHashMap);
		String User_grid=GenericMethods.getInnerText(driver, null, orAuditTrail.getElement(driver, "UserName", ScenarioDetailsHashMap,10), 2);
		String UserName=GenericMethods.getPropertyValue("userName", WebDriverInitilization.configurationStructurePath);
		GenericMethods.assertTwoValues(User_grid,UserName, "Validating UserName in Audit Trail grid", ScenarioDetailsHashMap);
		String actionID=GenericMethods.getInnerText(driver, null, orAuditTrail.getElement(driver, "ActionID", ScenarioDetailsHashMap,10), 2);
		GenericMethods.clickElement(driver, null,orAuditTrail.getElement(driver, "AuditFieldChanges_Tab",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		String Handling=ExcelUtils.getCellData("SE_PRS_HBL", RowNo, "HandlingInstructionsField", ScenarioDetailsHashMap);

		Actions builder=new Actions(driver);
		int i=1;
		while(i>0){
			if(actionID.equalsIgnoreCase(driver.findElement(By.id("dtAuditMasterDtlId"+i)).getText())){

				if(driver.findElement(By.id("dtFieldName"+i)).getText().equalsIgnoreCase(Handling)){
					String Original_Grid=driver.findElement(By.id("dtOriginalValue"+i)).getText();
					GenericMethods.highlightElement(driver.findElement(By.id("dtOriginalValue"+i)), driver);
					String Modified_Grid=driver.findElement(By.id("dtModifiedValue"+i)).getText();
					GenericMethods.highlightElement(driver.findElement(By.id("dtModifiedValue"+i)), driver);
					String Modified=ExcelUtils.getCellData("SE_PRS_HBL", RowNo,"HandlingInstructionsModified", ScenarioDetailsHashMap);
					String Original=ExcelUtils.getCellData("SE_PRS_HBL", RowNo,"HandlingInstructions", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(Original_Grid,Original, "Validating Handling Instruction Original Value in Audit Trail grid", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(Modified_Grid,Modified, "Validating Handling Instruction Modified Value in Audit Trail grid", ScenarioDetailsHashMap);
					System.out.println("Handling Instruction ");
				}	
			}
			i=i+1;
			if (driver.findElements(By.id("dtAuditMasterDtlId"+i)).size()>0)
				builder.moveToElement(driver.findElement(By.id("dtAuditMasterDtlId"+i))).build().perform();
			else
				i=0;
		}

	}




	/**
	 * This method is to perform Ocean PRS Add Functionality after creating  Master/OBL.
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
	public static void pictOceanPRS_Add_AfterOBL(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Add,"PRS ( Pick Up / Delivery )",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Ocean",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Truck", ScenarioDetailsHashMap,10), 10);
		String ShipmentMode = ExcelUtils.getCellData("SE_PRS_OBL",rowNo, "ShipmentMode", ScenarioDetailsHashMap);
		if (ShipmentMode.equalsIgnoreCase("Air")) 
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		} 
		else if (ShipmentMode.equalsIgnoreCase("Truck"))
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Truck",ScenarioDetailsHashMap, 10), 10);
			String FTLType = ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"FTL", ScenarioDetailsHashMap);
			if (FTLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "FTL", ScenarioDetailsHashMap,10), 10);
			} else 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "LTL",ScenarioDetailsHashMap, 10), 10);
			}
		} else 
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Ocean", ScenarioDetailsHashMap,10), 10);
			String OceanLCLType = ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"LCL", ScenarioDetailsHashMap);
			if (OceanLCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "LCL", ScenarioDetailsHashMap,10), 10);
			}
			String OceanFCLType = ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"FCL", ScenarioDetailsHashMap);
			if (OceanFCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "FCL", ScenarioDetailsHashMap,10), 10);
			} 
		}
		String ExportorImport = ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"ExportOrImport", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "XportOrImport",ScenarioDetailsHashMap, 10),ExportorImport);
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "TerminalRelease",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "TerminalReleaseStatus", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "shipmentRelease",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "ShipmentReleaseStatus", ScenarioDetailsHashMap));

		//Single PRS script
		String PRSType = ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PRSType", ScenarioDetailsHashMap);
		if(PRSType.equalsIgnoreCase("Single"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL", rowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
			boolean noRecordsMsg = orOceanPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
			String noRecords = orOceanPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
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

			if(GenericMethods.isFieldEnabled(driver, null, orOceanPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orOceanPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 10);
				//AppReusableMethods.msgBox("PRS CREATED SUCCESFULLY");
				String OnSavePrsText = GenericMethods.getInnerText(driver,null,orOceanPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 10),10);
				String[] PrsTextSplit = OnSavePrsText.split(":");
				String PrsText = PrsTextSplit[1].trim();
				String PrsId = PrsTextSplit[2];
				ExcelUtils.setCellData("SE_PRS_OBL", rowNo, "PRS_Id", PrsId, ScenarioDetailsHashMap);
				ExcelUtils.setCellData("SE_PRS_OBL", rowNo, "BookingId", ExcelUtils.getCellData("SE_OBL", rowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(PrsText, ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PrsAddMsg",ScenarioDetailsHashMap), "Validating PRS ADD",ScenarioDetailsHashMap);
				pictOceanPRS_SearchList_OBL(driver, ScenarioDetailsHashMap, rowNo);

				GenericMethods.assertInnerText(driver, null, orOceanPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PRS_Id",ScenarioDetailsHashMap), "PRS Id","Validating PRS ID", ScenarioDetailsHashMap);
			}

		}
		else
		{

			ArrayList<String> valuess = new ArrayList<String>();
			String PRSReferenceType = ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PRSAddReferenceType", ScenarioDetailsHashMap);
			if(PRSReferenceType.equalsIgnoreCase("Booking id"))
			{
				System.out.println("Booking id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
			}
			else if(PRSReferenceType.equalsIgnoreCase("House reference id")) 
			{
				System.out.println("House reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_HBLMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap);
			}
			else
			{
				System.out.println("Master reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_OBL","Shipment_ReferenceId", ScenarioDetailsHashMap);
			}
			System.out.println("valuess "+valuess);
			System.out.println("...valuess.size()"+valuess.size());	
			for (int i = 0; i < valuess.size(); i++) {
				System.out.println("valuess.get("+i+") :"+valuess.get(i));
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), valuess.get(i), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(10);
				//System.out.println("noRecordsMsg::"+ orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap, 10).isDisplayed());
				boolean noRecordsMsg = orOceanPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
				String noRecords = orOceanPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
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
			if(GenericMethods.isFieldEnabled(driver, null, orOceanPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orOceanPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 10);
				//AppReusableMethods.msgBox("PRS CREATED SUCCESFULLY");
				String OnSavePrsText = GenericMethods.getInnerText(driver,null,orOceanPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 10),10);
				String[] PrsTextSplit = OnSavePrsText.split(":");
				String PrsText = PrsTextSplit[1].trim();
				String PrsId = PrsTextSplit[2];
				//System.out.println("PrsText::::" + PrsText + "PrsId::::"+ PrsId);
				ArrayList<String> slno= ExcelUtils.getAllCellValuesOfColumn("SE_PRS_OBL", "sn", ScenarioDetailsHashMap);
				for (int RowNo = 0; RowNo < valuess.size(); RowNo++) 
				{
					ExcelUtils.setCellDataKF("SE_PRS_OBL",Integer.parseInt(slno.get(RowNo)), "PRS_Id",PrsId, ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("SE_PRS_OBL",Integer.parseInt(slno.get(RowNo)), "BookingId",valuess.get(RowNo), ScenarioDetailsHashMap);
				}
				GenericMethods.assertTwoValues(PrsText, ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PrsAddMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictOceanPRS_SearchList_OBL(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orOceanPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PRS_Id",ScenarioDetailsHashMap), "PRS Id","Validating PRS ID", ScenarioDetailsHashMap);
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
	public static void pictOceanDRS_Add(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Add,"PRS ( Pick Up / Delivery )",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Ocean",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Truck", ScenarioDetailsHashMap,10), 10);
		String ShipmentMode = ExcelUtils.getCellData("SE_DRS_HBL",rowNo, "ShipmentMode", ScenarioDetailsHashMap);
		if (ShipmentMode.equalsIgnoreCase("Air")) 
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		} 
		else if (ShipmentMode.equalsIgnoreCase("Truck"))
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Truck",ScenarioDetailsHashMap, 10), 10);
			String FTLType = ExcelUtils.getCellData("SE_DRS_HBL", rowNo,"FTL", ScenarioDetailsHashMap);
			if (FTLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "FTL", ScenarioDetailsHashMap,10), 10);
			} else 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "LTL",ScenarioDetailsHashMap, 10), 10);
			}
		} else 
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Ocean", ScenarioDetailsHashMap,10), 10);
			String OceanLCLType = ExcelUtils.getCellData("SE_DRS_HBL", rowNo,"LCL", ScenarioDetailsHashMap);
			if (OceanLCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "LCL", ScenarioDetailsHashMap,10), 10);
			}
			String OceanFCLType = ExcelUtils.getCellData("SE_DRS_HBL", rowNo,"FCL", ScenarioDetailsHashMap);
			if (OceanFCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "FCL", ScenarioDetailsHashMap,10), 10);
			} 
		}
		String ExportorImport = ExcelUtils.getCellData("SE_DRS_HBL", rowNo,"ExportOrImport", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "XportOrImport",ScenarioDetailsHashMap, 10),ExportorImport);
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "TerminalRelease",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "TerminalReleaseStatus", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "shipmentRelease",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "ShipmentReleaseStatus", ScenarioDetailsHashMap));

		//Single PRS script
		String PRSType = ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PRSType", ScenarioDetailsHashMap);
		if(PRSType.equalsIgnoreCase("Single"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "ShipmentReferenceNo", ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
			boolean noRecordsMsg = orOceanPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
			String noRecords = orOceanPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
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

			if(GenericMethods.isFieldEnabled(driver, null, orOceanPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orOceanPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_DRS_HBL", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 10);
				//AppReusableMethods.msgBox("PRS CREATED SUCCESFULLY");
				String OnSavePrsText = GenericMethods.getInnerText(driver,null,orOceanPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 10),10);
				String[] PrsTextSplit = OnSavePrsText.split(":");
				String PrsText = PrsTextSplit[1].trim();
				String PrsId = PrsTextSplit[2];
				ExcelUtils.setCellData("SE_DRS_HBL", rowNo, "PRS_Id", PrsId, ScenarioDetailsHashMap);
				ExcelUtils.setCellData("SE_DRS_HBL", rowNo, "BookingId", ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "ShipmentReferenceNo", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(PrsText, ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PrsAddMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictOceanDRS_SearchList(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orOceanPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PRS_Id",ScenarioDetailsHashMap), "PRS Id","Validating PRS ID", ScenarioDetailsHashMap);
			}

		}
		else
		{
			ArrayList<String> valuess = new ArrayList<String>();
			String PRSReferenceType = ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PRSAddReferenceType", ScenarioDetailsHashMap);
			if(PRSReferenceType.equalsIgnoreCase("Booking id"))
			{
				System.out.println("Booking id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
			}
			else if(PRSReferenceType.equalsIgnoreCase("House reference id")) 
			{
				System.out.println("House reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_HBLMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap);
			}
			else
			{
				System.out.println("Master reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_OBL","Shipment_ReferenceId", ScenarioDetailsHashMap);
			}
			System.out.println("valuess "+valuess);
			System.out.println("...valuess.size()"+valuess.size());	
			for (int i = 0; i < valuess.size(); i++) {
				System.out.println("valuess.get("+i+") :"+valuess.get(i));
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), valuess.get(i), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(10);
				//System.out.println("noRecordsMsg::"+ orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap, 10).isDisplayed());
				boolean noRecordsMsg = orOceanPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
				String noRecords = orOceanPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
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
			if(GenericMethods.isFieldEnabled(driver, null, orOceanPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orOceanPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_DRS_HBL", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 10);
				//AppReusableMethods.msgBox("PRS CREATED SUCCESFULLY");
				String OnSavePrsText = GenericMethods.getInnerText(driver,null,orOceanPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap, 10),10);
				String[] PrsTextSplit = OnSavePrsText.split(":");
				String PrsText = PrsTextSplit[1].trim();
				String PrsId = PrsTextSplit[2];
				//System.out.println("PrsText::::" + PrsText + "PrsId::::"+ PrsId);
				ArrayList<String> slno= ExcelUtils.getAllCellValuesOfColumn("SE_DRS_HBL", "sn", ScenarioDetailsHashMap);
				for (int RowNo = 0; RowNo < valuess.size(); RowNo++) 
				{
					ExcelUtils.setCellDataKF("SE_DRS_HBL",Integer.parseInt(slno.get(RowNo)), "PRS_Id",PrsId, ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("SE_DRS_HBL",Integer.parseInt(slno.get(RowNo)), "BookingId",valuess.get(RowNo), ScenarioDetailsHashMap);
				}
				GenericMethods.assertTwoValues(PrsText, ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PrsAddMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictOceanDRS_SearchList(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orOceanPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PRS_Id",ScenarioDetailsHashMap), "PRS Id","Validating PRS ID", ScenarioDetailsHashMap);
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
	public static void pictOceanPRS_Close_AfterBooking(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Close,	"PRS ( Pick Up / Delivery ) Close",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "PrsIdLOV",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.selectWindow(driver);
		pictOceanPRS_SearchList_Booking(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.switchToParent(driver); 
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(3000);
		int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
		//System.out.println("rowCount::::" + rowCount);
		for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
			String PrsStatus = ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"Prs_Status", ScenarioDetailsHashMap);
			if (PrsStatus.equalsIgnoreCase("Partial")) {
				//System.out.println("Inside partial");
				int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PacksReceived",ScenarioDetailsHashMap));
				ReceivedGoods = ReceivedGoods / 2;
				String ReceivedPacks = String.valueOf(ReceivedGoods);
				GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
			}
			else
			{
				//System.out.println("Inside full");
				GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
			}
			GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
			GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "Remarks",ScenarioDetailsHashMap), 10);

		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.pauseExecution(8000);
		String OnSavePrsText = orOceanPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
		String[] OnClosePrsSplit = OnSavePrsText.split(":");
		String PrsCloseText = OnClosePrsSplit[1].trim();
		String PrsId = OnClosePrsSplit[2];
		//System.out.println("PrsCloseText::" + PrsCloseText);
		//AppReusableMethods.msgBox("PRS CLOSED SUCCESFULLY");
		GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
		pictOceanPRS_SearchList_Booking(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.assertInnerText(driver, null, orOceanPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
		//AppReusableMethods.msgBox("ASSERTED PRSCLOSE ID IN SEARCHLIST");
	}



	/**
	 * This method is to perform Ocean PRS Close Functionality After HBL.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains TestData Record Row number
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @throws InterruptedException 
	 * @Author By: Pavan Bikumandla
	 * @Modified By: Sangeeta M
	 */
	public static void pictOceanPRS_Close_AfterHBL(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Close,	"PRS ( Pick Up / Delivery ) Close",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "PrsIdLOV",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.selectWindow(driver);
		pictOceanPRS_SearchList_HBL(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(3000);
		int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
		//System.out.println("rowCount::::" + rowCount);
		for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
			String PrsStatus = ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"Prs_Status", ScenarioDetailsHashMap);
			if (PrsStatus.equalsIgnoreCase("Partial")) {
				//System.out.println("Inside partial");
				int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PacksReceived",ScenarioDetailsHashMap));
				ReceivedGoods = ReceivedGoods / 2;
				String ReceivedPacks = String.valueOf(ReceivedGoods);
				GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
			}
			else
			{
				//System.out.println("Inside full");
				GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
			}
			GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
			GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "Remarks",ScenarioDetailsHashMap), 10);

		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.pauseExecution(8000);
		String OnSavePrsText = orOceanPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
		String[] OnClosePrsSplit = OnSavePrsText.split(":");
		String PrsCloseText = OnClosePrsSplit[1].trim();
		String PrsId = OnClosePrsSplit[2];
		//System.out.println("PrsCloseText::" + PrsCloseText);
		//AppReusableMethods.msgBox("PRS CLOSED SUCCESFULLY");
		GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
		pictOceanPRS_SearchList_HBL(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.assertInnerText(driver, null, orOceanPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
		//AppReusableMethods.msgBox("ASSERTED PRSCLOSE ID IN SEARCHLIST");
	}



	/**
	 * This method is to perform Ocean PRS Close Functionality After OBL.
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
	public static void pictOceanPRS_Close_AfterOBL(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Close,	"PRS ( Pick Up / Delivery ) Close",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "PrsIdLOV",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.selectWindow(driver);
		pictOceanPRS_SearchList_OBL(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(3000);
		int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
		//System.out.println("rowCount::::" + rowCount);
		for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
			String PrsStatus = ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"Prs_Status", ScenarioDetailsHashMap);
			if (PrsStatus.equalsIgnoreCase("Partial")) {
				//System.out.println("Inside partial");
				int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PacksReceived",ScenarioDetailsHashMap));
				ReceivedGoods = ReceivedGoods / 2;
				String ReceivedPacks = String.valueOf(ReceivedGoods);
				GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
			}
			else
			{
				//System.out.println("Inside full");
				GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
			}
			GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
			GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "Remarks",ScenarioDetailsHashMap), 10);

		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.pauseExecution(8000);
		String OnSavePrsText = orOceanPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
		String[] OnClosePrsSplit = OnSavePrsText.split(":");
		String PrsCloseText = OnClosePrsSplit[1].trim();
		String PrsId = OnClosePrsSplit[2];
		//System.out.println("PrsCloseText::" + PrsCloseText);
		//AppReusableMethods.msgBox("PRS CLOSED SUCCESFULLY");
		GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
		pictOceanPRS_SearchList_OBL(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.assertInnerText(driver, null, orOceanPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
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
	public static void pictOceanDRS_Close(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Close,	"PRS ( Pick Up / Delivery ) Close",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "PrsIdLOV",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.selectWindow(driver);
		pictOceanDRS_SearchList(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(3000);
		int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
		//System.out.println("rowCount::::" + rowCount);
		for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
			String PrsStatus = ExcelUtils.getCellData("SE_DRS_HBL", rowNo,"Prs_Status", ScenarioDetailsHashMap);
			if (PrsStatus.equalsIgnoreCase("Partial")) {
				//System.out.println("Inside partial");
				int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PacksReceived",ScenarioDetailsHashMap));
				ReceivedGoods = ReceivedGoods / 2;
				String ReceivedPacks = String.valueOf(ReceivedGoods);
				GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
			}
			else
			{
				//System.out.println("Inside full");
				GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("SE_DRS_HBL", grid_RowId,"PacksReceived", ScenarioDetailsHashMap), 10);
			}
			GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("SE_DRS_HBL", grid_RowId,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
			GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("SE_DRS_HBL", grid_RowId, "Remarks",ScenarioDetailsHashMap), 10);

		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
		GenericMethods.pauseExecution(8000);
		String OnSavePrsText = orOceanPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
		String[] OnClosePrsSplit = OnSavePrsText.split(":");
		String PrsCloseText = OnClosePrsSplit[1].trim();
		String PrsId = OnClosePrsSplit[2];
		//System.out.println("PrsCloseText::" + PrsCloseText);
		//AppReusableMethods.msgBox("PRS CLOSED SUCCESFULLY");
		GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
		pictOceanDRS_SearchList(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.assertInnerText(driver, null, orOceanPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
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
	public static void pictOceanPRS_QuickLink_AfterBooking(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		ExcelUtils.getAllCellValuesOfColumn("SE_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
		//System.out.println("in ocean prs add");
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Add,"PRS ( Pick Up / Delivery )",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Ocean",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Truck", ScenarioDetailsHashMap,10), 10);
		String ShipmentMode = ExcelUtils.getCellData("SE_PRS_Booking",rowNo, "ShipmentMode", ScenarioDetailsHashMap);
		if (ShipmentMode.equalsIgnoreCase("Air")) 
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		} 
		else if (ShipmentMode.equalsIgnoreCase("Truck"))
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Truck",ScenarioDetailsHashMap, 10), 10);
			String FTLType = ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"FTL", ScenarioDetailsHashMap);
			if (FTLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "FTL", ScenarioDetailsHashMap,10), 10);
			} else 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "LTL",ScenarioDetailsHashMap, 10), 10);
			}
		} else 
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Ocean", ScenarioDetailsHashMap,10), 10);
			String OceanLCLType = ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"LCL", ScenarioDetailsHashMap);
			if (OceanLCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "LCL", ScenarioDetailsHashMap,10), 10);
			}
			String OceanFCLType = ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"FCL", ScenarioDetailsHashMap);
			if (OceanFCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "FCL", ScenarioDetailsHashMap,10), 10);
			} 

		}
		String ExportorImport = ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"ExportOrImport", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "XportOrImport",ScenarioDetailsHashMap, 10),ExportorImport);
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "TerminalRelease",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "TerminalReleaseStatus", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "shipmentRelease",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "ShipmentReleaseStatus", ScenarioDetailsHashMap));

		//Single PRS script
		String PRSType = ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PRSType", ScenarioDetailsHashMap);
		if(PRSType.equalsIgnoreCase("Single"))
		{
			System.out.println("PRS Type :"+PRSType);
			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", rowNo, "BookingId", ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
			boolean noRecordsMsg = orOceanPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
			String noRecords = orOceanPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
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

			if(GenericMethods.isFieldEnabled(driver, null, orOceanPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orOceanPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSNavigationList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PRSNavigationList", ScenarioDetailsHashMap));
				GenericMethods.clickElement(driver, null, orOceanPRS.getElement(driver, "PRSNavigationListImg", ScenarioDetailsHashMap, 10), 2)	;
				GenericMethods.pauseExecution(6000);

				//Moving to PRS Close page
				//GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(3000);
				int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
				//System.out.println("rowCount::::" + rowCount);
				for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
					String PrsStatus = ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"Prs_Status", ScenarioDetailsHashMap);
					if (PrsStatus.equalsIgnoreCase("Partial")) {
						//System.out.println("Inside partial");
						int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PacksReceived",ScenarioDetailsHashMap));
						ReceivedGoods = ReceivedGoods / 2;
						String ReceivedPacks = String.valueOf(ReceivedGoods);
						GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
					}
					else
					{
						//System.out.println("Inside full");
						GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
					}
					GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
					GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "Remarks",ScenarioDetailsHashMap), 10);
				}
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
				GenericMethods.pauseExecution(8000);
				String OnSavePrsText = orOceanPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
				String[] OnClosePrsSplit = OnSavePrsText.split(":");
				String PrsCloseText = OnClosePrsSplit[1].trim();
				String PrsId = OnClosePrsSplit[2];
				ExcelUtils.setCellData("SE_PRS_Booking", rowNo, "PRS_Id", PrsId, ScenarioDetailsHashMap);
				ExcelUtils.setCellData("SE_PRS_Booking", rowNo, "BookingId", ExcelUtils.getCellData("SE_BookingMainDetails", rowNo, "BookingId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);

				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictOceanPRS_SearchList_Booking(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orOceanPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
			}

		}
		else
		{
			ArrayList<String> valuess = new ArrayList<String>();
			String PRSReferenceType = ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PRSAddReferenceType", ScenarioDetailsHashMap);
			if(PRSReferenceType.equalsIgnoreCase("Booking id"))
			{
				System.out.println("Booking id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
			}
			else if(PRSReferenceType.equalsIgnoreCase("House reference id")) 
			{
				System.out.println("House reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_HBLMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap);
			}
			else
			{
				System.out.println("Master reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_OBL","Shipment_ReferenceId", ScenarioDetailsHashMap);
			}

			System.out.println("...valuess.size()"+valuess.size());	
			for (int i = 0; i < valuess.size(); i++) {
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), valuess.get(i), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(10);
				//System.out.println("noRecordsMsg::"+ orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap, 10).isDisplayed());
				boolean noRecordsMsg = orOceanPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
				String noRecords = orOceanPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
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

			if(GenericMethods.isFieldEnabled(driver, null, orOceanPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(3000);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null,orOceanPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSNavigationList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PRSNavigationList", ScenarioDetailsHashMap));
				GenericMethods.clickElement(driver, null, orOceanPRS.getElement(driver, "PRSNavigationListImg", ScenarioDetailsHashMap, 10), 2)	;
				GenericMethods.pauseExecution(6000);

				//Moving to PRS Close page
				//GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(3000);
				int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
				//System.out.println("rowCount::::" + rowCount);
				for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
					String PrsStatus = ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"Prs_Status", ScenarioDetailsHashMap);
					if (PrsStatus.equalsIgnoreCase("Partial")) {
						//System.out.println("Inside partial");
						int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PacksReceived",ScenarioDetailsHashMap));
						ReceivedGoods = ReceivedGoods / 2;
						String ReceivedPacks = String.valueOf(ReceivedGoods);
						GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
					}
					else
					{
						//System.out.println("Inside full");
						GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
					}
					GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("SE_PRS_Booking", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
					GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "Remarks",ScenarioDetailsHashMap), 10);

				}
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
				GenericMethods.pauseExecution(8000);
				String OnSavePrsText = orOceanPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
				String[] OnClosePrsSplit = OnSavePrsText.split(":");
				String PrsCloseText = OnClosePrsSplit[1].trim();
				String PrsId = OnClosePrsSplit[2];
				//System.out.println("PrsCloseText::" + PrsCloseText);
				//AppReusableMethods.msgBox("PRS CLOSED SUCCESFULLY");
				ArrayList<String> slno= ExcelUtils.getAllCellValuesOfColumn("SE_PRS_Booking", "sn", ScenarioDetailsHashMap);
				for (int RowNo = 0; RowNo < valuess.size(); RowNo++) 
				{
					ExcelUtils.setCellDataKF("SE_PRS_Booking",Integer.parseInt(slno.get(RowNo)), "PRS_Id",PrsId, ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("SE_PRS_Booking",Integer.parseInt(slno.get(RowNo)), "BookingId",valuess.get(RowNo), ScenarioDetailsHashMap);
				}
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictOceanPRS_SearchList_Booking(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orOceanPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
				//AppReusableMethods.msgBox("ASSERTED PRSCLOSE ID IN SEARCHLIST");
			}
		}
	}



	/**
	 * This method is to perform Ocean PRS QuickLink Functionality After HBL.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains TestData Record Row number
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @throws InterruptedException 
	 * @Author By: Sangeeta	Mohanty
	 * 
	 */
	public static void pictOceanPRS_QuickLink_AfterHBL(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		ExcelUtils.getAllCellValuesOfColumn("SE_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
		//System.out.println("in ocean prs add");
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Add,"PRS ( Pick Up / Delivery )",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Ocean",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Truck", ScenarioDetailsHashMap,10), 10);
		String ShipmentMode = ExcelUtils.getCellData("SE_PRS_HBL",rowNo, "ShipmentMode", ScenarioDetailsHashMap);
		if (ShipmentMode.equalsIgnoreCase("Air")) 
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		} 
		else if (ShipmentMode.equalsIgnoreCase("Truck"))
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Truck",ScenarioDetailsHashMap, 10), 10);
			String FTLType = ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"FTL", ScenarioDetailsHashMap);
			if (FTLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "FTL", ScenarioDetailsHashMap,10), 10);
			} else 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "LTL",ScenarioDetailsHashMap, 10), 10);
			}
		} else 
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Ocean", ScenarioDetailsHashMap,10), 10);
			String OceanLCLType = ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"LCL", ScenarioDetailsHashMap);
			if (OceanLCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "LCL", ScenarioDetailsHashMap,10), 10);
			}
			String OceanFCLType = ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"FCL", ScenarioDetailsHashMap);
			if (OceanFCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "FCL", ScenarioDetailsHashMap,10), 10);
			} 

		}
		String ExportorImport = ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"ExportOrImport", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "XportOrImport",ScenarioDetailsHashMap, 10),ExportorImport);
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "TerminalRelease",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "TerminalReleaseStatus", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "shipmentRelease",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "ShipmentReleaseStatus", ScenarioDetailsHashMap));
				

		//Single PRS script
		String PRSType = ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PRSType", ScenarioDetailsHashMap);
		if(PRSType.equalsIgnoreCase("Single"))
		{
			System.out.println("PRS Type :"+PRSType);
			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "ShipmentReferenceNo", ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
			boolean noRecordsMsg = orOceanPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
			String noRecords = orOceanPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
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

			if(GenericMethods.isFieldEnabled(driver, null, orOceanPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orOceanPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);

				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSNavigationList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PRSNavigationList", ScenarioDetailsHashMap));
				GenericMethods.clickElement(driver, null, orOceanPRS.getElement(driver, "PRSNavigationListImg", ScenarioDetailsHashMap, 10), 2)	;
				GenericMethods.pauseExecution(6000);

				//Moving to PRS Close page
				//GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(3000);
				int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
				//System.out.println("rowCount::::" + rowCount);
				for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
					String PrsStatus = ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"Prs_Status", ScenarioDetailsHashMap);
					if (PrsStatus.equalsIgnoreCase("Partial")) {
						//System.out.println("Inside partial");
						int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PacksReceived",ScenarioDetailsHashMap));
						ReceivedGoods = ReceivedGoods / 2;
						String ReceivedPacks = String.valueOf(ReceivedGoods);
						GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
					}
					else
					{
						//System.out.println("Inside full");
						GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
					}
					GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
					GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "Remarks",ScenarioDetailsHashMap), 10);
				}
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
				GenericMethods.pauseExecution(8000);
				String OnSavePrsText = orOceanPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
				String[] OnClosePrsSplit = OnSavePrsText.split(":");
				String PrsCloseText = OnClosePrsSplit[1].trim();
				String PRSID = OnClosePrsSplit[2];
				ExcelUtils.setCellData("SE_PRS_HBL", rowNo, "PRS_Id", PRSID, ScenarioDetailsHashMap);
				ExcelUtils.setCellData("SE_PRS_HBL", rowNo, "BookingId", ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "ShipmentReferenceNo", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictOceanPRS_SearchList_HBL(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orOceanPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);

			}

		}
		else
		{
			ArrayList<String> valuess = new ArrayList<String>();
			String PRSReferenceType = ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PRSAddReferenceType", ScenarioDetailsHashMap);
			if(PRSReferenceType.equalsIgnoreCase("Booking id"))
			{
				System.out.println("Booking id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
			}
			else if(PRSReferenceType.equalsIgnoreCase("House reference id")) 
			{
				System.out.println("House reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_HBLMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap);
			}
			else
			{
				System.out.println("Master reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_OBL","Shipment_ReferenceId", ScenarioDetailsHashMap);
			}

			System.out.println("...valuess.size()"+valuess.size());	
			for (int i = 0; i < valuess.size(); i++) {
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), valuess.get(i), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(10);
				//System.out.println("noRecordsMsg::"+ orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap, 10).isDisplayed());
				boolean noRecordsMsg = orOceanPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
				String noRecords = orOceanPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
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
			if(GenericMethods.isFieldEnabled(driver, null, orOceanPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(3000);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null,orOceanPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSNavigationList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PRSNavigationList", ScenarioDetailsHashMap));
				GenericMethods.clickElement(driver, null, orOceanPRS.getElement(driver, "PRSNavigationListImg", ScenarioDetailsHashMap, 10), 2)	;
				GenericMethods.pauseExecution(6000);

				//Moving to PRS Close page
				//GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(3000);
				int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
				//System.out.println("rowCount::::" + rowCount);
				for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
					String PrsStatus = ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"Prs_Status", ScenarioDetailsHashMap);
					if (PrsStatus.equalsIgnoreCase("Partial")) {
						//System.out.println("Inside partial");
						int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PacksReceived",ScenarioDetailsHashMap));
						ReceivedGoods = ReceivedGoods / 2;
						String ReceivedPacks = String.valueOf(ReceivedGoods);
						GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
					}
					else
					{
						//System.out.println("Inside full");
						GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
					}
					GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
					GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "Remarks",ScenarioDetailsHashMap), 10);

				}
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
				GenericMethods.pauseExecution(8000);
				String OnSavePrsText = orOceanPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
				String[] OnClosePrsSplit = OnSavePrsText.split(":");
				String PrsCloseText = OnClosePrsSplit[1].trim();
				String PrsId = OnClosePrsSplit[2];
				System.out.println("PrsCloseText::" + PrsCloseText);
				//AppReusableMethods.msgBox("PRS CLOSED SUCCESFULLY");
				ArrayList<String> slno= ExcelUtils.getAllCellValuesOfColumn("SE_PRS_HBL", "sn", ScenarioDetailsHashMap);
				for (int RowNo = 0; RowNo < valuess.size(); RowNo++) 
				{
					ExcelUtils.setCellDataKF("SE_PRS_HBL",Integer.parseInt(slno.get(RowNo)), "PRS_Id",PrsId, ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("SE_PRS_HBL",Integer.parseInt(slno.get(RowNo)), "BookingId",valuess.get(RowNo), ScenarioDetailsHashMap);
				}
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictOceanPRS_SearchList_OBL(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orOceanPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
				//AppReusableMethods.msgBox("ASSERTED PRSCLOSE ID IN SEARCHLIST");
			}
		}
	}


	/**
	 * This method is to perform Ocean PRS QuickLink Functionality After OBL.
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
	public static void pictOceanPRS_QuickLink_AfterOBL(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		ExcelUtils.getAllCellValuesOfColumn("SE_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
		//System.out.println("in ocean prs add");
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Add,"PRS ( Pick Up / Delivery )",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Ocean",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Truck", ScenarioDetailsHashMap,10), 10);
		String ShipmentMode = ExcelUtils.getCellData("SE_PRS_OBL",rowNo, "ShipmentMode", ScenarioDetailsHashMap);
		if (ShipmentMode.equalsIgnoreCase("Air")) 
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		} 
		else if (ShipmentMode.equalsIgnoreCase("Truck"))
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Truck",ScenarioDetailsHashMap, 10), 10);
			String FTLType = ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"FTL", ScenarioDetailsHashMap);
			if (FTLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "FTL", ScenarioDetailsHashMap,10), 10);
			} else 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "LTL",ScenarioDetailsHashMap, 10), 10);
			}
		} else 
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Ocean", ScenarioDetailsHashMap,10), 10);
			String OceanLCLType = ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"LCL", ScenarioDetailsHashMap);
			if (OceanLCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "LCL", ScenarioDetailsHashMap,10), 10);
			}
			String OceanFCLType = ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"FCL", ScenarioDetailsHashMap);
			if (OceanFCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "FCL", ScenarioDetailsHashMap,10), 10);
			} 

		}
		String ExportorImport = ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"ExportOrImport", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "XportOrImport",ScenarioDetailsHashMap, 10),ExportorImport);
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "TerminalRelease",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "TerminalReleaseStatus", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "shipmentRelease",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "ShipmentReleaseStatus", ScenarioDetailsHashMap));

		//Single PRS script
		String PRSType = ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PRSType", ScenarioDetailsHashMap);
		if(PRSType.equalsIgnoreCase("Single"))
		{
			System.out.println("PRS Type :"+PRSType);
			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL", rowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
			boolean noRecordsMsg = orOceanPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
			String noRecords = orOceanPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
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

			if(GenericMethods.isFieldEnabled(driver, null, orOceanPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orOceanPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);


				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSNavigationList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PRSNavigationList", ScenarioDetailsHashMap));
				GenericMethods.clickElement(driver, null, orOceanPRS.getElement(driver, "PRSNavigationListImg", ScenarioDetailsHashMap, 10), 2)	;
				GenericMethods.pauseExecution(6000);

				//Moving to PRS Close page
				//GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(3000);
				int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
				//System.out.println("rowCount::::" + rowCount);
				for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
					String PrsStatus = ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"Prs_Status", ScenarioDetailsHashMap);
					if (PrsStatus.equalsIgnoreCase("Partial")) {
						//System.out.println("Inside partial");
						int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PacksReceived",ScenarioDetailsHashMap));
						ReceivedGoods = ReceivedGoods / 2;
						String ReceivedPacks = String.valueOf(ReceivedGoods);
						GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
					}
					else
					{
						//System.out.println("Inside full");
						GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
					}
					GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
					GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "Remarks",ScenarioDetailsHashMap), 10);
				}
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
				GenericMethods.pauseExecution(8000);
				String OnSavePrsText = orOceanPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
				String[] OnClosePrsSplit = OnSavePrsText.split(":");
				String PrsCloseText = OnClosePrsSplit[1].trim();
				String PRSID = OnClosePrsSplit[2];
				ExcelUtils.setCellData("SE_PRS_OBL", rowNo, "PRS_Id", PRSID, ScenarioDetailsHashMap);
				ExcelUtils.setCellData("SE_PRS_OBL", rowNo, "BookingId", ExcelUtils.getCellData("SE_OBL", rowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictOceanPRS_SearchList_OBL(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orOceanPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
			}

		}
		else
		{
			ArrayList<String> valuess = new ArrayList<String>();
			String PRSReferenceType = ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PRSAddReferenceType", ScenarioDetailsHashMap);
			if(PRSReferenceType.equalsIgnoreCase("Booking id"))
			{
				System.out.println("Booking id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
			}
			else if(PRSReferenceType.equalsIgnoreCase("House reference id")) 
			{
				System.out.println("House reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_HBLMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap);
			}
			else
			{
				System.out.println("Master reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_OBL","Shipment_ReferenceId", ScenarioDetailsHashMap);
			}

			System.out.println("...valuess.size()"+valuess.size());	
			for (int i = 0; i < valuess.size(); i++) {
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), valuess.get(i), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(10);
				//System.out.println("noRecordsMsg::"+ orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap, 10).isDisplayed());
				boolean noRecordsMsg = orOceanPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
				String noRecords = orOceanPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
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
			if(GenericMethods.isFieldEnabled(driver, null, orOceanPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(3000);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null,orOceanPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSNavigationList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PRSNavigationList", ScenarioDetailsHashMap));
				GenericMethods.clickElement(driver, null, orOceanPRS.getElement(driver, "PRSNavigationListImg", ScenarioDetailsHashMap, 10), 2)	;
				GenericMethods.pauseExecution(6000);

				//Moving to PRS Close page
				//GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(3000);
				int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
				//System.out.println("rowCount::::" + rowCount);
				for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
					String PrsStatus = ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"Prs_Status", ScenarioDetailsHashMap);
					if (PrsStatus.equalsIgnoreCase("Partial")) {
						//System.out.println("Inside partial");
						int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PacksReceived",ScenarioDetailsHashMap));
						ReceivedGoods = ReceivedGoods / 2;
						String ReceivedPacks = String.valueOf(ReceivedGoods);
						GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
					}
					else
					{
						//System.out.println("Inside full");
						GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
					}
					GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("SE_PRS_OBL", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
					GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "Remarks",ScenarioDetailsHashMap), 10);

				}
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
				GenericMethods.pauseExecution(8000);
				String OnSavePrsText = orOceanPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
				String[] OnClosePrsSplit = OnSavePrsText.split(":");
				String PrsCloseText = OnClosePrsSplit[1].trim();
				String PrsId = OnClosePrsSplit[2];
				System.out.println("PrsCloseText::" + PrsCloseText);
				//AppReusableMethods.msgBox("PRS CLOSED SUCCESFULLY");
				ArrayList<String> slno= ExcelUtils.getAllCellValuesOfColumn("SE_PRS_OBL", "sn", ScenarioDetailsHashMap);
				for (int RowNo = 0; RowNo < valuess.size(); RowNo++) 
				{
					ExcelUtils.setCellDataKF("SE_PRS_OBL",Integer.parseInt(slno.get(RowNo)), "PRS_Id",PrsId, ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("SE_PRS_OBL",Integer.parseInt(slno.get(RowNo)), "BookingId",valuess.get(RowNo), ScenarioDetailsHashMap);
				}
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictOceanPRS_SearchList_OBL(driver, ScenarioDetailsHashMap, rowNo);
			GenericMethods.assertInnerText(driver, null, orOceanPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
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
	public static void pictOceanDRS_QuickLink(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) 
	{
		ExcelUtils.getAllCellValuesOfColumn("SE_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
		//System.out.println("in ocean prs add");
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Add,"PRS ( Pick Up / Delivery )",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Ocean",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Truck", ScenarioDetailsHashMap,10), 10);
		String ShipmentMode = ExcelUtils.getCellData("SE_BookingMainDetails",rowNo, "ShipmentMode", ScenarioDetailsHashMap);
		if (ShipmentMode.equalsIgnoreCase("Air")) 
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		} 
		else if (ShipmentMode.equalsIgnoreCase("Truck"))
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Truck",ScenarioDetailsHashMap, 10), 10);
			String FTLType = ExcelUtils.getCellData("SE_DRS_HBL", rowNo,"FTL", ScenarioDetailsHashMap);
			if (FTLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "FTL", ScenarioDetailsHashMap,10), 10);
			} else 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "LTL",ScenarioDetailsHashMap, 10), 10);
			}
		} else 
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Ocean", ScenarioDetailsHashMap,10), 10);
			String OceanLCLType = ExcelUtils.getCellData("SE_DRS_HBL", rowNo,"LCL", ScenarioDetailsHashMap);
			if (OceanLCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "LCL", ScenarioDetailsHashMap,10), 10);
			}
			String OceanFCLType = ExcelUtils.getCellData("SE_DRS_HBL", rowNo,"FCL", ScenarioDetailsHashMap);
			if (OceanFCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "FCL", ScenarioDetailsHashMap,10), 10);
			} 

		}
		String ExportorImport = ExcelUtils.getCellData("SE_DRS_HBL", rowNo,"ExportOrImport", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "XportOrImport",ScenarioDetailsHashMap, 10),ExportorImport);
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "TerminalRelease",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "TerminalReleaseStatus", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "shipmentRelease",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "ShipmentReleaseStatus", ScenarioDetailsHashMap));

		//Single PRS script
		String PRSType = ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PRSType", ScenarioDetailsHashMap);
		if(PRSType.equalsIgnoreCase("Single"))
		{
			System.out.println("PRS Type :"+PRSType);
			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "ShipmentReferenceNo", ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
			boolean noRecordsMsg = orOceanPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
			String noRecords = orOceanPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
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

			if(GenericMethods.isFieldEnabled(driver, null, orOceanPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(1000);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null,orOceanPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_DRS_HBL", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);


				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSNavigationList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PRSNavigationList", ScenarioDetailsHashMap));
				GenericMethods.clickElement(driver, null, orOceanPRS.getElement(driver, "PRSNavigationListImg", ScenarioDetailsHashMap, 10), 2)	;
				GenericMethods.pauseExecution(6000);

				//Moving to PRS Close page
				//GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(3000);
				int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
				//System.out.println("rowCount::::" + rowCount);
				for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
					String PrsStatus = ExcelUtils.getCellData("SE_DRS_HBL", rowNo,"Prs_Status", ScenarioDetailsHashMap);
					if (PrsStatus.equalsIgnoreCase("Partial")) {
						//System.out.println("Inside partial");
						int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PacksReceived",ScenarioDetailsHashMap));
						ReceivedGoods = ReceivedGoods / 2;
						String ReceivedPacks = String.valueOf(ReceivedGoods);
						GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
					}
					else
					{
						//System.out.println("Inside full");
						GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("SE_DRS_HBL", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
					}
					GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("SE_DRS_HBL", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
					GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "Remarks",ScenarioDetailsHashMap), 10);
				}
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
				GenericMethods.pauseExecution(8000);
				String OnSavePrsText = orOceanPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
				String[] OnClosePrsSplit = OnSavePrsText.split(":");
				String PrsCloseText = OnClosePrsSplit[1].trim();
				String PrsId = OnClosePrsSplit[2];
				ExcelUtils.setCellData("SE_DRS_HBL", rowNo, "PRS_Id", PrsId, ScenarioDetailsHashMap);
				ExcelUtils.setCellData("SE_DRS_HBL", rowNo, "BookingId", ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "ShipmentReferenceNo", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictOceanDRS_SearchList(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orOceanPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);

			}

		}
		else
		{

			ArrayList<String> valuess = new ArrayList<String>();
			String PRSReferenceType = ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PRSAddReferenceType", ScenarioDetailsHashMap);
			if(PRSReferenceType.equalsIgnoreCase("Booking id"))
			{
				System.out.println("Booking id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
			}
			else if(PRSReferenceType.equalsIgnoreCase("House reference id")) 
			{
				System.out.println("House reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_HBLMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap);
			}
			else
			{
				System.out.println("Master reference id");
				valuess=ExcelUtils.getAllCellValuesOfColumn("SE_OBL","Shipment_ReferenceId", ScenarioDetailsHashMap);
			}

			System.out.println("...valuess.size()"+valuess.size());	
			for (int i = 0; i < valuess.size(); i++) {
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), valuess.get(i), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(10);
				//System.out.println("noRecordsMsg::"+ orAirPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap, 10).isDisplayed());
				boolean noRecordsMsg = orOceanPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
				String noRecords = orOceanPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
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
			if(GenericMethods.isFieldEnabled(driver, null, orOceanPRS.getElement(driver, "CheckBox",ScenarioDetailsHashMap, 10), 10)){
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "ETD",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "ETDTime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "ETA",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "ETATime",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "TruckId", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "TruckId",ScenarioDetailsHashMap), 10);
				GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "Landmarks",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "Landmarks",ScenarioDetailsHashMap), 10);
				GenericMethods.pauseExecution(3000);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "UOW", ScenarioDetailsHashMap));
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, null,orOceanPRS.getElement(driver,"HandelingInstructions",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_DRS_HBL", rowNo,"HandlingInstructions",ScenarioDetailsHashMap), 10);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "PRSNavigationList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PRSNavigationList", ScenarioDetailsHashMap));
				GenericMethods.clickElement(driver, null, orOceanPRS.getElement(driver, "PRSNavigationListImg", ScenarioDetailsHashMap, 10), 2)	;
				GenericMethods.pauseExecution(6000);

				//Moving to PRS Close page
				//GenericMethods.clickElement(driver, null,Common.getElement(driver, "ETDDate",ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(3000);
				int rowCount = driver.findElements(By.xpath(".//*[@id='prsCloseDataGrid']/tbody/tr")).size();
				//System.out.println("rowCount::::" + rowCount);
				for (int grid_RowId = 1; grid_RowId <= rowCount; grid_RowId++) {
					String PrsStatus = ExcelUtils.getCellData("SE_DRS_HBL", rowNo,"Prs_Status", ScenarioDetailsHashMap);
					if (PrsStatus.equalsIgnoreCase("Partial")) {
						//System.out.println("Inside partial");
						int ReceivedGoods = Integer.parseInt(ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PacksReceived",ScenarioDetailsHashMap));
						ReceivedGoods = ReceivedGoods / 2;
						String ReceivedPacks = String.valueOf(ReceivedGoods);
						GenericMethods.replaceTextofTextField(driver,By.id("editReceivedPieces" + grid_RowId), null,ReceivedPacks, 10);
					}
					else
					{
						//System.out.println("Inside full");
						GenericMethods.replaceTextofTextField(driver, By.id("editReceivedPieces" + grid_RowId), null,ExcelUtils.getCellData("SE_DRS_HBL", rowNo,"PacksReceived", ScenarioDetailsHashMap), 10);
					}
					GenericMethods.replaceTextofTextField(driver, By.id("editrcvdGoodsCond" + grid_RowId), null, ExcelUtils.getCellData("SE_DRS_HBL", rowNo,"ReceivedGoodsCondition", ScenarioDetailsHashMap),10);
					GenericMethods.replaceTextofTextField(driver, By.id("editRemarks"+ grid_RowId), null,ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "Remarks",ScenarioDetailsHashMap), 10);

				}
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 20);
				GenericMethods.pauseExecution(8000);
				String OnSavePrsText = orOceanPRS.getElement(driver, "VerificationMsg",ScenarioDetailsHashMap,10).getText();
				String[] OnClosePrsSplit = OnSavePrsText.split(":");
				String PrsCloseText = OnClosePrsSplit[1].trim();
				String PrsId = OnClosePrsSplit[2];
				//System.out.println("PrsCloseText::" + PrsCloseText);
				//AppReusableMethods.msgBox("PRS CLOSED SUCCESFULLY");
				ArrayList<String> slno= ExcelUtils.getAllCellValuesOfColumn("SE_DRS_HBL", "sn", ScenarioDetailsHashMap);
				for (int RowNo = 0; RowNo < valuess.size(); RowNo++) 
				{
					ExcelUtils.setCellDataKF("SE_DRS_HBL",Integer.parseInt(slno.get(RowNo)), "PRS_Id",PrsId, ScenarioDetailsHashMap);
					ExcelUtils.setCellDataKF("SE_DRS_HBL",Integer.parseInt(slno.get(RowNo)), "BookingId",valuess.get(RowNo), ScenarioDetailsHashMap);
				}
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(PrsCloseText,ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PrsCloseMsg",ScenarioDetailsHashMap), "Validated PRS ADD",ScenarioDetailsHashMap);
				pictOceanDRS_SearchList(driver, ScenarioDetailsHashMap, rowNo);
				GenericMethods.assertInnerText(driver, null, orOceanPRS.getElement(driver, "SearchGridPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PRS_Id", ScenarioDetailsHashMap),"PRS Id", "Validating PRS ID",  ScenarioDetailsHashMap);
				//AppReusableMethods.msgBox("ASSERTED PRSCLOSE ID IN SEARCHLIST");
			}
		}
	}


	/**
	 * This method is to perform Ocean  PRS Search Functionality after Booking.
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
	public static void pictOceanPRS_SearchList_Booking(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo)

	{
		GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "SearchPrsShipmentMode", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "ShipmentMode", ScenarioDetailsHashMap));
		GenericMethods.pauseExecution(1000);
		GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "SearchPrsShipmentType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "LoadType", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "SearchPRSExportImportIndicator",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "ExportOrImport", ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "SearchPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PRS_Id", ScenarioDetailsHashMap), 10);
		GenericMethods.pauseExecution(2000);
		String PRSCloseReferenceType = ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PRSCloseReferenceType", ScenarioDetailsHashMap);
		if(PRSCloseReferenceType.equalsIgnoreCase("Booking id"))
		{
			System.out.println("Booking id");
			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "SearchPrsReferenceID",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "BookingId",ScenarioDetailsHashMap), 10);

		}
		else if(PRSCloseReferenceType.equalsIgnoreCase("House reference id"))
		{
			System.out.println("House reference id");
			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "SearchPrsReferenceID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "ShipmentReferenceNo",ScenarioDetailsHashMap), 10);
			//ExcelUtils.setCellData("SE_PRS_Booking", rowNo, "HBL_ShipmentReferenceNo", ExcelUtils.getAllCellValuesOfColumn("SE_HBLMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap)+"", ScenarioDetailsHashMap);
		}
		else
		{
			System.out.println("master refernce id");
			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "SearchPrsReferenceID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_OBL", rowNo, "Shipment_ReferenceId",ScenarioDetailsHashMap), 10);
			//ExcelUtils.setCellData("SE_PRS_Booking", rowNo, "Master_ShipmentReferenceId", ExcelUtils.getAllCellValuesOfColumn("SE_OBL","Shipment_ReferenceId", ScenarioDetailsHashMap)+"", ScenarioDetailsHashMap);
		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap,10), 20);
		GenericMethods.pauseExecution(3000);

	}


	/**
	 * This method is to perform Ocean  PRS Search Functionality after HBL.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains TestData Record Row number
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @throws InterruptedException 
	 * @Author By: Sangeeta	Mohanty
	 * 
	 */
	public static void pictOceanPRS_SearchList_HBL(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo)

	{
		GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "SearchPrsShipmentMode", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "ShipmentMode", ScenarioDetailsHashMap));
		GenericMethods.pauseExecution(1000);
		GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "SearchPrsShipmentType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "LoadType", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "SearchPRSExportImportIndicator",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "ExportOrImport", ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "SearchPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PRS_Id", ScenarioDetailsHashMap), 10);
		GenericMethods.pauseExecution(2000);
		String PRSCloseReferenceType = ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PRSCloseReferenceType", ScenarioDetailsHashMap);
		if(PRSCloseReferenceType.equalsIgnoreCase("Booking id"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "SearchPrsReferenceID",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "BookingId",ScenarioDetailsHashMap), 10);

		}
		else if(PRSCloseReferenceType.equalsIgnoreCase("House reference id"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "SearchPrsReferenceID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "ShipmentReferenceNo",ScenarioDetailsHashMap), 10);
			//ExcelUtils.setCellData("SE_PRS_HBL", rowNo, "HBL_ShipmentReferenceNo", ExcelUtils.getAllCellValuesOfColumn("SE_HBLMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap)+"", ScenarioDetailsHashMap);
		}
		else
		{
			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "SearchPrsReferenceID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_OBL", rowNo, "Shipment_ReferenceId",ScenarioDetailsHashMap), 10);
			//ExcelUtils.setCellData("SE_PRS_HBL", rowNo, "Master_ShipmentReferenceId", ExcelUtils.getAllCellValuesOfColumn("SE_OBL","Shipment_ReferenceId", ScenarioDetailsHashMap)+"", ScenarioDetailsHashMap);
		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap,10), 20);
		GenericMethods.pauseExecution(3000);

	}



	/**
	 * This method is to perform Ocean  PRS Search Functionality after OBL.
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
	public static void pictOceanPRS_SearchList_OBL(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo)

	{
		GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "SearchPrsShipmentMode", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "ShipmentMode", ScenarioDetailsHashMap));
		GenericMethods.pauseExecution(1000);
		GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "SearchPrsShipmentType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "LoadType", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "SearchPRSExportImportIndicator",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "ExportOrImport", ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "SearchPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PRS_Id", ScenarioDetailsHashMap), 10);
		GenericMethods.pauseExecution(2000);
		String PRSCloseReferenceType = ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PRSCloseReferenceType", ScenarioDetailsHashMap);
		if(PRSCloseReferenceType.equalsIgnoreCase("Booking id"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "SearchPrsReferenceID",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "BookingId",ScenarioDetailsHashMap), 10);

		}
		else if(PRSCloseReferenceType.equalsIgnoreCase("House reference id"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "SearchPrsReferenceID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "ShipmentReferenceNo",ScenarioDetailsHashMap), 10);
			//ExcelUtils.setCellData("SE_PRS_OBL", rowNo, "HBL_ShipmentReferenceNo", ExcelUtils.getAllCellValuesOfColumn("SE_HBLMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap)+"", ScenarioDetailsHashMap);
		}
		else
		{
			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "SearchPrsReferenceID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_OBL", rowNo, "Shipment_ReferenceId",ScenarioDetailsHashMap), 10);
			//ExcelUtils.setCellData("SE_PRS_OBL", rowNo, "Master_ShipmentReferenceId", ExcelUtils.getAllCellValuesOfColumn("SE_OBL","Shipment_ReferenceId", ScenarioDetailsHashMap)+"", ScenarioDetailsHashMap);
		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap,10), 20);
		GenericMethods.pauseExecution(3000);

	}


	/**
	 * This method is to perform Ocean  DRS Search Functionality in Import SCeanrio.
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
	public static void pictOceanDRS_SearchList(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo)

	{
		GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "SearchPrsShipmentMode", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "ShipmentMode", ScenarioDetailsHashMap));
		GenericMethods.pauseExecution(1000);
		GenericMethods.selectOptionFromDropDown(driver, null, orOceanPRS.getElement(driver, "SearchPrsShipmentType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "LoadType", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "SearchPRSExportImportIndicator",ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "ExportOrImport", ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "SearchPrsID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PRS_Id", ScenarioDetailsHashMap), 10);
		GenericMethods.pauseExecution(2000);
		String PRSCloseReferenceType = ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PRSCloseReferenceType", ScenarioDetailsHashMap);
		if(PRSCloseReferenceType.equalsIgnoreCase("Booking id"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "SearchPrsReferenceID",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "BookingId",ScenarioDetailsHashMap), 10);

		}
		else if(PRSCloseReferenceType.equalsIgnoreCase("House reference id"))
		{
			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "SearchPrsReferenceID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "ShipmentReferenceNo",ScenarioDetailsHashMap), 10);
			//ExcelUtils.setCellData("SE_DRS_HBL", rowNo, "HBL_ShipmentReferenceNo", ExcelUtils.getAllCellValuesOfColumn("SE_HBLMainDetails","ShipmentReferenceNo", ScenarioDetailsHashMap)+"", ScenarioDetailsHashMap);
		}
		else
		{
			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "SearchPrsReferenceID", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_OBL", rowNo, "Shipment_ReferenceId",ScenarioDetailsHashMap), 10);
			//ExcelUtils.setCellData("SE_DRS_HBL", rowNo, "Master_ShipmentReferenceId", ExcelUtils.getAllCellValuesOfColumn("SE_OBL","Shipment_ReferenceId", ScenarioDetailsHashMap)+"", ScenarioDetailsHashMap);
		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap,10), 20);
		GenericMethods.pauseExecution(3000);

	}

	
	//Pavan Below Method will Perform Unblock Shipment after selection of Shipment Reference in PRS Module
	public static void pictOcean_PRS_Menu_Unblock_Shipment(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo)
	{
		ExcelUtils.getAllCellValuesOfColumn("SE_BookingMainDetails","BookingId", ScenarioDetailsHashMap);
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Add,"PRS ( Pick Up / Delivery )",ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Ocean",ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Truck", ScenarioDetailsHashMap,10), 10);
		String ShipmentMode = ExcelUtils.getCellData("SE_PRS_HBL",rowNo, "ShipmentMode", ScenarioDetailsHashMap);
		if (ShipmentMode.equalsIgnoreCase("Air")) 
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Air",ScenarioDetailsHashMap, 10), 10);
		} 
		else if (ShipmentMode.equalsIgnoreCase("Truck"))
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Truck",ScenarioDetailsHashMap, 10), 10);
			String FTLType = ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"FTL", ScenarioDetailsHashMap);
			if (FTLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "FTL", ScenarioDetailsHashMap,10), 10);
			} else 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "LTL",ScenarioDetailsHashMap, 10), 10);
			}
		} else 
		{
			GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "Ocean", ScenarioDetailsHashMap,10), 10);
			String OceanLCLType = ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"LCL", ScenarioDetailsHashMap);
			if (OceanLCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "LCL", ScenarioDetailsHashMap,10), 10);
			}
			String OceanFCLType = ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"FCL", ScenarioDetailsHashMap);
			if (OceanFCLType.equalsIgnoreCase("Yes")) 
			{
				GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "FCL", ScenarioDetailsHashMap,10), 10);
			} 

		}
		String ExportorImport = ExcelUtils.getCellData("SE_PRS_HBL", rowNo,"ExportOrImport", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null,orOceanPRS.getElement(driver, "XportOrImport",ScenarioDetailsHashMap, 10),ExportorImport);

		//Single PRS script
		String PRSType = ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PRSType", ScenarioDetailsHashMap);
		if(PRSType.equalsIgnoreCase("Single"))
		{
			System.out.println("PRS Type :"+PRSType);
			GenericMethods.replaceTextofTextField(driver, null, orOceanPRS.getElement(driver, "ReferenceId",ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "ShipmentReferenceNo", ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 10);
			boolean noRecordsMsg = orOceanPRS.getElement(driver, "NoRecordsFound",ScenarioDetailsHashMap,10).isDisplayed();
			String noRecords = orOceanPRS.getElement(driver, "NoRecordsFound", ScenarioDetailsHashMap,10).getText();
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
							try{
								GenericMethods.handleAlert(driver, "accept");

							}catch (Exception e) {
								e.printStackTrace();
							}
							GenericMethods.clickElement(driver, null,Common.getElement(driver, "CloseButton",ScenarioDetailsHashMap, 10), 10);
							GenericMethods.pauseExecution(3000);
							GenericMethods.selectWindow(driver);
							GenericMethods.clickElement(driver, null,orOceanPRS.getElement(driver, "ExitButton",ScenarioDetailsHashMap, 10), 10);
							GenericMethods.switchToParent(driver);
							AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
						}
					}

				}
			}


		}
	}
}
