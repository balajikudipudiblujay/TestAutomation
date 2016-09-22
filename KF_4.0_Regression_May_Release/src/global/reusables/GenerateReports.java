package global.reusables;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import DriverMethods.StartUp;

public class GenerateReports extends StartUp {
	static String path = System.getProperty("user.dir")
			+ "\\Configurations\\base.properties";
	
	public static String htmlTdTag_green = "<td class=\"second\" style=\"color:green\">";
	public static String htmlTdTag_red = "<td class=\"second\" style=\"color:red\">";
	public static String htmlTdTag_brown = "<td class=\"second\" style=\"color:brown\">";
	public static String htmlStyleTag_greencolor = "\" style=\"color:green\">";
	public static String htmlStyleTag_redcolor = "\" style=\"color:red\">";
	public static String htmlStyleTag_browncolor = "\" style=\"color:brown\">";
	
	public static String htmlThTag = "<th class=\"header\" width=\"10%\">";
	public static String htmlThEndTag = "</th>";
	public static String htmlTdTagColspan = "<td class=\"second\" colspan=\"12\">";
	public static String htmlTdTag = "<td class=\"second\">";
	public static String htmlTdTagClass = "<td class=\"div_bg_heading\">";
	public static String htmlTdStartTag = "<td>";
	public static String htmlTdEndTag = "</td>";
	//Srikala
	public static String htmlTHEndTag = "</th>";
	public static String headingTRTag = "<tr><td class=\"div_bg_heading\">";
	//Srikala
	public static String headingTHTag = "<tr><th class=\"div_bg_heading\">";
	/*public static String headingTH1Tag = "<th class=\"div_bg_heading\">";*/
	//
	public static String headingTDTag = "</td><td class=\"div_bg_heading\">";
	public static String TRTDEndTag = "</td></tr>";
	public static String htmlLinkTag = "<a class=\"second\" href=\"";
	public static String screenshot_htmlLinkTag = "<a class=\"second\"  target=\"_blank\" href=\"";
	public static String htmlStyleTag = "\" style=\"color:#ffffff\">";
	public static String htmlStyleTag1 = "\" style=\"color:#046CB6\">";
	public static String htmlTRTag = "<tr>";
	public static String htmlTREndTag = "</tr>";
	public static String htmlEndTableTag = "</table>";
	public static String htmlTableStartTag = "</table>";
	public static String htmlTableStart_borderTag = "<table class=\"scenarioDetails\" border=\"0\">";
	public static String htmlTableTag = "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"40%\"><tr><td class=\"con\" valign=\"top\"><div class=\"formTable\">";
	public static String htmlTableEndTag = "<table class=\"formTable\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" height=\"100%\"><tr>";
	/*public static String browserVersion = GenericMethods.getPropertyValue(
			"browserVer", configurationStructurePath);*/
	/*public static String browser = GenericMethods.getPropertyValue("browser",
			configurationStructurePath);*/
	/*public static String url = GenericMethods.getPropertyValue("url",
			configurationStructurePath);*/
	//Added for fetching dynamic url of Application
	//public static String url = GenericMethods.getAppUrl();
	//Short term fix, for reading URL, actually it should be read from Scenariodriverworkbook.xls
	public static String url = GenericMethods.getPropertyValue("url",
			configurationStructurePath);
	public static String username = GenericMethods.getPropertyValue("userName",
			configurationStructurePath);
	public static String os = GenericMethods.getPropertyValue("os",
			configurationStructurePath);
	/*public static  String appVersion = GenericMethods.getPropertyValue(
			"applicationVer", configurationStructurePath);*/
	public static String backButton = "<a class=\"backButtonStyle\" href=\"javascript:history.back()\">Back</a>";

	/**
	 * @Description:This method is to design HTML Page Header which consists of
	 *                   style and css sheets
	 * @Date:April 21st 2014
	 * @return:It returns Page header.
	 * @author Pavan,Sandeep,Priyaranjan
	 */
	public static String getHTMLPageHeader() {
		String headerBg1=System.getProperty("user.dir");
		if(headerBg1.contains("\\")){
			headerBg1=headerBg1.replace("\\", "/");
		}		
			//String headerBg="file://D:/Workspace/Framework/Cyborg_TestLink_16_July_Rep/Reports/Image/headerImg.jpg";
				
				//System.out.println("===headerBg1==="+headerBg1);
				String strCSS = " body { 	" + "margin: 0;"
				+ "padding: 0;" + "background:url(\"file://"
				+ headerBg1
				+ "/Reports/Image/headerBg.jpg\")no-repeat font: normal 12px Arial;color: #222;}"
				+ "#container { width: 100%; margin: 0 auto; }"
				+ "#logo { padding: 11px 0px;}"
				/*+ "#headerTitle { background: url(\"file://"+System.getProperty("user.dir")+"/Reports/Image/headerImg.jpg\")" +"no-repeat left top;	font: normal 26px \"Open Sans\", Arial;color: #FFF;padding:34px 0px;margin: 0;}"*/
				+"#headerTitle {background:url(\"file://"+ headerBg1+"/Reports/Image/headerImg.jpg\") no-repeat left top;	font: normal 26px \"Open Sans\", Arial;	color: #FFF;padding:34px 0px;margin: 0;			}"				
				+ ".formTable {clear:both;padding-bottom:30px;padding-left:1px;padding-bottom:1px; font-size:90%;height:1%; border:0px solid #2166ab;margin-top: 10px;} "
				+ ".formTable table {border:1px solid #fff;width:100%;height:25%} "
				+ ".formTable tr {background-color:#fff;} .formTable th {height:35px;} "
				+ ".formTable td {height:25px;} "
				+ ".formTable .header {padding-left: 5px; background-color: #007ac3;border: none;border-right: #abddff solid 1px;vertical-align:center;color:#ffffff;font-family:Arial;font-weight:bold;font-size:12px;text-align:left;} "
				+ ".formTable .first {padding-left:5px;background-color:#C5C7DC;border-right:0px solid #fff;color:#046CB6;font-family:Arial;font-weight:Bold;font-size:11px;} "
				+ ".formTable .second  {padding-left:5px;background-color: #ebf6fd;	border-right: #abddff solid 1px;color: #000;font-family:Arial;font-weight:bold;font-size:11px;line-height:22px; vertical-align:top} "
				+ ".formTable .third  {padding-left:5px;background-color:#ecedef;border-right:0px solid #fff;color:#000000;font-family:Arial;font-weight:Regular;font-size:11px;line-height:22px; vertical-align:top} "
				+ ".formTable a:link, a:visited {color:#046CB6;} "
				+ ".formTable .a {color:#046CB6;font-family:Arial;font-weight:Regular;font-size:11px;text-decoration:underline;} "
				+ ".formTable .text {text-align:left;} "
				+ ".formTable .num {text-align:right;}  "
				+ ".row_alter{ 	background-color:#ecedef; 	}  "
				+ "table {width:100%;} "
				+ ".div_bg_heading{font-family: Arial, Helvetica, sans-serif; 	font-size: 15px; 	font-weight:bold; 	color: #046CB6; 	padding-top:12px; 	padding-left:8px; 	padding-bottom:8px; 	 	}   "
				+ "a.link {font-family:\"Arial,verdana,tahoma\"; 	font-size:11px; 	font-weight:normal; 	color: #013d72; 	text-decoration:underline; 	cursor:pointer; } "
				+ "a.link:hover {font-family:\"Arial,verdana,tahoma\"; 	font-size:11px; 	font-weight:normal; 	color: #013d72; 	text-decoration:none; } /* Bottom */ "
				+ ".footer{height:21px;background-color:#2166ab; 	border-top:1px solid #e6e6e6;	 } "
				+ ".ALTText{background: none repeat scroll 0 0;    border-bottom: 0px solid #FFFFFF;    border-left: 0px solid #FFFFFF;   border-right: 0px solid white;    color: White;    font-family: Tahoma,Helvetica,sans-serif;    font-size: 35px;    font-weight: bold;    height: 70px;    padding-bottom: 5px;    padding-left: 1px;    padding-top: 4px;    text-align: center;}"
				+ ".Header{background: none repeat scroll 0 0;	border-bottom: 0px solid #FFFFFF;	border-left: 0px solid #FFFFFF;	border-right: 0px solid white;font-family: Arial, Helvetica, sans-serif;font-size: 50px; font-weight: bold;	padding-bottom: 5px;padding-left: 1px;padding-top: 4px;	text-align: right;}"
				+ ".TextHeader {background: none repeat scroll 0 0;border-bottom: 0px solid #FFFFFF;border-left: 5px solid #FFFFFF;border-right: 0px solid white;font-family: Arial, Helvetica, sans-serif;font-size: 18px;font-weight: bold;	padding-bottom: 5px;padding-left: 1px;padding-top: 4px;text-shadow: 4px 8px 3px #A6A6A6;}"
				+ ".subHeader {background: none repeat scroll 0 0;border-bottom: 0px solid #FFFFFF;border-left: 5px solid #FFFFFF;border-right: 0px solid white;color:#046CB6;font-family: Arial, Helvetica, sans-serif;font-size: 18px;font-weight: bold;padding-bottom: 5px;padding-left: 300px;padding-top: 4px;text-decoration: underline;}"
				+".SummaryHeader{background-color: #f2a209;	border-radius: 10px; margin-top: 10px; color: #FFF !important;	padding: 10px;}"
				+".SummaryHeaderTest{background-color: #f2a209;	border-radius: 10px; margin-top: 10px; color: #FFF !important;	padding: 0;}"
				+".formTable table td a { background: none !important; color: #000 !important;}"
				+".SummaryHeader td {color: #FFF !important;}"
				+".formTable table { background-color: #ebf6fd;	border: #b5dbf3 solid 1px;}"
				+".formTable table td a	{ background: none !important;color: #000 !important;}"
				+".scenarioDetails { margin-top: 10px; border: #abddff solid 1px; background-color: #ebf6fd;}"
				+".scenarioDetails th { padding-left: 5px; background-color: #007ac3; border: none; border-right: #abddff solid 1px; color: #FFF; text-align: left; }"
				+".scenarioDetails td {padding-left:5px; border-right: #abddff solid 1px; color: #000; font-size: 12px !important;}"
				+ ".backButtonStyle {background-color: #444; border: none; cursor: pointer;	color: #FFF; font-weight: bold;	padding: 8px 12px; float: right; margin-top: 10px; text-decoration: none;}";	
				//System.out.println("strCSS====="+strCSS);
				String logPic="file://"+System.getProperty("user.dir")+"/Reports/Image/logo.jpg";
				//System.out.println("logPic===="+logPic);
				
				String strPageHeader = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">"
				+ "<html>"
				+ "<head><title>Result</title>"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">"
				+ "<style type=\"text/css\">"

				+ strCSS
				+ "</style>"
				/*+"<link rel=\"stylesheet\" type=\"text/css\" href=\"file://D:/Workspace/Framework/Cyborg_TestLink_16_July_Rep/Configurations/style.css\" />"*/
				+ "</head><body>"
				+ "<div id=\"container\">"
				/*+ "<Table>"
				+ "<TR>"
				+ "<td id=\"logo\"><img src="+logPic+" alt=\"some_text\"></td>"
				+

				"</TR>"
				+ "</Table>"
				+
				"<Table>"
				+ "<tr>"
				+ "<td id=\"headerTitle\">Automation-Execution Results</td>"
				+ "</tr>" + "</Table>" +*/
				+ "<Table>"
				+ "<TR>"
				+ "<td class=\"Header\">KEWILL</td>"
				+ "<td class=\"TextHeader\">\"Keeping Your Supply Chain In Motion\"</td>"
				+

				"</TR>"
				+ "</Table>"
				+
				"<Table>"
				+ "<tr>"
				+ "<td class=\"subHeader\">Automation-Execution Results</td>"
				+ "</tr>" + "</Table>" +

				"<Table class=\"SummaryHeader\" >" + "</td></tr><tr class=\"header\">";

		return strPageHeader;

	}

