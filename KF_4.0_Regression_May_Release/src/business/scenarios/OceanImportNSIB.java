package business.scenarios;

import global.reusables.ExcelUtils;
import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import DriverMethods.WebDriverInitilization;
import app.reuseables.AppReusableMethods;
import app.reuseables.ETransMenu;

public class OceanImportNSIB {
	static ObjectRepository Common = new ObjectRepository(
			System.getProperty("user.dir")
			+ "/ObjectRepository/CommonObjects.xml");
	static ObjectRepository oROceanNsibObl = new ObjectRepository(
			System.getProperty("user.dir") + "/ObjectRepository/OceanImportNSIB.xml");
	static ObjectRepository OrDo=new ObjectRepository(
			System.getProperty("user.dir")+"/ObjectRepository/SeaDO.xml");

	public static void NsibObl_Copy(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) {
		/*AppReusableMethods.selectMenu(driver, ETransMenu.oceanNsibObl,"OCEAN NSIB MBL",ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(5000);	
		GenericMethods.clickElement(driver, null,
						Common.getElement(driver, "CopyButton", ScenarioDetailsHashMap,10), 2);
		 */		GenericMethods.pauseExecution(5000);
		 AppReusableMethods.unblockShipment(driver, "HBL789789", ScenarioDetailsHashMap);

		 //				oceanImportNsibObl_Add(driver, ScenarioDetailsHashMap, rowNo);
		 //				addHblDetails(driver, ScenarioDetailsHashMap, rowNo);
		 //				addDeliveryInstructions(driver, ScenarioDetailsHashMap, rowNo);
		 //				addDeliveryInstructions(driver, ScenarioDetailsHashMap, rowNo);
	}

