package business.scenarios;

import global.reusables.CyborgConstants;
import global.reusables.GenerateControlfiles;
import global.reusables.GenerateReports;
import global.reusables.GenericMethods;
import global.reusables.MailUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import testlink.api.java.client.TestLinkAPIResults;
import DriverMethods.StartUp;
import app.reuseables.ETransStartUp;
import app.reuseables.TestClass;
import app.reuseables.TestLinkData;

public class KFPICTScenario extends StartUp {

	//	static ObjectRepository Common=new ObjectRepository(System.getProperty("user.dir")+"\\ObjectRepository\\CommonFields.xml");
	static String status="Pass";
	static String appVersion;
	String testLinkstatus;
	@Parameters({"ScenarioName","SubScenario","BrowserType"})
	@Test
	public void KFPICTScenarioMain(String ScenarioName,String SubScenario,String BrowserType) throws Exception 
	{
		
		WebDriver driver=driverInt(BrowserType);
		SimpleDateFormat sdf = new SimpleDateFormat(CyborgConstants.TIME_FORMAT);
		@SuppressWarnings("rawtypes")
		Class[] paramString = new Class[3];	
		paramString[0] = WebDriver.class;
		paramString[1] = HashMap.class;
		paramString[2]=int.class;
		String functionName = null;
		String BUILDNAME=null;
		String error= "";
		String classname=ScenarioName;
		try {
			String scnname=classname+SubScenario;
			String relPath=System.getProperty("user.dir")+"\\ControlFiles"+"\\"+scnname+".xml";
			HashMap<String, String> ScenarioDetailsHashMapp = new HashMap<String, String>();
			ScenarioDetailsHashMapp.put("BrowserType", BrowserType);
			ETransStartUp.launchApp(driver,BrowserType,ScenarioDetailsHashMapp);
			ScenarioDetailsHashMapp.clear();
			File xmlFile= new File(relPath);
			DocumentBuilderFactory df=DocumentBuilderFactory.newInstance();
			DocumentBuilder db=df.newDocumentBuilder();
			Document doc=db.parse(xmlFile);		
			NodeList nodeList=doc.getElementsByTagName(CyborgConstants.DRIVER_BOOK_TESTCASE_NAME);
			for (int i = 0; i < nodeList.getLength(); i++) {	
				Calendar functionstarttime = Calendar.getInstance();
				String function_StartTime= sdf.format(functionstarttime.getTime()) ;
				String FieldName=nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue();
				String fieldDataSet=nodeList.item(i).getAttributes().getNamedItem("dataSet").getNodeValue();
				List<String> dataSet=GenericMethods.getDataSetNo(fieldDataSet);
				String [] Name=FieldName.split("/");
				String className=Name[0];
				functionName=Name[1];
				res.setClassName(className);
				res.setFunctionName(functionName);
				int rowNo=1;
				CyborgConstants.logger.info("#####---START---"+scnname+"-"+functionName+"---#####");
				for(int j=0;j<dataSet.size();j++)
				{
					Class c=Class.forName(className);
					Method m=c.getDeclaredMethod(functionName,paramString);
					HashMap<String, String> ScenarioDetailsHashMap = new HashMap<String, String>();
					ScenarioDetailsHashMap.put("ScenarioName", ScenarioName);
					ScenarioDetailsHashMap.put("SubScenarioNo", SubScenario);
					ScenarioDetailsHashMap.put("BrowserType", BrowserType);
					ScenarioDetailsHashMap.put("ClassName", className);
					ScenarioDetailsHashMap.put("DataSetNo", dataSet.get(j));
					ScenarioDetailsHashMap.put("FunctionName", functionName);
					//appVersion=GenericMethods.getAttribute(Common.getElement(driver, "Kewill_AppVersion", ScenarioDetailsHashMap, 20), "title");
					//GenericMethods.setPropertyValue("applicationVer", appVersion, configurationStructurePath);
					try{
						m.invoke(null,driver,ScenarioDetailsHashMap,rowNo);
						/*updateTestresult(BrowserType, functionName, BUILDNAME,
								classname);*/
						ScenarioDetailsHashMap.clear();

					}catch (Exception e) {
						e.printStackTrace();	
						/*System.out.println("inner catch");
							System.out.println(""+e.getMessage());*/
						status ="Fail";
						testLinkstatus=TestLinkAPIResults.TEST_FAILED;
						CyborgConstants.logger.fatal("^^^^^^^^^^^^^^^"+ScenarioName+"_"+SubScenario+"_"+functionName+"----------->"+status+"---FATAL ERROR::::"+e);
						if(SubScenario.equalsIgnoreCase("Web_Based_Plan_Board"))
						{
							break;
						}
					}

				}

				Calendar functionEndTime = Calendar.getInstance();
				String function_Endtime=sdf.format(functionEndTime.getTime());
				String functionExecutedTime=GenericMethods.Time(function_StartTime, function_Endtime);
				String scenarioDetails = ScenarioName+"_"+BrowserType;
				GenericMethods.setValueDriverSheet(scenarioDetails, SubScenario,functionName, res.getDataSetNo()+"",FieldName , status+"",functionExecutedTime);
				ArrayList<String> buildInfo=TestLinkData.getBuildInfo(GenericMethods.getPropertyValue("TeslinkProjectName", GenerateControlfiles.path), GenericMethods.getPropertyValue("AutomationTestPlan", GenerateControlfiles.path));
				for (int j = 0; j < buildInfo.size(); j++) {
					BUILDNAME=buildInfo.get(j);
					if(BUILDNAME.contains(BrowserType)){
						//System.out.println("BUILDNAME==="+BUILDNAME+"browsername***"+BrowserType);
						break;
					}
				}
				if(status.contains("Fail")){
					GenericMethods.captureScreenshot(driver, new File
							(reportStructurePath+"\\"+res.getExectionDateTime()
									+"\\"+scenarioDetails+"_"+SubScenario+"_"
									+functionName+".jpeg"));
					error="- Please refer Reports in the specified path for results   "+reportStructurePath+"\\"+res.getExectionDateTime();
					TestClass.reportTestCaseResult(GenericMethods.getPropertyValue("TeslinkProjectName",GenerateControlfiles.path), GenericMethods.getPropertyValue("AutomationTestPlan", GenerateControlfiles.path),testCaseap.get(functionName), BUILDNAME, error,TestLinkAPIResults.TEST_FAILED);
					if(SubScenario.equalsIgnoreCase("Web_Based_Plan_Board"))
					{
						break;
					}
					CyborgConstants.logger.fatal("#####-"+scenarioDetails+"_"+SubScenario+"_"
							+functionName+"------------->"+status);
				}else{
					/*	updateTestresult(BrowserType, FieldName,testCaseap.get(functionName), BUILDNAME,
								classname);*/
				}
			}//end for

		} catch (Exception e) {
			/*updateTestresult(BrowserType, functionName, BUILDNAME,
						classname);*/
			//System.out.println("print stack trce");
			CyborgConstants.logger.fatal(e);
			e.printStackTrace();			

		}
		finally
		{
			//				app.reuseables.AppReusables.logout(driver);
			GenericMethods.pauseExecution(3000);
			//driver.quit();
			/*updateTestresult(BrowserType, functionName, BUILDNAME,
						classname);*/
			MailUtil.sendMail("KF 4.0 - PICT Scenario Log File");
			CyborgConstants.logger.info("#####---END---#####");
		}
	}

