package global.reusables;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import DriverMethods.StartUp;


public class ExcelUtils extends StartUp {
	static ObjectRepository orTableSchema= new ObjectRepository(System.getProperty("user.dir")
			   + "/KF_Table_Schema/KFTable.xml");
	

	public static String getCellData(String sheetName,int RowNo,String colName,HashMap<String, String> ScenarioDetailsHashMap) 
    {
          String resultXl = null;
          resultXl = getPath(sheetName);
          String cellContent=null;
          try{
                String mandatory_Status = orTableSchema.getSchemaColumnValue(sheetName,colName);
                
          String      originalCellContent = getDetails(resultXl,sheetName,colName,RowNo,ScenarioDetailsHashMap);
                
          if((mandatory_Status.equalsIgnoreCase("Y")||mandatory_Status.equalsIgnoreCase("N"))&& originalCellContent!=null){
                      
                cellContent=originalCellContent;
                     // System.out.println("originalCellContent===="+originalCellContent);
                }
                else if(mandatory_Status.equalsIgnoreCase("Y")&& originalCellContent==null){
                //throw new Exception("Mandatory is"+mandatory_Status +"  and value is "+originalCellContent);
                System.out.println("Mandatory is "+mandatory_Status +"  and value is "+originalCellContent);
                GenericMethods.assertTwoValues("Actual Value for : "+colName+" is "+originalCellContent, "Expected value for "+colName+" but found \"null\"", "Verify value for madatory feild", ScenarioDetailsHashMap);
                cellContent="";
                }
                else
                {
                      cellContent="";
                }
          
                System.out.println("cellContent======"+cellContent);
          }catch(Exception ee)
          {
                ee.printStackTrace();
          }
          //Pavan-Below Script is to get the Date and Time
          if(cellContent.contains("curDate")||cellContent.equalsIgnoreCase("curDate")){
                int i= cellContent.indexOf("+");
                int j=cellContent.indexOf("-");
                if((i==-1)&& (j==-1)){
                      cellContent=GenericMethods.getDate(0);
                }
                else if (i!=-1) {
                      int incr=Integer.parseInt(cellContent.substring(i+1));
                      cellContent=GenericMethods.getDate(incr);
                }
                else if (j!=-1) {
                      int incr=Integer.parseInt(cellContent.substring(j));
                      cellContent=GenericMethods.getDate(incr);
                }     
                //Sandeep- Below script is added to back update Current Date value in TestData Sheet.
                
               if(cellContent!=null || !("".equalsIgnoreCase(cellContent))){
                setCellData(sheetName, RowNo, colName, cellContent, ScenarioDetailsHashMap); 
               }
          }
          if(cellContent.contains("curTime")||cellContent.equalsIgnoreCase("curTime")){
                int i=cellContent.indexOf("+");
                int j=cellContent.indexOf("-");
                if((i==-1)&&(j==-1)){
                      cellContent=GenericMethods.getTime(0);
                }
                else if (i!=-1) {
                      int incr=Integer.parseInt(cellContent.substring(i+1));
                      cellContent=GenericMethods.getTime(incr);
                }
                else if (j!=-1) {
                      int incr=Integer.parseInt(cellContent.substring(j));
                      cellContent=GenericMethods.getTime(incr);
                }
                //Sandeep- Below script is added to back update Current Time value in TestData Sheet.
                if(cellContent!=null || !("".equalsIgnoreCase(cellContent))){
                    setCellData(sheetName, RowNo, colName, cellContent, ScenarioDetailsHashMap); 
                   }
          }
          //Pavan Ends here
          return cellContent;
    }


