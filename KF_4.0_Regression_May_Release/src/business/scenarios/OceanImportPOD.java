package business.scenarios;

import global.reusables.ExcelUtils;
import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import app.reuseables.AppReusableMethods;
import app.reuseables.ETransMenu;

public class OceanImportPOD {
	static ObjectRepository Common = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/CommonObjects.xml");
	static ObjectRepository orOceanImportPOD = new ObjectRepository(System.getProperty("user.dir") + "/ObjectRepository/OceanImportPOD.xml");
    static boolean consignmentNoteStatus=false;

	/**
	 * This method is to perform Ocean POD Add Functionality.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains Records Row number which was given in
	 *            Testdata
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @Author By: Masthan
	 * @Modified By: Sandeep Jain.L
	 * @Modified Date: February 3rd 2015
	 */
	public static void OceanPOD_DI(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) {
		AppReusableMethods.selectMenu(driver, ETransMenu.oceanImportPOD,"POD", ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null, Common.getElement(driver, "AddButton", ScenarioDetailsHashMap, 10), 10);

		String houseID=ExcelUtils.getCellData("SE_HBLMainDetails", rowNo,"HBLId", ScenarioDetailsHashMap).trim();
		System.out.println("houseID::::::::"+houseID);
		//ExcelUtils.setCellData("SI_POD_DI", rowNo, "House_Id", houseID, ScenarioDetailsHashMap);

		GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "Textbox_House_Id", ScenarioDetailsHashMap, 10), houseID, 10);
		//		AppReusableMethods.selectValueFromLov(driver, orOceanImportPOD.getElement(driver, "houseLOV", ScenarioDetailsHashMap, 10), orOceanImportPOD, "houseReferencePOD",houseID, ScenarioDetailsHashMap );

		if(!ExcelUtils.getCellData("SI_POD_DI", rowNo, "DeliveryType", ScenarioDetailsHashMap).equalsIgnoreCase("DeliveryOrder")
				&&
				(!ExcelUtils.getCellData("SI_POD_DI", rowNo, "DeliveryType", ScenarioDetailsHashMap).equals("")
						||!ExcelUtils.getCellData("SI_POD_DI", rowNo, "DeliveryType", ScenarioDetailsHashMap).equals(null))
		)
		{
			GenericMethods.selectOptionFromDropDown(driver, null,orOceanImportPOD.getElement(driver, "DeliveryType", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SI_POD_DI", rowNo, "DeliveryType", ScenarioDetailsHashMap));
			GenericMethods.selectOptionFromDropDown(driver, null,orOceanImportPOD.getElement(driver, "PODType", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SI_POD_DI", rowNo, "PODType", ScenarioDetailsHashMap));
		}

		GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 10);

		//Pavan 17th April 2015 Added Below If Condition to handle DeliveryType=Consignment Note then HBL Id or MBL Id should not flow into POD Screen
		if(!ExcelUtils.getCellData("SI_POD_DI", rowNo, "DeliveryType", ScenarioDetailsHashMap).equalsIgnoreCase("Consignment Note")){

			GenericMethods.pauseExecution(3000);
			//		String XpathPrefix = "//span[@id='myGrid-content-box-middle']/span[@id='myGrid-rows']/span";
			String XpathPrefix = "//td[@id='GRID']/div/table/tbody/tr";

			int POD_GridRowCount = driver.findElements(By.xpath(XpathPrefix)).size();

			for (int GridRowID = 1; GridRowID <= POD_GridRowCount; GridRowID++) 
			{
				System.out.println("insde loop");
				System.out.println("houseID---"+GenericMethods.getInnerText(driver, By.xpath(XpathPrefix+"["+GridRowID+"]/td[4]"), null, 10));
				if(houseID.trim().equalsIgnoreCase(GenericMethods.getInnerText(driver, By.xpath(XpathPrefix+"["+GridRowID+"]/td[4]"), null, 10)))
				{
					GenericMethods.clickElement(driver,By.xpath(XpathPrefix+"["+GridRowID+"]/td[1]/input"), null, 10);
					//break;
				}
			}

			if(!ExcelUtils.getCellData("SI_POD_DI", rowNo, "PODEntry_DoIssuanceDate", ScenarioDetailsHashMap).equals(""))
			{
				GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_DoIssuanceDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DI", rowNo, "PODEntry_DoIssuanceDate", ScenarioDetailsHashMap), 5);
				GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_DoIssuanceTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DI", rowNo, "PODEntry_DoIssuanceTime", ScenarioDetailsHashMap), 5);

				//Date Validation start:Author Sangeeta
				//FUNC062.14-Date Validation(DO issuance date)
				if(ExcelUtils.getCellData("SI_POD_DI", rowNo, "DateValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
				{
					String ActualDOIssuanceDate =  driver.findElement(By.id("doIssuanceDate")).getAttribute("value");
					String ActualDOIssuanceTime =  driver.findElement(By.name("doIssuanceTime")).getAttribute("value");
					String ExpectedDOIssuanceDate = ExcelUtils.getCellData("SI_POD_DI", rowNo, "PODEntry_PUConfirmationDate", ScenarioDetailsHashMap);
					String ExpectedDOIssuanceTime = ExcelUtils.getCellData("SI_POD_DI", rowNo, "PODEntry_PUConfirmationTime", ScenarioDetailsHashMap);
					String ActualDOIssuanceDateTime = ActualDOIssuanceDate.concat(" "+ActualDOIssuanceTime);
					String ExpectedDOIssuanceDateTime = ExpectedDOIssuanceDate.concat(" "+ExpectedDOIssuanceTime);
					try {
						GenericMethods.compareDatesByCompareTo("dd-MM-yyyy hh:mm", ActualDOIssuanceDateTime, ExpectedDOIssuanceDateTime, "Less", "DO issuance date is less than P/U Confirmation Date", "DO issuance date is not less than P/U Confirmation Date", ScenarioDetailsHashMap);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	 
				}
				//Date Validation end
			}

			if(!ExcelUtils.getCellData("SI_POD_DI", rowNo, "PODEntry_PUConfirmationDate", ScenarioDetailsHashMap).equals(""))
			{
				GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_P_U_ConfirmationDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DI", rowNo, "PODEntry_PUConfirmationDate", ScenarioDetailsHashMap), 5);
				GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_P_U_ConfirmationTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DI", rowNo, "PODEntry_PUConfirmationTime", ScenarioDetailsHashMap), 5);
				//Date Validation Start Author-Sangeeta
				//FUNC062.15-Date Validation(P/U Confirmation date)
				if(ExcelUtils.getCellData("SI_POD_DI", rowNo, "DateValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
				{
					String ActualPUConfirmationDate =  driver.findElement(By.id("puConfirmationDate")).getAttribute("value");
					String ActualPUConfirmationTime =  driver.findElement(By.name("puConfirmationTime")).getAttribute("value");
					String ExpectedPUConfirmationDate = ExcelUtils.getCellData("SI_POD_DI", rowNo, "PODEntry_PODDate", ScenarioDetailsHashMap);
					String ExpectedPUConfirmationTime = ExcelUtils.getCellData("SI_POD_DI", rowNo, "PODEntry_PODTime", ScenarioDetailsHashMap);
					String ActualPUConfirmationDateTime = ActualPUConfirmationDate.concat(" "+ActualPUConfirmationTime);
					String ExpectedPUConfirmationDateTime = ExpectedPUConfirmationDate.concat(" "+ExpectedPUConfirmationTime);
					try {
						GenericMethods.compareDatesByCompareTo("dd-MM-yyyy hh:mm", ActualPUConfirmationDateTime, ExpectedPUConfirmationDateTime, "Less", "P/U Confirmation Date is less than Full POD Date", "P/U Confirmation Date is not less than Full POD Date", ScenarioDetailsHashMap);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	 
				}
				//Date Validation end
			}

			if(!ExcelUtils.getCellData("SI_POD_DI", rowNo, "PODEntry_PODDate", ScenarioDetailsHashMap).equals(""))
			{
				GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_PODDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DI", rowNo, "PODEntry_PODDate", ScenarioDetailsHashMap), 5);
				GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_PODTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DI", rowNo, "PODEntry_PODTime", ScenarioDetailsHashMap), 5);
				//Date Validation Start Author-Sangeeta
				//FUNC062.16-Date Validation(Full POD Date)
				if(ExcelUtils.getCellData("SI_POD_DI", rowNo, "DateValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
				{
					String ActualFullPODDate =  driver.findElement(By.id("fullPodDate")).getAttribute("value");
					String ActualFullPODTime =  driver.findElement(By.name("fullPodTime")).getAttribute("value");
					String ExpectedFullPODDate = ExcelUtils.getCellData("SI_POD_DI", rowNo, "ETDDate", ScenarioDetailsHashMap);
					String ExpectedFullPODTime = ExcelUtils.getCellData("SI_POD_DI", rowNo, "ETDTime", ScenarioDetailsHashMap);
					String ActualFullPODDateTime = ActualFullPODDate.concat(" "+ActualFullPODTime);
					String ExpectedFullPODateTime = ExpectedFullPODDate.concat(" "+ExpectedFullPODTime);
					try {
						GenericMethods.compareDatesByCompareTo("dd-MM-yyyy hh:mm", ActualFullPODDateTime, ExpectedFullPODateTime, "Less", "POD Date is same or less than the system date/Time ", "POD Date is not same or less than the system date/Time ", ScenarioDetailsHashMap);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	 
				}
				//Date Validation end
			}

			GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_DeliveryRemarks", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DI", rowNo, "POD_Remarks", ScenarioDetailsHashMap), 5);


			//validation for funct22.01

			if(ExcelUtils.getCellData("SI_POD_DI", rowNo, "DecimalPrecesionRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes")){
				int rows=GenericMethods.locateElements(driver, By.xpath("//td[@id='GRID']/div/table/tbody/tr"), 10).size();
				//			int rows=GenericMethods.locateElements(driver, By.xpath("//span[@id='myGrid-rows']/span[@aw='row']"), 10).size();

				for (int i = 0; i < rows; i++) {
					GenericMethods.clickElement(driver,By.xpath("//td[@id='GRID']/div/table/tbody/tr[1]/td[4]"), null, 10);
					//				GenericMethods.clickElement(driver, By.id("myGrid-cell-5-"+i+"-box-text"), null, 10);
					String val=GenericMethods.readValue(driver, orOceanImportPOD.getElement(driver, "Input_TotalWeight", ScenarioDetailsHashMap, 10), null);
					GenericMethods.assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", rowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for Weight Total ", ScenarioDetailsHashMap);
					val=GenericMethods.readValue(driver, orOceanImportPOD.getElement(driver, "Input_CurrentWeight", ScenarioDetailsHashMap, 10), null);
					GenericMethods.assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", rowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for Received Weight ", ScenarioDetailsHashMap);
					val=GenericMethods.readValue(driver, orOceanImportPOD.getElement(driver, "Input_TotalVolume", ScenarioDetailsHashMap, 10), null);
					GenericMethods.assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", rowNo, "DecimalsForVolume", ScenarioDetailsHashMap), "Validating Decimals digits for Total Volume ", ScenarioDetailsHashMap);

					val=GenericMethods.readValue(driver, orOceanImportPOD.getElement(driver, "Input_RecivedVolume", ScenarioDetailsHashMap, 10), null);
					GenericMethods.assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", rowNo, "DecimalsForVolume", ScenarioDetailsHashMap), "Validating Decimals digits for Received Volume ", ScenarioDetailsHashMap);

				}

			}
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 5);	
			String PODIdSummary=GenericMethods.getInnerText(driver, null, orOceanImportPOD.getElement(driver, "POD_VerificationMsg", ScenarioDetailsHashMap, 20), 2);
			String PODSuccessMessage=PODIdSummary.split(" : ")[1].split(":")[0];
			String PODHouseID = PODIdSummary.split(":")[2];
			ExcelUtils.setCellData("SI_POD_DI", rowNo, "POD_ID", PODHouseID, ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(PODSuccessMessage, "POD has done for Shipments", "Validating Sea POD Msg", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(PODHouseID,houseID , "Validating House ID in Msg", ScenarioDetailsHashMap);
		}//Ends IF Condition Pavan 17th April 2015
		//Pavan 17th April 2015 Added Below Code if DeliveryType=Consignment Note then HBL Id or MBL Id should not flow into POD Screen
		else if(ExcelUtils.getCellData("SI_POD_DI", rowNo, "DeliveryType", ScenarioDetailsHashMap).equalsIgnoreCase("Consignment Note")){
			consignmentNoteStatus=true;
			GenericMethods.assertTwoValues(consignmentNoteStatus+"",true+"", "Validating HBL ID or MBL ID will not flow into POD Screen as Delivery Type is Consignment Note", ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "CloseButton", ScenarioDetailsHashMap, 10), 5);
			GenericMethods.selectWindow(driver);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "ExitPopup", ScenarioDetailsHashMap, 10), 5);
		}//End Pavan 17th April 2015
		
		/*if(GenericMethods.locateElements(driver, By.xpath("//* //span[text()='"+houseID+"']"), 10).size()!=0)
		{
			WebElement elem=GenericMethods.locateElement(driver, By.id("myGrid-cell-2-0-box-text"), 10);
			String title=GenericMethods.getInnerText(driver, null, elem, 10);
			if(houseID.equals(title))
			{
				GenericMethods.locateElement(driver, By.id("selectedIds"+0), 10).click();
				GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_PODDate", ScenarioDetailsHashMap, 5),GenericMethods.currentDate(), 5);
				GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_PODTime", ScenarioDetailsHashMap, 5),GenericMethods.currentTime(), 5);
				GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_DeliveryRemarks", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DI", rowNo, "POD_Remarks", ScenarioDetailsHashMap), 5);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton2", ScenarioDetailsHashMap, 10), 5);	
				String PODIdSummary=GenericMethods.getInnerText(driver, null, orOceanImportPOD.getElement(driver, "VerificationMsg", ScenarioDetailsHashMap, 20), 2);
				String PODSuccessMessage=PODIdSummary.split(" : ")[1].split(":")[0];
				String PODHouseID = PODIdSummary.split(":")[2];
				ExcelUtils.setCellData("SI_POD_DI", rowNo, "POD_ID", PODHouseID, ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(PODSuccessMessage, "POD has done for Shipments", "Validating Sea POD Msg", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(PODHouseID,houseID , "Validating House ID in Msg", ScenarioDetailsHashMap);
			}
			else
			{
				GenericMethods.assertTwoValues(null,houseID, "Validating House ID in POD Screen", ScenarioDetailsHashMap);
			}
		}*/

	}
	/**
	 * This method is to perform Ocean POD Add Functionality for multiple houses.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains Records Row number which was given in
	 *            Testdata
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @Author By: Masthan
	 * @Modified By: Sandeep Jain.L
	 * @Modified Date: February 3rd 2015
	 */
	public static void NSIB_OceanPOD_DI(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) {
		int rowCount=ExcelUtils.getCellDataRowCount("SE_HBLMainDetails", ScenarioDetailsHashMap);
		for (int row = 1; row <= rowCount; row++) {
			AppReusableMethods.selectMenu(driver, ETransMenu.oceanImportPOD,"POD", ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "AddButton", ScenarioDetailsHashMap, 10), 10);

			String houseID=ExcelUtils.getCellData("SE_HBLMainDetails", row,"HBLId", ScenarioDetailsHashMap).trim();
			//ExcelUtils.setCellData("SI_POD_DI", rowNo, "House_Id", houseID, ScenarioDetailsHashMap);
			GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "Textbox_House_Id", ScenarioDetailsHashMap, 10), houseID, 10);
			//		AppReusableMethods.selectValueFromLov(driver, orOceanImportPOD.getElement(driver, "houseLOV", ScenarioDetailsHashMap, 10), orOceanImportPOD, "houseReferencePOD",houseID, ScenarioDetailsHashMap );

			if(!ExcelUtils.getCellData("SI_POD_DI", row, "DeliveryType", ScenarioDetailsHashMap).equalsIgnoreCase("DeliveryOrder")
					&&
					(!ExcelUtils.getCellData("SI_POD_DI", row, "DeliveryType", ScenarioDetailsHashMap).equals("")
							||!ExcelUtils.getCellData("SI_POD_DI", row, "DeliveryType", ScenarioDetailsHashMap).equals(null))
			)
			{
				GenericMethods.selectOptionFromDropDown(driver, null,orOceanImportPOD.getElement(driver, "DeliveryType", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SI_POD_DI", row, "DeliveryType", ScenarioDetailsHashMap));
				GenericMethods.selectOptionFromDropDown(driver, null,orOceanImportPOD.getElement(driver, "PODType", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SI_POD_DI", row, "PODType", ScenarioDetailsHashMap));
			}

			GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 10);

			//Pavan 17th April 2015 Added Below If Condition to handle DeliveryType=Consignment Note then HBL Id or MBL Id should not flow into POD Screen
			if(!ExcelUtils.getCellData("SI_POD_DI", row, "DeliveryType", ScenarioDetailsHashMap).equalsIgnoreCase("Consignment Note")){

				GenericMethods.pauseExecution(3000);
				//		String XpathPrefix = "//span[@id='myGrid-content-box-middle']/span[@id='myGrid-rows']/span";
				String XpathPrefix = "//td[@id='GRID']/div/table/tbody/tr";
				int POD_GridRowCount = driver.findElements(By.xpath(XpathPrefix)).size();

				for (int GridRowID = 1; GridRowID <= POD_GridRowCount; GridRowID++) 
				{

					System.out.println("houseID---"+GenericMethods.getInnerText(driver, By.xpath(XpathPrefix+"["+GridRowID+"]/td[4]"), null, 10));
					if(houseID.trim().equalsIgnoreCase(GenericMethods.getInnerText(driver, By.xpath(XpathPrefix+"["+GridRowID+"]/td[4]"), null, 10)))
					{
						GenericMethods.clickElement(driver,By.xpath(XpathPrefix+"["+GridRowID+"]/td[1]/input"), null, 10);
						//break;
					}
				}

				if(!ExcelUtils.getCellData("SI_POD_DI", row, "PODEntry_DoIssuanceDate", ScenarioDetailsHashMap).equals(""))
				{
					GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_DoIssuanceDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DI", row, "PODEntry_DoIssuanceDate", ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_DoIssuanceTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DI", row, "PODEntry_DoIssuanceTime", ScenarioDetailsHashMap), 5);

					//Date Validation start:Author Sangeeta
					//FUNC062.14-Date Validation(DO issuance date)
					if(ExcelUtils.getCellData("SI_POD_DI", row, "DateValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
					{
						String ActualDOIssuanceDate =  driver.findElement(By.id("doIssuanceDate")).getAttribute("value");
						String ActualDOIssuanceTime =  driver.findElement(By.name("doIssuanceTime")).getAttribute("value");
						String ExpectedDOIssuanceDate = ExcelUtils.getCellData("SI_POD_DI", row, "PODEntry_PUConfirmationDate", ScenarioDetailsHashMap);
						String ExpectedDOIssuanceTime = ExcelUtils.getCellData("SI_POD_DI", row, "PODEntry_PUConfirmationTime", ScenarioDetailsHashMap);
						String ActualDOIssuanceDateTime = ActualDOIssuanceDate.concat(" "+ActualDOIssuanceTime);
						String ExpectedDOIssuanceDateTime = ExpectedDOIssuanceDate.concat(" "+ExpectedDOIssuanceTime);
						try {
							GenericMethods.compareDatesByCompareTo("dd-MM-yyyy hh:mm", ActualDOIssuanceDateTime, ExpectedDOIssuanceDateTime, "Less", "DO issuance date is less than P/U Confirmation Date", "DO issuance date is not less than P/U Confirmation Date", ScenarioDetailsHashMap);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	 
					}
					//Date Validation end
				}

				if(!ExcelUtils.getCellData("SI_POD_DI", row, "PODEntry_PUConfirmationDate", ScenarioDetailsHashMap).equals(""))
				{
					GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_P_U_ConfirmationDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DI", row, "PODEntry_PUConfirmationDate", ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_P_U_ConfirmationTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DI", row, "PODEntry_PUConfirmationTime", ScenarioDetailsHashMap), 5);
					//Date Validation Start Author-Sangeeta
					//FUNC062.15-Date Validation(P/U Confirmation date)
					if(ExcelUtils.getCellData("SI_POD_DI", row, "DateValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
					{
						String ActualPUConfirmationDate =  driver.findElement(By.id("puConfirmationDate")).getAttribute("value");
						String ActualPUConfirmationTime =  driver.findElement(By.name("puConfirmationTime")).getAttribute("value");
						String ExpectedPUConfirmationDate = ExcelUtils.getCellData("SI_POD_DI", row, "PODEntry_PODDate", ScenarioDetailsHashMap);
						String ExpectedPUConfirmationTime = ExcelUtils.getCellData("SI_POD_DI", row, "PODEntry_PODTime", ScenarioDetailsHashMap);
						String ActualPUConfirmationDateTime = ActualPUConfirmationDate.concat(" "+ActualPUConfirmationTime);
						String ExpectedPUConfirmationDateTime = ExpectedPUConfirmationDate.concat(" "+ExpectedPUConfirmationTime);
						try {
							GenericMethods.compareDatesByCompareTo("dd-MM-yyyy hh:mm", ActualPUConfirmationDateTime, ExpectedPUConfirmationDateTime, "Less", "P/U Confirmation Date is less than Full POD Date", "P/U Confirmation Date is not less than Full POD Date", ScenarioDetailsHashMap);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	 
					}
					//Date Validation end
				}

				if(!ExcelUtils.getCellData("SI_POD_DI", row, "PODEntry_PODDate", ScenarioDetailsHashMap).equals(""))
				{
					GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_PODDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DI", row, "PODEntry_PODDate", ScenarioDetailsHashMap), 5);
					GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_PODTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DI", row, "PODEntry_PODTime", ScenarioDetailsHashMap), 5);
					//Date Validation Start Author-Sangeeta
					//FUNC062.16-Date Validation(Full POD Date)
					if(ExcelUtils.getCellData("SI_POD_DI", row, "DateValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
					{
						String ActualFullPODDate =  driver.findElement(By.id("fullPodDate")).getAttribute("value");
						String ActualFullPODTime =  driver.findElement(By.name("fullPodTime")).getAttribute("value");
						String ExpectedFullPODDate = ExcelUtils.getCellData("SI_POD_DI", row, "ETDDate", ScenarioDetailsHashMap);
						String ExpectedFullPODTime = ExcelUtils.getCellData("SI_POD_DI", row, "ETDTime", ScenarioDetailsHashMap);
						String ActualFullPODDateTime = ActualFullPODDate.concat(" "+ActualFullPODTime);
						String ExpectedFullPODateTime = ExpectedFullPODDate.concat(" "+ExpectedFullPODTime);
						try {
							GenericMethods.compareDatesByCompareTo("dd-MM-yyyy hh:mm", ActualFullPODDateTime, ExpectedFullPODateTime, "Less", "POD Date is same or less than the system date/Time ", "POD Date is not same or less than the system date/Time ", ScenarioDetailsHashMap);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	 
					}
					//Date Validation end
				}

				GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_DeliveryRemarks", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DI", row, "POD_Remarks", ScenarioDetailsHashMap), 5);


				//validation for funct22.01

				if(ExcelUtils.getCellData("SI_POD_DI", rowNo, "DecimalPrecesionRequired", ScenarioDetailsHashMap).equalsIgnoreCase("yes")){
					int rows=GenericMethods.locateElements(driver, By.xpath("//td[@id='GRID']/div/table/tbody/tr"), 10).size();
					for (int i = 0; i < rows; i++) {
						GenericMethods.clickElement(driver,By.xpath("//td[@id='GRID']/div/table/tbody/tr[1]/td[4]"), null, 10);
						String val=GenericMethods.readValue(driver, orOceanImportPOD.getElement(driver, "Input_TotalWeight", ScenarioDetailsHashMap, 10), null);
						GenericMethods.assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", row, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for Weight Total ", ScenarioDetailsHashMap);
						val=GenericMethods.readValue(driver, orOceanImportPOD.getElement(driver, "Input_CurrentWeight", ScenarioDetailsHashMap, 10), null);
						GenericMethods.assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", row, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for Received Weight ", ScenarioDetailsHashMap);
						val=GenericMethods.readValue(driver, orOceanImportPOD.getElement(driver, "Input_TotalVolume", ScenarioDetailsHashMap, 10), null);
						GenericMethods.assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", row, "DecimalsForVolume", ScenarioDetailsHashMap), "Validating Decimals digits for Total Volume ", ScenarioDetailsHashMap);

						val=GenericMethods.readValue(driver, orOceanImportPOD.getElement(driver, "Input_RecivedVolume", ScenarioDetailsHashMap, 10), null);
						GenericMethods.assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", row, "DecimalsForVolume", ScenarioDetailsHashMap), "Validating Decimals digits for Received Volume ", ScenarioDetailsHashMap);

					}

				}
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 5);	
				String PODIdSummary=GenericMethods.getInnerText(driver, null, orOceanImportPOD.getElement(driver, "POD_VerificationMsg", ScenarioDetailsHashMap, 20), 2);
				String PODSuccessMessage=PODIdSummary.split(" : ")[1].split(":")[0];
				String PODHouseID = PODIdSummary.split(":")[2];
				ExcelUtils.setCellData("SI_POD_DI", row, "POD_ID", PODHouseID, ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(PODSuccessMessage, "POD has done for Shipments", "Validating Sea POD Msg", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(PODHouseID,houseID , "Validating House ID in Msg", ScenarioDetailsHashMap);
			}//Ends IF Condition Pavan 17th April 2015
			//Pavan 17th April 2015 Added Below Code if DeliveryType=Consignment Note then HBL Id or MBL Id should not flow into POD Screen
			else if(ExcelUtils.getCellData("SI_POD_DI", row, "DeliveryType", ScenarioDetailsHashMap).equalsIgnoreCase("Consignment Note")){
				consignmentNoteStatus=true;
				GenericMethods.assertTwoValues(consignmentNoteStatus+"",true+"", "Validating HBL ID or MBL ID will not flow into POD Screen as Delivery Type is Consignment Note", ScenarioDetailsHashMap);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "CloseButton", ScenarioDetailsHashMap, 10), 5);
				GenericMethods.selectWindow(driver);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "ExitPopup", ScenarioDetailsHashMap, 10), 5);
			}//End Pavan 17th April 2015

		}
		/*if(GenericMethods.locateElements(driver, By.xpath("//* //span[text()='"+houseID+"']"), 10).size()!=0)
		{
			WebElement elem=GenericMethods.locateElement(driver, By.id("myGrid-cell-2-0-box-text"), 10);
			String title=GenericMethods.getInnerText(driver, null, elem, 10);
			if(houseID.equals(title))
			{
				GenericMethods.locateElement(driver, By.id("selectedIds"+0), 10).click();
				GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_PODDate", ScenarioDetailsHashMap, 5),GenericMethods.currentDate(), 5);
				GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_PODTime", ScenarioDetailsHashMap, 5),GenericMethods.currentTime(), 5);
				GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_DeliveryRemarks", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DI", rowNo, "POD_Remarks", ScenarioDetailsHashMap), 5);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton2", ScenarioDetailsHashMap, 10), 5);	
				String PODIdSummary=GenericMethods.getInnerText(driver, null, orOceanImportPOD.getElement(driver, "VerificationMsg", ScenarioDetailsHashMap, 20), 2);
				String PODSuccessMessage=PODIdSummary.split(" : ")[1].split(":")[0];
				String PODHouseID = PODIdSummary.split(":")[2];
				ExcelUtils.setCellData("SI_POD_DI", rowNo, "POD_ID", PODHouseID, ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(PODSuccessMessage, "POD has done for Shipments", "Validating Sea POD Msg", ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(PODHouseID,houseID , "Validating House ID in Msg", ScenarioDetailsHashMap);
			}
			else
			{
				GenericMethods.assertTwoValues(null,houseID, "Validating House ID in POD Screen", ScenarioDetailsHashMap);
			}
		}*/
	}

	/* This method is to perform Ocean POD Add Functionality.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains Records Row number which was given in
	 *            Testdata
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details. 
	 * @Modified By: Sandeep Jain.L
	 * @Modified Date: February 3rd 2015
	 */
	public static void OceanPOD_DO(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int POD_RowID) {

		for (int  rowNo= 1; rowNo <= ExcelUtils.getCellDataRowCount("SI_POD_DO", ScenarioDetailsHashMap); rowNo++) 
		{
			AppReusableMethods.selectMenu(driver, ETransMenu.oceanImportPOD,"POD", ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "AddButton", ScenarioDetailsHashMap, 10), 10);

			String houseID=ExcelUtils.getCellData("SE_HBLMainDetails", POD_RowID,"HBLId", ScenarioDetailsHashMap).trim();
			//ExcelUtils.setCellData("SI_POD_DO", rowNo, "House_Id", houseID, ScenarioDetailsHashMap);
			//			AppReusableMethods.selectValueFromLov(driver, orOceanImportPOD.getElement(driver, "houseLOV", ScenarioDetailsHashMap, 10), orOceanImportPOD, "houseReferencePOD",houseID, ScenarioDetailsHashMap );
			GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "Textbox_House_Id", ScenarioDetailsHashMap, 10), houseID, 10);
			if(!ExcelUtils.getCellData("SI_POD_DO", rowNo, "DeliveryType", ScenarioDetailsHashMap).equalsIgnoreCase("DeliveryOrder")
					&&
					(!ExcelUtils.getCellData("SI_POD_DO", rowNo, "DeliveryType", ScenarioDetailsHashMap).equals("")
							||!ExcelUtils.getCellData("SI_POD_DO", rowNo, "DeliveryType", ScenarioDetailsHashMap).equals(null))
			)
			{
				GenericMethods.selectOptionFromDropDown(driver, null,orOceanImportPOD.getElement(driver, "DeliveryType", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SI_POD_DO", rowNo, "DeliveryType", ScenarioDetailsHashMap));
				GenericMethods.selectOptionFromDropDown(driver, null,orOceanImportPOD.getElement(driver, "PODType", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODType", ScenarioDetailsHashMap));
			}

			GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 10);


			GenericMethods.pauseExecution(3000);
			//			String XpathPrefix = "//span[@id='myGrid-content-box-middle']/span[@id='myGrid-rows']/span";
			String XpathPrefix = "//td[@id='GRID']/div/table/tbody/tr";
			int POD_GridRowCount = driver.findElements(By.xpath(XpathPrefix)).size();

			int DOIDSplitCount = ExcelUtils.getCellData("SI_POD_DO", POD_RowID,"DO_ID", ScenarioDetailsHashMap).split(",").length;

			System.out.println("1111");
			System.out.println("2222"+DOIDSplitCount);
			System.out.println("3333"+ExcelUtils.getCellDataRowCount("SI_POD_DO", ScenarioDetailsHashMap));
			//Below if condition will execute when for multiple DO's for one POD.
			if(DOIDSplitCount>ExcelUtils.getCellDataRowCount("SI_POD_DO", ScenarioDetailsHashMap))
			{
				System.out.println("44444");
				//Below for loop will iterate for number of DO Ids available in the split variable.
				for (int DOID = 0; DOID < DOIDSplitCount; DOID++) 
				{
					System.out.println("5555555");
					//Below for loop is te search for the DOID which is available in the grid.
					for (int GridRowID = 1; GridRowID <= POD_GridRowCount; GridRowID++) 
					{
						System.out.println("666666");
						System.out.println("doid---"+GenericMethods.getInnerText(driver, By.xpath(XpathPrefix+"["+GridRowID+"]/td[3]"), null, 10));
						System.out.println("TESTDATA_DOID--"+ExcelUtils.getCellData("SI_POD_DO", rowNo,"DO_ID", ScenarioDetailsHashMap).split(",")[DOID].trim());
						if(ExcelUtils.getCellData("SI_POD_DO", rowNo,"DO_ID", ScenarioDetailsHashMap).split(",")[DOID].trim().equalsIgnoreCase(GenericMethods.getInnerText(driver, By.xpath(XpathPrefix+"["+GridRowID+"]/td[3]"), null, 10)))
						{
							GenericMethods.clickElement(driver,By.xpath(XpathPrefix+"["+GridRowID+"]/td[1]/input"), null, 10);
							if(!ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_DoIssuanceDate", ScenarioDetailsHashMap).equals(""))
							{
								GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_DoIssuanceDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_DoIssuanceDate", ScenarioDetailsHashMap), 5);
								GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_DoIssuanceTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_DoIssuanceTime", ScenarioDetailsHashMap), 5);
								//Date Validation Start Author-Sangeeta
								//FUNC062.14-Date Validation(DO issuance date)
								String ActualDOIssuanceDate =  driver.findElement(By.id("doIssuanceDate")).getAttribute("value");
								String ActualDOIssuanceTime =  driver.findElement(By.name("doIssuanceTime")).getAttribute("value");
								String ExpectedDOIssuanceDate = ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_PUConfirmationDate", ScenarioDetailsHashMap);
								String ExpectedDOIssuanceTime = ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_PUConfirmationTime", ScenarioDetailsHashMap);
								String ActualDOIssuanceDateTime = ActualDOIssuanceDate.concat(" "+ActualDOIssuanceTime);
								String ExpectedDOIssuanceDateTime = ExpectedDOIssuanceDate.concat(" "+ExpectedDOIssuanceTime);
								try {
									GenericMethods.compareDatesByCompareTo("dd-MM-yyyy hh:mm", ActualDOIssuanceDateTime, ExpectedDOIssuanceDateTime, "Less", "DO issuance date is less than P/U Confirmation Date", "DO issuance date is not less than P/U Confirmation Date", ScenarioDetailsHashMap);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	 
								//Date Validation end
							}

							if(!ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_PUConfirmationDate", ScenarioDetailsHashMap).equals(""))
							{
								GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_P_U_ConfirmationDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_PUConfirmationDate", ScenarioDetailsHashMap), 5);
								GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_P_U_ConfirmationTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_PUConfirmationTime", ScenarioDetailsHashMap), 5);
							}

							if(!ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_PODDate", ScenarioDetailsHashMap).equals(""))
							{
								GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_PODDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_PODDate", ScenarioDetailsHashMap), 5);
								GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_PODTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_PODTime", ScenarioDetailsHashMap), 5);
								//Date Validation Start Author-Sangeeta
								//FUNC062.16-Date Validation(Full POD Date)
								String ActualFullPODDate =  driver.findElement(By.id("fullPodDate")).getAttribute("value");
								String ActualFullPODTime =  driver.findElement(By.name("fullPodTime")).getAttribute("value");
								String ExpectedFullPODDate = ExcelUtils.getCellData("SI_POD_DO", rowNo, "ETDDate", ScenarioDetailsHashMap);
								String ExpectedFullPODTime = ExcelUtils.getCellData("SI_POD_DO", rowNo, "ETDTime", ScenarioDetailsHashMap);
								String ActualFullPODDateTime = ActualFullPODDate.concat(" "+ActualFullPODTime);
								String ExpectedFullPODateTime = ExpectedFullPODDate.concat(" "+ExpectedFullPODTime);
								try {
									GenericMethods.compareDatesByCompareTo("dd-MM-yyyy hh:mm", ActualFullPODDateTime, ExpectedFullPODateTime, "Less", "POD Date is same as ETA date or greater than ETA date and same or less than the system date/Time ", "POD Date is not same as ETA date or greater than ETA date and same or less than the system date/Time ", ScenarioDetailsHashMap);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	 
								//Date Validation end

							}

							break;
						}
					}
				}
			}
			//Below condition will execute when single DO's need to be performed.
			else
			{
				System.out.println("else codtiontion---");

				for (int GridRowID = 1; GridRowID <= POD_GridRowCount; GridRowID++) 
				{
					int actual_DOId = rowNo -1;
					/*System.out.println("doid---"+GenericMethods.getInnerText(driver, By.xpath(XpathPrefix+"["+GridRowID+"]/span[2]/span[1]/span[2]"), null, 10));
				System.out.println("TESTDATA_DOID--"+ExcelUtils.getCellData("SI_POD_DO", rowNo,"DO_ID", ScenarioDetailsHashMap).split(",")[actual_DOId].trim());*/
					if(ExcelUtils.getCellData("SI_POD_DO", rowNo,"DO_ID", ScenarioDetailsHashMap).split(",")[actual_DOId].trim().equalsIgnoreCase(GenericMethods.getInnerText(driver, By.xpath(XpathPrefix+"["+GridRowID+"]/td[3]"), null, 10)))
					{
						GenericMethods.clickElement(driver,By.xpath(XpathPrefix+"["+GridRowID+"]/td[1]/input"), null, 10);
						if(!ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_DoIssuanceDate", ScenarioDetailsHashMap).equals(""))
						{
							GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_DoIssuanceDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_DoIssuanceDate", ScenarioDetailsHashMap), 5);
							GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_DoIssuanceTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_DoIssuanceTime", ScenarioDetailsHashMap), 5);
							//Date Validation Start Author-Sangeeta
							//FUNC062.14-Date Validation(DO issuance date)
							if(ExcelUtils.getCellData("SI_POD_DO", rowNo, "DateValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
							{
								String ActualDOIssuanceDate =  driver.findElement(By.id("doIssuanceDate")).getAttribute("value");
								String ActualDOIssuanceTime =  driver.findElement(By.name("doIssuanceTime")).getAttribute("value");
								String ExpectedDOIssuanceDate = ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_PUConfirmationDate", ScenarioDetailsHashMap);
								String ExpectedDOIssuanceTime = ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_PUConfirmationTime", ScenarioDetailsHashMap);
								String ActualDOIssuanceDateTime = ActualDOIssuanceDate.concat(" "+ActualDOIssuanceTime);
								String ExpectedDOIssuanceDateTime = ExpectedDOIssuanceDate.concat(" "+ExpectedDOIssuanceTime);
								try {
									GenericMethods.compareDatesByCompareTo("dd-MM-yyyy hh:mm", ActualDOIssuanceDateTime, ExpectedDOIssuanceDateTime, "Less", "DO issuance date is less than P/U Confirmation Date", "DO issuance date is not less than P/U Confirmation Date", ScenarioDetailsHashMap);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
							}
							//Date Validation end
						}

						if(!ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_PUConfirmationDate", ScenarioDetailsHashMap).equals(""))
						{
							GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_P_U_ConfirmationDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_PUConfirmationDate", ScenarioDetailsHashMap), 5);
							GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_P_U_ConfirmationTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_PUConfirmationTime", ScenarioDetailsHashMap), 5);
						}

						if(!ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_PODDate", ScenarioDetailsHashMap).equals(""))
						{
							GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_PODDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_PODDate", ScenarioDetailsHashMap), 5);
							GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_PODTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DO", rowNo, "PODEntry_PODTime", ScenarioDetailsHashMap), 5);
							//Date Validation Start Author-Sangeeta
							//FUNC062.16-Date Validation(Full POD Date)
							if(ExcelUtils.getCellData("SI_POD_DO", rowNo, "DateValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
							{
								String ActualFullPODDate =  driver.findElement(By.id("fullPodDate")).getAttribute("value");
								String ActualFullPODTime =  driver.findElement(By.name("fullPodTime")).getAttribute("value");
								String ExpectedFullPODDate = ExcelUtils.getCellData("SI_POD_DO", rowNo, "ETDDate", ScenarioDetailsHashMap);
								String ExpectedFullPODTime = ExcelUtils.getCellData("SI_POD_DO", rowNo, "ETDTime", ScenarioDetailsHashMap);
								String ActualFullPODDateTime = ActualFullPODDate.concat(" "+ActualFullPODTime);
								String ExpectedFullPODateTime = ExpectedFullPODDate.concat(" "+ExpectedFullPODTime);
								try {
									GenericMethods.compareDatesByCompareTo("dd-MM-yyyy hh:mm", ActualFullPODDateTime, ExpectedFullPODateTime, "Less", "POD Date is same or less than the system date/Time ", "POD Date is not same or less than the system date/Time ", ScenarioDetailsHashMap);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
							}
							//Date Validation end

						}


						break;
					}
				}
			}


			GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_DeliveryRemarks", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_POD_DO", rowNo, "POD_Remarks", ScenarioDetailsHashMap), 5);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 5);	
			String PODIdSummary=GenericMethods.getInnerText(driver, null, orOceanImportPOD.getElement(driver, "POD_VerificationMsg", ScenarioDetailsHashMap, 20), 2);
			String PODSuccessMessage=PODIdSummary.split(" : ")[1].split(":")[0];
			String PODHouseID = PODIdSummary.split(":")[2];
			ExcelUtils.setCellData("SI_POD_DO", rowNo, "POD_ID", PODHouseID, ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(PODSuccessMessage, "POD has done for Shipments", "Validating Sea POD Msg", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(PODHouseID,houseID , "Validating House ID in Msg", ScenarioDetailsHashMap);
		}
	}



	//Sandeep- FUNC48 - Below method is to perform seach functionality in DO screen after entering details for all search fields. 
	public static void PODSearchList_Validations_DO(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		AppReusableMethods.selectMenu(driver, ETransMenu.oceanImportPOD,"POD", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "SearchlistDOId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "DoId", ScenarioDetailsHashMap), 2);
		//GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "SearchShipmentReference", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("", RowNo, "", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "SearchHawbId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo,"HBL_ID", ScenarioDetailsHashMap).trim(), 2);
		//		GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "SearchMasterNo", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "MasterId", ScenarioDetailsHashMap), 2);
		//GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "SearchReferenceNo", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo,"ReferenceId", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "SearchCustomerId", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_DO", RowNo, "Customer", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "SearchShipperId", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_DO", RowNo, "Shipper", ScenarioDetailsHashMap), 5);
		GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "SearchConsigneeId", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("SI_DO", RowNo, "Consignee", ScenarioDetailsHashMap), 5);
		/*GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "TotalBoxes", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "CuurentPieces", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "GrsWeight", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "CurrentWeight", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "SearchVolume", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "CurrentVolume", ScenarioDetailsHashMap), 2);*/
		//GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "SearchContainerNo", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "ContainerNo", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orOceanImportPOD.getElement(driver, "Search", ScenarioDetailsHashMap, 10), 10);
		GenericMethods.pauseExecution(2000);
		GenericMethods.assertInnerText(driver, By.id("dtDoIdSearch1"), null, ExcelUtils.getCellData("SI_DO", RowNo,"DoId", ScenarioDetailsHashMap), "DO ID", "Verifying DO ID details in grid.", ScenarioDetailsHashMap);

	}



}


