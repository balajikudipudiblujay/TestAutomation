package global.reusables;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import DriverMethods.StartUp;




public class AssertionResults extends StartUp{

	public static int i=1;
	static int k=1;
	/**
	 * This method will set the assertionResult to excel by fetching details from assertionResultBean.
	 * @param ares Object of AssertionResultBean Class.
	 * @author Priyaranjan
	 */
	public void setAssertionResult(AssertionResultBean ares,WebElement element, By by)
	{
		System.out.println("data set no in assertion:===="+res.getDataSetNo());
		setData("AssertionReports", i, "SlNo", i+"");
		setData("AssertionReports", i, "ScenarioName", ares.getScenarioClassName());
		setData("AssertionReports", i, "SubScenarioNo", res.getSubScenarioDetails());
		setData("AssertionReports", i, "DataSetNo", res.getDataSetNo());
		setData("AssertionReports", i, "FunctionName", res.getFunctionName());
		setData("AssertionReports", i, "ClassName", res.getClassName());
		setData("AssertionReports", i, "ElementId", ares.getElementId());
		setData("AssertionReports", i, "Description", ares.getAssertionDescription());
		setData("AssertionReports", i, "ExpectedValue", ares.getExpectedValue());
		setData("AssertionReports", i, "ActualValue", ares.getActualValue());
		setData("AssertionReports", i, "Status", ares.getResult());
		setData("AssertionReports", i, "Time", ares.getTime());
		if(ares.getResult().equalsIgnoreCase("fail"))
		{
			System.out.println("in assert fail");
			JavascriptExecutor js = (JavascriptExecutor) driver;
			if(element != null)
			{
			js.executeScript("arguments[0].style.backgroundColor='#00ff00'",element);
			GenericMethods.captureScreenshot(driver, new File
					(reportStructurePath+"\\"+res.getExectionDateTime()
							+"\\"+res.getScenarioClassName()
							+res.getFunctionName()+CyborgConstants.ASSERTION+i+".jpeg"));
			js.executeScript("arguments[0].style.backgroundColor=''",element);
			}else if(by != null)
			{
				element=GenericMethods.locateElement(driver, by, 10);
				js.executeScript("arguments[0].style.backgroundColor='#00ff00'",element);
				GenericMethods.captureScreenshot(driver, new File
						(reportStructurePath+"\\"+res.getExectionDateTime()
								+"\\"+res.getScenarioClassName()
								+res.getFunctionName()+CyborgConstants.ASSERTION+i+".jpeg"));
				js.executeScript("arguments[0].style.backgroundColor=''",element);
			}
		}
		
		i+=1;
		k=1;
	}
	
