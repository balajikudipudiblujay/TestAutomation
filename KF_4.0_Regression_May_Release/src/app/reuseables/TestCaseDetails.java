package app.reuseables;

import global.reusables.CyborgConstants;
import global.reusables.ExcelUtils;
import global.reusables.GenerateControlfiles;
import global.reusables.GenericMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import testlink.api.java.client.TestLinkAPIClient;
import testlink.api.java.client.TestLinkAPIException;
import testlink.api.java.client.TestLinkAPIResults;

public class TestCaseDetails {
	static String ProjectName=GenericMethods.getPropertyValue("TeslinkProjectName",
  			GenerateControlfiles.path);
	static String TestPlan=GenericMethods.getPropertyValue("AutomationTestPlan",
  			GenerateControlfiles.path);
	static String TestSuite=GenericMethods.getPropertyValue("TestLinkTestSuite",
  			GenerateControlfiles.path);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str="Order/entry";
		//str=str.replace("\\/", "$");
		if(str.contains("/")){
			 str=str.replace("/", "#");
			System.out.println("strs===="+str);
			
			
		}
		HashMap<String , String > Mps=getTestCaseMapped("Kewill_Logistics_6_2_4","Generic_Login_6_3_Q2");
		
		System.out.println("-----"+Mps.get("20140905_12_03_54_Show_hide_columns")+"----"+Mps.size()+Mps.get("Generic_Login"));
			
	}

	public static HashMap<String, String> getTestCaseMapped(String scenarioName, String SubScenario) {
		HashMap<String, String> tcMap= new HashMap<String, String>();
		TestLinkAPIClient testlinkAPIClient = new TestLinkAPIClient(CyborgConstants.DEVKEY,CyborgConstants.URL);
		try {
			String ProjectId=null;
			String TestSuiteId=null;
			ArrayList<Integer> TestSuitesId = new ArrayList<Integer>();
			TestLinkAPIResults  projResult=  testlinkAPIClient.getProjects();
		    for (int i = 0; i < projResult.size(); i++) {
		    	//System.out.println(""+projResult.getValueByName(i, "name"));
		  	if(projResult.getValueByName(i, "name").equals(ProjectName)){
		  		 ProjectId= (String) projResult.getValueByName(i, "id");
		  		 break;
		  	}
		  	
			}
			   String TestPalns[]= TestPlan.split(",");
			   for (int k = 0; k < TestPalns.length; k++) {
				TestLinkAPIResults  testSuiteResults=testlinkAPIClient.getTestSuitesForTestPlan(ProjectName, TestPalns[k]);
				//System.out.println("TestPalns[k]==="+TestPalns[k]);
				if(TestPalns[k].contains(" ")||TestPalns[k].contains(".")){
					TestPalns[k]=TestPalns[k].replace(" ", "_").replace(".", "_");
				}
				System.out.println("Test Plans==="+TestPalns[k]+"scenarioName====="+scenarioName);
				if(TestPalns[k].equalsIgnoreCase(scenarioName)){
					//System.out.println(testSuiteResults.size());
					for (int l = 0; l < testSuiteResults.size(); l++) {
						/*System.out.println("TestSuite==="+TestSuite);
						System.out.println("Suite Name==="+testSuiteResults.getValueByName(l, "name"));*/
						String TestSuites[]= TestSuite.split(",");
						/*System.out.println("TestSuites.length==="+TestSuites.length);
						System.out.println("Tl:::::::"+testSuiteResults.getValueByName(l, "name"));*/
					
						for (int z = 0; z < TestSuites.length; z++){
							//System.out.println("TestSuites[z]:::::"+TestSuites[z]);
							//System.out.println(testSuiteResults.getValueByName(l, "name"));
								if(testSuiteResults.getValueByName(l, "name").equals(TestSuites[z])){
								 TestSuiteId= (String) testSuiteResults.getValueByName(l, "id");
								// System.out.println("SuiteNamer==="+testSuiteResults.getValueByName(l, "name"));
								String SuiteName=(String)testSuiteResults.getValueByName(l, "name");
								//System.out.println("TestSuite==="+SuiteName);
								if(SuiteName.contains(" ")||SuiteName.contains(".")||SuiteName.contains("-")||SuiteName.contains("/")||SuiteName.contains("_(")){
									//System.out.println("SuiteName before==="+SuiteName);
									SuiteName=	SuiteName.replace(" ", "_").replace(".", "_").replace("-", "_").replace("/", "_").replace("_(", "_");
									if(SuiteName.endsWith(")")){
										SuiteName=SuiteName.replace(")", "").trim();
									}
									//System.out.println("SuiteName after==="+SuiteName);
								}
								if(SuiteName.equalsIgnoreCase(SubScenario)){
								tcMap=getTestCases(testlinkAPIClient, ProjectId, TestSuiteId,SuiteName,tcMap);
								}
								}
						}
					}
				}
				
								
		    }
			   
		  
		} catch (TestLinkAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tcMap;
	}
	
	private static HashMap<String, String> getTestCases(TestLinkAPIClient testlinkAPIClient,
			String ProjectId, String TestSuiteId,String SuiteName,HashMap<String, String>tcMap) throws TestLinkAPIException {
				TestLinkAPIResults  testCasesResult=  testlinkAPIClient.getCasesForTestSuite(Integer.parseInt(ProjectId), Integer.parseInt(TestSuiteId));
				for (int j = 0; j < testCasesResult.size(); j++) {
				
		    	String ExecutionType=(String) testCasesResult.getValueByName(j, "execution_type");
		    	String TestCaseName=null;
		    	TestCaseName=(String) testCasesResult.getValueByName(j, "name");
		    	String UpdatedTestCase= null;;
	    		 if(TestCaseName.contains(" ("))
	    		 {
	    			 UpdatedTestCase= TestCaseName.replace(" (","#");
		    		String TestCases[]=UpdatedTestCase.split("#");
		    		//System.out.println("Test Case[0]===="+TestCases[0]);
		    		if((TestCases[0].contains(" ")||TestCases[0].contains("/"))||(TestCases[0].contains(" ")&&TestCases[0].contains("/"))){
		    			UpdatedTestCase=TestCases[0].replace(" ", "_");
		    			UpdatedTestCase=UpdatedTestCase.replace("/", "_");
		    		}else{
		    			UpdatedTestCase=TestCases[0];
		    		}
		    		
	    		 }else if(TestCaseName.contains(" ")&&TestCaseName.contains("/")&&TestCaseName.contains(":")&&TestCaseName.contains("-")){
	    				 UpdatedTestCase=TestCaseName.replace(" ", "_").replace("/", "_").replace(":", "_").replace("-", "_");
	    		 }
	    		 else if(TestCaseName.contains(" ")&&TestCaseName.contains("/")&&TestCaseName.contains(":")){
	    				 UpdatedTestCase=TestCaseName.replace(" ", "_").replace("/", "_").replace(":", "_");
	    		 }
	    		 else if(TestCaseName.contains(" ")&&TestCaseName.contains("/")){
	    			 UpdatedTestCase=TestCaseName.replace(" ", "_").replace("/", "_").replace(":", "_");
	    		 }
	    		 else if(TestCaseName.contains(" - ")&&TestCaseName.contains(" ")){
	    			 UpdatedTestCase=TestCaseName.replace(" - ", "_").replace(" ", "_");
	    		 }
	    		 else if(TestCaseName.contains(" ")){
	    			 UpdatedTestCase=TestCaseName.replace(" ", "_");
	    		 }
	    		 else if(TestCaseName.contains(" - ")){
	    			 UpdatedTestCase=TestCaseName.replace(" - ", "_");
	    		 }
	    		 else{
	    			 UpdatedTestCase= TestCaseName;
	    		 }
	    		  //System.out.println(TestCaseName+"^^^^^^^^^^^^^"+UpdatedTestCase);
	    		 
	    		  tcMap.put(UpdatedTestCase,TestCaseName);
		    	}
				return tcMap;
			
	}
}
