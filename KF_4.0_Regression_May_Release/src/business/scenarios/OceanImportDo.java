package business.scenarios;

import global.reusables.ExcelUtils;
import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import app.reuseables.AppReusableMethods;
import app.reuseables.ETransMenu;

public class OceanImportDo {
	static ObjectRepository Common=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/CommonObjects.xml");
	static ObjectRepository OrDo=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/SeaDO.xml");
	static int count =1;
	public static void oceanDoFlow(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int datasetRowNo)
	{
		String DOID="";
		for (int rowNo = 1; rowNo <= ExcelUtils.getCellDataRowCount("SI_DO", ScenarioDetailsHashMap); rowNo++) 
		{
			System.out.println("numerber of times executed------"+count);
			AppReusableMethods.selectMenu(driver, ETransMenu.SI_Do, "Sea Delivery Order", ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "AddButton", ScenarioDetailsHashMap, 10), 2);
			ExcelUtils.setCellData("SI_DO", rowNo, "HBL_ID", ExcelUtils.getCellData("SI_ArrivalContainers", datasetRowNo, "HBL_ID", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			String ExpHouseID=ExcelUtils.getCellData("SI_DO", rowNo, "HBL_ID", ScenarioDetailsHashMap);
			System.out.println("exp housedid************"+ExpHouseID);
			String houseId=GenericMethods.locateElement(driver,  By.xpath("//td[text()='"+ExpHouseID+"']"), 10).getAttribute("id");
			String hsId[]=houseId.split("No");
			String chkId="dtIsSelected"+hsId[1];
			GenericMethods.clickElement(driver, By.id(chkId), null, 10);
			GenericMethods.clickElement(driver, By.xpath("html/body/form/table/tbody/tr[4]/td/div/table/tbody/tr["+hsId[1]+"]"), null, 10);
			String DO_DeliveryType = ExcelUtils.getCellData("SI_DO", rowNo, "Delivery_Type", ScenarioDetailsHashMap);
			if(DO_DeliveryType.equalsIgnoreCase("Partial"))
			{
				String LoadType = ExcelUtils.getCellData("SI_DO", rowNo, "LoadType", ScenarioDetailsHashMap);
				if(LoadType.equalsIgnoreCase("LCL")||LoadType.equalsIgnoreCase("Break Bulk")||LoadType.equalsIgnoreCase("RORO"))
				{
					System.out.println("LCL IF");
					GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "CurrentPieces", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", rowNo, "CuurentPieces", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "CurrentWeight", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", rowNo, "CurrentWeight", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "CurrentVolume", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", rowNo, "CurrentVolume", ScenarioDetailsHashMap), 2);
				}
				else
				{
					System.out.println("FCL else");
					String XLContainerDetails = ExcelUtils.getCellData("SI_DO", rowNo, "Container_Details", ScenarioDetailsHashMap);
					int AppContainerRowsize = driver.findElements(By.xpath("html/body/form/table/tbody/tr[3]/td/fieldset/table/tbody/tr[3]/td/table/tbody/tr")).size();
					for(int AppContainerNo=2;AppContainerNo<=AppContainerRowsize;AppContainerNo++)
					{
						GenericMethods.clickElement(driver, By.xpath("html/body/form/table/tbody/tr[3]/td/fieldset/table/tbody/tr[3]/td/table/tbody/tr["+AppContainerNo+"]/td[1]/input"), null, 10);
					}
					System.out.println("XLContainerDetails size"+XLContainerDetails.split(",").length);
					System.out.println("AppContainerRowsize --"+AppContainerRowsize);
					for(int XLContainerNo=0;XLContainerNo<XLContainerDetails.split(",").length;XLContainerNo++)
					{
						for(int AppContainerNo=2;AppContainerNo<=AppContainerRowsize;AppContainerNo++)
						{
							String AppContainer = driver.findElement(By.xpath("html/body/form/table/tbody/tr[3]/td/fieldset/table/tbody/tr[3]/td/table/tbody/tr["+AppContainerNo+"]/td[1]/input")).getAttribute("containerno");
							System.out.println("AppContainer"+AppContainerNo+"--"+AppContainer);
							System.out.println("XLContainerDetails"+XLContainerDetails.split(",")[XLContainerNo]);
							if(XLContainerDetails.split(",")[XLContainerNo].equalsIgnoreCase(AppContainer))
							{
								GenericMethods.clickElement(driver, By.xpath("html/body/form/table/tbody/tr[3]/td/fieldset/table/tbody/tr[3]/td/table/tbody/tr["+AppContainerNo+"]/td[1]/input"), null, 10);
								System.out.println("equal");
								break;
							}
						}

					}
				}
			}

			System.out.println("hsId[1]"+hsId[1]);
			String DeliveryRequired = ExcelUtils.getCellData("SI_DO", rowNo, "DeliveryRequired", ScenarioDetailsHashMap);
			if(DeliveryRequired.equalsIgnoreCase("YES"))
			{
				GenericMethods.selectOptionFromDropDown(driver, null, OrDo.getElement(driver, "DeliveryRequired", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", rowNo, "DeliveryRequired", ScenarioDetailsHashMap));
			}
			GenericMethods.pauseExecution(2000);
			
			
			ExcelUtils.setCellData("SI_DO", rowNo, "MasterId", GenericMethods.getInnerText(driver, By.xpath("//td[text()='"+ExpHouseID+"']/following-sibling::td[5]"), null,2), ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_DO", rowNo, "Customer", GenericMethods.getInnerText(driver, By.xpath("//td[text()='"+ExpHouseID+"']/following-sibling::td[21]"), null,2), ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_DO", rowNo, "Shipper", GenericMethods.getInnerText(driver, By.xpath("//td[text()='"+ExpHouseID+"']/following-sibling::td[23]"), null,2), ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_DO", rowNo, "Consignee", GenericMethods.getInnerText(driver, By.xpath("//td[text()='"+ExpHouseID+"']/following-sibling::td[25]"), null,2), ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_DO", rowNo, "Vessel", GenericMethods.getInnerText(driver, By.xpath("//td[text()='"+ExpHouseID+"']/following-sibling::td[38]"), null,2), ScenarioDetailsHashMap);
			ExcelUtils.setCellData("SI_DO", rowNo, "VoyageNo", GenericMethods.getInnerText(driver, By.xpath("//td[text()='"+ExpHouseID+"']/following-sibling::td[39]"), null,2), ScenarioDetailsHashMap);
			
			GenericMethods.clickElement(driver, null, OrDo.getElement(driver, "DoGridEditBtn", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(4000);
			GenericMethods.clickElement(driver, By.xpath("html/body/form/table/tbody/tr[4]/td/div/table/tbody/tr["+hsId[1]+"]"), null, 10);
			GenericMethods.pauseExecution(4000);
			String DICBROdetailsRequired = ExcelUtils.getCellData("SI_DO", rowNo, "DICBROdetailsRequired", ScenarioDetailsHashMap);
			if(DICBROdetailsRequired.equalsIgnoreCase("YES"))
			{
				oceanDoDICBRO(driver, ScenarioDetailsHashMap, rowNo);
			}
			//oceanDoNote(driver, ScenarioDetailsHashMap, rowNo);
			//oceanDoCustomStatus(driver, ScenarioDetailsHashMap, rowNo);
			//oceanDoeDoc(driver, ScenarioDetailsHashMap, rowNo);
			
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
			
			String doIdsummary=GenericMethods.getInnerText(driver, null, OrDo.getElement(driver, "DoSaveDetail", ScenarioDetailsHashMap, 10), 2);
			String[] DoID=doIdsummary.split("Delivery Order Id");
			ExcelUtils.setCellData("SI_DO", rowNo, "DoId", DoID[1].trim(), ScenarioDetailsHashMap);

			//Keeping DO ID in POD sheet
			
		/*	for(int PODRowcount=1;PODRowcount<=ExcelUtils.getCellDataRowCount("SI_POD_DO", ScenarioDetailsHashMap);PODRowcount++)
			{
				System.out.println("PODRowcount :"+PODRowcount);
				ExcelUtils.setCellData("SI_POD_DO", PODRowcount, "DO_ID", DoID[1].trim(), ScenarioDetailsHashMap);
				System.out.println(ExcelUtils.getCellData("SI_POD_DO", PODRowcount, "DO_ID", ScenarioDetailsHashMap));
				System.out.println("sangeeta typed");
				ExcelUtils.setCellDataKF("SI_POD_DO", PODRowcount, "sangeeta",  DoID[1].trim(), ScenarioDetailsHashMap);
				System.out.println(ExcelUtils.getCellData("SI_POD_DO", PODRowcount, "DO_ID", ScenarioDetailsHashMap));
				
			
			}*/
			DOID = DOID+ DoID[1].trim()+",";
			if(ExcelUtils.getCellData("SI_DO", rowNo, "DeliveryRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				
				System.out.println("DOID=************=="+DOID);
			}
			GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "DoNo", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", rowNo, "DoId", ScenarioDetailsHashMap), 2);
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(2000);
			GenericMethods.assertInnerText(driver, null, OrDo.getElement(driver, "GridDoNo", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", rowNo, "DoId", ScenarioDetailsHashMap), "Grid Do NO", "Validating Do No from the grid", ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(2000);
			count=count+1;
		}
		for(int PODRowcount=1;PODRowcount<=ExcelUtils.getCellDataRowCount("SI_POD_DO", ScenarioDetailsHashMap);PODRowcount++)
		{
			System.out.println("DOID.substring(0,DOID.length()-1)"+DOID.substring(0,DOID.length()-1));
			System.out.println("doid values"+DOID.substring(0,DOID.length()-1));
			  ExcelUtils.setCellData("SI_POD_DO", PODRowcount, "DO_ID", DOID.substring(0,DOID.length()-1), ScenarioDetailsHashMap);
		}
		
	}

	public static void oceanDoDICBRO(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo)
	{
		GenericMethods.clickElement(driver, null, OrDo.getElement(driver, "DICBRO", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "BankRefNo", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", rowNo, "DICBRO_BankRefNo", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "BroReceivedDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", rowNo, "BROReceivedDate", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "BLReceivedDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", rowNo, "BLReceivedDate", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "DICReceivedDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", rowNo, "DICReceivedDate", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "PersonName", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", rowNo, "PersonName", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "HighSeasSalesCustomer", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", rowNo, "HighSeasSalesCustomer", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "HighSeasSalesAgreement", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", rowNo, "HighSeasSalesAgreement", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "DoAddressTo", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", rowNo, "DOAddressTo", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "CHNO", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", rowNo, "CH_No", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "CHName", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", rowNo, "CHName", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "Notes", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", rowNo, "DCBRO_Notes", ScenarioDetailsHashMap), 2);
	}
	public static void oceanDoNote(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo)
	{
		GenericMethods.clickElement(driver, null, OrDo.getElement(driver, "NotesTab", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "NotesText", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", rowNo, "Notes", ScenarioDetailsHashMap), 2);
		GenericMethods.selectOptionFromDropDown(driver, null, OrDo.getElement(driver, "NotesCategory", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", rowNo, "NotesCatagory", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null, OrDo.getElement(driver, "NotesType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", rowNo, "NotesType", ScenarioDetailsHashMap));
		GenericMethods.clickElement(driver, null, OrDo.getElement(driver, "NotegridAddBtn", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(5000);
	}

	public static void oceanDoCustomStatus(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo)
	{
		GenericMethods.clickElement(driver, null, OrDo.getElement(driver, "CustomStatus", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);
	}
	public static void oceanDoeDoc(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo)
	{
		GenericMethods.clickElement(driver, null, OrDo.getElement(driver, "EDOC", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);
	}

	//Sandeep- FUNC48 - Below method is to perform seach functionality in DO screen after entering details for all search fields. 
	public static void DOSearchList_Validations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "DoNo", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "DoId", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "HouseId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "HBL_ID", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "MasterId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "MasterId", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "Search_DateFrom", ScenarioDetailsHashMap, 10), GenericMethods.getDate(-365), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "Search_DateTo", ScenarioDetailsHashMap, 10), GenericMethods.getDate(+365), 2);
		GenericMethods.selectOptionFromDropDown(driver, null, OrDo.getElement(driver, "LoadType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "LoadType", ScenarioDetailsHashMap));
		/*GenericMethods.selectOptionFromDropDown(driver, null, OrDo.getElement(driver, "References", ScenarioDetailsHashMap, 10), "");*/
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "Customer", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "Customer", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "Shipper", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "Shipper", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "Consignee", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "Consignee", ScenarioDetailsHashMap), 2);
		
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "TotalPices", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "CuurentPieces", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "Weight", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "CurrentWeight", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "TotalVolume", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "CurrentVolume", ScenarioDetailsHashMap), 2);
		/*GenericMethods.selectOptionFromDropDown(driver, null, OrDo.getElement(driver, "BankEndorsement", ScenarioDetailsHashMap, 10), "");
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "BankRef", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "ContainerNo", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "CarrierId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "", ScenarioDetailsHashMap), 2);*/
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "Vessel", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "Vessel", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "Voyage", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "VoyageNo", ScenarioDetailsHashMap), 2);
		GenericMethods.selectOptionFromDropDown(driver, null, OrDo.getElement(driver, "DoStatus", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SI_DO", RowNo, "DoStatus", ScenarioDetailsHashMap));
		GenericMethods.clickElement(driver, null, OrDo.getElement(driver, "Search", ScenarioDetailsHashMap, 10), 10);
		
		GenericMethods.pauseExecution(2000);
		GenericMethods.assertInnerText(driver, By.id("dtDoId1"), null, ExcelUtils.getCellData("SI_DO", RowNo,"DoId", ScenarioDetailsHashMap), "DO ID", "Verifying DO ID details in grid.", ScenarioDetailsHashMap);
		
	}
}
