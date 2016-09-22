package app.reuseables;

import global.reusables.CyborgConstants;
import testlink.api.java.client.TestLinkAPIClient;
import testlink.api.java.client.TestLinkAPIException;


public class TestClass{

	public static void reportTestCaseResult(String projectName,String testplanName,

			String testcaseName, String buildName,String msg,String result)

	throws TestLinkAPIException {

		TestLinkAPIClient testlinkAPIClient = new TestLinkAPIClient(CyborgConstants.DEVKEY,CyborgConstants.URL);
		System.out.println("ProjectName====="+projectName);
		System.out.println("testPlan===="+testplanName);
		System.out.println("buildName====="+buildName);
		System.out.println("testcaseName====="+testcaseName);
		System.out.println("msg====="+msg);
		System.out.println("result====="+result);
		testlinkAPIClient.reportTestCaseResult(projectName, testplanName,
				testcaseName, buildName, msg, result);
		System.out.println("updated sucessfully");
		
	}

}