	/**Below Method will fetch data with out comparing data set .
	 * @param sheetName DataSheet name
	 * @param RowNo RowNo of Sheet which Contains Data.
	 * @param colName Column Name from which Data need To be Fetched.
	 * @param ScenarioDetailsHashMap
	 * @author PriyaRanjanM
	 * @return
	 */
	public static String getCellDataWithoutDataSet(String sheetName,int RowNo,String colName,HashMap<String, String> ScenarioDetailsHashMap) 
    {
          String resultXl = null;
          resultXl = getPath(sheetName);
          String cellContent=null;
          try{
                String mandatory_Status = orTableSchema.getSchemaColumnValue(sheetName,colName);
                
          String      originalCellContent = getDetailsExculdeDataset(resultXl,sheetName,colName,RowNo,ScenarioDetailsHashMap);
                
          if((mandatory_Status.equalsIgnoreCase("Y")||mandatory_Status.equalsIgnoreCase("N"))&& originalCellContent!=null){
                      
                cellContent=originalCellContent;
                     // System.out.println("originalCellContent===="+originalCellContent);
                }
                else if(mandatory_Status.equalsIgnoreCase("Y")&& originalCellContent==null){
                //throw new Exception("Mandatory is"+mandatory_Status +"  and value is "+originalCellContent);
                System.out.println("Mandatory is "+mandatory_Status +"  and value is "+originalCellContent);
                GenericMethods.assertTwoValues("Actual Value for : "+colName+" is "+originalCellContent, "Expected value for "+colName+" but found \"null\"", "Verify value for madatory feild", ScenarioDetailsHashMap);
                cellContent="";
                }
                else
                {
                      cellContent="";
                }
          
                System.out.println("cellContent======"+cellContent);
          }catch(Exception ee)
          {
                ee.printStackTrace();
          }
          //Pavan-Below Script is to get the Date and Time
          if(cellContent.contains("curDate")||cellContent.equalsIgnoreCase("curDate")){
                int i= cellContent.indexOf("+");
                int j=cellContent.indexOf("-");
                if((i==-1)&& (j==-1)){
                      cellContent=GenericMethods.getDate(0);
                }
                else if (i!=-1) {
                      int incr=Integer.parseInt(cellContent.substring(i+1));
                      cellContent=GenericMethods.getDate(incr);
                }
                else if (j!=-1) {
                      int incr=Integer.parseInt(cellContent.substring(j));
                      cellContent=GenericMethods.getDate(incr);
                }     
                //Sandeep- Below script is added to back update Current Date value in TestData Sheet.
                
               if(cellContent!=null || !("".equalsIgnoreCase(cellContent))){
                setCellData(sheetName, RowNo, colName, cellContent, ScenarioDetailsHashMap); 
               }
          }
          if(cellContent.contains("curTime")||cellContent.equalsIgnoreCase("curTime")){
                int i=cellContent.indexOf("+");
                int j=cellContent.indexOf("-");
                if((i==-1)&&(j==-1)){
                      cellContent=GenericMethods.getTime(0);
                }
                else if (i!=-1) {
                      int incr=Integer.parseInt(cellContent.substring(i+1));
                      cellContent=GenericMethods.getTime(incr);
                }
                else if (j!=-1) {
                      int incr=Integer.parseInt(cellContent.substring(j));
                      cellContent=GenericMethods.getTime(incr);
                }
                //Sandeep- Below script is added to back update Current Time value in TestData Sheet.
                if(cellContent!=null || !("".equalsIgnoreCase(cellContent))){
                    setCellData(sheetName, RowNo, colName, cellContent, ScenarioDetailsHashMap); 
                   }
          }
          //Pavan Ends here
          return cellContent;
    }




	/**
	 *This method is to return details from an excel sheet.
	 * @param sheetName: Workbook sheet name.
	 * @param RowNo: Row Number in sheet.
	 * @param colName: Column Name/Column Header in sheet.
	 * @param ScenarioDetailsHashMap : Hashmap variable contains Scenario details.
	 * @return It returns Column data
	 * @author Sandeep Jain
	 * @Date June 20th 2014
	 */
	public static String getCellData1(String sheetName,int RowNo,String colName,HashMap<String, String> ScenarioDetailsHashMap) 
	{
		String resultXl = null;
		resultXl = getPath(sheetName);
		String cellContent=null;
		try{
			cellContent = getDetails(resultXl,sheetName,colName, RowNo,ScenarioDetailsHashMap);
		}catch(Exception ee)
		{
			ee.printStackTrace();
		}
		//Pavan-Below Script is to get the Date and Time
		if(cellContent.contains("curDate")||cellContent.equalsIgnoreCase("curDate")){
			int i= cellContent.indexOf("+");
			int j=cellContent.indexOf("-");
			if((i==-1)&& (j==-1)){
				cellContent=GenericMethods.getDate(0);
			}
			else if (i!=-1) {
				int incr=Integer.parseInt(cellContent.substring(i+1));
				cellContent=GenericMethods.getDate(incr);
			}
			else if (j!=-1) {
				int incr=Integer.parseInt(cellContent.substring(j));
				cellContent=GenericMethods.getDate(incr);
			}	
			//Sandeep- Below script is added to back update Current Date value in TestData Sheet.
			
			setCellData(sheetName, RowNo, colName, cellContent, ScenarioDetailsHashMap);	
		}
		if(cellContent.contains("curTime")||cellContent.equalsIgnoreCase("curTime")){
			int i=cellContent.indexOf("+");
			int j=cellContent.indexOf("-");
			if((i==-1)&&(j==-1)){
				cellContent=GenericMethods.getTime(0);
			}
			else if (i!=-1) {
				int incr=Integer.parseInt(cellContent.substring(i+1));
				cellContent=GenericMethods.getTime(incr);
			}
			else if (j!=-1) {
				int incr=Integer.parseInt(cellContent.substring(j));
				cellContent=GenericMethods.getTime(incr);
			}
			//Sandeep- Below script is added to back update Current Time value in TestData Sheet.
			setCellData(sheetName, RowNo, colName, cellContent, ScenarioDetailsHashMap);
		}
		//Pavan Ends here
		return cellContent;
	}
	
