package global.reusables;

import java.sql.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GenerateControlfiles {
	public static int hh = 1;
	public static String path = System.getProperty("user.dir")
			+ "\\Configurations\\base.properties";
	public static void generateSwb() throws ParserConfigurationException,
			InvalidFormatException, IOException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Transformer transformer = null;
		Document doc = docBuilder.newDocument();
		Element rootElem = doc.createElement("suite");
		doc.appendChild(rootElem);

		rootElem.setAttributeNode(getAttribute("name", "Suite", doc));
		rootElem.setAttributeNode(getAttribute("preserve-order", "true", doc));
		String PrarallelRequired = GenericMethods.getPropertyValue("parallel",
				path);
		if (PrarallelRequired.equalsIgnoreCase("Yes")
				|| PrarallelRequired.equalsIgnoreCase("true")) {
			rootElem.setAttributeNode(getAttribute("parallel", "tests", doc));
		}

		try {
			File file = new File(System.getProperty("user.dir")
					+ "\\TestData\\Scenario.xlsx");
			File destFile = new File(System.getProperty("user.dir")
					+ "\\Reports\\Scenario.xlsx");
			if (!destFile.exists()) {
				FileUtils.copyFile(file, destFile);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			String singleWorkBookPath = System.getProperty("user.dir")
					+ GenericMethods.getPropertyValue("singleworkbookPath",
							path);
			Connection con = DBConnectionManager
					.getConnection(singleWorkBookPath);
			Statement mainScenarioSTMT = con.createStatement();
			ResultSet mainScenariors = mainScenarioSTMT
					.executeQuery("select distinct "+CyborgConstants.DRIVER_BOOK_TEST_PALN +",Sr_No  from [ControlSheet$] where "+CyborgConstants.DRIVER_BOOK_TEST_PLAN_REQUIRED+"='YES'and " +CyborgConstants.DRIVER_BOOK_TESTCASE_REQUIRED+"='yes'and "+CyborgConstants.DRIVER_BOOK_SCENARIO_REQUIRED+"='yes'ORDER BY Sr_No");
			ResultSetMetaData mainScenarioRS = mainScenariors.getMetaData();
			int numberOfColumns = mainScenarioRS.getColumnCount();
			int counter = 0;
			while (mainScenariors.next()) {
				counter = counter + 1;
				for (int i = 1; i <= numberOfColumns; i++) {
					hh = 1;
					if (i > 1)
						System.out.println(", ");
					String mainScenario = mainScenariors.getString(i);
					setValueforScenario(i + "", mainScenario);
					try {
						File file = new File(System.getProperty("user.dir")
								+ "\\TestData\\ReportsHTML.xlsx");
						File destFile = new File(System.getProperty("user.dir")
								+ "\\Reports\\" + mainScenario + ".xlsx");
						//Added for Test Case History-Srikala
						File finalDestFile = new File(System.getProperty("user.dir")
								+ "/Configurations/"+mainScenario + ".xlsx");
						if (!destFile.exists()) {
							FileUtils.copyFile(file, destFile);
						}
						//Added for Test Case History-Srikala
						if(!finalDestFile.exists()){
							FileUtils.copyFile(file, finalDestFile);
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					Statement mainsSubScenarioNoSTMT = con.createStatement();
					ResultSet mainsSubScenarioNoRS = mainsSubScenarioNoSTMT
							.executeQuery("select distinct "+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME +","+CyborgConstants.DRIVER_BOOK_SCENARIO_NO+" from [ControlSheet$] where "+ CyborgConstants.DRIVER_BOOK_TEST_PLAN_REQUIRED+"='YES' and "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
									+ mainScenario
									+ "' and "+CyborgConstants.DRIVER_BOOK_SCENARIO_REQUIRED+"='yes' ORDER BY "+CyborgConstants.DRIVER_BOOK_SCENARIO_NO);

					/*ResultSetMetaData mainsSubScenarioNo = mainsSubScenarioNoRS
							.getMetaData();
					int count = mainsSubScenarioNo.getColumnCount();*/
					List<String> mainScenariosSubScenarioList = new ArrayList<String>();
					while (mainsSubScenarioNoRS.next()) {
						String mainSubScenario = mainsSubScenarioNoRS
								.getString(CyborgConstants.DRIVER_BOOK_SCENARIO_NAME);
						mainScenariosSubScenarioList.add(mainSubScenario);
					}

					for (int k = 0; k < mainScenariosSubScenarioList.size(); k++) {
						String ScenarioDetails = mainScenario + "_SubScenario"
								+ mainScenariosSubScenarioList.get(k);
						String SubScenarioDetails = mainScenariosSubScenarioList.get(k);

						Statement mainsScenarioNoBrowserTypeSTMT = con
								.createStatement();
						System.out.println(mainScenario+"*************"+mainScenariosSubScenarioList.get(k));
						ResultSet mainsScenarioNoBrowserTypeRS = mainsScenarioNoBrowserTypeSTMT
								.executeQuery("select distinct "+CyborgConstants.DRIVER_BOOK_BROWSER_TYPE +" from [ControlSheet$] where  "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
										+ mainScenario
										+ "' and "+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+"='"
										+ mainScenariosSubScenarioList.get(k)
										+ "'");
						
						
						/*ResultSetMetaData mainsScenarioBrowserType = mainsScenarioNoBrowserTypeRS
								.getMetaData();
						int datasetDetails = mainsScenarioBrowserType
								.getColumnCount();*/
						List<String> mainScenarioBrowserTypeList = new ArrayList<String>();
						while (mainsScenarioNoBrowserTypeRS.next()) {
							String mainScenarioBrowserType = mainsScenarioNoBrowserTypeRS
									.getString(CyborgConstants.DRIVER_BOOK_BROWSER_TYPE);
							mainScenarioBrowserTypeList
									.add(mainScenarioBrowserType);
						}
						String BrowserTypeValue = mainScenarioBrowserTypeList
								.get(0).toString();
						Statement functionFieldSTMT = con.createStatement();
						ResultSet functionFieldRS = functionFieldSTMT
								.executeQuery("select "+CyborgConstants.DRIVER_BOOK_TESTCASE_NAME+","+CyborgConstants.DRIVER_BOOK_DATA_SETS +" from [ControlSheet$] where "+ CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
										+ mainScenario
										+ "'and "+CyborgConstants.DRIVER_BOOK_TEST_PLAN_REQUIRED+"= 'yes' and "+CyborgConstants.DRIVER_BOOK_SCENARIO_REQUIRED+"='yes' and "+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+"='"
										+ mainScenariosSubScenarioList.get(k)
										+ "' and "+CyborgConstants.DRIVER_BOOK_TESTCASE_REQUIRED+"='yes'");

						/*ResultSetMetaData functionFieldNo = functionFieldRS
								.getMetaData();
						int functionFieldDetails = functionFieldNo
								.getColumnCount();*/
						List<String> functionFieldList = new ArrayList<String>();
						List<String> functioDataSetList = new ArrayList<String>();
						while (functionFieldRS.next()) {
							String mainScenarioDataset = functionFieldRS
									.getString(CyborgConstants.DRIVER_BOOK_TESTCASE_NAME);
							String dataSetNo = functionFieldRS
									.getString(CyborgConstants.DRIVER_BOOK_DATA_SETS);
							if (mainScenarioDataset != null) {
								functioDataSetList.add(dataSetNo);
								functionFieldList.add(mainScenarioDataset);
							}
						}

						String browserType[] = null;
						String actualScenarioDetails = null;
						if (BrowserTypeValue.contains(",")) {
							browserType = BrowserTypeValue.split(",");
							for (int browserDetails = 0; browserDetails < browserType.length; browserDetails++) {
								actualScenarioDetails = ScenarioDetails + "_"
										+ browserType[browserDetails];
								scenarioDetails(actualScenarioDetails,
										mainScenario, SubScenarioDetails,
										browserType[browserDetails], doc,
										rootElem);
							}
							genCntrlFiles(mainScenariosSubScenarioList.get(k)
									+ "", mainScenario, functionFieldList,
									functioDataSetList);
						} else {
							actualScenarioDetails = ScenarioDetails + "_"
									+ BrowserTypeValue;
							genCntrlFiles(mainScenariosSubScenarioList.get(k)
									+ "", mainScenario, functionFieldList,
									functioDataSetList);
							scenarioDetails(actualScenarioDetails,
									mainScenario, SubScenarioDetails,
									BrowserTypeValue, doc, rootElem);
						}

					}
					createScenarioBrowserReportFile(mainScenario);
					i = i + 1;
				}
			}

			mainScenarioSTMT.close();

			con.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}

		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(
				System.getProperty("user.dir") + "\\ControlFiles\\SWB.xml"));

		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

	public static Attr getAttribute(String nodeName, String Val, Document doc) {
		Attr attr = doc.createAttribute(nodeName);
		attr.setValue(Val);
		return attr;

	}

	public static void main(String[] Args) {

		try {
			generateSwb();

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void scenarioDetails(String ScenarioDetails,
			String ScenarioName, String SubScenarioDetails, String BrowserType,
			Document doc, Element rootElem) {
		Element ele1mentTest = doc.createElement("test");
		rootElem.appendChild(ele1mentTest);
		ele1mentTest
				.setAttributeNode(getAttribute("name", ScenarioDetails, doc));

		Element elementParameterScenarioName = doc.createElement("parameter");
		ele1mentTest.appendChild(elementParameterScenarioName);
		Element elementParameterSubScenarioNo = doc.createElement("parameter");
		ele1mentTest.appendChild(elementParameterSubScenarioNo);
		Element elementParameterBrowserType = doc.createElement("parameter");
		ele1mentTest.appendChild(elementParameterBrowserType);

		elementParameterScenarioName.setAttributeNode(getAttribute("name",
				"ScenarioName", doc));
		elementParameterScenarioName.setAttributeNode(getAttribute("value",
				ScenarioName, doc));
		elementParameterSubScenarioNo.setAttributeNode(getAttribute("name",
				"SubScenario", doc));
		elementParameterSubScenarioNo.setAttributeNode(getAttribute("value",
				SubScenarioDetails, doc));
		elementParameterBrowserType.setAttributeNode(getAttribute("name",
				"BrowserType", doc));
		elementParameterBrowserType.setAttributeNode(getAttribute("value",
				BrowserType, doc));

		Element elementClasses = doc.createElement("classes");
		ele1mentTest.appendChild(elementClasses);

		Element elementClass = doc.createElement("class");
		elementClasses.appendChild(elementClass);
		elementClass.setAttributeNode(getAttribute("name",
				"business.scenarios." + ScenarioName, doc));

		Element elementMethod = doc.createElement("methods");
		elementClass.appendChild(elementMethod);
		Element elementInclude = doc.createElement("include");
		elementMethod.appendChild(elementInclude);
		elementInclude.setAttributeNode(getAttribute("name", ScenarioName
				+ "Main", doc));

	}

	public static void genCntrlFiles(String subscenarioNo, String mainScenario,
			List<String> functionFieldList, List<String> datasetno) {
		DocumentBuilderFactory docFactoryCtrl = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilderCtrl = null;
		try {
			docBuilderCtrl = docFactoryCtrl.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		Transformer transformerCtrl = null;

		Document docCtrl = docBuilderCtrl.newDocument();
		Element mainScenarioCrtl = docCtrl.createElement(CyborgConstants.DRIVER_BOOK_TEST_PALN);
		docCtrl.appendChild(mainScenarioCrtl);
		mainScenarioCrtl.setAttributeNode(getAttribute("name", mainScenario,
				docCtrl));

		Element subScenarioNo = docCtrl.createElement(CyborgConstants.DRIVER_BOOK_SCENARIO_NAME);
		mainScenarioCrtl.appendChild(subScenarioNo);
		subScenarioNo.setAttributeNode(getAttribute("value", subscenarioNo,
				docCtrl));
		for (int p = 0; p < functionFieldList.size(); p++) {
			Element functionField = docCtrl.createElement(CyborgConstants.DRIVER_BOOK_TESTCASE_NAME);
			subScenarioNo.appendChild(functionField);
			functionField.setAttributeNode(getAttribute("name",
					functionFieldList.get(p) + "", docCtrl));
			functionField.setAttributeNode(getAttribute("dataSet",
					datasetno.get(p) + "", docCtrl));
			//Added extra parameter "Count" for Test Case History-Srikala
			setValue(hh + "", mainScenario, subscenarioNo, datasetno.get(p)
					+ "", functionFieldList.get(p) + "", "Skipped",0);
			hh = hh + 1;
		}
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		try {
			transformerCtrl = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}

		DOMSource source = new DOMSource(docCtrl);
		StreamResult result = new StreamResult(
				new File(System.getProperty("user.dir") + "\\ControlFiles\\"
						+ mainScenario + subscenarioNo + ".xml"));

		try {
			transformerCtrl.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

	public static void setValueforScenario(String Slno, String scenarioName) {
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
							+ scenarioName + "'");
			ArrayList<String> Browserdetails = new ArrayList<String>();
			ArrayList<String> UniqueBrowser = new ArrayList<String>();
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

			String report_ScenarioExcelPath = System.getProperty("user.dir")
					+ "\\Reports\\Scenario.xlsx";
			Connection con = DBConnectionManager
					.getConnection(report_ScenarioExcelPath);
			String Scenario = "";

			for (int j = 0; j < UniqueBrowser.size(); j++) {
				Scenario = scenarioName + "_" + UniqueBrowser.get(j);
				con.prepareStatement(
						"insert into [Scenarios$]([Slno],[ScenarioName]) values('"
								+ Slno + "','" + Scenario + "')").execute();
			}

			UniqueBrowser.clear();
			Browserdetails.clear();
			con_Browser.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void setValue(String Slno, String scenarioName,
			String subScenarioNo, String dataSetNo, String functionName,
			String status,int count) {
		try {

			String singleWorkBookPath = System.getProperty("user.dir")
					+ "\\Reports\\" + scenarioName + ".xlsx";
			Connection con = DriverManager
					.getConnection(GenericMethods.getPropertyValue("conString1", GenerateControlfiles.path)
							+ singleWorkBookPath
							+GenericMethods.getPropertyValue("conString2", GenerateControlfiles.path));
			Statement mainScenarioSTMT = con.createStatement();
			con.prepareStatement(
					"insert into [Driver$]([Slno],["+CyborgConstants.DRIVER_BOOK_TEST_PALN+"],["+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+"],["+CyborgConstants.DRIVER_BOOK_DATA_SETS+"],["+CyborgConstants.DRIVER_BOOK_TESTCASE_NAME+"],[Status]) values('"
							+ Slno
							+ "','"
							+ scenarioName
							+ "','"
							+ subScenarioNo
							+ "','"
							//+ "DataSet"
							+ dataSetNo
							+ "','" + functionName + "','" + status + "')")
					.execute();
//********Added for Test case History--Srikala*********//
			
			String originalWorkBookPath = System.getProperty("user.dir")
			+ "\\Configurations\\" + scenarioName + ".xlsx";
			
			Connection conn = DBConnectionManager
			.getConnection(originalWorkBookPath);
			Statement mainScenarioSTMT1 = conn.createStatement();
			conn.prepareStatement(
					"insert into [Driver$]([Slno],["+CyborgConstants.DRIVER_BOOK_TEST_PALN+"],["+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+"],["+CyborgConstants.DRIVER_BOOK_DATA_SETS+"],["+CyborgConstants.DRIVER_BOOK_TESTCASE_NAME+"],[Status],[TestCaseHistory]) values('"
							+ Slno
							+ "','"
							+ scenarioName
							+ "','"
							+ subScenarioNo
							+ "','"
							//+ "DataSet"
							+ dataSetNo
							+ "','" + functionName + "','" + status + "','"+count+"')")
					.execute();
			con.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


		/**
		 * This method is to create browser wise scenario excel files in Reports folder.
		 * @param scenarioName:  Scenario Name for which excel file is to be created.
		 * @author Sandeep Jain
		 */
		public static void createScenarioBrowserReportFile(String scenarioName) {
		try {

			String singleWorkPath = System.getProperty("user.dir")
					+ GenericMethods.getPropertyValue("singleworkbookPath",
							path);
			Connection con_Browser = DBConnectionManager
					.getConnection(singleWorkPath);
			Statement mainsScenarioNoBrowserTypeSTMT = con_Browser
					.createStatement();
			ResultSet mainsScenarioNoBrowserTypeRS = mainsScenarioNoBrowserTypeSTMT
					.executeQuery("select "+CyborgConstants.DRIVER_BOOK_BROWSER_TYPE +" from [ControlSheet$] where "+CyborgConstants.DRIVER_BOOK_TEST_PLAN_REQUIRED+"='YES' and "+CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
							+ scenarioName + "'");
			ArrayList<String> Browserdetails = new ArrayList<String>();
			ArrayList<String> UniqueBrowser = new ArrayList<String>();
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

			String report_ScenarioExcelPath = System.getProperty("user.dir")
					+ "\\Reports\\Scenario.xlsx";
			Connection con = DBConnectionManager
					.getConnection(report_ScenarioExcelPath);
			String Scenario = "";
			File file = new File(System.getProperty("user.dir") + "\\Reports\\"
					+ scenarioName + ".xlsx");
			//Added For Test Case History-Srikala
			File configfile = new File(System.getProperty("user.dir") + "/Configurations/"
					+ scenarioName + ".xlsx");
			for (int j = 0; j < UniqueBrowser.size(); j++) {
				Scenario = scenarioName + "_" + UniqueBrowser.get(j);
				try {
					File destFile = new File(System.getProperty("user.dir")
							+ "\\Reports\\" + Scenario + ".xlsx");
					//Added for Test Case History --Srikala
					File finalDestFile = new File(System.getProperty("user.dir")
							+ "/Configurations/" + Scenario + "_history.xlsx");
					if (!destFile.exists()) {
						FileUtils.copyFile(file, destFile);
					}
					//Added for Test Case History-Srikala
					if(!finalDestFile.exists()){
						System.out.println("Coming...");
						FileUtils.copyFile(configfile, finalDestFile);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			file.delete();
			configfile.delete();
			UniqueBrowser.clear();
			Browserdetails.clear();
			con_Browser.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	/**
	 * Below method is to fetch Unique Browsers for input ScenarioName from Control sheet of Scenariodriverworkbook.xlsx 
	 * @param scenarioName : Scenario Name of which unique browser details are required.
	 * @return: It returns unique browser values for a scenario.
	 * @author Sandeep Jain
	 */
	public static ArrayList<String> getScenario_BrowserUniqueValues(String scenarioName) {
		ArrayList<String> UniqueBrowser = new ArrayList<String>();
		try {
			String singleWorkPath = System.getProperty("user.dir")
					+ GenericMethods.getPropertyValue("singleworkbookPath",
							path);
			Connection con_Browser = DBConnectionManager
					.getConnection(singleWorkPath);
			Statement mainsScenarioNoBrowserTypeSTMT = con_Browser
					.createStatement();
			/*System.out.println("select "+CyborgConstants.DRIVER_BOOK_BROWSER_TYPE+" from [ControlSheet$] where"+ CyborgConstants.DRIVER_BOOK_TEST_PLAN_REQUIRED+"='YES' and"+ CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
					+ scenarioName + "'");*/
			ResultSet mainsScenarioNoBrowserTypeRS = mainsScenarioNoBrowserTypeSTMT
					.executeQuery("select "+CyborgConstants.DRIVER_BOOK_BROWSER_TYPE+" from [ControlSheet$] where "+ CyborgConstants.DRIVER_BOOK_TEST_PLAN_REQUIRED+"='YES' and "+ CyborgConstants.DRIVER_BOOK_TEST_PALN+"='"
							+ scenarioName + "'");
			
			ArrayList<String> Browserdetails = new ArrayList<String>();
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

}
