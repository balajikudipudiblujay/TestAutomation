package DriverMethods;

import global.reusables.CommonBean;
import global.reusables.GenerateReports;
import global.reusables.GenericMethods;
import global.reusables.MailUtil;

import global.reusables.ZipUtils;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.apache.tools.ant.taskdefs.Copyfile;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;


public class StartUp extends WebDriverInitilization {
	public static final String OUTPUT_ZIP_FILE = System.getProperty("user.dir")
	+ "/Configurations/Reports.zip";
	public static final String SOURCE_FOLDER = System.getProperty("user.dir")
	+ "/Reports";
	Calendar cal = Calendar.getInstance();
	public static HashMap< String, String> testCaseap= new HashMap<String, String>();
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	String startTime = null;
	public static String BrowserType;

	@BeforeSuite
	public void beforeSuite() {
		res = new CommonBean();
		DateFormat dt = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
		Date date = new Date();
		String d = dt.format(date).toString();
		startTime = sdf.format(cal.getTime());
		res.setExectionDateTime(d);
		GenericMethods.setPropertyValue("log4j.appender.R.File", reportStructurePath 
				+"\\logs\\"+"KF-Logs_"+ res.getExectionDateTime()+".log", System.getProperty("user.dir")+"\\Configurations\\log4j.properties");
		System.out.println("Log file updated::::");
		File directory = new File(reportStructurePath + "/"
				+ res.getExectionDateTime());
		//System.out.println("--" + directory);
		if (!directory.exists()) {
			directory.mkdir();
		}

		File dir1 = new File(reportStructurePath + "/");
		if (dir1.isDirectory()) {
			File[] content = dir1.listFiles();
			for (int i = 0; i < content.length; i++) {
				if (content[i].getName().endsWith("xlsx")) {
					try {
						//System.out.println(content[i]);
						File src = new File(content[i] + "");
						FileUtils.copyFile(src, new File(reportStructurePath
								+ "/" + d + "/" + content[i].getName()));
						src.delete();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	@Parameters({ "ScenarioName", "SubScenario", "BrowserType" })
	@BeforeTest
	public void beforeTest(String ScenarioName, String SubScenario,
			String BrowserType) {
		GenericMethods.startRecording();
		System.out.println("ScenarioName" + ScenarioName);
//		testCaseap=TestCaseDetails.getTestCaseMapped(ScenarioName,SubScenario);
		res.setScenarioClassName(ScenarioName);
		res.setSubScenarioDetails(SubScenario);
		PropertyConfigurator.configure(System.getProperty("user.dir")+"\\Configurations\\log4j.properties");
		System.out.println("configurator loaded");
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		Calendar cal1 = Calendar.getInstance();
		String endtime = sdf.format(cal1.getTime());
		String time = GenericMethods.Time(startTime, endtime);
		GenerateReports.Assertion_Reports();
		GenerateReports.scenariosFunctionReports();
		GenerateReports.subScenarioDetailsReports();
		GenerateReports.mainScreenDetailsReports(time, startTime, endtime);
		GenerateReports.startPageDetailsReports();
	GenericMethods.stopRecording();

		ZipUtils appZip = new ZipUtils(); 
		appZip.generateFileList(new
				File(SOURCE_FOLDER+"\\"+res.getExectionDateTime())); appZip.zipIt(OUTPUT_ZIP_FILE); 
				try {
					
					MailUtil.sendMail(); 
					}
				catch (Exception e) { e.printStackTrace(); }
				GenericMethods.backUpHistoryFile();

	}

}