	/**
	 * This method will setData into assertion excel sheet.
	 * @param sheetName Assertion sheet name.
	 * @param rowNum Row number.
	 * @param colName Column name where the data need to be set.
	 * @param cellContent String data value.
	 * @author Priyaranjan
	 */
	public static void setData (String sheetName,int rowNum,String colName,String cellContent) 
	{
		String resultXl = reportStructurePath+"\\"+res.getExectionDateTime()+"\\"+res.getScenarioClassName()+".xlsx";

		File file=new File(resultXl);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(fis);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int cell = 0;
		try{
			cell=getColoumnIndex(colName, sheetName);
		}catch(Exception ee)
		{
			System.out.println(ee.getMessage());;
		}
		Sheet sheet=wb.getSheet(sheetName);
		if(k == 1){
		sheet.createRow(rowNum);
		}
		Row row=sheet.getRow(rowNum);
		row.createCell(cell);
		Cell cel=row.getCell(cell);
		cel.setCellType(Cell.CELL_TYPE_STRING);
		cel.setCellValue(cellContent);
		FileOutputStream fso = null;
		try {
			fso = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			wb.write(fso);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fso.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		k=k+1;
	}
	/**
	 * This method will take column name as argument and will return column index.
	 * @param colName column Name
	 * @param sheetName Specific Sheet name.
	 * @return int columnIndex.
	 * @throws FileNotFoundException
	 * @throws InvalidFormatException
	 * @throws IOException
	 * @author Priyaranjan
	 */
	public static int getColoumnIndex(String colName,String sheetName) throws FileNotFoundException, InvalidFormatException, IOException 
	{	 
		String resultXl = reportStructurePath+"\\"+res.getExectionDateTime()+"\\"+res.getScenarioClassName()+".xlsx";
		int colIndex=0;
		File file=new File(resultXl);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Workbook wb=null;
		try{
			wb= WorkbookFactory.create(fis);
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		Sheet sheet=wb.getSheet(sheetName);
		Row row=sheet.getRow(0);

		for (colIndex = 0; colIndex < row.getPhysicalNumberOfCells(); colIndex++) {
			try{
				String cell=row.getCell(colIndex).getStringCellValue();
				if (cell != null) {
					if ( cell.equalsIgnoreCase(colName)) {
						break;
						/*if (cell != null) {
			          key = cell.getStringCellValue();*/
					}
				}else{
					System.out.println("coloumn with name: "+colName+" not found in "+sheetName+" table");
				}
			}
			catch(Exception e)
			{
				System.out.println("in catch block"+e.getMessage());

			}
		}
		return colIndex;

	}
	/*public static String getCellData(String sheetName,int RowNo,String colName) 
	{
		String resultXl  = reportStructurePath+"\\"+res.getExectionDateTime()+"\\"+res.getScenarioClassName()+".xlsx";;
		String cellContent=null;
		int cell = 0;
		try{
			cell=getColoumnIndex(colName, sheetName);
		}catch(Exception ee)
		{
			System.out.println(ee.getMessage());;
		}
		File file=new File(resultXl);
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Sheet sheet=wb.getSheet(sheetName);
		Row row=sheet.getRow(RowNo);
		Cell cel=row.getCell(cell);
		cel.setCellType(Cell.CELL_TYPE_STRING);
		cellContent=cel.getStringCellValue().toString();

		return cellContent;
	}*/
	public static void setData (String sheetName,int rowNum,String colName,String cellContent,String ScenarioName,String browserType) 
	{
		String resultXl = reportStructurePath+"\\"+res.getExectionDateTime()+"\\"+res.getScenarioClassName()+".xlsx";

		File file=new File(resultXl);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(fis);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int cell = 0;
		try{
			cell=getColoumnIndex(colName, sheetName);
		}catch(Exception ee)
		{
			System.out.println(ee.getMessage());;
		}
		Sheet sheet=wb.getSheet(sheetName);
		if(k == 1){
		sheet.createRow(rowNum);
		}
		Row row=sheet.getRow(rowNum);
		row.createCell(cell);
		Cell cel=row.getCell(cell);
		cel.setCellType(Cell.CELL_TYPE_STRING);
		cel.setCellValue(cellContent);
		FileOutputStream fso = null;
		try {
			fso = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			wb.write(fso);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fso.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		k=k+1;
	}
	
	
	/**
	 * This method is to save assertion results in AssertionResults sheet of scenario workbook.
	 * @param driver: Instance of webdriver.
	 * @param resultMap  : Hashmap variable contains Scenario details.
	 * @param element: element name in the application.
	 * @param by: The locating mechanism.
	 * @param Browser: Browser Name.
	 * @author Sandeep Jain
	 */
	public static void setAssertionResult(WebDriver driver,HashMap<String, String> resultMap,WebElement element, By by,String Browser)
	{
		//System.out.println("in set 444444Assertion Result");
		try{
		String ScenarioName_Browser = resultMap.get("ScenarioName")+"_"+Browser;
		String resultXl = reportStructurePath+"\\"+res.getExectionDateTime()+"\\"+ScenarioName_Browser+".xlsx";
		
		int Rowno = setValueinAssertionSheet(resultXl,resultMap);
		
		if(resultMap.get("Status").equalsIgnoreCase("fail"))
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			try{
			if(element != null)
			{
				js.executeScript("arguments[0].style.backgroundColor='#00ff00'",element);
				GenericMethods.captureScreenshot(driver, new File
						(reportStructurePath+"\\"+res.getExectionDateTime()
								+"\\"+ScenarioName_Browser+"_"+resultMap.get("SubScenarioNo")+"_"
								+resultMap.get("FunctionName")+CyborgConstants.ASSERTION+Rowno+".jpeg"));
			}else if(by != null)
			{
				element=GenericMethods.locateElement(driver, by, 10);
				js.executeScript("arguments[0].style.backgroundColor='#00ff00'",element);
				GenericMethods.captureScreenshot(driver, new File
						(reportStructurePath+"\\"+res.getExectionDateTime()
								+"\\"+ScenarioName_Browser+"_"+resultMap.get("SubScenarioNo")+"_"
								+resultMap.get("FunctionName")+CyborgConstants.ASSERTION+Rowno+".jpeg"));
			}}
			catch(Exception e)
			{
				System.out.println("Inside catch block in setAssertionResult method of AssertionResults.java file");
			}
		}
		resultMap.clear();
		i+=1;
		k=1;
		}
		catch(Exception ex)
		{
			ex.getStackTrace();
		}
	}
	
		
	public static int getRowCount(String sheetName,String path)
	{
		int rowCount=0;
		File file=new File(path);
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Sheet sheet=wb.getSheet(sheetName);
		rowCount=sheet.getLastRowNum();		
		return rowCount;
	}
	
	public static int setValueinAssertionSheet(String path,HashMap<String, String> resultMap)
	{
		int rowCount =1;
		try{
			Connection con = DBConnectionManager.getConnection(path);
			Statement mainScenarioSTMT = con.createStatement();
			ResultSet mainScenariors = mainScenarioSTMT
			.executeQuery("select * from [AssertionReports$]");
			while(mainScenariors.next())
			{			
				rowCount = rowCount+1;
			}
			//System.out.println("+++++"+rowCount);
			//System.out.println("row count++"+rowCount);			
			con.prepareStatement("insert into [AssertionReports$]([SlNo],["+CyborgConstants.DRIVER_BOOK_TEST_PALN+"],["+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+"],[ClassName],["+CyborgConstants.DRIVER_BOOK_TESTCASE_NAME+"],["+CyborgConstants.DRIVER_BOOK_DATA_SETS+"],[ElementId],[Description],[ExpectedValue],[ActualValue],[Status],[Time]) " +
					"values('"+rowCount+"','"+resultMap.get("ScenarioName")+"','"+resultMap.get("SubScenarioNo")+"','"+resultMap.get("ClassName")+"','"+resultMap.get("FunctionName")+"','"+resultMap.get("DataSetNo")+"','"+resultMap.get("ElementId")+"','"+resultMap.get("Description")+"','"+resultMap.get("ExpectedValue")+"','"+resultMap.get("ActualValue")+"','"+resultMap.get("Status")+"','"+resultMap.get("Time")+"')").execute();
			mainScenariors.close();
			con.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return rowCount;
	}
	
}