	/**
	 *This method returns number of records available for a dataset 
	 * @param sheetName: Workbook sheet name.
	 * @param ScenarioDetailsHashMap: Hashmap variable which contains Scenario details.
	 * @return returns count of records available for a dataset.
	 * @author Sandeep Jain
	 * @Date June 20th 2014
	 */
	public static int getCellDataRowCount(String sheetName,HashMap<String, String> ScenarioDetailsHashMap) 
	{
		String resultXl = null;
		resultXl = getPath(sheetName);
		int rowcount=0;
		try{
			rowcount = getRowCount(resultXl,sheetName,ScenarioDetailsHashMap);
		}catch(Exception exMessage)
		{
			exMessage.printStackTrace();
		}

		return rowcount;
	}
	
	public static int getSubScenarioRowCount(String sheetName,HashMap<String, String> ScenarioDetailsHashMap) 
	 {
	  String resultXl = null;
	  resultXl = getPath(sheetName);
	  int rowcount=0;
	  try{
	   rowcount = getSubScenarioRowCount(resultXl,sheetName,ScenarioDetailsHashMap);
	  }catch(Exception exMessage)
	  {
	   exMessage.printStackTrace();
	  }
	  System.out.println("rowcount:::"+rowcount);
	  return rowcount;
	 }
	

