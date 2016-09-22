package business.scenarios;
import global.reusables.ExcelUtils;
import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import app.reuseables.AppReusableMethods;
import app.reuseables.ETransMenu;
public class OceanExportsWareHouse 
{
	static ObjectRepository Common=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/CommonObjects.xml");
	static ObjectRepository orOceanExpWareHouse=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/OceanExportWarehouse.xml");
	public static void pictOceanExportsWareHouse_Add(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int rowNo )
	{
		System.out.println("in OceanExportsWareHouse Add");
		AppReusableMethods.selectMenu(driver, ETransMenu.SeaExport_WareHouse, "Warehouse", ScenarioDetailsHashMap);
		GenericMethods.pauseExecution(1000);
		GenericMethods.clickElement(driver, null,Common.getElement(driver, "AddButton", ScenarioDetailsHashMap, 10),2);
		String WareHouseBasis=ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseBasis", ScenarioDetailsHashMap);
		String PRSType = ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "PRSType", ScenarioDetailsHashMap);
		if(WareHouseBasis.equalsIgnoreCase("Booking"))
		{
			ExcelUtils.setCellData("SE_WarehouseMainDtls", rowNo, "BookingId", ExcelUtils.getCellData("SE_BookingMainDetails", rowNo, "BookingId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpWareHouse.getElement(driver, "WareHouseBasis_SearchType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseBasis", ScenarioDetailsHashMap));
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpWareHouse.getElement(driver, "WareHouse_SearchID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "BookingId", ScenarioDetailsHashMap), 2);
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(3000);
			int GridRowcount = driver.findElements(By.xpath("html/body/form/table/tbody/tr[5]/td/div/table/tbody/tr")).size();
			for (int GridRowNo = 1; GridRowNo <= GridRowcount; GridRowNo++) {
				String GridCommonRefernce = GenericMethods.getInnerText(driver, By.id("dtCommonReference"+GridRowNo), null, 2); 
				if(!GridCommonRefernce.equalsIgnoreCase(ExcelUtils.getCellData("SE_BookingMainDetails", rowNo, "ReferenceId", ScenarioDetailsHashMap)))
				{
					System.out.println("App dtCommonReference RowNo"+"dtCommonReference"+GridRowNo);
					GenericMethods.clickElement(driver, By.id("dtCommonReference"+GridRowNo), null, 2);
					GenericMethods.pauseExecution(2000);
					GenericMethods.clickElement(driver, null, orOceanExpWareHouse.getElement(driver, "GridDeleteIcon", ScenarioDetailsHashMap, 10), 2);
					GenericMethods.pauseExecution(2000);
				}
			}
		}
		else if(WareHouseBasis.equalsIgnoreCase("HBL"))
		{
			ExcelUtils.setCellData("SE_WarehouseMainDtls", rowNo, "HBLID", ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "HBLId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpWareHouse.getElement(driver, "WareHouseBasis_SearchType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseBasis", ScenarioDetailsHashMap));
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpWareHouse.getElement(driver, "WareHouse_SearchID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "HBLID", ScenarioDetailsHashMap), 2);
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(3000);
			int GridRowcount = driver.findElements(By.xpath("html/body/form/table/tbody/tr[5]/td/div/table/tbody/tr")).size();
			for (int GridRowNo = 1; GridRowNo <= GridRowcount; GridRowNo++) {
				String GridCommonRefernce = GenericMethods.getInnerText(driver, By.id("dtCommonReference"+GridRowNo), null, 2); 
				if(!GridCommonRefernce.equalsIgnoreCase(ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "HBLCommonReferenceNo", ScenarioDetailsHashMap)))
				{
					System.out.println("App dtCommonReference RowNo"+"dtCommonReference"+GridRowNo);
					GenericMethods.clickElement(driver, By.id("dtCommonReference"+GridRowNo), null, 2);
					GenericMethods.pauseExecution(2000);
					GenericMethods.clickElement(driver, null, orOceanExpWareHouse.getElement(driver, "GridDeleteIcon", ScenarioDetailsHashMap, 10), 2);
					GenericMethods.pauseExecution(2000);
				}
			}
		}

		else if(WareHouseBasis.equalsIgnoreCase("PRS")){
			if(PRSType.equalsIgnoreCase("PRSFromBooking"))
			{
				ExcelUtils.setCellData("SE_WarehouseMainDtls", rowNo, "PRSID", ExcelUtils.getCellData("SE_PRS_Booking", rowNo, "PRS_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpWareHouse.getElement(driver, "WareHouseBasis_SearchType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseBasis", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null, orOceanExpWareHouse.getElement(driver, "WareHouse_SearchID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "PRSID", ScenarioDetailsHashMap), 2);
				GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.pauseExecution(3000);
				int GridRowcount = driver.findElements(By.xpath("html/body/form/table/tbody/tr[5]/td/div/table/tbody/tr")).size();
				for (int GridRowNo = 1; GridRowNo <= GridRowcount; GridRowNo++) {
					String GridCommonRefernce = GenericMethods.getInnerText(driver, By.id("dtCommonReference"+GridRowNo), null, 2); 
					if(!GridCommonRefernce.equalsIgnoreCase(ExcelUtils.getCellData("SE_BookingMainDetails", rowNo, "ReferenceId", ScenarioDetailsHashMap)))
					{
						System.out.println("App dtCommonReference RowNo"+"dtCommonReference"+GridRowNo);
						GenericMethods.clickElement(driver, By.id("dtCommonReference"+GridRowNo), null, 2);
						GenericMethods.pauseExecution(2000);
						GenericMethods.clickElement(driver, null, orOceanExpWareHouse.getElement(driver, "GridDeleteIcon", ScenarioDetailsHashMap, 10), 2);
						GenericMethods.pauseExecution(2000);
					}
				}
			}
			else if(PRSType.equalsIgnoreCase("PRSFromHBL"))
			{
				ExcelUtils.setCellData("SE_WarehouseMainDtls", rowNo, "PRSID", ExcelUtils.getCellData("SE_PRS_HBL", rowNo, "PRS_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpWareHouse.getElement(driver, "WareHouseBasis_SearchType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseBasis", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null, orOceanExpWareHouse.getElement(driver, "WareHouse_SearchID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "PRSID", ScenarioDetailsHashMap), 2);
				GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.pauseExecution(3000);
				int GridRowcount = driver.findElements(By.xpath("html/body/form/table/tbody/tr[5]/td/div/table/tbody/tr")).size();
				for (int GridRowNo = 1; GridRowNo <= GridRowcount; GridRowNo++) {
					String GridCommonRefernce = GenericMethods.getInnerText(driver, By.id("dtCommonReference"+GridRowNo), null, 2); 
					if(!GridCommonRefernce.equalsIgnoreCase(ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "HBLCommonReferenceNo", ScenarioDetailsHashMap)))
					{
						System.out.println("App dtCommonReference RowNo"+"dtCommonReference"+GridRowNo);
						GenericMethods.clickElement(driver, By.id("dtCommonReference"+GridRowNo), null, 2);
						GenericMethods.pauseExecution(2000);
						GenericMethods.clickElement(driver, null, orOceanExpWareHouse.getElement(driver, "GridDeleteIcon", ScenarioDetailsHashMap, 10), 2);
						GenericMethods.pauseExecution(2000);
					}
				}

			}
			else if(PRSType.equalsIgnoreCase("PRSFromMBL"))
			{
				//ExcelUtils.setCellData("SE_WarehouseMainDtls", rowNo, "PRSID", ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PRS_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				for (int WRHRowNo = 1; WRHRowNo <= ExcelUtils.getSubScenarioRowCount("SE_WarehouseMainDtls", ScenarioDetailsHashMap); WRHRowNo++) 
				{
					ExcelUtils.setCellData_Without_DataSet("SE_WarehouseMainDtls", rowNo, "PRSID", ExcelUtils.getCellData("SE_PRS_OBL", rowNo, "PRS_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					ExcelUtils.setCellData_Without_DataSet("SE_WarehouseMainDtls", WRHRowNo, "MBLID", ExcelUtils.getCellData("SE_OBL", rowNo, "OBL_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					ExcelUtils.setCellData_Without_DataSet("SE_WarehouseMainDtls",WRHRowNo, "HBLID", ExcelUtils.getCellDataWithoutDataSet("SE_HBLMainDetails", WRHRowNo, "HBLId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
					ExcelUtils.setCellData_Without_DataSet("SE_WarehouseMainDtls", WRHRowNo, "HBLCommonReferenceNo", ExcelUtils.getCellDataWithoutDataSet("SE_HBLMainDetails", WRHRowNo, "HBLCommonReferenceNo", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				}
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpWareHouse.getElement(driver, "WareHouseBasis_SearchType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseBasis", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null, orOceanExpWareHouse.getElement(driver, "WareHouse_SearchID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "PRSID", ScenarioDetailsHashMap), 2);
				GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.pauseExecution(3000);
				// Note:- keep in SE_WarehouseMainDtls sheet those many rows, how many HBLs are present in shipment with same dataset no how it is availble in HBLMainDetails sheet
				//System.out.println("HBLCommonReferenceNo"+ExcelUtils.getAllCellValuesOfColumn("SE_WarehouseMainDtls", "HBLCommonReferenceNo", ScenarioDetailsHashMap));
				ArrayList<String> HBLCommonReferenceNos = ExcelUtils.getAllCellValuesOfColumn("SE_WarehouseMainDtls", "HBLCommonReferenceNo", ScenarioDetailsHashMap);
				int GridTotalRowcount = driver.findElements(By.xpath("html/body/form/table/tbody/tr[5]/td/div/table/tbody/tr")).size();
				System.out.println("GridTotalRowcount "+GridTotalRowcount);
				String flag="False";
				for (int GridRowcnt = 1; GridRowcnt <= GridTotalRowcount; GridRowcnt++) 
				{
					for (int HouseRowcnt = 0; HouseRowcnt < HBLCommonReferenceNos.size(); HouseRowcnt++) {
						String GridCommonRefernce = GenericMethods.getInnerText(driver, By.id("dtCommonReference"+GridRowcnt), null, 2); 
						//System.out.println("Actual GridCommonRefernce"+GridCommonRefernce);
						//System.out.println("Expected GridCommonRefernce"+HBLCommonReferenceNos.get(HouseRowcnt));
						if(HBLCommonReferenceNos.get(HouseRowcnt).equalsIgnoreCase(GridCommonRefernce))
						{
							ArrayList<String> WRHRequired = ExcelUtils.getAllCellValuesOfColumn("SE_WarehouseMainDtls", "WarehouseRequired", ScenarioDetailsHashMap);
							System.out.println("WRHRequired"+WRHRequired);
							if(WRHRequired.get(HouseRowcnt).equalsIgnoreCase("Yes"))
							{
								flag="true";
								break;
							}
						}
					}
					if(flag.equalsIgnoreCase("false")){
						GenericMethods.clickElement(driver, By.id("dtCommonReference"+GridRowcnt), null, 2);
						GenericMethods.pauseExecution(2000);
						GenericMethods.clickElement(driver, null, orOceanExpWareHouse.getElement(driver, "GridDeleteIcon", ScenarioDetailsHashMap, 10), 2);
						GenericMethods.pauseExecution(2000);
					}
					flag="false";
				}
			}
			else if(PRSType.equalsIgnoreCase("DRS"))
			{
				ExcelUtils.setCellData("SE_WarehouseMainDtls", rowNo, "PRSID", ExcelUtils.getCellData("SE_DRS_HBL", rowNo, "PRS_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpWareHouse.getElement(driver, "WareHouseBasis_SearchType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseBasis", ScenarioDetailsHashMap));
				GenericMethods.replaceTextofTextField(driver, null, orOceanExpWareHouse.getElement(driver, "WareHouse_SearchID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "PRSID", ScenarioDetailsHashMap), 2);
				GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
				GenericMethods.pauseExecution(3000);
				int GridRowcount = driver.findElements(By.xpath("html/body/form/table/tbody/tr[5]/td/div/table/tbody/tr")).size();
				for (int GridRowNo = 1; GridRowNo <= GridRowcount; GridRowNo++) {
					String GridCommonRefernce = GenericMethods.getInnerText(driver, By.id("dtCommonReference"+GridRowNo), null, 2); 
					if(!GridCommonRefernce.equalsIgnoreCase(ExcelUtils.getCellData("SE_HBLMainDetails", rowNo, "HBLCommonReferenceNo", ScenarioDetailsHashMap)))
					{
						System.out.println("App dtCommonReference RowNo"+"dtCommonReference"+GridRowNo);
						GenericMethods.clickElement(driver, By.id("dtCommonReference"+GridRowNo), null, 2);
						GenericMethods.pauseExecution(2000);
						GenericMethods.clickElement(driver, null, orOceanExpWareHouse.getElement(driver, "GridDeleteIcon", ScenarioDetailsHashMap, 10), 2);
						GenericMethods.pauseExecution(2000);
					}
				}
			}
			
		}
		else if(WareHouseBasis.equalsIgnoreCase("MBL"))
		{
			for (int WRHRowNo = 1; WRHRowNo <= ExcelUtils.getSubScenarioRowCount("SE_WarehouseMainDtls", ScenarioDetailsHashMap); WRHRowNo++) 
			{
				ExcelUtils.setCellData_Without_DataSet("SE_WarehouseMainDtls", WRHRowNo, "MBLID", ExcelUtils.getCellData("SE_OBL", rowNo, "OBL_Id", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				ExcelUtils.setCellData_Without_DataSet("SE_WarehouseMainDtls",WRHRowNo, "HBLID", ExcelUtils.getCellDataWithoutDataSet("SE_HBLMainDetails", WRHRowNo, "HBLId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
				ExcelUtils.setCellData_Without_DataSet("SE_WarehouseMainDtls", WRHRowNo, "HBLCommonReferenceNo", ExcelUtils.getCellDataWithoutDataSet("SE_HBLMainDetails", WRHRowNo, "HBLCommonReferenceNo", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
			}
			GenericMethods.selectOptionFromDropDown(driver, null, orOceanExpWareHouse.getElement(driver, "WareHouseBasis_SearchType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseBasis", ScenarioDetailsHashMap));
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpWareHouse.getElement(driver, "WareHouse_SearchID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "MBLID", ScenarioDetailsHashMap), 2);
			GenericMethods.clickElement(driver, null, Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
			GenericMethods.pauseExecution(3000);
			// Note:- keep in SE_WarehouseMainDtls sheet those many rows, how many HBLs are present in shipment with same dataset no how it is availble in HBLMainDetails sheet
			//System.out.println("HBLCommonReferenceNo"+ExcelUtils.getAllCellValuesOfColumn("SE_WarehouseMainDtls", "HBLCommonReferenceNo", ScenarioDetailsHashMap));
			ArrayList<String> HBLCommonReferenceNos = ExcelUtils.getAllCellValuesOfColumn("SE_WarehouseMainDtls", "HBLCommonReferenceNo", ScenarioDetailsHashMap);
			int GridTotalRowcount = driver.findElements(By.xpath("html/body/form/table/tbody/tr[5]/td/div/table/tbody/tr")).size();
			System.out.println("GridTotalRowcount "+GridTotalRowcount);
			String flag="False";
			for (int GridRowcnt = 1; GridRowcnt <= GridTotalRowcount; GridRowcnt++) 
			{

				for (int HouseRowcnt = 0; HouseRowcnt < HBLCommonReferenceNos.size(); HouseRowcnt++) {
					String GridCommonRefernce = GenericMethods.getInnerText(driver, By.id("dtCommonReference"+GridRowcnt), null, 2); 
					//System.out.println("Actual GridCommonRefernce"+GridCommonRefernce);
					//System.out.println("Expected GridCommonRefernce"+HBLCommonReferenceNos.get(HouseRowcnt));
					if(HBLCommonReferenceNos.get(HouseRowcnt).equalsIgnoreCase(GridCommonRefernce))
					{
						ArrayList<String> WRHRequired = ExcelUtils.getAllCellValuesOfColumn("SE_WarehouseMainDtls", "WarehouseRequired", ScenarioDetailsHashMap);
						System.out.println("WRHRequired"+WRHRequired);
						if(WRHRequired.get(HouseRowcnt).equalsIgnoreCase("Yes"))
						{
							flag="true";
							break;
						}
					}
				}
				if(flag.equalsIgnoreCase("false")){
					GenericMethods.clickElement(driver, By.id("dtCommonReference"+GridRowcnt), null, 2);
					GenericMethods.pauseExecution(2000);
					GenericMethods.clickElement(driver, null, orOceanExpWareHouse.getElement(driver, "GridDeleteIcon", ScenarioDetailsHashMap, 10), 2);
					GenericMethods.pauseExecution(2000);
				}
				flag="false";
			}
		}
		AppReusableMethods.selectValueFromLov(driver, orOceanExpWareHouse.getElement(driver, "WarehouseLOV_Img", ScenarioDetailsHashMap, 10), orOceanExpWareHouse, "Warehouse_PartyId",ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "Warehouse", ScenarioDetailsHashMap),  ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpWareHouse.getElement(driver, "WHReceivedDate", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WHReceiveDate", ScenarioDetailsHashMap), 2);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpWareHouse.getElement(driver, "WareHouseRemarks", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "Remarks", ScenarioDetailsHashMap), 2);

		//Date Validation start:Author Sangeeta
		//FUNC062.13-Date Validation(Warehouse Receive Date)
		if(ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "DateValidationRequired", ScenarioDetailsHashMap).equalsIgnoreCase("Yes"))
		{
			String ActualWarehouseReceiveDate =  driver.findElement(By.id("receivedDate")).getAttribute("value");
			String ExpectedWarehouseReceiveDate = ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "DocumentRequiredDate", ScenarioDetailsHashMap);
			try {
				GenericMethods.compareDatesByCompareTo("dd-MM-yyyy", ActualWarehouseReceiveDate, ExpectedWarehouseReceiveDate, "Less", "Warehouse Receive Date is Less than document req. date", "Warehouse Receive Date is not Less than document req. date", ScenarioDetailsHashMap);
			} catch (ParseException e) {
				e.printStackTrace();
			}	 
		}
		//Date Validation end

		GenericMethods.clickElement(driver, null, orOceanExpWareHouse.getElement(driver, "Warehouse_CargoDetailsTab", ScenarioDetailsHashMap, 20), 20);
		GenericMethods.pauseExecution(2000);
		GenericMethods.clickElement(driver, null, orOceanExpWareHouse.getElement(driver, "Warehouse_EDOCTab", ScenarioDetailsHashMap, 20), 20);
		GenericMethods.pauseExecution(2000);
		GenericMethods.clickElement(driver, null, Common.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 30), 10);
		GenericMethods.pauseExecution(5000);
		//"IKFOWH001: Warehouse Details Saved Successfully for Shipments:BOOKINGMANUAL00752"
		String OnSaveWareHouseText = GenericMethods.getInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "Warehouse_SuccessMSG", ScenarioDetailsHashMap, 10), 10);
		System.out.println("OnSaveWareHouseText :"+OnSaveWareHouseText);
		String[] WareHouseTextSplit  =OnSaveWareHouseText.split(":");
		String WareHouseMSG = null;
		String BookingID=null;
		String HBLID=null;
		for (int i = 0; i < WareHouseTextSplit.length; i++) {
			if(WareHouseTextSplit[i].trim().contains("Warehouse Details Saved"))
			{
				WareHouseMSG = WareHouseTextSplit[i].trim();
				System.out.println("WareHouseMSG :"+WareHouseMSG);
			}
		}
		GenericMethods.pauseExecution(3000);

		if(WareHouseBasis.equalsIgnoreCase("Booking"))
		{
			BookingID = WareHouseTextSplit[2].trim();
			System.out.println("BookingID :"+BookingID);
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpWareHouse.getElement(driver, "WarehouseShipmentIDSearch", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "BookingId", ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
			GenericMethods.pauseExecution(6000);
			GenericMethods.assertInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridShipmentID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "BookingId",ScenarioDetailsHashMap), "Booking Id", "Validating Booking ID in Warehouse page",  ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(WareHouseMSG, ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseMsg", ScenarioDetailsHashMap), "Validating Warehouse Creation Message.", ScenarioDetailsHashMap);
			String WarehouseID = GenericMethods.getInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridWarehouseID", ScenarioDetailsHashMap, 10), 2);
			ExcelUtils.setCellData("SE_WarehouseMainDtls", rowNo, "WarehouseId", WarehouseID, ScenarioDetailsHashMap);
			GenericMethods.assertInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridWarehouseID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseId",ScenarioDetailsHashMap), "WareHouse Id", "Validating WareHouse ID in Warehouse Page",  ScenarioDetailsHashMap);
		}
		else if(WareHouseBasis.equalsIgnoreCase("HBL"))
		{
			HBLID = WareHouseTextSplit[2].trim();
			System.out.println("HBL ID :"+HBLID);
			GenericMethods.replaceTextofTextField(driver, null, orOceanExpWareHouse.getElement(driver, "WarehouseShipmentIDSearch", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "HBLID", ScenarioDetailsHashMap), 10);
			GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
			GenericMethods.pauseExecution(6000);
			GenericMethods.assertInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridShipmentID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "HBLId",ScenarioDetailsHashMap), "HBLID", "Validating HBL ID in Warehouse page",  ScenarioDetailsHashMap);
			GenericMethods.assertTwoValues(WareHouseMSG, ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseMsg", ScenarioDetailsHashMap), "Validating Warehouse Creation Message.", ScenarioDetailsHashMap);
			String WarehouseID = GenericMethods.getInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridWarehouseID", ScenarioDetailsHashMap, 10), 2);
			ExcelUtils.setCellData("SE_WarehouseMainDtls", rowNo, "WarehouseId", WarehouseID, ScenarioDetailsHashMap);
			GenericMethods.assertInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridWarehouseID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseId",ScenarioDetailsHashMap), "WareHouse Id", "Validating WareHouse ID in Warehouse Page",  ScenarioDetailsHashMap);
		}
		else if(WareHouseBasis.equalsIgnoreCase("PRS"))
		{
			if(PRSType.equalsIgnoreCase("PRSFromBooking"))
			{
				String PRSBookingID = WareHouseTextSplit[2].trim();
				System.out.println("PRSBookingID :"+PRSBookingID);
				ExcelUtils.setCellData("SE_WarehouseMainDtls", rowNo, "PRSID_FromBooking", PRSBookingID, ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, null, orOceanExpWareHouse.getElement(driver, "WarehouseShipmentIDSearch", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "PRSID_FromBooking", ScenarioDetailsHashMap), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
				GenericMethods.pauseExecution(6000);
				GenericMethods.assertInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridShipmentID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "PRSID_FromBooking",ScenarioDetailsHashMap), "Booking ID", "Validating PRSID in Warehouse page",  ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(WareHouseMSG, ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseMsg", ScenarioDetailsHashMap), "Validating Warehouse Creation Message.", ScenarioDetailsHashMap);
				String WarehouseID = GenericMethods.getInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridWarehouseID", ScenarioDetailsHashMap, 10), 2);
				ExcelUtils.setCellData("SE_WarehouseMainDtls", rowNo, "WarehouseId", WarehouseID, ScenarioDetailsHashMap);
				GenericMethods.assertInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridWarehouseID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseId",ScenarioDetailsHashMap), "WareHouse Id", "Validating WareHouse ID in Warehouse Page",  ScenarioDetailsHashMap);
			}

			else if(PRSType.equalsIgnoreCase("PRSFromHBL"))
			{
				String PRSHBLID = WareHouseTextSplit[2].trim();
				System.out.println("PRSHBLID :"+PRSHBLID);
				ExcelUtils.setCellData("SE_WarehouseMainDtls", rowNo, "PRSID_FromHBL", PRSHBLID, ScenarioDetailsHashMap);
				GenericMethods.replaceTextofTextField(driver, null, orOceanExpWareHouse.getElement(driver, "WarehouseShipmentIDSearch", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "PRSID_FromHBL", ScenarioDetailsHashMap), 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
				GenericMethods.pauseExecution(6000);
				GenericMethods.assertInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridShipmentID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "PRSID_FromHBL",ScenarioDetailsHashMap), "HBL ID", "Validating PRS ID in Warehouse page",  ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(WareHouseMSG, ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseMsg", ScenarioDetailsHashMap), "Validating Warehouse Creation Message.", ScenarioDetailsHashMap);
				String WarehouseID = GenericMethods.getInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridWarehouseID", ScenarioDetailsHashMap, 10), 2);
				ExcelUtils.setCellData("SE_WarehouseMainDtls", rowNo, "WarehouseId", WarehouseID, ScenarioDetailsHashMap);
				GenericMethods.assertInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridWarehouseID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseId",ScenarioDetailsHashMap), "WareHouse Id", "Validating WareHouse ID in Warehouse Page",  ScenarioDetailsHashMap);
			}
			
			else if(PRSType.equalsIgnoreCase("PRSFromMBL"))
			{
				String PRSMBLID = WareHouseTextSplit[2].trim();
				System.out.println("PRSMBLID :"+PRSMBLID);
				ArrayList<String> WRHRequired = ExcelUtils.getAllCellValuesOfColumn("SE_WarehouseMainDtls", "WarehouseRequired", ScenarioDetailsHashMap);
				List<String> WRHRequiredYes =new ArrayList<String>();
				if(WRHRequired.size()>1)
				{
					String[] ShipmentId = (OnSaveWareHouseText.split(":")[2]).split(",");
					System.out.println(ShipmentId.length);
					String WarehouseID=null;
					String originalDataSt=ScenarioDetailsHashMap.get("DataSetNo");
					for (int XLrowno = 0; XLrowno < WRHRequired.size(); XLrowno++)
					{ 
						String slno= null;
						if(WRHRequired.get(XLrowno).equalsIgnoreCase("yes")){
							ScenarioDetailsHashMap.put("DataSetNo", String.valueOf(XLrowno+1));
							slno =ExcelUtils.getCellDataWithoutDataSet("SE_WarehouseMainDtls",  XLrowno+1, "sn", ScenarioDetailsHashMap);
							WRHRequiredYes.add(slno);
							System.out.println("slno"+slno);
						}
					}
					ScenarioDetailsHashMap.put("DataSetNo",originalDataSt);
					for (int i = 0; i < WRHRequiredYes.size(); i++) {
						System.out.println("WRHRequiredYes"+WRHRequiredYes.get(i));
						GenericMethods.replaceTextofTextField(driver, null, orOceanExpWareHouse.getElement(driver, "WarehouseShipmentIDSearch", ScenarioDetailsHashMap, 10),ShipmentId[i], 10);
						GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
						GenericMethods.pauseExecution(6000);
						GenericMethods.assertInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridShipmentID", ScenarioDetailsHashMap, 10), ShipmentId[i], "HBLID", "Validating HBL ID in Warehouse page",  ScenarioDetailsHashMap);
						GenericMethods.assertTwoValues(WareHouseMSG, ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseMsg", ScenarioDetailsHashMap), "Validating Warehouse Creation Message.", ScenarioDetailsHashMap);
						WarehouseID = GenericMethods.getInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridWarehouseID", ScenarioDetailsHashMap, 10), 2);
						ExcelUtils.setCellDataKF("SE_WarehouseMainDtls",Integer.parseInt(WRHRequiredYes.get(i)), "WarehouseId",WarehouseID, ScenarioDetailsHashMap);
					}
				}
				else
				{
					GenericMethods.replaceTextofTextField(driver, null, orOceanExpWareHouse.getElement(driver, "WarehouseShipmentIDSearch", ScenarioDetailsHashMap, 10),PRSMBLID, 10);
					GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
					GenericMethods.pauseExecution(6000);
					GenericMethods.assertInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridShipmentID", ScenarioDetailsHashMap, 10), PRSMBLID, "HBLID", "Validating HBL ID in Warehouse page",  ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(WareHouseMSG, ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseMsg", ScenarioDetailsHashMap), "Validating Warehouse Creation Message.", ScenarioDetailsHashMap);
					String WarehouseID = GenericMethods.getInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridWarehouseID", ScenarioDetailsHashMap, 10), 2);
					ExcelUtils.setCellData("SE_WarehouseMainDtls", rowNo, "WarehouseId", WarehouseID, ScenarioDetailsHashMap);
					GenericMethods.assertInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridWarehouseID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseId",ScenarioDetailsHashMap), "WareHouse Id", "Validating WareHouse ID in Warehouse Page",  ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(WareHouseMSG, ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseMsg", ScenarioDetailsHashMap), "Validating Warehouse Creation Message.", ScenarioDetailsHashMap);
				}
			}
			else if(PRSType.equalsIgnoreCase("DRS"))
			{
				String PRSHBLID = WareHouseTextSplit[2].trim();
				System.out.println("PRSHBLID :"+PRSHBLID);
				ArrayList<String> WRHRequired = ExcelUtils.getAllCellValuesOfColumn("SE_WarehouseMainDtls", "WarehouseRequired", ScenarioDetailsHashMap);
				List<String> WRHRequiredYes =new ArrayList<String>();
				if(WRHRequired.size()>1)
				{
					String[] ShipmentId = (OnSaveWareHouseText.split(":")[2]).split(",");
					System.out.println(ShipmentId.length);
					String WarehouseID=null;
					String originalDataSt=ScenarioDetailsHashMap.get("DataSetNo");
					for (int XLrowno = 0; XLrowno < WRHRequired.size(); XLrowno++)
					{ 
						String slno= null;
						if(WRHRequired.get(XLrowno).equalsIgnoreCase("yes")){
							ScenarioDetailsHashMap.put("DataSetNo", String.valueOf(XLrowno+1));
							slno =ExcelUtils.getCellDataWithoutDataSet("SE_WarehouseMainDtls",  XLrowno+1, "sn", ScenarioDetailsHashMap);
							WRHRequiredYes.add(slno);
							System.out.println("slno"+slno);
						}
					}
					ScenarioDetailsHashMap.put("DataSetNo",originalDataSt);
					for (int i = 0; i < WRHRequiredYes.size(); i++) {
						System.out.println("WRHRequiredYes"+WRHRequiredYes.get(i));
						GenericMethods.replaceTextofTextField(driver, null, orOceanExpWareHouse.getElement(driver, "WarehouseShipmentIDSearch", ScenarioDetailsHashMap, 10),ShipmentId[i], 10);
						GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
						GenericMethods.pauseExecution(6000);
						GenericMethods.assertInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridShipmentID", ScenarioDetailsHashMap, 10), ShipmentId[i], "HBLID", "Validating HBL ID in Warehouse page",  ScenarioDetailsHashMap);
						GenericMethods.assertTwoValues(WareHouseMSG, ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseMsg", ScenarioDetailsHashMap), "Validating Warehouse Creation Message.", ScenarioDetailsHashMap);
						WarehouseID = GenericMethods.getInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridWarehouseID", ScenarioDetailsHashMap, 10), 2);
						ExcelUtils.setCellDataKF("SE_WarehouseMainDtls",Integer.parseInt(WRHRequiredYes.get(i)), "WarehouseId",WarehouseID, ScenarioDetailsHashMap);
					}
				}
				else
				{
					GenericMethods.replaceTextofTextField(driver, null, orOceanExpWareHouse.getElement(driver, "WarehouseShipmentIDSearch", ScenarioDetailsHashMap, 10),PRSHBLID, 10);
					GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
					GenericMethods.pauseExecution(6000);
					GenericMethods.assertInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridShipmentID", ScenarioDetailsHashMap, 10), PRSHBLID, "HBLID", "Validating HBL ID in Warehouse page",  ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(WareHouseMSG, ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseMsg", ScenarioDetailsHashMap), "Validating Warehouse Creation Message.", ScenarioDetailsHashMap);
					String WarehouseID = GenericMethods.getInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridWarehouseID", ScenarioDetailsHashMap, 10), 2);
					ExcelUtils.setCellData("SE_WarehouseMainDtls", rowNo, "WarehouseId", WarehouseID, ScenarioDetailsHashMap);
					GenericMethods.assertInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridWarehouseID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseId",ScenarioDetailsHashMap), "WareHouse Id", "Validating WareHouse ID in Warehouse Page",  ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(WareHouseMSG, ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseMsg", ScenarioDetailsHashMap), "Validating Warehouse Creation Message.", ScenarioDetailsHashMap);
				}
			}
		}
		else if(WareHouseBasis.equalsIgnoreCase("MBL"))
		{
			HBLID = WareHouseTextSplit[2].trim();
			System.out.println("HBLID :"+HBLID);
			ArrayList<String> WRHRequired = ExcelUtils.getAllCellValuesOfColumn("SE_WarehouseMainDtls", "WarehouseRequired", ScenarioDetailsHashMap);
			List<String> WRHRequiredYes =new ArrayList<String>();
			if(WRHRequired.size()>1)
			{
				String[] ShipmentId = (OnSaveWareHouseText.split(":")[2]).split(",");
				System.out.println(ShipmentId.length);
				String WarehouseID=null;
				String originalDataSt=ScenarioDetailsHashMap.get("DataSetNo");
				for (int XLrowno = 0; XLrowno < WRHRequired.size(); XLrowno++)
				{ 
					String slno= null;
					if(WRHRequired.get(XLrowno).equalsIgnoreCase("yes")){
						ScenarioDetailsHashMap.put("DataSetNo", String.valueOf(XLrowno+1));
						slno =ExcelUtils.getCellDataWithoutDataSet("SE_WarehouseMainDtls",  XLrowno+1, "sn", ScenarioDetailsHashMap);
						WRHRequiredYes.add(slno);
						System.out.println("slno"+slno);
					}
				}
				ScenarioDetailsHashMap.put("DataSetNo",originalDataSt);
				for (int i = 0; i < WRHRequiredYes.size(); i++) {
					System.out.println("WRHRequiredYes"+WRHRequiredYes.get(i));
					GenericMethods.replaceTextofTextField(driver, null, orOceanExpWareHouse.getElement(driver, "WarehouseShipmentIDSearch", ScenarioDetailsHashMap, 10),ShipmentId[i], 10);
					GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
					GenericMethods.pauseExecution(6000);
					GenericMethods.assertInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridShipmentID", ScenarioDetailsHashMap, 10), ShipmentId[i], "HBLID", "Validating HBL ID in Warehouse page",  ScenarioDetailsHashMap);
					GenericMethods.assertTwoValues(WareHouseMSG, ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseMsg", ScenarioDetailsHashMap), "Validating Warehouse Creation Message.", ScenarioDetailsHashMap);
					WarehouseID = GenericMethods.getInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridWarehouseID", ScenarioDetailsHashMap, 10), 2);
					ExcelUtils.setCellDataKF("SE_WarehouseMainDtls",Integer.parseInt(WRHRequiredYes.get(i)), "WarehouseId",WarehouseID, ScenarioDetailsHashMap);
				}

			}
			else
			{
				GenericMethods.replaceTextofTextField(driver, null, orOceanExpWareHouse.getElement(driver, "WarehouseShipmentIDSearch", ScenarioDetailsHashMap, 10),HBLID, 10);
				GenericMethods.clickElement(driver, null,Common.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
				GenericMethods.pauseExecution(6000);
				GenericMethods.assertInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridShipmentID", ScenarioDetailsHashMap, 10), HBLID, "HBLID", "Validating HBL ID in Warehouse page",  ScenarioDetailsHashMap);
				GenericMethods.assertTwoValues(WareHouseMSG, ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseMsg", ScenarioDetailsHashMap), "Validating Warehouse Creation Message.", ScenarioDetailsHashMap);
				String WarehouseID = GenericMethods.getInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridWarehouseID", ScenarioDetailsHashMap, 10), 2);
				ExcelUtils.setCellData("SE_WarehouseMainDtls", rowNo, "WarehouseId", WarehouseID, ScenarioDetailsHashMap);
				GenericMethods.assertInnerText(driver, null, orOceanExpWareHouse.getElement(driver, "GridWarehouseID", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_WarehouseMainDtls", rowNo, "WarehouseId",ScenarioDetailsHashMap), "WareHouse Id", "Validating WareHouse ID in Warehouse Page",  ScenarioDetailsHashMap);
			}
		}
	}
}