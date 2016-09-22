package global.reusables;


public class AssertionResultBean {
	private String actualValue;
	private String scenarioClassName;
	private String assertionDescription;
	private String elementId;
	private String expectedValue;
	private String result;
	private String time;
	private boolean booleanResult;
	public String getActualValue() {
		return actualValue;
	}
	public void setActualValue(String actualValue) {
		this.actualValue = actualValue;
	}
	public String getScenarioClassName() {
		return scenarioClassName;
	}
	public void setScenarioClassName(String scenarioClassName) {
		this.scenarioClassName = scenarioClassName;
	}
	public String getAssertionDescription() {
		return assertionDescription;
	}
	public void setAssertionDescription(String assertionDescription) {
		this.assertionDescription = assertionDescription;
	}
	public String getElementId() {
		return elementId;
	}
	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
	public String getExpectedValue() {
		return expectedValue;
	}
	public void setExpectedValue(String expectedValue) {
		this.expectedValue = expectedValue;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public void setResult(boolean result) {
		this.booleanResult = result;
		if (result) {
		      this.result = "PASS";
		    } else {
		      this.result = "FAIL";
		    }
	}
	
	
}