	public static String getDetails(String WorkbookPath,String SheetName,String ColumnName, int RowNo,HashMap<String, String> ScenarioDetailsHashMap) throws ClassNotFoundException, SQLException
	      {
	            Connection con_GetExcelDetails = null;
	            String celldata = null;
	            //try {
	                  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	                  con_GetExcelDetails = DriverManager
	                  .getConnection(
	                              GenericMethods.getPropertyValue("conString1", GenerateControlfiles.path)
	                              + WorkbookPath
	                              +GenericMethods.getPropertyValue("conString2", GenerateControlfiles.path));
	                  
	                  Statement stmt_GetExcelDetails = con_GetExcelDetails.createStatement();
	                  
	                  ResultSet RS_GetCellDetails = stmt_GetExcelDetails.executeQuery
	                  ("select * from ["+SheetName+"$] where " +
	                              CyborgConstants.DRIVER_BOOK_TEST_PALN+" ='"+ScenarioDetailsHashMap.get("ScenarioName")+"' and " +
	                              CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" ='"+ScenarioDetailsHashMap.get("SubScenarioNo")+"' and " +
	                              CyborgConstants.DRIVER_BOOK_DATA_SETS+"='"+ScenarioDetailsHashMap.get("DataSetNo")+"'  and " +
	                              CyborgConstants.DRIVER_BOOK_BROWSER_TYPE+"='"+ScenarioDetailsHashMap.get("BrowserType")+"'");
	                  //System.out.println("===RowNo==="+RowNo+"===celldata===="+celldata);
	                  int counter = 0;
	                  while(RS_GetCellDetails.next() && RS_GetCellDetails != null)
	                  {
	                        counter =counter+1;
	                  //    System.out.println("===RowNo==="+RowNo+"===celldata===="+celldata);
	                        if(counter == RowNo)
	                        {
	                              celldata=RS_GetCellDetails.getString(ColumnName);
	                              //System.out.println("celldata:::"+celldata);
	                        }
	                  }
	                  con_GetExcelDetails.close();
	            /*} catch (ClassNotFoundException e) {
	                  e.printStackTrace();
	            } catch (SQLException e) {
	                  e.printStackTrace();
	            }*/
	            return celldata;
	      }

	
	public static String getDetailsExculdeDataset(String WorkbookPath,String SheetName,String ColumnName, int RowNo,HashMap<String, String> ScenarioDetailsHashMap) throws ClassNotFoundException, SQLException
    {
          Connection con_GetExcelDetails = null;
          String celldata = null;
          //try {
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                con_GetExcelDetails = DriverManager
                .getConnection(
                            GenericMethods.getPropertyValue("conString1", GenerateControlfiles.path)
                            + WorkbookPath
                            +GenericMethods.getPropertyValue("conString2", GenerateControlfiles.path));
                
                Statement stmt_GetExcelDetails = con_GetExcelDetails.createStatement();
                
                ResultSet RS_GetCellDetails = stmt_GetExcelDetails.executeQuery
                ("select * from ["+SheetName+"$] where " +
                            CyborgConstants.DRIVER_BOOK_TEST_PALN+" ='"+ScenarioDetailsHashMap.get("ScenarioName")+"' and " +
                            CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" ='"+ScenarioDetailsHashMap.get("SubScenarioNo")+"' and " +
                            
                            CyborgConstants.DRIVER_BOOK_BROWSER_TYPE+"='"+ScenarioDetailsHashMap.get("BrowserType")+"'");
                //System.out.println("===RowNo==="+RowNo+"===celldata===="+celldata);
                int counter = 0;
                while(RS_GetCellDetails.next() && RS_GetCellDetails != null)
                {
                      counter =counter+1;
                //    System.out.println("===RowNo==="+RowNo+"===celldata===="+celldata);
                      if(counter == RowNo)
                      {
                            celldata=RS_GetCellDetails.getString(ColumnName);
                            //System.out.println("celldata:::"+celldata);
                      }
                }
                con_GetExcelDetails.close();
          /*} catch (ClassNotFoundException e) {
                e.printStackTrace();
          } catch (SQLException e) {
                e.printStackTrace();
          }*/
          return celldata;
    }
	/**
	 *This method will fetch details from the excel sheet.
	 * @param WorkbookPath: Path of the Workbook
	 * @param SheetName: Sheet Name in the Workbook
	 * @param ColumnName: Column Name/Column Header in sheet.
	 * @param RowNo: Row Number in sheet.
	 * @param ScenarioDetailsHashMap: Hashmap variable which contains Scenario details.	 
	 * @return It returns Column data
	 * @author Sandeep Jain
	 * @Date June 20th 2014
	 * @Modified By: Sandeep Jain
	 * @Modified description: Added BrowserType parameter in Select query as we are maintating Browser details in testdata sheet.
	 * @Modified Date Oct 27th 2014
	 */
	public static String getDetails1(String WorkbookPath,String SheetName,String ColumnName, int RowNo,HashMap<String, String> ScenarioDetailsHashMap)
	{
		Connection con_GetExcelDetails = null;
		String celldata = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con_GetExcelDetails = DriverManager
			.getConnection(
					GenericMethods.getPropertyValue("conString1", GenerateControlfiles.path)
					+ WorkbookPath
					+GenericMethods.getPropertyValue("conString2", GenerateControlfiles.path));
			
			Statement stmt_GetExcelDetails = con_GetExcelDetails.createStatement();
			/*System.out.println("select * from ["+SheetName+"$] where " +
					CyborgConstants.DRIVER_BOOK_TEST_PALN+" ='"+ScenarioDetailsHashMap.get("ScenarioName")+"' and " +
					CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" ='"+ScenarioDetailsHashMap.get("SubScenarioNo")+"' and " +
					CyborgConstants.DRIVER_BOOK_DATA_SETS+"='"+ScenarioDetailsHashMap.get("DataSetNo")+"' ");*/
		//System.out.println("sheenatname"+ScenarioDetailsHashMap.get("BrowserType")+"....."+SheetName);
			/*System.out.println("SheetName***"+SheetName);
			System.out.println("ScenarioName***"+ScenarioDetailsHashMap.get("ScenarioName"));
			System.out.println("DataSetNo***"+ScenarioDetailsHashMap.get("DataSetNo"));
			System.out.println("BrowserType***"+ScenarioDetailsHashMap.get("BrowserType"));
			System.out.println("ColumnName***"+ColumnName);
			System.out.println("WorkbookPath:::::"+WorkbookPath);
			System.out.println("select * from ["+SheetName+"$] where " +
					CyborgConstants.DRIVER_BOOK_TEST_PALN+" ='"+ScenarioDetailsHashMap.get("ScenarioName")+"' and " +
					CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" ='"+ScenarioDetailsHashMap.get("SubScenarioNo")+"' and " +
					CyborgConstants.DRIVER_BOOK_DATA_SETS+"='"+ScenarioDetailsHashMap.get("DataSetNo")+"'  and " +
					CyborgConstants.DRIVER_BOOK_BROWSER_TYPE+"='"+ScenarioDetailsHashMap.get("BrowserType")+"'");
			
			*/
/*String query  = "select * from [SE_BookingMainDetails$] where TEST_PLAN='KFPICTScenario' and TEST_SUITE='E2E_Test_Suite'";
			
			ResultSet RS_GetCellDetails = stmt_GetExcelDetails.executeQuery(query);*/
			
			
			
			ResultSet RS_GetCellDetails = stmt_GetExcelDetails.executeQuery
			("select * from ["+SheetName+"$] where " +
					CyborgConstants.DRIVER_BOOK_TEST_PALN+" ='"+ScenarioDetailsHashMap.get("ScenarioName")+"' and " +
					CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" ='"+ScenarioDetailsHashMap.get("SubScenarioNo")+"' and " +
					CyborgConstants.DRIVER_BOOK_DATA_SETS+"='"+ScenarioDetailsHashMap.get("DataSetNo")+"'  and " +
					CyborgConstants.DRIVER_BOOK_BROWSER_TYPE+"='"+ScenarioDetailsHashMap.get("BrowserType")+"'");
			//System.out.println("===RowNo==="+RowNo+"===celldata===="+celldata);
			int counter = 0;
			while(RS_GetCellDetails.next() && RS_GetCellDetails != null)
			{
				counter =counter+1;
			//	System.out.println("===RowNo==="+RowNo+"===celldata===="+celldata);
				if(counter == RowNo)
				{
					celldata=RS_GetCellDetails.getString(ColumnName);
					//System.out.println("celldata:::"+celldata);
				}
			}
			con_GetExcelDetails.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return celldata;
	}
	
	
	public static int getRowCount(String WorkbookPath,String SheetName,HashMap<String, String> ScenarioDetailsHashMap)
	{
		int counter = 0;
		Connection con_GetExcelDetails = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con_GetExcelDetails = DriverManager
			.getConnection(
					"jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="
					+ WorkbookPath
					+ ";"
					+ "readOnly=false", "", "");
			
			Statement stmt_GetExcelDetails = con_GetExcelDetails.createStatement();
			ResultSet RS_GetCellDetails = stmt_GetExcelDetails.executeQuery
			("select * from ["+SheetName+"$]  where " +
					CyborgConstants.DRIVER_BOOK_TEST_PALN+" ='"+ScenarioDetailsHashMap.get("ScenarioName")+"' and " +
					CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" ='"+ScenarioDetailsHashMap.get("SubScenarioNo")+"' and " +
					CyborgConstants.DRIVER_BOOK_DATA_SETS+"='"+ScenarioDetailsHashMap.get("DataSetNo")+"'  and " +
					CyborgConstants.DRIVER_BOOK_BROWSER_TYPE+"='"+ScenarioDetailsHashMap.get("BrowserType")+"'");
		
			while(RS_GetCellDetails.next() && RS_GetCellDetails != null)
			{
				counter =counter+1;
			}
			con_GetExcelDetails.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return counter;
	}
	
