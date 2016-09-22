package business.scenarios;

import global.reusables.DumpExcelFiles;
import global.reusables.ExcelUtils;
import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;

import DriverMethods.WebDriverInitilization;
import app.reuseables.AppReusableMethods;
import app.reuseables.ETransMenu;

public class OceanExportsOBL extends WebDriverInitilization{
	//public static CommonBean res;
	static ObjectRepository Common=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/CommonObjects.xml");
	static ObjectRepository orOceanExpOBL=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/OceanExportOBL.xml");
	static ObjectRepository orHBL=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/HBL.xml");
	static ObjectRepository orDecisionTable = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/DecisionTable.xml");

	public static void main(String args[]){
		/*String txt="Certification Statements"+
		   "Refrigerated Cargo"+
		   "Carrier Reserves the Right to Place Cont"+
		   "No Shipper's Export Declaration Required"+
		   "Container Safety Act";
					System.out.println("check list"+txt);
					String str=txt.split("\n")[0];
					String str1=txt.split("/n")[0];
					System.out.println("1***"+str);
					System.out.println("2***"+str1);*/



	}
	public static void copyOBL_Add(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ) throws AWTException{
		//System.out.println("in pictOceanExportsOBL_Add Add");
		AppReusableMethods.selectMenu(driver, ETransMenu.OceanExport_Master, "Ocean Consolidation", ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(5000);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "CopyButton", ScenarioDetailsHashMap, 10),10);
		GenericMethods.pauseExecution(5000);
		AppReusableMethods.selectValueFromLov(driver, orOceanExpOBL.getElement(driver, "CarrierIdLov", ScenarioDetailsHashMap, 10), orOceanExpOBL, "CarrierId", "IRSL", ScenarioDetailsHashMap);
		AppReusableMethods.selectValueFromLov(driver, orOceanExpOBL.getElement(driver, "VesselNameLov", ScenarioDetailsHashMap, 10), orOceanExpOBL, "Search_VesselName", ExcelUtils.getCellData("SE_OBLVesselSchedule", rowNo, "VesselName", ScenarioDetailsHashMap), ScenarioDetailsHashMap);

