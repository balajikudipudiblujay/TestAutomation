package business.scenarios;

import global.reusables.ExcelUtils;
import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import app.reuseables.AppReusableMethods;
import app.reuseables.ETransMenu;

public class DecisionTable_Configuration {
	static ObjectRepository orCommon = new ObjectRepository(
			System.getProperty("user.dir")
			+ "/ObjectRepository/CommonObjects.xml");
	static ObjectRepository orDecisionTable = new ObjectRepository(
			System.getProperty("user.dir")
			+ "/ObjectRepository/DecisionTable.xml");


	public static void SetupForPARTY_ID_MODE_DecisionTable(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		//AppReusableMethods.switchOriginTerminalSea(driver, ScenarioDetailsHashMap, RowNo);
		AppReusableMethods.selectMenu(driver, ETransMenu.DecisionTable, "Decision Table Maintenance", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DecisionCode_SearchList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DecisionTable_PartyMode", RowNo, "DecisionCode_SearchList_PARTY_ID_MODE", ScenarioDetailsHashMap), 5);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Search", ScenarioDetailsHashMap, 10), 2);

		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "TableEntries_Tab", ScenarioDetailsHashMap, 10), 2);
		int rowsize = driver.findElements(By.xpath("//*[starts-with(@id,'wrappedentityTable-cell-0') and contains(@class,'aw-item-box')]")).size();

		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_SearchList", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("DecisionTable_PartyMode", RowNo, "DT_PartyID_PartyIdMode", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_CompanyId_SearchList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DecisionTable_PartyMode", RowNo, "DT_PartyID_CompanyId", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Search", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);


		if(driver.findElements(By.xpath("//*[@id='wrappedentityTable-row-0']")).size()>0)
		{
			System.out.println(" record present");
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "EntryDetails_Tab", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DecisionTable_PartyMode", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_PartyIdMode_MSG", "Validating PartyId Mode success MSG",  ScenarioDetailsHashMap);
		}
		else
		{
			System.out.println("record not present");
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "AddButton1", ScenarioDetailsHashMap, 10), 2);
			
			GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_PartyID_CompanyID_New", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("DecisionTable_PartyMode", RowNo, "DT_PartyID_CompanyId", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_PartyID_PartyIDMode_New", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("DecisionTable_PartyMode", RowNo, "DT_PartyID_PartyIdMode", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_PartyID_BranchCode_New", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("DecisionTable_PartyMode", RowNo, "DT_PartyID_BranchCode", ScenarioDetailsHashMap), 2);
			
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				System.out.println("no Alerts present");
			}
			GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DecisionTable_PartyMode", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_PartyIdMode_MSG", "Validating PartyId Mode success MSG",  ScenarioDetailsHashMap);
		}


	}

	//Sandeep- Below method is to perform setup for Pick up Instruction Details.
	public static void SetupForPUOR_NAME_CHANGE_DecisionTable(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		//AppReusableMethods.switchOriginTerminalSea(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.pauseExecution(3000);
		AppReusableMethods.selectMenu(driver, ETransMenu.DecisionTable, "Decision Table Maintenance", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DecisionCode_SearchList", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("DecisionTable_PUOR", RowNo, "DecisionCode_SearchList_PUOR_NAME", ScenarioDetailsHashMap), 5);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Search", ScenarioDetailsHashMap, 10), 2);

		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "TableEntries_Tab", ScenarioDetailsHashMap, 10), 2);

		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_PUOR_CompanyId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DecisionTable_PUOR", RowNo, "DT_CompanyId_SearchList", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Search", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);

		int rowSize = driver.findElements(By.xpath("//*[@id='wrappedentityTable-row-0']")).size();
		System.out.println("Row size :"+rowSize );

		if(rowSize>0)
		{
			if(!GenericMethods.getInnerText(driver, By.xpath("//span[@id='wrappedentityTable-content-box-middle']/span[1]/span[2]/span[2]/span[1]/span[2]"), null, 10).trim().equalsIgnoreCase(ExcelUtils.getCellData("DecisionTable_PUOR", RowNo, "Pickup_Order_Name_Change", ScenarioDetailsHashMap).trim()))
			{
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "EntryDetails_Tab", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.clickElement(driver, By.xpath("//td[text()='Pickup Order Name Change']/following-sibling::td[1]/input[2]"), null, 2);
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.pauseExecution(2000);
				GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DecisionTable_PUOR", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_PartyIdMode_MSG", "==> Validating Pickup Order Name Change creation_MSG",  ScenarioDetailsHashMap);
			}
		}
		else
		{
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "AddButton1", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.replaceTextofTextField(driver, By.xpath("//td[text()='Company Id']/following-sibling::td[1]/input[1]"), null, ExcelUtils.getCellData("DecisionTable_PUOR", RowNo, "DT_CompanyId_SearchList", ScenarioDetailsHashMap), 2);
			if(ExcelUtils.getCellData("DecisionTable_PUOR", RowNo, "Pickup_Order_Name_Change", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
			{
				GenericMethods.clickElement(driver, By.xpath("//td[text()='Pickup Order Name Change']/following-sibling::td[1]/input[2]"), null, 2);
			}
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(2000);
			GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DecisionTable_PUOR", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_PartyIdMode_MSG", "==> Validating Pickup Order Name Change creation_MSG",  ScenarioDetailsHashMap);
		}
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchList_Tab", ScenarioDetailsHashMap, 10), 2);
	}

	public static void SetupFor_NEW_PRS_ADD_DecisionTable(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		AppReusableMethods.switchOriginTerminalSea(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.pauseExecution(3000);
		AppReusableMethods.selectMenu(driver, ETransMenu.DecisionTable, "Decision Table Maintenance", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DecisionCode_SearchList", ScenarioDetailsHashMap, 10), 
				ExcelUtils.getCellData("Decision_Table", RowNo, "DecisionCode_SearchList_NEWPRSADD", ScenarioDetailsHashMap), 5);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Search", ScenarioDetailsHashMap, 10), 2);

		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "TableEntries_Tab", ScenarioDetailsHashMap, 10), 2);
		int rowsize = driver.findElements(By.xpath("//*[starts-with(@id,'wrappedentityTable-cell-0') and contains(@class,'aw-item-box')]")).size();
		System.out.println("Row size :"+driver.findElements(By.xpath("//*[starts-with(@id,'wrappedentityTable-cell-0') and contains(@class,'aw-item-box')]")).size() );

		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_CompanyId_SearchList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Decision_Table", RowNo, "DT_CompanyId_SearchList", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Search", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);


		if(driver.findElements(By.xpath("//*[@id='wrappedentityTable-row-0']")).size()>0){
			System.out.println(" RECORD ALREADY PRESENT ");


			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "EntryDetails_Tab", ScenarioDetailsHashMap, 10), 2);

			if(driver.findElement(By.xpath(".//*[@id='tabBody']/table/tbody/tr[1]/td[4]/input[2]")).isSelected())
			{
				System.out.println("b4 :"+driver.findElement(By.xpath(".//*[@id='tabBody']/table/tbody/tr[1]/td[4]/input[2]")).isSelected());

				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Decision_Table", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_PartyIdMode_MSG", "==> Validating Pickup Order Name Change creation_MSG",  ScenarioDetailsHashMap);

			}
			else
			{
				driver.findElement(By.xpath(".//*[@id='tabBody']/table/tbody/tr[1]/td[4]/input[2]")).click();
				System.out.println("else a4 :"+driver.findElement(By.xpath(".//*[@id='tabBody']/table/tbody/tr[1]/td[4]/input[2]")).isSelected());
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.pauseExecution(2000);
				GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Decision_Table", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_PartyIdMode_MSG", "==> Validating Pickup Order Name Change creation_MSG",  ScenarioDetailsHashMap);
			}

		}
		else{
			System.out.println("RECORD NOT PRESENT ");
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "AddButton1", ScenarioDetailsHashMap, 10), 2);
			driver.findElement(By.id("commandObjectdataObjectdecisionTables0decisionTableEntrys"+rowsize+"inputValue1")).sendKeys(ExcelUtils.getCellData("Decision_Table", RowNo, "DT_CompanyId_SearchList", ScenarioDetailsHashMap));
			driver.findElement(By.id("dataObject.decisionTables[0].decisionTableEntrys["+rowsize+"].resultValue1")).click();
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Decision_Table", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_PartyIdMode_MSG", "==> Validating Pickup Order Name Change creation_MSG",  ScenarioDetailsHashMap);
		}
	}

	public static void SetupForEDI_INTERFACE_DecisionTable(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) 
	{
		AppReusableMethods.switchOriginTerminalSea(driver, ScenarioDetailsHashMap, RowNo);
		AppReusableMethods.selectMenu(driver, ETransMenu.DecisionTable, "Decision Table Maintenance", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DecisionCode_SearchList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Decision_Table", RowNo, "DecisionCode_SearchList_EDIINTERFACE", ScenarioDetailsHashMap), 5);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Search", ScenarioDetailsHashMap, 10), 2);

		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "TableEntries_Tab", ScenarioDetailsHashMap, 10), 2);

		int rowsize1 = driver.findElements(By.xpath("//*[starts-with(@id,'wrappedentityTable-cell-0') and contains(@class,'aw-item-box')]")).size();
		System.out.println("rowsize1 :"+rowsize1);

		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_INTERFACE_SearchList", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Decision_Table", RowNo, "DT_INTERFACE_SearchList1", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_CompanyId_SearchList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Decision_Table", RowNo, "DT_CompanyId_SearchList", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Search", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);


		if(driver.findElements(By.xpath("//*[@id='wrappedentityTable-row-0']")).size()>0){
			System.out.println(" record present");
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "EntryDetails_Tab", ScenarioDetailsHashMap, 10), 2);
			//GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "TableEntries_Tab", 10), 2);

			if(driver.findElement(By.xpath(".//*[@id='tabBody']/table/tbody/tr[1]/td[4]/input[2]")).isSelected())
			{
				System.out.println("if :"+driver.findElement(By.xpath(".//*[@id='tabBody']/table/tbody/tr[1]/td[4]/input[2]")).isSelected());

				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Decision_Table", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_PartyIdMode_MSG", "==> Validating Pickup Order Name Change creation_MSG",  ScenarioDetailsHashMap);

			}
			else
			{
				System.out.println("else :"+driver.findElement(By.xpath(".//*[@id='tabBody']/table/tbody/tr[1]/td[4]/input[2]")).isSelected());
				GenericMethods.clickElement(driver, null, orDecisionTable.getElement(driver, "DT_CheckBox_New", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Decision_Table", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_PartyIdMode_MSG", "Validating PartyId Mode success MSG",  ScenarioDetailsHashMap);

			}



		}
		else{
			System.out.println("record not present");
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "AddButton1", ScenarioDetailsHashMap, 10), 2);
			driver.findElement(By.id("commandObjectdataObjectdecisionTables0decisionTableEntrys"+rowsize1+"inputValue1")).sendKeys(ExcelUtils.getCellData("Decision_Table", RowNo, "DT_CompanyId_SearchList", ScenarioDetailsHashMap));
			driver.findElement(By.id("dataObjectdecisionTables0decisionTableEntrys"+rowsize1+"codeInput3code")).sendKeys(ExcelUtils.getCellData("Decision_Table", RowNo, "INTERFACE1", ScenarioDetailsHashMap));
			driver.findElement(By.id("commandObjectdataObjectdecisionTables0decisionTableEntrys"+rowsize1+"inputValue4")).sendKeys(ExcelUtils.getCellData("Decision_Table", RowNo, "INTTRA_SENDER_ID", ScenarioDetailsHashMap));
			driver.findElement(By.id("dataObject.decisionTables[0].decisionTableEntrys["+rowsize1+"].resultValue1")).click();
			driver.findElement(By.id("dataObject.decisionTables[0].decisionTableEntrys["+rowsize1+"].resultValue2")).click();

			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				System.out.println("no Alerts present");
			}
			GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Decision_Table", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_PartyIdMode_MSG", "Validating PartyId Mode success MSG",  ScenarioDetailsHashMap);
		}

		//
		int rowsize2 = driver.findElements(By.xpath("//*[starts-with(@id,'wrappedentityTable-cell-0') and contains(@class,'aw-item-box')]")).size();
		System.out.println("rowsize2 :"+rowsize2);

		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_INTERFACE_SearchList", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Decision_Table", RowNo, "DT_INTERFACE_SearchList2", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_CompanyId_SearchList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Decision_Table", RowNo, "DT_CompanyId_SearchList", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Search", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);


		if(driver.findElements(By.xpath("//*[@id='wrappedentityTable-row-0']")).size()>0){
			System.out.println(" record present");
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "EntryDetails_Tab", ScenarioDetailsHashMap, 10), 2);

			if(driver.findElement(By.xpath(".//*[@id='tabBody']/table/tbody/tr[1]/td[4]/input[2]")).isSelected())
			{
				System.out.println("if :"+driver.findElement(By.xpath(".//*[@id='tabBody']/table/tbody/tr[1]/td[4]/input[2]")).isSelected());

				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Decision_Table", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_PartyIdMode_MSG", "==> Validating Pickup Order Name Change creation_MSG",  ScenarioDetailsHashMap);

			}
			else
			{
				System.out.println("else :"+driver.findElement(By.xpath(".//*[@id='tabBody']/table/tbody/tr[1]/td[4]/input[2]")).isSelected());
				GenericMethods.clickElement(driver, null, orDecisionTable.getElement(driver, "DT_CheckBox_New", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Decision_Table", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_PartyIdMode_MSG", "Validating PartyId Mode success MSG",  ScenarioDetailsHashMap);
			}
		}
		else{
			System.out.println("record not present");
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "AddButton1", ScenarioDetailsHashMap, 10), 2);
			driver.findElement(By.id("commandObjectdataObjectdecisionTables0decisionTableEntrys"+rowsize2+"inputValue1")).sendKeys(ExcelUtils.getCellData("Decision_Table", RowNo, "DT_CompanyId_SearchList", ScenarioDetailsHashMap));
			driver.findElement(By.id("dataObjectdecisionTables0decisionTableEntrys"+rowsize2+"codeInput3code")).sendKeys(ExcelUtils.getCellData("Decision_Table", RowNo, "INTERFACE2", ScenarioDetailsHashMap));
			//driver.findElement(By.id("commandObjectdataObjectdecisionTables0decisionTableEntrys"+rowsize2+"inputValue4")).sendKeys(ExcelUtils.getCellData("Decision_Table", RowNo, "INTERFACE2", ScenarioDetailsHashMap));
			driver.findElement(By.id("dataObject.decisionTables[0].decisionTableEntrys["+rowsize2+"].resultValue1")).click();
			driver.findElement(By.id("dataObject.decisionTables[0].decisionTableEntrys["+rowsize2+"].resultValue2")).click();

			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				System.out.println("no Alerts present");
			}
			GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Decision_Table", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_PartyIdMode_MSG", "Validating PartyId Mode success MSG",  ScenarioDetailsHashMap);
		}

		//
		int rowsize3 = driver.findElements(By.xpath("//*[starts-with(@id,'wrappedentityTable-cell-0') and contains(@class,'aw-item-box')]")).size();
		System.out.println("rowsize3 :"+rowsize3);

		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_INTERFACE_SearchList", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("Decision_Table", RowNo, "DT_INTERFACE_SearchList3", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_CompanyId_SearchList", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Decision_Table", RowNo, "DT_CompanyId_SearchList", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Search", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);


		if(driver.findElements(By.xpath("//*[@id='wrappedentityTable-row-0']")).size()>0){
			System.out.println(" record present");
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "EntryDetails_Tab", ScenarioDetailsHashMap, 10), 2);

			if(driver.findElement(By.xpath(".//*[@id='tabBody']/table/tbody/tr[1]/td[4]/input[2]")).isSelected())
			{
				System.out.println("if :"+driver.findElement(By.xpath(".//*[@id='tabBody']/table/tbody/tr[1]/td[4]/input[2]")).isSelected());

				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Decision_Table", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_PartyIdMode_MSG", "==> Validating Pickup Order Name Change creation_MSG",  ScenarioDetailsHashMap);

			}
			else
			{
				System.out.println("else :"+driver.findElement(By.xpath(".//*[@id='tabBody']/table/tbody/tr[1]/td[4]/input[2]")).isSelected());
				GenericMethods.clickElement(driver, null, orDecisionTable.getElement(driver, "DT_CheckBox_New", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Decision_Table", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_PartyIdMode_MSG", "Validating PartyId Mode success MSG",  ScenarioDetailsHashMap);
			}
		}
		else{
			System.out.println("record not present");
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "AddButton1", ScenarioDetailsHashMap, 10), 2);
			driver.findElement(By.id("commandObjectdataObjectdecisionTables0decisionTableEntrys"+rowsize3+"inputValue1")).sendKeys(ExcelUtils.getCellData("Decision_Table", RowNo, "DT_CompanyId_SearchList", ScenarioDetailsHashMap));
			driver.findElement(By.id("dataObjectdecisionTables0decisionTableEntrys"+rowsize3+"codeInput3code")).sendKeys(ExcelUtils.getCellData("Decision_Table", RowNo, "INTERFACE3", ScenarioDetailsHashMap));
			//driver.findElement(By.id("commandObjectdataObjectdecisionTables0decisionTableEntrys"+rowsize3+"inputValue4")).sendKeys(ExcelUtils.getCellData("Decision_Table", RowNo, "INTERFACE3", ScenarioDetailsHashMap));
			driver.findElement(By.id("dataObject.decisionTables[0].decisionTableEntrys["+rowsize3+"].resultValue1")).click();
			driver.findElement(By.id("dataObject.decisionTables[0].decisionTableEntrys["+rowsize3+"].resultValue2")).click();

			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
			try{
				GenericMethods.handleAlert(driver, "accept");

			}catch (Exception e) {
				System.out.println("no Alerts present");
			}
			GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Decision_Table", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_PartyIdMode_MSG", "Validating PartyId Mode success MSG",  ScenarioDetailsHashMap);
		}

	}

	//Sandeep- Below method is to perform Known Shipper funcationality setup
	public static void SetupFor_KnownShipper(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo)
	{
		GenericMethods.pauseExecution(3000);
		AppReusableMethods.selectMenu(driver, ETransMenu.DecisionTable, "Decision Table Maintenance", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DecisionCode_SearchList", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("DecisionTable_KnownShipper", RowNo, "DecisionCode_KnownShipper_NAME", ScenarioDetailsHashMap), 5);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Search", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "TableEntries_Tab", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_KnownShipper_Company_New", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DecisionTable_KnownShipper", RowNo, "Company", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_KnownShipper_Branch_New", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DecisionTable_KnownShipper", RowNo, "BRANCH", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Search", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);

		int rowSize = driver.findElements(By.xpath("//span[@id='wrappedentityTable-content-box-middle']/span[1]/span")).size();
		System.out.println("Row size :"+rowSize );

		Boolean RecordAvailability = false;
		if(rowSize>2)
		{
			for (int RowID = 2; RowID < rowSize; RowID++) 
			{
				//Below if condition will be executed when the record in the grid and testdata is same 
				if(
						GenericMethods.getInnerText(driver, By.xpath("//span[@id='wrappedentityTable-content-box-middle']/span[1]/span["+RowID+"]/span[1]/span[1]/span[2]"), null, 10).trim().equalsIgnoreCase(ExcelUtils.getCellData("DecisionTable_KnownShipper", RowNo, "Company", ScenarioDetailsHashMap).trim())
						&&GenericMethods.getInnerText(driver, By.xpath("//span[@id='wrappedentityTable-content-box-middle']/span[1]/span["+RowID+"]/span[2]/span[1]/span[2]"), null, 10).trim().equalsIgnoreCase(ExcelUtils.getCellData("DecisionTable_KnownShipper", RowNo, "BRANCH", ScenarioDetailsHashMap).trim())
						&& GenericMethods.getInnerText(driver, By.xpath("//span[@id='wrappedentityTable-content-box-middle']/span[1]/span["+RowID+"]/span[3]/span[1]/span[2]"), null, 10).trim().equalsIgnoreCase(ExcelUtils.getCellData("DecisionTable_KnownShipper", RowNo, "KNOWN_SHIPPER_ADVANCE_LEVEL", ScenarioDetailsHashMap).trim())
				)
				{
					System.out.println("Record is avaible");
					RecordAvailability = true;
					break;
				}

				if(
						GenericMethods.getInnerText(driver, By.xpath("//span[@id='wrappedentityTable-content-box-middle']/span[1]/span["+RowID+"]/span[1]/span[1]/span[2]"), null, 10).trim().equalsIgnoreCase(ExcelUtils.getCellData("DecisionTable_KnownShipper", RowNo, "Company", ScenarioDetailsHashMap).trim())
						&& GenericMethods.getInnerText(driver, By.xpath("//span[@id='wrappedentityTable-content-box-middle']/span[1]/span["+RowID+"]/span[2]/span[1]/span[2]"), null, 10).trim().equalsIgnoreCase(ExcelUtils.getCellData("DecisionTable_KnownShipper", RowNo, "BRANCH", ScenarioDetailsHashMap).trim())
						&& !GenericMethods.getInnerText(driver, By.xpath("//span[@id='wrappedentityTable-content-box-middle']/span[1]/span["+RowID+"]/span[3]/span[1]/span[2]"), null, 10).trim().equalsIgnoreCase(ExcelUtils.getCellData("DecisionTable_KnownShipper", RowNo, "KNOWN_SHIPPER_ADVANCE_LEVEL", ScenarioDetailsHashMap).trim())
				)
				{
					System.out.println("record is available but flag is no");
					GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "EntryDetails_Tab", ScenarioDetailsHashMap, 10), 2);
					GenericMethods.clickElement(driver, null, orDecisionTable.getElement(driver, "DT_KnownShipper_KnownShipperAdvanceLevel_Add", ScenarioDetailsHashMap, 10), 2);
					GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
					GenericMethods.pauseExecution(2000);
					GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DecisionTable_KnownShipper", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_ Known Shipper_MSG", "==> Validating Known Shipper Name Change creation_MSG",  ScenarioDetailsHashMap);
					RecordAvailability = true;
					break;
				}
			}

		}
		if(!RecordAvailability)
		{
			System.out.println("record is not avaible");
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "AddButton1", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_KnownShipper_Company_Add", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DecisionTable_KnownShipper", RowNo, "Company", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_KnownShipper_Branch_Add", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DecisionTable_KnownShipper", RowNo, "BRANCH", ScenarioDetailsHashMap), 2);
			
			if(ExcelUtils.getCellData("DecisionTable_KnownShipper", RowNo, "KNOWN_SHIPPER_ADVANCE_LEVEL", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
			{
				GenericMethods.clickElement(driver, null, orDecisionTable.getElement(driver, "DT_KnownShipper_KnownShipperAdvanceLevel_Add", ScenarioDetailsHashMap, 10), 2);
			}
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(2000);
			GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DecisionTable_KnownShipper", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_PartyIdMode_MSG", "==> Validating Pickup Order Name Change creation_MSG",  ScenarioDetailsHashMap);
		}
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchList_Tab", ScenarioDetailsHashMap, 10), 2);
	}	
	
	//Sandeep- Below method is to perform Warehouse Integration
	public static void Warehouse_Integration(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo)
	{
		GenericMethods.pauseExecution(3000);
		AppReusableMethods.selectMenu(driver, ETransMenu.DecisionTable, "Decision Table Maintenance", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DecisionCode_SearchList", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "DecisionCode_WarehouseIntegration", ScenarioDetailsHashMap), 5);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Search", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "TableEntries_Tab", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_WarehouseIntegration_CompanyCode", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "COMPANY_CODE", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_WarehouseIntegration_BranchCode", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "BRANCH_CODE", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Search", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);

		int rowSize = driver.findElements(By.xpath("//span[@id='wrappedentityTable-content-box-middle']/span[1]/span")).size();
		System.out.println("Row size :"+rowSize );
		Boolean RecordAvailability = false;
		if(rowSize>2)
		{
			for (int RowID = 2; RowID < rowSize; RowID++) 
			{
				//Below if condition will be executed when the record in the grid and testdata is same 
				if(
						GenericMethods.getInnerText(driver, By.xpath("//span[@id='wrappedentityTable-content-box-middle']/span[1]/span["+RowID+"]/span[1]/span[1]/span[2]"), null, 10).trim().equalsIgnoreCase(ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "COMPANY_CODE", ScenarioDetailsHashMap).trim())
						&&GenericMethods.getInnerText(driver, By.xpath("//span[@id='wrappedentityTable-content-box-middle']/span[1]/span["+RowID+"]/span[2]/span[1]/span[2]"), null, 10).trim().equalsIgnoreCase(ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "BRANCH_CODE", ScenarioDetailsHashMap).trim())
				)
				{
					System.out.println("Record is avaible");
					RecordAvailability = true;
					GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "EntryDetails_Tab", ScenarioDetailsHashMap, 10), 2);
					
					System.out.println("value of clprint-----"+GenericMethods.isChecked(GenericMethods.locateElement(driver, By.xpath("//td[text()='*CI_AND_PL_PRINT']/following-sibling::td[1]/input[2]"), 10)));
					System.out.println("value of show print--"+GenericMethods.isChecked(GenericMethods.locateElement(driver, By.xpath("//td[text()='*CI_AND_PL_PRINT']/ancestor::tr[1]/following-sibling::tr[2]/td[4]/input[2]"), 10)));
					
					GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_WarehouseIntegration_WAREHOUSE_APPLICATION_Add", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "WAREHOUSE_APPLICATION", ScenarioDetailsHashMap), 2);
					
					if(ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "CI_AND_PL_PRINT", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
					{
						if(!GenericMethods.isChecked(GenericMethods.locateElement(driver, By.xpath("//td[text()='*CI_AND_PL_PRINT']/following-sibling::td[1]/input[2]"), 10)))												
						{
							GenericMethods.clickElement(driver, null, orDecisionTable.getElement(driver, "DT_WarehouseIntegration_CiAndPlPrint_Add", ScenarioDetailsHashMap, 10), 2);
						}
					}
					else
					{
						if(GenericMethods.isChecked(GenericMethods.locateElement(driver, By.xpath("//td[text()='*CI_AND_PL_PRINT']/following-sibling::td[1]/input[2]"), 10)))												
						{
							GenericMethods.clickElement(driver, null, orDecisionTable.getElement(driver, "DT_WarehouseIntegration_CiAndPlPrint_Add", ScenarioDetailsHashMap, 10), 2);
						}

					}
					
					GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_WarehouseIntegration_AmtDtlExpDfltText_Add", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "AMT_DTL_EXP_DFLT_TEXT", ScenarioDetailsHashMap), 2);
					
					if(ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "SHOW_PRINT_BANK_DTLS", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
					{
						if(!GenericMethods.isChecked(GenericMethods.locateElement(driver, By.xpath("//td[text()='*CI_AND_PL_PRINT']/ancestor::tr[1]/following-sibling::tr[2]/td[4]/input[2]"), 10)))												
						{
							GenericMethods.clickElement(driver, null, orDecisionTable.getElement(driver, "DT_WarehouseIntegration_ShowPrint_Bank_Dtls_Add", ScenarioDetailsHashMap, 10), 2);
						}
					}
					else
					{
						if(GenericMethods.isChecked(GenericMethods.locateElement(driver, By.xpath("//td[text()='*CI_AND_PL_PRINT']/ancestor::tr[1]/following-sibling::tr[2]/td[4]/input[2]"), 10)))												
						{
							GenericMethods.clickElement(driver, null, orDecisionTable.getElement(driver, "DT_WarehouseIntegration_ShowPrint_Bank_Dtls_Add", ScenarioDetailsHashMap, 10), 2);
						}

					}
					
					GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_WarehouseIntegration_AmtDtlImpDfltText_Add", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "AMT_DTL_IMP_DFLT_TEXT", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_WarehouseIntegration_DeclRmrkExpDfltText_Add", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "DECL_RMRK_EXP_DFLT_TEXT", ScenarioDetailsHashMap), 2);
					GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_WarehouseIntegration_DeclRmrkImpDfltText_Add", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "DECL_RMRK_IMP_DFLT_TEXT", ScenarioDetailsHashMap), 2);
					
					GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
					GenericMethods.pauseExecution(2000);
					GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DecisionTable_KnownShipper", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_PartyIdMode_MSG", "==> Validating Pickup Order Name Change creation_MSG",  ScenarioDetailsHashMap);
					
					break;
				}
			}

		}
		if(!RecordAvailability)
		{
			System.out.println("record is not avaible");
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "AddButton1", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(3000);
			GenericMethods.selectFrame(driver, By.name("text"), null, 10);
			GenericMethods.selectFrame(driver, By.name("ESMenuMainPageiFrame"), null, 10);
			GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_WarehouseIntegration_CompanyCode_New", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "COMPANY_CODE", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_WarehouseIntegration_BranchCode_New", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "BRANCH_CODE", ScenarioDetailsHashMap), 2);
			if(ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "CI_AND_PL_PRINT", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
			{
				GenericMethods.clickElement(driver, null, orDecisionTable.getElement(driver, "DT_WarehouseIntegration_CiAndPlPrint_Add", ScenarioDetailsHashMap, 10), 2);
			}
			
			GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_WarehouseIntegration_WAREHOUSE_APPLICATION_Add", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "WAREHOUSE_APPLICATION", ScenarioDetailsHashMap), 2);
			
			if(ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "SHOW_PRINT_BANK_DTLS", ScenarioDetailsHashMap).equalsIgnoreCase("yes"))
			{
				GenericMethods.clickElement(driver, null, orDecisionTable.getElement(driver, "DT_WarehouseIntegration_ShowPrint_Bank_Dtls_Add", ScenarioDetailsHashMap, 10), 2);
			}
			
			GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_WarehouseIntegration_AmtDtlExpDfltText_Add", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "AMT_DTL_EXP_DFLT_TEXT", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_WarehouseIntegration_AmtDtlImpDfltText_Add", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "AMT_DTL_IMP_DFLT_TEXT", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_WarehouseIntegration_DeclRmrkExpDfltText_Add", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "DECL_RMRK_EXP_DFLT_TEXT", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_WarehouseIntegration_DeclRmrkImpDfltText_Add", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_WarehouseIntegration", RowNo, "DECL_RMRK_IMP_DFLT_TEXT", ScenarioDetailsHashMap), 2);
			
			
			
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(2000);
			GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DecisionTable_KnownShipper", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_PartyIdMode_MSG", "==> Validating Pickup Order Name Change creation_MSG",  ScenarioDetailsHashMap);
		}
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchList_Tab", ScenarioDetailsHashMap, 10), 2);
	
	}

	//Sandeep- Below method is to perform PARTY_ID_MODE setup
	public static void PARTY_ID_MODE(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo)
	{
		GenericMethods.pauseExecution(3000);
		AppReusableMethods.selectMenu(driver, ETransMenu.DecisionTable, "Decision Table Maintenance", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DecisionCode_SearchList", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("DT_PartyIDMode", RowNo, "DecisionCode_PartyID", ScenarioDetailsHashMap), 5);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Search", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "TableEntries_Tab", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_CompanyID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_PartyIDMode", RowNo, "Company_Id", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_BranchId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_PartyIDMode", RowNo, "Branch_Code", ScenarioDetailsHashMap), 2);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Search", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(2000);


		int rowSize = driver.findElements(By.xpath("//span[@id='wrappedentityTable-content-box-middle']/span[1]/span")).size();
		System.out.println("Row size :"+rowSize );

		Boolean RecordAvailability = false;
		if(rowSize>2)
		{
			for (int RowID = 2; RowID < rowSize; RowID++) 
			{
				//Below if condition will be executed when the record in the grid and testdata is same 
				if(
						GenericMethods.getInnerText(driver, By.xpath("//span[@id='wrappedentityTable-content-box-middle']/span[1]/span["+RowID+"]/span[1]/span[1]/span[2]"), null, 10).trim().equalsIgnoreCase(ExcelUtils.getCellData("DT_PartyIDMode", RowNo, "Company_Id", ScenarioDetailsHashMap).trim())
						&&GenericMethods.getInnerText(driver, By.xpath("//span[@id='wrappedentityTable-content-box-middle']/span[1]/span["+RowID+"]/span[2]/span[1]/span[2]"), null, 10).trim().equalsIgnoreCase(ExcelUtils.getCellData("DT_PartyIDMode", RowNo, "Branch_Code", ScenarioDetailsHashMap).trim())
						&& GenericMethods.getInnerText(driver, By.xpath("//span[@id='wrappedentityTable-content-box-middle']/span[1]/span["+RowID+"]/span[3]/span[1]/span[2]"), null, 10).trim().equalsIgnoreCase(ExcelUtils.getCellData("DT_PartyIDMode", RowNo, "Party_Id_Mode", ScenarioDetailsHashMap).trim())
				)
				{
					System.out.println("Record is avaible");
					RecordAvailability = true;
					break;
				}

				if(
						GenericMethods.getInnerText(driver, By.xpath("//span[@id='wrappedentityTable-content-box-middle']/span[1]/span["+RowID+"]/span[1]/span[1]/span[2]"), null, 10).trim().equalsIgnoreCase(ExcelUtils.getCellData("DT_PartyIDMode", RowNo, "Company_Id", ScenarioDetailsHashMap).trim())
						&& GenericMethods.getInnerText(driver, By.xpath("//span[@id='wrappedentityTable-content-box-middle']/span[1]/span["+RowID+"]/span[2]/span[1]/span[2]"), null, 10).trim().equalsIgnoreCase(ExcelUtils.getCellData("DT_PartyIDMode", RowNo, "Branch_Code", ScenarioDetailsHashMap).trim())
						&& !GenericMethods.getInnerText(driver, By.xpath("//span[@id='wrappedentityTable-content-box-middle']/span[1]/span["+RowID+"]/span[3]/span[1]/span[2]"), null, 10).trim().equalsIgnoreCase(ExcelUtils.getCellData("DT_PartyIDMode", RowNo, "Party_Id_Mode", ScenarioDetailsHashMap).trim())
				)
				{
					System.out.println("record is available but party id  is not proper");
					GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "EntryDetails_Tab", ScenarioDetailsHashMap, 10), 2);
					GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_PartyId_PartyIdMode_New", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_PartyIDMode", RowNo, "Party_Id_Mode", ScenarioDetailsHashMap), 2);
					GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
					GenericMethods.pauseExecution(3000);
					try{
						GenericMethods.handleAlert(driver, "accept");
					}catch (Exception e){}
					GenericMethods.pauseExecution(3000);
					try{
						GenericMethods.handleAlert(driver, "accept");
					}catch (Exception e){}
					GenericMethods.pauseExecution(3000);
					try{
						GenericMethods.handleAlert(driver, "accept");
					}catch (Exception e){}
					GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_PartyIDMode", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_ Known Shipper_MSG", "==> Validating Known Shipper Name Change creation_MSG",  ScenarioDetailsHashMap);
					RecordAvailability = true;
					break;
				}
			}

		}
		if(!RecordAvailability)
		{
			System.out.println("record is not avaible");
			GenericMethods.pauseExecution(2000);
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "AddButton1", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_PartyId_CompanyId_New", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_PartyIDMode", RowNo, "Company_Id", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_PartyId_PartyIdMode_New", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_PartyIDMode", RowNo, "Party_Id_Mode", ScenarioDetailsHashMap), 2);
			GenericMethods.replaceTextofTextField(driver, null, orDecisionTable.getElement(driver, "DT_PartyId_BranchCode_New", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_PartyIDMode", RowNo, "Branch_Code", ScenarioDetailsHashMap), 2);
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "DT_Save", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(2000);
			GenericMethods.assertInnerText(driver, null, orDecisionTable.getElement(driver, "DT_PartyIdMode_MSG", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("DT_PartyIDMode", RowNo, "DT_Sucess_MSG", ScenarioDetailsHashMap),"DT_PartyIdMode_MSG", "==> Validating Decision PartyIdMode MSG  creation_MSG",  ScenarioDetailsHashMap);
		}
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "SearchList_Tab", ScenarioDetailsHashMap, 10), 2);
		
		
		
	}


}
