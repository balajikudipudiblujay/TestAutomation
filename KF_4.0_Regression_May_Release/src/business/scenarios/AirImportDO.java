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

public class AirImportDO {
	static ObjectRepository Common=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/CommonObjects.xml");
	static ObjectRepository OrDo=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/AirDO.xml");
	static int count =1;
	public static void airDoFlow(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int datasetRowNo)
	{
		String DOID="";
		for (int rowNo = 1; rowNo <= ExcelUtils.getCellDataRowCount("Air_DO", ScenarioDetailsHashMap); rowNo++) 
		{
			System.out.println("numerber of times executed------"+count);
			AppReusableMethods.selectMenu(driver, ETransMenu.AIR_DO, "Air Delivery Order", ScenarioDetailsHashMap);
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "AddButton", ScenarioDetailsHashMap, 10), 2);
//			ExcelUtils.setCellData("Air_DO", rowNo, "HAWB_ID", ExcelUtils.getCellData("Air_Arrival", datasetRowNo, "HAWB_ID", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			String ExpHouseID=ExcelUtils.getCellData("Air_DO", rowNo, "HAWB_ID", ScenarioDetailsHashMap);
			System.out.println("exp housedid************"+ExpHouseID);
			String houseId=GenericMethods.locateElement(driver,  By.xpath("//td[text()='"+ExpHouseID+"']"), 10).getAttribute("id");
			String hsId[]=houseId.split("No");
			String chkId="dtIsSelected"+hsId[1];
			GenericMethods.clickElement(driver, By.id(chkId), null, 10);
			GenericMethods.clickElement(driver, By.xpath("html/body/form/table/tbody/tr[4]/td/div/table/tbody/tr["+hsId[1]+"]"), null, 10);
			String DO_DeliveryType = ExcelUtils.getCellData("Air_DO", rowNo, "Delivery_Type", ScenarioDetailsHashMap);
			if(DO_DeliveryType.equalsIgnoreCase("Partial"))
			{
				
				GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "CurrentPieces", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "CuurentPieces", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "CurrentWeight", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "CurrentWeight", ScenarioDetailsHashMap), 2);
				GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "CurrentVolume", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "CurrentVolume", ScenarioDetailsHashMap), 2);
		
			}

			System.out.println("hsId[1]"+hsId[1]);
			/*String DeliveryRequired = ExcelUtils.getCellData("Air_DO", rowNo, "DeliveryRequired", ScenarioDetailsHashMap);
			if(DeliveryRequired.equalsIgnoreCase("YES"))
			{
				GenericMethods.selectOptionFromDropDown(driver, null, OrDo.getElement(driver, "DeliveryRequired", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "DeliveryRequired", ScenarioDetailsHashMap));
			}*/
			GenericMethods.selectOptionFromDropDown(driver, null, OrDo.getElement(driver, "DeliveryRequired", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "DeliveryRequired", ScenarioDetailsHashMap));
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null, OrDo.getElement(driver, "DoGridEditBtn", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(4000);
			GenericMethods.clickElement(driver, By.xpath("html/body/form/table/tbody/tr[4]/td/div/table/tbody/tr["+hsId[1]+"]"), null, 10);
			GenericMethods.pauseExecution(4000);
			String DICBROdetailsRequired = ExcelUtils.getCellData("Air_DO", rowNo, "DICBROdetailsRequired", ScenarioDetailsHashMap);
			if(DICBROdetailsRequired.equalsIgnoreCase("YES"))
			{
				airDoDICBRO(driver, ScenarioDetailsHashMap, rowNo);
			}
			airDoNote(driver, ScenarioDetailsHashMap, rowNo);
			airDoCustomStatus(driver, ScenarioDetailsHashMap, rowNo);
			airDoeDoc(driver, ScenarioDetailsHashMap, rowNo);
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
			String doIdsummary=GenericMethods.getInnerText(driver, null, OrDo.getElement(driver, "DoSaveDetail", ScenarioDetailsHashMap, 10), 2);
			String[] DoID=doIdsummary.split("Delivery Order Id");
			ExcelUtils.setCellData("Air_DO", rowNo, "DoId", DoID[1].trim(), ScenarioDetailsHashMap);

			DOID = DOID+ DoID[1].trim()+",";
			System.out.println("DOID=************=="+DOID);
		
			//Keeping DO ID in POD sheet
			if(ExcelUtils.getCellData("Air_DO", rowNo, "DeliveryRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
			{
				//DOID = DOID+ DoID[1].trim()+",";
				//System.out.println("DOID=************=="+DOID);
			}
			GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "DoNo", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "DoId", ScenarioDetailsHashMap), 2);
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(2000);
			GenericMethods.assertInnerText(driver, null, OrDo.getElement(driver, "GridDoNo", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "DoId", ScenarioDetailsHashMap), "Grid Do NO", "Validating Do No from the grid", ScenarioDetailsHashMap);
			GenericMethods.pauseExecution(2000);
			count=count+1;
		}
		for(int PODRowcount=1;PODRowcount<=ExcelUtils.getCellDataRowCount("Air_POD_DO", ScenarioDetailsHashMap);PODRowcount++)
		{
			System.out.println("DOID.substring(0,DOID.length()-1)"+DOID.substring(0,DOID.length()-1));
			System.out.println("doid values"+DOID.substring(0,DOID.length()-1));
			  ExcelUtils.setCellData("Air_POD_DO", PODRowcount, "DO_ID", DOID.substring(0,DOID.length()-1), ScenarioDetailsHashMap);
		}
		
	}

	public static void airDoDICBRO(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo)
	{
		GenericMethods.clickElement(driver, null, OrDo.getElement(driver, "DICBRO", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "BankRefNo", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "DICBRO_BankRefNo", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "BroReceivedDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "BROReceivedDate", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "BLReceivedDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "BLReceivedDate", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "DICReceivedDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "DICReceivedDate", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "PersonName", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "PersonName", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "HighSeasSalesCustomer", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "HighSeasSalesCustomer", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "HighSeasSalesAgreement", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "HighSeasSalesAgreement", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "DoAddressTo", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "DOAddressTo", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "CHNO", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "CH_No", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "CHName", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "CHName", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "Notes", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "DCBRO_Notes", ScenarioDetailsHashMap), 2);
	}
	public static void airDoNote(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo)
	{
		GenericMethods.clickElement(driver, null, OrDo.getElement(driver, "NotesTab", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);
		GenericMethods.replaceTextofTextField(driver, null, OrDo.getElement(driver, "NotesText", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "Notes", ScenarioDetailsHashMap), 2);
		GenericMethods.selectOptionFromDropDown(driver, null, OrDo.getElement(driver, "NotesCategory", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "NotesCatagory", ScenarioDetailsHashMap));
		GenericMethods.selectOptionFromDropDown(driver, null, OrDo.getElement(driver, "NotesType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Air_DO", rowNo, "NotesType", ScenarioDetailsHashMap));
		GenericMethods.clickElement(driver, null, OrDo.getElement(driver, "NotegridAddBtn", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(5000);
	}

	public static void airDoCustomStatus(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo)
	{
		GenericMethods.clickElement(driver, null, OrDo.getElement(driver, "CustomStatus", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);
	}
	public static void airDoeDoc(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo)
	{
		GenericMethods.clickElement(driver, null, OrDo.getElement(driver, "EDOC", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);
	}
}