		/*if(!ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("Break bulk")||
				!ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("RORO")){
			Select pickUp_DropdownValue = new Select(orOceanExpOBL.getElement(driver, "DDL_PickUpRequired", ScenarioDetailsHashMap, 10));
			pickUp_DropdownValue.selectByVisibleText(ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "PickUpRequired",ScenarioDetailsHashMap));
			//			GenericMethods.selectOptionFromDropDown(driver, null, orHBL.getElement(driver, "DDL_PickUpRequired", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PickUpRequired", ScenarioDetailsHashMap));
		}
		 */
		pictOceanExportsOBL_INTTRA(driver, ScenarioDetailsHashMap, rowNo);

	}


	public static void pictOceanExportsOBL_Add(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ) throws AWTException{
		//System.out.println("in pictOceanExportsOBL_Add Add");
		AppReusableMethods.selectMenu(driver, ETransMenu.OceanExport_Master, "Ocean Consolidation", ScenarioDetailsHashMap);
		if (ExcelUtils.getCellData("SE_OBL", rowNo, "ModifyAndAssignHBL", ScenarioDetailsHashMap).equalsIgnoreCase("Yes")) {
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ShipmentRefNo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_OBL", rowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap) , 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "Consolidation_Tab", ScenarioDetailsHashMap, 10),30);
			GenericMethods.pauseExecution(2000);
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ConsolHBL_ID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL",rowNo,"HBL_Id", ScenarioDetailsHashMap), 30);
			GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "ConsoleSearch", ScenarioDetailsHashMap, 10),20);
			GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "ConsoleHouseAttachCheckBox", ScenarioDetailsHashMap, 10), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10),20);
		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton", ScenarioDetailsHashMap, 10),10);

		if(ExcelUtils.getCellData("SE_OBL", rowNo, "InttraCheckBoxValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			INTTRA_Checkboxvalidation(driver, ScenarioDetailsHashMap, rowNo);
		}
		else
		{
			GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpOBL.getElement(driver, "LoadType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL", rowNo, "ConsolType", ScenarioDetailsHashMap));
		}
		//		Code to validate Load type.
		if (ExcelUtils.getCellData("SE_OBL", rowNo, "AFR", ScenarioDetailsHashMap).equalsIgnoreCase("Yes")) {
			pictOceanExportsOBL_AFR(driver, ScenarioDetailsHashMap, rowNo);	
		}
		if (ExcelUtils.getCellData("SE_OBL", rowNo, "INTTRA_SI", ScenarioDetailsHashMap).equalsIgnoreCase("Yes")) {
			pictOceanExportsOBL_INTTRA(driver, ScenarioDetailsHashMap, rowNo);
		}
		AppReusableMethods.selectValueFromLov(driver, orOceanExpOBL.getElement(driver, "CarrierIdLov", ScenarioDetailsHashMap, 10), orOceanExpOBL, "CarrierId", ExcelUtils.getCellData("SE_OBL", rowNo, "Carrier_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		String randomOBL_ID=ExcelUtils.getCellData("SE_OBL", rowNo, "OBL_Id", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(5)+"";
		//System.out.println("randomOBL_ID:::::"+randomOBL_ID);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "OblId", ScenarioDetailsHashMap, 10),  randomOBL_ID, 20);
		ExcelUtils.setCellData("SE_OBL", rowNo, "OBL_Id", randomOBL_ID, ScenarioDetailsHashMap);
		System.out.println(rowNo+"***before rowNo");
		//	Updated code to add mbl id to all the rows of arrival confirmation sheet and to continue with actual data set of respective sub scenario. 
		int actualdatasetNo = rowNo;
		System.out.println(actualdatasetNo+"***actualdatasetNo");
		for (int i = 1; i <= ExcelUtils.getCellDataRowCount("SI_Arrival", ScenarioDetailsHashMap); i++) {
			rowNo=i;
			//		ScenarioDetailsHashMap.put("DataSetNo", i+"");
			ExcelUtils.setCellData("SI_Arrival", rowNo, "MBLId", randomOBL_ID, ScenarioDetailsHashMap);
		}
		/*ScenarioDetailsHashMap.remove("DataSetNo");
		ScenarioDetailsHashMap.put("DataSetNo", actualdatasetNo+"");*/
		rowNo=actualdatasetNo;
		System.out.println(rowNo+"****After rowNo");
		//		ExcelUtils.setCellData("SI_Arrival", rowNo, "MBLId", randomOBL_ID, ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "OblDate", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "OBL_Date", ScenarioDetailsHashMap), 20);
		//GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "CarrierBookingId", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "CarrierBookingRefNum", ScenarioDetailsHashMap), 20);
		//Added for HBl SC#25
		String randomCBRefId=ExcelUtils.getCellData("SE_OBL", rowNo, "CarrierBookingRefNum", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(5)+"";
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "CarrierBookingId", ScenarioDetailsHashMap, 10),  randomCBRefId, 20);
		ExcelUtils.setCellData("SE_OBL", rowNo, "CarrierBookingRefNum", randomCBRefId, ScenarioDetailsHashMap);
		System.out.println("dataset no---****"+ScenarioDetailsHashMap.get("DataSetNo"));
		System.out.println("value of destbarnc---"+ExcelUtils.getCellData("SE_OBL", rowNo, "DestBranch", ScenarioDetailsHashMap));

		if(ExcelUtils.getCellData("SE_OBL", rowNo, "Inttra_Required", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "ReferenceFields_Btn", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(6000);
			GenericMethods.selectWindow(driver);
			GenericMethods.pauseExecution(6000);
			GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpOBL.getElement(driver, "ReferenceType_DropDown", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL", rowNo, "ReferenceType", ScenarioDetailsHashMap));
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ReferenceType_ReferenceNo", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL", rowNo, "ReferenceNo", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ReferenceEffectiveDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL", rowNo, "ReferenceTypeExpiryDate", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Reference_EffectiveDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL", rowNo, "ReferenceTypeEffectiveDate", ScenarioDetailsHashMap), 2);
			GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "Reference_GridAddBtn", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(6000);
			GenericMethods.switchToParent(driver);
			GenericMethods.pauseExecution(6000);
			AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(6000);
		}


		AppReusableMethods.selectValueFromLov(driver, orOceanExpOBL.getElement(driver, "OriginDeptIdLov", ScenarioDetailsHashMap, 10), orOceanExpOBL, "OriginDeptId_SearchCode", ExcelUtils.getCellData("SE_OBL", rowNo, "OriginDept", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		AppReusableMethods.selectValueFromLov(driver, orOceanExpOBL.getElement(driver, "DestBranchIdLov", ScenarioDetailsHashMap, 10), orOceanExpOBL, "DestBranchId_SearchCode", ExcelUtils.getCellData("SE_OBL", rowNo, "DestBranch", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "DestDeptId", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "DestDept", ScenarioDetailsHashMap), 20);//Unable to Select Dept from Lov  
		//		AppReusableMethods.selectValueFromLov(driver, orOceanExpOBL.getElement(driver, "DestDeptIdLov", ScenarioDetailsHashMap, 10), orOceanExpOBL, "DestBranchId_SearchCode", ExcelUtils.getCellData("SE_OBL", rowNo, "DestDept", ScenarioDetailsHashMap));
		AppReusableMethods.selectValueFromLov(driver, orOceanExpOBL.getElement(driver, "ServiceLevelLov", ScenarioDetailsHashMap, 10), orOceanExpOBL, "ServiceLevel_SearchCode", ExcelUtils.getCellData("SE_OBL", rowNo, "ServiceLevel", ScenarioDetailsHashMap), ScenarioDetailsHashMap);

		//Pavan FUNC007 --Below condition will perform Validations 
		if(ExcelUtils.getCellData("SE_OBL", rowNo, "Location_Mandatory",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			Location_Terminal_Validations(driver, ScenarioDetailsHashMap, rowNo);
		}
		else{
			pictOceanExportsOBL_POL(driver, ScenarioDetailsHashMap, rowNo);
			pictOceanExportsOBL_POD(driver, ScenarioDetailsHashMap, rowNo);
		}// FUNC007 End

		//eRatingValidation starts here -Masthan


		if(ExcelUtils.getCellData("SE_OBL", rowNo, "eRatingValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{

			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ServiceTypeOrigin", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SE_OBL", rowNo, "OriginServiceType", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ServiceTypeDest", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SE_OBL", rowNo, "DestServiceType", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "PlaceOfReceipt", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SE_OBL", rowNo, "PlaceofReceipt", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "PlaceOfDelivery", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SE_OBL", rowNo, "PlaceofDelivery", ScenarioDetailsHashMap), 2);
			if(ExcelUtils.getCellData("SE_OBL", rowNo, "RatingType", ScenarioDetailsHashMap).equalsIgnoreCase("ManualRate"))
			{
				GenericMethods.selectOptionFromDropDown(driver, null,orOceanExpOBL.getElement(driver, "CarrierContract", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL", rowNo, "Contract_Tariff", ScenarioDetailsHashMap));
				GenericMethods.locateElement(driver, By.id("contractIdI"), 2).click();
				GenericMethods.pauseExecution(2000);
				GenericMethods.selectWindow(driver);
				GenericMethods.pauseExecution(4000);
				GenericMethods.locateElement(driver, By.id("origin"), 2).click();
				GenericMethods.pauseExecution(2000);
				try {
					if (Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap,10).isEnabled()) {
						GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 2);
					}	
				} catch (Exception e) {
					if (Common.getElement(driver, "SaveButton2",ScenarioDetailsHashMap,10).isEnabled()) {
						GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton2",ScenarioDetailsHashMap, 10), 2);
					}e.printStackTrace();
				}
				GenericMethods.switchToParent(driver);
				AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
			}
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "VendorId", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SE_OBL", rowNo, "VendorID", ScenarioDetailsHashMap), 2);
			String VendorReference=GenericMethods.randomNumericString(6)+"";
			ExcelUtils.setCellData("SE_OBL", rowNo, "VendorReference", VendorReference, ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "VendorReference", ScenarioDetailsHashMap, 2), VendorReference, 2);


		}
		//End here




		//Dates
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Carrier_CutOffDate", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "CarrierCutoff_Date", ScenarioDetailsHashMap), 20);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Carrier_CutOffTime", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "CarrierCutoff_Time", ScenarioDetailsHashMap), 20);
		//GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Service_CutOffDate", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "ServiceCutoff_Date", ScenarioDetailsHashMap), 20);
		//GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Service_CutOffTime", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "ServiceCutoff_Time", ScenarioDetailsHashMap), 20);
		GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDDate", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "ETD", ScenarioDetailsHashMap), 20);
		GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETDTime", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "ETD_Time", ScenarioDetailsHashMap), 20);
		GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETADate", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "ETA", ScenarioDetailsHashMap), 20);
		GenericMethods.replaceTextofTextField(driver, null, Common.getElement(driver, "ETATime", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "ETA_Time", ScenarioDetailsHashMap), 20);
		/*GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ETAFinalDest_Date", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "ETAFinal_DestDate", ScenarioDetailsHashMap), 20);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ETAFinalDest_Time", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "ETAFinal_DestTime", ScenarioDetailsHashMap), 20);
		 *///GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Tendered_Date", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "Tendered_Date", ScenarioDetailsHashMap), 20);
		//GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Tendered_Time", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "Tendered_Time", ScenarioDetailsHashMap), 20);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "LoadOnBoard_Date", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "LoadedOnBoard_Date", ScenarioDetailsHashMap), 20);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "LoadOnBoard_Time", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "LoadedOnBoard_Time", ScenarioDetailsHashMap), 20);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Service_CutOffDate", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "ServiceCutoff_Date", ScenarioDetailsHashMap), 20);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Service_CutOffTime", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "ServiceCutoff_Time", ScenarioDetailsHashMap), 20);
		pictOceanExportsOBL_VesselSchedule(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.pauseExecution(7000);


		//Date Validation start:Author Sangeeta
		//FUNC062.11-Date Validation(Carrier Cut-off Date / Time)
		if(ExcelUtils.getCellData("SE_OBL", rowNo, "DateValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			String ActualCarrierCutoffDate =  driver.findElement(By.id("cutOffDate")).getAttribute("value");
			String ActualCarrierCutoffTime =  driver.findElement(By.name("cutOffTime")).getAttribute("value");
			String ExpectedCarrierCutoffDate = ExcelUtils.getCellData("SE_OBL", rowNo, "ETD", ScenarioDetailsHashMap);
			String ExpectedCarrierCutoffTime = ExcelUtils.getCellData("SE_OBL", rowNo, "ETD_Time", ScenarioDetailsHashMap);
			String ActualCarrierCutoffDateTime = ActualCarrierCutoffDate.concat(" "+ActualCarrierCutoffTime);
			String ExpectedCarrierCutoffDateTime = ExpectedCarrierCutoffDate.concat(" "+ExpectedCarrierCutoffTime);
			try {
				GenericMethods.compareDatesByCompareTo("dd-MM-yyyy hh:mm", ActualCarrierCutoffDateTime, ExpectedCarrierCutoffDateTime, "Less", " Carrier Cut-off Date / Time is less than ETD date and time", " Carrier Cut-off Date / Time is not less than ETD date and time", ScenarioDetailsHashMap);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			//FUNC062.12-Date Validation(Service Cut-off Date / Time)
			String ActualServiceCutoffDate =  driver.findElement(By.id("serviceCutoffDate")).getAttribute("value");
			String ActualServiceCutoffTime =  driver.findElement(By.name("serviceCutoffTime")).getAttribute("value");
			String ExpectedServiceCutoffDate = ExcelUtils.getCellData("SE_OBL", rowNo, "CarrierCutoff_Date", ScenarioDetailsHashMap);
			String ExpectedServiceCutoffTime = ExcelUtils.getCellData("SE_OBL", rowNo, "CarrierCutoff_Time", ScenarioDetailsHashMap);
			String ActualServiceCutoffDateTime = ActualServiceCutoffDate.concat(" "+ActualServiceCutoffTime);
			String ExpectedServiceCutoffDateTime = ExpectedServiceCutoffDate.concat(" "+ExpectedServiceCutoffTime);
			try {
				GenericMethods.compareDatesByCompareTo("dd-MM-yyyy hh:mm", ActualServiceCutoffDateTime, ExpectedServiceCutoffDateTime, "Less", "Service Cut-off Date / Time is less than Carrier Cut off date", "Service Cut-off Date / Time is not less than Carrier Cut off date", ScenarioDetailsHashMap);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		//Date Validation end
		//		pictOceanExportsOBL_MainDetailsContainer(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.pauseExecution(3000);

		//Inttra validation added by sangeeta
		if(ExcelUtils.getCellData("SE_OBL", rowNo, "Inttra_Required", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			boolean flag=orOceanExpOBL.getElement(driver, "CHB_Intrra", ScenarioDetailsHashMap, 10).isSelected();
			System.out.println("flag***"+flag);
			GenericMethods.assertTwoValues(GenericMethods.isFieldEnabled(driver, null, orOceanExpOBL.getElement(driver, "CHB_Intrra", ScenarioDetailsHashMap, 10), 10)+"", true+"", "Verifying whether INTTRA SI checkbox is enabled", ScenarioDetailsHashMap);

			if (flag!=true) {
				GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "CHB_Intrra", ScenarioDetailsHashMap, 10),30);	
			}
			GenericMethods.pauseExecution(5000);
			GenericMethods.assertTwoValues(GenericMethods.getInnerText(driver, null, orOceanExpOBL.getElement(driver, "Intrra_Tab", ScenarioDetailsHashMap, 10), 2), ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "InttraTab_Name", ScenarioDetailsHashMap), "Validating availability of Inttra tab after checking inttra check box in Main Details tab of Ocean Consolidation module", ScenarioDetailsHashMap);
			//GenericMethods.assertTwoValues(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Intrra_Tab", ScenarioDetailsHashMap, 10), null), ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "InttraTab_Name", ScenarioDetailsHashMap), "Validating availability of Inttra tab after checking inttra check box in Main Details tab of Ocean Consolidation module", ScenarioDetailsHashMap);
			//********************
			GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "CarrierIdLov", ScenarioDetailsHashMap, 10), 10);
			GenericMethods.pauseExecution(6000);
			GenericMethods.selectWindow(driver);
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "CarrierId",ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_OBL", rowNo, "Carrier_Id", ScenarioDetailsHashMap), 10);
			GenericMethods.pauseExecution(2000);
			try {
				if (Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap,10).isEnabled()) {
					GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton",ScenarioDetailsHashMap, 10), 2);
				}	
			} catch (Exception e) {
				if (Common.getElement(driver, "SearchButton2",ScenarioDetailsHashMap,10).isEnabled()) {
					GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton2",ScenarioDetailsHashMap, 10), 2);
				}e.printStackTrace();
			}
			GenericMethods.pauseExecution(8000);
			String CarrierID = GenericMethods.getInnerText(driver, By.id("dtCarrierId1"), null, 10);
			GenericMethods.assertTwoValues(CarrierID, ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "Inttra_CarrierID", ScenarioDetailsHashMap), "After selecting inttra check box, validating correct inttra setup carrier ID is defaulting or not", ScenarioDetailsHashMap);
			try {
				if (Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap,10).isEnabled()) {
					GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 2);
				}	
			} catch (Exception e) {
				if (Common.getElement(driver, "SaveButton2",ScenarioDetailsHashMap,10).isEnabled()) {
					GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton2",ScenarioDetailsHashMap, 10), 2);
				}e.printStackTrace();
			}
			GenericMethods.switchToParent(driver);
			AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);

			//*****************

		}


		pictOceanExportsOBL_Parties(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.pauseExecution(3000);
		if (ExcelUtils.getCellData("SE_OBL", rowNo, "HBLRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes")) {
			pictOceanExportsOBL_Consolidation(driver, ScenarioDetailsHashMap, rowNo);
			//Changed container details method from pictOceanExportsOBL_ContainerDetails to seaMBLContainerDetails.
			int rowCount=ExcelUtils.getCellDataRowCount("SE_OBLContainersDetails", ScenarioDetailsHashMap);
			if (rowCount>0) {
				seaMBLContainerDetails(driver, ScenarioDetailsHashMap, rowNo);	
			}

			//Inttra validation added by sangeeta
			if(ExcelUtils.getCellData("SE_OBL", rowNo, "Inttra_Required", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				OceanExportsOBL_INTTRA(driver, ScenarioDetailsHashMap, rowNo);
			}

			//		pictOceanExportsOBL_ContainerDetails(driver, ScenarioDetailsHashMap, rowNo);
			pictOceanExportsOBL_PickUpInstructions(driver, ScenarioDetailsHashMap, rowNo);

			if(ExcelUtils.getCellData("SE_OBL", rowNo, "eRatingValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
			{
				pictOceanExportsOBL_FreightAndOthersCharges(driver, ScenarioDetailsHashMap, rowNo);
				pictOceanExportsOBL_ChargesAndCosts(driver, ScenarioDetailsHashMap, rowNo);
			}
			//pictOceanExportsOBL_OverRide(driver, ScenarioDetailsHashMap, rowNo);
			pictOceanExportsOBL_OverRide(driver, ScenarioDetailsHashMap, rowNo);
			pictOceanExportsOBL_NotesAndStatus(driver, ScenarioDetailsHashMap, rowNo);
			pictOceanExportsOBL_EDoc(driver, ScenarioDetailsHashMap, rowNo);

		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10),20);
		AppReusableMethods.msgBox("OBL ADDED SUCCESFULLY");
		//System.out.println("Savedd");
		String OnSave_RefText=GenericMethods.getInnerText(driver, null, orOceanExpOBL.getElement(driver, "OnSave_ShipmentReferenceText", ScenarioDetailsHashMap, 10), 10);
		//String [] ShipmentRefTextSplit=OnSave_RefText.split(":");
		/*String ShipmentRefText =ShipmentRefTextSplit[0];
		String ShipmentRefId =ShipmentRefTextSplit[1];*/
		String ShipmentRefText =OnSave_RefText.split(" : ")[1].trim();
		String ShipmentRefId =OnSave_RefText.split(" : ")[2].trim();
		//System.out.println("ShipmentRefText::"+ShipmentRefText+"ShipmentRefId::"+ShipmentRefId);
		ExcelUtils.setCellData("SE_OBL", rowNo, "Shipment_ReferenceId", ShipmentRefId, ScenarioDetailsHashMap);
		//Priya: added for Profit Share Validation
		if(ExcelUtils.getCellData("SE_OBL", rowNo, "ProfitShareRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes")){

			ExcelUtils.setCellData("Ocean_ProfitShareSatlement", rowNo, "OblId", ExcelUtils.getCellData("SE_OBL", rowNo, "OBL_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		}
		GenericMethods.assertTwoValues(ShipmentRefText, ExcelUtils.getCellData("SE_OBL", rowNo, "Shipment_ReferenceText", ScenarioDetailsHashMap), "Validating ShipmentReferenceText", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ShipmentRefNo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_OBL", rowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap) , 10);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
		//        GenericMethods.assertInnerText(driver, null, orOceanExpOBL.getElement(driver, "SearchGridShipmentRefId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL", rowNo, "Shipment_ReferenceId",ScenarioDetailsHashMap), "Shipment ReferenceId", "Validating Shipment Reference ID", false, ScenarioDetailsHashMap);
		GenericMethods.assertInnerText(driver, null, orOceanExpOBL.getElement(driver, "SearchGridShipmentRefId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL", rowNo, "Shipment_ReferenceId",ScenarioDetailsHashMap), "Shipment ReferenceId", "Validating Shipment Reference ID", ScenarioDetailsHashMap);
		AppReusableMethods.msgBox("OBL ASSERTED SUCCESFULLY");
		// Code for master Close
		//		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "MasterClose", ScenarioDetailsHashMap, 10),20);
		/*
		 * Code to write all container details to SE_OBLContainers sheet.
		 * - By Prasanna Modugu
		 */
		//		String mblId=ExcelUtils.getCellData("SE_OBL", rowNo, "OBL_Id", ScenarioDetailsHashMap);
		int rowCount=ExcelUtils.getCellDataRowCount("SE_OBLContainersDetails", ScenarioDetailsHashMap);
		int rowCount1=ExcelUtils.getCellDataRowCount("SE_HBL_MBL_Container", ScenarioDetailsHashMap);
		String mblId=ExcelUtils.getCellData("SE_OBL", rowNo, "OBL_Id", ScenarioDetailsHashMap);
		System.out.println("pictOceanExportsOBL_ContainerDetails rowCount::"+rowCount);
		for (int i = 1; i <= rowCount1; i++) {
			String containerReq=ExcelUtils.getCellData("SE_OBLContainers", i, "ContainerReq", ScenarioDetailsHashMap);
			String hblId=ExcelUtils.getCellData("SE_OBLContainers", i, "HBL_ID", ScenarioDetailsHashMap);
			String containerNo=ExcelUtils.getCellData("SE_OBLContainers", i, "Container_Number", ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_ArrivalContainers", i, "MBL_ID", mblId, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_ArrivalContainers", i, "HBL_ID", hblId, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_ArrivalContainers", i, "Container_Number", containerNo, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_ArrivalContainers", i, "ContainerReq", containerReq, ScenarioDetailsHashMap);
		}

		for (int i =1; i <= rowCount; i++) {
			String containerNo=ExcelUtils.getCellData("SE_OBLContainersDetails", i, "ContainerNumber", ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_ArrivalContainers", rowCount1+i, "ContainerReq", "MBL", ScenarioDetailsHashMap);
			//			ExcelUtils.setCellData("SE_OBLContainers", i, "HBL_ID", hblID, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_ArrivalContainers", rowCount1+i, "MBL_ID", mblId, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_ArrivalContainers", rowCount1+i, "Container_Number", containerNo, ScenarioDetailsHashMap);
		}	

	}
	public static void oceanExportsOBL_Modify(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ) throws AWTException{
		//System.out.println("in pictOceanExportsOBL_Add Add");
		AppReusableMethods.selectMenu(driver, ETransMenu.OceanExport_Master, "Ocean Consolidation", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ShipmentRefNo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_OBL", rowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap) , 10);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
		GenericMethods.pauseExecution(5000);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "Consolidation_Tab", ScenarioDetailsHashMap, 10),30);
		GenericMethods.handleAlert(driver, "accept");
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ConsolHBL_ID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL",rowNo,"HBL_Id", ScenarioDetailsHashMap), 30);
		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "ConsoleSearch", ScenarioDetailsHashMap, 10),20);
		GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "ConsoleHouseAttachCheckBox", ScenarioDetailsHashMap, 10), 10);
		seaMBLContainerDetails(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10),20);
	}
	public static void oceanExportsOBL_MasterClose(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		AppReusableMethods.selectMenu(driver, ETransMenu.OceanExport_Master, "Ocean Consolidation", ScenarioDetailsHashMap);
		//		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Search_MblId", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_OBL", rowNo, "OBL_Id", ScenarioDetailsHashMap) , 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ShipmentRefNo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_OBL", rowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap) , 10);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
		GenericMethods.assertInnerText(driver, null, orOceanExpOBL.getElement(driver, "SearchGridMblId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL", rowNo, "OBL_Id",ScenarioDetailsHashMap), "Shipment ReferenceId", "Validating Shipment Reference ID", ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "MasterClose", ScenarioDetailsHashMap, 10),20);
	}


	public static void pictOceanExportsOBL_POL(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "PortOfLoadingLov", ScenarioDetailsHashMap, 10),10);
		GenericMethods.pauseExecution(5000);
		GenericMethods.selectWindow(driver);
		GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpOBL.getElement(driver, "Location_SearchType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL", rowNo, "Location_SearchType", ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Location_SearchCode", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "PortOfLoad", ScenarioDetailsHashMap), 20);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),10);
		GenericMethods.pauseExecution(5000);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10),10);
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver, ScenarioDetailsHashMap);
	}
	public static void pictOceanExportsOBL_POD(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "PortOfDischargeLov", ScenarioDetailsHashMap, 10),10);
		GenericMethods.pauseExecution(5000);
		GenericMethods.selectWindow(driver);
		GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpOBL.getElement(driver, "Location_SearchType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL", rowNo, "Location_SearchType", ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Location_SearchCode", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBL", rowNo, "PortOfDischarge", ScenarioDetailsHashMap), 20);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),10);
		GenericMethods.pauseExecution(5000);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10),10);
		GenericMethods.switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver, ScenarioDetailsHashMap);
	}
	public static void pictOceanExportsOBL_MainDetailsContainer(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		int rowCount=ExcelUtils.getCellDataRowCount("SE_OBLContainers", ScenarioDetailsHashMap);
		//System.out.println("pictOceanExportsOBL_MainDetailsContainer rowCount::"+rowCount);
		for (int i = 1; i <= rowCount; i++) {
			//System.out.println("inside loop");
			GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpOBL.getElement(driver, "MainContainerType", ScenarioDetailsHashMap, 20), ExcelUtils.getCellData("SE_OBLContainers", i, "ContainerType", ScenarioDetailsHashMap));
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "MainNumOfContainers", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBLContainers", i, "NumOfContainers", ScenarioDetailsHashMap), 20);
			GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "MainContainer_AddBtn", ScenarioDetailsHashMap, 10),10);
		}
	}

	public static void pictOceanExportsOBL_ContainerDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ) throws AWTException{
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "ContainerDetails_Tab", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(2000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}
		//Changed test data sheet name from SE_OBLContainers to SE_OBLContainersDetails-By Prasanna
		int rowCount=ExcelUtils.getCellDataRowCount("SE_OBLContainersDetails", ScenarioDetailsHashMap);
		System.out.println("pictOceanExportsOBL_ContainerDetails rowCount::"+rowCount);
		for (int i = 1; i <= rowCount; i++) {
			//System.out.println("inside loop i ="+i);
			Select ContainerType = new Select(driver.findElement(By.id("containerType"+i)));
			ContainerType.selectByVisibleText(ExcelUtils.getCellData("SE_OBLContainersDetails", i, "ContainerType", ScenarioDetailsHashMap));
			String randomContainer_No=ExcelUtils.getCellData("SE_OBLContainersDetails", i, "ContainerNumber", ScenarioDetailsHashMap);
			//System.out.println("randomContainer_No:::::"+randomContainer_No);
			GenericMethods.pauseExecution(5000);
			driver.findElement(By.id("containerNo"+i)).sendKeys(randomContainer_No);
			GenericMethods.action_Key(driver, Keys.TAB);
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.pauseExecution(2000);
			//GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "SealNo_1", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBLContainersDetails", i, "Seal_Num1", ScenarioDetailsHashMap), 20);
			driver.findElement(By.id("sealNo1"+i)).sendKeys(ExcelUtils.getCellData("SE_OBLContainersDetails", i, "Seal_Num1", ScenarioDetailsHashMap));
			//GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "SealNo_2", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBLContainersDetails", i, "Seal_Num2", ScenarioDetailsHashMap), 20);
			driver.findElement(By.id("sealNo2"+i)).sendKeys(ExcelUtils.getCellData("SE_OBLContainersDetails", i, "Seal_Num2", ScenarioDetailsHashMap));
			//GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Container_GrsWt", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBLContainersDetails", i, "GrossWeight", ScenarioDetailsHashMap), 20);
			driver.findElement(By.id("contGrossWt"+i)).sendKeys(ExcelUtils.getCellData("SE_OBLContainersDetails", i, "GrossWeight", ScenarioDetailsHashMap));
			//GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Container_Volume", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBLContainersDetails", i, "Volume", ScenarioDetailsHashMap), 20);
			driver.findElement(By.id("contVolume"+i)).sendKeys(ExcelUtils.getCellData("SE_OBLContainersDetails", i, "Volume", ScenarioDetailsHashMap));
			//GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Container_PickupDate", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBLContainersDetails", i, "PickUp_Date", ScenarioDetailsHashMap), 20);
			driver.findElement(By.id("pickUpReqDate"+i)).sendKeys(ExcelUtils.getCellData("SE_OBLContainersDetails", i, "PickUp_Date", ScenarioDetailsHashMap));
			//GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Container_PickupTime", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBLContainersDetails", i, "PickUp_Time", ScenarioDetailsHashMap), 20);
			driver.findElement(By.id("pickUpTime"+i)).sendKeys(ExcelUtils.getCellData("SE_OBLContainersDetails", i, "PickUp_Time", ScenarioDetailsHashMap));
			//GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Container_CargoReadyDate", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBLContainersDetails", i, "CargoReady_Date", ScenarioDetailsHashMap), 20);
			driver.findElement(By.id("cargoReadyDate"+i)).sendKeys(ExcelUtils.getCellData("SE_OBLContainersDetails", i, "CargoReady_Date", ScenarioDetailsHashMap));
			//GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Container_CargoReadyTime", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBLContainersDetails", i, "CargoReady_Time", ScenarioDetailsHashMap), 20);
			driver.findElement(By.id("cargoReadyTime"+i)).sendKeys(ExcelUtils.getCellData("SE_OBLContainersDetails", i, "CargoReady_Time", ScenarioDetailsHashMap));
			//GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpOBL.getElement(driver, "StuffPacksInContainerNo", 20), ExcelUtils.getCellData("SE_OBLContainersDetails", i, "ContainerNumber", ScenarioDetailsHashMap));
			GenericMethods.pauseExecution(2000);
			int j =i-1;
			//System.out.println("j value :"+j);
			Select containerNoList = new Select(driver.findElement(By.name("containerNoList_"+j)));
			containerNoList.selectByVisibleText(ExcelUtils.getCellData("SE_OBLContainersDetails", i, "ContainerNumber", ScenarioDetailsHashMap));
			GenericMethods.pauseExecution(3000);

		}



	}

	static int stuffed_CargoPackCount = 1;
	static boolean containerCreation = false;
	static int ContainerCreationID = 1;

	public static void seaMBLContainerDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "ContainerDetails_Tab", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(2000);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}
		int rowCount=ExcelUtils.getCellDataRowCount("SE_OBLContainersDetails", ScenarioDetailsHashMap);
		System.out.println("pictOceanExportsOBL_ContainerDetails rowCount::"+rowCount);
		//Creation of Containers
		for (int ContainerCreationRowID = 1; ContainerCreationRowID <= rowCount; ContainerCreationRowID++) 
		{
			boolean grid_ContainerNumberAvailability=false;
			WebElement ContainerDropdown_element = driver.findElement(By.xpath("//td[@id='CNTRDTLSAREA']/table/tbody/tr/td/table[1]/tbody/tr[2]/td[10]/select"));
			List<WebElement> dropdownvalues= new Select(ContainerDropdown_element).getOptions(); 
			for (int DropdownValue = 1; DropdownValue < dropdownvalues.size(); DropdownValue++) 
			{	if(dropdownvalues.get(DropdownValue).getText().trim().equals(ExcelUtils.getCellData("SE_OBLContainersDetails", ContainerCreationRowID, "ContainerNumber", ScenarioDetailsHashMap)))
			{	grid_ContainerNumberAvailability=true;
			break;
			}
			}
			if(!grid_ContainerNumberAvailability)
			{	if(containerCreation){
				GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "Button_ContainerDetailsAdd", ScenarioDetailsHashMap,10), 10);
				GenericMethods.pauseExecution(2000);
				ContainerCreationID=ContainerCreationID+1;
			}
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.id("containerType"+ContainerCreationID)));
			GenericMethods.pauseExecution(1000);
			try {
				GenericMethods.selectOptionFromDropDown(driver, By.id("containerType"+ContainerCreationID), null, ExcelUtils.getCellData("SE_OBLContainersDetails", ContainerCreationRowID, "ContainerType", ScenarioDetailsHashMap));	
			} catch (Exception e) {
			}
			GenericMethods.replaceTextofTextField(driver, By.id("containerNo"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_OBLContainersDetails", ContainerCreationRowID, "ContainerNumber", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, By.id("containerNo"+ContainerCreationID), null,  Keys.TAB, 20);
			GenericMethods.pauseExecution(15000);
			try{
				System.out.println("hanled alert");
				//driver.switchTo().alert().accept();
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				System.out.println("alert not came"+e);
			}

			GenericMethods.pauseExecution(6000);
			GenericMethods.replaceTextofTextField(driver, By.id("sealNo1"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_OBLContainersDetails", ContainerCreationRowID, "Seal_Num1", ScenarioDetailsHashMap), 20);
			GenericMethods.replaceTextofTextField(driver, By.id("sealNo2"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_OBLContainersDetails", ContainerCreationRowID, "Seal_Num2", ScenarioDetailsHashMap), 20);
			GenericMethods.replaceTextofTextField(driver, By.id("contGrossWt"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_OBLContainersDetails", ContainerCreationRowID, "GrossWeight", ScenarioDetailsHashMap), 20);
			GenericMethods.replaceTextofTextField(driver, By.id("contVolume"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_OBLContainersDetails", ContainerCreationRowID, "Volume", ScenarioDetailsHashMap), 20);
			GenericMethods.replaceTextofTextField(driver, By.id("pickUpReqDate"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_OBLContainersDetails", ContainerCreationRowID, "PickUp_Date", ScenarioDetailsHashMap), 20);
			GenericMethods.replaceTextofTextField(driver, By.id("pickUpTime"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_OBLContainersDetails", ContainerCreationRowID, "PickUp_Time", ScenarioDetailsHashMap), 20);
			GenericMethods.replaceTextofTextField(driver, By.id("cargoReadyDate"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_OBLContainersDetails", ContainerCreationRowID, "CargoReady_Date", ScenarioDetailsHashMap), 20);
			GenericMethods.replaceTextofTextField(driver, By.id("cargoReadyTime"+ContainerCreationID), null,  ExcelUtils.getCellData("SE_OBLContainersDetails", ContainerCreationRowID, "CargoReady_Time", ScenarioDetailsHashMap), 20);
			GenericMethods.pauseExecution(2000);
			containerCreation = true;
			}

			//Masthan added for container validations

			if(ExcelUtils.getCellData("SE_OBLContainersDetails", ContainerCreationRowID, "ContainerValidationsReq", ScenarioDetailsHashMap).equalsIgnoreCase("Yes")){

				Validate_DuplicateContainers(driver,ContainerCreationRowID,ScenarioDetailsHashMap,RowNo);

			}	
			//			}
		}

		//Stuffing the Cargo Packs into Container
		int noOfPackLines=driver.findElements(By.id("packSrNo")).size();
		System.out.println(noOfPackLines+"***noOfPackLines");
		int container=1;
		for (int testdata_stuffingCargoPackDetails = 1; testdata_stuffingCargoPackDetails <= noOfPackLines; testdata_stuffingCargoPackDetails++) 
			//			for (int testdata_stuffingCargoPackDetails = 1; testdata_stuffingCargoPackDetails <= ExcelUtils.getSubScenarioRowCount("SE_MBLCargoPackDetails", ScenarioDetailsHashMap); testdata_stuffingCargoPackDetails++)
		{
			//			int ContainerAssigningRowID =testdata_stuffingCargoPackDetails+1;
			//			String xpathPrefix="//td[@id='CNTRDTLSAREA']/table/tbody/tr/td/table["+stuffed_CargoPackCount+"]/tbody/tr["+ContainerAssigningRowID;
			try {
				if (container>rowCount) {
					container=1;
				}
				GenericMethods.pauseExecution(2000);
				GenericMethods.selectOptionFromDropDown(driver, By.xpath("(//*[@id='containerNoList'])["+testdata_stuffingCargoPackDetails+"]"), null, ExcelUtils.getCellData("SE_OBLContainersDetails", container, "ContainerNumber", ScenarioDetailsHashMap));
				//				GenericMethods.selectOptionFromDropDown(driver, By.xpath(xpathPrefix+"]/td[10]/select"), null, ExcelUtils.getCellData("SE_OBLContainersDetails", testdata_stuffingCargoPackDetails, "ContainerNumber", ScenarioDetailsHashMap));	
				container=container+1;
				if(ExcelUtils.getCellData("SE_OBLContainersDetails", testdata_stuffingCargoPackDetails, "ContainerValidationsReq", ScenarioDetailsHashMap).equalsIgnoreCase("Yes")){
					Validate_ContainerWeight(driver,testdata_stuffingCargoPackDetails,ScenarioDetailsHashMap,RowNo);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			//			GenericMethods.pauseExecution(1000);
			//			GenericMethods.replaceTextofTextField(driver, By.xpath(xpathPrefix+"]/td[11]/input"), null,  ExcelUtils.getCellData("SE_OBLContainersDetails", testdata_stuffingCargoPackDetails, "Container_Packages", ScenarioDetailsHashMap), 20);
			GenericMethods.pauseExecution(1000);
			//			GenericMethods.action_Key(driver, Keys.TAB);
		}
		stuffed_CargoPackCount=stuffed_CargoPackCount+1;

	}
	public static void Stuffing_CargoPack_Into_Container(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		//Stuffing the Cargo Packs into Container
			oceanExportsOBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "ContainerDetails_Tab", ScenarioDetailsHashMap, 10),30);
			GenericMethods.pauseExecution(2000);
			try{
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {
			}
			int rowCount=ExcelUtils.getCellDataRowCount("SE_OBLContainersDetails", ScenarioDetailsHashMap);
			System.out.println("pictOceanExportsOBL_ContainerDetails rowCount::"+rowCount);
		
		int noOfPackLines=driver.findElements(By.id("packSrNo")).size();
		System.out.println(noOfPackLines+"***noOfPackLines");
		int container=1;
		for (int testdata_stuffingCargoPack = 1; testdata_stuffingCargoPack <= noOfPackLines; testdata_stuffingCargoPack++) 
		{
			try {
				
				GenericMethods.pauseExecution(2000);
				GenericMethods.selectOptionFromDropDown(driver, By.xpath("(//*[@id='containerNoList'])["+testdata_stuffingCargoPack+"]"), null, ExcelUtils.getCellData("SE_OBLContainersDetails", container, "ContainerNumber", ScenarioDetailsHashMap));
				container=container+1;
				if(ExcelUtils.getCellData("SE_OBLContainersDetails", testdata_stuffingCargoPack, "ContainerValidationsReq", ScenarioDetailsHashMap).equalsIgnoreCase("Yes")){
					Validate_ContainerWeight(driver,testdata_stuffingCargoPack,ScenarioDetailsHashMap,RowNo);
				}
			} catch (Exception e) {
			}
			GenericMethods.pauseExecution(1000);
		}
		OBL_Save(driver, ScenarioDetailsHashMap, RowNo);
	}
	
	
	//Masthan
	public static void Validate_ContainerWeight(WebDriver driver,int testdata_stuffingCargoPackDetails,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		if(testdata_stuffingCargoPackDetails==1){
			GenericMethods.replaceTextofTextField(driver, By.xpath("//*[@id='stuffingTable_0']/tbody/tr[2]/td[11]/input[1]"), null,  ExcelUtils.getCellData("SE_OBLContainersDetails", testdata_stuffingCargoPackDetails, "ModifiedContainerPackages", ScenarioDetailsHashMap), 20);
			GenericMethods.replaceTextofTextField(driver, By.xpath("//*[@id='stuffingTable_0']/tbody/tr[2]/td[11]/input[1]"), null,  Keys.TAB, 20);
			GenericMethods.clickElement(driver,By.id("PICKUP_INSTRUCTIONS"),null, 10);
			GenericMethods.pauseExecution(3000);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "ContainerDetails_Tab", ScenarioDetailsHashMap, 10),30);
			GenericMethods.pauseExecution(3000);
			String grossModified=GenericMethods.locateElement(driver, By.xpath("//*[@id='stuffingTable_0']/tbody/tr[2]/td[13]/input[1]"), 3).getAttribute("previousvalue");
			String VolumeModified=GenericMethods.locateElement(driver, By.xpath("//*[@id='stuffingTable_0']/tbody/tr[2]/td[14]/input[1]"), 3).getAttribute("previousvalue");
			System.out.println("Gross&Volume"+grossModified+" "+VolumeModified);
			String PackagesUpdated=GenericMethods.locateElement(driver, By.xpath("//*[@id='stuffingTable_0']/tbody/tr[3]/td[11]/input[1]"), 3).getAttribute("value");
			String grossUpdated=GenericMethods.locateElement(driver, By.xpath("//*[@id='stuffingTable_0']/tbody/tr[3]/td[13]/input[1]"),3).getAttribute("previousvalue");
			String VolumeUpdated=GenericMethods.locateElement(driver, By.xpath("//*[@id='stuffingTable_0']/tbody/tr[3]/td[14]/input[1]"),3).getAttribute("previousvalue");
			System.out.println("Gross Weight U-->"+grossUpdated);
			System.out.println("Volume U-->"+VolumeUpdated);
			GenericMethods.assertTwoValues(grossModified,ExcelUtils.getCellData("SE_OBLContainersDetails", testdata_stuffingCargoPackDetails, "UpdatedGrossWeight", ScenarioDetailsHashMap), "Verifying Modified Gross Weight", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(VolumeModified,ExcelUtils.getCellData("SE_OBLContainersDetails", testdata_stuffingCargoPackDetails, "UpdatedVolume", ScenarioDetailsHashMap), "Verifying Modified Volume", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(PackagesUpdated,ExcelUtils.getCellData("SE_OBLContainersDetails", testdata_stuffingCargoPackDetails, "UpdatedContainerPackages", ScenarioDetailsHashMap), "Verifying Updated Packages", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(grossUpdated,ExcelUtils.getCellData("SE_OBLContainersDetails", testdata_stuffingCargoPackDetails, "UpdatedGrossWeight", ScenarioDetailsHashMap), "Verifying Updated Gross Weight", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(VolumeUpdated,ExcelUtils.getCellData("SE_OBLContainersDetails", testdata_stuffingCargoPackDetails, "UpdatedVolume", ScenarioDetailsHashMap), "Verifying Updated Volume", ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver,By.xpath("//*[@id='stuffingTable_0']/tbody/tr[3]/td[16]/img"),null, 10);
		}
	}
	//Masthan
	public static void Validate_DuplicateContainers(WebDriver driver,int ContainerCreationRowID,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "Button_ContainerDetailsAdd", ScenarioDetailsHashMap,10), 10);
		int ContainerRow=ContainerCreationID+1;
		//code for container numbers
		GenericMethods.replaceTextofTextField(driver, By.id("containerNo"+ContainerRow), null,  ExcelUtils.getCellData("SE_OBLContainersDetails", ContainerCreationRowID, "ContainerNumber", ScenarioDetailsHashMap), 20);
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
			if(alertMessage.contains(ExcelUtils.getCellData("SE_OBLContainersDetails", ContainerCreationRowID, "DuplicateAlertMessage", ScenarioDetailsHashMap))){
				GenericMethods.assertTwoValues(alertMessage, alertMessage, "Verifying alert for Duplicate Container numbers", ScenarioDetailsHashMap);
			}else{
				GenericMethods.assertTwoValues(alertMessage,"alert message not valid", "Verifying alert for Duplicate Container numbers", ScenarioDetailsHashMap);

			}
		}catch (Exception e) {
			GenericMethods.assertTwoValues("true","false", "alert for Duplicate containers not displayed", ScenarioDetailsHashMap);
		}

		//code for seal1

		GenericMethods.replaceTextofTextField(driver, By.id("sealNo1"+ContainerRow), null,  ExcelUtils.getCellData("SE_OBLContainersDetails", ContainerCreationRowID, "Seal_Num1", ScenarioDetailsHashMap), 20);
		GenericMethods.replaceTextofTextField(driver, By.id("sealNo1"+ContainerRow), null,  Keys.TAB, 20);
		GenericMethods.pauseExecution(2000);
		try{
			Alert alert=driver.switchTo().alert();	
			String alertMessage=alert.getText();
			alert.accept();
			if(alertMessage.contains(ExcelUtils.getCellData("SE_OBLContainersDetails", ContainerCreationRowID, "DuplicateAlertMessage", ScenarioDetailsHashMap))){
				GenericMethods.assertTwoValues(alertMessage, alertMessage, "Verifying alert for Duplicate Seal1 numbers", ScenarioDetailsHashMap);
			}else{
				GenericMethods.assertTwoValues(alertMessage,"alert message not valid", "Verifying alert for Duplicate Seal1 numbers", ScenarioDetailsHashMap);

			}
		}catch (Exception e) {
			GenericMethods.assertTwoValues("true","false", "alert for Duplicate Seal1 not displayed", ScenarioDetailsHashMap);
		}
		//code for seal2
		GenericMethods.replaceTextofTextField(driver, By.id("sealNo2"+ContainerRow), null,  ExcelUtils.getCellData("SE_OBLContainersDetails", ContainerCreationRowID, "Seal_Num2", ScenarioDetailsHashMap), 20);
		GenericMethods.replaceTextofTextField(driver, By.id("sealNo2"+ContainerRow), null,  Keys.TAB, 20);
		GenericMethods.pauseExecution(2000);
		try{
			Alert alert=driver.switchTo().alert();	
			String alertMessage=alert.getText();
			alert.accept();
			if(alertMessage.contains(ExcelUtils.getCellData("SE_OBLContainersDetails", ContainerCreationRowID, "DuplicateAlertMessage", ScenarioDetailsHashMap))){
				GenericMethods.assertTwoValues(alertMessage, alertMessage, "Verifying alert for Duplicate Seal2 numbers", ScenarioDetailsHashMap);
			}else{
				GenericMethods.assertTwoValues(alertMessage,"alert message not valid", "Verifying alert for Duplicate Seal2 numbers", ScenarioDetailsHashMap);

			}
		}catch (Exception e) {
			GenericMethods.assertTwoValues("true","false", "alert for Duplicate Seal2 not displayed", ScenarioDetailsHashMap);
		}

		GenericMethods.clickElement(driver,By.id("multiSelectCheckboxcontainerDetailsGrid"+ContainerRow),null, 10);
		GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "Container_Remove", ScenarioDetailsHashMap,10), 10);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}
		GenericMethods.pauseExecution(2000);

	}

	public static void pictOceanExportsOBL_VesselSchedule(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		try{
			//System.out.println("vessel required= "+ExcelUtils.getCellData("SE_OBLVesselSchedule", rowNo, "VesselSchedule_Req", ScenarioDetailsHashMap));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		String VesselScheduleID=ExcelUtils.getCellData("SE_OBLVesselSchedule", rowNo, "VesselSchedule_Req", ScenarioDetailsHashMap);

		if(VesselScheduleID.equalsIgnoreCase("Yes")){

			AppReusableMethods.selectValueFromLov(driver, orOceanExpOBL.getElement(driver, "VesselScheduleIDLov", ScenarioDetailsHashMap, 10), orOceanExpOBL, "Search_VesselScheduleID", ExcelUtils.getCellData("SE_OBLVesselSchedule", rowNo, "VesselSchedule", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(4000);
			String VesselEtdDate = orOceanExpOBL.getElement(driver, "Etd_Date", ScenarioDetailsHashMap, 10).getAttribute("value");
			//				GenericMethods.getInnerText(driver, null, orOceanExpOBL.getElement(driver, "Etd_Date", ScenarioDetailsHashMap, 10), 2);

			String VesselEtaDate = orOceanExpOBL.getElement(driver, "Eta_Date", ScenarioDetailsHashMap, 10).getAttribute("value");
			//				GenericMethods.getInnerText(driver, null, orOceanExpOBL.getElement(driver, "Eta_Date", ScenarioDetailsHashMap, 10), 2);
			//System.out.println("VesselEtdDate :"+VesselEtdDate);
			//System.out.println("VesselEtaDate :"+VesselEtaDate);
			/*	ExcelUtils.setCellData("SI_Arrival", rowNo, "ArrivalDateFrom",VesselEtaDate , ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_Arrival", rowNo, "ArrivalDateTo", VesselEtaDate, ScenarioDetailsHashMap);
			 */		}
		else{
			/*int rowCount=ExcelUtils.getCellDataRowCount("SE_OBLVesselSchedule", ScenarioDetailsHashMap);
			for (int i = 1; i <= rowCount; i++) {*/
			pictOceanExportsOBL_POL(driver, ScenarioDetailsHashMap, rowNo);
			pictOceanExportsOBL_POD(driver, ScenarioDetailsHashMap, rowNo);
			AppReusableMethods.selectValueFromLov(driver, orOceanExpOBL.getElement(driver, "VesselNameLov", ScenarioDetailsHashMap, 10), orOceanExpOBL, "Search_VesselName", ExcelUtils.getCellData("SE_OBLVesselSchedule", rowNo, "VesselName", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "VoyageName", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBLVesselSchedule", rowNo, "VoyageName", ScenarioDetailsHashMap), 20);
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Etd_Date", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBLVesselSchedule", rowNo, "ETD", ScenarioDetailsHashMap), 20);
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Etd_Time", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBLVesselSchedule", rowNo, "ETD_Time", ScenarioDetailsHashMap), 20);
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Eta_Date", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBLVesselSchedule", rowNo, "ETA", ScenarioDetailsHashMap), 20);
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Eta_Time", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SE_OBLVesselSchedule", rowNo, "ETA_Time", ScenarioDetailsHashMap), 20);
			GenericMethods.pauseExecution(4000);
			String VesselEtdDate = GenericMethods.getInnerText(driver, null, orOceanExpOBL.getElement(driver, "Etd_Date", ScenarioDetailsHashMap, 10), 2);
			String VesselEtaDate = GenericMethods.getInnerText(driver, null, orOceanExpOBL.getElement(driver, "Eta_Date", ScenarioDetailsHashMap, 10), 2);
			/*	ExcelUtils.setCellData("SI_Arrival", rowNo, "ArrivalDateFrom",VesselEtaDate , ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_Arrival", rowNo, "ArrivalDateTo", VesselEtaDate, ScenarioDetailsHashMap);
			 */
			//			}
			//			if (ExcelUtils.getCellData("SE_OBL", rowNo, "Legs_VesselDetailsFromMainDetails", ScenarioDetailsHashMap).equalsIgnoreCase("Multiple")) {
			int rowCount=ExcelUtils.getCellDataRowCount("SE_OBLVesselSchedule", ScenarioDetailsHashMap);
			for (int i = 1; i < rowCount; i++) {
				int j=i-1;
				GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "Vessel_AddBtn", ScenarioDetailsHashMap, 10),20);	
				GenericMethods.replaceTextofTextField(driver,By.id("routePortOfDispatch"+j+"DKCode"), null,  ExcelUtils.getCellData("SE_OBLVesselSchedule", i, "POD", ScenarioDetailsHashMap), 20);
				//				AppReusableMethods.selectValueFromLov(driver, orOceanExpOBL.getElement(driver, "VesselNameLov", ScenarioDetailsHashMap, 10), orOceanExpOBL, "Search_VesselName", ExcelUtils.getCellData("SE_OBLVesselSchedule", i, "VesselName", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, By.id("routeEtaDate"+j), null,  ExcelUtils.getCellData("SE_OBLVesselSchedule", i, "ETA", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("routeEtaTime"+j), null,  ExcelUtils.getCellData("SE_OBLVesselSchedule", i, "ETA_Time", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("routeVesselName"+i), null,  ExcelUtils.getCellData("SE_OBLVesselSchedule", i+1, "VesselName", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("routeVoyage"+i), null,  ExcelUtils.getCellData("SE_OBLVesselSchedule", i+1, "VoyageName", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("routeEtdDate"+i), null,  ExcelUtils.getCellData("SE_OBLVesselSchedule", i+1, "ETD", ScenarioDetailsHashMap), 20);
				GenericMethods.replaceTextofTextField(driver, By.id("routeEtdTime"+i), null,  ExcelUtils.getCellData("SE_OBLVesselSchedule", i+1, "ETD_Time", ScenarioDetailsHashMap), 20);
			}

			//			}
			GenericMethods.pauseExecution(4000);
		}
	}
	public static void pictOceanExportsOBL_Parties(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo){
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "Parties_Tab", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(2000);
		try{
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.handleAlert(driver, "accept");

		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		// ((JavascriptExecutor)driver).executeScript("return document.getElementById('PARTY_DETAILS');");
		//		int GridValueCount=orOceanExpOBL.getElements(driver, "Parties_Tab", 10).size();
		//		System.out.println("GridValueCount:::"+GridValueCount);
	}

	public static void pictOceanExportsOBL_FreightAndOthersCharges(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "FreightandOtherCharges_Tab", ScenarioDetailsHashMap, 20),30);
		GenericMethods.pauseExecution(5000);
		boolean alertPresent=GenericMethods.isAlertFound(driver);
		//System.out.println("insde loopll alert"+alertPresent);
		if(alertPresent){
			GenericMethods.handleAlert(driver, "accept");
		}
		try{
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.handleAlert(driver, "accept");

		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		if(ExcelUtils.getCellData("SE_OBL", rowNo, "eRatingValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			if(GenericMethods.locateElement(driver, By.id("ratingTypeMasterTemp"), 3).getAttribute("value").equalsIgnoreCase("T")){

				GenericMethods.assertTwoValues(GenericMethods.locateElement(driver, By.id("ratingIdMasterTemp"), 3).getAttribute("value"), ExcelUtils.getCellData("SE_OBL",rowNo, "VendorTariff", ScenarioDetailsHashMap), "Verifying Vendor Tarrif", ScenarioDetailsHashMap);


			}else{
				GenericMethods.assertTwoValues(GenericMethods.locateElement(driver, By.id("ratingIdMasterTemp"), 3).getAttribute("value"), ExcelUtils.getCellData("SE_OBL",rowNo, "CarrierContract", ScenarioDetailsHashMap), "Verifying Carrier Contract", ScenarioDetailsHashMap);

			}

			int rowCount=ExcelUtils.getCellDataRowCount("SE_OBLFreightOtherCharges", ScenarioDetailsHashMap);
			for (int Testdata_Row = 1; Testdata_Row<= rowCount; Testdata_Row++) {
				GenericMethods.locateElement(driver, By.id("dtBuyChargeBasis"+Testdata_Row), 3).click();
				String ChargeCode=GenericMethods.locateElement(driver, By.id("chargeCode"), 3).getAttribute("value");
				GenericMethods.locateElement(driver, By.id("buyQuantityStr"), 3).click();
				String Quantity=GenericMethods.locateElement(driver, By.id("buyQuantityStr"), 3).getAttribute("value");
				if(Quantity.contains(" "))
					Quantity=Quantity.replace(" ", "");
				GenericMethods.locateElement(driver, By.id("buyRateStr"), 3).click();
				String buyRate=GenericMethods.locateElement(driver, By.id("buyRateStr"), 3).getAttribute("value");
				if(buyRate.contains(" "))
					buyRate=buyRate.replace(" ", "");
				GenericMethods.locateElement(driver, By.id("buyAmountStr"), 3).click();
				String buyAmount=GenericMethods.locateElement(driver, By.id("buyAmountStr"), 3).getAttribute("value");
				if(buyAmount.contains(" "))
					buyAmount=buyAmount.replace(" ", "");
				double quan=Double.parseDouble(Quantity);
				double buyR=Double.parseDouble(buyRate);
				double buyAmou=Double.parseDouble(buyAmount);
				double amount=quan*buyR;
				String Am=Double.toString(amount);
				GenericMethods.assertTwoValues(ChargeCode, ExcelUtils.getCellData("SE_OBLFreightOtherCharges",Testdata_Row, "Charge_Code", ScenarioDetailsHashMap), "Verifying Charge Code for Line--> "+Testdata_Row, ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(buyRate, ExcelUtils.getCellData("SE_OBLFreightOtherCharges",Testdata_Row, "BuyRate", ScenarioDetailsHashMap), "Verifying Buy Rate for Charge Code--> "+ChargeCode, ScenarioDetailsHashMap);
				if(buyAmou==amount){

					GenericMethods.assertTwoValues(buyAmount,buyAmount, "Verifying Buy Amount for Charge Code--> "+ChargeCode, ScenarioDetailsHashMap);

				}else{

					GenericMethods.assertTwoValues(buyAmount,Am, "Verifying Buy Amount for Charge Code--> "+ChargeCode, ScenarioDetailsHashMap);

				}

			}
		}

	}

	public static void pictOceanExportsOBL_ChargesAndCosts(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "ChargesAndCosts_Tab", ScenarioDetailsHashMap, 20),30);
		GenericMethods.pauseExecution(5000);
		boolean alertPresent=GenericMethods.isAlertFound(driver);
		//System.out.println("insde loopll alert"+alertPresent);
		if(alertPresent){
			GenericMethods.handleAlert(driver, "accept");
		}
		try{
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		if(ExcelUtils.getCellData("SE_OBL", rowNo, "eRatingValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			int i=1;
			while(i>0){
				if(GenericMethods.locateElements(driver, By.id("chargesVOList.costStatusH"+i), 3).size()>0){

					try{
						String ReadyStatus=GenericMethods.locateElement(driver, By.id("chargesVOList.costStatusH"+i), 3).getAttribute("value");
						if(ReadyStatus.equalsIgnoreCase("READY"))
							GenericMethods.assertTwoValues(ReadyStatus, "READY", "Verifying READY Status of Charges in Grid for Line--> "+i, ScenarioDetailsHashMap);
						i=i+1;
					}catch(Exception e){
						i=0;
					}


				}else{
					System.out.println("Charges in Grid are not found");
					i=0;
				}
			}
			GenericMethods.locateElement(driver,By.name("accept"), 2).click();
			GenericMethods.pauseExecution(2000);

			int j=1;
			while(j>0){
				if(GenericMethods.locateElements(driver, By.id("spanchargesVOList.costStatus"+j), 3).size()>0){
					try{
						String ReadyStatus=GenericMethods.locateElement(driver, By.id("spanchargesVOList.costStatus"+j), 3).getText();
						if(ReadyStatus.equalsIgnoreCase("ACCEPTED"))
							GenericMethods.assertTwoValues(ReadyStatus, "ACCEPTED", "Verifying Accepted Status of Charges in Grid for Line--> "+j, ScenarioDetailsHashMap);
						j=j+1;
					}catch(Exception e){
						j=0;
					}
				}else{
					System.out.println("Charges in Grid are not found");
					j=0;
				}
			}

			GenericMethods.locateElement(driver,By.name("postCosts"), 2).click();
			try{
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {
				System.out.println("no Alerts present");
			}

			GenericMethods.pauseExecution(8000);
			GenericMethods.selectWindow(driver);
			String title=GenericMethods.getInnerText(driver, null, Common.getElement(driver, "BannerTitle", ScenarioDetailsHashMap,10), 2);
			//Assert.assertEquals(title, "Document Final Confirm");
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 4), 4);
			GenericMethods.pauseExecution(4000);
			// GenericMethods.selectWindow(driver);
			String title1=GenericMethods.getInnerText(driver, null, Common.getElement(driver, "BannerTitle", ScenarioDetailsHashMap,10), 2);

			GenericMethods.assertTwoValues(title1, "Purchase Invoice","Verifying Purchase Invoice Page Title",ScenarioDetailsHashMap);

			GenericMethods.clickElement(driver, null, Common.getElement(driver, "CancelButton", ScenarioDetailsHashMap, 4), 4);
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.switchToParent(driver);
			AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
			int k=1;
			while(k>0){
				if(GenericMethods.locateElements(driver, By.id("chargesVOList.costStatusH"+k), 3).size()>0){
					try{
						String ReadyStatus=GenericMethods.locateElement(driver, By.id("chargesVOList.costStatusH"+k), 3).getAttribute("value");
						if(ReadyStatus.equalsIgnoreCase("INVOICED"))
							GenericMethods.assertTwoValues(ReadyStatus, "INVOICED", "Verifying Invoiced Status of Charges in Grid for Line--> "+k, ScenarioDetailsHashMap); 
						k=k+1;
					}catch(Exception e){
						k=0;
					}

				}else{
					System.out.println("Charges in Grid are not found");
					k=0;
				}
			}

		}
	}

	public static void pictOceanExportsOBL_OverRide(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "OverRide_Tab", ScenarioDetailsHashMap, 20),30);
		GenericMethods.pauseExecution(5000);
		try{
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
	}
	public static void pictOceanExportsOBL_NotesAndStatus(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "NotesAndStatus_Tab", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(5000);
		try{
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
	}

	public static void pictOceanExportsOBL_EDoc(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "EDoc_Tab", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(5000);
		try{
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
	}
	public static void pictOceanExportsOBL_Consolidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo){

		GenericMethods.clickElement(driver, null,Common.getElement(driver, "Consolidation_Tab", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(2000);
		try{
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}


		//Sandeep- FUNC018.1- Below method is added to perform party ID mode validation.
		if(ExcelUtils.getCellData("SE_OBL", rowNo, "PartyIdModeRequired",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			if(ExcelUtils.getCellData("SE_OBL", rowNo, "PartyIdModeType",ScenarioDetailsHashMap).equalsIgnoreCase("PartyNickName"))
			{
				String PartyNickName = AppReusableMethods.selectValueFromLov(driver, orOceanExpOBL.getElement(driver, "LOV_Consolidation_Shipper",ScenarioDetailsHashMap, 10), orOceanExpOBL,"LOV_Consolidation_PartyId", ExcelUtils.getCellData("SE_OBL", rowNo, "Shipper_PartyId",ScenarioDetailsHashMap),"PartyNickName",ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, By.id("searchShipperIdNickName"), null, Keys.TAB, 30);
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(driver.findElement(By.id("searchShipperIdNickName")).getAttribute("previousvalue"), PartyNickName, "Verifying whether Shipper Name is displayed", ScenarioDetailsHashMap);
			}
			else
			{
				AppReusableMethods.selectValueFromLov(driver, orOceanExpOBL.getElement(driver, "LOV_Consolidation_Shipper", ScenarioDetailsHashMap, 10), orOceanExpOBL, "LOV_Consolidation_PartyId", ExcelUtils.getCellData("SE_OBL", rowNo, "Shipper_PartyId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, By.id("searchShipperIdNickName"), null, Keys.TAB, 30);
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(driver.findElement(By.id("searchShipperIdNickName")).getAttribute("previousvalue"), ExcelUtils.getCellData("SE_OBL", rowNo, "Shipper_PartyId",ScenarioDetailsHashMap), "Verifying whether Shipper Is is displayed", ScenarioDetailsHashMap);
			}

			driver.findElement(By.id("searchShipperIdNickName")).clear();
		}//code ends here



		//Sandeep- FUNC018.1- Below method is added to perform party ID mode validation.
		if(ExcelUtils.getCellData("SE_OBL", rowNo, "PartyIdModeRequired",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			if(ExcelUtils.getCellData("SE_OBL", rowNo, "PartyIdModeType",ScenarioDetailsHashMap).equalsIgnoreCase("PartyNickName"))
			{
				String PartyNickName = AppReusableMethods.selectValueFromLov(driver, orOceanExpOBL.getElement(driver, "LOV_Consolidation_Consignee",ScenarioDetailsHashMap, 10), orOceanExpOBL,"LOV_Consolidation_PartyId", ExcelUtils.getCellData("SE_OBL", rowNo, "ConsigneeNickName",ScenarioDetailsHashMap),"PartyNickName",ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, By.id("consoleSearchConsigneeIdNickName"), null, Keys.TAB, 30);
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(driver.findElement(By.id("consoleSearchConsigneeIdNickName")).getAttribute("previousvalue"), PartyNickName, "Verifying whether Consignee Name is displayed", ScenarioDetailsHashMap);
			}
			else
			{
				AppReusableMethods.selectValueFromLov(driver, orOceanExpOBL.getElement(driver, "LOV_Consolidation_Consignee", ScenarioDetailsHashMap, 10), orOceanExpOBL, "LOV_Consolidation_PartyId", ExcelUtils.getCellData("SE_OBL", rowNo, "ConsigneeNickName", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, By.id("consoleSearchConsigneeIdNickName"), null, Keys.TAB, 30);
				GenericMethods.pauseExecution(3000);
				GenericMethods.assertTwoValues(driver.findElement(By.id("consoleSearchConsigneeIdNickName")).getAttribute("previousvalue"), ExcelUtils.getCellData("SE_OBL", rowNo, "Shipper_PartyId",ScenarioDetailsHashMap), "Verifying whether Consignee Is is displayed", ScenarioDetailsHashMap);
			}

			driver.findElement(By.id("consoleSearchConsigneeIdNickName")).clear();
		}//code ends here
		//		int rowCount=ExcelUtils.getCellDataRowCount("SE_HBLMainDetails", ScenarioDetailsHashMap);
		//Updated code to add multiple hbls.- Prasanna Modugu
		int rowCount=0;
		System.out.println("HBLALLOcation"+ExcelUtils.getCellData("SE_OBL",rowNo,"HBLAllocation", ScenarioDetailsHashMap));
		ArrayList<String> values = new ArrayList<String>();
		if (ExcelUtils.getCellData("SE_OBL",rowNo,"HBLAllocation", ScenarioDetailsHashMap).equalsIgnoreCase("Multiple")) {
			rowCount=ExcelUtils.getSubScenarioRowCount("SE_HBLMainDetails", ScenarioDetailsHashMap);
			values=ExcelUtils.getAllCellValuesOfColumn("SE_HBLMainDetails","HBLId", ScenarioDetailsHashMap);
			System.out.println("rowcount if"+rowCount);
			System.out.println(values.get(0)+"hbl1"+values.get(1)+"hbl2");
		}
		else{
			rowCount=ExcelUtils.getCellDataRowCount("SE_OBL", ScenarioDetailsHashMap);
			System.out.println("rowcount else"+rowCount);
		}
		//System.out.println("pictOceanExportsOBL_MainDetailsContainer rowCount::"+rowCount);
		for (int i = 0; i < rowCount; i++) {
			//			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ConsolHBL_ID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails",i,"HBLId", ScenarioDetailsHashMap), 30);
			if(rowCount>1){
				//			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ConsolHBL_ID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails",i,"HBLId", ScenarioDetailsHashMap), 30);
				System.out.println(values.get(i).toString()+"hbl***"+i);
				GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ConsolHBL_ID", ScenarioDetailsHashMap, 10),values.get(i).toString(), 30);
				System.out.println(values.get(i).toString()+"hbl***"+i);
			}
			else{
				if (ExcelUtils.getSubScenarioRowCount("SE_HBLRoutePlanDetails",ScenarioDetailsHashMap)>0){

					String actualdatasetnum = ScenarioDetailsHashMap.get("DataSetNo");

					ScenarioDetailsHashMap.put("DataSetNo","1");
					System.out.println("route plan hbl value***"+ExcelUtils.getCellData("SE_HBLMainDetails",rowNo,"HBLId", ScenarioDetailsHashMap));
					GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ConsolHBL_ID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails",rowNo,"HBLId", ScenarioDetailsHashMap), 30);
					ScenarioDetailsHashMap.put("DataSetNo",actualdatasetnum);
					System.out.println("in if-else-if");
				}
				else{	
					GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ConsolHBL_ID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL",rowNo,"HBL_Id", ScenarioDetailsHashMap), 30);
					System.out.println("in if-else-else");
				}
			}
			GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "ConsoleSearch", ScenarioDetailsHashMap, 10),20);
			GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "ConsoleHouseAttachCheckBox", ScenarioDetailsHashMap, 10), 10);

		}
		//Sangeeta FUNC0056 validation Start
		if(ExcelUtils.getCellData("SE_OBL", rowNo, "ColorValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			seaOBL_ConsolidationShipmentColorsValidation(driver, ScenarioDetailsHashMap, rowNo);
		}
		//FUNC0056 validation End

		//Pavan FUNC044 Below method will perform House Update details 
		if(ExcelUtils.getCellData("SE_OBL", rowNo, "OBL_HyperLink_Required",ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "Link_HBL_Id", ScenarioDetailsHashMap, 10), 10);
			GenericMethods.pauseExecution(5000);
			GenericMethods.assertInnerText(driver, null, Common.getElement(driver, "BannerTitle", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_OBL",rowNo,"HBL_Title", ScenarioDetailsHashMap), "HBL_Title", "Validating HBL Screen title after Clicking on HBL_Id Hyperlink", ScenarioDetailsHashMap);
			String InstructionRemarksBefore=orOceanExpOBL.getElement(driver, "Textbox_HBL_InstructionOrRemarks", ScenarioDetailsHashMap, 10).getText();
			System.out.println("InstructionRemarksBefore:::"+InstructionRemarksBefore);
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Textbox_HBL_InstructionOrRemarks", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL",rowNo,"HBL_InstructionRemarks", ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10),20);
			GenericMethods.pauseExecution(2000);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
			}
			GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "Link_HBL_Id", ScenarioDetailsHashMap, 10), 10);
			String InstructionRemarksAfter=orOceanExpOBL.getElement(driver, "Textbox_HBL_InstructionOrRemarks", ScenarioDetailsHashMap, 10).getText();
			System.out.println("InstructionRemarksAfter:::"+InstructionRemarksAfter);
			if(InstructionRemarksBefore!=InstructionRemarksAfter){
				GenericMethods.assertTwoValues_NotEqual(InstructionRemarksAfter,InstructionRemarksBefore,"Validating Instruction Remarks Field Before and After Updated Values", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(ExcelUtils.getCellData("SE_OBL", rowNo, "HBL_Updated_ValidationMsg", ScenarioDetailsHashMap), ExcelUtils.getCellData("SE_OBL", rowNo, "HBL_Updated_ValidationMsg", ScenarioDetailsHashMap), "Validating Updated House Details after Updating Instruction Remarks Field", ScenarioDetailsHashMap);
			}
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10),20);
			GenericMethods.pauseExecution(2000);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
			}
		}//End FUNC044


		/*ArrayList<String> values = new ArrayList<String>();
		values=ExcelUtils.getAllCellValuesOfColumn("SE_HBLMainDetails","HBLId", ScenarioDetailsHashMap);
		ArrayList<String> slno= ExcelUtils.getAllCellValuesOfColumn("SE_PRSMainDetails", "sn", ScenarioDetailsHashMap);
		//System.out.println("...valuess.size()"+values.size());	
		//System.out.println(values+"values");
		for (int i = 1; i <= slno.size(); i++) {
			//System.out.println("slno.size() :"+slno.size());
			//System.out.println("Integer.parseInt(slno.get(i))"+Integer.parseInt(slno.get(i)));
			ExcelUtils.setCellData("SE_OBL",i, "HBL_ID",
					values.get(i-1), ScenarioDetailsHashMap);
			//GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ConsolHBL_ID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", i, "HBLId", ScenarioDetailsHashMap), 30);
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ConsolHBL_ID", ScenarioDetailsHashMap, 10), values.get(i-1), 30);
			GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "ConsoleSearch", ScenarioDetailsHashMap, 10),20);
			String Hbl_BookingId=orOceanExpOBL.getElement(driver, "GridHbl_BookingId", ScenarioDetailsHashMap, 10).getText();
			GenericMethods.pauseExecution(2000);
			if(Hbl_BookingId.equalsIgnoreCase(values.get(i-1))){
				GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "ConsoleHouseAttachCheckBox", ScenarioDetailsHashMap, 10), 10);
			}
		}*/
	}

	public static void pictOceanExportsOBL_PickUpInstructions(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo){
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "PickUpInstructions_Tab", ScenarioDetailsHashMap, 20),30);
		try{
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.handleAlert(driver, "accept");

		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
	}


	public static void pictOceanExportsOBL_AFR(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo){
		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "CHB_AFR", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(5000);
		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "Tab_AFR", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(5000);
		GenericMethods.handleAlert(driver, "accept");
		GenericMethods.assertInnerText(driver, null, orOceanExpOBL.getElement(driver, "Tab_AFR", ScenarioDetailsHashMap, 10), "AFR", "Tab AFR.", "Tab AFR.", ScenarioDetailsHashMap);
		/*if(ExcelUtils.getCellData("SE_OBLVesselSchedule",rowNo, "POL", ScenarioDetailsHashMap).contains("JP")){
		GenericMethods.assertInnerText(driver, null, orOceanExpOBL.getElement(driver, "AFR_LastPOL", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBLVesselSchedule",rowNo, "POL", ScenarioDetailsHashMap), "Last POL", "Last POL", ScenarioDetailsHashMap);
		}*/
	}


	public static void pictOceanExportsOBL_INTTRA(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo){
		boolean flag=orOceanExpOBL.getElement(driver, "CHB_Intrra", ScenarioDetailsHashMap, 10).isSelected();
		System.out.println("flag***"+flag);
		if (flag!=true) {
			GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "CHB_Intrra", ScenarioDetailsHashMap, 10),30);	
		}
		GenericMethods.pauseExecution(5000);
		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "Tab_Intrra", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(5000);
		//			GenericMethods.handleAlert(driver, "accept");
		GenericMethods.assertInnerText(driver, null, orOceanExpOBL.getElement(driver, "Tab_Intrra", ScenarioDetailsHashMap, 10), "INTTRA", "Tab INTTRA.", "Tab INTTRA.", ScenarioDetailsHashMap);	
		String txt=driver.findElement(By.xpath("//div[@class='showAll']")).getText();	
		System.out.println("check list"+txt);
		String[] str=txt.split("\n");
		for(int i=0;i<str.length;i++){
			if(str[i].equalsIgnoreCase("Freeze Cargo")){	
				System.out.println(str[i]+"...str..."+i);
				GenericMethods.clickElement(driver, By.xpath("//div[@class='showAll']/input["+(i+1)+"]"),null,30);
				break;
			}
		}
		System.out.println("1***"+str);
		GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpOBL.getElement(driver, "DDL_AllCharges", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA",rowNo, "AllCharges", ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Text_BlLocation", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA",rowNo, "B/L_Location", ScenarioDetailsHashMap), 10);
		AppReusableMethods.selectValueFromLov(driver, orOceanExpOBL.getElement(driver, "Button_BlLocationImage", ScenarioDetailsHashMap, 10), orOceanExpOBL, "Text_LocationCode", ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "B/L_Location", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		//			GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver,"Button_BlLocationImage", ScenarioDetailsHashMap, 10), 5);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Text_BLReleaseDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA",rowNo, "B/L_Release Date", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Text_OriginalBlFrt", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA",rowNo, "OriginalB/LsFRT/NonFRT", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Text_CopyBlFrt", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA",rowNo, "B/LCopy", ScenarioDetailsHashMap), 10);




	}	
	public static void OceanExportsOBL_INTTRA(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo){
		/*GenericMethods.pauseExecution(5000);
		AppReusableMethods.selectMenu(driver, ETransMenu.OceanExport_Master, "Ocean Consolidation", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ShipmentRefNo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_OBL", rowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap) , 10);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
		 */

		GenericMethods.pauseExecution(5000);
		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "Tab_Intrra", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(5000);

		//GenericMethods.handleAlert(driver, "accept");
		//GenericMethods.assertInnerText(driver, null, orOceanExpOBL.getElement(driver, "Tab_Intrra", ScenarioDetailsHashMap, 10), "INTTRA", "Tab INTTRA.", "Tab INTTRA.", ScenarioDetailsHashMap);

		INTTRA_Tab_DefaultvaluesValidation(driver, ScenarioDetailsHashMap, rowNo);
		String txt=driver.findElement(By.xpath("//div[@class='showAll']")).getText();	
		System.out.println("check list"+txt);
		String[] str=txt.split("\n");
		for(int i=0;i<str.length;i++){
			if(str[i].equalsIgnoreCase("Freeze Cargo")){	
				System.out.println(str[i]+"...str..."+i);
				GenericMethods.clickElement(driver, By.xpath("//div[@class='showAll']/input["+(i+1)+"]"),null,30);
				break;
			}
		}
		System.out.println("1***"+str);
		if(ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "IndividualCharges_Validation_Required", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			if(GenericMethods.isChecked(orOceanExpOBL.getElement(driver, "InttraIndividualCharges_Checkbox", ScenarioDetailsHashMap, 10)))
			{
				System.out.println("checked");
			}
			else
			{
				System.out.println("not checked");
				GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "InttraIndividualCharges_Checkbox", ScenarioDetailsHashMap, 10), 2);
			}
			GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "InttraIndividualCharges_img", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(3000);
			GenericMethods.selectWindow(driver);
			GenericMethods.pauseExecution(3000);
			GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpOBL.getElement(driver, "OceanFreight_Dropdown", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "IndividualCharges_OceanFreight", ScenarioDetailsHashMap));
			GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpOBL.getElement(driver, "OriginPortCharges_Dropdown", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "IndividualCharges_OriginPortCharges", ScenarioDetailsHashMap));
			GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpOBL.getElement(driver, "OriginHaulageCharges_Dropdown", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "IndividualCharges_OriginHaulageCharges", ScenarioDetailsHashMap));
			GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpOBL.getElement(driver, "AdditionalCharges_Dropdown", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "IndividualCharges_AdditionalCharges", ScenarioDetailsHashMap));
			GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpOBL.getElement(driver, "DestPortCharges_Dropdown", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "IndividualCharges_DestinationPortCharges", ScenarioDetailsHashMap));
			GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpOBL.getElement(driver, "DestinationHaulageCharges_Dropdown", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "IndividualCharges_DestinationHaulageCharges", ScenarioDetailsHashMap));
			GenericMethods.pauseExecution(3000);
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.switchToParent(driver);
			driver.switchTo().defaultContent();
			GenericMethods.pauseExecution(10000);
			GenericMethods.selectFrame(driver, null,Common.getElement(driver, "TextFrame", ScenarioDetailsHashMap,10), 2);
			GenericMethods.selectFrame(driver, null,Common.getElement(driver, "MainFrame", ScenarioDetailsHashMap,10), 2);
			GenericMethods.pauseExecution(3000);

		}
		else
		{
			GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpOBL.getElement(driver, "DDL_AllCharges", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA",rowNo, "AllCharges", ScenarioDetailsHashMap));
		}
		System.out.println("*****************************");
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Text_BlLocation", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA",rowNo, "B/L_Location", ScenarioDetailsHashMap), 10);
		AppReusableMethods.selectValueFromLov(driver, orOceanExpOBL.getElement(driver, "Button_BlLocationImage", ScenarioDetailsHashMap, 10), orOceanExpOBL, "Text_LocationCode", ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "B/L_Location", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		//			GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver,"Button_BlLocationImage", ScenarioDetailsHashMap, 10), 5);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Text_BLReleaseDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA",rowNo, "B/L_Release Date", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Text_OriginalBlFrt", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA",rowNo, "OriginalB/LsFRT/NonFRT", ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Text_CopyBlFrt", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA",rowNo, "B/LCopy", ScenarioDetailsHashMap), 10);

		Select DDL_ManifestFilerStatus = new Select(driver.findElement(By.id("manifestFilerstatus"))); 
		GenericMethods.assertTwoValues(DDL_ManifestFilerStatus.getFirstSelectedOption().getText(),  ExcelUtils.getCellData("SE_HBLINTTRA", rowNo, "ManifestFilerStatus",ScenarioDetailsHashMap), "Verifying Manifest Filer Status dropdown defaulted value", ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "BTN_INTTRA_Generate", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e){}


		/*String INTTRASuccessSummary="IKFC11235 : Origin Carrier Move Type Should not be left BlankIKFC11236 : Dest Carrier Move Type Should not be left Blank-> For Carrier,IKFC11248 : Contact Name Should not be left Blank.IKFC11241 : Goods Description is mandatory for Shipment reference : EHJFBGLLNLRT00005712 and Pack Sr no: 1" +"\n"+
		"IKFC11225 : ZNCM Code not mapped to Commodity: :2511IKFC11228 : For Pack SR.No:1" +"\n"+
		"IKFC11224 : Commodity is mandatoryIKFC11227 : HS Commodity is mandatoryIKFC11228 : For Pack SR.No:1";*/
		String INTTRAErrorSummary=GenericMethods.getInnerText(driver, null, orOceanExpOBL.getElement(driver, "Message_INTTRA_Message_SuccessfulSave", ScenarioDetailsHashMap, 20), 2);
		String INTTRA_Summary = INTTRAErrorSummary.replaceAll("<br */>", "");
		String MessageText=null;
		String [] InttraMSG= INTTRAErrorSummary.split(":");
		//System.out.println("------"+InttraMSG.length);
		for (int i = 0; i < InttraMSG.length; i++) {
			String MSG= InttraMSG[i].trim();
			if(MSG.contains("IKFC"))
			{
				MessageText = MSG.split("IKFC")[0].trim();
			}
			else
			{
				MessageText = MSG.trim();
			}
			//System.out.println(MessageText);
			if(MessageText.contains("Origin Carrier Move Type Should not be left Blank"))
			{
				GenericMethods.assertTwoValues(MessageText, ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "OriginMoveType_InttraMSG", ScenarioDetailsHashMap), "Validating inttra error message while Origin Carrier Move Type field is empty", ScenarioDetailsHashMap);
				System.out.println("Text1 ="+MessageText);

			}
			if(MessageText.contains("Dest Carrier Move Type Should not be left Blank-> For Carrier"))
			{
				GenericMethods.assertTwoValues(MessageText, ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "DestMoveType_InttraMSG", ScenarioDetailsHashMap), "Validating inttra error message while Destination Carrier Move Type field is empty", ScenarioDetailsHashMap);
				System.out.println("Text2 ="+MessageText);
			}
			if(MessageText.contains("Contact Name Should not be left Blank"))
			{
				GenericMethods.assertTwoValues(MessageText, ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "PartyContactName_InttraMSG", ScenarioDetailsHashMap), "Validating inttra error message while ContactName field is empty", ScenarioDetailsHashMap);
				System.out.println("Text3 ="+MessageText);
			}
			if(MessageText.contains("Goods Description is mandatory for Shipment reference"))
			{
				GenericMethods.assertTwoValues(MessageText, ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "GoodsDescription_InttraMSG", ScenarioDetailsHashMap), "Validating inttra error message while GoodsDescription field is empty in Cargo Details", ScenarioDetailsHashMap);
				System.out.println("Text4 ="+MessageText);
			}
			if(MessageText.contains("ZNCM Code not mapped to Commodity"))
			{
				GenericMethods.assertTwoValues(MessageText, ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "PortOfLoading_InttraMSG", ScenarioDetailsHashMap), "Validating inttra error message while PortOfLoading field is empty", ScenarioDetailsHashMap);
				System.out.println("Text5 ="+MessageText);
			}
			if(MessageText.matches("Commodity is mandatory"))
			{
				GenericMethods.assertTwoValues(MessageText, ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "Commodity_InttraMSG", ScenarioDetailsHashMap), "Validating inttra error message while Commodity field is empty", ScenarioDetailsHashMap);
				System.out.println("Text6 ="+MessageText);
			}
			if(MessageText.contains("HS Commodity is mandatory"))
			{
				GenericMethods.assertTwoValues(MessageText, ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "HSCommodity_InttraMSG", ScenarioDetailsHashMap), "Validating inttra error message while HSCommodity field is empty", ScenarioDetailsHashMap);
				System.out.println("Text7 ="+MessageText);
			}
			if(MessageText.contains("Container Type 20DB Equipment Owner Should not be left Blank."))
			{
				GenericMethods.assertTwoValues(MessageText, ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "ContainerTypeEquipmentOwner_InttraMSG", ScenarioDetailsHashMap), "Validating inttra error message while Container Type Equipment Owner is not given", ScenarioDetailsHashMap);
				System.out.println("Text8 ="+MessageText);

			}

		}


		HBLUpdateInttraMessageValidation(driver, ScenarioDetailsHashMap, rowNo);

		GenericMethods.pauseExecution(5000);
		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "Tab_Intrra", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(5000);GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "BTN_INTTRA_Generate", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e){}
		String INTTRASuccessSummary=GenericMethods.getInnerText(driver, null, orOceanExpOBL.getElement(driver, "Message_INTTRA_Message_SuccessfulSave", ScenarioDetailsHashMap, 20), 2);
		String INTTRASummary = INTTRASuccessSummary.replaceAll("<br */>", "");
		System.out.println("INTTRASuccessSummary="+INTTRASuccessSummary);
		System.out.println("INTTRA_Summary="+INTTRASummary);
		GenericMethods.assertTwoValues(INTTRASuccessSummary.split(" : ")[1], ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "Message_SuccessfulSave",ScenarioDetailsHashMap), "Verifying Success Message in INTTRA Tab", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(INTTRASuccessSummary.split(" : ")[3], ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "Message_RecordSubmitted",ScenarioDetailsHashMap), "Verifying Record Submitted Message in INTTRA Tab", ScenarioDetailsHashMap);

		GenericMethods.assertInnerText(driver, By.id("dtStatus1"), null,  ExcelUtils.getCellData("SE_OBL_INTTRA", rowNo, "Status",ScenarioDetailsHashMap), "Grid_INTTRA Status", "Verifying INTTRA Status in the grid", ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton",ScenarioDetailsHashMap, 10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			System.out.println("no Alerts present");
		}

		GenericMethods.pauseExecution(3000);


	}	


	//Pavan  FUNC007--Below method will perform validation for Location to Terminal Mapping
	public static void Location_Terminal_Validations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){

		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Textbox_PortOfLoading", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL", RowNo, "Location_POL",ScenarioDetailsHashMap), 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Textbox_PortOfDestination", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL", RowNo, "Location_POD",ScenarioDetailsHashMap), 10);
		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "Tab_Parties", ScenarioDetailsHashMap,10), 2);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
		}
		GenericMethods.pauseExecution(5000);
		String Location_Terminal_Msg=driver.findElement(By.id("portOfLoadingDKCode")).getAttribute("title");
		System.out.println("Location_Terminal_Msg::"+Location_Terminal_Msg);
		ExcelUtils.setCellData("SE_OBL", RowNo, "Location_Validation_Alert", Location_Terminal_Msg, ScenarioDetailsHashMap);
		if(Location_Terminal_Msg.equalsIgnoreCase(ExcelUtils.getCellData("SE_OBL", RowNo, "Location_Validation_Alert", ScenarioDetailsHashMap))){
			GenericMethods.assertTwoValues(ExcelUtils.getCellData("SE_OBL", RowNo, "Location_Validation_Msg", ScenarioDetailsHashMap), ExcelUtils.getCellData("SE_OBL", RowNo, "Location_Validation_Msg", ScenarioDetailsHashMap), "Validating PortOfLanding Location to Terminal Mapping in OBL Module", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(ExcelUtils.getCellData("SE_OBL", RowNo, "Location_Validation_Msg", ScenarioDetailsHashMap), ExcelUtils.getCellData("SE_OBL", RowNo, "Location_Validation_Msg", ScenarioDetailsHashMap), "Validating PortOfDischarge Location to Terminal Mapping in OBL Module", ScenarioDetailsHashMap);
			pictOceanExportsOBL_POL(driver, ScenarioDetailsHashMap, RowNo);
			pictOceanExportsOBL_POD(driver, ScenarioDetailsHashMap, RowNo);

		}

	}// FUNC007 End




	//FUNC012.1 and	FUNC012.2 validation (Author-Sangeeta M)
	public static void seaOBL_FreightAndOtherChargesValidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		if(ExcelUtils.getCellData("SE_OBL", RowNo, "FreightChargesValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			AppReusableMethods.selectMenu(driver, ETransMenu.OceanExport_Master, "Ocean Consolidation", ScenarioDetailsHashMap);
			//GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Search_MblId", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_OBL", rowNo, "OBL_Id", ScenarioDetailsHashMap) , 10);
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ShipmentRefNo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap) , 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "Consolidation_Tab", ScenarioDetailsHashMap, 10),30);
			GenericMethods.pauseExecution(2000);
			try{
				GenericMethods.handleAlert(driver, "accept");
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {
				//System.out.println("no Alerts present");
			}
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ConsolHBL_ID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL",RowNo,"HBL_Id", ScenarioDetailsHashMap), 30);
			GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "ConsoleSearch", ScenarioDetailsHashMap, 10),20);
			String ExpTotalPieces = driver.findElement(By.id("dtPkgReceivedStr1")).getAttribute("title");
			String ExpTotalVolume = new DecimalFormat("##.00").format(Float.parseFloat(driver.findElement(By.id("dtVolumeStr1")).getAttribute("title")));
			System.out.println("ExpTotalVolume"+ExpTotalVolume);
			String ExpTotalGrossWeight = new DecimalFormat("##.00").format(Float.parseFloat(driver.findElement(By.id("dtWeightStr1")).getAttribute("title")));
			GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "DOC_CHARGES_Tab", ScenarioDetailsHashMap, 10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {
				e.printStackTrace();
			}
			GenericMethods.pauseExecution(3000);

			/*	//Closing opened Mail window
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
					}*/
			String ActTotalPieces = driver.findElement(By.name("totalPiecesTemp")).getAttribute("value");
			String ActTotalVolume = new DecimalFormat("##.00").format(Float.parseFloat(driver.findElement(By.name("volumeTemp")).getAttribute("value")));
			String ActTotalGrossWeight = new DecimalFormat("##.00").format(Float.parseFloat(driver.findElement(By.name("weightTemp")).getAttribute("value").replace(" ", "")));
			GenericMethods.assertTwoValues(ActTotalPieces,ExpTotalPieces, "Validating defaulting of the Charge Details of Pieces from Consolidation  screen.", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(ActTotalVolume,ExpTotalVolume, "Validating defaulting of the Charge Details of Volumes from Consolidation screen.", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(ActTotalGrossWeight,ExpTotalGrossWeight, "Validating defaulting of the Charge Details of Weight from Consolidation screen.", ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "ChargeBasisGrid", ScenarioDetailsHashMap, 10), 2);
			if(ExcelUtils.getCellData("SE_OBLFreightOtherCharges", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("LCL"))
			{
				GenericMethods.assertTwoValues(driver.findElement(By.id("buyChargeBasis")).getAttribute("value"), "B061", "Verifying the Freight default Charge Basis as B061 When load type is LCL", ScenarioDetailsHashMap);
				//String BuyQuantity = new DecimalFormat("##.00").format(Float.parseFloat(driver.findElement(By.id("buyQuantityStr")).getAttribute("value")));
				GenericMethods.assertTwoValues(new DecimalFormat("##.00").format(Float.parseFloat(driver.findElement(By.id("buyQuantityStr")).getAttribute("value"))), ExpTotalVolume, "Verifying the Freight Buy Quantity as Total Charge Volume When load type is LCL", ScenarioDetailsHashMap);
			}
			else if(ExcelUtils.getCellData("SE_OBLFreightOtherCharges", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("FCL"))
			{

				GenericMethods.clickElement(driver, By.xpath("html/body/form/table/tbody/tr[8]/td/div/table/tbody/tr[1]"), null, 10);
				GenericMethods.assertTwoValues(driver.findElement(By.id("buyChargeBasis")).getAttribute("value"), "B060", "Verifying the Freight default Charge Basis as B060 When load type is FCL", ScenarioDetailsHashMap);

			}
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "BuyMinAmount", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SE_OBLFreightOtherCharges", RowNo, "MinBuyAmount", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "BuyRate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBLFreightOtherCharges", RowNo, "BuyRate", ScenarioDetailsHashMap), 2);
			GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "BuyAmount", ScenarioDetailsHashMap, 10), 2);
			String BuyQuantity = driver.findElement(By.name("buyQuantityStr")).getAttribute("value");
			String BuyRate = driver.findElement(By.name("buyRateStr")).getAttribute("value");
			float MinumBuyAmount = Float.parseFloat(driver.findElement(By.id("buyMinAmountStr")).getAttribute("value"));
			float ActualBuyAmount = (Float.parseFloat(BuyQuantity))*(Float.parseFloat(BuyRate));
			System.out.println("Amount"+driver.findElement(By.name("buyAmountStr")).getAttribute("value"));
			//float ExpBuyAmount = Float.parseFloat(driver.findElement(By.name("buyAmountStr")).getAttribute("value"));
			String Exp_BuyAmount =driver.findElement(By.name("buyAmountStr")).getAttribute("value");
			float ExpBuyAmount ;
			if(Exp_BuyAmount.contains(" "))
			{
				ExpBuyAmount = Float.parseFloat(Exp_BuyAmount.replace(" ", ""));
			}
			else
			{
				ExpBuyAmount = Float.parseFloat(Exp_BuyAmount);
			}
			//float ExpBuyAmount = Float.parseFloat(Exp_BuyAmount.replace(" ", ""));
			if(MinumBuyAmount>ActualBuyAmount)
			{
				GenericMethods.assertTwoValues(MinumBuyAmount+"", ExpBuyAmount+"", "validating calculation of BuyAmount", ScenarioDetailsHashMap);
			}
			else
			{
				GenericMethods.assertTwoValues(ActualBuyAmount+"", ExpBuyAmount+"", "validating calculation of BuyAmount", ScenarioDetailsHashMap);
			}

			if(ExcelUtils.getCellData("SE_OBLFreightOtherCharges", RowNo, "LoadType", ScenarioDetailsHashMap).equalsIgnoreCase("LCL"))
			{
				System.out.println("entered");
				String Buy_Quantity = new DecimalFormat("##.00").format(Float.parseFloat(BuyQuantity));
				//System.out.println("Buy_Quantity"+Buy_Quantity);	
				GenericMethods.assertTwoValues(Buy_Quantity, ExpTotalVolume, "Verifying the Freight Buy Quantity as Total Charge Volume When load type is LCL", ScenarioDetailsHashMap);
			}
			GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "FrieghtChrgesEditBtn", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(2000);

			GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "ChargeBasisGrid", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {
			}
			GenericMethods.pauseExecution(5000);
		}
		//FUNC012.1 and	FUNC012.2 validation End(Author-Sangeeta M)


	}

	//FUNC0056 validation Start(Author-Sangeeta M)
	public static void seaOBL_ConsolidationShipmentColorsValidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		/*	AppReusableMethods.selectMenu(driver, ETransMenu.OceanExport_Master, "Ocean Consolidation", ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ShipmentRefNo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap) , 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
				GenericMethods.pauseExecution(2000);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "Consolidation_Tab", ScenarioDetailsHashMap, 10),30);
				GenericMethods.pauseExecution(2000);
				try{
					GenericMethods.handleAlert(driver, "accept");
					GenericMethods.handleAlert(driver, "accept");
				}catch (Exception e) {
					//System.out.println("no Alerts present");
				}

				int rowCount=ExcelUtils.getCellDataRowCount("SE_OBL", ScenarioDetailsHashMap);
				System.out.println("rowCount::"+rowCount);
				for (int rowNo = 1; rowNo <= rowCount; rowNo++) {
					GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ConsolHBL_ID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL",rowNo,"HBL_Id", ScenarioDetailsHashMap), 30);
					GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "ConsoleSearch", ScenarioDetailsHashMap, 10),20);
					GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "ConsoleHouseAttachCheckBox", ScenarioDetailsHashMap, 10), 10);
		 */	

		/*
		 * BNR - Booked but not Received - RED Color
		 * BR - Booked and Received - GREEN Color
		 * NBR - Not Booked but Received - BLUE Color
		 */
		if(driver.findElement(By.id("dtShipperIdNickName1")).getAttribute("style").equalsIgnoreCase("color: rgb(51, 204, 0);"))
		{
			String BR ="GREEN";
			GenericMethods.assertTwoValues(BR, "GREEN", "Validating House of the Color which is Booked and Received", ScenarioDetailsHashMap);
		}
		else if(driver.findElement(By.id("dtShipperIdNickName1")).getAttribute("style").equalsIgnoreCase("color: rgb(204, 0, 0);"))
		{
			String BNR = "BROWN";
			GenericMethods.assertTwoValues(BNR, "BROWN", "Validating House of the Color which is Booked but not Received", ScenarioDetailsHashMap);
		}
		else if(driver.findElement(By.id("dtShipperIdNickName1")).getAttribute("style").equalsIgnoreCase("color: rgb(0, 102, 255);"))
		{
			String NBR = "BLUE";
			GenericMethods.assertTwoValues(NBR, "BLUE", "Validating House of the Color which is not Booked but Received", ScenarioDetailsHashMap);
		}
		else
		{
			String BPR_NPR  = "BLACK";
			GenericMethods.assertTwoValues(BPR_NPR, "BLACK", "Validating House which is Booked and Partially Received / Not Booked but Partially Received", ScenarioDetailsHashMap);
		}	
	}
	//FUNC0056 validation End(Author-Sangeeta M)


	//Sangeeta RAT-48(This method will compare ETD date of OBL with siginificant date as per decision table in Charge/Cost page)
	public static void seaOBL_SignificantDateValidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		if(ExcelUtils.getCellData("SE_OBL", RowNo, "SignificantDateValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			AppReusableMethods.selectMenu(driver, ETransMenu.OceanExport_Master, "Ocean Consolidation", ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ShipmentRefNo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap) , 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "MAIN_DETAILS_Tab", ScenarioDetailsHashMap, 10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				e.printStackTrace();
			}
			GenericMethods.pauseExecution(3000);
			String HBLDate = driver.findElement(By.id("etdDate")).getAttribute("value");
			GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "CHARGES_COST_Tab", ScenarioDetailsHashMap, 10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				e.printStackTrace();
			}
			GenericMethods.pauseExecution(3000);
			String SignificantDate = driver.findElement(By.name("execDate")).getAttribute("value");
			GenericMethods.assertTwoValues(SignificantDate, HBLDate, "Validating Significant date in Ocean Consolidation Charges/Costs page.", ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {

			}
		}
	}
	public static void INTTRA_Tab_DefaultvaluesValidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo)
	{

		GenericMethods.assertTwoValues(GenericMethods.getSelectedOptionOfDropDown(driver, null, orOceanExpOBL.getElement(driver, "Inttra_CommentsType", ScenarioDetailsHashMap, 10)), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Inttra_CommentsType", ScenarioDetailsHashMap), "Validating Comments Type field value defaultation in Inttra tab of HBL module", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.getSelectedOptionOfDropDown(driver, null, orOceanExpOBL.getElement(driver, "Inttra_OceanFreightPaybleAt", ScenarioDetailsHashMap, 10)), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Inttra_OceanFreightPaybleAt", ScenarioDetailsHashMap), "Validating Ocean Freight Payable at field value defaultation in Inttra tab of HBL module", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.getSelectedOptionOfDropDown(driver, null, orOceanExpOBL.getElement(driver, "Inttra_BLDocType", ScenarioDetailsHashMap, 10)), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Inttra_BLDocType", ScenarioDetailsHashMap), "Validating B/L Doc Type field value defaultation in Inttra tab of HBL module", ScenarioDetailsHashMap);
		//GenericMethods.assertTwoValues(GenericMethods.getSelectedOptionOfDropDown(driver, null, orOceanExpOBL.getElement(driver, "Inttra_AllCharges", ScenarioDetailsHashMap, 10)), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Inttra_AllCharges", ScenarioDetailsHashMap), "Validating All Charges field value defaultation in Inttra tab of HBL module", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.getSelectedOptionOfDropDown(driver, null, orOceanExpOBL.getElement(driver, "Inttra_OriginalBlFreightType", ScenarioDetailsHashMap, 10)), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Inttra_OriginalBlFreightType", ScenarioDetailsHashMap), "Validating Original B/Ls FRT / Non FRT field value defaultation in Inttra tab of HBL module", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.getSelectedOptionOfDropDown(driver, null, orOceanExpOBL.getElement(driver, "Inttra_NonNegotiableFreightType", ScenarioDetailsHashMap, 10)), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Inttra_NonNegotiableFreightType", ScenarioDetailsHashMap), "Validating Non-Negotiable FRT / Non FRT field value defaultation in Inttra tab of HBL module", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.getSelectedOptionOfDropDown(driver, null, orOceanExpOBL.getElement(driver, "Inttra_SeaWayType", ScenarioDetailsHashMap, 10)), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Inttra_SeaWayType", ScenarioDetailsHashMap), "Validating Sea Way / Express field value defaultation in Inttra tab of HBL module", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.getSelectedOptionOfDropDown(driver, null, orOceanExpOBL.getElement(driver, "Inttra_CopyBlFreightType", ScenarioDetailsHashMap, 10)), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Inttra_CopyBlFreightType", ScenarioDetailsHashMap), "Validating CB/L Copy field value defaultation in Inttra tab of HBL module", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues(GenericMethods.getSelectedOptionOfDropDown(driver, null, orOceanExpOBL.getElement(driver, "Inttra_ManifestFilerstatus", ScenarioDetailsHashMap, 10)), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Inttra_ManifestFilerstatus", ScenarioDetailsHashMap), "Validating Manifest Filer Status field value defaultation in Inttra tab of HBL module", ScenarioDetailsHashMap);
	}
	public static void PartiesTab_PartyTypeDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null, orOceanExpOBL.getElement(driver, "Tab_Parties", ScenarioDetailsHashMap, 10), 2);
		//orOceanExpOBL.getElement(driver, "Tab_Parties", ScenarioDetailsHashMap,10).click();
		try{
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		GenericMethods.pauseExecution(3000);
		//logic to traverse table
		int partyTable= orOceanExpOBL.getElements(driver, "table_addressDtlsGrid", ScenarioDetailsHashMap, 10).size();
		if(partyTable>0){
			for (int Grid_RowNo = 1; Grid_RowNo < partyTable; Grid_RowNo++) {
				String partyXpath="//div[@id='divaddressDtlsGrid']/table/tbody/tr["+Grid_RowNo+"]/td[1]";
				if(GenericMethods.getInnerText(driver, By.xpath(partyXpath), null, 10).equalsIgnoreCase("CARRIER"))
				{
					GenericMethods.pauseExecution(3000);
					GenericMethods.clickElement(driver, By.xpath(partyXpath), null, 10);
					System.out.println("XL value="+ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Carrier_AddressName2", ScenarioDetailsHashMap)+"--- "+RowNo+"----");
					System.out.println("Name2 value"+GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "AddressName2", ScenarioDetailsHashMap, 10), null));
					System.out.println("localAddress value"+GenericMethods.readValue(driver, null, By.id("localAddress")));
					GenericMethods.pauseExecution(3000);
					GenericMethods.assertTwoValues(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "AddressName2", ScenarioDetailsHashMap, 10), null), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Carrier_AddressName2", ScenarioDetailsHashMap), "verifying Carrier AddressName2 defaultation from MDM in Ocean Consolidation Party tab", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Textbox_PartyTab_ContactName", ScenarioDetailsHashMap, 10), null), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "CarrierContactName", ScenarioDetailsHashMap), "verifying Carrier ContactName defaultation from MDM in Ocean Consolidation Party tab", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Parties_Email", ScenarioDetailsHashMap, 10), null), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Carrier_Email", ScenarioDetailsHashMap), "verifying Carrier Email defaultation from MDM in Ocean Consolidation Party tab", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Parties_OfficeTel", ScenarioDetailsHashMap, 10), null), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "CarrierTelephone", ScenarioDetailsHashMap), "verifying Carrier Telephone defaultation from MDM in Ocean Consolidation Party tab", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Parties_OfficeFax", ScenarioDetailsHashMap, 10), null), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "CarrierFax", ScenarioDetailsHashMap), "verifying Carrier Fax defaultation from MDM in Ocean Consolidation Party tab", ScenarioDetailsHashMap);

					if(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "AddressName2", ScenarioDetailsHashMap, 10), null).equalsIgnoreCase(""))
					{
						GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "AddressName2", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Carrier_AddressName2", ScenarioDetailsHashMap), 10);
					}
					if(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Textbox_PartyTab_ContactName", ScenarioDetailsHashMap, 10), null).equalsIgnoreCase(""))
					{
						GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Textbox_PartyTab_ContactName", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "CarrierContactName", ScenarioDetailsHashMap), 10);
					}
					if(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Parties_Email", ScenarioDetailsHashMap, 10), null).equalsIgnoreCase(""))
					{
						GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Parties_Email", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Carrier_Email", ScenarioDetailsHashMap), 10);
					}
					if(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Parties_OfficeTel", ScenarioDetailsHashMap, 10), null).equalsIgnoreCase(""))
					{
						GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Parties_OfficeTel", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "CarrierTelephone", ScenarioDetailsHashMap), 10);
					}
					if(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Parties_OfficeFax", ScenarioDetailsHashMap, 10), null).equalsIgnoreCase(""))
					{
						GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Parties_OfficeFax", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "CarrierFax", ScenarioDetailsHashMap), 10);
					}
					GenericMethods.pauseExecution(3000);

				}
				else if(GenericMethods.getInnerText(driver, By.xpath(partyXpath), null, 10).equalsIgnoreCase("ORIGIN BRANCH"))
				{
					GenericMethods.pauseExecution(3000);
					GenericMethods.clickElement(driver, By.xpath(partyXpath), null, 10);
					GenericMethods.pauseExecution(3000);
					GenericMethods.assertTwoValues(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "AddressName2", ScenarioDetailsHashMap, 10), null), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "OriginBranch_AddressName2", ScenarioDetailsHashMap), "verifying OriginBranch AddressName2 defaultation from MDM in Ocean Consolidation Party tab", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Textbox_PartyTab_ContactName", ScenarioDetailsHashMap, 10), null), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "OriginBranchContactName", ScenarioDetailsHashMap), "verifying OriginBranch ContactName defaultation from MDM in Ocean Consolidation Party tab", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Parties_Email", ScenarioDetailsHashMap, 10), null), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "OriginBranchEmail", ScenarioDetailsHashMap), "verifying OriginBranch Email defaultation from MDM in Ocean Consolidation Party tab", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Parties_OfficeTel", ScenarioDetailsHashMap, 10), null), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "OriginBranchTelephone", ScenarioDetailsHashMap), "verifying OriginBranch Telephone defaultation from MDM in Ocean Consolidation Party tab", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Parties_OfficeFax", ScenarioDetailsHashMap, 10), null), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "OriginBranchFax", ScenarioDetailsHashMap), "verifying OriginBranch Fax defaultation from MDM in Ocean Consolidation Party tab", ScenarioDetailsHashMap);
					if(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "AddressName2", ScenarioDetailsHashMap, 10), null).equalsIgnoreCase(""))
					{
						GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "AddressName2", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "OriginBranch_AddressName2", ScenarioDetailsHashMap), 10);
					}
					if(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Textbox_PartyTab_ContactName", ScenarioDetailsHashMap, 10), null).equalsIgnoreCase(""))
					{
						GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Textbox_PartyTab_ContactName", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "OriginBranchContactName", ScenarioDetailsHashMap), 10);
					}
					if(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Parties_Email", ScenarioDetailsHashMap, 10), null).equalsIgnoreCase(""))
					{
						GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Parties_Email", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "OriginBranchEmail", ScenarioDetailsHashMap), 10);
					}
					if(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Parties_OfficeTel", ScenarioDetailsHashMap, 10), null).equalsIgnoreCase(""))
					{
						GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Parties_OfficeTel", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "OriginBranchTelephone", ScenarioDetailsHashMap), 10);
					}
					if(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Parties_OfficeFax", ScenarioDetailsHashMap, 10), null).equalsIgnoreCase(""))
					{
						GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Parties_OfficeFax", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "OriginBranchFax", ScenarioDetailsHashMap), 10);
					}
					GenericMethods.pauseExecution(3000);

				}
				else if(GenericMethods.getInnerText(driver, By.xpath(partyXpath), null, 10).equalsIgnoreCase("DESTINATION BRANCH"))
				{
					GenericMethods.pauseExecution(3000);
					GenericMethods.clickElement(driver, By.xpath(partyXpath), null, 10);
					GenericMethods.pauseExecution(3000);
					GenericMethods.assertTwoValues(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "AddressName2", ScenarioDetailsHashMap, 10), null), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "DestBranch_AddressName2", ScenarioDetailsHashMap), "verifying Destination AddressName2 defaultation from MDM in Ocean Consolidation Party tab", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Textbox_PartyTab_ContactName", ScenarioDetailsHashMap, 10), null), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "DestBranchContactName", ScenarioDetailsHashMap), "verifying Destination ContactName defaultation from MDM in Ocean Consolidation Party tab", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Parties_Email", ScenarioDetailsHashMap, 10), null), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "DestinationBranchEMail", ScenarioDetailsHashMap), "verifying Destination Email defaultation from MDM in Ocean Consolidation Party tab", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Parties_OfficeTel", ScenarioDetailsHashMap, 10), null), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "DestinationTelephone", ScenarioDetailsHashMap), "verifying Destination Telephone defaultation from MDM in Ocean Consolidation Party tab", ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Parties_OfficeFax", ScenarioDetailsHashMap, 10), null), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "DestinationFax", ScenarioDetailsHashMap), "verifying Destination Fax defaultation from MDM in Ocean Consolidation Party tab", ScenarioDetailsHashMap);
					if(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "AddressName2", ScenarioDetailsHashMap, 10), null).equalsIgnoreCase(""))
					{
						GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "AddressName2", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "DestBranch_AddressName2", ScenarioDetailsHashMap), 10);
					}

					if(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Textbox_PartyTab_ContactName", ScenarioDetailsHashMap, 10), null).equalsIgnoreCase(""))
					{
						GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Textbox_PartyTab_ContactName", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "DestBranchContactName", ScenarioDetailsHashMap), 10);
					}
					if(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Parties_Email", ScenarioDetailsHashMap, 10), null).equalsIgnoreCase(""))
					{
						GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Parties_Email", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "DestinationBranchEMail", ScenarioDetailsHashMap), 10);
					}
					if(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Parties_OfficeTel", ScenarioDetailsHashMap, 10), null).equalsIgnoreCase(""))
					{
						GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Parties_OfficeTel", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "DestinationTelephone", ScenarioDetailsHashMap), 10);
					}
					if(GenericMethods.readValue(driver, orOceanExpOBL.getElement(driver, "Parties_OfficeFax", ScenarioDetailsHashMap, 10), null).equalsIgnoreCase(""))
					{
						GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Parties_OfficeFax", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "DestinationFax", ScenarioDetailsHashMap), 10);
					}
					GenericMethods.pauseExecution(3000);

				}

				WebElement myDynamicElement1 = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.id("button.gridEditBtn")));
				GenericMethods.clickElement(driver, null, myDynamicElement1, 2);	
				GenericMethods.pauseExecution(3000);
				GenericMethods.clickElement(driver, By.xpath(partyXpath), null, 10);

			}
		}
		GenericMethods.pauseExecution(3000);
	}

	public static void HBLUpdateInttraMessageValidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo)
	{
		GenericMethods.pauseExecution(6000);
		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "MAIN_DETAILS_Tab", ScenarioDetailsHashMap, 10),30);
		try{
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}

		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ServiceTypeOrigin", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "ServiceTypeOrigin", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ServiceTypeDest", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "ServiceTypeDest", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "PortOfLoading", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "PortOfLoading", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "PlaceOfReceipt", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "PlaceOfReceipt", ScenarioDetailsHashMap), 2);

		PartiesTab_PartyTypeDetails(driver, ScenarioDetailsHashMap, RowNo);

		//Updating Correct Container Type where 'Equipment Owner' is present in Container Details tab
		GenericMethods.pauseExecution(6000);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "ContainerDetails_Tab", ScenarioDetailsHashMap, 10),30);
		try{
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		GenericMethods.pauseExecution(5000);
		Select ContainerType = new Select(driver.findElement(By.id("containerType1")));
		ContainerType.selectByVisibleText(ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "ContainerType", ScenarioDetailsHashMap));
		GenericMethods.pauseExecution(6000);

		GenericMethods.clickElement(driver, null,Common.getElement(driver, "Consolidation_Tab", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(2000);
		try{
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ConsolHBL_ID", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "HBLId", ScenarioDetailsHashMap), 30);
		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "ConsoleSearch", ScenarioDetailsHashMap, 10),20);


		int consolidationGrid = driver.findElements(By.xpath("html/body/form/table/tbody/tr[4]/td/div/table/tbody/tr")).size();
		System.out.println("consolidationGrid"+consolidationGrid);

		for (int GridNo = 1; GridNo <= consolidationGrid; GridNo++) 
		{
			int XLTotalrowCount=ExcelUtils.getSubScenarioRowCount("SE_OBL_INTTRA", ScenarioDetailsHashMap);
			for (int XLrowCount = 1; XLrowCount <= XLTotalrowCount; XLrowCount++) {
				System.out.println("Excel Rowcount"+XLTotalrowCount);
				System.out.println("Actual innertext="+GenericMethods.getInnerText(driver, By.id("dtDisplayHblOrBookingIdHL"+GridNo), null, 10));
				System.out.println("Actual value="+GenericMethods.readValue(driver, null, By.id("dtDisplayHblOrBookingIdHL"+GridNo)));
				System.out.println("Expected="+ExcelUtils.getCellData("SE_OBL_INTTRA", XLrowCount, "HBLId", ScenarioDetailsHashMap));

				if(GenericMethods.readValue(driver, null, By.id("dtDisplayHblOrBookingIdHL"+GridNo)).equalsIgnoreCase(ExcelUtils.getCellData("SE_OBL_INTTRA", XLrowCount, "HBLId", ScenarioDetailsHashMap)))
				{
					System.out.println("Entered in XL loop");
					GenericMethods.clickElement(driver, By.id("dtDisplayHblOrBookingIdHL"+GridNo), null, 10);
					System.out.println("Clicked ");
					GenericMethods.pauseExecution(6000);

					SeaHBL.PartiesTab_PartyTypeDetails(driver, ScenarioDetailsHashMap, RowNo);
					GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "CargoDetails", ScenarioDetailsHashMap, 10), 2);
					try{
						GenericMethods.handleAlert(driver, "accept");

					}catch (Exception e) {
						e.printStackTrace();
					}
					GenericMethods.pauseExecution(3000);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "Commodity", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Commodity", ScenarioDetailsHashMap), 2);
					//GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "HsCommodity", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "HSCommodity", ScenarioDetailsHashMap), 2);
					//GenericMethods.action_Key(driver, Keys.TAB);

					/*AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "Pack_Commodity",ScenarioDetailsHashMap, 10), orHBL,"Code", ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Commodity",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					AppReusableMethods.selectValueFromLov(driver, orHBL.getElement(driver, "Img_PackHsCommodity",ScenarioDetailsHashMap, 10), orHBL,"Code", ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "HSCommodity",ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					 */		
					GenericMethods.pauseExecution(4000);
					GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "MarksNumberImg", ScenarioDetailsHashMap, 10), 2);
					GenericMethods.selectWindow(driver);
					GenericMethods.pauseExecution(4000);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "MarksAndNumber", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "MarksAndNumber", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orHBL.getElement(driver, "ExtendedDescription", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "ExtendedDescription", ScenarioDetailsHashMap), 2);
					GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 3);
					GenericMethods.switchToParent(driver);
					AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
					GenericMethods.pauseExecution(4000);
					GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "AddPacks", ScenarioDetailsHashMap, 10), 2);
					GenericMethods.pauseExecution(4000);
					GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 3);
					GenericMethods.pauseExecution(4000);

				}

			}

		}


	}


	public static void oceanExportsOBL_SearchList(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo )
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.OceanExport_Master, "Ocean Consolidation", ScenarioDetailsHashMap);
		//GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Search_MblId", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_OBL", rowNo, "OBL_Id", ScenarioDetailsHashMap) , 10);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ShipmentRefNo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_OBL", rowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap) , 10);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
		GenericMethods.assertInnerText(driver, null, orOceanExpOBL.getElement(driver, "SearchGridMblId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_OBL", rowNo, "OBL_Id",ScenarioDetailsHashMap), "Shipment ReferenceId", "Validating Shipment Reference ID", ScenarioDetailsHashMap);
	}

	public static void OBL_AuditTrail_InterfaceAudit_Tab(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "Audit_Btn",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);

		for (int Actions_GridRowID = 1; Actions_GridRowID <= driver.findElements(By.xpath("//table[@id='auditTrailScrhGrid']/tbody/tr")).size(); Actions_GridRowID++) 
		{
			String Actions_gridPrefix = "//table[@id='auditTrailScrhGrid']/tbody/tr["+Actions_GridRowID+"]/";

			//Below if condition is to click on record in the grid for which From Screen = INTTRA_SI_REQUEST
			System.out.println("ToScreenText="+GenericMethods.getInnerText(driver, By.xpath(Actions_gridPrefix+"td[13]"),null,  10));
			if(GenericMethods.getInnerText(driver, By.xpath(Actions_gridPrefix+"td[13]"), null, 10).equalsIgnoreCase("INTTRA_SI_REQUEST"))
			{
				GenericMethods.clickElement(driver, By.xpath(Actions_gridPrefix+"td[13]"),null, 2);
				GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "Tab_AuditTrail_InterfaceAudit",ScenarioDetailsHashMap, 10), 2);

				for (int Interface_GridRowID = 1; Interface_GridRowID <= driver.findElements(By.xpath("//table[@id='dataTable']/tbody/tr")).size(); Interface_GridRowID++) 
				{
					String Interface_gridPrefix = "//table[@id='dataTable']/tbody/tr["+Interface_GridRowID+"]/";

					//Below if condition is to generate xml from the record in the grid for which Event Type = STANDARD_EDI_INTTRA and save it in workspace(\\INTRA\ScenarioFiles) folder.
					if(GenericMethods.getInnerText(driver, By.xpath(Interface_gridPrefix+"td[7]"), null, 10).equals( ExcelUtils.getCellData("SE_OBL", RowNo, "EventType",ScenarioDetailsHashMap)))
					{
						System.out.println("res.getExectionDateTime="+res.getExectionDateTime());
						String Workspace_INTTRAPath = System.getProperty("user.dir")+ "\\INTTRA\\ScenarioFiles\\" + res.getExectionDateTime()+"_";
						System.out.println("getExectionDateTime="+res.getExectionDateTime());
						String JournalID = GenericMethods.getInnerText(driver, By.xpath(Interface_gridPrefix+"td[20]"), null, 10);
						System.out.println("journal id::--"+JournalID);
						ExcelUtils.setCellData("SE_HBLMainDetails", RowNo, "JournalID", JournalID, ScenarioDetailsHashMap);
						ExcelUtils.setCellData("SE_OBL", RowNo, "JournalID", JournalID, ScenarioDetailsHashMap);
						try {


							GenericMethods.clickElement(driver, By.xpath(Interface_gridPrefix+"td[10]/a/img"), null, 10);
							GenericMethods.pauseExecution(3000);

							Robot rb=new Robot();
							rb.keyPress(KeyEvent.VK_CONTROL);
							rb.keyPress(KeyEvent.VK_S);
							rb.keyRelease(KeyEvent.VK_S);
							rb.keyRelease(KeyEvent.VK_CONTROL);
							GenericMethods.pauseExecution(2000);
							rb.keyPress(KeyEvent.VK_HOME);
							rb.keyRelease(KeyEvent.VK_HOME);
							GenericMethods.pauseExecution(2000);
							GenericMethods.parseChars(Workspace_INTTRAPath, rb);
							rb.keyPress(KeyEvent.VK_ENTER);
							rb.keyRelease(KeyEvent.VK_ENTER);
							GenericMethods.pauseExecution(4000);
							rb.keyPress(KeyEvent.VK_ALT);
							rb.keyPress(KeyEvent.VK_F4);
							rb.keyRelease(KeyEvent.VK_F4);
							rb.keyRelease(KeyEvent.VK_ALT);




						} catch (AWTException e) {e.printStackTrace();
						} catch (Exception e){e.printStackTrace();
						}
						break;// Terminating the execution in Interface Audit screen
					}	
				}
				break;// Terminating the execution in Audit Actions screen
			}
		}

		GenericMethods.clickElement(driver, null,Common.getElement(driver, "CloseButton",ScenarioDetailsHashMap, 10), 2);

		try {
			XMLValidation(driver, ScenarioDetailsHashMap, RowNo);
			INTTRA_XMLFiles(driver, ScenarioDetailsHashMap, RowNo);
		} catch (ParserConfigurationException e) {e.printStackTrace();
		} catch (SAXException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();
		} catch (TransformerException e) {e.printStackTrace();
		}
	}

	public static void INTTRA_Validations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo)
	{
		try {
			INTTRA_XMLFiles(driver, ScenarioDetailsHashMap, RowNo);
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		GenericMethods.pauseExecution(5000);
		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "Tab_Intrra", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(6000);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e){}
		GenericMethods.assertInnerText(driver, By.id("dtStatus1"), null,  ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Status",ScenarioDetailsHashMap), "Grid_INTTRA Status", "Verifying INTTRA Status in the grid", ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "Tab_INTTRA_SIResponse", ScenarioDetailsHashMap, 10), 2);

		for (int StatusNo = 1; StatusNo <= ExcelUtils.getCellDataRowCount("SE_OBLINTTRA_SIResponse", ScenarioDetailsHashMap); StatusNo++) 
		{
			boolean StatusAvailability =false;
			String ExcelTestdata_Status = ExcelUtils.getCellData("SE_OBLINTTRA_SIResponse", StatusNo, "Status", ScenarioDetailsHashMap);
			for (int Response_GridNo = 1; Response_GridNo <= driver.findElements(By.xpath("//table[@id='inttraResponsetDtlsGrid']/tbody/tr")).size(); Response_GridNo++) 
			{
				String gridXpathPrefix = "//table[@id='inttraResponsetDtlsGrid']/tbody/tr["+Response_GridNo+"]/";
				String GridStatus= GenericMethods.getInnerText(driver, By.xpath(gridXpathPrefix+"td[7]"), null, 10);
				if(ExcelTestdata_Status.equals(GridStatus))
				{
					GenericMethods.assertInnerText(driver, By.xpath(gridXpathPrefix+"td[7]"), null, ExcelTestdata_Status, "Status", "Verifying INTTRA Status in the grid", ScenarioDetailsHashMap);
					GenericMethods.assertInnerText(driver, By.xpath(gridXpathPrefix+"td[9]"), null, ExcelUtils.getCellData("SE_OBLINTTRA_SIResponse", StatusNo, "VersionNo", ScenarioDetailsHashMap), "Version No", "Verifying INTTRA Version No in the grid", ScenarioDetailsHashMap);
					StatusAvailability = true;
				}
			}
			if(!StatusAvailability)
			{
				GenericMethods.assertTwoValues(ExcelTestdata_Status, " ", "Verifying INTTRA status is available in the grid", ScenarioDetailsHashMap);
			}
		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "CloseButton", ScenarioDetailsHashMap, 10), 2);
	}

	public static void XMLValidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo)
	{
		String Workspace_INTTRAPath = System.getProperty("user.dir")+ "\\INTTRA\\ScenarioFiles\\" + res.getExectionDateTime()+"_";
		try {
			//Below script is to copy JournalID xml file from FTP location and copy it in "\\INTTRA\\ScenarioFiles" folder path in workspace.
			GenericMethods.CopyFileFromFtp("10.200.35.11", "kf/outbound/inttra/si/in/", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "JournalID", ScenarioDetailsHashMap)+"_", Workspace_INTTRAPath, "kfftp", "aCmgRZ1%2Ycf");
			GenericMethods.pauseExecution(5000);
			System.out.println("************************1111111111111111************************");
			GenericMethods.CompareXMLFiles(GenericMethods.getFilePath(System.getProperty("user.dir")+"\\INTTRA\\ScenarioFiles\\",res.getExectionDateTime()+"_"+ExcelUtils.getCellData("SE_OBL", RowNo, "JournalID", ScenarioDetailsHashMap)+"_"), System.getProperty("user.dir")+"\\INTTRA\\Processed.xml",ScenarioDetailsHashMap);
			System.out.println("************************2222222222222222************************");
			GenericMethods.CompareXMLFiles(GenericMethods.getFilePath(System.getProperty("user.dir")+"\\INTTRA\\ScenarioFiles\\",res.getExectionDateTime()+"_ETJournalController"), System.getProperty("user.dir")+"\\INTTRA\\ETJournalController.fsc.xml",ScenarioDetailsHashMap);
			System.out.println("************************2222endddddddddddddd222************************");
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void INTTRA_SIResponse_StatusMSG_Validation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		//Validating Rejected Status in Inttra SI Submission Tab
		oceanExportsOBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.pauseExecution(5000);
		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "Tab_Intrra", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(6000);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e){}
		try {
			//Acknowledgement
			DumpExcelFiles.copyExcel(System.getProperty("user.dir") + "/INTTRA/Aperak_Jet_Rejected.xml", System.getProperty("user.dir") + "/INTTRA/ScenarioFiles/"+ res.getExectionDateTime()+"_Aperak_Jet_Rejected.xml");
			String ACK_Filename= System.getProperty("user.dir") + "/INTTRA/ScenarioFiles/"+ res.getExectionDateTime()+"_Aperak_Jet_Rejected.xml";

			GenericMethods.UpdateXMLValues(ACK_Filename, "DateTime",  GenericMethods.GetTestdataDate(ExcelUtils.getCellData("SE_OBL", RowNo, "OBL_Date", ScenarioDetailsHashMap))+"0000");
			GenericMethods.UpdateXMLValues(ACK_Filename, "DocumentIdentifier",  ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues(ACK_Filename, "ShipmentIdentifier",  ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues(ACK_Filename, "ReferenceInformation",  ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues_ParentNodeAttributeValue(ACK_Filename, "PartnerRole","Carrier","PartnerIdentifier",  ExcelUtils.getCellData("SE_OBL", RowNo, "Carrier_Id", ScenarioDetailsHashMap));
			GenericMethods.UploadFileToFTP("10.200.35.11", "kfftp", "aCmgRZ1%2Ycf", ACK_Filename, "kf/inbound/inttra/import/acknowledgment/"+res.getExectionDateTime()+"_Aperak_Jet_Rejected.xml");
			System.out.println("acknowledgement sumitter");
			GenericMethods.pauseExecution(5000);
			//Below script is to update value to update INTTRA Status
			ExcelUtils.setCellData("SE_OBL_INTTRA", RowNo, "Rejected_Status","REJECTED",ScenarioDetailsHashMap);

		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e){}
		GenericMethods.pauseExecution(6000);


		oceanExportsOBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.pauseExecution(5000);
		GenericMethods.pauseExecution(5000);
		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "Tab_Intrra", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(6000);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e){}
		for (int rowCount = 1; rowCount <= (driver.findElements(By.xpath("html/body/form/div/table/tbody/tr"))).size(); rowCount++) {
			System.out.println("Actual Rejected Status"+GenericMethods.getInnerText(driver, By.id("dtStatus"+rowCount), null, 10));
			System.out.println("Expected Rejected Status"+ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Rejected_Status",ScenarioDetailsHashMap)); 
			if((GenericMethods.getInnerText(driver, By.id("dtStatus"+rowCount), null, 10)).equalsIgnoreCase(ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Rejected_Status",ScenarioDetailsHashMap)))
			{
				System.out.println("********************************");
				GenericMethods.assertTwoValues(GenericMethods.getInnerText(driver, By.id("dtStatus"+rowCount), null, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Rejected_Status",ScenarioDetailsHashMap), "Verifying Rejected INTTRA Status in SI submission grid", ScenarioDetailsHashMap);
				//GenericMethods.assertInnerText(driver, By.id("dtStatus"+rowCount), null,  ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Rejected_Status",ScenarioDetailsHashMap), "Grid_INTTRA Status", "Verifying inner text of Rejected INTTRA Status in SI submission grid", ScenarioDetailsHashMap);
				break;
			}
		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);

		//Validating Accepted Status in Inttra SI Submission Tab
		oceanExportsOBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.pauseExecution(5000);
		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "Tab_Intrra", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(6000);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e){}
		try {
			//Acknowledgement
			DumpExcelFiles.copyExcel(System.getProperty("user.dir") + "/INTTRA/Aperak_Jet.xml", System.getProperty("user.dir") + "/INTTRA/ScenarioFiles/"+ res.getExectionDateTime()+"_Aperak_Jet.xml");
			String ACK_Filename= System.getProperty("user.dir") + "/INTTRA/ScenarioFiles/"+ res.getExectionDateTime()+"_Aperak_Jet.xml";

			GenericMethods.UpdateXMLValues(ACK_Filename, "DateTime",  GenericMethods.GetTestdataDate(ExcelUtils.getCellData("SE_OBL", RowNo, "OBL_Date", ScenarioDetailsHashMap))+"0000");
			GenericMethods.UpdateXMLValues(ACK_Filename, "DocumentIdentifier",  ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues(ACK_Filename, "ShipmentIdentifier",  ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues(ACK_Filename, "ReferenceInformation",  ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues_ParentNodeAttributeValue(ACK_Filename, "PartnerRole","Carrier","PartnerIdentifier",  ExcelUtils.getCellData("SE_OBL", RowNo, "Carrier_Id", ScenarioDetailsHashMap));
			GenericMethods.UploadFileToFTP("10.200.35.11", "kfftp", "aCmgRZ1%2Ycf", ACK_Filename, "kf/inbound/inttra/import/acknowledgment/"+res.getExectionDateTime()+"_Aperak_Jet.xml");
			System.out.println("acknowledgement sumitter");
			GenericMethods.pauseExecution(5000);
			//Below script is to update value to update INTTRA Status
			ExcelUtils.setCellData("SE_OBL_INTTRA", RowNo, "Status","ACCEPTED",ScenarioDetailsHashMap);


		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e){}
		GenericMethods.pauseExecution(6000);

		oceanExportsOBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.pauseExecution(5000);
		GenericMethods.pauseExecution(5000);
		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "Tab_Intrra", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(6000);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e){}
		for (int rowCount = 1; rowCount <= (driver.findElements(By.xpath("html/body/form/div/table/tbody/tr"))).size(); rowCount++) {
			//GenericMethods.assertTwoValues(GenericMethods.getInnerText(driver, By.id("dtStatus"+rowCount), null, 10), ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Status",ScenarioDetailsHashMap), "Verifying Rejected INTTRA Status in SI submission grid", ScenarioDetailsHashMap);
			System.out.println("Actual Rejected Status"+GenericMethods.getInnerText(driver, By.id("dtStatus"+rowCount), null, 10));
			System.out.println("Expected Rejected Status"+ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Rejected_Status",ScenarioDetailsHashMap)); 

			if((GenericMethods.getInnerText(driver, By.id("dtStatus"+rowCount), null, 10)).equalsIgnoreCase(ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Status",ScenarioDetailsHashMap)))
			{
				System.out.println("********************************");
				GenericMethods.assertInnerText(driver, By.id("dtStatus"+rowCount), null,  ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "Status",ScenarioDetailsHashMap), "Grid_INTTRA Status", "Verifying Accepted INTTRA Status in SI submission grid", ScenarioDetailsHashMap);
				break;
			}
		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);

	}
	public static void INTTRA_XMLFiles(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{

		/*//Acknowledgement
		DumpExcelFiles.copyExcel(System.getProperty("user.dir") + "/INTTRA/Aperak_Jet.xml", System.getProperty("user.dir") + "/INTTRA/ScenarioFiles/"+ res.getExectionDateTime()+"_Aperak_Jet.xml");
		String ACK_Filename= System.getProperty("user.dir") + "/INTTRA/ScenarioFiles/"+ res.getExectionDateTime()+"_Aperak_Jet.xml";

		GenericMethods.UpdateXMLValues(ACK_Filename, "DateTime",  GenericMethods.GetTestdataDate(ExcelUtils.getCellData("SE_OBL", RowNo, "OBL_Date", ScenarioDetailsHashMap))+"0000");
		GenericMethods.UpdateXMLValues(ACK_Filename, "DocumentIdentifier",  ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLValues(ACK_Filename, "ShipmentIdentifier",  ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLValues(ACK_Filename, "ReferenceInformation",  ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLValues_ParentNodeAttributeValue(ACK_Filename, "PartnerRole","Carrier","PartnerIdentifier",  ExcelUtils.getCellData("SE_OBL", RowNo, "Carrier_Id", ScenarioDetailsHashMap));
		GenericMethods.UploadFileToFTP("10.200.35.11", "kfftp", "aCmgRZ1%2Ycf", ACK_Filename, "kf/inbound/inttra/import/acknowledgment/"+res.getExectionDateTime()+"_Aperak_Jet.xml");
		System.out.println("acknowledgement sumitter");
		//Status
		 */		
		for (int FileNo = 1; FileNo <= ExcelUtils.getCellDataRowCount("SE_HBLINTTRA_SIResponse", ScenarioDetailsHashMap); FileNo++) 
		{
			DumpExcelFiles.copyExcel(System.getProperty("user.dir") + "/INTTRA/carrierstatus_Jet.xml", System.getProperty("user.dir") + "/INTTRA/ScenarioFiles/"+ res.getExectionDateTime()+"_carrierstatus_Jet_"+ExcelUtils.getCellData("SE_OBLINTTRA_SIResponse", FileNo, "Status", ScenarioDetailsHashMap)+".xml");
			String Status_Filename= System.getProperty("user.dir") + "/INTTRA/ScenarioFiles/"+ res.getExectionDateTime()+"_carrierstatus_Jet_"+ExcelUtils.getCellData("SE_OBLINTTRA_SIResponse", FileNo, "Status", ScenarioDetailsHashMap)+".xml";
			GenericMethods.UpdateXMLValues(Status_Filename, "DocumentIdentifier",  ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues_CurrentNodeAttributeValue(Status_Filename, "DateType","Document","DateTime", GenericMethods.GetTestdataDate(ExcelUtils.getCellData("SE_OBL", RowNo, "OBL_Date", ScenarioDetailsHashMap))+"0000" );
			GenericMethods.UpdateXMLValues(Status_Filename, "EventCode",  ExcelUtils.getCellData("SE_OBLINTTRA_SIResponse", FileNo, "Status", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues_CurrentNodeAttributeValue(Status_Filename, "DateType","StatusChange","DateTime", GenericMethods.GetTestdataDate(ExcelUtils.getCellData("SE_OBL", RowNo, "OBL_Date", ScenarioDetailsHashMap))+"0000" );
			GenericMethods.UpdateXMLValues_CurrentNodeAttributeValue(Status_Filename, "ReferenceType","BookingNumber","ReferenceInformation",  ExcelUtils.getCellData("SE_OBL", RowNo, "CarrierBookingRefNum", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues(Status_Filename, "CarrierSCAC",  ExcelUtils.getCellData("SE_OBL", RowNo, "Carrier_Id", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues(Status_Filename, "ConveyanceName",  ExcelUtils.getCellData("SE_OBLVesselSchedule", RowNo, "VesselName", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues(Status_Filename, "VoyageTripNumber",  ExcelUtils.getCellData("SE_OBLVesselSchedule", RowNo, "VoyageName", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues_ParentNodeAttributeValue(Status_Filename, "PartnerRole","Carrier","PartnerIdentifier",  ExcelUtils.getCellData("SE_OBL", RowNo, "Carrier_Id", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues_CurrentNodeAttributeValue(Status_Filename, "DateType","DepartureEstimated","DateTime",  GenericMethods.GetTestdataDate(ExcelUtils.getCellData("SE_OBL", RowNo, "ETD", ScenarioDetailsHashMap))+ExcelUtils.getCellData("SE_OBL", RowNo, "ETD_Time", ScenarioDetailsHashMap).replace(":", ""));
			GenericMethods.UpdateXMLValues_CurrentNodeAttributeValue(Status_Filename, "DateType","ArrivalEstimated","DateTime",  GenericMethods.GetTestdataDate( ExcelUtils.getCellData("SE_OBL", RowNo, "ETA", ScenarioDetailsHashMap))+ExcelUtils.getCellData("SE_OBL", RowNo, "ETA_Time", ScenarioDetailsHashMap).replace(":", ""));
			GenericMethods.UpdateXMLValues(Status_Filename, "EquipmentIdentifier",  ExcelUtils.getCellData("SE_OBLContainersDetails", RowNo, "ContainerNumber", ScenarioDetailsHashMap));
			GenericMethods.UpdateXMLValues(Status_Filename, "EquipmentTypeCode",  ExcelUtils.getCellData("SE_OBLContainersDetails", RowNo, "ContainerType", ScenarioDetailsHashMap));
			GenericMethods.UploadFileToFTP("10.200.35.11", "kfftp", "aCmgRZ1%2Ycf", Status_Filename, "kf/inbound/inttra/import/status/"+res.getExectionDateTime()+"_carrierstatus_Jet_"+ExcelUtils.getCellData("SE_OBLINTTRA_SIResponse", FileNo, "Status", ScenarioDetailsHashMap)+".xml");

			try {
				GenericMethods.FTP_RecordAvailability("10.200.35.11","kf/inbound/inttra/import/status/", res.getExectionDateTime()+"_carrierstatus_Jet_"+ExcelUtils.getCellData("SE_OBLINTTRA_SIResponse", FileNo, "Status", ScenarioDetailsHashMap),"kfftp", "aCmgRZ1%2Ycf",5);
			} catch (InterruptedException e) {e.printStackTrace();}
			//Wait time for file to upload in FTP
			//GenericMethods.pauseExecution(180000);
			System.out.println("status sumitted----"+ExcelUtils.getCellData("SE_OBLINTTRA_SIResponse", FileNo, "Status", ScenarioDetailsHashMap));
		}

		//Below script is to update value to update INTTRA Status
		ExcelUtils.setCellData("SE_OBL_INTTRA", RowNo, "Status","ACCEPTED",ScenarioDetailsHashMap);
	}

	public static void INTTRA_Checkboxvalidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		boolean InttraCheckBox_Status = false;
		GenericMethods.pauseExecution(2000);
		Select select = new Select(driver.findElement(By.id("consoleType")));
		System.out.println(select.getOptions().size());//will get all options as List<WebElement>
		int Totaldropdowncount =select.getOptions().size();
		for (int DDCount = 0; DDCount < Totaldropdowncount; DDCount++) {
			String dropdownvalue = select.getOptions().get(DDCount).getText();
			select.selectByVisibleText(dropdownvalue);
			GenericMethods.pauseExecution(2000);
			System.out.println(select.getOptions().get(DDCount).getText());
			InttraCheckBox_Status = GenericMethods.isFieldEnabled(driver, null, orOceanExpOBL.getElement(driver, "InttraSi", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(2000);
			if(dropdownvalue.equalsIgnoreCase("Co-Load Shipment"))
			{

				GenericMethods.assertTwoValues(InttraCheckBox_Status+"", false+"", "Validating inttra check box while Consol Type is selected as 'Co-Load Shipment'", ScenarioDetailsHashMap);
			}
			if(dropdownvalue.equalsIgnoreCase("Consolidation"))
			{

				GenericMethods.assertTwoValues(InttraCheckBox_Status+"", true+"", "Validating inttra check box while Consol Type is selected as 'Consolidation'", ScenarioDetailsHashMap);
			}
			if(dropdownvalue.equalsIgnoreCase("FCL"))
			{

				GenericMethods.assertTwoValues(InttraCheckBox_Status+"", true+"", "Validating inttra check box while Consol Type is selected as 'FCL'", ScenarioDetailsHashMap);
			}
			GenericMethods.pauseExecution(2000);

		}
	}


	public static void inttra_XMLValidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		oceanExportsOBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.clickElement(driver, null,orOceanExpOBL.getElement(driver, "Audit_Btn",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(3000);
		String Workspace_INTTRAPath = null;
		for (int Actions_GridRowID = 1; Actions_GridRowID <= driver.findElements(By.xpath("//table[@id='auditTrailScrhGrid']/tbody/tr")).size(); Actions_GridRowID++) 
		{
			String Actions_gridPrefix = "//table[@id='auditTrailScrhGrid']/tbody/tr["+Actions_GridRowID+"]/";

			//Below if condition is to click on record in the grid for which From Screen = INTTRA_SI_REQUEST
			System.out.println("ToScreenText="+GenericMethods.getInnerText(driver, By.xpath(Actions_gridPrefix+"td[13]"),null,  10));
			if(GenericMethods.getInnerText(driver, By.xpath(Actions_gridPrefix+"td[13]"), null, 10).equalsIgnoreCase("INTTRA_SI_REQUEST"))
			{
				GenericMethods.clickElement(driver, By.xpath(Actions_gridPrefix+"td[13]"),null, 2);
				GenericMethods.clickElement(driver, null,orHBL.getElement(driver, "Tab_AuditTrail_InterfaceAudit",ScenarioDetailsHashMap, 10), 2);

				for (int Interface_GridRowID = 1; Interface_GridRowID <= driver.findElements(By.xpath("//table[@id='dataTable']/tbody/tr")).size(); Interface_GridRowID++) 
				{
					String Interface_gridPrefix = "//table[@id='dataTable']/tbody/tr["+Interface_GridRowID+"]/";

					//Below if condition is to generate xml from the record in the grid for which Event Type = STANDARD_EDI_INTTRA and save it in workspace(\\INTRA\ScenarioFiles) folder.
					if(GenericMethods.getInnerText(driver, By.xpath(Interface_gridPrefix+"td[7]"), null, 10).equals( ExcelUtils.getCellData("SE_OBL", RowNo, "EventType",ScenarioDetailsHashMap)))
					{
						System.out.println("res.getExectionDateTime="+res.getExectionDateTime());
						Workspace_INTTRAPath = System.getProperty("user.dir")+ "\\INTTRA\\ScenarioFiles\\InttraValidationXMLFiles\\" + res.getExectionDateTime()+"_";
						System.out.println("getExectionDateTime="+res.getExectionDateTime());
						String JournalID = GenericMethods.getInnerText(driver, By.xpath(Interface_gridPrefix+"td[20]"), null, 10);
						System.out.println("journal id::--"+JournalID);
						ExcelUtils.setCellData("SE_HBLMainDetails", RowNo, "JournalID", JournalID, ScenarioDetailsHashMap);
						ExcelUtils.setCellData("SE_OBL", RowNo, "JournalID", JournalID, ScenarioDetailsHashMap);
						try {


							GenericMethods.clickElement(driver, By.xpath(Interface_gridPrefix+"td[10]/a/img"), null, 10);
							GenericMethods.pauseExecution(3000);

							Robot rb=new Robot();
							rb.keyPress(KeyEvent.VK_CONTROL);
							rb.keyPress(KeyEvent.VK_S);
							rb.keyRelease(KeyEvent.VK_S);
							rb.keyRelease(KeyEvent.VK_CONTROL);
							GenericMethods.pauseExecution(2000);
							rb.keyPress(KeyEvent.VK_HOME);
							rb.keyRelease(KeyEvent.VK_HOME);
							GenericMethods.pauseExecution(2000);
							System.out.println("Workspace_INTTRAPath Type="+Workspace_INTTRAPath);
							GenericMethods.parseChars(Workspace_INTTRAPath, rb);
							rb.keyPress(KeyEvent.VK_ENTER);
							rb.keyRelease(KeyEvent.VK_ENTER);
							GenericMethods.pauseExecution(4000);
							rb.keyPress(KeyEvent.VK_ALT);
							rb.keyPress(KeyEvent.VK_F4);
							rb.keyRelease(KeyEvent.VK_F4);
							rb.keyRelease(KeyEvent.VK_ALT);




						} catch (AWTException e) {e.printStackTrace();
						} catch (Exception e){e.printStackTrace();
						}
						break;// Terminating the execution in Interface Audit screen
					}	
				}
				break;// Terminating the execution in Audit Actions screen
			}
		}

		GenericMethods.clickElement(driver, null,Common.getElement(driver, "CloseButton",ScenarioDetailsHashMap, 10), 2);

		GenericMethods.pauseExecution(4000);
		GenericMethods.CopyXMLFiles(System.getProperty("user.dir")+ "\\INTTRA\\ScenarioFiles\\InttraValidationXMLFiles\\" + res.getExectionDateTime()+"_"+"ETJournalController.fsc.xml", System.getProperty("user.dir")+ "\\INTTRA\\ScenarioFiles\\InttraValidationXMLFiles\\" + res.getExectionDateTime()+"_"+"ETJournalController_Updated.fsc.xml");

		String INTTRAInputMSGPath=System.getProperty("user.dir")+ "\\INTTRA\\ScenarioFiles\\InttraValidationXMLFiles\\" + res.getExectionDateTime()+"_"+"ETJournalController_Updated.fsc.xml";

		//GenericMethods.UpdateXMLValues(INTTRAInputMSGPath, ExcelUtils.getCellData("SE_OBL", RowNo, "OBL_Id", ScenarioDetailsHashMap),  ExcelUtils.getCellData("XMLValidationSheet", RowNo, "MBLID", ScenarioDetailsHashMap));
		System.out.println("Column ="+ ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_PartnerName", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "ProfileID", ExcelUtils.getCellData("SE_OBL", RowNo, "DestBranch", ScenarioDetailsHashMap), ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_PartnerName", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "ProfileName", ExcelUtils.getCellData("SE_OBL", RowNo, "OriginBranch", ScenarioDetailsHashMap), ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_PartnerName", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "ProfileNickName", ExcelUtils.getCellData("SE_OBL", RowNo, "OriginBranch", ScenarioDetailsHashMap), ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_PartnerName", ScenarioDetailsHashMap));

		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "LegalName", ExcelUtils.getCellData("SE_OBL", RowNo, "OriginBranch", ScenarioDetailsHashMap), ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_PartnerName", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "Building", "BLUE GRACE LOGISTICS LIMITED BLDG#1", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_AddressLine1", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "Street", "Laan van Londen 100", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_AddressLine2", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "SuburbORLocality", "NL", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_CountryCode", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "City", "NEW YORK", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_City", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "StateProvinceCounty", "NEW YORK", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_StateProvince", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "PostalCode", "10584", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_PostalCode", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "CountryCode", "US", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_CountryCode", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "ContactPerson", "John Isrial", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_ContactName", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "Email", "John Isrial", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_Email", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "Telephone", "2131313213", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_Telephone", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "Fax", ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "DestinationFax", ScenarioDetailsHashMap), ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_FAX", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "MessageVersion", "000001", "00002");

		//Added
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "SealNo1", "278154", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Container_SealNo1", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "SealNo2", "D7033556", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Container_SealNo2", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "GrossWtStuffed", "12632.000", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "SplitGoodsGrossWeight", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "VolumeStuffed", "0.000", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "SplitGoodsGrossVolume", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "CountryCode", "PL", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "ManifestFilingCountryCodeAgency", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLValues(INTTRAInputMSGPath, "GoodsDescription", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "GoodsDescription", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLValues(INTTRAInputMSGPath, "PackageMarks", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Package_MarksAndNumbers", ScenarioDetailsHashMap));
		GenericMethods.setAttributValue(INTTRAInputMSGPath, "Commodity", "HSCODE", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "HarmonizedSystem_ProductId", ScenarioDetailsHashMap));

		
		
		
		String XMLFileName = res.getExectionDateTime()+"_ETJournalController_Updated.fsc.xml";
		GenericMethods.UploadFileToFTP("10.200.35.11", "kfftp", "aCmgRZ1%2Ycf", INTTRAInputMSGPath, "kf/outbound/inttra/si/out/"+XMLFileName);
		System.out.println("XMLFileName"+XMLFileName);
		System.out.println("File uploaded to FTP");
		GenericMethods.pauseExecution(9000);
		String Workspace_INTTRAInputPath = System.getProperty("user.dir")+ "\\INTTRA\\ScenarioFiles\\InttraValidationXMLFiles\\Input\\";
		System.out.println("Workspace_INTTRAInputPath="+Workspace_INTTRAInputPath);
		String INTTRAUpdatedOutputMSGPath = Workspace_INTTRAInputPath+XMLFileName;
		System.out.println("INTTRAUpdatedOutputMSGPath"+INTTRAUpdatedOutputMSGPath);
		
		try {
			GenericMethods.copyUpdatedFileFromFTP("10.200.35.11", "kf/outbound/inttra/si/in/", XMLFileName, Workspace_INTTRAInputPath, "kfftp", "aCmgRZ1%2Ycf");
			GenericMethods.pauseExecution(5000);			
			//Below script is to copy JournalID xml file from FTP location and copy it in "\\INTTRA\\ScenarioFiles" folder path in workspace.
			GenericMethods.CopyFileFromFtp("10.200.35.11", "kf/outbound/inttra/si/in/", ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "JournalID", ScenarioDetailsHashMap)+"_", Workspace_INTTRAPath, "kfftp", "aCmgRZ1%2Ycf");
			GenericMethods.pauseExecution(5000);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


		//Temporary Path(need to remove once copy problem solved in FTP)
		//INTTRAUpdatedOutputMSGPath = System.getProperty("user.dir")+ "\\INTTRA\\ScenarioFiles\\InttraValidationXMLFiles\\Input\\2015-08-13_10-56-42_ETJournalController_Updated.fsc.xml";

		///////////
		GenericMethods.getvalueFromDirectNode_AttributeNode_AttrubuteValue(INTTRAUpdatedOutputMSGPath, "PackageDetailComments", "CommentType", "GoodsDescription", "XMLValidationSheet","GoodsDescription", "PackageDetailComments", 35 , ScenarioDetailsHashMap, RowNo);
		GenericMethods.getXMLTagvalueFromDirectNode_AttributeNode_AttrubuteValue_WithValidation(INTTRAUpdatedOutputMSGPath, "EquipmentSeal", "SealingParty", "Carrier", "XMLValidationSheet", "Carrier_SealingNumber", "Container_SealNo1", 15, ScenarioDetailsHashMap, RowNo);
		GenericMethods.getvalueFromDirectNode_AttributeNode_AttrubuteValue(INTTRAUpdatedOutputMSGPath, "ProductId", "ItemTypeIdCode", "HarmonizedSystem", "XMLValidationSheet", "HarmonizedSystem_ProductId", "HarmonizedSystem_ProductId", 999, ScenarioDetailsHashMap, RowNo);
		GenericMethods.getvalueFromDirectNode_AttributeNode_AttrubuteValue(INTTRAUpdatedOutputMSGPath, "SplitGoodsGrossVolume", "UOM", "MTQ", "XMLValidationSheet", "SplitGoodsGrossVolume", "SplitGoodsGrossVolume", 50, ScenarioDetailsHashMap, RowNo);
		GenericMethods.getvalueFromDirectNode_AttributeNode_AttrubuteValue(INTTRAUpdatedOutputMSGPath, "SplitGoodsGrossWeight", "UOM", "KGM", "XMLValidationSheet", "SplitGoodsGrossWeight", "SplitGoodsGrossWeight", 50, ScenarioDetailsHashMap, RowNo);
		GenericMethods.getvalueFromDirectNode_AttributeNode_AttrubuteValue(INTTRAUpdatedOutputMSGPath, "ManifestFilingCountryCode", "Agency", "UN", "XMLValidationSheet", "ManifestFilingCountryCodeAgency", "ManifestFilingCountryCodeAgency", 50, ScenarioDetailsHashMap, RowNo);
		GenericMethods.getDirectNodeTextValue(INTTRAUpdatedOutputMSGPath, "Marks", "XMLValidationSheet", "Package_MarksAndNumbers", "MarksList", 999, ScenarioDetailsHashMap, RowNo);
         ////////
		
		//Validating Partner Identifier tag value
		String actualAssignedBySender = GenericMethods.GetXMLValuesFromInttraUpdatedInputFileWithANDOperation(INTTRAUpdatedOutputMSGPath, "PartnerRole", "Sender", "PartnerIdentifier", "", "");
		System.out.println("actualAssignedBySender"+actualAssignedBySender);
		GenericMethods.assertTwoValues(actualAssignedBySender, ExcelUtils.getCellData("XMLValidationSheet", RowNo, "AssignedBySender", ScenarioDetailsHashMap), "Validating Partner Identifier Agency AssignedBySender value", ScenarioDetailsHashMap);

		//Validating Consignee ContactName Tag Value
		String ActualConsignee_PartnerNameValue = GenericMethods.GetXMLValuesFromInttraUpdatedInputFileWithANDOperation(INTTRAUpdatedOutputMSGPath, "PartnerRole", "Consignee", "PartnerName", "", "");
		System.out.println("ActualConsignee_PartnerNameValue"+ActualConsignee_PartnerNameValue);
		GenericMethods.assertTwoValues_NotEqual(ActualConsignee_PartnerNameValue, ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_PartnerName", ScenarioDetailsHashMap), "Comparing inttra Consignee_PartnerName output xml value with updated inttra input xml value", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues_NotEqual(ActualConsignee_PartnerNameValue.length()+"", (ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_PartnerName", ScenarioDetailsHashMap)).length()+"", "Validating Consignee_PartnerName field length value inttra output xml value with updated inttra input xml value ", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues((ActualConsignee_PartnerNameValue.length()<=35)+"", ((ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_PartnerName", ScenarioDetailsHashMap)).length()>=35)+"", "validating Consignee_PartnerName field", ScenarioDetailsHashMap);

		//Validating Consignee ContactName Tag Value
		String ActualConsignee_ContactNameValue = GenericMethods.GetXMLValuesFromInttraUpdatedInputFileWithANDOperation(INTTRAUpdatedOutputMSGPath, "PartnerRole", "Consignee", "ContactInformation", "ContactName", "Informational");
		System.out.println("ActualConsignee_ContactNameValue"+ActualConsignee_ContactNameValue);
		GenericMethods.assertTwoValues_NotEqual(ActualConsignee_ContactNameValue, ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_ContactName", ScenarioDetailsHashMap), "Comparing inttra Consignee_ContactName output xml value with updated inttra input xml value", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues_NotEqual(ActualConsignee_ContactNameValue.length()+"", (ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_ContactName", ScenarioDetailsHashMap)).length()+"", "Validating Consignee_ContactName field length value inttra output xml value with updated inttra input xml value ", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues((ActualConsignee_ContactNameValue.length()<=35)+"", ((ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_ContactName", ScenarioDetailsHashMap)).length()>=35)+"", "validating Consignee_ContactName field", ScenarioDetailsHashMap);

		//Validating Consignee Telephone Tag Value
		String ActualConsignee_TelephoneValue = GenericMethods.GetXMLValuesFromInttraUpdatedInputFileWithANDOperation(INTTRAUpdatedOutputMSGPath, "PartnerRole", "Consignee", "ContactInformation", "CommunicationValue", "Telephone");
		System.out.println("ActualConsignee_TelephoneValue"+ActualConsignee_TelephoneValue);
		GenericMethods.assertTwoValues_NotEqual(ActualConsignee_TelephoneValue, ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_Telephone", ScenarioDetailsHashMap), "Comparing inttra Consignee_Telephone output xml value with updated inttra input xml value", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues_NotEqual(ActualConsignee_TelephoneValue.length()+"", (ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_Telephone", ScenarioDetailsHashMap)).length()+"", "Validating Consignee_Telephone field length value inttra output xml value with updated inttra input xml value ", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues((ActualConsignee_TelephoneValue.length()<=35)+"", ((ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_Telephone", ScenarioDetailsHashMap)).length()>=35)+"", "validating Consignee_Telephone field", ScenarioDetailsHashMap);

		//Validating Consignee FAX Tag Value
		String ActualConsignee_FAXValue = GenericMethods.GetXMLValuesFromInttraUpdatedInputFileWithANDOperation(INTTRAUpdatedOutputMSGPath, "PartnerRole", "Consignee", "ContactInformation", "CommunicationValue", "Fax");
		System.out.println("ActualConsignee_FAXValue"+ActualConsignee_FAXValue);
		GenericMethods.assertTwoValues_NotEqual(ActualConsignee_FAXValue, ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_FAX", ScenarioDetailsHashMap), "Comparing inttra Consignee_FAX output xml value with updated inttra input xml value", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues_NotEqual(ActualConsignee_FAXValue.length()+"", (ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_FAX", ScenarioDetailsHashMap)).length()+"", "Validating Consignee_FAX field length value inttra output xml value with updated inttra input xml value ", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues((ActualConsignee_FAXValue.length()<=35)+"", ((ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_FAX", ScenarioDetailsHashMap)).length()>=35)+"", "validating Consignee_FAX field", ScenarioDetailsHashMap);

		//Validating Consignee Email Tag Value
		String ActualConsignee_EmailValue = GenericMethods.GetXMLValuesFromInttraUpdatedInputFileWithANDOperation(INTTRAUpdatedOutputMSGPath, "PartnerRole", "Consignee", "ContactInformation", "CommunicationValue", "Email");
		System.out.println("ActualConsignee_EmailValue"+ActualConsignee_EmailValue);
		GenericMethods.assertTwoValues_NotEqual(ActualConsignee_EmailValue, ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_Email", ScenarioDetailsHashMap), "Comparing inttra Consignee_Email output xml value with updated inttra input xml value", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues_NotEqual(ActualConsignee_EmailValue.length()+"", (ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_Email", ScenarioDetailsHashMap)).length()+"", "Validating Consignee_Email field length value inttra output xml value with updated inttra input xml value ", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues((ActualConsignee_EmailValue.length()<=35)+"", ((ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_Email", ScenarioDetailsHashMap)).length()>=35)+"", "validating Consignee_Email field", ScenarioDetailsHashMap);

		//Validating Consignee Address Line Tag Value
		String ActualConsignee_AddressLineValue = GenericMethods.GetXMLValuesFromInttraUpdatedInputFileWithANDOperation(INTTRAUpdatedOutputMSGPath, "PartnerRole", "Consignee", "AddressInformation", "AddressLine", "");
		System.out.println("ActualConsignee_AddressLineValue"+ActualConsignee_AddressLineValue);
		GenericMethods.assertTwoValues_NotEqual(ActualConsignee_AddressLineValue, ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_AddressLine2", ScenarioDetailsHashMap), "Comparing inttra Consignee_AddressLine2 output xml value with updated inttra input xml value", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues_NotEqual(ActualConsignee_AddressLineValue.length()+"", (ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_AddressLine2", ScenarioDetailsHashMap)).length()+"", "Validating Consignee_AddressLine2 field length value inttra output xml value with updated inttra input xml value ", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues((ActualConsignee_AddressLineValue.length()<=35)+"", ((ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_AddressLine2", ScenarioDetailsHashMap)).length()>=35)+"", "validating Consignee_AddressLine2 field", ScenarioDetailsHashMap);

		//Validating Consignee City Tag Value
		String ActualConsignee_CityValue = GenericMethods.GetXMLValuesFromInttraUpdatedInputFileWithANDOperation(INTTRAUpdatedOutputMSGPath, "PartnerRole", "Consignee", "AddressInformation", "City", "");
		System.out.println("ActualConsignee_CityValue"+ActualConsignee_CityValue);
		GenericMethods.assertTwoValues_NotEqual(ActualConsignee_CityValue, ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_City", ScenarioDetailsHashMap), "Comparing inttra output xml Consignee_City value with updated inttra input xml value", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues_NotEqual(ActualConsignee_CityValue.length()+"", (ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_City", ScenarioDetailsHashMap)).length()+"", "Validating Consignee_City field length value inttra output xml value with updated inttra input xml value ", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues((ActualConsignee_CityValue.length()<=35)+"", ((ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_City", ScenarioDetailsHashMap)).length()>=35)+"", "validating Consignee_City field", ScenarioDetailsHashMap);

		//Validating Consignee StateProvince Tag Value
		String ActualConsignee_StateProvinceValue = GenericMethods.GetXMLValuesFromInttraUpdatedInputFileWithANDOperation(INTTRAUpdatedOutputMSGPath, "PartnerRole", "Consignee", "AddressInformation", "StateProvince", "");
		System.out.println("ActualConsignee_StateProvinceValue"+ActualConsignee_StateProvinceValue);
		GenericMethods.assertTwoValues_NotEqual(ActualConsignee_StateProvinceValue, ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_StateProvince", ScenarioDetailsHashMap), "Comparing inttra output Consignee_StateProvince xml value with updated inttra input xml value", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues_NotEqual(ActualConsignee_StateProvinceValue.length()+"", (ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_StateProvince", ScenarioDetailsHashMap)).length()+"", "Validating Consignee_StateProvince field length value inttra output xml value with updated inttra input xml value ", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues((ActualConsignee_StateProvinceValue.length()<=9)+"", ((ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_StateProvince", ScenarioDetailsHashMap)).length()>=35)+"", "validating Consignee_StateProvince field", ScenarioDetailsHashMap);

		//Validating Consignee PostalCode Tag Value
		String ActualConsignee_PostalCodeValue = GenericMethods.GetXMLValuesFromInttraUpdatedInputFileWithANDOperation(INTTRAUpdatedOutputMSGPath, "PartnerRole", "Consignee", "AddressInformation", "PostalCode", "");
		System.out.println("ActualConsignee_PostalCodeValue"+ActualConsignee_PostalCodeValue);
		GenericMethods.assertTwoValues_NotEqual(ActualConsignee_PostalCodeValue, ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_PostalCode", ScenarioDetailsHashMap), "Comparing inttra output xml Consignee_PostalCode field value with updated inttra input xml value", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues_NotEqual(ActualConsignee_PostalCodeValue.length()+"", (ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_PostalCode", ScenarioDetailsHashMap)).length()+"", "Validating Consignee_PostalCode field length value inttra output xml value with updated inttra input xml value ", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues((ActualConsignee_PostalCodeValue.length()<=17)+"", ((ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_PostalCode", ScenarioDetailsHashMap)).length()>=35)+"", "validating Consignee_PostalCode field", ScenarioDetailsHashMap);

		//Validating Consignee CountryCode Tag Value
		String ActualConsignee_CountryCodeValue = GenericMethods.GetXMLValuesFromInttraUpdatedInputFileWithANDOperation(INTTRAUpdatedOutputMSGPath, "PartnerRole", "Consignee", "AddressInformation", "CountryCode", "");
		System.out.println("ActualConsignee_CountryCodeValue"+ActualConsignee_CountryCodeValue);
		GenericMethods.assertTwoValues_NotEqual(ActualConsignee_CountryCodeValue, ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_CountryCode", ScenarioDetailsHashMap), "Comparing inttra output xml Consignee_CountryCode field value with updated inttra input xml value", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues_NotEqual(ActualConsignee_CountryCodeValue.length()+"", (ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_CountryCode", ScenarioDetailsHashMap)).length()+"", "Validating Consignee_CountryCode field length value inttra output xml value with updated inttra input xml value ", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues((ActualConsignee_CountryCodeValue.length()<=3)+"", ((ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Consignee_CountryCode", ScenarioDetailsHashMap)).length()>=35)+"", "validating Consignee_CountryCode field", ScenarioDetailsHashMap);

		//Validating Independent charges attrubute value
		if(ExcelUtils.getCellData("SE_OBL_INTTRA", RowNo, "IndividualCharges_Validation_Required", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			System.out.println("entered.............");
			GenericMethods.getAttributValue(INTTRAUpdatedOutputMSGPath, "ChargeCategory", "PrepaidorCollectIndicator", "XMLValidationSheet", "PrepaidorCollectIndicator", ScenarioDetailsHashMap, RowNo,"Comparing output XML independent charges value with input XML independent charges value");
		}

		//Validating ProductId ItemTypeIdCode value should not be **EMPTY**
		String ACtual_HarmonizedSystemText = GenericMethods.getTextContentFromDirectNode_AttributeNode_AttrubuteValue(INTTRAUpdatedOutputMSGPath, "ProductId", "ItemTypeIdCode", "HarmonizedSystem");
		System.out.println("ACtual_HarmonizedSystemText="+ACtual_HarmonizedSystemText);
		String ACtual_NCMCodeText = GenericMethods.getTextContentFromDirectNode_AttributeNode_AttrubuteValue(INTTRAUpdatedOutputMSGPath, "ProductId", "ItemTypeIdCode", "NCMCode");
		System.out.println("ACtual_NCMCodeText"+ACtual_NCMCodeText);
		String ACtual_USCensusScheduleBText = GenericMethods.getTextContentFromDirectNode_AttributeNode_AttrubuteValue(INTTRAUpdatedOutputMSGPath, "ProductId", "ItemTypeIdCode", "USCensusScheduleB");
		System.out.println("ACtual_USCensusScheduleBText"+ACtual_USCensusScheduleBText);
		String ExpectedText = "**EMPTY**";
		GenericMethods.assertTwoValues_NotEqual(ACtual_HarmonizedSystemText, ExpectedText, "Validating HarmonizedSystem Item Type should not having value like **EMPTY** ", ScenarioDetailsHashMap);
		//ACtual_NCMCodeText tag is not avaiable
		//GenericMethods.assertTwoValues_NotEqual(ACtual_NCMCodeText, ExpectedText, "Validating NCMCode Item Type should not having value like **EMPTY** ", ScenarioDetailsHashMap);
		GenericMethods.assertTwoValues_NotEqual(ACtual_USCensusScheduleBText, ExpectedText, "Validating USCensusScheduleB Item Type should not having value like **EMPTY** ", ScenarioDetailsHashMap);
		GenericMethods.getAttributValue(INTTRAUpdatedOutputMSGPath, "ShipmentIdentifier", "MessageStatus", "XMLValidationSheet", "ShipmentIdentifierMessageStatus", ScenarioDetailsHashMap, RowNo,"Comparing output XML ShipmentIdentifier MessageStatus while DocumentVersion is 000002");
		String ActualContainer = GenericMethods.getTextContentFromDirectNode_AttributeNode_AttrubuteValue(INTTRAUpdatedOutputMSGPath, "EquipmentIdentifier", "EquipmentSupplier", "Carrier");
		GenericMethods.assertTwoValues(ActualContainer, ExcelUtils.getCellData("SE_OBLContainersDetails", RowNo, "ContainerNumber", ScenarioDetailsHashMap), "Validating Container Number in inttra output xml file", ScenarioDetailsHashMap);

		//Validating LetterOfCreditNumber tag details
		String ActualLetterOfCreditNumber = GenericMethods.GetXMLValues_WithoutAttribute(INTTRAUpdatedOutputMSGPath, "LetterOfCreditNumber");
		GenericMethods.assertTwoValues(ActualLetterOfCreditNumber, ExcelUtils.getCellData("SE_OBL", RowNo, "ReferenceNo", ScenarioDetailsHashMap), "Validating ActualLetterOfCreditNumber in inttra output xml", ScenarioDetailsHashMap);
		String ActualReferenceTypeExpiryDate = (GenericMethods.getTextContentFromDirectNode_AttributeNode_AttrubuteValue(INTTRAUpdatedOutputMSGPath, "DateTime", "DateType", "ExpiryDate")).replace("-", "");
		GenericMethods.assertTwoValues(ActualReferenceTypeExpiryDate, (ExcelUtils.getCellData("SE_OBL", RowNo, "ReferenceTypeExpiryDate", ScenarioDetailsHashMap)).replace("-", ""), "Validating ReferenceTypeExpiryDate in inttra output xml", ScenarioDetailsHashMap);
		String ActualReferenceTypeEffectiveDate = (GenericMethods.getTextContentFromDirectNode_AttributeNode_AttrubuteValue(INTTRAUpdatedOutputMSGPath, "DateTime", "DateType", "IssueDate")).replace("-", "");
		GenericMethods.assertTwoValues(ActualReferenceTypeEffectiveDate, (ExcelUtils.getCellData("SE_OBL", RowNo, "ReferenceTypeEffectiveDate", ScenarioDetailsHashMap)).replace("-", ""), "Validating ReferenceTypeEffectiveDate in inttra output xml", ScenarioDetailsHashMap);

		//Validating GoodsDetails PackageDetail Level Outer and Inner
		//Outer Level validation
		GenericMethods.getAttributValue(INTTRAUpdatedOutputMSGPath, "PackageDetail", "Level", "XMLValidationSheet", "PackageDetailLevel", ScenarioDetailsHashMap, RowNo, "Validating GoodsDetails of Package Detail Level in inttra output xml");
		String Actual_OuterNumberOfPackages = GenericMethods.GetXMLValuesFromInttraUpdatedInputFileWithOROperation(INTTRAUpdatedOutputMSGPath, "PackageDetail", "Outer", "NumberOfPackages", "", "");
		System.out.println("Actual_OuterNumberOfPackages"+Actual_OuterNumberOfPackages);
		GenericMethods.assertTwoValues(Actual_OuterNumberOfPackages, ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Packages", ScenarioDetailsHashMap), "Validating PackageDetail NumberOfPackages of Outer Level in inttra output xml", ScenarioDetailsHashMap);
		String Actual_OuterPackageTypeCode = GenericMethods.GetXMLValuesFromInttraUpdatedInputFileWithOROperation(INTTRAUpdatedOutputMSGPath, "PackageDetail", "Outer", "PackageTypeCode", "", "");
		System.out.println("Actual_OuterPackageTypeCode"+Actual_OuterPackageTypeCode);
		GenericMethods.assertTwoValues(Actual_OuterPackageTypeCode, ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Type", ScenarioDetailsHashMap), "Validating PackageDetail PackageTypeCode of Outer Level in inttra output xml", ScenarioDetailsHashMap);
		String Actual_OuterPackageTypeDescription = GenericMethods.GetXMLValuesFromInttraUpdatedInputFileWithOROperation(INTTRAUpdatedOutputMSGPath, "PackageDetail", "Outer", "PackageTypeDescription", "", "");
		System.out.println("Actual_OuterPackageTypeDescription"+Actual_OuterPackageTypeDescription);
		GenericMethods.assertTwoValues(Actual_OuterPackageTypeDescription, ExcelUtils.getCellData("XMLValidationSheet", RowNo, "OuterPackageTypeDescription", ScenarioDetailsHashMap), "Validating PackageDetail PackageTypeCode of Outer Level in inttra output xml", ScenarioDetailsHashMap);

		//Inner Level validation
		String Actual_InnerNumberOfPackages = GenericMethods.GetXMLValuesFromInttraUpdatedInputFileWithOROperation(INTTRAUpdatedOutputMSGPath, "PackageDetail", "Inner", "NumberOfPackages", "", "");
		System.out.println("Actual_InnerNumberOfPackages"+Actual_InnerNumberOfPackages);
		GenericMethods.assertTwoValues(Actual_InnerNumberOfPackages, ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "SubPack", ScenarioDetailsHashMap), "Validating PackageDetail NumberOfPackages of Inner Level in inttra output xml", ScenarioDetailsHashMap);
		String Actual_InnerPackageTypeCode = GenericMethods.GetXMLValuesFromInttraUpdatedInputFileWithOROperation(INTTRAUpdatedOutputMSGPath, "PackageDetail", "Inner", "PackageTypeCode", "", "");
		System.out.println("Actual_InnerPackageTypeCode"+Actual_InnerPackageTypeCode);
		GenericMethods.assertTwoValues(Actual_InnerPackageTypeCode, ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "SubType", ScenarioDetailsHashMap), "Validating PackageDetail PackageTypeCode of Inner Level in inttra output xml", ScenarioDetailsHashMap);
		String Actual_InnerPackageTypeDescription = GenericMethods.GetXMLValuesFromInttraUpdatedInputFileWithOROperation(INTTRAUpdatedOutputMSGPath, "PackageDetail", "Inner", "PackageTypeDescription", "", "");
		System.out.println("Actual_InnerPackageTypeDescription"+Actual_InnerPackageTypeDescription);
		GenericMethods.assertTwoValues(Actual_InnerPackageTypeDescription, ExcelUtils.getCellData("XMLValidationSheet", RowNo, "InnerPackageTypeDescription", ScenarioDetailsHashMap), "Validating PackageDetail Package Type Description of Inner Level in inttra output xml", ScenarioDetailsHashMap);


	}



	public static void inttra_XMLValidation_Continue(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{

		GenericMethods.pauseExecution(4000);
		GenericMethods.CopyXMLFiles(System.getProperty("user.dir")+ "\\INTTRA\\ScenarioFiles\\InttraValidationXMLFiles\\"+"Production_XML.xml", System.getProperty("user.dir")+ "\\INTTRA\\ScenarioFiles\\InttraValidationXMLFiles\\" + res.getExectionDateTime()+"_"+"Production_XML.xml");
		String INTTRAInputMSGPath=System.getProperty("user.dir")+ "\\INTTRA\\ScenarioFiles\\InttraValidationXMLFiles\\" + res.getExectionDateTime()+"_"+"Production_XML.xml";

		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "SealNo1", "278154", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Container_SealNo1", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "SealNo2", "D7033556", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Container_SealNo2", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "GrossWtStuffed", "12632.000", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "SplitGoodsGrossWeight", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "VolumeStuffed", "0.000", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "SplitGoodsGrossVolume", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLduplicateTagValues(INTTRAInputMSGPath, "CountryCode", "PL", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "ManifestFilingCountryCodeAgency", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLValues(INTTRAInputMSGPath, "GoodsDescription", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "GoodsDescription", ScenarioDetailsHashMap));
		GenericMethods.UpdateXMLValues(INTTRAInputMSGPath, "PackageMarks", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "Package_MarksAndNumbers", ScenarioDetailsHashMap));
		GenericMethods.setAttributValue(INTTRAInputMSGPath, "Commodity", "HSCODE", ExcelUtils.getCellData("XMLValidationSheet", RowNo, "HarmonizedSystem_ProductId", ScenarioDetailsHashMap));

		//String XMLFileName = res.getExectionDateTime()+"_ETJournalController_Updated.fsc.xml";
		String XMLFileName = res.getExectionDateTime()+"_"+"Production_XML.xml";
		GenericMethods.UploadFileToFTP("10.200.35.11", "kfftp", "aCmgRZ1%2Ycf", INTTRAInputMSGPath, "kf/outbound/inttra/si/out/"+XMLFileName);
		System.out.println("XMLFileName"+XMLFileName);
		System.out.println("File uploaded to FTP");
		GenericMethods.pauseExecution(9000);
		String Workspace_INTTRAInputPath = System.getProperty("user.dir")+ "\\INTTRA\\ScenarioFiles\\InttraValidationXMLFiles\\Input\\";
		System.out.println("Workspace_INTTRAInputPath="+Workspace_INTTRAInputPath);

		GenericMethods.copyUpdatedFileFromFTP("10.200.35.11", "kf/outbound/inttra/si/in/", XMLFileName, Workspace_INTTRAInputPath, "kfftp", "aCmgRZ1%2Ycf");
		String INTTRAUpdatedOutputMSGPath = Workspace_INTTRAInputPath+XMLFileName;
		System.out.println("INTTRAUpdatedOutputMSGPath"+INTTRAUpdatedOutputMSGPath);


		//Temporary Path(need to remove once copy problem solved in FTP)
		INTTRAUpdatedOutputMSGPath = System.getProperty("user.dir")+ "\\INTTRA\\ScenarioFiles\\InttraValidationXMLFiles\\Input\\Production_Output.xml";
		//String ActualConsignee_CountryCodeValue = GenericMethods.GetXMLValuesFromInttraUpdatedInputFile(INTTRAUpdatedOutputMSGPath, "EquipmentDetails", "", "EquipmentSeal", "Carrier", "Carrier");


		GenericMethods.getvalueFromDirectNode_AttributeNode_AttrubuteValue(INTTRAUpdatedOutputMSGPath, "PackageDetailComments", "CommentType", "GoodsDescription", "XMLValidationSheet","GoodsDescription", "PackageDetailComments", 35 , ScenarioDetailsHashMap, RowNo);
		GenericMethods.getXMLTagvalueFromDirectNode_AttributeNode_AttrubuteValue_WithValidation(INTTRAUpdatedOutputMSGPath, "EquipmentSeal", "SealingParty", "Carrier", "XMLValidationSheet", "Carrier_SealingNumber", "Container_SealNo1", 15, ScenarioDetailsHashMap, RowNo);
		GenericMethods.getvalueFromDirectNode_AttributeNode_AttrubuteValue(INTTRAUpdatedOutputMSGPath, "ProductId", "ItemTypeIdCode", "HarmonizedSystem", "XMLValidationSheet", "HarmonizedSystem_ProductId", "HarmonizedSystem_ProductId", 999, ScenarioDetailsHashMap, RowNo);
		GenericMethods.getvalueFromDirectNode_AttributeNode_AttrubuteValue(INTTRAUpdatedOutputMSGPath, "SplitGoodsGrossVolume", "UOM", "MTQ", "XMLValidationSheet", "SplitGoodsGrossVolume", "SplitGoodsGrossVolume", 50, ScenarioDetailsHashMap, RowNo);
		GenericMethods.getvalueFromDirectNode_AttributeNode_AttrubuteValue(INTTRAUpdatedOutputMSGPath, "SplitGoodsGrossWeight", "UOM", "KGM", "XMLValidationSheet", "SplitGoodsGrossWeight", "SplitGoodsGrossWeight", 50, ScenarioDetailsHashMap, RowNo);
		GenericMethods.getvalueFromDirectNode_AttributeNode_AttrubuteValue(INTTRAUpdatedOutputMSGPath, "ManifestFilingCountryCode", "Agency", "UN", "XMLValidationSheet", "ManifestFilingCountryCodeAgency", "ManifestFilingCountryCodeAgency", 50, ScenarioDetailsHashMap, RowNo);
		GenericMethods.getDirectNodeTextValue(INTTRAUpdatedOutputMSGPath, "Marks", "XMLValidationSheet", "Package_MarksAndNumbers", "MarksList", 999, ScenarioDetailsHashMap, RowNo);

	}

	public static void pictOceanExportsOBL_MileStones_NotesAndStatus(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		oceanExportsOBL_SearchList(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "NotesAndStatus_Tab", ScenarioDetailsHashMap, 10),30);
		GenericMethods.pauseExecution(5000);
		try{
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}


		GenericMethods.clickElement(driver, By.id("houseManualNo1"), null, 10);
		GenericMethods.pauseExecution(6000);
		String TextValue = ExcelUtils.getCellData("SE_OBLNotes", rowNo, "SSL", ScenarioDetailsHashMap);
		((JavascriptExecutor)driver).executeScript("document.getElementById('editPhysical2').value='TextValue'");

		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement triggerDropDown = driver.findElement(By.id("editSSL2"));
		triggerDropDown.click();
		triggerDropDown.sendKeys(Keys.ARROW_DOWN);
		triggerDropDown.click();

		//GenericMethods.selectOptionFromDropDown(driver, By.xpath("html/body/form/table/tbody/tr[3]/td/fieldset/fieldset[1]/div/table/tbody/tr[1]/td[4]/select"), null, ExcelUtils.getCellData("SE_OBLNotes", rowNo, "SSL", ScenarioDetailsHashMap));
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10),20);
		try{
			GenericMethods.handleAlert(driver, "accept");
		}catch (Exception e) {
			//System.out.println("no Alerts present");
		}

		/*int HBLRowGrid = driver.findElements(By.xpath("html/body/form/table/tbody/tr[3]/td/fieldset/fieldset[2]/div/table/tbody/tr")).size();
		for (int RowGridNo = 1; RowGridNo <= HBLRowGrid; RowGridNo++) {
			System.out.println("Actual "+GenericMethods.getInnerText(driver, By.id("houseManualNo"+RowGridNo), null, 10));
			System.out.println("Expected "+ExcelUtils.getCellData("SE_HBLMainDetails", RowGridNo, "HBLId", ScenarioDetailsHashMap));
			if(GenericMethods.getInnerText(driver, By.id("houseManualNo"+RowGridNo), null, 10).equalsIgnoreCase(ExcelUtils.getCellData("SE_HBLMainDetails", RowGridNo, "HBLId", ScenarioDetailsHashMap)))
			{
				System.out.println("inside if -----------------");
				GenericMethods.clickElement(driver, By.id("houseManualNo"+RowGridNo), null, 10);
				GenericMethods.pauseExecution(2000);
				GenericMethods.replaceTextofTextField(driver, By.id("editPhysical"+RowGridNo), null, "adad", 10);
				GenericMethods.selectOptionFromDropDown(driver, By.id("editSSL"+RowGridNo), null, "Released");
				GenericMethods.replaceTextofTextField(driver, By.id("editCustoms"+RowGridNo), null, "adad", 10);

			}

		}*/
	}

	//Sangeeta- FUNC041 - Below method is to perform unblocking of the shipment for Denied party shipment
	public static void Licensee_DeniedPartyUnblocking(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.Licensee_DeniedPartyUnblocking, "Denied Party",ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DeniedParty_ShipmentReference", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_OBL", RowNo,"CarrierBookingRefNum", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap,10), 2);
		GenericMethods.clickElement(driver, By.id("multiSelectCheckbox1"),null, 2);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "MainDetails", ScenarioDetailsHashMap,10), 2);

		for (int GridRowID = 1; GridRowID <= driver.findElements(By.xpath("//table[@id='deniedPartyUnBlockingGrid']/tbody/tr")).size(); GridRowID++) 
		{
			String xpathPrefix = "//table[@id='deniedPartyUnBlockingGrid']/tbody/tr["+GridRowID+"]/";
			GenericMethods.clickElement(driver, By.xpath(xpathPrefix+"td[1]/input"),null, 2);
			System.out.println("value-1111---"+GenericMethods.getInnerText(driver,  By.xpath(xpathPrefix+"td[5]"),null, 10));
			System.out.println("value-2222---"+GenericMethods.getInnerText(driver,  By.xpath(xpathPrefix+"td[9]"),null, 10));
		}

		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DeniedParty_ReasonCode", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_OBL", RowNo,"DeniedParty_ReasonCode", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DeniedParty_Comments", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SE_OBL", RowNo,"DeniedParty_Comments", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null,orDecisionTable.getElement(driver, "DeniedParty_ApplyComments", ScenarioDetailsHashMap,10), 2);
		GenericMethods.clickElement(driver, null,orDecisionTable.getElement(driver, "DeniedParty_ApproveAll", ScenarioDetailsHashMap,10), 2);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
		ExcelUtils.setCellData("SE_OBL", RowNo, "DeniedStatus", "N", ScenarioDetailsHashMap);
	}
	public static void OBL_Save(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
	GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10),20);
	}
}