	public static int getSubScenarioRowCount(String WorkbookPath,String SheetName,HashMap<String, String> ScenarioDetailsHashMap)
	{
		int counter = 0;
		Connection con_GetExcelDetails = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con_GetExcelDetails = DriverManager
			.getConnection(
					"jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="
					+ WorkbookPath
					+ ";"
					+ "readOnly=false", "", "");
			
			Statement stmt_GetExcelDetails = con_GetExcelDetails.createStatement();
			ResultSet RS_GetCellDetails = stmt_GetExcelDetails.executeQuery
			("select * from ["+SheetName+"$]  where " +
					CyborgConstants.DRIVER_BOOK_TEST_PALN+" ='"+ScenarioDetailsHashMap.get("ScenarioName")+"' and " +
					CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" ='"+ScenarioDetailsHashMap.get("SubScenarioNo")+"' and " +
					CyborgConstants.DRIVER_BOOK_BROWSER_TYPE+"='"+ScenarioDetailsHashMap.get("BrowserType")+"'");
		
			while(RS_GetCellDetails.next() && RS_GetCellDetails != null)
			{
				counter =counter+1;
			}
			con_GetExcelDetails.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("counter:::"+counter);
		return counter;
		
	}

	
    /**
     * Below method is to save details in Workbook 
     * @param sheetName: Sheet Name in the Workbook
     * @param rowNo: Row Number in sheet.
     * @param columnName: Column Name/Column Header in sheet.
     * @param columnData: Details to be save in excel sheet. 
     * @param scenarioDetailsHashMap: Hashmap variable which contains Scenario details.
     * @author Sandeep Jain
	 * @Date June 20th 2014
     */
    public static void setCellData(String sheetName,int rowNo,String columnName,String columnData,HashMap<String, String> scenarioDetailsHashMap) 
    {
          String WorkBookPath = null;
          WorkBookPath = getPath(sheetName);

          try{
                setDetails(WorkBookPath,sheetName,columnName, columnData,rowNo,scenarioDetailsHashMap);

          }catch(Exception ee)
          {
                ee.printStackTrace();
          }

    }
    