	/**
	 * @Description:This method is to generate Scenario level HTML Report
	 * @Date: April 21st 2014
	 * @author Pavan,Sandeep,Priyaranjan
	 */
	public static void mainScreenDetailsReports(String totTime,
			String startTime, String endTime) {
		String filePath = reportStructurePath + "\\"
				+ res.getExectionDateTime() + "\\";
		File mainScreen = new File(filePath + "mainScreenDetails.html");
		try {
			if (!mainScreen.exists()) {
				mainScreen.createNewFile();
			}
			FileWriter fstream = new FileWriter(mainScreen);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(getHTMLPageHeader());
			out.write(backButton);
			out.write(getMainScreenHeaderDetails(totTime, startTime, endTime,
					GenericMethods.getPropertyValue(
							"applicationVer", configurationStructurePath), GenericMethods.getPropertyValue("browser",
									configurationStructurePath), GenericMethods.getPropertyValue(
									"browserVer", configurationStructurePath), url, username, os));
			out.write(getMainScreenDetails("pass", filePath));
			out.close();
			ArrayList<String> BrowserDetails = getScenario_UniqueBrowser();
			// Sandeep: Below for loop is to create Browser wise Scenario
			// Report.
			for (int i = 0; i < BrowserDetails.size(); i++) {
				ScenarioReports((String) BrowserDetails.get(i), totTime,
						startTime, endTime);
			}
			BrowserDetails.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description:This method is to generate Summary level HTML Report
	 * @Date: April 21st 2014
	 * @author Pavan,Sandeep,Priyaranjan
	 */
	public static void startPageDetailsReports() {
		String filePath = reportStructurePath + "\\"
				+ res.getExectionDateTime() + "\\";
		File mainScreen = new File(filePath + "summaryReport.html");
		if (!mainScreen.exists()) {
			try {
				mainScreen.createNewFile();
				FileWriter fstream = new FileWriter(mainScreen);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(getHTMLPageHeader());

				out.write(summaryReport(GenericMethods.getPropertyValue(
						"applicationVer", configurationStructurePath), GenericMethods.getPropertyValue("browser",
								configurationStructurePath), GenericMethods.getPropertyValue(
								"browserVer", configurationStructurePath),
						url, username, os));
				// out.write(getMainScreenDetails("pass",filePath));
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Description:This method is to enter details in Summary level HTML Report
	 * @Date: April 21st 2014
	 * @param version
	 *            : Application Version
	 * @param browser
	 *            : Browser Name
	 * @param browser_version
	 *            : Browser Version
	 * @param url
	 *            : Application URL
	 * @param userName
	 *            : Logged in UserName
	 * @param os
	 *            : System Operating System Name
	 * @return: returns complete summary details report.
	 * @author Pavan,Sandeep,Priyaranjan
	 */

	public static String summaryReport(String version, String browser,
			String browser_version, String url, String userName, String os) {
		int totalScenariosCount = getStatuscount("Pass")
				+ getStatuscount("Fail") + getScenariocount();
		String []appUrl=url.split("\\?");
		if(appUrl.length>0){
			url=appUrl[0];
		}
		String strTestDetails = headingTRTag + "Application URL : " + url
				+ headingTDTag + "Application Version : " + version
				+ TRTDEndTag;
		strTestDetails = strTestDetails + headingTRTag + "Username : "
				+ userName + headingTDTag + "Operating System : " + os
				+ TRTDEndTag;
		strTestDetails = strTestDetails + headingTRTag + "Browser : "
		+ browser + headingTDTag + "Browser Version : " + browser_version
		+ TRTDEndTag;
		strTestDetails = strTestDetails + headingTRTag + "</br>" + headingTDTag
				+ "               " + TRTDEndTag;
		strTestDetails = strTestDetails + headingTRTag + "" + TRTDEndTag;
		// ArrayList BrowserValues = new ArrayList();
		String Browserdetail = null;
		ArrayList<String> BrowserValues = GenerateReports
				.getScenario_UniqueBrowser();
		for (int i = 0; i < BrowserValues.size(); i++) {
				if (Browserdetail == null) {
					Browserdetail = "<th class=\"div_bg_heading\">" + "  " + BrowserValues.get(i).toUpperCase()
							+ "  " + htmlTHEndTag;
				}

				else {
					Browserdetail = Browserdetail + "<th class=\"div_bg_heading\">" 
							+ BrowserValues.get(i).toUpperCase() + "  " + htmlTHEndTag;
				}
		}

		strTestDetails = strTestDetails + htmlEndTableTag
		+ htmlTableStart_borderTag + headingTHTag + "Scenario Details"+htmlTHEndTag
		+Browserdetail + htmlTREndTag;
		Browserdetail = null;
		for (int i = 0; i < BrowserValues.size(); i++) {
			if (Browserdetail == null) {
				Browserdetail = htmlTdTagClass + "  " + getScenarioCount("Yes")
						+ "  " + htmlTdEndTag;
			}

			else {
				Browserdetail = Browserdetail + htmlTdTagClass
						+ getScenarioCount("Yes") + "  " + htmlTdEndTag;
			}
		}
		strTestDetails = strTestDetails + headingTRTag
				+ "Total No Of Test Plan Available:" + htmlTdEndTag
				+ Browserdetail + htmlTREndTag;

		Browserdetail = null;
		for (int i = 0; i < BrowserValues.size(); i++) {
			if (Browserdetail == null) {
				Browserdetail = htmlTdTagClass + "  " + htmlLinkTag
						+ "ScenarioDetails_" + BrowserValues.get(i) + ".html"
						+ htmlStyleTag1
						+ summary_Count(BrowserValues.get(i), "") + "  "
						+ htmlTdEndTag;
			} else {
				Browserdetail = Browserdetail + htmlTdTagClass + "  "
						+ htmlLinkTag + "ScenarioDetails_"
						+ BrowserValues.get(i) + ".html" + htmlStyleTag1
						+ summary_Count(BrowserValues.get(i), "") + "  "
						+ htmlTdEndTag;
			}
		}
		strTestDetails = strTestDetails + headingTRTag
				+ "Total No Of Browser Specific Test Plan:" + htmlTdEndTag
				+ Browserdetail + htmlTREndTag;

		Browserdetail = null;
		for (int i = 0; i < BrowserValues.size(); i++) {
			if (Browserdetail == null) {
				Browserdetail = htmlTdTagClass + "  "
						+ summary_Count(BrowserValues.get(i), "PASS") + "  "
						+ htmlTdEndTag;
			}

			else {
				Browserdetail = Browserdetail + htmlTdTagClass
						+ summary_Count(BrowserValues.get(i), "PASS") + "  "
						+ htmlTdEndTag;
			}
		}
		strTestDetails = strTestDetails + headingTRTag
				+ "Total No Of Pass Test Plan:" + htmlTdEndTag + Browserdetail
				+ htmlTREndTag;

		Browserdetail = null;
		for (int i = 0; i < BrowserValues.size(); i++) {
			if (Browserdetail == null) {
				Browserdetail = htmlTdTagClass + "  "
						+ summary_Count(BrowserValues.get(i), "FAIL") + "  "
						+ htmlTdEndTag;
			}

			else {
				Browserdetail = Browserdetail + htmlTdTagClass
						+ summary_Count(BrowserValues.get(i), "FAIL") + "  "
						+ htmlTdEndTag;
			}
		}
		strTestDetails = strTestDetails + headingTRTag
				+ "Total No Of Fail Test Plan:" + htmlTdEndTag + Browserdetail
				+ htmlTREndTag;

		Browserdetail = null;
		for (int i = 0; i < BrowserValues.size(); i++) {
			if (Browserdetail == null) {
				Browserdetail = htmlTdTagClass + "  "
						+ summary_Count(BrowserValues.get(i), "SKIPPED") + "  "
						+ htmlTdEndTag;
			}

			else {
				Browserdetail = Browserdetail + htmlTdTagClass
						+ summary_Count(BrowserValues.get(i), "SKIPPED") + "  "
						+ htmlTdEndTag;
			}
		}
		strTestDetails = strTestDetails + headingTRTag
				+ "Total No Of Skipped Test Plan:" + htmlTdEndTag
				+ Browserdetail + htmlTREndTag;
		BrowserValues.clear();
		return strTestDetails;
	}

	public static void subScenarioDetailsReports() {
		String filePath = reportStructurePath + "\\"
				+ res.getExectionDateTime() + "\\";
		ArrayList scenarios = GenerateReports.getScenarioNames();
		String scenarioName = "";
		String scenario_timeDetails = "0:0:0";
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		for (int ScenarioRowNo = 0; ScenarioRowNo < scenarios.size(); ScenarioRowNo++) {
			String strSubScenarioDetails = null;
			strSubScenarioDetails = htmlTableTag + htmlTableEndTag + htmlThTag
					+ CyborgConstants.REPORT_SERIAL_NUMBER + htmlThEndTag
					+ htmlThTag + CyborgConstants.REPORT_SCENARIO_NAME
					+ htmlThEndTag + htmlThTag
					+ CyborgConstants.REPORT_SUB_SCENARIO_NO + htmlThEndTag
					+ htmlThTag + CyborgConstants.REPORT_STATUS + htmlThEndTag
					+ htmlThTag + CyborgConstants.REPORTS_TIME + htmlThEndTag
					+ htmlTREndTag;
			ArrayList<String> Subscenario = GenerateReports
					.getScenario_SubScenarioNo((String) scenarios
							.get(ScenarioRowNo));
			int TotalSubScenarioPassCount = 0;
			int TotalSubScenarioFailCount = 0;
			for (int SubScenarioRowNo = 0; SubScenarioRowNo < Subscenario
					.size(); SubScenarioRowNo++) {
				String Status = "";
				int Pass = 0;
				int Fail = 0;
				int Skipped = 0;
				int SlNo = 1;
				int TotalSubScenarioCount = Subscenario.size();

				ArrayList<String> browservalue = GenerateReports
						.getScenario_BrowserUniqueValues(
								(String) scenarios.get(ScenarioRowNo),
								(String) Subscenario.get(SubScenarioRowNo));
				String scenario = (String) scenarios.get(ScenarioRowNo);
				String subscenario = (String) Subscenario.get(SubScenarioRowNo);
				String subscenariono[] = subscenario.split("\\.");
				String subscenariodetails = subscenariono[0];
				scenarioName = scenario;
				for (int BrowserRowNo = 0; BrowserRowNo < browservalue.size(); BrowserRowNo++) {
					String BrowserStatus = SubScenarioDetailsReport(scenario,
							subscenariodetails,
							(String) browservalue.get(BrowserRowNo));
					if (BrowserStatus.equalsIgnoreCase("Pass")) {
						Pass = 1;
					}
					if (BrowserStatus.equalsIgnoreCase("Fail")) {
						Fail = 2;
						break;
					}
					if (BrowserStatus.equalsIgnoreCase("Fail")) {
						Skipped = 3;
					}
				}// Browser For Loop end
				String timeDetails = "0:0:0";
				for (int ExecutionTime = 0; ExecutionTime < browservalue.size(); ExecutionTime++) {
					String subScenario_TimeDetails = subScenarioExecutionTime(
							scenario, subscenariodetails,
							(String) browservalue.get(ExecutionTime));
					if (subScenario_TimeDetails != null) {
						try {
							Date date_timeDetails = timeFormat
									.parse(timeDetails);
							Date date_subScenarioTimeDetails = timeFormat
									.parse(subScenario_TimeDetails);
							long sum = date_timeDetails.getTime()
									+ date_subScenarioTimeDetails.getTime();
							timeDetails = timeFormat.format(new Date(sum));
							// System.out.println("The sum is "+sum);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

				}// Execution Time For Loop end
					// System.out.println("The sum is----- "+timeDetails);
				try {

					Date date_scenarioTimeDetails = timeFormat
							.parse(scenario_timeDetails);
					Date date_subScenarioTimeDetails = timeFormat
							.parse(timeDetails);

					long sum = date_scenarioTimeDetails.getTime()
							+ date_subScenarioTimeDetails.getTime();
					scenario_timeDetails = timeFormat.format(new Date(sum));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				browservalue.clear();
				if (Fail == 2) {
					Status = "FAIL";
					TotalSubScenarioFailCount = TotalSubScenarioFailCount + 1;
				} else if (Pass == 1) {
					Status = "PASS";
					TotalSubScenarioPassCount = TotalSubScenarioPassCount + 1;
				} else {
					Status = "SKIPPED";
				}
				File mainScreen = new File(filePath + scenario
						+ "_SubScenarioDetails" + ".html");
				try {
					if (!mainScreen.exists()) {
						mainScreen.createNewFile();
					}
					FileWriter fstream = new FileWriter(mainScreen);
					BufferedWriter out = new BufferedWriter(fstream);
					out.write(getHTMLPageHeader());
					out.write(backButton);
					out.write(getOtherScreenHeader(TotalSubScenarioCount + "",
							TotalSubScenarioPassCount + "",
							TotalSubScenarioFailCount + "", GenericMethods.getPropertyValue(
									"applicationVer", configurationStructurePath), "", "",
							url, username, os, "Scenarios"));
					String resultPath = scenario + "_" + subscenariono[0] + "_"
							+ "FunctionReport.html";
					strSubScenarioDetails = strSubScenarioDetails + htmlTRTag
							+ htmlTdTag + SlNo + htmlTdEndTag;
					strSubScenarioDetails = strSubScenarioDetails + htmlTdTag
							+ scenario + htmlTdEndTag;
					strSubScenarioDetails = strSubScenarioDetails + htmlTdTag
							+ subscenariono[0] + htmlTdEndTag;
					
					
					if(Status.equalsIgnoreCase("FAIL"))
					{					
						strSubScenarioDetails = strSubScenarioDetails + htmlTdTag
						+ htmlLinkTag + resultPath + htmlStyleTag_redcolor + Status
						+ htmlTdEndTag;
					}
					if(Status.equalsIgnoreCase("PASS"))
					{					
						strSubScenarioDetails = strSubScenarioDetails + htmlTdTag
						+ htmlLinkTag + resultPath + htmlStyleTag_greencolor + Status
						+ htmlTdEndTag;
					}

					if(Status.equalsIgnoreCase("SKIPPED"))
					{					
						strSubScenarioDetails = strSubScenarioDetails + htmlTdTag
						+ htmlLinkTag + resultPath + htmlStyleTag_browncolor + Status
						+ htmlTdEndTag;
					}
					/*strSubScenarioDetails = strSubScenarioDetails + htmlTdTag
							+ htmlLinkTag + resultPath + htmlStyleTag + Status
							+ htmlTdEndTag;*/
					strSubScenarioDetails = strSubScenarioDetails + htmlTdTag
							+ timeDetails + htmlTdEndTag;

					out.write(strSubScenarioDetails);
					SlNo = SlNo + 1;
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			Subscenario.clear();
		}
	}

	/**
	 * Below method is to return Execution Time with respect to scenarioname, subscenariono, browsername
	 * @param ScenarioName : Scenario Name details is to be provided.
	 * @param SubScenarioNO : SubScenario NO details is to be provided.
	 * @param Browsername: Browser Name details is to be provided.
	 * @return
	 * @author Sandeep Jain
	 */
	public static String subScenarioExecutionTime(String ScenarioName,
			String SubScenarioNO, String Browsername) {
		Connection con_Time = null;
		String time_SubScenarioDetails = "0:0:0";

		String Path = reportStructurePath + "\\" + res.getExectionDateTime()
				+ "\\" + ScenarioName + "_" + Browsername + ".xlsx";
		try {
			con_Time = DBConnectionManager.getConnection(Path);
			Statement stmt_Time = con_Time.createStatement();
			/*System.out.println("^^^^^^^^^^^^^^^^^");
			System.out.println("select * from [Driver$] where "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
	+ ScenarioName+ "' and "+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" ='"+ SubScenarioNO
							+ "' and [Time] is NOT NULL");*/
			ResultSet RS_Time = stmt_Time
					.executeQuery("select * from [Driver$] where "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
							+ ScenarioName
							+ "' and "+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" ='"
							+ SubScenarioNO
							+ "' and [Time] is NOT NULL");
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

			while (RS_Time.next() && RS_Time != null) {
				String time_Details = RS_Time.getString("Time");
				Date date_time_SubScenarioDetails = timeFormat
						.parse(time_SubScenarioDetails);
				Date date_time_Details = timeFormat.parse(time_Details);
				long sum = date_time_SubScenarioDetails.getTime()
						+ date_time_Details.getTime();
				time_SubScenarioDetails = timeFormat.format(new Date(sum));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time_SubScenarioDetails;
	}

	// Sandeep- Below method is to return executiontime with respect to
	// scenarioname, and browsername
	public static String subScenarioExecutionTime(String ScenarioName,
			String Browsername) {
		Connection con_Time = null;
		String time_SubScenarioDetails = "0:0:0";

		String Path = reportStructurePath + "\\" + res.getExectionDateTime()
				+ "\\" + ScenarioName + "_" + Browsername + ".xlsx";
		try {
			con_Time = DBConnectionManager.getConnection(Path);
			Statement stmt_Time = con_Time.createStatement();
			ResultSet RS_Time = stmt_Time
					.executeQuery("select * from [Driver$] where "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
							+ ScenarioName + "' and [Time] is NOT NULL");
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

			while (RS_Time.next() && RS_Time != null) {
				String time_Details = RS_Time.getString("Time");
				Date date_time_SubScenarioDetails = timeFormat
						.parse(time_SubScenarioDetails);
				Date date_time_Details = timeFormat.parse(time_Details);
				long sum = date_time_SubScenarioDetails.getTime()
						+ date_time_Details.getTime();
				time_SubScenarioDetails = timeFormat.format(new Date(sum));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return time_SubScenarioDetails;
	}
	
	public static void scenariosFunctionReports() {
		String filePath = reportStructurePath + "\\"
				+ res.getExectionDateTime() + "\\";
		ArrayList<String> scenarios = GenerateReports.getScenarioNames();
		for (int ScenarioRowNo = 0; ScenarioRowNo < scenarios.size(); ScenarioRowNo++) {

			String strFunctionReport = null;

			ArrayList<String> Subscenario = GenerateReports
					.getScenario_SubScenarioNo((String) scenarios
							.get(ScenarioRowNo));
			for (int SubScenarioRowNo = 0; SubScenarioRowNo < Subscenario
					.size(); SubScenarioRowNo++) {
				String scenario = (String) scenarios.get(ScenarioRowNo);
				String subscenario = (String) Subscenario.get(SubScenarioRowNo);
				String subscenariono[] = subscenario.split("\\.");
				String subscenariodetails = subscenariono[0];
				File mainScreen = new File(filePath + scenario + "_"
						+ subscenariodetails + "_" + "FunctionReport.html");
				try {
					if (!mainScreen.exists()) {
						mainScreen.createNewFile();
					}

					FileWriter fstream = new FileWriter(mainScreen);
					BufferedWriter out = new BufferedWriter(fstream);
					out.write(getHTMLPageHeader());
					strFunctionReport = htmlTableTag + htmlTableEndTag
							+ htmlThTag + CyborgConstants.REPORT_SERIAL_NUMBER
							+ htmlThEndTag + htmlThTag
							+ CyborgConstants.REPORT_SCENARIO_NAME
							+ htmlThEndTag + htmlThTag
							+ CyborgConstants.REPORT_SUB_SCENARIO_NO
							+ htmlThEndTag + htmlThTag
							+ CyborgConstants.REPORT_FUNCTION_NAME
							+ htmlThEndTag + htmlThTag
							+ CyborgConstants.REPORT_DATA_SET_NO + htmlThEndTag
							+ htmlThTag + CyborgConstants.REPORT_STATUS
							+ htmlThEndTag + htmlThTag
							+ CyborgConstants.REPORTS_TIME + htmlThEndTag
							+ htmlThEndTag + htmlThTag
							+ CyborgConstants.REPORT_TEST_CASE_HISTORY + htmlThEndTag							
							+ htmlTREndTag;
					out.write(backButton);
					ArrayList browservalue = GenerateReports
							.getScenario_BrowserUniqueValues(
									(String) scenarios.get(ScenarioRowNo),
									(String) Subscenario.get(SubScenarioRowNo));
					FunctionReport_SlNo = 1;
					FunctionReport_TotalFunction = 0;
					FunctionReport_Pass = 0;
					FunctionReport_Fail = 0;
					ArrayList<String> lst_SubScenarioFunction = new ArrayList<String>();
					for (int BrowserRowNo = 0; BrowserRowNo < browservalue
							.size(); BrowserRowNo++) {
						String Scenario_Browserdetails = scenario + "_"
								+ (String) browservalue.get(BrowserRowNo);
						String ReportDetails = getScenariosFunctionReport(
								strFunctionReport, Scenario_Browserdetails,
								scenario, subscenariodetails,
								(String) browservalue.get(BrowserRowNo));
						lst_SubScenarioFunction.add(ReportDetails);
						strFunctionReport = "";

					}

					out.write(getOtherScreenHeader(FunctionReport_TotalFunction
							+ "", FunctionReport_Pass + "", FunctionReport_Fail
							+ "", GenericMethods.getPropertyValue(
									"applicationVer", configurationStructurePath), "", "", url, username, os,
							"Functions"));

					for (int i = 0; i < lst_SubScenarioFunction.size(); i++) {
						out.write((String) lst_SubScenarioFunction.get(i));
					}
					strFunctionReport = "";
					lst_SubScenarioFunction.clear();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

	}

	public static void AssertionReports() {

		String filePath = reportStructurePath + "\\"
				+ res.getExectionDateTime() + "\\";
		Connection con = null;
		try {

			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String singleWorkBookPath = reportStructurePath + "\\"
					+ res.getExectionDateTime() + "\\Scenario.xlsx";
			con = DriverManager
					.getConnection(
							"jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="
									+ singleWorkBookPath
									+ ";"
									+ "READONLY=false", "", "");

			Statement mainScenarioSTMT = con.createStatement();
			ResultSet mainScenariors = mainScenarioSTMT
					.executeQuery("select * from [Scenarios$]");
			while (mainScenariors.next()) {
				String scenarioname = mainScenariors.getString("ScenarioName");
				File mainScreen = new File(filePath + scenarioname
						+ "Assertions.html");
				if (!mainScreen.exists()) {
					mainScreen.createNewFile();
					FileWriter fstream = new FileWriter(mainScreen);
					BufferedWriter out = new BufferedWriter(fstream);
					out.write(getHTMLPageHeader());
					out.write(backButton);
					out.write(getOtherScreenHeader(
							getConnection("AssertionReports", "pass",
									scenarioname)
									+ getConnection("AssertionReports", "fail",
											scenarioname) + "",
							getConnection("AssertionReports", "pass",
									scenarioname) + "",
							getConnection("AssertionReports", "fail",
									scenarioname) + "", GenericMethods.getPropertyValue(
											"applicationVer", configurationStructurePath), GenericMethods.getPropertyValue("browser",
													configurationStructurePath),
											GenericMethods.getPropertyValue(
													"browserVer", configurationStructurePath), url, username, os, "Assertions"));
					out.write(getAssertionReport(scenarioname));
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DBConnectionManager.close(con);

	}

	// Sandeep- for parallel testing

	public static void Assertion_Reports() {

		String filePath = reportStructurePath + "\\"
				+ res.getExectionDateTime() + "\\";
		ArrayList<String> scenarios = GenerateReports.getScenarioNames();
		try {
			for (int ScenarioRowNo = 0; ScenarioRowNo < scenarios.size(); ScenarioRowNo++) {
				String scenarioname = (String) scenarios.get(ScenarioRowNo);
				File mainScreen = new File(filePath + scenarioname + "_"
						+ "AssertionsReport.html");
				if (!mainScreen.exists()) {
					mainScreen.createNewFile();
				}
				FileWriter fstream = new FileWriter(mainScreen);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(getHTMLPageHeader());
				out.write(backButton);
				ArrayList<String> Scenario_AssertionDetails = new ArrayList<String>();
				ArrayList<String> Scenario_AssertionReportDetails = null;
				ArrayList<String> Subscenario = GenerateReports
						.getScenario_SubScenarioNo((String) scenarios
								.get(ScenarioRowNo));
				String strScenarioBrowserReport = null;
				strScenarioBrowserReport = htmlTableTag + htmlTableEndTag
						+ "<th class=\"header\" width=\"5%\">"
						+ CyborgConstants.REPORT_SERIAL_NUMBER + htmlThEndTag
						+ htmlThTag + CyborgConstants.REPORT_SCENARIO_NAME
						+ htmlThEndTag + htmlThTag
						+ CyborgConstants.REPORT_SUB_SCENARIO_NO + htmlThEndTag
						+ htmlThTag + CyborgConstants.REPORT_CLASS_NAME
						+ htmlThEndTag + htmlThTag
						+ CyborgConstants.REPORT_FUNCTION_NAME + htmlThEndTag
						+ htmlThTag + CyborgConstants.REPORT_DATA_SET_NO
						+ htmlThEndTag + htmlThTag + CyborgConstants.ELEMENT_ID
						+ htmlThEndTag + htmlThTag
						+ CyborgConstants.REPORT_DESCRIPTION + htmlThEndTag
						+ htmlThTag + CyborgConstants.REPORT_EXPECTED_VALUE
						+ htmlThEndTag + htmlThTag
						+ CyborgConstants.REPORT_ACTUAL_VALUE + htmlThEndTag
						+ htmlThTag + CyborgConstants.REPORT_RESULT
						+ htmlThEndTag + htmlThTag
						+ CyborgConstants.REPORTS_TIME + htmlThEndTag
						+ htmlTREndTag;
				Assertion_RowNo = 1;
				Assertion_Pass = 0;
				Assertion_Fail = 0;
				ArrayList<String> browservalue = GenerateControlfiles
						.getScenario_BrowserUniqueValues((String) scenarios
								.get(ScenarioRowNo));
								for (int BrowserRowNo = 0; BrowserRowNo < browservalue.size(); BrowserRowNo++) {
					Scenario_AssertionReportDetails = getAssertionReport(
							strScenarioBrowserReport, scenarioname,
							(String) browservalue.get(BrowserRowNo));
					if (Scenario_AssertionReportDetails.size() > 0) {
						Scenario_AssertionDetails
								.addAll(Scenario_AssertionReportDetails);
					}

					Scenario_AssertionReportDetails.clear();
					strScenarioBrowserReport = "";

				}
				//Logic to display No record Found-Sandeep
					 Assertion_RowNo=Assertion_Pass+Assertion_Fail;
				out.write(getOtherScreenHeader(Assertion_RowNo + "",
						Assertion_Pass + "", Assertion_Fail + "", GenericMethods.getPropertyValue(
								"applicationVer", configurationStructurePath),
						"", "", url, username, os, "Assertions"));
				if (Scenario_AssertionDetails.size() > 0) {
					for (int i = 0; i < Scenario_AssertionDetails.size(); i++) {
						out.write((String) Scenario_AssertionDetails.get(i));
					}
				}
				//Logic to display No record Found-Sandeep
                if(Assertion_Pass== 0 &&Assertion_Fail == 0)
                {
                      String NoRecordText =htmlTRTag+ htmlTdTagColspan+"No Records to be displayed in Assertion Reports"+TRTDEndTag ;
                      out.write(NoRecordText);
                }


				out.close();
				Scenario_AssertionDetails.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	public static String getOtherScreenHeader(String tot, String passNo,
			String failNo, String appVersion, String browser,
			String browser_version, String url, String userName, String os,
			String type) {
		String []appUrl=url.split("\\?");
		if(appUrl.length>0){
			url=appUrl[0];
		}
		String strTestDetails = headingTRTag + "Application URL : " + url
				+ headingTDTag + "Application Version : " + appVersion
				+ TRTDEndTag;
		if (!browser.equals("")) {
			strTestDetails = strTestDetails + headingTRTag + "Browser Name : "
					+ browser + headingTDTag + "Browser Version : "
					+ browser_version + TRTDEndTag;
		}
		strTestDetails = strTestDetails + headingTRTag + "Username : "
				+ userName + headingTDTag + "Total No of " + type + " : " + tot
				+ TRTDEndTag;
		strTestDetails = strTestDetails + headingTRTag + "Operating System : "
				+ os + headingTDTag + "Pass " + type + " : " + passNo
				+ TRTDEndTag;
		strTestDetails = strTestDetails + headingTRTag + "Fail " + type + " : "
				+ failNo + TRTDEndTag;
		return strTestDetails;
	}

	public static String getMainScreenHeaderDetails(String time,
			String startTime, String endTime, String version, String browser,
			String browser_version, String url, String userName, String os) {
		String []appUrl=url.split("\\?");
		if(appUrl.length>0){
			url=appUrl[0];
		}
		String strTestDetails = headingTRTag + "Application URL : " + url
				+ headingTDTag + "Application Version : " + version
				+ TRTDEndTag;
		// Sandeep- Below if condition is added to check whether browser has any
		// null value, because this method is used at some other place also
		// where we are pasing values into it.
		if (!browser.equals("")) {
			strTestDetails = strTestDetails + headingTRTag + "Browser Name : "
					+ browser + headingTDTag + "Browser Version : "
					+ browser_version + TRTDEndTag;
		}
		strTestDetails = strTestDetails + headingTRTag + "Username : "
				+ userName + headingTDTag + "Execution Start Time : "
				+ startTime + TRTDEndTag;
		strTestDetails = strTestDetails + headingTRTag + "Operating System : "
				+ os + headingTDTag + "Execution End Time : " + endTime
				+ TRTDEndTag;
		strTestDetails = strTestDetails + headingTRTag
				+ "Total Time of Execution : " + time + " Seconds" + TRTDEndTag;
		return strTestDetails;
	}

	public static String getMainScreenDetails(String status, String relPath) {
		String strMainScreenDetails = null;
		strMainScreenDetails = htmlTableTag + htmlTableEndTag + htmlThTag
				+ CyborgConstants.REPORT_SERIAL_NUMBER + htmlThEndTag
				+ htmlThTag + CyborgConstants.REPORT_SCENARIO_NAME
				+ htmlThEndTag + htmlThTag + CyborgConstants.REPORT_STATUS
				+ htmlThEndTag + htmlThTag + CyborgConstants.REPORTS_TIME
				+ htmlThEndTag + htmlTREndTag;
		try {

			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String singleWorkBookPath = reportStructurePath + "\\"
					+ res.getExectionDateTime() + "\\Scenario.xlsx";
			Connection con = DBConnectionManager
					.getConnection(singleWorkBookPath);
			Statement mainScenarioSTMT = con.createStatement();
			ResultSet mainScenariors = mainScenarioSTMT
					.executeQuery("select * from [Scenarios$]");
			Connection scenarioCon = null;
			Connection subScenarioCon = null;
			Connection subScenarioNoCon = null;
			Connection scenarioUpdateCon = null;
			int SlNo = 1;
			String ScenarioPath = null;
			ArrayList<String> statusinfo = new ArrayList<String>();
			ArrayList<String> subscenariolst = new ArrayList<String>();
			ArrayList subscenarioNolst = new ArrayList();
			ArrayList<String> subscenariocompletelst = new ArrayList<String>();
			String actualScenarioName = null;
			String browserType = null;
			while (mainScenariors.next()) {
				String scenarioname = mainScenariors.getString("ScenarioName");
				String resultPath = scenarioname + ".html";
				ScenarioPath = reportStructurePath + "\\"
						+ res.getExectionDateTime() + "\\" + scenarioname
						+ ".xlsx";
				int index = scenarioname.lastIndexOf("_");
				actualScenarioName = scenarioname.substring(0, index);
				browserType = scenarioname.substring(index + 1);
				scenarioCon = DBConnectionManager.getConnection(ScenarioPath);
				Statement scenarioStatusSTMT = scenarioCon.createStatement();
				ResultSet scenarioStatusRs = scenarioStatusSTMT
						.executeQuery("select distinct Status from [AssertionReports$] where "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
								+ actualScenarioName + "'");

				while (scenarioStatusRs.next()) {
					statusinfo.add(scenarioStatusRs.getString("Status"));
				}

				String Status = "";
				// Sandeep- Below if condition is to check values in Assertion
				// Reports if any assertion is fail w.r.to scenario
				if (statusinfo.size() > 0) {
					int Pass = 0;
					int Fail = 0;
					int Skipped = 0;
					for (int i = 0; i < statusinfo.size(); i++) {
						String statuscheck = (String) statusinfo.get(i);
						if (statuscheck.equalsIgnoreCase("Pass")) {
							Pass = 1;
						}
						if (statuscheck.equalsIgnoreCase("Fail")) {
							Fail = 2;
						}
						if (statuscheck.equalsIgnoreCase("Skipped")) {
							Skipped = 3;
						}
					}

					if (Fail == 2) {
						Status = "FAIL";
					} else if (Pass == 1) {
						Status = "PASS";
					} else {
						Status = "SKIPPED";
					}
				}
				// Sandeep- Below else condition is to check values in Driver
				// sheet if any assertion is fail w.r.to scenario
				if (!Status.equalsIgnoreCase("Fail")) {
					subScenarioNoCon = DBConnectionManager
							.getConnection(ScenarioPath);
					Statement subScenarioNOSTMT = subScenarioNoCon
							.createStatement();
					ResultSet subScenarioNoRS = subScenarioNOSTMT
							.executeQuery("select distinct "+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" from [Driver$] where "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
									+ actualScenarioName + "'");
					while (subScenarioNoRS.next()) {
						subscenarioNolst.add(subScenarioNoRS
								.getString(CyborgConstants.DRIVER_BOOK_SCENARIO_NAME) + "");
					}
					if (subscenarioNolst.size() > 0) {
						for (int subscenario = 0; subscenario < subscenarioNolst
								.size(); subscenario++) {
							subScenarioCon = DBConnectionManager
									.getConnection(ScenarioPath);
							Statement subScenarioStatusSTMT = subScenarioCon
									.createStatement();
							ResultSet subScenarioStatusRs = subScenarioStatusSTMT
									.executeQuery("select distinct Status from [Driver$] where "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
											+ actualScenarioName
											+ "' and "+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+"='"
											+ subscenarioNolst.get(subscenario)
											+ "'");

							while (subScenarioStatusRs.next()) {
								subscenariolst.add(subScenarioStatusRs
										.getString("Status"));
							}
							Status = "";
							if (subscenariolst.size() > 0) {
								int Pass = 0;
								int Fail = 0;
								int Skipped = 0;

								for (int statusid = 0; statusid < subscenariolst
										.size(); statusid++) {
									String statuscheck = (String) subscenariolst
											.get(statusid);
									if (statuscheck.equalsIgnoreCase("Pass")) {
										Pass = 1;
									}
									if (statuscheck.equalsIgnoreCase("Fail")) {
										Fail = 2;
									}
									if (statuscheck.equalsIgnoreCase("Skipped")) {
										Skipped = 3;
									}
								}

								if (Fail == 2) {
									Status = "FAIL";
								} else if (Pass == 1) {
									Status = "PASS";
								} else {
									Status = "SKIPPED";
								}
							} else {
								Status = "N.A";
							}
							subscenariocompletelst.add(Status);
						}
					}

					Status = "";
					if (subscenariocompletelst.size() > 0) {
						int Pass = 0;
						int Fail = 0;
						int Skipped = 0;
						for (int j = 0; j < subscenariocompletelst.size(); j++) {
							String statuscheck = (String) subscenariocompletelst
									.get(j);
							if (statuscheck.equalsIgnoreCase("Pass")) {
								Pass = 1;
							}
							if (statuscheck.equalsIgnoreCase("Fail")) {
								Fail = 2;
							}
							if (statuscheck.equalsIgnoreCase("Skipped")) {
								Skipped = 3;
							}
						}
						if (Fail == 2) {
							Status = "FAIL";
						} else if (Pass == 1) {
							Status = "PASS";
						} else {
							Status = "SKIPPED";
						}
					} else {
						Status = "N.A";
					}
				}

				strMainScreenDetails = strMainScreenDetails + htmlTRTag
						+ htmlTdTag + SlNo + htmlTdEndTag;
				strMainScreenDetails = strMainScreenDetails + htmlTdTag
						+ htmlLinkTag + scenarioname + ".xlsx" + htmlStyleTag
						+ scenarioname + htmlTdEndTag;
				strMainScreenDetails = strMainScreenDetails + htmlTdTag
						+ htmlLinkTag + resultPath + htmlStyleTag + Status
						+ htmlTdEndTag;
				strMainScreenDetails = strMainScreenDetails + htmlTdTag + "222"
						+ htmlTdEndTag;
				SlNo = SlNo + 1;

				// Sandeep- Below code is to update Status details in
				// "scenario.xls" excel sheet.
				String scenario_Time = subScenarioExecutionTime(
						actualScenarioName, browserType);
				String scenarioExcelSheetName = reportStructurePath + "\\"
						+ res.getExectionDateTime() + "\\Scenario.xlsx";
				scenarioUpdateCon = DBConnectionManager
						.getConnection(scenarioExcelSheetName);
				Statement scenarioUpdateSTMT = scenarioUpdateCon
						.createStatement();
				scenarioUpdateCon.prepareStatement(
						"update [Scenarios$] set Status = '" + Status
								+ "',[Time]='" + scenario_Time
								+ "' where ScenarioName ='" + scenarioname
								+ "'").execute();
				statusinfo.clear();
				subscenariolst.clear();
				subscenarioNolst.clear();
				subscenariocompletelst.clear();
			}

			ArrayList<String> Browserdetails = getScenario_UniqueBrowser();
			// subScenarioCon.close();
			scenarioCon.close();
			con.close();
			scenarioUpdateCon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return strMainScreenDetails;
	}

	public static String getSubScenarioDetails(String relPath) {
		String strSubScenarioDetails = null;
		strSubScenarioDetails = htmlTableTag + htmlTableEndTag + htmlThTag
				+ CyborgConstants.REPORT_SERIAL_NUMBER + htmlThEndTag
				+ htmlThTag + CyborgConstants.REPORT_SCENARIO_NAME
				+ htmlThEndTag + htmlThTag
				+ CyborgConstants.REPORT_SUB_SCENARIO_NO + htmlThEndTag
				+ htmlThTag + CyborgConstants.REPORT_STATUS + htmlThEndTag
				+ htmlThTag + CyborgConstants.REPORTS_TIME + htmlThEndTag
				+ htmlTREndTag;
		Connection con = null;
		Connection scenariosCon = null;
		Connection subScenarioCon = null;
		try {

			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String singleWorkBookPath = reportStructurePath + "\\"
					+ res.getExectionDateTime() + "\\"
					+ res.getScenarioClassName() + ".xlsx";
			con = DBConnectionManager.getConnection(singleWorkBookPath);
			Statement mainScenarioSTMT = con.createStatement();
			ResultSet mainScenariors = mainScenarioSTMT
					.executeQuery("select distinct SubScenarioNo from [Driver$] where ScenarioName='"
							+ res.getScenarioClassName() + "'");
			int SlNo = 1;
			ArrayList<String> scenarioStatuslist = new ArrayList<String>();

			ArrayList<String> subScenarioStatuslist = new ArrayList<String>();

			while (mainScenariors.next()) {
				String subscenariodetails = "SubScenario"
						+ mainScenariors.getInt("SubScenarioNo") + "";
				scenariosCon = DBConnectionManager
						.getConnection(singleWorkBookPath);
				Statement scenariosSTMT = scenariosCon.createStatement();
				ResultSet scenariosRS = scenariosSTMT
						.executeQuery("select distinct Status from [AssertionReports$] where ScenarioName='"
								+ res.getScenarioClassName() + "'");
				while (scenariosRS.next()) {
					scenarioStatuslist.add(scenariosRS.getString("Status"));
				}
				String Status = "";
				// Sandeep- Below if condition is to check values in Assertion
				// Reports if any assertion is fail w.r.to scenario then will
				// make scenario status as fail
				if (scenarioStatuslist.size() > 0) {
					int Pass = 0;
					int Fail = 0;
					int Skipped = 0;
					for (int i = 0; i < scenarioStatuslist.size(); i++) {
						String statuscheck = (String) scenarioStatuslist.get(i);
						if (statuscheck.equalsIgnoreCase("Pass")) {
							Pass = 1;
						}
						if (statuscheck.equalsIgnoreCase("Fail")) {
							Fail = 2;
						}
						if (statuscheck.equalsIgnoreCase("Skipped")) {
							Skipped = 3;
						}
					}

					if (Fail == 2) {
						Status = "FAIL";
					} else if (Pass == 1) {
						Status = "PASS";
					} else {
						Status = "SKIPPED";
					}
				}

				if (!Status.equalsIgnoreCase("Fail")) {
					subScenarioCon = DBConnectionManager
							.getConnection(singleWorkBookPath);
					Statement subScenarioStatusSTMT = subScenarioCon
							.createStatement();
					ResultSet subScenarioStatusRS = subScenarioStatusSTMT
							.executeQuery("select distinct Status from [Driver$] where ScenarioName='"
									+ res.getScenarioClassName()
									+ "' and SubScenarioNo='"
									+ subscenariodetails.substring(11) + "'");

					while (subScenarioStatusRS.next()) {
						subScenarioStatuslist.add(subScenarioStatusRS
								.getString("Status"));
					}
					Status = "";
					// Sandeep- Below if condition is to check values in
					// Assertion Reports if any assertion is fail w.r.to
					// scenario then will make scenario status as fail
					if (subScenarioStatuslist.size() > 0) {
						int Pass = 0;
						int Fail = 0;
						int Skipped = 0;
						for (int i = 0; i < subScenarioStatuslist.size(); i++) {
							String statuscheck = (String) subScenarioStatuslist
									.get(i);
							if (statuscheck.equalsIgnoreCase("Pass")) {
								Pass = 1;
							}
							if (statuscheck.equalsIgnoreCase("Fail")) {
								Fail = 2;
							}
							if (statuscheck.equalsIgnoreCase("Skipped")) {
								Skipped = 3;
							}
						}

						if (Fail == 2) {
							Status = "FAIL";
						} else if (Pass == 1) {
							Status = "PASS";
						} else {
							Status = "SKIPPED";
						}

					}

				}

				if (Status == "" || Status == null) {
					Status = "N.A";
				}
				String resultPath = res.getScenarioClassName()
						+ subscenariodetails + "FunctionReport.html";
				strSubScenarioDetails = strSubScenarioDetails + htmlTRTag
						+ htmlTdTag + SlNo + htmlTdEndTag;
				strSubScenarioDetails = strSubScenarioDetails + htmlTdTag
						+ res.getScenarioClassName() + htmlTdEndTag;
				strSubScenarioDetails = strSubScenarioDetails + htmlTdTag
						+ subscenariodetails + htmlTdEndTag;
				strSubScenarioDetails = strSubScenarioDetails + htmlTdTag
						+ htmlLinkTag + resultPath + htmlStyleTag + Status
						+ htmlTdEndTag;
				strSubScenarioDetails = strSubScenarioDetails + htmlTdTag
						+ "300" + htmlTdEndTag;
				SlNo = SlNo + 1;
			}

			subScenarioStatuslist.clear();
			scenarioStatuslist.clear();

		} catch (Exception e) {
			e.printStackTrace();
		}
		DBConnectionManager.close(scenariosCon);
		DBConnectionManager.close(con);
		DBConnectionManager.close(subScenarioCon);
		return strSubScenarioDetails;
	}

	public static String SubScenarioDetailsReport(String ScenarioName,
			String SubScenarioNO, String Browsername) {
		Connection con = null;
		Connection scenariosCon = null;
		Connection subScenarioCon = null;
		String Status = "";
		try {

			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String singleWorkBookPath = reportStructurePath + "\\"
					+ res.getExectionDateTime() + "\\" + ScenarioName + "_"
					+ Browsername + ".xlsx";
			int SlNo = 1;
			ArrayList<String> scenarioStatuslist = new ArrayList<String>();
			ArrayList<String> subScenarioStatuslist = new ArrayList<String>();
			scenariosCon = DBConnectionManager
					.getConnection(singleWorkBookPath);
			Statement scenariosSTMT = scenariosCon.createStatement();
			ResultSet scenariosRS = scenariosSTMT
					.executeQuery("select distinct Status from [AssertionReports$] where "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
							+ ScenarioName
							+ "' and "+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" ='"
							+ SubScenarioNO + "'");
			while (scenariosRS.next()) {
				scenarioStatuslist.add(scenariosRS.getString("Status"));
			}

			// Sandeep- Below if condition is to check values in Assertion
			// Reports if any assertion is fail w.r.to scenario then will make
			// scenario status as fail
			if (scenarioStatuslist.size() > 0) {
				int Pass = 0;
				int Fail = 0;
				int Skipped = 0;
				for (int i = 0; i < scenarioStatuslist.size(); i++) {
					String statuscheck = (String) scenarioStatuslist.get(i);
					if (statuscheck.equalsIgnoreCase("Pass")) {
						Pass = 1;
					}
					if (statuscheck.equalsIgnoreCase("Fail")) {
						Fail = 2;
					}
					if (statuscheck.equalsIgnoreCase("Skipped")) {
						Skipped = 3;
					}
				}

				if (Fail == 2) {
					Status = "FAIL";
				} else if (Pass == 1) {
					Status = "PASS";
				} else {
					Status = "SKIPPED";
				}
			}

			if (!Status.equalsIgnoreCase("Fail")) {
				subScenarioCon = DBConnectionManager
						.getConnection(singleWorkBookPath);
				Statement subScenarioStatusSTMT = subScenarioCon
						.createStatement();
				ResultSet subScenarioStatusRS = subScenarioStatusSTMT
						.executeQuery("select distinct Status from [Driver$] where "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
								+ ScenarioName
								+ "' and "+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+"='"
								+ SubScenarioNO + "'");

				while (subScenarioStatusRS.next()) {
					subScenarioStatuslist.add(subScenarioStatusRS
							.getString("Status"));
				}
				Status = "";
				// Sandeep- Below if condition is to check values in Assertion
				// Reports if any assertion is fail w.r.to scenario then will
				// make scenario status as fail
				if (subScenarioStatuslist.size() > 0) {
					int Pass = 0;
					int Fail = 0;
					int Skipped = 0;
					for (int i = 0; i < subScenarioStatuslist.size(); i++) {
						String statuscheck = (String) subScenarioStatuslist
								.get(i);
						if (statuscheck.equalsIgnoreCase("Pass")) {
							Pass = 1;
						}
						if (statuscheck.equalsIgnoreCase("Fail")) {
							Fail = 2;
						}
						if (statuscheck.equalsIgnoreCase("Skipped")) {
							Skipped = 3;
						}
					}

					if (Fail == 2) {
						Status = "FAIL";
					} else if (Pass == 1) {
						Status = "PASS";
					} else {
						Status = "SKIPPED";
					}

				}

			}

			if (Status == "" || Status == null) {
				Status = "N.A";
			}
			subScenarioStatuslist.clear();
			scenarioStatuslist.clear();

		} catch (Exception e) {
			e.printStackTrace();
		}
		DBConnectionManager.close(scenariosCon);
		DBConnectionManager.close(con);
		DBConnectionManager.close(subScenarioCon);
		return Status;
	}

	

	static int FunctionReport_SlNo = 1;
	static int FunctionReport_TotalFunction = 0;
	static int FunctionReport_Pass = 0;
	static int FunctionReport_Fail = 0;

	// Overloaded
	public static String getScenariosFunctionReport(String strFunctionReport,
            String scenarioDetails, String ScenarioName, String SubScenarioNo,
            String BrowserValue) {
      Connection con = null;
      try {

            String singleWorkBookPath = reportStructurePath + "\\"
            + res.getExectionDateTime() + "\\" + scenarioDetails
            + ".xlsx";
            con = DBConnectionManager.getConnection(singleWorkBookPath);
            Statement mainScenarioSTMT = con.createStatement();
            ResultSet mainScenariors = mainScenarioSTMT
            .executeQuery("select * from [Driver$] where "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
                        + ScenarioName
                        + "' and "+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" ='"
                        + SubScenarioNo + "'");
            while (mainScenariors.next()) {
                  String FunctionName = mainScenariors.getString(CyborgConstants.DRIVER_BOOK_TESTCASE_NAME)
                  .toString();
                  String acutal_Functioname = FunctionName.split("/")[1];
                  // String
                  // ClickHere=ScenarioName+"writefunctionname"+FunctionReport_SlNo+".jpeg";
                  String ClickHere = ScenarioName + "_" + BrowserValue + "_"
                  + SubScenarioNo + "_" + acutal_Functioname + ".jpeg";
                  String resultPath = ScenarioName + "_"
                  + "AssertionsReport.html";
                  strFunctionReport = strFunctionReport + htmlTRTag + htmlTdTag
                  + FunctionReport_SlNo + htmlTdEndTag;
                  strFunctionReport = strFunctionReport + htmlTdTag
                  + scenarioDetails + htmlTdEndTag;
                  strFunctionReport = strFunctionReport + htmlTdTag
                  + SubScenarioNo + htmlTdEndTag;
                  strFunctionReport = strFunctionReport + htmlTdTag
                  + FunctionName + htmlTdEndTag;
                  strFunctionReport = strFunctionReport + htmlTdTag
                  + mainScenariors.getString(CyborgConstants.DRIVER_BOOK_DATA_SETS) + htmlTdEndTag;
                  String FunctionstatusValue = mainScenariors
                  .getString("FunctionStatus");
                  String Statusvalue = mainScenariors.getString("Status");
                 //Sandeep-Below if condition is updated with "screenshot_htmlLinkTag" so that screenshot will be opened in new tab.
                  if (FunctionstatusValue != null&&FunctionstatusValue.equalsIgnoreCase("Fail")) {
                        strFunctionReport = strFunctionReport + htmlTdTag
                        + htmlLinkTag + resultPath + htmlStyleTag_redcolor
                        + FunctionstatusValue + "</a>" + screenshot_htmlLinkTag
                        + ClickHere + htmlStyleTag_redcolor + "Snapshot" + "</a>"
                        + htmlTdEndTag;
                        FunctionReport_Fail = FunctionReport_Fail + 1;
                  } else if (Statusvalue.equalsIgnoreCase("Fail")) {
                        strFunctionReport = strFunctionReport + htmlTdTag
                        + htmlLinkTag + resultPath + htmlStyleTag_redcolor
                        + Statusvalue + htmlTdEndTag;
                        FunctionReport_Fail = FunctionReport_Fail + 1;
                  } else if (Statusvalue.equalsIgnoreCase("skipped")) {
                        strFunctionReport = strFunctionReport + htmlTdTag_brown
                        + Statusvalue + htmlTdEndTag;
                  }

                  else {
                        strFunctionReport = strFunctionReport + htmlTdTag
                        + htmlLinkTag + resultPath + htmlStyleTag_greencolor
                        + FunctionstatusValue + htmlTdEndTag;
                        FunctionReport_Pass = FunctionReport_Pass + 1;
                  }

                  String Time = mainScenariors.getString("Time");
                  if(Time!=null)
                  {
                        strFunctionReport = strFunctionReport + htmlTdTag
                        + Time + htmlTdEndTag;
                  }
                  else
                  {
                        strFunctionReport = strFunctionReport + htmlTdTag
                        + "0:0:0" + htmlTdEndTag;
                  }
                //Added for Test Case History--Srikala
                  strFunctionReport = getExecutionHistory(strFunctionReport,
    						mainScenariors,scenarioDetails,ScenarioName,FunctionName,SubScenarioNo,FunctionReport_SlNo,FunctionstatusValue,Statusvalue,Time);

                  FunctionReport_SlNo = FunctionReport_SlNo + 1;
                  FunctionReport_TotalFunction = FunctionReport_TotalFunction + 1;
            }
      } catch (Exception e) {
            e.printStackTrace();
      }
      DBConnectionManager.close(con);
      return strFunctionReport;

}


	public static String getAssertionReport(String scenarioname) {
		String strScenarioBrowserReport = null;
		strScenarioBrowserReport = htmlTableTag + htmlTableEndTag
				+ "<th class=\"header\" width=\"5%\">"
				+ CyborgConstants.REPORT_SERIAL_NUMBER + htmlThEndTag
				+ htmlThTag + CyborgConstants.REPORT_SCENARIO_NAME
				+ htmlThEndTag + htmlThTag
				+ CyborgConstants.REPORT_SUB_SCENARIO_NO + htmlThEndTag
				+ htmlThTag + CyborgConstants.REPORT_CLASS_NAME + htmlThEndTag
				+ htmlThTag + CyborgConstants.REPORT_FUNCTION_NAME
				+ htmlThEndTag + htmlThTag + CyborgConstants.REPORT_DATA_SET_NO
				+ htmlThEndTag + htmlThTag + CyborgConstants.ELEMENT_ID
				+ htmlThEndTag + htmlThTag + CyborgConstants.REPORT_DESCRIPTION
				+ htmlThEndTag + htmlThTag
				+ CyborgConstants.REPORT_EXPECTED_VALUE + htmlThEndTag
				+ htmlThTag + CyborgConstants.REPORT_ACTUAL_VALUE
				+ htmlThEndTag + htmlThTag + CyborgConstants.REPORT_STATUS
				+ htmlThEndTag + htmlThTag + CyborgConstants.REPORTS_TIME
				+ htmlThEndTag + htmlTREndTag;
		Connection con = null;

		try {

			String singleWorkBookPath = reportStructurePath + "\\"
					+ res.getExectionDateTime() + "\\" + scenarioname + ".xlsx";
			con = DBConnectionManager.getConnection(singleWorkBookPath);

			Statement mainScenarioSTMT = con.createStatement();
			ResultSet mainScenariors = mainScenarioSTMT
					.executeQuery("select * from [AssertionReports$]");
			int m = 1;
			while (mainScenariors.next()) {
				String resultPath = res.getScenarioClassName()
						+ res.getFunctionName() + CyborgConstants.ASSERTION + m
						+ ".jpeg";
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTRTag
						+ htmlTdTag + mainScenariors.getString("SlNo")
						+ htmlTdEndTag;
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ mainScenariors.getString("ScenarioName")
						+ htmlTdEndTag;
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ mainScenariors.getString("SubScenarioNo")
						+ htmlTdEndTag;
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ mainScenariors.getString("ClassName") + htmlTdEndTag;
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ mainScenariors.getString("FunctionName")
						+ htmlTdEndTag;
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ mainScenariors.getString("DataSetNo") + htmlTdEndTag;
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ mainScenariors.getString("ElementId") + htmlTdEndTag;
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ mainScenariors.getString("Description")
						+ htmlTdEndTag;
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ mainScenariors.getString("ExpectedValue")
						+ htmlTdEndTag;
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ mainScenariors.getString("ActualValue")
						+ htmlTdEndTag;
				String status = mainScenariors.getString("Status");
				if (status.equalsIgnoreCase("fail")) {
					strScenarioBrowserReport = strScenarioBrowserReport
							+ htmlTdTag + htmlLinkTag + resultPath
							+ htmlStyleTag + status + htmlTdEndTag;
				} else {
					strScenarioBrowserReport = strScenarioBrowserReport
							+ htmlTdTag + status + htmlTdEndTag;
				}
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ mainScenariors.getString("Time") + htmlTdEndTag;
				m = m + 1;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		DBConnectionManager.close(con);
		return strScenarioBrowserReport;

	}

	// Overload, Sandeep- for parallel testing
	static int Assertion_RowNo = 1;
	static int Assertion_Pass = 0;
	static int Assertion_Fail = 0;

	public static ArrayList<String> getAssertionReport(
			String strScenarioBrowserReport, String scenarioname,
			String BrowserValue) {

		ArrayList<String> assertionreport = new ArrayList<String>();
		Connection con = null;
		try {
			String singleWorkBookPath = reportStructurePath + "\\"
					+ res.getExectionDateTime() + "\\" + scenarioname + "_"
					+ BrowserValue + ".xlsx";
			con = DBConnectionManager.getConnection(singleWorkBookPath);
			Statement mainScenarioSTMT = con.createStatement();
			ResultSet mainScenariors = mainScenarioSTMT
					.executeQuery("select * from [AssertionReports$]");
			while (mainScenariors.next() && mainScenariors != null) {
				String FunctionName = mainScenariors.getString(CyborgConstants.DRIVER_BOOK_TESTCASE_NAME);
				String SubScenarioNo = mainScenariors
						.getString(CyborgConstants.DRIVER_BOOK_SCENARIO_NAME);
				String resultPath = scenarioname + "_" + BrowserValue + "_"
						+ SubScenarioNo + "_" + FunctionName
						+ CyborgConstants.ASSERTION
						+ mainScenariors.getString("SlNo") + ".jpeg";
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTRTag
						+ htmlTdTag + Assertion_RowNo + htmlTdEndTag;
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ mainScenariors.getString(CyborgConstants.DRIVER_BOOK_TEST_PALN) + "_"
						+ BrowserValue + htmlTdEndTag;
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ SubScenarioNo + htmlTdEndTag;
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ mainScenariors.getString("ClassName") + htmlTdEndTag;
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ FunctionName + htmlTdEndTag;
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ mainScenariors.getString(CyborgConstants.DRIVER_BOOK_DATA_SETS) + htmlTdEndTag;
				String ElementID = mainScenariors.getString("ElementId");
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ ElementID + htmlTdEndTag;
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ mainScenariors.getString("Description")
						+ htmlTdEndTag;
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ mainScenariors.getString("ExpectedValue")
						+ htmlTdEndTag;
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ mainScenariors.getString("ActualValue")
						+ htmlTdEndTag;
				String status = mainScenariors.getString("Status");
				if (status.equalsIgnoreCase("fail")) {
					
					if(ElementID.equalsIgnoreCase(CyborgConstants.REPORT_ASSERTTWOVALUES))
					{
						strScenarioBrowserReport = strScenarioBrowserReport
						+ htmlTdTag_red + status + htmlTdEndTag;
					}
					
					else
					{
		                 //Sandeep-Below condition is updated with "screenshot_htmlLinkTag" so that screenshot will be opened in new tab.
					strScenarioBrowserReport = strScenarioBrowserReport
							+ htmlTdTag + screenshot_htmlLinkTag + resultPath
							+ htmlStyleTag_redcolor + status + htmlTdEndTag;
					
					}
					Assertion_Fail = Assertion_Fail + 1;
				} else {
					strScenarioBrowserReport = strScenarioBrowserReport
							+ htmlTdTag_green + status + htmlTdEndTag;
					Assertion_Pass = Assertion_Pass + 1;
				}
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ mainScenariors.getString("Time") + htmlTdEndTag;
				Assertion_RowNo = Assertion_RowNo + 1;
			}
			assertionreport.add(strScenarioBrowserReport);
		} catch (Exception e) {
			e.printStackTrace();
		}
		DBConnectionManager.close(con);
		return assertionreport;

	}

	public static void HTMLTestCases(String strFilePath, String time,
			String startTime, String endTime, String app_version,
			String browser, String browser_version, String url, String status,
			String userName, String os) {
		// Create new HTML file.
		File file = new File(strFilePath);
		try {
			file.createNewFile();
			FileWriter fstream = new FileWriter(strFilePath);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(getMainScreenHeaderDetails(time, startTime, endTime,
					app_version, browser, browser_version, url, userName, os));
			out.write(getMainScreenDetails(status, strFilePath));
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static int getConnection(String sheetName, String status,
			String scenarioName) {
		int counter = 0;
		String singleWorkBookPath = reportStructurePath + "\\"
				+ res.getExectionDateTime() + "\\" + scenarioName + ".xlsx";
		Connection con = null;
		try {
			con = DBConnectionManager.getConnection(singleWorkBookPath);
			Statement mainScenarioSTMT = con.createStatement();
			ResultSet mainScenariors = mainScenarioSTMT
					.executeQuery("select * from [" + sheetName
							+ "$] where Status='" + status + "'");

			while (mainScenariors.next()) {
				counter = counter + 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		DBConnectionManager.close(con);
		return counter;
	}

	public static int getNoofPassFunctions(String sheetName, String status,
			String scenarioName, String subScenario) {
		int counter = 0;
		String singleWorkBookPath = reportStructurePath + "\\"
				+ res.getExectionDateTime() + "\\" + res.getScenarioClassName()
				+ ".xlsx";
		Connection con = null;
		try {
			con = DBConnectionManager.getConnection(singleWorkBookPath);
			Statement mainScenarioSTMT = con.createStatement();
			ResultSet mainScenariors = mainScenarioSTMT
					.executeQuery("select * from [" + sheetName
							+ "$] where Status='" + status
							+ "' and ScenarioName='" + scenarioName
							+ "' and SubScenarioNo='" + subScenario + "'");

			while (mainScenariors.next()) {
				counter = counter + 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		DBConnectionManager.close(con);
		return counter;
	}

	public static int getStatuscount(String status) {
		int statusCount = 0;
		Connection con = null;
		try {
			String singleWorkBookPath = System.getProperty("user.dir")
					+ "\\Reports\\" + res.getExectionDateTime()
					+ "\\Scenario.xlsx";
			con = DBConnectionManager.getConnection(singleWorkBookPath);
			Statement mainScenarioSTMT = con.createStatement();
			ResultSet mainScenariors = mainScenarioSTMT
					.executeQuery("select * from [Scenarios$] where Status='"
							+ status + "'");

			while (mainScenariors.next()) {
				statusCount = statusCount + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DBConnectionManager.close(con);
		return statusCount;
	}

	public static int getScenariocount() {
		int scenarioCount = 0;
		Connection con = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String singleWorkBookPath = System.getProperty("user.dir")
					+ "\\TestData\\Scenariodriverworkbook.xlsx";
			con = DBConnectionManager.getConnection(singleWorkBookPath);
			Statement mainScenarioSTMT = con.createStatement();
			ResultSet mainScenariors = mainScenarioSTMT
					.executeQuery("select distinct "+CyborgConstants.DRIVER_BOOK_TEST_PALN+" from [ControlSheet$] where "+CyborgConstants.DRIVER_BOOK_TEST_PLAN_REQUIRED+"='NO'");

			while (mainScenariors.next()) {
				scenarioCount = scenarioCount + 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		DBConnectionManager.close(con);
		return scenarioCount;
	}

	/**
	 * @Description: Below method is to get scenario count from
	 *               Scenariodriverworkbook.xlsx based on mainScenarioRequired
	 *               parameter
	 * @param mainScenarioRequired
	 *            :Have to pass 'Yes' or 'No' value.
	 * @return: It returns the count of ScenarioName which has status 'Yes' in
	 *          Scenariodriverworkbook
	 * @author: Sandeep Jain
	 * @Date:5/19/2014
	 */
	public static int getScenarioCount(String mainScenarioRequired) {
		int scenarioCount = 0;
		Connection con = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String singleWorkBookPath = System.getProperty("user.dir")
					+ "\\TestData\\Scenariodriverworkbook.xlsx";
			con = DBConnectionManager.getConnection(singleWorkBookPath);
			Statement mainScenarioSTMT = con.createStatement();
			ResultSet mainScenariors = mainScenarioSTMT
					.executeQuery("select distinct "+CyborgConstants.DRIVER_BOOK_TEST_PALN+" from [ControlSheet$] where "+CyborgConstants.DRIVER_BOOK_TEST_PLAN_REQUIRED+"='"
							+ mainScenarioRequired + "'");

			while (mainScenariors.next()) {
				scenarioCount = scenarioCount + 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		DBConnectionManager.close(con);
		return scenarioCount;
	}

	/**
	 * @Description: Below method is to fetch unique BrowserType values from all
	 *               Scenarios in Scenariodriverworkbook.xlsx
	 * @return: unique Browservalues
	 * @author: Sandeep Jain
	 * @Date:5/19/2014
	 */
	public static ArrayList<String> getScenario_UniqueBrowser() {
		ArrayList<String> BrowserValues = new ArrayList<String>();
		Connection con = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String singleWorkBookPath = System.getProperty("user.dir")
					+ "\\TestData\\Scenariodriverworkbook.xlsx";
			con = DBConnectionManager.getConnection(singleWorkBookPath);
			Statement mainScenarioSTMT = con.createStatement();
			ResultSet mainScenariors = mainScenarioSTMT
					.executeQuery("select distinct "+CyborgConstants.DRIVER_BOOK_TEST_PALN+" from [ControlSheet$] where "+CyborgConstants.DRIVER_BOOK_TEST_PLAN_REQUIRED+"='Yes'");

			while (mainScenariors.next()) {
				ArrayList<String> scenario_BrowserValues = new ArrayList<String>();
				String mainScenarioName = mainScenariors
						.getString(CyborgConstants.DRIVER_BOOK_TEST_PALN);
				scenario_BrowserValues = GenerateControlfiles
						.getScenario_BrowserUniqueValues(mainScenarioName);
				if (BrowserValues.isEmpty()) {
					for (int j = 0; j < scenario_BrowserValues.size(); j++) {
						BrowserValues.add(scenario_BrowserValues.get(j));
					}
				}
				if (!BrowserValues.isEmpty()) {
					for (int j = 0; j < scenario_BrowserValues.size(); j++) {
						if (!BrowserValues.contains(scenario_BrowserValues
								.get(j))) {
							BrowserValues.add(scenario_BrowserValues.get(j));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DBConnectionManager.close(con);
		return BrowserValues;
	}

	public static int getSubScenariocount(String result) {
		int resultCount = 0;
		Connection con = null;
		Connection scenariosCon = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String singleWorkBookPath = reportStructurePath + "\\"
					+ res.getExectionDateTime() + "\\"
					+ res.getScenarioClassName() + ".xlsx";
			con = DBConnectionManager.getConnection(singleWorkBookPath);
			Statement mainScenarioSTMT = con.createStatement();
			ResultSet mainScenariors = mainScenarioSTMT
					.executeQuery("select distinct SubScenarioNo from [Driver$] where ScenarioName='"
							+ res.getScenarioClassName() + "'");
			ArrayList<String> scenarioStatuslist = new ArrayList<String>();

			while (mainScenariors.next()) {
				scenariosCon = DriverManager
						.getConnection(
								"jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="
										+ singleWorkBookPath
										+ ";"
										+ "READONLY=false", "", "");
				Statement scenariosSTMT = scenariosCon.createStatement();
				ResultSet scenariosRS = scenariosSTMT
						.executeQuery("select distinct Status from [Driver$] where ScenarioName='"
								+ res.getScenarioClassName()
								+ "' and SubScenarioNo='"
								+ mainScenariors.getInt("SubScenarioNo")
								+ "' and Status = '" + result + "'");
				while (scenariosRS.next()) {
					scenarioStatuslist.add(scenariosRS.getString("Status"));
				}

				for (int i = 0; i < scenarioStatuslist.size(); i++) {

					resultCount = resultCount + 1;
				}

			}

			scenarioStatuslist.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
		DBConnectionManager.close(con);
		DBConnectionManager.close(scenariosCon);
		return resultCount;
	}

	/**
	 * @Description: Below method is to create Browser-wise Scenario report by
	 *               fetching details from Scenario.xlsx.
	 * @param BrowserValue
	 *            : Browser Name should be provided (Ex:ie,firefox,chrome)
	 * @param totTime
	 *            : Total Time of all scenarios executed.
	 * @param startTime
	 *            : Starting execution time of first Scenario.
	 * @param endTime
	 *            : Ending execution time of last Scenario.
	 * @author: Sandeep Jain
	 * @Time: May 26th 2014
	 */
	public static void ScenarioReports(String BrowserValue, String totTime,
			String startTime, String endTime) {
		String strScenarioBrowserReport = null;
		int SlNo = 1;
		strScenarioBrowserReport = htmlTableTag + htmlTableEndTag + htmlThTag
				+ CyborgConstants.REPORT_SERIAL_NUMBER + htmlThEndTag
				+ htmlThTag + CyborgConstants.REPORT_SCENARIO_NAME
				+ htmlThEndTag + htmlThTag + CyborgConstants.REPORT_STATUS
				+ htmlThEndTag + htmlThTag + CyborgConstants.REPORTS_TIME
				+ htmlThEndTag + htmlTREndTag;

		String filePath = reportStructurePath + "\\"
				+ res.getExectionDateTime() + "\\";
		Connection con = null;
		try {

			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String singleWorkBookPath = reportStructurePath + "\\"
					+ res.getExectionDateTime() + "\\Scenario.xlsx";
			con = DriverManager
					.getConnection(
							"jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="
									+ singleWorkBookPath
									+ ";"
									+ "READONLY=false", "", "");

			Statement mainScenarioSTMT = con.createStatement();
			ResultSet mainScenariors = mainScenarioSTMT
					.executeQuery("select * from [Scenarios$] where ScenarioName like '%_"
							+ BrowserValue + "'");
			while (mainScenariors.next()) {
				String scenarioname = mainScenariors.getString("ScenarioName");
				int index = scenarioname.lastIndexOf("_");
				String actualScenarioName = scenarioname.substring(0, index);
				File mainScreen = new File(filePath + "ScenarioDetails_"
						+ BrowserValue + ".html");
				if (!mainScreen.exists()) {
					mainScreen.createNewFile();
				}
				FileWriter fstream = new FileWriter(mainScreen);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(getHTMLPageHeader());
				out.write(backButton);
				out.write(getMainScreenHeaderDetails(totTime, startTime,
						endTime, GenericMethods.getPropertyValue(
								"applicationVer", configurationStructurePath), "", "", url, username, os));
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTRTag
						+ htmlTdTag + SlNo + htmlTdEndTag;
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ htmlLinkTag + scenarioname + ".xlsx" + htmlStyleTag
						+ scenarioname + htmlTdEndTag;
				
				String ScenarioStatus = mainScenariors.getString("Status");
								
								if(ScenarioStatus.equalsIgnoreCase("Fail"))
								{
								strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
										+ htmlLinkTag + actualScenarioName
										+ "_SubScenarioDetails.html" + htmlStyleTag_redcolor
										+ ScenarioStatus + htmlTdEndTag;
								}
								if(ScenarioStatus.equalsIgnoreCase("Pass"))
								{
								strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
										+ htmlLinkTag + actualScenarioName
										+ "_SubScenarioDetails.html" + htmlStyleTag_greencolor 
										+ ScenarioStatus + htmlTdEndTag;
								}
								if(ScenarioStatus.equalsIgnoreCase("skipped"))
								{
								strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
										+ htmlLinkTag + actualScenarioName
										+ "_SubScenarioDetails.html" + htmlStyleTag_browncolor
										+ ScenarioStatus + htmlTdEndTag;
								}
								
								/*strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
										+ htmlLinkTag + actualScenarioName
										+ "_SubScenarioDetails.html" + htmlStyleTag
										+ mainScenariors.getString("Status") + htmlTdEndTag;*/
								
								
								
				strScenarioBrowserReport = strScenarioBrowserReport + htmlTdTag
						+ mainScenariors.getString("Time") + htmlTdEndTag;
				out.write(strScenarioBrowserReport);
				SlNo = SlNo + 1;
				
				
				String Browserdetail = null;
				ArrayList<String> BrowserValues = GenerateReports
						.getScenario_UniqueBrowser();
				//out.write(strTestDetailss);
				//strTestDetails=strTestDetailss;
				String strTestDetails =  htmlEndTableTag+"<tr><td class=\"SummaryHeaderTest\" align=\"Center\">"+"TEST CASE EXECUTION DETAILS"+"</td></tr><tr>";
				strTestDetails = strTestDetails + htmlEndTableTag
				+ htmlTableStart_borderTag + "<tr>"+
				"<th class=\"div_bg_heading\">Test Case Details</th>"+
				"<th class=\"div_bg_heading\">Count</th>"
				+"</tr>";		
				Browserdetail = null;
				for (int i = 0; i < BrowserValues.size(); i++) {
					if (Browserdetail == null) {
						Browserdetail =   htmlTdTagClass + "  " +getTestCaseCount("Yes")
						+ "  " + htmlTdEndTag;
					}

				}
				//System.out.println("Browserdetail======"+Browserdetail);
				//System.out.println("str details::::"+strTestDetails);
				
				strTestDetails = strTestDetails + headingTRTag+"Total No Of Test Cases Available:" + Browserdetail + htmlTREndTag;
				

				Browserdetail = null;
				for (int i = 0; i < BrowserValues.size(); i++) {
					if (Browserdetail == null) {
						Browserdetail =   htmlTdTagClass + "  " +
								 getTestCaseStatus(BrowserValues.get(i),actualScenarioName,scenarioname,"Pass") + "  " + htmlTdEndTag;
					}

				}
				
				strTestDetails = strTestDetails + headingTRTag+"Total No Of Pass Test Cases:" + Browserdetail + htmlTREndTag;

				Browserdetail = null;
				for (int i = 0; i < BrowserValues.size(); i++) {
					if (Browserdetail == null) {
						Browserdetail =   htmlTdTagClass + "  " + getTestCaseStatus(BrowserValues.get(i),actualScenarioName,scenarioname,"PF") + "  " + htmlTdEndTag;
					}

				}
				strTestDetails = strTestDetails + headingTRTag+"Total No Of Fail Test Cases:" + Browserdetail + htmlTREndTag;

				Browserdetail = null;
				for (int i = 0; i < BrowserValues.size(); i++) {
					if (Browserdetail == null) {
						Browserdetail =  htmlTdTagClass + "  " + getTestCaseStatus(BrowserValues.get(i),actualScenarioName,scenarioname,"Skipped") + "  " + htmlTdEndTag;
					}

				}
				strTestDetails = strTestDetails + headingTRTag+"Total No Of Skipped Test Cases:" + Browserdetail + htmlTREndTag+"</table><br> <p> </p>";
				
				BrowserValues.clear();
				
				System.out.println("****************************************************");
				System.out.println(strTestDetails);
				System.out.println("****************************************************");
				out.write(strTestDetails);
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DBConnectionManager.close(con);
	}

	/**
	 * Description: Below method is to get count from Scenario.xlsx, by taking
	 * Browser type and Status as input.
	 * 
	 * @param BrowserType
	 *            : Browser value should be provided.(Ex: ie,firefox,chrome).
	 * @param Status
	 *            : Status value should be provided.(Ex: Pass, Fail, Skipped).
	 * @return: Based on BrowserType and Status input, it will return the count.
	 * @author: Sandeep Jain
	 * @Date: May 26th 2014
	 */
	public static int summary_Count(Object BrowserType, String Status) {

		int countValue = 0;

		String filePath = reportStructurePath + "\\"
				+ res.getExectionDateTime() + "\\";
		Connection con = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String singleWorkBookPath = reportStructurePath + "\\"
					+ res.getExectionDateTime() + "\\Scenario.xlsx";

			con = DriverManager
					.getConnection(
							"jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="
									+ singleWorkBookPath
									+ ";"
									+ "READONLY=false", "", "");

			Statement mainScenarioSTMT = con.createStatement();
			ResultSet mainScenariors = null;
			if (!Status.equals("")) {
				mainScenariors = mainScenarioSTMT
						.executeQuery("select * from [Scenarios$] where ScenarioName like '%_"
								+ BrowserType + "' AND Status='" + Status + "'");
			} else {
				mainScenariors = mainScenarioSTMT
						.executeQuery("select * from [Scenarios$] where ScenarioName like '%_"
								+ BrowserType + "'");
			}

			while (mainScenariors.next()) {
				countValue = countValue + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DBConnectionManager.close(con);
		return countValue;

	}

	/**
	 * @Description: Below method is to fetch subscenariono for ScenarioName
	 *               input from ControlSheet of
	 * @param scenarioName
	 *            : Need to pass scenarioname
	 * @return: It return of arraylist which will contain the subscenariono for
	 *          scenarionname input.
	 * @author Sandeep Jain
	 * @Date: May 26 2014
	 */
	public static ArrayList<String> getScenario_SubScenarioNo(
			String scenarioName) {
		ArrayList<String> Browserdetails = new ArrayList<String>();
		try {
			String singleWorkPath = System.getProperty("user.dir")
					+ GenericMethods.getPropertyValue("singleworkbookPath",
							path);
			Connection con_SubScenario = DBConnectionManager
					.getConnection(singleWorkPath);
			Statement mainsSubScenarioSTMT = con_SubScenario.createStatement();
			ResultSet mainsSubScenarioRS = mainsSubScenarioSTMT
					.executeQuery("select "+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" from [ControlSheet$] where "+CyborgConstants.DRIVER_BOOK_TEST_PLAN_REQUIRED+"='YES' and "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
							+ scenarioName + "'");
			while (mainsSubScenarioRS.next()) {
				String hello = mainsSubScenarioRS.getString(CyborgConstants.DRIVER_BOOK_SCENARIO_NAME);
				if (Browserdetails.isEmpty()) {
					Browserdetails.add(hello);
				}
				if (!Browserdetails.isEmpty()
						&& !Browserdetails.contains(hello)) {
					Browserdetails.add(hello);
				}
			}
			con_SubScenario.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Browserdetails;
	}

	/**
	 * @Description: Below method is to fetch Unique Browser values for
	 *               ScenarioName and subscenarion Below method is to fetch
	 *               Unique Browsers for input ScenarioName from Control sheet
	 *               of Scenariodriverworkbook.xlsx values from Control sheet of
	 *               Scenariodriverworkbook.xlsx
	 * @param scenarioName
	 *            : Need to provide scenarioName
	 * @param SubScenario
	 *            : Need to provide SubscenarioName
	 * @return: Returns Browser Type in an arraylist.
	 * @author SandeepJ
	 * @Date: may 26 2014
	 */
	public static ArrayList<String> getScenario_BrowserUniqueValues(
			String scenarioName, String SubScenario) {
		ArrayList<String> UniqueBrowser = new ArrayList<String>();
		try {
			String singleWorkPath = System.getProperty("user.dir")
					+ GenericMethods.getPropertyValue("singleworkbookPath",
							path);
			Connection con_Browser = DBConnectionManager
					.getConnection(singleWorkPath);
			Statement mainsScenarioNoBrowserTypeSTMT = con_Browser
					.createStatement();
			ResultSet mainsScenarioNoBrowserTypeRS = mainsScenarioNoBrowserTypeSTMT
					.executeQuery("select "+CyborgConstants.DRIVER_BOOK_BROWSER_TYPE+" from [ControlSheet$] where "+CyborgConstants.DRIVER_BOOK_TEST_PLAN_REQUIRED+"='YES' and "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
							+ scenarioName
							+ "' and "+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+"='"
							+ SubScenario + "'");
			
			ArrayList<String> Browserdetails = new ArrayList<String>();
			// ArrayList UniqueBrowser = new ArrayList();
			while (mainsScenarioNoBrowserTypeRS.next()) {
				String mainScenarioBrowserType = mainsScenarioNoBrowserTypeRS
						.getString(CyborgConstants.DRIVER_BOOK_BROWSER_TYPE);
				if (Browserdetails.isEmpty()) {
					Browserdetails.add(mainScenarioBrowserType);
				}
				if (!Browserdetails.isEmpty()
						&& !Browserdetails.contains(mainScenarioBrowserType)) {
					Browserdetails.add(mainScenarioBrowserType);
				}
				if (Browserdetails.size() > 1) {
					for (int i = 0; i < Browserdetails.size(); i++) {
						String Browser = (String) Browserdetails.get(i);
						if (Browser.contains(",")) {
							String[] Browserdata = Browser.split(",");
							for (int j = 0; j < Browserdata.length; j++) {
								if (UniqueBrowser.isEmpty()) {
									UniqueBrowser.add(Browserdata[j]);
								}
								if (!UniqueBrowser.isEmpty()
										&& !UniqueBrowser
												.contains(Browserdata[j])) {
									UniqueBrowser.add(Browserdata[j]);
								}
							}
						}

						else {
							if (UniqueBrowser.isEmpty()) {
								UniqueBrowser.add(Browserdetails.get(i));
							}
							if (!UniqueBrowser.isEmpty()
									&& !UniqueBrowser.contains(Browserdetails
											.get(i))) {
								UniqueBrowser.add(Browserdetails.get(i));
							}
						}
					}
				}

				if (Browserdetails.size() == 1) {
					String Browser = (String) Browserdetails.get(0);
					if (Browser.contains(",")) {
						String[] Browserdata = Browser.split(",");
						for (int j = 0; j < Browserdata.length; j++) {
							if (UniqueBrowser.isEmpty()) {
								UniqueBrowser.add(Browserdata[j]);
							}
							if (!UniqueBrowser.isEmpty()
									&& !UniqueBrowser.contains(Browserdata[j])) {
								UniqueBrowser.add(Browserdata[j]);
							}
						}
					} else {
						if (UniqueBrowser.isEmpty()) {
							UniqueBrowser.add(Browserdetails.get(0));
						}
						if (!UniqueBrowser.isEmpty()
								&& !UniqueBrowser.contains(Browserdetails
										.get(0))) {
							UniqueBrowser.add(Browserdetails.get(0));
						}
					}
				}
			}

			Browserdetails.clear();
			con_Browser.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return UniqueBrowser;
	}

	/**
	 * @Description: Below method is to get unique scenarioNames from
	 *               Scenariodriverworkbook.xlsx
	 * @return: ScenarioNames in ArrayList
	 * @author SandeepJ
	 * @Date: May 26 2014
	 */
	public static ArrayList<String> getScenarioNames() {
		ArrayList<String> ScenarioNames = new ArrayList<String>();
		Connection con = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String singleWorkBookPath = System.getProperty("user.dir")
					+ "\\TestData\\Scenariodriverworkbook.xlsx";
			con = DBConnectionManager.getConnection(singleWorkBookPath);
			Statement mainScenarioSTMT = con.createStatement();
			ResultSet mainScenariors = mainScenarioSTMT
					.executeQuery("select distinct "+CyborgConstants.DRIVER_BOOK_TEST_PALN+" from [ControlSheet$] where "+CyborgConstants.DRIVER_BOOK_TEST_PLAN_REQUIRED+"='Yes'");
			while (mainScenariors.next()) {
				ScenarioNames.add(mainScenariors.getString(CyborgConstants.DRIVER_BOOK_TEST_PALN));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		DBConnectionManager.close(con);
		return ScenarioNames;
	}

	
	
	public static HashMap<String, String> getConfigurationDetails()
	{
		HashMap<String, String> ConfigurationDetailsHashMap = new HashMap<String, String>();
		Connection con = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String singleWorkBookPath = System.getProperty("user.dir")
			+ "\\TestData\\Scenariodriverworkbook.xlsx";
			con = DBConnectionManager.getConnection(singleWorkBookPath);
			Statement mainScenarioSTMT = con.createStatement();
			ResultSet mainScenariors = mainScenarioSTMT
			.executeQuery("select * from [Configurations$]");

			while (mainScenariors.next()) {

				ConfigurationDetailsHashMap.put("URL", mainScenariors.getString("URL"));
				ConfigurationDetailsHashMap.put("USERNAME_IE", mainScenariors.getString("USERNAME_IE"));
				ConfigurationDetailsHashMap.put("PASSWORD_IE", mainScenariors.getString("PASSWORD_IE"));
				ConfigurationDetailsHashMap.put("USERNAME_FIREFOX", mainScenariors.getString("USERNAME_FIREFOX"));
				ConfigurationDetailsHashMap.put("PASSWORD_FIREFOX", mainScenariors.getString("PASSWORD_FIREFOX"));
				ConfigurationDetailsHashMap.put("USERNAME_CHROME", mainScenariors.getString("USERNAME_CHROME"));
				ConfigurationDetailsHashMap.put("PASSWORD_CHROME", mainScenariors.getString("PASSWORD_CHROME"));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DBConnectionManager.close(con);
		return ConfigurationDetailsHashMap;
	}
	
	
	
	public static String getAssertionDetails(String ScenarioName,String FunctionNames, String BrowserType){
		String resultXl = reportStructurePath+"\\"+res.getExectionDateTime()+"\\"+ScenarioName+"_"+BrowserType+".xlsx";
		String testLinkAssert="Passed";
		String FinalDescription="-";
		try {
			//System.out.println("&&&&FunctionNames&&&&&&"+FunctionNames);
			Connection con = DBConnectionManager.getConnection(resultXl);
			Statement scenarioStatusSTMT = con.createStatement();
			/*System.out.println("select * from [Driver$] where "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
							+ ScenarioName + "'and TEST_CASE='"+ FunctionNames+"'");*/
			ResultSet scenarioStatusRs = scenarioStatusSTMT
					.executeQuery("select * from [Driver$] where "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
							+ ScenarioName + "'and TEST_CASE='"+ FunctionNames+"'" );
			//System.out.println("scenarioStatusRs.next()======"+scenarioStatusRs.next());
			while (scenarioStatusRs.next()) {
			String[] FunctionName=scenarioStatusRs.getString(CyborgConstants.DRIVER_BOOK_TESTCASE_NAME).split("/");
			//System.out.println("FunctionName[1]======="+FunctionName[1]);
			String status=scenarioStatusRs.getString("Status");
			//System.out.println("&&&&&&&&&&&"+status);
			testLinkAssert=status;
			if(testLinkAssert.equalsIgnoreCase("fail")){
				Connection assertCon = DBConnectionManager.getConnection(resultXl);
				Statement assertStmt = assertCon.createStatement();
				ResultSet assertStatusStmt = assertStmt
						.executeQuery("select * from [AssertionReports$] where "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
								+ ScenarioName + "'and Status ='Fail' and "+CyborgConstants.DRIVER_BOOK_TESTCASE_NAME+"='"+FunctionName[1]+"'"  );
				if(assertStatusStmt.next()){
				while (assertStatusStmt.next()) {
					//System.out.println("inside looop");
					 FinalDescription=assertStatusStmt.getString("Description")+"for which Expected vaue is :"+assertStatusStmt.getString("ExpectedValue")+"  and Actual Value is :"+assertStatusStmt.getString("ActualValue");
					
				}
			}
				else{
					testLinkAssert="Fail";
				}
			}
			}
		}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FinalDescription= FinalDescription+"- Please refer Reports in the specified path for results  : "+reportStructurePath+"\\"+res.getExectionDateTime();
		//System.out.println("testLinkAssert===="+testLinkAssert);
		//System.out.println("FinalDescription===="+FinalDescription);
		return testLinkAssert+"#"+FinalDescription;
	}
	
	//
	public static int getTestCaseStatus(Object BrowserType,String actualScenarioName,String scenarioName,String Status ) {
	             
		Connection con = null;
		int Skipped = 0;
		int Pass = 0;
		int Fail = 0;
		int strStatusReport=0;
		try {

		            String singleWorkBookPath = reportStructurePath + "\\"
		            + res.getExectionDateTime() + "\\" + scenarioName
		            + ".xlsx";
		            con = DBConnectionManager.getConnection(singleWorkBookPath);
		            Statement mainScenarioSTMT = con.createStatement();
		            ResultSet mainScenariors = mainScenarioSTMT
		            .executeQuery("select * from [Driver$] where "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
		                        + actualScenarioName
		                        + "'");
		            while (mainScenariors.next()) {
		                  String FunctionstatusValue = mainScenariors
		                  .getString("FunctionStatus");
		                  String Statusvalue = mainScenariors.getString("Status");
		                 //Sandeep-Below if condition is updated with "screenshot_htmlLinkTag" so that screenshot will be opened in new tab.
		                  if (FunctionstatusValue != null&&FunctionstatusValue.equalsIgnoreCase("Fail")) {
		                	  Fail = Fail + 1;
		                  } else if (Statusvalue.equalsIgnoreCase("Fail")) {
		                	  Fail = Fail + 1;
		                  } else if (Statusvalue.equalsIgnoreCase("skipped")) {
		                	  Skipped=Skipped+1;
		                  }

		                  else {
		                	  Pass=Pass+1;
		                       
		                  }
		            }
		            if(Status.equalsIgnoreCase("PF")){
		            	strStatusReport=Fail;
		            }else if(Status.equalsIgnoreCase("Skipped")){
		            	strStatusReport=Skipped;
		            }else{
		            	strStatusReport=Pass;
		            }
		            
		      } catch (Exception e) {
		            e.printStackTrace();
		      }
		      DBConnectionManager.close(con);
		      return strStatusReport;

		}
	
	/**
	 * @Description: Below method is to get TestCase count from
	 *               Scenariodriverworkbook.xlsx based on mainScenarioRequired
	 *               parameter
	 * @param mainScenarioRequired
	 *            :Have to pass 'Yes' or 'No' value.
	 * @return: It returns the count of ScenarioName which has status 'Yes' in
	 *          Scenariodriverworkbook
	 * @author: Srikala Gudavalli
	 * @Date:9/24/2014
	 */
	public static int getTestCaseCount(String mainScenarioRequired) {
		int scenarioCount = 0;
		Connection con = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String singleWorkBookPath = System.getProperty("user.dir")
					+ "\\TestData\\Scenariodriverworkbook.xlsx";
			con = DBConnectionManager.getConnection(singleWorkBookPath);
			Statement mainScenarioSTMT = con.createStatement();
			/*System.out.println("select distinct "+CyborgConstants.DRIVER_BOOK_TESTCASE_NAME+" from [ControlSheet$] where "+CyborgConstants.DRIVER_BOOK_TEST_PLAN_REQUIRED+"='"
							+ mainScenarioRequired + "' AND "+CyborgConstants.DRIVER_BOOK_SCENARIO_REQUIRED+"='"
							+ mainScenarioRequired +"' AND "+ CyborgConstants.DRIVER_BOOK_TESTCASE_REQUIRED+"='"
							+ mainScenarioRequired +"'");*/
			ResultSet mainScenariors = mainScenarioSTMT
					.executeQuery("select distinct "+CyborgConstants.DRIVER_BOOK_TESTCASE_NAME+" from [ControlSheet$] where "+CyborgConstants.DRIVER_BOOK_TEST_PLAN_REQUIRED+"='"
							+ mainScenarioRequired + "' AND "+CyborgConstants.DRIVER_BOOK_SCENARIO_REQUIRED+"='"
							+ mainScenarioRequired +"' AND "+ CyborgConstants.DRIVER_BOOK_TESTCASE_REQUIRED+"='"
							+ mainScenarioRequired +"'");

			while (mainScenariors.next()) {
				scenarioCount = scenarioCount + 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		DBConnectionManager.close(con);
		return scenarioCount;
	}
	
	/**
	 * @Description: Below method is to get Total execution times of a TestCase count from
	 *               Scenariodriverworkbook.xlsx based on mainScenarioRequired
	 *               parameter
	 * @author: Srikala Gudavalli
	 */
	private static String getExecutionHistory(String strFunctionReport,
			ResultSet mainScenariors,String scenarioDetails,String ScenarioName,String FunctionName,String SubScenarioNo,int functionReport_SlNo2,String FunctionstatusValue,String Statusvalue,String Time) throws SQLException, ClassNotFoundException {
		String workBookPath = System.getProperty("user.dir")
		+ "\\Configurations\\" + scenarioDetails + "_history.xlsx";
		int TestCaseHistory=0;
		String FuncName="";
		System.out.println("workBookPath==="+workBookPath);
      Connection  con = DBConnectionManager.getConnection(workBookPath);
        Statement mainScenarioSTMT = con.createStatement();
        System.out.println("select * from [Driver$] where "+CyborgConstants.DRIVER_BOOK_TEST_PALN+" ='"
                + ScenarioName
                + "' and "+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+"='" + SubScenarioNo + "'and "+CyborgConstants.DRIVER_BOOK_TESTCASE_NAME+" ='"
                + FunctionName + "'");
         mainScenariors = mainScenarioSTMT
        .executeQuery("select * from [Driver$] where "+CyborgConstants.DRIVER_BOOK_TEST_PALN+" ='"
                + ScenarioName
                + "' and "+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+"='" + SubScenarioNo + "'and "+CyborgConstants.DRIVER_BOOK_TESTCASE_NAME+" ='"
                + FunctionName + "'");
         while (mainScenariors.next()) {
        	 FuncName= mainScenariors.getString(CyborgConstants.DRIVER_BOOK_TESTCASE_NAME);
        	System.out.println("Statusvalue execution history"+Statusvalue+"===Status==="+FuncName);
        	if(Statusvalue.equalsIgnoreCase("Pass")||Statusvalue.equalsIgnoreCase("Fail")){
          TestCaseHistory = mainScenariors.getInt("TestCaseHistory");
          System.out.println("TestCaseHistory===="+TestCaseHistory);
		   TestCaseHistory =TestCaseHistory+1;
        	}else{
        		 TestCaseHistory = mainScenariors.getInt("TestCaseHistory");
        	}
		   System.out.println("TestCaseHistory==="+TestCaseHistory);
		  if(Integer.toString(TestCaseHistory)!=null){
		  strFunctionReport = strFunctionReport + htmlTdTag
		  + TestCaseHistory + htmlTdEndTag;
		  }
		  
		  
		  
         }
         
         if(TestCaseHistory!=0){
        	 Connection UpdateCon = DBConnectionManager
 			.getConnection(workBookPath);
 	Statement scenarioUpdateSTMT = UpdateCon
 			.createStatement();
 	UpdateCon.prepareStatement(
 			"update [Driver$] set TestCaseHistory = '" + TestCaseHistory
 					+ "' where "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
                     + ScenarioName
                     + "' and "+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" ='"
                     + SubScenarioNo +  "' and "+CyborgConstants.DRIVER_BOOK_TESTCASE_NAME+" ='"
                     + FunctionName + "'").execute();
 	UpdateCon.prepareStatement(
 			"update [Driver$] set FunctionStatus = '" + FunctionstatusValue
 			+ "' where "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
            + ScenarioName
            + "' and "+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" ='"
            + SubScenarioNo +  "' and "+CyborgConstants.DRIVER_BOOK_TESTCASE_NAME+" ='"
            + FunctionName + "'").execute();
 	UpdateCon.prepareStatement(
 			"update [Driver$] set Status = '" + Statusvalue
 			+ "' where "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
            + ScenarioName
            + "' and "+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" ='"
            + SubScenarioNo +  "' and "+CyborgConstants.DRIVER_BOOK_TESTCASE_NAME+" ='"
            + FunctionName + "'").execute();
 	System.out.println("*****&&&&&^^^^%%%%%$$$$$$"+Time+"------------------------"+"update [Driver$] set Time = '" + Time
 			+ "' where "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
            + ScenarioName
            + "' and "+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" ='"
            + SubScenarioNo +  "' and "+CyborgConstants.DRIVER_BOOK_TESTCASE_NAME+" ='"
            + FunctionName + "'");
 	/*UpdateCon.prepareStatement(
 			"update [Driver$] set Time = '" + Time
 					+ "' where ScenarioName='"
                     + ScenarioName
                     + "' and SubScenarioNo ='"
                     + SubScenarioNo +  "' and FunctionName ='"
                     + FunctionName + "'").execute();*/
         }
         else{
        	 if(!FunctionName.equalsIgnoreCase(FuncName)){
        	 System.out.println("TestCaseHistory in else"+TestCaseHistory);
         Connection UpdateCon = DBConnectionManager
			.getConnection(workBookPath);
	Statement scenarioUpdateSTMT = UpdateCon
			.createStatement();
	UpdateCon.prepareStatement(
			"insert into [Driver$]([Slno],[TEST_PLAN],[TEST_SUITE],[TEST_CASE],[TestCaseHistory],[Time],[Status],[FunctionStatus]) values('"
			+ functionReport_SlNo2
			+ "','"
			+ ScenarioName
			+ "','"
			+ SubScenarioNo
			+ "','"
			//+ "DataSet"
			+ FunctionName +  "','"+TestCaseHistory+"','"+Time+"','"+Statusvalue+"','"+FunctionstatusValue+"')")
			.execute();
        	 }
         }
         
		return strFunctionReport;
	}
	
	
	public static String ScenarioReports() {
		  String strScenarioBrowserReport = null;
		  /*String filePath = GenerateReports.reportStructurePath + "/"
		    + res.getExectionDateTime() + "/";*/
		  String filePath = GenerateReports.reportStructurePath + "/"
		  + res.getExectionDateTime() + "/";
		  Connection  con =null;
		  try {
			  String singleWorkBookPath = GenerateReports.reportStructurePath + "/"
			     + res.getExectionDateTime()+ "/Scenario.xlsx";
		 /*  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		   
		   con = DriverManager
		     .getConnection(
		       "jdbc:odbc:Driver={Microsoft Excel Driver (.xls, .xlsx, .xlsm, .xlsb)};DBQ="
		         + singleWorkBookPath
		         + ";"
		         + "READONLY=false", "", "");*/
			  System.out.println("workBookPath==="+singleWorkBookPath);
		     con = DBConnectionManager.getConnection(singleWorkBookPath);
		   Statement mainScenarioSTMT = con.createStatement();
		   ResultSet mainScenariors = mainScenarioSTMT
		     .executeQuery("select * from [Scenarios$]");
		   
		   while (mainScenariors.next()) {
		    String scenarioname = mainScenariors.getString("ScenarioName");
		    strScenarioBrowserReport =  scenarioname;
		    strScenarioBrowserReport = strScenarioBrowserReport +"                       "
		      + mainScenariors.getString("Status");
		    System.out.println("******************"+strScenarioBrowserReport);
		    
		   }
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		  
		  DBConnectionManager.close(con);
		  return strScenarioBrowserReport;
		 }
}
