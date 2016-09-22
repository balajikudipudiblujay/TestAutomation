package app.reuseables;
import global.reusables.CyborgConstants;
import global.reusables.ExcelUtils;
import global.reusables.GenerateControlfiles;
import global.reusables.GenericMethods;

import java.util.ArrayList;

import testlink.api.java.client.TestLinkAPIClient;
import testlink.api.java.client.TestLinkAPIException;
import testlink.api.java.client.TestLinkAPIResults;


public class ReadTestLinkData{

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
		TestLinkAPIClient testlinkAPIClient = new TestLinkAPIClient(CyborgConstants.DEVKEY, CyborgConstants.URL);
	
		//TestLinkAPIClient testlinkAPIClient = new TestLinkAPIClient(CyborgConstants.DEVKEY,CyborgConstants.URL);
		try {
			String ProjectId=null;
			String TestSuiteId=null;
			testlinkAPIClient.ping();
			 TestLinkAPIResults  projResult=  testlinkAPIClient.getProjects();
			    for (int i = 0; i < projResult.size(); i++) {
			  	if(projResult.getValueByName(i, "name").equals(ProjectName)){
			  		System.out.println("name:::"+projResult.getValueByName(i, "name"));
			  		ProjectId= (String) projResult.getValueByName(i, "id");
			  		 break;
			  	}
				}
			    System.out.println("TestPlan----"+TestPlan);
				TestLinkAPIResults  testSuiteResults=testlinkAPIClient.getTestSuitesForTestPlan(ProjectName, TestPlan);
				System.out.println(testSuiteResults.size());
				for (int i = 0; i < testSuiteResults.size(); i++) {
					System.out.println(testSuiteResults.getValueByName(i, "name"));
					if(testSuiteResults.getValueByName(i, "name").equals(TestSuite)){
					 TestSuiteId= (String) testSuiteResults.getValueByName(i, "id");
					}
					}
				
				System.out.println("proj ID::"+Integer.parseInt(ProjectId));
				System.out.println("TestSuiteId ID::"+Integer.parseInt(TestSuiteId));
				
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
			    		 System.out.println("buildName========"+buildName);
			    		 ExcelUtils.getCellDataTL("ControlSheet", "TEST_CASE",TestCaseName,buildName,"ABC");
			    	}
		    }
		  
				
		} catch (TestLinkAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	

}