	/**
	 * This method is to add NSIB OBL
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains Records Row number which was given in
	 *            Testdata
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @throws AWTException 
	 * @Author By: Prasanna Modugu
	 */
	public static void oceanImportNsibObl_Add(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) throws AWTException {

		//		AppReusableMethods.unblockShipment(driver,"test", ScenarioDetailsHashMap);
		AppReusableMethods.selectMenu(driver, ETransMenu.oceanNsibObl,"OCEAN NSIB MBL",ScenarioDetailsHashMap);
		//		GenericMethods.clickElement(driver, null, Common.getElement(driver, "AddButton1", ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(10000);
		AppReusableMethods.validateTimeZoneFormat(driver, oROceanNsibObl.getElement(driver, "CreatedDate", ScenarioDetailsHashMap, 5), oROceanNsibObl.getElement(driver, "CreatedTime", ScenarioDetailsHashMap, 5), GenericMethods.getPropertyValue("timeZone", WebDriverInitilization.configurationStructurePath), ScenarioDetailsHashMap);
		
		//Sandeep - Below method is to verify  functional validation FUNC064
		GenericMethods.assertTwoValues(driver.findElement(By.id("destinationGateway")).getAttribute("previousvalue"), ExcelUtils.getCellData("Terminals", rowNo, "Sea_OriginTerminal", ScenarioDetailsHashMap).split("#")[0], "FUNC064 Verifying Department Defaultation with Branch mapped with single department", ScenarioDetailsHashMap);
		//code ends here
		
		/*GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "", 5),GenericMethods.currentDate(), 5);
		GenericMethods.clickElement(driver, null, Common.getElement(driver, "", 10), 10);
		GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "", 10), ExcelUtils.getCellData("", rowNo,"", ScenarioDetailsHashMap));
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "", ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "", ScenarioDetailsHashMap,10), 10);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "", ScenarioDetailsHashMap,10), 10);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "", ScenarioDetailsHashMap,10), 10);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "", ScenarioDetailsHashMap,10), 10);*/
		String randomMBLNo=ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLNo", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(5)+"";
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "MBLNo", ScenarioDetailsHashMap, 5),randomMBLNo, 5);
		ExcelUtils.setCellData("SI_NSIBOBL", rowNo, "MBLNo", randomMBLNo, ScenarioDetailsHashMap);
		ExcelUtils.setCellData_Without_DataSet("SE_OBL", rowNo, "OBL_Id", randomMBLNo, ScenarioDetailsHashMap);
		ExcelUtils.setCellData_Without_DataSet("SI_Arrival", rowNo,"MBLId",randomMBLNo, ScenarioDetailsHashMap);
		for (int i = 1; i <= ExcelUtils.getCellDataRowCount("SI_Arrival", ScenarioDetailsHashMap); i++) {
			ScenarioDetailsHashMap.put("DataSetNo", i+"");
			ExcelUtils.setCellData("SI_Arrival", rowNo, "MBLId", randomMBLNo, ScenarioDetailsHashMap);
		}
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "MBLDate", ScenarioDetailsHashMap,5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLDate", ScenarioDetailsHashMap), 5);
		//		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "LoadType", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "LoadType", ScenarioDetailsHashMap), 5);
		GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "LoadType", ScenarioDetailsHashMap, 5), ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "LoadType", ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "OriginBranch", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "OriginBranch", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "OriginDept", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "OriginDept", ScenarioDetailsHashMap), 5);
		//		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "DestBranch", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "DestBranch", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "DestDept", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "DestDept", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "PlaceOfReceipt", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "PlaceofReceipt", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "PortOfLoading", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "PortofLoading", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "PortOfDischarge", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "PortofDischarge", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "PlaceOfDelivery", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "PlaceofDelivery", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "InstructionRemarks", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "InstructionsRemarks", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "CarrierId", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "Carrier", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "VesselName", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "Vessel", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "VoyageNo", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "VoyageNo", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "ETD", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "ETD", ScenarioDetailsHashMap), 5);
		GenericMethods.action_Key(driver, Keys.TAB);
		AppReusableMethods.validateDateFormat(driver, oROceanNsibObl.getElement(driver, "ETD", ScenarioDetailsHashMap, 5), ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "ETD", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "ETA", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "ETA", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "ServiceLevel", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "ServiceLevel", ScenarioDetailsHashMap), 5);
		GenericMethods.pauseExecution(5000);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "NoOfContainers", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "NoOfContainers", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "NoOfHBLs", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "NoOfHBLs", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "NoOfPCS", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "NoOfPCS", ScenarioDetailsHashMap), 5);
		GenericMethods.pauseExecution(2000);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "TotalWeight", ScenarioDetailsHashMap,10), 10);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "TotalWeight", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "TotalWeight", ScenarioDetailsHashMap), 5);
		GenericMethods.pauseExecution(2000);
		
		//Added for Erating functionality-Masthan
		if(ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "eRatingValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "OriginMoveType", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "OriginMoveType", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "DestMoveType", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "DestMoveType", ScenarioDetailsHashMap), 5);

			
			
			if(ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "RatingType", ScenarioDetailsHashMap).equalsIgnoreCase("ManualRate"))
			{
				GenericMethods.selectOptionFromDropDown(driver, null,oROceanNsibObl.getElement(driver, "MBLContractType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "Contract_Quote_Tariff", ScenarioDetailsHashMap));
				oROceanNsibObl.getElement(driver, "CarrierContractId", ScenarioDetailsHashMap, 10).clear();
				GenericMethods.pauseExecution(2000);
				GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "ContractLOV", ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(5000);
				GenericMethods.selectWindow(driver);
				GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "GridOrigin", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.pauseExecution(4000);
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
		}
		//End here

		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "TotalUOW", ScenarioDetailsHashMap,10), 10);
		GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "TotalUOW", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "TotalUOW", ScenarioDetailsHashMap));
		//Code added for UOW conversion verification.
		GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "TotalUOW", ScenarioDetailsHashMap, 5),"LB");
		String value=null;
		value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "TotalWeight", ScenarioDetailsHashMap))*2.20462262185, ExcelUtils.getCellData("DecimalPrecision", rowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
		//Commenting because of issue KF-4672
		//GenericMethods.assertTwoValues(oROceanNsibObl.getElement(driver, "TotalWeight", ScenarioDetailsHashMap, 5).getAttribute("value").replace(" ", ""),value+"", "Validating Gross Weight COnversion In UOW KG to LB", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "TotalUOW", ScenarioDetailsHashMap, 5),"TON");
		value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "TotalWeight", ScenarioDetailsHashMap))*0.001, ExcelUtils.getCellData("DecimalPrecision", rowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
		//Commenting because of issue KF-4672
		//GenericMethods.assertTwoValues(oROceanNsibObl.getElement(driver, "TotalWeight", ScenarioDetailsHashMap, 5).getAttribute("value").trim(),value+"", "Validating Gross Weight COnversion In UOW KG to LB", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "TotalUOW", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "TotalUOW", ScenarioDetailsHashMap));
		GenericMethods.pauseExecution(2000);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "TotalVolume", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "TotalVolume", ScenarioDetailsHashMap), 5);
		
		
		//		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "TotalUOM", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "TotalUOM", ScenarioDetailsHashMap), 5);
		GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "PaymentTerms", ScenarioDetailsHashMap, 5), ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "PaymentTerms", ScenarioDetailsHashMap));
		//		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "PaymentTerms", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "PaymentTerms", ScenarioDetailsHashMap), 5);
		//		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "ContainerType", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "Container_Type", ScenarioDetailsHashMap), 5);
		
		
		GenericMethods.pauseExecution(5000);
		addContainerDetails(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.pauseExecution(10000);
		addHblDetails(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.pauseExecution(5000);
		updateHblAndRefIds(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.pauseExecution(5000);
//		Added code for hbl charges and costs validation.- By Prasanna Modugu
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_HblDetails", ScenarioDetailsHashMap,10), 10);
		GenericMethods.handleAlert(driver, "accept");
		addDeliveryInstructions(driver, ScenarioDetailsHashMap, rowNo);
		
		//Added for Erating functionality-Masthan
		if(ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "eRatingValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			NSIB_MBLChargesCosts(driver,ScenarioDetailsHashMap,rowNo);

		}
		//Code ends
		
		/*GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_StuffingDetails", ScenarioDetailsHashMap, 5), 5);
		GenericMethods.pauseExecution(5000);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_MBLChargesCode", ScenarioDetailsHashMap, 5), 5);
		GenericMethods.pauseExecution(5000);
		GenericMethods.handleAlert(driver, "accept");
		GenericMethods.doubleClickOnElement(driver, null, oROceanNsibObl.getElement(driver, "ChargeCode", ScenarioDetailsHashMap, 5), 5);
		driver.switchTo().activeElement().sendKeys(ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLChargesCosts_ChargeCode", ScenarioDetailsHashMap));
		GenericMethods.pauseExecution(1000);
		oROceanNsibObl.getElement(driver, "ChargeCode", ScenarioDetailsHashMap, 5).click();
		GenericMethods.pauseExecution(1000);
//		oROceanNsibObl.getElement(driver, "ChargeCode", ScenarioDetailsHashMap, 5).sendKeys(ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLChargesCosts_ChargeCode", ScenarioDetailsHashMap));
//		oROceanNsibObl.clickElement(driver, By.xpath("//td[contains(text(),'"+ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLChargesCosts_ChargeCode", ScenarioDetailsHashMap)+"')]"), null, 5);
//		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "ChargeCode", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLChargesCosts_ChargeCode", ScenarioDetailsHashMap), 5);
		GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "PrepaidAndCollect", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLChargesCosts_PrepaidAndCollect", ScenarioDetailsHashMap));
		GenericMethods.pauseExecution(2000);
//		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Textbox_BillToParty", ScenarioDetailsHashMap, 5), 10);
		GenericMethods.action_Key(driver, Keys.TAB);
		GenericMethods.pauseExecution(1000);
		GenericMethods.switchToActiveElement(driver).sendKeys(ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLChargesCosts_BillToParty", ScenarioDetailsHashMap));
//		oROceanNsibObl.getElement(driver, "Textbox_BillToParty", ScenarioDetailsHashMap, 5).sendKeys(ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLChargesCosts_BillToParty", ScenarioDetailsHashMap));
		GenericMethods.pauseExecution(2000);
		oROceanNsibObl.clickElement(driver, By.xpath("//td[contains(text(),'"+ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLChargesCosts_BillToParty", ScenarioDetailsHashMap)+"')]"), null, 5);
		System.out.println("xpath****"+"//td[contains(text(),'"+ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLChargesCosts_BillToParty", ScenarioDetailsHashMap)+"')]");
		//GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "BillToParty", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLChargesCosts_BillToParty", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "PriceAmt", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLChargesCosts_SalesPriceAmount", ScenarioDetailsHashMap), 5);
		GenericMethods.action_Key(driver, Keys.TAB);
		oROceanNsibObl.getElement(driver, "PriceCurrency", ScenarioDetailsHashMap, 5).sendKeys(ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLChargesCosts_SalesPriceCurrency", ScenarioDetailsHashMap));
//		oROceanNsibObl.getElement(driver, "PriceCurrency", ScenarioDetailsHashMap, 5).sendKeys(ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLChargesCosts_SalesPriceCurrency", ScenarioDetailsHashMap));
//		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "PriceCurrency", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLChargesCosts_SalesPriceCurrency", ScenarioDetailsHashMap), 5);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Button_Invoice", ScenarioDetailsHashMap, 5), 5);		
		GenericMethods.pauseExecution(2000);
		GenericMethods.handleAlert(driver, "accept");
		GenericMethods.pauseExecution(2000);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Button_Request", ScenarioDetailsHashMap, 5), 5);
		GenericMethods.pauseExecution(5000);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Button_Cancel", ScenarioDetailsHashMap, 5), 5);
		GenericMethods.pauseExecution(5000);
		GenericMethods.doubleClickOnElement(driver, null, oROceanNsibObl.getElement(driver, "Vendor", ScenarioDetailsHashMap, 5), 5);
//		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "Vendor", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLChargesCosts_Vendor", ScenarioDetailsHashMap), 5);
		oROceanNsibObl.getElement(driver, "Vendor", ScenarioDetailsHashMap, 5).sendKeys(ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLChargesCosts_Vendor", ScenarioDetailsHashMap));
		oROceanNsibObl.clickElement(driver, By.xpath("//td[contains(text(),'"+ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLChargesCosts_Vendor", ScenarioDetailsHashMap)+"')]"), null, 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "Vendor_PriceAmt", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLChargesCosts_Vendor_PriceAmount", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "Vendor_Reference", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLChargesCosts_Vendor_Reference", ScenarioDetailsHashMap), 5);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Checkbox_Vendor", ScenarioDetailsHashMap, 5), 5);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Button_PostSales", ScenarioDetailsHashMap, 5), 5);
		GenericMethods.handleAlert(driver, "accept");
		GenericMethods.pauseExecution(3000);
		GenericMethods.selectWindow(driver);
		GenericMethods.pauseExecution(3000);
		driver.close();
		GenericMethods.pauseExecution(3000);
		GenericMethods.switchToParent(driver);*/
		
		
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10),20);
		String OnSave_RefText=GenericMethods.getInnerText(driver, null, oROceanNsibObl.getElement(driver, "Label_MBLSuccMsg_OnSave", ScenarioDetailsHashMap, 10), 10);
		String [] ShipmentRefTextSplit=OnSave_RefText.split(":");
		String refId =ShipmentRefTextSplit[1];
		ExcelUtils.setCellData("SI_NSIBOBL", rowNo, "Shipment_ReferenceId", refId, ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "MBLNo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLNo", ScenarioDetailsHashMap) , 10);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
		GenericMethods.assertInnerText(driver, null, oROceanNsibObl.getElement(driver, "SearchGridOblId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLNo",ScenarioDetailsHashMap), "MBLNo", "Validating MBL Id", ScenarioDetailsHashMap);
		//		Code added to get the status of the record as complete.
		GenericMethods.clickElement(driver, By.id("MBL_MAIN_DETAILS"),null,20);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "TotalWeight", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "TotalWeight", ScenarioDetailsHashMap), 5);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10),20);
		GenericMethods.pauseExecution(3000);
		AppReusableMethods.unblockShipment(driver, ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBLIdToUnblock", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		int rowCount=ExcelUtils.getCellDataRowCount("SI_NSIBOBL_ContainerDetails", ScenarioDetailsHashMap);
		for (int i =1; i <= rowCount; i++) {
			String containerNo=ExcelUtils.getCellData("SE_OBLContainersDetails", i, "ContainerNumber", ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_ArrivalContainers", i, "ContainerReq", "MBL", ScenarioDetailsHashMap);
			//			ExcelUtils.setCellData("SE_OBLContainers", i, "HBL_ID", hblID, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_ArrivalContainers", i, "MBLId", randomMBLNo, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_ArrivalContainers", i, "Container_Number", containerNo, ScenarioDetailsHashMap);
		}	
	}
	public static void oceanImportNsibObl_AddNew(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) {

		//		AppReusableMethods.unblockShipment(driver,"test", ScenarioDetailsHashMap);
		AppReusableMethods.selectMenu(driver, ETransMenu.oceanNsibObl,"OCEAN NSIB MBL",ScenarioDetailsHashMap);
		//		GenericMethods.clickElement(driver, null, Common.getElement(driver, "AddButton1", ScenarioDetailsHashMap, 10), 10);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton",ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(10000);
		if(ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "TimeZoneFormat", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
		AppReusableMethods.validateTimeZoneFormat(driver, oROceanNsibObl.getElement(driver, "CreatedDate", ScenarioDetailsHashMap, 5), oROceanNsibObl.getElement(driver, "CreatedTime", ScenarioDetailsHashMap, 5), GenericMethods.getPropertyValue("timeZone", WebDriverInitilization.configurationStructurePath), ScenarioDetailsHashMap);
		}
		//Sandeep - Below method is to verify  functional validation FUNC064
		GenericMethods.assertTwoValues(driver.findElement(By.id("destinationGateway")).getAttribute("previousvalue"), ExcelUtils.getCellData("Terminals", rowNo, "Sea_OriginTerminal", ScenarioDetailsHashMap).split("#")[0], "FUNC064 Verifying Department Defaultation with Branch mapped with single department", ScenarioDetailsHashMap);
		//code ends here
		
		String randomMBLNo=ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLNo", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(5)+"";
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "MBLNo", ScenarioDetailsHashMap, 5),randomMBLNo, 5);
		ExcelUtils.setCellData("SI_NSIBOBL", rowNo, "MBLNo", randomMBLNo, ScenarioDetailsHashMap);
		for (int i = 1; i <= ExcelUtils.getCellDataRowCount("SI_Arrival", ScenarioDetailsHashMap); i++) {
			ScenarioDetailsHashMap.put("DataSetNo", i+"");
			ExcelUtils.setCellData("SI_Arrival", rowNo, "MBLId", randomMBLNo, ScenarioDetailsHashMap);
		}
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "MBLDate", ScenarioDetailsHashMap,5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLDate", ScenarioDetailsHashMap), 5);
		//		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "LoadType", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "LoadType", ScenarioDetailsHashMap), 5);
		GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "LoadType", ScenarioDetailsHashMap, 5), ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "LoadType", ScenarioDetailsHashMap));
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "OriginBranch", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "OriginBranch", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "OriginDept", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "OriginDept", ScenarioDetailsHashMap), 5);
		//		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "DestBranch", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "DestBranch", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "DestDept", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "DestDept", ScenarioDetailsHashMap), 5);
		if(ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "Contract_Quote_Tariff", ScenarioDetailsHashMap).equalsIgnoreCase("Tariff"))
		{	
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "PlaceOfReceipt", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "PlaceofReceipt", ScenarioDetailsHashMap), 5);
		}
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "PortOfLoading", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "PortofLoading", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "PortOfDischarge", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "PortofDischarge", ScenarioDetailsHashMap), 5);
		if(ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "Contract_Quote_Tariff", ScenarioDetailsHashMap).equalsIgnoreCase("Tariff"))
		{
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "PlaceOfDelivery", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "PlaceofDelivery", ScenarioDetailsHashMap), 5);
		}
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "InstructionRemarks", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "InstructionsRemarks", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "CarrierId", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "Carrier", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "VesselName", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "Vessel", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "VoyageNo", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "VoyageNo", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "ETD", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "ETD", ScenarioDetailsHashMap), 5);
		GenericMethods.action_Key(driver, Keys.TAB);
		AppReusableMethods.validateDateFormat(driver, oROceanNsibObl.getElement(driver, "ETD", ScenarioDetailsHashMap, 5), ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "ETD", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "ETA", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "ETA", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "ServiceLevel", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "ServiceLevel", ScenarioDetailsHashMap), 5);
		GenericMethods.pauseExecution(5000);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "NoOfContainers", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "NoOfContainers", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "NoOfHBLs", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "NoOfHBLs", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "NoOfPCS", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "NoOfPCS", ScenarioDetailsHashMap), 5);
		GenericMethods.pauseExecution(2000);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "TotalWeight", ScenarioDetailsHashMap,10), 10);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "TotalWeight", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "TotalWeight", ScenarioDetailsHashMap), 5);
		GenericMethods.pauseExecution(2000);
		
		//eRatingValidation starts here
		if(ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "eRatingValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
		{
			GenericMethods.selectOptionFromDropDown(driver, null,oROceanNsibObl.getElement(driver, "MBLContractType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "Contract_Quote_Tariff", ScenarioDetailsHashMap));
			if(ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "RatingType", ScenarioDetailsHashMap).equalsIgnoreCase("ManualRate"))
			{
				oROceanNsibObl.getElement(driver, "CarrierContractId", ScenarioDetailsHashMap, 10).clear();
				GenericMethods.pauseExecution(2000);
				GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "ContractLOV", ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(5000);
				GenericMethods.selectWindow(driver);
				GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "GridOrigin", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.pauseExecution(4000);
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
		}
		//End here

		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "TotalUOW", ScenarioDetailsHashMap,10), 10);
		GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "TotalUOW", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "TotalUOW", ScenarioDetailsHashMap));
		//Code added for UOW conversion verification.
		GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "TotalUOW", ScenarioDetailsHashMap, 5),"LB");
		String value=null;
		value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "TotalWeight", ScenarioDetailsHashMap))*2.20462262185, ExcelUtils.getCellData("DecimalPrecision", rowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
		//GenericMethods.assertTwoValues(oROceanNsibObl.getElement(driver, "TotalWeight", ScenarioDetailsHashMap, 5).getAttribute("value").replace(" ", ""),value+"", "Validating Gross Weight COnversion In UOW KG to LB", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "TotalUOW", ScenarioDetailsHashMap, 5),"TON");
		value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "TotalWeight", ScenarioDetailsHashMap))*0.001, ExcelUtils.getCellData("DecimalPrecision", rowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
		//GenericMethods.assertTwoValues(oROceanNsibObl.getElement(driver, "TotalWeight", ScenarioDetailsHashMap, 5).getAttribute("value").trim(),value+"", "Validating Gross Weight COnversion In UOW KG to LB", ScenarioDetailsHashMap);
		GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "TotalUOW", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "TotalUOW", ScenarioDetailsHashMap));
		GenericMethods.pauseExecution(2000);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "TotalVolume", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "TotalVolume", ScenarioDetailsHashMap), 5);
		GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "PaymentTerms", ScenarioDetailsHashMap, 5), ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "PaymentTerms", ScenarioDetailsHashMap));
		GenericMethods.pauseExecution(5000);
		addContainerDetails(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.pauseExecution(10000);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10),20);
		String OnSave_RefText=GenericMethods.getInnerText(driver, null, oROceanNsibObl.getElement(driver, "Label_MBLSuccMsg_OnSave", ScenarioDetailsHashMap, 10), 10);
		String [] ShipmentRefTextSplit=OnSave_RefText.split(":");
		String refId =ShipmentRefTextSplit[1];
		ExcelUtils.setCellData("SI_NSIBOBL", rowNo, "Shipment_ReferenceId", refId, ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "MBLNo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLNo", ScenarioDetailsHashMap) , 10);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
		GenericMethods.assertInnerText(driver, null, oROceanNsibObl.getElement(driver, "SearchGridOblId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLNo",ScenarioDetailsHashMap), "MBLNo", "Validating MBL Id", ScenarioDetailsHashMap);
		//		Code added to get the status of the record as complete.
		GenericMethods.clickElement(driver, By.id("MBL_MAIN_DETAILS"),null,20);
		refId=driver.findElement(By.xpath("//input[@name='sumryShipRef']")).getAttribute("value");
		System.out.println("***refId"+refId);
		ExcelUtils.setCellData("SI_NSIBOBL", rowNo, "Shipment_ReferenceId", refId, ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "TotalWeight", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "TotalWeight", ScenarioDetailsHashMap), 5);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10),20);
		GenericMethods.pauseExecution(3000);
		
		int rowCount=ExcelUtils.getCellDataRowCount("SI_NSIBOBL_ContainerDetails", ScenarioDetailsHashMap);
		for (int i =1; i <= rowCount; i++) {
			String containerNo=ExcelUtils.getCellData("SE_OBLContainersDetails", i, "ContainerNumber", ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_ArrivalContainers", i, "ContainerReq", "MBL", ScenarioDetailsHashMap);
			//			ExcelUtils.setCellData("SE_OBLContainers", i, "HBL_ID", hblID, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_ArrivalContainers", i, "MBLId", randomMBLNo, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_ArrivalContainers", i, "Container_Number", containerNo, ScenarioDetailsHashMap);
		}	
	}
	public static void updateHblAndRefIds(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) {
		int rowCount=ExcelUtils.getCellDataRowCount("SI_NSIBOBL_HBLDetails", ScenarioDetailsHashMap);	
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_ArrivalNotice", ScenarioDetailsHashMap, 5), 5);
		GenericMethods.pauseExecution(5000);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_DeliveryInstructions", ScenarioDetailsHashMap, 5), 5);
		GenericMethods.pauseExecution(5000);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_HblDetails", ScenarioDetailsHashMap,10), 10);
		for (int i = 1; i <= rowCount; i++) {
			String hblId=ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "HBLNo", ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SE_HBLMainDetails", i, "HBLId",hblId , ScenarioDetailsHashMap);
			String hblRefId=driver.findElement(By.xpath("//td[text()='"+hblId+"']/following-sibling::td[1]")).getText();
			ExcelUtils.setCellData("SE_HBLMainDetails", i, "ShipmentReferenceNo", hblRefId, ScenarioDetailsHashMap);
		}
	}
	public static void updateHblAndRefIdsNew(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) {
		String actualdatasetnum = ScenarioDetailsHashMap.get("DataSetNo");
		ScenarioDetailsHashMap.put("DataSetNo","1");
		oceanImportNsibObl_SearchNew(driver, ScenarioDetailsHashMap, rowNo);
		ScenarioDetailsHashMap.put("DataSetNo",actualdatasetnum);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_ArrivalNotice", ScenarioDetailsHashMap, 5), 5);
		GenericMethods.pauseExecution(5000);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_DeliveryInstructions", ScenarioDetailsHashMap, 5), 5);
		GenericMethods.pauseExecution(5000);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_HblDetails", ScenarioDetailsHashMap,10), 10);
			String hblId=ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBLNo", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(driver.findElement(By.xpath("//*[@id='dtQuoteOrContractStatus"+rowNo+"']")).getText(), "Provisional", "Quote / Contract Status", ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SE_HBLMainDetails", rowNo, "HBLId",hblId , ScenarioDetailsHashMap);
			String hblRefId=driver.findElement(By.xpath("//td[text()='"+hblId+"']/following-sibling::td[1]")).getText().trim();
			ExcelUtils.setCellData("SE_HBLMainDetails", rowNo, "ShipmentReferenceNo", hblRefId, ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10),20);
	}
	public static void addContainerDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) {
		int rowCount=ExcelUtils.getCellDataRowCount("SI_NSIBOBL_ContainerDetails", ScenarioDetailsHashMap);
		System.out.println("SI_NSIBOBL_ContainerDetails rowCount::"+rowCount);
		for (int i = 1; i <= rowCount; i++) {
			GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "ContainerType", ScenarioDetailsHashMap, 5), ExcelUtils.getCellData("SI_NSIBOBL_ContainerDetails", i, "Container_Type", ScenarioDetailsHashMap));
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "ContainerNo", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_ContainerDetails", i, "ContainerNo", ScenarioDetailsHashMap), 5);
			GenericMethods.action_Key(driver, Keys.TAB);
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "SealNo", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_ContainerDetails", i, "Container_SealNo", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "StuffedVolume", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_ContainerDetails", i, "Container_StuffedVolume", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "StuffedWeight", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_ContainerDetails", i, "Container_StuffedWeight", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "StuffedPieces", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_ContainerDetails", i, "Container_StuffedPieces", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "StuffedHbls", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_ContainerDetails", i, "Container_StuffedHBL", ScenarioDetailsHashMap), 5);
			/*GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "", ScenarioDetailsHashMap), 5);
	GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "NoOfHBLs", ScenarioDetailsHashMap), 5);
	GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "NoOfPCS", ScenarioDetailsHashMap), 5);
			 */GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Btn_Add_ContainerDetails", ScenarioDetailsHashMap, 5), 5);
		}
	}
	public static void addHblDetails(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) {
		//oceanImportNsibObl_Search(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_HblDetails", ScenarioDetailsHashMap,10), 10);
		GenericMethods.pauseExecution(5000);
		GenericMethods.handleAlert(driver, "accept");
		int rowCount=ExcelUtils.getCellDataRowCount("SI_NSIBOBL_HBLDetails", ScenarioDetailsHashMap);
		System.out.println("SI_NSIBOBL_HBLDetails rowCount::"+rowCount);
		for (int i = 1; i <= rowCount; i++) {
			String randomHBLNo=ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "HBLNo", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(5)+"";
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBLNo", ScenarioDetailsHashMap, 5),randomHBLNo, 5);
			ExcelUtils.setCellData("SI_NSIBOBL_HBLDetails", i, "HBLNo", randomHBLNo, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_NSIBOBL_DeliveryInstructions", i, "HBLNo", randomHBLNo, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_NSIBOBL_RoutePlan", rowNo, "HBLNo", randomHBLNo, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_Arrival", rowNo, "HBL_ID", randomHBLNo, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_ArrivalContainers", i, "HBL_ID", randomHBLNo, ScenarioDetailsHashMap);
			
			if(ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "OBLUpdateRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				System.out.println("entered in loop");
				ExcelUtils.setCellData_Without_DataSet("SE_OBL", rowNo, "HBL_Id", randomHBLNo, ScenarioDetailsHashMap);
				ExcelUtils.setCellData_Without_DataSet("Air_HAWBMainDetails", rowNo, "HAWB_Id", randomHBLNo, ScenarioDetailsHashMap);
				
				
			}
			//eRatingValidation starts here
			
			if(ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "eRatingValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
			{
				GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "PlaceOfReceipt", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "PlaceofReceipt", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "PlaceOfDelivery", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "PlaceofDelivery", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "PortOfLoading", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "PortofLoading", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "PortOfDischarge", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "PortofDischarge", ScenarioDetailsHashMap), 2);
				
				if(ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "RatingType", ScenarioDetailsHashMap).equalsIgnoreCase("ManualRate"))
				{
					GenericMethods.selectOptionFromDropDown(driver, null,oROceanNsibObl.getElement(driver, "MBLContractType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "Contract_Quote_Tariff", ScenarioDetailsHashMap));
					GenericMethods.pauseExecution(3000);
					/*try {
						driver.findElement(By.xpath("//img[@id='contractLov']")).click();
						System.out.println("in try");
					} catch (Exception e) {
						e.printStackTrace();
						driver.findElement(By.xpath("//a[@id='contractLov']")).click();
						System.out.println("in catch");
					}
					*/
					oROceanNsibObl.getElement(driver, "CustomerContractId", ScenarioDetailsHashMap, 2).clear();
					GenericMethods.pauseExecution(2000);
					GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Contract_LOV", ScenarioDetailsHashMap, 10), 10);
					GenericMethods.pauseExecution(5000);
					GenericMethods.selectWindow(driver);
					GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "GridOrigin", ScenarioDetailsHashMap, 10), 2);
					GenericMethods.pauseExecution(4000);
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
					
					
					//Sandeep - Below method is to verify  functional validation RAT 20(Verifying defaulting value of Agent Contract/Quote Id field)
					if(ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "ContractQuote_Required", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
					{
						GenericMethods.replaceTextofTextField(driver, By.id("agentRatingId"), null, Keys.TAB, 10);
						GenericMethods.assertTwoValues(driver.findElement(By.id("agentRatingId")).getAttribute("previousvalue"), ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "AgentContractQuote_ID", ScenarioDetailsHashMap), "RAT 20 Verifying defaulting value of Agent Contract/Quote Id field", ScenarioDetailsHashMap);
					}//code end
					
					GenericMethods.pauseExecution(5000);
					//Code added to check Quote / Contract Status(RAT19)- By Prasanna Modugu
//					AppReusableMethods.quoteOrContractStatus(driver, ScenarioDetailsHashMap, rowNo);
				}
			}
			//End here
			
			GenericMethods.pauseExecution(5000);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBLDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "HBLDate", ScenarioDetailsHashMap), 5);
			GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "ContainerNo", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "ContainerNo", ScenarioDetailsHashMap));
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_Destination", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "DestBranch", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_Dept", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "DestDept", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_Shipper", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "HBL_Shipper", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_Consignee", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "HBL_Consignee", ScenarioDetailsHashMap), 5);
			if (ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "Blocked", ScenarioDetailsHashMap).equalsIgnoreCase("Yes")) {
				GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_Customer", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "Blocked_Customer", ScenarioDetailsHashMap), 5);
				ExcelUtils.setCellData("SI_NSIBOBL_HBLDetails", i, "HBLIdToUnblock", randomHBLNo, ScenarioDetailsHashMap);
			}
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_NotifyToOrder", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "HBL_NotifyToOrder", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_MarksAndNumbers", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "HBL_MarksAndNumbers", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_ServiceLevel", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "HBL_ServiceLevel", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_InvoiceBranch", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "HBL_InvoiceBranch", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_InvoiceDept", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "HBL_InvoiceDept", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_Package", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "HBL_Package", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_PackageType", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "HBL_PackageType", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_GrossWeight", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "HBL_GrossWeight", ScenarioDetailsHashMap), 5);
			GenericMethods.action_Key(driver, Keys.TAB);
			String value=null;
			value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBL_GrossWeight", ScenarioDetailsHashMap)), ExcelUtils.getCellData("DecimalPrecision", rowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
			AppReusableMethods.validateNumberFormat(driver, oROceanNsibObl.getElement(driver, "HBL_GrossWeight", ScenarioDetailsHashMap, 5), value, ScenarioDetailsHashMap);
			GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "HBL_GrossUOW", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "HBL_GrossUOW", ScenarioDetailsHashMap));
			//		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_GrossUOW", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "HBL_GrossUOW", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_Volume", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "HBL_Volume", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_GoodsDescription", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "HBL_GoodsDescription", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_AMSRefNo", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "HBL_AMSRefNo", ScenarioDetailsHashMap)+randomHBLNo, 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_AMSDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "HBL_AMSDate", ScenarioDetailsHashMap), 5);
			GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Button_Add", ScenarioDetailsHashMap, 5), 5);
			GenericMethods.handleAlert(driver, "accept");
			if (ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "NotesAndAlertsRequird", ScenarioDetailsHashMap).equalsIgnoreCase("Yes")) {
				AppReusableMethods.checkNotesAndAlerts(driver, ScenarioDetailsHashMap, rowNo);
			}
			
			

		}
		int routePlanCount=ExcelUtils.getCellDataRowCount("SI_NSIBOBL_RoutePlan", ScenarioDetailsHashMap);
		System.out.println(routePlanCount+"***routePlanCount");
		if (routePlanCount>0) {
			String routePlanHBL=ExcelUtils.getCellData("SI_NSIBOBL_RoutePlan", rowNo, "HBLNo", ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SE_OBL", rowNo, "HBL_Id", routePlanHBL, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("Air_MAWB", rowNo, "HAWBID", routePlanHBL, ScenarioDetailsHashMap);
			System.out.println("routePlanHBL***"+routePlanHBL);
			GenericMethods.clickElement(driver, By.xpath("//td[text()='"+routePlanHBL+"']"), null, 5);
			for (int i = 1; i <= routePlanCount; i++) {
				GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_RoutePlan", ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(5000);
				//				GenericMethods.selectOptionFromDropDown(driver, By.id(("l_shipmentMode3").toString()), null,ExcelUtils.getCellData("SI_NSIBOBL_RoutePlan", rowNo, "Mode", ScenarioDetailsHashMap));	
				GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Add_RoutePlanRow", ScenarioDetailsHashMap, 10), 10);
				//				GenericMethods.action_Key(driver, Keys.TAB);
				GenericMethods.pauseExecution(5000);
				GenericMethods.replaceTextofTextField(driver, By.id("destination"+(i+2)), null,ExcelUtils.getCellData("SI_NSIBOBL_RoutePlan", rowNo, "RoutePlan", ScenarioDetailsHashMap), 5);
				GenericMethods.action_Key(driver, Keys.TAB);
				GenericMethods.pauseExecution(5000);
				WebElement ele=driver.findElement(By.id("l_shipmentMode1001"));
				Select select=new Select(ele);
				String mode=ExcelUtils.getCellData("SI_NSIBOBL_RoutePlan", rowNo, "Mode", ScenarioDetailsHashMap);
				select.selectByVisibleText(mode);
				System.out.println(i+"****i");
			}
		}	
	}
	public static void addHblDetailsNew(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) {
		String actualdatasetnum = ScenarioDetailsHashMap.get("DataSetNo");
		ScenarioDetailsHashMap.put("DataSetNo","1");
		oceanImportNsibObl_SearchNew(driver, ScenarioDetailsHashMap, rowNo);
		ScenarioDetailsHashMap.put("DataSetNo",actualdatasetnum);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_HblDetails", ScenarioDetailsHashMap,10), 10);
		GenericMethods.pauseExecution(5000);
		GenericMethods.handleAlert(driver, "accept");
		if (driver.findElement(By.id("button.gridEditBtn")).isDisplayed()) {
		try{
			driver.findElement(By.id("button.gridEditBtn")).click();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		}
		int rowCount=ExcelUtils.getCellDataRowCount("SI_NSIBOBL_HBLDetails", ScenarioDetailsHashMap);
		System.out.println("SI_NSIBOBL_HBLDetails rowCount::"+rowCount);
			String randomHBLNo=ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBLNo", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(5)+"";
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBLNo", ScenarioDetailsHashMap, 5),randomHBLNo, 5);
			ExcelUtils.setCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBLNo", randomHBLNo, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_NSIBOBL_DeliveryInstructions", rowNo, "HBLNo", randomHBLNo, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_NSIBOBL_RoutePlan", rowNo, "HBLNo", randomHBLNo, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_Arrival", rowNo, "HBL_ID", randomHBLNo, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_ArrivalContainers", rowNo, "HBL_ID", randomHBLNo, ScenarioDetailsHashMap);
			
			//eRatingValidation starts here
			if(ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "eRatingValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
			{
				GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "PlaceOfReceipt", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "PlaceofReceipt", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "PlaceOfDelivery", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "PlaceofDelivery", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "PortOfLoading", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "PortofLoading", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "PortOfDischarge", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "PortofDischarge", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_Customer", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "Customer", ScenarioDetailsHashMap), 5);
				GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBLmoveTypeOrigin", ScenarioDetailsHashMap, 2), ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "MoveTypeOrigin", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBLmoveTypeDest", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "MoveTypeDest", ScenarioDetailsHashMap), 5);
				
				GenericMethods.selectOptionFromDropDown(driver, null,oROceanNsibObl.getElement(driver, "MBLContractType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "Contract_Quote_Tariff", ScenarioDetailsHashMap));
				if(ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "RatingType", ScenarioDetailsHashMap).equalsIgnoreCase("ManualRate"))
				{
					
					GenericMethods.pauseExecution(3000);
					/*try {
						driver.findElement(By.xpath("//img[@id='contractLov']")).click();
						System.out.println("in try");
					} catch (Exception e) {
						e.printStackTrace();
						driver.findElement(By.xpath("//a[@id='contractLov']")).click();
						System.out.println("in catch");
					}
					*/
					oROceanNsibObl.getElement(driver, "CustomerContractId", ScenarioDetailsHashMap, 2).clear();
					GenericMethods.pauseExecution(2000);
					GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Contract_LOV", ScenarioDetailsHashMap, 10), 10);
					GenericMethods.pauseExecution(5000);
					GenericMethods.selectWindow(driver);
					GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "GridOrigin", ScenarioDetailsHashMap, 10), 2);
					GenericMethods.pauseExecution(4000);
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
					
					
					//Sandeep - Below method is to verify  functional validation RAT 20(Verifying defaulting value of Agent Contract/Quote Id field)
					if(ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "ContractQuote_Required", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
					{
						GenericMethods.replaceTextofTextField(driver, By.id("agentRatingId"), null, Keys.TAB, 10);
						GenericMethods.assertTwoValues(driver.findElement(By.id("agentRatingId")).getAttribute("previousvalue"), ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "AgentContractQuote_ID", ScenarioDetailsHashMap), "RAT 20 Verifying defaulting value of Agent Contract/Quote Id field", ScenarioDetailsHashMap);
					}//code end
				}
			}
			//End here
			//Code added to check Quote / Contract Status(RAT19)- By Prasanna Modugu
