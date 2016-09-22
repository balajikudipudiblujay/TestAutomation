package business.scenarios;

import java.text.ParseException;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import app.reuseables.AppReusableMethods;
import app.reuseables.ETransMenu;
import global.reusables.ExcelUtils;
import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;

public class AirImport_POD {

	static ObjectRepository Common = new ObjectRepository(System.getProperty("user.dir")+ "\\ObjectRepository\\CommonObjects.xml");
	static ObjectRepository orAirImportPOD = new ObjectRepository(System.getProperty("user.dir") + "\\ObjectRepository\\AirImportPOD.xml");
	static boolean consignmentNoteStatus=false;

	/**
	 * This method is to perform Air POD DI Add Functionality.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains Records Row number which was given in
	 *            Testdata
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @Author By: Sandeep Jain.L
	 * @Modified By: Sangeeta M
	 * @Modified Date: June 10th 2015
	 */
	/*
	 */



	public static void AirPOD_DI(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int rowNo) {
		AppReusableMethods.selectMenu(driver, ETransMenu.oceanImportPOD,"POD", ScenarioDetailsHashMap);
		GenericMethods.clickElement(driver, null, orAirImportPOD.getElement(driver, "Air_PODNewBtn", ScenarioDetailsHashMap, 10), 10);
		System.out.println("DatasetNo-->"+ScenarioDetailsHashMap.get("DataSetNo"));
		String houseID=ExcelUtils.getCellData("Air_HAWBMainDetails", rowNo,"HAWB_Id", ScenarioDetailsHashMap).trim();
		System.out.println("houseID::::::::"+houseID);
		//ExcelUtils.setCellData("Air_POD_DI", rowNo, "House_Id", houseID, ScenarioDetailsHashMap);

		GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_HouseNoSearch", ScenarioDetailsHashMap, 10), houseID, 10);
		//		AppReusableMethods.selectValueFromLov(driver, orOceanImportPOD.getElement(driver, "houseLOV", ScenarioDetailsHashMap, 10), orOceanImportPOD, "houseReferencePOD",houseID, ScenarioDetailsHashMap );

		if(!ExcelUtils.getCellData("Air_POD_DI", rowNo, "DeliveryType", ScenarioDetailsHashMap).equalsIgnoreCase("DeliveryOrder")
				&&
				(!ExcelUtils.getCellData("Air_POD_DI", rowNo, "DeliveryType", ScenarioDetailsHashMap).equals("")
						||!ExcelUtils.getCellData("Air_POD_DI", rowNo, "DeliveryType", ScenarioDetailsHashMap).equals(null))
		)
		{
			GenericMethods.selectOptionFromDropDown(driver, null,orAirImportPOD.getElement(driver, "Air_DeliveryTypeSearch", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("Air_POD_DI", rowNo, "DeliveryType", ScenarioDetailsHashMap));
			GenericMethods.selectOptionFromDropDown(driver, null,orAirImportPOD.getElement(driver, "Air_PodTypeSearch", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("Air_POD_DI", rowNo, "PODType", ScenarioDetailsHashMap));
		}

		GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 10);

		//Pavan 17th April 2015 Added Below If Condition to handle DeliveryType=Consignment Note then HBL Id or MBL Id should not flow into POD Screen
		if(!ExcelUtils.getCellData("Air_POD_DI", rowNo, "DeliveryType", ScenarioDetailsHashMap).equalsIgnoreCase("Consignment Note")){

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

			if(!ExcelUtils.getCellData("Air_POD_DI", rowNo, "PODEntry_DoIssuanceDate", ScenarioDetailsHashMap).equals(""))
			{
				GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_DoIssuanceDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DI", rowNo, "PODEntry_DoIssuanceDate", ScenarioDetailsHashMap), 5);
				GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_DoIssuanceTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DI", rowNo, "PODEntry_DoIssuanceTime", ScenarioDetailsHashMap), 5);
			}

			if(!ExcelUtils.getCellData("Air_POD_DI", rowNo, "PODEntry_PUConfirmationDate", ScenarioDetailsHashMap).equals(""))
			{
				GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_PuConfirmationDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DI", rowNo, "PODEntry_PUConfirmationDate", ScenarioDetailsHashMap), 5);
				GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_PUConfirmationTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DI", rowNo, "PODEntry_PUConfirmationTime", ScenarioDetailsHashMap), 5);
			
			}

			if(!ExcelUtils.getCellData("Air_POD_DI", rowNo, "PODEntry_PODDate", ScenarioDetailsHashMap).equals(""))
			{
				GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_FullPodDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DI", rowNo, "PODEntry_PODDate", ScenarioDetailsHashMap), 5);
				GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_FullPodTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DI", rowNo, "PODEntry_PODTime", ScenarioDetailsHashMap), 5);
			}

			GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_DeliveryRemarks", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DI", rowNo, "POD_Remarks", ScenarioDetailsHashMap), 5);

			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 5);	
			String PODIdSummary=GenericMethods.getInnerText(driver, null, orAirImportPOD.getElement(driver, "Air_PODVerificationMSG", ScenarioDetailsHashMap, 20), 2);
			String PODSuccessMessage=PODIdSummary.split(" : ")[1].split(":")[0];
			String PODHouseID = PODIdSummary.split(":")[2];
			ExcelUtils.setCellData("Air_POD_DI", rowNo, "POD_ID", PODHouseID, ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(PODSuccessMessage, "POD has done for Shipments", "Validating Sea POD Msg", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(PODHouseID,houseID , "Validating House ID in Msg", ScenarioDetailsHashMap);
		}//Ends IF Condition Pavan 17th April 2015
		//Pavan 17th April 2015 Added Below Code if DeliveryType=Consignment Note then HBL Id or MBL Id should not flow into POD Screen
		else if(ExcelUtils.getCellData("Air_POD_DI", rowNo, "DeliveryType", ScenarioDetailsHashMap).equalsIgnoreCase("Consignment Note")){
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
				GenericMethods.replaceTextofTextField(driver, null, orOceanImportPOD.getElement(driver, "PODEntry_DeliveryRemarks", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DI", rowNo, "POD_Remarks", ScenarioDetailsHashMap), 5);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton2", ScenarioDetailsHashMap, 10), 5);	
				String PODIdSummary=GenericMethods.getInnerText(driver, null, orOceanImportPOD.getElement(driver, "VerificationMsg", ScenarioDetailsHashMap, 20), 2);
				String PODSuccessMessage=PODIdSummary.split(" : ")[1].split(":")[0];
				String PODHouseID = PODIdSummary.split(":")[2];
				ExcelUtils.setCellData("Air_POD_DI", rowNo, "POD_ID", PODHouseID, ScenarioDetailsHashMap);
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
	 * This method is to perform Air POD DO Add Functionality.
	 * 
	 * @param driver
	 *            : Instance of WebDriver.
	 * @param rowNo
	 *            : This RowNo contains Records Row number which was given in
	 *            Testdata
	 * @param ScenarioDetailsHashMap
	 *            : Hashmap variable contains Scenario details.
	 * @Modified By: Sangeeta M
	 * @Modified Date: June 10th 2015
	 */

	public static void AirPOD_DO(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int POD_RowID) {

		for (int  rowNo= 1; rowNo <= ExcelUtils.getCellDataRowCount("Air_POD_DO", ScenarioDetailsHashMap); rowNo++) 
		{
			AppReusableMethods.selectMenu(driver, ETransMenu.oceanImportPOD,"POD", ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver, null, orAirImportPOD.getElement(driver, "Air_PODNewBtn", ScenarioDetailsHashMap, 10), 10);

			String houseID=ExcelUtils.getCellData("Air_POD_DO", POD_RowID,"House_Id", ScenarioDetailsHashMap).trim();
			//ExcelUtils.setCellData("Air_POD_DO", rowNo, "House_Id", houseID, ScenarioDetailsHashMap);
			//			AppReusableMethods.selectValueFromLov(driver, orOceanImportPOD.getElement(driver, "houseLOV", ScenarioDetailsHashMap, 10), orOceanImportPOD, "houseReferencePOD",houseID, ScenarioDetailsHashMap );
			GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_HouseNoSearch", ScenarioDetailsHashMap, 10), houseID, 10);
			if(!ExcelUtils.getCellData("Air_POD_DO", rowNo, "DeliveryType", ScenarioDetailsHashMap).equalsIgnoreCase("DeliveryOrder")
					&&
					(!ExcelUtils.getCellData("Air_POD_DO", rowNo, "DeliveryType", ScenarioDetailsHashMap).equals("")
							||!ExcelUtils.getCellData("Air_POD_DO", rowNo, "DeliveryType", ScenarioDetailsHashMap).equals(null))
			)
			{
				GenericMethods.selectOptionFromDropDown(driver, null,orAirImportPOD.getElement(driver, "Air_DeliveryTypeSearch", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("Air_POD_DO", rowNo, "DeliveryType", ScenarioDetailsHashMap));
				GenericMethods.selectOptionFromDropDown(driver, null,orAirImportPOD.getElement(driver, "Air_PodTypeSearch", ScenarioDetailsHashMap, 10),  ExcelUtils.getCellData("Air_POD_DO", rowNo, "PODType", ScenarioDetailsHashMap));
			}

			GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 10);


			GenericMethods.pauseExecution(3000);
			//			String XpathPrefix = "//span[@id='myGrid-content-box-middle']/span[@id='myGrid-rows']/span";
			//String XpathPrefix = "//td[@id='GRID']/div/table/tbody/tr";
			int POD_GridRowCount = driver.findElements(By.xpath("html/body/form/table/tbody/tr[4]/td/div/table/tbody/tr")).size();

			int DOIDSplitCount = ExcelUtils.getCellData("Air_POD_DO", POD_RowID,"DO_ID", ScenarioDetailsHashMap).split(",").length;

			System.out.println("1111");
			System.out.println("2222"+DOIDSplitCount);
			System.out.println("3333"+ExcelUtils.getCellDataRowCount("Air_POD_DO", ScenarioDetailsHashMap));
			//Below if condition will execute when for multiple DO's for one POD.
			if(DOIDSplitCount>ExcelUtils.getCellDataRowCount("Air_POD_DO", ScenarioDetailsHashMap))
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
						System.out.println("doid---"+GenericMethods.getInnerText(driver, By.xpath("html/body/form/table/tbody/tr[4]/td/div/table/tbody/tr["+GridRowID+"]/td[3]"), null, 10));
						System.out.println("TESTDATA_DOID--"+ExcelUtils.getCellData("Air_POD_DO", rowNo,"DO_ID", ScenarioDetailsHashMap).split(",")[DOID].trim());
						if(ExcelUtils.getCellData("Air_POD_DO", rowNo,"DO_ID", ScenarioDetailsHashMap).split(",")[DOID].trim().equalsIgnoreCase(GenericMethods.getInnerText(driver, By.xpath("html/body/form/table/tbody/tr[4]/td/div/table/tbody/tr["+GridRowID+"]/td[3]"), null, 10)))
						{
							GenericMethods.clickElement(driver,By.xpath("html/body/form/table/tbody/tr[4]/td/div/table/tbody/tr["+GridRowID+"]/td[1]/input"), null, 10);
							if(!ExcelUtils.getCellData("Air_POD_DO", rowNo, "PODEntry_DoIssuanceDate", ScenarioDetailsHashMap).equals(""))
							{
								GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_DoIssuanceDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DO", rowNo, "PODEntry_DoIssuanceDate", ScenarioDetailsHashMap), 5);
								GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_DoIssuanceTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DO", rowNo, "PODEntry_DoIssuanceTime", ScenarioDetailsHashMap), 5);
							}

							if(!ExcelUtils.getCellData("Air_POD_DO", rowNo, "PODEntry_PUConfirmationDate", ScenarioDetailsHashMap).equals(""))
							{
								GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_PuConfirmationDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DO", rowNo, "PODEntry_PUConfirmationDate", ScenarioDetailsHashMap), 5);
								GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_PUConfirmationTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DO", rowNo, "PODEntry_PUConfirmationTime", ScenarioDetailsHashMap), 5);
							}

							if(!ExcelUtils.getCellData("Air_POD_DO", rowNo, "PODEntry_PODDate", ScenarioDetailsHashMap).equals(""))
							{
								GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_FullPodDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DO", rowNo, "PODEntry_PODDate", ScenarioDetailsHashMap), 5);
								GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_FullPodTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DO", rowNo, "PODEntry_PODTime", ScenarioDetailsHashMap), 5);
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
					System.out.println("doidApp---"+GenericMethods.getInnerText(driver, By.xpath("html/body/form/table/tbody/tr[4]/td/div/table/tbody/tr["+GridRowID+"]/td[3]"), null, 10));
				System.out.println("TESTDATA_DOID--"+ExcelUtils.getCellData("Air_POD_DO", rowNo,"DO_ID", ScenarioDetailsHashMap).split(",")[actual_DOId].trim());
					if(ExcelUtils.getCellData("Air_POD_DO", rowNo,"DO_ID", ScenarioDetailsHashMap).split(",")[actual_DOId].trim().equalsIgnoreCase(GenericMethods.getInnerText(driver, By.xpath("html/body/form/table/tbody/tr[4]/td/div/table/tbody/tr["+GridRowID+"]/td[3]"), null, 10)))
					{
						//GenericMethods.clickElement(driver,By.xpath(XpathPrefix+"["+GridRowID+"]/td[1]/input"), null, 10);
						GenericMethods.clickElement(driver,By.xpath("html/body/form/table/tbody/tr[4]/td/div/table/tbody/tr["+GridRowID+"]/td[1]/input"), null, 10);
						if(!ExcelUtils.getCellData("Air_POD_DO", rowNo, "PODEntry_DoIssuanceDate", ScenarioDetailsHashMap).equals(""))
						{
							GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_DoIssuanceDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DO", rowNo, "PODEntry_DoIssuanceDate", ScenarioDetailsHashMap), 5);
							GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_DoIssuanceTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DO", rowNo, "PODEntry_DoIssuanceTime", ScenarioDetailsHashMap), 5);
						}

						if(!ExcelUtils.getCellData("Air_POD_DO", rowNo, "PODEntry_PUConfirmationDate", ScenarioDetailsHashMap).equals(""))
						{
							GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_PuConfirmationDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DO", rowNo, "PODEntry_PUConfirmationDate", ScenarioDetailsHashMap), 5);
							GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_PUConfirmationTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DO", rowNo, "PODEntry_PUConfirmationTime", ScenarioDetailsHashMap), 5);
						}

						if(!ExcelUtils.getCellData("Air_POD_DO", rowNo, "PODEntry_PODDate", ScenarioDetailsHashMap).equals(""))
						{
							GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_FullPodDate", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DO", rowNo, "PODEntry_PODDate", ScenarioDetailsHashMap), 5);
							GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_FullPodTime", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DO", rowNo, "PODEntry_PODTime", ScenarioDetailsHashMap), 5);

						}


						break;
					}
				}
			}


			GenericMethods.replaceTextofTextField(driver, null, orAirImportPOD.getElement(driver, "Air_DeliveryRemarks", ScenarioDetailsHashMap, 5),ExcelUtils.getCellData("Air_POD_DO", rowNo, "POD_Remarks", ScenarioDetailsHashMap), 5);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 5);	
			String PODIdSummary=GenericMethods.getInnerText(driver, null, orAirImportPOD.getElement(driver, "Air_PODVerificationMSG", ScenarioDetailsHashMap, 20), 2);
			String PODSuccessMessage=PODIdSummary.split(" : ")[1].split(":")[0];
			String PODHouseID = PODIdSummary.split(":")[2];
			ExcelUtils.setCellData("Air_POD_DO", rowNo, "POD_ID", PODHouseID, ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(PODSuccessMessage, "POD has done for Shipments", "Validating Sea POD Msg", ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(PODHouseID,houseID , "Validating House ID in Msg", ScenarioDetailsHashMap);
		}
	}


}