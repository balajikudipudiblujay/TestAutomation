package app.reuseables;
import global.reusables.CyborgConstants;
import global.reusables.ExcelUtils;
import global.reusables.GenerateControlfiles;
import global.reusables.GenericMethods;

import java.util.ArrayList;

import testlink.api.java.client.TestLinkAPIClient;
import testlink.api.java.client.TestLinkAPIException;
import testlink.api.java.client.TestLinkAPIResults;


public class TestLinkData{

	/**
	 * @param args
	 */
	static String ProjectName=GenericMethods.getPropertyValue("TeslinkProjectName",
  			GenerateControlfiles.path);
	static String TestPlan=GenericMethods.getPropertyValue("AutomationTestPlan",
  			GenerateControlfiles.path);
	static String TestSuite=GenericMethods.getPropertyValue("TestLinkTestSuite",
  			GenerateControlfiles.path);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestLinkAPIClient testlinkAPIClient = new TestLinkAPIClient(CyborgConstants.DEVKEY,CyborgConstants.URL);
		try {
			String ProjectId=null;
			String TestSuiteId=null;
			ArrayList<Integer> TestSuitesId = new ArrayList<Integer>();
			TestLinkAPIResults  projResult=  testlinkAPIClient.getProjects();
		    for (int i = 0; i < projResult.size(); i++) {
		  	if(projResult.getValueByName(i, "name").equals(ProjectName)){
		  		 ProjectId= (String) projResult.getValueByName(i, "id");
		  		 System.out.println(projResult.getValueByName(i, "name"));
		  		 break;
		  		 
		  	}
			}
		   
			   String TestPalns[]= TestPlan.split(",");
			   for (int k = 0; k < TestPalns.length; k++) {
				TestLinkAPIResults  testSuiteResults=testlinkAPIClient.getTestSuitesForTestPlan(ProjectName, TestPalns[k]);
				//System.out.println("TestPalns[k]==="+TestPalns[k]);
				for (int l = 0; l < testSuiteResults.size(); l++) {
					//System.out.println("TestSuite==="+TestSuite);
					//System.out.println("Suite Name==="+testSuiteResults.getValueByName(l, "name"));
					String TestSuites[]= TestSuite.split(",");
					for (int z = 0; z < TestSuites.length; z++){
							if(testSuiteResults.getValueByName(l, "name").equals(TestSuites[z])){
							System.out.println(testSuiteResults.getValueByName(l, "name").equals(TestSuites[z]));
							 TestSuiteId= (String) testSuiteResults.getValueByName(l, "id");
							// System.out.println("SuiteNamer==="+testSuiteResults.getValueByName(l, "name"));
							String SuiteName=(String)testSuiteResults.getValueByName(l, "name");
							 getTestCases(testlinkAPIClient, ProjectId, TestSuiteId,SuiteName);
							}
					}
				}
								
		    }
			   
		  
		} catch (TestLinkAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void getTestCases(TestLinkAPIClient testlinkAPIClient,
			String ProjectId, String TestSuiteId,String SuiteName) throws TestLinkAPIException {
				TestLinkAPIResults  testCasesResult=  testlinkAPIClient.getCasesForTestSuite(Integer.parseInt(ProjectId), Integer.parseInt(TestSuiteId));
				if(SuiteName.contains(" ")||SuiteName.contains(".")||SuiteName.contains("-")||SuiteName.contains("/")||SuiteName.contains("_(")){
					System.out.println("SuiteName before==="+SuiteName);
					SuiteName=	SuiteName.replace(" ", "_").replace(".", "_").replace("-", "_").replace("/", "_").replace("_(", "_");
					if(SuiteName.endsWith(")")){
						SuiteName=SuiteName.replace(")", "").trim();
					}
					System.out.println("SuiteName after==="+SuiteName);
				}
				for (int j = 0; j < testCasesResult.size(); j++) {
				String TestCaseName=null;
		    	String ExecutionType=(String) testCasesResult.getValueByName(j, "execution_type");
		    	//System.out.println("ExecutionType===="+ExecutionType);
		    	if(ExecutionType.equalsIgnoreCase("2")){
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
		    		  System.out.println(UpdatedTestCase+"^^^^^^^^^^^^^"+TestCaseName);
		    		/*
		    	TestCaseName=(String) testCasesResult.getValueByName(j, "name");
	    		 if(TestCaseName.contains(" ("))
	    		 {
	    			 TestCaseName= TestCaseName.replace(" (","#");
		    		String TestCases[]=TestCaseName.split("#");
		    		if((TestCases[0].contains(" ")||TestCases[0].contains("/"))||(TestCases[0].contains(" ")&&TestCases[0].contains("/")))
		    		TestCaseName=TestCases[0].replace(" ", "_");
		    		TestCaseName=TestCaseName.replace("/", "_");
	    		 }else{
	    			 if(TestCaseName.contains(" ")||TestCaseName.contains("/"))
	    		  TestCaseName=TestCaseName.replace(" ", "_");
	    			 TestCaseName=TestCaseName.replace("/", "_");
	    		 }
	    		  System.out.println(TestCaseName);
	    		 
	    		// ExcelUtils.getCellDataTL("ControlSheet", "TEST_CASE",TestCaseName,"ie",SuiteName);
		    	*/}
		    	}
			
	}

	public static ArrayList<String> getBuildInfo(String ProjectName, String TestPlan) throws TestLinkAPIException {
		TestLinkAPIClient testlinkAPIClient = new TestLinkAPIClient(CyborgConstants.DEVKEY,CyborgConstants.URL);
		String buildName;
		ArrayList<String> buildInfo= new ArrayList<String>();
		TestLinkAPIResults buildResults=  testlinkAPIClient.getBuildsForTestPlan(ProjectName, TestPlan);
		for (int i = 0; i < buildResults.size(); i++) {
			System.out.println("******"+buildResults.getValueByName(i, "id")+"*************"+buildResults.getValueByName(i, "name"));
			 buildName= (String) buildResults.getValueByName(i, "name");
			 buildInfo.add(buildName);
			}
		return buildInfo;
	}
	
	 /*for (int z = 0; z < TestSuites.length; z++){
		   System.out.println(testSuiteResults.getValueByName(l, "name").equals(TestSuites[z]));
			if(testSuiteResults.getValueByName(l, "name").equals(TestSuites[z])){
			 TestSuiteId= (String) testSuiteResults.getValueByName(l, "id");
			 TestLinkAPIResults  testCasesResult=  testlinkAPIClient.getCasesForTestSuite(Integer.parseInt(ProjectId), Integer.parseInt(TestSuiteId));
				for (int i = 0; i < testCasesResult.size(); i++) {
			    	String TestCaseName=null;
			    	String ExecutionType=(String) testCasesResult.getValueByName(i, "execution_type");
			    	if(ExecutionType.equalsIgnoreCase("2")){
			    		 TestCaseName=(String) testCasesResult.getValueByName(i, "name");
			    		String TestCaseId=(String) testCasesResult.getValueByName(i, "id");
			    		 String buildName="";
			    		 String buildName2="";
			    		 ArrayList<String > buildInfo =getBuildInfo(ProjectName, TestPlan);
			    		 for (int j = 0; j < buildInfo.size(); j++) {
			    			 buildName=buildInfo.get(j);
			    			 String[]buildNames= buildName.split("_");
			    			 buildName2=buildName2+","+buildNames[2];
			    			 buildName=buildName2.replaceFirst(",", " ");
			    			 buildName=buildName.trim();
						}
			    		// System.out.println("buildName========"+buildName);
			    		 ExcelUtils.getCellDataTL("ControlSheet", "TEST_CASE",TestCaseName,buildName);
			    	}
				}
			}
	   }*/

}