//			AppReusableMethods.quoteOrContractStatus(driver, ScenarioDetailsHashMap, rowNo);
			GenericMethods.pauseExecution(5000);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBLDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBLDate", ScenarioDetailsHashMap), 5);
			GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "ContainerNo", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "ContainerNo", ScenarioDetailsHashMap));
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_Destination", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails",rowNo, "DestBranch", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_Dept", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "DestDept", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_Shipper", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails",rowNo, "HBL_Shipper", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_Consignee", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBL_Consignee", ScenarioDetailsHashMap), 5);
			if (ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "Blocked", ScenarioDetailsHashMap).equalsIgnoreCase("Yes")) {
				GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_Customer", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "Blocked_Customer", ScenarioDetailsHashMap), 5);
				ExcelUtils.setCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBLIdToUnblock", randomHBLNo, ScenarioDetailsHashMap);
			}
			
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_NotifyToOrder", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBL_NotifyToOrder", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_MarksAndNumbers", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBL_MarksAndNumbers", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_ServiceLevel", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBL_ServiceLevel", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_InvoiceBranch", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBL_InvoiceBranch", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_InvoiceDept", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBL_InvoiceDept", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_Package", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBL_Package", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_PackageType", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBL_PackageType", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_GrossWeight", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails",rowNo, "HBL_GrossWeight", ScenarioDetailsHashMap), 5);
			GenericMethods.action_Key(driver, Keys.TAB);
			String value=null;
			value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBL_GrossWeight", ScenarioDetailsHashMap)), ExcelUtils.getCellData("DecimalPrecision", rowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
			AppReusableMethods.validateNumberFormat(driver, oROceanNsibObl.getElement(driver, "HBL_GrossWeight", ScenarioDetailsHashMap, 5), value, ScenarioDetailsHashMap);
			GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "HBL_GrossUOW", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBL_GrossUOW", ScenarioDetailsHashMap));
			//		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_GrossUOW", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "HBL_GrossUOW", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_Volume", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails",rowNo, "HBL_Volume", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_GoodsDescription", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBL_GoodsDescription", ScenarioDetailsHashMap), 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_AMSRefNo", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBL_AMSRefNo", ScenarioDetailsHashMap)+randomHBLNo, 5);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_AMSDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBL_AMSDate", ScenarioDetailsHashMap), 5);
			GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Button_Add", ScenarioDetailsHashMap, 5), 5);
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10),20);
			int routePlanCount=ExcelUtils.getCellDataRowCount("SI_NSIBOBL_RoutePlan", ScenarioDetailsHashMap);
			System.out.println(routePlanCount+"***routePlanCount");
			if (routePlanCount>0) {
			String routePlanHBL=ExcelUtils.getCellData("SI_NSIBOBL_RoutePlan", rowNo, "HBLNo", ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SE_OBL", rowNo, "HBL_Id", routePlanHBL, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("Air_MAWB", rowNo, "HAWBID", routePlanHBL, ScenarioDetailsHashMap);
			System.out.println("routePlanHBL***"+routePlanHBL);
			GenericMethods.clickElement(driver, By.xpath("//td[text()='"+routePlanHBL+"']"), null, 5);
			for (int i = 1; i <= routePlanCount; i++) {
				GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_RoutePlan", ScenarioDetailsHashMap, 10), 10);
				GenericMethods.pauseExecution(5000);
				//				GenericMethods.selectOptionFromDropDown(driver, By.id(("l_shipmentMode3").toString()), null,ExcelUtils.getCellData("SI_NSIBOBL_RoutePlan", rowNo, "Mode", ScenarioDetailsHashMap));	
				GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Add_RoutePlanRow", ScenarioDetailsHashMap, 10), 10);
				//				GenericMethods.action_Key(driver, Keys.TAB);
				GenericMethods.pauseExecution(5000);
				GenericMethods.replaceTextofTextField(driver, By.id("destination"+(i+2)), null,ExcelUtils.getCellData("SI_NSIBOBL_RoutePlan", rowNo, "RoutePlan", ScenarioDetailsHashMap), 5);
				GenericMethods.action_Key(driver, Keys.TAB);
				GenericMethods.pauseExecution(5000);
				WebElement ele=driver.findElement(By.id("l_shipmentMode1001"));
				Select select=new Select(ele);
				String mode=ExcelUtils.getCellData("SI_NSIBOBL_RoutePlan", rowNo, "Mode", ScenarioDetailsHashMap);
				select.selectByVisibleText(mode);
				System.out.println(i+"****i");
			}
		}	
	}
	public static void addDeliveryInstructions(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) {
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_ArrivalNotice", ScenarioDetailsHashMap, 5), 5);
		GenericMethods.pauseExecution(5000);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "VesselName", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "Vessel", ScenarioDetailsHashMap), 5);
		GenericMethods.assertTwoValues(oROceanNsibObl.getElement(driver, "VesselName", ScenarioDetailsHashMap, 5).getAttribute("value"), ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "Vessel", ScenarioDetailsHashMap), "Verifying Vessel Name.", ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_DeliveryInstructions", ScenarioDetailsHashMap, 5), 5);
		int rowCount=ExcelUtils.getCellDataRowCount("SI_NSIBOBL_DeliveryInstructions", ScenarioDetailsHashMap);
		System.out.println("SI_NSIBOBL_DeliveryInstructions rowCount::"+rowCount);
		for (int i = 1; i <= rowCount; i++) 
		{
			String containerNo=ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "ContainerNo", ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(5000);
			Select selectDeliveryType=new Select(oROceanNsibObl.getElement(driver, "DeliveryType", ScenarioDetailsHashMap, 10));
			String DeliveryType=ExcelUtils.getCellData("SI_NSIBOBL_DeliveryInstructions", i, "DeliveryType", ScenarioDetailsHashMap);
			selectDeliveryType.selectByVisibleText(DeliveryType);
			//	GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "DeliveryType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_NSIBOBL_DeliveryInstructions", rowNo, "DeliveryType", ScenarioDetailsHashMap));
			GenericMethods.pauseExecution(5000);
			Select selectTypeOfMovement=new Select(oROceanNsibObl.getElement(driver, "TypeOfMovement", ScenarioDetailsHashMap, 10));
			String typeOfMovement=ExcelUtils.getCellData("SI_NSIBOBL_DeliveryInstructions", i, "TypeOfMovement", ScenarioDetailsHashMap);
			selectTypeOfMovement.selectByVisibleText(typeOfMovement);
			//	GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "TypeOfMovement", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_NSIBOBL_DeliveryInstructions", rowNo, "TypeOfMovement", ScenarioDetailsHashMap));
			GenericMethods.pauseExecution(5000);
			String deliveryStatus=ExcelUtils.getCellData("SI_NSIBOBL_DeliveryInstructions", i, "DeliveryStatus", ScenarioDetailsHashMap);
			Select selectDIType=new Select(oROceanNsibObl.getElement(driver, "DIType", ScenarioDetailsHashMap, 10));
			selectDIType.selectByVisibleText(deliveryStatus);
			GenericMethods.pauseExecution(5000);
			if (!deliveryStatus.equalsIgnoreCase("Complete")) 
			{
				//	GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "DIType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_NSIBOBL_DeliveryInstructions", rowNo, "DeliveryStatus", ScenarioDetailsHashMap));
				System.out.println(oROceanNsibObl.getElements(driver, "DI_Containers", ScenarioDetailsHashMap, 10).size()+"containers****");
				for (int j = 0; j < oROceanNsibObl.getElements(driver, "DI_Containers", ScenarioDetailsHashMap, 10).size(); j++) 
				{
					//GenericMethods.clickElement(driver, By.xpath("//input[@value='"+containerNo+"']/ancestor::td[1]/input[3]"),null,20);		
					if (!driver.findElement(By.id("containerCheck_"+j)).isSelected()) 
					{
						GenericMethods.clickElement(driver, By.id("containerCheck_"+j),null,10);
					}
				}
				GenericMethods.pauseExecution(2000);
				if (!driver.findElement(By.id("houseCheck_"+(i-1))).isSelected()) 
				{
					GenericMethods.clickElement(driver, By.id("houseCheck_"+(i-1)),null,10);
				}

			}
			//	GenericMethods.clickElement(driver, By.xpath("//*[@name='houseId']/ancestor::td[1]/input[@value='"+hblId+"']/following-sibling::input[2]"),null,20);
			GenericMethods.clickElement(driver, null,oROceanNsibObl.getElement(driver, "Button_Add", ScenarioDetailsHashMap, 10),20);
			GenericMethods.pauseExecution(10000);
		}
	}
	public static void addDeliveryInstructionsNew(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) {
		oceanImportNsibObl_SearchNew(driver, ScenarioDetailsHashMap, rowNo);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_ArrivalNotice", ScenarioDetailsHashMap, 5), 5);
		GenericMethods.pauseExecution(5000);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "VesselName", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "Vessel", ScenarioDetailsHashMap), 5);
		GenericMethods.assertTwoValues(oROceanNsibObl.getElement(driver, "VesselName", ScenarioDetailsHashMap, 5).getAttribute("value"), ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "Vessel", ScenarioDetailsHashMap), "Verifying Vessel Name.", ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_DeliveryInstructions", ScenarioDetailsHashMap, 5), 5);
		int rowCount=ExcelUtils.getCellDataRowCount("SI_NSIBOBL_DeliveryInstructions", ScenarioDetailsHashMap);
		System.out.println("SI_NSIBOBL_DeliveryInstructions rowCount::"+rowCount);
		for (int i = 1; i <= rowCount; i++) 
		{
			String containerNo=ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "ContainerNo", ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(5000);
			Select selectDeliveryType=new Select(oROceanNsibObl.getElement(driver, "DeliveryType", ScenarioDetailsHashMap, 10));
			String DeliveryType=ExcelUtils.getCellData("SI_NSIBOBL_DeliveryInstructions", i, "DeliveryType", ScenarioDetailsHashMap);
			selectDeliveryType.selectByVisibleText(DeliveryType);
			//	GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "DeliveryType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_NSIBOBL_DeliveryInstructions", rowNo, "DeliveryType", ScenarioDetailsHashMap));
			GenericMethods.pauseExecution(5000);
			Select selectTypeOfMovement=new Select(oROceanNsibObl.getElement(driver, "TypeOfMovement", ScenarioDetailsHashMap, 10));
			String typeOfMovement=ExcelUtils.getCellData("SI_NSIBOBL_DeliveryInstructions", i, "TypeOfMovement", ScenarioDetailsHashMap);
			selectTypeOfMovement.selectByVisibleText(typeOfMovement);
			//	GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "TypeOfMovement", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_NSIBOBL_DeliveryInstructions", rowNo, "TypeOfMovement", ScenarioDetailsHashMap));
			GenericMethods.pauseExecution(5000);
			String deliveryStatus=ExcelUtils.getCellData("SI_NSIBOBL_DeliveryInstructions", i, "DeliveryStatus", ScenarioDetailsHashMap);
			Select selectDIType=new Select(oROceanNsibObl.getElement(driver, "DIType", ScenarioDetailsHashMap, 10));
			selectDIType.selectByVisibleText(deliveryStatus);
			GenericMethods.pauseExecution(5000);
			if (!deliveryStatus.equalsIgnoreCase("Complete")) 
			{
				//	GenericMethods.selectOptionFromDropDown(driver, null, oROceanNsibObl.getElement(driver, "DIType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_NSIBOBL_DeliveryInstructions", rowNo, "DeliveryStatus", ScenarioDetailsHashMap));
				System.out.println(oROceanNsibObl.getElements(driver, "DI_Containers", ScenarioDetailsHashMap, 10).size()+"containers****");
				for (int j = 0; j < oROceanNsibObl.getElements(driver, "DI_Containers", ScenarioDetailsHashMap, 10).size(); j++) 
				{
					//GenericMethods.clickElement(driver, By.xpath("//input[@value='"+containerNo+"']/ancestor::td[1]/input[3]"),null,20);		
					if (!driver.findElement(By.id("containerCheck_"+j)).isSelected()) 
					{
						GenericMethods.clickElement(driver, By.id("containerCheck_"+j),null,10);
					}
				}
				GenericMethods.pauseExecution(2000);
				if (!driver.findElement(By.id("houseCheck_"+(i-1))).isSelected()) 
				{
					GenericMethods.clickElement(driver, By.id("houseCheck_"+(i-1)),null,10);
				}

			}
			//	GenericMethods.clickElement(driver, By.xpath("//*[@name='houseId']/ancestor::td[1]/input[@value='"+hblId+"']/following-sibling::input[2]"),null,20);
			GenericMethods.clickElement(driver, null,oROceanNsibObl.getElement(driver, "Button_Add", ScenarioDetailsHashMap, 10),20);
			GenericMethods.pauseExecution(10000);
		}
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10),20);
	}
	public static void oceanImportNsibObl_Close(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		AppReusableMethods.selectMenu(driver, ETransMenu.oceanNsibObl,"OCEAN NSIB MBL",ScenarioDetailsHashMap);
		//		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "Search_MblId", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_OBL", rowNo, "OBL_Id", ScenarioDetailsHashMap) , 10);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "MBLNo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLNo", ScenarioDetailsHashMap) , 10);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
		GenericMethods.pauseExecution(5000);
		GenericMethods.assertInnerText(driver, null, oROceanNsibObl.getElement(driver, "SearchGridOblId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLNo",ScenarioDetailsHashMap), "MBL Id", "Validating MBL Id", ScenarioDetailsHashMap);
		//		GenericMethods.clickElement(driver, null,oROceanNsibObl.getElement(driver, "SearchGridOblId", ScenarioDetailsHashMap, 10),20); 
		GenericMethods.pauseExecution(5000);
		/*	Select navigationList=new Select(oROceanNsibObl.getElement(driver, "DDL_Navigator", ScenarioDetailsHashMap, 10));
		navigationList.selectByVisibleText("Master Close");*/
		GenericMethods.pauseExecution(1000);
		System.out.println("******before");
		GenericMethods.clickElement(driver, null,oROceanNsibObl.getElement(driver, "Img_NavigationListImage", ScenarioDetailsHashMap, 10),20);
		GenericMethods.pauseExecution(2000);
		String MAWBClosedSummary=GenericMethods.getInnerText(driver, null, oROceanNsibObl.getElement(driver, "MasterClose_VerificationMsG", ScenarioDetailsHashMap, 20), 2);
		String[] NSIBClosed = MAWBClosedSummary.split(": ");
		System.out.println(NSIBClosed[0]);
		System.out.println(NSIBClosed[1]);
		GenericMethods.assertTwoValues(NSIBClosed[1].trim(), ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MasterClose_Validation_MSG", ScenarioDetailsHashMap), "Validating NSIB Closed success message", ScenarioDetailsHashMap);
	}
	public static void checkNotesAndAlerts(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_Shipper", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBL_Shipper", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "HBL_Consignee", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", rowNo, "HBL_Consignee", ScenarioDetailsHashMap), 5);
		GenericMethods.clickElement(driver, By.id("button.gridEditBtn"), null, 5);
		GenericMethods.selectWindow(driver);
		GenericMethods.assertValue(driver, By.xpath("//td[contains(text(),'Notes And Alert Information')]"), null, "Notes And Alert Information", "Notes And Alert", "Notes And Alert", ScenarioDetailsHashMap);
		GenericMethods.assertValue(driver, By.xpath("//span[text()='ALRT103']"), null, "ALRT103", "Notes And Alert", "Notes And Alert", ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, By.name("Proceed"), null, 5);
		//			GenericMethods.clickElement(driver, By.name("discard"), null, 5);
		GenericMethods.switchToDefaultContent(driver);
	}	
	//Sangeeta- RAT03/04 - Below method is to  validating  selected Contract/Quote is Hazardous or not
	public static void  HazardousValidationForRating(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.oceanNsibObl,"OCEAN NSIB MBL",ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "ShipmentReferenceNo", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_NSIBOBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(3000); 
		GenericMethods.clickElement(driver, null,oROceanNsibObl.getElement(driver, "Tab_HblDetails", ScenarioDetailsHashMap,10), 2);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
		GenericMethods.pauseExecution(7000);
		GenericMethods.clickElement(driver, null,oROceanNsibObl.getElement(driver, "HBLHazardousCheckBox", ScenarioDetailsHashMap,10), 2);	
		GenericMethods.pauseExecution(2000);
		try{
			GenericMethods.selectWindow(driver);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "Hazardous_NumofPack", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "Haz_ItemsNo", ScenarioDetailsHashMap), 2);
			GenericMethods.selectOptionFromDropDown(driver,null,oROceanNsibObl.getElement(driver, "Hazardous_PackType", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "Haz_ItemType", ScenarioDetailsHashMap));
			GenericMethods.action_Key(driver, Keys.TAB);
			oROceanNsibObl.getElement(driver, "Hazardous_netQuantity", ScenarioDetailsHashMap,10).sendKeys(ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "Haz_NetWeight", ScenarioDetailsHashMap));
			GenericMethods.action_Key(driver, Keys.TAB);
			GenericMethods.selectOptionFromDropDown(driver,null,oROceanNsibObl.getElement(driver, "Hazardous_netQuantityUom", ScenarioDetailsHashMap,10), ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "Haz_NetWeightUnit", ScenarioDetailsHashMap));
			GenericMethods.action_Key(driver, Keys.TAB);
			oROceanNsibObl.getElement(driver, "Hazardous_grossWeight", ScenarioDetailsHashMap,10).sendKeys(ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "HazGrossWeight", ScenarioDetailsHashMap));
			GenericMethods.action_Key(driver, Keys.TAB);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "Hazardous_UNDGNumber", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "Haz_UNDGNumber", ScenarioDetailsHashMap), 2);
			//GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "Hazardous_contactName", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "HazContactName", ScenarioDetailsHashMap), 2);
			//GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "Hazardous_contactNumber", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "Haz_ContactNumber", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "Hazardous_properShippingName", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "Haz_ProperShippingName", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "Hazardous_technicalName", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "Haz_TechnicalName", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "Haz_HandlingInstructions", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "Haz_HandlingInstructions", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "Haz_AdditionalNotes", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "Haz_AdditionalNotes", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "Haz_DangerousGoodsCard1", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "Haz_DangerousGoodsCard1", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "Haz_DangerousGoodsCard2", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "Haz_DangerousGoodsCard2", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "Haz_DangerousGoodsCard3", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "Haz_DangerousGoodsCard3", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "Haz_Labeling1", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "Haz_Labeling1", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "Haz_Labeling2", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "Haz_Labeling2", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "Haz_Labeling3", ScenarioDetailsHashMap,10),ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "Haz_Labeling3", ScenarioDetailsHashMap), 2);
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);	
			GenericMethods.pauseExecution(4000);
			GenericMethods.switchToParent(driver);
			AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
		}catch(Exception e){
			GenericMethods.assertTwoValues("PopUp Screen Not Opened", "PopUp Screen Opened", "Verifying Haz PopUp Screen", ScenarioDetailsHashMap);

		}
		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "HBL_GridBtn", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(3000);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);	

		//"EKFC10124 : Selected Contract/Quote is Not Hazardous.";
		System.out.println(GenericMethods.getInnerText(driver, null, oROceanNsibObl.getElement(driver, "HazMSG", ScenarioDetailsHashMap, 2), 10));
		System.out.println(driver.findElement(By.xpath("html/body/form/table[1]/tbody/tr[2]/td/table/tbody/tr[2]/td/table[2]/tbody/tr/td/font/b")).getText());
		String HazErrMSGSummary = GenericMethods.getInnerText(driver, null, oROceanNsibObl.getElement(driver, "HazMSG", ScenarioDetailsHashMap, 2), 10);
		String HazErrMSGApp ="";
		if(HazErrMSGSummary.contains(":"))
		{
			String[] HazErrorMSGApp =HazErrMSGSummary.split(":");
			HazErrMSGApp = HazErrorMSGApp[1].trim();
			System.out.println("HazErrMSGApp :"+HazErrMSGApp);
		}
		else
		{
			String[] HazErrorMSGApp =HazErrMSGSummary.split(" -->");
			HazErrMSGApp = HazErrorMSGApp[0];
			System.out.println(HazErrorMSGApp[0]);
			GenericMethods.assertTwoValues(HazErrMSGApp, ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "HazErrorMSG", ScenarioDetailsHashMap), "Entering the Haz details and validating  selected Contract/Quote is not Hazardous", ScenarioDetailsHashMap);
		}

		GenericMethods.pauseExecution(2000);
		if(HazErrMSGApp.equalsIgnoreCase(ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "HazErrorMSG", ScenarioDetailsHashMap)))
		{
			GenericMethods.clickElement(driver, null,oROceanNsibObl.getElement(driver, "HBLHazardousCheckBox", ScenarioDetailsHashMap,10), 2);	
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "HBL_GridBtn", ScenarioDetailsHashMap,10), 2);
			GenericMethods.pauseExecution(3000);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);	
		}
		//GenericMethods.pauseExecution(3000);
		//GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);	
	}	
	public static void oceanImportNsibObl_Search(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		AppReusableMethods.selectMenu(driver, ETransMenu.oceanNsibObl,"OCEAN NSIB MBL",ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "ShipmentReferenceNo", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(5000);
	}
	public static void oceanImportNsibObl_SearchNew(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ){
		AppReusableMethods.selectMenu(driver, ETransMenu.oceanNsibObl,"OCEAN NSIB MBL",ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, oROceanNsibObl.getElement(driver, "MBLNo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SI_NSIBOBL", rowNo, "MBLNo", ScenarioDetailsHashMap) , 10);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(5000);
	}
	
	//RAT17-Prasanna Modugu
	public static void chargesAndCost(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
		{
		try{
			oceanImportNsibObl_Search(driver, ScenarioDetailsHashMap, RowNo);
			GenericMethods.pauseExecution(5000);
			GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_HblDetails", ScenarioDetailsHashMap,10), 10);
			String hblId=ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "HBLNo", ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SE_HBLMainDetails", RowNo, "HBLId",hblId , ScenarioDetailsHashMap);
			driver.findElement(By.xpath("//td[text()='"+hblId+"']")).click();
			AppReusableMethods.chargesAndCosts(driver, ScenarioDetailsHashMap, RowNo);
		}catch(Exception e){
			e.printStackTrace();
		}
		}
	//RAT24-Prasanna Modugu
	public static void hblChargesAndCost(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		try{
		if(ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "HBL_InvoiceBranch", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			
		}
		int rowCount=ExcelUtils.getCellDataRowCount("SI_NSIBOBL_HBLDetails", ScenarioDetailsHashMap);
		oceanImportNsibObl_Search(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.pauseExecution(5000);
		for (int i = 1; i <= rowCount; i++) {
			GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_HblDetails", ScenarioDetailsHashMap,10), 10);
			String hblId=ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", i, "HBLNo", ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SE_HBLMainDetails", i, "HBLId",hblId , ScenarioDetailsHashMap);
			driver.findElement(By.xpath("//td[text()='"+hblId+"']")).click();
			GenericMethods.pauseExecution(2000);
			//Code added to check Quote / Contract Status(RAT24)- By Prasanna Modugu
			String profitCenter=ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "HBL_InvoiceBranch",ScenarioDetailsHashMap)+"/"+ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "HBL_InvoiceDept",ScenarioDetailsHashMap);
			System.out.println("profitCenter****"+profitCenter);
			AppReusableMethods.chargesAndRates(driver,  oROceanNsibObl.getElement(driver, "Tab_HBLChargesCode", ScenarioDetailsHashMap, 10), profitCenter, ScenarioDetailsHashMap, RowNo);
			//Code added to check Quote / Contract Status(RAT19)- By Prasanna Modugu
			/*GenericMethods.clickElement(driver, null, orHBL.getElement(driver, "HBL_MAIN_DETAILS_Tab", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(3000);
			AppReusableMethods.quoteOrContractStatus(driver, ScenarioDetailsHashMap, RowNo);*/	
			GenericMethods.pauseExecution(3000);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
//	RAT26 
	/**
 * This Method is used to generate sales invoice by changing invoice currency with local currency.
* @param driver Instance of WebDriver
* @param searchValue
* @param ScenarioDetailsHashMap
* @author Prasanna Modugu
*/	
	public static void invoiceCurrencyAsLocalCurrency(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		try{	
		oceanImportNsibObl_Search(driver, ScenarioDetailsHashMap, RowNo);
			GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_HblDetails", ScenarioDetailsHashMap,10), 10);
			String hblId=ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "HBLNo", ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SE_HBLMainDetails", RowNo, "HBLId",hblId , ScenarioDetailsHashMap);
			driver.findElement(By.xpath("//td[text()='"+hblId+"']")).click();
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_HBLChargesCode", ScenarioDetailsHashMap, 10), 5);
			GenericMethods.pauseExecution(5000);
			GenericMethods.handleAlert(driver, "accept");
			if (driver.findElement(By.id("multiSelectCheckBoxchargeGrid")).isSelected()) {
				driver.findElement(By.id("multiSelectCheckBoxchargeGrid")).click();
			}
				GenericMethods.pauseExecution(1000);
			if (!driver.findElement(By.id("multiSelectCheckboxchargeGrid1")).isSelected()) {
				driver.findElement(By.id("multiSelectCheckboxchargeGrid1")).click();
			}
			String salesAmtCurr=driver.findElement(By.id("salesPriceCurrency1")).getText();
			GenericMethods.doubleClickOnElement(driver, By.id("salesInvCurrency1"), null, 5);
			GenericMethods.handleAlert(driver, "accept");
			driver.switchTo().activeElement().clear();
			GenericMethods.handleAlert(driver, "accept");
			driver.switchTo().activeElement().sendKeys(salesAmtCurr);
			GenericMethods.action_Key(driver, Keys.TAB);
			GenericMethods.pauseExecution(3000);
			GenericMethods.clickElement(driver, By.name("postSales"), null, 5);
			GenericMethods.pauseExecution(3000);
			GenericMethods.selectWindow(driver);
			driver.close();
			GenericMethods.pauseExecution(1000);
			GenericMethods.switchToParent(driver);
            AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(2000);
			String invoiceIdSummary=GenericMethods.getInnerText(driver, By.xpath("//font[@color='green']/b"), null, 2);
			String invoiceId=invoiceIdSummary.split(":")[1].trim();
			invoiceId=invoiceId.substring(0,invoiceId.indexOf(" "));
			invoiceId=invoiceId.split("\n")[0].trim()+" ";
			System.out.println(invoiceIdSummary.split(":")[0]+"succ msg***");
			System.out.println("invoiceId::"+invoiceId);
			ExcelUtils.setCellData("CostAndCharges", RowNo, "SalesInvoiceNo", invoiceId, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("Ocean_CreaditNote", RowNo, "SalesInvoiceNo", invoiceId, ScenarioDetailsHashMap);
			//		BGLSGSIN Invoice has been posted to Accounts with Transaction Id: SIBGLLUSNY000002564
			GenericMethods.assertValue(driver, By.id("salesInvCurrency1"), null, salesAmtCurr, "Invoice Currency", "Invoice Currency as foreign currency.", ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10),20);
			GenericMethods.pauseExecution(3000);
		}catch(Exception e){
			e.printStackTrace();
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10),20);
		}
		}
	//RAT27
	/**
 * This Method is used to generate sales invoice by changing invoice currency with foreign currency.
* @param driver Instance of WebDriver
* @param searchValue
* @param ScenarioDetailsHashMap
* @author Prasanna Modugu
*/	
	public static void invoiceCurrencyAsForeignCurrency(WebDriver driver, HashMap<String, String> ScenarioDetailsHashMap,int RowNo)	{
		try{
		if(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "FreightChargesValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
		}
			String foreignCurrency="USD";	
			oceanImportNsibObl_Search(driver, ScenarioDetailsHashMap, RowNo);
			GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_HblDetails", ScenarioDetailsHashMap,10), 10);
			String hblId=ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "HBLNo", ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SE_HBLMainDetails", RowNo, "HBLId",hblId , ScenarioDetailsHashMap);
			driver.findElement(By.xpath("//td[text()='"+hblId+"']")).click();
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_HBLChargesCode", ScenarioDetailsHashMap, 10), 5);
			GenericMethods.pauseExecution(5000);
			GenericMethods.handleAlert(driver, "accept");
			if (driver.findElement(By.id("multiSelectCheckBoxchargeGrid")).isSelected()) {
				driver.findElement(By.id("multiSelectCheckBoxchargeGrid")).click();
			}
			GenericMethods.pauseExecution(1000);
			if (!driver.findElement(By.id("multiSelectCheckboxchargeGrid1")).isSelected()) {
			driver.findElement(By.id("multiSelectCheckboxchargeGrid1")).click();
			driver.findElement(By.id("multiSelectCheckboxchargeGrid2")).click();
			}
			GenericMethods.doubleClickOnElement(driver, By.id("salesInvCurrency1"), null, 5);
			GenericMethods.handleAlert(driver, "accept");
			driver.switchTo().activeElement().clear();
			GenericMethods.handleAlert(driver, "accept");
			driver.switchTo().activeElement().sendKeys(foreignCurrency);
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.action_Key(driver, Keys.TAB);
			GenericMethods.doubleClickOnElement(driver, By.id("salesInvCurrency2"), null, 5);
			GenericMethods.handleAlert(driver, "accept");
			driver.switchTo().activeElement().clear();
			GenericMethods.handleAlert(driver, "accept");
			driver.switchTo().activeElement().sendKeys(foreignCurrency);
			GenericMethods.handleAlert(driver, "accept");
			GenericMethods.action_Key(driver, Keys.TAB);
			GenericMethods.pauseExecution(3000);
			GenericMethods.clickElement(driver, By.name("postSales"), null, 5);
			GenericMethods.pauseExecution(3000);
			GenericMethods.selectWindow(driver);
			driver.close();
			GenericMethods.pauseExecution(1000);
			GenericMethods.switchToParent(driver);
            AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(2000);
			String invoiceIdSummary=GenericMethods.getInnerText(driver, By.xpath("//font[@color='green']/b"), null, 2);
			String invoiceId=invoiceIdSummary.split(":")[1].trim();
			invoiceId=invoiceId.substring(0,invoiceId.indexOf(" "));
			invoiceId=invoiceId.split("\n")[0].trim()+" ";
			System.out.println(invoiceIdSummary.split(":")[0]+"succ msg***");
			System.out.println("invoiceId::"+invoiceId);
			ExcelUtils.setCellData("CostAndCharges", RowNo, "SalesInvoiceNo", invoiceId, ScenarioDetailsHashMap);
			ExcelUtils.setCellData("Ocean_CreaditNote", RowNo, "SalesInvoiceNo", invoiceId, ScenarioDetailsHashMap);
			//		BGLSGSIN Invoice has been posted to Accounts with Transaction Id: SIBGLLUSNY000002564
			GenericMethods.assertValue(driver, By.id("salesInvCurrency1"), null, foreignCurrency, "Invoice Currency", "Invoice Currency as foreign currency.", ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10),20);
			GenericMethods.pauseExecution(5000);
		}catch(Exception e){
			e.printStackTrace();
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10),20);
		}
	}
	
	public static void hblChargesAndCostsValidations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		try{
			hblChargesAndCost(driver, ScenarioDetailsHashMap, RowNo);
			chargesAndCost(driver, ScenarioDetailsHashMap, RowNo);
			/*invoiceCurrencyAsLocalCurrency(driver, ScenarioDetailsHashMap, RowNo);
			invoiceCurrencyAsForeignCurrency(driver, ScenarioDetailsHashMap, RowNo);*/
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	
	//Added for Erating functionality-Masthan
	public static void NSIB_MBLChargesCosts(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo ) throws AWTException{

		GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_MBLChargesCode", ScenarioDetailsHashMap, 5), 5);
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
	     String profitCenter=GenericMethods.locateElement(driver,By.name("profitCentreName"), 2).getAttribute("value");
		 System.out.println("&&&&&&&&&&"+profitCenter+"********1");
	     GenericMethods.assertTwoValues(profitCenter, ExcelUtils.getCellData("SI_NSIB_MBLCharges",rowNo, "ProfitCenter", ScenarioDetailsHashMap), "Verifying Profit Center in MBL Charges", ScenarioDetailsHashMap);
        Robot robot=new Robot();
		int rowCount=ExcelUtils.getCellDataRowCount("SI_NSIB_MBLCharges", ScenarioDetailsHashMap);
		 for (int Testdata_Row = 1; Testdata_Row<= rowCount; Testdata_Row++) {
			 GenericMethods.pauseExecution(2000);
		     String ReadyStatus=GenericMethods.locateElement(driver,By.id("spanchargesVOList.costStatus"+Testdata_Row), 2).getText();
			 GenericMethods.assertTwoValues(ReadyStatus, "READY", "Verifying READY Status of Charges in Grid for Line--> "+Testdata_Row, ScenarioDetailsHashMap);
		     String buyPrice=GenericMethods.locateElement(driver,By.id("spanchargesVOList.costPriceAmt"+Testdata_Row), 2).getText();
			 System.out.println("&&&&&&&&&&"+buyPrice+"********2");

		     GenericMethods.assertTwoValues(buyPrice, ExcelUtils.getCellData("SI_NSIB_MBLCharges",Testdata_Row, "BuyPriceAmount", ScenarioDetailsHashMap), "Verifying Buy Rate at Grid Line--> "+Testdata_Row, ScenarioDetailsHashMap);
		     String buyCurrency=GenericMethods.locateElement(driver,By.id("spanchargesVOList.costPriceCurrency"+Testdata_Row), 2).getText();
			 System.out.println("&&&&&&&&&&"+buyCurrency+"********3");

		     GenericMethods.assertTwoValues(buyCurrency, ExcelUtils.getCellData("SI_NSIB_MBLCharges",Testdata_Row, "BuyCurrency", ScenarioDetailsHashMap), "Verifying Buy Currency at Grid Line--> "+Testdata_Row, ScenarioDetailsHashMap);
			String randomref=ExcelUtils.getCellData("SI_NSIB_MBLCharges", rowNo, "MBLChargesCosts_Vendor_Reference", ScenarioDetailsHashMap)+GenericMethods.randomNumericString(5)+"";
			ExcelUtils.setCellData("SI_NSIB_MBLCharges", rowNo, "MBLChargesCosts_Vendor_Reference", randomref, ScenarioDetailsHashMap);

		    GenericMethods.doubleClickOnElement(driver, By.id("vendorPartyMode"+Testdata_Row),null, 5);
		    		
		    if(Testdata_Row>1){
		    GenericMethods.locateElement(driver, By.id("chargesVOList.vendorPartyMode"+Testdata_Row),2).clear();	
		    }
		    GenericMethods.locateElement(driver, By.id("chargesVOList.vendorPartyMode"+Testdata_Row),2).sendKeys(ExcelUtils.getCellData("SI_NSIB_MBLCharges", Testdata_Row, "MBLChargesCosts_Vendor", ScenarioDetailsHashMap));
			oROceanNsibObl.clickElement(driver, By.xpath("//td[contains(text(),'"+ExcelUtils.getCellData("SI_NSIB_MBLCharges", Testdata_Row, "MBLChargesCosts_Vendor", ScenarioDetailsHashMap)+"')]"), null, 5);
			if(Testdata_Row>1){
			GenericMethods.locateElement(driver, By.id("chargesVOList.reference"+Testdata_Row),2).clear();
			}
			GenericMethods.locateElement(driver, By.id("chargesVOList.reference"+Testdata_Row),2).sendKeys(randomref);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
		 }
	
	     GenericMethods.locateElement(driver,By.name("accept"), 2).click();
		 GenericMethods.pauseExecution(2000);
	     for (int Testdata_Row = 1; Testdata_Row<= rowCount; Testdata_Row++) {
		     String ReadyStatus=GenericMethods.locateElement(driver,By.id("spanchargesVOList.costStatus"+Testdata_Row), 2).getText();
			 GenericMethods.assertTwoValues(ReadyStatus, "ACCEPTED", "Verifying ACCEPTED Status of Charges in Grid for Line--> "+Testdata_Row, ScenarioDetailsHashMap);
	     }  
	     GenericMethods.locateElement(driver,By.name("postCosts"), 2).click();
		 GenericMethods.pauseExecution(2000);
         try{
				GenericMethods.handleAlert(driver, "accept");
				

			}catch (Exception e) {
				System.out.println("no Alerts present");
			}
			 GenericMethods.pauseExecution(2000);
		try{
				GenericMethods.handleAlert(driver, "accept");
			

			}catch (Exception e) {
				System.out.println("no Alerts present");
			}
			try{
				GenericMethods.handleAlert(driver, "accept");
			

			}catch (Exception e) {
				System.out.println("no Alerts present");
			}
			GenericMethods.pauseExecution(12000);

				GenericMethods.selectWindow(driver);
				String title=GenericMethods.getInnerText(driver, null, Common.getElement(driver, "BannerTitle", ScenarioDetailsHashMap,10), 2);
				Assert.assertEquals(title, "Document Confirmation");
			    GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 4), 4);
				GenericMethods.pauseExecution(4000);
			   // GenericMethods.selectWindow(driver);
			    String title1=GenericMethods.getInnerText(driver, null, Common.getElement(driver, "BannerTitle", ScenarioDetailsHashMap,10), 2);

	            GenericMethods.assertTwoValues(title1, "Purchase Invoice","Verifying Purchase Invoice Page Title",ScenarioDetailsHashMap);

			    GenericMethods.clickElement(driver, null, Common.getElement(driver, "CancelButton", ScenarioDetailsHashMap, 4), 4);
				GenericMethods.handleAlert(driver, "accept");
				GenericMethods.switchToParent(driver);
				AppReusableMethods.selectMainFrame(driver,ScenarioDetailsHashMap);
				 for (int Testdata_Row = 1; Testdata_Row<= rowCount; Testdata_Row++) {
					 GenericMethods.pauseExecution(2000);
				     String ReadyStatus=GenericMethods.locateElement(driver,By.id("chargesVOList.costStatusH"+Testdata_Row), 2).getAttribute("value");
					 GenericMethods.assertTwoValues(ReadyStatus, "INVOICED", "Verifying INVOICED Status of Charges in Grid for Line--> "+Testdata_Row, ScenarioDetailsHashMap);
			     }  
	
		
	}

	 //Sangeeta RAT-48(This method will compare HBL date of NSIBL HBL with siginificant date as per decision table in HBLCharges/Cost page)
	public static void oceanImportNsibHBL_SignificantDateValidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		if(ExcelUtils.getCellData("SI_NSIBOBL_HBLDetails", RowNo, "SignificantDateValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			oceanImportNsibObl_Search(driver, ScenarioDetailsHashMap, RowNo);
			GenericMethods.clickElement(driver, null,oROceanNsibObl.getElement(driver, "Tab_HblDetails", ScenarioDetailsHashMap,10), 2);
			try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
			GenericMethods.pauseExecution(7000);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				e.printStackTrace();
			}
			GenericMethods.pauseExecution(3000);
			String HBLDate = driver.findElement(By.id("hblDate")).getAttribute("value");
			GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "HBL_CHARGES_COST_Tab", ScenarioDetailsHashMap, 10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				e.printStackTrace();
			}
			GenericMethods.pauseExecution(7000);
			String SignificantDate = driver.findElement(By.name("execDate")).getAttribute("value");
			GenericMethods.assertTwoValues(SignificantDate, HBLDate, "Validating Significant date in OCEAN NSIB HBL for HBLCharges/Costs page.", ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {

			}
		}
	}
	
	 //Sangeeta RAT-48(This method will compare MBL date of NSIBL MBL with siginificant date as per decision table in MBLCharges/Cost page)
	public static void oceanImportNsibOBL_SignificantDateValidation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		if(ExcelUtils.getCellData("SI_NSIBOBL", RowNo, "SignificantDateValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			oceanImportNsibObl_Search(driver, ScenarioDetailsHashMap, RowNo);
			GenericMethods.clickElement(driver, null,oROceanNsibObl.getElement(driver, "Tab_MainDetails", ScenarioDetailsHashMap,10), 2);
			try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
			GenericMethods.pauseExecution(7000);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				e.printStackTrace();
			}
			GenericMethods.pauseExecution(3000);
			String HBLDate = driver.findElement(By.id("oblDateStr")).getAttribute("value");
			GenericMethods.clickElement(driver, null, oROceanNsibObl.getElement(driver, "Tab_MBLChargesCode", ScenarioDetailsHashMap, 10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				e.printStackTrace();
			}
			GenericMethods.pauseExecution(7000);
			String SignificantDate = driver.findElement(By.name("execDate")).getAttribute("value");
			GenericMethods.assertTwoValues(SignificantDate, HBLDate, "Validating Significant date in OCEAN NSIB MBL for MBLCharges/Costs page.", ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap,10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");
			}catch (Exception e) {

			}
		}
	}


	
}




