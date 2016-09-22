package global.reusables;

import java.util.Map;

public class CommonBean {
	private  String scenarioClassName;
	private  String dataSetNo;
	private  String ExectionDateTime;
	private  String functionName;
	private String className;
	private  String SubScenarioDetails;
	private String browser;
	private Map appTestCaseNamesMap;

	
	public Map getAppTestCaseNamesMap() {
		return appTestCaseNamesMap;
	}
	public void setAppTestCaseNamesMap(Map appTestCaseNamesMap) {
		this.appTestCaseNamesMap = appTestCaseNamesMap;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public  String getSubScenarioDetails() {
		return SubScenarioDetails;
	}
	public void setSubScenarioDetails(String subScenarioDetails) {
		this.SubScenarioDetails = subScenarioDetails;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public   String getFunctionName() {
		return functionName;
	}
	public  void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public  String getScenarioClassName() {
		return scenarioClassName;
	}
	public  void setScenarioClassName(String scenarioClassName) {
		this.scenarioClassName = scenarioClassName;
	}
	public  String getDataSetNo() {
		return dataSetNo;
	}
	public  void setDataSetNo(String dataSetNo) {
		this.dataSetNo = dataSetNo;
	}
	public  void setExectionDateTime(String ExectionDateTime) {
		this.ExectionDateTime=ExectionDateTime;
	}
	public  String getExectionDateTime() {
		return ExectionDateTime;
	}

}