    /*@Modified By: Sandeep Jain
	 * @Modified description: Added BrowserType parameter in Select query as we are maintating Browser details in testdata sheet.
	 * @Modified Date Oct 27th 2014
	 */
    public static void setDetails(String WorkbookPath,String SheetName,String ColumnName, String ColumnData,int RowNo,HashMap<String, String> ScenarioDetailsHashMap)
    {
    	System.out.println("RowNo::"+RowNo);
          Connection con_SetDetails = null;
          try {
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                con_SetDetails = DriverManager.getConnection(
                            "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="
                            + WorkbookPath
                            + ";"
                            + "readOnly=false", "", "");
                Statement stmt_SetExcelDetails = con_SetDetails.createStatement();
                ResultSet RS_SetCellDetails = stmt_SetExcelDetails.executeQuery
                ("select * from ["+SheetName+"$]  where " +
                		CyborgConstants.DRIVER_BOOK_TEST_PALN+" ='"+ScenarioDetailsHashMap.get("ScenarioName")+"' and " +
    					CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" ='"+ScenarioDetailsHashMap.get("SubScenarioNo")+"' and " +
    					CyborgConstants.DRIVER_BOOK_DATA_SETS+"='"+ScenarioDetailsHashMap.get("DataSetNo")+"' and " +
    					CyborgConstants.DRIVER_BOOK_BROWSER_TYPE+"='"+ScenarioDetailsHashMap.get("BrowserType")+"'");
                int counter = 0;
                while(RS_SetCellDetails.next() && RS_SetCellDetails != null)
                {
                      counter =counter+1;
                      if(counter == RowNo)
                      {
                            String data_SN=RS_SetCellDetails.getString("sn");
                            con_SetDetails.prepareStatement(
                                        "update ["+SheetName+"$] set "+ColumnName+" = '" + ColumnData
                                                    + "' where sn ='" + data_SN
                                                    + "'").execute();
                      }
                }
                con_SetDetails.close();
                
          } catch (ClassNotFoundException e) {
                e.printStackTrace();
          } catch (SQLException e) {
                e.printStackTrace();
          }
          
    }

	
    /**
	 * @param sheetName: Excel sheet name.
	 * @param BrowserType: Browser Name
	 * @author Priyaranjan
	 * @Modified By: Sandeep Jain
	 * @Modified description: Added parameter BrowserType to peform Parallel Testing
	 * @Date June 20th 2014
	 * @return path of the workbook.
	 */
	public static String getPath(String sheetName,String BrowserType) 
	{
		boolean status=false;
		boolean excelSheetStatus = false;
		String relPath=null;
		List< String> tableeNames=getTableNames(BrowserType);
		for (int i = 0; i < tableeNames.size(); i++) {
			
			relPath=System.getProperty("user.dir")+"\\TestData\\VirtualTestData\\"+"\\"+tableeNames.get(i);
			File file=new File(relPath);
			Workbook wb = null;			
			try {
				wb = WorkbookFactory.create(file);
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			int noOfExcelSheets = wb.getNumberOfSheets();
			for (int j = 0; j < noOfExcelSheets; j++) 
			{
				if(wb.getSheetName(j).equals(sheetName))
				{
					excelSheetStatus = true;
					break;
				}
			}

			if(excelSheetStatus == true)
			{ 
				status=true;
				break;

			}
		}
		if(status == false)
		{
			System.out.println("Sheet not available");
		}
		return relPath;
	}
	
	/**
	 * @param sheetName: Excel sheet name.
	 * @param BrowserType: Browser Name
	 * @author Priyaranjan
	 * @Modified By: Sandeep Jain
	 * @Modified description: Added parameter BrowserType to peform Parallel Testing
	 * @Date June 20th 2014
	 * @return path of the workbook.
	 * @Modified By: Sandeep Jain
	 * @Modified description: Removed parameter BrowserType as we are not maintating seperate testdata for different browsers.
	 * @Modified Date Oct 27th 2014
	 * 
	 */
	public static String getPath(String sheetName) 
	{/*
		boolean status=false;
		boolean excelSheetStatus = false;
		String relPath=null;
		List< String> tableeNames=getTableNames();
		for (int i = 0; i < tableeNames.size(); i++) {
			relPath=System.getProperty("user.dir")+"\\TestData\\VirtualTestData\\"+tableeNames.get(i);
			File file=new File(relPath);
			Workbook wb = null;			
			try {
				wb = WorkbookFactory.create(file);
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			int noOfExcelSheets = wb.getNumberOfSheets();
			for (int j = 0; j < noOfExcelSheets; j++) 
			{
				if(wb.getSheetName(j).equals(sheetName))
				{
					excelSheetStatus = true;
					break;
				}
			}

			if(excelSheetStatus == true)
			{ 
				status=true;
				break;

			}
			
		}
		if(status == false)
		{
			System.out.println("Sheet not avauilable");
		}
		*/
		String relPath=System.getProperty("user.dir")+"\\TestData\\VirtualTestData\\"+GenericMethods.getPropertyValue("WorkBookName", configurationStructurePath);
		return relPath;
	}
	
	/**
	 * To get the list of tables available inside a schema.
	 * @param schemaName Schema name.
	 * @return List of tables .
	 * @author Priyaranjan
	 * @Modified By: Sandeep Jain
	 * @Modified description: Removed parameter BrowserType as we are not maintating seperate testdata for different browsers.
	 * @Modified Date Oct 27th 2014
	 */
	public static  List< String> getTableNames()
	{
		File folder= new File(System.getProperty("user.dir")+"\\TestData\\VirtualTestData\\");
		File[] filelist=folder.listFiles();
		List<String> filenames=new ArrayList<String>();
		for(int i=0;i<filelist.length;i++)
		{//Logic to ensure only "xls" and ".xlsx" are selected for test data other than any other folder like "CVS"-Priyaranjan.
			if(filelist[i].getName().endsWith(".xlsx"))
			{

				filenames.add(filelist[i].getName());
			}else if(filelist[i].getName().endsWith(".xls")){
				filenames.add(filelist[i].getName());
			}
			//Ends here
		}
		return filenames;
	}
	

	/**
	 * To get the list of tables available inside a schema.
	 * @param schemaName Schema name.
	 * @return List of tables .
	 * @author Priyaranjan
	 */
	public static  List< String> getTableNames(String BrowserName)
	{
		File folder= new File(System.getProperty("user.dir")+"\\TestData\\VirtualTestData\\");
		File[] filelist=folder.listFiles();
		List<String> filenames=new ArrayList<String>();
		for(int i=0;i<filelist.length;i++)
		{//Logic to ensure only "xls" and ".xlsx" are selected for test data other than any other folder like "CVS"-Priyaranjan.
			if(filelist[i].getName().endsWith(".xlsx"))
			{

				filenames.add(filelist[i].getName());
			}else if(filelist[i].getName().endsWith(".xls")){
				filenames.add(filelist[i].getName());
			}
			//Ends here
		}
		return filenames;
	}
	
	
	//Method for TestLink
	public static String getCellDataTL(String sheetName,String colName,String TestCaseName,String BrowserType,String subscenarioName) 
	{
		String cellContent=null;
		try{
			String celldata = null;
			String singleWorkBookPath = System.getProperty("user.dir")
			+ GenericMethods.getPropertyValue("singleworkbookPath",
					GenerateControlfiles.path);
			Connection con_GetExcelDetails = DBConnectionManager
			.getConnection(singleWorkBookPath);
			 System.out.println("=============="+TestCaseName);
			 String TestPlan= GenericMethods.getPropertyValue("AutomationTestPlan",GenerateControlfiles.path);
			 if(TestPlan.contains(" ")||TestPlan.contains(".")||TestPlan.contains("-")||TestPlan.contains("/")||TestPlan.contains("_(")){
					System.out.println("SuiteName before==="+TestPlan);
					TestPlan=	TestPlan.replace(" ", "_").replace(".", "_").replace("-", "_").replace("/", "_").replace("_(", "_");
					if(TestPlan.endsWith(")")){
						TestPlan=TestPlan.replace(")", "").trim();
					}
					System.out.println("SuiteName after==="+TestPlan);
				}
				Statement stmt_GetExcelDetails = con_GetExcelDetails.createStatement();
				System.out.println("select * from ["+sheetName+"$]  where " +"TEST_PLAN='"+TestPlan+"'"+"and TEST_SUITE='"+subscenarioName+"'");
				ResultSet RS_GetCellDetails = stmt_GetExcelDetails.executeQuery
				("select * from ["+sheetName+"$]  where " +
						"TEST_PLAN='"+TestPlan+"'"+"and TEST_SUITE='"+subscenarioName+"'");
				while(RS_GetCellDetails.next() && RS_GetCellDetails != null)
				{
						celldata=RS_GetCellDetails.getString(colName);
						if(celldata.contains(TestCaseName)){
							String data_SN=RS_GetCellDetails.getString("Sr_No");
							con_GetExcelDetails.prepareStatement(
                                    "update [ControlSheet$] set TEST_PLAN_REQUIRED='YES' ,TEST_SUITE_REQUIRED='YES' , TEST_CASE_REQUIRED='YES',BROWSER_TYPE='"+BrowserType+"' where Sr_No ='" + data_SN
                                                + "'").execute();
						}
					
				}
				con_GetExcelDetails.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		//Pavan Ends here
		return cellContent;
	}
	/**
	 *This method returns list of cell  values of specified column 
	 * @param sheetName: Workbook sheet name.
	 * @param columnName: Sheet column name.
	 * @param ScenarioDetailsHashMap: Hashmap variable which contains Scenario details.
	 * @return returns list of cell  values of specified column.
	 * @author Prasanna Modugu
	 * @Date June 27th 2014
	 */
	public static ArrayList<String> getAllCellValuesOfColumn(String sheetName,String columnName,HashMap<String, String> ScenarioDetailsHashMap)
	{
		//String WorkbookPath ="D:/eTrans/Automation/KF_3.2/TestData/VirtualTestData/chrome/SeaTestData.xls";
		String WorkbookPath =getPath(sheetName,ScenarioDetailsHashMap.get("BrowserType"));
		ArrayList<String> values =new ArrayList<String>();
		Connection con_GetExcelDetails = null;
		try {
			if(WorkbookPath!=null){
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con_GetExcelDetails = DriverManager
			.getConnection(
					"jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="
					+ WorkbookPath
					+ ";"
					+ "readOnly=false", "", "");
			}
			Statement stmt_GetExcelDetails = con_GetExcelDetails.createStatement();
			ResultSet RS_GetCellDetails = stmt_GetExcelDetails.executeQuery
			("select * from ["+sheetName+"$]  where " +
					CyborgConstants.DRIVER_BOOK_TEST_PALN+" ='"+ScenarioDetailsHashMap.get("ScenarioName")+"' and " +
					//"data_set_no='"+ScenarioDetailsHashMap.get("DataSetNo")+"' and " +
					CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" ='"+ScenarioDetailsHashMap.get("SubScenarioNo")+"'");
			while(RS_GetCellDetails.next() && RS_GetCellDetails != null)
			{
			String	val=RS_GetCellDetails.getString(columnName);
		//	System.out.println("Test=="+val);
			values.add(val);
				
			}
			con_GetExcelDetails.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return values;
	}
	 public static void setCellDataKF(String sheetName,int data_SN,String columnName,String columnData,HashMap<String, String> scenarioDetailsHashMap){
   	  String WorkBookPath = null;
         WorkBookPath = getPath(sheetName,scenarioDetailsHashMap.get("BrowserType"));
         Connection con_SetDetails = null;
         try {
               Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
               con_SetDetails = DriverManager.getConnection(
                           "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="
                           + WorkBookPath
                           + ";"
                           + "readOnly=false", "", "");
                     
                           con_SetDetails.prepareStatement(
                                       "update ["+sheetName+"$] set "+columnName+" = '" + columnData
                                                   + "' where sn ='" + data_SN
                                                   + "'").execute();
                     
               con_SetDetails.close();
               
         } catch (ClassNotFoundException e) {
               e.printStackTrace();
         } catch (SQLException e) {
               e.printStackTrace();
         }
   }
	//Pavan--Below is method is to set the value in test data irrespective of DataSet
		public static void setCellData_Without_DataSet(String sheetName,int rowNo,String columnName,String columnData,HashMap<String, String> scenarioDetailsHashMap) 
		{
			String WorkBookPath = null;
			WorkBookPath = getPath(sheetName);

			try{
				setDetails_Without_DataSet(WorkBookPath,sheetName,columnName, columnData,rowNo,scenarioDetailsHashMap);

			}catch(Exception ee)
			{
				ee.printStackTrace();
			}

		}
		//Pavan--Below is method is to set the value in test data irrespective of DataSet
		public static void setDetails_Without_DataSet(String WorkbookPath,String SheetName,String ColumnName, String ColumnData,int RowNo,HashMap<String, String> ScenarioDetailsHashMap)
		{
			Connection con_SetDetails = null;
			try {
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				con_SetDetails = DriverManager.getConnection(
						"jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="
						+ WorkbookPath
						+ ";"
						+ "readOnly=false", "", "");
				Statement stmt_SetExcelDetails = con_SetDetails.createStatement();
				ResultSet RS_SetCellDetails = stmt_SetExcelDetails.executeQuery
				("select * from ["+SheetName+"$]  where " +
						CyborgConstants.DRIVER_BOOK_TEST_PALN+" ='"+ScenarioDetailsHashMap.get("ScenarioName")+"' and " +
						CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" ='"+ScenarioDetailsHashMap.get("SubScenarioNo")+"' and " +
						CyborgConstants.DRIVER_BOOK_BROWSER_TYPE+"='"+ScenarioDetailsHashMap.get("BrowserType")+"'");
				int counter = 0;
				while(RS_SetCellDetails.next() && RS_SetCellDetails != null)
				{
					counter =counter+1;
					if(counter == RowNo)
					{
						String data_SN=RS_SetCellDetails.getString("sn");
						con_SetDetails.prepareStatement(
								"update ["+SheetName+"$] set "+ColumnName+" = '" + ColumnData
								+ "' where sn ='" + data_SN
								+ "'").execute();
					}
				}
				con_SetDetails.close();

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		//Pavan--Below is method is to set the value in test data irrespective of RowNum
		public static void setCellData_Without_RowNum(String sheetName,String columnName,String columnData,HashMap<String, String> scenarioDetailsHashMap) 
		{
			String WorkBookPath = null;
			WorkBookPath = getPath(sheetName);

			try{
				setDetails(WorkBookPath,sheetName,columnName, columnData,scenarioDetailsHashMap);

			}catch(Exception ee)
			{
				ee.printStackTrace();
			}

		}

		//Pavan--Below is method is an overloaded method to set the value in test data irrespective of RowNum
		public static void setDetails(String WorkbookPath,String SheetName,String ColumnName, String ColumnData,HashMap<String, String> ScenarioDetailsHashMap)
		{
			Connection con_SetDetails = null;
			try {
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				con_SetDetails = DriverManager.getConnection(
						"jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="
						+ WorkbookPath
						+ ";"
						+ "readOnly=false", "", "");
				Statement stmt_SetExcelDetails = con_SetDetails.createStatement();
				ResultSet RS_SetCellDetails = stmt_SetExcelDetails.executeQuery
				("select * from ["+SheetName+"$]  where " +
						CyborgConstants.DRIVER_BOOK_TEST_PALN+" ='"+ScenarioDetailsHashMap.get("ScenarioName")+"' and " +
						CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" ='"+ScenarioDetailsHashMap.get("SubScenarioNo")+"' and " +
						CyborgConstants.DRIVER_BOOK_DATA_SETS+"='"+ScenarioDetailsHashMap.get("DataSetNo")+"' and " +
						CyborgConstants.DRIVER_BOOK_BROWSER_TYPE+"='"+ScenarioDetailsHashMap.get("BrowserType")+"'");
				int counter = 0;
				while(RS_SetCellDetails.next() && RS_SetCellDetails != null)
				{
					/*counter =counter+1;
	                      if(counter == RowNo)
	                      {*/
					String data_SN=RS_SetCellDetails.getString("sn");
					con_SetDetails.prepareStatement(
							"update ["+SheetName+"$] set "+ColumnName+" = '" + ColumnData
							+ "' where sn ='" + data_SN
							+ "'").execute();
					//                      }
				}
				con_SetDetails.close();

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}


}