	public void updateTestresult(String BrowserType, String functionName,String functionNameTl,
			String BUILDNAME, String classname) {
		String testLinkstatus="";
		String Description = "KL:";
		try {
			String assertDesc=GenerateReports.getAssertionDetails( classname,functionName, BrowserType);
			if(assertDesc.contains("#")){
				String[] assertDescription=assertDesc.split("#");
				if(assertDescription.length>0&&assertDescription[0].equalsIgnoreCase("Fail")){
					testLinkstatus=TestLinkAPIResults.TEST_FAILED;
					if(assertDescription[1]!=null){
						Description= assertDescription[1];
					}else{
						//Description="Change in Locator . Please Check.";
					}
				}
				else{
					if(assertDescription.length>0&&assertDescription[0].equalsIgnoreCase("Pass")){
						testLinkstatus=TestLinkAPIResults.TEST_PASSED;
						//Description="Test data for KL TransportManagement scenario:"+"\n"+ "FileName:nlrd;Principal:NL-PANABERG";
					}
				}
				Description=assertDescription[1];
			}
			else{

				testLinkstatus=TestLinkAPIResults.TEST_FAILED;
				Description=reportStructurePath;
			}
			TestClass.reportTestCaseResult(GenericMethods.getPropertyValue("TeslinkProjectName",GenerateControlfiles.path), GenericMethods.getPropertyValue("AutomationTestPlan", GenerateControlfiles.path),functionNameTl, BUILDNAME, Description, testLinkstatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
}